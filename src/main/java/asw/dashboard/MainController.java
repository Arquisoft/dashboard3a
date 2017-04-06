package asw.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asw.model.ComentarioRepository;
import asw.model.Sugerencia;
import asw.model.SugerenciaRepository;

@Controller
public class MainController {

    @Autowired
    private SugerenciaRepository sugerenciaRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
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
}