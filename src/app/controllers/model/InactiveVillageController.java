package app.controllers.model;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProjectState;
import app.bean.VillageList;
import app.controllers.MenuController;
import app.model.IwmpMProject;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpVillage;
import app.service.BlockMasterService;
import app.service.CommonService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;
import app.service.VillageMasterService;
import app.service.reports.ListofStateToVillageService;

@Controller
public class InactiveVillageController {

	HttpSession session;

	@Autowired(required = true)
	public ListofStateToVillageService stvService;
	@Autowired(required = true)
	StateMasterService stateMasterService;
	@Autowired
	DistrictMasterService districtMasterService;
	@Autowired
	BlockMasterService blockMasterService;
	@Autowired
	VillageMasterService villageMasterService;
	@Autowired
	CommonService commonService;
	@Autowired(required = true)
	MenuController menuController;

	@RequestMapping(value = "/inactiveVillage", method = RequestMethod.GET)
	public String InactiveVillageList(Locale locale, Model model, HttpServletRequest request) {
		//System.out.print("hello");

		session = request.getSession(true);
		//System.out.print("hello");

		if (session != null && session.getAttribute("loginID") != null) {
			// villageList=new VillageList();
			VillageList villageList = new VillageList();
			List<IwmpVillage> vList = null;
			List<IwmpBlock> bList = null;
			List<IwmpGramPanchayat> gList = null;
			villageList.setInactivevillageList(vList);
			villageList.setInactiveblockList(bList);
			villageList.setInactivegpList(gList);

			int stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			villageList.setStateCode(stcode);
			model.addAttribute("menu", menuController.getMenuUserId(request));
			model.addAttribute("statelist", stateMasterService.getAllState());
			model.addAttribute("villageDetail", villageList);
			model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(stcode));
			model.addAttribute("blockList", blockMasterService.getBlockByDistCode(villageList.getBlockCode()));
			// model.addAttribute("gpList",
			// stvService.getGPList(villageList.getBlockCode()));
			return "inActiveMaster/InactiveVillage";

		} else {
			model.addAttribute("login", new Login());
			return "login";
		}
		// return mav;
	}

	@RequestMapping(value = "/activateVillage", params = "list", method = RequestMethod.POST)
	public String activateVillageList(@ModelAttribute("villageDetail") VillageList villageList, BindingResult result,
			Model model, HttpServletRequest request) {
		//System.out.println("hi");
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			int stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			villageList.setStateCode(stcode);
			VillageList temp = new VillageList();
			//System.out.println("else()" + villageList.getDirectoryLevel());
			if (villageList.getDirectoryLevel() < 1) {
				model.addAttribute("error", "Select Directory Level");
				return "inActiveMaster/InactiveVillage";
			}
			if (villageList.getDirectoryLevel() == 3) {
				List<IwmpVillage> villages = villageMasterService.getVillageList(stcode, villageList.getDistCode(),
						villageList.getBlockCode(), villageList.getGrampanchayatCode());
				temp.setInactivevillageList(villages);
			}
			if (villageList.getDirectoryLevel() == 1) {
				List<IwmpBlock> blockes = blockMasterService.getInactiveBlockList(stcode, villageList.getDistCode(),
						villageList.getBlockCode());
				temp.setInactiveblockList(blockes);
			}
			if (villageList.getDirectoryLevel() == 2) {
				List<IwmpGramPanchayat> gps = commonService.getInactiveGramPanchayatList(stcode,
						villageList.getDistCode(), villageList.getBlockCode());
				temp.setInactivegpList(gps);
			}

			//System.out.println("final()");

			temp.setBlockCode(villageList.getBlockCode());
			temp.setDirectoryLevel(villageList.getDirectoryLevel());
			temp.setDistCode(villageList.getDistCode());
			temp.setGrampanchayatCode(villageList.getGrampanchayatCode());
			temp.setStateCode(villageList.getStateCode());
			model.addAttribute("villageDetail", temp);
			model.addAttribute("statelist", stateMasterService.getAllState());
			model.addAttribute("districtList",
					districtMasterService.getDistrictByStateCode(villageList.getStateCode()));
			model.addAttribute("blockList", blockMasterService.getBlockByDistCode(villageList.getDistCode()));
			model.addAttribute("gpList", stvService.getGPList(villageList.getBlockCode()));
			return "inActiveMaster/InactiveVillage";
		} else {
			model.addAttribute("login", new Login());
			return "login";
		}
	}

	@RequestMapping(value = "/activateVillage", params = "action2", method = RequestMethod.POST)
	public String OnFinalized(@ModelAttribute("villageDetail") VillageList villageList, BindingResult result,
			Model model, HttpServletRequest request) {
		//System.out.println("savevillage()");
		String msg="";
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			int stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			villageList.setStateCode(stcode);
			VillageList tempv = new VillageList();
			int temp = 0;
			// projectDetail = new ProjectState();
			if (villageList.getDirectoryLevel() == 3) {
				List<IwmpVillage> tempvi = null;
				if (villageList.getInactivevillageList() != null && villageList.getInactivevillageList().size() > 0) {
					tempvi = villageList.getInactivevillageList().stream().filter(p -> p.getUpdatestatus())
							.collect(Collectors.toList());
					//System.out.print("tot selec vill===" + tempvi.size());
					if (tempvi.size() > 0) {
						villageMasterService.updateVillageList(tempvi);
					}
				}
				temp=tempvi.size();
				List<IwmpVillage> villages = villageMasterService.getVillageList(stcode, villageList.getDistCode(),
						villageList.getBlockCode(), villageList.getGrampanchayatCode());
				tempv.setInactivevillageList(villages);
				msg="Village";
				
			}
			else if (villageList.getDirectoryLevel() == 2) {
				List<IwmpGramPanchayat> tempvi = null;
				if (villageList.getInactivegpList() != null && villageList.getInactivegpList().size() > 0) {
					tempvi = villageList.getInactivegpList().stream().filter(p -> p.getUpdatestatus())
							.collect(Collectors.toList());
				//	System.out.print("tot selec vill===" + tempvi.size());
					if (tempvi.size() > 0) {
						commonService.updateGramPanchayatList(tempvi);
					}
				}
				temp=tempvi.size();
				msg="Gram Panchayat";
				List<IwmpGramPanchayat> gps = commonService.getInactiveGramPanchayatList(stcode,
						villageList.getDistCode(), villageList.getBlockCode());
				tempv.setInactivegpList(gps);
			}
			else if (villageList.getDirectoryLevel() == 1) {
				List<IwmpBlock> tempvi = null;
				if (villageList.getInactiveblockList() != null && villageList.getInactiveblockList().size() > 0) {
					tempvi = villageList.getInactiveblockList().stream().filter(p -> p.getUpdatestatus())
							.collect(Collectors.toList());
					//System.out.print("tot selec vill===" + tempvi.size());
					if (tempvi.size() > 0) {
						blockMasterService.updateBlockList(tempvi);
					}
				}
				temp=tempvi.size();
				msg="Blocks";
				List<IwmpBlock> blockes = blockMasterService.getInactiveBlockList(stcode, villageList.getDistCode(),
						villageList.getBlockCode());
				tempv.setInactiveblockList(blockes);
			}

			model.addAttribute("statelist", stateMasterService.getAllState());
			model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(stcode));
			model.addAttribute("blockList", blockMasterService.getBlockByDistCode(villageList.getDistCode()));
			model.addAttribute("gpList", stvService.getGPList(villageList.getBlockCode()));

			
			if (temp > 0) {
				villageList = new VillageList();
				
				model.addAttribute("msg", "Selected "+msg+" have been activated Successfully");
				model.addAttribute("villageDetail", villageList);
				return "inActiveMaster/InactiveVillage";
			} else {
				tempv.setBlockCode(villageList.getBlockCode());
				tempv.setDistCode(villageList.getDistCode());
				tempv.setGrampanchayatCode(villageList.getGrampanchayatCode());
				tempv.setStateCode(stcode);
			
//				tempv.setInactivevillageList(villageList.getInactivevillageList());
//				tempv.setInactiveblockList(villageList.getInactiveblockList());
//				tempv.setInactivegpList(villageList.getInactivegpList());
				tempv.setDirectoryLevel(villageList.getDirectoryLevel());
				model.addAttribute("villageDetail", tempv);
				model.addAttribute("error", "Select at least one "+ msg);
				return "inActiveMaster/InactiveVillage";
			}
		} else {
			model.addAttribute("login", new Login());
			return "login";
		}
	}
}
