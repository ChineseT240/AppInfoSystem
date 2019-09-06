package cn.appsys.service.infolist;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_category;
import cn.appsys.pojo.App_info;
import cn.appsys.pojo.Data_dictionary;

public interface InfoListService {

	/**
	 * 获取app状态信息
	 * @return
	 */
	public List<Data_dictionary> SelectAppState();
	
	/**
	 * 所属平台
	 * @return
	 */
	public List<Data_dictionary> SelectPlatform();
	
	/**
	 * 查询一级菜单
	 * @return
	 */
	public List<App_category> SelectV1();
	
	/**
	 * 查询二级菜单,三级菜单
	 * @param pid
	 * @return
	 */
	public List<App_category> SelectV2(String pid);
	
	/**
	 * 分页查询所有数据
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param frm
	 * @param pageSize
	 * @return
	 */
	public List<App_info> Select(String softwareName, String status, String flatformId, String categoryLevel1, String categoryLevel2, String categoryLevel3, int frm, int pageSize);
	
	public int Add(String softwareName,String APKName,String supportROM ,String interfaceLanguage,BigDecimal softwareSize,Integer downloads, Integer flatformId,Integer categoryLevel1,Integer categoryLevel2,Integer categoryLevel3, String appInfo, String logoPicPath,String logoLocPath);
	
	
	public int Count(String softwareName,String status, String flatformId,String categoryLevel1,String categoryLevel2,String categoryLevel3);
	
	public List<App_info> SelectAPK(String APKName);

	public App_info UpDateShow(String id,String versionId);
	
	public int Updata(App_info app_info);
}
