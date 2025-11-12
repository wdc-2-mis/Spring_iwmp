<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<title>Report ME7- Project and Activities Wise OOMF Before Pushing Data</title>
	<script type="text/javascript">
	function downloadPDF(dcode, stName, distName){
		document.getElementById("dcode").value=dcode;
		document.getElementById("stName").value=stName;
		document.getElementById("distName").value=distName;
		document.OOMFProjActivities.action="downloadPDFProjOOMFBeforePrayashData";
		document.OOMFProjActivities.method="post";
		document.OOMFProjActivities.submit();
	}

	function exportExcel(dcode, stName, distName){
		document.getElementById("dcode").value=dcode;
		document.getElementById("stName").value=stName;
		document.getElementById("distName").value=distName;
		document.OOMFProjActivities.action="downloadExcelProjOOMFBeforePrayashData";
		document.OOMFProjActivities.method="post";
		document.OOMFProjActivities.submit();
	}
	
	</script>

<body>
<br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report ME7- Project and Activities Wise Current Achievement for the Financial Year '<c:out value='${finyr}' />' and Month '<c:out value='${month}' />' for District '<c:out value='${distName}' />' of State '<c:out value='${stName}' />' </label></h5></div>
<br>
<div class ="card">

	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10" >
	<form action="getOOMFBeforePrayashData" method="post" id="OOMFProjActivities" name="OOMFProjActivities">
	<input type="hidden" name="stcd" id="stcd" value="" />
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="dcode" id="dcode" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
			<div class="form-row">
			</div>
		</form>
	
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${dcode}', '${stName}','${distName}')" class="btn btn-info">Excel</button>  
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${dcode}', '${stName}','${distName}')" class="btn btn-info">PDF</button>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="dtBasicExample" cellspacing="0" class="table" >
  	<thead>
    	<tr>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
      	<!-- 	<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Project</th> -->
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area of Degraded Land Covered/Rainfed Area Developed</th>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area covered with soil and moisture conservation activities</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area Brought under Plantation (Afforestation/Horticulture)</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Water Harvesting Structure (created/renovated)</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Farmers Benefited</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area Brought under Protective Irrigation (created/renovated)</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of man-days generated</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total No. of Springs Rejuvenated </th>
			<c:if test="${monaddi>0}">
			<th style="text-align:center; vertical-align: middle; width: 7%;">Area covered under diversified crops/change in cropping system</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Area brought from no crop/single crop to single/multiple crop</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Increase in gross cropped area</th>
			</c:if>
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
			<c:if test="${monaddi>0}">
			<th class="text-center">11</th>
			<th class="text-center">12</th>
			<th class="text-center">13</th>
			</c:if>
		</tr>
  	</thead>
  	<tbody>
    	
		<c:set var="count" value="1" />
		
				<c:if test="${dataListSize>0}">
						<c:forEach items="${dataList}" var="data" varStatus="count">							
							<tr>
								<td><c:out value='${count.count}' /></td>
 								<%-- <td><a href="getdistrictWiseJanbhagidariActivitiesReport?id=<c:out value='${data.st_code}' />&stname=${data.st_name}" > <c:out value="${data.st_name}" /></a></td> --%>
 								<td> <c:out value="${data.proj_name}" /></td> 
 							<%-- 	<td class="text-right"> <c:out value="${data.totalproject}" /></td> --%>
 								<td class="text-right"> <c:out value="${data.degraded_land}" /></td>
 								<td class="text-right"> <c:out value="${data.soilmoisture}" /></td>
 								<td class="text-right"> <c:out value="${data.afforestation_horticulture}" /></td>
								<td class="text-right"> <c:out value="${data.water_harvest}" /></td>
		 						<td class="text-right"> <c:out value="${data.farmer_benefitte}" /></td>
								<td class="text-right"> <c:out value="${data.protective_irrigation}" /></td>
								<td class="text-right"> <c:out value="${data.mandays_generated}" /></td>
								<td class="text-right"> <c:out value="${data.spring_rej}" /></td>
								<c:if test="${monaddi>0}">
								<td class="text-right"> <c:out value="${data.diversified}" /></td>
								<td class="text-right"> <c:out value="${data.chnagesingle}" /></td>
								<td class="text-right"> <c:out value="${data.incr_corp}" /></td> 
								</c:if>
								
 							</tr>
						</c:forEach>
					</c:if>
		
		 	<tr>
				<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${degraded_landt}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${soilmoisturet}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${afforestation_horticulturet}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="0"><c:out value='${water_harvestt}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="0"><c:out value='${farmer_benefittet}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${protective_irrigationt}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="0"><c:out value='${mandays_generatedt}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="0"><c:out value='${spring_rejt}' /></fmt:formatNumber></b></td>
				<c:if test="${monaddi>0}">
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${diversifiedt}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${chnagesinglet}' /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${incr_corpt}' /></fmt:formatNumber></b></td>
				</c:if>
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