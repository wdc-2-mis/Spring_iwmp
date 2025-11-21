<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${sessionScope.loginid eq null }">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
	</c:when>
	<c:otherwise>
		<%@include file="/WEB-INF/jspf/header2.jspf"%>
	</c:otherwise>
</c:choose>

<title>Report WM1 - State-wise Watershed Mahotsav Inauguration Program</title>

<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.1/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">

function downloadPDF(){
		document.getWMIDetails.action="downloadPDFStWMInauguration";
		document.getWMIDetails.method="post";
		document.getWMIDetails.submit();
}

function exportExcel(){
		document.getWMIDetails.action="downloadExcelStWMInauguration";
		document.getWMIDetails.method="post";
		document.getWMIDetails.submit();
}

</script>

<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>
</head>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h5>Report WM1 - Watershed Mahotsav Inauguration Program</h5>
    </div>
    <br>
    <div class ="card">
		<div class="table-responsive">
			<br>
    <c:if test="${not empty stateWMInaugurationList1}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelStMidProjEvoluation" name="getWMIDetails" id="getWMIDetails" method="post">
        
                <table class="table" id="stWMI" >
                    <thead>
                        <tr>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">S.No.</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">State Name</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Date of Inauguration</th>
						
						
						<th colspan="6" style="text-align:center; vertical-align: middle;">Number of Participation</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Number of Works for Bhoomi Poojan</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Number of Works for Lokarpan</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Shramdaan</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Agro forestry / Horticulture Plantation (Area in Ha.)</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Number of Projects Awarded for Janbhagidari Cup 2025</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">No of Photographs Uploaded</th>
					</tr>
					<tr>
						<th colspan="3" style="text-align:center; vertical-align: middle;">Participants/Villagers</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Public Representatives</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Government Officials</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Participation (6+7+8)</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of Locations</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">No. of people participated</th>

					</tr>
					<tr>
						<th style="text-align:center; vertical-align: middle;">Male</th>
						<th style="text-align:center; vertical-align: middle;">Female</th>
						<th style="text-align:center; vertical-align: middle;">Total (4+5)</th>
					</tr>
					<tr>
						<% for (int i = 1; i <= 16; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
                    </thead>
                    
                      <tbody id="tbodyStWMInaugurationRpt">
						<c:forEach items="${stateWMInaugurationList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td class="text-left"><c:out value="${dt.stname}" /></td>
<%-- 								<td><a href = "distMidProjEvoluationRpt?stcd=${dt.st_code}&stName=${dt.st_name}"><c:out value='${dt.st_name}'/></a></td> --%>
 								<td class="text-right"><c:out value="${dt.date}" /></td> 
								<td class="text-right"><c:out value="${dt.male_participants}" /></td>
								<td class="text-right"><c:out value="${dt.female_participants}" /></td>
								<td class="text-right"><c:out value="${dt.participants}" /></td>
								<td class="text-right"><c:out value="${dt.others}" /></td>
								<td class="text-right"><c:out value="${dt.gov_officials}" /></td>
								<td class="text-right"><c:out value="${dt.total_participation}" /></td>
								<td class="text-right"><c:out value="${dt.no_works_bhoomipoojan}" /></td>
								<td class="text-right"><c:out value="${dt.no_works_lokarpan}" /></td>
								<td class="text-right"><c:out value="${dt.no_location_shramdaan}" /></td>
								<td class="text-right"><c:out value="${dt.no_people_shramdaan}" /></td>
								<td class="text-right"><c:out value="${dt.area_plantation}" /></td>
								<td class="text-right"><c:out value="${dt.no_awards}" /></td>
								<td class="text-right"><c:out value="${dt.image_count}" /></td>
							</tr>
							
 							<c:set var="totMale" value="${totMale + dt.male_participants}" /> 
							<c:set var="totFemale" value="${totFemale + dt.female_participants}" />
							<c:set var="totParticipants" value="${totParticipants + dt.participants}" />
							<c:set var="totOthers" value="${totOthers + dt.others}" />
							<c:set var="totGov" value="${totGov + dt.gov_officials}" />
							<c:set var="total" value="${total + dt.total_participation}" />
							<c:set var="totBhoomiPoojan" value="${totBhoomiPoojan + dt.no_works_bhoomipoojan}" />
							<c:set var="totLokarpan" value="${totLokarpan + dt.no_works_lokarpan}" />
							<c:set var="totShramdaanLoc" value="${totShramdaanLoc + dt.no_location_shramdaan}" />
							<c:set var="totShramdaanPeople" value="${totShramdaanPeople + dt.no_people_shramdaan}" />
							<c:set var="totPlantation" value="${totPlantation + dt.area_plantation}" />
							<c:set var="totAwards" value="${totAwards + dt.no_awards}" />
							<c:set var="totImage" value="${totImage + dt.image_count}" />
							
						</c:forEach>
						<c:if test="${stateWMInaugurationListSize>0}">
							<tr>
 								<td colspan="3" align="right" class="table-primary"><b>Grand Total</b></td> 
								<td align="right" class="table-primary"><b><c:out value="${totMale}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totFemale}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totParticipants}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOthers}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGov}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${total}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totBhoomiPoojan}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totLokarpan}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totShramdaanLoc}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totShramdaanPeople}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPlantation}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totAwards}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totImage}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${stateWMInaugurationListSize==0}">
							<tr>
								<td align="center" colspan="16" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
            
    </form>
    </div>
    </div>
    </div>

<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

</body>
</html>
