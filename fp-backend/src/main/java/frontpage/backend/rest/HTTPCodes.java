package frontpage.backend.rest;

/**
 * @author willstuckey
 * @date 10/5/16
 * <p></p>
 */
public class HTTPCodes {
    public static final int CONTINUE = 100;
    public static final int SWITCHING_PROTOCOLS = 101;

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NON_AUTHORATATIVE_INFORMATION = 203;
    public static final int NO_CONTENT = 204;
    public static final int RESET_CONTENT = 205;
    public static final int PARTIAL_CONTENT = 206;

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;

    public static final int INTERNAL_SERVER_ERROR = 500;

    public static boolean isError(final int httpCode) {
        return httpCode >= 300;
    }

    private HTTPCodes() {}
}
