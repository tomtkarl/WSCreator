package com.thoughtress.jsp.gen;

//Start of user code imports
import java.text.DateFormat;
import java.util.List;
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
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("vtr34RpnJwAUW6pLk1F4gA")
          .setOAuthConsumerSecret("mA6E6L6Jyxwr5URE9IkqYIGTIoXASN2ori9YdiNo")
          .setOAuthAccessToken("385184847-uGJ3hTUy9TJWvyzhqIoNEE7yJ7tREwUZzBW4jQDR")
          .setOAuthAccessTokenSecret("OSSTnOwUEoPoro6YW3PvfXt9VeRsyJNBOno2kuvk2yw");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        MessagePart ret = null;
        try {
            if (reqMethod.equals("GetHomeTimeline")){
                List<Status> statuses = twitter.getHomeTimeline();
                ret = statusesToMessagePart(statuses);
            } else if (reqMethod.equals("ShowStatus")){
                String id = req.getChild("id").textValue;
                Status status = twitter.showStatus(Long.decode(id));
                ret = statusToMessagePart(status);
            }
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
        // End of user code
    }
    private MessagePart statusesToMessagePart(List<Status> statuses){
        MessagePart root = new MessagePart("TwitterResponse");
        for (Status status: statuses){
            root.children.add(statusToMessagePart(status));
        }
        return root;
    }
    private MessagePart statusToMessagePart(final Status status){
        //System.out.println("Tweet:: " + status.getText());
        final DateFormat df = DateFormat.getInstance();
        MessagePart tweet = new MessagePart("tweet"){{
           textValue = status.getText();
           attrs.put("id", String.valueOf(status.getId()));
           attrs.put("favourited", Boolean.toString(status.isFavorited()));
           attrs.put("author", status.getUser().getName());
           attrs.put("created", df.format(status.getCreatedAt()));
           attrs.put("isRetweet", Boolean.toString(status.isRetweet()));
        }};
        return tweet;
    }
}
