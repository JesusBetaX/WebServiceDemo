<?php

namespace libs;

use \Exception;
use \PDO;
use libs\Config;

class DB 
{
  private $pdo; // as PDO
  private static $pool = array();

  public function __construct($dsn, $username, $password) {
    $this->pdo = new PDO($dsn, $username, $password);
  }

  public static function connect($tag = 'db') {
    if (array_key_exists($tag, self::$pool)) {
      return self::$pool[$tag];
    }
    $conf = Config::getConfig($tag);
    self::$pool[$tag] = new DB($conf['dsn'], $conf['username'], $conf['password']);
    return self::$pool[$tag];
  }

  public function cursor($sql) {
    $statement = $this->pdo->prepare($sql);
    return new Cursor($statement);
  }

  /** Obtiene el ultimo id insertado. */
  public function lastInsertId($name = null) {
    return $this->pdo->lastInsertId($name);
  }

  /**
   * Ejecuta una consulta a la base de datos.
   */
  public function query($sql, $params = array()) {
    $cursor = $this->cursor($sql);
    $cursor->execute($params);
    return $cursor;
  }

  /**
   * Construye y executa una sentencia sql insert.
   * @param String $table nombre de la tabla.
   * @param fixed $obj parametros a inyectar.
   * @return int <b>id</b> del registro insertado <b>-1</b> fallo.
   */
  public function insert($table, $obj) {
    $params = is_array($obj) ? $obj : get_object_vars($obj);
    $keys = array_keys($params);
    $values = array_fill(0, count($params), '?');
    
    $sql = 'INSERT INTO '.$table.' ('.implode(', ', $keys).') VALUES ('.implode(', ', $values).')';
    $cursor = $this->cursor($sql);
    
    $inserted = $cursor->execute(array_values($params));
    return ($inserted) ? (int) $this->lastInsertId() : -1;
  }

  /**
   * Construye y executa una sentencia sql update.
   * @param String $table nombre de la tabla.
   * @param fixed $obj parametros a inyectar.
   * @param String $whereArgs argumentos para cláusula WHERE.
   * @return int numero de filas afectadas.
   */
  public function update($table, $obj, $whereArgs = NULL) {
    $params = is_array($obj) ? $obj : get_object_vars($obj);

    $sql = "UPDATE $table SET ";
    $i = 0;
    foreach (array_keys($params) as $key) {
      $sql .= ($i++ > 0) ? (", $key = ?") : ("$key = ?");
    }
    if ($whereArgs != null) {
      $sql .= ' WHERE ' . $whereArgs;
    }

    $cursor = $this->cursor($sql);
    $cursor->execute(array_values($params));
    return $cursor->rowCount();
  }

  /**
   * Construye y executa una sentencia sql delete.
   * @param String $table nombre de la tabla.
   * @param String $whereArgs argumentos para cláusula WHERE.
   * @return int numero de filas afectadas.
   */
  public function delete($table, $whereArgs = NULL) {
    $sql = "DELETE FROM $table"; 
    if ($whereArgs != null) {
      $sql .= ' WHERE ' . $whereArgs;
    }
    $cursor = $this->cursor($sql);
    $cursor->execute();
    return $cursor->rowCount();
  }
}


class Cursor implements \Iterator
{
  private $statement; // as PDOStatement
  public $row_factory = null;
  private $index = 0;

  public function __construct($statement) {
    $this->statement = $statement;
  }
  public function __destruct() {
    $this->close();
    $this->statement = null;
    $this->row_factory = null;
    $this->index = null;
  }

  public function rowCount() {
    return $this->statement->rowCount();
  }

  public function execute($params = array()) {
    if ($this->statement->execute($params)) {
      return $this->rowCount() > 0;
    }
    self::throwDbError($this->statement->errorInfo());
  }

  public function fetchOne() {
    return $this->statement->fetchObject($this->row_factory);
  }
  
  public function fetchMany($size) {
    $array = array();
    for ($i = 0; $i < $size; $i++) {
      $array[$i] = $this->fetchOne();
    }
    return $array;
  }

  public function fetchAll() {
    return $this->fetchMany($this->rowCount());
  }
    
  //public function fetchArray() {
  //  return $this->statement->fetchAll(PDO::FETCH_ASSOC);
  //}
  
  public function close() {
    $this->statement->closeCursor();
  }

  // getIterator
  public function current() {
    return $this->fetchOne();
  }
  public function key() {
    return $this->index;
  }
  public function next() {
    $this->index++;
  }
  public function rewind() {
  }
  public function valid() { 
    return $this->index < $this->rowCount();
  }

  private static function throwDbError(array $errorInfo) {
    // TODO log error, send email, etc.
    throw new Exception('DB error [' . $errorInfo[0] . ', ' . $errorInfo[1] . ']: ' . $errorInfo[2], 500);
  }
 
}
