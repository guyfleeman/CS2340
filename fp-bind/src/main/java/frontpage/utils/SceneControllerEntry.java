package frontpage.utils;

import javafx.scene.Scene;

/**
 * @author willstuckey
 * <p>Duple containing a scene and its controller</p>
 *
 * @param <C> controller class
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SceneControllerEntry<C> {
    private Scene scene;
    private C controller;
    private Class<C> controllerClass;

    /**
     * creates a scene controller duple
     * @param scene scene
     * @param controller controller
     */
    public SceneControllerEntry(final Scene scene,
                                final C controller) {
        this(scene, controller, null);
    }

    /**
     * creates a scene controller duple
     * @param scene scene
     * @param controller controller
     * @param controllerClass controller class
     */
    @SuppressWarnings("SameParameterValue")
    public SceneControllerEntry(final Scene scene,
                                final C controller,
                                final Class<C> controllerClass) {
        this.scene = scene;
        this.controller = controller;
        this.controllerClass = controllerClass;
    }

    /**
     * gets the scene
     * @return scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * sets the scene
     * @param scene scene
     */
    public void setScene(final Scene scene) {
        this.scene = scene;
    }

    /**
     * gets the controller
     * @return controller
     */
    public C getController() {
        return controller;
    }

    /**
     * sets the controller
     * @param controller controller
     */
    public void setController(final C controller) {
        this.controller = controller;
    }

    /**
     * gets controller class type
     * @return controller class type
     */
    public Class<C> getControllerClass() {
        return controllerClass;
    }

    /**
     * sets the controller class
     * @param controllerClass controller class
     */
    public void setControllerClass(final Class<C> controllerClass) {
        this.controllerClass = controllerClass;
    }
}
