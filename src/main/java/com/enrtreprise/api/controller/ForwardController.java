package com.enrtreprise.api.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        // Renvoie toutes les routes vers Angular
        return "forward:/index.html";
    }
}
