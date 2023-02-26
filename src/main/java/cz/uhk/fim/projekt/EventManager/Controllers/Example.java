package cz.uhk.fim.projekt.EventManager.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Example {


    @GetMapping(value = "/")
    public String getExample(){
        return "example1";
    }
}
