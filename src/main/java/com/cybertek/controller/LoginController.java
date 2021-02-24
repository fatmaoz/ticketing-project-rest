package com.cybertek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = {"/login","/"})//SecurityConfig de logout olurken /logine yonlendiriyoruz o yuzden burada onu da ekledik
	public String login(){
		return "login";
	}
	
	@RequestMapping("/welcome")
	public String welcome(){
		return "welcome";
	}

}
