<?php
/**
 * Created by PhpStorm.
 * User: willstuckey
 * Date: 10/10/16
 * Time: 3:45 PM
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

if (!(isset($_POST["email"])
            && isset($_POST["tok"])
            && isset($_POST["type"])
            && isset($_POST["action"]))) {
    http_response_code(400);
    exit;
}

$email = $_POST["email"];
$tok = $_POST["tok"];
$type = $_POST["type"];
$action = $_POST["action"];
if (empty($email)
        || empty($tok)
        || empty($type)
        || empty($action)) {
    http_response_code(400);
    exit;
}

if ($type == "source") {
    if ($action == "GET") {

    } else if ($action == "PUT") {
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
            $id = $row["id"];

            $pdostmt = "SELECT * FROM profiles WHERE id = :id";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":id", $id);
            $stmt->execute();
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            echo $e->getMessage();
            exit;
        }
    } else {
        http_response_code(400);
        exit;
    }
} else {
    http_response_code(400);
    exit;
}

?>