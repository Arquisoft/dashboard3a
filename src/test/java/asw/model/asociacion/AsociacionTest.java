package asw.model.asociacion;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import asw.model.Association.Asignar;
import asw.model.Association.AsignarCategoria;
import asw.model.Association.AsignarComentario;
import asw.model.Association.AsignarSugerencia;
import asw.model.Categoria;
import asw.model.Ciudadano;
import asw.model.Comentario;
import asw.model.Sugerencia;
import asw.model.Usuario;

public class AsociacionTest {

	
	@Test
	public void testAsignar() {
		Ciudadano ciudadano = new Ciudadano("Fran", "Garcia", "e@email.com", new Date(), "Pamplona", "Española", "123456S");
		Usuario user = new Usuario("Paco", "ocap", ciudadano);
		
		assertTrue(ciudadano.getUsuario().equals(user));
		assertTrue(user.getCiudadano().equals(ciudadano));
		
		Asignar.unlink(user, ciudadano);
		
		assertTrue(ciudadano.getUsuario() == null);
		assertTrue(user.getCiudadano() == null);
	}
	
	@Test
	public void testAsignarSugerencia() {
		Usuario user = new Usuario("Paco");
		Categoria categoria = new Categoria("Categoria");
		Sugerencia sugerencia = new Sugerencia("Contenido sugerencia", categoria, user);
		
		assertTrue(user.getSugerencias().contains(sugerencia));
		assertTrue(sugerencia.getUsuario().equals(user));
		
		AsignarSugerencia.unlink(user, sugerencia);
		
		assertTrue(sugerencia.getUsuario() == null);
		assertEquals(user._getSugerencias().contains(sugerencia), false);
	}
	
	@Test
	public void testAsignarComentario() {
		Usuario user = new Usuario("Paco");
		Categoria categoria = new Categoria("Categoria");
		Sugerencia sugerencia = new Sugerencia("Contenido sugerencia", categoria, user);
		Comentario comentario = new Comentario("Mal", sugerencia, user);
		
		assertTrue(user.getComentarios().contains(comentario));
		assertTrue(sugerencia.getComentarios().contains(comentario));
		assertTrue(comentario.getSugerencia().equals(sugerencia));
		assertTrue(comentario.getUsuario().equals(user));
		
		AsignarComentario.unlink(comentario, sugerencia, user);
		
		assertTrue(user._getComentarios().contains(comentario));
		assertTrue(sugerencia._getComentarios().contains(comentario));
		assertTrue(comentario.getSugerencia() == null);
		assertTrue(comentario.getUsuario() == null);
	}
	
	@Test
	public void testAsignarCategoria() {
		Usuario user = new Usuario("Paco");
		Categoria categoria = new Categoria("Categoria");
		Sugerencia sugerencia = new Sugerencia("Contenido sugerencia", categoria, user);
		
		assertTrue(categoria.getSugerencias().contains(sugerencia));
		assertTrue(sugerencia.getCategoria().equals(categoria));
		
		AsignarCategoria.unlink(categoria, sugerencia);
		
		assertTrue(categoria._getSugerencias().contains(sugerencia));
		assertTrue(sugerencia.getCategoria() == null);
	}


}
