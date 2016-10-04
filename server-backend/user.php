<?php
/**
 * Created by PhpStorm.
 * User: willstuckey
 * Date: 10/4/16
 * Time: 2:30 AM
 */
include 'creds.php';
include 'authentication_check.php';

try {
    $dbcon = new PDO($pdoserverstr, $dbusername, $dbpassword);
    $dbcon->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    http_response_code(500);
    exit;
}

if (!(isset($_POST["email"]) && isset($_POST["tok"]) && isset($_POST["property"]))) {
    http_response_code(400);
    exit;
}

$email = $_POST["email"];
$tok = $_POST["tok"];
$property = $_POST["property"];
if (empty($email) || empty($tok) || empty($property)) {
    http_response_code(400);
    exit;
}

// TODO validate property

if (!isAuthenticated($email, $tok)) {
    http_response_code(401);
    exit;
}

try {
    $pdostmt = "SELECT * FROM users WHERE email = :email";
    $stmt = $dbcon->prepare($pdostmt);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $propertyValue = $row[$property];

    echo "status=success\r\n";
    echo "message=retrieved value\r\n";
    echo $property . "=" .$propertyValue . "\r\n";
} catch (PDOException $e) {
    echo $e->getMessage();
    http_response_code(500);
    exit;
}