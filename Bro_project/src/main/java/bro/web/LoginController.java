package bro.web;

import bro.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/main")
@SessionAttributes("member")
public class LoginController {
    public String loginForm(Model model){
        List<Member> members = Arrays.asList(
                new Member("lee", "11111111"),
                new Member("min", "11111111")
        );
        model.addAttribute("member",new Member());
        return "main";
    }
}
