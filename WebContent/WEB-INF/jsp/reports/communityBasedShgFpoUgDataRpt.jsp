<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE10-  State-wise Mid Term Evaluation of State-wise Mid Term Evaluation of Community Based Organization Details of FPO, SHG and UG</title>

<script type="text/javascript">

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
	
</script>


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
            <th colspan="8" style="text-align: center">Number of Community Based Organization</th>
            <th colspan="8" style="text-align: center">Members in Community Based Organization</th>
        </tr>
        <tr>
            <th colspan="2" style="text-align: center">SHG</th>
            <th colspan="2" style="text-align: center">FPO</th>
            <th colspan="2" style="text-align: center">UG</th>
            <th colspan="2" style="text-align: center">Total</th>
            <th colspan="2" style="text-align: center">SHG</th>
            <th colspan="2" style="text-align: center">FPO</th>
            <th colspan="2" style="text-align: center">UG</th>
            <th colspan="2" style="text-align: center">Total</th>
        </tr>
        <tr>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
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
		</tr>
		</thead>
		<tbody id = "getCommunityBasedDataRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totComBasedShg" value="0"/>
			<c:set var="totControlComBasedShg" value="0"/>
			<c:set var="totComBasedFpo" value="0"/>
			<c:set var="totControlComBasedFpo" value="0"/>
			<c:set var="totComBasedUg" value="0"/>
			<c:set var="totControlComBasedUg" value="0"/>
			<c:set var="totComBasedTot" value="0"/>
			<c:set var="totControlComBasedTot" value="0"/>
			<c:set var="totMemBsdShg" value="0"/>
			<c:set var="totControlMemBsdShg" value="0"/>
			<c:set var="totMemBsdFpo" value="0"/>
			<c:set var="totControlMemBsdFpo" value="0"/>
			<c:set var="totMemBsdUg" value="0"/>
			<c:set var="totControlMemBsdUg" value="0"/>
			<c:set var="totMemBsdTot" value="0"/>
			<c:set var="totControlMemBsdTot" value="0"/>
			<c:forEach items="${list}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a href="getDistwiseCommunityBasedData?stcode=${list.stcode}&&stname=${list.stname}">${list.stname}</a></td>
					<td>${list.totproj}</td>
					<td>${list.communitybasedshg}</td>
					<td>${list.controlcommunitybasedshg}</td>
					<td>${list.communitybasedfpo}</td>
					
					<td>${list.controlcommunitybasedfpo}</td>
					<td>${list.communitybasedug}</td>
					<td>${list.controlcommunitybasedug}</td>
					<td>${list.communitybasedshg + list.communitybasedfpo + list.communitybasedug}</td>
					<td>${list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}</td>
					<td>${list.memberbasedshg}</td>
					<td>${list.controlmemberbasedshg}</td>
					<td>${list.memberbasedfpo}</td>
					<td>${list.controlmemberbasedfpo}</td>
					<td>${list.memberbasedug}</td>
					<td>${list.controlmemberbasedug}</td>
					<td>${list.memberbasedshg + list.memberbasedfpo + list.memberbasedug}</td>
					<td>${list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totComBasedShg" value="${totComBasedShg + list.communitybasedshg}"/>
			<c:set var="totControlComBasedShg" value="${totControlComBasedShg + list.controlcommunitybasedshg}"/>
			<c:set var="totComBasedFpo" value="${totComBasedFpo + list.communitybasedfpo}"/>
			<c:set var="totControlComBasedFpo" value="${totControlComBasedFpo + list.controlcommunitybasedfpo}"/>
			<c:set var="totComBasedUg" value="${totComBasedUg + list.communitybasedug}"/>
			<c:set var="totControlComBasedUg" value="${totControlComBasedUg + list.controlcommunitybasedug}"/>
			<c:set var="totComBasedTot" value="${totComBasedTot + list.communitybasedshg + list.communitybasedfpo + list.communitybasedug}"/>
			<c:set var="totControlComBasedTot" value="${totControlComBasedTot + list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}"/>
			
			<c:set var="totMemBsdShg" value="${totMemBsdShg + list.memberbasedshg}"/>
			<c:set var="totControlMemBsdShg" value="${totControlMemBsdShg + list.controlmemberbasedshg}"/>
			<c:set var="totMemBsdFpo" value="${totMemBsdFpo + list.memberbasedfpo}"/>
			<c:set var="totControlMemBsdFpo" value="${totControlMemBsdFpo + list.controlmemberbasedfpo}"/>
			<c:set var="totMemBsdUg" value="${totMemBsdUg + list.memberbasedug}"/>
			<c:set var="totControlMemBsdUg" value="${totControlMemBsdUg + list.controlmemberbasedug}"/>
			<c:set var="totMemBsdTot" value="${totMemBsdTot + list.memberbasedshg + list.memberbasedfpo + list.memberbasedug}"/>
			<c:set var="totControlMemBsdTot" value="${totControlMemBsdTot + list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2">Total</td>
				<td>${totproj}</td>
				<td>${totComBasedShg}</td>
				<td>${totControlComBasedShg}</td>
				<td>${totComBasedFpo}</td>
				<td>${totControlComBasedFpo}</td>
				<td>${totComBasedUg}</td>
				<td>${totControlComBasedUg}</td>
				<td>${totComBasedTot}</td>
				<td>${totControlComBasedTot}</td>
				
				<td>${totMemBsdShg}</td>
				<td>${totControlMemBsdShg}</td>
				<td>${totMemBsdFpo}</td>
				<td>${totControlMemBsdFpo}</td>
				<td>${totMemBsdUg}</td>
				<td>${totControlMemBsdUg}</td>
				<td>${totMemBsdTot}</td>
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
            <th colspan="8" style="text-align: center">Number of Community Based Organization</th>
            <th colspan="8" style="text-align: center">Members in Community Based Organization</th>
        </tr>
        <tr>
            <th colspan="2" style="text-align: center">SHG</th>
            <th colspan="2" style="text-align: center">FPO</th>
            <th colspan="2" style="text-align: center">UG</th>
            <th colspan="2" style="text-align: center">Total</th>
            <th colspan="2" style="text-align: center">SHG</th>
            <th colspan="2" style="text-align: center">FPO</th>
            <th colspan="2" style="text-align: center">UG</th>
            <th colspan="2" style="text-align: center">Total</th>
        </tr>
        <tr>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
			<th>Controlled Area</th>
			<th>Project Area</th>
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
		</tr>
		</thead>
		<tbody id = "getCommunityBasedDataRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totComBasedShg" value="0"/>
			<c:set var="totControlComBasedShg" value="0"/>
			<c:set var="totComBasedFpo" value="0"/>
			<c:set var="totControlComBasedFpo" value="0"/>
			<c:set var="totComBasedUg" value="0"/>
			<c:set var="totControlComBasedUg" value="0"/>
			<c:set var="totComBasedTot" value="0"/>
			<c:set var="totControlComBasedTot" value="0"/>
			<c:set var="totMemBsdShg" value="0"/>
			<c:set var="totControlMemBsdShg" value="0"/>
			<c:set var="totMemBsdFpo" value="0"/>
			<c:set var="totControlMemBsdFpo" value="0"/>
			<c:set var="totMemBsdUg" value="0"/>
			<c:set var="totControlMemBsdUg" value="0"/>
			<c:set var="totMemBsdTot" value="0"/>
			<c:set var="totControlMemBsdTot" value="0"/>
			<c:forEach items="${distList}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${list.distname}</td>
					<td>${list.totproj}</td>
					<td>${list.communitybasedshg}</td>
					<td>${list.controlcommunitybasedshg}</td>
					<td>${list.communitybasedfpo}</td>
					
					<td>${list.controlcommunitybasedfpo}</td>
					<td>${list.communitybasedug}</td>
					<td>${list.controlcommunitybasedug}</td>
					<td>${list.communitybasedshg + list.communitybasedfpo + list.communitybasedug}</td>
					<td>${list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}</td>
					<td>${list.memberbasedshg}</td>
					<td>${list.controlmemberbasedshg}</td>
					<td>${list.memberbasedfpo}</td>
					<td>${list.controlmemberbasedfpo}</td>
					<td>${list.memberbasedug}</td>
					<td>${list.controlmemberbasedug}</td>
					<td>${list.memberbasedshg + list.memberbasedfpo + list.memberbasedug}</td>
					<td>${list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totComBasedShg" value="${totComBasedShg + list.communitybasedshg}"/>
			<c:set var="totControlComBasedShg" value="${totControlComBasedShg + list.controlcommunitybasedshg}"/>
			<c:set var="totComBasedFpo" value="${totComBasedFpo + list.communitybasedfpo}"/>
			<c:set var="totControlComBasedFpo" value="${totControlComBasedFpo + list.controlcommunitybasedfpo}"/>
			<c:set var="totComBasedUg" value="${totComBasedUg + list.communitybasedug}"/>
			<c:set var="totControlComBasedUg" value="${totControlComBasedUg + list.controlcommunitybasedug}"/>
			<c:set var="totComBasedTot" value="${totComBasedTot + list.communitybasedshg + list.communitybasedfpo + list.communitybasedug}"/>
			<c:set var="totControlComBasedTot" value="${totControlComBasedTot + list.controlcommunitybasedshg + list.controlcommunitybasedfpo + list.controlcommunitybasedug}"/>
			
			<c:set var="totMemBsdShg" value="${totMemBsdShg + list.memberbasedshg}"/>
			<c:set var="totControlMemBsdShg" value="${totControlMemBsdShg + list.controlmemberbasedshg}"/>
			<c:set var="totMemBsdFpo" value="${totMemBsdFpo + list.memberbasedfpo}"/>
			<c:set var="totControlMemBsdFpo" value="${totControlMemBsdFpo + list.controlmemberbasedfpo}"/>
			<c:set var="totMemBsdUg" value="${totMemBsdUg + list.memberbasedug}"/>
			<c:set var="totControlMemBsdUg" value="${totControlMemBsdUg + list.controlmemberbasedug}"/>
			<c:set var="totMemBsdTot" value="${totMemBsdTot + list.memberbasedshg + list.memberbasedfpo + list.memberbasedug}"/>
			<c:set var="totControlMemBsdTot" value="${totControlMemBsdTot + list.controlmemberbasedshg + list.controlmemberbasedfpo + list.controlmemberbasedug}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2">Total</td>
				<td>${totproj}</td>
				<td>${totComBasedShg}</td>
				<td>${totControlComBasedShg}</td>
				<td>${totComBasedFpo}</td>
				<td>${totControlComBasedFpo}</td>
				<td>${totComBasedUg}</td>
				<td>${totControlComBasedUg}</td>
				<td>${totComBasedTot}</td>
				<td>${totControlComBasedTot}</td>
				
				<td>${totMemBsdShg}</td>
				<td>${totControlMemBsdShg}</td>
				<td>${totMemBsdFpo}</td>
				<td>${totControlMemBsdFpo}</td>
				<td>${totMemBsdUg}</td>
				<td>${totControlMemBsdUg}</td>
				<td>${totMemBsdTot}</td>
				<td>${totControlMemBsdTot}</td>
			</tr>
			
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>

</c:if>

	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>