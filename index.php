<?php

// -------------------------------------------------------------------
//  CONFIGURACION PARA LA APLICACION
// -------------------------------------------------------------------
require_once 'libs/Config.php';

/**
 * Configuración inicial.
 */
error_reporting(E_ALL | E_STRICT);
mb_internal_encoding('UTF-8');
set_error_handler('handleError');
set_exception_handler('handleException');
spl_autoload_register('loadClass');

/**
 * Manipulador de errores.
 * @param int $severity
 * @param string  $message
 * @param string  $filepath
 * @param int $line
 */
function handleError($severity, $message, $filepath, $line) {
  require 'view/error/error.php';
  exit(1);
}

/**
 * Manipulador de excepciónes.
 * @param Exception $error
 */
function handleException(Exception $error) {
  require 'view/error/exception.php';
}

/**
 * Cargador de clases. Este metodo se encarga de importar las clases 
 * que definimos en el archivo config.php 'classes'
 * @param String $name nombre de la clase.
 */
function loadClass($name) {
  $classes = Config::getConfig('classes');
  if (!array_key_exists($name, $classes)) {
    throw new Exception("Class \"$name\" not found.", 404);
  }
  require_once $classes[$name];
}

// -------------------------------------------------------------------
//  RECUPERACION DEL CONTROLADOR Y LA ACCION DE LA URL 
// -------------------------------------------------------------------

$_URL = array();
if (array_key_exists('url', $_GET)) {
  $_URL = rtrim($_GET['url'], '/');
  $_URL = explode('/', filter_var($_URL, FILTER_SANITIZE_URL));
}

$webConfig = Config::getConfig('web');

/**
 * -------------------------------------------------------------------
 *  NOMBRE DEL CONTROLADOR
 * -------------------------------------------------------------------
 *
 * Recupera el controlador de la url : "index.php/{controller}", si no 
 * se encontro un controlador se asignara el controlador por default 
 * del "$webConfig". 
 */
$class = ucfirst(
        array_key_exists(0, $_URL) ? $_URL[0] : $webConfig['controller']
);

/**
 * -------------------------------------------------------------------
 *  NOMBRE DE LA ACCION DEL CONTROLADOR A LLAMAR
 * -------------------------------------------------------------------
 *
 * Recupera la acción de la url : "index.php/{controller}/{action}",
 * si no se encontro la acción se asignara la acción por default del 
 * "$webConfig". 
 */
$method = strtolower(
        array_key_exists(1, $_URL) ? $_URL[1] : $webConfig['action']
);

// -------------------------------------------------------------------
//  EJECUTAMOS LA APLICACION
// -------------------------------------------------------------------
// Validamos si existe el controlador en la carpeta "controller".
if (!file_exists("controller/$class.php")) {
  throw new Exception("El controlador \"$class\" no existe en la carpeta \"controller\".", 404);
  //throw new Exception("Page Not Found.", 404);
}

// Instanciamos el controlador.
require_once "controller/$class.php";
$controller = new $class();


// Validamos si existe el metodo en el controlador.
if (!method_exists($controller, $method)) {
  throw new Exception("La función \"$method\" no existe en \"$class\".", 404);
  //throw new Exception("Page Not Found.", 404);
}

// Llama la accion del controlador. Y le pasamos la request.
$return = call_user_func(
        array($controller, $method), new Request()
);

if (!is_null($return)) {
  header("Content-Type: application/json; charset=utf-8");
  echo json_encode($return);
}

