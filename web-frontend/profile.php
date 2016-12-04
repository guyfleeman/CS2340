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

    <script src="js/util/message.js"></script>
    <script src="js/util/cookie.js"></script>
    <script src="js/util/remotes.js"></script>
    <script src="js/util/response.js"></script>
    <script src="js/nav.js"></script>
    <script src="js/profile.js"></script>

    <script type="text/javascript">
        conditionalRedirect();

        function pageInit() {
            nav_setMenuOptions();
            profile_init();
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
            <h1>User Profile</h1>

            <div id="message_banner">

            </div>

            <form>
                <div class="form-group" id="address_div">
                    <label for="address_input">Address</label>
                    <input type="text" class="form-control" id="address_input">
                    <span id="address_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="city_div">
                    <label for="city_input">City</label>
                    <input type="text" class="form-control" id="city_input">
                    <span id="city_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="state_div">
                    <label for="state_input">State</label>
                    <input type="text" class="form-control" id="state_input">
                    <span id="state_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="zip_div">
                    <label for="zip_input">Zip</label>
                    <input type="text" class="form-control" id="zip_input">
                    <span id="zip_input" class="help-block"></span>
                </div>

                <div class="form-group" id="title_div">
                    <label for="title_input">Title</label>
                    <input type="text" class="form-control" id="title_input">
                    <span id="title_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group">
                    <button type="button" class="btn btn-primary" id="update_button">Update Profile</button>
                    <button type="button" class="btn btn-primary" id="refresh_button">Refresh Profile</button>
                    <span id="input_feedback" class="help-block"></span>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>