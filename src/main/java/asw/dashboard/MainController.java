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
    public String vistaSugerencias(Model model,
    		@ModelAttribute("user") final Citizen citizen) {
    	
    	if (citizen == null || citizen.getUser() == null)
    		return "redirect:/";
    	
    	model.addAttribute("sugerencias", sugerenciaRepository.findAll());
    	model.addAttribute("comentarios", comentarioRepository.findAll());
        return "vistaSugerencias";
    }
    
    @RequestMapping("/datos")
    public String vistaDatos(Model model,
    		@ModelAttribute("user") final Citizen citizen) {

    	if (citizen == null || citizen.getUser() == null)
    		return "redirect:/";
    	
		model.addAttribute("email", citizen.getEmail());
		model.addAttribute("firstName", citizen.getName());
		model.addAttribute("lastName", citizen.getSurname());
		model.addAttribute("nif", citizen.getDni());
		model.addAttribute("address", citizen.getResidence());
		model.addAttribute("nationality", citizen.getNationality());
		model.addAttribute("admin", citizen.getUser().isAdmin());
		
    	return "datos";
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
			
			redirectAttributes.addFlashAttribute("user", user);
			
			if (user.getUser().isAdmin())
				return "redirect:/sugerencias";
			
			return "redirect:/datos";
		} else { 
			//Ciudadano no encontrado
			redirectAttributes.addFlashAttribute("invalidUser", true);
			return "redirect:/";
		}
	}
}