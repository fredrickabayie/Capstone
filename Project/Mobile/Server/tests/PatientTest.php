<?php

/**
 * Created by PhpStorm.
 * User: fredrickabayie
 * Date: 03/04/2016
 * Time: 21:42
 *
 */

require '../vendor/autoload.php';
require '../models/Patient.php';


class PatientTest extends PHPUnit_Framework_TestCase
{
    private $patient = null;


    /**
     * Function setUp
     *
     * Constructor for the PatientTest class
     */
    public function setUp()
    {
        $this->patient = new Patient();
    }


    /**
     * Function tearDown
     *
     * Destructor for the PatientTest class
     */
    public function tearDown()
    {
        $this->patient = null;
    }


    /**
     *
     */
    public function testPatientLogin(){
        $patientLogin = $this->patient->patientLogin('fredrick.abayie@ashesi.edu.gh','chms');

        $this->assertEquals(1, count($patientLogin));
    }

}
