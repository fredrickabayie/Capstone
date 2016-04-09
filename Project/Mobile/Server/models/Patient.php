<?php

/**
 * Created by PhpStorm.
 * User: fredrickabayie
 * Date: 03/04/2016
 * Time: 20:22
 */

require_once 'Adb.php';
include_once 'PatientInterface.php';

/**
 * Class Patient
 *
 * The patient class models a patient in the chms
 * system. The class models
 *
 */
class Patient extends Adb implements PatientInterface
{

    /**
     * Function to allow patients to login
     * to the mobile application by providing
     * their email address and password
     * provided by the system administrator
     *
     * @param $email
     * @param $password
     * @return mixed
     */
    public function patientLogin($email, $password)
    {
        $patientLoginQuery = /** @lang MySQL */
            <<<PATIENTLOGINQUERY
                SELECT
                  `chms_local_patient_registration`.`email`,
                  `chms_local_patient_registration`.`password`
                FROM `chms_local_patient_registration`
                WHERE `chms_local_patient_registration`.`email` = ?
                      AND `chms_local_patient_registration`.`password` = ?
                LIMIT 1
PATIENTLOGINQUERY;

        if ($statement = $this->prepare($patientLoginQuery)) {
            $statement->bind_param("ss", $email, $password);
            $statement->execute();
            return $statement->get_result();
        }
        $statement->close();
        return false;
    }
}