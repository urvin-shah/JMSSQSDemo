package com.urvin.sqs.jms.textmessage;

public class JMSSQSDemo {
    public static void main(String[] args) {
        TextMessageQueue queue = new TextMessageQueue("TextMessageQueue");
        queue.sendTextMessage("This is Second Text Message");
    }
}
