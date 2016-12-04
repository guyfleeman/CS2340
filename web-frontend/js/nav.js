/**
 *
 */
function nav_setMenuOptions() {
    $( '#user_dropdown' ).empty();

    var email_cookie = getCookie('email');
    var tok_cookie = getCookie('sessionid');
    if (email_cookie == null || tok_cookie == null) {

        // no login cookie, load not logged in dropdown

        $( '#account_header' ).html('Account <span class="caret"></span>');

        $( '#user_dropdown' ).append( '<li><a href="login.php">Login</a></li>' );
        $( '#user_dropdown' ).append( '<li><a href="create_account.php">Create Account</a></li>' );
    } else {

        // cookies present, logged in

        $( '#account_header' ).html('Account (' + getCookie('username') + ') <span class="caret"></span>');

        $( '#user_dropdown' ).append( '<li><a href="profile.php">Profile</a></li>' );
        $( '#user_dropdown' ).append( '<li role="separator" class="divider"></li>' );
        $( '#user_dropdown' ).append( '<li><a href="logout.php">Logout</a></li>' );
    }
}