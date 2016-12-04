package frontpage.test.backend.user.LocalUserManager;

import frontpage.backend.user.LocalUserManager;
import frontpage.model.user.UserClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author George Tang
 * date 11/14/2016
 *
 * Tests createUser in LocalUserManager
 */
public class createUserTest {

    /**
     * Tests to see if a valid user is created properly
     *
     */
    @Test
    public void testCreateValidUser() {
        LocalUserManager ua = new LocalUserManager();

        try {
            ua.createUser("gburdell1", "buzz", "gburdell1@gatech.edu", "George", "Burdell", "MANAGER");
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1, ua.getUsers().size());
        Assert.assertEquals("gburdell1", ua.getUsers().get(0).getUsername());
        Assert.assertEquals("gburdell1@gatech.edu", ua.getUsers().get(0).getEmail());
        Assert.assertEquals(UserClass.MANAGER, ua.getUsers().get(0).getUserClass());
    }

    /**
     * Tests to see if a user with invalid info is allowed to be made
     *
     */
    @Test
    public void testInvalidInput() {
        LocalUserManager ua = new LocalUserManager();
        try {
            ua.createUser("gburdell1", "buzz", "gburdell1@gatech.edu", "George", "Burdell", "SUPERHUMAN");
        } catch (Exception e) {
            Assert.assertEquals(0, ua.getUsers().size());
            return;
        }
        Assert.fail();
    }

    /**
     * Tests to see if a user whose email is already registered can be made again
     *
     */
    @Test
    public void testSameUser()  {
        LocalUserManager ua = new LocalUserManager();
        try {
            ua.createUser("gburdell1", "buzz", "gburdell1@gatech.edu", "George", "Burdell", "MANAGER");
            ua.createUser("gburdell2", "ramblin", "gburdell1@gatech.edu", "G", "B", "USER");
        } catch (Exception e) {
            Assert.assertEquals(1, ua.getUsers().size());
            return;
        }
        Assert.fail();
    }
}
