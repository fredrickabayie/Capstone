<?php

/**
 * Created by PhpStorm.
 * User: fredrickabayie
 * Date: 17/12/2015
 * Time: 17:13
 */

include_once '../config/database.php';

/**
 * Class Adb
 *
 * This is a class to model the mysqli instances,
 * thus to handle connections, querying, fetching
 * and closing of the connection to the database
 * server. It also extends 'mysqli' to be able to
 * access it methods and objects
 *
 */
class Adb extends mysqli
{

    /**
     * Adb constructor.
     *
     * Function to establish a connection each time
     * the adb class is instantiated. The constructor
     * takes in the host, username, password, the name
     * of the database and the port as its parameters
     *
     * @internal param string $host
     * @internal param string $username
     * @internal param string $passwd
     * @internal param string $dbname
     * @internal param int $port
     */
    public function __construct()
    {
        parent::__construct(DB_HOST, DB_USER, DB_PWORD, DB_NAME, DB_PORT);

        if (mysqli_connect_error()) {
            die('Connection Error (' . mysqli_connect_errno() . ') ' . mysqli_connect_error());
        }
    }

    /**
     *
     */
    public function okay(){
        return true;
    }


    /**
     *Adb Destructor
     *
     * Function to close all the connection established
     * to the database server. This is the last function
     * that runs each time the adb class is referred to
     */
    public function __destruct()
    {
        $this->close();
    }

}