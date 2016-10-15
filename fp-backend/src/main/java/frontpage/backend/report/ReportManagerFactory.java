package frontpage.backend.report;

import frontpage.bind.report.ReportManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * @date 10/14/16
 * <p></p>
 */
public class ReportManagerFactory {
    private static Logger logger;
    private static Map<String,
            Class<? extends ReportManager>> reportManagerMap;
    private static ReportManager instance;

    static {
        logger = Logger.getLogger(ReportManagerFactory.class.getName());
        logger.setLevel(Level.ALL);
        reportManagerMap = new HashMap<>();
        reportManagerMap.put("remote", RemoteReportManager.class);
        reportManagerMap.put("local", LocalReportManager.class);
    }

    public static void createInstance(final String type) {
        Class<? extends ReportManager> instClass = reportManagerMap.get(type);
        try {
            instance = instClass.newInstance();
        } catch (Exception e) {
            logger.error("failed to create report manager instance for backend", e);
        }
    }

    public static ReportManager getInstance() {
        return instance;
    }
}
