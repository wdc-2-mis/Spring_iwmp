<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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

<title>Report-O4(ProjectDetail)</title>

<%-- <script src='<c:url value="/resources/js/bootstrapWithExport.js" />'></script> --%>

</head>
<title>
Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, 
Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for Project
</title>
</head>
<script type="text/javascript">
function downloadPDF(id, stateName, distName, projName){
		document.getElementById("id").value=id;
		document.getElementById("stateName").value=stateName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.delcompBase.action="downloadPDFblsoutrptdetail";
		document.delcompBase.method="post";
		document.delcompBase.submit();
		}
		
function exportExcel(id, stateName, distName, projName){
	document.getElementById("id").value=id;
	document.getElementById("stateName").value=stateName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.delcompBase.action="downloadExcelblsoutrptdetail";
	document.delcompBase.method="post";
	document.delcompBase.submit();
	}

</script>

<div class="container-fluid">
<div class="offset-md-3 col-6 formheading" style="text-align:center;">
<h5><label id="head1">Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, 
Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for Project "<c:out value="${projName}"/>"</label></h5></div>
 <hr/>
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${id}','${stateName}','${distName}','${projName}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${id}','${stateName}','${distName}','${projName}')" class="btn btn-info">PDF</button>
  <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
  <form name="delcompBase" id="delcompBase">
     <input type="hidden" name="id" id="id" value="" />
     <input type="hidden" name="stateName" id="stateName" value="" />
    <input type="hidden" name="distName" id="distName" value="" />
     <input type="hidden" name="projName" id="projName" value="" />
     
      
    
     <div class="form-row">
     <div class="form-group ">
    <!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->
   <c:set var="totalincome" value="0"/>
   <c:set var="totaltreatableprojectarea" value="0"/>
   <c:set var="cultivablewasteland" value="0"/>
   <c:set var="degradedland" value="0"/>
   <c:set var="rainfed" value="0"/>
<%--    <c:set var="forestland" value="0"/> --%>
   <c:set var="others" value="0"/>
   <c:set var="netsownarea" value="0"/>
   <c:set var="grossedcroppedarea" value="0"/>
   
     <table class="tblAddPhysicalAchievement" id="pdfBasicExample" name="tblReport" style="width:100%">
     	<thead>
     	<tr><th colspan="4">State:- <c:out value="${stateName}"/><br/> District:- <c:out value="${distName}"/><br/> Project:- <c:out value="${projName}"/></th><th class="text-right" colspan="12"> All area in ha. <br/> All amounts are in Rs.</th></tr>
     	<tr>
     		<th class="text-center">Block </th>
     		<th class="text-center">Gram Panchayat </th>
     		<th class="text-center">Village </th>
     		<th class="text-center">Plot/Gata No.</th>
     		<th class="text-center">Plot Area</th>
     		<th class="text-center">Irrigation Status</th>
     		<th class="text-center">Ownership</th>
     		<th class="text-center">Owner Name</th>
     		<th class="text-center">Classification of Land</th>
     		<th class="text-center">No. of Crop</th>
<!--      		<th class="text-center">Forest Land type</th> -->
     		<th class="text-center">Season</th>
     		<th class="text-center">Crop Type</th>
     		<th class="text-center">Area (Col-A)</th>
     		<th class="text-center">Crop Production per Hectare (in Quintal)  <br/>(Col-B)</th>
     		<th class="text-center">Avg. Income per Quintal <br/>(Col-C)</th>
     		<th class="text-center">Total Income (A*B*C)</th>
     	</tr>
     	</thead>
     	<tbody>
     	<c:set var="block" value="" />
     	<c:set var="grampanchayat" value="" />
     	<c:set var="village" value="" />
     	<c:set var="plot" value="" />
     	<c:set var="gtoftarea" value="0" />
     	<c:set var="gtofcroparea" value="0" />
     	<c:set var="gtofcropproduction" value="0" />
     	<c:set var="gtofavgincome" value="0" />
     	<c:set var="gtoftotalincome" value="0" />
     	
     	<c:set var="block_achv" value="" />
     	<c:set var="grampanchayat_achv" value="" />
     	<c:set var="village_achv" value="" />
     	<c:set var="plot_achv" value="" />
     	<c:set var="gtoftarea_achv" value="0" />
     	<c:set var="gtofcroparea_achv" value="0" />
     	<c:set var="gtofcropproduction_achv" value="0" />
     	<c:set var="gtofavgincome_achv" value="0" />
     	<c:set var="gtoftotalincome_achv" value="0" />
     	
     	 <c:forEach items="${valueList}" var="value1" >
     	<c:set var="achvVill" value="" />
     	<c:set var="tparea" value="" />
     	<c:set var="irri" value="" />
     	<c:set var="ownership" value="" />
     	<c:set var="ownername" value="" />
     	<c:set var="classland" value="" />
     	<c:set var="nocrop" value="" />
