<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>

<head>
<title>Report MT2-Cropped Others Detail Report</title>
</head>

<script type="text/javascript">
function downloadPDF()
{
		document.getcropdtlothrpt.action="downloadStWiseCropDtlAreaOthPDF";
		document.getcropdtlothrpt.method="post";
		document.getcropdtlothrpt.submit();
	
} 
function exportExcel()
{
		document.getcropdtlothrpt.action="downloadStWiseCropDtlAreaOthExcel";
		document.getcropdtlothrpt.method="post";
		document.getcropdtlothrpt.submit();
	
}

function downloadPDF1(stcode, stname){
    document.getElementById("stcode").value=stcode;
    document.getElementById("stname").value=stname;
	document.getcropdtlothrpt.action="downloadDistWiseCropDtlAreaOthPDF";
	document.getcropdtlothrpt.method="post";
	document.getcropdtlothrpt.submit();
}

function exportExcel1(stcode, stname){
    document.getElementById("stcode").value=stcode;
    document.getElementById("stname").value=stname;
	document.getcropdtlothrpt.action="downloadDistWiseCropDtlAreaOthExcel";
	document.getcropdtlothrpt.method="post";
	document.getcropdtlothrpt.submit();
}
</script>
<c:if test = "${listsize>0}">
<form action="downloadblsurveyPDF" method="post" name="getcropdtlothrpt"></form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report PE5-State wise Cropped Others Detail Report</label></h5>
	</div>
	 <button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()"	class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="pdfBasicExample" class="table">
<!-- 	<tbody id="dtBasicExample"> -->
		<thead>
			<tr>
				<th class="text-right" colspan="21">All area in ha.</th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">State</th>
				<th rowspan="3" class="text-center">Total Project</th>
				<th colspan="3" class="text-center">Total Gross Cropped Area</th>
				<th colspan="3" class="text-center">Area of horticulture crop</th>
				<th colspan="3" class="text-center">Net Sown Area</th>
				<th colspan="3" class="text-center">Cropping Intensity (%)</th>
				<th colspan="3" class="text-center">Area under protective irrigation</th>
			</tr>
			
			<tr>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			</tr>
			
			 <tr>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			</tr> 
			
			 <tbody id="dtBasicExample">
			<c:forEach items="${stwiseAreacroppedothsdtl}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<td><a href="getDistwiseCroppedDtlOthRpt?stcode=${project.st_code}&&stname=${project.st_name}">${project.st_name}</a></td>
				 	<td class="text-right"><c:out value="${project.project}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.total_pre_gross_cropped}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.total_mid_gross_cropped}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.total_control_gross_cropped}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_horticulture}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_horticulture}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_horticulture}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_netsown}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_netsown}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_netsown}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_cropping}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_cropping}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_cropping}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_protective}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_protective}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_protective}" /></fmt:formatNumber></td>
					
			</tr>
			
			<c:set var="totalproject"
					value="${totalproject+project.project}" />
				<c:set var="totalpre_gross_cropped"
					value="${totalpre_gross_cropped+project.total_pre_gross_cropped}" />
				<c:set var="totalmid_gross_cropped"
					value="${totalmid_gross_cropped+project.total_mid_gross_cropped}" />
				<c:set var="totalcontrol_gross_cropped"
					value="${totalcontrol_gross_cropped+project.total_control_gross_cropped}" />
					
				<c:set var="totalpre_horticulture"
					value="${totalpre_horticulture+project.pre_horticulture}" />
				<c:set var="totalmid_horticulture"
					value="${totalmid_horticulture+project.mid_horticulture}" />
				<c:set var="totalcontrol_horticulture"
					value="${totalcontrol_horticulture+project.control_horticulture}" />
					
				<c:set var="totalpre_netsown"
					value="${totalpre_netsown+project.pre_netsown}" />
				<c:set var="totalmid_netsown"
					value="${totalmid_netsown+project.mid_netsown}" />
				<c:set var="totalcontrol_netsown"
					value="${totalcontrol_netsown+project.control_netsown}" />
					
				<c:set var="totalpre_cropping"
					value="${totalpre_cropping+project.pre_cropping}" />
				<c:set var="totalmid_cropping"
					value="${totalmid_cropping+project.mid_cropping}" />
				<c:set var="totalcontrol_cropping"
					value="${totalcontrol_cropping+project.control_cropping}" />
					
				<c:set var="totalpre_protective"
					value="${totalpre_protective+project.pre_protective}" />
				<c:set var="totalmid_protective"
					value="${totalmid_protective+project.mid_protective}" />
				<c:set var="totalcontrol_protective"
					value="${totalcontrol_protective+project.control_protective}" />
						
			</c:forEach>
			<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalproject}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_gross_cropped}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_gross_cropped}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_gross_cropped}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_horticulture}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_horticulture}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_horticulture}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_netsown}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_netsown}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_netsown}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_cropping}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_cropping}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_cropping}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_protective}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_protective}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_protective}" /></fmt:formatNumber></b></td>
				
			</tr>	 
			</tbody> 		
