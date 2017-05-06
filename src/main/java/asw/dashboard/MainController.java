package asw.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import asw.model.Citizen;
import asw.model.Suggestion;
import asw.repositories.CitizenRepository;
import asw.repositories.CommentRepository;
import asw.repositories.SuggestionRepository;
import asw.util.Encrypter;

@Controller
public class MainController {

    @Autowired
    private SuggestionRepository sugerenciaRepository;
    
    @Autowired
    private CommentRepository comentarioRepository;
    
    @Autowired
    private CitizenRepository repository;
    
    @RequestMapping("/")
    public String landing(Model model, 
    		@ModelAttribute("invalidUser") final boolean invalidUser) {
    	model.addAttribute("invalidUser", invalidUser);
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
    
    @RequestMapping(path = "/sugerencias/{id}", method = RequestMethod.GET)
	public String detalles(@PathVariable("id") String id, Model model) {
    	Long ident = Long.valueOf(id);
    	Suggestion sugerencia = sugerenciaRepository.findOne(ident);
		model.addAttribute("id", id);
		model.addAttribute("detalles", sugerencia);
		model.addAttribute("comentarios", sugerencia.getComments());
		model.addAttribute("valoracion", sugerencia.getRating());
		return "detallesSugerencia";
    }
    
    @RequestMapping(value = "/validarse", method = RequestMethod.POST)
	public String postUserHtml(@RequestBody String parametros, Model model,
			RedirectAttributes redirectAttributes) {

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
				return "redirect:/sugerencias";
			
			return "datos";
		} else { 
			//Ciudadano no encontrado
			redirectAttributes.addFlashAttribute("invalidUser", true);
			return "redirect:/";
		}
	}
}