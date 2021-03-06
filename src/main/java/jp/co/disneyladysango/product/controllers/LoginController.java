package jp.co.disneyladysango.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.disneyladysango.product.services.TopService;

/**
 * ログイン用のコントローラ
 *
 * @author y-wayama
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	TopService topService;

	@RequestMapping(value = "" , method = RequestMethod.GET)
	public String login(Model model){
		System.out.println("kていない");
		return "login";
	}

	@RequestMapping(value = "/complete" , method = RequestMethod.GET)
	public String loginForm(){
		return "login/complete";
	}

}
