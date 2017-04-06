package asw.model.dominio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asw.model.Categoria;
import asw.model.Comentario;
import asw.model.Sugerencia;
import asw.model.Usuario;

public class ComentarioTest {

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
	public void testContenido() {
		assertTrue(comentario1.getContenido().equals("Mal"));
		assertTrue(comentario2.getContenido().equals("Bien"));
		assertTrue(comentario3.getContenido().equals("Regular"));
		assertTrue(comentario4.getContenido().equals("Horrible"));
		assertTrue(comentario5.getContenido().equals("Nefasto"));
		assertTrue(comentario6.getContenido().equals("Maravilloso"));
	}
	
	@Test
	public void testSugerencia() {
		assertTrue(comentario1.getSugerencia().equals(sugerencia1));
		assertTrue(comentario4.getSugerencia().equals(sugerencia1));
		
		assertTrue(comentario2.getSugerencia().equals(sugerencia2));
		assertTrue(comentario3.getSugerencia().equals(sugerencia2));
		assertTrue(comentario5.getSugerencia().equals(sugerencia2));
		
		assertTrue(comentario6.getSugerencia().equals(sugerencia3));
	}
	
	@Test
	public void testEquals() {
		Comentario comentario = new Comentario("Mal", sugerencia1, usuario3);
		
		assertTrue(comentario.equals(comentario1));
	}
	
	@Test
	public void testToString() {
		Comentario comentario = new Comentario("Mal", sugerencia1, usuario3);

		assertEquals(comentario1.toString(), comentario.toString());
	}

}
