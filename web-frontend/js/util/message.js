/**
 * Created by willstuckey on 12/3/16.
 */

function showMessage(msg, styleClass) {
    var htmlContent = '';
    htmlContent += '<div class="alert ' + styleClass + ' fade in">';
    if (styleClass == 'alert-danger') {
        htmlContent += '<strong>Error: </strong>'
    } else if (styleClass == 'alert-warning') {
        htmlContent += '<strong>Warning: </strong>'
    } else if (styleClass == 'alert-success') {
        htmlContent += '<strong>Success: </strong>'
    } else if (styleClass == 'alert-info') {
        htmlContent += '<strong>Info: </strong>'
    }
    htmlContent += msg;
    htmlContent += '</div>';
    $('#message_banner').html(htmlContent);
}

function showError(msg) {
    showMessage(msg, 'alert-danger');
}

function showWarning(msg) {
    showMessage(msg, 'alert-warning');
}

function showSuccess(msg) {
    showMessage(msg, 'alert-success');
}

function showInfo(msg) {
    showMessage(msg, 'alert-info');
}
