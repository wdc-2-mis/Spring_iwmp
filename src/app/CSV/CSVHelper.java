package app.CSV;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import app.model.IwmpDistrict;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.IwmpState;
import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhyHeads;
import app.model.master.IwmpMPhySubactivity;
import app.model.master.IwmpVillage;
import app.model.outcome.MEpaCoreactivity;
import app.model.outcome.MLivelihoodCoreactivity;
import app.model.outcome.MProductivityCoreactivity;

public class CSVHelper {
	public static ByteArrayInputStream stateCsv(List<IwmpState> states) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
		              "stateCode",
		              "stateLGDCode",
		              "stateName"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpState state : states) {
	        List<String> data = Arrays.asList(
	              String.valueOf(state.getStCode()),
	              String.valueOf(state.getStCode()),
	              state.getStName()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream districtCsv(List<IwmpDistrict> districts) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"stateCode",
		              "districtLGDCode",
		              "districtCode",
		              "districtName"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpDistrict district : districts) {
	        List<String> data = Arrays.asList(
	        		 String.valueOf(district.getIwmpState().getStCode()),
	              String.valueOf(district.getDcode()),
	              String.valueOf(district.getDistrictCodelgd()),
	              district.getDistName()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream blockCsv(List<IwmpBlock> blocks) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"stateCode",
		              "districtLGDCode",
		              "districtCode",
		              "blockLgdCode",
		              "blockCode",		              
		              "blockName"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpBlock block : blocks) {
	        List<String> data = Arrays.asList(
	        		 String.valueOf(block.getIwmpDistrict().getIwmpState().getStCode()),
	              String.valueOf(block.getIwmpDistrict().getDistrictCodelgd()),
	              String.valueOf(block.getIwmpDistrict().getDcode()),
	              String.valueOf(block.getBlockCodelgd()),
	              String.valueOf(block.getBcode()),
	              block.getBlockName()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream grampanchayatCsv(List<IwmpGramPanchayat> gps) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"stateCode",
		              "districtLGDCode",
		              "districtCode",
		              "blockLgdCode",
		              "blockCode",		              
		              "gpLgdCode",
		              "gpCode",
		              "gramPanchayatName"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpGramPanchayat gp : gps) {
	        List<String> data = Arrays.asList(
	        		 String.valueOf(gp.getIwmpBlock().getIwmpDistrict().getIwmpState().getStCode()),
	              String.valueOf(gp.getIwmpBlock().getIwmpDistrict().getDistrictCodelgd()),
	              String.valueOf(gp.getIwmpBlock().getIwmpDistrict().getDcode()),
	              String.valueOf(gp.getIwmpBlock().getBlockCodelgd()),
	              String.valueOf(gp.getIwmpBlock().getBcode()),
	              String.valueOf(gp.getGramPanchayatLgdCode()),
	              String.valueOf(gp.getGcode()),
	              gp.getGramPanchayatName()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream villageCsv(List<IwmpVillage> villages) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"stateCode",
		              "districtLGDCode",
		              "districtCode",
		              "blockLgdCode",
		              "blockCode",		              
		              "gpLgdCode",
		              "gpCode",
		              "villageLgdCode",
		              "villageCode",
		              "villageName"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpVillage village : villages) {
	        List<String> data = Arrays.asList(
	        		 String.valueOf(village.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getStCode()),
	              String.valueOf(village.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getDistrictCodelgd()),
	              String.valueOf(village.getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getDcode()),
	              String.valueOf(village.getIwmpGramPanchayat().getIwmpBlock().getBlockCodelgd()),
	              String.valueOf(village.getIwmpGramPanchayat().getIwmpBlock().getBcode()),
	              String.valueOf(village.getIwmpGramPanchayat().getGramPanchayatLgdCode()),
	              String.valueOf(village.getIwmpGramPanchayat().getGcode()),
	              String.valueOf(village.getVillageLgdcode()),
	              String.valueOf(village.getVcode()),
	              village.getVillageName()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream physicalHeadCsv(List<IwmpMPhyHeads> physHeads) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"headCode",
		              "headDescription"		             
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpMPhyHeads physHead : physHeads) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(physHead.getHeadCode()),
	             
	        	  physHead.getHeadDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream physicalHeadActivityCsv(List<IwmpMPhyActivity> physHeadActivitiess) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"headCode",
		              "ActivityCode",
		              "ActivityDescription"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpMPhyActivity physHead : physHeadActivitiess) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(physHead.getIwmpMPhyHeads().getHeadCode()),
	        	  String.valueOf(physHead.getActivityCode()),             
	        	  physHead.getActivityDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }

	public static ByteArrayInputStream physicalHeadSubActivityCsv(List<IwmpMPhySubactivity> physHeadActivitiess) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"headCode",
		              "ActivityCode",
		              "ActivityType",
		              "ActivityTypeDescription"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpMPhySubactivity physHead : physHeadActivitiess) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(physHead.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode()),
	        	  String.valueOf(physHead.getIwmpMPhyActivity().getActivityCode()), 
	        	  String.valueOf(physHead.getSubActivityCode()),
	        	  physHead.getSubActivityDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream financialYar(List<IwmpMFinYear> finyears) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"FinancialYearCode",
		              "FinancialYearDescription"		             
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpMFinYear finyear : finyears) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(finyear.getFinYrCd()),
	             
	        	  finyear.getFinYrDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }

	public static ByteArrayInputStream sactionedProjectCsv(List<IwmpMProject> projects) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"stateCode",
		              "districtLGDCode",
		              "districtCode",
		              "FinancialYearCode",
		              "projectCode",
		              "projectName"
		            );
		        csvPrinter.printRecord(data1);
	      for (IwmpMProject project : projects) {
	        List<String> data = Arrays.asList(
	        		 String.valueOf(project.getIwmpDistrict().getIwmpState().getStCode()),
	              String.valueOf(project.getIwmpDistrict().getDistrictCodelgd()),
	              String.valueOf(project.getIwmpDistrict().getDcode()),
	              String.valueOf(project.getIwmpMFinYear().getFinYrCd()),
	              project.getProjectCd(),
	              project.getProjName()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream EntryPointActivityCsv(List<MEpaCoreactivity> physHeads) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"epaActivityId",
		              "epaActivityDesc"		             
		            );
		        csvPrinter.printRecord(data1);
	      for (MEpaCoreactivity physHead : physHeads) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(physHead.getEpaActivityId()),
	             
	        	  physHead.getEpaDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream LivelihoodActivityCsv(List<MLivelihoodCoreactivity> physHeads) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"LivelihoodCoreactivityId",
		              "LivelihoodCoreactivityDesc"		             
		            );
		        csvPrinter.printRecord(data1);
	      for (MLivelihoodCoreactivity physHead : physHeads) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(physHead.getLivelihoodCoreactivityId()),
	             
	        	  physHead.getLivelihoodCoreactivityDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
	
	public static ByteArrayInputStream ProductionActivityCsv(List<MProductivityCoreactivity> physHeads) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	List<String> data1 = Arrays.asList(
	    			"ProductivityCoreactivityId",
		              "ProductivityCoreactivityDesc"		             
		            );
		        csvPrinter.printRecord(data1);
	      for (MProductivityCoreactivity physHead : physHeads) {
	        List<String> data = Arrays.asList(
	        	  String.valueOf(physHead.getProductivityCoreactivityId()),
	             
	        	  physHead.getProductivityCoreactivityDesc()
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
}
