package app.controllers.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.reports.UserUploadDetailsBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.UserUploadDetailServices;

@Controller("UserUploadDetailsReport")
public class UserUploadDetailsReport {
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;

	@Autowired(required = true)
	public UserUploadDetailServices uds;
	
	private Map<Integer, String> stateList;
	
	@RequestMapping(value = "/userUploadDetailsReport", method = RequestMethod.GET)
	public ModelAndView UserUploadDetails(HttpServletRequest request, HttpServletResponse response) 
	{
		ModelAndView mav=null;
		try {
			mav = new ModelAndView();
			mav = new ModelAndView("reports/userUploadDetailsReport");
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getUploadDtlRpt", method = RequestMethod.POST)
	public ModelAndView getUploadDtlRpt(HttpServletRequest request, HttpServletResponse response) 
	{

		String data[] = null;
		int i=1;
		String stname = "";
		ModelAndView mav = new ModelAndView();
		List<String[]> dataList = new ArrayList<String[]>();
		
				mav = new ModelAndView("reports/userUploadDetailsReport");
				String stCode=request.getParameter("state");
				stateList=stateMasterService.getAllState();
				
				List<UserUploadDetailsBean> list = new ArrayList<UserUploadDetailsBean>();
				list = uds.getListofUploadedDetailsSLNAReport(Integer.parseInt(stCode));
				
				for (UserUploadDetailsBean bean : list) 
				{
						data = new String[14];
						
						data[1] = bean.getSt_code().toString();	// st_code
						if (stname.isEmpty() || !stname.equals(bean.getState_name())) 
						{
							data[0] = String.valueOf(i); // serial no
							data[2] = bean.getState_name();	// statename
							stname = bean.getState_name();
							i++;
						} 
						else {
							data[0] = "";
							data[2] = "";
						}	
						
						data[3] = bean.getId().toString();
						data[4] = bean.getUpload();
						data[5] = bean.getSubject();
						data[6] = bean.getFile_name();
						data[7] = bean.getIs_new().toString();
						String path="http://wdcpmksy.dolr.gov.in";  
						data[8] = path+bean.getFile_full_path();
						data[9] = bean.getPublish().toString();
						data[10] = bean.getUpload_category_id().toString();
						data[11] = bean.getFile_extension();
						data[12] = bean.getUpdated_date();
						data[13] = bean.getUpdatesixty();
		
						dataList.add(data);
				}
				mav.addObject("stateList", stateList);
				mav.addObject("dataList", dataList);
				mav.addObject("dataListsize", dataList.size());
				mav.addObject("state", stCode);
				
			
		
		return mav;
	}

}
