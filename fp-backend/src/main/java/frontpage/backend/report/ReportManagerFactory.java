package frontpage.backend.report;

import frontpage.bind.report.PurityReportManager;
import frontpage.bind.report.SourceReportManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p>Creates report managers on the backend.</p>
 */
public final class ReportManagerFactory {
    /**
     * class logger
     */
    private static final Logger logger;

    /**
     * map of type keys to backend classes for source reports
     */
    private static final Map<String,
            Class<? extends SourceReportManager>> sourceReportManagerMap;

    /**
     * map of type keys to backend classes for purity reports
     */
    private static final Map<String,
            Class<? extends PurityReportManager>> purityReportManagerMap;

    /**
     * instance of backend for source management
     */
    private static SourceReportManager sourceInstance;

    /**
     * instnace of backend for purity management
     */
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

    /**
     * utility constructor
     */
    private ReportManagerFactory() { }

    /**
     * creates an instance of a backend manager for each type of report given
     * a type key
     * @param type key
     */
    @SuppressWarnings("SameParameterValue")
    public static void createInstance(final String type) {
        Class<? extends SourceReportManager> sourceClass =
                sourceReportManagerMap.get(type);
        Class<? extends PurityReportManager> purityClass =
                purityReportManagerMap.get(type);
        try {
            sourceInstance = sourceClass.newInstance();
            purityInstance = purityClass.newInstance();
        } catch (Exception e) {
            logger.error("failed to create report manager "
                    + "instance for backend", e);
        }
    }

    /**
     * gets the backend instance for source report management
     * @return instance
     */
    public static SourceReportManager getSourceInstance() {
        return sourceInstance;
    }

    /**
     * gets the backend instance for purity report management
     * @return instnace
     */
    public static PurityReportManager getPurityInstance() {
        return purityInstance;
    }
}
