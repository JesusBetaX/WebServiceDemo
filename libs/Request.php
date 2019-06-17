<?php

namespace libs;

use libs\UploadedFile;

final class Request {
    
  public function __destruct() {
    unset($_REQUEST);
  }

  public function __get($name) {
    return $this->get($name);
  }

  public function __set($name, $value) {
    return $this->set($name, $value);
  }

  public function __isset($name) {
    return $this->has($name);
  }

  public function __unset($name) {
    unset($_REQUEST[$name]);
  }
  
  /**
   * Regresa verdadero si un valor adicional es asociado con su nombre a la request.
   * @param $name nombre del parametro.
   * @return true si el parametro dado está presente.
   */
  public function has($name) {
    return array_key_exists($name, $_REQUEST);
  }

  /**
   * Regresa el valor asociado con la request.
   * @param $name nombre del parametro.
   * @param $defaultValue [opcional] valor por defecto.
   * @return parametro asociado.
   */
  public function get($name, $defaultValue = null) {
    return $this->has($name)
        ? $_REQUEST[$name]
        : $defaultValue;
  }

  /**
   * Asigna un valor asociado con la request.
   * @param $name nombre del parametro.
   * @param $value nuevo valor.
   */
  public function set($name, $value) {
    $_REQUEST[$name] = $value;
  }
  
  /**
   * Regresa todos los nombres del parametros de la request.
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
   * @return /libs/UploadedFile
   */
  public function getFile($name) {
    return $this->hasFile($name)
        ? new UploadedFile($_FILES[$name])
        : null;
  }
}