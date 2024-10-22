<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<title>Report SB4 - District wise Distribution of Area According to Crop Type as per Baseline Survey</title>
</head>
<script type="text/javascript">
 
	function downloadPDF(stateName,distCode)
	{
		document.getElementById("stateName").value=stateName;
		document.getElementById("distCode").value=distCode;
			document.getblsprpt.action="downloadblsDistWisecropAreaSurveyPDF";
			document.getblsprpt.method="post";
			document.getblsprpt.submit();
		
	} 
	function exportExcel(stateName,distCode)
	{
		document.getElementById("stateName").value=stateName;
		document.getElementById("distCode").value=distCode;
			document.getblsprpt.action="downloadExcelDistWiseCropAreaSrvy";
			document.getblsprpt.method="post";
			document.getblsprpt.submit();
		
	} 
	
	</script>


<form action="downloadblsDistWisecropAreaSurveyPDF" method="post" name="getblsprpt">
<div class="form-row">
			<input type="hidden" name="stateName" id="stateName" value="" />
			<input type="hidden" name="distCode" id="distCode" value="" />
			</div>
		</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report SB4 - District wise Distribution of Area According to Crop Type as per Baseline Survey for State <br>' <c:out value="${stateName}" /> ' </label></h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stateName}','${distCode}')"	class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF"	onclick="downloadPDF('${stateName}','${distCode}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="pdfBasicExample" class="table">
		<thead>
			<tr>
			<th class="text-right" colspan="2">State: <c:out value="${stateName}"/> </th>
				<th class="text-right" colspan="19">All area in ha. And All Production in unit Quintal </th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">District Name</th>
				<th rowspan="3" class="text-center">Total Project Area</th>
				<th rowspan="3" class="text-center">Plot Area</th>
				<th colspan="16" class="text-center">Crop Type</th>
			</tr>
			<tr>
			
				<th colspan="2" class="text-center">Cereals</th>
				<th colspan="2" class="text-center">Pulses</th>
				<th colspan="2" class="text-center">Oil seeds</th>
				<th colspan="2" class="text-center">Fiber Crops</th>
				<th colspan="2" class="text-center">Pastures</th>
				<th colspan="2" class="text-center">Horticultures</th>
				<th colspan="2" class="text-center">Plantation</th>
				<th colspan="2" class="text-center">Others</th>
			</tr>
			<tr>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
				<th class="text-center">Area</th>
				<th class="text-center">Production</th>
			</tr>

		</thead>

		<tbody id="dtBasicExample">

	
			<c:forEach items="${distList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					
					<td><a
						href="getProjWiseblsCrpareaSrvyDetail?id=<c:out value="${project.dcode}"/>"><c:out
								value="${project.dist_name}" /></a></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.plot_area}" /></td>
					<td class="text-right"><c:out value="${project.cereals}" /></td>
					<td class="text-right"><c:out value="${project.cereals_prod}" /></td>
					<td class="text-right"><c:out value="${project.pulses}" /></td>
					<td class="text-right"><c:out value="${project.pulses_prod}" /></td>
					<td class="text-right"><c:out value="${project.oilseeds}" /></td>
					<td class="text-right"><c:out value="${project.oilseeds_prod}" /></td>
					<td class="text-right"><c:out value="${project.fiber_crops}" /></td>
					<td class="text-right"><c:out value="${project.fiber_crops_prod}" /></td>
					<td class="text-right"><c:out value="${project.pasture}" /></td>
					<td class="text-right"><c:out value="${project.pasture_prod}" /></td>
					<td class="text-right"><c:out value="${project.horticulture}" /></td>
					<td class="text-right"><c:out value="${project.horticulture_prod}" /></td>
					<td class="text-right"><c:out value="${project.plantation_crops}" /></td>
					<td class="text-right"><c:out value="${project.plantation_crops_prod}" /></td>
					<td class="text-right"><c:out value="${project.other_crops}" /></td>
					<td class="text-right"><c:out value="${project.other_crops_prod}" /></td>
				</tr>
					<c:set var="totalproject_area"
					value="${totalproject_area+project.project_area}" />
					<c:set var="totalplot_area"
					value="${totalplot_area+project.plot_area}" />
					<c:set var="totalcereals"
					value="${totalcereals+project.cereals}" />
					<c:set var="totalcereals_prod"
					value="${totalcereals_prod+project.cereals_prod}" />
					<c:set var="totalpulses"
					value="${totalpulses+project.pulses}" />
					<c:set var="totalpulses_prod"
					value="${totalpulses_prod+project.pulses_prod}" />
					<c:set var="totaloilseeds"
					value="${totaloilseeds+project.oilseeds}" />
					<c:set var="totaloilseeds_prod"
					value="${totaloilseeds_prod+project.oilseeds_prod}" />
					<c:set var="totalfiber_crops"
					value="${totalfiber_crops+project.fiber_crops}" />
					<c:set var="totalfiber_crops_prod"
					value="${totalfiber_crops_prod+project.fiber_crops_prod}" />
					<c:set var="totalpasture"
					value="${totalpasture+project.pasture}" />
					<c:set var="totalpasture_prod"
					value="${totalpasture_prod+project.pasture_prod}" />
					<c:set var="totalhorticulture"
					value="${totalhorticulture+project.horticulture}" />
					<c:set var="totalhorticulture_prod"
					value="${totalhorticulture_prod+project.horticulture_prod}" />
					<c:set var="totalplantation_crops"
					value="${totalplantation_crops+project.plantation_crops}" />
					<c:set var="totalplantation_crops_prod"
					value="${totalplantation_crops_prod+project.plantation_crops_prod}" />
					<c:set var="totalother_crops"
					value="${totalother_crops+project.other_crops}" />
					<c:set var="totalother_crops_prod"
					value="${totalother_crops_prod+project.other_crops_prod}" />
					
					
				</c:forEach>
				
				<tr>
<!-- 				<th  colspan="2" class="text-center ;">Grand Total</th> -->
				<td class="table-primary"></td>
								
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproject_area}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplot_area}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcereals}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcereals_prod}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpulses}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpulses_prod}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totaloilseeds}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totaloilseeds_prod}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalfiber_crops}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalfiber_crops_prod}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpasture}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalpasture_prod}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalhorticulture}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalhorticulture_prod}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplantation_crops}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalplantation_crops_prod}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalother_crops}"/></b></td> 
				<td align="right" class="table-primary"><b><c:out value="${totalother_crops_prod}" /></b></td>


		</tbody>
	</table>
</div>