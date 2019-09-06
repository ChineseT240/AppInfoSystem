package cn.appsys.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.jsf.FacesContextUtils;
import org.springframework.web.multipart.MultipartFile;

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
	public Object List(Model model,HttpSession session){
		System.err.println("显示列表方法");
		List<Data_dictionary> statusList=iService.SelectAppState();     //app状态
		List<Data_dictionary> flatFormList=iService.SelectPlatform();     //所属平台
		List<App_category> categoryLevel1List=iService.SelectV1();     //一级菜单
		model.addAttribute("statusList", statusList);                    //app状态
		model.addAttribute("flatFormList", flatFormList);                 //所属平台
		model.addAttribute("categoryLevel1List", categoryLevel1List);     //一级菜单
		PageSupport page=new PageSupport();
		if (session.getAttribute("appInfoList")==null) {
			List<App_info> infoList=iService.Select("", "", "", "", "", "",0,page.getPageSize());
			session.setAttribute("appInfoList", infoList);
			int totalCount=iService.Count("", "", "", "", "", "");
			System.err.println("totalCount:"+totalCount);
			page.setTotalCount(totalCount);
			page.setCurrentPageNo(1);
			int num=0;   //总页数
			if (totalCount%page.getPageSize()==0) {
				num=totalCount/page.getPageSize()-1;
			}else {
				num=totalCount/page.getPageSize();
			}
			page.setTotalPageCount(num);
			System.err.println("CurrentPageNo:"+1+"TotalPageCount:"+totalCount);
			session.setAttribute("pages", page);
		}
		
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
	
	@RequestMapping(value="listcha")
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
				frm=Integer.parseInt(size)-1;
			}
			System.err.println("frm"+frm);
			List<App_info> infoList=iService.Select(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3,frm*page.getPageSize(),page.getPageSize());
			System.err.println("List:"+infoList);
			for (App_info app_info : infoList) {
				System.err.println(app_info.getSoftwareName());
			}
			session.setAttribute("appInfoList", infoList);
			int totalCount=iService.Count(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
			System.err.println("totalCount:"+totalCount);
			page.setTotalCount(totalCount);
			page.setCurrentPageNo(frm);
			int num=0;   //总页数
			if (totalCount%page.getPageSize()==0) {
				num=totalCount/page.getPageSize()-1;
			}else {
				num=totalCount/page.getPageSize();
			}
			page.setTotalPageCount(num);
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
	
	/**
	 * 验证是否有此APK
	 * @param APKName
	 * @return
	 */
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
	
	/**
	 * 新增保存
	 * @param softwareName
	 * @param APKName
	 * @param supportROM
	 * @param interfaceLanguage
	 * @param softwareSize
	 * @param downloads
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param appInfo
	 * @param attach
	 * @param request
	 * @return
	 */
	@RequestMapping("/appinfoaddsave")
	public String appinfoaddsave(@RequestParam(value = "softwareName") String softwareName,
			@RequestParam(value = "APKName") String APKName,
			@RequestParam(value = "supportROM") String supportROM, @RequestParam(value = "interfaceLanguage") String interfaceLanguage,
			@RequestParam(value = "softwareSize") BigDecimal softwareSize, @RequestParam(value = "downloads") Integer downloads,
			@RequestParam(value = "flatformId") Integer flatformId, @RequestParam(value = "categoryLevel1") Integer categoryLevel1,
			@RequestParam(value = "categoryLevel2") Integer categoryLevel2, @RequestParam(value = "categoryLevel3") Integer categoryLevel3,
			@RequestParam(value = "appInfo") String appInfo,@RequestParam(value="a_logoPicPath",required=false) MultipartFile attach
			,HttpServletRequest request) {
		String idPicpath=null;
		String path=null;
		String logoPicPath=null;
		System.err.println("进入增加方法");
		System.err.println("softwareName"+softwareName);
		System.err.println("APKName"+APKName);
		System.err.println("supportROM"+supportROM);
		System.err.println("interfaceLanguage"+interfaceLanguage);
		System.err.println("softwareSize"+softwareSize);
		System.err.println("downloads"+downloads);
		System.err.println("flatformId"+flatformId);
		System.err.println("categoryLevel1"+categoryLevel1);
		System.err.println("categoryLevel2"+categoryLevel2);
		System.err.println("categoryLevel3"+categoryLevel3);
		System.err.println("appInfo"+appInfo);
	//	System.err.println("logoPicPath"+logoPicPath);
		if (!attach.isEmpty()) {
		    path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			System.err.println("uploadFile path=======>"+path);   //服务器路径名
			String oldFileName=attach.getOriginalFilename(); //原文件名
			System.err.println("原文件名:=========>"+oldFileName);
			String prefix=FilenameUtils.getExtension(oldFileName);   //原文件名后缀
			System.err.println("原文件后缀:"+prefix);
			int filesize=9999999;
		    System.err.println("原文件大小:=========>"+attach.getSize());
		    if (attach.getSize()>filesize) {
				request.setAttribute("fileUploadError", "上传文件不得超过9999999KB");
				return "redirect:/infolist/appinfoaddsave";
			}else if (prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||prefix.equalsIgnoreCase("jpeg")||prefix.equalsIgnoreCase("pneg")) {
				int random=(int)(Math.random()*(1000000-0)+0);
				String fileName=System.currentTimeMillis()+random+"_Personal.jpg";
				System.err.println("新名字:===========>"+fileName);
				File targetFile=new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdir();
				}
				//保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "*上传失败!");
					return "redirect:/infolist/appinfoaddsave";
				}
				idPicpath=path+File.separator+fileName;
			    System.err.println("idPicpath:=========>"+idPicpath);
			   logoPicPath=request.getServletContext().getContextPath()+"/statics/uploadfiles/"+fileName;
			   System.err.println(logoPicPath);
			}else {
				request.setAttribute("fileUploadError", "*上传图片格式不正确");
				return "redirect:/infolist/appinfoaddsave";
			}
		}
		int num=iService.Add(softwareName, APKName, supportROM, interfaceLanguage, softwareSize, downloads, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, appInfo, logoPicPath,path);
		System.err.println(num);
		return "developer/appinfolist";

	}
	
	
	/**
	 * 修改
	 * @param vid
	 * @param aid
	 * @param session
	 * @return
	 */
	@RequestMapping("appversionmodify")
	public String appversionmodify(@RequestParam(value="vid") String vid,@RequestParam(value="aid") String aid,HttpSession session){
		System.err.println("进入修改方法:>>>>>>>>>>>>>>>>>vid:"+vid+",aid:"+aid);
		App_info app_info=iService.UpDateShow(aid, vid);
		session.setAttribute("appInfo", app_info);
		return "redirect:appinfomodify";	
	}
	
	@RequestMapping("appinfomodify")
	public String appinfomodify(){
		return "developer/appinfomodify";
		
	}
	
	/**
	 * 修改
	 * @param softwareName
	 * @param APKName
	 * @param supportROM
	 * @param interfaceLanguage
	 * @param softwareSize
	 * @param downloads
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param id
	 * @return
	 */
	@RequestMapping("appinfomodifysave")
	public String appinfomodifysave(@RequestParam("softwareName") String softwareName,@RequestParam("APKName") String APKName,
			@RequestParam("supportROM") String supportROM,@RequestParam("interfaceLanguage") String interfaceLanguage,
			@RequestParam("softwareSize") BigDecimal softwareSize,@RequestParam("downloads") int downloads,
			@RequestParam("flatformId") int flatformId,@RequestParam("categoryLevel1") int categoryLevel1,
			@RequestParam("categoryLevel2") int categoryLevel2,@RequestParam("categoryLevel3") int categoryLevel3,
			@RequestParam("id") int id,@RequestParam("appInfo") String appInfo){
		    System.out.println("softwareName"+softwareName);
		    System.out.println("APKName"+APKName);
		    System.out.println("supportROM"+supportROM);
		    System.out.println("interfaceLanguage"+interfaceLanguage);
		    System.out.println("softwareSize"+softwareSize);
		    System.out.println("downloads"+downloads);
		    System.out.println("flatformId"+flatformId);
		    System.out.println("categoryLevel1"+categoryLevel1);
		    System.out.println("categoryLevel2"+categoryLevel2);
		    System.out.println("categoryLevel3"+categoryLevel3);
		    System.out.println("id"+id);
		    App_info app_info=new App_info();
		    app_info.setSoftwareName(softwareName);
		    app_info.setSupportROM(supportROM);
		    app_info.setAPKName(APKName);
		    app_info.setInterfaceLanguage(interfaceLanguage);
		    app_info.setDownloads(downloads);
		    app_info.setSoftwareSize(softwareSize);
		    app_info.setFlatformId(flatformId);
		    app_info.setCategoryLevel1(categoryLevel1);
		    app_info.setCategoryLevel2(categoryLevel2);
		    app_info.setCategoryLevel3(categoryLevel3);
		    app_info.setId(id);
		    app_info.setAppInfo(appInfo);
		    if (iService.Updata(app_info)>0) {
		       System.err.println("进来了修改信息");
		    	return "redirect:/infolist/listcha";
			}else{
				return "redirect:appinfomodifysave";
			}
	}
	
	@RequestMapping("appversionadd")
	public String appversionadd(@RequestParam(value="id") String id){
		System.err.println("id"+id);
		return "appversionadd";
		
	}
}