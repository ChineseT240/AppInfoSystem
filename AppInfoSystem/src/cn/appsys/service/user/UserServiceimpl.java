package cn.appsys.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.user.UserMapper;
import cn.appsys.pojo.Backend_user;

@Service("UserService")
public class UserServiceimpl implements UserService {

	@Autowired
	private UserMapper UserMapper;
	
	
	@Override
	public Backend_user Login(String userCode, String userPassword) {
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UserMapper.Login(userCode, userPassword);
	}

}
