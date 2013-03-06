package com.thoughtress.jsp.gen;

//Start of user code imports
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

//End of user code
/**
 * A FunctionProvider for the Web Service<br />
 * Implements one or more functions to be made available through the service.
 */
public class TwitterFunctionProvider extends FunctionProvider {
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
        if (req.name.equals("TwitterRequest") && req.options.get("nsURI") != null
                && req.options.get("nsURI").equals("https://api.twitter.com")) {
            return true;
        } else if (req.name.equals("TwitterRequest") && req.options.get("nsURI") == null) {
            return true;
        } else {
            return false;
        }
        // End of user code
    }

    public MessagePart process(MessagePart<?> req) throws UserServiceException {
     // Start of user code process
        String reqMethod;
        MessagePart<String> methodParam;
        if ((methodParam = (MessagePart<String>)req.getChild("method")) != null) {
            reqMethod = methodParam.getValue();
            req.children.remove(methodParam);
        } else {
            throw new UserServiceException(400, "No Method Element");
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("vtr34RpnJwAUW6pLk1F4gA")
                .setOAuthConsumerSecret("mA6E6L6Jyxwr5URE9IkqYIGTIoXASN2ori9YdiNo")
                .setOAuthAccessToken("385184847-uGJ3hTUy9TJWvyzhqIoNEE7yJ7tREwUZzBW4jQDR")
                .setOAuthAccessTokenSecret("OSSTnOwUEoPoro6YW3PvfXt9VeRsyJNBOno2kuvk2yw");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            if (reqMethod.equals("GetHomeTimeline")) {
                List<Status> statuses = twitter.getHomeTimeline();
                return statusesToMessagePart(statuses);
            } else if (reqMethod.equals("ShowStatus")) {
                String id = req.getChild("id").getValue();
                Status status = twitter.showStatus(Long.decode(id));
                return statusToMessagePart(status);
            } else if (reqMethod.equals("Search")) {
                Date since = ((Date)req.getChild("since").getValueAsType());
                Date until = ((Date)req.getChild("until").getValueAsType());
                String key = req.getChild("query").getValue();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Query query = new Query().since(df.format(since)).until(df.format(until));
                query.setQuery(key);
                System.out.println("query:" + query);
                QueryResult rslt = twitter.search(query);
                List<Status> statuses = rslt.getTweets();
                System.out.println("query returns: " + statuses.size());
                return statusesToMessagePart(statuses);
            } else {
                throw new UserServiceException(400, "Unrecognised TwitterRequest");
            }
        } catch (TwitterException e) {
            throw new UserServiceException(500, "Error Whilst Processing Request");
        } catch (ClassCastException e) {
            throw new UserServiceException(400, "Malformed Data in Request");
        }
        // End of user code
    }

    /**
     * Build a MessagePart from the given Status list
     * @param statuses A List of Status objects
     * @return A MessagePart from the given Status list
     * @generated-not
     */
    private MessagePart<String> statusesToMessagePart(List<Status> statuses) {
        MessagePart<String> root = new MessagePart<String>("TwitterResponse");
        for (Status status : statuses) {
            root.children.add(statusToMessagePart(status));
        }
        return root;
    }

    /**
     * Build a MessagePart from the given Status
     * @param status A Status object
     * @return A MessagePart from the given Status
     * @generated-not
     */
    private MessagePart<String> statusToMessagePart(final Status status) {
        // System.out.println("Tweet:: " + status.getText());
        final DateFormat df = DateFormat.getInstance();
        MessagePart<String> tweet = new MessagePart<String>("tweet") {
            {
                setValue(status.getText());
                attrs.put("id", String.valueOf(status.getId()));
                attrs.put("favourited", Boolean.toString(status.isFavorited()));
                attrs.put("author", status.getUser().getName());
                attrs.put("created", df.format(status.getCreatedAt()));
                attrs.put("isRetweet", Boolean.toString(status.isRetweet()));
            }
        };
        return tweet;
    }
}
