package com.anyang.study.board.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	@RequestMapping(value = "/swagger", method = RequestMethod.GET)
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}
}
