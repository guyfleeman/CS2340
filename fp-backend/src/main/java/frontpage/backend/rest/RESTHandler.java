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
 * @date 10/1/16
 * <p></p>
 */
public class RESTHandler {
    public static final String ACCOUNT_CREATION_ENTRY_POINT =
            "staging/water/api/create_account.php";
    public static final String ACCOUNT_AUTH_ENTRY_POINT =
            "staging/water/api/authenticate.php";
    public static final String ACCOUNT_PROFILE_ENTRY_POINT =
            "staging/water/api/profile.php";

    private static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * debugs the REST query, never deploy with this set to true
     * as passwords may be logged in plain text
     */
    private static final boolean DEBUG_REST = false;
    private static final Logger logger;

    public enum RestAction {
        POST,
        GET
    }

    static {
        logger = Logger.getLogger(RESTHandler.class.getName());
        logger.setLevel(Level.ALL);
    }

    /**
     * makes an api request
     * @param action request action
     * @param apiEntryPoint entry point on public facing directory
     * @param attribMap map of attributes
     * @return success
     */
    public static synchronized RESTReport apiRequest(final RestAction action,
                                     final String apiEntryPoint,
                                     final Map<String, String> attribMap) {
        logger.debug("API Request Invoked");
        logger.trace("Building API Request");
        String query = "";
        boolean firstAttrib = true;
        for (String key : attribMap.keySet()) {
            if (key != null) {
                final String val = attribMap.get(key);
                if (val != null) {
                    logger.trace("API Request appending valid <K,V>: <" + key + "," + val + ">.");
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
                        logger.error("default encoding was invalid, error in hardcoded default");
                        return new RESTReport(true, e.getMessage());
                    }
                }
            }
        }
        logger.trace("query built: " + ((DEBUG_REST) ? query : ""));

        String urlStr = null;
        final URL url;
        try {
            urlStr = GlobalProperties.getProperties().get("remote-server")
                    + "/"
                    + apiEntryPoint;
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            logger.error("malformed url for api entry: " + urlStr);
            return new RESTReport(true, e.getMessage());
        }

        try {
            logger.trace("Connection Type: " + action.toString());
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(action.toString());
            con.setRequestProperty("Content-length", String.valueOf(query.length()));
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
            //con.setConnectTimeout(2000);
            //con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setDoInput(true);

            DataOutputStream conOutput = new DataOutputStream(con.getOutputStream());
            conOutput.writeBytes(query);
            conOutput.close();

            String responsePayload = "";
            DataInputStream conInput = new DataInputStream(con.getInputStream());
            for(int c = conInput.read(); c != -1; c = conInput.read()) {
                responsePayload += (char) c;
            }
            conInput.close();
            logger.trace("Return Data: \r\n\r\n" + responsePayload);
            RESTReport report = new RESTReport(con.getResponseCode(), con.getResponseMessage(), responsePayload);
            logger.trace("Response Code:" + report.getHttpResponseCode());
            logger.trace("Response Message: " + report.getHttpResponseMessage());
            logger.trace(report);

            if (!report.success()) {
                logger.error("API request rejected");
                logger.error(report);
            }

            return report;
        } catch (IOException e) {
            logger.error("failed to open connection" + e.getCause().getMessage(), e);
            return new RESTReport(true, e.getMessage());
        }
    }

    private RESTHandler() {}
}
