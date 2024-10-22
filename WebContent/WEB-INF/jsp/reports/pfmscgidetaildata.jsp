<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>


<title>Report PF6- GOI Release To State Level Implementing Agency</title>



<script type="text/javascript">
function downloadPDF(){
	document.cgiReport.action="downloadcgiDetailPDF";
	document.cgiReport.method="post";
	document.cgiReport.submit();
}

function exportExcel(){
	document.cgiReport.action="downloadExcelcgidetailrpt";
	document.cgiReport.method="post";
	document.cgiReport.submit();
}

</script>
<form action ="downloadcgiDetailPDF" method="post" name="cgiReport"></form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head">Report PF6 - GOI Release To State Level Implementing Agency</label></h5>
	</div>
	
	<div class="col-2" ></div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	<table id="dtBasicExample" cellspacing="0" class="table"    >
  	<thead>
  	<tr>
				<th class="text-right" colspan="7">All
					amounts are Rs.
				</th>
			</tr>
    	<tr>
      		<th  style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 10%;">State Name</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Financial Year</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Sanction No.</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Transaction Id</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Transaction Date</th>
      		<th  style="text-align:center; vertical-align: middle; width: 7%;">Release from Govt. India (Central Share)</th>
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
			<c:forEach items="${pfmscgldata}" var="proj" varStatus="status">
								<tr>
									<td align="center">${status.count}</td>
									<td>${proj.iwmpState.stName} </td>
									 <td align="right">${proj.financialYear}</td>
									<td align="right">${proj.sanctionNo}</td>
									<td align="right">${proj.transactionId}</td>
									<td align="right"><fmt:formatDate value="${proj.transactionDate}"
											pattern="dd/MM/yyyy" /></td>
									<td align="right"><fmt:formatNumber value = "${proj.totalAmount}"
									type="number" minFractionDigits="2"/>
									</td>
									</tr>
									<c:set var="totaltotalAmount"
											value="${totaltotalAmount+proj.totalAmount}" />
									</c:forEach>
									<tr>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td class="table-primary"></td>
									<td align="right" class="table-primary"><b>Grand Total </b></td>
									<td align="right" class="table-primary"><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value="${totaltotalAmount}" /></fmt:formatNumber></b></td>
									</tr>
      		</table>
      		</div>
   <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>   		