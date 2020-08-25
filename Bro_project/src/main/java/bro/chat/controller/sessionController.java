package bro.chat.controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class sessionController {
    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }
}