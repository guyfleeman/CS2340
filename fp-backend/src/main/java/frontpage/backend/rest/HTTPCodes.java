package frontpage.backend.rest;

/**
 * @author willstuckey
 * <p>Please see external resources for what return code actually mean</p>
 */
@SuppressWarnings("unused")
public final class HTTPCodes {

    /**
     * Http Response Code error threshold
     */
    private static final int ERROR_THRESH = 300;

    // 100s

    /**
     * Http Response Code CONTINUE (100)
     */
    public static final int CONTINUE = 100;

    /**
     * Http Response Code SWITCHING PROTOCOLS (101)
     */
    public static final int SWITCHING_PROTOCOLS = 101;

    // 200s

    /**
     * Http Response Code OK (200)
     */
    public static final int OK = 200;

    /**
     * Http Response Code CREATED (201)
     */
    public static final int CREATED = 201;

    /**
     * Http Response Code ACCEPTED (202)
     */
    public static final int ACCEPTED = 202;

    /**
     * Http Response Code NON AUTHORITATIVE INFORMATION (203)
     */
    public static final int NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * Http Response Code NO CONTENT (204)
     */
    public static final int NO_CONTENT = 204;

    /**
     * Http Response Code RESET CONTENT (205)
     */
    public static final int RESET_CONTENT = 205;

    /**
     * Http Response Code PARTIAL CONTENT (206)
     */
    public static final int PARTIAL_CONTENT = 206;

    // 400s

    /**
     * Http Response Code BAD REQUEST (400)
     */
    public static final int BAD_REQUEST = 400;

    /**
     * Http Response Code UNAUTHORIZED (401)
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Http Response Code FORBIDDEN (403)
     */
    public static final int FORBIDDEN = 403;

    /**
     * Http Response Code NOT FOUND (404)
     */
    public static final int NOT_FOUND = 404;

    /**
     * Http Response Code METHOD NOT ALLOWED (405)
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    // 500s

    /**
     * Http Response Code INTERNAL SERVER ERROR (500)
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * checks if an http response code is an error
     * @param httpCode response code
     * @return status
     */
    public static boolean isError(final int httpCode) {
        return httpCode >= ERROR_THRESH;
    }

    /**
     * utility constructor
     */
    private HTTPCodes() { }
}
