package asw.dashboard.producers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import asw.model.Comment;
import asw.model.Suggestion;
import asw.model.SuggestionVote;
import asw.model.types.VoteStatus;
import asw.repositories.SuggestionRepository;
import asw.repositories.SuggestionVoteRepository;

@Service
public class ScheduleMockProducer {

	@Autowired
	private MockKafkaProducer kafkaProducer;

	@Autowired
	private SuggestionRepository sugerenciaRepository;

	@Autowired
	private SuggestionVoteRepository voteRepository;

	@Scheduled(fixedRate = 5000)
	public void trigger() {
		kafkaProducer.sendSugerencia();
		//kafkaProducer.sendComentario();

		List<Suggestion> sugerencias = sugerenciaRepository.findAll();

		if (!sugerencias.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				int index = (int) (Math.random() * sugerencias.size());
				Suggestion sugerencia = saveSugerencia(sugerencias.get(index));
				
				kafkaProducer.sendMessageJSON("sugerencias", sugerencia);

				Comment comentario = new Comment("contenido" + System.currentTimeMillis(),
						sugerencia, sugerencia.getUser());
				kafkaProducer.sendMessageJSON("comentarios", comentario);
			}
		}
	}
	
	private Suggestion saveSugerencia(Suggestion sugerencia) {
		sugerencia = sugerenciaRepository.findByContents(sugerencia.getContents());

		SuggestionVote suggestionVote;
		
		if (Math.round(Math.random()) == 1)
			suggestionVote = new SuggestionVote(sugerencia, sugerencia.getUser(), 
					VoteStatus.IN_FAVOUR);
		else
			suggestionVote = new SuggestionVote(sugerencia, sugerencia.getUser(),
					VoteStatus.AGAINST);

		//voteRepository.save(suggestionVote);
		sugerencia.getVotes().add(suggestionVote);
		
		return sugerencia;
	}
}