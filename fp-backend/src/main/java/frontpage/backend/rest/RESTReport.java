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
    private final boolean dynamicData;
    private final String internalErrorMessage;
    private final int httpResponseCode;
    private final String httpResponseMessage;
    private final String httpResponseBody;
    private final Map<String, String>[] responseValues;

    protected RESTReport(final int httpResponseCode,
                         final String httpResponseMessage,
                         final String httpResponseBody) {
        this(false, null, httpResponseCode, httpResponseMessage, httpResponseBody);
    }

    protected RESTReport(final boolean internalError,
                         final String internalErrorMessage) {
        this(internalError, internalErrorMessage, -1, null, null);
    }

    @SuppressWarnings("unchecked")
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
        int count = 0;
        if (httpResponseBody.contains("variablepayload")) {
            for (String s : httpResponseBody.split("\r\n")) {
                if (s.contains("variablepayload")) {
                    String[] kv = s.split("=");
                    count = Integer.parseInt(kv[1]);
                    break;
                }
            }
        }
        dynamicData = count > 0;
        responseValues = (Map<String, String>[]) new HashMap[count + 1];
        RESTReport.addKVPairsToMaps(responseValues, this.httpResponseBody, dynamicData);
    }

    public String getResponseValue(final String key) {
        for (Map<String, String> map : responseValues) {
            if (map != null) {
                String result = map.get(key);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public boolean success() {
        return !wasInternalError()
                && !rejected()
                && responseValues[0].get("status") != null
                && responseValues[0].get("status").contains("success");
    }

    public boolean rejected() {
        return !wasInternalError()
        && httpResponseCode >= 300;
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

    public Map<String, String>[] getResponseValues() {
        return responseValues;
    }

    public Map<String, String> getSingleResponseMap() {
        return responseValues[0];
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
        ret += "API Keys Returned:\r\n";
        for (final Map<String, String> map : responseValues) {
            ret += "[";
            for (final String key : map.keySet()) {
                ret += key;
                ret += ",";
            }
            ret += "]\r\n";
        }
        if (ret.charAt(ret.length() - 1) == ',') {
            ret = ret.substring(0, ret.length() - 1);
        }

        return ret;
    }

    private static void addKVPairsToMaps(final Map<String, String>[] maps,
                                         final String raw,
                                         final boolean dyn) {
        if (!dyn) {
            maps[0] = new HashMap<>();
            addKVPairsToMap(maps[0], raw, false);
        } else {
            maps[0] = new HashMap<>();
            int mapIndex = 1;
            boolean inDynDataSec = false;
            for (final String line : raw.split("\r\n")) {
                if (line.equalsIgnoreCase("--- BEGIN ---")) {
                    inDynDataSec = true;
                } else if (line.equalsIgnoreCase("--- END ---")) {
                    inDynDataSec = false;
                } else if (inDynDataSec) {
                    maps[mapIndex] = new HashMap<>();
                    addKVPairsToMap(maps[mapIndex++], line, true);
                } else {
                    final String[] kvPair = line.split("=");
                    if (kvPair.length >= 2) {
                        String value = "";
                        for (int i = 1; i < kvPair.length; i++) {
                            value += kvPair[i];
                        }
                        maps[0].put(kvPair[0], value);
                    } else if (kvPair.length == 1) {
                        maps[0].put(kvPair[0], "");
                    }
                }
            }
        }
    }

    private static void addKVPairsToMap(final Map<String, String> map,
                                        final String raw,
                                        final boolean dyn) {
        if (map == null || raw == null) {
            return;
        }

        if (!dyn) {
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
        } else {
            String[] indDataPair = raw.split(":", 2);
            map.put("index", indDataPair[0]);
            addKVPairsToMap(map, indDataPair[1].replace(",", "\r\n"), false);
        }
    }

    public boolean isDynamicData() {
        return dynamicData;
    }
}
