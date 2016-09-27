package frontpage.backend;

import frontpage.bind.Backend;
import frontpage.bind.auth.UserAuthenticator;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p></p>
 */
public class RemoteBackend implements Backend {
    public UserAuthenticator getUserAuthenticator() {
        return null;
    }
}
