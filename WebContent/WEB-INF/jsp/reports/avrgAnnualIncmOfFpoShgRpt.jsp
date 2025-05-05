<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE2- State-wise Mid Term Evaluation of Average Annual Income FPOs, FPO and SHG members</title>

<script type="text/javascript">

function downloadPDF(){
		var radiobutton = document.querySelector('input[name="reportType"]:checked').value;
		document.getElementById("radioBtn").value=radiobutton;
		document.getCrpDtl.action="downloadCroppedDetailsReportPdf";
		document.getCrpDtl.method="post";
		document.getCrpDtl.submit();
}

function exportExcel(){
		var radiobutton = document.querySelector('input[name="reportType"]:checked').value;
		document.getElementById("radioBtn").value=radiobutton;
		document.getCrpDtl.action="downloadExcelCroppedDetailsReport";
		document.getCrpDtl.method="post";
		document.getCrpDtl.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE3- State-wise Mid Term Evaluation of Average Annual Income FPOs, FPO and SHG members</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-1" ></div>
			<div class="col-10">

	<form:form action="getAverageAnnualIncome" name="getAvrgAnnualIncome" id="getAvrgAnnualIncome" method="get">
		
    	<input type="hidden" name="radioBtn" id="radioBtn" value="" />
 	</form:form>
 
<br>
	<div id ="excelpdf" style="display: none;">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="2">S.No.</th>
            <th rowspan="2">State Name</th>
            <th rowspan="2">Total No. of Project</th>
            <th colspan="3" style="text-align: center">Average Annual Income of FPOs</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of FPO Members</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of SHG Members</th>
        </tr>
        <tr>
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
           
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
            
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
        </tr>
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
			
		</tr>
		</thead>
		<tbody id = "avgAnnaulIncomeRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totPreFpoTurnOver" value="0"/>
			<c:set var="totMidFpoTurnOver" value="0"/>
			<c:set var="totControlFpoTurnOver" value="0"/>
			<c:set var="totPreFpoIncome" value="0"/>
			<c:set var="totMidFpoIncome" value="0"/>
			<c:set var="totControlFpoIncome" value="0"/>
			<c:set var="totPreShgAnnaulIncome" value="0"/>
			<c:set var="totMidShgAnnaulIncome" value="0"/>
			<c:set var="totControlShgAnnaulIncome" value="0"/>
			<c:forEach items="${list}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${list.stname}</td>
					<td>${list.totproj}</td>
					<td>${list.prefpoturnover}</td>
					<td>${list.midfpoturnover}</td>
					<td>${list.controlfpoturnover}</td>
					
					<td>${list.prefpoincome}</td>
					<td>${list.midfpoincome}</td>
					<td>${list.controlfpoincome}</td>
					<td>${list.preannualincomeshg}</td>
					<td>${list.midannualincomeshg}</td>
					<td>${list.controlannualincomeshg}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totPreFpoTurnOver" value="${totPreFpoTurnOver + list.prefpoturnover}"/>
			<c:set var="totMidFpoTurnOver" value="${totMidFpoTurnOver + list.midfpoturnover}"/>
			<c:set var="totControlFpoTurnOver" value="${totControlFpoTurnOver + list.controlfpoturnover}"/>
			<c:set var="totPreFpoIncome" value="${totPreFpoIncome + list.prefpoincome}"/>
			<c:set var="totMidFpoIncome" value="${totMidFpoIncome + list.midfpoincome}"/>
			<c:set var="totControlFpoIncome" value="${totControlFpoIncome + list.controlfpoincome}"/>
			<c:set var="totPreShgAnnaulIncome" value="${totPreShgAnnaulIncome + list.preannualincomeshg}"/>
			<c:set var="totMidShgAnnaulIncome" value="${totMidShgAnnaulIncome + list.midannualincomeshg}"/>
			<c:set var="totControlShgAnnaulIncome" value="${totControlShgAnnaulIncome + list.controlannualincomeshg}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2">Total</td>
				<td>${totproj}</td>
				<td>${totPreFpoTurnOver}</td>
				<td>${totMidFpoTurnOver}</td>
				<td>${totControlFpoTurnOver}</td>
				<td>${totPreShgAnnaulIncome}</td>
				<td>${totMidFpoIncome}</td>
				<td>${totControlFpoIncome}</td>
				<td>${totPreShgAnnaulIncome}</td>
				<td>${totMidShgAnnaulIncome}</td>
				<td>${totControlShgAnnaulIncome}</td>
			</tr>
			
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