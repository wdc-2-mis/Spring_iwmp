<!DOCTYPE html>
<html>
<head>

<title>Report WM4 - Watershed Mahotsav Social Media</title>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jspf/mahotsavReportheader.jspf"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" />
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<script type="text/javascript">

$(document).ready(function() {

    // State → District
    $('#state').on('change', function() {
        var stateId = $(this).val();
        $.ajax({
            url: 'getDistrictsByState',
            type: 'POST',
            data: { state: stateId },
            success: function(data) {
                $('#district').empty().append('<option value="0">--All District--</option>');
                $.each(data, function(key, value) {
                    $('#district').append('<option value="'+value+'">'+key+'</option>');
                });
                $('#block').html('<option value="0">--All Block--</option>');
                $('#village').html('<option value="0">--All Village--</option>');
            }
        });
    });

    // District → Block
    $('#district').on('change', function() {
        var stateId = $('#state').val();
        var districtId = $(this).val();
        $.ajax({
            url: 'getBlocksByDistrict',
            type: 'POST',
            data: { state: stateId, district: districtId },
            success: function(data) {
                $('#block').empty().append('<option value="0">--All Block--</option>');
                $.each(data, function(key, value) {
                    $('#block').append('<option value="'+value+'">'+key+'</option>');
                });
                $('#village').html('<option value="0">--All Village--</option>');
            }
        });
    });

    // Block → Village
    $('#block').on('change', function() {
        var blockId = $(this).val();
        $.ajax({
            url: 'getVillagesByBlock',
            type: 'POST',
            data: { block: blockId },
            success: function(data) {
                $('#village').empty().append('<option value="0">--All Village--</option>');
                $.each(data, function(key, value) {
                    $('#village').append('<option value="'+value+'">'+key+'</option>');
                });
            }
        });
    });

    // Submit report
    $('#submitBtn').on('click', function() {
        document.mohotsavRpt.action = "wmSocialMediaReport";
        document.mohotsavRpt.method = "post";
        document.mohotsavRpt.submit();
    });
}); 


