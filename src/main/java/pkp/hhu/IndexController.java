package pkp.hhu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/hhu")
    public String index(){
        return "index";
    }
    @GetMapping("/after-login")
    public String afterLogin(){
        return "after-login";
    }
}
