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
    private static final Logger LOGGER;

    /**
     * global properties
     */
    private static final Properties PROPERTIES;

    static {
        LOGGER = Logger.getLogger(GlobalProperties.class);
        PROPERTIES = new Properties();
        LOGGER.warn("TODO: load properties from file");
        PROPERTIES.setProperty("remote-server", "https://willstuckey.com");
        PROPERTIES.setProperty("remote-port", "443");
    }

    /**
     * returns globally coordinated properties
     * @return properties
     */
    public static Properties getProperties() {
        return PROPERTIES;
    }

    /**
     * utility constructor
     */
    private GlobalProperties() { }
}
