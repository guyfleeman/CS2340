package frontpage.backend.rest;

import frontpage.bind.GlobalProperties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author willstuckey
 * <p>This class serves as the low level communications layer between
 * our server API and user interface backend abstractions.</p>
 *
 * This class also contains definitions for known API entry points
 * in the form of hosted php scripts.
 */
public final class RESTHandler {
    /**
     * URL of the page that handles account creation requests
     */
    public static final String ACCOUNT_CREATION_ENTRY_POINT =
            "staging/water/api/create_account.php";

    /**
     * URL of the page that handles account authentication
     * requests
     */
    public static final String ACCOUNT_AUTH_ENTRY_POINT =
            "staging/water/api/authenticate.php";

    /**
     * URL of the page that handles profile requests
     */
    public static final String ACCOUNT_PROFILE_ENTRY_POINT =
            "staging/water/api/profile.php";

    /**
     * URL of the page that handles user data requests
     */
    public static final String ACCOUNT_USER_ENTRY_POINT =
            "staging/water/api/user.php";

    /**
     * URL of the page that handles report requests
     */
    public static final String REPORT_ENTRY_POINT =
            "staging/water/api/report.php";

    /**
     * time after which we give up trying to connect to the
     * server
     */
    private static final int CONNECTION_TIMEOUT = 2 * 1000;

    /**
     * time after which we give up waiting for the server
     * to reply
     */
    private static final int READ_TIMEOUT = 5 * 1000;

    /**
     * default encoding for URL
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * debugs the REST query, never deploy with this set to true
     * as passwords may be logged in plain text
     */
    private static final boolean DEBUG_REST = false;

    /**
     * class logger
     */
    private static final Logger LOGGER;

    /**
     * REST actions, standard HTTP request codes
     */
    public enum RestAction {
        /**
         * POST
         */
        POST,

        /**
         * GET
         */
        GET
    }

    static {
        LOGGER = Logger.getLogger(RESTHandler.class.getName());
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * makes an api request. The API request will be made with an action,
     * GET and POST are supported for now. The request will be made to
     * a php script, or entry point on the server. The server address is
     * pulled from global properties. The attribute map contains standard
     * GET maps in the form of Key (published variable), Value.
     * @param action request action
     * @param apiEntryPoint entry point on public facing directory
     * @param attribMap map of attributes
     * @return RESTReport detailing types and points of failure, as well
     *         as received payload.
     */
    public static RESTReport apiRequest(final RestAction action,
                                     final String apiEntryPoint,
                                     final Map<String, String> attribMap) {
        LOGGER.debug("API Request Invoked");
        LOGGER.trace("Building API Request");
        String query = "";
        boolean firstAttrib = true;
        for (String key : attribMap.keySet()) {
            if (key != null) {
                final String val = attribMap.get(key);
                if (val != null) {
                    LOGGER.trace("API Request appending valid <K,V>: <"
                            + key + "," + val + ">.");
                    if (!firstAttrib) {
                        query += "&";
                    } else {
                        firstAttrib = false;
                    }

                    try {
                        query += URLEncoder.encode(key, DEFAULT_ENCODING);
                        query += "=";
                        query += URLEncoder.encode(val, DEFAULT_ENCODING);
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("default encoding was invalid, "
                                + "error in hardcoded default");
                        return new RESTReport(true, e.getMessage());
                    }
                }
            }
        }
        LOGGER.trace("query built: " + ((DEBUG_REST) ? query : ""));

        String urlStr = null;
        final URL url;
        try {
            urlStr = GlobalProperties.getProperties().get("remote-server")
                    + "/"
                    + apiEntryPoint;
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            LOGGER.error("malformed url for api entry: " + urlStr);
            return new RESTReport(true, e.getMessage());
        }

        try {
            LOGGER.trace("Connection Type: " + action.toString());
            HttpsURLConnection con =
                    (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(action.toString());
            con.setRequestProperty("Content-length",
                    String.valueOf(query.length()));
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
            con.setConnectTimeout(CONNECTION_TIMEOUT);
            con.setReadTimeout(READ_TIMEOUT);
            con.setDoOutput(true);
            con.setDoInput(true);

            DataOutputStream conOutput =
                    new DataOutputStream(con.getOutputStream());
            conOutput.writeBytes(query);
            conOutput.close();

            String responsePayload = "";
            if (!HTTPCodes.isError(con.getResponseCode())) {
                DataInputStream conInput =
                        new DataInputStream(con.getInputStream());
                for (int c = conInput.read(); c != -1; c = conInput.read()) {
                    responsePayload += (char) c;
                }
                conInput.close();
            }

            LOGGER.trace("Return Data: \r\n\r\n" + responsePayload);

            RESTReport report = new RESTReport(con.getResponseCode(),
                    con.getResponseMessage(),
                    responsePayload);
            LOGGER.trace("Response Code:"
                    + report.getHttpResponseCode());
            LOGGER.trace("Response Message: "
                    + report.getHttpResponseMessage());
            LOGGER.trace(report);

            if (!report.success()) {
                LOGGER.error("API request rejected");
                LOGGER.error(report);
            }

            return report;
        } catch (IOException e) {
            LOGGER.error("failed to open connection" + e.getCause(), e);
            return new RESTReport(true, e.getMessage());
        }
    }

    /**
     * utility constructor
     */
    private RESTHandler() { }
}
