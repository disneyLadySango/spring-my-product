package jp.co.disneyladysango.product.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/test")
public class HelloWorldController {
    @RequestMapping("/hello")
    @ResponseBody
    public String index(Model model){
        System.out.println("kていない");
        return "hello";
    }
}
