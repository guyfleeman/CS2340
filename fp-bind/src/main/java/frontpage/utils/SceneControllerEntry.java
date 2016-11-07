package frontpage.utils;

import javafx.scene.Scene;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public class SceneControllerEntry<C> {
    private Scene scene;
    private C controller;
    private Class<C> controllerClass;

    public SceneControllerEntry(Scene scene, C controller) {
        this(scene, controller, null);
    }

    @SuppressWarnings("SameParameterValue")
    public SceneControllerEntry(Scene scene, C controller, Class<C> controllerClass) {
        this.scene = scene;
        this.controller = controller;
        this.controllerClass = controllerClass;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public C getController() {
        return controller;
    }

    public void setController(C controller) {
        this.controller = controller;
    }

    public Class<C> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<C> controllerClass) {
        this.controllerClass = controllerClass;
    }
}
