package asw.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interfaz para trabajar con la base de datos de usuarios
 * (CRUD)
 * 
 * @author UO246008
 *
 */
public interface CitizenRepository extends JpaRepository<Ciudadano, Long> {
	/**
	 * Busca un usuario dados su login y contraseña
	 * 
	 * @param login login del usuario
	 * @param password contraseña
	 * @return usuario con ese login y contraseña
	*/
	 @Query("select c from Ciudadano c where c.email = ?1 and c.usuario.contraseña = ?2")
	 Ciudadano findByEmailAndPassword(String email, String password);
}
