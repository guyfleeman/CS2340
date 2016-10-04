package frontpage.backend.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * @date 10/2/16
 * <p></p>
 */
public class RESTReport {

    private final boolean internalError;
    private final String internalErrorMessage;
    private final int httpResponseCode;
    private final String httpResponseMessage;
    private final String httpResponseBody;
    private final Map<String, String> responseValues
            = new HashMap<>();

    protected RESTReport(final int httpResponseCode,
                         final String httpResponseMessage,
                         final String httpResponseBody) {
        this(false, null, httpResponseCode, httpResponseMessage, httpResponseBody);
    }

    protected RESTReport(final boolean internalError,
                         final String internalErrorMessage) {
        this(internalError, internalErrorMessage, -1, null, null);
    }

    private RESTReport(final boolean internalError,
                       final String internalErrorMessage,
                       final int httpResponseCode,
                       final String httpResponseMessage,
                       final String httpResponseBody) {
        this.internalError = internalError;
        this.internalErrorMessage = internalErrorMessage;
        this.httpResponseCode  = httpResponseCode;
        this.httpResponseMessage = httpResponseMessage;
        this.httpResponseBody = httpResponseBody;
        RESTReport.addKVPairsToMap(responseValues, this.httpResponseBody);
    }

    public String getResponseValue(final String key) {
        return responseValues.get(key);
    }

    public boolean success() {
        return !rejected() && responseValues.get("status").contains("success");
    }

    public boolean rejected() {
        return internalError || httpResponseCode >= 300;
    }

    public boolean wasInternalError() {
        return internalError;
    }

    public String getInternalErrorMessage() {
        return internalErrorMessage;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public String getHttpResponseMessage() {
        return httpResponseMessage;
    }

    public String getHttpResponseBody() {
        return httpResponseBody;
    }

    public Map<String, String> getResponseValues() {
        return responseValues;
    }

    public String toString() {
        String ret = "";
        ret += "\r\nRESTReport:\r\n";
        if (internalError) {
            ret += "Internal Error: true\r\n";
            ret += "Internal Error Message: " + internalErrorMessage + "\r\n";
        }
        ret += "HTTP Response Code: " + httpResponseCode + "\r\n";
        ret += "HTTP Response: " + httpResponseMessage + "\r\n";
        ret += "API Keys Returned: [";
        for (final String key : responseValues.keySet()) {
            ret += key;
            ret += ",";
        }
        if (ret.charAt(ret.length() - 1) == ',') {
            ret = ret.substring(0, ret.length() - 1);
        }
        ret += "]\r\n";
        return ret;
    }

    private static void addKVPairsToMap(Map<String, String> map, String raw) {
        if (map == null || raw == null) {
            return;
        }

        for (final String line : raw.split("\r\n")) {
            final String[] kvPair = line.split("=");
            if (kvPair.length >= 2) {
                String value = "";
                for (int i = 1; i < kvPair.length; i++) {
                    value += kvPair[i];
                }
                map.put(kvPair[0], value);
            } else if (kvPair.length == 1) {
                map.put(kvPair[0], "");
            }
        }
    }
}
