<?php

final class Request {

  public function __destruct() {
    unset($_REQUEST);
  }

  /**
   * Regresa verdadero si un valor adicional es asociado con su nombre a la request.
   * @param $name nombre del parametro.
   * @return true si el parametro dado está presente.
   */
  public function has($name) {
    return $this->get($name) != NULL;
  }

  /**
   * Regresa el valor asociado con la request.
   * @param $name nombre del parametro.
   * @param $defaultValue [opcional] valor por defecto.
   * @return parametro asociado.
   */
  public function get($name, $defaultValue = NULL) {
    return array_key_exists($name, $_REQUEST) ? $_REQUEST[$name] : $defaultValue;
  }

  /**
   * Regresa todos los nombres de los parametros de la request.
   * @return array
   */
  public function keys() {
    return array_keys($_REQUEST);
  }
  
  /**
   * Regresa todos los valores de los parametros de la request.
   * @return array
   */
  public function values() {
    return array_values($_REQUEST);
  }

  /**
   * Regresa el numero de parametros
   * @return int
   */
  public function size() {
    return count($_REQUEST);
  }

  /**
   * Regresa verdadero si un archivo adicional es asociado con su nombre a la request.
   * @param $name nombre del archivo.
   * @return true si el archivo dado está presente.
   */
  public function hasFile($name) {
    return is_uploaded_file($_FILES[$name]['tmp_name']);
  }

  /**
   * Regresa un archivo asociado a la request
   * @param $name nombre del archivo.
   * @return /libs/File
   */
  public function getFile($name) {
    return $this->hasFile($name) ? new File($_FILES[$name]) : NULL;
  }

}