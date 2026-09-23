
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${sessionScope.loginid eq null }">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
	</c:when>
	<c:otherwise>
		<%@include file="/WEB-INF/jspf/header2.jspf"%>
	</c:otherwise>
</c:choose>

<!DOCTYPE html>
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
	document.getWSProjLvlDetails.action="downloadPDFStSwachhataProgram";
	document.getWSProjLvlDetails.method="post";
	document.getWSProjLvlDetails.submit();
}

function exportExcel(){
	document.getWSProjLvlDetails.action="downloadExcelStSwachhataProgram";
	document.getWSProjLvlDetails.method="post";
	document.getWSProjLvlDetails.submit();
}

function downloadDPDF(stcd, stName){
document.getElementById("stcd").value=stcd;
document.getElementById("stName").value=stName;
document.getWSProjLvlDetails.action="downloadDistPDFSwachhataProgram";
document.getWSProjLvlDetails.method="post";
document.getWSProjLvlDetails.submit();
}

function exportDExcel(stcd, stName){
document.getElementById("stcd").value=stcd;
document.getElementById("stName").value=stName;
document.getWSProjLvlDetails.action="downloadDistExcelSwachhataProgram";
document.getWSProjLvlDetails.method="post";
document.getWSProjLvlDetails.submit();
}