function dataAvailableFunction() {

$(document).ready(function () {
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
}


function validateUrl(url) {
    if (!url) return "about:blank"; // fallback if empty

    // Trim spaces and remove accidental "%20" encodings
    url = url.trim().replace(/%20/g, "");

    // Fix malformed "https//" (missing colon)
    url = url.replace(/https\/\//gi, "https://");

    // Remove duplicate "https://" at the start
    url = url.replace(/^(https:\/\/)+/i, "https://");

    // Ensure proper protocol (only http/https allowed)
    if (!/^https?:\/\//i.test(url)) {
        url = "https://" + url;
    }

    return url;
}

function openCenteredPopup(url, name, width, height) {
    // Get screen dimensions
    const screenWidth = window.screen.availWidth;
    const screenHeight = window.screen.availHeight;
    
    // Ensure width/height don't exceed screen size
    width = Math.min(width, screenWidth - 100);
    height = Math.min(height, screenHeight - 100);
    
    // Calculate center position
    const left = Math.max(0, (screenWidth - width) / 2);
    const top = Math.max(0, (screenHeight - height) / 2);
    
    // Enhanced features
    const features = `
        width=${width},
        height=${height},
        top=${top},
        left=${left},
        screenX=${left},
        screenY=${top},
        resizable=yes,
        scrollbars=yes,
        toolbar=yes,
        menubar=no,
        location=yes,
        status=yes,
        directories=no,
        fullscreen=no
    `.replace(/\s+/g, ''); // Remove whitespace
    
    // Open the popup
    const popup = window.open(url, name, features);
    
    // Focus the popup
    if (popup) {
        popup.focus();
        
        // Add a small delay and try to resize/move to ensure it's positioned correctly
        setTimeout(() => {
            try {
                popup.resizeTo(width, height);
                popup.moveTo(left, top);
            } catch(e) {
                // Ignore cross-origin errors
            }
        }, 100);
    }
    
    // Fallback if popup is blocked
    if (!popup || popup.closed || typeof popup.closed == 'undefined') {
        alert('Popup blocked! Please allow popups for this site.');
        // Open in new tab as fallback
        window.open(url, '_blank');
    }
    
    return popup;
}

function openVideoPlayer(url) {
    // Ensure URL has proper protocol
    url = validateUrl(url);
    
    let embedUrl = url;
    let popupName = "";
    
 	// YouTube
    if (url.includes("youtube.com") || url.includes("youtu.be")) {
        embedUrl = url;
        popupName = "ytPopup";
    }
    // Facebook
    else if (url.includes("facebook.com")) {
        embedUrl = url;
        popupName = "fbPopup";
    }
    // Instagram
    else if (url.includes("instagram.com")) {
        embedUrl = url;
        popupName = "igPopup";
    }
    // Twitter/X
    else if (url.includes("twitter.com") || url.includes("x.com")) {
        embedUrl = url;
        popupName = "twPopup";
    }
    // LinkedIn
    else if (url.includes("linkedin.com")) {
        embedUrl = url;
        popupName = "liPopup";
    }
    // Direct MP4 or other video files
    else if (url.match(/\.(mp4|webm|ogg|mov|avi|wmv|flv)$/i)) {
        embedUrl = url;
        popupName = "videoPopup";
    }
    // Default
    else {
        embedUrl = "about:blank";
        popupName = "popup";
    }
    
    // Open the popup with unique name to prevent overwriting
    openCenteredPopup(embedUrl, popupName + "_" + Date.now(), 800, 600);
}


function downloadPDF(){
		document.mohotsavRpt.action="downloadPDFStWMInauguration";
		document.mohotsavRpt.method="post";
		document.mohotsavRpt.submit();
}

function exportExcel(){
		document.mohotsavRpt.action="downloadExcelStWMInauguration";
		document.mohotsavRpt.method="post";
		document.mohotsavRpt.submit();
}

</script>

<style>
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
</style>

<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>
</head>
<body>

	<c:if test="${stateWMSocialMediaListSize !=0}">

		<script>
			// Call the function automatically when page loads
			dataAvailableFunction();
		</script>

	</c:if>

	<div class="maindiv">

		<div class="card shadow mt-1 p-5">

			<div class="offset-md-3 col-6 formheading" style="text-align: center;">
				<h4 class="text-center text-primary mb-4 text-decoration-underline">Report WM4 - Watershed Mahotsav Social Media</h4>
			</div>

			<div class="nav-item text-left mb-2">
				<c:if test="${not empty stateWMInaugurationList1}">
					<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()">Excel</button>
					<button type="button" name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button>
				</c:if>
				<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
			</div>

			<form name="mohotsavRpt" id="mohotsavReport" action="wmSocialMediaReport" method="post">


				<div class="row mb-3">

					<div class="col-md-2">
						<label>State <span class="required">*</span></label> <select
							name="state" id="state" class="form-select">
							<option value="0">--All State--</option>
							<c:forEach items="${stateList}" var="lists">
								<option value="${lists.key}"
									${lists.key eq state ? 'selected' : ''}>${lists.value}</option>
							</c:forEach>
						</select>
						<div id="stateError" class="error-text">Please select state</div>
					</div>


					<div class="col-md-2">
						<label>District <span class="required">*</span></label> <select
							name="district" id="district" class="form-select">
							<option value="0">--All District--</option>
							<c:forEach items="${districtList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq district ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
						<div id="districtError" class="error-text">Please select
							district</div>
					</div>

					<div class="col-md-2">
						<label>Block <span class="required">*</span></label> <select
							name="block" id="block" class="form-select">
							<option value="0">--All Block--</option>
							<c:forEach items="${blockList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq blkd ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
						<div id="blockError" class="error-text">Please select block</div>
					</div>

					<div class="col-md-2">
						<label>Village <span class="required">*</span></label> <select
							name="village" id="village" class="form-select">
							<option value="0">--All Village--</option>
							<c:forEach items="${villageList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq vlg ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
						<div id="villageError" class="error-text">Please select
							village</div>
					</div>
						<div class="col-md-2">
							<label>Media Type<span class="required">*</span></label><br>

							<c:choose>
								<c:when test="${empty selectedMediaType}">
									<input type="radio" name="mediaType" value="ALL" checked> All
                					<input type="radio" name="mediaType" value="P"> Photo
                					<input type="radio" name="mediaType" value="V"> Video
            					</c:when>

								<c:otherwise>
									<input type="radio" name="mediaType" value="ALL" ${selectedMediaType eq 'ALL' ? 'checked' : ''}> All
                						<input type="radio" name="mediaType" value="P" ${selectedMediaType eq 'P' ? 'checked' : ''}> Photo
                						<input type="radio" name="mediaType" value="V" ${selectedMediaType eq 'V' ? 'checked' : ''}> Video
            					</c:otherwise>
							</c:choose>
						</div>


					<div class="col-md-2 d-flex align-items-end">
						<button type="button" id="submitBtn" class="btn btn-primary px-5">Get</button>
					</div>
				</div><br>
			</form>

			<table class="table table-bordered table-striped" id="stWMR">
				<thead>
					<tr>
						<th rowspan="2" style="text-align: center; vertical-align: middle;">S.No.</th>
						<th rowspan="2" style="text-align: center; vertical-align: middle;">Registration Number</th>
						<th rowspan="2" style="text-align: center; vertical-align: middle;">Name</th>
						<th rowspan="2" style="text-align: center; vertical-align: middle;">Contact Number</th>
						<th colspan="${selectedMediaType eq 'P' ? 4 : 5}" style="text-align: center; vertical-align: middle;">
							<c:choose>
								<c:when test="${selectedMediaType eq 'P'}">List of Uploaded Photos</c:when>
								<c:otherwise>List of Uploaded Videos</c:otherwise>
							</c:choose>
						</th>
					</tr>
					<tr>
						<th style="text-align: center; vertical-align: middle;"><i class="fab fa-facebook" style="color: white; background-color: #1877f2; padding: 5px; border-radius: 50%; margin-right: 5px;"></i>Facebook</th>
						<c:if test="${selectedMediaType ne 'P'}">						
						<th style="text-align: center; vertical-align: middle;"><i class="fab fa-youtube" style="color: white; background-color: #1877f2; padding: 5px; border-radius: 50%; margin-right: 5px;"></i>YouTube</th>
						</c:if>
						<th style="text-align: center; vertical-align: middle;"><i class="fab fa-instagram" style="color: white; background-color: #1877f2; padding: 5px; border-radius: 50%; margin-right: 5px;"></i>Instagram</th>
						<th style="text-align: center; vertical-align: middle;"><i class="fab fa-twitter" style="color: white; background-color: #1877f2; padding: 5px; border-radius: 50%; margin-right: 5px;"></i>Twitter</th>
						<th style="text-align: center; vertical-align: middle;"><i class="fab fa-linkedin" style="color: white; background-color: #1877f2; padding: 5px; border-radius: 50%; margin-right: 5px;"></i>LinkedIn</th>
					</tr>

					<tr>
						<%
						int maxCols = "P".equals(request.getAttribute("selectedMediaType")) ? 8 : 9;
						for (int i = 1; i <= maxCols; i++) {
						%>
						<th class="text-center"><%=i%></th>
						<%
						}
						%>
					</tr>
				</thead>

				<tbody id="tbodyWMSocialMediaRpt">

					<c:set var="sno" value="0" />
					<c:set var="regNo" value="" />
					<c:set var="name" value="" />
					<c:set var="phno" value="" />

					<c:forEach items="${stateWMSocialMediaList}" var="dt">
						<tr>
							<td class="text-left">
								<c:if test="${dt.user_reg_no ne regNo}">
									<c:set var="sno" value="${sno + 1}" />
									<c:out value="${sno}" /> 
								</c:if>
							</td>
							
							<td><c:if test="${dt.user_reg_no ne regNo}">
									<c:out value="${dt.user_reg_no}" />
								</c:if>
							</td>
							
							<td><c:if test="${dt.reg_name ne name or dt.user_reg_no ne regNo}"> 
									<c:out value="${dt.reg_name}" />
								</c:if>
							</td>

							<td class="text-center"><c:if test="${dt.phno ne phno or dt.user_reg_no ne regNo}">
									<c:out value="${dt.phno}" />
								</c:if>
							</td>

							<td class="text-center">
								<a href="javascript:void(0);" onclick="openVideoPlayer('${dt.facebook_urls}')">${dt.facebook_urls}</a>
							</td>
							
							<c:if test="${selectedMediaType ne 'P'}">
							<td class="text-center">
								<a href="javascript:void(0);" onclick="openVideoPlayer('${dt.youtube_urls}')">${dt.youtube_urls}</a>
							</td>
							</c:if>
							
							<td class="text-center">
								<a href="javascript:void(0);" onclick="openVideoPlayer('${dt.instagram_urls}')">${dt.instagram_urls}</a>
							</td>

							<td class="text-center">
								<a href="javascript:void(0);" onclick="openVideoPlayer('${dt.twitter_urls}')">${dt.twitter_urls}</a>
							</td>
							
							<td class="text-center">
								<a href="javascript:void(0);" onclick="openVideoPlayer('${dt.linkedin_urls}')">${dt.linkedin_urls}</a>
							</td>
							
						</tr>


						<c:set var="regNo" value="${dt.user_reg_no}" />
						<c:set var="name" value="${dt.reg_name}" />
						<c:set var="phno" value="${dt.phno}" />


					</c:forEach>

					<c:if test="${stateWMSocialMediaListSize==0}">
						<tr>
							<td align="center" colspan="9" class="required" style="color: red;"><b>Data Not Found</b></td>
						</tr>
					</c:if>

				</tbody>
			</table>

		</div>
	</div>


	<footer class="text-center">
		<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
	</footer>

</body>
</html>
