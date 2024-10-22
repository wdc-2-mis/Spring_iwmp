package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import app.bean.PhysicalHeaddataBean;
import app.bean.UOMDataBean;
import app.service.UOMDataService;


@Controller("uomformcontroller")
public class UOMformController {

	HttpSession session;
	@Autowired(required = true)
	UOMDataService uomdataservice;
	
	@RequestMapping(value = "/showuomform", method = RequestMethod.GET)
	public ModelAndView ShowUOMForm(HttpServletRequest request, HttpServletResponse response) {
	  ModelAndView mav;
	  session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("master/uomform");
			mav.addObject("uomdata",uomdataservice.getUOMdata());
		    //System.out.println("data-----------"+uomdataservice.getUOMdata());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
	  return mav;
	}

	@RequestMapping(value="/getuomcodee", method = RequestMethod.POST)
	@ResponseBody
	public List<UOMDataBean> getuomcodee(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<UOMDataBean>> map = new LinkedHashMap<Integer,List<UOMDataBean>>();
		List<UOMDataBean> proj = new ArrayList<UOMDataBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = uomdataservice.getuomcode();
			
		}else {
			proj=null;
			
		}
		return proj;  
	}

	@RequestMapping(value="/saveuomData", method = RequestMethod.POST)
	public ModelAndView saveuomData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uomcode") int uomcode, @RequestParam("uomdesc") String uomdesc)
	{
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/uomform");
		Boolean flag=false;
		
		  try{ session = request.getSession(true); 
		  if(session!=null && session.getAttribute("loginID")!=null) { 
		  flag =uomdataservice.savephyact(uomcode,uomdesc,session.getAttribute("loginID").toString());
		  if(flag) {
		  mav.addObject("message","Data has been saved successfully");
		  }
		  else {
			  mav.addObject("message","Data can not saved because UOM Code already used");
			  mav.addObject("uomdata",uomdataservice.getUOMdata());
		  }
		  }else { mav.addObject("message","Unable to save data"); }
		  mav.addObject("uomdata",uomdataservice.getUOMdata());
		  }catch(Exception ex) { ex.printStackTrace(); }
		 
		
		return mav; 
	}

	
	@RequestMapping(value="/editUOMModal", method = RequestMethod.POST)
	@ResponseBody
	public List<UOMDataBean> editUOMModal(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)
	{
		System.out.println("value of id:" +id);
		session = request.getSession(true);
		LinkedHashMap<Integer,List<UOMDataBean>> map = new LinkedHashMap<Integer,List<UOMDataBean>>();
		List<UOMDataBean> proj = new ArrayList<UOMDataBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		proj = uomdataservice.edituomdata(id);
			System.out.println("value of proj:" +proj);
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	
	@RequestMapping(value="/updateuomData", method = RequestMethod.POST)
	public ModelAndView updateuomData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") int id, @RequestParam("uomdesc") String uomdesc)
	{
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/uomform");
		Boolean flag=false;
		
		  try{ 
			  session = request.getSession(true); 
		  if(session!=null && session.getAttribute("loginID")!=null) 
		  { 
		  flag = uomdataservice.updateuomdata(id, uomdesc, session.getAttribute("loginID").toString());
		  if(flag) {
		  mav.addObject("message","UOM Code:"+" "+id +" "+"has been updated successfully");
		 
		  }
		  else {
			  mav.addObject("message","Could not update record because UOM Code already used in transaction table");
			  mav.addObject("uomdata",uomdataservice.getUOMdata());
			  
		  }
		  }else { mav.addObject("message","Problem on updated data"); 
		  }
		  mav.addObject("uomdata",uomdataservice.getUOMdata());
		  }catch(Exception ex) 
		  { 
			  ex.printStackTrace(); 
		  }
		 
		
		return mav; 
	}
	@RequestMapping(value="/deleteuomData", method = RequestMethod.POST)
	public ModelAndView deleteuomData(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)
	{
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/uomform");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = uomdataservice.deleteUOMdata(id);
				if(flag) {
				mav.addObject("message","UOM Code:"+" "+id+" "+ "has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("uomdata",uomdataservice.getUOMdata());
				}
				
			}else {
				mav.addObject("message","Unable to remove data");
			}
			mav.addObject("uomdata",uomdataservice.getUOMdata());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
	
}
