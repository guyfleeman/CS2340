/**
 * collection of functions to manage cookies
 *
 * @author Will Stuckey
 */

/**
 * creates a cookie
 * @param cname cookie name
 * @param cvalue cookie value
 * @param expire_hours number of hours till expiry
 */
function setCookie(cname, cvalue, expire_hours) {
    var dt = new Date();
    dt.setTime(dt.getTime() + (expire_hours * 60 * 60 * 1000));
    var expires = ("expires=" + dt.toUTCString());
    document.cookie = (cname + "=" + cvalue + ";" + expires + ";path=/");
}

/**
 * deletes a cookie
 * @param cname cookie name
 */
function deleteCookie(name) {
    document.cookie = (name + '=; Path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;');
}

/**
 * gets a cookie
 * @param name cookie name
 * @returns {*} cookie, null if not found
 */
function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1, c.length);
        }

        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length, c.length);
        }
    }

    return null;
}

var cookie_redirect_message = '';
var cookie_redirect_page = '';

/**
 *
 */
function conditionalRedirect() {
    if (getCookie('sessionid') == null
        || getCookie('email') == null
        || getCookie('type') == null
        || getCookie('username') == null) {
        cookie_redirect_message = 'You must be logged in to view this page.';
        cookie_redirect_page = window.location.href;
        window.location.href = 'login.php';
        return true;
    }
}

function requireWorker() {
    if (getCookie('type') == 'USER') {
        showError('You do not have permission to view this page.');
        return false;
    }

    return true;
}

function requireManager() {
    if (getCookie('type') == 'USER' || getCookie('type') == 'WORKER') {
        showError('You do not have permission to view this page.');
        return false;
    }

    return true;
}

