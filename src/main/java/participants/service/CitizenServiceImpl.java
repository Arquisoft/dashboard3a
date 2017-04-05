package participants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.CitizenRepository;
import model.Ciudadano;

/**
 * Implementación de los servicios de 
 * gestión de persistencia de usuarios
 * 
 * @author UO246008
 *
 */
@Service
public class CitizenServiceImpl implements CitizenService {

	@Autowired
	private CitizenRepository citizenRepository;

	@Override
	public List<Ciudadano> findCitizens() {
		return citizenRepository.findAll();
	}

	@Override
	public void saveCitizen(Ciudadano ciudadano) {
		citizenRepository.save(ciudadano);
	}

	@Override
	public Ciudadano findByEmailAndPassword(String email, String password) {
		return citizenRepository.findByEmailAndPassword(email, password);
	}
	
	@Override
	public void removeCitizen(Ciudadano ciudadano) {
		citizenRepository.delete(ciudadano);
	}
}
