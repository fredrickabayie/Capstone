<?php

/**
 * Created by PhpStorm.
 * User: fredrickabayie
 * Date: 03/04/2016
 * Time: 19:51
 */

require '../vendor/autoload.php';
require '../models/Adb.php';

/**
 * Class AdbTest
 *
 * The class that tests the Adb class to make
 * sure all the function works.
 */
class AdbTest extends PHPUnit_Framework_TestCase
{
    public $adb;

    /**
     *
     */
    public function setUp()
    {
        $this->adb = new Adb();
    }

    /**
     *
     */
    public function testConnection() {
        $connection = $this->adb->__construct();
        $this->assertTrue($connection == true);
    }
}
