package frontpage.test.backend.user.LocalUserManager;


import frontpage.backend.user.LocalUserManager;
import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.errorhandling.InvalidDataException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Devan Williams
 * date 11/12/2016
 *
 * Tests authenticateUser in LocalUserManager
 */
public class authenticateUserTest  {


    /**
     * Tests to find Valid User
     *
     * @throws InvalidDataException user already registered
     */

    @Test
    public void testFindCorrectUser() throws InvalidDataException {
        LocalUserManager ua = new LocalUserManager();
        ua.createUser("dww2250", "snick", "devan.williams@gmail.com",
                "Devan", "Williams", "USER");
        try {
            ua.authenticateUser("devan.williams@gmail.com", "snick");
        } catch (Exception e) {
            Assert.fail();

        }
    }

    /**
     * Tests to find user with correct email and different passwords.
     *
     * @throws AuthenticationException user does not exist
     * @throws InvalidDataException user already registered
     */

    @Test (expected = AuthenticationException.class)
    public void testDifferentPwd() throws AuthenticationException,
            InvalidDataException {
        LocalUserManager ua = new LocalUserManager();
        ua.createUser("dww2250", "snick",
                "DEVAN.WILLIAMS@GMAIL.COM", "Devan", "Williams", "USER");
        ua.authenticateUser("DEVAN.WILLIAMS@GMAIL.COM", "snickers");

        }

    /**
     * Tests two different emails
     * Test finds bug in code
     *
     * @throws AuthenticationException user does not exist
     * @throws InvalidDataException user already registered
     */
    @Test
    public void testDifferentEmail() throws AuthenticationException,
            InvalidDataException {
        LocalUserManager ua = new LocalUserManager();
        ua.createUser("dww2250", "snick",
                "DEVAN.WILLIAMS@GMAIL.COM", "Devan", "Williams", "USER");

        ua.authenticateUser("devan.williams@gmail.com", "snick");



    }
    /**
     * Tests to find no User in LocalUserManager
     *
     * @throws AuthenticationException user does not exist
     */
    @Test (expected = AuthenticationException.class)
    public void testNoUserInManager() throws AuthenticationException {
        LocalUserManager ua = new LocalUserManager();
        ua.authenticateUser("devan.williams@gmail.com", "snick");

    }


}













