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

import com.itextpdf.text.log.SysoCounter;

import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.ProfileBean;
import app.model.master.IwmpMPhySubactivity;
import app.service.PhysicalActService;
import app.service.ProfileService;

@Controller("phySubActController")
public class PhysicalSubActController {
HttpSession session;
	
	@Autowired(required = true)
	PhysicalActService physicalActservice;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@RequestMapping(value = "/phySubActivityGeneric", method = RequestMethod.GET)
	public ModelAndView modifyProjectList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("model/physubactivity");
			mav.addObject("physubact",physicalActservice.getPhySubActdata());
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}
	@RequestMapping(value = "/getsubheadcode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getsubheadcode(HttpServletRequest request, HttpServletResponse response) {
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
	@RequestMapping(value="/getactdesc", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getseqno(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="headcode") int headcode)
	{
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			map = physicalActservice.getactdesc(headcode);
			
		}else {
			map=null;
			
		}
		return map;  
	}

	

	@RequestMapping(value = "/savephysubactData", method = RequestMethod.POST)
	public ModelAndView saveupdatephysubactData(HttpServletRequest request, HttpServletResponse response, @RequestParam("actdesc")int actdesc,
			@RequestParam("sbactdesc")String sbactdesc, @RequestParam("status")Character status, @RequestParam("seqno")BigDecimal seqno)	
	{
		//System.out.println("act code:" +actdesc);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/physubactivity");
		session = request.getSession(true);
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		String userType = session.getAttribute("userType").toString();
		List<ProfileBean> listm=new  ArrayList<ProfileBean>();
		int stCode = 0;
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = physicalActservice.savephysubact(actdesc,sbactdesc,status,seqno,session.getAttribute("loginID").toString(), userType, stCode);
			if(res=="success") {
				  mav.addObject("message","Data has been saved successfully");
			}
				  else {
					  mav.addObject("message","Data can not saved because seqno already used");
					  mav.addObject("physubact",physicalActservice.getPhySubActdata());
				  }
				  }else { mav.addObject("message","Problem on insert data"); 
				  }
				  mav.addObject("physubact",physicalActservice.getPhySubActdata()); 
	
		return mav;
	}
	
	@RequestMapping(value="/physubactdatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<PhysicalActBean> physubactdatafind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		session = request.getSession(true);
		List<IwmpMPhySubactivity> proj = new ArrayList<IwmpMPhySubactivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = physicalActservice.findsubactdesc(id);
			for(IwmpMPhySubactivity list : proj) {
			bean = new PhysicalActBean();
			bean.setHeaddesc(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
			bean.setSubactivitydesc(list.getSubActivityDesc());
			bean.setSeqno(list.getSeqNo());
			bean.setActdesc(list.getIwmpMPhyActivity().getActivityDesc());
			bean.setStatus(list.getStatus());
			//bean.setHeaddesc(null)
			finalList.add(bean);
			}
			
		}else {
			proj=null;
			
		}
		return finalList;  
	}


	@RequestMapping(value = "/updatesubactData", method = RequestMethod.POST)
	public ModelAndView updatesubactData(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int id,
			@RequestParam("sbactdesc1")String sbactdesc, @RequestParam("status")Character status, @RequestParam("seqno1")BigDecimal seqno)	
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/physubactivity");
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = physicalActservice.updatephysubact(id,sbactdesc,status,seqno,session.getAttribute("loginID").toString());
			if(res=="success") {
				  mav.addObject("message","Activity Code:"+" "+id +" "+"has been updated successfully");
			}
				  else {
					  mav.addObject("message","Could not update record because seqno already used");
					  mav.addObject("physubact",physicalActservice.getPhySubActdata());
				  }
				  }else { mav.addObject("message","Problem on updated data"); 
				  }
				  mav.addObject("physubact",physicalActservice.getPhySubActdata()); 
	
		return mav;
	}

	@RequestMapping(value="/deletephysubactData", method = RequestMethod.POST)
	public ModelAndView deletephysubactData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/physubactivity");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = physicalActservice.deletephysubhead(id);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("physubact",physicalActservice.getPhySubActdata());
				}
			}
			else {
				mav.addObject("message","Unable to remove data");
			}
			mav.addObject("physubact",physicalActservice.getPhySubActdata());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
}
