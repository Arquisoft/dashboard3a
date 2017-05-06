package asw.dashboard.listeners;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class MessageListener {

	@Autowired
	private SimpMessagingTemplate template;

    private static final Logger logger = Logger.getLogger(MessageListener.class);

    @KafkaListener(topics = "sugerencias")
    public void listenSugerencia(String data) {
        logger.info("New suggestion received: \"" + data + "\"");
        template.convertAndSend("/topic/sugerencias", data);
    }
    
    @KafkaListener(topics = "comentarios")
    public void listenComentario(String data) {
    	logger.info("New comment received: \"" + data + "\"");
    	template.convertAndSend("/topic/comentarios", data);
    }
}
