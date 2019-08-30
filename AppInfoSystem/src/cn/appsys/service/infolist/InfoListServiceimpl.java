package cn.appsys.service.infolist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.infolist.InfoListMapper;
import cn.appsys.pojo.App_category;
import cn.appsys.pojo.Data_dictionary;

@Service("InfoListService")
public class InfoListServiceimpl implements InfoListService {

	@Autowired
	private InfoListMapper infolistMapper;
	
	@Override
	public List<Data_dictionary> SelectAppState() {
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infolistMapper.SelectAppState();
	}

	@Override
	public List<Data_dictionary> SelectPlatform() {
        try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infolistMapper.SelectPlatform();
	}

	@Override
	public List<App_category> SelectV1() {
          try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infolistMapper.SelectV1();
	}

	@Override
	public List<App_category> SelectV2(String pid) {
		 try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return infolistMapper.SelectV2(pid);
	}

	
}
