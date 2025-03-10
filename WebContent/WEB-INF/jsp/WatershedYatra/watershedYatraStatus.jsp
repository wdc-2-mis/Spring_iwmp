<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Report - State wise Watershed Yatra Activity Status</title>

<script type="text/javascript">

function downloadPDF(){
	
    document.getreport.action="downloadPDFWatershedYatraStatusReport";
	document.getreport.method="post";
	document.getreport.submit();
}

function exportExcel(){
	
    document.getreport.action="downloadExcelWatershedYatraStatusReport";
	document.getreport.method="post";
	document.getreport.submit();
}

</script>

</head>

<body>
<br>
<form action="downloadblOutcomePDF" method="post" name="getreport"></form>
<div class="container-fluid">
	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
		<h5>Report - State wise Watershed Yatra Activity Status</h5>
	</div>
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()"	class="btn btn-info">PDF</button>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>

<table id="tblReport" class="table">
		<thead>
		
		<tr>
		<th rowspan="2" class="text-center">S.No.</th>
		<th rowspan="2" class="text-center">State</th>
<!-- 		<th rowspan="3" class="text-center">Total Projects</th> -->
<!-- 		<th rowspan="3" class="text-center">Location Planned</th> -->
<!-- 		<th rowspan="3" class="text-center">Location Completed</th> -->

		<th rowspan="2" class="text-center">State Inauguration Date</th>
		<th colspan="2" class="text-center">Pre Yatra Activity</th>
		<th colspan="5" class="text-center">Total No. of Participants</th>
		<th rowspan="2" class="text-center">No. of  Locations Covered(Till Date)</th>
		<th rowspan="2" class="text-center">Total No. of Locations to be Covered for Van Activities </th>
		<th rowspan="2" class="text-center">No. of People Availed AR Experience</th>
		<th rowspan="2" class="text-center">No. of Works for Bhoomi Poojan</th>
		<th rowspan="2" class="text-center">No. of Works for Lokarpan </th>
		<th colspan="2" class="text-center">Shramdaan</th>
		
		<th rowspan="2" class="text-center">No. of Sapling Planted</th>
		<th rowspan="2" class="text-center">No. of Watershed Margdarshaks Honored</th>
		<th colspan="2" class="text-center">Activity</th>
		</tr>
		
		<tr>
		<th  class="text-center">Gram Sabha</th>
		<th  class="text-center">Prabhat Phere</th>
		
		<th  class="text-center">Gram Sabha Participants</th>
		<th  class="text-center">Prabhat Phere Participants</th>
		<th  class="text-center">Inauguration Participants</th>
		<th  class="text-center">Village Participants</th>
		<th  class="text-center">Total</th>
		<th  class="text-center">No. of Locations</th>
		<th  class="text-center">No. of People Participated</th>
		<th  class="text-center">Completed Activity</th>
		<th  class="text-center">Not Completed Activity</th>
		
		</tr>
		<tr>
						<% for (int i = 1; i <= 21; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
</thead>

<tbody id="stwiseAreaRptTbody">
    <c:forEach items="${getRecords}" var="project" varStatus="sno">
        <c:set var="gramsabha" value="${not empty project.gramsabha_participants ? project.gramsabha_participants : 0}" />
        <c:set var="prabhatpheri" value="${not empty project.prabhatpheri_participants ? project.prabhatpheri_participants : 0}" />
        <c:set var="inauguration" value="${not empty project.total_inauguration_participants ? project.total_inauguration_participants : 0}" />
        <c:set var="village" value="${not empty project.total_village_participants ? project.total_village_participants : 0}" />
        
        <!-- Calculate the total sum -->
        <c:set var="total" value="${gramsabha + prabhatpheri + inauguration + village}" />

        <tr>
            <td class="text-center"><c:out value="${sno.count}" /></td>
            <td>
                <a href="getDistWatershedYatraStatus?stcd=<c:out value="${project.st_code}"/>&stName=<c:out value="${project.st_name}" />">
                    <c:out value="${project.st_name}" />
                </a>
            </td>

            <td class="text-right"><c:out value="${project.inauguration_date}" /></td>
            <td class="text-right"><c:out value="${not empty project.gramsabha ? project.gramsabha : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.prabhatpheri ? project.prabhatpheri : 0}" /></td>
            <td class="text-right"><c:out value="${gramsabha}" /></td>
            <td class="text-right"><c:out value="${prabhatpheri}" /></td>
            <td class="text-right"><c:out value="${inauguration}" /></td>
            <td class="text-right"><c:out value="${village}" /></td>
            <td class="text-right"><c:out value="${total}" /></td> <!-- Total column -->
            <td class="text-right"><c:out value="${project.total_locv}" /></td>
            <td class="text-right"><c:out value="${project.total_vanplan}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_arexp ? project.total_arexp : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_bhoomi_poojan ? project.total_bhoomi_poojan : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_lokarpan ? project.total_lokarpan : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_loc_shramdaan ? project.total_loc_shramdaan : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_partcp_shramdaan ? project.total_partcp_shramdaan : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_plantation_area ? project.total_plantation_area : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.total_award_distribution ? project.total_award_distribution : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.activity_entered ? project.activity_entered : 0}" /></td>
            <td class="text-right"><c:out value="${not empty project.act_not_entered ? project.act_not_entered : 0}" /></td>
        </tr>
    </c:forEach>
</tbody>

			
		<tfoot>
    <tr>
        <td  align="right" class="table-primary" colspan="3"><b>Grand Total</b></td>
        <td align="right" class="table-primary" id="totalGramsabha"></td>
        <td align="right" class="table-primary" id="totalPrabhatpheri"></td>
        <td align="right" class="table-primary" id="totalGramsabhapartcpnts"></td>
        <td align="right" class="table-primary" id="totalPrabhatpheripartcpnts"></td>
        <td align="right" class="table-primary" id="totalInaugurationpartcpnts"></td>
        <td align="right" class="table-primary" id="totalVillagepartcpnts"></td>
        <td align="right" class="table-primary" id="totalpartcpnts"></td>
        <td align="right" class="table-primary" id="totalLocv"></td>
        <td align="right" class="table-primary" id="totalVanplan"></td>
        
        <td align="right" class="table-primary" id="totalArexp"></td>
        <td align="right" class="table-primary" id="totalBhoomiPoojan"></td>
        <td align="right" class="table-primary" id="totalLokarpan"></td>
        <td align="right" class="table-primary" id="totalShramdaanloc"></td>
        <td align="right" class="table-primary" id="totalShramdaanprt"></td>
        <td align="right" class="table-primary" id="totalPlantation"></td>
        <td align="right" class="table-primary" id="totalaward"></td>
        <td align="right" class="table-primary" id="totalActivityEntered"></td>
        <td align="right" class="table-primary" id="totalActNotEntered"></td>
        
        
        
        
        
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
    const tbody = document.getElementById('stwiseAreaRptTbody');
//     const totalProjects = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[2].innerText || 0), 0);
 	const totalGramsabha = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[3].innerText || 0), 0);
 	const totalPrabhatpheri = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[4].innerText || 0), 0);
 	const totalGramsabhapartcpnts = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[5].innerText || 0), 0);
 	const totalPrabhatpheripartcpnts = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[6].innerText || 0), 0);
 	const totalInaugurationpartcpnts = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[7].innerText || 0), 0);
 	const totalVillagepartcpnts = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[8].innerText || 0), 0);
 	const totalpartcpnts = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[9].innerText || 0), 0);
 	const totalLocv = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[10].innerText || 0), 0);
 	const totalVanplan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[11].innerText || 0), 0);
    const totalArexp = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[12].innerText || 0), 0);
    const totalBhoomiPoojan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[13].innerText || 0), 0);
    const totalLokarpan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[14].innerText || 0), 0);
    const totalShramdaanloc = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[15].innerText || 0), 0);
    const totalShramdaanprt = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[16].innerText || 0), 0);
    const totalPlantation = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[17].innerText || 0), 0);
    const totalaward = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[18].innerText || 0), 0);
    const totalActivityEntered = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[19].innerText || 0), 0);
    const totalActNotEntered = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[20].innerText || 0), 0);
    

