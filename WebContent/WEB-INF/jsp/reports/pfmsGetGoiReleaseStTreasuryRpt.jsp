<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/report.css" />">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src='<c:url value="/resources/js/jquery-3.5.1.js" />'></script>
<script
	src='<c:url value="/resources/js/1.12.1jquery.dataTables.min.js" />'></script>
<script
	src='<c:url value="/resources/js/2.2.3dataTables.buttons.min.js" />'></script>
<script src='<c:url value="/resources/js/jszip.min.js" />'></script>
<script src='<c:url value="/resources/js/pdfmake.min.js" />'></script>
<script src='<c:url value="/resources/js/1.53vfs_fonts.js" />'></script>
<script
	src='<c:url value="/resources/js/export/buttons.html5.min.js" />'></script>
<%-- <script src='<c:url value="/resources/js/buttons.print.min.js" />'></script> --%>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/bootstrap/jquery.dataTables.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/2.2.3buttons.dataTables.min.css" />" />

<title>Report PF1 - Central Share Release Data to Various State Treasury</title>


</head>


<script type="text/javascript">

function ShowContent(d) 
{
	var dd = document.getElementById(d);
	var width = dd.style.width;
	var index = width.indexOf("px");
	document.getElementById("waitDiv").style.display = "";
	width = width.substring(0,index);
	dd.style.left = ((document.body.clientWidth-width)/2) + "px";
	dd.style.display = "";
}

function downloadPDF(fyear){
	document.getElementById("fyear").value=fyear;
	document.goiReport.action="DownloadGoiReleaseToStateTreasuryPDF";
	document.goiReport.method="post";
	document.goiReport.submit();
}

function exportExcel(fyear){
	document.getElementById("fyear").value=fyear;
	document.goiReport.action="downloadExcelGoiReleaseToStateTreasury";
	document.goiReport.method="post";
	document.goiReport.submit();
}

</script>

<form action ="DownloadGoiReleaseToStateTreasuryPDF" method="post" name="goiReport">
<div class="form-row">
			<input type="hidden" name="fyear" id="fyear" value="" />
			</div>
</form>

<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
	<c:if test="${fyear eq 0}" >
		<h5>Report PF1 - Central Share Release Data to Various State Treasury For All Financial Year</h5></c:if>
		<c:if test="${fyear gt 0}" >
		<h5>Report PF1 - Central Share Release Data to Various State Treasury For Financial Year   '<c:out value="20${fyear}" />-<c:out value="20${fyear+1}" />'</h5></c:if>
	
	</div>
	
	<div class="col-2" ></div>
	<c:if test="${pfmsGoiReleaseDataSize>0}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${fyear}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${fyear}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	</c:if>
	<form action="getGoiReleaseToStateTreasury" method="get">
		<div class="form-group row col-8">
			<label for="state" class="col-sm-3 col-form-label"><b>Financial Year</b></label>
			<div class="col-sm-3">
				<select id="fyear" name="fyear" class="form-control d-inline" onchange="this.form.submit();">
				<option value="">--All--</option>
					<c:forEach var="list" items="${yearList}">
						<c:if test="${fyear eq list.key }">
							<option value="${list.key }" selected><c:out value="${list.value }" /></option>
							<c:set var ="finYrName" value ="${list.value }"/>
						</c:if>
						<c:if test="${fyear ne list.key }">
							<option value="${list.key }"><c:out value="${list.value }" /></option>
						</c:if>
					</c:forEach>
				</select>
			</div>

		</div>
	</form>
	<table id="dtBasicExample" cellspacing="0" class="table"    >
	
  	<thead>
    	<tr>
      		<th  style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 10%;">State Name</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Financial Year</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Sanction No.</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Transaction Id</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Transaction Date</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Release from Govt. India (Central Share)<br>(in Rs.)</th>
      		</tr>
      		</thead>
      		
      		<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
			<th class="text-center">7</th>
			</tr>
			<c:set var = "checkStName" value = "" />
			<c:forEach items="${pfmsGoiReleaseData}" var="proj" varStatus="status">
			
								<c:if test ="${checkStName ne proj.getIwmpStateByStCode().stName && status.count ne 1}">
									<tr>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td align="right" class="table-primary"><b>State Total </b></td>
										<td align="right" class="table-primary"><b>
										<fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totalAmnt}" /></fmt:formatNumber></b></td>
									</tr>
									<c:set var="totalAmnt" value="0" />
									<c:set var="totalAmnt" value="${totalAmnt + proj.sanctionAmount}" />
								</c:if>
								<tr>
									<td align="center">${status.count}</td>
									<td>${proj.getIwmpStateByStCode().stName} </td>
									 <td align="right">${proj.financialyear}</td>
									<td align="right">${proj.sanctionNumber}</td>
									<td align="right">${proj.transaction_id}</td>
									<td align="right"><fmt:formatDate value="${proj.transactionDate}"
											pattern="dd/MM/yyyy" /></td>
									<td align="right"><fmt:formatNumber value = "${proj.sanctionAmount}"
									type="number" minFractionDigits="2"/>
									</td>
									</tr>
									<c:if test = "${checkStName eq proj.getIwmpStateByStCode().stName || status.count eq 1}">
										<c:set var="totalAmnt" value="${totalAmnt + proj.sanctionAmount}" />
									</c:if>
									<c:if test = "${ status.count eq fn:length(pfmsGoiReleaseData)}">
									<tr>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td class="table-primary"></td>
										<td align="right" class="table-primary"><b>State Total </b></td>
										<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totalAmnt}" /></fmt:formatNumber></b></td>
									</tr>
									
									</c:if>
									
									
									<c:set var = "checkStName" value = "${proj.getIwmpStateByStCode().stName}" />
									<c:set var="totaltotalAmount"
											value="${totaltotalAmount+proj.sanctionAmount}" />
									</c:forEach>
				<c:if test="${pfmsGoiReleaseDataSize>0}">
									<tr>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									
									<td align="right" class="table-primary"><b>Grand Total </b></td>
									<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totaltotalAmount}" /></fmt:formatNumber></b></td>
									</tr>
				</c:if>
									 <c:if test="${pfmsGoiReleaseDataSize==0}">
			<tr>
				<td align="center" colspan="20" class="required">Data Not Found</td>
			</tr>
		</c:if>
      		</table>
      		</div>
   <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>   		