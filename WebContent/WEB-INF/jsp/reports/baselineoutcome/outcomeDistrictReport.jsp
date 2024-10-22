<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<title>Report O3- Distribution of Gross Cropped Area as per Classification of Land,
Net Sown Area, Gross Cropped Area and Total Income as on date for State</title>


<script type="text/javascript">
	
	function downloadPDF(stateName,stcd)
	{
			document.getElementById("stateName").value=stateName;
			document.getElementById("stcd").value=stcd;
			document.outBls.action="downloaDistbaselineOutcomePDF";
			document.outBls.method="post";
			document.outBls.submit();	
	} 

	function downloadExcel(stateName,stcd)
	{
			document.getElementById("stateName").value=stateName;
			document.getElementById("stcd").value=stcd;
			document.outBls.action="downloadExceloutrptdist";
			document.outBls.method="post";
			document.outBls.submit();	
	}
	</script>

<div class="container-fluid">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"><h5><label id="head">Report O3- Distribution of Gross Cropped Area as per Classification of Land,
Net Sown Area, Gross Cropped Area and Total Income as on date for State  "<c:out value="${stateName}"/>"</label></h5></div>
<!-- <div class="col" style="text-align:right;"><p>* All area in ha. <br/>* All amount in lakhs.</p></div> -->
 <hr/>
 <form action="downloaDistbaselineOutcomePDF" method="post" id="getDistWiseBls" name="outBls">
			<div class="form-row">
			<input type="hidden" name="stateName" id="stateName" value="" />
			<input type="hidden" name="stcd" id="stcd" value="" />
			</div>
		</form>
 <button name="exportExcel" id="exportExcel" onclick="downloadExcel('${stateName}','${stcode}')" class="btn btn-info">Excel</button>
 <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stateName}','${stcode}')" class="btn btn-info">PDF</button>
 <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="dtBasicExample" class="table">
  <thead >
  <tr><th>State:- <c:out value="${stateName}"/></th><th class="text-right" colspan="9"> All area in ha. <br/> All amounts are Rs. in Lakh</th></tr>
   <tr>
    <th rowspan="2" class="text-center">S.No.</th>
      <th rowspan="2" class="text-center">District</th>
      <th rowspan="2" class="text-center">Plot Area</th>
      <th colspan="4" class="text-center">Classification of Grossed Cropped Area</th>
      
      <th rowspan="2" class="text-center">Net Sown Area</th>
      <th rowspan="2" class="text-center ">Grossed Cropped Area</th>
      <th rowspan="2" class="text-center ">Total Income</th>
    </tr>
<tr>
<th class="text-center">Cultivable Wasteland</th>
      <th class="text-center">Degraded Land</th>
      <th class="text-center">Rainfed</th>
<!--       <th class="text-center">Forest Land</th> -->
      <th class="text-center">Others</th>
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
<!-- 		<td class="text-center">11</td> -->
	</tr>
  </thead>

  <tbody id="assetreportTbody">
  <c:set var="totalincome" value="0"/>
   <c:set var="totaltreatableprojectarea" value="0"/>
   <c:set var="cultivablewasteland" value="0"/>
   <c:set var="degradedland" value="0"/>
   <c:set var="rainfed" value="0"/>
   <c:set var="forestland" value="0"/>
   <c:set var="others" value="0"/>
   <c:set var="netsownarea" value="0"/>
   <c:set var="grossedcroppedarea" value="0"/>
   
   <c:forEach items="${projectList}" var="project" varStatus="sno">
   <tr><td class="text-center"><c:out value="${sno.count}"/></td><td><a href="outrptproj?id=<c:out value="${project.dcode}"/>"><c:out value="${project.distname}"/></a></td><td class="text-right"><c:out value="${project.treatable_area}"/></td><td class="text-right"><c:out value="${project.cultivable_wasteland}"/></td><td class="text-right"><c:out value="${project.degraded_land}"/></td><td class="text-right"><c:out value="${project.rainfed}"/></td>
<%--    <td class="text-right"><c:out value="${project.forest_land}"/></td> --%>
   <td class="text-right"><c:out value="${project.others}"/></td><td class="text-right"><c:out value="${project.net_sown_area}"/></td><td class="text-right"><c:out value="${project.gross_cropped_area}"/></td><td class="text-right"><fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${project.total_income/100000}" /></td></tr>
        
         <c:set var="totalincome" value="${totalincome+ project.total_income}"/>	
			<c:set var="totaltreatableprojectarea" value="${ totaltreatableprojectarea+project.treatable_area}"/>
			<c:set var="cultivablewasteland" value="${ cultivablewasteland+project.cultivable_wasteland}"/>	
			<c:set var="degradedland" value="${ degradedland+project.degraded_land}"/>
			<c:set var="rainfed" value="${ rainfed+project.rainfed}"/>	
			<c:set var="forestland" value="${ forestland+project.forest_land}"/>
			<c:set var="others" value="${ others+project.others}"/>	
			<c:set var="netsownarea" value="${ netsownarea+project.net_sown_area}"/>
			<c:set var="grossedcroppedarea" value="${ grossedcroppedarea+project.gross_cropped_area}"/>		
							
						</c:forEach>
	<tr>
	<td class="table-primary">
	<td align="right" class="table-primary"> <b>Grand Total</b></td>
	
	<td align="right" class="table-primary"><b><fmt:formatNumber type = "number" minFractionDigits = "4" value = "${totaltreatableprojectarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${cultivablewasteland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${degradedland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${rainfed}" /></b></td>
<%--     <td class="text-right"><fmt:formatNumber type = "number"  maxFractionDigits = "3" value = "${forestland}" /></td> --%>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${others}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${netsownarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${grossedcroppedarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${totalincome/100000}" /></b></td>
         
         </tr>
  </tbody>
</table>



</div>

<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
<script src='<c:url value="/resources/js/blsOutcome.js" />'></script>
</body>
</html>