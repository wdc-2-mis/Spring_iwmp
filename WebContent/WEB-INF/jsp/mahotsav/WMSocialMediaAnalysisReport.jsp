<!DOCTYPE html>
<html>
<head>
<title>Report WM5 - Watershed Mahotsav Social Media Analysis as per Entries</title>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="/WEB-INF/jspf/mahotsavReportheader.jspf"%>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>

<script>
$(document).ready(function () {

    // Load districts on state change
    $('#state').change(function () {
        $.post('getDistrictsByStateForWM',
            { state: $(this).val() },
            function (data) {
                $('#district').empty()
                    .append('<option value="0">--All District--</option>');
                $.each(data, function (key, value) {
                    $('#district').append(
                        '<option value="' + value + '">' + key + '</option>');
                });
            });
    });

    // Get button submit
    $('#submitBtn').click(function () {
        $('#mohotsavReport').submit();
    });

    // Order By auto submit
    $('#orderBy').change(function () {
        $('#mohotsavReport').submit();
    });

    $('#stWMR').DataTable({
   	 "paging": true,          // Enable pagination
        "pageLength": 10,        // Default rows per page
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],  // Dropdown options with "All"
        "lengthChange": true,    // Allow changing page size
        "searching": true,       // Enable search box
        "ordering": false,        // Disable sorting
        "info": true             // Show info (e.g., "Showing 1 to 10 of 50 entries")
   });
});
function downloadPDF() {
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
//     var media = document.getElementById("platform").options[document.getElementById("platform").selectedIndex].text;

    document.getElementById("stName").value = stName;
    document.getElementById("distName").value = distName;
//     document.getElementById("media").value = media;

    document.mohotsavRpt.action = "downloadPDFwmSocialMediaAnalysisReport";
    document.mohotsavRpt.method = "post";
    document.mohotsavRpt.submit();
}

function closeLargeImagePopup() {
	document.getElementById('largeImagePopup').style.display = 'none';
}

function showimage(file) {

//		 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/wmMediaViewsScreenshot/' + file;		
	// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/wmMediaViewsScreenshot/' + file;

	//local				
	document.getElementById('largeImage').src = 'resources/images/wmMediaViewsScreenshot/'	+ file;
	document.getElementById('largeImagePopup').style.display = 'block';
}
</script>

<style>

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
/* Add margin above the DataTables controls */
div.dataTables_wrapper div.dataTables_length, div.dataTables_wrapper div.dataTables_filter
	{
	margin-bottom: 20px; /* gap between controls and table */
}