</thead>

</table>
</div>
</c:if>

<c:if test = "${distListSize>0}">
<form action="downloadblsurveyPDF" method="post" name="getcropdtlothrpt">
				<input type="hidden" name="stcode" id="stcode" value="" />
	     	 	<input type="hidden" name="stname" id="stname" value="" /></form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report PE5-District wise Cropped Others Detail Report</label></h5>
	</div>
	 <button name="exportExcel" id="exportExcel" onclick="exportExcel1('${stcode}','${stname}')" class="btn btn-info">Excel</button>
	 <button name="exportPDF" id="exportPDF" onclick="downloadPDF1('${stcode}','${stname}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="pdfBasicExample" class="table">
<!-- 	<tbody id="dtBasicExample"> -->
		<thead>
			<tr>
		    	<th class="text-left" colspan="16">State: ${stname}</th>
				<th class="text-right" colspan="2">All area in ha.</th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">District</th>
				<th rowspan="3" class="text-center">Total Project</th>
				<th colspan="3" class="text-center">Total Gross Cropped Area</th>
				<th colspan="3" class="text-center">Area of horticulture crop</th>
				<th colspan="3" class="text-center">Net Sown Area</th>
				<th colspan="3" class="text-center">Cropping Intensity (%)</th>
				<th colspan="3" class="text-center">Area under protective irrigation</th>
			</tr>
			
			<tr>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			<th colspan="2" class="text-center">Project Area Details</th>
			<th colspan="1" rowspan="2" class="text-center">Controlled Area Details</th>
			</tr>
			
			 <tr>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			<th rowspan="2" class="text-center">Pre Project</th>
			<th rowspan="2" class="text-center">Mid Project </th>
			</tr> 
			
			 <tbody id="dtBasicExample">
			<c:forEach items="${distList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<td><c:out value="${project.dist_name}" /></td>
					<td class="text-right"><c:out value="${project.project}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.total_pre_gross_cropped}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.total_mid_gross_cropped}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.total_control_gross_cropped}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_horticulture}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_horticulture}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_horticulture}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_netsown}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_netsown}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_netsown}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_cropping}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_cropping}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_cropping}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.pre_protective}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.mid_protective}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.control_protective}" /></fmt:formatNumber></td>
					
			</tr>
			
			<c:set var="totalproject"
					value="${totalproject+project.project}" />
				<c:set var="totalpre_gross_cropped"
					value="${totalpre_gross_cropped+project.total_pre_gross_cropped}" />
				<c:set var="totalmid_gross_cropped"
					value="${totalmid_gross_cropped+project.total_mid_gross_cropped}" />
				<c:set var="totalcontrol_gross_cropped"
					value="${totalcontrol_gross_cropped+project.total_control_gross_cropped}" />
					
				<c:set var="totalpre_horticulture"
					value="${totalpre_horticulture+project.pre_horticulture}" />
				<c:set var="totalmid_horticulture"
					value="${totalmid_horticulture+project.mid_horticulture}" />
				<c:set var="totalcontrol_horticulture"
					value="${totalcontrol_horticulture+project.control_horticulture}" />
					
				<c:set var="totalpre_netsown"
					value="${totalpre_netsown+project.pre_netsown}" />
				<c:set var="totalmid_netsown"
					value="${totalmid_netsown+project.mid_netsown}" />
				<c:set var="totalcontrol_netsown"
					value="${totalcontrol_netsown+project.control_netsown}" />
					
				<c:set var="totalpre_cropping"
					value="${totalpre_cropping+project.pre_cropping}" />
				<c:set var="totalmid_cropping"
					value="${totalmid_cropping+project.mid_cropping}" />
				<c:set var="totalcontrol_cropping"
					value="${totalcontrol_cropping+project.control_cropping}" />
					
				<c:set var="totalpre_protective"
					value="${totalpre_protective+project.pre_protective}" />
				<c:set var="totalmid_protective"
					value="${totalmid_protective+project.mid_protective}" />
				<c:set var="totalcontrol_protective"
					value="${totalcontrol_protective+project.control_protective}" />
						
			</c:forEach>
			<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalproject}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_gross_cropped}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_gross_cropped}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_gross_cropped}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_horticulture}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_horticulture}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_horticulture}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_netsown}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_netsown}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_netsown}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_cropping}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_cropping}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_cropping}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalpre_protective}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalmid_protective}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcontrol_protective}" /></fmt:formatNumber></b></td>
				
			</tr>	 
			</tbody> 		
</thead>

</table>
</div>
</c:if>
<footer class="text-center">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</footer>
</html>
			