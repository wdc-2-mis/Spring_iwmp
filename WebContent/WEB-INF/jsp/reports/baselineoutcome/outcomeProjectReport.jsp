<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<title>Report O3- Distribution of Gross Cropped Area as per Classification of Land,
Net Sown Area, Gross Cropped Area and Total Income as on date for District</title>

<script type="text/javascript">
	
	function downloadPDF(stateName,distName,projId){
		document.getElementById("stateName").value=stateName;
		document.getElementById("distName").value=distName;
		document.getElementById("projId").value=projId;
		document.rptProjWiseBls.action="downloaProjectWiseOutcomebaselinePDF";
		document.rptProjWiseBls.method="post";
		document.rptProjWiseBls.submit();
	}
	function exportExcel(stateName,distName,projId){
		document.getElementById("stateName").value=stateName;
		document.getElementById("distName").value=distName;
		document.getElementById("projId").value=projId;
		document.rptProjWiseBls.action="downloadExcelProjectReport";
		document.rptProjWiseBls.method="post";
		document.rptProjWiseBls.submit();
	}
	
	</script>

<div class="container-fluid">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"><h5><label id="head">Report O3- Distribution of Gross Cropped Area as per Classification of Land,
Net Sown Area, Gross Cropped Area and Total Income as on date for District  " <c:out value="${distName}"/>"</label></h5></div>
<!-- <div class="col" style="text-align:right;"><p>* All area in ha. <br/>* All amount in lakhs.</p></div> -->
 <hr/>
 
 <form action="downloaProjectWiseOutcomebaselinePDF" method="post" id="getProjWiseBls" name="rptProjWiseBls">
			<div class="form-row">
			<input type="hidden" name="stateName" id="stateName" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
			<input type="hidden" name="projId" id="projId" value="" />
			</div>
		</form>
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stateName}','${distName}','${projId}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF"	onclick="downloadPDF('${stateName}','${distName}','${projId}')" class="btn btn-info">PDF</button>
 
 <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="dtBasicExample" class="table">
  <thead >
  <tr><th>State:- <c:out value="${stateName}"/><br/>District:- <c:out value="${distName}"/></th><th class="text-right" colspan="9"> All area in ha. <br/> All amounts are Rs. in Lakh</th></tr>
   <tr>
    <th rowspan="2" class="text-center">S.No.</th>
      <th rowspan="2" class="text-center">Project</th>
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

<!--      <tr> -->
<!-- 		<td class="text-center">1</td> -->
<!-- 		<td class="text-center">2</td> -->
<!-- 		<td class="text-center">3</td> -->
<!-- 		<td class="text-center">4</td> -->
<!-- 		<td class="text-center">5</td> -->
<!-- 		<td class="text-center">6</td> -->
<!-- 		<td class="text-center">7</td> -->
<!-- 		<td class="text-center">8</td> -->
<!-- 		<td class="text-center">9</td> -->
<!-- 		<td class="text-center">10</td> -->
		
<!-- 	</tr> -->
  </thead>

  <tbody id="assetreportTbody">
  
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
		
	</tr>
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
   <tr><td class="text-center"><c:out value="${sno.count}"/></td><td><a href="outrptdetail?id=<c:out value="${project.projid}"/>"><c:out value="${project.projname}"/></a></td><td class="text-right"><c:out value="${project.treatable_area}"/></td><td class="text-right"><c:out value="${project.cultivable_wasteland}"/></td><td class="text-right"><c:out value="${project.degraded_land}"/></td><td class="text-right"><c:out value="${project.rainfed}"/></td>
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
						<c:if test="${projectList.size()==0}">
						<tr><td colspan="11" class="text-center text-danger">Data not found</td></tr>
						</c:if>
	<tr>
	<td class="table-primary"></td>
	<td  align="right" class="table-primary"><b>Grand Total</b></td>
	
	<td align="right" class="table-primary"><b><fmt:formatNumber type = "number" minFractionDigits = "4" value = "${totaltreatableprojectarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${cultivablewasteland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${degradedland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${rainfed}" /></b></td>
<%--     <td class="text-right"><fmt:formatNumber type = "number"  maxFractionDigits = "3" value = "${forestland}" /></td> --%>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number" minFractionDigits = "4" value = "${others}" /></b></td>
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
<%-- <script src='<c:url value="/resources/js/blsOutcome.js" />'></script> --%>
</body>
</html>