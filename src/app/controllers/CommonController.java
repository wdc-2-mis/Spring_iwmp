package app.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import com.google.api.translate.Language; 
//import com.google.api.translate.Translate;
import com.itextpdf.text.log.SysoCounter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.LastModified;

import app.bean.GalleryBean;
import app.bean.RoleMenuList;
import app.bean.reports.DolrDashboardBean;
import app.common.CommonFunctions;
import app.service.BlockMasterService;
import app.service.CommonService;
import app.service.DashBoardService;
import app.service.DistrictMasterService;
import app.service.MenuService;

@Controller("commonController")
public class CommonController {

	HttpSession session;

	@Autowired(required = true)
	CommonFunctions commonFunctions;

	@Autowired(required = true)
	AlertController alertController;

	@Autowired(required = true)
	StoriesController storiesController;

	@Autowired(required = true)
	GalleryController galleryController;

	@Autowired(required = true)
	IndexCircleDataController indexCircleDataController;

	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired
	HitCountController hitCountController;

	@Autowired
	BlockMasterService blockMasterService;

	@Autowired
	CommonService commonService;

	@Autowired
	DashBoardService  dashBoardService;
	// afterLogin is used to get the after login page
	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	public ModelAndView afterLogin(HttpServletRequest request, Locale locale)
			throws ParseException, GeneralSecurityException, IOException {
		List<String> list = indexCircleDataController.getData();
		String hitCount = "0";
		ModelAndView mav = new ModelAndView("index3");
		if (alertController.getAlert(locale) != null)
			mav.addObject("alert", alertController.getAlert(locale));
		else
			mav.addObject("alert", null);
		if (alertController.getAlert(locale) != null)
			mav.addObject("stories", storiesController.getStories());
		else
			mav.addObject("stories", null);
		if (list != null)
			mav.addObject("area_soilmoisture_activities_achie", list.get(0));
		else
			mav.addObject("area_soilmoisture_activities_achie", "Data Not Available");
		if (list != null)
			mav.addObject("area_afforestation_horticulture_achie", list.get(1));
		else
			mav.addObject("area_afforestation_horticulture_achie", "Data Not Available");
		if (list != null)
			mav.addObject("water_created_renovated_achie", list.get(2));
		else
			mav.addObject("water_created_renovated_achie", "Data Not Available");
		if (list != null)
			mav.addObject("protective_irrigation_achie", list.get(3));
		else
			mav.addObject("protective_irrigation_achie", "Data Not Available");
		if (list != null)
			mav.addObject("farmer_benefitted_achie", list.get(4));
		else
			mav.addObject("farmer_benefitted_achie", "Data Not Available");
		if (list != null)
			mav.addObject("man_days_gen", list.get(5));
		else
			mav.addObject("man_days_gen", "Data Not Available");
		if (list != null)
			mav.addObject("degraded_rainfed", list.get(6));
		else
			mav.addObject("degraded_rainfed", "Data Not Available");
		
		if (alertController.getWhatsNew() != null)
			mav.addObject("whatsNew", alertController.getWhatsNew());
		else
			mav.addObject("whatsNew", "Data Not Available");


		if (galleryController.getIndexImages() != null)
			mav.addObject("images", galleryController.getIndexImages());
		else
			mav.addObject("images", null);
		String Count = hitCountController.getHitCount(request).toString();
		for (int i = 7; i > Count.length(); i--) {
			hitCount += "0";
			if (i - 1 == Count.length())
				hitCount += Count;
		}
		mav.addObject("hitCount", hitCount);
		return mav;
	}

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	/*commented by Himanshu 
	 * 
	 * @RequestMapping(value = "/index3", method = RequestMethod.GET) public
	 * ModelAndView index3(HttpServletRequest request, Locale locale) throws
	 * ParseException, GeneralSecurityException, IOException {
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * 
	 * 
	 * String[] languages = Locale.getISOLanguages(); for(String lauguage :
	 * languages) { locale = new Locale(lauguage); System.out.println(lauguage +":"
	 * +locale.getDisplayLanguage()); }
	 * 
	 * 
	 * System.out.println("value of locale:" +locale); List<String> list =
	 * indexCircleDataController.getData(); String hitCount = "0"; ModelAndView mav
	 * = new ModelAndView("index3"); if (alertController.getAlert(locale) != null)
	 * mav.addObject("alert", alertController.getAlert(locale)); else
	 * mav.addObject("alert", null); if (alertController.getAlert(locale) != null)
	 * mav.addObject("stories", storiesController.getStories()); else
	 * mav.addObject("stories", null); if (list != null) mav.addObject("whsnew",
	 * list.get(0)); else mav.addObject("whsnew", "Data Not Available"); if (list !=
	 * null) mav.addObject("whsrenovate", list.get(1)); else
	 * mav.addObject("whsrenovate", "Data Not Available"); if (list != null)
	 * mav.addObject("afforestation", list.get(2)); else
	 * mav.addObject("afforestation", "Data Not Available"); if (list != null)
	 * mav.addObject("horticulture", list.get(3)); else
	 * mav.addObject("horticulture", "Data Not Available"); if (list != null)
	 * mav.addObject("degradedland", list.get(4)); else
	 * mav.addObject("degradedland", "Data Not Available"); if (list != null)
	 * mav.addObject("rainfed", list.get(5)); else mav.addObject("rainfed",
	 * "Data Not Available"); if (list != null) mav.addObject("nilsingle",
	 * list.get(6)); else mav.addObject("nilsingle", "Data Not Available"); if (list
	 * != null) mav.addObject("mandays", list.get(7)); else mav.addObject("mandays",
	 * "Data Not Available"); if (list != null) mav.addObject("croppedarea",
	 * list.get(8)); else mav.addObject("croppedarea", "Data Not Available"); if
	 * (list != null) mav.addObject("farmersbenefited", list.get(9)); else
	 * mav.addObject("farmersbenefited", "Data Not Available");
	 * 
	 * if (list != null) { mav.addObject("soilmoistureconservation", list.get(9)); }
	 * 
	 * if (alertController.getWhatsNew() != null) mav.addObject("whatsNew",
	 * alertController.getWhatsNew()); else mav.addObject("whatsNew",
	 * "Data Not Available");
	 * 
	 * if (galleryController.getIndexImages() != null) mav.addObject("images",
	 * galleryController.getIndexImages()); else mav.addObject("images", null);
	 * String Count = hitCountController.getHitCount(request).toString(); for (int i
	 * = 7; i > Count.length(); i--) { hitCount += "0"; if (i - 1 == Count.length())
	 * hitCount += Count; } mav.addObject("hitCount", hitCount); return mav; }
	 */

