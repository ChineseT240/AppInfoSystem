package cn.appsys.dao.infolist;

import java.math.BigDecimal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_category;
import cn.appsys.pojo.App_info;
import cn.appsys.pojo.Data_dictionary;

public interface InfoListMapper {

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
	public List<App_category> SelectV2(@Param("pid") String pid);
	
	/**
	 * 查询分页列表
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param size
	 * @param pageSize
	 * @return
	 */
	public List<App_info> Select(@Param("softwareName") String softwareName,@Param("status") String status,@Param("flatformId") String flatformId,@Param("categoryLevel1") String categoryLevel1,@Param("categoryLevel2") String categoryLevel2,@Param("categoryLevel3") String categoryLevel3,@Param("size") int size,@Param("pageSize") int pageSize);
	
	/**
	 * 查询总记录数
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @return
	 */
	public int Count(@Param("softwareName") String softwareName,@Param("status") String status,@Param("flatformId") String flatformId,@Param("categoryLevel1") String categoryLevel1,@Param("categoryLevel2") String categoryLevel2,@Param("categoryLevel3") String categoryLevel3);
	
	/**
	 * 查询是否有APKName
	 * @param APKName
	 * @return
	 */
	public List<App_info> SelectAPK(@Param("APKName") String APKName);
	
	public int Add(@Param("softwareName") String softwareName,@Param("APKName") String APKName,@Param("supportROM") String supportROM ,@Param("interfaceLanguage") String interfaceLanguage,@Param("softwareSize") BigDecimal softwareSize,@Param("downloads") Integer downloads,@Param("flatformId") Integer flatformId,@Param("categoryLevel1") Integer categoryLevel1,@Param("categoryLevel2") Integer categoryLevel2,@Param("categoryLevel3") Integer categoryLevel3,@Param("appInfo") String appInfo,@Param("logoPicPath") String logoPicPath,@Param("logoLocPath") String logoLocPath);
	
	public App_info UpDateShow(@Param("id") String id,@Param("versionId") String versionId);
	
	public int Updata(App_info app_info);
}
