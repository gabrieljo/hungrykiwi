package app.me.hungrykiwi.http;



/**
 * http response message
 * Created by user on 10/10/2016.
 */
public class HttpMessage {
    public enum Type {
        INVALID_TOKEN,
        HTTP_NOT_FOUND,
        INTERNAL_ERROR,
        TIME_OUT,
    }

    /**
     * get message
     *
     * @param type
     * @return
     */
    public static String getMessage(Type type) {
        String message = null;
        switch (type) {
            case INVALID_TOKEN:
                message = "Token is invalid or expired, please try again.";
                break;
            case HTTP_NOT_FOUND:
                message = "HTTP not found.";
                break;
            case INTERNAL_ERROR:
                message = "Server has internal problem, please try again later.";
                break;
            case TIME_OUT:
                message = "Time Out, Server does not response to request.";
                break;
        }
        return message;
    }
}
