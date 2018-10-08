<?php

namespace vendor;

use \Config;
use \PDO;

/**
 * A class file to connect to database
 */
final class DB {
  /** @var PDO */
  private static $db = null;

  /**
   * @return PDO
   */
  public static function getDb() {
    if (self::$db !== null) return self::$db;
    $config = Config::getConfig("db");
    self::$db = new PDO($config['dsn'], $config['username'], $config['password']);
    return self::$db;
  }

  /**
   * Realiza una consulta sql.
   * @param String $sql consulta.
   * @param array $params [opcional] parametros a inyectar.
   * @return array <b>PDOStatement::fetchAll</b>
   */
  public static function search($sql, array $params = null) {
    $statement = self::getDb()->prepare($sql);
    if ($statement->execute($params)) {
      return $statement->fetchAll(PDO::FETCH_OBJ);
    }
    self::throwDbError($statement->errorInfo());
  }

  /**
   * Realiza una busqueda sql.
   * @param String $sql consulta
   * @param array $params parametros a inyectar.
   * @param string $class_name [opciónal] El nombre de la clase a crear.
   * @return Una instancia de la clase requerida.
   */
  public static function find($sql, array $params, $class_name = null) {
    $statement = self::getDb()->prepare($sql);
    if ($statement->execute($params)) {
      return $statement->fetchObject($class_name);
    }
    self::throwDbError($statement->errorInfo());
  }

  /**
   * Executa una sentencia sql.
   * @param String $sql sentencia
   * @param array $params parametros a inyectar.
   * @return bool <b>TRUE</b> exito <b>FALSE</b> fallo.
   */
  public static function execute($sql, array $params) {
    $statement = self::getDb()->prepare($sql);
    if ($statement->execute($params)) {
      return $statement->rowCount() === 1;
    }
    self::throwDbError($statement->errorInfo());
  }

  private static function throwDbError(array $errorInfo) {
    // TODO log error, send email, etc.
    throw new Exception('DB error [' . $errorInfo[0] . ', ' . $errorInfo[1] . ']: ' . $errorInfo[2], 500);
  }

  /**
   * Construye y executa una sentencia sql insert.
   * @param String $tableName nombre de la tabla.
   * @param array $map parametros a inyectar.
   * @return bool <b>TRUE</b> exito <b>FALSE</b> fallo.
   */
  public static function insert($tableName, array $map) {
    if (empty($map)) return false;
    $attrs = $values = '';
    $i = 0;
    foreach (array_keys($map) as $name) {
      $attrs .= ($i > 0) ? ", $name" : $name;
      $values .= ($i > 0) ? ", :$name" : ":$name";
      $i++;
    }
    $sql = "INSERT INTO $tableName($attrs) VALUES($values)";
    return self::execute($sql, $map);
  }

  /**
   * Construye y executa una sentencia sql update.
   * @param String $tableName nombre de la tabla.
   * @param array $values parametros a inyectar.
   * @param array $whereArgs argumentos para cláusula WHERE.
   * @return bool <b>TRUE</b> exito <b>FALSE<s/b> fallo.
   */
  public static function update($tableName, array $values, $whereClause = NULL) {
    if (empty($values)) return false;
    $sql = "UPDATE $tableName SET ";
    $i = 0;
    foreach (array_keys($values) as $name) {
      $sql .= ($i++ > 0) ? ", $name = :$name" : "$name = :$name";
    }
    if ($whereClause != null) {
      $sql .= ' WHERE ' . $whereClause;
    }
    return self::execute($sql, $values);
  }

  /**
   * Construye y executa una sentencia sql delete.
   * @param String $tableName nombre de la tabla.
   * @param array $whereArgs argumentos para cláusula WHERE.
   * @return bool <b>TRUE</b> exito <b>FALSE<s/b> fallo.
   */
  public static function delete($tableName, $whereArgs) {
    if (empty($whereArgs)) return false;
    return self::getDb()
                    ->query("DELETE FROM $tableName WHERE $whereArgs")
                    ->rowCount() === 1;
  }

}