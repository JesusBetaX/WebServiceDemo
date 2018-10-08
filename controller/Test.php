<?php

namespace controller;

use vendor\Request;
use vendor\Mysql;

class Test {
  /**
   * Muestra la Bienvenida.
   * @param  \libs\Request  $request
   */
  public function index(Request $request) {
    $mysql = Mysql::getInstance();
    
    $cursor = $mysql->cursor();
    $cursor->execute('SELECT * FROM persona');
    return $cursor->fetchArray();
  }
}