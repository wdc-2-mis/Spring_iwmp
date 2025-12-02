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

$(document).on('click', '.showImage', function(e) {
	
	let stCode = $(this).data('id');
	let imgType = $(this).data('type');
	$.ajax({
		type: 'POST',
		url: "getImageMahotProgRpt",
		data: { stCode: stCode, imgType: imgType },
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
//			var imageContainer = $('.image-container');
//			imageContainer.empty();
			let list = '<ul>';
			for (let i = 0; i < data.length; i++) {
				if (data[i] != null) 
				{
				//PRD
//					list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
				//TEST
				//	list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
				//Local
					list += '<li><img src="resources/images/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';

				}
			}
			list += '</ul>';
			document.getElementById('imageList').innerHTML = list;
			document.getElementById('imagePopup').style.display = 'block';
		}
	});
});

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }
  
function openLargeImage(imageSrc, index, total) {
	document.getElementById('imagePopup').style.display = 'none';
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/projectLevel/' + imageSrc;		
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/projectLevel/' + imageSrc;
 	document.getElementById('largeImage').src = 'resources/images/projectLevel/' + imageSrc;											
	document.getElementById('largeImagePopup').style.display = 'block';
	currentIndex = index;
	totalImages = total;
}

function closeLargeImagePopup() {
	document.getElementById('largeImagePopup').style.display = 'none';
}

function showNextImage() {
	if (currentIndex < totalImages - 1) {
		currentIndex++;
		let nextImageSrc = $('.image-container img')[currentIndex].src;
		document.getElementById('largeImage').src = nextImageSrc;
	}
}

function showPrevImage() {
	if (currentIndex > 0) {
		currentIndex--;
		let prevImageSrc = $('.image-container img')[currentIndex].src;
		document.getElementById('largeImage').src = prevImageSrc;
	}
}

</script>

<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>

<style>

#imagePopup {
display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ */
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}

/* Popup content */
.popup-content {
  background-color: #fefefe;
  margin-left: 300px;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px; /* Increased max-width */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
}

/* Close button */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}



/* Image list */
.image-container ul {
  list-style-type: none;
  padding: 30px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); /* Adjust minmax values as needed */
  gap: 10px; /* Adds equal space between images */
}

.image-container li {
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-container img {
  max-width: 100%;
  max-height: 100px;
  border-radius: 5px;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
}

#largeImagePopup {
  display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ */
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1500px; /* Max width of the popup */
  border-radius: 10px;
}


/* Popup content */
.large-image-popup-content {
  background-color: #fefefe;
  width: 100%;
  height: auto;
  max-height: 80vh; /* Set a max height to avoid overflowing */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  display: flex;
  justify-content: center; /* Center the image horizontally */
  align-items: center; /* Center the image vertically */
  position: relative;
}

/* Adjust close button position for large image pop-up */
.large-image-popup-content .close {
  position: absolute; /* Change from float to absolute */
  top: 10px; /* Adjust as needed */
  right: 10px; /* Adjust as needed */
  color: #aaa;
  font-size: 28px;
  font-weight: bold;
}

.large-image-popup-content .close:hover,
.large-image-popup-content .close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

/* Large image */
#largeImage {
  width: 100%; /* Ensure it fits inside the popup */
  height: auto;
  max-height: 80vh; /* Restrict height to 80% of the viewport height */
  object-fit: contain; /* Ensure the aspect ratio is maintained */
}

.nav-arrow {
  color: black;
  font-size: 40px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2;
}

#prevImage {
  left: 20px;
}

#nextImage {
  right: 20px;
}

</style>
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
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Agro Forestry/ Horticulture Plantation(Number of Saplings) </th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">No. of Photographs Uploaded</th>
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
											<a href="#" data-id="${dt.st_code}" data-type="prabhat" class="showImage" data-toggle="modal" style ="color: blue;"> <c:out value="${dt.total_prabhat_photo}" /></a>
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
										</c:when>
										<c:otherwise>
										<a href="#" data-id="${dt.st_code}" data-type="projectlvl" class="showImage" data-toggle="modal" style ="color: blue;">	<c:out value="${dt.total_projlvl_photo}" /></a>
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
 <div id="imagePopup" class="popup" style="display:none;">
		<div class="popup-content">
			<span class="close" onclick="closePopup()">&times;</span>
			<div id="imageList" class="image-container"></div>
		</div>
	</div>

	<div id="largeImagePopup" class="popup" style="display: none;">
		<div class="large-image-popup-content">
			<span class="close" onclick="closeLargeImagePopup()">&times;</span>
			<div class="nav-arrow" id="prevImage" onclick="showPrevImage()">&#10094;</div>
			<img id="largeImage" src="" alt="Large Image" />
			<div class="nav-arrow" id="nextImage" onclick="showNextImage()">&#10095;</div>
		</div>
		
	</div>  

<footer class=" ">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>

</body>
</html>