/* Add margin above the pagination/info section */
div.dataTables_wrapper div.dataTables_info, div.dataTables_wrapper div.dataTables_paginate
	{
	margin-top: 20px; /* gap between table and pagination */
}
.facebook  { color:#1877F2; font-weight:600; }
.instagram { color:#C13584; font-weight:600; }
.youtube   { color:#FF0000; font-weight:600; }
.twitter   { color:#1DA1F2; font-weight:600; }
.linkedin  { color:#0A66C2; font-weight:600; }
</style>

</head>

<body>

<div class="maindiv">
<div class="card shadow mt-1 p-5">

<h4 class="text-center text-primary mb-4 text-decoration-underline">
Report WM5 - Watershed Mahotsav Social Media Analysis as per Entries
</h4>

<form id="mohotsavReport" name="mohotsavRpt" action="wmSocialMediaAnalysisReport" method="post">
			<input type="hidden" name="stName" id="stName" value="" />
    		<input type="hidden" name="distName" id="distName" value="" />
    		<input type="hidden" name="mediaName" id="mediaName" value="" />

	<div class="row mb-3">
		<div class="col-md-2">
				<label>State</label>
				<select name="state" id="state" class="form-select">
				<option value="0">--All State--</option>
					<c:forEach items="${stateList}" var="s">
					<option value="${s.key}" ${s.key eq state ? 'selected' : ''}>${s.value}</option>
					</c:forEach>
					</select>
					</div>

		<div class="col-md-2">
				<label>District</label>
				<select name="district" id="district" class="form-select">
				<option value="0">--All District--</option>
				<c:forEach items="${districtList}" var="d">
				<option value="${d.value}" ${d.value eq district ? 'selected' : ''}>${d.key}</option>
				</c:forEach>
				</select>
			</div>

			<div class="col-md-2">
					<label>Platform</label>
					<select name="platform" class="form-select">
					<option value="0">--All Platform--</option>
				<c:forEach items="${platformList}" var="p">
				<option value="${p.key}" ${p.key eq platform ? 'selected' : ''}>${p.value}</option>
				</c:forEach>
				</select>
			</div>

		<div class="col-md-2 d-flex align-items-end">
			<button type="button" id="submitBtn" class="btn btn-primary px-4">Get</button>
		</div>

	</div>

<!-- ORDER BY (ONLY AFTER DATA LOADS) -->
				<c:if test="${wmListSize > 0}">
					<div class="row mb-3">
					<div class="col-md-2">
					<label>Order By</label>
					<select name="orderBy" id="orderBy" class="form-select">
					<option value="views" ${empty orderBy || orderBy eq 'views' ? 'selected' : ''}>Views</option>
					<option value="likes" ${orderBy eq 'likes' ? 'selected' : ''}>Likes</option>
					</select>
					</div>
					</div>
				</c:if>

	</form>
<div class="nav-item text-left mb-2">
<%-- 				<c:if test="${not empty wmList}"> --%>
<!-- 					<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()">Excel</button> -->
<!-- 					<button type="button" name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button> -->
<%-- 				</c:if> --%>
				<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
			</div>
<c:if test="${wmListSize > 0}">
<table class="table table-bordered table-striped mt-3" id="stWMR">
<thead>
				<tr>
				<th class="text-center">S.No.</th>
				<th class="text-center">State</th>
				<th class="text-center">District</th>
				<th class="text-center">Reg. No</th>
				<th class="text-center">Name</th>
				<th class="text-center">Platform</th>
				<th class="text-center">Link</th>
				<th class="text-center">Views</th>
				<th class="text-center">Likes</th>
				<th class="text-center">Comments</th>
				<th class="text-center">Shares</th>
				<th class="text-center">Image</th>
				</tr>
					<tr>
			<th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
					</tr>
</thead>

<tbody>
			<c:forEach items="${wmList}" var="dt" varStatus="i">
				<tr>
						<td>${i.count}</td>
						<td>${dt.stname}</td>
						<td>${dt.dist_name}</td>
						<td>${dt.user_reg_no}</td>
						<td>${dt.reg_name}</td>
						<td><c:set var="m" value="${fn:toLowerCase(dt.media_name)}"/>
						<span class="${m}"> ${dt.media_name}</span></td>
						<td class="text-center"><c:if test="${not empty dt.media_url}">
						<a href="${dt.media_url}" target="_blank"><i class="fas fa-eye text-primary"></i></a>
						</c:if>
						</td>
						<td class="text-end">${dt.no_of_views}</td>
						<td class="text-end">${dt.no_of_likes}</td>
						<td class="text-end">${dt.no_of_comments}</td>
						<td class="text-end">${dt.no_of_shares}</td>
<!-- 						<td class="text-end">view</td> -->
<!-- 						<td class="text-end"> -->
<%-- 									<c:choose> --%>
<%-- 										<c:when test="${dt.media_view_url eq null}"> --%>
<!-- 											blank -->
<%-- 										</c:when> --%>
<%-- 										<c:otherwise> --%>
<%-- 										<a href="#" data-id="${dt.media_view_url}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="view" /></a> --%>
<%-- 											<c:out value="${dt.image_count}" /> --%>
<%-- 										</c:otherwise> --%>
<%-- 									</c:choose> --%>
									
<!-- 								</td> -->

								<td class="text-center"><a href="#" class="showImage"
														data-toggle="modal" style="color: blue;"
														onclick="showimage('${dt.mediaFileName}')">view</a></td>
				</tr>
			</c:forEach>
</tbody>
</table>
</c:if>

<c:if test="${wmListSize == 0}"><p class="text-center text-danger">No records found</p></c:if>

</div>
</div>
<!-- Show Image Modal HTML -->
	<div id="largeImagePopup" class="popup" style="display: none;">
		<div class="large-image-popup-content">
			<span class="close" onclick="closeLargeImagePopup()">&times;</span>
			<img id="largeImage" src="" alt="Large Image" />
		</div>
		
	</div>

<br>
<footer class="text-center">
<%@ include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>

</body>
</html>
