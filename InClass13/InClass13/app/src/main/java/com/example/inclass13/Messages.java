package com.example.inclass13;

import java.io.Serializable;

/**
 * Created by nalin on 4/23/2018.
 */

public class Messages implements Serializable{



    String messageSender;
    String messagedescription;
    String dateTimeOfMessage;
    boolean isMessageRead;
    String messageKey;

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessagedescription() {
        return messagedescription;
    }

    public void setMessagedescription(String messagedescription) {
        this.messagedescription = messagedescription;
    }

    public String getDateTimeOfMessage() {
        return dateTimeOfMessage;
    }

    public void setDateTimeOfMessage(String dateTimeOfMessage) {
        this.dateTimeOfMessage = dateTimeOfMessage;
    }

    public boolean isMessageRead() {
        return isMessageRead;
    }

    public void setMessageRead(boolean messageRead) {
        isMessageRead = messageRead;
    }
}
