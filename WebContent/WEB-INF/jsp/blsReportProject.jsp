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
<h5>Detail of Baseline Survey of a Project of WDC-PMKSY 2.0</h5>

</div>
</div>

<div class="col">
<div class="col-12 text-center">
<br/>
<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="exportPDFProject()" class="btn btn-info">PDF</button>
<p class="d-inline" style="float: right;">* All area in hactare</p>

<table  class="w-100" id="tblReport">
<thead>
<tr>
<th colspan="20" data-tableexport-display="always">State: <c:out value="${state }" /> &nbsp;&nbsp;&nbsp;&nbsp;District: <c:out value="${district }" /> &nbsp;&nbsp;&nbsp;&nbsp;Project: <c:out value="${project }" /></th>
</tr>
<tr>
<td colspan="20"  data-tableexport-display="always" class="text-center">Basic Details</td>
</tr>
<tr>
<td colspan="7">Sanctioned Area</td>
<td colspan="7" >Total Geographical Area</td>
<td  colspan="7">Project Area Covering</td>
</tr>
</thead>
<tbody>
<tr>
<td colspan="7" ><c:out value="${sanctionedarea }" /></td><td  colspan="7"><c:out value="${geoarea }" /></td><td  colspan="7"><c:out value="${projectareacovering }" /></td></tr>
 <tr>
<td  colspan="20" class="text-center">Treatable Area</td>
</tr>
<tr>
<td  colspan="7">Total area of Degraded Land/Rainfed Area</td>
<td  colspan="7">Total area of Wasteland</td>
<td  colspan="7">Total area of Forest Wasteland</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'treatablearea' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="7" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td colspan="20" class="text-center">Total Existing Area covered under diversified crops/change in cropping pattern (in ha)</td>
</tr>
<tr>
<td colspan="4" >Covered Area of Cerals</td>
<td  colspan="4">Covered Area of Pulses</td>
<td  colspan="4">Covered Area of Oilseeds</td>
<td colspan="4" >Covered Area of Fibre crops</td>
<td  colspan="4">Covered Area of Others</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'areacoveredunderdiversifiedcrops' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="4" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td  colspan="20"class="text-center">Total Existing Area brought from nil/single crop to double or more crop (in ha)</td>
</tr>
<tr>
<td colspan="4" >Area brought from Nil to 1 crop</td>
<td colspan="4" >Area brought from 1 to double crop</td>
<td  colspan="4">Area brought from double to multi crop</td>
<td  colspan="4">Gross cropped Area</td>
<td  colspan="4">Total existing Net Sown area</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'areabroughtfromnil' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="4" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<!-- <tr>
<td colspan="20" class="text-center">Total Existing Area brought under Afforestation/ Agriculture/ Pasture etc.</td>
</tr>
<tr>
<td  colspan="4">Area of Afforestation</td>
<td colspan="4" >Area of Total Additional Agriculture</td>
<td  colspan="4">Area of Total Pasture</td>
<td  colspan="4">Area of Medical Plants</td>
<td  colspan="4">Area of Others</td>
</tr> -->
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'areabroughtunderafforestation' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="4" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td colspan="20" class="text-center">Total Existing Area brought under Afforestation/ Agriculture/ Pasture etc.</td>
</tr>
<tr>
<td colspan="3" >Area of Moring</td>
<td colspan="3" >Area of Amla</td>
<td  colspan="3">Area of Cahsew</td>
<td  colspan="3">Area of Pine Apple</td>
<td  colspan="3">Area of Olericulture</td>
<td  colspan="3">Area of Floriculture</td>
<td colspan="3" >Area of Others</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'areabroughtunderhorticulture' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="3" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td colspan="20" class="text-center">Total Existing Water Harvesting Structure</td>
</tr>
<tr>
<td colspan="3" >No. of Farm Ponds</td>
<td  colspan="3">No. of Check dams</td>
<td  colspan="3">No. of Nallah Buns</td>
<td  colspan="3">No. of Percolation tanks</td>
<td  colspan="3">No. of Ground Recharge Structure</td>
<td  colspan="3">No. of Gully Plugs</td>
<td  colspan="3">No. of Others</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'waterharvestingstructure' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td  colspan="3"><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td colspan="20" class="text-center">Total Existing Area brought under protective irrigation</td>
</tr>
<tr>
<td colspan="5" >Area of Farms Ponds</td>
<td colspan="5" >Area of Check Dams</td>
<td  colspan="5">Area of Nallah Bunds</td>
<td  colspan="5">Area of Others</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'areabroughtunderprotectiveirrigation' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="5" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td colspan="20" class="text-center">Total Existing Area covered with soil and moisture conservation activities</td>
</tr>
<tr>
<td colspan="4" >Area of Staggered Trenching</td>
<td colspan="4" >Area of Contour Bunding</td>
<td colspan="4" >Area of Graded Bunding</td>
<td colspan="4" >Area of Bench Terracing</td>
<td colspan="4" >Area of Others</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'soilandmoistureconservation' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="4" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
<tr>
<td colspan="20" class="text-center">No. of Household</td>
</tr>
<tr>
<td colspan="2" >No. of SC</td>
<td colspan="2" >No. of ST</td>
<td colspan="2" >No. of Others</td>

<td colspan="2" >No. of Household of landless people</td>
<td colspan="2" >No. of BPL Household</td>
<td colspan="2" >No. of Small Farmers`s Household</td>
<td colspan="2" >No. of Marginal Farmers`s Household</td>
<td colspan="2">No. of Peson-Days of Seasonal Migration</td>
</tr>
<tr>
<c:forEach var="list" items="${map}">
<c:if test="${list.key eq 'noofhousehold' }">
 <c:forEach var="detailList" items="${list.value}"> 
<td colspan="2" ><c:out value="${detailList }" /></td>
</c:forEach>
</c:if>
</c:forEach>
</tr>
</tbody>
</table>
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