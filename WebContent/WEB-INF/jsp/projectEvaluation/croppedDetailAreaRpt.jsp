<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>

<head>
<title>Report PE4-Cropped Detail Area</title>
</head>

<script type="text/javascript">
var stName = "${stName != null ? stName : ''}";
function handleProjectClick(projId) {
    $.ajax({
        url: "checkProjIdExists",
        type: "GET",
        data: { projectId: projId },
        contentType: "application/x-www-form-urlencoded",
        success: function(response) {
        	 if (response.exists && response.status === "C") {
                var reportdata = "rptdata";
                var url = "getviewcomplete?"
                    + "project=" + response.projId
                    + "&district=" + response.distCode
                    + "&distName=" + encodeURIComponent(response.distName)
                    + "&projName=" + encodeURIComponent(response.projName)
                    + "&finyear=" + response.finYearCode
                    + "&finName=" + encodeURIComponent(response.finYearDesc)
                    + "&month=" + response.monthId
                    + "&monthName=" + encodeURIComponent(response.monthName)
                    + "&reportdata=" + encodeURIComponent(reportdata)
                    + "&stName=" + encodeURIComponent(stName);

                // Load JSP inside iframe and open modal
                document.getElementById("childFrame").src = url;
                document.getElementById("childModal").style.display = "block";
            } else {
                alert("Project not found or not completed.");
            }
        },
        error: function() {
            console.log("Error checking project existence.");
        }
    });
}

function closeChildModal() {
    document.getElementById("childModal").style.display = "none";
    document.getElementById("childFrame").src = "";
}

function downloadPDF()
{
		document.getcropdtlrpt.action="downloadStWiseCropDtlAreaPDF";
		document.getcropdtlrpt.method="post";
		document.getcropdtlrpt.submit();
	
}

function exportExcel()
{
		document.getcropdtlrpt.action="downloadStWiseCropDtlAreaExcel";
		document.getcropdtlrpt.method="post";
		document.getcropdtlrpt.submit();
	
}

function downloadPDF1(stcode, stname){
    document.getElementById("stcode").value=stcode;
    document.getElementById("stname").value=stname;
	document.getcropdtlrpt.action="downloadDistWiseCropDtlAreaPDF";
	document.getcropdtlrpt.method="post";
	document.getcropdtlrpt.submit();
}

function exportExcel1(stcode, stname){
    document.getElementById("stcode").value=stcode;
    document.getElementById("stname").value=stname;
	document.getcropdtlrpt.action="downloadDistWiseCropDtlAreaExcel";
	document.getcropdtlrpt.method="post";
	document.getcropdtlrpt.submit();
}

function downloadPDF2(stcode, stname, dcode, distname){
    document.getElementById("stcode").value=stcode;
    document.getElementById("stname").value=stname;
    document.getElementById("dcode").value=dcode;
    document.getElementById("distname").value=distname;
	document.getcropdtlrpt.action="downloadProjWiseCropDtlAreaPDF";
	document.getcropdtlrpt.method="post";
	document.getcropdtlrpt.submit();
}

function exportExcel2(stcode, stname, dcode, distname){
    document.getElementById("stcode").value=stcode;
    document.getElementById("stname").value=stname;
    document.getElementById("dcode").value=dcode;
    document.getElementById("distname").value=distname;
	document.getcropdtlrpt.action="downloadProjWiseCropDtlAreaExcel";
	document.getcropdtlrpt.method="post";
	document.getcropdtlrpt.submit();
}

</script>
<style>
/* ===== Modal Styling ===== */
.modal1 {
    display: none;
    position: fixed;
    z-index: 2000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.6);
}

.modal1-content {
    background-color: #fff;
    margin: 3% auto;
    border-radius: 8px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.3);
    position: relative;
    width: 90%;
    height: 90%;
}

.close {
    color: #aaa;
    position: absolute;
    top: 10px;
    right: 20px;
    font-size: 28px;
    cursor: pointer;
}
.close:hover {
    color: #000;
}
</style>

