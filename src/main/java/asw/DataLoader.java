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
import asw.model.Comentario;
import asw.model.ComentarioRepository;
import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;
import asw.model.Usuario;

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
	private JpaContext jpaContext;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		loadSugerencias(dateFormat);
		loadComentarios(dateFormat);
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
