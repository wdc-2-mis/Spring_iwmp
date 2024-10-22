<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	
<head>
<title>Report O4- State wise Comparison of Distribution of Gross Cropped Area as Per Classification of Land, 
Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status</title>
</head>	
<script type="text/javascript">
function downloadPDF()
{
// 	alert('lk');
	if(document.getElementById("state").value==='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	else{
		document.getreports.action="downloadblsPDF";
		document.getreports.method="post";
		document.getreports.submit();
	}
	return false;
} 

function exportExcel1()
{

	if(document.getElementById("state").value==='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	else{
		document.getreports.action="downloadExcelblsoutrpt";
		document.getreports.method="post";
		document.getreports.submit();
	}
	return false;
} 

	
</script>
<div class="container-fluid">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"><h5>Report O4- State wise Comparison of Distribution of Gross Cropped Area as Per Classification of Land, 
Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status</h5></div>
<!-- <div class="col" style="text-align:right;"><p>* All area in ha. <br/>* All amounts are Rs. in Lakh</p></div> -->
 <hr/>
<form action ="blsoutrpt" method="post" name="getreports">
<div class="form-group row col-8">
    <label for="state" class="col-sm-1 col-form-label">State</label>
    <div class="col-sm-3">
     <select id="state" name="state" class="form-control d-inline"><option value="0">--ALL--</option>
     <c:forEach var="list" items="${stateList}">
     <c:if test="${stCode eq list.key }">
     <option value="${list.key }" selected><c:out value="${list.value }"/></option>
     </c:if>
     <option value="${list.key }"><c:out value="${list.value }"/></option>
     </c:forEach>
     </select>
    </div>
    
   <%--   <label for="inputPassword" class="col-sm-1 col-form-label">District</label>
    <div class="col-sm-4">
     <select id="district" name="district" class="form-control d-inline"><option value="0">--ALL--</option>
     <c:forEach var="list" items="${districtList}">
     <c:if test="${distCode eq list.key }">
     <option value="${list.key }" selected><c:out value="${list.value }"/></option>
     </c:if>
     <option value="${list.key }"><c:out value="${list.value }"/></option>
     </c:forEach>
     </select>
    </div> --%>
    
    <div class="col-sm-3">
     <input type="submit" id="getreport" name="getreport" class="btn btn-info" value="Get Data" />
    </div>
  </div>
</form>
<button name="exportExcel" id="exportExcel" onclick="exportExcel1()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="tblReport" class="table">
  <thead >
  <tr><th class="text-right" colspan="17"> All area in ha. <br/> All amounts are Rs. in Lakh</th></tr>
    <tr>
    <th rowspan="3" class="text-center">S.No.</th>
      <th rowspan="3" class="text-center">State</th>
      <th rowspan="3" class="text-center">Plot Area</th>
      <th colspan="8" class="text-center">Classification of Land(Cropped Area)</th>
      
      <th rowspan="2" colspan="2" class="text-center">Net Sown Area</th>
      <th rowspan="2" colspan="2" class="text-center ">Grossed Cropped Area</th>
      <th rowspan="2" colspan="2" class="text-center ">Total Income</th>
    </tr>
<tr>
<th class="text-center" colspan="2">Cultivable Wasteland</th>
      <th  colspan="2" class="text-center">Degraded Land</th>
      <th class="text-center" colspan="2">Rainfed</th>
<!--       <th class="text-center" colspan="2">Forest Land</th> -->
      <th class="text-center" colspan="2">Others</th>
      </tr>
      <tr>
      <th>As on Baseline</th><th>As on date</th><th>As on Baseline</th><th>As on date</th><th>As on Baseline</th><th>As on date</th><th>As on Baseline</th><th>As on date</th><th>As on Baseline</th><th>As on date</th><th>As on Baseline</th><th>As on date</th><th>As on Baseline</th><th>As on date</th>
<!--       <th>As on Baseline</th><th>As on date</th> -->
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
		<th class="text-center">13</th>
		<th class="text-center">14</th>
		<th class="text-center">15</th>
		<th class="text-center">16</th>
		<th class="text-center">17</th>
<!-- 		<th class="text-center">18</th> -->
<!-- 		<th class="text-center">19</th> -->
	</tr>
  </thead>

  <tbody id="assetreportTbody">
  <c:set var="blstotalincome" value="0"/>
   <c:set var="totaltreatableprojectarea" value="0"/>
   <c:set var="blscultivablewasteland" value="0"/>
   <c:set var="blsdegradedland" value="0"/>
   <c:set var="blsrainfed" value="0"/>
