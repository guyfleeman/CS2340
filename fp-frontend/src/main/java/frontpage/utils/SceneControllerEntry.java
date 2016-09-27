package frontpage.utils;

import javafx.scene.Scene;

/**
 * @author willstuckey
 * @date 9/27/16
 * <p></p>
 */
public class SceneControllerEntry<C> {
    private Scene scene;
    private C controller;
    private Class<C> controllerClass;

    public SceneControllerEntry(Scene scene, C controller) {
        this(scene, controller, null);
    }

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
}
