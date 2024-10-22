package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.ProfileBean;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.WdcpmksyMPhyOtherActivity;
import app.service.PhysicalActService;
import app.service.ProfileService;

@Controller
public class AddPhyOtherSubCategoryController {
	HttpSession session;
	
	@Autowired(required = true)
	PhysicalActService physicalActservice;
	
	@Autowired(required = true)
	ProfileService profileService;

	@RequestMapping(value = "/phyOtherSubCategory", method = RequestMethod.GET)
	public ModelAndView modifyProjectList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
	          Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
	          String userType = session.getAttribute("userType").toString();
			List<ProfileBean> listm=new  ArrayList<ProfileBean>();
			int stCode = 0;
				listm=profileService.getMapstate(regId, userType);
				for(ProfileBean bean : listm) {
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
			mav = new ModelAndView("model/addSubOtherCategory");
			mav.addObject("phyothrsubcat",physicalActservice.getOtherSubCategorydata(stCode));
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}
	
	@RequestMapping(value = "/getheadActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getsubheadcode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
        LinkedHashMap<Integer,String> chkMap = new LinkedHashMap<Integer,String>();
        LinkedHashMap<Integer,String> stMap = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=physicalActservice.getHeadCode();
				for(Map.Entry<Integer, String> chk : map.entrySet()) {
					chkMap = physicalActservice.getactdesc(chk.getKey());
					if(chkMap.containsValue("Others")) {
						stMap.put(chk.getKey(), chk.getValue());
					}
				}
		
		//System.out.println("value of map:" +map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return stMap; 
	}
	
	@RequestMapping(value="/othrsubcatdatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<PhysicalActBean> othrsubcatdatafind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();  
		List<WdcpmksyMPhyOtherActivity> proj = new ArrayList<WdcpmksyMPhyOtherActivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = physicalActservice.findothrsubcatdesc(id);
			for(WdcpmksyMPhyOtherActivity list : proj) {
			bean = new PhysicalActBean();
			bean.setHeaddesc(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
			bean.setActdesc("Others");
			bean.setSeqno(list.getSeqNo());
			bean.setOtherActivityDesc(list.getOtherActivityDesc());
			bean.setStatus(list.getStatus());
			//bean.setHeaddesc(null)
			finalList.add(bean);
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return finalList;  
	}
	
	@RequestMapping(value = "/updateothrsubcatData", method = RequestMethod.POST)
	public ModelAndView updateothrsubcatData(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int id,
			@RequestParam("oscatdesc1")String oscatdesc, @RequestParam("status")Character status, @RequestParam("seqno1")BigDecimal seqno)	
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
	        String userType = session.getAttribute("userType").toString();
			List<ProfileBean> listm=new  ArrayList<ProfileBean>();
			int stCode = 0;
				listm=profileService.getMapstate(regId, userType);
				for(ProfileBean bean : listm) {
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
			mav = new ModelAndView("model/addSubOtherCategory");
			res  = physicalActservice.updateothrsubcat(id,oscatdesc,status,seqno,session.getAttribute("loginID").toString());
			if(res=="success") {
				  mav.addObject("message","Activity Code:"+" "+id +" "+"has been updated successfully");
			}
			else {
				mav.addObject("message","Could not update record because seqno already used");
			}
			mav.addObject("phyothrsubcat",physicalActservice.getOtherSubCategorydata(stCode));
		}else { 
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
			 
	
		return mav;
	}
	
	@RequestMapping(value = "/saveOthrSubCatData", method = RequestMethod.POST)
	public ModelAndView saveupdateOthrSubCatData(HttpServletRequest request, HttpServletResponse response, @RequestParam("actdesc")int actdesc,
			@RequestParam("othrsubcatdesc")String othrsubcatdesc, @RequestParam("status")Character status, @RequestParam("seqno")BigDecimal seqno)	
	{
		//System.out.println("act code:" +actdesc);
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("model/addSubOtherCategory");
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			List<ProfileBean> listm=new  ArrayList<ProfileBean>();
			int stCode = 0;
				listm=profileService.getMapstate(regId, userType);
				for(ProfileBean bean : listm) {
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
			String res = new String("fail");
			res  = physicalActservice.savephysubact(actdesc,othrsubcatdesc,status,seqno,session.getAttribute("loginID").toString(), userType, stCode);
			if(res=="success") {
				  mav.addObject("message","Data has been saved successfully");
			}
			else {
				mav.addObject("message","Data can not saved because seqno already used");
			}
			mav.addObject("phyothrsubcat",physicalActservice.getOtherSubCategorydata(stCode));
		}else { 
			mav = new ModelAndView("login");
			mav.addObject("login", new Login()); 
		}
	
		return mav;
	}
	
	@RequestMapping(value="/deleteothrsubcatData", method = RequestMethod.POST)
	public ModelAndView deleteothrsubcatData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		List<ProfileBean> listm=new  ArrayList<ProfileBean>();
		mav = new ModelAndView("model/addSubOtherCategory");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				String userType = session.getAttribute("userType").toString();
				int stCode = 0;
				listm=profileService.getMapstate(regId, userType);
				for(ProfileBean bean : listm) {
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
				flag = physicalActservice.deleteothersubcat(id);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("physubact",physicalActservice.getPhySubActdata());
				}
				mav.addObject("phyothrsubcat",physicalActservice.getOtherSubCategorydata(stCode));
			}
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login()); 
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
}
