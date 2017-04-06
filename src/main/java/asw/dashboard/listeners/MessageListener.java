package asw.dashboard.listeners;

import java.io.IOException;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import asw.model.Comentario;
import asw.model.ComentarioRepository;
import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class MessageListener {

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private SugerenciaRepository sugerenciaRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
    private static final Logger logger = Logger.getLogger(MessageListener.class);

    @KafkaListener(topics = "sugerencias")
    public void listenSugerencia(String data) {
        logger.info("New suggestion received: \"" + data + "\"");
        try {
        	Sugerencia sugerencia = new ObjectMapper().readValue(data, Sugerencia.class);
			sugerencia = sugerenciaRepository.saveAndFlush(sugerencia);
			data = new ObjectMapper().writeValueAsString(sugerencia);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        template.convertAndSend("/topic/sugerencias", data);
    }
    
    @KafkaListener(topics = "comentarios")
    public void listenComentario(String data) {
    	logger.info("New comment received: \"" + data + "\"");
    	try {
    		Comentario comentario = new ObjectMapper().readValue(data, Comentario.class);
			comentario = comentarioRepository.saveAndFlush(comentario);
			data = new ObjectMapper().writeValueAsString(comentario);
		} catch (IOException e) {
			e.printStackTrace();
		}

    	template.convertAndSend("/topic/comentarios", data);
    }
}
