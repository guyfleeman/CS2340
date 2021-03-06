/**
 * Created by willstuckey on 11/27/16.
 */

function login_clearNotifications() {
    $('#email_input_feedback').text('');
    if ($('#email_div').hasClass('has-warning')) {
        $('#email_div').removeClass('has-warning');
    }
    $('#passwd_input_feedback').text('');
    if ($('#passwd_div').hasClass('has-warning')) {
        $('#passwd_div').removeClass('has-warning');
    }
    $('#input_feedback').text('');
}

function login_init() {
    login_clearNotifications();

    if (!(getCookie('email') == null
        || getCookie('sessionid') == null
        || getCookie('type') == null
        || getCookie('username') == null)) {
        window.location.href = "view_source.php";
    }

    $('#login_button').click(function () {
        login_clearNotifications();

        var validated = true;
        var email = $('#email_input').val();
        var passwd = $('#passwd_input').val();
        $('#passwd_input').val('');

        if (!email.trim()) {
            validated = false;
            $('#email_input_feedback').text('Email cannot be blank.');
            $('#email_div').addClass('has-warning');
        } else {
            var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if (!regex.test(email)) {
                validated = false;
                $('#email_input_feedback').text('Not a valid email.');
                $('#email_div').addClass('has-warning');
            }
        }

        if (!passwd) {
            validated = false;
            $('#passwd_input_feedback').text("Password cannot be blank.");
            $('#passwd_div').addClass('has-warning');
        }

        if (validated) {
            var enc_email = encodeURIComponent(email);
            var enc_passwd = encodeURIComponent(passwd);
            var data = "email=" + enc_email + "&password=" + enc_passwd;

            $.ajax({
                type: "POST",
                url: "api/authenticate.php",
                dataType: "text",
                data: data,
                success: function(msg) {
                    var response = parseResponse(msg);
                    if (response['status'] != 'success') {
                        $('#email_div').addClass('has-warning');
                        $('#passwd_div').addClass('has-warning');
                        showWarning("invalid login credentials")
                    } else {
                        setCookie('sessionid', response['sessionid']);
                        setCookie('email', email);
                        setCookie('type', response['type']);
                        setCookie('username', response['username']);

                        nav_setMenuOptions();

                        showSuccess('logged in as ' + response['username']
                            + '<br>taking you to the home page...');
                        setTimeout(function() {
                            window.location.href = "index.php";
                        }, 1500);
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    showError("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
                }
            });
        }
    });
}