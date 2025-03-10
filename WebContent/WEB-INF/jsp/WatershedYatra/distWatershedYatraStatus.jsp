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
				<th colspan="20">State:- <c:out value="${stName}" />,&nbsp&nbsp&nbsp
				Inauguration Date :  <c:out value="${inaugurationDate}" /><br /> 
				</th>
			</tr>
		<tr>
		
		<th rowspan="2" class="text-center">S.No.</th>
		<th rowspan="2" class="text-center">District</th>
<!-- 		<th rowspan="3" class="text-center">Total Projects</th> -->
<!-- 		<th rowspan="3" class="text-center">Location Planned</th> -->
<!-- 		<th rowspan="3" class="text-center">Location Completed</th> -->

<!-- 		<th rowspan="2" class="text-center">State Inauguration Date</th> -->
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
						<% for (int i = 1; i <= 20; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
</thead>

	<tbody id="distwiseAreaRptTbody">
		<c:forEach items="${getRecords}" var="project" varStatus="sno">
			 <tr>
				<td class="text-center"><c:out value="${sno.count}" /></td>
				<td class="text-right"><c:out value="${project.dist_name}" /></td>
               	<td class="text-right"><c:out value="${not empty project.gramsabha ? project.gramsabha : 0}" /></td>
					
					<td class="text-right"><c:out value="${not empty project.prabhatpheri ? project.prabhatpheri : 0}" /></td>
					<td class="text-right"><c:out value="${not empty project.gramsabha_participants ? project.gramsabha_participants : 0}" /></td>
					<td class="text-right"><c:out value="${not empty project.prabhatpheri_participants ? project.prabhatpheri_participants : 0}" /></td>
					<td class="text-right"><c:out value="${not empty project.total_inauguration_participants ? project.total_inauguration_participants : 0}" /></td>
					<td class="text-right"><c:out value="${not empty project.total_village_participants ? project.total_village_participants : 0}" /></td>
					<td class="text-right"> <c:out value="${(not empty project.gramsabha_participants ? project.gramsabha_participants : 0) 
                     + (not empty project.prabhatpheri_participants ? project.prabhatpheri_participants : 0) 
                     + (not empty project.total_inauguration_participants ? project.total_inauguration_participants : 0) 
                     + (not empty project.total_village_participants ? project.total_village_participants : 0)}" /></td>
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
        	<td  align="right" class="table-primary" colspan=2><b>Grand Total</b></td>
        	<td align="right" class="table-primary" id="totalGramsabha"></td>
        	<td align="right" class="table-primary" id="totalPrabhatpheri"></td>
        	<td align="right" class="table-primary" id="totalGSParticipant"></td>
        	<td align="right" class="table-primary" id="totalPPParticipant"></td>
        	<td align="right" class="table-primary" id="totalInaugParticipant"></td>
        	<td align="right" class="table-primary" id="totalVillageParticipant"></td>
        	<td align="right" class="table-primary" id="total"></td>
        	<td align="right" class="table-primary" id="totalNoofLocations"></td>
        	<td align="right" class="table-primary" id="totalLocCovered"></td>
        	<td align="right" class="table-primary" id="totalArexp"></td>
        	<td align="right" class="table-primary" id="totalBhoomiPoojan"></td>
        	<td align="right" class="table-primary" id="totalLokarpan"></td>
        	<td align="right" class="table-primary" id="totalShramdaan"></td>
        	<td align="right" class="table-primary" id="totalSharamdaanPP"></td>
        	<td align="right" class="table-primary" id="totalSapling"></td>
        	<td align="right" class="table-primary" id="totalWatershedMargdarshak"></td>
        	<td align="right" class="table-primary" id="totalCompletedActivity"></td>
        	<td align="right" class="table-primary" id="totalNotCompletedActivity"></td>
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
    const totalGramsabha = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[2].innerText || 0), 0);
    const totalPrabhatpheri = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[3].innerText || 0), 0);
    const totalGSParticipant = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[4].innerText || 0), 0);
    const totalPPParticipant = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[5].innerText || 0), 0);
    const totalInaugParticipant = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[6].innerText || 0), 0);
    const totalVillageParticipant = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[7].innerText || 0), 0);
    const total = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[8].innerText || 0), 0);
    const totalNoofLocations = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[9].innerText || 0), 0);
    const totalLocCovered = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[10].innerText || 0), 0);
    const totalArexp = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[11].innerText || 0), 0);
    const totalBhoomiPoojan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[12].innerText || 0), 0);
    const totalLokarpan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[13].innerText || 0), 0);
    const totalShramdaan = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[14].innerText || 0), 0);
    const totalSharamdaanPP = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[15].innerText || 0), 0);
    const totalSapling = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[16].innerText || 0), 0);
    const totalWatershedMargdarshak = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[17].innerText || 0), 0);
    const totalCompletedActivity = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[18].innerText || 0), 0);
    const totalNotCompletedActivity = [...tbody.getElementsByTagName('tr')].reduce((sum, row) => sum + Number(row.cells[19].innerText || 0), 0);

    document.getElementById('totalGramsabha').innerHTML = '<b>' + totalGramsabha + '</b>';
    document.getElementById('totalPrabhatpheri').innerHTML = '<b>' + totalPrabhatpheri + '</b>';
    document.getElementById('totalGSParticipant').innerHTML = '<b>' + totalGSParticipant + '</b>';
    document.getElementById('totalPPParticipant').innerHTML = '<b>' + totalPPParticipant + '</b>';
    document.getElementById('totalInaugParticipant').innerHTML = '<b>' + totalInaugParticipant + '</b>';
    document.getElementById('totalVillageParticipant').innerHTML = '<b>' + totalVillageParticipant + '</b>';
    document.getElementById('total').innerHTML = '<b>' + total + '</b>';
    document.getElementById('totalNoofLocations').innerHTML = '<b>' + totalNoofLocations + '</b>';
    document.getElementById('totalLocCovered').innerHTML = '<b>' + totalLocCovered + '</b>';
    document.getElementById('totalArexp').innerHTML = '<b>' + totalArexp + '</b>';
    document.getElementById('totalBhoomiPoojan').innerHTML = '<b>' + totalBhoomiPoojan + '</b>';
    document.getElementById('totalLokarpan').innerHTML = '<b>' + totalLokarpan + '</b>';
    document.getElementById('totalShramdaan').innerHTML = '<b>' + totalShramdaan + '</b>';
    document.getElementById('totalSharamdaanPP').innerHTML = '<b>' + totalSharamdaanPP + '</b>';
    document.getElementById('totalSapling').innerHTML = '<b>' + totalSapling + '</b>';
    document.getElementById('totalWatershedMargdarshak').innerHTML = '<b>' + totalWatershedMargdarshak + '</b>';
    document.getElementById('totalCompletedActivity').innerHTML = '<b>' + totalCompletedActivity + '</b>';
    document.getElementById('totalNotCompletedActivity').innerHTML = '<b>' + totalNotCompletedActivity + '</b>';
    
};

</script>
</html>
