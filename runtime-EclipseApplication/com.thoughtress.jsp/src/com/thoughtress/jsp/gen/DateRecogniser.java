package com.thoughtress.jsp.gen;

import java.util.Date;
import org.joda.time.DateTime;

public class DateRecogniser extends DataRecogniser{

    public static MessagePart recognise(MessagePart<?> root) {
        if (root.children.isEmpty() && root.attrs.get("type") != null
                && root.attrs.get("type").equals("Date")) {
            try {
                DateTime dt = new DateTime(root.getValue());
                MessagePart<Date> ret = new MessagePart<Date>(root.name);
                ret.setValue(dt.toDate());
                return ret;
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        } else {
            for (int i = 0; i < root.children.size(); i++) {
                MessagePart child = root.children.get(i);
                root.children.set(i, recognise(child));
            }
        }
        return root;
    }
}
