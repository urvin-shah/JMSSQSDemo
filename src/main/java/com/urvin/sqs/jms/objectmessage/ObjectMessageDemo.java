package com.urvin.sqs.jms.objectmessage;

public class ObjectMessageDemo {
    public static void main(String[] args) {
         ObjectMessageQueue queue = new ObjectMessageQueue("ObjectMessageQueue");
         User user = new User("admin","admin@urvin.com","9012345678");
         queue.sendObjectMessage(user);

    }
}
