<?php

/**
 * Created by PhpStorm.
 * User: fredrickabayie
 * Date: 03/04/2016
 * Time: 20:24
 */

/**
 * Interface PatientInterface
 *
 * This is the interface that models the functions
 * of the Patient class.
 */
interface PatientInterface
{

    /**
     * Function handle patient logins
     *
     * @param $email
     * @param $password
     * @return mixed
     */
    public function patientLogin($email, $password);
}