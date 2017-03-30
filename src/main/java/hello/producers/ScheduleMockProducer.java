package hello.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMockProducer {

	@Autowired
	private MockKafkaProducer kafkaProducer;
	
    @Scheduled(fixedRate = 5000)
    public void trigger() {
    	kafkaProducer.send("exampleTopic");
    }
}