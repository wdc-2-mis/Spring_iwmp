package app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import app.bean.User;
import app.bean.UserBean;
import app.common.CommonFunctions;
import app.model.UserMap;
import app.model.UserReg;
import app.service.ProfileService;
import app.service.UserService;
import app.util.CommonUtility;
import java.io.OutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;

@Controller("registrationController")
public class RegistrationController {
	@Autowired(required = true)
	public UserService userService;

	@Autowired
	public CommonUtility utility;

	@Autowired
	public UserReg userReg;

	@Autowired
	public UserMap userMap;

	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired(required = true)
	ProfileService profileService;

	HttpSession session;
	private Map<String, String> userTypeList;
	private Map<String, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
//	private Logger logger=Logger.getRootLogger();

	// showRegisterl is use to get the registration page
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegisterl(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("userBean") UserBean userBean) 
	{
			ModelAndView mav = new ModelAndView("register");
			userTypeList = new HashMap<String, String>();
			userTypeList=commonFunction.getUserTypeList();
			mav.addObject("userTypeList", userTypeList); 
		
		return mav;
	}

	// showRegister use to get the statelist, districtlist on user type change
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userBean") UserBean userBean) 
	{
		ModelAndView mav = new ModelAndView("register");
		String userType = userBean.getUserType();
		String userState = userBean.getUserState();
		String userDistrict = userBean.getUserDistrict();
		String userProject = userBean.getUserProject();
		userTypeList = new HashMap<String, String>();
		if (userType != null) 
		{
			stateList = userService.getStateList();
			if (userType.equalsIgnoreCase("DI") || userType.equalsIgnoreCase("PI") || userType.equalsIgnoreCase("NGO")) 
			{
				if (userState != null) 
				{
					if (!userState.equalsIgnoreCase(""))
						districtList = userService.getDistrictList(Integer.parseInt(userState));
				}
			}
			/*
			 * if(userType.equalsIgnoreCase("PI")) { if(userState!=null &&
			 * userDistrict!=null) { if(!userState.equalsIgnoreCase("--Select--") &&
			 * !userDistrict.equalsIgnoreCase("--Select--"))
			 * projectList=userService.getProjectList(Integer.parseInt(userState),
			 * Integer.parseInt(userDistrict)); } } userregid
			 */
		}
		userTypeList.putAll(commonFunction.getUserTypeList());
		mav.addObject("user", new User());
		mav.addObject("userType", userType);
		mav.addObject("userState", userState);
		mav.addObject("userDistrict", userDistrict);
		mav.addObject("userProject", userProject);

		mav.addObject("userTypeList", userTypeList);
		mav.addObject("stateList", stateList);
		mav.addObject("districtList", districtList);
		mav.addObject("projectList", projectList);
		return mav;
	}

	public Map<String, String> getUserTypeList() {
		return userTypeList;
	}

	public void setUserTypeList(Map<String, String> userTypeList) {
		this.userTypeList = userTypeList;
	}

	// addUser is use to save the user details to data base
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("user") User user, @ModelAttribute("userBean") UserBean userBean) 
	{
		session = request.getSession(true);
		String capcha= userBean.getInputCaptcha();
		String userType = userBean.getUserType();
		// userService.register(user);
		String captcha=(String)session.getAttribute("CAPTCHA");
		if(captcha!=null && !captcha.equals(userBean.getInputCaptcha()))
		{
			userBean.setInputCaptcha(""); 
			ModelAndView model = new ModelAndView("register");
			model.addObject("message","Captcha does not match"); 
			userTypeList.putAll(commonFunction.getUserTypeList());
			model.addObject("userTypeList", userTypeList);
			return model;
		}
		  
		userReg = utility.createUserRegModel(userBean, request);
		//userMap = utility.createUserRegModel(userBean, request);
		userService.saveUserReg(userReg);
		ModelAndView mav = new ModelAndView("register");
		userTypeList = new HashMap<String, String>();
		userTypeList=commonFunction.getUserTypeList();
		mav.addObject("userTypeList", userTypeList);
		
		mav.addObject("messagerj", "Registration form submit successfully."); 
		mav.addObject("regid", "WDC-PMKSY-000"+userReg.getRegId());
		if(userType.equals("PI")) 
		{
			mav.addObject("message1", "Kindly forward registration id to your SLNA for activation ");
		}
		else {
			mav.addObject("message1", "Kindly forward your registration id to support help desk of DOLR(support-wdcpmksy@nic.in)");
		}
		//mav.addObject("userType", userType);
		
		return mav;
	}
	
	@RequestMapping(value = "/showRegistration", method = RequestMethod.GET)
	public ModelAndView showRegistration(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("userBean") UserBean userBean) 
	{
			ModelAndView mav = new ModelAndView("registerView");
			return mav;
	}
	
	@RequestMapping(value = "/viewNewRegPDF", method = RequestMethod.POST)
	public ModelAndView viewNewRegPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String uname=null;
		String regid=request.getParameter("newuserRId");
		ModelAndView mav = new ModelAndView("register");
		List<UserReg> list=new  ArrayList<UserReg>();
		
		try {
			
			String c= regid;
	        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
	        Matcher match= pt.matcher(c);
	        while(match.find())
	        {
	            String s= match.group();
	            c=c.replaceAll("\\"+s, "");
	        }
			String str = c.toUpperCase();
			String regID = str.replaceAll("([A-Z])", "");
		//	System.out.println("kdy="+regID);
			String strPattern = "^0+(?!$)";
		    String newreg = regID.replaceAll(strPattern, "");
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(249, 232, 239));
			Document document = new Document(layout, 25, 10, 0, 0);
			document.addTitle("View New Registration PDF");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Font f = new Font(FontFamily.HELVETICA, 15.0f, Font.BOLD, new BaseColor(0, 100, 33));
			Font f1 = new Font(FontFamily.HELVETICA, 15.0f, Font.BOLDITALIC );
			Font f2 = new Font(FontFamily.HELVETICA, 12.0f, Font.BOLD, new BaseColor(0, 100, 0));
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));

			PdfPTable table = null;
			document.newPage();
			 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources\n Ministry of Rural Development\n", f1);
			Paragraph paragraph = new Paragraph("Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)", f);
			Paragraph paragraph3 = new Paragraph("View New Registration PDF", f2);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			document.add(paragraph2);
			document.add(paragraph);
			document.add(paragraph3);

			table = new PdfPTable(7);
			table.setWidths(new int[] {  8, 8, 5, 5, 5, 5, 10 });
			table.setWidthPercentage(70);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
			if(regid!=null)
			list=profileService.getNewUserDetail(Integer.parseInt(regID));
				
			CommonFunctions.insertCellHeader(table, "Registration Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Department", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Designation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Email", Element.ALIGN_CENTER, 1, 1,bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mobile", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Address", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			if(list.size()!=0)
			for(int i=0;i<list.size();i++) 
			{
				uname=list.get(i).getUserName().toUpperCase();
				CommonFunctions.insertCell(table, "WDC-PMKSY-000"+newreg, Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, uname, Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getDepartment(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getDesignation(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getEmail(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getMobileNo(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getCurAddress(), Element.ALIGN_LEFT, 1, 1, bf8);
			}
			if(list.size()==0)
				CommonFunctions.insertCell(table, "Registration Id not match/ Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
			
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"Report generated by WDC-PMKSY software on "+ 
					CommonFunctions.dateToString(null, "dd/MMM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=View_New_Registration.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
		
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		
		return mav;
	}
	
}
