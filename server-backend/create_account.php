<?php
include 'creds.php';
include 'util/email_validator.php';
include 'util/password_validator.php';
error_reporting(0);
// error_reporting(E_ALL);

// connect to database
try {
	$dbcon = new PDO($pdoserverstr, $dbusername, $dbpassword);
	$dbcon->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
	$dbcon->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	echo "could not connect to database\r\n";
	http_response_code(500);
	exit;
}

// validate information request method
/*
if ($_SERVER["REQUEST_METHOD"] != "POST") {
	http_response_code(400);
	exit;
}
*/

// validate input existence
if (!(isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["email"])
		&& isset($_POST["firstname"]) && isset($_POST["lastname"]) && isset($_POST["type"]))) {
	echo "existence validation failedr\r\n";
	http_response_code(400);
	exit;
}

$username = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];
$firstname = $_POST["firstname"];
$lastname = $_POST["lastname"];
$type = $_POST["type"];
if (empty($type)) {
	$type = "USER";
}


// validate content present
if (empty($username) || empty($password) || empty($email)
		|| empty($firstname) || empty($lastname)) {
	echo "a parameter was empty\r\n";
	http_response_code(400);
	exit;
}

// validate password
if (!isValidPassword($password)) {
	echo "status=failed\r\n";
	echo "message=invalid password\r\n";
	exit;
}

// validate email
if (!isValidEmail($email)) {
	echo "status=failed\r\n";
	echo "message=invalid email\r\n";
	exit;
}

// create server generated data
$userid = uniqid("", TRUE);
$salt = random_bytes(64);
$hash = hash("sha512", $salt . $password, TRUE);
$sessiontimestamp = -1;
$sessionid = -1;
$pwattempts = -1;

// check for existing user
try {
	$pdostmt = "SELECT * FROM users WHERE email = :email";
	$stmt = $dbcon->prepare($pdostmt);
	$stmt->bindParam(":email", $email);
	$stmt->execute();
	$row = $stmt->fetch();
	if (empty($row)) {
		$exists = false;
	} else {
		$exists = true;
		echo "status=failed\r\n";
		echo "message=existing user found\r\n";
		exit;
	}
} catch (PDOException $e) {
	echo "prepared statement exception: " . $e->getMessage();
	http_response_code(500);
	exit;
}

// add new user if the email isn't already in use
if (!$exists) {
	try {
		$pdostmt = "INSERT INTO users (id, type, username, email, salt, "
			. "pwhash, sessionid, sessiontimestamp, pwattempts, "
			. "firstname, lastname) "
			. "VALUES (:id, :type, :username, :email, :salt, "
			. ":pwhash, :sessionid, :sessiontimestamp, :pwattempts, "
			. ":firstname, :lastname)";
		$stmt = $dbcon->prepare($pdostmt);
		$stmt->bindParam(":id", $userid);
		$stmt->bindParam(":type", $type);
		$stmt->bindParam(":username", $username);
		$stmt->bindParam(":email", $email);
		$stmt->bindParam(":salt", $salt);
		$stmt->bindParam(":pwhash", $hash);
		$stmt->bindParam(":sessionid", $sessionid);
		$stmt->bindParam(":sessiontimestamp", $sessiontimestamp);
		$stmt->bindParam(":pwattempts", $pwattempts);
		$stmt->bindParam(":firstname", $firstname);
		$stmt->bindParam(":lastname", $lastnane);
		$stmt->execute();

		$pdostmt = "INSERT INTO profiles (id) VALUES (:id)";
		$stmt = $dbcon->prepare($pdostmt);
		$stmt->bindParam(":id", $userid);
		$stmt->execute();

		echo "status=success\r\n";
		echo "message=account created successfully";
	} catch (PDOException $e) {
		echo "prepared statement exception: " . $e->getMessage();
		http_response_code(500);
		exit;
	}
}

// TODO: send email
$dbcon = null;
?>
