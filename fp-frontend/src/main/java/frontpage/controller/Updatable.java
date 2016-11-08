package frontpage.controller;

/**
 * @author willstuckey
 * <p>Updateable interface used as a call to prepare a
 * scene prior to setting it as active.</p>
 */
public interface Updatable {
    /**
     * updates an object
     * @return success
     */
    boolean update();
}
