package cn.appsys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.App_category;
import cn.appsys.pojo.Data_dictionary;
import cn.appsys.service.infolist.InfoListService;

@Controller
@RequestMapping("/infolist")
public class InfoListController {

	@Autowired
	private InfoListService iService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public Object List(Model model){
		System.err.println("显示列表方法");
		List<Data_dictionary> statusList=iService.SelectAppState();     //app状态
		List<Data_dictionary> flatFormList=iService.SelectPlatform();     //所属平台
		List<App_category> categoryLevel1List=iService.SelectV1();     //一级菜单
		
		
		model.addAttribute("statusList", statusList);                    //app状态
		model.addAttribute("flatFormList", flatFormList);                 //所属平台
		model.addAttribute("categoryLevel1List", categoryLevel1List);     //一级菜单
		return "developer/appinfolist";
		
	}
	
	@RequestMapping(value="categorylevellist",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object categorylevellist(String pid){
		System.err.println("根据一级菜单加载二级菜单,父id为:"+pid);
		List<App_category> categoryLevel2List=null;
		try {
		    categoryLevel2List=iService.SelectV2(pid);
			for (App_category app_category : categoryLevel2List) {
				System.err.println(app_category.getCategoryName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str=JSONArray.toJSONString(categoryLevel2List);
		System.err.println(str);
		return str;
		
		
	}
}
