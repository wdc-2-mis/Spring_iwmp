package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import app.bean.reports.SelfHelpGroupReportBean;
import app.common.CommonFunctions;
import app.service.SelfHelpGroupService;
import app.service.StateMasterService;

@Controller("SelfHelpGroupControllerReport")
public class SelfHelpGroupControllerReport {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	private SelfHelpGroupService self;
	
	private PdfTemplate t;
	private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

	@RequestMapping(value = "/getRptshg", method = RequestMethod.GET)
	public ModelAndView rpcreport(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("output/SelfHelpGroupState");
		mav.addObject("stateList", stateMasterService.getAllState());
		Map<String, String> map = new HashMap<String, String>();
		map.put("shg", "Self Help Group(SHG)");
		map.put("ug", "User Group");
		mav.addObject("headtype", map); 
		return mav;

	}
	
	@RequestMapping(value = "/getRptshg", method = RequestMethod.POST)  
	public String listRptdata(Locale locale, Model model, HttpServletRequest request,
			@RequestParam("headtype") String headtype, @RequestParam("state") Integer state) {
		List<SelfHelpGroupReportBean> ListShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		if (headtype.equals("shg")) {

			ListShgBean = self.getSelfHelpCreatedExistList(state);
		}
		if (headtype.equals("ug")) {

			ListUserGBean = self.getSelfHelpUserGroupList(state);
		}
		model.addAttribute("rptList", ListShgBean);
		model.addAttribute("rptListSize", ListShgBean.size());
		model.addAttribute("beanListGroup", ListUserGBean);
		model.addAttribute("beanListGroupSize", ListUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtypec", headtype);
		model.addAttribute("stCode", state);
		Map<String, String> map = new HashMap<String, String>();
		map.put("shg", "Self Help Group(SHG)");
		map.put("ug", "User Group");
		model.addAttribute("headtype", map); 
		return "output/SelfHelpGroupState";
	}
	
	@RequestMapping(value = "/getSelfHelpGroupListDist", method = RequestMethod.POST)   
	public String getRptLiveProdEpaDist(Locale locale, Model model, HttpServletRequest request) 
	{
		String stcd=request.getParameter("stateid");
		String headtypeh= request.getParameter("headtypeh");
		String stname= request.getParameter("stname");
		List<SelfHelpGroupReportBean> ListShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		if (headtypeh.equals("shg")) {

			ListShgBean = self.getSelfHelpGroupListDist(Integer.parseInt(stcd));
		}
		if (headtypeh.equals("ug")) {

			ListUserGBean = self.getSelfHelpUserGroupListDist(Integer.parseInt(stcd));
		}
		model.addAttribute("rptList", ListShgBean);
		model.addAttribute("rptListSize", ListShgBean.size());
		model.addAttribute("beanListGroup", ListUserGBean);
		model.addAttribute("beanListGroupSize", ListUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("stname", stname);
		model.addAttribute("stcd", stcd);
		return "output/SelfHelpGroupDistrict";  
	}
	
	@RequestMapping(value = "/getSelfHelpGroupListProject", method = RequestMethod.POST)   
	public String getSelfHelpGroupListProject(Locale locale, Model model, HttpServletRequest request) 
	{
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		List<SelfHelpGroupReportBean> ListShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		if (headtypeh.equals("shg")) {

			ListShgBean = self.getSelfHelpGroupListProject(Integer.parseInt(distid));
		}
		if (headtypeh.equals("ug")) {

			ListUserGBean = self.getSelfHelpUserGroupListProject(Integer.parseInt(distid));
		}
		model.addAttribute("rptList", ListShgBean);
		model.addAttribute("rptListSize", ListShgBean.size());
		model.addAttribute("beanListGroup", ListUserGBean);
		model.addAttribute("beanListGroupSize", ListUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		model.addAttribute("distid", distid);
		return "output/SelfHelpGroupProject";  
	}
	
	@RequestMapping(value = "/getSelfHelpGroupProjectDetailView", method = RequestMethod.POST)   
	public String getSelfHelpGroupProjectDetailView(Locale locale, Model model, HttpServletRequest request) 
	{
		String projectid=request.getParameter("projectid");
		String projname=request.getParameter("projname");
		String headtypeh=request.getParameter("headtypeh");
		String distname=request.getParameter("distname");
		String stname=request.getParameter("stname");
		
		List<SelfHelpGroupReportBean> ListShgCreatedBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListShgExistedBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		if (headtypeh.equals("shg")) {

			ListShgCreatedBean = self.getSHGCreatedProjectDetail(Integer.parseInt(projectid));
			ListShgExistedBean = self.getSHGExistedProjectDetail(Integer.parseInt(projectid));
		}
		if (headtypeh.equals("ug")) {

			ListUserGBean = self.getSHGUserGroupProjectDetail(Integer.parseInt(projectid));
		}
		model.addAttribute("ListShgCreatedBean", ListShgCreatedBean);
		model.addAttribute("ListShgCreatedBeanSize", ListShgCreatedBean.size());
		model.addAttribute("ListShgExistedBean", ListShgExistedBean);
		model.addAttribute("ListShgExistedBeanSize", ListShgExistedBean.size());
		model.addAttribute("ListUserGBean", ListUserGBean);
		model.addAttribute("ListUserGBeanSize", ListUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("projectid",projectid);
		model.addAttribute("projname", projname);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		return "output/SelfHelpGroup1ProjectViewDetails";  
	}
	
	@RequestMapping(value = "/downloadPDFSHG", method = RequestMethod.POST)
	public ModelAndView downloadPDFSHG(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");;
		String headtype=request.getParameter("headtype");
		
		ModelAndView mav = new ModelAndView("output/SelfHelpGroupState");
		List<SelfHelpGroupReportBean> ListShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("SHG/UG");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 PdfWriter writer=PdfWriter.getInstance(document, baos);
			
			document.open(); 
		//	String emb="C://Users//Spectra//eclipse-workspace//Spring_latest//WebContent//WEB-INF//resources//images//tiranga_national_emblem.png";
		//	String g20l="C://Users//Spectra//eclipse-workspace//Spring_latest//WebContent//WEB-INF//resources//images//g20-logo.png";
		//	Image img = Image.getInstance(emb);
		//	Image imgg = Image.getInstance(g20l);
		//	Image img = Image.getInstance(this.getClass().getResource("/WEB-INF/resources/images/g20-logo.png"));
		//	img.setAbsolutePosition(450f, 10f);
		//	img.scaleAbsolute(20f, 20f);
		//	imgg.scaleAbsolute(20f, 20f);
	    //    img.setAbsolutePosition(PageSize.POSTCARD.getWidth() + 240, PageSize.POSTCARD.getHeight() + 340);
	       
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			
			if (headtype.equals("ug")) {
				paragraph3 = new Paragraph("Report OC1- State wise details of User Group ", f3);
			}
			if (headtype.equals("shg")) {
				paragraph3 = new Paragraph("Report OC1- State wise details of Self Help Group (SHGs) ", f3);
			}
			//paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
			if (headtype.equals("shg")) {
				table = new PdfPTable(32);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 ,5 });
				table.setWidthPercentage(100);
				table.setHeaderRows(4);
			}
			if (headtype.equals("ug")) {
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(3);
			}
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			int newno=0,  newsc=0, newst=0, newother=0, newtotal=0, newwomen=0, newthrift=0, newbimayogana=0, newfederated=0;
			int oldno=0,  oldsc=0, oldst=0, oldother=0, oldtotal=0, oldwomen=0, oldthrift=0, oldbimayogana=0, oldfederated=0;
			int totno=0,  totsc=0, totst=0, totother=0, tottotal=0, totwomen=0, totthrift=0, totbimayogana=0, totfederated=0;
			long newrevolving=0,oldrevolving=0,totrevolving=0;
			if (headtype.equals("shg")) {

				ListShgBean = self.getSelfHelpCreatedExistList(Integer.parseInt(state));
				
				CommonFunctions.insertCellHeader(table, "SlNo.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Newly Created SHG", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Existing Created SHG", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total ( Newly Created SHG + Existing Created SHG )", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "17", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "25", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "26", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "27", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "28", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "29", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "30", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "31", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "32", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(ListShgBean.size()!=0)
					for(int i=0;i<ListShgBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTottotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						newno=newno+ListShgBean.get(i).getNewno();
						newrevolving=newrevolving+ListShgBean.get(i).getNewrevolving();
						newsc=newsc+ListShgBean.get(i).getNewsc();
						newst=newst+ListShgBean.get(i).getNewst();
						newother=newother+ListShgBean.get(i).getNewother();
						newtotal=newtotal+ListShgBean.get(i).getNewtotal();
						newwomen=newwomen+ListShgBean.get(i).getNewwomen();
						newthrift=newthrift+ListShgBean.get(i).getNewthrift();
						newbimayogana=newbimayogana+ListShgBean.get(i).getNewbimayogana();
						newfederated=newfederated+ListShgBean.get(i).getNewfederated();
						
						oldno=oldno+ListShgBean.get(i).getOldno();
						oldrevolving=oldrevolving+ListShgBean.get(i).getOldrevolving();
						oldsc= oldsc+ListShgBean.get(i).getOldsc();
						oldst= oldst+ListShgBean.get(i).getOldst();
						oldother=oldother+ListShgBean.get(i).getOldother();
						oldtotal= oldtotal+ListShgBean.get(i).getOldtotal();
						oldwomen= oldwomen+ListShgBean.get(i).getOldwomen();
						oldthrift= oldthrift+ListShgBean.get(i).getOldthrift();
						oldbimayogana= oldbimayogana+ListShgBean.get(i).getOldbimayogana();
						oldfederated= oldfederated+ListShgBean.get(i).getOldfederated();
						
						totno= totno+ListShgBean.get(i).getTotno();
						totrevolving= totrevolving+ListShgBean.get(i).getTotrevolving();
						totsc= totsc+ListShgBean.get(i).getTotsc();
						totst= totst+ListShgBean.get(i).getTotst();
						totother= totother+ListShgBean.get(i).getTotother();
						tottotal= tottotal+ListShgBean.get(i).getTottotal();
						totwomen= totwomen+ListShgBean.get(i).getTotwomen();
						totthrift= totthrift+ListShgBean.get(i).getTotthrift();
						totbimayogana= totbimayogana+ListShgBean.get(i).getTotbimayogana();
						totfederated= totfederated+ListShgBean.get(i).getTotfederated();
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(oldno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(totno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(tottotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(ListShgBean.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 32, 1, bf8);
				
			}
			
			if (headtype.equals("ug")) {

				ListUserGBean = self.getSelfHelpUserGroupList(Integer.parseInt(state));
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "User Group", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 5, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int j=1;
				if(ListUserGBean.size()!=0)
					for(int i=0;i<ListUserGBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(j), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						j=j+1;
						newno=newno+ListUserGBean.get(i).getNewno();
						newsc=newsc+ListUserGBean.get(i).getNewsc();
						newst=newst+ListUserGBean.get(i).getNewst();
						newother=newother+ListUserGBean.get(i).getNewother();
						newtotal=newtotal+ListUserGBean.get(i).getNewtotal();
						newwomen=newwomen+ListUserGBean.get(i).getNewwomen();
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(ListUserGBean.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			}
				
			
	/*		if(list.size()!=0)
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
			CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.0f", newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			if(list.size()==0)
				CommonFunctions.insertCell(table, "Registration Id not match/ Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
			*/
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		//	CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			///addHeader(writer);
			CommonFunctions.addFooter(writer);
			document.close();
			//getHeaderForDilrmpMMP(document);
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC1(Statewise).pdf");
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
		
		return null;
	}
	
	@RequestMapping(value = "/downloadPDFDistSHG", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistSHG(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("stcd");;
		String headtype=request.getParameter("headtypeh");
		String stname=request.getParameter("stname");
		
		ModelAndView mav = new ModelAndView("output/SelfHelpGroupState");
		List<SelfHelpGroupReportBean> ListShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("SHG/UG");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer=PdfWriter.getInstance(document, baos);
			document.open(); 
		
			
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			if (headtype.equals("ug")) {
				paragraph3 = new Paragraph("Report OC1- District wise details of User Group ", f3);
			}
			if (headtype.equals("shg")) {
				paragraph3 = new Paragraph("Report OC1- District wise details of Self Help Group (SHGs) ", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if (headtype.equals("shg")) {
				table = new PdfPTable(32);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 ,5 });
				table.setWidthPercentage(100);
				table.setHeaderRows(5);
			}
			if (headtype.equals("ug")) {
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(4);
			}
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			int newno=0, newrevolving=0, newsc=0, newst=0, newother=0, newtotal=0, newwomen=0, newthrift=0, newbimayogana=0, newfederated=0;
			int oldno=0, oldrevolving=0, oldsc=0, oldst=0, oldother=0, oldtotal=0, oldwomen=0, oldthrift=0, oldbimayogana=0, oldfederated=0;
			int totno=0, totrevolving=0, totsc=0, totst=0, totother=0, tottotal=0, totwomen=0, totthrift=0, totbimayogana=0, totfederated=0;
			
			if (headtype.equals("shg")) {

				ListShgBean = self.getSelfHelpGroupListDist(Integer.parseInt(state));
				CommonFunctions.insertCellHeader(table, "State : " +stname+ "   Head : Self Help Group(SHG) ", Element.ALIGN_LEFT, 32, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "SlNo.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Newly Created SHG", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Existing Created SHG", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total ( Newly Created SHG + Existing Created SHG )", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "17", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "25", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "26", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "27", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "28", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "29", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "30", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "31", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "32", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(ListShgBean.size()!=0)
					for(int i=0;i<ListShgBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTottotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						newno=newno+ListShgBean.get(i).getNewno();
						newrevolving=newrevolving+ListShgBean.get(i).getNewrevolving();
						newsc=newsc+ListShgBean.get(i).getNewsc();
						newst=newst+ListShgBean.get(i).getNewst();
						newother=newother+ListShgBean.get(i).getNewother();
						newtotal=newtotal+ListShgBean.get(i).getNewtotal();
						newwomen=newwomen+ListShgBean.get(i).getNewwomen();
						newthrift=newthrift+ListShgBean.get(i).getNewthrift();
						newbimayogana=newbimayogana+ListShgBean.get(i).getNewbimayogana();
						newfederated=newfederated+ListShgBean.get(i).getNewfederated();
						
						oldno=oldno+ListShgBean.get(i).getOldno();
						oldrevolving=oldrevolving+ListShgBean.get(i).getOldrevolving();
						oldsc= oldsc+ListShgBean.get(i).getOldsc();
						oldst= oldst+ListShgBean.get(i).getOldst();
						oldother=oldother+ListShgBean.get(i).getOldother();
						oldtotal= oldtotal+ListShgBean.get(i).getOldtotal();
						oldwomen= oldwomen+ListShgBean.get(i).getOldwomen();
						oldthrift= oldthrift+ListShgBean.get(i).getOldthrift();
						oldbimayogana= oldbimayogana+ListShgBean.get(i).getOldbimayogana();
						oldfederated= oldfederated+ListShgBean.get(i).getOldfederated();
						
						totno= totno+ListShgBean.get(i).getTotno();
						totrevolving= totrevolving+ListShgBean.get(i).getTotrevolving();
						totsc= totsc+ListShgBean.get(i).getTotsc();
						totst= totst+ListShgBean.get(i).getTotst();
						totother= totother+ListShgBean.get(i).getTotother();
						tottotal= tottotal+ListShgBean.get(i).getTottotal();
						totwomen= totwomen+ListShgBean.get(i).getTotwomen();
						totthrift= totthrift+ListShgBean.get(i).getTotthrift();
						totbimayogana= totbimayogana+ListShgBean.get(i).getTotbimayogana();
						totfederated= totfederated+ListShgBean.get(i).getTotfederated();
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(oldno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(totno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(tottotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(ListShgBean.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 32, 1, bf8);
				
			}
			
			if (headtype.equals("ug")) {

				ListUserGBean = self.getSelfHelpUserGroupListDist(Integer.parseInt(state));
				CommonFunctions.insertCellHeader(table, "State : " +stname+ "   Head : User Group ", Element.ALIGN_LEFT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "User Group", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 5, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int j=1;
				if(ListUserGBean.size()!=0)
					for(int i=0;i<ListUserGBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(j), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						j=j+1;
						newno=newno+ListUserGBean.get(i).getNewno();
						newsc=newsc+ListUserGBean.get(i).getNewsc();
						newst=newst+ListUserGBean.get(i).getNewst();
						newother=newother+ListUserGBean.get(i).getNewother();
						newtotal=newtotal+ListUserGBean.get(i).getNewtotal();
						newwomen=newwomen+ListUserGBean.get(i).getNewwomen();
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(ListUserGBean.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			}
	
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC1(Districtwise).pdf");
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
		
		return null;
	}
	
	@RequestMapping(value = "/downloadPDFProjSHG", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjSHG(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String distname=request.getParameter("distname");
		String headtype=request.getParameter("headtypeh");
		String stname=request.getParameter("stname");
		String distid=request.getParameter("distid");		
		
		ModelAndView mav = new ModelAndView("output/SelfHelpGroupState");
		List<SelfHelpGroupReportBean> ListShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> ListUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("SHG/UG");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer=PdfWriter.getInstance(document, baos);
			document.open(); 
	       
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			
			if (headtype.equals("ug")) {
				paragraph3 = new Paragraph("Report OC1- Project wise details of User Group ", f3);
			}
			if (headtype.equals("shg")) {
				paragraph3 = new Paragraph("Report OC1- Project wise details of Self Help Group (SHGs) ", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if (headtype.equals("shg")) {
				table = new PdfPTable(32);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 ,5 });
				table.setWidthPercentage(100);
				table.setHeaderRows(5);
			}
			if (headtype.equals("ug")) {
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(4);
			}
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			int newno=0, newrevolving=0, newsc=0, newst=0, newother=0, newtotal=0, newwomen=0, newthrift=0, newbimayogana=0, newfederated=0;
			int oldno=0, oldrevolving=0, oldsc=0, oldst=0, oldother=0, oldtotal=0, oldwomen=0, oldthrift=0, oldbimayogana=0, oldfederated=0;
			int totno=0, totrevolving=0, totsc=0, totst=0, totother=0, tottotal=0, totwomen=0, totthrift=0, totbimayogana=0, totfederated=0;
			
			if (headtype.equals("shg")) {

				ListShgBean = self.getSelfHelpGroupListProject(Integer.parseInt(distid));
				CommonFunctions.insertCellHeader(table, "State : " +stname+ "  District : " +distname+ "  Head : Self Help Group(SHG) ", Element.ALIGN_LEFT, 32, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "SlNo.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Newly Created SHG", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Existing Created SHG", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total ( Newly Created SHG + Existing Created SHG )", Element.ALIGN_CENTER, 10, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 8, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of SHG members having PM", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Fedrated SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "17", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "25", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "26", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "27", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "28", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "29", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "30", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "31", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "32", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(ListShgBean.size()!=0)
					for(int i=0;i<ListShgBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getNewfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getOldfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotrevolving().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTottotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotthrift().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotbimayogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListShgBean.get(i).getTotfederated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						newno=newno+ListShgBean.get(i).getNewno();
						newrevolving=newrevolving+ListShgBean.get(i).getNewrevolving();
						newsc=newsc+ListShgBean.get(i).getNewsc();
						newst=newst+ListShgBean.get(i).getNewst();
						newother=newother+ListShgBean.get(i).getNewother();
						newtotal=newtotal+ListShgBean.get(i).getNewtotal();
						newwomen=newwomen+ListShgBean.get(i).getNewwomen();
						newthrift=newthrift+ListShgBean.get(i).getNewthrift();
						newbimayogana=newbimayogana+ListShgBean.get(i).getNewbimayogana();
						newfederated=newfederated+ListShgBean.get(i).getNewfederated();
						
						oldno=oldno+ListShgBean.get(i).getOldno();
						oldrevolving=oldrevolving+ListShgBean.get(i).getOldrevolving();
						oldsc= oldsc+ListShgBean.get(i).getOldsc();
						oldst= oldst+ListShgBean.get(i).getOldst();
						oldother=oldother+ListShgBean.get(i).getOldother();
						oldtotal= oldtotal+ListShgBean.get(i).getOldtotal();
						oldwomen= oldwomen+ListShgBean.get(i).getOldwomen();
						oldthrift= oldthrift+ListShgBean.get(i).getOldthrift();
						oldbimayogana= oldbimayogana+ListShgBean.get(i).getOldbimayogana();
						oldfederated= oldfederated+ListShgBean.get(i).getOldfederated();
						
						totno= totno+ListShgBean.get(i).getTotno();
						totrevolving= totrevolving+ListShgBean.get(i).getTotrevolving();
						totsc= totsc+ListShgBean.get(i).getTotsc();
						totst= totst+ListShgBean.get(i).getTotst();
						totother= totother+ListShgBean.get(i).getTotother();
						tottotal= tottotal+ListShgBean.get(i).getTottotal();
						totwomen= totwomen+ListShgBean.get(i).getTotwomen();
						totthrift= totthrift+ListShgBean.get(i).getTotthrift();
						totbimayogana= totbimayogana+ListShgBean.get(i).getTotbimayogana();
						totfederated= totfederated+ListShgBean.get(i).getTotfederated();
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(oldno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(totno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totrevolving), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(tottotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totthrift), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totbimayogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totfederated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(ListShgBean.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 32, 1, bf8);
				
			}
			
			if (headtype.equals("ug")) {

				ListUserGBean = self.getSelfHelpUserGroupListProject(Integer.parseInt(distid));
				CommonFunctions.insertCellHeader(table, "State : " +stname+ "  District : " +distname+ "   Head : User Group ", Element.ALIGN_LEFT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "User Group", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 5, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int j=1;
				if(ListUserGBean.size()!=0)
					for(int i=0;i<ListUserGBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(j), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewno().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewsc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewst().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewother().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewtotal().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ListUserGBean.get(i).getNewwomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						j=j+1;
						newno=newno+ListUserGBean.get(i).getNewno();
						newsc=newsc+ListUserGBean.get(i).getNewsc();
						newst=newst+ListUserGBean.get(i).getNewst();
						newother=newother+ListUserGBean.get(i).getNewother();
						newtotal=newtotal+ListUserGBean.get(i).getNewtotal();
						newwomen=newwomen+ListUserGBean.get(i).getNewwomen();
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newno), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newsc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newother), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newwomen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(ListUserGBean.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			}
				
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC1(Projectwise).pdf");
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
		
		return null;
	}
	
	@RequestMapping(value = "/downloadExcelStSHG", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStSHG(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String state=request.getParameter("state");
		String headtype=request.getParameter("headtype");
		
//		ModelAndView mav = new ModelAndView("output/SelfHelpGroupState");
		List<SelfHelpGroupReportBean> listShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		if (headtype.equals("ug")) {
			listUserGBean = self.getSelfHelpUserGroupList(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- State wise details of User Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- State wise details of User Group";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listUserGBean.size()+8,listUserGBean.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("User Group");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead.createCell(7);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			cell = rowhead2.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(2);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(3);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(4);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			
	        
	        int sno = 8;
	        int totNo =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        for(SelfHelpGroupReportBean ugBean: listUserGBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-7);  
	        	row.createCell(1).setCellValue(ugBean.getSt_name());  
	        	row.createCell(2).setCellValue(ugBean.getNewno());  
	        	row.createCell(3).setCellValue(ugBean.getNewsc());  
	        	row.createCell(4).setCellValue(ugBean.getNewst()); 
	        	row.createCell(5).setCellValue(ugBean.getNewother());  
	        	row.createCell(6).setCellValue(ugBean.getNewtotal());  
	        	row.createCell(7).setCellValue(ugBean.getNewwomen());
	        	sno++;
	        	
	        	totNo = totNo + ugBean.getNewno();
	        	totSc = totSc + ugBean.getNewsc();
	        	totSt = totSt + ugBean.getNewst();
	        	totOther = totOther + ugBean.getNewother();
	        	totTotal = totTotal + ugBean.getNewtotal();
	        	totWomen = totWomen + ugBean.getNewwomen();
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listUserGBean.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listUserGBean.size(), 7);
	        String fileName = "attachment; filename=Report OC1- State wise UG Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		if (headtype.equals("shg")) {
			listShgBean = self.getSelfHelpCreatedExistList(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- State wise details of Self Help Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- State wise details of Self Help Group (SHGs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 31, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,12,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,22,31); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,22,22); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,23,23); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,24,31); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listShgBean.size()+9,listShgBean.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Newly created SHG");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead.createCell(7);
			cell.setCellStyle(style);
			cell = rowhead.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellStyle(style);
			cell = rowhead.createCell(11);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Existing created SHG");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(13);
			cell.setCellStyle(style);
			cell = rowhead.createCell(14);
			cell.setCellStyle(style);
			cell = rowhead.createCell(15);
			cell.setCellStyle(style);
			cell = rowhead.createCell(16);
			cell.setCellStyle(style);
			cell = rowhead.createCell(17);
			cell.setCellStyle(style);
			cell = rowhead.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead.createCell(20);
			cell.setCellStyle(style);
			cell = rowhead.createCell(21);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(22);
			cell.setCellValue("Total ( Newly created SHG + Existing created SHG )");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(23);
			cell.setCellStyle(style);
			cell = rowhead.createCell(24);
			cell.setCellStyle(style);
			cell = rowhead.createCell(25);
			cell.setCellStyle(style);
			cell = rowhead.createCell(26);
			cell.setCellStyle(style);
			cell = rowhead.createCell(27);
			cell.setCellStyle(style);
			cell = rowhead.createCell(28);
			cell.setCellStyle(style);
			cell = rowhead.createCell(29);
			cell.setCellStyle(style);
			cell = rowhead.createCell(30);
			cell.setCellStyle(style);
			cell = rowhead.createCell(31);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(4);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(13);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(14);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(16);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(17);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(20);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(21);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(22);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(23);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(24);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(25);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(26);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(27);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(28);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(29);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(30);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(31);
			cell.setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(7);
			cell = rowhead2.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(2);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(4);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(5);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(6);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(7);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(8);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(9);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(10);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(11);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(13);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(14);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(15);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(16);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(17);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(18);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(19);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(20);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(21);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(22);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(23);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(24);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(25);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(26);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(27);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(28);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(29);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(30);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(31);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(8); 
			for(int i = 0; i<32; i++) {	
			Cell cell1 = rowhead3.createCell(i);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);
			}
			
	        int sno = 9;
	        int totNewNo =0;
	        double totNewrevolving = 0.0;
	        int totNewSc = 0;
	        int totNewSt = 0;
	        int totNewOther = 0;
	        int totNewTotal = 0;
	        int totNewWomen = 0;
	        int totNewthrift = 0;
	        int totNewbimayogana = 0;
	        int totNewfederated = 0;
	        
	        int totOldNo =0;
	        double totOldrevolving = 0.0;
	        int totOldSc = 0;
	        int totOldSt = 0;
	        int totOldOther = 0;
	        int totOldTotal = 0;
	        int totOldWomen = 0;
	        int totOldthrift = 0;
	        int totOldbimayogana = 0;
	        int totOldfederated = 0;
	        
	        int totNo =0;
	        double totrevolving = 0.0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        int totthrift = 0;
	        int totbimayogana = 0;
	        int totfederated = 0;
	        for(SelfHelpGroupReportBean ugBean: listShgBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-8);  
	        	row.createCell(1).setCellValue(ugBean.getSt_name());  
	        	row.createCell(2).setCellValue(ugBean.getNewno()); 
	        	row.createCell(3).setCellValue(ugBean.getNewrevolving());  
	        	row.createCell(4).setCellValue(ugBean.getNewsc());  
	        	row.createCell(5).setCellValue(ugBean.getNewst()); 
	        	row.createCell(6).setCellValue(ugBean.getNewother());  
	        	row.createCell(7).setCellValue(ugBean.getNewtotal());  
	        	row.createCell(8).setCellValue(ugBean.getNewwomen());
	        	row.createCell(9).setCellValue(ugBean.getNewthrift());  
	        	row.createCell(10).setCellValue(ugBean.getNewbimayogana());  
	        	row.createCell(11).setCellValue(ugBean.getNewfederated());
	        	
	        	row.createCell(12).setCellValue(ugBean.getOldno()); 
	        	row.createCell(13).setCellValue(ugBean.getOldrevolving());  
	        	row.createCell(14).setCellValue(ugBean.getOldsc());  
	        	row.createCell(15).setCellValue(ugBean.getOldst()); 
	        	row.createCell(16).setCellValue(ugBean.getOldother());  
	        	row.createCell(17).setCellValue(ugBean.getOldtotal());  
	        	row.createCell(18).setCellValue(ugBean.getOldwomen());
	        	row.createCell(19).setCellValue(ugBean.getOldthrift());  
	        	row.createCell(20).setCellValue(ugBean.getOldbimayogana());  
	        	row.createCell(21).setCellValue(ugBean.getOldfederated());
	        	
	        	row.createCell(22).setCellValue(ugBean.getTotno()); 
	        	row.createCell(23).setCellValue(ugBean.getTotrevolving());  
	        	row.createCell(24).setCellValue(ugBean.getTotsc());  
	        	row.createCell(25).setCellValue(ugBean.getTotst()); 
	        	row.createCell(26).setCellValue(ugBean.getTotother());  
	        	row.createCell(27).setCellValue(ugBean.getTottotal());  
	        	row.createCell(28).setCellValue(ugBean.getTotwomen());
	        	row.createCell(29).setCellValue(ugBean.getTotthrift());  
	        	row.createCell(30).setCellValue(ugBean.getTotbimayogana());  
	        	row.createCell(31).setCellValue(ugBean.getTotfederated());
	        	sno++;
	        	
	        	totNewNo = totNewNo + ugBean.getNewno();
	        	totNewrevolving = totNewrevolving + ugBean.getNewrevolving();
	        	totNewSc = totNewSc + ugBean.getNewsc();
	        	totNewSt = totNewSt + ugBean.getNewst();
	        	totNewOther = totNewOther + ugBean.getNewother();
	        	totNewTotal = totNewTotal + ugBean.getNewtotal();
	        	totNewWomen = totNewWomen + ugBean.getNewwomen();
	        	totNewthrift = totNewthrift + ugBean.getNewthrift();
		        totNewbimayogana = totNewbimayogana + ugBean.getNewbimayogana();
		        totNewfederated = totNewfederated + ugBean.getNewfederated();
		        
		        totOldNo =totOldNo +ugBean.getOldno();
		        totOldrevolving = totOldrevolving + ugBean.getOldrevolving();
		        totOldSc = totOldSc + ugBean.getOldsc();
		        totOldSt = totOldSt + ugBean.getOldst();
		        totOldOther = totOldOther + ugBean.getOldother();
		        totOldTotal = totOldTotal + ugBean.getOldtotal();
		        totOldWomen = totOldWomen + ugBean.getOldwomen();
		        totOldthrift = totOldthrift + ugBean.getOldthrift();
		        totOldbimayogana = totOldbimayogana + ugBean.getOldbimayogana();
		        totOldfederated = totOldfederated + ugBean.getOldfederated();
		        
		        totNo =totNo + ugBean.getTotno();
		        totrevolving = totrevolving + ugBean.getTotrevolving();
		        totSc = totSc + ugBean.getTotsc();
		        totSt = totSt + ugBean.getTotst();
		        totOther = totOther + ugBean.getTotother();
		        totTotal = totTotal + ugBean.getTottotal();
		        totWomen = totWomen + ugBean.getTotwomen();
		        totthrift = totthrift + ugBean.getTotthrift();
		        totbimayogana = totbimayogana + ugBean.getTotbimayogana();
		        totfederated = totfederated + ugBean.getTotfederated();
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listShgBean.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(2);
	        cell.setCellValue(totNewNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNewrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totNewSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totNewSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNewOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totNewTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNewWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totNewthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totNewbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totNewfederated);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totOldNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totOldrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totOldSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totOldSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOldOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totOldTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOldWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOldthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totOldbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totOldfederated);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(22);
	        cell.setCellValue(totNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	        cell.setCellValue(totrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(24);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(25);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(26);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(27);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(28);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(29);
	        cell.setCellValue(totthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(30);
	        cell.setCellValue(totbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(31);
	        cell.setCellValue(totfederated);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listShgBean.size(), 31);
	        String fileName = "attachment; filename=Report OC1- State wise SHG Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		model.addAttribute("rptList", listShgBean);
		model.addAttribute("rptListSize", listShgBean.size());
		model.addAttribute("beanListGroup", listUserGBean);
		model.addAttribute("beanListGroupSize", listUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtypec", headtype);
		model.addAttribute("stCode", state);
		Map<String, String> map = new HashMap<String, String>();
		map.put("shg", "Self Help Group(SHG)");
		map.put("ug", "User Group");
		model.addAttribute("headtype", map); 
		
		return "output/SelfHelpGroupState";
		
	}
	
	@RequestMapping(value = "/downloadExcelDistSHG", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistSHG(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String state=request.getParameter("stcd");;
		String headtype=request.getParameter("headtypeh");
		String stname=request.getParameter("stname");
		
//		ModelAndView mav = new ModelAndView("output/SelfHelpGroupState");
		List<SelfHelpGroupReportBean> listShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		if (headtype.equals("ug")) {
			listUserGBean = self.getSelfHelpUserGroupListDist(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- District wise details of User Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- District wise details of User Group";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,7);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,8,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,2,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listUserGBean.size()+9,listUserGBean.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   Head : User Group");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<8;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("User Group");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead.createCell(7);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			cell = rowhead2.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(2);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(3);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(4);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			
	        
	        int sno = 9;
	        int totNo =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        for(SelfHelpGroupReportBean ugBean: listUserGBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-8);  
	        	row.createCell(1).setCellValue(ugBean.getDist_name());  
	        	row.createCell(2).setCellValue(ugBean.getNewno());  
	        	row.createCell(3).setCellValue(ugBean.getNewsc());  
	        	row.createCell(4).setCellValue(ugBean.getNewst()); 
	        	row.createCell(5).setCellValue(ugBean.getNewother());  
	        	row.createCell(6).setCellValue(ugBean.getNewtotal());  
	        	row.createCell(7).setCellValue(ugBean.getNewwomen());
	        	sno++;
	        	
	        	totNo = totNo + ugBean.getNewno();
	        	totSc = totSc + ugBean.getNewsc();
	        	totSt = totSt + ugBean.getNewst();
	        	totOther = totOther + ugBean.getNewother();
	        	totTotal = totTotal + ugBean.getNewtotal();
	        	totWomen = totWomen + ugBean.getNewwomen();
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listUserGBean.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listUserGBean.size(), 7);
	        String fileName = "attachment; filename=Report OC1- District wise UG Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		if (headtype.equals("shg")) {
			listShgBean = self.getSelfHelpGroupListDist(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- District wise details of Self Help Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- District wise details of Self Help Group (SHGs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 31, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,31);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,8,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,2,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,22,31); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,4,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,13,13); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,14,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,22,22); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,23,23); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,24,31); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listShgBean.size()+10,listShgBean.size()+10,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   Head : Self Help Group(SHG)");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<32;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Newly created SHG");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =3; i<12;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Existing created SHG");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =13; i<22;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(22);
			cell.setCellValue("Total ( Newly created SHG + Existing created SHG )");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =23; i<31;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(7);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(4);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =5; i<12;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(13);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(14);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =15; i<22;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(22);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(23);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(24);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =25; i<32;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			
			Row rowhead2 = sheet.createRow(8);
			cell = rowhead2.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(2);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(4);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(5);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(6);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(7);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(8);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(9);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(10);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(11);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(13);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(14);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(15);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(16);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(17);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(18);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(19);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(20);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(21);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(22);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(23);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(24);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(25);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(26);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(27);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(28);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(29);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(30);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(31);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(9); 
			for(int i = 0; i<32; i++) {	
			Cell cell1 = rowhead3.createCell(i);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);
			}
	        
	        int sno = 10;
	        int totNewNo =0;
	        double totNewrevolving = 0.0;
	        int totNewSc = 0;
	        int totNewSt = 0;
	        int totNewOther = 0;
	        int totNewTotal = 0;
	        int totNewWomen = 0;
	        int totNewthrift = 0;
	        int totNewbimayogana = 0;
	        int totNewfederated = 0;
	        
	        int totOldNo =0;
	        double totOldrevolving = 0.0;
	        int totOldSc = 0;
	        int totOldSt = 0;
	        int totOldOther = 0;
	        int totOldTotal = 0;
	        int totOldWomen = 0;
	        int totOldthrift = 0;
	        int totOldbimayogana = 0;
	        int totOldfederated = 0;
	        
	        int totNo =0;
	        double totrevolving = 0.0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        int totthrift = 0;
	        int totbimayogana = 0;
	        int totfederated = 0;
	        for(SelfHelpGroupReportBean ugBean: listShgBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-9);  
	        	row.createCell(1).setCellValue(ugBean.getDist_name());  
	        	row.createCell(2).setCellValue(ugBean.getNewno()); 
	        	row.createCell(3).setCellValue(ugBean.getNewrevolving());  
	        	row.createCell(4).setCellValue(ugBean.getNewsc());  
	        	row.createCell(5).setCellValue(ugBean.getNewst()); 
	        	row.createCell(6).setCellValue(ugBean.getNewother());  
	        	row.createCell(7).setCellValue(ugBean.getNewtotal());  
	        	row.createCell(8).setCellValue(ugBean.getNewwomen());
	        	row.createCell(9).setCellValue(ugBean.getNewthrift());  
	        	row.createCell(10).setCellValue(ugBean.getNewbimayogana());  
	        	row.createCell(11).setCellValue(ugBean.getNewfederated());
	        	
	        	row.createCell(12).setCellValue(ugBean.getOldno()); 
	        	row.createCell(13).setCellValue(ugBean.getOldrevolving());  
	        	row.createCell(14).setCellValue(ugBean.getOldsc());  
	        	row.createCell(15).setCellValue(ugBean.getOldst()); 
	        	row.createCell(16).setCellValue(ugBean.getOldother());  
	        	row.createCell(17).setCellValue(ugBean.getOldtotal());  
	        	row.createCell(18).setCellValue(ugBean.getOldwomen());
	        	row.createCell(19).setCellValue(ugBean.getOldthrift());  
	        	row.createCell(20).setCellValue(ugBean.getOldbimayogana());  
	        	row.createCell(21).setCellValue(ugBean.getOldfederated());
	        	
	        	row.createCell(22).setCellValue(ugBean.getTotno()); 
	        	row.createCell(23).setCellValue(ugBean.getTotrevolving());  
	        	row.createCell(24).setCellValue(ugBean.getTotsc());  
	        	row.createCell(25).setCellValue(ugBean.getTotst()); 
	        	row.createCell(26).setCellValue(ugBean.getTotother());  
	        	row.createCell(27).setCellValue(ugBean.getTottotal());  
	        	row.createCell(28).setCellValue(ugBean.getTotwomen());
	        	row.createCell(29).setCellValue(ugBean.getTotthrift());  
	        	row.createCell(30).setCellValue(ugBean.getTotbimayogana());  
	        	row.createCell(31).setCellValue(ugBean.getTotfederated());
	        	sno++;
	        	
	        	totNewNo = totNewNo + ugBean.getNewno();
	        	totNewrevolving = totNewrevolving + ugBean.getNewrevolving();
	        	totNewSc = totNewSc + ugBean.getNewsc();
	        	totNewSt = totNewSt + ugBean.getNewst();
	        	totNewOther = totNewOther + ugBean.getNewother();
	        	totNewTotal = totNewTotal + ugBean.getNewtotal();
	        	totNewWomen = totNewWomen + ugBean.getNewwomen();
	        	totNewthrift = totNewthrift + ugBean.getNewthrift();
		        totNewbimayogana = totNewbimayogana + ugBean.getNewbimayogana();
		        totNewfederated = totNewfederated + ugBean.getNewfederated();
		        
		        totOldNo =totOldNo +ugBean.getOldno();
		        totOldrevolving = totOldrevolving + ugBean.getOldrevolving();
		        totOldSc = totOldSc + ugBean.getOldsc();
		        totOldSt = totOldSt + ugBean.getOldst();
		        totOldOther = totOldOther + ugBean.getOldother();
		        totOldTotal = totOldTotal + ugBean.getOldtotal();
		        totOldWomen = totOldWomen + ugBean.getOldwomen();
		        totOldthrift = totOldthrift + ugBean.getOldthrift();
		        totOldbimayogana = totOldbimayogana + ugBean.getOldbimayogana();
		        totOldfederated = totOldfederated + ugBean.getOldfederated();
		        
		        totNo =totNo + ugBean.getTotno();
		        totrevolving = totrevolving + ugBean.getTotrevolving();
		        totSc = totSc + ugBean.getTotsc();
		        totSt = totSt + ugBean.getTotst();
		        totOther = totOther + ugBean.getTotother();
		        totTotal = totTotal + ugBean.getTottotal();
		        totWomen = totWomen + ugBean.getTotwomen();
		        totthrift = totthrift + ugBean.getTotthrift();
		        totbimayogana = totbimayogana + ugBean.getTotbimayogana();
		        totfederated = totfederated + ugBean.getTotfederated();
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listShgBean.size()+10);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(2);
	        cell.setCellValue(totNewNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNewrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totNewSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totNewSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNewOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totNewTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNewWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totNewthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totNewbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totNewfederated);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totOldNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totOldrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totOldSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totOldSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOldOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totOldTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOldWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOldthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totOldbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totOldfederated);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(22);
	        cell.setCellValue(totNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	        cell.setCellValue(totrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(24);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(25);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(26);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(27);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(28);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(29);
	        cell.setCellValue(totthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(30);
	        cell.setCellValue(totbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(31);
	        cell.setCellValue(totfederated);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listShgBean.size(), 31);
	        String fileName = "attachment; filename=Report OC1- District wise SHG Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		model.addAttribute("rptList", listShgBean);
		model.addAttribute("rptListSize", listShgBean.size());
		model.addAttribute("beanListGroup", listUserGBean);
		model.addAttribute("beanListGroupSize", listUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtypec", headtype);
		model.addAttribute("stCode", state);
		Map<String, String> map = new HashMap<String, String>();
		map.put("shg", "Self Help Group(SHG)");
		map.put("ug", "User Group");
		model.addAttribute("headtype", map); 
		
		return "output/SelfHelpGroupDistrict";
		
	}

	@RequestMapping(value = "/downloadExcelProjSHG", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjSHG(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String distname=request.getParameter("distname");
		String headtype=request.getParameter("headtypeh");
		String stname=request.getParameter("stname");
		String distid=request.getParameter("distid");
		
//		ModelAndView mav = new ModelAndView("output/SelfHelpGroupState");
		List<SelfHelpGroupReportBean> listShgBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		if (headtype.equals("ug")) {
			listUserGBean = self.getSelfHelpUserGroupListProject(Integer.parseInt(distid));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- Project wise details of User Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- Project wise details of User Group";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,7);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,8,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,2,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listUserGBean.size()+9,listUserGBean.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : User Group");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<8;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("User Group");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead.createCell(7);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			cell = rowhead2.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(2);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(3);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(4);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			
	        
	        int sno = 9;
	        int totNo =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        for(SelfHelpGroupReportBean ugBean: listUserGBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-8);  
	        	row.createCell(1).setCellValue(ugBean.getProj_name());  
	        	row.createCell(2).setCellValue(ugBean.getNewno());  
	        	row.createCell(3).setCellValue(ugBean.getNewsc());  
	        	row.createCell(4).setCellValue(ugBean.getNewst()); 
	        	row.createCell(5).setCellValue(ugBean.getNewother());  
	        	row.createCell(6).setCellValue(ugBean.getNewtotal());  
	        	row.createCell(7).setCellValue(ugBean.getNewwomen());
	        	sno++;
	        	
	        	totNo = totNo + ugBean.getNewno();
	        	totSc = totSc + ugBean.getNewsc();
	        	totSt = totSt + ugBean.getNewst();
	        	totOther = totOther + ugBean.getNewother();
	        	totTotal = totTotal + ugBean.getNewtotal();
	        	totWomen = totWomen + ugBean.getNewwomen();
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listUserGBean.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listUserGBean.size(), 7);
	        String fileName = "attachment; filename=Report OC1- Project wise UG.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		if (headtype.equals("shg")) {
			listShgBean = self.getSelfHelpGroupListProject(Integer.parseInt(distid));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- Project wise details of Self Help Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- Project wise details of Self Help Group (SHGs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 31, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,31);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,8,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,2,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,22,31); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,4,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,13,13); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,14,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,22,22); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,23,23); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,24,31); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listShgBean.size()+10,listShgBean.size()+10,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : Self Help Group(SHGs)");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<32;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Newly created SHG");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =3; i<12;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Existing created SHG");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =13; i<22;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(22);
			cell.setCellValue("Total ( Newly created SHG + Existing created SHG )");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =23; i<31;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(7);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(4);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =5; i<12;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(13);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(14);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =15; i<22;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(22);
			cell.setCellValue("No."); 
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(23);
			cell.setCellValue("Total Revolving Fund (in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead1.createCell(24);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =25; i<32;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			
			Row rowhead2 = sheet.createRow(8);
			cell = rowhead2.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(2);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(3);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(4);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(5);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(6);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(7);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(8);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(9);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(10);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(11);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(13);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(14);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(15);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(16);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(17);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(18);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(19);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(20);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(21);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(22);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(23);
			cell.setCellStyle(style);
			cell = rowhead2.createCell(24);
			cell.setCellValue("SC"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(25);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(26);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(27);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(28);
			cell.setCellValue("Women"); 
			cell.setCellStyle(style);
			cell = rowhead2.createCell(29);
			cell.setCellValue("Thrift & Credit");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(30);
			cell.setCellValue("No. of SHG members having PM");
			cell.setCellStyle(style);
			cell = rowhead2.createCell(31);
			cell.setCellValue("No. of Fedrated SHG"); 
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(9); 
			for(int i = 0; i<32; i++) {	
			Cell cell1 = rowhead3.createCell(i);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);
			}
	        
	        int sno = 10;
	        int totNewNo =0;
	        double totNewrevolving = 0.0;
	        int totNewSc = 0;
	        int totNewSt = 0;
	        int totNewOther = 0;
	        int totNewTotal = 0;
	        int totNewWomen = 0;
	        int totNewthrift = 0;
	        int totNewbimayogana = 0;
	        int totNewfederated = 0;
	        
	        int totOldNo =0;
	        double totOldrevolving = 0.0;
	        int totOldSc = 0;
	        int totOldSt = 0;
	        int totOldOther = 0;
	        int totOldTotal = 0;
	        int totOldWomen = 0;
	        int totOldthrift = 0;
	        int totOldbimayogana = 0;
	        int totOldfederated = 0;
	        
	        int totNo =0;
	        double totrevolving = 0.0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        int totthrift = 0;
	        int totbimayogana = 0;
	        int totfederated = 0;
	        for(SelfHelpGroupReportBean ugBean: listShgBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-9);  
	        	row.createCell(1).setCellValue(ugBean.getProj_name());  
	        	row.createCell(2).setCellValue(ugBean.getNewno()); 
	        	row.createCell(3).setCellValue(ugBean.getNewrevolving());  
	        	row.createCell(4).setCellValue(ugBean.getNewsc());  
	        	row.createCell(5).setCellValue(ugBean.getNewst()); 
	        	row.createCell(6).setCellValue(ugBean.getNewother());  
	        	row.createCell(7).setCellValue(ugBean.getNewtotal());  
	        	row.createCell(8).setCellValue(ugBean.getNewwomen());
	        	row.createCell(9).setCellValue(ugBean.getNewthrift());  
	        	row.createCell(10).setCellValue(ugBean.getNewbimayogana());  
	        	row.createCell(11).setCellValue(ugBean.getNewfederated());
	        	
	        	row.createCell(12).setCellValue(ugBean.getOldno()); 
	        	row.createCell(13).setCellValue(ugBean.getOldrevolving());  
	        	row.createCell(14).setCellValue(ugBean.getOldsc());  
	        	row.createCell(15).setCellValue(ugBean.getOldst()); 
	        	row.createCell(16).setCellValue(ugBean.getOldother());  
	        	row.createCell(17).setCellValue(ugBean.getOldtotal());  
	        	row.createCell(18).setCellValue(ugBean.getOldwomen());
	        	row.createCell(19).setCellValue(ugBean.getOldthrift());  
	        	row.createCell(20).setCellValue(ugBean.getOldbimayogana());  
	        	row.createCell(21).setCellValue(ugBean.getOldfederated());
	        	
	        	row.createCell(22).setCellValue(ugBean.getTotno()); 
	        	row.createCell(23).setCellValue(ugBean.getTotrevolving());  
	        	row.createCell(24).setCellValue(ugBean.getTotsc());  
	        	row.createCell(25).setCellValue(ugBean.getTotst()); 
	        	row.createCell(26).setCellValue(ugBean.getTotother());  
	        	row.createCell(27).setCellValue(ugBean.getTottotal());  
	        	row.createCell(28).setCellValue(ugBean.getTotwomen());
	        	row.createCell(29).setCellValue(ugBean.getTotthrift());  
	        	row.createCell(30).setCellValue(ugBean.getTotbimayogana());  
	        	row.createCell(31).setCellValue(ugBean.getTotfederated());
	        	sno++;
	        	
	        	totNewNo = totNewNo + ugBean.getNewno();
	        	totNewrevolving = totNewrevolving + ugBean.getNewrevolving();
	        	totNewSc = totNewSc + ugBean.getNewsc();
	        	totNewSt = totNewSt + ugBean.getNewst();
	        	totNewOther = totNewOther + ugBean.getNewother();
	        	totNewTotal = totNewTotal + ugBean.getNewtotal();
	        	totNewWomen = totNewWomen + ugBean.getNewwomen();
	        	totNewthrift = totNewthrift + ugBean.getNewthrift();
		        totNewbimayogana = totNewbimayogana + ugBean.getNewbimayogana();
		        totNewfederated = totNewfederated + ugBean.getNewfederated();
		        
		        totOldNo =totOldNo +ugBean.getOldno();
		        totOldrevolving = totOldrevolving + ugBean.getOldrevolving();
		        totOldSc = totOldSc + ugBean.getOldsc();
		        totOldSt = totOldSt + ugBean.getOldst();
		        totOldOther = totOldOther + ugBean.getOldother();
		        totOldTotal = totOldTotal + ugBean.getOldtotal();
		        totOldWomen = totOldWomen + ugBean.getOldwomen();
		        totOldthrift = totOldthrift + ugBean.getOldthrift();
		        totOldbimayogana = totOldbimayogana + ugBean.getOldbimayogana();
		        totOldfederated = totOldfederated + ugBean.getOldfederated();
		        
		        totNo =totNo + ugBean.getTotno();
		        totrevolving = totrevolving + ugBean.getTotrevolving();
		        totSc = totSc + ugBean.getTotsc();
		        totSt = totSt + ugBean.getTotst();
		        totOther = totOther + ugBean.getTotother();
		        totTotal = totTotal + ugBean.getTottotal();
		        totWomen = totWomen + ugBean.getTotwomen();
		        totthrift = totthrift + ugBean.getTotthrift();
		        totbimayogana = totbimayogana + ugBean.getTotbimayogana();
		        totfederated = totfederated + ugBean.getTotfederated();
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listShgBean.size()+10);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(2);
	        cell.setCellValue(totNewNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNewrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totNewSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totNewSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNewOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totNewTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNewWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totNewthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totNewbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totNewfederated);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totOldNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totOldrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totOldSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totOldSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOldOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totOldTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOldWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOldthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totOldbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totOldfederated);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(22);
	        cell.setCellValue(totNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	        cell.setCellValue(totrevolving);
	        cell.setCellStyle(style1);
	        cell = row.createCell(24);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(25);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(26);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(27);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(28);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(29);
	        cell.setCellValue(totthrift);
	        cell.setCellStyle(style1);
	        cell = row.createCell(30);
	        cell.setCellValue(totbimayogana);
	        cell.setCellStyle(style1);
	        cell = row.createCell(31);
	        cell.setCellValue(totfederated);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listShgBean.size(), 31);
	        String fileName = "attachment; filename=Report OC1- Project wise SHG.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		model.addAttribute("rptList", listShgBean);
		model.addAttribute("rptListSize", listShgBean.size());
		model.addAttribute("beanListGroup", listUserGBean);
		model.addAttribute("beanListGroupSize", listUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtype);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		model.addAttribute("distid", distid); 
		
		return "output/SelfHelpGroupProject";
		
	}
	
	@RequestMapping(value = "/downloadExcelProjDetailSHG", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjDetailSHG(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String projectid=request.getParameter("projectid");
		String projname=request.getParameter("projname");
		String headtype=request.getParameter("headtypeh");
		String shgType=request.getParameter("shgHead");
		String distname=request.getParameter("distname");
		String stname=request.getParameter("stname");
		
		List<SelfHelpGroupReportBean> listShgCreatedBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listShgExistedBean = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listUserGBean = new ArrayList<SelfHelpGroupReportBean>();
		
		if (headtype.equals("ug")) {
			listUserGBean = self.getSHGUserGroupProjectDetail(Integer.parseInt(projectid));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC1- Project details of User Group");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC1- Project details of User Group";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,6);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(listUserGBean.size()+9,listUserGBean.size()+9,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Project : "+projname+"   Head : User Group");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<7;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("No. of User Group");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Name of User Groups");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			cell = rowhead1.createCell(0);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(1);
			cell.setCellStyle(style);
			cell = rowhead1.createCell(2);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Other");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Women");
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8); 
			for(int i = 0; i<7; i++) {	
			Cell cell1 = rowhead2.createCell(i);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  =9;
	        int noOfUserGrp = 0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totWomen = 0;
	        for(SelfHelpGroupReportBean ugBean: listUserGBean) {
	        	Row row = sheet.createRow(rowno);
	        	if(noOfUserGrp != ugBean.getShg_id()) {
	        		noOfUserGrp = ugBean.getShg_id();
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(ugBean.getTotalno());
	        		sno++;
	        	}else {
	        		row.createCell(0).setCellValue(""); 
	        		row.createCell(1).setCellValue(""); 
	        	}
	        	 
	        	row.createCell(2).setCellValue(ugBean.getName());  
	        	row.createCell(3).setCellValue(ugBean.getSc());  
	        	row.createCell(4).setCellValue(ugBean.getSt()); 
	        	row.createCell(5).setCellValue(ugBean.getOther());  
	        	row.createCell(6).setCellValue(ugBean.getWomen());  
	        	
	        	totSc = totSc + ugBean.getSc();
	        	totSt = totSt + ugBean.getSt();
	        	totOther = totOther + ugBean.getOther();
	        	totWomen = totWomen + ugBean.getWomen();
	        	rowno++;
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listUserGBean.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listUserGBean.size(), 6);
	        String fileName = "attachment; filename=Report OC1- Project details of User Group.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		if (headtype.equals("shg")) {
			listShgCreatedBean = self.getSHGCreatedProjectDetail(Integer.parseInt(projectid));
			listShgExistedBean = self.getSHGExistedProjectDetail(Integer.parseInt(projectid));
			if (listShgCreatedBean.size() > 0 && shgType.equals("shgNew")) {

				Workbook workbook = new XSSFWorkbook();
				// invoking creatSheet() method and passing the name of the sheet to be created
				Sheet sheet = workbook.createSheet("Report OC1- Project wise details of Newly Created Self Help Group(SHG)");

				CellStyle style = CommonFunctions.getStyle(workbook);

				String rptName = "Report OC1- Project wise details of Newly Created Self Help Group(SHG)";
				String areaAmtValDetail = "";

				CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 0);
				CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 15, areaAmtValDetail, workbook);

				mergedRegion = new CellRangeAddress(5, 5, 0, 15);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 0, 0);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 1, 1);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 2, 2);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 3, 3);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 4, 4);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 5, 5);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 6, 6, 9);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 10, 10);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 11, 11);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 12, 12);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 13, 13);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 14, 14);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 15, 15);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(listShgCreatedBean.size() + 9, listShgCreatedBean.size() + 9, 0, 4);
				sheet.addMergedRegion(mergedRegion);

				Row detail = sheet.createRow(5);
				Cell cell = detail.createCell(0);
				cell.setCellValue("State : "+stname +"   District : "+distname +"   Project : "+projname+"   Head : Newly Created Self Help Group(SHG)");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
				for (int i = 1; i < 16; i++) {
					detail.createCell(i).setCellStyle(style);
				}

				// creating the 5th row using the createRow() method
				Row rowhead = sheet.createRow(6);

				cell = rowhead.createCell(0);
				cell.setCellValue("S.No.");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(1);
				cell.setCellValue("No. of Newly Created SHG");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(2);
				cell.setCellValue("Name of SHG");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(3);
				cell.setCellValue("Department/ Scheme");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(4);
				cell.setCellValue("Date of registration");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(5);
				cell.setCellValue("Amount of Revolving Fund (in Rs.)");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(6);
				cell.setCellValue("Total Members");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				for (int i = 7; i < 10; i++) {
					rowhead.createCell(i).setCellStyle(style);
				}

				cell = rowhead.createCell(10);
				cell.setCellValue("Core Activity");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(11);
				cell.setCellValue("No. of SHG members having PM Bima Yojana");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(12);
				cell.setCellValue("Thrift & Credit");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(13);
				cell.setCellValue("Avg. turnover of SHG");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(14);
				cell.setCellValue("Avg. Income Per Annum");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(15);
				cell.setCellValue("Whether Fedrated");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				Row rowhead1 = sheet.createRow(7);
				for (int i = 0; i < 6; i++) {
					rowhead1.createCell(i).setCellStyle(style);
				}
				
				cell = rowhead1.createCell(6);
				cell.setCellValue("SC");
				cell.setCellStyle(style);
				cell = rowhead1.createCell(7);
				cell.setCellValue("ST");
				cell.setCellStyle(style);
				cell = rowhead1.createCell(8);
				cell.setCellValue("Other");
				cell.setCellStyle(style);
				cell = rowhead1.createCell(9);
				cell.setCellValue("Women");
				cell.setCellStyle(style);
				for (int i = 10; i < 16; i++) {
					rowhead1.createCell(i).setCellStyle(style);
				}
				
				Row rowhead2 = sheet.createRow(8); 
				for(int i = 0; i<16; i++) {	
				Cell cell1 = rowhead2.createCell(i);
				cell1.setCellValue(i+1);
				cell1.setCellStyle(style);
				}

				int rowno = 9;
				int sno = 1;
				int noOfUserGrp = 0;
				double totrevolving = 0.0;
				int totSc = 0;
				int totSt = 0;
				int totOther = 0;
				int totWomen = 0;
				int totbimayogana = 0;
				double totTrnovr = 0;
				double totIncme = 0;
				for (SelfHelpGroupReportBean ugBean : listShgCreatedBean) {
					Row row = sheet.createRow(rowno);
					if(noOfUserGrp != ugBean.getShg_id()) {
						noOfUserGrp = ugBean.getShg_id();
		        		row.createCell(0).setCellValue(sno); 
		        		row.createCell(1).setCellValue(ugBean.getTotalno());
		        		sno++;
		        	}else {
		        		row.createCell(0).setCellValue(""); 
		        		row.createCell(1).setCellValue("");
		        	}
					
					row.createCell(2).setCellValue(ugBean.getName());
					row.createCell(3).setCellValue(ugBean.getSchemename());
					row.createCell(4).setCellValue(ugBean.getReg_date());
					row.createCell(5).setCellValue(ugBean.getRevolving_amount()==null?0.0:ugBean.getRevolving_amount().doubleValue());
					//row.createCell(5).setCellValue(ugBean.getRevolving_amount().doubleValue());
					row.createCell(6).setCellValue(ugBean.getSc());
					row.createCell(7).setCellValue(ugBean.getSt());
					row.createCell(8).setCellValue(ugBean.getOther());
					row.createCell(9).setCellValue(ugBean.getWomen());
					row.createCell(10).setCellValue(ugBean.getCoreactivity());
					row.createCell(11).setCellValue(ugBean.getPm_bima_yogana());

					row.createCell(12).setCellValue(ugBean.getThrift());
					row.createCell(13).setCellValue(ugBean.getAvg_turnover()==null?0.0:ugBean.getAvg_turnover().doubleValue());
					row.createCell(14).setCellValue(ugBean.getAvg_income()==null?0.0:ugBean.getAvg_income().doubleValue());
					row.createCell(15).setCellValue(ugBean.getFederated());

					totrevolving = totrevolving + (ugBean.getRevolving_amount()==null?0.0:ugBean.getRevolving_amount().doubleValue());
					totSc = totSc + ugBean.getSc();
					totSt = totSt + ugBean.getSt();
					totOther = totOther + ugBean.getOther();
					totWomen = totWomen + ugBean.getWomen();
					totbimayogana = totbimayogana + ugBean.getPm_bima_yogana();
					totTrnovr = totTrnovr + (ugBean.getAvg_turnover()==null?0.0:ugBean.getAvg_turnover().doubleValue());
					totIncme = totIncme + (ugBean.getAvg_income()==null?0.0:ugBean.getAvg_income().doubleValue());
					rowno++;
				}

				CellStyle style1 = workbook.createCellStyle();
				style1.setBorderTop(BorderStyle.THIN);
				style1.setBorderBottom(BorderStyle.THIN);
				style1.setBorderLeft(BorderStyle.THIN);
				style1.setBorderRight(BorderStyle.THIN);
				style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
				font1.setFontHeightInPoints((short) 12);
				font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
				style1.setFont(font1);

				Row row = sheet.createRow(listShgCreatedBean.size() + 9);
				cell = row.createCell(0);
				cell.setCellValue("Grand Total");
				cell.setCellStyle(style1);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
				for (int i = 1; i < 5; i++) {
					row.createCell(i).setCellStyle(style);
				}
				cell = row.createCell(5);
				cell.setCellValue(totrevolving);
				cell.setCellStyle(style1);

				cell = row.createCell(6);
				cell.setCellValue(totSc);
				cell.setCellStyle(style1);
				cell = row.createCell(7);
				cell.setCellValue(totSt);
				cell.setCellStyle(style1);
				cell = row.createCell(8);
				cell.setCellValue(totOther);
				cell.setCellStyle(style1);
				cell = row.createCell(9);
				cell.setCellValue(totWomen);
				cell.setCellStyle(style1);
				cell = row.createCell(10);
				cell.setCellStyle(style1);
				
				cell = row.createCell(11);
				cell.setCellValue(totbimayogana);
				cell.setCellStyle(style1);
				cell = row.createCell(12);
				cell.setCellStyle(style1);
				cell = row.createCell(13);
				cell.setCellValue(totTrnovr);
				cell.setCellStyle(style1);
				cell = row.createCell(14);
				cell.setCellValue(totIncme);
				cell.setCellStyle(style1);
				cell = row.createCell(15);
				cell.setCellStyle(style1);


				CommonFunctions.getExcelFooter(sheet, mergedRegion, listShgCreatedBean.size(), 15);
				String fileName = "attachment; filename=Report OC1- Project Detail Newly Created of Self Help Group.xlsx";

				CommonFunctions.downloadExcel(response, workbook, fileName);
			}
			if(listShgExistedBean.size()>0 && shgType.equals("shgExisted")) {


				Workbook workbook = new XSSFWorkbook();
				// invoking creatSheet() method and passing the name of the sheet to be created
				Sheet sheet = workbook.createSheet("Report OC1- Project wise details of Existing Created Self Help Group(SHG)");

				CellStyle style = CommonFunctions.getStyle(workbook);

				String rptName = "Report OC1- Project wise details of Existing Created Self Help Group(SHG)";
				String areaAmtValDetail = "";

				CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 0);
				CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 15, areaAmtValDetail, workbook);

