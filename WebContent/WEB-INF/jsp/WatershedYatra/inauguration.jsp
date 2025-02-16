<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/inauguration.js" />'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script type="text/javascript">

let formSubmitted = false;
let allValid = true;
function validation() 
{
	if (formSubmitted) return false;
	allValid = true;
	
	var allowedFiles = [".jpg", ".jpeg",".png"];
	
	$district = $('#district option:selected').val();
	$block = $('#block option:selected').val();

	$date = $('#date').val();
	$location = $('#location').val();
	$male_participants = $('#male_participants').val();
	$female_participants = $('#female_participants').val();
	$central_ministers = $('#central_ministers').val();
	$stateMinisters = $('#stateMinisters').val();
	$parliament = $('#parliament').val();
	$assembly_members = $('#assembly_members').val();
	$council_members = $('#council_members').val();
	$others = $('#others').val();
	$gov_officials = $('#gov_officials').val();
	$flagoff = $('input[name="flagoff"]:checked').val();
	$flagOffPhotos = $('#flagOffPhotos').val();
	$themesong = $('input[name="themesong"]:checked').val();
	$themeSongPhotos = $('#themeSongPhotos').val();
	$no_works_bhoomipoojan = $('#no_works_bhoomipoojan').val();
	$tot_works_bhoomipoojan = $('#tot_works_bhoomipoojan').val();
	$bhoomiPoojanPhotos = $('#bhoomiPoojanPhotos').val();
	$no_works_lokarpan = $('#no_works_lokarpan').val();
	$tot_works_lokarpan = $('#tot_works_lokarpan').val();
	$lokarpanPhotos = $('#lokarpanPhotos').val();
	$no_location_shramdaan = $('#no_location_shramdaan').val();
	$no_people_shramdaan = $('#no_people_shramdaan').val();
	$man = $('#man').val();
	$shramdaanPhotos = $('#shramdaanPhotos').val();
	$area_plantation = $('#area_plantation').val();
	$noPlantation = $('#noPlantation').val();
	$plantationPhotos = $('#plantationPhotos').val();
	$no_awards = $('#no_awards').val();
	$awardPhotos = $('#awardPhotos').val();
	
	$dept_stalls = $('#dept_stalls').val();
	$shg_fpo_stalls = $('#shg_fpo_stalls').val();
	$no_lakhpati_didi = $('#no_lakhpati_didi').val();

	$flagoff_photo1 = $('#flagoff_photo1').val();
	$flagoff_photo2 = $('#flagoff_photo2').val();
	$themesong_photo1 = $('#themesong_photo1').val();
	$themesong_photo2 = $('#themesong_photo2').val();
	$bhoomipoojan_photo1 = $('#bhoomipoojan_photo1').val();
	$bhoomipoojan_photo2 = $('#bhoomipoojan_photo2').val();
	$lokarpan_photo1 = $('#lokarpan_photo1').val();
	$lokarpan_photo2 = $('#lokarpan_photo2').val();
	$shramdaan_photo1 = $('#shramdaan_photo1').val();
	$shramdaan_photo2 = $('#shramdaan_photo2').val();
	$plantation_photo1 = $('#plantation_photo1').val();
	$plantation_photo2 = $('#plantation_photo2').val();
	$award_photo1 = $('#award_photo1').val();
	$award_photo2 = $('#award_photo2').val();
	$dept_stalls_photo1 = $('#dept_stalls_photo1').val();
	$dept_stalls_photo2 = $('#dept_stalls_photo2').val();
	$shg_fpo_stalls_photo1 = $('#shg_fpo_stalls_photo1').val();
	$shg_fpo_stalls_photo2 = $('#shg_fpo_stalls_photo2').val();
	$lakhpati_didi_photo1 = $('#lakhpati_didi_photo1').val();
	$lakhpati_didi_photo2 = $('#lakhpati_didi_photo2').val();

	if ($date === '' || typeof $date === 'undefined') {
		alert('Please select a Date');
		$('#date').focus();
		allValid = false;
		return false;
	}
	if ($('#district option:selected').val() === '' || typeof $('#district option:selected').val() === 'undefined') {
		alert('Please select District');
		$('#district').focus();
		allValid = false;
		return false;
	}
	if ($block === '' || typeof $block === 'undefined') {
		alert('Please select Block');
		$('#block').focus();
		allValid = false;
		return false;
	}
	if ($location === '' || typeof $location === 'undefined') {
		alert('Please enter Location');
		$('#location').focus();
		allValid = false;
		return false;
	}
	if ($male_participants === '' || typeof $male_participants === 'undefined') {
		alert('Please enter the Number of Male Participants/Villagers');
		$('#male_participants').focus();
		allValid = false;
		return false;
	}
	if ($female_participants === '' || typeof $female_participants === 'undefined') {
		alert('Please enter the Number of Female Participants/Villagers');
		$('#female_participants').focus();
		allValid = false;
		return false;
	}
	if ($central_ministers === '' || typeof $central_ministers === 'undefined') {
		alert('Please enter the Number of Central Ministers');
		$('#central_ministers').focus();
		allValid = false;
		return false;
	}
	if ($stateMinisters === '' || typeof $stateMinisters === 'undefined') {
		alert('Please enter the Number of State Ministers');
		$('#stateMinisters').focus();
		allValid = false;
		return false;
	}
	if ($parliament === '' || typeof $parliament === 'undefined') {
		alert('Please enter the Number of Members of Parliament');
		$('#parliament').focus();
		allValid = false;
		return false;
	}
	if ($assembly_members === '' || typeof $assembly_members === 'undefined') {
		alert('Please enter the Number of Legislative Assembly Members');
		$('#assembly_members').focus();
		allValid = false;
		return false;
	}
	if ($council_members === '' || typeof $council_members === 'undefined') {
		alert('Please enter the Number of Legislative Council Members');
		$('#council_members').focus();
		allValid = false;
		return false;
	}
	if ($others === '' || typeof $others === 'undefined') {
		alert('Please enter the Number of other Public Representatives');
		$('#others').focus();
		allValid = false;
		return false;
	}
	if ($gov_officials === '' || typeof $gov_officials === 'undefined') {
		alert('Please enter the Number of Government Officials');
		$('#gov_officials').focus();
		allValid = false;
		return false;
	}
	if ($flagoff === '' || typeof $flagoff === 'undefined') {
		alert('Please select Flag off of Van');
// 		$('#flagoff').focus();
		$('input[name="flagoff"]').first().focus();
		allValid = false;
		return false;
	}
	if ($flagoff === 'true') {
	if ($flagoff_photo1 === '' || typeof $flagoff_photo1 === 'undefined') {
		alert('Please upload photo for the Flag off of Van');
// 		$('#flagoff_photo1').focus();
		document.getElementById('flagoff_photo1').click();
		allValid = false;
		return false;
	}if ($flagoff_photo2 === '' || typeof $flagoff_photo2 === 'undefined') {
		alert('Please upload photo for the Flag off of Van');
// 		$('#flagoff_photo2').focus();
		document.getElementById('flagoff_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($themesong === '' || typeof $themesong === 'undefined') {
		alert('Please select the Launch of the Theme Song');
// 		$('#themesong').focus();
		$('input[name="themesong"]').first().focus();
		allValid = false;
		return false;
	}
	if ($themesong === 'true') {
	if ($themesong_photo1 === '' || typeof $themesong_photo1 === 'undefined') {
		alert('Please upload photo for the Launch of the Theme Song');
// 		$('#themesong_photo1').focus();
		document.getElementById('themesong_photo1').click();
		allValid = false;
		return false;
	}if ($themesong_photo2 === '' || typeof $themesong_photo2 === 'undefined') {
		alert('Please upload photo for the Launch of the Theme Song');
// 		$('#themesong_photo2').focus();
		document.getElementById('themesong_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($no_works_bhoomipoojan === '' || typeof $no_works_bhoomipoojan === 'undefined') {
		alert('Please enter Number of Works of Bhoomi Poojan');
		$('#no_works_bhoomipoojan').focus();
		allValid = false;
		return false;
	}
	if ($tot_works_bhoomipoojan === '' || typeof $tot_works_bhoomipoojan === 'undefined') {
		alert('Please enter the Cost of the Total Works of Bhoomi Poojan in Lakhs');
		$('#tot_works_bhoomipoojan').focus();
		allValid = false;
		return false;
	}
	if ($no_works_bhoomipoojan > 0) {
	if ($bhoomipoojan_photo1 === '' || typeof $bhoomipoojan_photo1 === 'undefined') {
		alert('Please upload photo for Bhoomi Poojan');
// 		$('#bhoomipoojan_photo1').focus();
		document.getElementById('bhoomipoojan_photo1').click();
		allValid = false;
		return false;
	}if ($bhoomipoojan_photo2 === '' || typeof $bhoomipoojan_photo2 === 'undefined') {
		alert('Please upload photo for Bhoomi Poojan');
// 		$('#bhoomipoojan_photo2').focus();
		document.getElementById('bhoomipoojan_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($no_works_lokarpan === '' || typeof $no_works_lokarpan === 'undefined') {
		alert('Please enter the Number of Works of Lokarpan');
		$('#no_works_lokarpan').focus();
		allValid = false;
		return false;
	}
	if ($tot_works_lokarpan === '' || typeof $tot_works_lokarpan === 'undefined') {
		alert('Please enter the Cost of the Total Works of Lokarpan in Lakhs');
		$('#tot_works_lokarpan').focus();
		allValid = false;
		return false;
	}
	if ($no_works_lokarpan > 0) {
	if ($lokarpan_photo1 === '' || typeof $lokarpan_photo1 === 'undefined') {
		alert('Please upload photo for Lokarpan');
// 		$('#lokarpan_photo1').focus();
		document.getElementById('lokarpan_photo1').click();
		allValid = false;
		return false;
	}if ($lokarpan_photo2 === '' || typeof $lokarpan_photo2 === 'undefined') {
		alert('Please upload photo for Lokarpan');
// 		$('#lokarpan_photo2').focus();
		document.getElementById('lokarpan_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($no_location_shramdaan === '' || typeof $no_location_shramdaan === 'undefined') {
		alert('Please enter the Number of Locations of Shramdaan');
		$('#no_location_shramdaan').focus();
		allValid = false;
		return false;
	}
	if ($no_people_shramdaan === '' || typeof $no_people_shramdaan === 'undefined') {
		alert('Please enter No of people participated in Shramdaan');
		$('#no_people_shramdaan').focus();
		allValid = false;
		return false;
	}
	if ($man === '' || typeof $man === 'undefined') {
		alert('Please enter No of Man Hours in Shramdaan');
		$('#man').focus();
		allValid = false;
		return false;
	}
	if ($no_location_shramdaan > 0) {
	if ($shramdaan_photo1 === '' || typeof $shramdaan_photo1 === 'undefined') {
		alert('Please upload photo for Shramdaan');
// 		$('#shramdaan_photo1').focus();
		document.getElementById('shramdaan_photo1').click();
		allValid = false;
		return false;
	}if ($shramdaan_photo2 === '' || typeof $shramdaan_photo2 === 'undefined') {
		alert('Please upload photo for Shramdaan');
// 		$('#shramdaan_photo2').focus();
		document.getElementById('shramdaan_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($area_plantation === '' || typeof $area_plantation === 'undefined') {
		alert('Please enter the Plantation Area in hectares');
		$('#area_plantation').focus();
		allValid = false;
		return false;
	}
	if ($noPlantation === '' || typeof $noPlantation === 'undefined') {
		alert('Please enter the Number of Agro forestry / Horticultural Plants');
		$('#noPlantation').focus();
		allValid = false;
		return false;
	}
	if ($area_plantation > 0) {
	if ($plantation_photo1 === '' || typeof $plantation_photo1 === 'undefined') {
		alert('Please upload photo for Plantation');
// 		$('#plantation_photo1').focus();
		document.getElementById('plantation_photo1').click();
		allValid = false;
		return false;
	}if ($plantation_photo2 === '' || typeof $plantation_photo2 === 'undefined') {
		alert('Please upload photo for Plantation');
// 		$('#plantation_photo2').focus();
		document.getElementById('plantation_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($no_awards === '' || typeof $no_awards === 'undefined') {
		alert('Please enter the Number of Award Distribution');
		$('#no_awards').focus();
		allValid = false;
		return false;
	}
	if ($no_awards > 0) {
	if ($award_photo1 === '' || typeof $award_photo1 === 'undefined') {
		alert('Please upload photo for Award Distribution');
// 		$('#award_photo1').focus();
		document.getElementById('award_photo1').click();
		allValid = false;
		return false;
	}if ($award_photo2 === '' || typeof $award_photo2 === 'undefined') {
		alert('Please upload photo for Award Distribution');
// 		$('#award_photo2').focus();
		document.getElementById('award_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($dept_stalls === '' || typeof $dept_stalls === 'undefined') {
		alert('Please enter the Number of stalls of Departments');
		$('#dept_stalls').focus();
		allValid = false;
		return false;
	}
	if ($dept_stalls > 0) {
	if ($dept_stalls_photo1 === '' || typeof $dept_stalls_photo1 === 'undefined') {
		alert('Please upload photo for stalls of Departments');
// 		$('#dept_stalls_photo1').focus();
		document.getElementById('dept_stalls_photo1').click();
		allValid = false;
		return false;
	}if ($dept_stalls_photo2 === '' || typeof $dept_stalls_photo2 === 'undefined') {
		alert('Please upload photo for stalls of Departments');
// 		$('#dept_stalls_photo2').focus();
		document.getElementById('dept_stalls_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($shg_fpo_stalls === '' || typeof $shg_fpo_stalls === 'undefined') {
		alert('Please enter the Number of stalls of SHGs/FPOs');
		$('#shg_fpo_stalls').focus();
		allValid = false;
		return false;
	}
	if ($shg_fpo_stalls > 0) {
	if ($shg_fpo_stalls_photo1 === '' || typeof $shg_fpo_stalls_photo1 === 'undefined') {
		alert('Please upload photo for stalls of SHGs/FPOs');
// 		$('#shg_fpo_stalls_photo1').focus();
		document.getElementById('shg_fpo_stalls_photo1').click();
		allValid = false;
		return false;
	}if ($shg_fpo_stalls_photo2 === '' || typeof $shg_fpo_stalls_photo2 === 'undefined') {
		alert('Please upload photo for stalls of SHGs/FPOs');
// 		$('#shg_fpo_stalls_photo2').focus();
		document.getElementById('shg_fpo_stalls_photo2').click();
		allValid = false;
		return false;
	}
	}
	if ($no_lakhpati_didi === '' || typeof $no_lakhpati_didi === 'undefined') {
		alert('Please enter the Number of people participated in LakhPati Didi');
		$('#no_lakhpati_didi').focus();
		allValid = false;
		return false;
	}
	if ($no_lakhpati_didi > 0) {
	if ($lakhpati_didi_photo1 === '' || typeof $lakhpati_didi_photo1 === 'undefined') {
		alert('Please upload photo for LakhPati Didi');
// 		$('#lakhpati_didi_photo1').focus();
		document.getElementById('lakhpati_didi_photo1').click();
		allValid = false;
		return false;
	}if ($lakhpati_didi_photo2 === '' || typeof $lakhpati_didi_photo2 === 'undefined') {
		alert('Please upload photo for LakhPati Didi');
// 		$('#lakhpati_didi_photo2').focus();
		document.getElementById('lakhpati_didi_photo2').click();
		allValid = false;
		return false;
	}
	}

if (allValid) {
	if(confirm("Do you want to save Inauguration Program Details?")) {
    formSubmitted = true; 
	document.inauguration.action="saveInaugurationDetails";
	document.inauguration.method="post";
	document.inauguration.submit();
	}
	return true;
}

    return false;
}


function checkImage(input, inputId) {
    var file = input.files[0];
    var fileType = file.type;
    var fileSize = file.size; // Get the file size in bytes
    var maxFileSize = 300 * 1024; // Max size: 100KB (in bytes)
    var maxWidth = 400; // Max width in pixels
    var maxHeight = 400; // Max height in pixels

    // Check if the file is an image
    if (!fileType.startsWith('image/')) {
        alert('Only image files are allowed');
        input.value = ''; // Clear the file input
        document.getElementById(inputId).focus();
        return; // Stop further execution
    }

    // Check if the file size exceeds 100KB
    if (fileSize > maxFileSize) {
        alert('File size exceeds 300 KB. Please choose a smaller file.');
        input.value = ''; // Clear the file input
        document.getElementById(inputId).focus();
        return; // Stop further execution
    }

    var reader = new FileReader();
    reader.onload = function(e) {
        var img = new Image();
        img.onload = function() {
            var width = img.width;
            var height = img.height;

            // Check if the image dimensions exceed the max width or height
            if (width > maxWidth || height > maxHeight) {
                alert('Image dimensions exceed the allowed size of ' + maxWidth + 'x' + maxHeight + ' pixels.');
                input.value = ''; // Clear the file input
                document.getElementById(inputId).focus();
                return; // Stop further execution
            }

        };

        img.src = e.target.result;
    };

    reader.readAsDataURL(file);
}

	
function toggleFileUpload(radioId, filesId) {
	  var radioButtons = document.getElementsByName(radioId);
	  var fileUploadSection = document.getElementById(filesId);

	  for (var i = 0; i < radioButtons.length; i++) {
	    if (radioButtons[i].checked && radioButtons[i].value === "true") {
	      fileUploadSection.style.display = 'block';
	      return;
	    }
	  }

	  fileUploadSection.style.display = 'none';
}

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

<meta charset="ISO-8859-1">
<title>Inauguration Program</title>

<style>
input[type=text] {
	width: 100px;
	
}

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
#largeImagePopup {
  display: none; /* Hidden by default */
  position: fixed;
  top: 300px;
  left: 60%;
  width: 30%;
  transform: translateX(-50%);
  z-index: 1000;
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
<!--  
<c:if test="${result != null}">
	<script>
	    alert("<c:out value='${result}'/>");
	</script>
</c:if>
-->

<c:if test="${not empty result}">
    <div class="alert alert-info" style="color: green;">${result}</div>
     <script>
	    alert("<c:out value='${result}'/>");
	</script> 
</c:if>

<c:if test="${not empty result1}">
    <div class="alert alert-info" style="color: red;">${result1}</div>
     <script>
	    alert("<c:out value='${result1}'/>");
	</script> 
</c:if>

	<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Yatra - Inauguration Program</h4> </div>
		<label>
			<span style="color:blue;">Note:- The image size must be under 300KB, with dimensions of 300 x 400 pixels with Geo-referenced and Time-stamped.</span>
		</label>
<!-- 		<form name="inauguration" id="inauguration" modelAttribute="inauguration" action="saveInaugurationDetails" method="post" enctype="multipart/form-data"> -->
		<!-- <form name="inauguration" id="inauguration" modelAttribute="WatershedYatraInauguaration" enctype="multipart/form-data"> -->
		<form:form autocomplete="off" method="post" name="inauguration" id="inauguration" action="saveInaugurationDetails" modelAttribute="useruploadign" enctype="multipart/form-data">
			  <hr/>
			  <div class="row">
    			<div class="form-group col-3">
    			
      		  <label for="date">Date: </label>
      		  <input type="date" name="date" id="date" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-3">
			<c:if test="${userType== 'SL' }"><br/>
				 State Name: <br/>
				<c:out value="${stateName}"></c:out>
			</c:if>
			</div>
    		<div class="form-group col-3">
      			<label for="district">District: </label>
<!--       			<span class="projectError"></span> -->
      			<select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="block">Block: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block" name="block" >
    				<option value="">--Select Block--</option>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="location">Location (Nearby/Milestone):</label>
      			<input type="text" class="form-control activity" name="location" id="location" autocomplete="off" style="width: 100%; max-width: 800px;" />
    		</div>
    		
    		</div>
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=3 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td>Number of Participants/Villagers</td>
     		<td>Male<br><input type="text" id="male_participants" name="male_participants" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="female_participants" name="female_participants" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Ministers</td>
     		<td>Central Level<br><input type="text" id="central_ministers" name="central_ministers" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateMinisters" name="state_ministers" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td>Number of Member of Parliament</td>
     		<td colspan=2><input type="text" id="parliament" name="parliament" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Members</td>
     		<td>Legislative Assembly<br><input type="text" id="assembly_members" name="assembly_members" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="council_members" name="council_members" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of other Public Representatives</td>
     		<td colspan=2><input type="text" id="others" name="others" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Government Officials</td>
     		<td colspan=2><input type="text" id="gov_officials" name="gov_officials" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	</table>
     	<table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
     	<tr>
     		<td>Flag off of Van</td>
     		<td><input type="radio" id="flagOffYes" name="flagoff" value="true" autocomplete="off" onclick="toggleFileUpload('flagoff', 'flagoff_files')" />Yes</td>
     		<td><input type="radio" id="flagOffNo" name="flagoff" value="false" autocomplete="off" onclick="toggleFileUpload('flagoff', 'flagoff_files')" />No</td>
     		<td id="flagoff_files">Upload Photographs<br>
     			<input type="file" id="flagoff_photo1" name="flagoff_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" /><br/>
     			<input type="hidden" id="flagoff_photo1_lat" name="flagoff_photo1_lat">
                <input type="hidden" id="flagoff_photo1_lng" name="flagoff_photo1_lng">
                <input type="hidden" id="flagoff_photo1_time" name="flagoff_photo1_time">
     			
     			<input type="file" id="flagoff_photo2" name="flagoff_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" />
     			<input type="hidden" id="flagoff_photo2_lat" name="flagoff_photo2_lat">
                <input type="hidden" id="flagoff_photo2_lng" name="flagoff_photo2_lng">
                <input type="hidden" id="flagoff_photo2_time" name="flagoff_photo2_time">
     		</td>
     	</tr>
     	<tr>
     		<td>Launch of Theme Song</td>
     		<td><input type="radio" id="themeSongYes" name="themesong" value="true" autocomplete="off" onclick="toggleFileUpload('themesong', 'themesong_files')" />Yes</td>
     		<td><input type="radio" id="themeSongNo" name="themesong" value="false" autocomplete="off" onclick="toggleFileUpload('themesong', 'themesong_files')" />No</td>
     		<td id="themesong_files">Upload Photographs<br>
     			<input type="file" id="themesong_photo1" name="themesong_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" /><br/>
     			<input type="hidden" id="themesong_photo1_lat" name="themesong_photo1_lat">
                <input type="hidden" id="themesong_photo1_lng" name="themesong_photo1_lng">
                <input type="hidden" id="themesong_photo1_time" name="themesong_photo1_time">
     			
     			<input type="file" id="themesong_photo2" name="themesong_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" />
     			<input type="hidden" id="themesong_photo2_lat" name="themesong_photo2_lat">
                <input type="hidden" id="themesong_photo2_lng" name="themesong_photo2_lng">
                <input type="hidden" id="themesong_photo2_time" name="themesong_photo2_time">
     		</td>
     	</tr>
     	<tr>
     		<td>Bhoomi Poojan</td>
     		<td>Number of Works<br><input type="text" id="no_works_bhoomipoojan" name="no_works_bhoomipoojan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_bhoomipoojan" name="tot_works_bhoomipoojan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="bhoomipoojan_photo1" name="bhoomipoojan_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="bhoomipoojan_photo1_lat" name="bhoomipoojan_photo1_lat">
                <input type="hidden" id="bhoomipoojan_photo1_lng" name="bhoomipoojan_photo1_lng">
                <input type="hidden" id="bhoomipoojan_photo1_time" name="bhoomipoojan_photo1_time">
				
				<input type="file" id="bhoomipoojan_photo2" name="bhoomipoojan_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="bhoomipoojan_photo2_lat" name="bhoomipoojan_photo2_lat">
                <input type="hidden" id="bhoomipoojan_photo2_lng" name="bhoomipoojan_photo2_lng">
                <input type="hidden" id="bhoomipoojan_photo2_time" name="bhoomipoojan_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Lokarpan</td>
     		<td>Number of Works<br><input type="text" id="no_works_lokarpan" name="no_works_lokarpan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_lokarpan" name="tot_works_lokarpan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="lokarpan_photo1" name="lokarpan_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="lokarpan_photo1_lat" name="lokarpan_photo1_lat">
                <input type="hidden" id="lokarpan_photo1_lng" name="lokarpan_photo1_lng">
                <input type="hidden" id="lokarpan_photo1_time" name="lokarpan_photo1_time">
				
				<input type="file" id="lokarpan_photo2" name="lokarpan_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="lokarpan_photo2_lat" name="lokarpan_photo2_lat">
                <input type="hidden" id="lokarpan_photo2_lng" name="lokarpan_photo2_lng">
                <input type="hidden" id="lokarpan_photo2_time" name="lokarpan_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Shramdaan</td>
     		<td>Number of Locations<br><input type="text" id="no_location_shramdaan" name="no_location_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>No. of people participated<br><input type="text" id="no_people_shramdaan" name="no_people_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /><br/> <br/>
			Number of Man Hours<br><input type="text" id="man" name="man" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="shramdaan_photo1" name="shramdaan_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="shramdaan_photo1_lat" name="shramdaan_photo1_lat">
                <input type="hidden" id="shramdaan_photo1_lng" name="shramdaan_photo1_lng">
                <input type="hidden" id="shramdaan_photo1_time" name="shramdaan_photo1_time">
				
				<input type="file" id="shramdaan_photo2" name="shramdaan_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="shramdaan_photo2_lat" name="shramdaan_photo2_lat">
                <input type="hidden" id="shramdaan_photo2_lng" name="shramdaan_photo2_lng">
                <input type="hidden" id="shramdaan_photo2_time" name="shramdaan_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Plantation</td>
     		<td>Area (in ha.)<br><input type="text" id="area_plantation" name="area_plantation" autocomplete="off" onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     		<td>No. of Agro forestry / Horticultural Plants (No. of Sapling)<br><input type="text" id="noPlantation" name="no_plantation" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="plantation_photo1" name="plantation_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="plantation_photo1_lat" name="plantation_photo1_lat">
                <input type="hidden" id="plantation_photo1_lng" name="plantation_photo1_lng">
                <input type="hidden" id="plantation_photo1_time" name="plantation_photo1_time">
				
				<input type="file" id="plantation_photo2" name="plantation_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="plantation_photo2_lat" name="plantation_photo2_lat">
                <input type="hidden" id="plantation_photo2_lng" name="plantation_photo2_lng">
                <input type="hidden" id="plantation_photo2_time" name="plantation_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Award Distribution (Felicitation)</td>
     		<td colspan=2>Number of Watershed Margdarshaks<br><input type="text" id="no_awards" name="no_awards" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="award_photo1" name="award_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="award_photo1_lat" name="award_photo1_lat">
                <input type="hidden" id="award_photo1_lng" name="award_photo1_lng">
                <input type="hidden" id="award_photo1_time" name="award_photo1_time">
				
				<input type="file" id="award_photo2" name="award_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="award_photo2_lat" name="award_photo2_lat">
                <input type="hidden" id="award_photo2_lng" name="award_photo2_lng">
                <input type="hidden" id="award_photo2_time" name="award_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Number of stalls of Departments</td>
     		<td colspan=2><input type="text" id="dept_stalls" name="dept_stalls" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="dept_stalls_photo1" name="dept_stalls_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="dept_stalls_photo1_lat" name="dept_stalls_photo1_lat">
                <input type="hidden" id="dept_stalls_photo1_lng" name="dept_stalls_photo1_lng">
                <input type="hidden" id="dept_stalls_photo1_time" name="dept_stalls_photo1_time">
				
				<input type="file" id="dept_stalls_photo2" name="dept_stalls_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="dept_stalls_photo2_lat" name="dept_stalls_photo2_lat">
                <input type="hidden" id="dept_stalls_photo2_lng" name="dept_stalls_photo2_lng">
                <input type="hidden" id="dept_stalls_photo2_time" name="dept_stalls_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Number of stalls of SHGs/FPOs</td>
     		<td colspan=2><input type="text" id="shg_fpo_stalls" name="shg_fpo_stalls" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="shg_fpo_stalls_photo1" name="shg_fpo_stalls_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="shg_fpo_stalls_photo1_lat" name="shg_fpo_stalls_photo1_lat">
                <input type="hidden" id="shg_fpo_stalls_photo1_lng" name="shg_fpo_stalls_photo1_lng">
                <input type="hidden" id="shg_fpo_stalls_photo1_time" name="shg_fpo_stalls_photo1_time">
				
				<input type="file" id="shg_fpo_stalls_photo2" name="shg_fpo_stalls_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="shg_fpo_stalls_photo2_lat" name="shg_fpo_stalls_photo2_lat">
                <input type="hidden" id="shg_fpo_stalls_photo2_lng" name="shg_fpo_stalls_photo2_lng">
                <input type="hidden" id="shg_fpo_stalls_photo2_time" name="shg_fpo_stalls_photo2_time">
			</td>
     	</tr>
     	<tr>
     		<td>Number of LakhPati Didi Participated</td>
     		<td colspan=2><input type="text" id="no_lakhpati_didi" name="no_lakhpati_didi" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br>
				<input type="file" id="lakhpati_didi_photo1" name="lakhpati_didi_photo1" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required /><br/>
				<input type="hidden" id="lakhpati_didi_photo1_lat" name="lakhpati_didi_photo1_lat">
                <input type="hidden" id="lakhpati_didi_photo1_lng" name="lakhpati_didi_photo1_lng">
                <input type="hidden" id="lakhpati_didi_photo1_time" name="lakhpati_didi_photo1_time">
				
				<input type="file" id="lakhpati_didi_photo2" name="lakhpati_didi_photo2" autocomplete="off" accept="image/*" onchange="validatePhoto(this)" required />
				<input type="hidden" id="lakhpati_didi_photo2_lat" name="lakhpati_didi_photo2_lat">
                <input type="hidden" id="lakhpati_didi_photo2_lng" name="lakhpati_didi_photo2_lng">
                <input type="hidden" id="lakhpati_didi_photo2_time" name="lakhpati_didi_photo2_time">
			</td>
     	</tr>
     	
     	<tr>
     		<td colspan=4 class="text-left">
<!--      			<button class="btn btn-primary" type="submit">Save</button> -->
<!--      			<input type="button" name="click" id="click" value="Save" class="btn btn-info" onclick="savedata();" /> -->
     			<!-- <input type="button" name="click" id="click" value="Save" class="btn btn-info" onclick="validation();" /> -->
     			<input type="button" class="btn btn-info" id="view" onclick="validation();" name="view" value='Save' />
     		</td>
     	</tr>
     </table>
     </div>
		</div>  	
		</form:form>
		
	</div>
	
	
	
	<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">List of Watershed Yatra - Inauguration Program Details</h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="3">S.No.</th> 
								<th rowspan="3">Date</th>
<!-- 								<th rowspan="3">State Name</th> -->
								<th rowspan="3">District Name</th>
								<th rowspan="3">Block Name</th>
								<th rowspan="3">Location</th>
								<th colspan="9">Number of Participation</th>
								<th colspan="19">Activities</th>
							</tr>
							<tr>
								<th colspan="2">Participants/Villagers</th>
								<th colspan="2">Ministers</th>
								<th rowspan="2">Member of Parliament</th>
								<th colspan="2">Legislative Members</th>
								<th rowspan="2">Other Public Representatives</th>
								<th rowspan="2">Government Officials</th>
								
								<th rowspan="2">Flag off of Van</th>
								<th rowspan="2">Launch of Theme Song</th>
								<th colspan="2">Bhoomi Poojan</th>
								<th colspan="2">Lokarpan</th>
								<th colspan="3">Shramdaan</th>
								<th colspan="2">Plantation</th>
								<th rowspan="2">Award Distribution (Felicitation)</th>
								<th rowspan="2">Number of stalls of Departments</th>
								<th rowspan="2">Number of stalls of SHGs/FPOs</th>
								<th rowspan="2">Number of LakhPati Didi Participated</th>
								<th rowspan="2">No of Uploaded Photographs</th>
							</tr>
							<tr>
								<th>Male</th>
								<th>Female</th>
								<th>Central Level</th>
								<th>State Level</th>
								<th>Assembly</th>
								<th>Council</th>
								
								<th>Number of Works</th>
								<th>Cost of Total works (in Lakh)</th>
								<th>Number of Works</th>
								<th>Cost of Total works (in Lakh)</th>
								<th>Number of Locations</th>
								<th>No. of people participated</th>
								<th>No. of Man Hours</th>
								<th>Area (in ha.)</th>
								<th>No. of Agro forestry / Horticultural Plants (No. of Sapling)</th>
								
							</tr>
						</thead>
						
 						<c:set var="st" value="" />
 						<c:forEach items="${dataList}" var="data" varStatus="count">
 							<tr>
								<td><c:out value='${count.count}' /></td>
								<td> <c:out value="${data.date}" /></td>
<%--  								<c:choose> --%>
<%--  									<c:when test="${st ne data.stname}"> --%>
<%--  										<c:set var="st" value="${data.stname}" /> --%>
<%--  										<td> <c:out value="${data.stname}" /></td> --%>
<%--  									</c:when> --%>
<%--  								<c:otherwise> --%>
<!--  										<td></td> -->
<%--  								</c:otherwise> --%>
<%--  								</c:choose> --%>
								<td class="text-left"> <c:out value="${data.distname}" /></td>
 								<td class="text-left"> <c:out value="${data.blockname}" /></td>
								<td class="text-left"> <c:out value="${data.location}" /></td>
 								<td class="text-right"> <c:out value="${data.male_participants}" /></td>
								<td class="text-right"> <c:out value="${data.female_participants}" /></td>
 								<td class="text-right"> <c:out value="${data.central_ministers}" /></td>
								<td class="text-right"> <c:out value="${data.state_ministers}" /></td>
 								<td class="text-right"> <c:out value="${data.parliament}" /></td>
 								<td class="text-right"> <c:out value="${data.assembly_members}" /></td>
 								<td class="text-right"> <c:out value="${data.council_members}" /></td>
								<td class="text-right"> <c:out value="${data.others}" /></td>
 								<td class="text-right"> <c:out value="${data.gov_officials}" /></td>
								<td class="text-left"> <c:out value="${data.flagoff == 'true' ? 'Yes' : 'No'}" /></td>
 								<td class="text-left"> <c:out value="${data.themesong == 'true' ? 'Yes' : 'No'}" /></td>
								<td class="text-right"> <c:out value="${data.no_works_bhoomipoojan}" /></td>
 								<td class="text-right"> <c:out value="${data.tot_works_bhoomipoojan}" /></td>
 								<td class="text-right"> <c:out value="${data.no_works_lokarpan}" /></td>
								<td class="text-right"> <c:out value="${data.tot_works_lokarpan}" /></td>
 								<td class="text-right"> <c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.no_people_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.man}" /></td>
 								<td class="text-right"> <c:out value="${data.area_plantation}" /></td>
								<td class="text-right"> <c:out value="${data.no_plantation}" /></td>
 								<td class="text-right"> <c:out value="${data.no_awards}" /></td>
 								<td class="text-right"> <c:out value="${data.dept_stalls}" /></td>
 								<td class="text-right"> <c:out value="${data.shg_fpo_stalls}" /></td>
 								<td class="text-right"> <c:out value="${data.no_lakhpati_didi}" /></td>
								<td class="text-right">
									<a href="#" data-id="${data.inauguaration_id}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${data.image_count}" /></a> 
								</td>
					</tr>
 						</c:forEach>
						<c:if test="${dataListSize eq 0}">
							<tr>
								<td align="center" colspan="20" class="required" style="color:red;">Data Not Found</td>
								<td colspan="10" ></td>
							</tr>
						</c:if>
		</table>
		
		
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

	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>