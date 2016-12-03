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
    document.cookie = (name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;');
}

/**
 * gets a cookie
 * @param name cookie name
 * @returns {*} cookie, null if not found
 */
function getCookie(name) {
    var dc = document.cookie;
    var prefix = (name + "=");

    var begin = dc.indexOf("; " + prefix);
    if (begin == -1) {
        begin = dc.indexOf(prefix);
        if (begin != 0) {
            return null;
        }
    } else {
        begin += 2;
        var end = document.cookie.indexOf(";", begin);
        if (end == -1) {
            end = dc.length;
        }
    }

    return decodeURI(dc.substring(begin + prefix.length, end));
}