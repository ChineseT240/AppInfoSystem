package cn.appsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@RequestMapping("/devlogin")
	public String Login(){
		System.out.println("进入登陆页面");
		return "devlogin";
		
	}
	
}
