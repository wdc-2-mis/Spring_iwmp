package app.controllers.reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.PfmsTreasureBean;
import app.service.CommonService;
import app.service.reports.TargetAchievementQuarterService;

@Controller("StateWisePFMSComponentController")
public class StateWisePFMSComponentController {
	
	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@RequestMapping(value="/getStateWisePFMSComponent", method = RequestMethod.GET)
	public ModelAndView getStateWisePFMSComponent(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView();
		String year= request.getParameter("year");
			mav = new ModelAndView("reports/stateWisePFMSComponent");
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("year", year);
		return mav; 
	}
	
	@RequestMapping(value="/getStateWisePFMSComponent", method = RequestMethod.POST)
	public ModelAndView getStateWisePFMSComponentpost(HttpServletRequest request, HttpServletResponse response)
	{
	
			String year= request.getParameter("year");
			String finName= request.getParameter("finName");
		
			ModelAndView mav = new ModelAndView();
			String data[] = null;
			int i=1;
			List<String[]> dataList = new ArrayList<String[]>();
			ArrayList dataListNetTotal = new ArrayList();
			List<PfmsTreasureBean> list=new  ArrayList<PfmsTreasureBean>();
			mav = new ModelAndView("reports/stateWisePFMSComponent");
			String[] dataArrNetTotalStr = null;
			Integer[] dataArrNetTotal = null;
			BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
			list=ser.getStateWisePFMSComponent(Integer.parseInt(year));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "", "", ""};
			if(list != null) 
			{
				for(PfmsTreasureBean bean : list) 
				{
					dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(bean.getAfforestation1());
					dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(bean.getAgriculture1());
					dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(bean.getOthers1());
					dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(bean.getCountourbunding3());
					dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(bean.getOthers3());
					dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(bean.getFarmponds4());
					dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(bean.getNallahbunds4());
					dataArrNetTotalBD[7] = dataArrNetTotalBD[7].add(bean.getOthers4());
					dataArrNetTotalBD[8] = dataArrNetTotalBD[8].add(bean.getCheckdams5());
					dataArrNetTotalBD[9] = dataArrNetTotalBD[9].add(bean.getOthers5());
					dataArrNetTotalBD[10] = dataArrNetTotalBD[10].add(bean.getNallahbunding5());
					dataArrNetTotalBD[11] = dataArrNetTotalBD[11].add(bean.getFarmponds6());
				}
				dataListNetTotal = new ArrayList();
				dataArrNetTotalStr[0] = dataArrNetTotalBD[0].toString();
				dataArrNetTotalStr[1] = dataArrNetTotalBD[1].toString();
				dataArrNetTotalStr[2] = dataArrNetTotalBD[2].toString();
				dataArrNetTotalStr[3] = dataArrNetTotalBD[3].toString();
				dataArrNetTotalStr[4] = dataArrNetTotalBD[4].toString();
				dataArrNetTotalStr[5] = dataArrNetTotalBD[5].toString();
				dataArrNetTotalStr[6] = dataArrNetTotalBD[6].toString();
				dataArrNetTotalStr[7] = dataArrNetTotalBD[7].toString();
				dataArrNetTotalStr[8] = dataArrNetTotalBD[8].toString();
				dataArrNetTotalStr[9] = dataArrNetTotalBD[9].toString();
				dataArrNetTotalStr[10] = dataArrNetTotalBD[10].toString();
				dataArrNetTotalStr[11] = dataArrNetTotalBD[11].toString();
				
				dataListNetTotal.add(dataArrNetTotalStr);
				mav.addObject("dataListNetTotal", dataListNetTotal);
			}
			mav.addObject("dataList", list);
			mav.addObject("dataListsize", list.size());
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("year", year);
			
