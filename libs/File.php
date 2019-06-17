<?php

namespace libs;

final class File {

  private $file;

  public function __construct(&$file) {
    $this->file = $file;
  }

  public function __destruct() {
    $this->file = null;
  }

  /**
   * Obtiene el tamaño del archivo.
   * @return int
   */
  public function size() {
    return $this->file['size'];
  }

  /**
   * Recupera el nombre original del archivo que se pasa.
   * @return String
   */
  public function getBaseName() {
    return $this->file['name'];
  }

  /**
   * Recupera el nombre temporal del archivo.
   * @return String
   */
  public function getTmpName() {
    return $this->file['tmp_name'];
  }

  /**
   * Recupera el tipo de archivo.
   * @return String
   */
  public function getType() {
    return pathinfo($this->file['type'], PATHINFO_BASENAME);
  }

  /**
   * Recupera la extencion de archivo.
   * @return String
   */
  public function getExtension() {
    return pathinfo($this->getBaseName(), PATHINFO_EXTENSION);
  }

  /**
   * Recupera el nombre completo del archivo.
   * @return String
   */
  public function getFileName() {
    return pathinfo($this->getBaseName(), PATHINFO_FILENAME);
  }

  /**
   * Generamos un nombre aleatorio para la imagen.
   * @return String
   */
  public function getRandomName() {
    // Recumeramos la extencion de la imagen.
    $ext = pathinfo($this->getBaseName(), PATHINFO_EXTENSION);
    $current = round(microtime(true) * 1000);
    $random = rand(100000, 999999);
    return $current . '_' . $random . '.' . $ext;
  }

  /**
   * Asigna el nombre antiguo del archivo para 
   * reemplazarlo cuando se ejecute la función 'save'.
   * @param String nameFile antiguo nombre del archivo a remplazar.
   */
  public function setOldName($nameFile) {
    $this->file['oldName'] = $nameFile;
  }

  /**
   * Recupera el antiguo nombre del archivo.
   * @return String nombre del archivo.
   */
  public function getOldName() {
    return array_key_exists('oldName', $this->file) ? $this->file['oldName'] : null;
  }

  /**
   * Guarda o remplaza un archivo.
   * @param $src directorio donde se guardara el archivo.
   * @param $nameFile nombre que se le pondra al archivo.
   * @return true si el archivo se guardo correctamente.
   */
  public function save($src, $nameFile) {
    $copy = copy($this->getTmpName(), $src . $nameFile);
    $oldName = $this->getOldName();
    // Si tenia un archivo antiguo lo elimina.
    if (!empty($oldName) && $copy) {
      unlink($src . $oldName);
    }
    return $copy;
  }

}