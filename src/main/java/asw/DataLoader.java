package asw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Component;

import asw.model.Category;
import asw.model.Citizen;
import asw.model.Comment;
import asw.model.Suggestion;
import asw.model.SuggestionVote;
import asw.model.User;
import asw.model.types.VoteStatus;
import asw.repositories.CategoryRepository;
import asw.repositories.CitizenRepository;
import asw.repositories.CommentRepository;
import asw.repositories.SuggestionRepository;
import asw.repositories.SuggestionVoteRepository;
import asw.repositories.UserRepository;
import asw.util.Encrypter;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

	private static final String DEFAULT_SUGGESTIONS_FILE_PATH = "/defaultSuggestions.txt";
	private static final String DEFAULT_COMMENTS_FILE_PATH = "/defaultComments.txt";

	@Autowired
	private SuggestionRepository sugerenciaRepository;

	@Autowired
	private CommentRepository comentarioRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SuggestionVoteRepository voteRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private JpaContext jpaContext;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		String clave1 = Encrypter.getInstance().makeSHA1Hash("user1");
		Citizen ciud1 = new Citizen("user1", "user1", "user1@me.com", new Date(), "casa1", "esp", "12345678A");

		User user1 = new User("user1", clave1, ciud1);
		user1.setAdmin(false);
		
		userRepository.save(user1);
		citizenRepository.save(ciud1);

		String clave2 = Encrypter.getInstance().makeSHA1Hash("user2");
		Citizen ciud2 = new Citizen("user2", "user2", "user2@me.com", new Date(), "casa2", "esp", "22345678A");

		User user2 = new User("user2", clave2, ciud2);
		user2.setAdmin(true);
		
		userRepository.save(user2);
		citizenRepository.save(ciud2);

		String clave3 = Encrypter.getInstance().makeSHA1Hash("user3");
		Citizen ciud3 = new Citizen("user3", "user3", "user3@me.com", new Date(), "casa3", "esp", "32345678A");

		User user3 = new User("user3", clave3, ciud3);
		user3.setAdmin(true);
		
		userRepository.save(user3);
		citizenRepository.save(ciud3);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		loadSugerencias(dateFormat);
		loadComentarios(dateFormat);
	}

	private void loadSugerencias(DateFormat dateFormat) throws NumberFormatException, IOException, ParseException {
		BufferedReader bfReader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(DEFAULT_SUGGESTIONS_FILE_PATH)));

		while (bfReader.ready()) {
			String line = bfReader.readLine();
			String[] lineParts = line.split(";");

			String titulo = lineParts[0];
			String contenido = lineParts[1];
			Date fecha = dateFormat.parse(lineParts[2]);
			Citizen citizen = new Citizen(lineParts[3], lineParts[3], lineParts[3] + "@me.com",
					new Date(), "Asturias", "ESP", "00000000A");
			User usuario = new User(lineParts[3], citizen);

			userRepository.save(usuario);
			citizenRepository.save(citizen);
			
			Category categoria = new Category(lineParts[4]);

			categoryRepository.save(categoria);

			int votosPositivos = Integer.parseInt(lineParts[5]);
			int votosNegativos = Integer.parseInt(lineParts[6]);

			Suggestion sugerencia = new Suggestion(contenido, categoria, usuario);
			sugerencia.setContents(titulo);
			sugerencia.setCreationDate(fecha);

			sugerencia = sugerenciaRepository.save(sugerencia);

			for (int i = 0; i < votosPositivos; i++)
				voteRepository.save(new SuggestionVote(sugerencia, usuario, VoteStatus.IN_FAVOUR));

			for (int i = 0; i < votosNegativos; i++)
				voteRepository.save(new SuggestionVote(sugerencia, usuario, VoteStatus.AGAINST));

		}
	}

	private void loadComentarios(DateFormat format) throws NumberFormatException, IOException, ParseException {
		BufferedReader bfReader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(DEFAULT_COMMENTS_FILE_PATH)));

		while (bfReader.ready()) {
			String line = bfReader.readLine();
			String[] lineParts = line.split(";");

			String contenido = lineParts[0];
			Citizen citizen = new Citizen(lineParts[2], lineParts[2], lineParts[2] + "@me.com",
					new Date(), "Asturias", "ESP", "00000000A");
			User usuario = new User(lineParts[2], citizen);

			userRepository.save(usuario);
			citizenRepository.save(citizen);
			
			Date fecha = format.parse(lineParts[3]);

			loadComentario(contenido, lineParts[1], usuario, fecha);
		}
	}

	private void loadComentario(String contenido, String contSugerencia, User usuario, Date fecha) {

		Suggestion sugerencia = sugerenciaRepository.findByContents(contSugerencia);
		sugerencia = jpaContext.getEntityManagerByManagedType(sugerencia.getClass()).merge(sugerencia);
		Comment comentario = new Comment(contenido, sugerencia, usuario);
		comentario.setCreationDate(fecha);

		comentarioRepository.save(comentario);
	}
}
