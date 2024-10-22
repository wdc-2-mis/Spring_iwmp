package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
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

import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.ProjectPrepareBean;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpMProjectPrepare;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MFpoCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;
import app.model.outcome.MShgCoreactivity;
import app.model.outcome.MTrainingSubject;
import app.service.AllMasterService;

@Controller("allMasterController")
public class AllMasterController {

	HttpSession session;

	@Autowired(required = true)
	AllMasterService allmasterservice;

	@RequestMapping(value = "/allActivityMasters", method = RequestMethod.GET)
	public ModelAndView getallActivityMasters(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		if (session != null && session.getAttribute("loginID") != null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			mav = new ModelAndView("model/allactivitymaster");
			mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
			mav.addObject("productiondata", allmasterservice.getproductiondata());
			mav.addObject("epadata", allmasterservice.getepadata());
			mav.addObject("fpodata", allmasterservice.getfpodata());
			mav.addObject("shgdata", allmasterservice.getshgdata());
			mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
			mav.addObject("preparedness", allmasterservice.getPreparednessData());
		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

	@RequestMapping(value = "/saveAllMasterData", method = RequestMethod.POST)
	public ModelAndView saveData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id, @RequestParam("actdesc") String actdesc,
			@RequestParam("status") Boolean status) {
		// System.out.println("act code:" +actdesc);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/allactivitymaster");
		session = request.getSession(true);
		String res = new String("fail");
		if (session != null && session.getAttribute("loginID") != null) {
			res = allmasterservice.saveallmasterdata(id, actdesc, status, session.getAttribute("loginID").toString());
			if (res == "success") {
				mav.addObject("message", id + " " + "Data has been saved successfully");
			} else {
				mav.addObject("message", "Problem on Inserting data");
				mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
				mav.addObject("productiondata", allmasterservice.getproductiondata());
				mav.addObject("epadata", allmasterservice.getepadata());
				mav.addObject("fpodata", allmasterservice.getfpodata());
				mav.addObject("shgdata", allmasterservice.getshgdata());
				mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
			}
		} else {
			mav.addObject("message", "Problem on inserting data");
		}
		mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
		mav.addObject("productiondata", allmasterservice.getproductiondata());
		mav.addObject("epadata", allmasterservice.getepadata());
		mav.addObject("fpodata", allmasterservice.getfpodata());
		mav.addObject("shgdata", allmasterservice.getshgdata());
		mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
		mav.addObject("preparedness", allmasterservice.getPreparednessData());

		return mav;
	}
	
	@RequestMapping(value = "/saveProjectPrepareData", method = RequestMethod.POST)
	public ModelAndView saveProjectPrepareData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("seqno") Integer seqno, @RequestParam("pdesc") String pdesc, @RequestParam("srtname") String srtname,
			@RequestParam("status1") String status1, @RequestParam("status2") String status2 ) {
		// System.out.println("act code:" +actdesc);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/allactivitymaster");
		session = request.getSession(true);
		String res = new String("fail");
		if (session != null && session.getAttribute("loginID") != null) {
			res = allmasterservice.saveProjectPrepareData(seqno, pdesc, srtname, status1, status2, session);
			if (res == "success") {
				mav.addObject("message", "Data has been saved successfully");
			} else {
				mav.addObject("message", "Problem on Inserting data");
				mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
				mav.addObject("productiondata", allmasterservice.getproductiondata());
				mav.addObject("epadata", allmasterservice.getepadata());
				mav.addObject("fpodata", allmasterservice.getfpodata());
				mav.addObject("shgdata", allmasterservice.getshgdata());
				mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
			}
		} else {
			mav.addObject("message", "Problem on inserting data");
		}
		mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
		mav.addObject("productiondata", allmasterservice.getproductiondata());
		mav.addObject("epadata", allmasterservice.getepadata());
		mav.addObject("fpodata", allmasterservice.getfpodata());
		mav.addObject("shgdata", allmasterservice.getshgdata());
		mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
		mav.addObject("preparedness", allmasterservice.getPreparednessData());

		return mav;
	}

