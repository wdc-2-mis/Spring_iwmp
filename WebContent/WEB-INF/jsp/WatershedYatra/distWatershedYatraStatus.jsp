<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Report - District wise Watershed Yatra Activity Status</title>

<script type="text/javascript">

function downloadPDF(stcd, stName)
{
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	
    document.getreport.action="downloadPDFDistWatershedYatraStatusReport";
	document.getreport.method="post";
	document.getreport.submit();
}

function exportExcel(stcd, stName)
{
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	
    document.getreport.action="downloadExcelDistWatershedYatraStatusReport";
	document.getreport.method="post";
	document.getreport.submit();
}

</script>

</head>

<body>
<br>
<form action="downloadblOutcomePDF" method="post" name="getreport">

	<input type="hidden" name="stcd" id="stcd" value="" />
	<input type="hidden" name="stName" id="stName" value="" />
	
</form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>Report - District wise Watershed Yatra Activity Status</h5>
	</div>
  	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${stName}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}', '${stName}')" class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

<table id="tblReport" class="table">
	<thead>
		<tr>
			<th colspan=14 class="text-left">State : ${stName} &emsp; Inauguration Date : ${inaugurationDate}</th>
		</tr>
		<tr>
			<th rowspan="3" class="text-center">S.No.</th>
			<th rowspan="3" class="text-center">District</th>
			<th rowspan="3" class="text-center">Total Projects</th>
			<th rowspan="3" class="text-center">Location Planned</th>
			<th rowspan="3" class="text-center">Location Completed</th>
			<th colspan="2" class="text-center">Activity</th>
			<th colspan="2" class="text-center">No. of Locations Where Pre Yatra Activities Completed</th>
			<th rowspan="2" class="text-center">AR Experience (No. of Peoples)</th>
			<th rowspan="2" class="text-center">Bhoomi Poojan (No. of Works)</th>
			<th rowspan="2" class="text-center">Lokarpan (No. of Works)</th>
			<th rowspan="2" class="text-center">Sharmdaan (No. of Locations)</th>
			<th rowspan="2" class="text-center">Plantation (No. of Agro Forestry)</th>
		</tr>
		
		<tr>
			<th rowspan="3" class="text-center">Completed Activity</th>
			<th rowspan="3" class="text-center">Not Completed Activity</th>
			<th rowspan="2" class="text-center">Gram Sabha</th>
			<th rowspan="2" class="text-center">Prabhat Phere</th>
		</tr>
	</thead>

	<tbody id="distwiseAreaRptTbody">
		<c:forEach items="${getRecords}" var="project" varStatus="sno">
			<tr>
				<td class="text-center"><c:out value="${sno.count}" /></td>
                    <%-- <td><a href="getDistwiseWSYatraStatusDetail?stcode=<c:out value="${project.st_code}"/>&stname=<c:out value="${project.st_name}" />"><c:out value="${project.st_name}" /></a></td> --%>
				<td><c:out value="${project.dist_name}" /></td>
				<td class="text-right"><c:out value="${project.total_project}" /></td>
				<td class="text-right"><c:out value="${project.total_vanplan}" /></td>
				<td class="text-right"><c:out value="${project.total_locv}" /></td>
				<td class="text-right"><c:out value="${project.activity_entered}" /></td>
				<td class="text-right"><c:out value="${project.act_not_entered}" /></td>
				<td class="text-right"><c:out value="${project.gramsabha}" /></td>
				<td class="text-right"><c:out value="${project.prabhatpheri}" /></td>
				<td class="text-right"><c:out value="${project.total_arexp}" /></td>
				<td class="text-right"><c:out value="${project.total_bhoomi_poojan}" /></td>
				<td class="text-right"><c:out value="${project.total_lokarpan}" /></td>
				<td class="text-right"><c:out value="${project.total_shramdaan}" /></td>
				<td class="text-right"><c:out value="${project.total_plantation}" /></td>
			</tr>
		</c:forEach>	
	</tbody>
			
	<tfoot>
    	<tr>
        	<td  align="right" class="table-primary" colspan="2"><b>Grand Total</b></td>
        	<td align="right" class="table-primary" id="totalProjects"></td>
        	<td align="right" class="table-primary" id="totalVanplan"></td>
        	<td align="right" class="table-primary" id="totalLocv"></td>
        	<td align="right" class="table-primary" id="totalActivityEntered"></td>
        	<td align="right" class="table-primary" id="totalActNotEntered"></td>
        	<td align="right" class="table-primary" id="totalGramsabha"></td>
        	<td align="right" class="table-primary" id="totalPrabhatpheri"></td>
        	<td align="right" class="table-primary" id="totalArexp"></td>
        	<td align="right" class="table-primary" id="totalBhoomiPoojan"></td>
        	<td align="right" class="table-primary" id="totalLokarpan"></td>
        	<td align="right" class="table-primary" id="totalShramdaan"></td>
        	<td align="right" class="table-primary" id="totalPlantation"></td>
    	</tr>
	</tfoot>		
</table>

</div>



<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

</body>
<script type="text/javascript">
window.onload = function() {
    const tbody = document.getElementById('distwiseAreaRptTbody');
    const totalProjects = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[2].innerText || 0), 0);
    const totalVanplan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[3].innerText || 0), 0);
    const totalLocv = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[4].innerText || 0), 0);
    const totalActivityEntered = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[5].innerText || 0), 0);
    const totalActNotEntered = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[6].innerText || 0), 0);
    const totalGramsabha = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[7].innerText || 0), 0);
    const totalPrabhatpheri = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[8].innerText || 0), 0);
    const totalArexp = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[9].innerText || 0), 0);
    const totalBhoomiPoojan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[10].innerText || 0), 0);
    const totalLokarpan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[11].innerText || 0), 0);
    const totalShramdaan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[12].innerText || 0), 0);
    const totalPlantation = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[13].innerText || 0), 0);

    document.getElementById('totalProjects').innerHTML = '<b>' + totalProjects + '</b>';
    document.getElementById('totalVanplan').innerHTML = '<b>' + totalVanplan + '</b>';
    document.getElementById('totalLocv').innerHTML = '<b>' + totalLocv + '</b>';
    document.getElementById('totalActivityEntered').innerHTML = '<b>' + totalActivityEntered + '</b>';
    document.getElementById('totalActNotEntered').innerHTML = '<b>' + totalActNotEntered + '</b>';
    document.getElementById('totalGramsabha').innerHTML = '<b>' + totalGramsabha + '</b>';
    document.getElementById('totalPrabhatpheri').innerHTML = '<b>' + totalPrabhatpheri + '</b>';
    document.getElementById('totalArexp').innerHTML = '<b>' + totalArexp + '</b>';
    document.getElementById('totalBhoomiPoojan').innerHTML = '<b>' + totalBhoomiPoojan + '</b>';
    document.getElementById('totalLokarpan').innerHTML = '<b>' + totalLokarpan + '</b>';
    document.getElementById('totalShramdaan').innerHTML = '<b>' + totalShramdaan + '</b>';
    document.getElementById('totalPlantation').innerHTML = '<b>' + totalPlantation + '</b>';
};

</script>
</html>
