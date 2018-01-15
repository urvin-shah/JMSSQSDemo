package com.urvin.sqs.jms.objectmessage;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.message.SQSObjectMessage;
import com.urvin.sqs.jms.SQSQueue;

import javax.jms.*;

public class ObjectMessageQueue extends SQSQueue implements MessageListener {

    public ObjectMessageQueue(String queueName) {
        super(queueName);
        receiveMessageAsync();
    }

    public void sendObjectMessage(Message message) {
        try {
            SQSConnection connection = getConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


            ensureQueueExists(connection, this.getQueueName());
            Queue queue = session.createQueue(this.getQueueName());
            MessageProducer producer = session.createProducer(queue);
            SQSObjectMessage sqsObjectMessage = new SQSObjectMessage(message);


            producer.send(sqsObjectMessage);
            System.out.println("JMS Message " + sqsObjectMessage.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessageAsync() {
        try {
            SQSConnection connection = this.getConnection();
            // Create the session
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer( session.createQueue( this.getQueueName() ) );

            consumer.setMessageListener( this );

            // No messages are processed until this is called
            connection.start();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        try {
            // Cast the received message as TextMessage and print the text to screen.
            System.out.println("Message receiver get called");
            User user = (User)((SQSObjectMessage)message).getObject();
            System.out.println("Username : "+user.getUsername());
            System.out.println("Email : "+user.getEmail());
            System.out.println("Phone : "+user.getPhone());
        } catch (JMSException e) {
            System.out.println("There is some error");
            e.printStackTrace();
        }
    }

}
