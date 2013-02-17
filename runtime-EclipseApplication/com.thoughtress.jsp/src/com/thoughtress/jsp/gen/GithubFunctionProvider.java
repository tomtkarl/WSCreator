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
        if (req.name.equals("GitHubRequest")
                && req.options.get("nsURI").equals("https://api.github.com")) {
            return true;
        } else if (req.name.equals("GET") && req.attrs.get("pathInfo") != null
                && req.attrs.get("pathInfo").contains("GitHubRequest")) {
            return true;
        } else {
            return false;
        }
        // End of user code
    }

    public MessagePart process(MessagePart req) {
        // Start of user code process
        String reqMethod;
        MessagePart methodParam;
        if ((methodParam = req.getChild("method")) != null) {
            reqMethod = methodParam.textValue;
            req.children.remove(methodParam);
        } else {
            return null;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return req;
        // End of user code
    }
}
