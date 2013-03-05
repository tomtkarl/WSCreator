package com.thoughtress.jsp.gen;

//Start of user code imports
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

//End of user code
/**
 * A FunctionProvider for the Web Service<br />
 * Implements one or more functions to be made available through the service.
 */
public class GithubFunctionProvider extends FunctionProvider {
    // Start of user code classvars

    // End of user code
    /**
     * Match the functions defined by this FunctionProvider to the method of the given MessagePart.
     * 
     * @param req The MessagePart object to be matched against.
     * @return A Boolean value denoting whether this FunctionProvider matches the given MessagePart.
     */
    public static Boolean match(MessagePart req) {
        // Start of user code match
        if (req.name.equals("GitHubRequest") && req.options.get("nsURI") != null
                && req.options.get("nsURI").equals("https://api.github.com")) {
            return true;
        } else if (req.name.equals("GitHubRequest") && req.options.get("nsURI") == null) {
            return true;
        } else {
            return false;
        }
        // End of user code
    }

    public MessagePart process(MessagePart req) throws UserServiceException{
        // Start of user code process
        String reqMethod;
        MessagePart methodParam;
        if ((methodParam = req.getChild("method")) != null) {
            reqMethod = methodParam.textValue;
            req.children.remove(methodParam);
        } else {
            throw new UserServiceException(400, "No Method Element");
        }
        String githubScheme = "https";
        String githubHost = "api.github.com";

        HttpClient httpclient = new DefaultHttpClient();
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme(githubScheme).setHost(githubHost);
            builder.setPath(reqMethod);
            for (MessagePart param : req.children) {
                builder.addParameter(param.name, param.textValue);
            }
            URI uri = builder.build();
            HttpGet httpget = new HttpGet(uri);
            System.out.println("GitHubProcess::" + httpget.getURI());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responseBody = httpclient.execute(httpget, responseHandler);
            return new MessagePart("GitHubResponse") {
                {
                    textValue = responseBody;
                }
            };
            // System.out.println(responseBody);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Processing Request");
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Processing Request");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Processing Request");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        // End of user code
    }
}
