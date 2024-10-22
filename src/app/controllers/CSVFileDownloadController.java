package app.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.CSV.CSVService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;

@Controller
public class CSVFileDownloadController {
	
	@Autowired
	CSVService fileService;

	String csvFileName;

//	@RequestMapping(path = "/employees")
//    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException {
//        servletResponse.setContentType("text/csv");
//        servletResponse.addHeader("Content-Disposition","attachment; filename=\"employees.csv\"");
//        InputStreamResource file = new InputStreamResource(fileService.loadState());
//        csvExportService.writeEmployeesToCsv(servletResponse.getWriter());
//    }
	
	@RequestMapping(value = "/downloadStateCSV")
	public ResponseEntity<Resource> downloadStateCSV(HttpServletResponse response) throws IOException {

		String filename = "stateMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadState());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadDistrictCSV")
	public ResponseEntity<Resource> downloadDistrictCSV(HttpServletResponse response) throws IOException {

		String filename = "districtMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadDistrict());
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	@GetMapping(value = "/downloadBlockCSV")
	public ResponseEntity<Resource> downloadBlockCSV(HttpServletResponse response) throws IOException {

		String filename = "blockMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadBlock());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	@GetMapping(value = "/downloadGrampanchayatCSV")
	public ResponseEntity<Resource> downloadGrampanchayatCSV(HttpServletResponse response) throws IOException {

		String filename = "gramPanchayatMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadGramPanchayat());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	@GetMapping(value = "/downloadVillageCSV")
	public ResponseEntity<Resource> downloadVillageCSV(HttpServletResponse response) throws IOException {

		String filename = "villageMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadVillage());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadHeadCSV")
	public ResponseEntity<Resource> downloadHeadCSV(HttpServletResponse response) throws IOException {

		String filename = "headMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadPhysicalHead());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadActivityCSV")
	public ResponseEntity<Resource> downloadActivityCSV(HttpServletResponse response) throws IOException {

		String filename = "Activity.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadPhysicalActivity());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadSubActivityCSV")
	public ResponseEntity<Resource> downloadSubActivityCSV(HttpServletResponse response) throws IOException {

		String filename = "activitytype.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadPhysicalSubActivity());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadFiancialyearCSV")
	public ResponseEntity<Resource> downloadFinancialyearCSV(HttpServletResponse response) throws IOException {

		String filename = "financialyear.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadFiancialyear());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadSanctionedProjectCSV")
	public ResponseEntity<Resource> downloadSanctionedProjectCSV(HttpServletResponse response) throws IOException {

		String filename = "sanctionedproject.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadSanctionedProject());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@RequestMapping(value="/viewCSV")
	public ModelAndView viewCSV(HttpServletResponse response) throws IOException {
	    ModelAndView model = new ModelAndView("downloadCsv");
	    return model;
	}
	
	@GetMapping(value = "/downloadEPAActivityCSV")
	public ResponseEntity<Resource> downloadEPAActivityCSV(HttpServletResponse response) throws IOException {

		String filename = "EPAsMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadEntryPointActivity());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadLivelihoodActivityCSV")
	public ResponseEntity<Resource> downloadLivelihoodActivityCSV(HttpServletResponse response) throws IOException {

		String filename = "LivelihoodActivityMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadLivelihoodActivity());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping(value = "/downloadProductionActivityCSV")
	public ResponseEntity<Resource> downloadProductionActivityCSV(HttpServletResponse response) throws IOException {

		String filename = "ProductionActivityMaster.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadProductionActivity());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
}
