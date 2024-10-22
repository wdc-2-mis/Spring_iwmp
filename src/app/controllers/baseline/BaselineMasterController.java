package app.controllers.baseline;

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

import app.bean.BaselineMasterBean;
import app.bean.Login;

import app.model.MBlsOutcome;
import app.service.BaselineTypeService;

@Controller("baselineMasterController")
public class BaselineMasterController {

	HttpSession session;
	
	@Autowired(required = true)
	BaselineTypeService baselineTypeService;
	
	@RequestMapping(value = "/baselinesurveymaster", method = RequestMethod.GET)
	public ModelAndView modifyProjectList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("baseline/baselinemaster");
			mav.addObject("baseline",baselineTypeService.getbaselinedata());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}

	@RequestMapping(value = "/gettypecode", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> gettypecode(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();  
		session = request.getSession(true);
        LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=baselineTypeService.gettypeCode();
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return map; 
	}
	
	@RequestMapping(value = "/savebaseLineMasterData", method = RequestMethod.POST)
	public ModelAndView savebaseLinetypeMasterData(HttpServletRequest request, HttpServletResponse response, @RequestParam("btype")Integer btype,
			@RequestParam("bdesc")String bdesc, @RequestParam("seqno")BigDecimal seqno)	
	{
		//System.out.println("act code:" +actdesc);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("baseline/baselinemaster");
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = baselineTypeService.savebaselinedata(btype,bdesc,seqno,session.getAttribute("loginID").toString());
			if(res=="success") {
				  mav.addObject("message","Data has been saved successfully");
			}
				  else {
					  mav.addObject("message","Data can not saved because seqno already used");
					  mav.addObject("baseline",baselineTypeService.getbaselinedata());
				  }
				  }else { mav.addObject("message","Problem on insert data"); 
				  }
		mav.addObject("baseline",baselineTypeService.getbaselinedata());
	
		return mav;
	}
	
	@RequestMapping(value="/baselinefind", method = RequestMethod.GET)
	@ResponseBody
	public List<BaselineMasterBean> baselinefind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		session = request.getSession(true);
		List<MBlsOutcome> proj = new ArrayList<MBlsOutcome>();
		List<BaselineMasterBean> finalList = new ArrayList<BaselineMasterBean>();
		BaselineMasterBean bean = new BaselineMasterBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = baselineTypeService.findbaselinedesc(id);
			for(MBlsOutcome list : proj) {
			bean = new BaselineMasterBean();
			bean.setBldescription(list.getMBlsOutType().getMBlsOutTypeIdPk());
			bean.setDescription(list.getDescription());
			bean.setSeqno(list.getSeqNo());
			finalList.add(bean);
			}
			
		}else {
			proj=null;
			
		}
		return finalList;  
	}

	@RequestMapping(value = "/updatebaselineData", method = RequestMethod.POST)
	public ModelAndView updatebaselinetypeData(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int id,
			@RequestParam("typedesc")int typedesc, @RequestParam("baslinedesc")String baslinedesc, @RequestParam("seqno")BigDecimal seqno)	
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("baseline/baselinemaster");
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = baselineTypeService.updatebaselinedata(id,typedesc,baslinedesc,seqno,session.getAttribute("loginID").toString());
			if(res=="success") {
				  mav.addObject("message","Activity Code has been updated successfully");
			}
				  else {
					  mav.addObject("message","Could not update record because seqno already used");
					  mav.addObject("baselinetype",baselineTypeService.getbaselinedata());
				  }
				  }else { mav.addObject("message","Problem on updated data"); 
				  }
		mav.addObject("baseline",baselineTypeService.getbaselinedata());
		return mav;
	}

	@RequestMapping(value="/deletebaselineData", method = RequestMethod.POST)
	public ModelAndView deletebaselineData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("baseline/baselinemaster");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = baselineTypeService.deletebaseline(id);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("baseline",baselineTypeService.getbaselinedata());
				}
			}
			else {
				mav.addObject("message","Unable to remove data");
			}
			mav.addObject("baseline",baselineTypeService.getbaselinedata());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}	
	
}
