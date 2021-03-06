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

if (!(isset($_POST["reporttype"]) && isset($_POST["action"]))) {
    http_response_code(400);
    exit;
}

$reporttype = $_POST["reporttype"];
$action = $_POST["action"];
if (empty($reporttype) || empty($action)) {
    http_response_code(400);
    exit;
}

$email = "";
$tok = "";
if ($action != "GET") {
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

    //TODO: probably move auth here...
}

if ($reporttype == "source") {
    if ($action == "GET") {
        if (isset($_POST["reportid"]) && !empty($_POST["reportid"])) {
            try {
                $reportid = $_POST["reportid"];
                if ($reportid == "ALL") {
                    $pdostmt = "SELECT * FROM sourcereports";
                    $stmt = $dbcon->prepare($pdostmt);
                    $pdocntstmt = "SELECT count(*) FROM sourcereports";
                    $cntstmt = $dbcon->prepare($pdocntstmt);
                } else {
                    $pdostmt = "SELECT * FROM sourcereports WHERE reportid = :reportid";
                    $stmt = $dbcon->prepare($pdostmt);
                    $stmt->bindParam(":reportid", $reportid);
                    $pdocntstmt = "SELECT count(*) FROM sourcereports WHERE reportid = :reportid";
                    $cntstmt = $dbcon->prepare($pdocntstmt);
                    $cntstmt->bindParam(":reportid", $reportid);
                }
                $stmt->execute();
                $cntstmt->execute();
                $count = $cntstmt->fetchColumn();

                echo "status=success\r\n";
                echo "message=fetched report\r\n";
                echo "variablepayload=" . $count . "\r\n";
                echo "--- BEGIN ---\r\n";
                $resultnum = 1;
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC, PDO::FETCH_ORI_NEXT)) {
                    $id = $row["id"];
                    $pdounstmt = "SELECT * FROM users WHERE id = :id";
                    $unstmt = $dbcon->prepare($pdounstmt);
                    $unstmt->bindParam(":id", $id);
                    $unstmt->execute();
                    $unrow = $unstmt->fetch(PDO::FETCH_ASSOC);
                    $username = $unrow["username"];

                    echo "" . $resultnum . ":";
                    echo "username=" . $username . "|";
                    echo "id=" . $id . "|";
                    echo "reportid=" . $row["reportid"] . "|";
                    echo "reportdt=" . $row["reportdt"] . "|";
                    echo "location=" . $row["location"] . "|";
                    echo "type=" . $row["type"] . "|";
                    echo "cond=" . $row["cond"] . "|";
                    echo "name=" . $row["name"] . "|";
                    echo "description=" . $row["description"];
                    echo "\r\n";
                    $resultnum++;
                }
                echo "--- END ---\r\n";
            } catch (PDOException $e) {
                http_response_code(500);
                exit;
            }
        } else {
            // search indexing
        }
    } else if ($action == "ADD") {
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
            $username = $row["username"];

            $reportid = uniqid("", TRUE);
            $pdostmt = "INSERT INTO sourcereports "
                . "(id, reportid) "
                . "VALUES "
                . "(:id, :reportid)";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":id", $id);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();

            echo "status=success\r\n";
            echo "message=void source report created\r\n";
            echo "submitter=" . $username . "\r\n";
            echo "reportid=" . $reportid . "\r\n";
        } catch (PDOException $e) {
            //http_response_code(500);
            echo $e;
            exit;
        }
    } else if ($action == "UPDATE") {
        if (!(isset($_POST["reportid"]))) {
            http_response_code(400);
            exit;
        }

        $reportid = $_POST["reportid"];
        if (empty($reportid)) {
            http_parse_params(400);
            exit;
        }

        if (!isAuthenticated($email, $tok)) {
            http_response_code(401);
            exit;
        }

        try {
            $pdostmt = "SELECT * FROM sourcereports WHERE reportid = :reportid";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();
            $row = $stmt->fetch(PDO::FETCH_ASSOC);

            $id = $row["id"];
            $reportdt = $row["reportdt"];
            $location = $row["location"];
            $type = $row["type"];
            $cond = $row["cond"];
            $name = $row["name"];
            $description = $row["description"];

            if (isset($_POST["id"]) && !empty($_POST["id"])) {
                $id = $_POST["id"];
            }

            if (empty($id)) {
                $id = NULL;
            }

            if (isset($_POST["reportdt"]) && !empty($_POST["reportdt"])) {
                $reportdt = $_POST["reportdt"];
            }

            if (empty($reportdt)) {
                $reportdt = NULL;
            }

            if (isset($_POST["location"]) && !empty($_POST["location"])) {
                $location = $_POST["location"];
            }

            if (empty($location)) {
                $location = NULL;
            }

            if (isset($_POST["type"]) && !empty($_POST["type"])) {
                $type = $_POST["type"];
            }

            if (empty($type)) {
                $type = NULL;
            }

            if (isset($_POST["cond"]) && !empty($_POST["cond"])) {
                $cond = $_POST["cond"];
            }

            if (empty($cond)) {
                $cond = NULL;
            }

            if (isset($_POST["name"]) && !empty($_POST["name"])) {
                $name = $_POST["name"];
            }

            if (empty($name)) {
                $name = NULL;
            }

            if (isset($_POST["description"]) && !empty($_POST["description"])) {
                $description = $_POST["description"];
            }

            if (empty($description)) {
                $description = NULL;
            }

            $pdostmt = "UPDATE sourcereports "
                . "SET "
                . "id = :id, "
                . "reportdt = :reportdt, "
                . "location = :location, "
                . "type = :type, "
                . "cond = :cond, "
                . "name = :name, "
                . "description = :description "
                . "WHERE "
                . "reportid = :reportid";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":id", $id);
            $stmt->bindParam(":reportdt", $reportdt);
            $stmt->bindParam(":location", $location);
            $stmt->bindParam(":type", $type);
            $stmt->bindParam(":cond", $cond);
            $stmt->bindParam(":name", $name);
            $stmt->bindParam(":description", $description);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();

            echo "status=success\r\n";
            echo "message=source report updated\r\n";
        } catch (PDOException $e) {
            http_response_code(500);
            exit;
        }
    } else if ($action == "DELETE") {
        if (!(isset($_POST["reportid"]))) {
            http_response_code(400);
            exit;
        }

        $reportid = $_POST["reportid"];
        if (empty($reportid)) {
            http_parse_params(400);
            exit;
        }

        if (!isAuthenticated($email, $tok)) {
            http_response_code(401);
            exit;
        }

        try {
            $pdostmt = "DELETE FROM sourcereports WHERE reportid = :reportid";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();

            echo "status=success\r\n";
            echo "message=source report deleted\r\n";
            echo "reportid=" . $reportid . "\r\n";
        } catch (PDOException $e) {
            http_response_code(500);
            exit;
        }
    } else {
        http_response_code(400);
        exit;
    }
} else if ($reporttype == "purity") {
    if ($action == "GET") {
        if (isset($_POST["reportid"]) && !empty($_POST["reportid"])) {
            try {
                $reportid = $_POST["reportid"];
                if ($reportid == "ALL") {
                    $pdostmt = "SELECT * FROM purityreports";
                    $stmt = $dbcon->prepare($pdostmt);
                    $pdocntstmt = "SELECT count(*) FROM purityreports";
                    $cntstmt = $dbcon->prepare($pdocntstmt);
                } else {
                    $pdostmt = "SELECT * FROM purityreports WHERE reportid = :reportid";
                    $stmt = $dbcon->prepare($pdostmt);
                    $stmt->bindParam(":reportid", $reportid);
                    $pdocntstmt = "SELECT count(*) FROM purityreports WHERE reportid = :reportid";
                    $cntstmt = $dbcon->prepare($pdocntstmt);
                    $cntstmt->bindParam(":reportid", $reportid);
                }
                $stmt->execute();
                $cntstmt->execute();
                $count = $cntstmt->fetchColumn();

                echo "status=success\r\n";
                echo "message=fetched report\r\n";
                echo "variablepayload=" . $count . "\r\n";
                echo "--- BEGIN ---\r\n";
                $resultnum = 1;
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC, PDO::FETCH_ORI_NEXT)) {
                    $id = $row["userid"];
                    $pdounstmt = "SELECT * FROM users WHERE id = :id";
                    $unstmt = $dbcon->prepare($pdounstmt);
                    $unstmt->bindParam(":id", $id);
                    $unstmt->execute();
                    $unrow = $unstmt->fetch(PDO::FETCH_ASSOC);
                    $username = $unrow["username"];

                    echo "" . $resultnum . ":";
                    echo "username=" . $username . "|";
                    echo "userid=" . $id . "|";
                    echo "sourceid=" . $row["sourcerptid"] . "|";
                    echo "reportid=" . $row["reportid"] . "|";
                    echo "reportdt=" . $row["reportdt"] . "|";
                    echo "location=" . $row["location"] . "|";
                    echo "cond=" . $row["cond"] . "|";
                    echo "virusppm=" . $row["virusppm"] . "|";
                    echo "contaminantppm=" . $row["contaminantppm"];
                    echo "\r\n";
                    $resultnum++;
                }
                echo "--- END ---\r\n";
            } catch (PDOException $e) {
                http_response_code(500);
                exit;
            }
        } else {
            // search indexing
        }
    } else if ($action == "ADD") {
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
            $username = $row["username"];

            $reportid = uniqid("", TRUE);
            $pdostmt = "INSERT INTO purityreports "
                . "(userid, reportid) "
                . "VALUES "
                . "(:userid, :reportid)";
            $stmt =  $dbcon->prepare($pdostmt);
            $stmt->bindParam(":userid", $id);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();

            echo "status=success\r\n";
            echo "message=void purity report created\r\n";
            echo "username=" . $username . "\r\n";
            echo "reportid=" . $reportid . "\r\n";
        } catch (PDOException $e) {
            //http_response_code(500);
            echo $e;
            exit;
        }
    } else if ($action == "UPDATE") {
        if (!(isset($_POST["reportid"]))) {
            http_response_code(400);
            exit;
        }

        $reportid = $_POST["reportid"];
        if (empty($reportid)) {
            http_parse_params(400);
            exit;
        }

        if (!isAuthenticated($email, $tok)) {
            http_response_code(401);
            exit;
        }

        try {
            $pdostmt = "SELECT * FROM purityreports WHERE reportid = :reportid";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();
            $row = $stmt->fetch(PDO::FETCH_ASSOC);

            $userid = $row["userid"];
            $sourceid = $row["sourcerptid"];
            $reportdt = $row["reportdt"];
            $location = $row["location"];
            $cond = $row["cond"];
            $virusppm = $row["virusppm"];
            $contaminantppm = $row["contaminantppm"];

            if (isset($_POST["userid"]) && !empty($_POST["userid"])) {
                $userid = $_POST["userid"];
            }

            if (empty($userid)) {
                $userid = NULL;
            }

            if (isset($_POST["sourcerptid"]) && !empty($_POST["sourcerptid"])) {
                $sourcerptid = $_POST["sourcerptid"];
            }

            if (empty($sourcerptid)) {
                $sourcerptid = NULL;
            }

            if (isset($_POST["reportdt"]) && !empty($_POST["reportdt"])) {
                $reportdt = $_POST["reportdt"];
            }

            if (empty($reportdt)) {
                $reportdt = NULL;
            }

            if (isset($_POST["location"]) && !empty($_POST["location"])) {
                $location = $_POST["location"];
            }

            if (empty($location)) {
                $location = NULL;
            }

            if (isset($_POST["cond"]) && !empty($_POST["cond"])) {
                $cond = $_POST["cond"];
            }

            if (empty($cond)) {
                $cond = NULL;
            }

            if (isset($_POST["virusppm"]) && !empty($_POST["virusppm"])) {
                $virusppm = $_POST["virusppm"];
            }

            if (empty($virusppm)) {
                $virusppm = NULL;
            }

            if (isset($_POST["contaminantppm"]) && !empty($_POST["contaminantppm"])) {
                $contaminantppm = $_POST["contaminantppm"];
            }

            if (empty($contaminantppm)) {
                $contaminantppm = NULL;
            }

            $pdostmt = "UPDATE purityreports "
                . "SET "
                . "userid = :userid, "
                . "sourcerptid = :sourcerptid, "
                . "reportdt = :reportdt, "
                . "location = :location, "
                . "cond = :cond, "
                . "virusppm = :virusppm, "
                . "contaminantppm = :contaminantppm "
                . "WHERE "
                . "reportid = :reportid";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":userid", $userid);
            $stmt->bindParam(":sourcerptid", $sourcerptid);
            $stmt->bindParam(":reportdt", $reportdt);
            $stmt->bindParam(":location", $location);
            $stmt->bindParam(":cond", $cond);
            $stmt->bindParam(":virusppm", $virusppm);
            $stmt->bindParam(":contaminantppm", $contaminantppm);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();

            echo "status=success\r\n";
            echo "message=source report updated\r\n";
        } catch (PDOException $e) {
            http_response_code(500);
            exit;
        }
    } else if ($action == "DELETE") {
        if (!(isset($_POST["reportid"]))) {
            http_response_code(400);
            exit;
        }

        $reportid = $_POST["reportid"];
        if (empty($reportid)) {
            http_parse_params(400);
            exit;
        }

        if (!isAuthenticated($email, $tok)) {
            http_response_code(401);
            exit;
        }

        try {
            $pdostmt = "DELETE FROM purityreports WHERE reportid = :reportid";
            $stmt = $dbcon->prepare($pdostmt);
            $stmt->bindParam(":reportid", $reportid);
            $stmt->execute();

            echo "status=success\r\n";
            echo "message=source report deleted\r\n";
            echo "reportid=" . $reportid . "\r\n";
        } catch (PDOException $e) {
            http_response_code(500);
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