<?php
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

if (!(isset($_POST["email"]) && isset($_POST["tok"]) && isset($_POST["action"]))) {
    http_response_code(400);
    exit;
}

$email = $_POST["email"];
$tok = $_POST["tok"];
$action = $_POST["action"];
if (empty($email) || empty($tok) || empty($action)) {
    http_response_code(400);
    exit;
} else if (!($action == 'GET' || $action == 'PUT')) {
    http_response_code(400);
    exit;
}

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

if ($action == 'GET') {
    try {
        echo "status=success\r\n";
        echo "message=fetched profile\r\n";
        echo "address=" . $row["address"] . "\r\n";
        echo "city=" . $row["city"] . "\r\n";
        echo "state=" . $row["state"] . "\r\n";
        echo "zip=" . $row["zip"] . "\r\n";
        echo "title=" . $row["title"] . "\r\n";
        http_response_code(200);
        exit;
    } catch (PDOException $e) {
        http_response_code(500);
        exit;
    }
} else if ($action == 'PUT') {
    if (!(isset($_POST["address"]) && isset($_POST["city"])
        && isset($_POST["state"]) && isset($_POST["zip"])
        && isset($_POST["title"]))) {
        http_response_code(400);
        exit;
    }

    $address = $_POST["address"];
    if (empty($address)) {
        $address = $row["address"];
        if (empty($address)) {
            $address = NULL;
        }
    }
    echo "id=" . $id . "\r\n";
    echo "address=" . $address . "\r\n";

    $city = $_POST["city"];
    if (empty($city)) {
        $city = $row["city"];
        if (empty($city)) {
            $city = NULL;
        }
    }

    $state = $_POST["state"];
    if (empty($state)) {
        $state = $row["state"];
        if (empty($state)) {
            $state = NULL;
        }
    }

    $zip = $_POST["zip"];
    if (empty($zip)) {
        $zip = $row["zip"];
        if (empty($zip)) {
            $zip = NULL;
        }
    }

    $title = $_POST["title"];
    if (empty($title)) {
        $title = $row["title"];
        if (empty($title)) {
            $title = NULL;
        }
    }

    try {
        $pdostmt = "UPDATE profiles "
            . "SET "
            . "address = :address, "
            . "city = :city, "
            . "state = :state, "
            . "zip = :zip, "
            . "title = :title "
            . "WHERE id = :id";
        $stmt = $dbcon->prepare($pdostmt);
        $stmt->bindParam(":address", $address);
        $stmt->bindParam(":city", $city);
        $stmt->bindParam(":state", $state);
        $stmt->bindParam(":zip", $zip);
        $stmt->bindParam(":title", $title);
        $stmt->bindParam(":id", $id);
        $stmt->execute();

        echo "status=success\r\n";
        echo "message=profile updated\r\n";
    } catch (PDOException $e) {
        http_response_code(500);
        exit;
    }
} else {
	//error
	http_response_code(405);
	exit;
}
?>
