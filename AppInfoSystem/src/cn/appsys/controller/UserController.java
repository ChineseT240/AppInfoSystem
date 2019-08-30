package cn.appsys.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.Backend_user;
import cn.appsys.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userservice;
	
	@RequestMapping("/devlogin")
	public String Login(){
		System.err.println("进入登陆页面");
		return "devlogin";
	}
	
	/**
	 * 登陆的方法
	 * @param devCode
	 * @param devPassword
	 * @param session
	 * @return
	 */
	@RequestMapping(value="JoinLogin",method=RequestMethod.POST)
	public String JoinLogin(@RequestParam(value = "devCode") String devCode,@RequestParam(value = "devPassword") String devPassword,HttpSession session){
		Backend_user user=null;
		System.err.println("devCode:"+devCode+"  devPassword"+devPassword);
		try {
			user=userservice.Login(devCode, devPassword);
			if (user==null) {
				session.setAttribute("error", "账号或密码错误");
				return "/devlogin";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("devUserSession", user);
		return "developer/main";
		
	}
	
	@RequestMapping(value="main")
	public String Main(){
		System.err.println("进入主界面");
		return "developer/main";
		
	}
	
	@RequestMapping(value="/logout")
	public String Logout(HttpSession session){
		System.err.println("注销");
		session.setAttribute("devUserSession", null);
		return "/devlogin";
	}
}
