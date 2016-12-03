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
    <script src="js/util/time.js"></script>
    <script src="js/nav.js"></script>
    <script src="js/create_purity.js"></script>

    <script type="text/javascript">
        conditionalRedirect();

        function pageInit() {
            if (!requireWorker()) {
                return;
            }

            nav_setMenuOptions();
            createPurity_init();
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
            <div id="message_banner">

            </div>

            <form>
                <div class="form-group" id="reportid_div">
                    <label for="reportid_input">Report ID</label>
                    <input type="text" class="form-control" id="reportid_input" disabled>
                    <span id="reportid_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="submitter_div">
                    <label for="submitter_input">Submitter</label>
                    <input type="text" class="form-control" id="submitter_input" disabled>
                    <span id="submitter_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="date_div">
                    <label for="date_input">Date</label>
                    <input type="text" class="form-control" id="date_input" disabled>
                    <span id="date_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="location_div">
                    <label for="location_input">Location</label>
                    <textarea class="form-control" id="location_input"></textarea>
                    <span id="location_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="putiy_div">
                    <label for="purity_input">Purity</label>
                    <select class="form-control" id="purity_input">
                        <option>SAFE</option>
                        <option>TREATABLE</option>
                        <option>UNSAFE</option>
                    </select>
                </div>

                <div class="form-group" id="virusppm_div">
                    <label for="virusppm_input">Virus PPM</label>
                    <input type="text" class="form-control" id="virusppm_input">
                    <span id="virusppm_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group" id="contaminantppm_div">
                    <label for="contaminantppm_input">Contaminant PPM</label>
                    <input type="text" class="form-control" id="contaminantppm_input">
                    <span id="contaminantppm_input_feedback" class="help-block"></span>
                </div>

                <div class="form-group">
                    <button type="button" class="btn btn-primary" id="create_button">Create Purity Report</button>
                    <span id="input_feedback" class="help-block"></span>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>