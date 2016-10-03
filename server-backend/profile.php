<?php
include 'creds.php';
include 'authenticate.php';

try {
        $dbcon = new PDO($pdoserverstr, $dbusername, $dbpassword);
        $dbcon->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
        $dbcon->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
        http_response_code(500);
        exit;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
	if (!(isset($_POST["email"]) && isset($_POST["tok"]))) {
        http_response_code(400);
        exit;
	}

	$email = $_POST["email"];
    $tok = $_POST["tok"];
	if (empty($email) || empty($tok)) {
	    http_response_code(400);
        exit;
    }

    if (!isAuthenticated($email, $tok)) {
        http_response_code(401);
        exit;
    }


} else if ($_SERVER["REQUEST_METHOD"] == "GET") {
    if (!(isset($_GET["email"]) && isset($_GET["tok"]))) {
        http_response_code(400);
        exit;
    }

    $email = $_GET["email"];
    $tok = $_GET["tok"];
    if (empty($email) || empty($tok)) {
        http_response_code(400);
        exit;
    }

    if (!isAuthenticated($email, $tok)) {
        http_response_code(401);
        exit;
    }

    $pdostmt = "SELECT id FROM users WHERE email = :email";
    $stmt = $dbcon->prepare($pdostmt);
    $stmt->bindParam(":email", $email);
    $stmt->execute();
    $id = $stmt->fetch(PDO::FETCH_ASSOC);

    $pdostmt = "SELECT * FROM profile WHERE id = :id";
    $stmt = $dbcon->prepare($pdostmt);
    $stmt->bindParam(":id", $id);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);

    echo "status=success";
    echo "message=fetched profile";
    echo "address=" . $row["address"];
    echo "city=" . $row["city"];
    echo "state=" . $row["state"];
    echo "zip=" . $row["zip"];
    echo "title=" . $row["title"];
} else {
	//error
	http_response_code(405);
	exit;
}
?>
