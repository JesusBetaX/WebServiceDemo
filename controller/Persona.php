<?php

namespace controller;

use libs\Request;
use libs\DB;

class Persona {

  /**
   * Muestra un listado del recurso.
   * @param  \libs\Request  $request
   */
  public function index(Request $request) {
    // Abrimos la base de datos
    $db = DB::connect(/*db*/);

    $search = '%' . trim($request->search) . '%';
    $sql = '
        select * from persona 
        where concat(nombre, apellidos) like ? 
        order by nombre asc'
    ;

    // Busca registros con el criterio de busqueda 'search'.
    $cursor = $db->query( $sql, [$search] );
    return $cursor->fetchAll();
  }

  /**
   * Muestra un recurso por su identificador.
   * @param  \libs\Request  $request
   */
  public function find(Request $request) {
    // Abrimos la base de datos
    $db = DB::connect(/*db2*/);

    $cursor = $db->cursor('select * from persona where id = ?');
    $cursor->execute([ $request->id ]);

    // Muestra el json.
    return ['persona' => $cursor->fetchOne()];
  }

  /**
   * Inserta el recurso.
   * @param  \libs\Request  $request
   */
  public function insert(Request $request) {
    // Abrimos la base de datos
    $db = DB::connect();
/*
    $cursor = $db->cursor('
        insert into persona(nombre, apellidos)
        values(?, ?)
    ');

    $success = $cursor->execute([
      	$request->nombre,
      	$request->apellidos
    ]);
*/
    $id = $db->insert('persona', [
    	'nombre'    => $request->nombre,
      'apellidos' => $request->apellidos
    ]);

    $success = $id > 0;

    // Muestra el json.
    return ['success' => $success];
  }

  /**
   * Modifica el recurso.
   * @param  \libs\Request  $request
   */
  public function update(Request $request) {
    // Abrimos la base de datos
    $db = DB::connect();
/*
    $cursor = $db->cursor('
      	update persona set
      	  nombre = ?,
     	    apellidos = ?
      	where id = ?
    ');

    $success = $cursor->execute([
      	$request->nombre,
      	$request->apellidos,
      	$request->id
    ]);
*/
    $rows = $db->update('persona', [
    	'nombre'    => $request->nombre,
      'apellidos' => $request->apellidos
    ], 'id = ' . $request->id);
    
    $success = $rows > 0;

    // Muestra el json.
    return ['success' => $success];
  }

  /**
   * Elimina el recurso por su identificador.
   * @param  \libs\Request  $request
   */
  public function delete(Request $request) {
    // Abrimos la base de datos
    $db = DB::connect();
/*
    $cursor = $db->cursor('delete from persona where id = ?');
    $success = $cursor->execute([ $request->id ]);
*/
    $rows = $db->delete('persona', 'id = ' . $request->id);
    $success = $rows > 0;
    
    // Muestra el json.
    return ['success' => $success];
  }

}