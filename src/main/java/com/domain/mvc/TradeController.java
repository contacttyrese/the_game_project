package com.domain.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TradeController {
	
	@RequestMapping (value = "/trades", method = RequestMethod.GET)
	public String goToTradesPage(){
		return "trades";
	}

}
