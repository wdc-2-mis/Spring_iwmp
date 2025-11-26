<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavReportheader.jspf" %>

<title>Report WM2 - Project Level Watershed Mahotsav Program</title>

<html>
<head>

<script type="text/javascript">

function downloadPDF(){
		document.getWMProjLvlDetails.action="downloadPDFProjLvlProgram";
		document.getWMProjLvlDetails.method="post";
		document.getWMProjLvlDetails.submit();
}

function exportExcel(){
		document.getWMProjLvlDetails.action="downloadExcelProjLvlProgram";
		document.getWMProjLvlDetails.method="post";
		document.getWMProjLvlDetails.submit();
}

</script>

<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>
</head>
<body>
<div class="maindiv">
    <div class="card shadow mt-1 p-5"> 
    
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        	<h4 class="text-center text-primary mb-4"><u>Report WM2 - Project Level Watershed Mahotsav Program</u></h4>
    	</div>
    	
    <div class="nav-item text-left mb-2">
    	<c:if test="${not empty projLvlWMPrgListSize}">
    		<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()"> Excel </button>
        	<button type="button"  name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button>
        </c:if>   
        <p align="right">  Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
    </div>
        
        <form action="downloadExcelStMidProjEvoluation" name="getWMProjLvlDetails" id="getWMProjLvlDetails" method="post">
        
                <table class="table table-bordered table-striped" id="stWMI" >
                    <thead>
                        <tr>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">S.No.</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">State Name</th>
						<th colspan="5" style="text-align:center; vertical-align: middle;">Prabhat Pheri</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">No. of Project Level Watershed Mahotsav Organized</th>
						<th colspan="6" style="text-align:center; vertical-align: middle;">No. of Participation in Project Level Waterhshed Mahotsav</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">No. of Works for Bhoomi Poojan</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">No. of Works for Lokarpan</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Shramdaan</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Agro Forestry/ Horticulture Plantation no. of Saplings </th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">No of Photographs Uploaded</th>
					</tr>
					<tr>
					    <th rowspan="2" style="text-align:center; vertical-align: middle;">No. of Prabhat Pheri Organized</th>
						<th colspan="3" style="text-align:center; vertical-align: middle;">Participants/Villagers</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">No of Photographs Uploaded</th>
						<th colspan="3" style="text-align:center; vertical-align: middle;">Participants/Villagers</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Public Representatives</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Government Officials</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Participation (6+11+12+13)</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of Locations</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">No. of people participated</th>

					</tr>
					<tr>
						<th style="text-align:center; vertical-align: middle;">Male</th>
						<th style="text-align:center; vertical-align: middle;">Female</th>
						<th style="text-align:center; vertical-align: middle;">Total (4+5)</th>
						<th style="text-align:center; vertical-align: middle;">Male</th>
						<th style="text-align:center; vertical-align: middle;">Female</th>
						<th style="text-align:center; vertical-align: middle;">Total (8+9)</th>
					</tr>
					<tr>
						<% for (int i = 1; i <= 20; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
                    </thead>
                    
                      <tbody id="tbodyProjLvlProgRpt">
						<c:forEach items="${projLvlWMPrgList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td class="text-left"><c:out value="${dt.stname}" /></td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_of_prabhat == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_of_prabhat}" />
										</c:otherwise>
									</c:choose>
								</td>
 								<td class="text-end">
									<c:choose>
										<c:when test="${dt.pr_male == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.pr_male}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.pr_female == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.pr_female}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.pr_total_male_female == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.pr_total_male_female}" />
										</c:otherwise>
									</c:choose>
								</td>
								
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.total_prabhat_photo == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.total_prabhat_photo}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_of_projectlvl == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_of_projectlvl}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.pl_male == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.pl_male}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.pl_female == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.pl_female}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.pl_total_male_female == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.pl_total_male_female}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.representatives == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.representatives}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.govt_officials == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.govt_officials}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.total_participations == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.total_participations}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.bhoomi_poojan == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.bhoomi_poojan}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.lokarpans == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.lokarpans}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.shramdaan_location == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.shramdaan_location}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.shramdaan_participated == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.shramdaan_participated}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_of_agro == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_of_agro}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.total_projlvl_photo == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.total_projlvl_photo}" />
										</c:otherwise>
									</c:choose>
								</td>
								
						
							</tr>
							
							<c:set var="totPPOrganized" value="${totPPOrganized + dt.no_of_prabhat}" />
 							<c:set var="totPPMale" value="${totPPMale + dt.pr_male}" /> 
							<c:set var="totPPFemale" value="${totPPFemale + dt.pr_female}" />
							<c:set var="totPPParticipants" value="${totPPParticipants + dt.pr_total_male_female}" />
							<c:set var="totPPPhoto" value="${totPPPhoto + dt.total_prabhat_photo}" />
							
							<c:set var="totPLOrganized" value="${totPLOrganized + dt.no_of_projectlvl}" />
							<c:set var="totPLMale" value="${totPLMale + dt.pl_male}" />
							<c:set var="totPLFemale" value="${totPLFemale + dt.pl_female}" />
							<c:set var="totPLMFParticipants" value="${totPLMFParticipants + dt.pl_total_male_female}" />
							<c:set var="totPLRepresentatives" value="${totPLRepresentatives + dt.representatives}" />
							<c:set var="totPLGoverment" value="${totPLGoverment + dt.govt_officials}" />
							<c:set var="totPLParticipants" value="${totPLParticipants + dt.total_participations}" />
							<c:set var="totPLBhoomiPoojan" value="${totPLBhoomiPoojan + dt.bhoomi_poojan}" />
							<c:set var="totPLLokarpan" value="${totPLLokarpan + dt.lokarpans}" />
							<c:set var="totPLShramdaanLoc" value="${totPLShramdaanLoc + dt.shramdaan_location}" />
							<c:set var="totPLShramdaanPeople" value="${totPLShramdaanPeople + dt.shramdaan_participated}" />
							<c:set var="totPLPlantation" value="${totPLPlantation + dt.no_of_agro}" />
							<c:set var="totPLImage" value="${totPLImage + dt.total_projlvl_photo}" />
							
						</c:forEach>
						<c:if test="${projLvlWMPrgListSize>0}">
							<tr class="table-secondary fw-bold">
 								<td colspan="2" class="text-end"><b>Grand Total</b></td> 
								<td class="text-end"><b><c:out value="${totPPOrganized}" /></b></td>
								<td class="text-end"><b><c:out value="${totPPMale}" /></b></td>
								<td class="text-end"><b><c:out value="${totPPFemale}" /></b></td>
								<td class="text-end"><b><c:out value="${totPPParticipants}" /></b></td>
								<td class="text-end"><b><c:out value="${totPPPhoto}" /></b></td>
								
								<td class="text-end"><b><c:out value="${totPLOrganized}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLMale}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLFemale}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLMFParticipants}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLRepresentatives}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLGoverment}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLParticipants}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLBhoomiPoojan}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLLokarpan}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLShramdaanLoc}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLShramdaanPeople}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLPlantation}" /></b></td>
								<td class="text-end"><b><c:out value="${totPLImage}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${projLvlWMPrgListSize==0}">
							<tr>
								<td align="center" colspan="20" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
            
    </form>
    </div>
    </div>
   

<footer class=" ">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>

</body>
</html>
