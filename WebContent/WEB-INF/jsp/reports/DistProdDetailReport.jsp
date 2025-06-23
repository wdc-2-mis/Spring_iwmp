<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE8-  District-wise Mid Term Evaluation of Production Details</title>

<script type="text/javascript">

function downloadPDF(stcd, stName){
    document.getElementById("stcd").value=stcd;
    document.getElementById("stName").value=stName;
	document.getprod.action="downloadDistProdDetailsReportPdf";
	document.getprod.method="post";
	document.getprod.submit();
}

function exportExcel(stcd, stName){
    document.getElementById("stcd").value=stcd;
    document.getElementById("stName").value=stName;
	document.getprod.action="downloadExcelDistProdDetailsReport";
	document.getprod.method="post";
	document.getprod.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE8-  District-wise Mid Term Evaluation of Production Details (Milk, Fodder, Migration from Rural to Urban, Springs Rejuvenated and Persons Benefitted) for State  '<c:out value="${stName}"/>'</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-11">

	<form:form action="getConWorksDetails" name="getprod" id="getprod" method="get">
				<input type="hidden" name="stcd" id="stcd" value="" />
	     	 	<input type="hidden" name="stName" id="stName" value="" />
		
 	</form:form>
 
<br>
	<c:if test="${not empty prodDList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}','${stName}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}','${stName}')" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table"  style="width: 70%;">
		<thead>
			   <tr>
            <th rowspan="3">S.No.</th>
            <th rowspan="3">District Name</th>
            <th rowspan="3">Total No. of Project</th>
            <th colspan="6" style="text-align: center">Production</th>
            <th colspan="3" rowspan="2" style="text-align: center">Annual Migration from Rural to Urban Area in Project Area (Nos.)</th>
            <th colspan="3" rowspan="2" style="text-align: center">No. of Springs Rejuvenated</th>
            <th colspan="3" rowspan="2" style="text-align: center">No. of Persons Benefitted due to Rejuvenation of Springs</th>
        </tr>
        <tr>
        <th colspan="3" style="text-align: center">	Milk Production of Milch Cattle (Kl/Yr.)</th>
        <th colspan="3" style="text-align: center">Fodder Production (Qt./Yr.)</th>
        </tr>
        <tr>
            <th rowspan="2">Pre - Project Status(Aggregate)</th>
            <th rowspan="2">Mid - Project Status(Aggregate)</th>
            <th rowspan="2">Controlled Area</th>
             <th rowspan="2">Pre - Project Status(Aggregate)</th>
            <th rowspan="2">Mid - Project Status(Aggregate)</th>
            <th rowspan="2">Controlled Area</th>
             <th rowspan="2">Pre - Project Status(Aggregate)</th>
            <th rowspan="2">Mid - Project Status(Aggregate)</th>
            <th rowspan="2">Controlled Area</th>
            <th>Pre - Project Status(Aggregate)</th>
            <th>Mid - Project Status(Aggregate)a</th>
            <th>Controlled Area</th>
            <th>Pre - Project Status(Aggregate)</th>
            <th>Mid - Project Status(Aggregate)</th>
            <th>Controlled Area</th>
        </tr>
        
		</thead>
		<tbody id = "convergedWorksRptTbody">
			<tr>
				<th class="text-center">1</th>
				<th class="text-center">2</th>
				<th class="text-center">3</th>
				<th class="text-center">4</th>
				<th class="text-center">5</th>
				<th class="text-center">6</th>
				<th class="text-center">7</th>
				<th class="text-center">8</th>
				<th class="text-center">9</th>
				<th class="text-center">10</th>
				<th class="text-center">11</th>
				<th class="text-center">12</th>
				<th class="text-center">13</th>
				<th class="text-center">14</th>
				<th class="text-center">15</th>
				<th class="text-center">16</th>
				<th class="text-center">17</th>
				<th class="text-center">18</th>
			</tr>
			<c:forEach items="${prodDList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.dist_name}" /></td>
					<td class="text-right"><c:out value="${dt.total_project}" /></td>
					<td class="text-right"><c:out value="${dt.pre_milch_cattle}" /></td>
					<td class="text-right"><c:out value="${dt.mid_milch_cattle}" /></td>
					<td class="text-right"><c:out value="${dt.control_milch_cattle}" /></td>
					<td class="text-right"><c:out value="${dt.pre_fodder_production}" /></td>
					<td class="text-right"><c:out value="${dt.mid_fodder_production}" /></td>
					<td class="text-right"><c:out value="${dt.control_fodder_production}" /></td>
					<td class="text-right"><c:out value="${dt.pre_rural_urban}" /></td>
					<td class="text-right"><c:out value="${dt.mid_rural_urban}" /></td>
					<td class="text-right"><c:out value="${dt.control_rural_urban}" /></td>
					<td class="text-right"><c:out value="${dt.pre_spring_rejuvenated}" /></td>
					<td class="text-right"><c:out value="${dt.mid_spring_rejuvenated}" /></td>
					<td class="text-right"><c:out value="N/A" /></td>
					<td class="text-right"><c:out value="${dt.pre_person_benefitte}" /></td>
					<td class="text-right"><c:out value="${dt.mid_person_benefitte}" /></td>
					<td class="text-right"><c:out value="N/A" /></td>
					
				</tr>
				
 				<c:set var = "totproj" 
  				value = "${totproj + dt.total_project}" />  
 				<c:set var = "premilchcattle"  
  				value = "${premilchcattle + dt.pre_milch_cattle}" /> 
  				<c:set var = "midmilchcattle"  
  				value = "${midmilchcattle + dt.mid_milch_cattle}" />  
  				<c:set var = "controlmilchcattle"  
  				value = "${controlmilchcattle + dt.control_milch_cattle}" /> 
 				
 				<c:set var = "prefodderproduction"  
 				value = "${prefodderproduction + dt.pre_fodder_production}" />
 				<c:set var = "midfodderproduction"  
 				value = "${midfodderproduction + dt.mid_fodder_production}" />
 				<c:set var = "controlfodderproduction"  
 				value = "${controlfodderproduction + dt.control_fodder_production}" />
 				
 				<c:set var = "preruralurban"  
  				value = "${preruralurban + dt.pre_rural_urban}" /> 
  				<c:set var = "midruralurban"  
  				value = "${midruralurban + dt.mid_rural_urban}" />  
  				<c:set var = "controlruralurban"  
  				value = "${controlruralurban + dt.control_rural_urban}" /> 
  				
  				<c:set var = "springrejuvenated"  
 				value = "${springrejuvenated + dt.pre_spring_rejuvenated}" />
 				
 				<c:set var = "mspringrejuvenated"  
 				value = "${mspringrejuvenated + dt.mid_spring_rejuvenated}" />
 				<c:set var = "controlspringrejuvenated"  
 				value = "${controlspringrejuvenated + dt.control_spring_rejuvenated}" />
 				
 				<c:set var = "personbenefitte"  
 				value = "${personbenefitte + dt.pre_person_benefitte}" />
 				<c:set var = "mpersonbenefitte"  
 				value = "${mpersonbenefitte + dt.mid_person_benefitte}" />
 				<c:set var = "controlpersonbenefitte"  
 				value = "${controlpersonbenefitte + dt.control_person_benefitte}" />

			</c:forEach>
			<c:if test="${prodListDSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${premilchcattle}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${midmilchcattle}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${controlmilchcattle}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${prefodderproduction}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${midfodderproduction}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${controlfodderproduction}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${preruralurban}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${midruralurban}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${controlruralurban}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${springrejuvenated}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${mspringrejuvenated}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="N/A" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${personbenefitte}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${mpersonbenefitte}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="N/A" /></b></td>
					
				</tr>
			</c:if>
			<c:if test="${prodListDSize==0}">
				<tr>
					<td align="center" colspan="10" class="required" style="color:red;"><b>Data Not Found</b></td>
				</tr>
			</c:if>
		</tbody>
	</table>
			</div>
 		</div>
	</div>


	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>