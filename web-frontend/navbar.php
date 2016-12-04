<nav class="navbar navbar-default navbar-static-top navbar-inverse">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">TFP</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#"
                       class="dropdown-toggle"
                       data-toggle="dropdown"
                       role="button"
                       aria-haspopup="true"
                       aria-expanded="false">Source Report<span class="caret"></span>
                    </a>

                    <ul class="dropdown-menu">
                        <li><a href="create_source.php">Create</a></li>
                        <li><a href="view_source.php">View</a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#"
                       class="dropdown-toggle"
                       data-toggle="dropdown"
                       role="button"
                       aria-haspopup="true"
                       aria-expanded="false">Purity Report<span class="caret"></span>
                    </a>

                    <ul class="dropdown-menu">
                        <li><a href="create_purity.php">Create</a></li>
                        <li><a href="view_purity.php">View</a></li>
                        <li><a href="graph_purity.php">Historical Analysis</a></li>
                    </ul>
                </li>


                <li>
                    <a href="index.php">Map</a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" id="account_header"></a>
                    <ul class="dropdown-menu" id="user_dropdown">
                        <!-- content populated by js -->
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
