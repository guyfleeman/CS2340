package frontpage.test.backend.profile.LocalProfileManager;


import frontpage.backend.profile.LocalProfileManager;
import frontpage.backend.user.LocalUserManager;
import frontpage.bind.errorhandling.AuthenticationException;
import frontpage.bind.errorhandling.InvalidDataException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Austin Bayon on 11/16/2016.
 */
public class getProfileTest {

    /**
     * tests the get profile method
     * @throws InvalidDataException
     * @throws AuthenticationException
     */
    @Test
    public void testGetProfile()
            throws InvalidDataException, AuthenticationException {
        LocalProfileManager pm = new LocalProfileManager();
        LocalUserManager ua = new LocalUserManager();
        ua.createUser("abayon3", "pass", "abayon3@gmail.com",
                "Austin", "Bayon", "USER");
        String tok =  ua.authenticateUser("abayon3", "pass");
        Map<String, String> profiles = new HashMap<String, String>();
        profiles.put("address", "1 Cherry Street");
        profiles.put("city", "Atlanta");
        profiles.put("state", "Georgia");
        profiles.put("zip", "30313");
        profiles.put("title", "test title");
        try {
            pm.setProfile("abayon3@gmail.com", tok, profiles);
        } catch (Exception e) {
            Assert.fail("Failed while setting profile.");
        }
        Map<String, String> actualProfile = new HashMap<String, String>();
        try {
            actualProfile = pm.getProfile("abayon3@gmail.com", tok);
        } catch (Exception e) {
            Assert.fail("No matching user found in database");
        }
        Assert.assertTrue(profiles.equals(actualProfile));
    }

    /**
     * tests that the getProfile method throws an exception when appropriate
     * @throws InvalidDataException
     */
    @Test
    public void testGetProfileException()
        throws InvalidDataException {
        LocalProfileManager pm = new LocalProfileManager();
        try {
            pm.getProfile("abayon3@gmail.com", "fail");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }

    }
}
