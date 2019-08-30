package cn.appsys.dao.infolist;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_category;
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
	
}