<%--      	<c:set var="forestlandtype" value="" /> --%>
     	<c:set var="seasonval" value="" />
     	
     	<c:set var="sumofcroparea" value="0" />
     	<c:set var="sumofcropproduction" value="0" />
     	<c:set var="sumofavgincome" value="0" />
     	<c:set var="sumoftotalincome" value="0" />
     	
     	<!-- --------- Achievement Fields ----------- -->
     	<c:set var="achvVill_achv" value="" />
     	<c:set var="tparea_achv" value="" />
     	<c:set var="irri_achv" value="" />
     	<c:set var="ownership_achv" value="" />
     	<c:set var="ownername_achv" value="" />
     	<c:set var="classland_achv" value="" />
     	<c:set var="nocrop_achv" value="" />
<%--      	<c:set var="forestlandtype_achv" value="" /> --%>
     	<c:set var="seasonval_achv" value="" />
     	
     	<c:set var="sumofcroparea_achv" value="0" />
     	<c:set var="sumofcropproduction_achv" value="0" />
     	<c:set var="sumofavgincome_achv" value="0" />
     	<c:set var="sumoftotalincome_achv" value="0" />
     	<c:set var="blsVill" value="" />
     	
     	
     	<c:forEach items="${value1.value}" var="v1" varStatus="count1">
     	<%-- <c:forEach items="${val}" var="v1" varStatus="count1">
     	<tr><td><c:out value="${v1.plot_no}" /></td><td><c:out value="${v1.plot_no_achv}" /></td></tr>
     	</c:forEach> --%>
     	
     	
     	
     	 <%-- <c:forEach items="${val}" var="v1" varStatus="count1"> --%>
     	<c:if test="${v1.plot_no_achv eq null && achvVill eq ''}"> 
     	
     	<tr>
     	 <c:choose>
			<c:when test="${block ne v1.block_name}">
				<c:set var="block" value="${v1.block_name}" />
				<td> <c:out value="${v1.block_name}" /></td>
			</c:when>	
			<c:otherwise>
<!-- 				<td></td> -->
			</c:otherwise>
		</c:choose> 
		 <c:choose>
			<c:when test="${grampanchayat ne v1.gram_panchayat_name}">
				<c:set var="grampanchayat" value="${v1.gram_panchayat_name}" />
				<td> <c:out value="${v1.gram_panchayat_name}" /></td>
			</c:when>	
			<c:otherwise>
<!-- 				<td></td> -->
			</c:otherwise>
		</c:choose> 
     	<c:choose>
			<c:when test="${village ne v1.village_name}">
				<c:set var="village" value="${v1.village_name}" />
				<td> <c:out value="${v1.village_name}" /></td>
			</c:when>	
			<c:otherwise>
<!-- 				<td></td> -->
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${plot ne v1.plot_no}">
				<c:set var="plot" value="${v1.plot_no}" />
				<td> <c:out value="${v1.plot_no}" /></td>
			</c:when>	
			<c:otherwise>
<!-- 				<td></td> -->
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${tparea ne v1.area}">
			<c:set var="gtoftarea" value="${gtoftarea+v1.area}" />
				<c:set var="tparea" value="${v1.area}" />
				<td class="text-right"> <c:out value="${v1.area}" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</c:when>	
			<c:otherwise>
