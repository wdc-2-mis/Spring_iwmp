package app.controllers.pfms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.VillageList;
import app.bean.pfms.PfmsInvoiceData;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.service.AddProjectService;
import app.service.PfmsInvoiceService;
import app.service.VillageMasterService;


@Controller("PfmsStateProjectController")
public class PfmsStateProjectController {
	
HttpSession session;
	
	@Autowired(required = true)
	PfmsInvoiceService pfmsService;
	@Autowired(required = true)
	private AddProjectService projectService;
	
	
	@RequestMapping(value = "/pfmsStateMapping", method = RequestMethod.GET)
	public ModelAndView getStateLevelInvoices(HttpServletRequest request) {
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			ModelAndView mav = new ModelAndView();
			mav = new ModelAndView("/pfms/projectMapping");
			return gotomain(mav, session);

		} else {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			return mav;
		}
	}
	
	public  ModelAndView gotomain(ModelAndView mav,HttpSession session) {
		 VillageList villageList = new VillageList();
			List<IwmpVillage> vList = null;
			List<IwmpBlock> bList = null;
			List<IwmpGramPanchayat> gList = null;
			villageList.setInactivevillageList(vList);
			villageList.setInactiveblockList(bList);
			villageList.setInactivegpList(gList);

			int stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			villageList.setStateCode(stcode);
		
			mav.addObject("projectList", projectService.getListSanctionedProjectStatus(stcode,0,0,"C"));
			mav.addObject("invoiceData", pfmsService.getPfmsInvoiceStateData(stcode));
			return mav;

	   }

}
