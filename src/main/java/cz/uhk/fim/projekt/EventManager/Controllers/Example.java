package cz.uhk.fim.projekt.EventManager.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class Example {


    @GetMapping()
    public String getExample(){
        return "example1";
    }
}
