<?php

/**
 * Archivo para configurar nuestra aplicación.
 */
return [
    /**
     * Configuración para la base de datos.
     */
    'db' => [
        'dsn' => 'mysql:host=127.0.0.1;dbname=WebServiceDemoDB;charset=utf8',
        'username' => 'root',
        'password' => 'abc1234'
    ],
    /**
     * Configuración para la paguina.
     */
    'web' => [
        // Controlador por default o main.
        // Esté controlador sera el primero en ejecutarse.
        'controller' => 'Home',
        // Acción por default para todos los controladores.
        // Esta función debera estar definida en todos los controladores.
        'action' => 'index'
    ]
];
