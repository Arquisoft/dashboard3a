package asw.model.dominio;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.model.Categoria;
import asw.model.Comentario;
import asw.model.Sugerencia;
import asw.model.Usuario;

public class UsuarioTest {

	private Usuario usuario1, usuario2, usuario3;
	private Categoria categoria1, categoria2, categoria3;
	private Comentario comentario1, comentario2, comentario3, comentario4, comentario5, comentario6;
	private Sugerencia sugerencia1, sugerencia2, sugerencia3, sugerencia4;
	
	@Before
	public void setUp() {
		
		usuario1 = new Usuario("Pepe");
		usuario2 = new Usuario("Jose");
		usuario3 = new Usuario("Paco");
		
		categoria1 = new Categoria("Categoria 1");
		categoria2 = new Categoria("Categoria 2");
		categoria3 = new Categoria("Categoria 3");
		
		sugerencia1 = new Sugerencia("Contenido sugerencia 1", categoria1, usuario1);
		sugerencia1.setTitulo("Titulo sugerencia 1");
		for(int i = 0; i < 4; i++)
			sugerencia1.addVotoNegativo();
		for(int i = 0; i < 3; i++)
			sugerencia1.addVotoPositivo();
		
		sugerencia2 = new Sugerencia("Contenido sugerencia 2", categoria2, usuario2);
		sugerencia2.setTitulo("Titulo sugerencia 2");
		for(int i = 0; i < 2; i++)
			sugerencia2.addVotoNegativo();
		for(int i = 0; i < 6; i++)
			sugerencia2.addVotoPositivo();
		
		sugerencia3 = new Sugerencia("Contenido sugerencia 3", categoria3, usuario3);
		sugerencia3.setTitulo("Titulo sugerencia 3");
		for(int i = 0; i < 10; i++)
			sugerencia3.addVotoNegativo();
		for(int i = 0; i < 6; i++)
			sugerencia3.addVotoPositivo();
		
		sugerencia4 = new Sugerencia("Contenido sugerencia 4", categoria2, usuario1);
		sugerencia4.setTitulo("Titulo sugerencia 4");
		for(int i = 0; i < 10; i++)
			sugerencia4.addVotoNegativo();
		for(int i = 0; i < 20; i++)
			sugerencia4.addVotoPositivo();
		
		comentario1 = new Comentario("Mal", sugerencia1, usuario3);
		comentario4 = new Comentario("Horrible", sugerencia1, usuario2);
		
		comentario2 = new Comentario("Bien", sugerencia2, usuario1);
		comentario3 = new Comentario("Regular", sugerencia2, usuario3);
		comentario5 = new Comentario("Nefasto", sugerencia2, usuario1);
		
		comentario6 = new Comentario("Maravilloso", sugerencia3, usuario2);
	}
	
	@Test
	public void testNombre() {
		assertTrue(usuario1.getUsuario().equals("Pepe"));
		assertTrue(usuario2.getUsuario().equals("Jose"));
		assertTrue(usuario3.getUsuario().equals("Paco"));
	}
	
	@Test
	public void testComentarios() {
		assertTrue(usuario1.getComentarios().size() == 2);
		assertTrue(usuario1.getComentarios().contains(comentario2));
		assertTrue(usuario1.getComentarios().contains(comentario5));
		
		assertTrue(usuario2.getComentarios().size() == 2);
		assertTrue(usuario2.getComentarios().contains(comentario4));
		assertTrue(usuario2.getComentarios().contains(comentario6));
		
		assertTrue(usuario3.getComentarios().size() == 2);
		assertTrue(usuario3.getComentarios().contains(comentario1));
		assertTrue(usuario3.getComentarios().contains(comentario3));
	}
	
	@Test
	public void testSugerencia() {
		assertTrue(usuario1.getSugerencias().size() == 2);
		assertTrue(usuario1.getSugerencias().contains(sugerencia1));
		assertTrue(usuario1.getSugerencias().contains(sugerencia4));
		
		assertTrue(usuario2.getSugerencias().size() == 1);
		assertTrue(usuario2.getSugerencias().contains(sugerencia2));
		
		assertTrue(usuario3.getSugerencias().size() == 1);
		assertTrue(usuario3.getSugerencias().contains(sugerencia3));
	}
	
	@Test
	public void testSugerenciaMejorValorada() {
		assertTrue(usuario1.getSugerenciaMejorValorada().equals(sugerencia4));
		
		assertTrue(usuario2.getSugerenciaMejorValorada().equals(sugerencia2));
		
		assertTrue(usuario3.getSugerenciaMejorValorada().equals(sugerencia3));
	}
	
	@Test
	public void testMediaVotosPositivos() {
		assertTrue(usuario1.getMediaVotosPositivosTodasSugerencias() == ((sugerencia1.getVotosPositivos() + sugerencia4.getVotosPositivos())/ (sugerencia1.getVotosTotales() + sugerencia4.getVotosTotales())) );
	}
	
	@Test
	public void testSafeReturn() {
		Set<Comentario> comentarios = usuario1.getComentarios();
		comentarios.clear();
		
		assertTrue(comentarios.size() == 0);
		assertTrue(usuario1.getComentarios().size() == 2);
		
		Set<Sugerencia> sugerencias = usuario1.getSugerencias();
		sugerencias.clear();
		
		assertTrue(sugerencias.size() == 0);
		assertTrue(usuario1.getSugerencias().size() == 2);
	}
	

}
