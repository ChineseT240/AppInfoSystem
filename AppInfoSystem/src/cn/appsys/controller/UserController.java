package cn.appsys.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@RequestMapping("/devlogin")
	public String Login(){
		System.out.println("进入登陆页面");
		return "devlogin";
	}
	
	@RequestMapping("/JoinLogin")
	public String JoinLogin(@RequestParam(value = "devCode") String devCode,@RequestParam(value = "devPassword") String devPassword,HttpSession session){
		System.err.println("devCode:"+devCode+"  ");
		return "backendlogin";
		
	}
	
}