<c:if test = "${listsize>0}">
<form action="downloadblsurveyPDF" method="post" name="getcropdtlrpt"></form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report PE4-State wise Cropped Detail Area</label></h5>
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
				<th colspan="2" rowspan="2" class="text-center">Area covered under diversified crops/ change in cropping system</th>
				<th colspan="4" class="text-center">Area brought from Nil/Single crop to double or more crop</th>
				<th colspan="2" rowspan="2" class="text-center">No. of Water Harvesting Structure (WHS) constructed /rejuvenated</th>
				<th colspan="2" rowspan="2" class="text-center">Area Covered with soil and Moisture</th>
				<th colspan="2" rowspan="2" class="text-center">Area of degraded land covered /rainfed area developed</th>
			</tr>
			
			<tr>
			<th colspan="2" class="text-center">Nil to single crop</th>
			<th colspan="2" class="text-center">Single to double or more crop</th>
			</tr>
			
			<tr>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			</tr>
			
			<tbody id="dtBasicExample">
			<c:forEach items="${list}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<td><a href="getDistwiseCroppedDtlArea?stcode=${project.st_code}&&stname=${project.st_name}">${project.st_name}</a></td>
				 	<td class="text-right"><c:out value="${project.project}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_div_change}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_div_change}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_nil_single}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_nil_single}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_single_double}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_single_double}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.proj_whs_rejuvenated}" /></td>
					<td class="text-right"><c:out value="${project.con_whs_rejuvenated}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_soil_moist}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_soil_moist}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_deg_rain}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_deg_rain}" /></fmt:formatNumber></td>
					
			</tr>
			
			<c:set var="totalproject"
					value="${totalproject+project.project}" />
				<c:set var="totalproj_div_change"
					value="${totalproj_div_change+project.proj_div_change}" />
				<c:set var="totalcon_div_change"
					value="${totalcon_div_change+project.con_div_change}" />
				<c:set var="totalproj_nil_single"
					value="${totalproj_nil_single+project.proj_nil_single}" />
				<c:set var="totalcon_nil_single"
					value="${totalcon_nil_single+project.con_nil_single}" />
				<c:set var="totalproj_single_double"
					value="${totalproj_single_double+project.proj_single_double}" />
				<c:set var="totalcon_single_double"
					value="${totalcon_single_double+project.con_single_double}" />
					
				<c:set var="totalproj_whs_rejuvenated"
					value="${totalproj_whs_rejuvenated+project.proj_whs_rejuvenated}" />
				<c:set var="totalcon_whs_rejuvenated"
					value="${totalcon_whs_rejuvenated+project.con_whs_rejuvenated}" />
				<c:set var="totalproj_soil_moist"
					value="${totalproj_soil_moist+project.proj_soil_moist}" />
				<c:set var="totalcon_soil_moist"
					value="${totalcon_soil_moist+project.con_soil_moist}" />
				<c:set var="totalproj_deg_rain"
					value="${totalproj_deg_rain+project.proj_deg_rain}" />
				<c:set var="totalcon_deg_rain"
					value="${totalcon_deg_rain+project.con_deg_rain}" />
			</c:forEach>
			<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalproject}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_div_change}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_div_change}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_nil_single}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_nil_single}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_single_double}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_single_double}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totalproj_whs_rejuvenated}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcon_whs_rejuvenated}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_soil_moist}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_soil_moist}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_deg_rain}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_deg_rain}" /></fmt:formatNumber></b></td>
				
			</tr>	 
			</tbody>		
</thead>

</table>
</div>
</c:if>

<c:if test ="${distListSize >0}">
<form action="downloadblsurveyPDF" method="post" name="getcropdtlrpt">
	<input type="hidden" name="stcode" id="stcode" value="" />
	     	 	<input type="hidden" name="stname" id="stname" value="" />
