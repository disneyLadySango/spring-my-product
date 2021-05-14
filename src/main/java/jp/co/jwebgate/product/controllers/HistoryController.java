package jp.co.jwebgate.product.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/history")
public class HistoryController {


	@RequestMapping(value = "" , method = RequestMethod.GET)
	public String history(Model model){

		return "history/history";
	}

}
