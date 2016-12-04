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
    <script src="js/util/remotes.js"></script>
    <script src="js/util/cookie.js"></script>
    <script src="js/util/response.js"></script>
    <script src="js/nav.js"></script>
    <script src="js/view_source.js"></script>

    <script type="text/javascript">
        conditionalRedirect();

        function pageInit() {
            nav_setMenuOptions();
            viewSource_init();
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
        <div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10">
            <h1>Source Reports</h1>

            <div id="message_banner">

            </div>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Datetime</th>
                            <th>Report Number</th>
                            <th>Reporter</th>
                            <th>Location</th>
                            <th>Type</th>
                            <th>Condition</th>
                        </tr>
                    </thead>
                    <tbody id="report_table">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>