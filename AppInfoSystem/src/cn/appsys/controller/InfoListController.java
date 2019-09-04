package cn.appsys.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.jsf.FacesContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.App_category;
import cn.appsys.pojo.App_info;
import cn.appsys.pojo.Data_dictionary;
import cn.appsys.pojo.PageSupport;
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
	
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String List(HttpSession session,@RequestParam(value="querySoftwareName",required=false) String softwareName,@RequestParam(value="queryStatus",required=false) String status,@RequestParam(value="queryFlatformId",required=false) String flatformId,@RequestParam(value="queryCategoryLevel1",required=false) String categoryLevel1,@RequestParam(value="queryCategoryLevel2",required=false) String categoryLevel2,@RequestParam(value="queryCategoryLevel3",required=false) String categoryLevel3,@RequestParam(value="pageIndex",required=false) String size
			,@RequestParam(value="querySoftwareName",required=false) String querySoftwareName
			,@RequestParam(value="queryStatus",required=false) Integer queryStatus
			,@RequestParam(value="queryFlatformId",required=false) Integer queryFlatformId
			,@RequestParam(value="queryCategoryLevel1",required=false) Integer queryCategoryLevel1
			,@RequestParam(value="categorylevel2list",required=false) Integer queryCategoryLevel2
			,@RequestParam(value="queryCategoryLevel3",required=false) Integer queryCategoryLevel3){
		session.setAttribute("querySoftwareName",querySoftwareName );
		session.setAttribute("queryStatus",queryStatus );
		session.setAttribute("queryFlatformId",queryFlatformId );
		session.setAttribute("queryCategoryLevel1",queryCategoryLevel1 );
		session.setAttribute("queryCategoryLevel2",queryCategoryLevel2 );
		session.setAttribute("queryCategoryLevel3",queryCategoryLevel3 );
		System.err.println("查询List");
		int frm=0;
		PageSupport page=new PageSupport();
		System.err.println(softwareName+"   "+status+"   "+flatformId+"   "+categoryLevel1+"   "+categoryLevel2+"   "+ categoryLevel3+"   "+ size);
		try {
			if (size==null) {
				frm=0;
			}else{
				frm=Integer.parseInt(size);
			}
			System.err.println("frm"+frm);
			List<App_info> infoList=iService.Select(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3,(frm-1)*page.getPageSize(),page.getPageSize());
			System.err.println("List:"+infoList);
			for (App_info app_info : infoList) {
				System.err.println(app_info.getSoftwareName());
			}
			session.setAttribute("appInfoList", infoList);
			int totalCount=iService.Count(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
			System.err.println("totalCount:"+totalCount);
			page.setTotalCount(totalCount);
			page.setCurrentPageNo(frm);
			page.setTotalPageCount(totalCount/page.getPageSize()+1);
			System.err.println("CurrentPageNo:"+frm+"TotalPageCount:"+totalCount);
			session.setAttribute("pages", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/infolist/list";
	}
	
	@RequestMapping("/appinfoadd")
	public String appinfoadd(){
		return "developer/appinfoadd";
		
	}
	
	@RequestMapping(value="/datadictionarylist.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String Load(@RequestParam(value="tcode",required=false) String tcode){
		System.err.println("进入新增界面");
		System.err.println("加载所属平台");
		String str=null;
		try {
			List<Data_dictionary> flatFormList=iService.SelectPlatform();     //所属平台
			for (Data_dictionary data_dictionary : flatFormList) {
				System.err.println("1"+data_dictionary.getValueName());
			}
		
			str=JSONArray.toJSONString(flatFormList);
			System.err.println(str);
			System.err.println(tcode);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
	}
		return str;
}
	@RequestMapping(value="/categorylevellist.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object Load1(@RequestParam(value="pid",required=false) String pid){
		System.err.println("加载一级菜单");
		List<App_category> list=null;
		String str =null;
		try {
			System.err.println("pid:"+pid);
			if (pid==null||pid=="") {
				list=iService.SelectV1();
				System.err.println("list1:"+list);
				
			}else{
				list=iService.SelectV2(pid);
			}
			str=JSONArray.toJSONString(list);
			System.err.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@RequestMapping(value="apkexist.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object blurAPKName(@RequestParam(value="APKName",required=false) String APKName){
		String str="";
		List<App_info> list=null;
		try {
			System.err.println("APKName"+APKName);
			if (APKName==null||APKName=="") {
				str="empty";
			}else{
				list=iService.SelectAPK(APKName);

				System.err.println("list:"+list);
				if (list==null||list.size()==0 ) {
					str="noexist";
				}else{
					str="exist";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("str:"+str);
		String data=JSON.toJSONString(str);
		System.err.println("data"+data);
		return data;
		
	}
}