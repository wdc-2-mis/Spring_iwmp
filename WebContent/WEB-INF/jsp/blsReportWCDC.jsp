<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />

<div class="container-fluid">
<div class="row">
<div class="offset-md-3 col-6 text-center">
<div class="formheading">
<h5>Detail of Baseline Survey District wise of WDC-PMKSY 2.0</h5>

</div>
</div>

<div class="col">
<div class="col-12">
<button name="exportExcel" id="exportExcel" onclick="exportExcelWCDC()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="exportPDFWCDC()" class="btn btn-info">PDF</button>
<p class="d-inline" style="float: right;">* All area in hactare</p>
<table style="width:100%" id="tblReport">
<thead>
<tr>
<th colspan="14">State : <c:out value="${state}" /></th>
</tr>
<tr>
<th>District:</th>
<th>Project:</th>
<th>Sanctioned Area</th>
<th>Total Geographical Area</th>
<th>Project Area Covering Type</th>
<th>Total Treatable Area</th>
<th>Total Existing Area covered under diversified crops/change in cropping pattern (in ha)</th>
<th>Total Existing Area brought from nil/single crop to double or more crop (in ha)</th>
<th>Total Existing Area brought under Afforestation/ Agriculture/ Pasture etc.</th>
<th>Total Existing Area brought under Horticulture</th>
<th>Total Existing Water Harvesting Structure</th>
<th>Total Existing Area brought under protective irrigation</th>
<th>Total Existing Area covered with soil and moisture conservation activities</th>
<th>Total No. of Household</th>
</tr>
</thead>
<tbody>
<tr class="text-center">
<td>1</td>
<td>2</td>
<td>3</td>
<td>4</td>
<td>5</td>
<td>6</td>
<td>7</td>
<td>8</td>
<td>9</td>
<td>10</td>
<td>11</td>
<td>12</td>
<td>13</td>
<td>14</td>
</tr>

<c:forEach var="list" items="${finalList}">

 <c:forEach var="detailList" items="${list.value}"> 
<tr>

<c:choose> 
  	<c:when test="${distname ne detailList.dist_name }">
  	<td><c:out value="${detailList.dist_name}" /> </td>
  	<c:set var="distname" value="${detailList.dist_name}" />
  	</c:when>
 	<c:otherwise>
    <td> </td>
  	</c:otherwise>
</c:choose>
<td><a href="#" data-id="${detailList.proj_id},${detailList.dist_name},${state}" class="project"><c:out value="${detailList.proj_name}" /> </a></td>
<td><c:out value="${detailList.sanctioned_area}" /> </td>
<td><c:out value="${detailList.total_geo_area}" /> </td>
<td><c:out value="${detailList.area_covering_type}" /> </td>
<td> <c:out value="${detailList.treatable_area}" /></td>
<td> <c:out value="${detailList.area_covered_under_diversified_crops}" /></td>
<td> <c:out value="${detailList.area_brought_from_nil_single}" /></td>
<td> <c:out value="${detailList.area_brought_under_afforestation}" /></td>
<td> <c:out value="${detailList.area_brought_under_horticulture}" /></td>

<td> <c:out value="${detailList.water_harvesting_structure}" /></td>
<td> <c:out value="${detailList.area_brought_under_protective_irrigation}" /></td>
<td> <c:out value="${detailList.area_covered_with_soil_and_moisture_conservation}" /></td>
<td> <c:out value="${detailList.no_of_household}" /></td>
</tr>
 </c:forEach> 

</c:forEach>
</tbody>
</table>
<br/>
</div>


</div>
</div>

</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
<script src='<c:url value="/resources/js/baselinesurvey.js" />'></script>
</body>
</html>