	@RequestMapping(value = "/index3", method = RequestMethod.GET)
	public ModelAndView index3(HttpServletRequest request, Locale locale)
			throws ParseException, GeneralSecurityException, IOException {
		logger.info("Welcome home! The client locale is {}.", locale);

		/*
		 * String[] languages = Locale.getISOLanguages(); for(String lauguage :
		 * languages) { locale = new Locale(lauguage); System.out.println(lauguage +":"
		 * +locale.getDisplayLanguage()); }
		 */

		/* System.out.println("value of locale:" +locale); */
		List<String> list = indexCircleDataController.getData();
		String hitCount = "0";
		ModelAndView mav = new ModelAndView("index3");
		if (alertController.getAlert(locale) != null)
			mav.addObject("alert", alertController.getAlert(locale));
		else
			mav.addObject("alert", null);
		if (alertController.getAlert(locale) != null)
			mav.addObject("stories", storiesController.getStories());
		else
			mav.addObject("stories", null);
		if (list != null)
			mav.addObject("area_soilmoisture_activities_achie", list.get(0));
		else
			mav.addObject("area_soilmoisture_activities_achie", "Data Not Available");
		if (list != null)
			mav.addObject("area_afforestation_horticulture_achie", list.get(1));
		else
			mav.addObject("area_afforestation_horticulture_achie", "Data Not Available");
		if (list != null)
			mav.addObject("water_created_renovated_achie", list.get(2));
		else
			mav.addObject("water_created_renovated_achie", "Data Not Available");
		if (list != null)
			mav.addObject("protective_irrigation_achie", list.get(3));
		else
			mav.addObject("protective_irrigation_achie", "Data Not Available");
		if (list != null)
			mav.addObject("farmer_benefitted_achie", list.get(4));
		else
			mav.addObject("farmer_benefitted_achie", "Data Not Available");
		if (list != null)
			mav.addObject("man_days_gen", list.get(5));
		else
			mav.addObject("man_days_gen", "Data Not Available");
		if (list != null)
			mav.addObject("degraded_rainfed", list.get(6));
		else
			mav.addObject("degraded_rainfed", "Data Not Available");
		
		if (alertController.getWhatsNew() != null)
			mav.addObject("whatsNew", alertController.getWhatsNew());
		else
			mav.addObject("whatsNew", "Data Not Available");

		if (galleryController.getIndexImages() != null)
			mav.addObject("images", galleryController.getIndexImages());
		else
			mav.addObject("images", null);
		String Count = hitCountController.getHitCount(request).toString();
		for (int i = 7; i > Count.length(); i--) {
			hitCount += "0";
			if (i - 1 == Count.length())
				hitCount += Count;
		}
		mav.addObject("hitCount", hitCount);
		return mav;
	}

