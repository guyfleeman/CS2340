function nav_setMenuOptions() {
    var email_cookie = getCookie('email');
    var tok_cookie = getCookie('sessionid');
    if (email_cookie == null || tok_cookie == null) {

        // no login cookie, load not logged in dropdown

        $( '#user_dropdown' ).append( '<li><a href="https://www.willstuckey.com/staging/water/login.php">Login</a></li>' );
        $( '#user_dropdown' ).append( '<li><a href="#">Create Account</a></li>' );
    } else {

        // cookies present, logged in

        $( '#user_dropdown' ).append( '<li><a href="#">Profile</a></li>' );
        $( '#user_dropdown' ).append( '<li role="separator" class="divider"></li>' );
        $( '#user_dropdown' ).append( '<li><a href="#">Logout</a></li>' );
    }
}