<?php
/**
 * Created by PhpStorm.
 * User: willstuckey
 * Date: 10/3/16
 * Time: 10:13 PM
 */
include 'creds.php';

/**
 * @param $email
 * @param $tok
 * @return bool
 */
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