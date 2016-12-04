/**
 * Created by willstuckey on 12/3/16.
 */

/**
 *
 */
function createAccount_clearNotifications() {
    $('#email_input_feedback').text('');
    if ($('#email_div').hasClass('has-warning')) {
        $('#email_div').removeClass('has-warning');
    }

    $('#username_input_feedback').text('');
    if ($('#username_div').hasClass('has-warning')) {
        $('#username_div').removeClass('has-warning');
    }

    $('#passwd_input_feedback').text('');
    if ($('#passwd_div').hasClass('has-warning')) {
        $('#passwd_div').removeClass('has-warning');
    }

    $('#cnf_passwd_input_feedback').text('');
    if ($('#cnf_passwd_div').hasClass('has-warning')) {
        $('#cnf_passwd_div').removeClass('has-warning');
    }


}

/**
 *
 */
function createAccount_init() {
    createAccount_clearNotifications();

    $('#create_button').click(function () {
        createAccount_clearNotifications();

        var validated = true;
        var email = $('#email_input').val();
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

        var username = $('#username_input').val();
        if (!username) {
            validated = false;
            $('#username_div').addClass('has-warning');
            $('#username_input_feedback').text('Username cannot be blank.');
        }

        var passwd = $('#passwd_input').val();
        if (!passwd) {
            validated = false;
            $('#passwd_div').addClass('has-warning');
            $('#passwd_input_feedback').text('Password cannot be blank.');
        }

        var cnf_passwd = $('#cnf_passwd_input').val();
        if (!cnf_passwd) {
            validated = false;
            $('#cnf_passwd_div').addClass('has-warning');
            $('#cnf_passwd_input_feedback').text('Password confirmation cannot be blank.');
        } else if (cnf_passwd != passwd) {
            validated = false;
            $('#cnf_passwd_div').addClass('has-warning');
            $('#cnf_passwd_input_feedback').text('Passwords do not match.');
        }

        var type = $('#type_input').val();

        if (validated) {
            var data = '';
            data += 'username=' + encodeURIComponent(username);
            data += '&password=' + encodeURIComponent(passwd);
            data += '&email=' + encodeURIComponent(email);
            data += '&type=' + encodeURIComponent(type);
            data += '&firstname=none';
            data += '&lastname=none';
            $.ajax({
                type: 'POST',
                url: 'api/create_account.php',
                dataType: 'text',
                data: data,
                success: function(msg) {
                    var response = parseResponse(msg);
                    if (response['status'] != 'success') {
                        showError('failed to create account'
                            + '<br>'
                            + '<strong>Reason: </strong>'
                            + response['message']);
                    } else {
                        showSuccess('created user (' + username + ', ' + email + ')'
                            + '<br>Taking you to the login screen...');
                        setTimeout(function() {
                            window.location.href = "login.php";
                        }, 1500);
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    showError("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
                }
            })

        } else {
            showInfo('user not created');
        }
    });
}