<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE10-  State, District, Project-wise Mid Term Evaluation of  Community Based Organization Details of FPO, SHG and UG</title>

<script type="text/javascript">
var stName = "${stname != null ? stname : ''}";

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



function exportExcel() {
	document.getComBasedShgFpoUgData.action = "downloadExcelComBasedShgFpoUgReport";
	document.getComBasedShgFpoUgData.method = "post";
	document.getComBasedShgFpoUgData.submit();
}

function downloadPDF() {
	document.getComBasedShgFpoUgData.action = "downloadComBasedShgFpoUgReportPdf";
	document.getComBasedShgFpoUgData.method = "post";
	document.getComBasedShgFpoUgData.submit();
}

function exportDExcel(stcode, stname) {
	document.getElementById("stcode").value = stcode;
	document.getElementById("stname").value = stname;
	document.getComBasedShgFpoUgData.action = "downloadExcelDistComBasedShgFpoUgReport";
	document.getComBasedShgFpoUgData.method = "post";
	document.getComBasedShgFpoUgData.submit();
}

function downloadDPDF(stcode, stname) {
	document.getElementById("stcode").value = stcode;
	document.getElementById("stname").value = stname;
	document.getComBasedShgFpoUgData.action = "downloadDistComBasedShgFpoUgReportPdf";
	document.getComBasedShgFpoUgData.method = "post";
	document.getComBasedShgFpoUgData.submit();
}

