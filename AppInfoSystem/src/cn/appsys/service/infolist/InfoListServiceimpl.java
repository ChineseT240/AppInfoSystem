package cn.appsys.service.infolist;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.infolist.InfoListMapper;
import cn.appsys.pojo.App_category;
import cn.appsys.pojo.App_info;
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

	@Override
	public List<App_info> Select(String softwareName, String status, String flatformId, String categoryLevel1,
			String categoryLevel2, String categoryLevel3, int size, int pageSize) {
		 try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		 System.err.println("size:"+size);
			return infolistMapper.Select(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, size, pageSize);
	}

	@Override
	public int Count(String softwareName, String status, String flatformId, String categoryLevel1,
			String categoryLevel2, String categoryLevel3) {
		 try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return infolistMapper.Count(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
	}

	@Override
	public List<App_info> SelectAPK(String APKName) {
		 try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return infolistMapper.SelectAPK(APKName);
	}

	@Override
	public int Add(String softwareName, String APKName, String supportROM, String interfaceLanguage,
			BigDecimal softwareSize, Integer downloads, Integer flatformId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3, String appInfo, String logoPicPath,String logoLocPath) {
		// TODO Auto-generated method stub
		return infolistMapper.Add(softwareName, APKName, supportROM, interfaceLanguage, softwareSize, downloads, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, appInfo,logoPicPath,logoLocPath);
	}

	@Override
	public App_info UpDateShow(String id, String versionId) {
		// TODO Auto-generated method stub
		return infolistMapper.UpDateShow(id, versionId);
	}

	@Override
	public int Updata(App_info app_info) {
		// TODO Auto-generated method stub
		return infolistMapper.Updata(app_info);
	}

	

	
}
