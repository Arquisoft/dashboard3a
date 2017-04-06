package asw.dashboard.listeners;

import java.io.IOException;

import javax.annotation.ManagedBean;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import asw.model.CategoriaRepository;
import asw.model.Comentario;
import asw.model.ComentarioRepository;
import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;
import asw.model.Usuario;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
@Transactional
public class MessageListener {

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private SugerenciaRepository sugerenciaRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private JpaContext jpaContext;
	
    private static final Logger logger = Logger.getLogger(MessageListener.class);

    @KafkaListener(topics = "sugerencias")
    public void listenSugerencia(String data) {
        logger.info("New suggestion received: \"" + data + "\"");
        try {
        	Sugerencia sugerencia = new ObjectMapper().readValue(data, Sugerencia.class);
        	sugerencia.setTitulo(sugerencia.getContenido());
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
    		Sugerencia sugerencia = comentario.getSugerencia();
    		categoriaRepository.saveAndFlush(sugerencia.getCategoria());
    		sugerencia = jpaContext.getEntityManagerByManagedType(sugerencia.getClass())
    								.merge(sugerencia);
    		comentario.setSugerencia(sugerencia);
    		Usuario usuario = comentario.getUsuario();
    		usuario = jpaContext.getEntityManagerByManagedType(usuario.getClass())
    							.merge(usuario);
    		comentario.setUsuario(usuario);
			comentario = comentarioRepository.saveAndFlush(comentario);
			data = new ObjectMapper().writeValueAsString(comentario);
		} catch (IOException e) {
			e.printStackTrace();
		}

    	template.convertAndSend("/topic/comentarios", data);
    }
}
