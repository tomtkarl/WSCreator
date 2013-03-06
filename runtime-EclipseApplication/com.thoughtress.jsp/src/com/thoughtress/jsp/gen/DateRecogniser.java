package com.thoughtress.jsp.gen;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

public class DateRecogniser {

    public MessagePart recognise(MessagePart<?> root) {
        if (root.children.isEmpty()){
            try {
                DateTime dt = new DateTime(root.getValue());
                MessagePart<Date> ret = new MessagePart<Date>(root.name);
                ret.setValue(dt.toDate());
                return ret;
            } catch (IllegalArgumentException e) {
                //do nothing
            } 
        } else {
            for (int i = 0; i < root.children.size(); i++){
                MessagePart child = root.children.get(i);
                root.children.set(i, recognise(child));
            }
        }
        return root;
    }
}
