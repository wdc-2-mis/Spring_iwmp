<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<title>Report SMC1 - Watershed Mahotsav Social Media Analysis as per Entries</title>



<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>

<script>
$(document).ready(function () {

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

    $('#stWMR').DataTable({
        paging: true,
        pageLength: 10,
        lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
        searching: true,
        ordering: false,
        info: true
    });
});


function closeLargeImagePopup() {
	document.getElementById('largeImagePopup').style.display = 'none';
}

function showimage(file) {

// 		document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/wmMediaViewsScreenshot/' + file;		
	// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/wmMediaViewsScreenshot/' + file;

	//local				
	document.getElementById('largeImage').src = 'resources/images/wmMediaViewsScreenshot/'	+ file;
	document.getElementById('largeImagePopup').style.display = 'block';
}

function downloadsPDF() {

    var pdfForm = document.createElement("form");
    pdfForm.method = "post";
    pdfForm.action = "downloadPDFwmSocialMediaAnalysisReport";

    function addField(name, value) {
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = name;
        input.value = value;
        pdfForm.appendChild(input);
    }
    addField("state", document.getElementById("state").value);
    addField("district", document.getElementById("district").value);
    addField("platform", document.getElementById("platform").value);

    var orderByEl = document.getElementById("orderBy");
    addField("orderBy", orderByEl ? orderByEl.value : "views");

    var screenshotEl = document.getElementById("screenshotOnly");
    addField("screenshotOnly", screenshotEl && screenshotEl.checked ? "Y" : "");

    addField("stName",
        document.getElementById("state").selectedOptions[0].text);

    addField("distName",
        document.getElementById("district").selectedOptions[0].text);

    addField("mediaName",
        document.getElementById("platform").selectedOptions[0].text);

    document.body.appendChild(pdfForm);
    pdfForm.submit();
    document.body.removeChild(pdfForm);
}

function exportExcel() {

    var pdfForm = document.createElement("form");
    pdfForm.method = "post";
    pdfForm.action = "downloadExcelwmSocialMediaAnalysisReport";

    function addField(name, value) {
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = name;
        input.value = value;
        pdfForm.appendChild(input);
    }
    addField("state", document.getElementById("state").value);
    addField("district", document.getElementById("district").value);
    addField("platform", document.getElementById("platform").value);

    var orderByEl = document.getElementById("orderBy");
    addField("orderBy", orderByEl ? orderByEl.value : "views");

    var screenshotEl = document.getElementById("screenshotOnly");
    addField("screenshotOnly", screenshotEl && screenshotEl.checked ? "Y" : "");

    addField("stName",
        document.getElementById("state").selectedOptions[0].text);

    addField("distName",
        document.getElementById("district").selectedOptions[0].text);

    addField("mediaName",
        document.getElementById("platform").selectedOptions[0].text);

    document.body.appendChild(pdfForm);
    pdfForm.submit();
    document.body.removeChild(pdfForm);
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
<div class="table-responsive">

<div class="col formheading" style>
<h4 class="text-center mb-4 text-decoration-underline"
    style="color:white;">
    Report SMC1 - Watershed Mahotsav Social Media Analysis as per Entries
</h4>


</div>

<form id="mohotsavReport" name="mohotsavRpt"
      action="wmSocialMediaAnalysisReport" method="post">

<input type="hidden" name="stName" id="stName">
<input type="hidden" name="distName" id="distName">
<input type="hidden" name="mediaName" id="mediaName">
<!-- <input type="hidden" name="orderBy" id="orderByVal"> -->
<br>

    <!-- STATE -->
    <div class="row mb-4 align-items-end g-3">

    <!-- STATE -->
    <div class="col-lg-3">
        <label>State</label>
        <select name="state" id="state" class="form-select">
            <option value="0">--All State--</option>
            <c:forEach items="${stateList}" var="s">
                <option value="${s.key}"
                    ${s.key eq state ? 'selected' : ''}>
                    ${s.value}
                </option>
            </c:forEach>
        </select>
    </div>

    <!-- DISTRICT -->
    <div class="col-lg-3">
        <label>District</label>
        <select name="district" id="district" class="form-select">
            <option value="0">--All District--</option>
            <c:forEach items="${districtList}" var="d">
                <option value="${d.value}"
                    ${d.value eq district ? 'selected' : ''}>
                    ${d.key}
                </option>
            </c:forEach>
        </select>
    </div>

    <!-- PLATFORM -->
    <div class="col-lg-3">
        <label>Platform</label>
        <select name="platform" id="platform" class="form-select">
            <option value="0">--All Platform--</option>
            <c:forEach items="${platformList}" var="p">
                <option value="${p.key}"
                    ${p.key eq platform ? 'selected' : ''}>
                    ${p.value}
                </option>
            </c:forEach>
        </select>
    </div>

    <!-- CHECKBOX -->
    <div class="col-lg-2">
        <div class="form-check">
            <input class="form-check-input"
                   type="checkbox"
                   id="screenshotOnly"
                   name="screenshotOnly"
                   value="Y"
                   ${screenshotOnly eq 'Y' ? 'checked' : ''}>
            <label class="form-check-label" for="screenshotOnly">
                Screenshot Uploaded
            </label>
        </div>
    </div>

    <!-- GET BUTTON -->
    <div class="col-lg-1 d-flex justify-content-end">
        <button type="submit" class="btn btn-primary px-4">
            Get
        </button>
    </div>

</div>
<c:if test="${wmListSize gt 0}">
    <div class="row mb-3">
        <div class="col-lg-3">
            <label>Order By</label>
            <select name="orderBy"
                    class="form-select"
                    onchange="this.form.submit();">
                <option value="views"
                    ${orderBy eq 'views' ? 'selected' : ''}>
                    Views
                </option>
                <option value="likes"
                    ${orderBy eq 'likes' ? 'selected' : ''}>
                    Likes
                </option>
            </select>
        </div>
    </div>
</c:if>



</form>

<div class="nav-item text-left mb-2">
				<c:if test="${not empty wmList}">
					<button type="button" name="exportExcel" id="exportExcel" class="btn btn-info" onclick="exportExcel()">Excel</button>
					<button type="button" name="exportPDF" id="exportPDF" class="btn btn-info" onclick="downloadsPDF()">PDF</button>
					<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
				</c:if>
				
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
						<td class="text-center">
    <c:choose>
        <c:when test="${not empty dt.mediaFileName}">
            <a href="#" class="showImage" data-toggle="modal" style="color: blue;"
               onclick="showimage('${dt.mediaFileName}')">view</a>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</td>
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
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

</body>
</html>
