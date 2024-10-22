<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)</title>

</head>
<script type= "text/javascript">
function exportExcel(){
	document.outcome.action="getPMKSYDetailsExcel";
	document.outcome.method="post";
	document.outcome.submit();
}
function downloadPDF(){
	document.outcome.action="getPMKSYDetailsPDF";
	document.outcome.method="post";
	document.outcome.submit();
}
</script>

<body>
<br>
<form id="outcome" name="outcome">
<!-- 			<input type="hidden" name="finyear" id="finyear" value="" /> -->
<!-- 			<input type="hidden" name="finYrName" id="finYrName" value="" /> -->
		</form>

<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O12 - Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)</h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8" >
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" class="table" >
  	<thead>
    	<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">Components</th>
      		<th colspan="${yrMapSize}" style="text-align:center; vertical-align: middle; width: 7%;">WDC 2.0 </th>
       	</tr>
       	<tr>
       		<c:forEach var="yr" items="${yrMap}">
      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value="${yr.value}"/></th>
      		</c:forEach>
       	</tr>
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var ="count" value ="1"/>
			<c:forEach var="yr" items="${yrMap}">
      			<th class="text-center"><c:out value="${count +2}"/></th>
      			<c:set var ="count" value ="${count +1}"/>
      		</c:forEach>
		</tr>
				<tr>
					<td>1</td>
					<td><a href="getStateWiseWtrStrDetails">No. of Water Harvesting Structures Constructed/Revived(No.)</a></td>
					<c:forEach var="wtr" items="${wtrMap}">
      					<td style="text-align:center;"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${wtr.value}"/></td>
      				</c:forEach>
				</tr>
				<tr>
					<td>2</td>
					<td><a href="getStateWiseIrriDetails">Additional Area Brought Under Irrigation(Ha.)</a></td>
					<c:forEach var="irri" items="${irriMap}">
      					<td style="text-align:center;"><c:out value="${irri.value}"/></td>
      				</c:forEach>
				</tr>
				<tr>
					<td>3</td>
					<td><a href="getStateWisePMKSYDetails">Benefited Farmers(No.)</a></td>
					<c:forEach var="frmr" items="${frmrMap}">
      					<td style="text-align:center;"><c:out value="${frmr.value}"/></td>
      				</c:forEach>
				</tr>
	 	
	</tbody>
  </table>
</div>
</div>
</div>


<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>