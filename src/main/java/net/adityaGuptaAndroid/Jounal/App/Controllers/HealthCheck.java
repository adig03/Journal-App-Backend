package net.adityaGuptaAndroid.Jounal.App.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("Check")
    String healthCheck(){
        return "ok";
    }
}
