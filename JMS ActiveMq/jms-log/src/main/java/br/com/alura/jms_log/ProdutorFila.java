package br.com.alura.jms_log;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class ProdutorFila {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("LOG");
		MessageProducer producer = session.createProducer(fila);

		TextMessage message = session.createTextMessage(
				"|  ERROR | Apache ActiveMQ 5.12.0 (localhost, ID:DESKTOP-0JE6COH-50836-1565181637324-0:1) is starting");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 9, 80000);

//		for (Integer i = 0; i < 1000; i++) {
//
//			TextMessage message = session.createTextMessage("<pedido><id>"+ i + "</id></pedido>");
//			producer.send(message);
//
//		}

		session.close();
		connection.close();
		context.close();
	}

}
