package asw.model.dominio;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.model.Categoria;
import asw.model.Comentario;
import asw.model.Sugerencia;
import asw.model.Usuario;

public class SugerenciaTest {

	private Usuario usuario1, usuario2, usuario3;
	private Categoria categoria1, categoria2, categoria3;
	private Comentario comentario1, comentario2, comentario3, comentario4, comentario5, comentario6;
	private Sugerencia sugerencia1, sugerencia2, sugerencia3;
	
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
		
		comentario1 = new Comentario("Mal", sugerencia1, usuario3);
		comentario4 = new Comentario("Horrible", sugerencia1, usuario2);
		
		comentario2 = new Comentario("Bien", sugerencia2, usuario1);
		comentario3 = new Comentario("Regular", sugerencia2, usuario3);
		comentario5 = new Comentario("Nefasto", sugerencia2, usuario1);
		
		comentario6 = new Comentario("Maravilloso", sugerencia3, usuario2);
	}
	
	@Test
	public void testTituloYContenido() {
		assertTrue(sugerencia1.getTitulo().equals("Titulo sugerencia 1"));
		assertTrue(sugerencia1.getContenido().equals("Contenido sugerencia 1"));
		
		assertTrue(sugerencia2.getTitulo().equals("Titulo sugerencia 2"));
		assertTrue(sugerencia2.getContenido().equals("Contenido sugerencia 2"));
		
		assertTrue(sugerencia3.getTitulo().equals("Titulo sugerencia 3"));
		assertTrue(sugerencia3.getContenido().equals("Contenido sugerencia 3"));
	}
	
	@Test
	public void testNumeroVotos() {
		assertTrue(sugerencia1.getVotosPositivos() == 3);
		assertTrue(sugerencia1.getVotosNegativos() == 4);
		assertTrue(sugerencia1.getVotosTotales() == 7);
		assertTrue(sugerencia1.getValoracion() == (3/7d));
		
		assertTrue(sugerencia2.getVotosPositivos() == 6);
		assertTrue(sugerencia2.getVotosNegativos() == 2);
		assertTrue(sugerencia2.getVotosTotales() == 8);

		assertTrue(sugerencia3.getVotosPositivos() == 6);
		assertTrue(sugerencia3.getVotosNegativos() == 10);
		assertTrue(sugerencia3.getVotosTotales() == 16);
		assertTrue(sugerencia3.getValoracion() == (6/16d));
	}
	
	@Test
	public void testNumeroComentarios() {
		assertTrue(sugerencia1.getNumComentarios() == 2);
		
		assertTrue(sugerencia2.getNumComentarios() == 3);
		
		assertTrue(sugerencia3.getNumComentarios() == 1);
	}
	
	@Test
	public void testCategoria() {
		assertTrue(sugerencia1.getCategoria().getNombre().equals("Categoria 1"));
		
		assertTrue(sugerencia2.getCategoria().getNombre().equals("Categoria 2"));
		
		assertTrue(sugerencia3.getCategoria().getNombre().equals("Categoria 3"));
	}

	@Test
	public void testComentarios() {
		assertTrue(sugerencia1.getComentarios().size() == 2);
		assertTrue(sugerencia1.getComentarios().contains(comentario1));
		assertTrue(sugerencia1.getComentarios().contains(comentario4));
		
		assertTrue(sugerencia2.getComentarios().size() == 3);
		assertTrue(sugerencia2.getComentarios().contains(comentario2));
		assertTrue(sugerencia2.getComentarios().contains(comentario3));
		assertTrue(sugerencia2.getComentarios().contains(comentario5));
		
		assertTrue(sugerencia3.getComentarios().size() == 1);
		assertTrue(sugerencia3.getComentarios().contains(comentario6));
	}
	
	@Test
	public void testSafeReturn() {
		Set<Comentario> comentarios = sugerencia1.getComentarios();
		comentarios.clear();
		
		assertTrue(comentarios.size() == 0);
		assertTrue(sugerencia1.getComentarios().size() != 0);
	}
	
	@Test
	public void testEquals() {
		Sugerencia sugerencia = new Sugerencia("Contenido sugerencia 1", categoria1, usuario1);
		sugerencia.setTitulo("Titulo sugerencia 1");
		for(int i = 0; i < 4; i++)
			sugerencia.addVotoNegativo();
		for(int i = 0; i < 3; i++)
			sugerencia.addVotoPositivo();
		
		assertTrue(sugerencia.equals(sugerencia1));
	}
	
	@Test
	public void testToString() {
		Sugerencia sugerencia = new Sugerencia("Contenido sugerencia 1", categoria1, usuario1);
		sugerencia.setTitulo("Titulo sugerencia 1");
		for(int i = 0; i < 4; i++)
			sugerencia.addVotoNegativo();
		for(int i = 0; i < 3; i++)
			sugerencia.addVotoPositivo();
		
		assertEquals(sugerencia.toString(), sugerencia1.toString());
	}
	
	@Test
	public void testContenido() {
		Sugerencia sugerencia = new Sugerencia("Contenido sugerencia", categoria1, usuario1);
		assertTrue(sugerencia.getContenido().equals("Contenido sugerencia"));
		
		sugerencia.setContenido("Ahora cambio el contenido");
		assertTrue(sugerencia.getContenido().equals("Ahora cambio el contenido"));
	}
	
	@Test
	public void testSetComentarios() {
		assertTrue(sugerencia1.getComentarios().size() == 2);
		
		Set<Comentario> comentarios = new HashSet<>();
		sugerencia1.setComentarios(comentarios);
		assertTrue(sugerencia1.getComentarios().size() == 0);
	}
}
