package projet.cardlibrary_amrane_vallipuram.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeWebService{

    @GetMapping("/")
    public String home() {
        return "CardLibrary API is running.";
    }
}
