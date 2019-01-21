<?php

namespace controller;

use vendor\Request;
use vendor\DB;

class Persona {

  /**
   * Muestra un listado del recurso.
   * @param  \libs\Request  $request
   */
  public function index(Request $request) {
    $sql = '
        select * from persona 
        where concat(nombre, apellidos) like ? 
        order by nombre asc'
    ;
    // Busca registros con el criterio de busqueda 'search'.
    return DB::query($sql, array(
        '%' . trim($request->get('search')) . '%'
    ));
  }

  /**
   * Muestra un recurso por su identificador.
   * @param  \libs\Request  $request
   */
  public function find(Request $request) {
    $persona = DB::get('select * from persona where id = ?', array(
      	$request->get('id')
    ));
    // Muestra el json.
    return array('persona' => $persona);
  }

  /**
   * Inserta el recurso.
   * @param  \libs\Request  $request
   */
  public function insert(Request $request) {
    $sql = '
      	insert into persona(nombre, apellidos)
      	values(?, ?)
    ';
    $success = DB::execute($sql, array(
      	$request->get('nombre'),
      	$request->get('apellidos')
    ));
    // Muestra el json. 
    return array('success' => $success);
  }

  /**
   * Modifica el recurso.
   * @param  \libs\Request  $request
   */
  public function update(Request $request) {
    $sql = '
      	update persona set
      	nombre = ?,
     	  apellidos = ?
      	where id = ?
    ';
    $success = DB::execute($sql, array(
      	$request->get('nombre'),
      	$request->get('apellidos'),
      	$request->get('id')
    ));
    // Muestra el json.
    return array('success' => $success);
  }

  /**
   * Elimina el recurso por su identificador.
   * @param  \libs\Request  $request
   */
  public function delete(Request $request) {
    $success = DB::execute('delete from persona where id = ?', array(
      	$request->get('id')
    ));
    // Muestra el json.
    return array('success' => $success);
  }

}