<%--    <c:set var="blsforestland" value="0"/> --%>
   <c:set var="blsothers" value="0"/>
   <c:set var="blsnetsownarea" value="0"/>
   <c:set var="blsgrossedcroppedarea" value="0"/>
   
   <c:set var="outtotalincome" value="0"/>
   <c:set var="outcultivablewasteland" value="0"/>
   <c:set var="outdegradedland" value="0"/>
   <c:set var="outrainfed" value="0"/>
<%--    <c:set var="outforestland" value="0"/> --%>
   <c:set var="outothers" value="0"/>
   <c:set var="outnetsownarea" value="0"/>
   <c:set var="outgrossedcroppedarea" value="0"/>
   
   <c:forEach items="${projectList}" var="project" varStatus="sno">
   <tr><td class="text-center"><c:out value="${sno.count}"/></td>
   <td><a href="blsoutrptdist?id=<c:out value="${project.stcode}"/>">
   <c:out value="${project.stname}"/></a></td>
   <td class="text-right"><c:out value="${project.treatable_area}"/></td>
   <td class="text-right"><c:out value="${project.cultivable_wasteland}"/></td>
   <td class="text-right"><c:out value="${project.cultivable_wasteland_achv}"/></td>
   <td class="text-right"><c:out value="${project.degraded_land}"/></td>
   <td class="text-right"><c:out value="${project.degraded_land_achv}"/></td>
   <td class="text-right"><c:out value="${project.rainfed}"/></td>
   <td class="text-right"><c:out value="${project.rainfed_achv}"/></td>
<%--    <td class="text-right"><c:out value="${project.forest_land}"/></td> --%>
<%--    <td class="text-right"><c:out value="${project.forest_land_achv}"/></td> --%>
   <td class="text-right"><c:out value="${project.others}"/></td>
   <td class="text-right"><c:out value="${project.others_achv}"/></td>
   <td class="text-right"><c:out value="${project.net_sown_area}"/></td>
   <td class="text-right"><c:out value="${project.net_sown_area_achv}"/></td> 
   <td class="text-right"><c:out value="${project.gross_cropped_area}"/></td>
   <td class="text-right"><c:out value="${project.gross_cropped_area_achv}"/></td>
   <td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${project.total_income/100000}" /></td>
    <td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${project.total_income_achv/100000}" /></td></tr>
			
			<c:set var="totaltreatableprojectarea" value="${ totaltreatableprojectarea+project.treatable_area}"/>
			
			<c:set var="blstotalincome" value="${blstotalincome+ project.total_income}"/>	
			<c:set var="blscultivablewasteland" value="${ blscultivablewasteland+project.cultivable_wasteland}"/>	
			<c:set var="blsdegradedland" value="${ blsdegradedland+project.degraded_land}"/>
			<c:set var="blsrainfed" value="${ blsrainfed+project.rainfed}"/>	
<%-- 			<c:set var="blsforestland" value="${ blsforestland+project.forest_land}"/> --%>
			<c:set var="blsothers" value="${ blsothers+project.others}"/>	
			<c:set var="blsnetsownarea" value="${ blsnetsownarea+project.net_sown_area}"/>
			<c:set var="blsgrossedcroppedarea" value="${ blsgrossedcroppedarea+project.gross_cropped_area}"/>
			
			<c:set var="outtotalincome" value="${outtotalincome+ project.total_income_achv}"/>	
			<c:set var="outcultivablewasteland" value="${ outcultivablewasteland+project.cultivable_wasteland_achv}"/>	
			<c:set var="outdegradedland" value="${ outdegradedland+project.degraded_land_achv}"/>
			<c:set var="outrainfed" value="${ outrainfed+project.rainfed_achv}"/>	
<%-- 			<c:set var="outforestland" value="${ outforestland+project.forest_land_achv}"/> --%>
			<c:set var="outothers" value="${ outothers+project.others_achv}"/>	
			<c:set var="outnetsownarea" value="${ outnetsownarea+project.net_sown_area_achv}"/>
			<c:set var="outgrossedcroppedarea" value="${ outgrossedcroppedarea+project.gross_cropped_area_achv}"/>
						
						</c:forEach>
	
	<tr>
	<td align="right" class="table-primary"></td>
	<td align="right" class="table-primary"><b>Grand Total </b></td>
	<td align="right" class="table-primary"><b><fmt:formatNumber type = "number" minFractionDigits = "4" value = "${totaltreatableprojectarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blscultivablewasteland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outcultivablewasteland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blsdegradedland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outdegradedland}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blsrainfed}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outrainfed}" /></b></td>
<%--     <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blsforestland}" /></b></td> --%>
<%--     <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outforestland}" /></b></td> --%>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blsothers}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outothers}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blsnetsownarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outnetsownarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${blsgrossedcroppedarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  minFractionDigits = "4" value = "${outgrossedcroppedarea}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${blstotalincome/100000}" /></b></td>
    <td align="right" class="table-primary"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${outtotalincome/100000}" /></b></td>
         
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