$(document).on('click', '.showImage', function(e) {
	
	let stCode = e.target.getAttribute('data-id');
	$.ajax({
		type: 'POST',
		url: "getImageByStcode",
		data: { stCode: stCode},
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
//					list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/swachhata/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
				//TEST
				//	list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/swachhata/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
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

$(document).on('click', '.showDistImage', function(e) {
	
	let dcode = e.target.getAttribute('data-id');
	alert(dcode);
	$.ajax({
		type: 'POST',
		url: "getImageByDcode",
		data: { dcode: dcode},
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
//					list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/swachhata/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
				//TEST
				//	list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/swachhata/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
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
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/Inauguration/' + imageSrc;			//PRD
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/vanyatradoc/Inauguration/' + imageSrc;	//TEST
// 	document.getElementById('largeImage').src = 'resources/images/watershedyatra/' + imageSrc;												//Local
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

<style type="text/css">

/* Popup container */
#imagePopup {
display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ 
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}

/* Popup content */
.popup-content {
  background-color: #fefefe;
  margin-left: 500px;
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

/* Large image pop-up */
/*#largeImagePopup {
  display: none; /* Hidden by default 
  position: fixed;
  top: 300px;
  left: 60%;
  width: 30%;
  transform: translateX(-50%);
  z-index: 1000;
}
*/
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
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}

/* Large image pop-up content */
.large-image-popup-content {
  background-color: #fefefe;
  border: 1px solid #888;
  width: 100%;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative; /* Add this line */
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

#largeImage {
  width: 80%;
  height: auto;
  max-height: 80vh; /* Adjust this value as needed */
}

.nav-arrow {
  color: black;
  font-size: 40px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
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
<div class="card">
    <div class="card shadow mt-1 p-5"> 
         <c:if test="${projLvlWSPrgListSize ne null}">
         <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        	<h4 class="text-center text-primary mb-4"><u>Report WS1 - State Wise Project Level Watershed Swachhata hi Seva Program</u></h4>
    	</div>
    	</c:if>
    	
    	<c:if test="${distWSProjListSize ne null}">
    	<div class="offset-md-3 col-6 formheading" style="text-align: center;">
        	<h4 class="text-center text-primary mb-4"><u>Report WS1 - District Wise Project Level Watershed Swachhata hi Seva Program</u></h4>
    	</div>
    	</c:if>
    <div class="nav-item text-left mb-2">
    	<c:if test="${not empty projLvlWSPrgListSize}">
    		<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()"> Excel </button>
        	<button type="button"  name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button>
        </c:if>
        
        <c:if test="${not empty distWSProjListSize}">
    	    <button type="button" name="exportDExcel" id="exportDExcel" class="btn pdf-gradient" onclick="exportDExcel('${stcd}','${stName}')"> Excel </button> 
        	<button type="button"  name="exportDPDF" id="exportDPDF" class="btn pdf-gradient" onclick="downloadDPDF('${stcd}','${stName}')">PDF</button>
        </c:if>   
        <p align="right">  Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
    </div>
        
        <form action="downloadExcelStSwachhataProgram" name="getWSProjLvlDetails"  id="getWSProjLvlDetails" method="post">
        <input type="hidden" name="stcd" id="stcd" value="" />
        <input type="hidden" name="stName" id="stName" value="" />
        <c:if test="${projLvlWSPrgListSize > 0}">
                <table class="table table-bordered table-striped" id="stWMI" >
                    <thead>
                        <tr>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">State Name</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Number of Villages Involved</th>
						<th colspan="6" style="text-align:center; vertical-align: middle;">Total Number of People Participated</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of Photographs Uploaded </th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Number of Saplings Planted</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Number of Works for Lokarpan</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Shramdaan Undertaken on Total Number of Locations</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of Cleaniness Drives Organised</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Awareness Sessions Oraganised</th>
					</tr>
					<tr>
					    <th style="text-align:center; vertical-align: middle;">SHG</th>
						<th style="text-align:center; vertical-align: middle;">User Groups</th>
						<th style="text-align:center; vertical-align: middle;">FPO Youth</th>
						<th style="text-align:center; vertical-align: middle;">Students</th>
						<th style="text-align:center; vertical-align: middle;">Others</th>
						<th style="text-align:center; vertical-align: middle;">Total</th>
					</tr>
					<tr>
						<% for (int i = 1; i <= 15; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
                    </thead>
                      <tbody id="tbodyProjLvlProgRpt">
						<c:forEach items="${projLvlWSPrgList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<%-- <td class="text-left"><c:out value="${dt.stname}" /></td> --%>
								<td><a href = "distWSProjLvlProgRpt?stcd=${dt.st_code}&stName=${dt.stname}"><c:out value="${dt.stname}"/></a></td>
								
								<td class="text-end"><c:out value="${dt.village}"/></td>
 								<td class="text-end"><c:out value="${dt.shg}"/></td>
 								<td class="text-end"><c:out value="${dt.ug}"/></td>
 								<td class="text-end"><c:out value="${dt.fpo}"/></td>
 								<td class="text-end"><c:out value="${dt.youth}"/></td>
 								<td class="text-end"><c:out value="${dt.other}"/></td>
 								<td class="text-end"><c:out value="${dt.total}"/></td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.total_photos == 0}">
										</c:when>
										<c:otherwise>
											<a href="#" data-id="${dt.st_code}" data-type="projectlvl" class="showImage" data-toggle="modal" style="color: blue;">
												<c:out value="${dt.total_photos}" />
											</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end"><c:out value="${dt.no_sapling}"/></td>
 								<td class="text-end"><c:out value="${dt.no_works_lokarpan}"/></td>
 								<td class="text-end"><c:out value="${dt.no_location_shramdaan}"/></td>
 								<td class="text-end"><c:out value="${dt.no_cleanliness}"/></td>
								<td class="text-end"><c:out value="${dt.no_awareness}"/></td>
								</tr>
							
							<c:set var="totVillage" value="${totVillage + dt.village}" />
 							<c:set var="totShg" value="${totShg + dt.shg}" /> 
							<c:set var="totUg" value="${totUg + dt.ug}" />
							<c:set var="totFpo" value="${totFpo + dt.fpo}" />
							<c:set var="totYouth" value="${totYouth + dt.youth}" />
							<c:set var="totOther" value="${totOther + dt.other}" />
							<c:set var="totTotal" value="${totTotal + dt.total}" />
							
							<c:set var="totTotPhotos" value="${totTotPhotos + dt.total_photos}" />
							<c:set var="totSaplings" value="${totSaplings + dt.no_sapling}" />
							<c:set var="totLokarpans" value="${totLokarpans + dt.no_works_lokarpan}" />
							<c:set var="totShramdaans" value="${totShramdaans + dt.no_location_shramdaan}" />
							<c:set var="totCleaniness" value="${totCleaniness + dt.no_cleanliness}" />
							<c:set var="totAwareness" value="${totAwareness + dt.no_awareness}" />
							
						</c:forEach>
						<c:if test="${projLvlWSPrgListSize>0}">
							<tr class="table-secondary fw-bold">
 								<td colspan="2" class="text-end"><b>Grand Total</b></td> 
								<td class="text-end"><b><c:out value="${totVillage}" /></b></td>
								<td class="text-end"><b><c:out value="${totShg}" /></b></td>
								<td class="text-end"><b><c:out value="${totUg}" /></b></td>
								<td class="text-end"><b><c:out value="${totFpo}" /></b></td>
								<td class="text-end"><b><c:out value="${totYouth}" /></b></td>
								<td class="text-end"><b><c:out value="${totOther}" /></b></td>
								<td class="text-end"><b><c:out value="${totTotal}" /></b></td>
								
								<td class="text-end"><b><c:out value="${totTotPhotos}" /></b></td>
								<td class="text-end"><b><c:out value="${totSaplings}" /></b></td>
								<td class="text-end"><b><c:out value="${totLokarpans}" /></b></td>
								<td class="text-end"><b><c:out value="${totShramdaans}" /></b></td>
								<td class="text-end"><b><c:out value="${totCleaniness}" /></b></td>
								<td class="text-end"><b><c:out value="${totAwareness}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${projLvlWSPrgListSize==0}">
							<tr>
								<td align="center" colspan="15" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
           </c:if>
           
            <c:if test="${distWSProjListSize ne null}">
                <table class="table table-bordered table-striped" id="stWMI" >
                    <thead>
                    <tr>
                        <th style="text-align:left;" colspan = "15">State Name: ${stName}</th>
                    </tr>
                        <tr>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">District Name</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Number of Villages Involved</th>
						<th colspan="6" style="text-align:center; vertical-align: middle;">Total Number of People Participated</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of Photographs Uploaded </th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Number of Saplings Planted</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Total Number of Works for Lokarpan</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Shramdaan Undertaken on Total Number of Locations</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Number of Cleaniness Drives Organised</th>
						<th rowspan="2" style="text-align:center; vertical-align: middle;">Awareness Sessions Oraganised</th>
					</tr>
					<tr>
					    <th style="text-align:center; vertical-align: middle;">SHG</th>
						<th style="text-align:center; vertical-align: middle;">User Groups</th>
						<th style="text-align:center; vertical-align: middle;">FPO Youth</th>
						<th style="text-align:center; vertical-align: middle;">Students</th>
						<th style="text-align:center; vertical-align: middle;">Others</th>
						<th style="text-align:center; vertical-align: middle;">Total</th>
					</tr>
					<tr>
						<% for (int i = 1; i <= 15; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
                    </thead>
                    
                      <tbody id="tbodyProjLvlProgRpt">
						<c:forEach items="${distWSProjList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td class="text-left"><c:out value="${dt.distname}" /></td> 
								<td class="text-end"><c:out value="${dt.village}"/></td>
 								<td class="text-end"><c:out value="${dt.shg}"/></td>
 								<td class="text-end"><c:out value="${dt.ug}"/></td>
 								<td class="text-end"><c:out value="${dt.fpo}"/></td>
 								<td class="text-end"><c:out value="${dt.youth}"/></td>
 								<td class="text-end"><c:out value="${dt.other}"/></td>
 								<td class="text-end"><c:out value="${dt.total}"/></td>
								<td class="text-end">
									<c:choose>
										<c:when test="${dt.total_photos == 0}">
										</c:when>
										<c:otherwise>
											<a href="#" data-id="${dt.dcode}" data-type="projectlvl" class="showDistImage" data-toggle="modal" style="color: blue;">
												<c:out value="${dt.total_photos}" />
											</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="text-end"><c:out value="${dt.no_sapling}"/></td>
 								<td class="text-end"><c:out value="${dt.no_works_lokarpan}"/></td>
 								<td class="text-end"><c:out value="${dt.no_location_shramdaan}"/></td>
 								<td class="text-end"><c:out value="${dt.no_cleanliness}"/></td>
								<td class="text-end"><c:out value="${dt.no_awareness}"/></td>
								</tr>
							
							<c:set var="totVillage" value="${totVillage + dt.village}" />
 							<c:set var="totShg" value="${totShg + dt.shg}" /> 
							<c:set var="totUg" value="${totUg + dt.ug}" />
							<c:set var="totFpo" value="${totFpo + dt.fpo}" />
							<c:set var="totYouth" value="${totYouth + dt.youth}" />
							<c:set var="totOther" value="${totOther + dt.other}" />
							<c:set var="totTotal" value="${totTotal + dt.total}" />
							
							<c:set var="totTotPhotos" value="${totTotPhotos + dt.total_photos}" />
							<c:set var="totSaplings" value="${totSaplings + dt.no_sapling}" />
							<c:set var="totLokarpans" value="${totLokarpans + dt.no_works_lokarpan}" />
							<c:set var="totShramdaans" value="${totShramdaans + dt.no_location_shramdaan}" />
							<c:set var="totCleaniness" value="${totCleaniness + dt.no_cleanliness}" />
							<c:set var="totAwareness" value="${totAwareness + dt.no_awareness}" />
							
						</c:forEach>
						<c:if test="${distWSProjListSize>0}">
							<tr class="table-secondary fw-bold">
 								<td colspan="2" class="text-end"><b>Grand Total</b></td> 
								<td class="text-end"><b><c:out value="${totVillage}" /></b></td>
								<td class="text-end"><b><c:out value="${totShg}" /></b></td>
								<td class="text-end"><b><c:out value="${totUg}" /></b></td>
								<td class="text-end"><b><c:out value="${totFpo}" /></b></td>
								<td class="text-end"><b><c:out value="${totYouth}" /></b></td>
								<td class="text-end"><b><c:out value="${totOther}" /></b></td>
								<td class="text-end"><b><c:out value="${totTotal}" /></b></td>
								
								<td class="text-end"><b><c:out value="${totTotPhotos}" /></b></td>
								<td class="text-end"><b><c:out value="${totSaplings}" /></b></td>
								<td class="text-end"><b><c:out value="${totLokarpans}" /></b></td>
								<td class="text-end"><b><c:out value="${totShramdaans}" /></b></td>
								<td class="text-end"><b><c:out value="${totCleaniness}" /></b></td>
								<td class="text-end"><b><c:out value="${totAwareness}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${distWSProjListSize==0}">
							<tr>
								<td align="center" colspan="15" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
           </c:if> 
            
            
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
    
    <span id="prevImage" class="nav-arrow" onclick="showPrevImage()">&#10094;</span>
    <img id="largeImage" src="" alt="Large Image">
    <span id="nextImage" class="nav-arrow" onclick="showNextImage()">&#10095;</span>
</div>
</div>


<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>