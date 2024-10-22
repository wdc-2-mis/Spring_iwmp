package app.controllers.outcome;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.ProjectState;
import app.bean.RPCLivelihoodBean;
import app.model.IwmpMProject;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.LivelihoodEpaProd;
import app.model.outcome.MCroptype;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.ProductionDetail;
import app.service.ProjectMasterService;
import app.service.outcome.LivelihoodProductionEpaService;

@Controller("LivelihoodProductionEPA")
public class LivelihoodProductionEPA {
	@Autowired(required = true)
	private LivelihoodProductionEpaService lhoodproductepaserice;

	@Autowired(required = true)
	ProjectMasterService projectMasterService;

	HttpSession session;

	private static final Logger logger = Logger.getLogger(LivelihoodProductionEPA.class);

	public LivelihoodProductionEPA() {

	}

	@RequestMapping(value = "/addlivelihood", method = RequestMethod.GET)
	public String newProject(Locale locale, Model model, HttpServletRequest request) {

		String result = null;
		try {

			String msg = null;
			if (request.getParameter("msg") != null)
				msg = request.getParameter("msg");

//			InetAddress inet = InetAddress.getLocalHost();
//			String ipAddr = inet.getHostAddress();

			session = request.getSession(true);
			int regId = (int) session.getAttribute("regId");

			if (session != null && session.getAttribute("loginID") != null) {
				LivelihoodEpaProd maintable = new LivelihoodEpaProd();

				List<LivelihoodDetail> lvhList = new ArrayList<LivelihoodDetail>();
				LivelihoodDetail lvh = new LivelihoodDetail();
				lvh.setMLivelihoodCoreactivity(new MLivelihoodCoreactivity());
				lvh.setLivelihoodEpaProd(maintable);

				lvhList.add(lvh);
				maintable.setLivelihoodDetailsList(lvhList);

				List<ProductionDetail> prodList = new ArrayList<ProductionDetail>();
				ProductionDetail prd = new ProductionDetail();
				prd.setMCroptype(new MCroptype());
				prd.setMProductivityCoreactivity(new MProductivityCoreactivity());
				prd.setLivelihoodEpaProd(maintable);
				prodList.add(prd);

				maintable.setProductionDetailList(prodList);

				List<EpaDetail> epaList = new ArrayList<EpaDetail>();
				EpaDetail epa = new EpaDetail();
				epa.setLivelihoodEpaProd(maintable);
				epa.setMEpaCoreactivity(new MEpaCoreactivity());
				epaList.add(epa);

				maintable.setEpaDetailList(epaList);

				model.addAttribute("maintable", maintable);
				model.addAttribute("sourcelivelihoodList", lhoodproductepaserice.getLivelihoodCoreActivity());
				model.addAttribute("sourceEpaList", lhoodproductepaserice.getEpaCoreActivity());
				model.addAttribute("sourceProductionList", lhoodproductepaserice.getProductionCoreActivity());
				model.addAttribute("cropList", lhoodproductepaserice.getCropList());
				model.addAttribute("projectList", projectMasterService.getProjectByRegId(regId));
				model.addAttribute("msg", msg);
//				model.addAttribute("livelihoodData", lhoodproductepaserice.getLivelihoodDetail(regId));
//				model.addAttribute("productionData", lhoodproductepaserice.getProductionDetail(regId));
//				model.addAttribute("epaData", lhoodproductepaserice.getEpaDetail(regId));

				result = "output/AddLivelihood";
			} else {
				result = "login";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/saveLivelihoodProductionEpa", method = RequestMethod.POST)
	public String saveLivelihoodProductionEpa(@ModelAttribute("maintable") @Valid LivelihoodEpaProd maintable,
			BindingResult result, Model model, WebRequest request) {

		String msg = "";
		String error = "";

		if (result.hasErrors()) {

		} else {
			if (maintable.getIwmpMProject().getProjectId() <= 0)
				model.addAttribute("error", "Please Select Project");
			else if (maintable.getHeadType() == "-1")
				model.addAttribute("error", "Please Select Head Type");
			else {
				try {
					InetAddress inet = InetAddress.getLocalHost();
					String ipAddr = inet.getHostAddress();

					maintable.setRequestIp(ipAddr);
					maintable.setCreatedOn(new java.util.Date());
					maintable.setCreatedBy(session.getAttribute("loginID").toString());
					// if (result.hasErrors()) {
					if (maintable.getHeadType().equals("livelihood")) {
						List<LivelihoodDetail> lvhList = maintable.getLivelihoodDetailsList();
						Set<LivelihoodDetail> livelihoodDetails = new HashSet<>(0);
						for (int i = 0; i < lvhList.size(); i++) {
							LivelihoodDetail lvh = new LivelihoodDetail();
							lvh = lvhList.get(i);
							if (lvh.getMLivelihoodCoreactivity().getLivelihoodCoreactivityId() == 0)
								error = "Select Livelihood Coreactivity ";
							if (lvh.getSc()==null ||lvh.getSt()==null  ||lvh.getWomen()==null )
								error+= " <br> Enter Beneficiary Details for SC, ST and Women";
							if( lvh.getPreAvgIncome()==null)
								error+= " <br> Average Income of Beneficiaries Prior to Livelihood activities ";
							/*
							 * if(lvh.getPostAvgIncome()==null || lvh.getPreAvgIncome()==null) error+=
							 * " <br> Average Income of Beneficiaries Post to Livelihood activities ";
							 */
						
							lvh.setLivelihoodEpaProd(maintable);
							lvh.setRequestIp(ipAddr);
							lvh.setStatus("D");
							lvh.setCreatedOn(new java.util.Date());
							livelihoodDetails.add(lvh);

						}

						maintable.setLivelihoodDetails(livelihoodDetails);
						// lhoodproductepaserice.se addLivelihoodProductionEPA(maintable);
					} else if (maintable.getHeadType().equals("production")) {
						List<ProductionDetail> lvhList = maintable.getProductionDetailList();
						Set<ProductionDetail> productionDetails = new HashSet<>(0);
						for (int i = 0; i < lvhList.size(); i++) {
							ProductionDetail lvh = new ProductionDetail();
							lvh = lvhList.get(i);
							if (lvh.getMProductivityCoreactivity().getProductivityCoreactivityId() == 0)
								error = "Select Coreactivity";
							if (lvh.getMProductivityCoreactivity().getProductivityCoreactivityId() == 16) {
								if (lvh.getMCroptype().getCropId() == 0)
									error = "Select Crop";
							}
							if (lvh.getSc()==null ||lvh.getSt()==null  ||lvh.getWomen()==null )
								error+= " <br> Enter Beneficiary Details for SC, ST and Women";
							if( lvh.getPreAvgIncome()==null)
								error+= " <br> Average Income of Beneficiaries Prior to Livelihood activities ";
							/*
							 * if(lvh.getPostAvgIncome()==null || lvh.getPreAvgIncome()==null) error+=
							 * " <br> Average Income of Beneficiaries Post to Livelihood activities ";
							 */
						

							lvh.setLivelihoodEpaProd(maintable);
							lvh.setRequestIp(ipAddr);
							lvh.setCreatedOn(new java.util.Date());
							lvh.setStatus("D");
							productionDetails.add(lvh);

						}
						maintable.setProductionDetails(productionDetails);

					} else if (maintable.getHeadType().equals("epa")) {
						List<EpaDetail> lvhList = maintable.getEpaDetailList();
						Set<EpaDetail> epaDetails = new HashSet<>(0);
						for (int i = 0; i < lvhList.size(); i++) {
							EpaDetail lvh = new EpaDetail();
							lvh = lvhList.get(i);
							if (lvh.getMEpaCoreactivity().getEpaActivityId() == 0)
								error = "Select Coreactivity.";
							if (lvh.getNoActivities()==null)
								error = "<br> Enter No of Activites.";
							lvh.setRequestIp(ipAddr);
							lvh.setCreatedOn(new java.util.Date());
							lvh.setStatus("D");
							lvh.setLivelihoodEpaProd(maintable);
							epaDetails.add(lvh);

						}
						maintable.setEpaDetails(epaDetails);

					}
					if (error.equals("")) {
						msg = lhoodproductepaserice.addLivelihoodProductionEPA(maintable);
						
						/*
						 * if (maintable.getHeadType().equals("livelihood")) {
						 * model.addAttribute("sourcelivelihoodList",
						 * lhoodproductepaserice.getLivelihoodCoreActivity());
						 * model.addAttribute("livelihoodData",
						 * lhoodproductepaserice.getLivelihoodProjectDetail(maintable.getIwmpMProject().
						 * getProjectId())); } else if (maintable.getHeadType().equals("production")) {
						 * model.addAttribute("sourceProductionList",
						 * lhoodproductepaserice.getProductionCoreActivity());
						 * model.addAttribute("cropList", lhoodproductepaserice.getCropList());
						 * model.addAttribute("productionData",
						 * lhoodproductepaserice.getProductionProjectDetail(maintable.getIwmpMProject().
						 * getProjectId())); } else if (maintable.getHeadType().equals("epa")) {
						 * model.addAttribute("sourceEpaList",
						 * lhoodproductepaserice.getEpaCoreActivity()); model.addAttribute("epaData",
						 * lhoodproductepaserice.getEpaProjectDetail(maintable.getIwmpMProject().
						 * getProjectId())); }
						 */
						if (msg.equals("success")) {
							model.addAttribute("msg", "Record is Successfully Saved");
							maintable = new LivelihoodEpaProd();
							maintable.getLivelihoodDetailsList().add(new LivelihoodDetail());
							// maintable.getLivelihoodDetailsList().add(new LivelihoodDetail());
							maintable.getProductionDetailList().add(new ProductionDetail());
							// maintable.getProductionDetailList().add(new ProductionDetail());
							maintable.getEpaDetailList().add(new EpaDetail());
							// maintable.getEpaDetailList().add(new EpaDetail());
							
							
							}

						else
							model.addAttribute("error", msg);
					} 
					else
					{
						int regId = (int) session.getAttribute("regId");
						model.addAttribute("error", error);
						model.addAttribute("sourcelivelihoodList", lhoodproductepaserice.getLivelihoodCoreActivity());
						model.addAttribute("sourceEpaList", lhoodproductepaserice.getEpaCoreActivity());
						model.addAttribute("sourceProductionList", lhoodproductepaserice.getProductionCoreActivity());
						model.addAttribute("cropList", lhoodproductepaserice.getCropList());
						model.addAttribute("projectList", projectMasterService.getProjectByRegId(regId));
						return "output/AddLivelihood";
					}
					
				
					
					
					
					
					
					
					
					
				} 
				catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		}

		//int regId = (int) session.getAttribute("regId");

	//	model.addAttribute("maintable", maintable);
		//model.addAttribute("projectList", projectMasterService.getProjectByRegId(regId));
//		if (maintable.getHeadType().equals("livelihood"))
//		{
//			model.addAttribute("sourcelivelihoodList", lhoodproductepaserice.getLivelihoodCoreActivity());
//			model.addAttribute("livelihoodData",
//					lhoodproductepaserice.getLivelihoodProjectDetail(maintable.getIwmpMProject().getProjectId()));
//		}
//		else if (maintable.getHeadType().equals("production"))
//		{
//			model.addAttribute("sourceProductionList", lhoodproductepaserice.getProductionCoreActivity());
//			model.addAttribute("cropList", lhoodproductepaserice.getCropList());
//			model.addAttribute("productionData",
//					lhoodproductepaserice.getProductionProjectDetail(maintable.getIwmpMProject().getProjectId()));
//		}
//		else if (maintable.getHeadType().equals("epa"))
//		{
//			model.addAttribute("sourceEpaList", lhoodproductepaserice.getEpaCoreActivity());
//			model.addAttribute("epaData",
//					lhoodproductepaserice.getEpaProjectDetail(maintable.getIwmpMProject().getProjectId()));
//		}return "redirect:/mainmenu";
		return "redirect:/addlivelihood";
	}

	@RequestMapping(value = "/saveLivelihoodProductionEpa", params = "add", method = RequestMethod.POST)
	public String addEpaDetails(@ModelAttribute("maintable") LivelihoodEpaProd maintable, BindingResult result,
			Model model) {

		int regId = (int) session.getAttribute("regId");
	
		if (maintable.getHeadType().equals("livelihood"))
		{
			List<LivelihoodDetail> epalist = maintable.getLivelihoodDetailsList();
			if (epalist == null) {
				maintable.getLivelihoodDetailsList().add(new LivelihoodDetail());
				// projectDetail.setIwmpMProjectList(projectList);
			} else {
				maintable.getLivelihoodDetailsList().add(new LivelihoodDetail());
			}
			model.addAttribute("sourcelivelihoodList", lhoodproductepaserice.getLivelihoodCoreActivity());
			model.addAttribute("livelihoodData",
					lhoodproductepaserice.getLivelihoodProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		else if (maintable.getHeadType().equals("production"))
		{
			List<ProductionDetail> epalist = maintable.getProductionDetailList();
			if (epalist == null) {
				maintable.getProductionDetailList().add(new ProductionDetail());
				// projectDetail.setIwmpMProjectList(projectList);
			} else {
				maintable.getProductionDetailList().add(new ProductionDetail());
			}
			model.addAttribute("sourceProductionList", lhoodproductepaserice.getProductionCoreActivity());
			model.addAttribute("cropList", lhoodproductepaserice.getCropList());
			model.addAttribute("productionData",
					lhoodproductepaserice.getProductionProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		else if (maintable.getHeadType().equals("epa"))
		{
			List<EpaDetail> epalist = maintable.getEpaDetailList();
			if (epalist == null) {
				maintable.getEpaDetailList().add(new EpaDetail());
				// projectDetail.setIwmpMProjectList(projectList);
			} else {
				maintable.getEpaDetailList().add(new EpaDetail());
			}
			model.addAttribute("sourceEpaList", lhoodproductepaserice.getEpaCoreActivity());
			model.addAttribute("epaData",
					lhoodproductepaserice.getEpaProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		model.addAttribute("maintable", maintable);
		model.addAttribute("projectList", projectMasterService.getProjectByRegId(regId));
		
		return "output/AddLivelihood";
	}

	

	@RequestMapping(value = "/saveLivelihoodProductionEpa", params = "ondelete", method = RequestMethod.POST)
	public String deleteRecord(@ModelAttribute("maintable") LivelihoodEpaProd maintable, BindingResult result,
			Model model, WebRequest request) {

		
		int regId = (int) session.getAttribute("regId");
	
		if (maintable.getHeadType().equals("livelihood"))
		{
			if (maintable.getLivelihoodDetailsList() != null) {

				if (request.getParameter("id") != null) {
					String id = request.getParameter("id");
					maintable.getLivelihoodDetailsList().remove(Integer.parseInt(id));
				}
			}
			
			model.addAttribute("sourcelivelihoodList", lhoodproductepaserice.getLivelihoodCoreActivity());
			model.addAttribute("livelihoodData",
					lhoodproductepaserice.getLivelihoodProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		else if (maintable.getHeadType().equals("production"))
		{
			if (maintable.getProductionDetailList() != null) {

				if (request.getParameter("id") != null) {
					String id = request.getParameter("id");
					maintable.getProductionDetailList().remove(Integer.parseInt(id));
				}
			}
			model.addAttribute("sourceProductionList", lhoodproductepaserice.getProductionCoreActivity());
			model.addAttribute("cropList", lhoodproductepaserice.getCropList());
			model.addAttribute("productionData",
					lhoodproductepaserice.getProductionProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		else if (maintable.getHeadType().equals("epa"))
		{
			if (maintable.getEpaDetailList() != null) {

				if (request.getParameter("id") != null) {
					String id = request.getParameter("id");
					maintable.getEpaDetailList().remove(Integer.parseInt(id));
				}
			}
			model.addAttribute("sourceEpaList", lhoodproductepaserice.getEpaCoreActivity());
			model.addAttribute("epaData",
					lhoodproductepaserice.getEpaProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		model.addAttribute("maintable", maintable);
		model.addAttribute("projectList", projectMasterService.getProjectByRegId(regId));

		return "output/AddLivelihood";
	}

	

	
	

	// deleteMenu will delete menu from data base
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMenu(HttpServletRequest request) {

		String msg = "";
		if (request.getParameter("id") != null) {
			String temp = request.getParameter("id");

			if (temp.contains("l")) {
				int sepPos = temp.lastIndexOf("l");

				int id = Integer.parseInt(temp.substring(0, sepPos));
				msg = lhoodproductepaserice.deleteLivelihoodDetail(id);
			}
			if (temp.contains("p")) {
				int sepPos = temp.lastIndexOf("p");

				int id = Integer.parseInt(temp.substring(0, sepPos));
				msg = lhoodproductepaserice.deleteProductionDetail(id);
			}
			if (temp.contains("e")) {
				int sepPos = temp.lastIndexOf("e");

				int id = Integer.parseInt(temp.substring(0, sepPos));
				msg = lhoodproductepaserice.deleteEpaDetail(id);
			}

		}

		ModelAndView mv = new ModelAndView("redirect:/addlivelihood");
		if (msg.equals("success")) {

			mv.addObject("msg", msg);
			// return msg;
			mv.addObject("msg", "Record is Deleted Successfully");
		} else
			mv.addObject("error", msg);
		return msg;
	}

	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	@ResponseBody
	public String completeLivelihood(@ModelAttribute("maintable") LivelihoodEpaProd maintable, BindingResult result,
			Model model, WebRequest request) {

		String msg = "";
		if (request.getParameter("id") != null) {
			String temp = request.getParameter("id");

			if (temp.contains("l")) {
				int sepPos = temp.lastIndexOf("l");

				int id = Integer.parseInt(temp.substring(0, sepPos));
				msg = lhoodproductepaserice.completeLivelihoodDetail(id);
			}
			if (temp.contains("p")) {
				int sepPos = temp.lastIndexOf("p");

				int id = Integer.parseInt(temp.substring(0, sepPos));
				msg = lhoodproductepaserice.completeProductionDetail(id);
			}
			if (temp.contains("e")) {
				int sepPos = temp.lastIndexOf("e");

				int id = Integer.parseInt(temp.substring(0, sepPos));
				msg = lhoodproductepaserice.completeEpaDetail(id);
			}

		}

		ModelAndView mv = new ModelAndView("redirect:/addlivelihood");
		if (msg.equals("success")) {

			mv.addObject("msg", msg);
			// return msg;
			mv.addObject("msg", "Record is Updated Successfully");
		} else
			mv.addObject("error", msg);
		return msg;
	}

	@RequestMapping(value = "/onChangeLivelihood", method = RequestMethod.POST)
	public String onChange(@ModelAttribute("maintable") LivelihoodEpaProd maintable, BindingResult result, Model model,
			HttpServletRequest request) {
		session = request.getSession(true);
		int regId = (int) session.getAttribute("regId");
		String str = null;
		model.addAttribute("maintable", maintable);
		model.addAttribute("projectList", projectMasterService.getProjectByRegId(regId));

		if (maintable.getHeadType().equals("livelihood"))
		{
			model.addAttribute("sourcelivelihoodList", lhoodproductepaserice.getLivelihoodCoreActivity());
			model.addAttribute("livelihoodData",
					lhoodproductepaserice.getLivelihoodProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		else if (maintable.getHeadType().equals("production"))
		{
			model.addAttribute("sourceProductionList", lhoodproductepaserice.getProductionCoreActivity());
			model.addAttribute("cropList", lhoodproductepaserice.getCropList());
			model.addAttribute("productionData",
					lhoodproductepaserice.getProductionProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}
		else if (maintable.getHeadType().equals("epa"))
		{
			model.addAttribute("sourceEpaList", lhoodproductepaserice.getEpaCoreActivity());
			model.addAttribute("epaData",
					lhoodproductepaserice.getEpaProjectDetail(maintable.getIwmpMProject().getProjectId()));
		}

		str = "output/AddLivelihood";
		return str;
	}
	
	@RequestMapping(value="/completeAllEPALivelihoodProduction", method = RequestMethod.POST)
	@ResponseBody
	public String completeAllEPALivelihoodProduction(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="assetid") List<BigInteger> tempassetid, @RequestParam(value="scheme") String scheme)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
		
			res=lhoodproductepaserice.completeAllEPALivelihoodProduction(sentfrom,tempassetid,scheme);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	
	@RequestMapping(value="/livelihooddatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<RPCLivelihoodBean> livelihooddatafind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		//System.out.println("value of id:" +id);
		session = request.getSession(true);
		LinkedHashMap<Integer,List<RPCLivelihoodBean>> map = new LinkedHashMap<Integer,List<RPCLivelihoodBean>>();
		List<RPCLivelihoodBean> proj = new ArrayList<RPCLivelihoodBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = lhoodproductepaserice.findlivelidesc(id);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@RequestMapping(value="/productiondatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<RPCLivelihoodBean> productiondatafind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="id") Integer id)
	{
		//System.out.println("value of id:" +id);
		session = request.getSession(true);
		LinkedHashMap<Integer,List<RPCLivelihoodBean>> map = new LinkedHashMap<Integer,List<RPCLivelihoodBean>>();
		List<RPCLivelihoodBean> proj = new ArrayList<RPCLivelihoodBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = lhoodproductepaserice.findproddesc(id);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	
	
	
	@RequestMapping(value="/updatelivePostData", method = RequestMethod.POST)
	public ModelAndView updatelivePostData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id")Integer id,  @RequestParam("livepost") Integer livepost , Model model)
	{
		String msg = null;
		Boolean flag=false;
		msg = "output/AddLivelihood";
		flag = lhoodproductepaserice.updatepostdata(id, livepost, session.getAttribute("loginID").toString());
		ModelAndView mv = new ModelAndView("redirect:/addlivelihood");
		if(flag) {
			mv.addObject("msg", msg);
			// return msg;
			mv.addObject("msg", "Livelihood Post Record is Updated Successfully");
		}
		else
			mv.addObject("error", msg);
		return mv;
	}
	
	@RequestMapping(value="/updateprodPostData", method = RequestMethod.POST)
	public ModelAndView updateprodPostData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id")Integer id,  @RequestParam("prodpost") Integer prodpost , Model model)
	{
		String msg = null;
		Boolean flag=false;
		msg = "output/AddLivelihood";
		flag = lhoodproductepaserice.updateprodpostdata(id, prodpost);
		ModelAndView mv = new ModelAndView("redirect:/addlivelihood");
		if(flag) {
			mv.addObject("msg", msg);
			// return msg;
			mv.addObject("msg", "Production Post Record is Updated Successfully");
		}
		else
			mv.addObject("error", msg);
		return mv;
	}
}
