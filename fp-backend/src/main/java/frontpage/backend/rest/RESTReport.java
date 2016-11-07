package frontpage.backend.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willstuckey
 * <p></p>
 */
@SuppressWarnings("WeakerAccess")
public class RESTReport {
    /**
     * if the REST request generated an internal error before
     * being sent to the server
     */
    private final boolean internalError;

    /**
     * if the response payload contains variable length data
     */
    private final boolean dynamicData;

    /**
     * internal error message, contents undefined if
     * internalError is false
     */
    private final String internalErrorMessage;

    /**
     * the server response code
     * @see frontpage.backend.rest.HTTPCodes
     */
    private final int httpResponseCode;

    /**
     * http header message of the response
     */
    private final String httpResponseMessage;

    /**
     * the raw contents of of the response body
     */
    private final String httpResponseBody;

    /**
     * map of response KV pairs
     */
    private final Map<String, String>[] responseValues;

    /**
     * creates a rest report from the response of a request
     * @see frontpage.backend.rest.HTTPCodes
     *
     * @param httpResponseCode response code of request
     * @param httpResponseMessage header message
     * @param httpResponseBody raw response
     */
    protected RESTReport(final int httpResponseCode,
                         final String httpResponseMessage,
                         final String httpResponseBody) {
        this(false,
                null,
                httpResponseCode,
                httpResponseMessage,
                httpResponseBody);
    }

    /**
     * creates a rest report whos request could not be formed
     * @param internalError status of internal error
     * @param internalErrorMessage internal error message
     */
    @SuppressWarnings("SameParameterValue")
    protected RESTReport(final boolean internalError,
                         final String internalErrorMessage) {
        this(internalError, internalErrorMessage, -1, null, null);
    }

    /**
     * creates a rest report
     * @see frontpage.backend.rest.HTTPCodes
     *
     * @param internalError internal error status
     * @param internalErrorMessage internal error message
     * @param httpResponseCode response code
     * @param httpResponseMessage http response header message
     * @param httpResponseBody raw payload
     */
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
        RESTReport.addKVPairsToMaps(responseValues,
                this.httpResponseBody,
                dynamicData);
    }

    /**
     * gets a response value from POST key or return key
     * @param key key
     * @return value
     */
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

    /**
     * returns if the request was completely successful
     * @return success
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean success() {
        return !wasInternalError()
                && !rejected()
                && responseValues[0].get("status") != null
                && responseValues[0].get("status").contains("success");
    }

    /**
     * returns if the request was successfully made
     * @return request made
     */
    public boolean rejected() {
        return !wasInternalError()
        && HTTPCodes.isError(httpResponseCode);
    }

    /**
     * returns if an internal error caused the request to be rejected
     * @return internal error caused rejection
     */
    public boolean wasInternalError() {
        return internalError;
    }

    /**
     * returns internal error message. If no internal error occurred
     * return value is undefined
     * @return internal error message
     */
    public String getInternalErrorMessage() {
        return internalErrorMessage;
    }

    /**
     * returns the http response code
     * @see frontpage.backend.rest.HTTPCodes
     *
     * @return response code
     */
    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    /**
     * gets the http response message from the reponse header
     * @return response message
     */
    public String getHttpResponseMessage() {
        return httpResponseMessage;
    }

    /**
     * gets the raw response payload/body
     * @return body
     */
    public String getHttpResponseBody() {
        return httpResponseBody;
    }

    /**
     * gets an array of maps, where the first map is response metadata
     * and all other maps are variable payload entries
     * @return parsed response
     */
    public Map<String, String>[] getResponseValues() {
        return responseValues;
    }

    /**
     * gets a map of non dynamic length responses
     * @return parsed response
     */
    public Map<String, String> getSingleResponseMap() {
        return responseValues[0];
    }

    /**
     * toString
     * @return string
     */
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

    /**
     * parses response data and loads KVs into an array of maps
     * @param maps array of maps to load
     * @param raw raw data
     * @param dyn if the data has dynamic length
     */
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

    /**
     * loads raw data into a map
     * @param map map
     * @param raw raw data
     * @param dyn if the data is part of a dynamic entry
     */
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
            addKVPairsToMap(map, indDataPair[1].replace("|", "\r\n"), false);
        }
    }

    /**
     * returns if the response had dynamic length data
     * @return if the response had dynamic length data
     */
    public boolean isDynamicData() {
        return dynamicData;
    }
}
