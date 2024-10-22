<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<script type="text/javascript">
	function exportExcel(stCode){
		document.getElementById("stCode").value=stCode;
		document.rptStateWiseBls.action="downloadExcelStateWiseBaseline";
		document.rptStateWiseBls.method="post";
		document.rptStateWiseBls.submit();
	}
	function downloadPDF(stCode)
	{
			document.getElementById("stCode").value=stCode;
			document.rptStateWiseBls.action="downloadbaselinePDF";
			document.rptStateWiseBls.method="post";
			document.rptStateWiseBls.submit();
		
	} 
	function getStwiseData(){
		var stcd = $('#state').val();
		document.getElementById("stCode").value=stcd;
		document.rptStateWiseBls.action="blsrpt";
		document.rptStateWiseBls.method="post";
		document.rptStateWiseBls.submit();
	}
	</script>
<title>Report SB1- State wise Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey</title>

<!-- <form action="downloadbaselinePDF" method="post" name="getblsrpt"></form> -->
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report SB1- State wise Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey </label></h5>
	</div>
	<!-- <div class="col" style="text-align:right;"><p>* All area in ha. <br/>* All amounts are Rs. in Lakh</p></div> -->
	<hr />
	<form action="blsrpt" method="post" id="getStateWiseBls" name="rptStateWiseBls">
		<div class="form-group row col-8">
		<input type="hidden" name="stCode" id="stCode" value="" />
			<label for="state" class="col-sm-1 col-form-label">State</label>
			<div class="col-sm-3">
				<select id="state" name="state" class="form-control d-inline"><option
						value="0">--ALL--</option>
					<c:forEach var="list" items="${stateList}">
						<c:if test="${stCode eq list.key }">
							<option value="${list.key }" selected><c:out
									value="${list.value }" /></option>
						</c:if>
						<option value="${list.key }"><c:out
								value="${list.value }" /></option>
					</c:forEach>
				</select>
			</div>



			<div class="col-sm-3">
				<input type="submit" id="getreport" name="getreport"
					class="btn btn-info" onclick ="getStwiseData()" value="Get Data" />
			</div>
		</div>
	</form>

	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}')" class="btn btn-info">Excel</button>

	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}')" class="btn btn-info">PDF</button>

	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<!-- 	<input type="text" id="projectArea" name="projectArea"  class="form-control" onfocusin="decimalCheck(e)" /> -->
	<div class="form-group  col-md-12">
		<div class="message">
			<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong>
				Total Income is the Total Value of Output.</label>
		</div>
	</div>
	<table id="dtBasicExample" class="table" >
		<thead>
			<tr>
				<th class="text-right" colspan="13">All area in ha. <br /> All
					amounts are Rs. in Lakh
				</th>
			</tr>
			<tr>
				<th rowspan="2" class="text-center">S.No.</th>
				<th rowspan="2" class="text-center">State</th>
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
				<th class="text-right">1</th>
				<th class="text-right">2</th>
				<th class="text-right">3</th>
				<th class="text-right">4</th>
				<th class="text-right">5</th>
				<th class="text-right">6</th>
				<th class="text-right">7</th>
				<th class="text-right">8</th>
				<th class="text-right">9</th>
				<th class="text-right">10</th>
				<th class="text-right">11</th>
				<th class="text-right">12</th>
				<th class="text-right">13</th>
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
					<td class="text-center"><fmt:formatNumber 
						maxFractionDigits="2" ><c:out value="${sno.count}" /></fmt:formatNumber></td>
					<td><a href="blsrptdist?id=<c:out value="${project.stcode}"/>"><c:out
								value="${project.stname}" /></a></td>
					<td class="text-right"><c:out value = "${project.total_plot}"/></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.project_area}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out
							value="${project.treatable_area}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out
							value="${project.cultivable_wasteland}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.degraded_land}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.rainfed}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.forest_land}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.others}" /></fmt:formatNumber></td>
					<c:if test="${project.net_sown_area le project.gross_cropped_area }">
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.net_sown_area}" /></fmt:formatNumber></td>
					</c:if>
					<c:if test="${project.net_sown_area gt project.gross_cropped_area }">
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.gross_cropped_area}" /></fmt:formatNumber></td>
					</c:if>
					<td class="text-right"><fmt:formatNumber 
						minFractionDigits="4" ><c:out value="${project.gross_cropped_area}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber 
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
				
				<c:set var="projectarea"
					value="${ projectarea+project.project_area}" />
				<c:set var="grossedcroppedarea"
					value="${ grossedcroppedarea+project.gross_cropped_area}" />
			</c:forEach>
			<tr>
			<td class="table-primary"></td>
			<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right"class="table-primary"><b><c:out value = "${totpltno}"/></b></td>
				<td align="right"class="table-primary"><b><fmt:formatNumber type="number"
						minFractionDigits="4" value="${projectarea}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${totaltreatableprojectarea}" /></b></td>
				<td align="right"class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${cultivablewasteland}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${degradedland}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${rainfed}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${forestland}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${others}" /></b></td>
				<td align="right"  class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${netsownarea}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber type="number"
						maxFractionDigits="4" value="${grossedcroppedarea}" /></b></td>
				<td align="right" colspan="10" class="table-primary"><b><fmt:formatNumber
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