package asw.participants.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.model.Citizen;
import asw.repositories.CitizenRepository;

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
	public List<Citizen> findCitizens() {
		return citizenRepository.findAll();
	}

	@Override
	public void saveCitizen(Citizen ciudadano) {
		citizenRepository.save(ciudadano);
	}

	@Override
	public Citizen findByEmailAndPassword(String email, String password) {
		return citizenRepository.findByEmailAndPassword(email, password);
	}
	
	@Override
	public void removeCitizen(Citizen ciudadano) {
		citizenRepository.delete(ciudadano);
	}
}
