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

    <!-- google charts -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <script src="js/util/message.js"></script>
    <script src="js/util/cookie.js"></script>
    <script src="js/util/remotes.js"></script>
    <script src="js/util/response.js"></script>
    <script src="js/nav.js"></script>
    <script src="js/graph_purity.js"></script>

    <script type="text/javascript">
        conditionalRedirect();

        function pageInit() {
            if (!requireManager()) {
                return;
            }

            nav_setMenuOptions();
            graphPurity_init();
        }

        $(document).ready(function () {
            pageInit();
        });
    </script>

    <style>
        #chart {
            height: 720px;
            width: 100%;
        }
    </style>
</head>

<body>
<?php include 'navbar.php' ?>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10">
            <h1>Historical Purity Data</h1>

            <div class="well">
                <div id="message_banner">

                </div>

                <form class="form-inline text-center">
                    <div class="form-group" id="keywords_div">
                        <label for="keywords_input">Keywords</label>
                        <input type="text" class="form-control" id="keywords_input" placeholder="keywords">
                        <span id="keywords_input_feedback" class="help-block"></span>
                    </div>

                    <div class="form-group">
                        <button type="button" class="btn btn-primary" id="display_button">Display</button>
                        <button type="button" class="btn btn-primary" id="refresh_button">Refresh Data</button>
                        <span id="display_feedback" class="help-block"></span>
                    </div>
                </form>

                <div id="chart">

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>