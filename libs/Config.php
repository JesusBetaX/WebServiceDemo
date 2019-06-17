<?php

namespace libs;

/**
 * Application config.
 */
final class Config {
  /** @var array config data */
  private static $data = null;

  /**
   * @return array
   * @throws Exception
   */
  public static function getConfig($section = null) {
    if ($section === null) return self::getData();
    $data = self::getData();
    if (!array_key_exists($section, $data)) {
      throw new Exception('Unknown config section: ' . $section);
    }
    return $data[$section];
  }

  /**
   * @return array
   */
  private static function getData() {
    if (self::$data !== null) return self::$data;
    return self::$data = require_once 'config/config.php';
  }

}