//     document.getElementById('totalProjects').innerHTML = '<b>' + totalProjects + '</b>';
//     document.getElementById('totalVanplan').innerHTML = '<b>' + totalVanplan + '</b>';
//     document.getElementById('totalLocv').innerHTML = '<b>' + totalLocv + '</b>';
    
    document.getElementById('totalPrabhatpheri').innerHTML = '<b>' + totalPrabhatpheri + '</b>';
    document.getElementById('totalGramsabha').innerHTML = '<b>' + totalGramsabha + '</b>';
    document.getElementById('totalPrabhatpheripartcpnts').innerHTML = '<b>' + totalPrabhatpheripartcpnts + '</b>';
    document.getElementById('totalGramsabhapartcpnts').innerHTML = '<b>' + totalGramsabhapartcpnts + '</b>';
    document.getElementById('totalInaugurationpartcpnts').innerHTML = '<b>' + totalInaugurationpartcpnts + '</b>';
    document.getElementById('totalVillagepartcpnts').innerHTML = '<b>' + totalVillagepartcpnts + '</b>';
    document.getElementById('totalpartcpnts').innerHTML = '<b>' + totalpartcpnts + '</b>';
    document.getElementById('totalLocv').innerHTML = '<b>' + totalLocv + '</b>';
    document.getElementById('totalVanplan').innerHTML = '<b>' + totalVanplan + '</b>';
    document.getElementById('totalArexp').innerHTML = '<b>' + totalArexp + '</b>';
    document.getElementById('totalBhoomiPoojan').innerHTML = '<b>' + totalBhoomiPoojan + '</b>';
    document.getElementById('totalLokarpan').innerHTML = '<b>' + totalLokarpan + '</b>';
    document.getElementById('totalShramdaanloc').innerHTML = '<b>' + totalShramdaanloc + '</b>';
    document.getElementById('totalShramdaanprt').innerHTML = '<b>' + totalShramdaanprt + '</b>';
    document.getElementById('totalPlantation').innerHTML = '<b>' + totalPlantation + '</b>';
    document.getElementById('totalaward').innerHTML = '<b>' + totalaward + '</b>';
    document.getElementById('totalActivityEntered').innerHTML = '<b>' + totalActivityEntered + '</b>';
    document.getElementById('totalActNotEntered').innerHTML = '<b>' + totalActNotEntered + '</b>';
};

</script>
</html>
