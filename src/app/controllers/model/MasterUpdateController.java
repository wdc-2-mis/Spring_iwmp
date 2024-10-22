package app.controllers.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.VillageList;
import app.controllers.MenuController;
import app.model.IwmpDistrict;
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
public class MasterUpdateController {

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
	

	@RequestMapping(value = "/masterlist", method = RequestMethod.GET)
	public ModelAndView MasterList(Locale locale, Model model, HttpServletRequest request) {
			session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			ModelAndView mav = new ModelAndView();
			mav = new ModelAndView("master/masterModify");
			return gotomain(mav, session);

		} else {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			return mav;
		}
	}

	@RequestMapping(value = "/updateMasterList", params = "list", method = RequestMethod.POST)
	public String updateMasterList(@ModelAttribute("villageDetail") VillageList villageList, BindingResult result,
			Model model, HttpServletRequest request) {
		//System.out.println("hi");
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			int stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			if(stcode!=0)
				villageList.setStateCode(stcode);
			VillageList temp = new VillageList();
			//System.out.println("else()" + villageList.getDirectoryLevel());
			if (villageList.getDirectoryLevel() < 1) {
				model.addAttribute("error", "Select Directory Level");
				return "master/masterModify";
			}
			if(villageList.getStateCode()<1) {
				model.addAttribute("error", "Select State");
				model.addAttribute("statelist", stateMasterService.getAllState());
				return "master/masterModify";
			}
			if (villageList.getDirectoryLevel() == 4) {
				if(villageList.getDistCode()<1)
				{
					model.addAttribute("error", "Select District ");
					model.addAttribute("statelist", stateMasterService.getAllState());
					model.addAttribute("districtList",
							districtMasterService.getDistrictByStateCode(villageList.getStateCode()));
					model.addAttribute("blockList", blockMasterService.getBlockByDistCode(villageList.getDistCode()));
					return "master/masterModify";
				}
				if(villageList.getBlockCode()<1)
				{
					model.addAttribute("error", "Select Block ");
					model.addAttribute("statelist", stateMasterService.getAllState());
					model.addAttribute("districtList",
							districtMasterService.getDistrictByStateCode(villageList.getStateCode()));
					model.addAttribute("blockList", blockMasterService.getBlockByDistCode(villageList.getDistCode()));
					return "master/masterModify";
				}
				List<IwmpVillage> villages = villageMasterService.getActiveVillageList(villageList.getStateCode(),
						villageList.getDistCode(), villageList.getBlockCode(), villageList.getGrampanchayatCode());
				temp.setInactivevillageList(villages);
			}
			if (villageList.getDirectoryLevel() == 2) {
				
				List<IwmpBlock> blockes = blockMasterService.getActiveBlockList(villageList.getStateCode(), villageList.getDistCode(),
						villageList.getBlockCode());
				temp.setInactiveblockList(blockes);
			}
			if (villageList.getDirectoryLevel() == 3) {
				List<IwmpGramPanchayat> gps = commonService.getActiveGramPanchayatList(villageList.getStateCode(),
						villageList.getDistCode(), villageList.getBlockCode());
				temp.setInactivegpList(gps);
			}
			if (villageList.getDirectoryLevel() == 1) {
				List<IwmpDistrict> dist=null;
				if(villageList.getDistCode()>0)
				{
					dist=districtMasterService.getDistrictListDistCode(villageList.getDistCode());
				}
				else
					{
					dist = districtMasterService.getDistrictListState(stcode);
					
					}
				temp.setDistrictList(dist);
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
			return "master/masterModify";
		} else {
			model.addAttribute("login", new Login());
			return "login";
		}
	}

	@RequestMapping(value = "/mstrvilldatafind", method = RequestMethod.GET)
	@ResponseBody
	public String[] mstrvilldatafind(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id, @RequestParam(value = "lvl") Integer lvl) {
		session = request.getSession(true);
		String[] temp = new String[4];

		if (session != null && session.getAttribute("loginID") != null) {

			if (lvl == 4) {
				IwmpVillage village = villageMasterService.findVillageVcode(id);

				if (village != null) {
					temp[0] = Integer.toString(village.getVcode());
					temp[1] = village.getVillageName();
					temp[2] = "4";
					temp[3] = Integer.toString(village.getIwmpGramPanchayat().getGcode());
				}
			}
			if (lvl == 2) {
				IwmpBlock block = blockMasterService.findBlockBcode(id);

				if (block != null) {
					temp[0] = Integer.toString(block.getBcode());
					temp[1] = block.getBlockName();
					temp[2] = "2";
					temp[3] = Integer.toString(block.getIwmpDistrict().getDcode());
				}
			}
			if (lvl == 3) {
				IwmpGramPanchayat gp = commonService.findGramPanchaytGcode(id);

				if (gp != null) {
					temp[0] = Integer.toString(gp.getGcode());
					temp[1] = gp.getGramPanchayatName();
					temp[2] = "3";
					temp[3] = Integer.toString(gp.getIwmpBlock().getBcode());
				}
			}
			if (lvl == 1) {
				IwmpDistrict dist = districtMasterService.findDistrictDcode(id);

				if (dist != null) {
					temp[0] = Integer.toString(dist.getDcode());
					temp[1] = dist.getDistName();
					temp[2] = "1";
				}
			}

		} else {

		}
		return temp;
	}

	@RequestMapping(value = "/updateVillageData", method = RequestMethod.POST)
	public ModelAndView updateactData(HttpServletRequest request, HttpServletResponse response) {

		int id = Integer.parseInt(request.getParameter("id1"));
		String villageName = request.getParameter("villageName1");
		int lvl = Integer.parseInt(request.getParameter("lvl1"));
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("master/masterModify");
		Boolean flag = false;

		try {
			session = request.getSession(true);
			if (session != null && session.getAttribute("loginID") != null) {
				if (lvl == 4) {
					int gpCode = Integer.parseInt(request.getParameter("grmPnchyt"));
					IwmpVillage village = new IwmpVillage();
					IwmpGramPanchayat gp = new IwmpGramPanchayat();
					village.setVillageName(villageName);
					village.setVcode(id);
					gp.setGcode(gpCode);
					village.setIwmpGramPanchayat(gp);

					flag = villageMasterService.updateVillageList(village);
					if (flag) {
						mav.addObject("msg", "Village:" + " " + villageName + " " + "has been updated successfully");
					} else {
						mav.addObject("error", "Could not update Village Detail");
						mav.addObject("statelist", stateMasterService.getAllState());
						// mav.addObject("phyact", physicalActservice.getPhyActdata());
					}
				}
				else if (lvl == 2) {
					IwmpBlock blk = new IwmpBlock();
					IwmpDistrict dist = new IwmpDistrict();
					int dCode = Integer.parseInt(request.getParameter("distCode"));
					blk.setBlockName(villageName);
					blk.setBcode(id);
					dist.setDcode(dCode);
					blk.setIwmpDistrict(dist);

					flag = blockMasterService.updateBlock(blk);
					if (flag) {
						mav.addObject("msg", "Block:" + " " + villageName + " " + "has been updated successfully");
					} else {
						mav.addObject("error", "Could not update Block Detail");
						mav.addObject("statelist", stateMasterService.getAllState());
						// mav.addObject("phyact", physicalActservice.getPhyActdata());
					}
				}
				else if (lvl == 3) {
					IwmpGramPanchayat gp = new IwmpGramPanchayat();
					IwmpBlock blk = new IwmpBlock();
					int bCode =  Integer.parseInt(request.getParameter("blkCode"));
					gp.setGramPanchayatName(villageName);
					gp.setGcode(id);
					blk.setBcode(bCode);
					gp.setIwmpBlock(blk);

					flag = commonService.updateGramPanchayatList(gp);
					if (flag) {
						mav.addObject("msg", "Gram Panchayat:" + " " + villageName + " " + "has been updated successfully");
					} else {
						mav.addObject("error", "Could not update Gram Panchyat Detail");
						mav.addObject("statelist", stateMasterService.getAllState());
						// mav.addObject("phyact", physicalActservice.getPhyActdata());
					}
				}
				else if (lvl == 1) {
					IwmpDistrict dist = new IwmpDistrict();
					dist.setDistName(villageName);
					dist.setDcode(id);

					flag = districtMasterService.updateDistrict(dist);
					if (flag) {
						mav.addObject("msg", "District:" + " " + villageName + " " + "has been updated successfully");
					} else {
						mav.addObject("error", "Could not update Distrcit Detail");
						mav.addObject("statelist", stateMasterService.getAllState());
						// mav.addObject("phyact", physicalActservice.getPhyActdata());
					}
				}
			} else {
				mav.addObject("msg", "Problem on updated data");
			}

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return gotomain(mav,session);
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
			mav.addObject("statelist", stateMasterService.getAllState());
			mav.addObject("villageDetail", villageList);
			mav.addObject("districtList", districtMasterService.getDistrictByStateCode(stcode));
			mav.addObject("blockList", blockMasterService.getBlockByDistCode(villageList.getBlockCode()));
			return mav;

	   }
	 
	 @RequestMapping(value = "/getGPListWithLgdCode", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<String,Integer> getGramPanchayatList (HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value = "id") Integer id, @RequestParam(value = "lvl") Integer lvl) {
		 
		 session = request.getSession(true);
		 LinkedHashMap<String,Integer> map = new LinkedHashMap<>();
		 List<IwmpDistrict> districtList=new ArrayList<IwmpDistrict>();
		 if (session != null && session.getAttribute("loginID") != null) {
			 if (lvl == 4) {
				 IwmpVillage village = villageMasterService.findVillageVcode(id);
				 map = stvService.getGPListWithLgdCode(village.getIwmpGramPanchayat().getIwmpBlock().getBcode());
			 } if (lvl == 3) {
				 IwmpGramPanchayat gp = commonService.findGramPanchaytGcode(id);
				 map = blockMasterService.getBlockWithBlockLgdByDistCode(gp.getIwmpBlock().getIwmpDistrict().getDcode());
			 }if (lvl ==2) {
				 IwmpBlock block = blockMasterService.findBlockBcode(id);
				 districtList =districtMasterService.getDistrictListState(block.getIwmpDistrict().getIwmpState().getStCode());
				 for(IwmpDistrict dist : districtList) {
					 map.put( dist.getDistName()+" ("+dist.getDistrictCodelgd()+")", dist.getDcode());
				 }
			 }
		 }
		 
		 return map;
		 
	 }
	 
	 @RequestMapping(value = "/getAllStatesList", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<String, Integer> getAllStateData(HttpServletRequest request, HttpServletResponse response){
			session = request.getSession(true);
			LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
			LinkedHashMap<String, Integer> remap = new LinkedHashMap<>();
			ModelAndView mav = new ModelAndView();
			try {	
				if (session != null && session.getAttribute("loginID") != null) {
					map = stateMasterService.getAllState();
					for(Map.Entry<Integer, String> chk: map.entrySet()) {
						remap.put(chk.getValue(), chk.getKey());
					}

				}else {
					mav = new ModelAndView("login");
					mav.addObject("login", new Login());
				}
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
			return remap;
			
		}
	 
	 @RequestMapping(value = "/getDistrictsList", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<String, Integer> getAllSubcategories(HttpServletRequest request,
				@RequestParam("stateCode") int stateCode) {
		 LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		 LinkedHashMap<String, Integer> remap = new LinkedHashMap<>();
			 
			try {
				map = districtMasterService.getDistrictByStateCode((int) stateCode);
				for(Map.Entry<Integer, String> chk: map.entrySet()) {
					remap.put(chk.getValue(), chk.getKey());
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return remap;
		}
	 
	 @RequestMapping(value = "/getBlocksList", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<String, Integer> getAllSubBlockcategories(HttpServletRequest request,
				@RequestParam("distCode") int distCode) {
		 LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		 LinkedHashMap<String, Integer> remap = new LinkedHashMap<>();
			 
			try {
				map = blockMasterService.getBlockByDistCode((int) distCode);
				for(Map.Entry<Integer, String> chk: map.entrySet()) {
					remap.put(chk.getValue(), chk.getKey());
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return remap;
		}
}
