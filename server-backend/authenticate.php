<?php
include 'creds.php';
error_reporting(E_ALL);

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


if (!(isset($_POST["email"]) && isset($_POST["password"]))) {
	echo "credentials existence check failed\r\n";
	http_response_code(400);
	exit;
}

$email = $_POST["email"];
$password = $_POST["password"];
if (empty($email) || empty($password)) {
	echo "one or more crednetials are empty\r\n";
	http_response_code(400);
	exit;
}


try {
	$pdostmt = "SELECT * FROM users WHERE email = :email";
	$stmt = $dbcon->prepare($pdostmt);
	$stmt->bindParam(":email", $email);
	$stmt->execute();
	$row = $stmt->fetch(PDO::FETCH_ASSOC);
	if (empty($row)) {
		echo "status=failed\r\n";
		echo "message=matching credentials not found\r\n";
		echo "sessionid=null\r\n";
		exit;
	}

	$dbsalt = $row["salt"];
	$dbpwhash = $row["pwhash"];
	$dbpwattempts = $row["pwattempts"];
	$dbsessionid = $row["sessionid"];
	$dbsessionts = $row["sessiontimestamp"];

	// TODO: bounds check pwattempts

	$ts = microtime(TRUE);
	$time_since_last_session_creation = $ts - $dbsessionts;
	$hash = hash("sha512", $dbsalt . $password, TRUE);
	if ((bin2hex($dbpwhash) === bin2hex($hash))
		|| ($time_since_last_session_creation < $session_timeout_mt
		&& $password == bin2hex($dbsessionid))) {
		// user provided email + password
		// OR user provided email + unexpired sessionid
		$sessionid = random_bytes(64);

		$pdostmt = "UPDATE users "
			. "SET "
			. "sessionid = :sessionid, "
			. "sessiontimestamp = :sessionts, "
			. "pwattempts = 0 "
			. "WHERE email = :email";
		$stmt = $dbcon->prepare($pdostmt);
		$stmt->bindParam(":sessionid", $sessionid);
		$stmt->bindParam(":sessionts", $ts);
		$stmt->bindParam(":email", $email);
		$stmt->execute();
		echo "status=success\r\n";
		echo "message=authentication successful\r\n";
		echo "sessionid=" . bin2hex($sessionid) . "\r\n";
		http_response_code(200);
		$dbcon = null;
		exit;
	} else {
		echo "status=failed\r\n";
		echo "message=matching credentials not found\r\n";
		echo "sessionid=null\r\n";
		exit;
	}
} catch (PDOException $e) {
	http_response_code(500);
	exit;
}

function isAuthenticated($email, $tok) {
	global $session_timeout_mt;
	global $pdoserverstr;
	global $dbusername;
	global $dbpassword;

	try {
		$dbcon = new PDO($pdoserverstr, $dbusername, $dbpassword);
		$dbcon->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
		$dbcon->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

		$pdostmt = "SELECT * FROM users WHERE email = :email";
		$stmt = $dbcon->prepare($pdostmt);
		$stmt->bindParam(":email", $email);
		$stmt->execute();
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		if (empty($row)) {
			echo "status=failed\r\n";
			echo "message=matching credentials not found\r\n";
			echo "sessionid=null\r\n";
			exit;
		}

		$dbsalt = $row["salt"];
		$dbpwhash = $row["pwhash"];
		$dbpwattempts = $row["pwattempts"];
		$dbsessionid = $row["sessionid"];
		$dbsessionts = $row["sessiontimestamp"];

		// TODO: bounds check pwattempts

		$ts = microtime(TRUE);
		$time_since_last_session_creation = $ts - $dbsessionts;
		$hash = hash("sha512", $dbsalt . $tok, TRUE);
		if ((bin2hex($dbpwhash) === bin2hex($hash))
			|| ($time_since_last_session_creation < $session_timeout_mt
				&& $tok == bin2hex($dbsessionid))
		) {
			// user provided email + password
			// OR user provided email + unexpired sessionid
			return true;
		}
	} catch (PDOException $e) {
		return false;
	}

	return false;
}
?>
