package com.bzyness.bzyness.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

/**
 * Created by Pervacio on 3/30/2017.
 */



public class Chat {
    public Chat(){
    }

    public Chat(String sender, String msg) {
        this.sender = sender;
        this.msg = msg;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Exclude
    public long getMsgTimeLong() {
        if(msgTime instanceof Long){
            return msgTime;
        }
        else{
            return (Long)null;
        }
    }


    public java.util.Map<String, String> getMsgTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

    Long msgTime;
    String sender;
    String msg;

}
