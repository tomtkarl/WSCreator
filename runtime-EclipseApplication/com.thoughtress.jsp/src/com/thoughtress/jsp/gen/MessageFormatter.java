package com.thoughtress.jsp.gen;

//Start of user code imports
import javax.servlet.http.HttpServletRequest;

//End of user code

/**
 * The MessageFormatter abstract class.<br />
 * MessageFormatter is subclassed by all Message Formatter implementations.
 */
public abstract class MessageFormatter {
    /**
     * Match the MessageFormatter against the given request
     * 
     * @param A HttpServletRequest representing the current request
     * @return A boolean whether this MessageFormatter matches.
     */
    public static boolean match(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Parse the given data string to a Request object This method may return null if the data is not a recognised
     * message format.
     * 
     * @param data The data string to be parsed
     * @return A MessagePart object derived from the given data.
     * @throws UserServiceException
     */
    public MessagePart parseToRequest(String data, HttpServletRequest request)
            throws UserServiceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Parse the given MessagePart object to a message format.
     * 
     * @param answer The MessagePart object to be parsed to string
     * @return A MessagePart object derived from the given data.
     * @throws UserServiceException
     */
    public String parseToFormat(MessagePart answer) throws UserServiceException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Build an error message to be returned to the user.
     * 
     * @param code The error code (using HTTP standard codes)
     * @param message A description of the error reason
     * @return A pretty string explaining the error to the user.
     * @throws Exception
     */
    public String buildError(UserServiceException e) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
