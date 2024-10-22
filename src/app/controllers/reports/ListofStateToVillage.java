package app.controllers.reports;

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

import app.bean.LastActionLogBean;
import app.bean.Login;
import app.bean.StateToVillageBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.ListofStateToVillageService;

@Controller("ListofStateToVillage")
public class ListofStateToVillage {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public ListofStateToVillageService stvService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> blockList;
	private Map<String, String> gpList;
	
	@RequestMapping(value="/listofStateToVillge", method = RequestMethod.GET)
	public ModelAndView listofsttovill(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String unviewlgd= request.getParameter("unviewlgd");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("reports/ListofStateToVillage");
			mav.addObject("menu", menuController.getMenuUserId(request));
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
			blockList = stvService.getBlockList(Integer.parseInt(userState), Integer.parseInt(district));
			mav.addObject("blockList", blockList);}
			mav.addObject("block", block);
			
			if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
				gpList = stvService.getGPList( Integer.parseInt(block));
				mav.addObject("gpList", gpList);}
			//	mav.addObject("block", block);
				
				mav.addObject("unviewlgd", unviewlgd);
			
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/ListofStateVill", method = RequestMethod.POST)
	public ModelAndView ListofStateVill(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String userType=session.getAttribute("userType").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String gp= request.getParameter("gp");
		String unviewlgd= request.getParameter("unviewlgd");
		System.out.println("district="+district+", block= "+block);
		
		List<StateToVillageBean> list=new  ArrayList<StateToVillageBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("reports/ListofStateToVillage");
			
			list=stvService.getListofStateToVill(Integer.parseInt(userState), Integer.parseInt(district),  
					Integer.parseInt(block),  Integer.parseInt(gp), unviewlgd, userType);
			mav.addObject("Listofstatetovill", list);
			
		
			/*
			 * for(int i=0;i<list.size();i++) {
			 * System.out.println(list.get(i).getSt_name()+"= kedar"); }
			 */
			 
			
			mav.addObject("menu", menuController.getMenuUserId(request));
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equalsIgnoreCase(""))
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") )
			blockList = stvService.getBlockList(Integer.parseInt(userState), Integer.parseInt(district));
			mav.addObject("blockList", blockList);
			mav.addObject("block", block);
			
			if( block!=null && !block.equalsIgnoreCase("") )
				gpList = stvService.getGPList( Integer.parseInt(block));
				mav.addObject("gpList", gpList);
				mav.addObject("gp", gp);
			
				mav.addObject("unviewlgd", unviewlgd);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	

}
