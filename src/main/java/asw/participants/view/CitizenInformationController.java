package asw.participants.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.util.Encrypter;
import asw.participants.information.citizen.CitizenInformationRequest;
import asw.participants.information.citizen.CitizenInformationResponse;
import asw.model.Ciudadano;
import asw.participants.service.CitizenService;
import asw.participants.information.errors.*;

/**
 * Representa la información que irá en el JSON cuando se 
 * envíen sus datos al usuario
 * 
 * @author UO247242
 * 
 */
@RestController
public class CitizenInformationController {
	
	@Autowired 
	CitizenService citizenService;

	/**
	 * Respuesta HTTP. Incluye información sobre el ciudadano
	 * si existe, sino devuelve error 404 
	 * 
	 * @author UO247242
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},	
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})	
	public ResponseEntity<CitizenInformationResponse> retrieveCitizenInformation(@RequestBody CitizenInformationRequest form) {	
		String email = form.getLogin();
		String password = form.getPassword();		
		String encryptedPassword = Encrypter.getInstance().makeSHA1Hash(password);

		Ciudadano ciudadano = citizenService.findByEmailAndPassword(email, encryptedPassword);		
		
		if (ciudadano != null) {
			
			CitizenInformationResponse citizen = new CitizenInformationResponse(ciudadano);

		return ResponseEntity.ok(citizen);
		}
	
		else throw new CitizenNotFoundError();		
	}	
	
	@ExceptionHandler(ErrorInterface.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorInterface error) {
		return error.getJSONError();
	}
}
