package app.controllers.baseline;

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
import app.bean.Login;
import app.bean.PhysicalActBean;
import app.model.MBlsOutType;
import app.model.master.IwmpMPhySubactivity;
import app.service.BaselineTypeService;

@Controller("baselineTypeMasterController")
public class BaselineTypeMasterController {

	HttpSession session;
	
	@Autowired(required = true)
	BaselineTypeService baselineTypeService;
	
	@RequestMapping(value = "/baselinetypemaster", method = RequestMethod.GET)
	public ModelAndView modifyBaselineTypeList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("baseline/baselinetypemaster");
			mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}

	@RequestMapping(value = "/savebaseLinetypeMasterData", method = RequestMethod.POST)
	public ModelAndView savebaseLinetypeMasterData(HttpServletRequest request, HttpServletResponse response, @RequestParam("typecode")String typecode,
			@RequestParam("desc")String desc, @RequestParam("seqno")BigDecimal seqno)	
	{
		//System.out.println("act code:" +actdesc);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("baseline/baselinetypemaster");
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = baselineTypeService.savebaselinetypedata(typecode,desc,seqno,session.getAttribute("loginID").toString());
			if(res=="success") {
				  mav.addObject("message","Data has been saved successfully");
			}
				  else {
					  mav.addObject("message","Data can not saved because seqno already used");
					  mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
				  }
				  }else { mav.addObject("message","Problem on insert data"); 
				  }
		mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
	
		return mav;
	}

	@RequestMapping(value="/baselinetypefind", method = RequestMethod.GET)
	@ResponseBody
	public List<BaselineMasterBean> baselinetypefind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		session = request.getSession(true);
		List<MBlsOutType> proj = new ArrayList<MBlsOutType>();
		List<BaselineMasterBean> finalList = new ArrayList<BaselineMasterBean>();
		BaselineMasterBean bean = new BaselineMasterBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = baselineTypeService.findbaselinetypedesc(id);
			for(MBlsOutType list : proj) {
			bean = new BaselineMasterBean();
			bean.setTypecode(list.getTypeCode());
			bean.setBdescription(list.getDescription());
			bean.setSeqno(list.getSeqNo());
			finalList.add(bean);
			}
			
		}else {
			proj=null;
			
		}
		return finalList;  
	}

	@RequestMapping(value = "/updatebaselinetypeData", method = RequestMethod.POST)
	public ModelAndView updatebaselinetypeData(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")int id,
			@RequestParam("typecode")String typecode, @RequestParam("desc")String desc, @RequestParam("seqno")BigDecimal seqno)	
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("baseline/baselinetypemaster");
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = baselineTypeService.updatebaselinetypedata(id,typecode,desc,seqno,session.getAttribute("loginID").toString());
			if(res=="success") {
				  mav.addObject("message","Activity Code:"+" "+id +" "+"has been updated successfully");
			}
				  else {
					  mav.addObject("message","Could not update record because seqno already used");
					  mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
				  }
				  }else { mav.addObject("message","Problem on updated data"); 
				  }
		mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
	
		return mav;
	}

	@RequestMapping(value="/deletebaselinetypeData", method = RequestMethod.POST)
	public ModelAndView deletebaselinetypeData(HttpServletRequest request, HttpServletResponse response)
	{
		int id=0;
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("baseline/baselinetypemaster");
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = baselineTypeService.deletebaselinetype(id);
				if(flag) {
				mav.addObject("message","Data has been removed successfully");
				}
				else {
					mav.addObject("message","Could not delete record because this record exist on transaction table");
					mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
				}
			}
			else {
				mav.addObject("message","Unable to remove data");
			}
			mav.addObject("baselinetype",baselineTypeService.getbaselinetypedata());
		}catch(Exception ex) {
			ex.printStackTrace();
		
		}
		return mav; 
	}
}
