package asw.participants.service;

import java.util.List;

import asw.model.Citizen;

/**
 * Punto de entrada a los servicios de 
 * persistencia de los usuarios
 * 
 * @author UO246008
 *
 */
public interface CitizenService {
	/**
	 * Consulta todos los usuarios registrados
	 * en el sistema
	 * 
	 * @return usuarios del sistema
	 */
	List<Citizen> findCitizens();
	/**
	 * Registra un usuario en el sistema
	 * 
	 * @param user usuario a registrar
	 */

	void saveCitizen(Citizen ciudadano);

	/**
	 * Busca un usuario dado su email y contraseña
	 * 
	 * @param email email del usuario
	 * @param password contraseña del usuario (cifrada)
	 * @return usuario con esos datos
	 */
	Citizen findByEmailAndPassword(String email, String password);
	
	/**
	 * Elimina un ciudadano de la base de datos
	 * 
	 * @param ciudadano ciudadano a eliminar
	 */
	void removeCitizen(Citizen ciudadano);
}