		return mav; 
	}
	
	
	@RequestMapping(value="/getStateWisePFMSNotComponent", method = RequestMethod.GET)
	public ModelAndView getStateWisePFMSNotComponent(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView();
		String year= request.getParameter("year");
			mav = new ModelAndView("reports/stateWisePFMSNotComponent");
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("year", year);
		return mav; 
	}
	
	@RequestMapping(value="/getStateWisePFMSNotComponent", method = RequestMethod.POST)
	public ModelAndView getStateWisePFMSNotComponentpost(HttpServletRequest request, HttpServletResponse response)
	{
	
			String year= request.getParameter("year");
			String finName= request.getParameter("finName");
		
			ModelAndView mav = new ModelAndView();
			String data[] = null;
			int i=1;
			List<String[]> dataList = new ArrayList<String[]>();
			ArrayList dataListNetTotal = new ArrayList();
			List<PfmsTreasureBean> list=new  ArrayList<PfmsTreasureBean>();
			mav = new ModelAndView("reports/stateWisePFMSNotComponent");
			String[] dataArrNetTotalStr = null;
			Integer[] dataArrNetTotal = null;
			BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
					 BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
			list=ser.getStateWisePFMSNotComponent(Integer.parseInt(year));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
			if(list != null) 
			{
				for(PfmsTreasureBean bean : list) 
				{
					dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(bean.getHorticulture());
					dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(bean.getBadmincost());
					dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(bean.getBsalary());
					dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(bean.getBtransport());
					dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(bean.getBother());
					dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(bean.getCmonitor());
					dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(bean.getEother());
					dataArrNetTotalBD[7] = dataArrNetTotalBD[7].add(bean.getGlivelihoodact());
					dataArrNetTotalBD[8] = dataArrNetTotalBD[8].add(bean.getGother());
					dataArrNetTotalBD[9] = dataArrNetTotalBD[9].add(bean.getHother());
					dataArrNetTotalBD[10] = dataArrNetTotalBD[10].add(bean.getHdistributionirrig());
					dataArrNetTotalBD[11] = dataArrNetTotalBD[11].add(bean.getIpromotion());
					dataArrNetTotalBD[12] = dataArrNetTotalBD[12].add(bean.getIother());
					dataArrNetTotalBD[13] = dataArrNetTotalBD[13].add(bean.getJevaluation());
					dataArrNetTotalBD[14] = dataArrNetTotalBD[14].add(bean.getKinstitutionalfund());
					dataArrNetTotalBD[15] = dataArrNetTotalBD[15].add(bean.getKsalary());
					dataArrNetTotalBD[16] = dataArrNetTotalBD[16].add(bean.getKofficeexpenses());
					dataArrNetTotalBD[17] = dataArrNetTotalBD[17].add(bean.getLflexifund());
					dataArrNetTotalBD[18] = dataArrNetTotalBD[18].add(bean.getNeeranchalwater());
					dataArrNetTotalBD[19] = dataArrNetTotalBD[19].add(bean.getRwdc2admin());
					dataArrNetTotalBD[20] = dataArrNetTotalBD[20].add(bean.getRsalary());
					dataArrNetTotalBD[21] = dataArrNetTotalBD[21].add(bean.getRexpenditure());
					dataArrNetTotalBD[22] = dataArrNetTotalBD[22].add(bean.getRtransport());
					dataArrNetTotalBD[23] = dataArrNetTotalBD[23].add(bean.getRother());
					dataArrNetTotalBD[24] = dataArrNetTotalBD[24].add(bean.getUother());
					dataArrNetTotalBD[25] = dataArrNetTotalBD[25].add(bean.getVprepardpr());
					dataArrNetTotalBD[26] = dataArrNetTotalBD[26].add(bean.getWlivelihoodact());
					dataArrNetTotalBD[27] = dataArrNetTotalBD[27].add(bean.getWother());
					dataArrNetTotalBD[28] = dataArrNetTotalBD[28].add(bean.getXother());
					dataArrNetTotalBD[29] = dataArrNetTotalBD[29].add(bean.getXvermicompost());
					dataArrNetTotalBD[30] = dataArrNetTotalBD[30].add(bean.getWdc2flexifund());
					
					
				}
				dataListNetTotal = new ArrayList();
				dataArrNetTotalStr[0] = dataArrNetTotalBD[0].toString();
				dataArrNetTotalStr[1] = dataArrNetTotalBD[1].toString();
				dataArrNetTotalStr[2] = dataArrNetTotalBD[2].toString();
				dataArrNetTotalStr[3] = dataArrNetTotalBD[3].toString();
				dataArrNetTotalStr[4] = dataArrNetTotalBD[4].toString();
				dataArrNetTotalStr[5] = dataArrNetTotalBD[5].toString();
				dataArrNetTotalStr[6] = dataArrNetTotalBD[6].toString();
				dataArrNetTotalStr[7] = dataArrNetTotalBD[7].toString();
				dataArrNetTotalStr[8] = dataArrNetTotalBD[8].toString();
				dataArrNetTotalStr[9] = dataArrNetTotalBD[9].toString();
				dataArrNetTotalStr[10] = dataArrNetTotalBD[10].toString();
				dataArrNetTotalStr[11] = dataArrNetTotalBD[11].toString();
				dataArrNetTotalStr[12] = dataArrNetTotalBD[12].toString();
				dataArrNetTotalStr[13] = dataArrNetTotalBD[13].toString();
				dataArrNetTotalStr[14] = dataArrNetTotalBD[14].toString();
				dataArrNetTotalStr[15] = dataArrNetTotalBD[15].toString();
				dataArrNetTotalStr[16] = dataArrNetTotalBD[16].toString();
				dataArrNetTotalStr[17] = dataArrNetTotalBD[17].toString();
				dataArrNetTotalStr[18] = dataArrNetTotalBD[18].toString();
				dataArrNetTotalStr[19] = dataArrNetTotalBD[19].toString();
				dataArrNetTotalStr[20] = dataArrNetTotalBD[20].toString();
				dataArrNetTotalStr[21] = dataArrNetTotalBD[21].toString();
				dataArrNetTotalStr[22] = dataArrNetTotalBD[22].toString();
				dataArrNetTotalStr[23] = dataArrNetTotalBD[23].toString();
				dataArrNetTotalStr[24] = dataArrNetTotalBD[24].toString();
				dataArrNetTotalStr[25] = dataArrNetTotalBD[25].toString();
				dataArrNetTotalStr[26] = dataArrNetTotalBD[26].toString();
				dataArrNetTotalStr[27] = dataArrNetTotalBD[27].toString();
				dataArrNetTotalStr[28] = dataArrNetTotalBD[28].toString();
				dataArrNetTotalStr[29] = dataArrNetTotalBD[29].toString();
				dataArrNetTotalStr[30] = dataArrNetTotalBD[30].toString();
				
				dataListNetTotal.add(dataArrNetTotalStr);
				mav.addObject("dataListNetTotal", dataListNetTotal);
			}
			mav.addObject("dataList", list);
			mav.addObject("dataListsize", list.size());
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("year", year);
			
		return mav; 
	}

}
