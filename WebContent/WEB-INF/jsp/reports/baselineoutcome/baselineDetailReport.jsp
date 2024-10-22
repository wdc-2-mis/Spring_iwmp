<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<title>Report SB1</title>
<script type="text/javascript">
function downloadPDF(id, stateName,distName, projName,totplot){
		document.getElementById("id").value=id;
	    document.getElementById("stateName").value=stateName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.getElementById("totplot").value=totplot;
	    document.delcompBase.action="downloadblsrptDetailOfOutcomePDF";
		document.delcompBase.method="post";
		document.delcompBase.submit();
		}
		
function exportExcel(id, stateName,distName, projName,totplot){
	document.getElementById("id").value=id;
    document.getElementById("stateName").value=stateName;
    document.getElementById("distName").value=distName;
    document.getElementById("projName").value=projName;
    document.getElementById("totplot").value=totplot;
    document.delcompBase.action="downloadExcelblsrptdetail";
	document.delcompBase.method="post";
	document.delcompBase.submit();
	}

</script>
<div class="container-fluid">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"><h5><label id="head1">Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for Project "<c:out value="${projName}"/>"</label></h5></div>
 <hr/>
   <button name="exportExcel" id="exportExcel" onclick="exportExcel('${id}','${stateName}','${distName}','${projName}','${totplot}')" class="btn btn-info">Excel</button>
    <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${id}','${stateName}','${distName}','${projName}','${totplot}')" class="btn btn-info">PDF</button>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<div class="form-group  col-md-12">
		<div class="message">
			<label class="alert alert-info alert-dismissible fade show"><strong>Note:</strong>
				Total Income is the Total Value of Output.</label>
		</div>
	</div>
	<form name="delcompBase" id="delcompBase">
  <input type="hidden" name="id" id="id" value="" />
   <input type="hidden" name="stateName" id="stateName" value="" />
    <input type="hidden" name="distName" id="distName" value="" />
     <input type="hidden" name="projName" id="projName" value="" />
     <input type="hidden" name="totplot" id="totplot" value="" />
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
    <table class="table table-bordered table-striped table-highlight w-auto"
						id="pdfBasicExample">
    	<thead>
     	<tr><th colspan="4">State:- <c:out value="${stateName}"/><br/> District:- <c:out value="${distName}"/><br/> Project:- <c:out value="${projName}"/></th><th class="text-right" colspan="13"> All area in ha. <br/> All amounts are in Rs.</th></tr>
     	<tr>
<!--      	line no 34,35,54 and 55 added by Yogesh -->
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
     	<c:set var="block" value="" />
     	<c:set var="grampanchayat" value="" />
     	<c:set var="village" value="" />
     	<c:set var="plot" value="" />
     	<c:set var="gtoftarea" value="0" />
     	<c:set var="gtofcroparea" value="0" />
     	<c:set var="gtofcropproduction" value="0" />
     	<c:set var="gtofavgincome" value="0" />
     	<c:set var="gtoftotalincome" value="0" />
     	
     	 <c:forEach items="${valueList}" var="value1" >
     	
     	<c:set var="tparea" value="" />
     	<c:set var="irri" value="" />
     	<c:set var="ownership" value="" />
     	<c:set var="ownername" value="" />
     	<c:set var="classland" value="" />
     	<c:set var="nocrop" value="" />
     	<c:set var="forestlandtype" value="" />
     	<c:set var="seasonval" value="" />
     	
     	<c:set var="sumofcroparea" value="0" />
     	<c:set var="sumofcropproduction" value="0" />
     	<c:set var="sumofavgincome" value="0" />
     	<c:set var="sumoftotalincome" value="0" />
     	<c:forEach items="${value1.value}" var="val" varStatus="count1">
     	<tr>
     	<!--      	line no 82 to 100 added by Yogesh -->
     	<c:choose>
			<c:when test="${block ne val.block_name}">
				<c:set var="block" value="${val.block_name}" />
				<td> <c:out value="${val.block_name}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${grampanchayat ne val.gram_panchayat_name}">
				<c:set var="grampanchayat" value="${val.gram_panchayat_name}" />
				<td> <c:out value="${val.gram_panchayat_name}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	<c:choose>
			<c:when test="${village ne val.village_name}">
				<c:set var="village" value="${val.village_name}" />
				<td> <c:out value="${val.village_name}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${plot ne val.plot_no}">
				<c:set var="plot" value="${val.plot_no}" />
				<td> <c:out value="${val.plot_no}" /></td>
