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

import app.bean.DisApprovalBean;
import app.bean.Login;
import app.bean.PhysicalActBean;

import app.service.PhysicalActService;


@Controller("phyActController")
public class PhysicalActController {

	HttpSession session;
	
	@Autowired(required = true)
	PhysicalActService physicalActservice;
	
	@RequestMapping(value = "/phyActivityGeneric", method = RequestMethod.GET)
	public ModelAndView modifyProjectList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("model/phyactivity");
			mav.addObject("phyact",physicalActservice.getPhyActdata());
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}

	
	@RequestMapping(value = "/getheadcode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getheadcode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		map=physicalActservice.getHeadCode();
		//System.out.println("value of map:" +map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return map; 
	}
	
	
	@RequestMapping(value="/getseqno", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> getseqno(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="headcode") int headcode)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<PhysicalActBean>> map = new LinkedHashMap<Integer,List<PhysicalActBean>>();
		List<PhysicalActBean> proj = new ArrayList<PhysicalActBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = physicalActservice.getseqnum(headcode);
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	
	
	@RequestMapping(value = "/getuomcode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getuomcode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		  session = request.getSession(true);
          LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		map=physicalActservice.getUomCode();
		//System.out.println("value of map:" +map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return map; 
	}


	@RequestMapping(value="/savephyactData", method = RequestMethod.POST)
	public ModelAndView savephyactData(HttpServletRequest request, HttpServletResponse response, @RequestParam("adesc")String adesc,
			@RequestParam("headcode")int headcode, @RequestParam("status")String status, @RequestParam("uomcode")int uomcode,
			@RequestParam("seqno")BigDecimal seqno, @RequestParam("assets") int assets)
	{
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/phyactivity");
		Boolean flag=false;
		
		  try{ session = request.getSession(true); 
		  if(session!=null && session.getAttribute("loginID")!=null) { 
		  flag =physicalActservice.savephyact(adesc,headcode,uomcode,status,seqno,assets,session.getAttribute("loginID").toString());
		  if(flag) {
		  mav.addObject("message","Data has been saved successfully");
		  }
		  else {
			  mav.addObject("message","Data can not saved because seqno already used");
			  mav.addObject("phyact",physicalActservice.getPhyActdata());
		  }
		  }else { mav.addObject("message","Unable to save data"); }
		  mav.addObject("phyact",physicalActservice.getPhyActdata());
		  }catch(Exception ex) { ex.printStackTrace(); }
		 
		
		return mav; 
	}

	@RequestMapping(value="/phyactdatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<PhysicalActBean> phyactdatafind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		//System.out.println("value of id:" +id);
		session = request.getSession(true);
		LinkedHashMap<Integer,List<PhysicalActBean>> map = new LinkedHashMap<Integer,List<PhysicalActBean>>();
		List<PhysicalActBean> proj = new ArrayList<PhysicalActBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = physicalActservice.findactdesc(id);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}

	@RequestMapping(value="/updateactData", method = RequestMethod.POST)
	public ModelAndView updateactData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id")int id, @RequestParam("adesc")String adesc, @RequestParam("status")String status,
			@RequestParam("uomcode1")int uomcode, @RequestParam("seqno1")BigDecimal seqno, @RequestParam("assets1") int asset)
	{
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/phyactivity");
		Boolean flag=false;
		
		  try{ 
			  session = request.getSession(true); 
		  if(session!=null && session.getAttribute("loginID")!=null) 
		  { 
		  flag = physicalActservice.updateactdata(id, adesc,status,uomcode, seqno, asset, session.getAttribute("loginID").toString());
		  if(flag) {
		  mav.addObject("message","Activity Code:"+" "+id +" "+"has been updated successfully");
		  }
		  else {
			  mav.addObject("message","Could not update record because seqno already used");
			  mav.addObject("phyact",physicalActservice.getPhyActdata());
		  }
		  }else { mav.addObject("message","Problem on updated data"); 
		  }
		  mav.addObject("phyact",physicalActservice.getPhyActdata()); 
		  }catch(Exception ex) 
		  { 
			  ex.printStackTrace(); 
		  }
		 
		
		return mav; 
	}

	@RequestMapping(value="/deletephyactData", method = RequestMethod.POST)
	public ModelAndView deletephyactData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/phyactivity");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = physicalActservice.deletephyhead(id);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("phyact",physicalActservice.getPhyActdata());
				}
				
			}else {
				mav.addObject("message","Unable to remove data");
			}
			mav.addObject("phyact",physicalActservice.getPhyActdata());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
}
