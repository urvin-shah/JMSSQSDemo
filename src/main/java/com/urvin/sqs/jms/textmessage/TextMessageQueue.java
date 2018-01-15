package com.urvin.sqs.jms.textmessage;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.urvin.sqs.jms.SQSQueue;
import javafx.scene.text.Text;

import javax.jms.*;

public class TextMessageQueue extends SQSQueue implements MessageListener{

    public TextMessageQueue(String queueName) {
        super("TextMessageQueue");
        receiveMessageAsync();
    }

    public void sendTextMessage(String textMessage) {
        try {
            SQSConnection connection = getConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


            ensureQueueExists(connection, this.getQueueName());
            Queue queue = session.createQueue(this.getQueueName());
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage(textMessage);


            producer.send(message);
            System.out.println("JMS Message " + message.getJMSMessageID());
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
    public void onMessage(Message message) {
        try {
            // Cast the received message as TextMessage and print the text to screen.
            System.out.println("Received: " + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
