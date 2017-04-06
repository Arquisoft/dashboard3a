package asw.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import asw.model.asociacion.AsociacionTest;
import asw.model.dominio.CategoriaTest;
import asw.model.dominio.ComentarioTest;
import asw.model.dominio.SugerenciaTest;
import asw.model.dominio.UsuarioTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	CategoriaTest.class,
	ComentarioTest.class,
	SugerenciaTest.class,
	UsuarioTest.class,
	AsociacionTest.class
})
public class AllTests {

}