<%-- 				<c:set var="tparea" value="${val.area}" /> --%>
<%-- 				<c:set var="gtoftarea" value="${gtoftarea+val.area}" /> --%>
<%-- 				<td class="text-right"> <c:out value="${val.area}" /></td> --%>
			</c:when>	
			<c:otherwise>
				<td></td>
<!-- 				<td></td> -->
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${tparea ne val.area}">
				<c:set var="tparea" value="${val.area}" />
				<c:set var="gtoftarea" value="${gtoftarea+val.area}" />
				<td class="text-right"> <c:out value="${val.area}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose> 
     	
     	<c:choose>
			<c:when test="${irri ne val.irrigation}">
				<c:set var="irri" value="${val.irrigation}" />
				<td> <c:out value="${val.irrigation}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownership ne val.ownership}">
				<c:set var="ownership" value="${val.ownership}" />
				<td> <c:out value="${val.ownership}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ownername ne val.owner_name}">
				<c:set var="ownername" value="${val.owner_name}" />
				<td> <c:out value="${val.owner_name}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${classland ne val.classification_land}">
				<c:set var="classland" value="${val.classification_land}" />
				<td> <c:out value="${val.classification_land}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${nocrop ne val.no_crop}">
				<c:set var="nocrop" value="${val.no_crop}" />
				<td> <c:out value="${val.no_crop}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${forestlandtype ne val.forest_land}">
				<c:set var="forestlandtype" value="${val.forest_land}" />
				<td> <c:out value="${val.forest_land}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
		
		 <c:choose>
			<c:when test="${seasonval ne val.season}">
				<c:set var="seasonval" value="${val.season}" />
				<td> <c:out value="${val.season}" /></td>
			</c:when>	
			<c:otherwise>
				<td></td>
			</c:otherwise>
		</c:choose>
     	<td class="text-right"><c:out value="${val.crop_type}" /></td>
     	
     	<c:set var="sumofcroparea" value="${sumofcroparea+val.crop_area}" />
     	<c:set var="gtofcroparea" value="${gtofcroparea+val.crop_area}" />
     	<td class="text-right"><c:out value="${val.crop_area}" /></td>
     	<c:set var="sumofcropproduction" value="${sumofcropproduction+val.crop_production}" />
     	<c:set var="gtofcropproduction" value="${gtofcropproduction+val.crop_production}" />
     	<td class="text-right"><c:out value="${val.crop_production}" /></td>
     	<c:set var="sumofavgincome" value="${sumofavgincome+val.avg_income}" />
     	<c:set var="gtofavgincome" value="${gtofavgincome+val.avg_income}" />
     	<td class="text-right"><c:out value="${val.avg_income}" /></td>
     	<c:set var="sumoftotalincome" value="${sumoftotalincome+(val.crop_area*val.crop_production*val.avg_income) }" />
     	<c:set var="gtoftotalincome" value="${gtoftotalincome+(val.crop_area*val.crop_production*val.avg_income) }" />
     	<td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${val.crop_area*val.crop_production*val.avg_income}" /></td>
     	
     	</tr>
     	</c:forEach>
     	 <tr style="color: #001fff !important; font-weight:bold">
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
     	 <td ></td>
<!--      	 <td ></td> -->
<!--      	 <td ></td> -->
<!--      	 <td ></td> -->
     	 <td ></td><td>Total</td><td class="text-right"><c:out value="${sumofcroparea}" /></td>
     	 <td class="text-right"><c:out value="${sumofcropproduction}" /></td>
     	 <td class="text-right"><c:out value="${sumofavgincome}" /></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${sumoftotalincome}" /></td></tr>
     	</c:forEach> 
     	<tr style="color: #001fff !important; font-weight:bold">
     	 <td ></td>
     	  <td ></td>
     	<td class="text-center" >Grand Total</td><td class="text-right"><c:out value = "${totplot}"/></td><td class="text-right"><c:out value="${gtoftarea}" /></td>
     	<td colspan="8"></td>

     	<td class="text-right"><c:out value="${gtofcroparea}" /></td><td class="text-right"><c:out value="${gtofcropproduction}" /></td>
     	 <td class="text-right"></td><td class="text-right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${gtoftotalincome}" /></td></tr>
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
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>