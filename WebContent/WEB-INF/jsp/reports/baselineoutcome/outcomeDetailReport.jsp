<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<title>Report O3- Distribution of Gross Cropped Area as per Classification of Land,
Net Sown Area, Gross Cropped Area and Total Income as on date for Project</title>


<script type="text/javascript">
function downloadPDF(id, stateName,distName, projName){
		document.getElementById("id").value=id;
	    document.getElementById("stateName").value=stateName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.delcompBase.action="downloadProjectDetailOfOutcomePDF";
		document.delcompBase.method="post";
		document.delcompBase.submit();
		}

function exportExcell(id, stateName,distName, projName){
	document.getElementById("id").value=id;
    document.getElementById("stateName").value=stateName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.delcompBase.action="downloadExceloutrptdetail";
	document.delcompBase.method="post";
	document.delcompBase.submit();
	}

</script>
<div class="container-fluid">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"><h5><label id="head1">Report O3- Distribution of Gross Cropped Area as per Classification of Land,
Net Sown Area, Gross Cropped Area and Total Income as on date for Project "<c:out value="${projName}"/>"</label></h5></div>
 <hr/>
  <button name="exportExcel" id="exportExcel" onclick="exportExcell('${id}','${stateName}','${distName}','${projName}')" class="btn btn-info">Excel</button>
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
   <c:set var="forestland" value="0"/>
   <c:set var="others" value="0"/>
   <c:set var="netsownarea" value="0"/>
   <c:set var="grossedcroppedarea" value="0"/>
   
     <table class="tblAddPhysicalAchievement" id="pdfBasicExample" name="dtBasicExample" style="width:100%">
     	<thead>
     	<tr><th colspan="4">State:- <c:out value="${stateName}"/><br/> District:- <c:out value="${distName}"/><br/> Project:- <c:out value="${projName}"/></th><th class="text-right" colspan="13"> All area in ha. <br/> All amounts are in Rs.</th></tr>
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
     		<th class="text-center">Forest Land type</th>
     		<th class="text-center">Season</th>
     		<th class="text-center">Crop Type</th>
     		<th class="text-center">Area (Col-A)</th>
     		<th class="text-center">Crop Production per Hectare (in Quintal)  <br/>(Col-B)</th>
     		<th class="text-center">Avg. Income per Quintal <br/>(Col-C)</th>
     		<th class="text-center">Total Income (A*B*C)</th>
     	</tr>
     	</thead>
     	<tbody>
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
     	
     	<c:set var="tparea_achv" value="" />
     	<c:set var="irri_achv" value="" />
     	<c:set var="ownership_achv" value="" />
     	<c:set var="ownername_achv" value="" />
     	<c:set var="classland_achv" value="" />
     	<c:set var="nocrop_achv" value="" />
     	<c:set var="forestlandtype_achv" value="" />
     	<c:set var="seasonval_achv" value="" />
     	
     	<c:set var="sumofcroparea_achv" value="0" />
     	<c:set var="sumofcropproduction_achv" value="0" />
     	<c:set var="sumofavgincome_achv" value="0" />
     	<c:set var="sumoftotalincome_achv" value="0" />
     	<c:forEach items="${value1.value}" var="val" varStatus="count1">
     	<tr>
     	<c:choose>
			<c:when test="${block_achv ne val.block_name_achv}">
				<c:set var="block_achv" value="${val.block_name_achv}" />
				<td> <c:out value="${val.block_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${grampanchayat_achv ne val.gram_panchayat_name_achv}">
				<c:set var="grampanchayat_achv" value="${val.gram_panchayat_name_achv}" />
				<td> <c:out value="${val.gram_panchayat_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	
     	<c:choose>
			<c:when test="${village_achv ne val.village_name_achv}">
				<c:set var="village_achv" value="${val.village_name_achv}" />
				<td> <c:out value="${val.village_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${plot_achv ne val.plot_no_achv}">
				<c:set var="plot_achv" value="${val.plot_no_achv}" />
				<td> <c:out value="${val.plot_no_achv}" /></td>
				<c:set var="tparea_achv" value="${val.area_achv}" />
				<c:set var="gtoftarea_achv" value="${gtoftarea_achv+val.area_achv}" />
				<td class="text-right"> <c:out value="${val.area_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
				<td></td>
			</c:otherwise>
		</c:choose>
		
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${plot_achv ne val.plot_no_achv}"> --%>
<%-- 				<c:set var="tparea_achv" value="${val.area_achv}" /> --%>
<%-- 				<c:set var="gtoftarea_achv" value="${gtoftarea_achv+val.area_achv}" /> --%>
<%-- 				<td class="text-right"> <c:out value="${val.area_achv}" /></td> --%>
<%-- 			</c:when>	 --%>
<%-- 			<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose>  --%>
     	
     	<c:choose>
			<c:when test="${irri_achv ne val.irrigation_achv}">
				<c:set var="irri_achv" value="${val.irrigation_achv}" />
				<td> <c:out value="${val.irrigation_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownership_achv ne val.ownership_achv}">
				<c:set var="ownership_achv" value="${val.ownership_achv}" />
				<td> <c:out value="${val.ownership_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownername_achv ne val.owner_name_achv}">
				<c:set var="ownername_achv" value="${val.owner_name_achv}" />
				<td> <c:out value="${val.owner_name_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${classland_achv ne val.classification_land_achv}">
				<c:set var="classland_achv" value="${val.classification_land_achv}" />
				<td> <c:out value="${val.classification_land_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${nocrop_achv ne val.no_crop_achv}">
				<c:set var="nocrop_achv" value="${val.no_crop_achv}" />
				<td> <c:out value="${val.no_crop_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${forestlandtype_achv ne val.forest_land_achv}">
				<c:set var="forestlandtype_achv" value="${val.forest_land_achv}" />
				<td> <c:out value="${val.forest_land_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		 <c:choose>
			<c:when test="${seasonval_achv ne val.season_achv}">
				<c:set var="seasonval_achv" value="${val.season_achv}" />
				<td> <c:out value="${val.season_achv}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	<td class="text-right"><c:out value="${val.crop_type_achv}" /></td>
     	
     	<c:set var="sumofcroparea_achv" value="${sumofcroparea_achv+val.crop_area_achv}" />
     	<c:set var="gtofcroparea_achv" value="${gtofcroparea_achv+val.crop_area_achv}" />
     	<td class="text-right"><c:out value="${val.crop_area_achv}" /></td>
     	<c:set var="sumofcropproduction_achv" value="${sumofcropproduction_achv+val.crop_production_achv}" />
     	<c:set var="gtofcropproduction_achv" value="${gtofcropproduction_achv+val.crop_production_achv}" />
     	<td class="text-right"><c:out value="${val.crop_production_achv}" /></td>
     	<c:set var="sumofavgincome_achv" value="${sumofavgincome_achv+val.avg_income_achv}" />
     	<c:set var="gtofavgincome_achv" value="${gtofavgincome_achv+val.avg_income_achv}" />
     	<td class="text-right"><c:out value="${val.avg_income_achv}" /></td>
     	<c:set var="sumoftotalincome_achv" value="${sumoftotalincome_achv+(val.crop_area_achv*val.crop_production_achv*val.avg_income_achv) }" />
     	<c:set var="gtoftotalincome_achv" value="${gtoftotalincome_achv+(val.crop_area_achv*val.crop_production_achv*val.avg_income_achv) }" />
     	<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${val.crop_area_achv*val.crop_production_achv*val.avg_income_achv}" /></td>
     	
     	</tr>
     	</c:forEach>
     	 <tr style="color: #001fff !important; font-weight:bold">
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
     	 <td></td><td>Total</td><td class="text-right"><c:out value="${sumofcroparea_achv}" /></td>
     	 <td class="text-right"><c:out value="${sumofcropproduction_achv}" /></td>
     	 <td class="text-right"><c:out value="${sumofavgincome_achv}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${sumoftotalincome_achv}" /></td></tr>
     	</c:forEach> 
     	<tr style="color: #001fff !important; font-weight:bold">
     	<td></td>
     	<td></td>
     	<td></td>
     	<td class="text-center">Grand Total</td><td class="text-right"><c:out value="${gtoftarea_achv}" /></td>
     	<td></td>
     	<td></td>
     	<td></td>
     	<td></td>
     	<td></td>
     	<td></td>
     	<td></td>
     	<td></td>
     	<td class="text-right"><c:out value="${gtofcroparea_achv}" /></td><td class="text-right"><c:out value="${gtofcropproduction_achv}" /></td>
     	 <td class="text-right"><c:out value="${gtofavgincome_achv}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${gtoftotalincome_achv}" /></td></tr>
     	</tbody>
     		
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
<script src='<c:url value="/resources/js/blsOutcome.js" />'></script>
</body>
</html>