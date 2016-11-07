package frontpage.bind;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author willstuckey
 * <p>This class contains the global properties for the application.</p>
 */
public final class GlobalProperties {
    /**
     * internal logger
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static final Logger logger;

    /**
     * global properties
     */
    private static final Properties properties;

    static {
        logger = Logger.getLogger(GlobalProperties.class);
        properties = new Properties();
        logger.warn("TODO: load properties from file");
        properties.setProperty("remote-server", "https://willstuckey.com");
        properties.setProperty("remote-port", "443");
    }

    /**
     * returns globally coordinated properties
     * @return properties
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * utility constructor
     */
    private GlobalProperties() { }
}
