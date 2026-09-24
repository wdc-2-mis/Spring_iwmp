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
 <script src='<c:url value="/resources/js/swachhata.js" />'></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>


<meta charset="ISO-8859-1">
<title>Watershed Swachhata at Project Level</title>

<script>
//Show Add More button when a file is chosen

/* document.getElementById("datetime").addEventListener("change", function () {

    const selectedDate = new Date(this.value);
    const minDate = new Date("2026-09-20T00:00");
    const maxDate = new Date("2026-10-02T23:59");

    if (selectedDate < minDate || selectedDate > maxDate) {
        alert("Please select date between 20/09/2026 and 02/10/2026.");
        this.value = "";
        this.focus();
    }
}); */

document.addEventListener("change", function(e) {
    if (e.target.classList.contains("photo-input")) {
        
        let container = e.target.closest(".photo-block");
        let addBtn = container.querySelector(".addPhotoBtn");
        addBtn.style.display = "inline-block";
    }
});

// Add new upload field for the correct activity

function addPhotoField1(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=2) {
        alert("Maximum 2 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_sapling" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_sapling_lat" name="photos_sapling_lat" value = ""/>
        <input type="hidden" id="photos_sapling_lng" name="photos_sapling_lng" value = "0"/>
        <input type="hidden" id="photos_sapling_time" name="photos_sapling_time" value = "0"/>
    `;

    container.appendChild(div);
}
function addPhotoField2(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=2) {
        alert("Maximum 2 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_lokarpan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_lokarpan_lat" name="photos_lokarpan_lat" value = "0"/>
	    <input type="hidden" id="photos_lokarpan_lng" name="photos_lokarpan_lng" value = "0"/>
	    <input type="hidden" id="photos_lokarpan_time" name="photos_lokarpan_time" value = "0"/>
    `;

    container.appendChild(div);
}
function addPhotoField3(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=2) {
        alert("Maximum 2 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_shramdaan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_shramdaan_lat" name="photos_shramdaan_lat" value = "0"/>
	    <input type="hidden" id="photos_shramdaan_lng" name="photos_shramdaan_lng" value = "0"/>
	    <input type="hidden" id="photos_shramdaan_time" name="photos_shramdaan_time" value = "0"/>
    `;

    container.appendChild(div);
}
function addPhotoField4(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=2) {
        alert("Maximum 2 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_cleanliness" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)"  required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_cleanliness_lat" name="photos_cleanliness_lat" value = "0"/>
	    <input type="hidden" id="photos_cleanliness_lng" name="photos_cleanliness_lng" value = "0"/>
	    <input type="hidden" id="photos_cleanliness_time" name="photos_cleanliness_time" value = "0"/>
    `;

    container.appendChild(div);
}

function addPhotoField5(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=2) {
        alert("Maximum 2 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_awareness" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)"  required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_awareness_lat" name="photos_awareness_lat" value = "0"/>
	    <input type="hidden" id="photos_awareness_lng" name="photos_awareness_lng" value = "0"/>
	    <input type="hidden" id="photos_awareness_time" name="photos_awareness_time" value = "0"/>
    `;

    container.appendChild(div);
}

// Remove field
function removePhotoField(btn) {
    btn.closest("div").remove();
}


//Store image hashes to prevent duplicates
let imageRecords = {};

function validatePhoto(input) {
	let checkValid = true;
    const file = input.files[0];
    if (!file) return;

    const maxSizeKB = 300; // 300 KB limit
    const allowedTypes = ["image/jpeg", "image/png"];
    const requiredWidth = 600;
    const requiredHeight = 600;


    // 1. Validate file type
    if (!allowedTypes.includes(file.type)) {
        alert("Invalid file type! Only JPEG or PNG images are allowed.");
        input.value = "";
        checkValid = false;
        return false;
    }

    // 2. Validate file size
    let sizeKB = file.size / 1024;
    if (sizeKB > maxSizeKB && checkValid) {
        alert("Image size must be 300 KB or less.\nYour image size: " + Math.round(sizeKB) + " KB");
        input.value = "";
        checkValid = false;
        return false;
    }
    
 // 3. Validate image dimensions
//     let reader = new FileReader();
// 	reader.onload = function (e) {
//     let img = new Image();
//     img.onload = function () {
//         if (checkValid && (img.width > requiredWidth || img.height > requiredHeight)) {
//             alert(`Image dimensions must be less than ` +requiredWidth +` x `+ requiredHeight +` pixels.\n But Your image: ` +img.width+` x `+img.height+` pixels.`);
//             input.value = "";
//             checkValid = false;
//             return;
//         	}
//    	 	};
//     	img.src = e.target.result; // use base64 data URL from FileReader
// 		};
// 	reader.readAsDataURL(file);



    // 4. Check duplicate image by hashing
    getImageHash(file, function(hash) {
        if (checkValid && imageRecords[file.name] === hash) {
            alert("This image is already uploaded! Please upload a different image.");
            input.value = "";
            checkValid = false;
            return;
        }

        // 5. Check filename for special characters
        if (checkValid && !/^[A-Za-z0-9]+\.(jpg|jpeg|png)$/i.test(file.name)) {
            alert("Filename contains special characters or file extension name is incorrect! Please rename the file and upload again.");
            input.value = "";
            checkValid = false;
            return;
        }
		
        // 6. Read EXIF metadata (GPS + timestamp)
        EXIF.getData(file, function () {
            let lat = EXIF.getTag(this, "GPSLatitude");
            let lng = EXIF.getTag(this, "GPSLongitude");
            let time = EXIF.getTag(this, "DateTimeOriginal");

            let latRef = EXIF.getTag(this, "GPSLatitudeRef") || "N";
            let lngRef = EXIF.getTag(this, "GPSLongitudeRef") || "E";

            // Convert DMS → Decimal Degrees
            function convert(dms, ref) {
                if (!dms) return null;
                let d = dms[0].numerator / dms[0].denominator;
                let m = dms[1].numerator / dms[1].denominator;
                let s = dms[2].numerator / dms[2].denominator;
                let decimal = d + m / 60 + s / 3600;
                return (ref === "S" || ref === "W") ? -decimal : decimal;
            }

            let latitude = convert(lat, latRef);
            let longitude = convert(lng, lngRef);
            
            // 7. Detect correct hidden fields dynamically
            let parentDiv = input.closest('div');
            let latInput = parentDiv.querySelector('input[id$="_lat"]');
            let lngInput = parentDiv.querySelector('input[id$="_lng"]');
            let timeInput = parentDiv.querySelector('input[id$="_time"]');
            
            if (latInput) latInput.value = latitude || "0";
            if (lngInput) lngInput.value = longitude || "0";
            if (timeInput) timeInput.value = time || "0";
//             alert('kdy_lat= '+document.getElementById("bhoomipoojan_lat").value +' kdy_lon= '+document.getElementById("bhoomipoojan_lng").value);
            // 8. Warn if GPS or timestamp missing
            
            if (checkValid && (!latitude || !longitude || !time)) {
                if (!confirm("This photo does NOT contain GPS or timestamp information.\nDo you still want to upload?")) {
                    input.value = "";
                }
            }
            if(checkValid){
            	imageRecords[file.name] = hash;
            }
            	
        });
    });
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


window.formSubmitted = false;
function validation() {

    if (window.formSubmitted) {
        return false;
    }

    let allValid = true;
	
	var allowedFiles = [".jpg", ".jpeg",".png"];

	
	$location = $('#location').val();
	$shg = $('#shg').val();
	$ug = $('#ug').val();
	$fpo = $('#fpo').val();
	$youth = $('#youth').val();
	$other = $('#other').val();
	$no_sapling = $('#no_sapling').val();
	$photos_sapling = $('#photos_sapling').val();
	
	$no_works_lokarpan = $('#no_works_lokarpan').val();
	$lokarpan_photo1 = $('#photos_lokarpan').val();
	
	$no_location_shramdaan = $('#no_location_shramdaan').val();
	$shramdaan_photo1 = $('#photos_shramdaan').val();
	
	$no_cleanliness = $('#no_cleanliness').val();
	$photos_cleanliness = $('#photos_cleanliness').val();
	
	$no_awareness = $('#no_awareness').val();
	$photos_awareness = $('#photos_awareness').val();
	
	
	if ($location === '' || typeof $location === 'undefined') {
		alert('Please enter Location');
		$('#location').focus();
		allValid = false;
		return false;
	}
	if ($shg === '' || typeof $shg === 'undefined') {
		alert('Please enter the Number Of SHG');
		$('#shg').focus();
		allValid = false;
		return false;
	}
	if ($ug === '' || typeof $ug === 'undefined') {
		alert('Please enter the Number Of User Group');
		$('#ug').focus();
		allValid = false;
		return false;
	}
	if ($fpo === '' || typeof $fpo === 'undefined') {
		alert('Please enter the Number of FPO');
		$('#fpo').focus();
		allValid = false;
		return false;
	}
	if ($youth === '' || typeof $youth === 'undefined') {
		alert('Please enter the Number of Youth Participant');
		$('#youth').focus();
		allValid = false;
		return false;
	}
	if ($other === '' || typeof $other === 'undefined') {
		alert('Please enter the Number of Other Participant');
		$('#other').focus();
		allValid = false;
		return false;
	}
	if ($no_sapling === '' || typeof $no_sapling === 'undefined') {
		alert('Please enter Total No. of Sapling Planted');
		$('#no_sapling').focus();
		allValid = false;
		return false;
	}
	if ($no_works_lokarpan === '' || typeof $no_works_lokarpan === 'undefined') {
		alert('Please enter Total No. of Works for Lokarpan');
		$('#no_works_lokarpan').focus();
		allValid = false;
		return false;
	}
	if ($no_location_shramdaan === '' || typeof $no_location_shramdaan === 'undefined') {
		alert('Please enter Total No. of Location Shramdaan Undertaken');
		$('#no_location_shramdaan').focus();
		allValid = false;
		return false;
	}
	if ($no_cleanliness === '' || typeof $no_cleanliness === 'undefined') {
		alert('Please enter Total No. of Cleanliness drives Organised');
		$('#no_cleanliness').focus();
		allValid = false;
		return false;
	}
	
	if ($no_awareness === '' || typeof $no_awareness === 'undefined') {
		alert('Please enter Awareness Sessions Organised');
		$('#no_awareness').focus();
		allValid = false;
		return false;
	}
	
	//let isValid = true;

	$(".photo-block").each(function () {

	    let block = $(this);
	    let container = block.find(".photoContainer");
	    let photos = container.find("input[type='file']");
	    let uploaded = 0;
	    let errorDiv = block.find(".photoError");

	    errorDiv.html("");

	    // Count selected photos
	    photos.each(function () {
	        if ($(this).val() !== "") {
	            uploaded++;
	        }
	    });

	    // If at least one photo is selected,
	    // exactly 2 photos are required
	    if (uploaded > 0 && uploaded !== 2) {

	        let label = block.find("label").first().text().trim();

	        errorDiv.html("Please upload exactly 2 photos.");

	        alert(
	            label +
	            "\nPlease upload exactly 2 photos required for this activity."
	        );

	        allValid = false;

	        return false; // stop .each()
	    }
	});

    if (allValid) {

        if (!confirm("Do you want to Update Watershed Swachhata at Project Level?")) {
            return false;
        }

        window.formSubmitted = true;

        document.saveWatershed.action = "updateWatershedSwachhataDetails";
        document.saveWatershed.method = "post";
        document.saveWatershed.submit();

        return false;
    }
    return false;
//     document.getElementById("saveWatershed").submit();
}

/*function displaydata(){
	allValid = true;
	$datetime = $('#datetime').val();
	$projId = $('#project').val();
	$block = $('#block').val();
	
	if ($datetime === '' || typeof $datetime === 'undefined') {
		alert('Please Select the Date and Time');
		$('#location').val('');
		$('#datetime').focus();
		allValid = false;
		return false;
	}
	if ($projId === '' || typeof $projId === 'undefined') {
		alert('Please Select the Project');
		$('#location').val('');
		$('#project').focus();
		allValid = false;
		return false;
	}
	if ($block === '' || typeof $block === 'undefined') {
		alert('Please Select the Block');
		$('#location').val('');
		$('#block').focus();
		allValid = false;
		return false;
	}
	if(allValid){
		document.saveWatershed.action="getWatershedMahotsavAtProjLvl";
		document.saveWatershed.method="post";
		document.saveWatershed.submit();
		return true;
	}else{
		return false;
	}
	
} */

function editChancel(){
	
    document.saveWatershed.action="getWatershedSwachhataAtProj";
	document.saveWatershed.method="get";
	document.saveWatershed.submit();
}

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }
  
function openLargeImage(imageSrc, index, total) {
	document.getElementById('imagePopup').style.display = 'none';
 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/swachhata/projectLevel/' + imageSrc;		
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/swachhata/projectLevel/' + imageSrc;
 //	document.getElementById('largeImage').src = 'resources/images/projectLevel/' + imageSrc;											
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
function calculateTotal() {
    let shg   = parseInt(document.getElementById("shg").value) || 0;
    let ug    = parseInt(document.getElementById("ug").value) || 0;
    let fpo   = parseInt(document.getElementById("fpo").value) || 0;
    let youth = parseInt(document.getElementById("youth").value) || 0;
    let other = parseInt(document.getElementById("other").value) || 0;

    let total = shg + ug + fpo + youth + other;

    document.getElementById("total").value = total;
}

</script>

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
<c:if test="${result != null}">
	<script>
	    alert("<c:out value='${result}'/>");
	</script>
</c:if>
	<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Update Watershed Swachhata at Project Level</h4> </div>
		<!-- 	<label>
		<span style="color:blue;">Note:- The Image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label> -->
		<form:form autocomplete="off" method="post" name="saveWatershed" id="saveWatershed" action="updateWatershedSwachhataDetails" modelAttribute="useruploadsl" enctype="multipart/form-data">
			 <c:forEach items="${dataList}" var="data" varStatus="count">
				<input type="hidden" id="waterid" name="waterid" value="${data.swachhata_id}"/>
				<input type="hidden" id="datetime" name="datetime" value="${data.datetime}"/>
				<input type="hidden" id="district" name="district" value="${data.district}"/>
				<input type="hidden" id="project" name="project" value="${data.project}"/>
				<input type="hidden" id="block" name="block" value="${data.block}"/>
			 	<input type="hidden" id="village" name="village" value="${data.village}"/>
			<hr/>
			  <div class="row">
    			<div class="form-group col-3">
      		  <label for="datetime">Date of Activity:<span style="color: red;">*</span> </label>
       		 <input type="datetime-local" name="datetime1" id="datetime1" min="2026-09-20T00:00" max="2026-10-02T23:59" 
       		 		class="form-control activity" style="width: 100%;" value="${data.datetime}" />
    		</div>
			</div>
			<div class="row">
				<div class="form-group col-2">
				State Name: &nbsp; <c:out value="${stateName}"></c:out>
				</div>
	    		<div class="form-group col-2">
	      			<label for="district">District: </label>
	      			<span class="districtError"></span>
	      			 <c:out value="${data.distname}" />
	    		</div>
	    		<div class="form-group col-2">
	    			<label for="project">Project: </label>
	      			<span class="projectError"></span>
	      			<c:out value="${data.projname}" />
	    		</div>
	    		<div class="form-group col-2">
	    			<label for="block">Block: </label>
	      			<span class="blockError"></span>
	      			<c:out value="${data.blockname}" />
	    		</div>
	    		<div class="form-group col-2">
	    			<label for="block">Village: </label>
	      			<span class="blockError"></span>
	      			<c:out value="${data.villagename}" />
	    		</div>
	    		<div class="form-group col-4">
	    			<label for="activity">Location (Nearby/Milestone)<span style="color: red;">*</span></label>
	    			<input type="text" class="form-control activity" name="location" id="location" value="${data.location}" style="width: 100%; max-width: 800px;" />
	    		</div>
    		</div>
    		
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Total No. of People Participated :</th>
     	</tr>
     	<tr>
     		
     		<td>SHG<span style="color: red;">*</span><br><input type="text" id="shg" name="shg" autocomplete="off" onblur="calculateTotal();"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" value="${data.shg}" required /></td>
     		<td>User Group<span style="color: red;">*</span> <br><input type="text" id="ug" name="ug" autocomplete="off" onblur="calculateTotal();"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" value="${data.usg}" required /></td>
     		<td colspan=2>FPOs<span style="color: red;">*</span> <br><input type="text" id="fpo" name="fpo" autocomplete="off" onblur="calculateTotal();"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" value="${data.fpo}" required /></td>
		</tr>
		<tr>					 
     		<td>Youth/Students<span style="color: red;">*</span><br><input type="text" id="youth" name="youth" autocomplete="off" onblur="calculateTotal();"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" value="${data.youth}" required /></td>
			<td>Others<span style="color: red;">*</span> <br><input type="text" id="other" name="other" autocomplete="off" onblur="calculateTotal();"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" value="${data.other}" required /></td>	
			<td colspan=2>Total <br><input type="text" id="total" name="total" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" value="${data.total}" readonly="readonly" /></td>						 				 					 
     	</tr>
     	
     	
     	<tr>
     		<th colspan=5 class="text-left">Activities :</th>
     	</tr>
						<tr>
							<td>Total No. of Sapling Planted<span style="color: red;">*</span> <input type="hidden" name="sapling" id="sapling" value="1"/></td>
							<td colspan=2><input type="text" id="no_sapling" name="no_sapling" autocomplete="off" value="${data.no_sapling}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_sapling">

									<label><b>Upload Photographs (Min 2):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_sapling"
												id="photos_sapling" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_sapling_lat" name="photos_sapling_lat" value = "0"/> 
											<input type="hidden" id="photos_sapling_lng" name="photos_sapling_lng" value = "0"/> 
											<input type="hidden" id="photos_sapling_time" name="photos_sapling_time" value = "0"/>
										</div>
									</div>

									<button type="button"
										class="btn btn-sm btn-primary mt-2 addPhotoBtn"
										style="display: none;" onclick="addPhotoField1(this)">
										+ Add More</button>

									<small class="text-danger photoError"></small>

								</div>
							</td>

						</tr>
						<tr>
							<td>Total No. of Works for Lokarpan<span style="color: red;">*</span> <input type="hidden" name="lokarpan" id="lokarpan" value="2"/></td>
							<td colspan=2><input type="text" id="no_works_lokarpan"
								name="no_works_lokarpan" autocomplete="off" pattern="^\d{10}$"
								maxlength="5" value="${data.no_works_lokarpan}"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_lokarpan">

									<label><b>Upload Photographs (Min 2):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_lokarpan"
												id="photos_lokarpan" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_lokarpan_lat" name="photos_lokarpan_lat" value = "0"/>
											<input type="hidden" id="photos_lokarpan_lng" name="photos_lokarpan_lng" value = "0"/>
											<input type="hidden" id="photos_lokarpan_time" name="photos_lokarpan_time" value = "0"/>
										</div>
									</div>

									<button type="button"
										class="btn btn-sm btn-primary mt-2 addPhotoBtn"
										style="display: none;" onclick="addPhotoField2(this)">
										+ Add More</button>

									<small class="text-danger photoError"></small>

								</div>
							</td>

						</tr>
						<tr>
							<td>Total No. of Location Shramdaan Undertaken<span style="color: red;">*</span><input type="hidden" name="shramdaan" id="shramdaan" value="3"/> </td>
							<td colspan=2> <input type="text" id="no_location_shramdaan" name="no_location_shramdaan" autocomplete="off" value="${data.no_location_shramdaan}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required />
							</td>
							<td>
								<div class="photo-block" data-name="photos_shramdaan">

									<label><b>Upload Photographs (Min 2):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_shramdaan"
												id="photos_shramdaan" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_shramdaan_lat" name="photos_shramdaan_lat" value = "0"/>
											<input type="hidden" id="photos_shramdaan_lng" name="photos_shramdaan_lng" value = "0"/>
											<input type="hidden" id="photos_shramdaan_time" name="photos_shramdaan_time" value = "0"/>
										</div>
									</div>

									<button type="button"
										class="btn btn-sm btn-primary mt-2 addPhotoBtn"
										style="display: none;" onclick="addPhotoField3(this)">
										+ Add More</button>

									<small class="text-danger photoError"></small>

								</div>
							</td>
						</tr>
						<tr>
							<td>Total No. of Cleanliness drives Organised<span style="color: red;">*</span> <input type="hidden" name="cleanliness" id="cleanliness" value="4"/></td>
							<td colspan=2><input type="text" id="no_cleanliness" name="no_cleanliness" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" value="${data.no_cleanliness}"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_cleanliness">

									<label><b>Upload Photographs (Min 2):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_cleanliness"
												id="photos_cleanliness" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_cleanliness_lat" name="photos_cleanliness_lat" value = "0"/>
											<input type="hidden" id="photos_cleanliness_lng" name="photos_cleanliness_lng" value = "0"/>
											<input type="hidden" id="photos_cleanliness_time" name="photos_cleanliness_time" value = "0"/>
										</div>
									</div>

									<button type="button"
										class="btn btn-sm btn-primary mt-2 addPhotoBtn"
										style="display: none;" onclick="addPhotoField4(this)">
										+ Add More</button>

									<small class="text-danger photoError"></small>

								</div>
							</td>
						</tr>
						
						<tr>
							<td>Awareness Sessions Organised<span style="color: red;">*</span> <input type="hidden" name="awareness" id="awareness" value="5"/></td>
							<td colspan=2><input type="text" id="no_awareness" name="no_awareness" autocomplete="off" value="${data.no_awareness}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_awareness">

									<label><b>Upload Photographs (Min 2):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_awareness"
												id="photos_awareness" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_awareness_lat" name="photos_awareness_lat" value = "0"/>
											<input type="hidden" id="photos_awareness_lng" name="photos_awareness_lng" value = "0"/>
											<input type="hidden" id="photos_awareness_time" name="photos_awareness_time" value = "0"/>
										</div>
									</div>

									<button type="button"
										class="btn btn-sm btn-primary mt-2 addPhotoBtn"
										style="display: none;" onclick="addPhotoField5(this)">
										+ Add More</button>

									<small class="text-danger photoError"></small>

								</div>
							</td>
						</tr>
						
						<tr>
     					<td colspan=4 class="text-left">
							<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="return validation();"  value ="Update"/>
     						<input type="button" class="btn btn-info" id="cancelbtn" name="cancelbtn" onclick="return editChancel();"  value ="Cancel"/>
     					</td>
     					</tr>

					</table>
		<%-- <c:if test ="${!check}">
        <div class="form-row">
				<div class="form-group col-8">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button"
					       class="btn btn-info"
					       id="submitbtn"
					       name="submitbtn"
					       onclick="return validation();"
					       value="Save" />
     			</div>
     		</div> 
     	</c:if> --%>
     </div>
		</div>
	<br/>
     		</c:forEach>
		</form:form>
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