<!-- 				<td></td> -->
			</c:otherwise>
		</c:choose> 
     	
     	</tr>
     	<c:if test="${v1.plot_no_achv eq null && blsVill eq ''}">
     	<c:set var="block_achv" value="" />
     	<c:set var="grampanchayat_achv" value="" />
     	<c:set var="village_achv" value="" />
     	<c:set var="plot_achv" value="" />
     	<c:set var="blsVill" value="${v1.plot_no}" />
     	<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td class="text-center" style="background:#fff;font-weight:bold">As on Baseline</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
     	</c:if>
     	<tr>
     	<td></td><td></td><td></td><td></td><td></td>
     	<c:choose>
			<c:when test="${irri ne v1.irrigation}">
				<c:set var="irri" value="${v1.irrigation}" />
				<td> <c:out value="${v1.irrigation}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownership ne v1.ownership}">
				<c:set var="ownership" value="${v1.ownership}" />
				<td> <c:out value="${v1.ownership}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownername ne v1.owner_name}">
				<c:set var="ownername" value="${v1.owner_name}" />
				<td> <c:out value="${v1.owner_name}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${classland ne v1.classification_land}">
				<c:set var="classland" value="${v1.classification_land}" />
				<td> <c:out value="${v1.classification_land}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${nocrop ne v1.no_crop}">
				<c:set var="nocrop" value="${v1.no_crop}" />
				<td> <c:out value="${v1.no_crop}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${forestlandtype ne v1.forest_land}"> --%>