function exportPExcel(dcode, district, stname){
    document.getElementById("dcode").value=dcode;
    document.getElementById("district").value=district;
    document.getElementById("stname").value=stname;
    document.getComBasedShgFpoUgData.action="downloadExcelProjComBasedShgFpoUgReport";
    document.getComBasedShgFpoUgData.method="post";
    document.getComBasedShgFpoUgData.submit();
}
function downloadPPDF(dcode, district, stname){
    document.getElementById("dcode").value=dcode;
    document.getElementById("district").value=district;
    document.getElementById("stname").value=stname;
    document.getComBasedShgFpoUgData.action="downloadProjComBasedShgFpoUgReportPdf";
    document.getComBasedShgFpoUgData.method="post";
    document.getComBasedShgFpoUgData.submit();
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

<body>
<c:if test = "${listsize>0}">
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE10 - State-wise Mid Term Evaluation of Community Based Organization Details of FPO, SHG and UG</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-12">

	<form:form action="getCommunityBasedData" name="getComBasedShgFpoUgData" id="getComBasedShgFpoUgData" method="get">
    	
 	</form:form>
 
<br>
	<div id ="excelpdf">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="3">S.No.</th>
            <th rowspan="3">State Name</th>
            <th rowspan="3">Total No. of Project</th>
            <th colspan="12" style="text-align: center">Number of Community Based Organization</th>
            <th colspan="12" style="text-align: center">Members in Community Based Organization</th>
        </tr>
        <tr>
            <th colspan="3" style="text-align: center">SHG</th>
            <th colspan="3" style="text-align: center">FPO</th>
            <th colspan="3" style="text-align: center">UG</th>
            <th colspan="3" style="text-align: center">Total</th>
            <th colspan="3" style="text-align: center">SHG</th>
            <th colspan="3" style="text-align: center">FPO</th>
            <th colspan="3" style="text-align: center">UG</th>
            <th colspan="3" style="text-align: center">Total</th>
        </tr>
        <tr>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
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
			<th class="text-center">18</th>
			<th class="text-center">19</th>
			<th class="text-center">20</th>
			<th class="text-center">21</th>
			<th class="text-center">22</th>
			<th class="text-center">23</th>
			<th class="text-center">24</th>
			<th class="text-center">25</th>
			<th class="text-center">26</th>
			<th class="text-center">27</th>
		</tr>
		</thead>
		<tbody id = "getCommunityBasedDataRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totPreComBasedShg" value="0"/>
			<c:set var="totMidComBasedShg" value="0"/>
			<c:set var="totControlComBasedShg" value="0"/>
			<c:set var="totPreComBasedFpo" value="0"/>
			<c:set var="totMidComBasedFpo" value="0"/>
			<c:set var="totControlComBasedFpo" value="0"/>
			<c:set var="totPreComBasedUg" value="0"/>
			<c:set var="totMidComBasedUg" value="0"/>
			<c:set var="totControlComBasedUg" value="0"/>
			<c:set var="totPreComBasedTot" value="0"/>
			<c:set var="totMidComBasedTot" value="0"/>
			<c:set var="totControlComBasedTot" value="0"/>
			<c:set var="totPreMemBsdShg" value="0"/>
			<c:set var="totMidMemBsdShg" value="0"/>
			<c:set var="totControlMemBsdShg" value="0"/>
			<c:set var="totPreMemBsdFpo" value="0"/>
			<c:set var="totMidMemBsdFpo" value="0"/>
			<c:set var="totControlMemBsdFpo" value="0"/>
			<c:set var="totPreMemBsdUg" value="0"/>
			<c:set var="totMidMemBsdUg" value="0"/>
			<c:set var="totControlMemBsdUg" value="0"/>
			<c:set var="totPreMemBsdTot" value="0"/>
			<c:set var="totMidMemBsdTot" value="0"/>
			<c:set var="totControlMemBsdTot" value="0"/>
			<c:forEach items="${list}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a href="getDistwiseCommunityBasedData?stcode=${list.stcode}&&stname=${list.stname}">${list.stname}</a></td>
					<td>${list.totproj}</td>
					<td>${list.precommunitybasedshg}</td>
					<td>${list.midcommunitybasedshg}</td>
					<td>${list.controlcommunitybasedshg}</td>
					<td>${list.precommunitybasedfpo}</td>
					<td>${list.midcommunitybasedfpo}</td>
					<td>${list.controlcommunitybasedfpo}</td>
					<td>${list.precommunitybasedug}</td>
					<td>${list.midcommunitybasedug}</td>
					<td>${list.controlcommunitybasedug}</td>
					<td>${list.precommunitybasedshg + list.precommunitybasedfpo + list.precommunitybasedug}</td>
					<td>${list.midcommunitybasedshg + list.midcommunitybasedfpo + list.midcommunitybasedug}</td>
					<td>${list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}</td>
					<td>${list.prememberbasedshg}</td>
					<td>${list.midmemberbasedshg}</td>
					<td>${list.controlmemberbasedshg}</td>
					<td>${list.prememberbasedfpo}</td>
					<td>${list.midmemberbasedfpo}</td>
					<td>${list.controlmemberbasedfpo}</td>
					<td>${list.prememberbasedug}</td>
					<td>${list.midmemberbasedug}</td>
					<td>${list.controlmemberbasedug}</td>
					<td>${list.prememberbasedshg + list.prememberbasedfpo + list.prememberbasedug}</td>
					<td>${list.midmemberbasedshg + list.midmemberbasedfpo + list.midmemberbasedug}</td>
					<td>${list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totPreComBasedShg" value="${totPreComBasedShg + list.precommunitybasedshg}"/>
			<c:set var="totMidComBasedShg" value="${totMidComBasedShg + list.midcommunitybasedshg}"/>
			<c:set var="totControlComBasedShg" value="${totControlComBasedShg + list.controlcommunitybasedshg}"/>
			<c:set var="totPreComBasedFpo" value="${totPreComBasedFpo + list.precommunitybasedfpo}"/>
			<c:set var="totMidComBasedFpo" value="${totMidComBasedFpo + list.midcommunitybasedfpo}"/>
			<c:set var="totControlComBasedFpo" value="${totControlComBasedFpo + list.controlcommunitybasedfpo}"/>
			<c:set var="totPreComBasedUg" value="${totPreComBasedUg + list.precommunitybasedug}"/>
			<c:set var="totMidComBasedUg" value="${totMidComBasedUg + list.midcommunitybasedug}"/>
			<c:set var="totControlComBasedUg" value="${totControlComBasedUg + list.controlcommunitybasedug}"/>
			<c:set var="totPreComBasedTot" value="${totPreComBasedTot + list.precommunitybasedshg + list.precommunitybasedfpo + list.precommunitybasedug}"/>
			<c:set var="totMidComBasedTot" value="${totMidComBasedTot + list.midcommunitybasedshg + list.midcommunitybasedfpo + list.midcommunitybasedug}"/>
			<c:set var="totControlComBasedTot" value="${totControlComBasedTot + list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}"/>
			
			<c:set var="totPreMemBsdShg" value="${totPreMemBsdShg + list.prememberbasedshg}"/>
			<c:set var="totMidMemBsdShg" value="${totMidMemBsdShg + list.midmemberbasedshg}"/>
			<c:set var="totControlMemBsdShg" value="${totControlMemBsdShg + list.controlmemberbasedshg}"/>
			<c:set var="totPreMemBsdFpo" value="${totPreMemBsdFpo + list.prememberbasedfpo}"/>
			<c:set var="totMidMemBsdFpo" value="${totMidMemBsdFpo + list.midmemberbasedfpo}"/>
			<c:set var="totControlMemBsdFpo" value="${totControlMemBsdFpo + list.controlmemberbasedfpo}"/>
			<c:set var="totPreMemBsdUg" value="${totPreMemBsdUg + list.prememberbasedug}"/>
			<c:set var="totMidMemBsdUg" value="${totMidMemBsdUg + list.midmemberbasedug}"/>
			<c:set var="totControlMemBsdUg" value="${totControlMemBsdUg + list.controlmemberbasedug}"/>
			<c:set var="totPreMemBsdTot" value="${totPreMemBsdTot + list.prememberbasedshg + list.prememberbasedfpo + list.prememberbasedug}"/>
			<c:set var="totMidMemBsdTot" value="${totMidMemBsdTot + list.midmemberbasedshg + list.midmemberbasedfpo + list.midmemberbasedug}"/>
			<c:set var="totControlMemBsdTot" value="${totControlMemBsdTot + list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2">Total</td>
				<td>${totproj}</td>
				<td>${totPreComBasedShg}</td>
				<td>${totMidComBasedShg}</td>
				<td>${totControlComBasedShg}</td>
				<td>${totPreComBasedFpo}</td>
				<td>${totMidComBasedFpo}</td>
				<td>${totControlComBasedFpo}</td>
				<td>${totPreComBasedUg}</td>
				<td>${totMidComBasedUg}</td>
				<td>${totControlComBasedUg}</td>
				<td>${totPreComBasedTot}</td>
				<td>${totMidComBasedTot}</td>
				<td>${totControlComBasedTot}</td>
				
				<td>${totPreMemBsdShg}</td>
				<td>${totMidMemBsdShg}</td>
				<td>${totControlMemBsdShg}</td>
				<td>${totPreMemBsdFpo}</td>
				<td>${totMidMemBsdFpo}</td>
				<td>${totControlMemBsdFpo}</td>
				<td>${totPreMemBsdUg}</td>
				<td>${totMidMemBsdUg}</td>
				<td>${totControlMemBsdUg}</td>
				<td>${totPreMemBsdTot}</td>
				<td>${totMidMemBsdTot}</td>
				<td>${totControlMemBsdTot}</td>
			</tr>
			
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>
</c:if>
<c:if test ="${distListSize >0}">

<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE10 - District-wise Mid Term Evaluation of Community Based Organization Details of FPO, SHG and UG of <c:out value ="${stname}"/></label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-12">

	<form:form action="getCommunityBasedData" name="getComBasedShgFpoUgData" id="getComBasedShgFpoUgData" method="get">
    	<input type="hidden" name="stcode" id="stcode" value="" />
    	<input type="hidden" name="stname" id="stname" value="" />
 	</form:form>
 
<br>
	<div id ="excelpdf">
	<button name="exportDExcel" id="exportDExcel" onclick="exportDExcel('${stcode}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportDPDF" id="exportDPDF" onclick="downloadDPDF('${stcode}','${stname}')" class="btn btn-info">PDF</button>  
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="3">S.No.</th>
            <th rowspan="3">District Name</th>
            <th rowspan="3">Total No. of Project</th>
            <th colspan="12" style="text-align: center">Number of Community Based Organization</th>
            <th colspan="12" style="text-align: center">Members in Community Based Organization</th>
        </tr>
        <tr>
            <th colspan="3" style="text-align: center">SHG</th>
            <th colspan="3" style="text-align: center">FPO</th>
            <th colspan="3" style="text-align: center">UG</th>
            <th colspan="3" style="text-align: center">Total</th>
            <th colspan="3" style="text-align: center">SHG</th>
            <th colspan="3" style="text-align: center">FPO</th>
            <th colspan="3" style="text-align: center">UG</th>
            <th colspan="3" style="text-align: center">Total</th>
        </tr>
        <tr>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
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
			<th class="text-center">18</th>
			<th class="text-center">19</th>
			<th class="text-center">20</th>
			<th class="text-center">21</th>
			<th class="text-center">22</th>
			<th class="text-center">23</th>
			<th class="text-center">24</th>
			<th class="text-center">25</th>
			<th class="text-center">26</th>
			<th class="text-center">27</th>
		</tr>
		</thead>
		<tbody id = "getCommunityBasedDataRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totPreComBasedShg" value="0"/>
			<c:set var="totMidComBasedShg" value="0"/>
			<c:set var="totControlComBasedShg" value="0"/>
			<c:set var="totPreComBasedFpo" value="0"/>
			<c:set var="totMidComBasedFpo" value="0"/>
			<c:set var="totControlComBasedFpo" value="0"/>
			<c:set var="totPreComBasedUg" value="0"/>
			<c:set var="totMidComBasedUg" value="0"/>
			<c:set var="totControlComBasedUg" value="0"/>
			<c:set var="totPreComBasedTot" value="0"/>
			<c:set var="totMidComBasedTot" value="0"/>
			<c:set var="totControlComBasedTot" value="0"/>
			<c:set var="totPreMemBsdShg" value="0"/>
			<c:set var="totMidMemBsdShg" value="0"/>
			<c:set var="totControlMemBsdShg" value="0"/>
			<c:set var="totPreMemBsdFpo" value="0"/>
			<c:set var="totMidMemBsdFpo" value="0"/>
			<c:set var="totControlMemBsdFpo" value="0"/>
			<c:set var="totPreMemBsdUg" value="0"/>
			<c:set var="totMidMemBsdUg" value="0"/>
			<c:set var="totControlMemBsdUg" value="0"/>
			<c:set var="totPreMemBsdTot" value="0"/>
			<c:set var="totMidMemBsdTot" value="0"/>
			<c:set var="totControlMemBsdTot" value="0"/>
			<c:forEach items="${distList}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					
					<td><a href="getProjwiseCommunityBasedData?distcd=${list.dcode}&dName=${list.distname}&stName=${stname}">
					<c:out value="${list.distname}"/></a></td>
					<%-- <td>${list.distname}</td> --%>
					
					<td>${list.totproj}</td>
					<td>${list.precommunitybasedshg}</td>
					<td>${list.midcommunitybasedshg}</td>
					<td>${list.controlcommunitybasedshg}</td>
					<td>${list.precommunitybasedfpo}</td>
					<td>${list.midcommunitybasedfpo}</td>
					<td>${list.controlcommunitybasedfpo}</td>
					<td>${list.precommunitybasedug}</td>
					<td>${list.midcommunitybasedug}</td>
					<td>${list.controlcommunitybasedug}</td>
					<td>${list.precommunitybasedshg + list.precommunitybasedfpo + list.precommunitybasedug}</td>
					<td>${list.midcommunitybasedshg + list.midcommunitybasedfpo + list.midcommunitybasedug}</td>
					<td>${list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}</td>
					<td>${list.prememberbasedshg}</td>
					<td>${list.midmemberbasedshg}</td>
					<td>${list.controlmemberbasedshg}</td>
					<td>${list.prememberbasedfpo}</td>
					<td>${list.midmemberbasedfpo}</td>
					<td>${list.controlmemberbasedfpo}</td>
					<td>${list.prememberbasedug}</td>
					<td>${list.midmemberbasedug}</td>
					<td>${list.controlmemberbasedug}</td>
					<td>${list.prememberbasedshg + list.prememberbasedfpo + list.prememberbasedug}</td>
					<td>${list.midmemberbasedshg + list.midmemberbasedfpo + list.midmemberbasedug}</td>
					<td>${list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totPreComBasedShg" value="${totPreComBasedShg + list.precommunitybasedshg}"/>
			<c:set var="totMidComBasedShg" value="${totMidComBasedShg + list.midcommunitybasedshg}"/>
			<c:set var="totControlComBasedShg" value="${totControlComBasedShg + list.controlcommunitybasedshg}"/>
			<c:set var="totPreComBasedFpo" value="${totPreComBasedFpo + list.precommunitybasedfpo}"/>
			<c:set var="totMidComBasedFpo" value="${totMidComBasedFpo + list.midcommunitybasedfpo}"/>
			<c:set var="totControlComBasedFpo" value="${totControlComBasedFpo + list.controlcommunitybasedfpo}"/>
			<c:set var="totPreComBasedUg" value="${totPreComBasedUg + list.precommunitybasedug}"/>
			<c:set var="totMidComBasedUg" value="${totMidComBasedUg + list.midcommunitybasedug}"/>
			<c:set var="totControlComBasedUg" value="${totControlComBasedUg + list.controlcommunitybasedug}"/>
			<c:set var="totPreComBasedTot" value="${totPreComBasedTot + list.precommunitybasedshg + list.precommunitybasedfpo + list.precommunitybasedug}"/>
			<c:set var="totMidComBasedTot" value="${totMidComBasedTot + list.midcommunitybasedshg + list.midcommunitybasedfpo + list.midcommunitybasedug}"/>
			<c:set var="totControlComBasedTot" value="${totControlComBasedTot + list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}"/>
			
			<c:set var="totPreMemBsdShg" value="${totPreMemBsdShg + list.prememberbasedshg}"/>
			<c:set var="totMidMemBsdShg" value="${totMidMemBsdShg + list.midmemberbasedshg}"/>
			<c:set var="totControlMemBsdShg" value="${totControlMemBsdShg + list.controlmemberbasedshg}"/>
			<c:set var="totPreMemBsdFpo" value="${totPreMemBsdFpo + list.prememberbasedfpo}"/>
			<c:set var="totMidMemBsdFpo" value="${totMidMemBsdFpo + list.midmemberbasedfpo}"/>
			<c:set var="totControlMemBsdFpo" value="${totControlMemBsdFpo + list.controlmemberbasedfpo}"/>
			<c:set var="totPreMemBsdUg" value="${totPreMemBsdUg + list.prememberbasedug}"/>
			<c:set var="totMidMemBsdUg" value="${totMidMemBsdUg + list.midmemberbasedug}"/>
			<c:set var="totControlMemBsdUg" value="${totControlMemBsdUg + list.controlmemberbasedug}"/>
			<c:set var="totPreMemBsdTot" value="${totPreMemBsdTot + list.prememberbasedshg + list.prememberbasedfpo + list.prememberbasedug}"/>
			<c:set var="totMidMemBsdTot" value="${totMidMemBsdTot + list.midmemberbasedshg + list.midmemberbasedfpo + list.midmemberbasedug}"/>
			<c:set var="totControlMemBsdTot" value="${totControlMemBsdTot + list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2">Total</td>
				<td>${totproj}</td>
				<td>${totPreComBasedShg}</td>
				<td>${totMidComBasedShg}</td>
				<td>${totControlComBasedShg}</td>
				<td>${totPreComBasedFpo}</td>
				<td>${totMidComBasedFpo}</td>
				<td>${totControlComBasedFpo}</td>
				<td>${totPreComBasedUg}</td>
				<td>${totMidComBasedUg}</td>
				<td>${totControlComBasedUg}</td>
				<td>${totPreComBasedTot}</td>
				<td>${totMidComBasedTot}</td>
				<td>${totControlComBasedTot}</td>
				
				<td>${totPreMemBsdShg}</td>
				<td>${totMidMemBsdShg}</td>
				<td>${totControlMemBsdShg}</td>
				<td>${totPreMemBsdFpo}</td>
				<td>${totMidMemBsdFpo}</td>
				<td>${totControlMemBsdFpo}</td>
				<td>${totPreMemBsdUg}</td>
				<td>${totMidMemBsdUg}</td>
				<td>${totControlMemBsdUg}</td>
				<td>${totPreMemBsdTot}</td>
				<td>${totMidMemBsdTot}</td>
				<td>${totControlMemBsdTot}</td>
			</tr>
			
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>

</c:if>

<c:if test ="${projListSize >0}">

<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE10 - Project-wise Mid Term Evaluation of Community Based Organization Details of FPO, SHG and UG of <c:out value ="${stname}"/> (<c:out value ="${district}"/>)</label>
		</h5>
	</div>
<br>

<div class ="card">
		<div class="row">
			<div class="col-12">

	<form:form action="getCommunityBasedData" name="getComBasedShgFpoUgData" id="getComBasedShgFpoUgData" method="get">
    	<input type="hidden" name="dcode" id="dcode" value="" />
    	<input type="hidden" name="district" id="district" value="" />
    	<input type="hidden" name="stname" id="stname" value="" />
 	</form:form>
 
<br>
	<div id ="excelpdf">
	<button name="exportPExcel" id="exportPExcel" onclick="exportPExcel('${dcode}','${district}', '${stname}')" class="btn btn-info">Excel</button>
	<button name="exportPPDF" id="exportPPDF" onclick="downloadPPDF('${dcode}','${district}', '${stname}')" class="btn btn-info">PDF</button>  
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="3">S.No.</th>
            <th rowspan="3">Project Name</th>
            <th colspan="12" style="text-align: center">Number of Community Based Organization</th>
            <th colspan="12" style="text-align: center">Members in Community Based Organization</th>
        </tr>
        <tr>
            <th colspan="3" style="text-align: center">SHG</th>
            <th colspan="3" style="text-align: center">FPO</th>
            <th colspan="3" style="text-align: center">UG</th>
            <th colspan="3" style="text-align: center">Total</th>
            <th colspan="3" style="text-align: center">SHG</th>
            <th colspan="3" style="text-align: center">FPO</th>
            <th colspan="3" style="text-align: center">UG</th>
            <th colspan="3" style="text-align: center">Total</th>
        </tr>
        <tr>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
			<th>Pre Project Status(Aggregate)</th>
			<th>Mid Project Status(Aggregate)</th>
			<th>Controlled Area</th>
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
			<th class="text-center">18</th>
			<th class="text-center">19</th>
			<th class="text-center">20</th>
			<th class="text-center">21</th>
			<th class="text-center">22</th>
			<th class="text-center">23</th>
			<th class="text-center">24</th>
			<th class="text-center">25</th>
			<th class="text-center">26</th>
		</tr>
		</thead>
		<tbody id = "getCommunityBasedDataRptTbody">
			<%-- <c:set var="totproj" value="0"/> --%>
			<c:set var="totPreComBasedShg" value="0"/>
			<c:set var="totMidComBasedShg" value="0"/>
			<c:set var="totControlComBasedShg" value="0"/>
			<c:set var="totPreComBasedFpo" value="0"/>
			<c:set var="totMidComBasedFpo" value="0"/>
			<c:set var="totControlComBasedFpo" value="0"/>
			<c:set var="totPreComBasedUg" value="0"/>
			<c:set var="totMidComBasedUg" value="0"/>
			<c:set var="totControlComBasedUg" value="0"/>
			<c:set var="totPreComBasedTot" value="0"/>
			<c:set var="totMidComBasedTot" value="0"/>
			<c:set var="totControlComBasedTot" value="0"/>
			<c:set var="totPreMemBsdShg" value="0"/>
			<c:set var="totMidMemBsdShg" value="0"/>
			<c:set var="totControlMemBsdShg" value="0"/>
			<c:set var="totPreMemBsdFpo" value="0"/>
			<c:set var="totMidMemBsdFpo" value="0"/>
			<c:set var="totControlMemBsdFpo" value="0"/>
			<c:set var="totPreMemBsdUg" value="0"/>
			<c:set var="totMidMemBsdUg" value="0"/>
			<c:set var="totControlMemBsdUg" value="0"/>
			<c:set var="totPreMemBsdTot" value="0"/>
			<c:set var="totMidMemBsdTot" value="0"/>
			<c:set var="totControlMemBsdTot" value="0"/>
			<c:forEach items="${projList}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					
					<td align="left">
								<c:choose>
                                   <c:when test="${list.status ne null}">
                                   <a href="javascript:void(0);" onclick="handleProjectClick(${list.proj_id})">
                                            <c:out value="${list.proj_name}" />
                                        </a>
                                   </c:when>
                              <c:otherwise>
                                <c:out value="${list.proj_name}"/>
                              </c:otherwise>
                              </c:choose>
                              </td>
					<td>${list.precommunitybasedshg}</td>
					<td>${list.midcommunitybasedshg}</td>
					<td>${list.controlcommunitybasedshg}</td>
					<td>${list.precommunitybasedfpo}</td>
					<td>${list.midcommunitybasedfpo}</td>
					<td>${list.controlcommunitybasedfpo}</td>
					<td>${list.precommunitybasedug}</td>
					<td>${list.midcommunitybasedug}</td>
					<td>${list.controlcommunitybasedug}</td>
					<td>${list.precommunitybasedshg + list.precommunitybasedfpo + list.precommunitybasedug}</td>
					<td>${list.midcommunitybasedshg + list.midcommunitybasedfpo + list.midcommunitybasedug}</td>
					<td>${list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}</td>
					<td>${list.prememberbasedshg}</td>
					<td>${list.midmemberbasedshg}</td>
					<td>${list.controlmemberbasedshg}</td>
					<td>${list.prememberbasedfpo}</td>
					<td>${list.midmemberbasedfpo}</td>
					<td>${list.controlmemberbasedfpo}</td>
					<td>${list.prememberbasedug}</td>
					<td>${list.midmemberbasedug}</td>
					<td>${list.controlmemberbasedug}</td>
					<td>${list.prememberbasedshg + list.prememberbasedfpo + list.prememberbasedug}</td>
					<td>${list.midmemberbasedshg + list.midmemberbasedfpo + list.midmemberbasedug}</td>
					<td>${list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}</td>
				</tr>
			<c:set var="totPreComBasedShg" value="${totPreComBasedShg + list.precommunitybasedshg}"/>
			<c:set var="totMidComBasedShg" value="${totMidComBasedShg + list.midcommunitybasedshg}"/>
			<c:set var="totControlComBasedShg" value="${totControlComBasedShg + list.controlcommunitybasedshg}"/>
			<c:set var="totPreComBasedFpo" value="${totPreComBasedFpo + list.precommunitybasedfpo}"/>
			<c:set var="totMidComBasedFpo" value="${totMidComBasedFpo + list.midcommunitybasedfpo}"/>
			<c:set var="totControlComBasedFpo" value="${totControlComBasedFpo + list.controlcommunitybasedfpo}"/>
			<c:set var="totPreComBasedUg" value="${totPreComBasedUg + list.precommunitybasedug}"/>
			<c:set var="totMidComBasedUg" value="${totMidComBasedUg + list.midcommunitybasedug}"/>
			<c:set var="totControlComBasedUg" value="${totControlComBasedUg + list.controlcommunitybasedug}"/>
			<c:set var="totPreComBasedTot" value="${totPreComBasedTot + list.precommunitybasedshg + list.precommunitybasedfpo + list.precommunitybasedug}"/>
			<c:set var="totMidComBasedTot" value="${totMidComBasedTot + list.midcommunitybasedshg + list.midcommunitybasedfpo + list.midcommunitybasedug}"/>
			<c:set var="totControlComBasedTot" value="${totControlComBasedTot + list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}"/>
			
			<c:set var="totPreMemBsdShg" value="${totPreMemBsdShg + list.prememberbasedshg}"/>
			<c:set var="totMidMemBsdShg" value="${totMidMemBsdShg + list.midmemberbasedshg}"/>
			<c:set var="totControlMemBsdShg" value="${totControlMemBsdShg + list.controlmemberbasedshg}"/>
			<c:set var="totPreMemBsdFpo" value="${totPreMemBsdFpo + list.prememberbasedfpo}"/>
			<c:set var="totMidMemBsdFpo" value="${totMidMemBsdFpo + list.midmemberbasedfpo}"/>
			<c:set var="totControlMemBsdFpo" value="${totControlMemBsdFpo + list.controlmemberbasedfpo}"/>
			<c:set var="totPreMemBsdUg" value="${totPreMemBsdUg + list.prememberbasedug}"/>
			<c:set var="totMidMemBsdUg" value="${totMidMemBsdUg + list.midmemberbasedug}"/>
			<c:set var="totControlMemBsdUg" value="${totControlMemBsdUg + list.controlmemberbasedug}"/>
			<c:set var="totPreMemBsdTot" value="${totPreMemBsdTot + list.prememberbasedshg + list.prememberbasedfpo + list.prememberbasedug}"/>
			<c:set var="totMidMemBsdTot" value="${totMidMemBsdTot + list.midmemberbasedshg + list.midmemberbasedfpo + list.midmemberbasedug}"/>
			<c:set var="totControlMemBsdTot" value="${totControlMemBsdTot + list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2">Total</td>
				<td>${totPreComBasedShg}</td>
				<td>${totMidComBasedShg}</td>
				<td>${totControlComBasedShg}</td>
				<td>${totPreComBasedFpo}</td>
				<td>${totMidComBasedFpo}</td>
				<td>${totControlComBasedFpo}</td>
				<td>${totPreComBasedUg}</td>
				<td>${totMidComBasedUg}</td>
				<td>${totControlComBasedUg}</td>
				<td>${totPreComBasedTot}</td>
				<td>${totMidComBasedTot}</td>
				<td>${totControlComBasedTot}</td>
				
				<td>${totPreMemBsdShg}</td>
				<td>${totMidMemBsdShg}</td>
				<td>${totControlMemBsdShg}</td>
				<td>${totPreMemBsdFpo}</td>
				<td>${totMidMemBsdFpo}</td>
				<td>${totControlMemBsdFpo}</td>
				<td>${totPreMemBsdUg}</td>
				<td>${totMidMemBsdUg}</td>
				<td>${totControlMemBsdUg}</td>
				<td>${totPreMemBsdTot}</td>
				<td>${totMidMemBsdTot}</td>
				<td>${totControlMemBsdTot}</td>
			</tr>
			
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>
</c:if>
	<div id="childModal" class="modal1">
    <div class="modal1-content">
        <span class="close" onclick="closeChildModal()">&times;</span>
        <iframe id="childFrame" src="" width="100%" height="95%" frameborder="0"></iframe>
    </div>
</div>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>