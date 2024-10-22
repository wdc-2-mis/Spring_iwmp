package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.BaseLineSurveyBean;
import app.bean.Login;
import app.model.BaseLineSurveyActivityDetails;
import app.model.BaseLineSurveyMain;
import app.model.IwmpMProject;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;
import app.service.BaseLineSurveyService;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;

@Controller("BaseLineSurvey")
public class BaseLineSurveyController {
	
	HttpSession session;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	BaseLineSurveyService baseLineSurveyService;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	
	@RequestMapping(value = "/bls", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String,String> areaCover = new LinkedHashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			if(session.getAttribute("roleName").toString().contains("PIA")) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				//System.out.println(session.getAttribute("roleName"));
				areaCover.put("ddp","DDP");
				areaCover.put("dpap","DPAP");
				areaCover.put("other","Other");
				mav = new ModelAndView("baseLineSurvey");
				mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
				mav.addObject("areaCover",areaCover);
				List<IwmpMPhyHeads> listPhyHead= baseLineSurveyService.getPhysicalHead();
				List<IwmpMPhyActivity> listPhyActivity = baseLineSurveyService.getPhysicalActivity();
				List<WdcpmksyMOutcome> listOutHead = baseLineSurveyService.getOutcomeHead();
				List<WdcpmksyMOutcomeDetail> listOutActivity = baseLineSurveyService.getOutcomeActivity();
				LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>> phyMap1 = new LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>>();
				LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>> phyMap2 = new LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>>();
				LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>> phyMap3 = new LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>>();
				for(IwmpMPhyHeads headList : listPhyHead) {
					LinkedHashMap<Integer,String> actMap= new LinkedHashMap<Integer,String>();
					if(headList.getBlsRequired()) {
				for(IwmpMPhyActivity list : listPhyActivity) {
					if(headList.getHeadCode()==list.getIwmpMPhyHeads().getHeadCode())
					actMap.put(list.getActivityCode(), list.getActivityDesc());
				}
				phyMap1.put(headList, actMap);
					}
					/*
					 * if(headList.getHeadCode()==3 || headList.getHeadCode()==7) {
					 * for(IwmpMPhyActivity list : listPhyActivity) {
					 * if(headList.getHeadCode()==list.getIwmpMPhyHeads().getHeadCode())
					 * actMap.put(list.getActivityCode(), list.getActivityDesc()); }
					 * phyMap2.put(headList, actMap); }
					 */
					/*
					 * if(headList.getHeadCode()==5) { for(IwmpMPhyActivity list : listPhyActivity)
					 * { if(headList.getHeadCode()==list.getIwmpMPhyHeads().getHeadCode())
					 * actMap.put(list.getActivityCode(), list.getActivityDesc()); }
					 * phyMap3.put(headList, actMap); }
					 */
				}
				mav.addObject("phyHeadActivity1",phyMap1);
				mav.addObject("phyHeadActivity2",phyMap2);
				mav.addObject("phyHeadActivity3",phyMap3);
				
				LinkedHashMap<Integer,String> outHeadMap= new LinkedHashMap<Integer,String>();
				for(WdcpmksyMOutcome list : listOutHead) {
					outHeadMap.put(list.getOutcomeId(), list.getOutcomeDesc());
				}
				mav.addObject("outHead",outHeadMap);
				
				LinkedHashMap<WdcpmksyMOutcome,LinkedHashMap<Integer,String>> outHeadActivity = new LinkedHashMap<WdcpmksyMOutcome,LinkedHashMap<Integer,String>>();
				for(WdcpmksyMOutcome listHead : listOutHead) {
					LinkedHashMap<Integer,String> outActMap= new LinkedHashMap<Integer,String>();
				for(WdcpmksyMOutcomeDetail list : listOutActivity) {
					if(list.getWdcpmksyMOutcome().getOutcomeId()==listHead.getOutcomeId())
					outActMap.put(list.getOutcomeDetailId(), list.getOutcomeDetailDesc());
				}
				outHeadActivity.put(listHead, outActMap);
				}
				mav.addObject("outHeadActivity",outHeadActivity);
				
			}else {
				return new ModelAndView("pagenotfound");
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	
	@RequestMapping(value = "/blss", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveySLNA(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String,String> areaCover = new LinkedHashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			if(session.getAttribute("roleName").toString().contains("SL")) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				String userType = session.getAttribute("userType").toString();
				areaCover.put("ddp","DDP");
				areaCover.put("dpap","DPAP");
				areaCover.put("other","Other");
				mav = new ModelAndView("baseLineSurveySLNA");
				mav.addObject("projectList",baseLineSurveyService.getProjectforSLNA(regId));
				mav.addObject("areaCover",areaCover);
				List<IwmpMPhyHeads> listPhyHead= baseLineSurveyService.getPhysicalHead();
				List<IwmpMPhyActivity> listPhyActivity = baseLineSurveyService.getPhysicalActivity();
				List<WdcpmksyMOutcome> listOutHead = baseLineSurveyService.getOutcomeHead();
				List<WdcpmksyMOutcomeDetail> listOutActivity = baseLineSurveyService.getOutcomeActivity();
				LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>> phyMap1 = new LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>>();
				LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>> phyMap2 = new LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>>();
				LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>> phyMap3 = new LinkedHashMap<IwmpMPhyHeads,LinkedHashMap<Integer,String>>();
				for(IwmpMPhyHeads headList : listPhyHead) {
					LinkedHashMap<Integer,String> actMap= new LinkedHashMap<Integer,String>();
					if(headList.getHeadCode()<=2) {
				for(IwmpMPhyActivity list : listPhyActivity) {
					if(headList.getHeadCode()==list.getIwmpMPhyHeads().getHeadCode())
					actMap.put(list.getActivityCode(), list.getActivityDesc());
				}
				phyMap1.put(headList, actMap);
					}
					if(headList.getHeadCode()==3 || headList.getHeadCode()==7) {
						for(IwmpMPhyActivity list : listPhyActivity) {
							if(headList.getHeadCode()==list.getIwmpMPhyHeads().getHeadCode())
							actMap.put(list.getActivityCode(), list.getActivityDesc());
						}
						phyMap2.put(headList, actMap);
							}
					if(headList.getHeadCode()==5) {
						for(IwmpMPhyActivity list : listPhyActivity) {
							if(headList.getHeadCode()==list.getIwmpMPhyHeads().getHeadCode())
							actMap.put(list.getActivityCode(), list.getActivityDesc());
						}
						phyMap3.put(headList, actMap);
							}
				}
				mav.addObject("phyHeadActivity1",phyMap1);
				mav.addObject("phyHeadActivity2",phyMap2);
				mav.addObject("phyHeadActivity3",phyMap3);
				
				LinkedHashMap<Integer,String> outHeadMap= new LinkedHashMap<Integer,String>();
				for(WdcpmksyMOutcome list : listOutHead) {
					outHeadMap.put(list.getOutcomeId(), list.getOutcomeDesc());
				}
				mav.addObject("outHead",outHeadMap);
				
				LinkedHashMap<WdcpmksyMOutcome,LinkedHashMap<Integer,String>> outHeadActivity = new LinkedHashMap<WdcpmksyMOutcome,LinkedHashMap<Integer,String>>();
				for(WdcpmksyMOutcome listHead : listOutHead) {
					LinkedHashMap<Integer,String> outActMap= new LinkedHashMap<Integer,String>();
				for(WdcpmksyMOutcomeDetail list : listOutActivity) {
					if(list.getWdcpmksyMOutcome().getOutcomeId()==listHead.getOutcomeId())
					outActMap.put(list.getOutcomeDetailId(), list.getOutcomeDetailDesc());
				}
				///if(!outActMap.isEmpty())
				outHeadActivity.put(listHead, outActMap);
				}
				mav.addObject("outHeadActivity",outHeadActivity);
				
			
		            for (WdcpmksyMOutcome keya : outHeadActivity.keySet())
			        {
		            	LinkedHashMap<Integer,String> valA = outHeadActivity.get(keya);
		            	if(valA.size()>0) {
		            		System.out.println("Key = " + keya.getOutcomeId() + ", Value = " + keya.getOutcomeDesc());
		            		for(Integer actKey : valA.keySet()) {
		            			System.out.println("Key = " + actKey + ", Value = " + valA.get(actKey));
		            		}
		            	}else {
		            		System.out.println("Key = " + keya.getOutcomeId() + ", Value = " + keya.getOutcomeDesc());
		            	}
			            
			        }
				
			}else {
				return new ModelAndView("pagenotfound");
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	
	@RequestMapping(value = "/saveAsDraftBaseLineSurvey", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraftBaseLineSurvey(HttpServletRequest request, @ModelAttribute("formData") String formData) throws ParseException {
		String res="";
		session = request.getSession(true);
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		String[] str =formData.substring(1,(formData.length()-1)).split("&");
try {
	if(session!=null && session.getAttribute("loginID")!=null) {
		BaseLineSurveyActivityDetails blsActDetails = new BaseLineSurveyActivityDetails();
		BaseLineSurveyMain main = new BaseLineSurveyMain();
		IwmpMProject project = new IwmpMProject();
		WdcpmksyMOutcome mOutcome = new WdcpmksyMOutcome();
		WdcpmksyMOutcomeDetail mOutcomeDetails = new WdcpmksyMOutcomeDetail();
		IwmpMPhyActivity mPhyAct = new IwmpMPhyActivity();
		IwmpMPhyHeads mPhyHead = new IwmpMPhyHeads();
		List<String> mainList = new ArrayList<String>();
		List<String> outList = new ArrayList<String>();
		List<String> phyList = new ArrayList<String>();
		List<BaseLineSurveyActivityDetails> finalList = new ArrayList<BaseLineSurveyActivityDetails>();
		for(String s:str) {
		if(s.contains("review_out_")) {
			outList.add(s.replace("review_out_", ""));
		}else if(s.contains("review_phy_")) {
			phyList.add(s.replace("review_phy_", ""));
		}else {
			mainList.add(s.replace("review_", ""));
		}
		}
		mainList.remove(0);
		mainList.remove(2);
		for(String s: mainList) {
			String[] ss = s.split("=");	
			project = new IwmpMProject();
			if(ss[0].equalsIgnoreCase("areaCoveringType"))
			main.setAreaCoveringType(ss[1]);
			
			
			if(ss[0].equalsIgnoreCase("personDaysMigration"))
				main.setPersonDaysMigration(Integer.parseInt(ss[1]));
			
			if(ss[0].equalsIgnoreCase("totalGrossCroppedArea"))
				main.setTotalGrossCroppedArea(new BigDecimal(ss[1]));
			if(ss[0].equalsIgnoreCase("totalNetSownArea"))
				main.setTotalNetSownArea(new BigDecimal(ss[1]));
			if(ss[0].equalsIgnoreCase("totalPopulation"))
				main.setTotalPopulation(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("totalSc"))
				main.setTotalSc(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("totalSt"))
				main.setTotalSt(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("totalOthers"))
				main.setTotalOthers(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("projectId")) {
				project.setProjectId(Integer.parseInt(ss[1]));
			main.setIwmpMProject(project);
			}
			if(ss[0].equalsIgnoreCase("totalGeoArea"))
				main.setTotalGeoArea(new BigDecimal(ss[1]));
			
			if(ss[0].equalsIgnoreCase("houseTotal"))
				main.setHouseholdTotal(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("scPopulation"))
				main.setPopulationSc(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("stPopulation"))
				main.setPopulationSt(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("houseOthersPopulation"))
				main.setPopulationOther(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("houseTotalPopulation"))
				main.setPopulationTotal(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("householdLandlessPeople"))
				main.setNoOfLandlessHousehold(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("bplHousehold"))
				main.setNoOfBplHousehold(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("smallFarmerHousehold"))
				main.setSmallFarmerHousehold(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("marginalFarmerHousehold"))
				main.setMarginalFarmerHousehold(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("householdLandlessPeoplePopulation"))
				main.setPopulationLandlessPeople(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("bplHouseholdPopulation"))
				main.setPopulationBpl(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("smallFarmerHouseholdPopulation"))
				main.setPopulationSmallFarmers(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("marginalFarmerHouseholdPopulation"))
				main.setPopulationMarginalFarmers(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("marginalFarmerHouseholdPopulation"))
				main.setPopulationMarginalFarmers(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("houseTotal"))
				main.setPopulationTotal(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whsfarmponds"))
				main.setWhsFarmPond(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whscheckdams"))
				main.setWhsCheckDam(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whsnallahbunds"))
				main.setWhsNallahBund(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whspercolationtanks"))
				main.setWhsPercolationTank(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whsgwrs"))
				main.setWhsGwrs(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whsgullyplugs"))
				main.setWhsGullyPlug(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("whsothers"))
				main.setWhsOther(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("picheckdams"))
				main.setPiCheckDam(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("pifarmponds"))
				main.setPiFarmPond(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("pinallahbunds"))
				main.setPiNallahBund(Integer.parseInt(ss[1]));
			if(ss[0].equalsIgnoreCase("piothers"))
				main.setPiOther(Integer.parseInt(ss[1]));
			
			main.setCreatedBy(session.getAttribute("loginID").toString());
			main.setStatus('D');
			
			
		}
		for(String out : outList) {
			String[] ss = out.split("=");
			String[] ss2 = ss[0].split("_");
			String outHead =ss2[1];
			String outActivity = ss2[2];
			String val = ss[1];
			mOutcome = new WdcpmksyMOutcome();
			mOutcomeDetails = new WdcpmksyMOutcomeDetail();
			blsActDetails = new BaseLineSurveyActivityDetails();
			mOutcome.setOutcomeId(Integer.parseInt(outHead));
			if(!outActivity.equals("0")) {
			mOutcomeDetails.setOutcomeDetailId(Integer.parseInt(outActivity));
			blsActDetails.setWdcpmksyMOutcomeDetail(mOutcomeDetails);
			}
			blsActDetails.setWdcpmksyMOutcome(mOutcome);
			blsActDetails.setAreaOfActivity(new BigDecimal(val));
			finalList.add(blsActDetails);
		}
		
		for(String out : phyList) {
			String[] ss = out.split("=");
			String[] ss2 = ss[0].split("_");
			String phyHead =ss2[1];
			String phyActivity = ss2[2];
			String val = ss[1];
			mPhyHead = new IwmpMPhyHeads();
			mPhyAct = new IwmpMPhyActivity();
			blsActDetails = new BaseLineSurveyActivityDetails();
			mPhyHead.setHeadCode(Integer.parseInt(phyHead));
			if(!phyActivity.equals("0")) {
			mPhyAct.setActivityCode(Integer.parseInt(phyActivity));
			blsActDetails.setIwmpMPhyActivity(mPhyAct);
			}
			blsActDetails.setIwmpMPhyHeads(mPhyHead);
			blsActDetails.setAreaOfActivity(new BigDecimal(val));
			finalList.add(blsActDetails);
		}
		res=baseLineSurveyService.saveDataAsDraft(main,finalList);
	}
}catch(Exception ex) {
	ex.printStackTrace();
}
		return res;
	}
	
	
	@RequestMapping(value = "/getSanctionedArea", method = RequestMethod.POST)
	@ResponseBody
	public String getSanctionedArea(HttpServletRequest request, @RequestParam("projId") Integer projId) {
	String res="";
	try {
		res=baseLineSurveyService.getSanctionedArea(projId);
	}catch(Exception ex) {
		res="0";
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getPreFilledData", method = RequestMethod.POST)
	@ResponseBody
	public List<BaseLineSurveyBean> getPreFilledData(HttpServletRequest request, @RequestParam("projId") Integer projId) {
		List<BaseLineSurveyBean> res=new ArrayList<BaseLineSurveyBean>();
		List<BaseLineSurveyBean> list=new ArrayList<BaseLineSurveyBean>();
	try {
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
		String userType = session.getAttribute("userType").toString();
		list=baseLineSurveyService.getPreFilledData(projId);
		for(BaseLineSurveyBean bean : list) {
			bean.setUserType(userType);
			res.add(bean);
		}
		}
		
	}catch(Exception ex) {
		res=null;
	}
	return res;
		
	}
	
	@RequestMapping(value = "/completeBaseLineSurvey", method = RequestMethod.POST)
	@ResponseBody
	public String completeBaseLineSurvey(HttpServletRequest request, @RequestParam("projId") Integer projId) {
		String res="fail";
	try {
		res=baseLineSurveyService.completeBaseLineSurvey(projId);
	}catch(Exception ex) {
		//res=null;
	}
	return res;
		
	}
	
	/************************************************************** Report Section ******************************************************/
	@RequestMapping(value = "/blsreportproject", method = RequestMethod.POST)
	public ModelAndView getBaseLineSurveyReport(HttpServletRequest request,@RequestParam("value") String value) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		System.out.println(value);
		String [] arr = value.split(",");
		mav = new ModelAndView("blsReportProject");
		List<BaseLineSurveyBean> list = baseLineSurveyService.getBaseLineSurveyDetailByProjId(Integer.parseInt(arr[0].toString()));
		List<String> treatablearea = new ArrayList<String>();
		List<String> areacoveredunderdiversifiedcrops = new ArrayList<String>();
		List<String> areabroughtfromnil = new ArrayList<String>();
		List<String> areabroughtunderafforestation = new ArrayList<String>();
		List<String> areabroughtunderhorticulture = new ArrayList<String>();
		List<String> waterharvestingstructure = new ArrayList<String>();
		List<String> areabroughtunderprotectiveirrigation = new ArrayList<String>();
		List<String> soilandmoistureconservation = new ArrayList<String>();
		List<String> noofhousehold = new ArrayList<String>();
		
		BigDecimal gross_cropped_area = BigDecimal.ZERO,net_sown_area = BigDecimal.ZERO;
		Integer totalst=0,totalsc = 0, totalothers=0, totalpopulation=0, landlesshousehold=0,BPLhousehold=0,smallfarmershousehold=0,marginalfarmershousehold=0,persondaysofseasonalmigration=0 ;
		
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		for(BaseLineSurveyBean b : list) {
			
			mav.addObject("project",b.getProj_name());
			mav.addObject("sanctionedarea",b.getSanctioned_area());
			mav.addObject("geoarea",b.getTotal_geo_area());
			mav.addObject("projectareacovering",b.getArea_covering_type());
			if(b.getOutcome_id()!=null && b.getOutcome_id()<=3)
			treatablearea.add(b.getArea_of_activity().toString());
			
			if(b.getOutcome_id()!=null && b.getOutcome_id()==4)
				areacoveredunderdiversifiedcrops.add(b.getArea_of_activity().toString());
			
			if(b.getOutcome_id()!=null && b.getOutcome_id()==5)
				areabroughtfromnil.add(b.getArea_of_activity().toString());
			gross_cropped_area =b.getTotal_gross_cropped_area();
			net_sown_area = b.getTotal_net_sown_area();
			
			if(b.getPhy_head()!=null && b.getPhy_head()==1)
				areabroughtunderafforestation.add(b.getArea_of_activity().toString());
			
			if(b.getPhy_head()!=null && b.getPhy_head()==2)
				areabroughtunderhorticulture.add(b.getArea_of_activity().toString());
			
			if(b.getPhy_head()!=null && b.getPhy_head()==5)
				waterharvestingstructure.add(b.getArea_of_activity().toString());
			
			if(b.getPhy_head()!=null && b.getPhy_head()==7)
				areabroughtunderprotectiveirrigation.add(b.getArea_of_activity().toString());
			
			if(b.getPhy_head()!=null && b.getPhy_head()==3)
				soilandmoistureconservation.add(b.getArea_of_activity().toString());
			
			totalst=b.getTotal_st();
			totalsc = b.getTotal_sc();
			totalothers = b.getTotal_others();
			totalpopulation = b.getTotal_population();
			landlesshousehold = b.getNo_of_landless_household();
			BPLhousehold = b.getNo_of_bpl_household();
			smallfarmershousehold = b.getSmall_farmer_household();
			marginalfarmershousehold = b.getMarginal_farmer_household();
			persondaysofseasonalmigration = b.getPerson_days_migration();
			
		}
		areabroughtfromnil.add(gross_cropped_area.toString());
		areabroughtfromnil.add(net_sown_area.toString());
		noofhousehold.add(totalst.toString());
		noofhousehold.add(totalsc.toString());
		noofhousehold.add(totalothers.toString());
		//noofhousehold.add(totalpopulation.toString());
		noofhousehold.add(landlesshousehold.toString());
		noofhousehold.add(BPLhousehold.toString());
		noofhousehold.add(smallfarmershousehold.toString());
		noofhousehold.add(marginalfarmershousehold.toString());
		noofhousehold.add(persondaysofseasonalmigration.toString());
		
		map.put("treatablearea", treatablearea);
		map.put("areacoveredunderdiversifiedcrops", areacoveredunderdiversifiedcrops);
		map.put("areabroughtfromnil", areabroughtfromnil);
		map.put("areabroughtunderafforestation", areabroughtunderafforestation);
		map.put("areabroughtunderhorticulture", areabroughtunderhorticulture);
		map.put("waterharvestingstructure", waterharvestingstructure);
		map.put("areabroughtunderprotectiveirrigation", areabroughtunderprotectiveirrigation);
		map.put("soilandmoistureconservation", soilandmoistureconservation);
		map.put("noofhousehold", noofhousehold);
		mav.addObject("state",arr[2]);
		mav.addObject("district",arr[1]);
		mav.addObject("map",map);
		return mav;
	}
	
	@RequestMapping(value = "/blsreportwcdc", method = RequestMethod.POST)
	public ModelAndView getBaseLineSurveyReportWCDC(HttpServletRequest request,@RequestParam("value") String value) {
		session = request.getSession(true);
		System.out.println(value);
		String [] arr = value.split(",");
		ModelAndView mav = new ModelAndView("blsReportWCDC");	
		
		String stName="";
		LinkedHashMap<Integer,List<BaseLineSurveyBean>> map = new LinkedHashMap<Integer,List<BaseLineSurveyBean>>();
		List<BaseLineSurveyBean> list = baseLineSurveyService.getBaseLineSurveyDetailByDistCode(Integer.parseInt(arr[0].toString()));
		List<BaseLineSurveyBean> sublist = new ArrayList<BaseLineSurveyBean>();
		for(BaseLineSurveyBean bean : list) {
			if(!map.containsKey(bean.getDist_code())) {
				sublist = new ArrayList<BaseLineSurveyBean>();
				sublist.add(bean);
				map.put(bean.getDist_code(),sublist);
				stName=bean.getStname();
			}else {
				sublist = map.get(bean.getDist_code());
				sublist.add(bean);
				map.put(bean.getDist_code(),sublist);
			}
			
		}
		mav.addObject("finalList",map);
		mav.addObject("state",stName);
		return mav;
	}
	
	@RequestMapping(value = "/blsreport", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReport(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView("blsReportSLNA");	
		LinkedHashMap<String,BaseLineSurveyBean> map = new LinkedHashMap<String,BaseLineSurveyBean>();
		map = baseLineSurveyService.getBaseLineSurveyDetail();
		LinkedHashMap<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
		stateMap.putAll(stateMasterService.getAllState());
		mav.addObject("finalList",map);
		mav.addObject("stateList",stateMap);
		return mav;
	}
	
	@RequestMapping(value = "/blsreport", method = RequestMethod.POST)
	public ModelAndView getBaseLineSurveyReportPOST(HttpServletRequest request,@RequestParam("state") Integer stCode,@RequestParam("district") Integer distCode) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView("blsReportSLNA");	
		LinkedHashMap<String,BaseLineSurveyBean> map = new LinkedHashMap<String,BaseLineSurveyBean>();
		map = baseLineSurveyService.getBaseLineSurveyDetailByStCodeDistCode(stCode,distCode);
		mav.addObject("finalList",map);
		mav.addObject("stCode", stCode);
		mav.addObject("distCode", distCode);
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("districtList",districtMasterService.getDistrictByStateCode(stCode));
		return mav;
	}

}
