/**
 * Created by willstuckey on 12/3/16.
 */

/**
 *
 * @param notify
 * @param callback
 */
function loadFields(notify, callback) {
    if (!($('#update_button').hasClass('disabled'))) {
        $('#update_button').addClass('disabled');
    }

    $('#address_input').val('');
    $('#city_input').val('');
    $('#state_input').val('');
    $('#zip_input').val('');
    $('#title_input').val('');

    var data = '';
    data += 'email=' + encodeURIComponent(getCookie('email'));
    data += '&tok=' + encodeURIComponent(getCookie('sessionid'));
    data += '&action=GET';
    $.ajax({
        type: "POST",
        url: 'api/profile.php',
        dataType: 'text',
        data: data,
        success: function(msg) {
            var response = parseResponse(msg);
            if (response['status'] == 'success') {
                $('#address_input').val(response['address']);
                $('#city_input').val(response['city']);
                $('#state_input').val(response['state']);
                $('#zip_input').val(response['zip']);
                $('#title_input').val(response['title']);

                if ($('#update_button').hasClass('disabled')) {
                    $('#update_button').removeClass('disabled');
                }

                if (notify) {
                    showSuccess('fetched profile');
                }

                if (callback != null) {
                    callback();
                }
            } else {
                showError('server refused request to fetch profile');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            showError("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
        }
    });
}

/**
 *
 */
function sendFields() {
    var address = $('#address_input').val();
    var city = $('#city_input').val();
    var state = $('#state_input').val();
    var zip = $('#zip_input').val();
    var title = $('#title_input').val();

    var data = '';
    data += 'email=' + encodeURIComponent(getCookie('email'));
    data += '&tok=' + encodeURIComponent(getCookie('sessionid'));
    data += '&action=PUT';
    data += '&address=' + encodeURIComponent(address);
    data += '&city=' + encodeURIComponent(city);
    data += '&state=' + encodeURIComponent(state);
    data += '&zip=' + encodeURIComponent(zip);
    data += '&title=' + encodeURIComponent(title);
    $.ajax({
        type: "POST",
        url: 'api/profile.php',
        dataType: "text",
        data: data,
        success: function(msg) {
            var response = parseResponse(msg);
            if (response['status'] == 'success') {
                loadFields(false, null);
                showSuccess("updated profile");
            } else {
                showError('server refused request to send profile');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            showError("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
        }
    });
}

/**
 *
 */
function profile_init() {
    loadFields(true, null);

    $('#refresh_button').click(function () {
        loadFields(true, null);
    });

    $('#update_button').click(function () {
        sendFields();
    });
}