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

    private static int lastHttpsResponseCode = -1;
    private static String lastHttpsResponseMessage = "";
    private static String lastHttpsResponsePayload = "";

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
    public static synchronized boolean apiRequest(final RestAction action,
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
                        return false;
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
            return false;
        }

        try {
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(action.toString());
            con.setRequestProperty("Content-length", String.valueOf(query.length()));
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
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
            lastHttpsResponsePayload = responsePayload;

            lastHttpsResponseCode = con.getResponseCode();
            lastHttpsResponseMessage = con.getResponseMessage();
            logger.trace("Response Code:" + lastHttpsResponseCode);
            logger.trace("Response Message: " + lastHttpsResponseMessage);

            if (lastHttpsResponseCode >= 300) {
                logger.error("API request reject with error code: " + lastHttpsResponseCode);
                logger.error("API request rejection message: " + lastHttpsResponseMessage);
                logger.error("API request rejection payload: " + responsePayload);
                return false;
            }
        } catch (IOException e) {
            logger.error("failed to open connection", e);
            return false;
        }

        return true;
    }

    public static int getLastHttpsResponseCode() {
        return lastHttpsResponseCode;
    }

    public static String getLastHttpsResponseMessage() {
        return lastHttpsResponseMessage;
    }

    public static String getLastHttpsResponsePayload() {
        return lastHttpsResponsePayload;
    }

    private RESTHandler() {}
}