<%-- 				<c:set var="forestlandtype" value="${v1.forest_land}" /> --%>
<%-- 				<td> <c:out value="${v1.forest_land}" /></td> --%>
<%-- 			</c:when>	 --%>
<%-- 			<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
		
		 <c:choose>
			<c:when test="${seasonval ne v1.season}">
				<c:set var="seasonval" value="${v1.season}" />
				<td> <c:out value="${v1.season}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	<td class="text-right"><c:out value="${v1.crop_type}" /></td>
     	
     	<c:set var="sumofcroparea" value="${sumofcroparea+v1.crop_area}" />
     	<c:set var="gtofcroparea" value="${gtofcroparea+v1.crop_area}" />
     	<td class="text-right"><c:out value="${v1.crop_area}" /></td>
     	<c:set var="sumofcropproduction" value="${sumofcropproduction+v1.crop_production}" />
     	<c:set var="gtofcropproduction" value="${gtofcropproduction+v1.crop_production}" />
     	<td class="text-right"><c:out value="${v1.crop_production}" /></td>
     	<c:set var="sumofavgincome" value="${sumofavgincome+v1.avg_income}" />
     	<c:set var="gtofavgincome" value="${gtofavgincome+v1.avg_income}" />
     	<td class="text-right"><c:out value="${v1.avg_income}" /></td>
     	<c:set var="sumoftotalincome" value="${sumoftotalincome+(v1.crop_area*v1.crop_production*v1.avg_income) }" />
     	<c:set var="gtoftotalincome" value="${gtoftotalincome+(v1.crop_area*v1.crop_production*v1.avg_income) }" />
     	<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${v1.crop_area*v1.crop_production*v1.avg_income}" /></td>
     	
     	</tr>
     	</c:if>
     	
     	<!-- -------------OUTCOME DATA ------------------- -->
     	<c:if test="${v1.plot_no_achv ne null && achvVill eq ''}"> 
     	<c:set var="block" value="" />
     	<c:set var="grampanchayat" value="" />
     	<c:set var="village" value="" />
     	<c:set var="plot" value="" />
     	<c:set var="achvVill" value="${v1.plot_no_achv}" />
     	<tr style="color: #001fff !important; font-weight:bold"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>Total</td><td class="text-right"><c:out value="${sumofcroparea}" /></td>
     	 <td class="text-right"><c:out value="${sumofcropproduction}" /></td>
     	 <td class="text-right"><c:out value="${sumofavgincome}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${sumoftotalincome}" /></td></tr>
     	<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td class="text-center" style="background:#fff;font-weight:bold">As on Date</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
     	</c:if>
     
     	 <c:if test="${v1.plot_no_achv ne null }"> 
     	<c:set var="achvVill" value="${v1.plot_no_achv}" />
     	<tr>
     	 <c:choose>
			<c:when test="${block_achv ne v1.block_name_achv}">
				<c:set var="block_achv" value="${v1.block_name_achv}" />
				<td><c:out value="${v1.block_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose><c:choose>
			<c:when test="${grampanchayat_achv ne v1.gram_panchayat_name_achv}">
				<c:set var="grampanchayat_achv" value="${v1.gram_panchayat_name_achv}" />
				<td><c:out value="${v1.gram_panchayat_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	 <c:choose>
			<c:when test="${village_achv ne v1.village_name_achv}">
				<c:set var="village_achv" value="${v1.village_name_achv}" />
				<td><c:out value="${v1.village_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${plot_achv ne v1.plot_no_achv}">
				<c:set var="plot_achv" value="${v1.plot_no_achv}" />
				<td> <c:out value="${v1.plot_no_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose> 
		
		 <c:choose>
			<c:when test="${tparea_achv ne v1.area_achv}">
			<c:set var="gtoftarea_achv" value="${gtoftarea_achv+v1.area_achv}" />
				<c:set var="tparea_achv" value="${v1.area_achv}" />
				<td class="text-right"> <c:out value="${v1.area_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>  
     <!-- 	<td></td><td></td><td></td> -->
     	<c:choose>
			<c:when test="${irri_achv ne v1.irrigation_achv}">
				<c:set var="irri_achv" value="${v1.irrigation_achv}" />
				<td> <c:out value="${v1.irrigation_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownership_achv ne v1.ownership_achv}">
				<c:set var="ownership_achv" value="${v1.ownership_achv}" />
				<td> <c:out value="${v1.ownership_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownername_achv ne v1.owner_name_achv}">
				<c:set var="ownername_achv" value="${v1.owner_name_achv}" />
				<td> <c:out value="${v1.owner_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${classland_achv ne v1.classification_land_achv}">
				<c:set var="classland_achv" value="${v1.classification_land_achv}" />
				<td> <c:out value="${v1.classification_land_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${nocrop_achv ne v1.no_crop_achv}">
				<c:set var="nocrop_achv" value="${v1.no_crop_achv}" />
				<td> <c:out value="${v1.no_crop_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${forestlandtype_achv ne v1.forest_land_achv}"> --%>
