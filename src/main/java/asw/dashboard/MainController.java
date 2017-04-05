package asw.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import asw.model.ComentarioRepository;
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
}