package asw.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asw.model.CitizenRepository;
import asw.model.Ciudadano;
import asw.model.ComentarioRepository;
import asw.model.RolCiudadano;
import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;
import asw.participants.information.errors.CitizenNotFoundError;
import asw.util.Encrypter;

@Controller
public class MainController {

    @Autowired
    private SugerenciaRepository sugerenciaRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private CitizenRepository repository;
    
    @RequestMapping("/")
    public String landing(Model model) {
    	model.addAttribute("sugerencias", sugerenciaRepository.findAll());
    	model.addAttribute("comentarios", comentarioRepository.findAll());
        return "index";
    }
    
    @RequestMapping("/sugerencias")
    public String vistaSugerencias(Model model) {
    	model.addAttribute("sugerencias", sugerenciaRepository.findAll());
    	model.addAttribute("comentarios", comentarioRepository.findAll());
        return "vistaSugerencias";
    }
    
    @RequestMapping("/graficas")
    public String vistaGraficas(Model model) {
        return "vistaGraficas";
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public String detalles(@PathVariable("id") String id, Model model) {
    	Long ident = Long.valueOf(id);
    	Sugerencia sugerencia = sugerenciaRepository.findOne(ident);
		model.addAttribute("id", id);
		model.addAttribute("detalles", sugerencia);
		model.addAttribute("comentarios", sugerencia.getComentarios());
		model.addAttribute("valoracion", sugerencia.getValoracion());
		return "detallesSugerencia";
    }
    
    @RequestMapping(value = "/validarse", method = RequestMethod.POST)
	public String postUserHtml(@RequestBody String parametros, Model model) {

		String[] parametro = parametros.split("&");

		String email = parametro[0].split("=")[1].replace("%40", "@");
		String contraseña = parametro[1].split("=")[1];

		String contraseñaEncriptada = Encrypter.getInstance().makeSHA1Hash(contraseña);
		Ciudadano user = repository.findByEmailAndPassword(email, contraseñaEncriptada);

		if (user != null) {

			model.addAttribute("email", user.getEmail());
			model.addAttribute("firstName", user.getNombre());
			model.addAttribute("lastName", user.getApellidos());
			model.addAttribute("nif", user.getDni());
			model.addAttribute("address", user.getResidencia());
			model.addAttribute("nationality", user.getNacionalidad());
			model.addAttribute("role", user.getRol());

			if (user.getRol() != RolCiudadano.PARTICIPANT) 
				return "redirect:/sugerencias";					

			return "datos";
		}
		else 
			throw new CitizenNotFoundError();
	}
}