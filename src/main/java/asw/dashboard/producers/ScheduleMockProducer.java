package asw.dashboard.producers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;

@Service
public class ScheduleMockProducer {

	@Autowired
	private MockKafkaProducer kafkaProducer;

	@Autowired
	private SugerenciaRepository sugerenciaRepository;

	@Scheduled(fixedRate = 5000)
	public void trigger() {
		kafkaProducer.sendSugerencia();
		kafkaProducer.sendComentario();

		List<Sugerencia> sugerencias = sugerenciaRepository.findAll();

		if (!sugerencias.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				int index = (int) (Math.random() * sugerencias.size());
				Sugerencia sugerencia = sugerencias.get(index);

				if (Math.round(Math.random()) == 1)
					sugerencia.addVotoPositivo();
				else
					sugerencia.addVotoNegativo();

				kafkaProducer.sendMessageJSON("sugerencias", sugerencia);
			}
		}
	}
}