</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report PE4-District wise Cropped Detail Area</label></h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel1('${stcode}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF1('${stcode}','${stname}')" class="btn btn-info">PDF</button> 
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="pdfBasicExample" class="table">
<!-- 	<tbody id="dtBasicExample"> -->
		<thead>
			<tr>
			    <th class="text-left" colspan="13">State: ${stname}</th>
				<th class="text-right" colspan="2">All area in ha.</th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">District</th>
				<th rowspan="3" class="text-center">Total Project</th>
				<th colspan="2" rowspan="2" class="text-center">Area covered under diversified crops/ change in cropping system</th>
				<th colspan="4" class="text-center">Area brought from Nil/Single crop to double or more crop</th>
				<th colspan="2" rowspan="2" class="text-center">No. of Water Harvesting Structure (WHS) constructed /rejuvenated</th>
				<th colspan="2" rowspan="2" class="text-center">Area Covered with soil and Moisture</th>
				<th colspan="2" rowspan="2" class="text-center">Area of degraded land covered /rainfed area developed</th>
			</tr>
			
			<tr>
			<th colspan="2" class="text-center">Nil to single crop</th>
			<th colspan="2" class="text-center">Single to double or more crop</th>
			</tr>
			
			<tr>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			</tr>
			
			<tbody id="dtBasicExample">
			<c:forEach items="${distList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<td class="text-right"><a href="getProjwiseCroppedDtlArea?dcode=${project.dcode}&&distname=${project.dist_name}&&stcode=${stcode}&&stname=${stname}"><c:out value="${project.dist_name}" /></a></td>
				 	<td class="text-right"><c:out value="${project.project}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_div_change}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_div_change}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_nil_single}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_nil_single}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_single_double}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_single_double}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.proj_whs_rejuvenated}" /></td>
					<td class="text-right"><c:out value="${project.con_whs_rejuvenated}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_soil_moist}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_soil_moist}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_deg_rain}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_deg_rain}" /></fmt:formatNumber></td>
					
			</tr>
			
			<c:set var="totalproject"
					value="${totalproject+project.project}" />
				<c:set var="totalproj_div_change"
					value="${totalproj_div_change+project.proj_div_change}" />
				<c:set var="totalcon_div_change"
					value="${totalcon_div_change+project.con_div_change}" />
				<c:set var="totalproj_nil_single"
					value="${totalproj_nil_single+project.proj_nil_single}" />
				<c:set var="totalcon_nil_single"
					value="${totalcon_nil_single+project.con_nil_single}" />
				<c:set var="totalproj_single_double"
					value="${totalproj_single_double+project.proj_single_double}" />
				<c:set var="totalcon_single_double"
					value="${totalcon_single_double+project.con_single_double}" />
					
				<c:set var="totalproj_whs_rejuvenated"
					value="${totalproj_whs_rejuvenated+project.proj_whs_rejuvenated}" />
				<c:set var="totalcon_whs_rejuvenated"
					value="${totalcon_whs_rejuvenated+project.con_whs_rejuvenated}" />
				<c:set var="totalproj_soil_moist"
					value="${totalproj_soil_moist+project.proj_soil_moist}" />
				<c:set var="totalcon_soil_moist"
					value="${totalcon_soil_moist+project.con_soil_moist}" />
				<c:set var="totalproj_deg_rain"
					value="${totalproj_deg_rain+project.proj_deg_rain}" />
				<c:set var="totalcon_deg_rain"
					value="${totalcon_deg_rain+project.con_deg_rain}" />
			</c:forEach>
			<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalproject}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_div_change}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_div_change}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_nil_single}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_nil_single}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_single_double}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_single_double}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totalproj_whs_rejuvenated}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcon_whs_rejuvenated}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_soil_moist}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_soil_moist}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_deg_rain}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_deg_rain}" /></fmt:formatNumber></b></td>
				
			</tr>	 
			</tbody>		
</thead>

</table>
</div>
</c:if>

<c:if test ="${projListSize >0}">
<form action="downloadblsurveyPDF" method="post" name="getcropdtlrpt">
	<input type="hidden" name="stcode" id="stcode" value="" />
	     	 	<input type="hidden" name="stname" id="stname" value="" />
	     	 	<input type="hidden" name="dcode" id="dcode" value="" />
	     	 	<input type="hidden" name="distname" id="distname" value="" />
