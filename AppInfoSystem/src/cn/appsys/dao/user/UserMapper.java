package cn.appsys.dao.user;

import java.util.Random;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Backend_user;

public interface UserMapper {

	/**
	 * 登陆的方法
	 * @return
	 */
	public Backend_user Login(@Param("userCode") String userCode,@Param("userPassword") String userPassword);
	
}
