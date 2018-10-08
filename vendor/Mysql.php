<?php

namespace vendor;

use \PDO;
use \PDOStatement;
use vendor\Config;

class Mysql 
{
  private $pdo;
  private static $instance;

  public function __construct($dsn, $username, $password) {
    $this->pdo = new PDO($dsn, $username, $password);
  }

  public function __destruct() {
    $this->pdo = null;
  }

  public static function getInstance() {
    if (self::$instance == null) {
      $config = Config::getConfig("db");
      self::$instance = new Mysql(
        $config['dsn'], $config['username'], $config['password']
      );
    }
    return self::$instance;
  }

  public function cursor() {
    return new Cursor($this->pdo);
  }
}

class Cursor 
{
  private $pdo; // PDO
  private $statement; // PDOStatement

  public function __construct($pdo) {
    $this->pdo = $pdo;
  }

  public function __destruct() {
    $this->close();
  }

  public function close() {
    if (!is_null($this->statement)) {
      $this->statement->closeCursor();
    }
  }

  public function execute($sql, $params = null) {
    $this->statement = $this->pdo->prepare($sql);
    return $this->statement->execute($params);
  }

  public function rowCount() {
    return $this->statement->rowCount();
  }

  public function fetchObject($classOf = null) {
    return $this->statement->fetchObject($classOf);
  }

  public function fetchAll() {
    return $this->statement->fetchAll(PDO::FETCH_OBJ);
  }

  public function fetchArray() {
    return $this->statement->fetchAll(PDO::FETCH_ASSOC);
  }
}