				mergedRegion = new CellRangeAddress(5, 5, 0, 15);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 0, 0);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 1, 1);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 2, 2);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 3, 3);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 4, 4);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 5, 5);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 6, 6, 9);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 10, 10);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 11, 11);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 12, 12);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 13, 13);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 14, 14);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(6, 7, 15, 15);
				sheet.addMergedRegion(mergedRegion);
				mergedRegion = new CellRangeAddress(listShgExistedBean.size() + 8, listShgExistedBean.size() + 8, 0, 4);
				sheet.addMergedRegion(mergedRegion);

				Row detail = sheet.createRow(5);
				Cell cell = detail.createCell(0);
				cell.setCellValue("State : "+stname +"   District : "+distname +"   Project : "+projname+"   Head : Newly Created Self Help Group(SHG)");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
				for (int i = 1; i < 16; i++) {
					detail.createCell(i).setCellStyle(style);
				}

				// creating the 5th row using the createRow() method
				Row rowhead = sheet.createRow(6);

				cell = rowhead.createCell(0);
				cell.setCellValue("S.No.");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(1);
				cell.setCellValue("No. of Existing Created SHG");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(2);
				cell.setCellValue("Name of SHG");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(3);
				cell.setCellValue("Department/ Scheme");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(4);
				cell.setCellValue("Date of registration");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(5);
				cell.setCellValue("Amount of Revolving Fund (in Rs.)");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(6);
				cell.setCellValue("Total Members");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				for (int i = 7; i < 10; i++) {
					rowhead.createCell(i).setCellStyle(style);
				}

				cell = rowhead.createCell(10);
				cell.setCellValue("Core Activity");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(11);
				cell.setCellValue("No. of SHG members having PM Bima Yojana");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				cell = rowhead.createCell(12);
				cell.setCellValue("Thrift & Credit");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(13);
				cell.setCellValue("Avg. turnover of SHG");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(14);
				cell.setCellValue("Avg. Income Per Annum");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
				cell = rowhead.createCell(15);
				cell.setCellValue("Whether Fedrated");
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

				Row rowhead1 = sheet.createRow(7);
				for (int i = 0; i < 6; i++) {
					rowhead1.createCell(i).setCellStyle(style);
				}
				
				cell = rowhead1.createCell(6);
				cell.setCellValue("SC");
				cell.setCellStyle(style);
				cell = rowhead1.createCell(7);
				cell.setCellValue("ST");
				cell.setCellStyle(style);
				cell = rowhead1.createCell(8);
				cell.setCellValue("Other");
				cell.setCellStyle(style);
				cell = rowhead1.createCell(9);
				cell.setCellValue("Women");
				cell.setCellStyle(style);
				for (int i = 10; i < 16; i++) {
					rowhead1.createCell(i).setCellStyle(style);
				}

				int sno = 1;
				int rowno = 8;
				int noOfUserGrp = 0;
				double totrevolving = 0.0;
				int totSc = 0;
				int totSt = 0;
				int totOther = 0;
				int totWomen = 0;
				int totbimayogana = 0;
				double totTrnovr = 0;
				double totIncme = 0;
				for (SelfHelpGroupReportBean ugBean : listShgExistedBean) {
					Row row = sheet.createRow(rowno);
					if(noOfUserGrp != ugBean.getShg_id()) {
						noOfUserGrp = ugBean.getShg_id();
						row.createCell(1).setCellValue(ugBean.getTotalno());
		        		row.createCell(0).setCellValue(sno); 
		        		sno++;
		        	}else {
		        		row.createCell(0).setCellValue(""); 
		        		row.createCell(1).setCellValue("");
		        	}
					row.createCell(2).setCellValue(ugBean.getName());
					row.createCell(3).setCellValue(ugBean.getSchemename());
					row.createCell(4).setCellValue(ugBean.getReg_date());
					row.createCell(5).setCellValue(ugBean.getRevolving_amount().doubleValue());
					row.createCell(6).setCellValue(ugBean.getSc());
					row.createCell(7).setCellValue(ugBean.getSt());
					row.createCell(8).setCellValue(ugBean.getOther());
					row.createCell(9).setCellValue(ugBean.getWomen());
					row.createCell(10).setCellValue(ugBean.getCoreactivity());
					row.createCell(11).setCellValue(ugBean.getPm_bima_yogana());

					row.createCell(12).setCellValue(ugBean.getThrift());
					row.createCell(13).setCellValue(ugBean.getAvg_turnover()==null?0.0:ugBean.getAvg_turnover().doubleValue());
					row.createCell(14).setCellValue(ugBean.getAvg_income()==null?0.0:ugBean.getAvg_income().doubleValue());
					row.createCell(15).setCellValue(ugBean.getFederated());

					totrevolving = totrevolving + (ugBean.getRevolving_amount()==null?0.0:ugBean.getRevolving_amount().doubleValue());
					totSc = totSc + ugBean.getSc();
					totSt = totSt + ugBean.getSt();
					totOther = totOther + ugBean.getOther();
					totWomen = totWomen + ugBean.getWomen();
					totbimayogana = totbimayogana + ugBean.getPm_bima_yogana();
					totTrnovr = totTrnovr + (ugBean.getAvg_turnover()==null?0.0:ugBean.getAvg_turnover().doubleValue());
					totIncme = totIncme + (ugBean.getAvg_income()==null?0.0:ugBean.getAvg_income().doubleValue());
					rowno++;
				}

				CellStyle style1 = workbook.createCellStyle();
				style1.setBorderTop(BorderStyle.THIN);
				style1.setBorderBottom(BorderStyle.THIN);
				style1.setBorderLeft(BorderStyle.THIN);
				style1.setBorderRight(BorderStyle.THIN);
				style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
				style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
				font1.setFontHeightInPoints((short) 12);
				font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
				style1.setFont(font1);

				Row row = sheet.createRow(listShgExistedBean.size() + 8);
				cell = row.createCell(0);
				cell.setCellValue("Grand Total");
				cell.setCellStyle(style1);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
				for (int i = 1; i < 5; i++) {
					row.createCell(i).setCellStyle(style);
				}
				cell = row.createCell(5);
				cell.setCellValue(totrevolving);
				cell.setCellStyle(style1);

				cell = row.createCell(6);
				cell.setCellValue(totSc);
				cell.setCellStyle(style1);
				cell = row.createCell(7);
				cell.setCellValue(totSt);
				cell.setCellStyle(style1);
				cell = row.createCell(8);
				cell.setCellValue(totOther);
				cell.setCellStyle(style1);
				cell = row.createCell(9);
				cell.setCellValue(totWomen);
				cell.setCellStyle(style1);
				cell = row.createCell(10);
				cell.setCellStyle(style1);
				
				cell = row.createCell(11);
				cell.setCellValue(totbimayogana);
				cell.setCellStyle(style1);
				cell = row.createCell(12);
				cell.setCellStyle(style1);
				cell = row.createCell(13);
				cell.setCellValue(totTrnovr);
				cell.setCellStyle(style1);
				cell = row.createCell(14);
				cell.setCellValue(totIncme);
				cell.setCellStyle(style1);
				cell = row.createCell(15);
				cell.setCellStyle(style1);


				CommonFunctions.getExcelFooter(sheet, mergedRegion, listShgExistedBean.size(), 15);
				String fileName = "attachment; filename=Report OC1- Project Detail of Existing Created Self Help Group.xlsx";

				CommonFunctions.downloadExcel(response, workbook, fileName);
			
			}
			
		}
		model.addAttribute("ListShgCreatedBean", listShgCreatedBean);
		model.addAttribute("ListShgCreatedBeanSize", listShgCreatedBean.size());
		model.addAttribute("ListShgExistedBean", listShgExistedBean);
		model.addAttribute("ListShgExistedBeanSize", listShgExistedBean.size());
		model.addAttribute("ListUserGBean", listUserGBean);
		model.addAttribute("ListUserGBeanSize", listUserGBean.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtype);
		model.addAttribute("projname", projname);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		
		return "output/SelfHelpGroup1ProjectViewDetails";
		
	}
	
	@RequestMapping(value = "/downloadUserSelfHelpGroupProjectDetailViewPDF", method = RequestMethod.POST)
	public ModelAndView downloadUserSelfHelpGroupProjectDetailViewPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String projectid=request.getParameter("projectid");
		String projname=request.getParameter("projname");
		String headtype=request.getParameter("headtypeh");
		String shgType=request.getParameter("shgHead");
		String distname=request.getParameter("distname");
		String stname=request.getParameter("stname");
		
		List<SelfHelpGroupReportBean> listShgCreatedBeans = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listShgExistedBeans = new ArrayList<SelfHelpGroupReportBean>();
		List<SelfHelpGroupReportBean> listUserGBeans = new ArrayList<SelfHelpGroupReportBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("SHG/UG");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer=PdfWriter.getInstance(document, baos);
			document.open(); 
	       
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			
			if (headtype.equals("ug")) {
				paragraph3 = new Paragraph("Report OC1- Project wise details of User Group ", f3);
			}
			if (headtype.equals("shg")) {
				paragraph3 = new Paragraph("Report OC1- Project wise details of Newly/Existing Created Self Help Group(SHG)  ", f3);
			}
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
			if (headtype.equals("ug")) {
				table = new PdfPTable(7);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(4);
			}
			if (headtype.equals("shg")) {
				table = new PdfPTable(16);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(4);
			}
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			int totalno=0, sc=0, st=0, other=0, women=0;
			if (headtype.equals("ug")) {
				listUserGBeans = self.getSHGUserGroupProjectDetail(Integer.parseInt(projectid));
				CommonFunctions.insertCellHeader(table, "State : " +stname+ "  District : " +distname+" Project : "+projname+ "   Head : User Group ", Element.ALIGN_LEFT, 7, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of User Group", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of User Group", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 4, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//				
//				for (SelfHelpGroupReportBean ugBean : listShgCreatedBean) {
//					Row row = sheet.createRow(rowno);
//					if(noOfUserGrp != ugBean.getShg_id()) {
//						noOfUserGrp = ugBean.getShg_id();
//		        		row.createCell(0).setCellValue(sno); 
//		        		row.createCell(1).setCellValue(ugBean.getTotalno());
//		        		sno++;
//		        	}else {
//		        		row.createCell(0).setCellValue(""); 
//		        		row.createCell(1).setCellValue("");
//		        	}
				
				
				int j=1;
				int totNum = 0;
				if(listUserGBeans.size()!=0)
					for(int i=0;i<listUserGBeans.size();i++) {
						if(listUserGBeans.get(i).getShg_id() != totNum) {
							CommonFunctions.insertCell(table, String.valueOf(j), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listUserGBeans.get(i).getTotalno().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							j=j+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}		CommonFunctions.insertCell(table, listUserGBeans.get(i).getName(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listUserGBeans.get(i).getSc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listUserGBeans.get(i).getSt().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listUserGBeans.get(i).getOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listUserGBeans.get(i).getWomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						totNum = listUserGBeans.get(i).getShg_id();
						
						sc=sc+listUserGBeans.get(i).getSc();
						st=st+listUserGBeans.get(i).getSt();
						other=other+listUserGBeans.get(i).getOther();
						women=women+listUserGBeans.get(i).getWomen();
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(listUserGBeans.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
			}
		
			int  pm_bima_yogana=0;
			BigDecimal avg_turnover= BigDecimal.valueOf(0);
			BigDecimal avg_income= BigDecimal.valueOf(0);
			BigDecimal revolving_amount=BigDecimal.valueOf(0);

			if (headtype.equals("shg")) {
				listShgCreatedBeans = self.getSHGCreatedProjectDetail(Integer.parseInt(projectid));
				listShgExistedBeans = self.getSHGExistedProjectDetail(Integer.parseInt(projectid));
				if (listShgCreatedBeans.size() > 0 && shgType.equals("shgNew")) {

					
						CommonFunctions.insertCellHeader(table, "State : " +stname+ "  District : " +distname+ "  Project : "+projname+"  Head : Newly Created Self Help Group(SHG)  ", Element.ALIGN_LEFT, 16, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "No. of Newly Created SHG", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Name of SHG", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Department/ Scheme", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						
						CommonFunctions.insertCellHeader(table, "Date of registration", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Amount of Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 4, 1,bf8Bold);
						
						CommonFunctions.insertCellHeader(table, "Core Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "No. of SHG members having PM Bima Yojana", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 2,bf8Bold);
						
						CommonFunctions.insertCellHeader(table, "Avg. turnover of SHG", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Avg. Income Per Annum", Element.ALIGN_CENTER, 1, 2, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Whether Fedrated", Element.ALIGN_CENTER, 1, 2,bf8Bold);
						
						CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
						
						
						CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						
						int k=1;
						
						int totNum = 0;
						if(listShgCreatedBeans.size()!=0)
							for(int i=0;i<listShgCreatedBeans.size();i++) 
							{
								if(listShgCreatedBeans.get(i).getShg_id() != totNum) {
									CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
									CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getTotalno().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
									k=k+1;
								}
								else {
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								}
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getName(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getSchemename(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getReg_date(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getRevolving_amount()==null?"":listShgCreatedBeans.get(i).getRevolving_amount().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getSc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getSt().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getWomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getCoreactivity(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getPm_bima_yogana()==null?"":listShgCreatedBeans.get(i).getPm_bima_yogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getThrift(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getAvg_turnover()==null?"":listShgCreatedBeans.get(i).getAvg_turnover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getAvg_income()==null?"":listShgCreatedBeans.get(i).getAvg_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgCreatedBeans.get(i).getFederated(), Element.ALIGN_RIGHT, 1, 1, bf8);
								totNum = listShgCreatedBeans.get(i).getShg_id();
								
								revolving_amount=revolving_amount.add(listShgCreatedBeans.get(i).getRevolving_amount()==null?BigDecimal.ZERO:listShgCreatedBeans.get(i).getRevolving_amount());
								sc=sc+listShgCreatedBeans.get(i).getSc();
								st=st+listShgCreatedBeans.get(i).getSt();
								other=other+listShgCreatedBeans.get(i).getOther();
								women=women+listShgCreatedBeans.get(i).getWomen();
								pm_bima_yogana=pm_bima_yogana+listShgCreatedBeans.get(i).getPm_bima_yogana();
								avg_turnover=avg_turnover.add(listShgCreatedBeans.get(i).getAvg_turnover()==null?BigDecimal.ZERO:listShgCreatedBeans.get(i).getAvg_turnover());
								avg_income=avg_income.add(listShgCreatedBeans.get(i).getAvg_income()==null?BigDecimal.ZERO:listShgCreatedBeans.get(i).getAvg_income());
							}
							CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 5, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(revolving_amount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(pm_bima_yogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(avg_turnover), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(avg_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							
							if(listShgCreatedBeans.size()==0)
								CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 16, 1, bf8);
						
					}
				if(listShgExistedBeans.size()>0 && shgType.equals("shgExisted")) {
					
					CommonFunctions.insertCellHeader(table, "State : " +stname+ "  District : " +distname+ "  Project : "+projname+"  Head : Existing Created Self Help Group(SHG)  ", Element.ALIGN_LEFT, 16, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "No. of Existing Created SHG", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Name of SHG", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Department/ Scheme", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Date of registration", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Amount of Revolving Fund (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total Members", Element.ALIGN_CENTER, 4, 1,bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Core Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "No. of SHG members having PM Bima Yojana", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Thrift & Credit", Element.ALIGN_CENTER, 1, 2,bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Avg. turnover of SHG", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Avg. Income Per Annum", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Whether Fedrated", Element.ALIGN_CENTER, 1, 2,bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
					
					
					CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					int k=1;
					int totNum = 0;
					if(listShgExistedBeans.size()!=0)
						for(int i=0;i<listShgExistedBeans.size();i++) 
						{
							if(listShgExistedBeans.get(i).getShg_id() != totNum) {
								CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getTotalno().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
								k=k+1;
							}
							else {
								CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							}
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getName(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getSchemename(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getReg_date(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getRevolving_amount()==null?"":listShgExistedBeans.get(i).getRevolving_amount().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getSc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getSt().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getWomen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getCoreactivity(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getPm_bima_yogana()==null?"":listShgExistedBeans.get(i).getPm_bima_yogana().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getThrift(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getAvg_turnover()==null?"":listShgExistedBeans.get(i).getAvg_turnover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getAvg_income()==null?"":listShgExistedBeans.get(i).getAvg_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, listShgExistedBeans.get(i).getFederated(), Element.ALIGN_RIGHT, 1, 1, bf8);
							
							totNum = listShgExistedBeans.get(i).getShg_id();
							
							revolving_amount=revolving_amount.add(listShgExistedBeans.get(i).getRevolving_amount()==null?BigDecimal.ZERO:listShgExistedBeans.get(i).getRevolving_amount());
							sc=sc+listShgExistedBeans.get(i).getSc();
							st=st+listShgExistedBeans.get(i).getSt();
							other=other+listShgExistedBeans.get(i).getOther();
							women=women+listShgExistedBeans.get(i).getWomen();
							pm_bima_yogana=pm_bima_yogana+listShgExistedBeans.get(i).getPm_bima_yogana();
							avg_turnover=avg_turnover.add(listShgExistedBeans.get(i).getAvg_turnover()==null?BigDecimal.ZERO:listShgExistedBeans.get(i).getAvg_turnover());
							avg_income=avg_income.add(listShgExistedBeans.get(i).getAvg_income()==null?BigDecimal.ZERO:listShgExistedBeans.get(i).getAvg_income());
						}
						CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 5, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(revolving_amount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(pm_bima_yogana), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(avg_turnover), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(avg_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						
						if(listShgExistedBeans.size()==0)
							CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 16, 1, bf8);
					
				}
				}
				

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC1(projectDetails).pdf");
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
		
		return null;
	}
}
