package frontpage;

import frontpage.backend.LocalBackend;
import frontpage.backend.RemoteBackend;
import frontpage.bind.Backend;
import frontpage.controller.CreatePurityReportController;
import frontpage.controller.CreateSourceReportController;
import frontpage.controller.LoginScreenController;
import frontpage.controller.MainScreenController;
import frontpage.controller.ProfileScreenController;
import frontpage.controller.PurityGraphController;
import frontpage.controller.RegisterScreenController;
import frontpage.controller.SourceReportMapController;
import frontpage.controller.Updatable;
import frontpage.controller.ViewPurityReportScreenController;
import frontpage.controller.ViewSourceReportScreenController;
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

    private static final int MIN_MAJOR_VERSION = 8;
    private static final int MIN_MINOR_VERSION = 110;

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

        if (Arrays.asList(args).contains("--force-local")) {
            backend = new LocalBackend();
        } else {
            backend = new RemoteBackend();
            try {
                final String jreVersion = System.getProperty("java.version");
                final String major = "" + jreVersion.charAt(2);
                final String minor = jreVersion.split("_")[1];
                if ((Integer.parseInt(major) < MIN_MAJOR_VERSION)
                        || (Integer.parseInt(minor) < MIN_MINOR_VERSION)) {
                    System.err.println("Detected Java version before LetEncrypt certificate added.");
                    boolean res = DialogueUtils.askYesNo("Detected java version \"" + jreVersion + "\"\r\n\r\n"
                            + "This app requires a minimum java version of 1.8.0_110 for SSL certificates to "
                            + "work.\r\n\r\n"
                            + "The problem is usually only on windows, not Linux or Mac. Please upgrade your "
                            + " java version or install the lets encrypt root certificate to your certificate "
                            + "store.\r\n\r\n"
                            + "Continue? (should be okay for Mac/Linux)");
                    if (!res) {
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                DialogueUtils.showMessage("Unable to detect and identify problems with java version.");
            }

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

        ViewSourceReportScreenController.create();
        SceneControllerEntry<ViewSourceReportScreenController> vrsc = new SceneControllerEntry<>(
                new Scene(ViewSourceReportScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                ViewSourceReportScreenController.getViewReportController());
        viewSceneMap.put("viewsourcerpts", vrsc);

        CreateSourceReportController.create();
        SceneControllerEntry<CreateSourceReportController> csrc = new SceneControllerEntry<>(
                new Scene(CreateSourceReportController.getRoot(), RES_WIDTH, RES_HEIGHT),
                CreateSourceReportController.getCreateSourceReportController());
        viewSceneMap.put("createsourcerpt", csrc);

        ViewPurityReportScreenController.create();
        SceneControllerEntry<ViewPurityReportScreenController> vpsc = new SceneControllerEntry<>(
                new Scene(ViewPurityReportScreenController.getRoot(), RES_WIDTH, RES_HEIGHT),
                ViewPurityReportScreenController.getViewReportController());
        viewSceneMap.put("viewpurityrpts", vpsc);

        CreatePurityReportController.create();
        SceneControllerEntry<CreatePurityReportController> cprc = new SceneControllerEntry<>(
                new Scene(CreatePurityReportController.getRoot(), RES_WIDTH, RES_HEIGHT),
                CreatePurityReportController.getPurityReportController()
        );
        viewSceneMap.put("createpurityrpt", cprc);

        SourceReportMapController.create();
        SceneControllerEntry<SourceReportMapController> srpc = new SceneControllerEntry<>(
                new Scene(SourceReportMapController.getRoot(), RES_WIDTH, RES_HEIGHT),
                SourceReportMapController.getSourceReportMapController());
        viewSceneMap.put("map", srpc);

        PurityGraphController.create();
        SceneControllerEntry<PurityGraphController> pgc = new SceneControllerEntry<>(
                new Scene(PurityGraphController.getRoot(), RES_WIDTH, RES_HEIGHT),
                PurityGraphController.getPurityGraphController());
        viewSceneMap.put("puritygraph", pgc);

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
