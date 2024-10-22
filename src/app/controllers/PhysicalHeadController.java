
package app.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.PhysicalHeaddataBean;
import app.bean.ProjectLocationBean;
import app.bean.ProjectState;
import app.model.IwmpMProject;
import app.model.master.IwmpBlock;
import app.service.PhysicalHeadservice;
import app.service.ProjectMasterService;

@Controller("physicalHeadController")
public class PhysicalHeadController {
	HttpSession session;
	
	@Autowired(required = true)
	PhysicalHeadservice physicalheadservice;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@RequestMapping(value = "/phyFinGeneric", method = RequestMethod.GET)
	public ModelAndView modifyProjectList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("model/phyFinHeadM");
			mav.addObject("headdata",physicalheadservice.getPhyHeaddata());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

	
	@RequestMapping(value="/saveData", method = RequestMethod.POST)
	public ModelAndView saveData(HttpServletRequest request, HttpServletResponse response, @RequestParam("seqno") BigDecimal seqno, @RequestParam("bline") Boolean bline)
	{
		String headdesc="";
		String status="";
		headdesc = request.getParameter("hdesc");
		status = request.getParameter("status");
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/phyFinHeadM");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = physicalheadservice.savephyhead(headdesc,status, seqno, bline, session.getAttribute("loginID").toString());
				if(flag) {
				mav.addObject("message","Data has been saved successfully");
				}
				else {
					mav.addObject("message","Data can not saved because seqno already used");
				}
			}else {
				mav.addObject("message","Unable to save data");
			}
			mav.addObject("headdata",physicalheadservice.getPhyHeaddata());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return mav; 
	}
	
	@RequestMapping(value="/deleteData", method = RequestMethod.POST)
	public ModelAndView deleteData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/phyFinHeadM");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = physicalheadservice.deletephyhead(id);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("headdata",physicalheadservice.getPhyHeaddata());
				}
				
			}else {
				mav.addObject("message","Unable to remove data");
			}
			mav.addObject("headdata",physicalheadservice.getPhyHeaddata());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
	
	@RequestMapping(value="/getheadseqno", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalHeaddataBean> getheadseqno(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<PhysicalHeaddataBean>> map = new LinkedHashMap<Integer,List<PhysicalHeaddataBean>>();
		List<PhysicalHeaddataBean> proj = new ArrayList<PhysicalHeaddataBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = physicalheadservice.getheadseqno();
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@RequestMapping(value="/phydatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<PhysicalHeaddataBean> phyheaddesc(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<PhysicalHeaddataBean>> map = new LinkedHashMap<Integer,List<PhysicalHeaddataBean>>();
		List<PhysicalHeaddataBean> proj = new ArrayList<PhysicalHeaddataBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = physicalheadservice.findphyhead(id);
			
			System.out.println("List: "+proj);
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@RequestMapping(value="/updateData", method = RequestMethod.POST)
	public ModelAndView updateData(HttpServletRequest request, HttpServletResponse response, @RequestParam("seqno1")BigDecimal seqno, @RequestParam("bline") Boolean bline)
	{
		String headdesc="";
		String status="";
		
        int id = Integer.parseInt(request.getParameter("id"));
		//System.out.println("id after updated data:" +id);
		headdesc = request.getParameter("hdesc");
		status = request.getParameter("status");
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/phyFinHeadM");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = physicalheadservice.updatephyhead(headdesc, id, status, seqno, bline, session.getAttribute("loginID").toString());
				if(flag) {
				
				mav.addObject("message","Head Code:"+" "+id +" "+"has been updated successfully");
				}
				else {
					mav.addObject("message","Could not update record because Head Code already used in transaction table");
				}
			}else {
				mav.addObject("message","Problem on updated data");
			}
			mav.addObject("headdata",physicalheadservice.getPhyHeaddata());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return mav; 
	}
	
	

}