	@RequestMapping(value = "/lastModify", method = RequestMethod.GET)
	@ResponseBody
	public String lastModify(HttpServletRequest request, @RequestParam(value = "page") String page) {
		// System.out.println("Page: "+page.substring(page.lastIndexOf("/")+1));
		page = page.substring(page.lastIndexOf("/") + 1);
		String filePath = "D:\\\\New WorkSpace\\\\Spring_Working\\\\WebContent\\\\WEB-INF\\\\jsp";
		String currentWorkingDir = System.getProperty("user.dir");
		// System.out.println("currentWorkingDir"+currentWorkingDir);
		Path root = Paths.get(".").normalize().toAbsolutePath();
		// System.out.println(currentWorkingDir.substring(currentWorkingDir.lastIndexOf("\\")+1)+":"+root.toString());
		File f = new File(filePath);
		File filesList[] = f.listFiles();
		String lastModifyDate = null;
		try {
			// for(File file : filesList) {
			// System.out.println("File name: "+page);
			/*
			 * System.out.println("File path: "+file.getAbsolutePath());
			 * System.out.println("Size :"+file.getTotalSpace()); System.out.println(" ");
			 */
			// if(page.equals(file.getName())) {
			/*
			 * Path fi = Paths.get(filePath+"\\"+page); BasicFileAttributes attr =
			 * Files.readAttributes(fi, BasicFileAttributes.class);
			 * 
			 * 
			 * System.out.println("creationTime: " + attr.creationTime());
			 * System.out.println("lastAccessTime: " + attr.lastAccessTime());
			 * System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
			 * System.out.println(" ");
			 * 
			 * LocalDateTime myDateObj = LocalDateTime.ofInstant(
			 * attr.lastModifiedTime().toInstant(), ZoneId.systemDefault());
			 * DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			 * DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
			 * 
			 * String formattedDate = myDateObj.format(date); String formattedTime =
			 * myDateObj.format(time); lastModifyDate=formattedDate+"@"+formattedTime;
			 */
			// }
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastModifyDate;
	}

	@RequestMapping(value="/getwhshomedata", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getwhshomedata(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id)
	{
		LinkedHashMap<Integer,List<DolrDashboardBean>> map = new LinkedHashMap<Integer,List<DolrDashboardBean>>();
		List<DolrDashboardBean> proj = new ArrayList<DolrDashboardBean>();
       	proj = dashBoardService.getHomePagetarget(id);
		return proj;
	}
	
	@RequestMapping(value="/getcircledisrictdata", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getcircledisrictdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id, @RequestParam("activity") String activity)
	{
		LinkedHashMap<Integer,List<DolrDashboardBean>> map = new LinkedHashMap<Integer,List<DolrDashboardBean>>();
		List<DolrDashboardBean> proj = new ArrayList<DolrDashboardBean>();
       	proj = dashBoardService.getCircleDistricttarget(id, activity);
		return proj;
	}
	
	
	// getIndex will return the index page with all required data
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(HttpServletRequest request, Locale locale) throws Exception {
		List<String> list = indexCircleDataController.getData();
		String hitCount = "0";
		ModelAndView mav = new ModelAndView("index3");
		if (alertController.getAlert(locale) != null)
			mav.addObject("alert", alertController.getAlert(locale));
		else
			mav.addObject("alert", null);
		if (alertController.getAlert(locale) != null)
			mav.addObject("stories", storiesController.getStories());
		else
			mav.addObject("stories", null);
		if (list != null)
			mav.addObject("area_soilmoisture_activities_achie", list.get(0));
		else
			mav.addObject("area_soilmoisture_activities_achie", "Data Not Available");
		if (list != null)
			mav.addObject("area_afforestation_horticulture_achie", list.get(1));
		else
			mav.addObject("area_afforestation_horticulture_achie", "Data Not Available");
		if (list != null)
			mav.addObject("water_created_renovated_achie", list.get(2));
		else
			mav.addObject("water_created_renovated_achie", "Data Not Available");
		if (list != null)
			mav.addObject("protective_irrigation_achie", list.get(3));
		else
			mav.addObject("protective_irrigation_achie", "Data Not Available");
		if (list != null)
			mav.addObject("farmer_benefitted_achie", list.get(4));
		else
			mav.addObject("farmer_benefitted_achie", "Data Not Available");
		if (list != null)
			mav.addObject("man_days_gen", list.get(5));
		else
			mav.addObject("man_days_gen", "Data Not Available");
		if (list != null)
			mav.addObject("degraded_rainfed", list.get(6));
		else
			mav.addObject("degraded_rainfed", "Data Not Available");
		
		if (alertController.getWhatsNew() != null)
			mav.addObject("whatsNew", alertController.getWhatsNew());
		else
			mav.addObject("whatsNew", "Data Not Available");

		if (galleryController.getIndexImages() != null)
			mav.addObject("images", galleryController.getIndexImages());
		else
			mav.addObject("images", null);
		String Count = hitCountController.getHitCount(request).toString();
		for (int i = 7; i > Count.length(); i--) {
			hitCount += "0";
			if (i - 1 == Count.length())
				hitCount += Count;
		}
		mav.addObject("hitCount", hitCount);
		return mav;
	}

// aboutUs will return about us page with data
	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public ModelAndView aboutUs(HttpServletRequest request) {
		return new ModelAndView("aboutus");
	}

	// Reports will return report page with data
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView Reports(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView();

		String parentName = "";
		String data[] = null;
		int i = 1;
		List<String[]> dataList = new ArrayList<String[]>();
		List<RoleMenuList> list = new ArrayList<RoleMenuList>();
		mav = new ModelAndView("reports");
		String lang = locale.getDisplayLanguage();
		System.out.println("report lang:" + lang);
		if (lang == "Hindi") {
			list = commonService.getPublicHindiReport();
		} else {
			list = commonService.getPublicReport();
		}

		for (RoleMenuList bean : list) {
			data = new String[10];
			data[0] = String.valueOf(i); // serial no
			data[1] = bean.getParentid().toString(); // parentId
			if (parentName.isEmpty() || !parentName.equals(bean.getParentname())) {
				data[2] = bean.getParentname(); // parentName
				parentName = bean.getParentname();
			} else {
				data[2] = "";
			}
			i++;
			data[3] = bean.getMenuid().toString();
			data[4] = bean.getMenuname();
			data[5] = bean.getTarget();

			dataList.add(data);
		}

		mav.addObject("dataList", dataList);
		mav.addObject("dataListsize", dataList.size());
		return mav;
	}

	// technicalSupport will return technical support page
	@RequestMapping(value = "/technicalsupport", method = RequestMethod.GET)
	public ModelAndView technicalSupport(HttpServletRequest request) {
		return new ModelAndView("technicalsupport");
	}

	// dolrSupport will return dolr support page
	/*
	 * @RequestMapping(value="/dolrsupport", method = RequestMethod.GET) public
	 * ModelAndView dolrSupport(HttpServletRequest request) { return new
	 * ModelAndView("dolrsupport"); }
	 */

	
	
	

	// handleError404 will return page not found page
	@RequestMapping(value = "/handleError404", method = RequestMethod.GET)
	public ModelAndView handleError404(HttpServletRequest request) {
		return new ModelAndView("pagenotfound");
	}

	// successStories will return success Stories page with all data
	@RequestMapping(value = "/successStories", method = RequestMethod.GET)
	public ModelAndView successStories(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("successStories");
		String path = "", name = "", subject = "";
		if (request.getParameter("path") != null) {
			path = request.getParameter("path");
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the
			// following line to
			// avoid encoded attacks.
			// path = ESAPI.encoder().canonicalize(path);

			// Avoid null characters
			path = path.replaceAll("", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			path = scriptPattern.matcher(path).replaceAll("");

//            // Avoid anything in a src='...' type of expression
//            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            path = scriptPattern.matcher(path).replaceAll("");
//
//            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            path = scriptPattern.matcher(path).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			path = scriptPattern.matcher(path).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			path = scriptPattern.matcher(path).replaceAll("");

//            // Avoid eval(...) expressions
//            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            path = scriptPattern.matcher(path).replaceAll("");
//
//            // Avoid expression(...) expressions
//            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            path = scriptPattern.matcher(path).replaceAll("");
//
//            // Avoid javascript:... expressions
//            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
//            path = scriptPattern.matcher(path).replaceAll("");
//
//            // Avoid vbscript:... expressions
//            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
//            path = scriptPattern.matcher(path).replaceAll("");
//
//            // Avoid onload= expressions
//            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//            path = scriptPattern.matcher(path).replaceAll("");
		}
		if (request.getParameter("name") != null)
			name = request.getParameter("name");
		if (request.getParameter("subject") != null)
			subject = request.getParameter("subject");
		int total = 12;
		int pageid = 1;
		boolean displayNext = true;
		if (request.getParameter("id") != null)
			pageid = Integer.parseInt(request.getParameter("id"));
		int page = pageid;
		if (pageid > 0)
			if (pageid == 1) {
			} else {
				pageid = (pageid - 1) * total + 1;
			}
		int maxRecord = storiesController.getAllStories(pageid, total).size();
		if (maxRecord == total)
			displayNext = true;
		else
			displayNext = false;
		mav.addObject("stories", storiesController.getAllStories(pageid, total));
		mav.addObject("prevPage", page - 1);
		mav.addObject("nextPage", page + 1);
		mav.addObject("displayNext", displayNext);
		mav.addObject("path", path);
		mav.addObject("name", name);
		mav.addObject("subject", subject);
		return mav;
	}

	/*
	 * List<IwmpUserUploadDetail> list = new ArrayList<IwmpUserUploadDetail>();
	 * 
	 * @RequestMapping(value="/successStories", method = RequestMethod.GET) public
	 * ModelAndView successStories() { list = new ArrayList<IwmpUserUploadDetail>();
	 * int pageid=1; int total=10; if(pageid>0) if(pageid==1){} else{
	 * pageid=(pageid-1)*total+1; } list =
	 * storiesController.getAllStories(pageid,total);
	 * System.out.println(pageid+" ::: "+list); return new
	 * ModelAndView("successStories", "stories", list ); }
	 */

	// gallery will return gallery page with data
	@RequestMapping(value = "/gallery", method = RequestMethod.GET)
	public ModelAndView gallery(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("gallery");
		Map<String, List<GalleryBean>> map = new HashMap<String, List<GalleryBean>>();
		Map<String, List<GalleryBean>> allfiles = new HashMap<String, List<GalleryBean>>();
		Integer stCode = 0;
		if (request.getParameter("code") != null)
			stCode = Integer.parseInt(request.getParameter("code"));
		// System.out.println(stCode);
		map.putAll(galleryController.getFiles(stCode));
		allfiles.putAll(galleryController.getAllImages());
		mav.addObject("files", map);
		mav.addObject("allfiles", allfiles);

		return mav;
	}

	// siteMap will siteMap page
	@RequestMapping(value = "/siteMap", method = RequestMethod.GET)
	public ModelAndView siteMap(HttpServletRequest request) {
		return new ModelAndView("siteMap");
	}

	// whatsNew will return whatsNew page
	@RequestMapping(value = "/whatsNew", method = RequestMethod.GET)
	public ModelAndView whatsNew(HttpServletRequest request) {
		return new ModelAndView("whatsNew");
	}

	@RequestMapping(value = "/getDistrictData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getAllSubcategories(HttpServletRequest request,
			@RequestParam("stateCode") int stateCode) {
		// System.out.print("SSS" + stateCode);
		return districtMasterService.getDistrictByStateCode((int) stateCode);
	}

	@RequestMapping(value = "/getDistrictDataNew", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getDistrictDataNew(HttpServletRequest request,
			@RequestParam("stateCode") int stateCode) {
		// System.out.print("SSS" + stateCode);
		return districtMasterService.getDistrictDataNew((int) stateCode);
	}

	@RequestMapping(value = "/getBlockData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getAllSubBlockcategories(HttpServletRequest request,
			@RequestParam("distCode") int distCode) {
		System.out.print("SSS" + distCode);
		return blockMasterService.getBlockByDistCode((int) distCode);
	}

	@RequestMapping(value = "/getGpData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getAllSubGpcategories(HttpServletRequest request,
			@RequestParam("blockCode") int blockCode) {
		System.out.print("SSS" + blockCode);
		return commonService.getGramPanchayatByblockCode((int) blockCode);
	}

	@RequestMapping(value = "/userAction", method = RequestMethod.POST)
	@ResponseBody
	public String userAction(HttpServletRequest request, @RequestParam("url") String url) {
		String res = "";
		Boolean result = false;
		session = request.getSession(true);
		result = commonService.authenticateUser(url, session, request);
		return res;
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	@ResponseBody
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("url") String url,
			@RequestParam("fileName") String fileName) throws IOException {

		try {
			// response.setContentType("application/octet-stream");
			File downloadFile = new File(url + fileName);
			FileInputStream inStream = new FileInputStream(downloadFile);
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inStream.close();
			outStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
