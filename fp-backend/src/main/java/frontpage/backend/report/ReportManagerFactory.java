package frontpage.backend.report;

import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import frontpage.model.report.PurityReport;
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
            Class<? extends SourceReportManager>> sourceReportManagerMap;
    private static Map<String,
            Class<? extends PurityReportManager>> purityReportManagerMap;
    private static SourceReportManager sourceInstance;
    private static PurityReportManager purityInstance;

    static {
        logger = Logger.getLogger(ReportManagerFactory.class.getName());
        logger.setLevel(Level.ALL);
        sourceReportManagerMap = new HashMap<>();
        purityReportManagerMap = new HashMap<>();
        sourceReportManagerMap.put("remote", RemoteSourceReportManager.class);
        sourceReportManagerMap.put("local", LocalSourceReportManager.class);
        purityReportManagerMap.put("remote", RemotePurityReportManager.class);
        purityReportManagerMap.put("local", LocalPurityReportManager.class);
    }

    public static void createInstance(final String type) {
        Class<? extends SourceReportManager> sourceClass = sourceReportManagerMap.get(type);
        Class<? extends PurityReportManager> purityClass = purityReportManagerMap.get(type);
        try {
            sourceInstance = sourceClass.newInstance();
            purityInstance = purityClass.newInstance();
        } catch (Exception e) {
            logger.error("failed to create report manager instance for backend", e);
        }
    }

    public static SourceReportManager getSourceInstance() {
        return sourceInstance;
    }

    public static PurityReportManager getPurityInstance() {
        return purityInstance;
    }
}
