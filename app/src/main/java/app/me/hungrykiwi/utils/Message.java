package app.me.hungrykiwi.utils;

/**
 * message utils
 * Created by user on 10/10/2016.
 */

public class Message {
    public enum Type {
        PROBLEM_DATA_CONNECTION,
        PROBLEM_STRING_EMPTY,
        PROBLEM_IMAGE_NOT_ALLOWED,
    }

    /**
     * get message
     * @param type
     * @return
     */
    public static String getMessage(Type type) {
        String message = null;
        switch (type) {
            case PROBLEM_DATA_CONNECTION:
                message = "Data is not connected, please connect with wifi or data";
               break;
            case PROBLEM_STRING_EMPTY:
                message = "Please type in";
                break;
            case PROBLEM_IMAGE_NOT_ALLOWED:
                message = "The number of image can not be over 5";
                break;
        }
        return message;
    }
}
