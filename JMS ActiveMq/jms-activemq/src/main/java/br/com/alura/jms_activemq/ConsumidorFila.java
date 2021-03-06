package br.com.alura.jms_activemq;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ConsumidorFila 
{
    public static void main( String[] args ) throws Exception 
    {
    	
    	InitialContext context = new InitialContext();
    	ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
    	
    	Connection connection = factory.createConnection();
    	connection.start();
    	
//		Confirma recebimento de mensagem automaticamente.
//    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//		Precisa confirmar recebimento de mensagem "message.acknowledge();"
//    	Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
    	
    	final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
    	Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				
				TextMessage textMessage = (TextMessage) message;
				try {
//					message.acknowledge();
					System.out.println(textMessage.getText());
					session.commit();
//					session.rollback();
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		
    	new Scanner(System.in).nextLine();
    	
    	session.close();
    	connection.close();
    	context.close();
    }
}
