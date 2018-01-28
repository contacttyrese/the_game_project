package com.domain.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OfferController {
	
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public String offerPage(){
		return "offers";
	}

}
