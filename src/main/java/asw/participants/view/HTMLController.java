package asw.participants.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.util.Encrypter;
import asw.model.Citizen;
import asw.repositories.CitizenRepository;
import asw.participants.information.errors.CitizenNotFoundError;
import asw.participants.information.errors.ErrorInterface;

/**
 * Representa la información que irá en el JSON cuando se 
 * envíen sus datos al usuario
 * 
 * @author UO237394 (base principal), UO247242 (gestión de cuando la información no se encuentra
 *  y de la encrioptacion de contraseña)
 * 
 */
@Controller
public class HTMLController {

	@Autowired
	private CitizenRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getLoginHtml(Model model) {
		return "login";
	}

	@RequestMapping(value = "/validarsepart", method = RequestMethod.POST)
	public String postUserHtml(@RequestBody String parametros, Model model) {

		String[] parametro = parametros.split("&");

		String email = parametro[0].split("=")[1].replace("%40", "@");
		String contraseña = parametro[1].split("=")[1];

		String contraseñaEncriptada = Encrypter.getInstance().makeSHA1Hash(contraseña);
		Citizen user = repository.findByEmailAndPassword(email, contraseñaEncriptada);

		if (user != null) {

			model.addAttribute("email", user.getEmail());
			model.addAttribute("firstName", user.getName());
			model.addAttribute("lastName", user.getSurname());
			model.addAttribute("nif", user.getDni());
			model.addAttribute("address", user.getResidence());
			model.addAttribute("nationality", user.getNationality());
			model.addAttribute("admin", user.getUser().isAdmin());

			if (user.getUser().isAdmin())
				return "vistaSugerencias";
			
			return "datos";
		}
		else 
			throw new CitizenNotFoundError();
	}

	@ExceptionHandler(ErrorInterface.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorInterface excep, Model model) {
		model.addAttribute("error", excep.getStringError());

		return "error";
	}
}