	@RequestMapping(value = "/updateactivitymasterData", method = RequestMethod.POST)
	public ModelAndView updatesubactData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") int id, @RequestParam("modal") String modal, @RequestParam("actdesc") String actdesc,
			@RequestParam("status") Boolean status) {
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/allactivitymaster");
		session = request.getSession(true);
		String res = new String("fail");
		if (session != null && session.getAttribute("loginID") != null) {
			res = allmasterservice.updateallactivitymaster(id, modal, actdesc, status,
					session.getAttribute("loginID").toString());
			if (res == "success") {
				mav.addObject("message",
						modal + " " + "Activity Code:" + " " + id + " " + "has been updated successfully");
			} else {
				mav.addObject("message", "Problem on updated data");
				mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
				mav.addObject("productiondata", allmasterservice.getproductiondata());
				mav.addObject("epadata", allmasterservice.getepadata());
				mav.addObject("fpodata", allmasterservice.getfpodata());
				mav.addObject("shgdata", allmasterservice.getshgdata());
				mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
			}
		} else {
			mav.addObject("message", "Problem on updating data");
		}
		mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
		mav.addObject("productiondata", allmasterservice.getproductiondata());
		mav.addObject("epadata", allmasterservice.getepadata());
		mav.addObject("fpodata", allmasterservice.getfpodata());
		mav.addObject("shgdata", allmasterservice.getshgdata());
		mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
		mav.addObject("preparedness", allmasterservice.getPreparednessData());

		return mav;
	}

	@RequestMapping(value = "/deleteallactivityData", method = RequestMethod.POST)
	public ModelAndView deleteallactivityData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("modal") String modal) {
		int id = 0;
		id = Integer.parseInt(request.getParameter("id"));
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/allactivitymaster");
		Boolean flag = false;
		try {
			session = request.getSession(true);
			if (session != null && session.getAttribute("loginID") != null) 
			{
				flag = allmasterservice.deletemodalactivityid(id, modal);
				if (flag) {
					mav.addObject("message", modal + " " + "Data has been removed successfully");
				} 
				else {
					mav.addObject("message", "Could not delete record because this record exist on transaction table");
					mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
					mav.addObject("productiondata", allmasterservice.getproductiondata());
					mav.addObject("epadata", allmasterservice.getepadata());
					mav.addObject("fpodata", allmasterservice.getfpodata());
					mav.addObject("shgdata", allmasterservice.getshgdata());
					mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
				}
			} 
			else {
				mav.addObject("message", "Session out");
			}
			mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
			mav.addObject("productiondata", allmasterservice.getproductiondata());
			mav.addObject("epadata", allmasterservice.getepadata());
			mav.addObject("fpodata", allmasterservice.getfpodata());
			mav.addObject("shgdata", allmasterservice.getshgdata());
			mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
			mav.addObject("preparedness", allmasterservice.getPreparednessData());
			
			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/getlivelihoodData", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> getactivityData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<MLivelihoodCoreactivity> proj = new ArrayList<MLivelihoodCoreactivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if (session != null && session.getAttribute("loginID") != null) {

			proj = allmasterservice.findlivelihoodactdesc(id);
			for (MLivelihoodCoreactivity list : proj) {
				bean = new PhysicalActBean();
				bean.setActdesc(list.getLivelihoodCoreactivityDesc());
				bean.setIsactive(list.getIsActive());
				finalList.add(bean);
			}

		} else {
			proj = null;

		}
		return finalList;
	}

	@RequestMapping(value = "/getproductionData", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> getproductionData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<MProductivityCoreactivity> proj = new ArrayList<MProductivityCoreactivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if (session != null && session.getAttribute("loginID") != null) {

			proj = allmasterservice.findproductionactdesc(id);
			for (MProductivityCoreactivity list : proj) {
				bean = new PhysicalActBean();
				bean.setActdesc(list.getProductivityCoreactivityDesc());
				bean.setIsactive(list.getIsActive());
				finalList.add(bean);
			}

		} else {
			proj = null;

		}
		return finalList;
	}

	@RequestMapping(value = "/getepaData", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> getepaData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<MEpaCoreactivity> proj = new ArrayList<MEpaCoreactivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if (session != null && session.getAttribute("loginID") != null) {

			proj = allmasterservice.findepaactdesc(id);
			for (MEpaCoreactivity list : proj) {
				bean = new PhysicalActBean();
				bean.setActdesc(list.getEpaDesc());
				bean.setIsactive(list.getIsActive());
				finalList.add(bean);
			}

		} else {
			proj = null;

		}
		return finalList;
	}

	@RequestMapping(value = "/getfpoData", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> getfpoData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<MFpoCoreactivity> proj = new ArrayList<MFpoCoreactivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if (session != null && session.getAttribute("loginID") != null) {

			proj = allmasterservice.findfpoactdesc(id);
			for (MFpoCoreactivity list : proj) {
				bean = new PhysicalActBean();
				bean.setActdesc(list.getFpoCoreactivityDesc());
				bean.setIsactive(list.getIsActive());
				finalList.add(bean);
			}

		} else {
			proj = null;

		}
		return finalList;
	}

	@RequestMapping(value = "/getshgData", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> getshgData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<MShgCoreactivity> proj = new ArrayList<MShgCoreactivity>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if (session != null && session.getAttribute("loginID") != null) {

			proj = allmasterservice.findshgactdesc(id);
			for (MShgCoreactivity list : proj) {
				bean = new PhysicalActBean();
				bean.setActdesc(list.getShgCoreactivityDesc());
				bean.setIsactive(list.getIsActive());
				finalList.add(bean);
			}

		} else {
			proj = null;

		}
		return finalList;
	}

	@RequestMapping(value = "/gettrainingData", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActBean> gettrainingData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<MTrainingSubject> proj = new ArrayList<MTrainingSubject>();
		List<PhysicalActBean> finalList = new ArrayList<PhysicalActBean>();
		PhysicalActBean bean = new PhysicalActBean();
		if (session != null && session.getAttribute("loginID") != null) {

			proj = allmasterservice.findtrainingactdesc(id);
			for (MTrainingSubject list : proj) {
				bean = new PhysicalActBean();
				bean.setActdesc(list.getTrainingSubDesc());
				bean.setIsactive(list.getIsActive());
				finalList.add(bean);
			}

		} else {
			proj = null;

		}
		return finalList;
	}

	@RequestMapping(value = "/getPreparednessMasterFind", method = RequestMethod.POST)
	@ResponseBody
	public List<ProjectPrepareBean> getPreparednessMasterFind(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<IwmpMProjectPrepare> proj = new ArrayList<IwmpMProjectPrepare>();
		List<ProjectPrepareBean> finalList = new ArrayList<ProjectPrepareBean>();
		ProjectPrepareBean bean = new ProjectPrepareBean();
		if (session != null && session.getAttribute("loginID") != null) 
		{
			proj = allmasterservice.findProjectPrepare(id);
			for (IwmpMProjectPrepare list : proj) 
			{
				bean.setProjectMprepareId(list.getProjectMprepareId());
				bean.setActive(list.getActive());
				bean.setPrepareDesc(list.getPrepareDesc());
				bean.setShortDesc(list.getShortDesc());
				bean.setSelectedDesc1(list.getSelectedDesc1());
				bean.setSelectedDesc2(list.getSelectedDesc2());
				bean.setSequence(list.getSequence());
				
				finalList.add(bean);
			}
		} 
		else {
			proj = null;

		}
		return finalList;
	}
	
	@RequestMapping(value = "/updateProjectPrepareData", method = RequestMethod.POST)
	public ModelAndView updateProjectPrepareData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") int id, @RequestParam("modal") String modal, @RequestParam("prepareDesc") String prepareDesc,
			@RequestParam("status") Boolean status, @RequestParam("shortDesc") String shortDesc, @RequestParam("selectedDesc1") String selectedDesc1
			, @RequestParam("selectedDesc2") String selectedDesc2, @RequestParam("sequence") int sequence) {
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/allactivitymaster");
		session = request.getSession(true);
		String res = new String("fail");
		if (session != null && session.getAttribute("loginID") != null) {
			res = allmasterservice.updateProjectPrepareData(id, modal, prepareDesc, status, shortDesc, selectedDesc1, selectedDesc2, sequence, session);
			if (res == "success") {
				mav.addObject("message",
						modal+" "+ "has been updated successfully");
			} else {
				mav.addObject("message", "Problem on updated data");
				mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
				mav.addObject("productiondata", allmasterservice.getproductiondata());
				mav.addObject("epadata", allmasterservice.getepadata());
				mav.addObject("fpodata", allmasterservice.getfpodata());
				mav.addObject("shgdata", allmasterservice.getshgdata());
				mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
			}
		} else {
			mav.addObject("message", "Problem on updating data");
		}
		mav.addObject("livelihooddata", allmasterservice.getlivelihooddata());
		mav.addObject("productiondata", allmasterservice.getproductiondata());
		mav.addObject("epadata", allmasterservice.getepadata());
		mav.addObject("fpodata", allmasterservice.getfpodata());
		mav.addObject("shgdata", allmasterservice.getshgdata());
		mav.addObject("trainingdata", allmasterservice.gettrainingsubdata());
		mav.addObject("preparedness", allmasterservice.getPreparednessData());
		
		return mav;
	}

}