<%-- 				<c:set var="forestlandtype_achv" value="${v1.forest_land_achv}" /> --%>
<%-- 				<td> <c:out value="${v1.forest_land_achv}" /></td> --%>
<%-- 			</c:when>	 --%>
<%-- 			<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
		
		 <c:choose>
			<c:when test="${seasonval_achv ne v1.season_achv}">
				<c:set var="seasonval_achv" value="${v1.season_achv}" />
				<td> <c:out value="${v1.season_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	<td class="text-right"><c:out value="${v1.crop_type_achv}" /></td>
     	
     	<c:set var="sumofcroparea_achv" value="${sumofcroparea_achv+v1.crop_area_achv}" />
     	<c:set var="gtofcroparea_achv" value="${gtofcroparea_achv+v1.crop_area_achv}" />
     	<td class="text-right"><c:out value="${v1.crop_area_achv}" /></td>
     	<c:set var="sumofcropproduction_achv" value="${sumofcropproduction_achv+v1.crop_production_achv}" />
     	<c:set var="gtofcropproduction_achv" value="${gtofcropproduction_achv+v1.crop_production_achv}" />
     	<td class="text-right"><c:out value="${v1.crop_production_achv}" /></td>
     	<c:set var="sumofavgincome_achv" value="${sumofavgincome_achv+v1.avg_income_achv}" />
     	<c:set var="gtofavgincome_achv" value="${gtofavgincome_achv+v1.avg_income_achv}" />
     	<td class="text-right"><c:out value="${v1.avg_income_achv}" /></td>
     	<c:set var="sumoftotalincome_achv" value="${sumoftotalincome_achv+(v1.crop_area_achv*v1.crop_production_achv*v1.avg_income_achv) }" />
     	<c:set var="gtoftotalincome_achv" value="${gtoftotalincome_achv+(v1.crop_area_achv*v1.crop_production_achv*v1.avg_income_achv) }" />
     	<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${v1.crop_area_achv*v1.crop_production_achv*v1.avg_income_achv}" /></td>
     	
     	</tr>
     	</c:if>
     	<%-- </c:forEach> --%>
     	
     	</c:forEach>
     	 <tr style="color: #001fff !important; font-weight:bold"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>Total</td><td class="text-right"><c:out value="${sumofcroparea_achv}" /></td>
     	 <td class="text-right"><c:out value="${sumofcropproduction_achv}" /></td>
     	 <td class="text-right"><c:out value="${sumofavgincome_achv}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${sumoftotalincome_achv}" /></td></tr>
     	 <tr class="" style="background:#699ea7;"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
     	</c:forEach>
     	 
     	<tr style="color: #001fff !important; font-weight:bold"><td></td><td></td><td></td><td class="text-center">Grand Total(Baseline)</td><td class="text-right"><c:out value="${gtoftarea}" /></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
     	<td class="text-right"><c:out value="${gtofcroparea}" /></td><td class="text-right"><c:out value="${gtofcropproduction}" /></td>
     	 <td class="text-right"><c:out value="${gtofavgincome}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${gtoftotalincome}" /></td></tr>
     	 
     	 <tr style="color: #001fff !important; font-weight:bold"> <td></td><td></td><td></td><td class="text-center">Grand Total(Outcome)</td><td class="text-right"><c:out value="${gtoftarea_achv}" /></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
     	<td class="text-right"><c:out value="${gtofcroparea_achv}" /></td><td class="text-right"><c:out value="${gtofcropproduction_achv}" /></td>
     	 <td class="text-right"><c:out value="${gtofavgincome_achv}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${gtoftotalincome_achv}" /></td></tr>
     	</tbody>
<!--      		<thead> -->
<!--      	<tr> -->
<!--      		<th class="text-center">Block </th> -->
<!--      		<th class="text-center">Gram Panchayat </th> -->
<!--      		<th class="text-center">Village </th> -->
<!--      		<th class="text-center">Plot/Gata No.</th> -->
<!--      		<th class="text-center">Plot Area</th> -->
<!--      		<th class="text-center">Irrigation Status</th> -->
<!--      		<th class="text-center">Ownership</th> -->
<!--      		<th class="text-center">Owner Name</th> -->
<!--      		<th class="text-center">Classification of Land</th> -->
<!--      		<th class="text-center">No. of Crop</th> -->
<!--      		<th class="text-center">Forest Land type</th> -->
<!--      		<th class="text-center">Season</th> -->
<!--      		<th class="text-center">Crop Type</th> -->
<!--      		<th class="text-center">Area (Col-A)</th> -->
<!--      		<th class="text-center">Crop Production per Hectare (in Quintal)  <br/>(Col-B)</th> -->
<!--      		<th class="text-center">Avg. Income per Quintal <br/>(Col-C)</th> -->
<!--      		<th class="text-center">Total Income (A*B*C)</th> -->
<!--      	</tr> -->
<!--      	</thead> -->
     </table>
     
     
     
     </div>
  </div>
  
  				<c:if test="${dataListsize==0}">
  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
          				<tr class="tabs">
            				<td><center><span style="color: red;"> No Data Found !</span></center></td>
            				
          				</tr>
        			</table>
 				</c:if>
     </form>
     <br/>
     
     </div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
<%-- <script src='<c:url value="/resources/js/blsOutcome.js" />'></script> --%>
</body>
</html>
