package br.com.alura.jms_activemq;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.alura.modelo.Pedido;
import br.com.alura.modelo.PedidoFactory;

public class ProdutorTopico {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination topico = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(topico);

		Pedido pedido = new PedidoFactory().geraPedidoComValores();

		StringWriter stringWriter = new StringWriter();
		JAXB.marshal(pedido, stringWriter);
		String xml = stringWriter.toString();

		System.out.println(xml);

		TextMessage message = session.createTextMessage(xml);
//		message.setBooleanProperty("ebook", true);
		producer.send(message);

		session.close();
		connection.close();
		context.close();
	}

}
