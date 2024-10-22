package app.controllers.master;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.FinYearBean;
import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.reports.WdcpmksyOutcomeBean;
import app.service.CommonService;
import app.service.master.OutcomeMasterServices;

@Controller("OutcomeMasterController")
public class OutcomeMasterController {
	
	HttpSession session;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required = true)
	OutcomeMasterServices outcomeserv;
	
	private Map<String, String> finYrList=null;
	
	@RequestMapping(value="/outcomeMasterEntry", method = RequestMethod.GET) 
	public ModelAndView outcomeMasterEntry(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
	
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("master/outcomeMasterView");
			String regid=session.getAttribute("regId").toString();
			list=outcomeserv.getOutcomeMaster();
			mav.addObject("outcomeMasterList", list);
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("months", outcomeserv.getAllMonths());
		
			/*
			 * for(int i=0;i<ProjectPreparelist.size();i++) {
			 * System.out.println(ProjectPreparelist.get(i).getProjectPrepareId()); }
			 */
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getoutcomeseqno", method = RequestMethod.POST)
	@ResponseBody
	public List<WdcpmksyOutcomeBean> getheadseqno(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>> map = new LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>>();
		List<WdcpmksyOutcomeBean> proj = new ArrayList<WdcpmksyOutcomeBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = outcomeserv.getoutcomeseqno();
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@RequestMapping(value="/getoutcomedseqno", method = RequestMethod.POST)
	@ResponseBody
	public List<WdcpmksyOutcomeBean> getoutcomedseqno(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>> map = new LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>>();
		List<WdcpmksyOutcomeBean> proj = new ArrayList<WdcpmksyOutcomeBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = outcomeserv.getoutcomedseqno();
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@RequestMapping(value="/saveOutcomeMaster", method = RequestMethod.POST) 
	public ModelAndView saveOutcomeMaster(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("addoutcome") WdcpmksyOutcomeBean  outcome )
	{
		session = request.getSession(true);
		
		int b=0;
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("master/outcomeMasterView");
			String regid=session.getAttribute("regId").toString();
			b=outcomeserv.saveOutcomeMaster(outcome, session);
			list=outcomeserv.getOutcomeMaster();
			mav.addObject("outcomeMasterList", list);
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("months", outcomeserv.getAllMonths());
		
			if(b>0) 
			{
				mav.addObject("message", "Outcome added successfully.");
			}
			
			/*
			 * for(int i=0;i<list.size();i++) {
			 * System.out.println(list.get(i).getOutcome_desc()); }
			 */
			 
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/deleteOutcomeMaster", method = RequestMethod.POST)
	public ModelAndView deleteData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/outcomeMasterView");
		Boolean flag=false;
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = outcomeserv.deleteOutcomeMaster(id);
				if(flag) {
				mav.addObject("message","Outcome Activity has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					
				}
				list=outcomeserv.getOutcomeMaster();
				mav.addObject("outcomeMasterList", list);
				mav.addObject("financialYear", commonService.getAllFinancialYear());
				mav.addObject("months", outcomeserv.getAllMonths());
				
			}
			else {
				mav.addObject("message","Unable to remove data");
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	
	}
	
	@RequestMapping(value = "/getfinyearcode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getfinyearcode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		map=outcomeserv.getfinyearCode();
		//System.out.println("value of map:" +map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return map; 
	}
	
	@RequestMapping(value = "/getmonthcode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getmonthcode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		map=outcomeserv.getmonthcode();
		//System.out.println("value of map:" +map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return map; 
	}
	
	@RequestMapping(value="/outcomedatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<WdcpmksyOutcomeBean> outcomedatafind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		//System.out.println("value of id:" +id);
		session = request.getSession(true);
		LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>> map = new LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>>();
		List<WdcpmksyOutcomeBean> proj = new ArrayList<WdcpmksyOutcomeBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = outcomeserv.outcomedatafind(id);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@RequestMapping(value="/updateOutcomeMaster", method = RequestMethod.POST)
	public ModelAndView updateOutcomeMaster(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doutcomeid") Integer id, @RequestParam("outcome_desc1") String outcomedesc, @RequestParam("detail_exist1") Character detail_exist1,
			@RequestParam("fin_yr_cd1") Integer fin_yr_cd1, @RequestParam("seqno1") BigDecimal seqno, @RequestParam("month_id1") Integer month_id1, @RequestParam("bls_required")Boolean bls_required)
	{
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/outcomeMasterView");
		Boolean flag=false;
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		  try{ 
			  session = request.getSession(true); 
			  if(session!=null && session.getAttribute("loginID")!=null) 
			  { 
				  flag = outcomeserv.updateOutcomeMaster(id, outcomedesc, detail_exist1, fin_yr_cd1, seqno, month_id1, bls_required);
				  if(flag) 
				  {
					  mav.addObject("message","Outcome has been updated successfully.");
				  }
				  else {
					  mav.addObject("message","Could not update record because some error.");
				  }
			  }
			  else { mav.addObject("message","Problem on updated data/login expired"); 
			  }
			  
			  list=outcomeserv.getOutcomeMaster();
			  mav.addObject("outcomeMasterList", list);
			  mav.addObject("financialYear", commonService.getAllFinancialYear());
			  mav.addObject("months", outcomeserv.getAllMonths());
		  }
		  catch(Exception ex) 
		  { 
			  ex.printStackTrace(); 
		  }
		 
		
		return mav; 
	}
	
	@RequestMapping(value="/outcomeMasterDetailEntry", method = RequestMethod.GET) 
	public ModelAndView outcomeMasterDetailEntry(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
	
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("master/outcomeMasterDetailView");
			String regid=session.getAttribute("regId").toString();
			list=outcomeserv.getOutcomeDetailMaster();
			mav.addObject("outcomeDeatilList", list);
		
			mav.addObject("outcomeDesc", outcomeserv.getOutcomeYes());
		
			/*
			 * for(int i=0;i<ProjectPreparelist.size();i++) {
			 * System.out.println(ProjectPreparelist.get(i).getProjectPrepareId()); }
			 */
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	@RequestMapping(value="/saveOutcomeDetailMaster", method = RequestMethod.POST) 
	public ModelAndView saveOutcomeDetailMaster(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("addoutcome") WdcpmksyOutcomeBean  outcome )
	{
		session = request.getSession(true);
		
		int b=0;
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("master/outcomeMasterDetailView");
			String regid=session.getAttribute("regId").toString();
			b=outcomeserv.saveOutcomeDetailMaster(outcome, session);
			list=outcomeserv.getOutcomeDetailMaster();
			mav.addObject("outcomeDeatilList", list);
			mav.addObject("outcomeDesc", outcomeserv.getOutcomeYes());
		
			if(b>0) 
			{
				mav.addObject("message", "Outcome Detail added successfully.");
			}
			
			/*
			 * for(int i=0;i<list.size();i++) {
			 * System.out.println(list.get(i).getOutcome_desc()); }
			 */
			 
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
		
	}
	
	@RequestMapping(value="/deleteOutcomeDetailMaster", method = RequestMethod.POST)
	public ModelAndView deleteOutcomeDetailMaster(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		id = Integer.parseInt(request.getParameter("id")); 
		int idoutcomed = Integer.parseInt(request.getParameter("idoutcomed"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/outcomeMasterDetailView");
		Boolean flag=false;
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = outcomeserv.deleteOutcomeDetailMaster(id, idoutcomed);
				if(flag) {
				mav.addObject("message","Outcome detail Activity removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist in transaction table.");
					
				}
				list=outcomeserv.getOutcomeDetailMaster();
				mav.addObject("outcomeDeatilList", list);
				mav.addObject("outcomeDesc", outcomeserv.getOutcomeYes());
				
			}
			else {
				mav.addObject("message","Unable to remove data");
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav;    //
	
	}
	
	@RequestMapping(value = "/getOutcomeHeadcode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getOutcomeHeadcode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		map=outcomeserv.getOutcomeHeadcode();
		//System.out.println("value of map:" +map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return map; 
	}
	@RequestMapping(value="/outcomedatadetailfind", method = RequestMethod.GET)
	@ResponseBody
	public List<WdcpmksyOutcomeBean> outcomedatadetailfind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		//System.out.println("value of id:" +id);     
		session = request.getSession(true);
		LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>> map = new LinkedHashMap<Integer,List<WdcpmksyOutcomeBean>>();
		List<WdcpmksyOutcomeBean> proj = new ArrayList<WdcpmksyOutcomeBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = outcomeserv.outcomedatadetailfind(id);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}

	@RequestMapping(value="/updateOutcomeDetailMaster", method = RequestMethod.POST)
	public ModelAndView updateOutcomeDetailMaster(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doutcomedid") Integer id, @RequestParam("outcome_detail_desc1") String outcomedetdesc, @RequestParam("dseq_no1") BigDecimal seqno)
	{
		Integer doutcomeidh=0;
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/outcomeMasterDetailView");
		Boolean flag=false;
		List<WdcpmksyOutcomeBean> list=new  ArrayList<WdcpmksyOutcomeBean>();
		  try{ 
			  session = request.getSession(true); 
			  if(session!=null && session.getAttribute("loginID")!=null) 
			  { 
				  flag = outcomeserv.updateOutcomeDetailMaster(id, outcomedetdesc, doutcomeidh, seqno);
				  if(flag) 
				  {
					  mav.addObject("message","Outcome detail has been updated successfully.");
				  }
				  else {
					  mav.addObject("message","Could not update record because some error.");
				  }
			  }
			  else { mav.addObject("message","Problem on updated data/login expired"); 
			  }
			  
			  list=outcomeserv.getOutcomeDetailMaster();
			  mav.addObject("outcomeDeatilList", list);
			  mav.addObject("outcomeDesc", outcomeserv.getOutcomeYes());
		  }
		  catch(Exception ex) 
		  { 
			  ex.printStackTrace(); 
		  }
		 
		
		return mav; 
	}
	
	
}
