package asw.model.dominio;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.model.Categoria;
import asw.model.Comentario;
import asw.model.Sugerencia;
import asw.model.Usuario;

public class CategoriaTest {

	private Usuario usuario1, usuario2, usuario3;
	private Categoria categoria1, categoria2, categoria3;
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

	}
	
	@Test
	public void testSugerencia() {
		assertTrue(sugerencia1.getCategoria().equals(categoria1));
		assertTrue(sugerencia2.getCategoria().equals(categoria2));
		assertTrue(sugerencia3.getCategoria().equals(categoria3));
	}

	@Test
	public void testSafeReturn() {
		Set<Sugerencia> sugerencias = categoria1.getSugerencias();
		sugerencias.clear();
		
		assertTrue(sugerencias.size() == 0);
		assertTrue(categoria1.getSugerencias().size() != 0);
	}
}