</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5><label id="head1">Report PE4-Project wise Cropped Detail Area</label></h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel2('${stcode}','${stname}','${dcode}','${distname}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF2('${stcode}','${stname}','${dcode}','${distname}')" class="btn btn-info">PDF</button> 
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id="pdfBasicExample" class="table">
<!-- 	<tbody id="dtBasicExample"> -->
		<thead>
			<tr>
			    <th class="text-left" colspan="12">State: ${stname} District: ${distname}</th>
				<th class="text-right" colspan="2">All area in ha.</th>
			</tr>
			<tr>
				<th rowspan="3" class="text-center">S.No.</th>
				<th rowspan="3" class="text-center">Project</th>
				<th colspan="2" rowspan="2" class="text-center">Area covered under diversified crops/ change in cropping system</th>
				<th colspan="4" class="text-center">Area brought from Nil/Single crop to double or more crop</th>
				<th colspan="2" rowspan="2" class="text-center">No. of Water Harvesting Structure (WHS) constructed /rejuvenated</th>
				<th colspan="2" rowspan="2" class="text-center">Area Covered with soil and Moisture</th>
				<th colspan="2" rowspan="2" class="text-center">Area of degraded land covered /rainfed area developed</th>
			</tr>
			
			<tr>
			<th colspan="2" class="text-center">Nil to single crop</th>
			<th colspan="2" class="text-center">Single to double or more crop</th>
			</tr>
			
			<tr>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			<th rowspan="2" class="text-center">Project Area</th>
			<th rowspan="2" class="text-center">Controlled Area</th>
			</tr>
			
			<tbody id="dtBasicExample">
			<c:forEach items="${projList}" var="project" varStatus="sno">
				<tr>
					<td class="text-center"><c:out value="${sno.count}" /></td>
					<c:choose>
						<c:when test ="${project.proj_div_change eq 0 && project.con_div_change eq 0 && project.proj_nil_single eq 0 && project.con_nil_single eq 0}">
							<td class="text-right"><c:out value="${project.proj_name}" /></td>
						</c:when>
						<c:otherwise>
							<td class="text-right"><a href="javascript:void(0);" onclick="handleProjectClick(${project.proj_id})"><c:out value="${project.proj_name}" /></a></td>
						</c:otherwise>
					</c:choose>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_div_change}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_div_change}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_nil_single}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_nil_single}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_single_double}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_single_double}" /></fmt:formatNumber></td>
					<td class="text-right"><c:out value="${project.proj_whs_rejuvenated}" /></td>
					<td class="text-right"><c:out value="${project.con_whs_rejuvenated}" /></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_soil_moist}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_soil_moist}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.proj_deg_rain}" /></fmt:formatNumber></td>
					<td class="text-right"><fmt:formatNumber minFractionDigits="4"><c:out value="${project.con_deg_rain}" /></fmt:formatNumber></td>
					
			</tr>
			
				<c:set var="totalproj_div_change"
					value="${totalproj_div_change+project.proj_div_change}" />
				<c:set var="totalcon_div_change"
					value="${totalcon_div_change+project.con_div_change}" />
				<c:set var="totalproj_nil_single"
					value="${totalproj_nil_single+project.proj_nil_single}" />
				<c:set var="totalcon_nil_single"
					value="${totalcon_nil_single+project.con_nil_single}" />
				<c:set var="totalproj_single_double"
					value="${totalproj_single_double+project.proj_single_double}" />
				<c:set var="totalcon_single_double"
					value="${totalcon_single_double+project.con_single_double}" />
					
				<c:set var="totalproj_whs_rejuvenated"
					value="${totalproj_whs_rejuvenated+project.proj_whs_rejuvenated}" />
				<c:set var="totalcon_whs_rejuvenated"
					value="${totalcon_whs_rejuvenated+project.con_whs_rejuvenated}" />
				<c:set var="totalproj_soil_moist"
					value="${totalproj_soil_moist+project.proj_soil_moist}" />
				<c:set var="totalcon_soil_moist"
					value="${totalcon_soil_moist+project.con_soil_moist}" />
				<c:set var="totalproj_deg_rain"
					value="${totalproj_deg_rain+project.proj_deg_rain}" />
				<c:set var="totalcon_deg_rain"
					value="${totalcon_deg_rain+project.con_deg_rain}" />
			</c:forEach>
			<tr>
				<td class="table-primary"></td>
				<td align="right" class="table-primary"><b>Grand Total </b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_div_change}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_div_change}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_nil_single}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_nil_single}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_single_double}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_single_double}" /></fmt:formatNumber></b></td>
				
				<td align="right" class="table-primary"><b><c:out value="${totalproj_whs_rejuvenated}" /></b></td>
				<td align="right" class="table-primary"><b><c:out value="${totalcon_whs_rejuvenated}" /></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_soil_moist}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_soil_moist}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalproj_deg_rain}" /></fmt:formatNumber></b></td>
				<td align="right" class="table-primary"><b><fmt:formatNumber minFractionDigits="4"><c:out value="${totalcon_deg_rain}" /></fmt:formatNumber></b></td>
				
			</tr>	 
			</tbody>		
</thead>

</table>
</div>
</c:if>
<div id="childModal" class="modal1">
    <div class="modal1-content">
        <span class="close" onclick="closeChildModal()">&times;</span>
        <iframe id="childFrame" src="" width="100%" height="95%" frameborder="0"></iframe>
    </div>
</div>

<footer class="text-center">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</footer>
</html>
			