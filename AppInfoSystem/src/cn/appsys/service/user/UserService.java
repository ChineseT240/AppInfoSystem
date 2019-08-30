package cn.appsys.service.user;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Backend_user;

public interface UserService {

	/**
	 * 登陆的方法
	 * @return
	 */
	public Backend_user Login(String userCode,String userPassword);
}
