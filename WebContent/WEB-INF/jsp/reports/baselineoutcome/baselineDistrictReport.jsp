<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>
<title>Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for State</title>

</head>
<script type="text/javascript">
	function exportExcel(stateName,distCode){
		document.getElementById("stateName").value=stateName;
		document.getElementById("distCode").value=distCode;
		document.rptDistWiseBls.action="downloadExcelDistWiseBaseline";
		document.rptDistWiseBls.method="post";
		document.rptDistWiseBls.submit();
	}
	
	function downloadPDF(stateName,distCode)
	{
		document.getElementById("stateName").value=stateName;
		document.getElementById("distCode").value=distCode;
			document.rptDistWiseBls.action="downloaDistWisebaselinePDF";
			document.rptDistWiseBls.method="post";
			document.rptDistWiseBls.submit();
		
	} 
	
	</script>


<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">
			Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for State
			" <c:out value="${stateName}" /> "
			
		</label></h5>
	</div>
	<hr />
	<form action="downloadExcelDistWiseBaseline" method="post" id="getDistWiseBls" name="rptDistWiseBls">
			<div class="form-row">
			<input type="hidden" name="stateName" id="stateName" value="" />
			<input type="hidden" name="distCode" id="distCode" value="" />
			</div>
		</form>
	<button name="exportExcel" id="exportExcel"	onclick="exportExcel('${stateName}','${distCode}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF"	onclick="downloadPDF('${stateName}','${distCode}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<div class="form-group  col-md-12">
		<div class="message">
			<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong>
				Total Income is the Total Value of Output.</label>
		</div>
	</div>
	<table id="dtBasicExample" class="table">
		<thead>
			<tr>
				<th>State:- <c:out value="${stateName}" /></th>
				<th class="text-right" colspan="12">All area in ha. <br /> All
					amounts are Rs. in Lakh
				</th>
			</tr>
			<tr>
				<th rowspan="2" class="text-center">S.No.</th>
				<th rowspan="2" class="text-center">District</th>
				<th rowspan="2" class="text-center">Total Plot No.</th>
				<th rowspan="2" class="text-center">Project Area</th>
				<th rowspan="2" class="text-center">Plot Area</th>
				<th colspan="5" class="text-center">Classification of Land</th>

				<th rowspan="2" class="text-center">Net Sown Area</th>
				<th rowspan="2" class="text-center ">Grossed Cropped Area</th>
				<th rowspan="2" class="text-center ">Total Income</th>
			</tr>
			<tr>
				<th class="text-center">Cultivable Wasteland</th>
				<th class="text-center">Degraded Land</th>
				<th class="text-center">Rainfed</th>
				<th class="text-center">Forest Land</th>
				<th class="text-center">Others</th>
			</tr>

<!-- 			<tr> -->
<!-- 				<td class="text-center">1</td> -->
<!-- 				<td class="text-center">2</td> -->
<!-- 				<td class="text-center">3</td> -->
<!-- 				<td class="text-center">4</td> -->
<!-- 				<td class="text-center">5</td> -->
<!-- 				<td class="text-center">6</td> -->
<!-- 				<td class="text-center">7</td> -->
<!-- 				<td class="text-center">8</td> -->
<!-- 				<td class="text-center">9</td> -->
<!-- 				<td class="text-center">10</td> -->
<!-- 				<td class="text-center">11</td> -->
<!-- 				<td class="text-center">12</td> -->
<!-- 			</tr> -->
		</thead>

		<tbody id="dtBasicExample">
		
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
			</tr>
			<c:set var="totalincome" value="0" />
			<c:set var="totpltno" value="0" />
			<c:set var="totaltreatableprojectarea" value="0" />
			<c:set var="cultivablewasteland" value="0" />
			<c:set var="degradedland" value="0" />
			<c:set var="rainfed" value="0" />
			<c:set var="forestland" value="0" />
			<c:set var="others" value="0" />
			<c:set var="netsownarea" value="0" />
			<c:set var="grossedcroppedarea" value="0" />
			<c:set var="projectarea" value="0" />

			<c:forEach items="${projectList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<td><a href="blsrptproj?id=<c:out value="${project.dcode}"/>"><c:out
								value="${project.distname}" /></a></td>
					<td class="text-right"><c:out value = "${project.total_plot}"/></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.treatable_area}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.cultivable_wasteland}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.degraded_land}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.rainfed}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.forest_land}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber type="number" minFractionDigits="4">
					<c:out value="${project.others}" /></fmt:formatNumber></td>
					<c:if test="${project.net_sown_area le project.gross_cropped_area }">
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.net_sown_area}" /></fmt:formatNumber></td>
					</c:if>
					<c:if test="${project.net_sown_area gt project.gross_cropped_area }">
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.gross_cropped_area}" /></fmt:formatNumber></td>
					</c:if>
					<td class="text-right">
					<c:out value="${project.gross_cropped_area}" /></td>
					<td class="text-right"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${project.total_income/100000}" /></td>
				</tr>

				<c:set var="totpltno" value="${totpltno+ project.total_plot}" />
				<c:set var="totalincome"
					value="${totalincome+ project.total_income}" />
				<c:set var="totaltreatableprojectarea"
					value="${ totaltreatableprojectarea+project.treatable_area}" />
				<c:set var="cultivablewasteland"
					value="${ cultivablewasteland+project.cultivable_wasteland}" />
				<c:set var="degradedland"
					value="${ degradedland+project.degraded_land}" />
				<c:set var="rainfed" value="${ rainfed+project.rainfed}" />
				<c:set var="forestland" value="${ forestland+project.forest_land}" />
				<c:set var="others" value="${ others+project.others}" />
				<c:if test="${project.net_sown_area le project.gross_cropped_area }">
				<c:set var="netsownarea"
					value="${ netsownarea+project.net_sown_area}" />
				</c:if>	
				<c:if test="${project.net_sown_area gt project.gross_cropped_area }">
				<c:set var="netsownarea"
					value="${ netsownarea+project.gross_cropped_area}" />
				</c:if>	
					
				<c:set var="grossedcroppedarea"
					value="${ grossedcroppedarea+project.gross_cropped_area}" />
				<c:set var="projectarea"
					value="${ projectarea+project.project_area}" />
			</c:forEach>
			<tr>
			<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total</b></td>
				<td align="right"class="table-primary"><b><c:out value = "${totpltno}"/></b></td>
<!-- 				<th colspan="2" class="text-center">Grand Total</th> -->
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${projectarea}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${totaltreatableprojectarea}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${cultivablewasteland}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${degradedland}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${rainfed}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${forestland}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${others}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${netsownarea}" /></b></td>
				<td class="table-primary" align="right"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${grossedcroppedarea}" /></b></td>
				<td colspan="10" class="table-primary" align="right"><b><fmt:formatNumber
						type="number" maxFractionDigits="2" value="${totalincome/100000}" /></b></td>

			</tr>
		</tbody>
	</table>



</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
<%-- <script src='<c:url value="/resources/js/blsOutcome.js" />'></script> --%>
</body>
</html>