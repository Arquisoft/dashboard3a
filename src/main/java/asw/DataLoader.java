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

import asw.model.Categoria;
import asw.model.CitizenRepository;
import asw.model.Ciudadano;
import asw.model.Comentario;
import asw.model.ComentarioRepository;
import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;
import asw.model.Usuario;
import asw.util.Encrypter;

@Component
@Transactional
public class DataLoader implements ApplicationRunner {

	private static final String DEFAULT_SUGGESTIONS_FILE_PATH = "/defaultSuggestions.txt";
	private static final String DEFAULT_COMMENTS_FILE_PATH = "/defaultComments.txt";
	
	@Autowired
	private SugerenciaRepository sugerenciaRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private CitizenRepository citizenRepository;
	
	@Autowired
	private JpaContext jpaContext;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		loadSugerencias(dateFormat);
		loadComentarios(dateFormat);
		
		String clave1 = Encrypter.getInstance().makeSHA1Hash("user1");
		Ciudadano ciud1 = new Ciudadano("user1", "user1", "user1@me.com", new Date(),
				"casa1", "esp", "12345678A", new Usuario("user1", clave1));
		
		String clave2 = Encrypter.getInstance().makeSHA1Hash("user2");
		Ciudadano ciud2 = new Ciudadano("user2", "user2", "user2@me.com", new Date(),
				"casa2", "esp", "22345678A", new Usuario("user2", clave2));
		
		String clave3 = Encrypter.getInstance().makeSHA1Hash("user3");
		Ciudadano ciud3 = new Ciudadano("user3", "user3", "user3@me.com", new Date(),
				"casa3", "esp", "32345678A", new Usuario("user3", clave3));
		
		citizenRepository.save(ciud1);
		citizenRepository.save(ciud2);
		citizenRepository.save(ciud3);
	}
	
	private void loadSugerencias(DateFormat dateFormat) throws NumberFormatException, IOException, ParseException {
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEFAULT_SUGGESTIONS_FILE_PATH)));
		
		while (bfReader.ready()) {
			String line = bfReader.readLine();
			String[] lineParts = line.split(";");
			
			String titulo = lineParts[0];
			String contenido = lineParts[1];
			Date fecha = dateFormat.parse(lineParts[2]);
			Usuario usuario = new Usuario(lineParts[3]);
			Categoria categoria = new Categoria(lineParts[4]);
			int votosPositivos = Integer.parseInt(lineParts[5]);
			int votosNegativos = Integer.parseInt(lineParts[6]);
			
			Sugerencia sugerencia = new Sugerencia(contenido, categoria, usuario);
			sugerencia.setTitulo(titulo);
			sugerencia.setFecha(fecha);
			sugerencia.setVotosPositivos(votosPositivos);
			sugerencia.setVotosNegativos(votosNegativos);
			
			sugerenciaRepository.save(sugerencia);
		}
	}
	
	private void loadComentarios(DateFormat format) throws NumberFormatException, IOException, ParseException {
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEFAULT_COMMENTS_FILE_PATH)));
		
		while (bfReader.ready()) {
			String line = bfReader.readLine();
			String[] lineParts = line.split(";");
			
			String contenido = lineParts[0];
			Usuario usuario = new Usuario(lineParts[2]);
			Date fecha = format.parse(lineParts[3]);

			loadComentario(contenido, lineParts[1], usuario, fecha);
		}
	}

	private void loadComentario(String contenido, String titSugerencia, Usuario usuario,
								Date fecha) {

		Sugerencia sugerencia = sugerenciaRepository.findByTitulo(titSugerencia);
		sugerencia = jpaContext.getEntityManagerByManagedType(sugerencia.getClass())
								.merge(sugerencia);
		Comentario comentario = new Comentario(contenido, sugerencia, usuario);
		comentario.setFecha(fecha);
		
		comentarioRepository.save(comentario);
	}
}
