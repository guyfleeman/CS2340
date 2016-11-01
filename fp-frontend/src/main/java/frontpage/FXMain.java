package frontpage;

import frontpage.backend.LocalBackend;
import frontpage.backend.RemoteBackend;
import frontpage.bind.Backend;
import frontpage.controller.CreateSourceReportController;
import frontpage.controller.LoginScreenController;
import frontpage.controller.MainScreenController;
import frontpage.controller.ProfileScreenController;
import frontpage.controller.RegisterScreenController;
import frontpage.controller.SourceReportMapController;
import frontpage.controller.Updatable;
import frontpage.controller.ViewPurityReportScreenController;
import frontpage.controller.WelcomeScreenController;
import frontpage.model.user.User;
import frontpage.utils.DialogueUtils;
import frontpage.utils.SceneControllerEntry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Application entry point.
 *
 * @author willstuckey
 */
public class FXMain extends Application {

    private static final int RES_WIDTH = 640;
    private static final int RES_HEIGHT = 480;
    // logger format
    private static final String LOG_FORMAT = "%d{ISO8601} [%t] %-5p %c %x - %m%n";
    private static final Map<String, SceneControllerEntry> viewSceneMap = new HashMap<>();

    private static Stage stage;
    private static Backend backend;
    private static User user;

    public static void main(final String[] args) {
        ConsoleAppender console = new ConsoleAppender();
        console.setLayout(new PatternLayout(LOG_FORMAT));
        console.setThreshold(Level.ALL);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);

        if (Arrays.asList(args).contains("--force-local")
                || !DialogueUtils.askYesNo("Use remote backend?")) {
            backend = new LocalBackend();
        } else {
            backend = new RemoteBackend();
//            try {
//                backend.getUserManager().createUser("guyfleeman", "password".toCharArray(), "guyfleeman@gmail.net", "will", "stuckey");
//                String res = backend.getUserManager().authenticateUser("guyfleeman@gmail.net", "password");
//                res = backend.getUserManager().authenticateUser("guyfleeman@gmail.net", res);
//                backend.getUserManager().authenticateUser("guyfleeman@gmail.net", "password");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        User.setPm(backend.getProfileManager());

        launch(args);
    }

    public static Backend getBackend() {
        return backend;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FXMain.user = user;
    }

    /**
     * FX entry point
     *
     * @param primaryStage default stage
     */
    public final void start(final Stage primaryStage) {
        FXMain.stage = primaryStage;


        RegisterScreenController.create();
        SceneControllerEntry<RegisterScreenController> rse = new SceneControllerEntry<>(
                new Scene(RegisterScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                RegisterScreenController.getRegisterController());
        viewSceneMap.put("register", rse);

        LoginScreenController.create();
        SceneControllerEntry<LoginScreenController> lse = new SceneControllerEntry<>(
                new Scene(LoginScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                LoginScreenController.getLoginController());
        viewSceneMap.put("login", lse);

        WelcomeScreenController.create();
        SceneControllerEntry<WelcomeScreenController> wse = new SceneControllerEntry<>(
                new Scene(WelcomeScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                WelcomeScreenController.getWelcomeController());
        viewSceneMap.put("welcome", wse);

        MainScreenController.create();
        SceneControllerEntry<MainScreenController> mse = new SceneControllerEntry<>(
                new Scene(MainScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                MainScreenController.getMainController());
        viewSceneMap.put("main", mse);

        ProfileScreenController.create();
        SceneControllerEntry<ProfileScreenController> psc = new SceneControllerEntry<>(
                new Scene(ProfileScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                ProfileScreenController.getRegisterController()
        );
        viewSceneMap.put("profile", psc);

        ViewPurityReportScreenController.create();
        SceneControllerEntry<ViewPurityReportScreenController> vrsc = new SceneControllerEntry<>(
                new Scene(ViewPurityReportScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                ViewPurityReportScreenController.getViewReportController());
        viewSceneMap.put("viewsourcerpts", vrsc);

        CreateSourceReportController.create();
        SceneControllerEntry<CreateSourceReportController> csrc = new SceneControllerEntry<>(
                new Scene(CreateSourceReportController.getRoot(), RES_WIDTH, RES_HEIGHT),
                CreateSourceReportController.getCreateSourceReportController());
        viewSceneMap.put("createsourcerpt", csrc);

        CreatePurityReportController.create();
        SceneControllerEntry<CreatePurityReportController> swpr = new SceneControllerEntry<>(
                new Scene(CreatePurityReportController.getRoot(), RES_WIDTH, RES_HEIGHT),
                CreatePurityReportController.getPurityReportController()
        );
        viewSceneMap.put("createpurityrpt", swpr);

        SourceReportMapController.create();
        SceneControllerEntry<SourceReportMapController> srpc = new SceneControllerEntry<>(
                new Scene(SourceReportMapController.getRoot(), RES_WIDTH, RES_HEIGHT),
                SourceReportMapController.getSourceReportMapController());
        viewSceneMap.put("map", srpc);

        setView("welcome");
        primaryStage.show();

    }

    public static boolean setView(final String view) {
        if (view == null) {
            return false;
        }

        Scene s = viewSceneMap.get(view.toLowerCase()).getScene();
        if (s == null) {
            return false;
        }

        Object con = viewSceneMap.get(view.toLowerCase()).getController();
        if (con instanceof Updatable) {
             if (!((Updatable) con).update()) {
                 return false;
             }
        }
        stage.setScene(s);
        return true;
    }

    public static Object getController(final String view) {
        return viewSceneMap.get(view.toLowerCase()).getController();
    }
}
