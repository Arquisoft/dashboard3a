package asw.dashboard.producers;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import asw.model.Category;
import asw.model.Comment;
import asw.model.Suggestion;
import asw.model.User;

/**
 * Created by herminio on 26/12/16.
 */
@ManagedBean
public class MockKafkaProducer {

	private static final Logger logger = Logger.getLogger(MockKafkaProducer.class);
	public static final AtomicInteger counterComentario = new AtomicInteger(0);
	public static final AtomicInteger counterSugerencia = new AtomicInteger(0);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendComentario() {
		int counter = MockKafkaProducer.counterComentario.incrementAndGet();
		User usuario = new User("Nuevo usuario " + counter, null);
		Comment comentario = new Comment("Nuevo comentario" + counter, 
				new Suggestion("Nueva sugerencia" + counter, 
						new Category("Nueva categoría " + counter), usuario), 
				usuario);
				
		sendMessageJSON("comentarios", comentario);
	}

	public void sendSugerencia() {
		int counter = MockKafkaProducer.counterSugerencia.incrementAndGet();
		Suggestion sugerencia = new Suggestion("Nueva sugerencia " + counter, 
				new Category("Nueva categoría " + counter), 
				new User("Nuevo usuario " + counter, null));
		sugerencia.setCreationDate(new Date());
		
		sendMessageJSON("sugerencias", sugerencia);
	}

	public void sendMessage(String topic, String data) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success on sending message \"" + data + "\" to topic " + topic);
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending message \"" + data + "\", stacktrace " + ex.getMessage());
			}
		});
	}
	
	public void sendMessageJSON(String topic, Object data) {
		try {
			sendMessage(topic, new ObjectMapper().writeValueAsString(data));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
