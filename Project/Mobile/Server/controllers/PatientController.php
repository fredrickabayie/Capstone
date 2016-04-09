<?php
/**
 * Created by PhpStorm.
 * User: fredrickabayie
 * Date: 03/04/2016
 * Time: 23:00
 */

if (isset ($_REQUEST ['cmd'])) {

    $command = $_REQUEST['cmd'];

    switch ($command) {

        case 1:
            patientLoginController();
            break;

        default:
            echo '{"result":0,status:"unknown command"}';
            break;
    }

}


/**
 * Function patientLoginController
 *
 * This function will handle a patient login details
 * and also check if the user is valid.
 */
function patientLoginController()
{
    $email = null;
    $password = null;

    if (isset($_GET['email']) && isset($_GET['password'])
        && !empty($_GET['password']) && !empty($_GET['email'])
    ) {
        require_once '../models/Patient.php';
        $patient = new Patient();

        $email = filter_var($_GET['email'], FILTER_VALIDATE_EMAIL);
        $password = $_GET['password'];

        $result = $patient->patientLogin($email, $password);
        $patientDetails = $result->fetch_array(MYSQLI_ASSOC);

        if ($patientDetails) {
            echo json_encode($patientDetails);
        } else {
            echo 'failed';
        }
    } else {
        echo json_encode("{'error':'please provide necessary details'}");
    }
}