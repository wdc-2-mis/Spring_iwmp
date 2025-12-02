<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavReportheader.jspf" %>
<script src='<c:url value="/resources/js/mahotsavinauguration.js" />'></script>
<title>Report WM1 - State-wise Watershed Mahotsav Inauguration Program</title>

<html>
<head>


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


function getImageHash(file, callback) {
    let reader = new FileReader();
    reader.onload = function(e) {
        let wordArray = CryptoJS.lib.WordArray.create(e.target.result);
        let hash = CryptoJS.SHA256(wordArray).toString();
        callback(hash);
    };
    reader.readAsArrayBuffer(file);
}

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }
  
function openLargeImage(imageSrc, index, total) {
	document.getElementById('imagePopup').style.display = 'none';
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/Inauguration/' + imageSrc;		
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/Inauguration/' + imageSrc;
 //	document.getElementById('largeImage').src = 'resources/images/inauguration/' + imageSrc;											
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
  width: auto; /* Ensure it fits inside the popup */
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
        	<h4 class="text-center text-primary mb-4"><u>Report WM1 - Watershed Mahotsav Inauguration Program</u></h4>
    	</div>
			<!-- <br> -->

    <div class="nav-item text-left mb-2">
    	<c:if test="${not empty stateWMInaugurationList}">
    		<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()"> Excel </button>
        	<button type="button"  name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button>
        </c:if>   
        <p align="right">  Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
    </div>
        
        <form action="downloadExcelStMidProjEvoluation" name="getWMIDetails" id="getWMIDetails" method="post">
        
                <table class="table table-bordered table-striped" id="stWMI" >
                    <thead>
                        <tr>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">S.No.</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">State Name</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Date of Inauguration</th>
						
						
						<th colspan="6" style="text-align:center; vertical-align: middle;">Number of Participation</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Number of Works for Bhoomi Poojan</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Number of Works for Lokarpan</th>
						<th colspan="2" style="text-align:center; vertical-align: middle;">Shramdaan</th>
						<th rowspan="3" style="text-align:center; vertical-align: middle;">Agro forestry / Horticulture Plantation no. of Saplings</th>
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
 								<td><c:out value="${dt.date}" /></td> 
<%-- 								<td class="text-end"><c:out value="${dt.male_participants}" /></td> --%>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.male_participants == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.male_participants}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.female_participants == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.female_participants}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.participants == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.participants}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.others == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.others}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.gov_officials == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.gov_officials}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.total_participation == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.total_participation}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_works_bhoomipoojan == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_works_bhoomipoojan}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_works_lokarpan == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_works_lokarpan}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_location_shramdaan == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_location_shramdaan}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_people_shramdaan == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_people_shramdaan}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.area_plantation == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.area_plantation}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.no_awards == 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
											<c:out value="${dt.no_awards}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.image_count eq 0}">
											<!-- blank -->
										</c:when>
										<c:otherwise>
										<a href="#" data-id="${dt.inauguaration_id}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${dt.image_count}" /></a>
											<%-- <c:out value="${dt.image_count}" /> --%>
										</c:otherwise>
									</c:choose>
									
								</td>
								
								
								
<%-- 								<td class="text-end"><c:out value="${dt.female_participants}" /></td> 
								<td class="text-end"><c:out value="${dt.participants}" /></td>
								<td class="text-end"><c:out value="${dt.others}" /></td>
								<td class="text-end"><c:out value="${dt.gov_officials}" /></td>
								<td class="text-end"><c:out value="${dt.total_participation}" /></td>
								<td class="text-end"><c:out value="${dt.no_works_bhoomipoojan}" /></td>
								<td class="text-end"><c:out value="${dt.no_works_lokarpan}" /></td>
								<td class="text-end"><c:out value="${dt.no_location_shramdaan}" /></td>
								<td class="text-end"><c:out value="${dt.no_people_shramdaan}" /></td>
								<td class="text-end"><c:out value="${dt.area_plantation}" /></td>
								<td class="text-end"><c:out value="${dt.no_awards}" /></td>
								<td class="text-end"><c:out value="${dt.image_count}" /></td> --%>
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
							<tr class="table-secondary fw-bold">
 								<td colspan="3" class="text-end"><b>Grand Total</b></td> 
								<td class="text-end"><b><c:out value="${totMale}" /></b></td>
								<td class="text-end"><b><c:out value="${totFemale}" /></b></td>
								<td class="text-end"><b><c:out value="${totParticipants}" /></b></td>
								<td class="text-end"><b><c:out value="${totOthers}" /></b></td>
								<td class="text-end"><b><c:out value="${totGov}" /></b></td>
								<td class="text-end"><b><c:out value="${total}" /></b></td>
								<td class="text-end"><b><c:out value="${totBhoomiPoojan}" /></b></td>
								<td class="text-end"><b><c:out value="${totLokarpan}" /></b></td>
								<td class="text-end"><b><c:out value="${totShramdaanLoc}" /></b></td>
								<td class="text-end"><b><c:out value="${totShramdaanPeople}" /></b></td>
								<td class="text-end"><b><c:out value="${totPlantation}" /></b></td>
								<td class="text-end"><b><c:out value="${totAwards}" /></b></td>
								<td class="text-end"><b><c:out value="${totImage}" /></b></td>
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
	<!-- Show Image Modal HTML -->
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
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>

</body>
</html>
