package hello;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import hello.listeners.MessageListener;
import hello.producers.MockKafkaProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);
    private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private MockKafkaProducer kafkaProducer;
    
    @RequestMapping("/")
    public String landing(Model model) {
    	model.addAttribute("contents", MessageListener.contents);
        return "index";
    }

    @RequestMapping("/send")
    public String send(Model model) {
        kafkaProducer.send("exampleTopic");
        return "redirect:/";
    }
}