<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">

    <!-- Optional theme -->
    <!--<link rel="stylesheet" -->
    <!--href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" -->
    <!--integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" -->
    <!--crossorigin="anonymous">-->

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <script src="js/nav.js"></script>
    <script src="js/util/cookie.js"></script>

    <script type="text/javascript">
        function pageInit() {
            nav_setMenuOptions()
        }

        $(document).ready(function () {
            pageInit();
        });
    </script>
</head>

<body>
<?php include 'navbar.php' ?>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
            <form>
                <div class="form-group">
                    <label for="email_input">Email</label>
                    <input type="email" class="form-control" id="email_input" placeholder="email">
                </div>

                <div class="form-group">
                    <label for="username_input">Username</label>
                    <input type="text" class="form-control" id="username_input" placeholder="email">
                </div>

                <div class="form-group">
                    <label for="passwd_input">Password</label>
                    <input type="password" class="form-control" id="passwd_input" placeholder="password">
                </div>

                <div class="form-group">
                    <label for="cnf_passwd_input">Confirm Password</label>
                    <input type="password" class="form-control" id="cnf_passwd_input" placeholder="password">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>