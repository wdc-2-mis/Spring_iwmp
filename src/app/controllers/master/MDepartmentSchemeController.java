package app.controllers.master;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import app.bean.BaselineMasterBean;
import app.bean.DepartmentSchemeMasterBean;
import app.bean.Login;
import app.model.MBlsOutType;
import app.model.MDepartmentScheme;
import app.service.master.DepartmentSchemeMasterService;

@Controller("MDepartmentSchemeController")
public class MDepartmentSchemeController {
	
	HttpSession session;
	
	@Autowired(required = true)
	DepartmentSchemeMasterService dsms;
	
	@RequestMapping(value = "/departmentSchemeMaster", method = RequestMethod.GET)
	public ModelAndView departmentschememaster(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
          
          if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("master/departmentSchemeMaster");
			mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
          }else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
          }
          return mav; 
	}
	
	@RequestMapping(value = "/saveDeptSchemeMasterData", method = RequestMethod.POST)
	public ModelAndView saveDeptSchemeMasterData(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("desc")String desc, @RequestParam("seqno")BigDecimal seqno, @RequestParam("type")String type)	
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/departmentSchemeMaster");
		session = request.getSession(true);
		String stcode= session.getAttribute("stateCode").toString();
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = dsms.saveDeptSchemeMasterData(desc, seqno, type, session);
			if(res=="success") {
				  mav.addObject("message","Data has been saved successfully");
			}
			else {
					  mav.addObject("message","Data can not saved ");
					  mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
			}
		}
		else { 
			mav.addObject("message","Problem on insert data"); 
		}
		mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
	
		return mav;
	}
	
	@RequestMapping(value="/deleteDeptSchemeData", method = RequestMethod.POST)
	public ModelAndView deleteDeptSchemeData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		id = Integer.parseInt(request.getParameter("id"));
		String schemetype=request.getParameter("schemetype");
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/departmentSchemeMaster");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			String stcode= session.getAttribute("stateCode").toString();
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = dsms.deleteDeptSchemeData(id, schemetype);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this scheme exist on transaction table");
					mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
				}
			}
			else {
				mav.addObject("message","Unable tologin session");
			}
			mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
	
	@RequestMapping(value="/deptschememasterfind", method = RequestMethod.GET)
	@ResponseBody
	public List<DepartmentSchemeMasterBean> deptschememasterfind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		session = request.getSession(true);
		List<MDepartmentScheme> proj = new ArrayList<MDepartmentScheme>();
		List<DepartmentSchemeMasterBean> finalList = new ArrayList<DepartmentSchemeMasterBean>();
		DepartmentSchemeMasterBean bean = new DepartmentSchemeMasterBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = dsms.deptschememasterfind(id);
			for(MDepartmentScheme list : proj) {
			bean = new DepartmentSchemeMasterBean();
			bean.setDepartmentSchemeIdPk(list.getDepartmentSchemeIdPk());
			bean.setSchemeDescription(list.getSchemeDescription());
			bean.setSeqno(list.getSeqNo());
			bean.setStatus(list.getStatus());
			finalList.add(bean);
			}
			
		}else {
			proj=null;
			
		}
		return finalList;  
	}
	
	@RequestMapping(value = "/updateDeptSchemeData", method = RequestMethod.POST)
	public ModelAndView updateDeptSchemeData(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int id,
			@RequestParam("desc")String desc, @RequestParam("seqno")BigDecimal seqno, @RequestParam("status")String status)	
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/departmentSchemeMaster");
		session = request.getSession(true);
		String stcode= session.getAttribute("stateCode").toString();
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = dsms.updateDeptSchemeData(id, desc, seqno, status, session);
			if(res=="success") 
			{
				  mav.addObject("message", "Data has been updated successfully");
			}
			else {
				  mav.addObject("message","Could not update record because seqno already used");
				  mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
			}
		}
		else { 
				mav.addObject("message","Problem on updated data"); 
		}
		mav.addObject("deptscheme", dsms.getDeptSchemeMaster());
	
		return mav;
	}

}
