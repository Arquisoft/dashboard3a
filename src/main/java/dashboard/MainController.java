package dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dashboard.model.ComentarioRepository;
import dashboard.model.SugerenciaRepository;

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
}