package hello.listeners;

import java.util.ArrayList;
import java.util.List;

import hello.MainController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.ManagedBean;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class MessageListener {

    private static final Logger logger = Logger.getLogger(MessageListener.class);
    public static final List<String> contents = new ArrayList<>();
    
    @KafkaListener(topics = "exampleTopic")
    public void listen(String data) {
        logger.info("New message received: \"" + data + "\"");
        contents.add(data);
    }
}
