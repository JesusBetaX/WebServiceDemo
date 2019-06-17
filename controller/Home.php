<?php

namespace controller;

use libs\Request;

class Home {
  /**
   * Muestra la Bienvenida.
   * @param  \libs\Request  $request
   */
  public function index(Request $request) {
    require 'view/index.html';
  }
}