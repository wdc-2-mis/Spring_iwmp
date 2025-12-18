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
 <script src='<c:url value="/resources/js/mahotsavAtProjLvl.js" />'></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>


<meta charset="ISO-8859-1">
<title>Watershed Mahotsav at Project Level</title>

<script>
//Show Add More button when a file is chosen
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

    if (inputs.length >=10) {
        alert("Maximum 10 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_bhoomipoojan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_bhoomipoojan_lat" name="photos_bhoomipoojan_lat" value = ""/>
        <input type="hidden" id="photos_bhoomipoojan_lng" name="photos_bhoomipoojan_lng" value = "0"/>
        <input type="hidden" id="photos_bhoomipoojan_time" name="photos_bhoomipoojan_time" value = "0"/>
    `;

    container.appendChild(div);
}
function addPhotoField2(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=10) {
        alert("Maximum 10 photographs allowed for this activity.");
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

    if (inputs.length >=10) {
        alert("Maximum 10 photographs allowed for this activity.");
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

    if (inputs.length >=10) {
        alert("Maximum 10 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_forestry" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)"  required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_forestry_lat" name="photos_forestry_lat" value = "0"/>
	    <input type="hidden" id="photos_forestry_lng" name="photos_forestry_lng" value = "0"/>
	    <input type="hidden" id="photos_forestry_time" name="photos_forestry_time" value = "0"/>
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


let formSubmitted = false;
let allValid = true;
function validation() 
{
	if (formSubmitted) return false;
	allValid = true;
	
	var allowedFiles = [".jpg", ".jpeg",".png"];

    $block = $('#block option:selected').val();
    $datetime = $('#datetime').val();
	$location = $('#location').val();
	$maleParticipants = $('#maleparticipants').val();
	$femaleParticipants = $('#femaleparticipants').val();
	$centralMinisters = $('#centralministers').val();
	$stateMinisters = $('#stateministers').val();
	$membersOfParliament = $('#membersofparliament').val();
	$legAssemblyMembers = $('#legassemblymembers').val();
	$legCouncilMembers = $('#legcouncilmembers').val();
	$publicReps = $('#publicreps').val();
	$govOfficials = $('#govofficials').val();
	$no_works_bhoomipoojan = $('#no_works_bhoomipoojan').val();
	$bhoomipoojan_photo1 = $('#photos_bhoomipoojan').val();
	
	$no_works_lokarpan = $('#no_works_lokarpan').val();
	$lokarpan_photo1 = $('#photos_lokarpan').val();
	
	$no_location_shramdaan = $('#no_location_shramdaan').val();
	$no_people_shramdaan = $('#no_people_shramdaan').val();
	$shramdaan_photo1 = $('#photos_shramdaan').val();
	
	$area_plantation = $('#area_plantation').val();
	$plantation_photo1 = $('#photos_forestry').val();
	
	if ($datetime === '' || typeof $datetime === 'undefined') {
		alert('Please select a Date and Time');
		$('#datetime').focus();
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
	if ($maleParticipants === '' || typeof $maleParticipants === 'undefined') {
		alert('Please enter the Number Of Male Participants/Villagers');
		$('#maleparticipants').focus();
		allValid = false;
		return false;
	}
	if ($femaleParticipants === '' || typeof $femaleParticipants === 'undefined') {
		alert('Please enter the Number Of Female Participants/Villagers');
		$('#femaleparticipants').focus();
		allValid = false;
		return false;
	}
	if ($centralMinisters === '' || typeof $centralMinisters === 'undefined') {
		alert('Please enter the Number of Central Ministers');
		$('#centralministers').focus();
		allValid = false;
		return false;
	}
	if ($stateMinisters === '' || typeof $stateMinisters === 'undefined') {
		alert('Please enter the Number of State Ministers');
		$('#stateministers').focus();
		allValid = false;
		return false;
	}
	if ($membersOfParliament === '' || typeof $membersOfParliament === 'undefined') {
		alert('Please enter the Number of Members of Parliament');
		$('#membersofparliament').focus();
		allValid = false;
		return false;
	}
	if ($legAssemblyMembers === '' || typeof $legAssemblyMembers === 'undefined') {
		alert('Please enter the Number of Legislative Assembly Members');
		$('#legassemblymembers').focus();
		allValid = false;
		return false;
	}
	if ($legCouncilMembers === '' || typeof $legCouncilMembers === 'undefined') {
		alert('Please enter the Number of Legislative Council Members');
		$('#legcouncilmembers').focus();
		allValid = false;
		return false;
	}
	if ($publicReps === '' || typeof $publicReps === 'undefined') {
		alert('Please enter the Number of other Public Representatives');
		$('#publicreps').focus();
		allValid = false;
		return false;
	}
	if ($govOfficials === '' || typeof $govOfficials === 'undefined') {
		alert('Please enter the Number of Government Officials');
		$('#govofficials').focus();
		allValid = false;
		return false;
	}
	
	if ($no_works_bhoomipoojan === '' || typeof $no_works_bhoomipoojan === 'undefined') {
		alert('Please enter Number of Works of Bhoomi Poojan');
		$('#no_works_bhoomipoojan').focus();
		allValid = false;
		return false;
	}
	if ($no_works_bhoomipoojan > 0) {
		if ($bhoomipoojan_photo1 === '' || typeof $bhoomipoojan_photo1 === 'undefined') {
			alert('Please upload photo for Bhoomi Poojan');
//	 		$('#bhoomipoojan_photo1').focus();
			document.getElementById('photos_bhoomipoojan').click();
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
	if ($no_works_lokarpan > 0) {
		if ($lokarpan_photo1 === '' || typeof $lokarpan_photo1 === 'undefined') {
			alert('Please upload photo for Lokarpan');
//	 		$('#lokarpan_photo1').focus();
			document.getElementById('photos_lokarpan').click();
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
	if ($no_location_shramdaan > 0) {
		if ($shramdaan_photo1 === '' || typeof $shramdaan_photo1 === 'undefined') {
			alert('Please upload photo for Shramdaan');
//	 		$('#shramdaan_photo1').focus();
			document.getElementById('photos_shramdaan').click();
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
	if ($area_plantation > 0) {
		if ($plantation_photo1 === '' || typeof $plantation_photo1 === 'undefined') {
			alert('Please upload photo for Plantation');
//	 		$('#plantation_photo1').focus();
			document.getElementById('photos_forestry').click();
			allValid = false;
			return false;
		}
	}
	
	// For each activity block
    document.querySelectorAll(".photo-block").forEach(block => {

        let container = block.querySelector(".photoContainer");
        let errorDiv = block.querySelector(".photoError");
        let inputs = container.querySelectorAll("input[type='file']");
        let totalFiles = 0;
        let minPhotos = 2;
        errorDiv.innerHTML = ""; // clear old errors
        inputs.forEach(inp => {
            if (inp.files.length > 0) {
                totalFiles++;
            }
        });

        let activityInput = block.closest("tr").querySelector("input[type='text']");
        let activityValue = activityInput ? parseInt(activityInput.value || 0) : 0;
        if (activityValue > 0 && totalFiles < minPhotos) {
            errorDiv.innerHTML = "Please upload minimum " +minPhotos+" photos.";
            alert(`Minimum `+minPhotos+` photos required for this activity.`);
            allValid = false;
            return false;
        }

    });
	

	if (allValid) {
		if(confirm("Do you want to save Watershed Mahotsav at Project Level?")) {
	    formSubmitted = true; 
		document.saveWatershed.action="saveWatershedMahotsavProjLvlDetails";
		document.saveWatershed.method="post";
		document.saveWatershed.submit();
		}
		return true;
	}else{
		return false;
	}
//     document.getElementById("saveWatershed").submit();
}

function displaydata(){
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
	
}

function editChangedata(waterid){
	
	document.getElementById('waterid').value=waterid;
    document.saveWatershed.action="getWatershedMahotsavProjLvlEdit";
	document.saveWatershed.method="post";
	document.saveWatershed.submit();
}

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
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Mahotsav at Project Level</h4> </div>
			<label>
		<span style="color:blue;">Note:- The Image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
		<form:form autocomplete="off" method="post" name="saveWatershed" id="saveWatershed" action="saveWatershedMahotsavProjLvlDetails" modelAttribute="useruploadsl" enctype="multipart/form-data">
			 <input type="hidden" id="waterid" name="waterid" />
			<hr/>
			  <div class="row">
    			<div class="form-group col-3">
      		  <label for="datetime">Date and Time:<span style="color: red;">*</span> </label>
       		 <input type="datetime-local" name="datetime" id="datetime" class="form-control activity" style="width: 100%;" value="${datetimeValue}" />
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-2">
			
				State Name:</br> <c:out value="${stateName}"></c:out>
			
			</div>
    		<div class="form-group col-2">
      			District: </br> <c:out value="${distName}"></c:out>
      			
      		<input type="hidden" id="district" name="district" value="${distCode}">
      			
    		</div>
				<div class="form-group col-2">
					<label for="project">Projects:<span style="color: red;">*</span> </label> <select	class="form-control project" id="project" name="project">
						<option value="">--Select Project--</option>
						<c:forEach items="${projectList}" var="proj">
							<c:if test="${proj.key == project}">
								<option value="${proj.key}" selected>${proj.value}</option>
							</c:if>
							<c:if test="${proj.key != project}">
								<option value="${proj.key}">${proj.value}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-2">
    			<label for="block">Block:<span style="color: red;">*</span> </label>
      			<select class="form-control activity" id="block" name="block">
    				<option value="">--Select Block--</option>
    				<c:forEach items="${blkList}" var="dist"> 
    				<c:if test ="${dist.key == blkcode}">
						<option value="<c:out value="${dist.key}"/>" selected><c:out value="${dist.value}" /></option>
					</c:if>
					<c:if test ="${dist.key != blkcode}">
						<option value="<c:out value="${dist.key}"/>" ><c:out value="${dist.value}" /></option>
					</c:if>
					</c:forEach>
    			</select>
    		</div>
    		
    		<div class="form-group col-2">
    <label for="location">Location (Nearby/Milestone)<span style="color: red;">*</span></label>
    <input type="text" class="form-control activity" name="location" id="location" onblur="displaydata()" style="width: 100%; max-width: 800px;" value="${location}" />
</div>

    		
    		</div>
    		
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td colspan=2>Number Of Participants/Villagers<span style="color: red;">*</span></td>
     		<td>Male<br><input type="text" id="maleparticipants" name="maleparticipants" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="femaleparticipants" name="femaleparticipants" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Ministers<span style="color: red;">*</span></td>
     		<td>Central Level<br><input type="text" id="centralministers" name="centralministers" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateministers" name="stateministers" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td colspan=2>Number of Member of Parliament<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="membersofparliament" name="membersofparliament" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Members<span style="color: red;">*</span></td>
     		<td>Legislative Assembly<br><input type="text" id="legassemblymembers" name="legassemblymembers" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="legcouncilmembers" name="legcouncilmembers" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of other Public Representatives<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="publicreps" name="publicreps" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	<tr>
     		<td colspan=2>Number of Government Officials<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="govofficials" name="govofficials" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
						<tr>
							<td>Number of Works for Bhoomi Poojan<span style="color: red;">*</span> <input type="hidden" name="bhoomipoojan" id="bhoomipoojan" value="1"/></td>
							<td colspan=2><input type="text" id="no_works_bhoomipoojan"
								name="no_works_bhoomipoojan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_bhoomipoojan">

									<label><b>Upload Photographs (Min 2, Max 10):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_bhoomipoojan"
												id="photos_bhoomipoojan" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_bhoomipoojan_lat" name="photos_bhoomipoojan_lat" value = "0"/> 
											<input type="hidden" id="photos_bhoomipoojan_lng" name="photos_bhoomipoojan_lng" value = "0"/> 
											<input type="hidden" id="photos_bhoomipoojan_time"	name="photos_bhoomipoojan_time" value = "0"/>
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
							<td>Number of Works for Lokarpan<span style="color: red;">*</span> <input type="hidden" name="lokarpan" id="lokarpan" value="2"/></td>
							<td colspan=2><input type="text" id="no_works_lokarpan"
								name="no_works_lokarpan" autocomplete="off" pattern="^\d{10}$"
								maxlength="5"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_lokarpan">

									<label><b>Upload Photographs (Min 2, Max 10):</b></label>

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
							<td>Shramdaan<span style="color: red;">*</span> <input type="hidden" name="shramdaan" id="shramdaan" value="3"/></td>
							<td>Number of Locations<br>
							<input type="text" id="no_location_shramdaan"
								name="no_location_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>Number of people participated<br>
							<input type="text" id="no_people_shramdaan"
								name="no_people_shramdaan" autocomplete="off" pattern="^\d{10}$"
								maxlength="5"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_shramdaan">

									<label><b>Upload Photographs (Min 2, Max 10):</b></label>

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
							<td>Agro forestry / Horticultural Plantation - Number of Saplings<span style="color: red;">*</span> <input type="hidden" name="forestry" id="forestry" value="4"/></td>
							<td colspan=2><input type="text" id="area_plantation" name="area_plantation" autocomplete="off"
								pattern="^\d{10}$" maxlength="5"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>
								<div class="photo-block" data-name="photos_forestry">

									<label><b>Upload Photographs (Min 2, Max 10):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_forestry"
												id="photos_forestry" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="photos_forestry_lat" name="photos_forestry_lat" value = "0"/>
											<input type="hidden" id="photos_forestry_lng" name="photos_forestry_lng" value = "0"/>
											<input type="hidden" id="photos_forestry_time" name="photos_forestry_time" value = "0"/>
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

					</table>
		<c:if test ="${!check}">
        <div class="form-row">
				<div class="form-group col-8">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Save"/>
     			</div>
     		</div> 
     	</c:if>
     </div>
		</div>
	<br/>
     		
		</form:form>
	</div> 
	
	<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Draft List of Watershed Mahotsav at Project Level Details</h5>
	     <table class="table table-bordered table-striped table-highlight w-auto" id="inaugurationTable">
						<thead class ="theadlist" id = "theadlist">
							<tr>
								<th rowspan="3">Action</th>
								<th rowspan="3">S.No.  &nbsp; <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /></th> 
								<th rowspan="3">Date</th>
<!-- 								<th rowspan="3">State Name</th> -->
								<th rowspan="3">District Name</th>
								<th rowspan="3">Block Name</th>
								<th rowspan="3">Location</th>
								
								<th colspan="9">Number of Participation</th>
								<th colspan="7">Activities</th>
							</tr>
							<tr>
								<th colspan="2">Number of Participants</th>
								<th colspan="2">Number of Ministers</th>
								<th rowspan="2">Member of Parliament</th>
								<th colspan="2">Number of Members</th>
								<th rowspan="2">Number of other Public Representatives</th>
								<th rowspan="2">Number of Government Officials</th>
								<th rowspan="2">Number of Works for Bhoomi Poojan </th>
								<th rowspan="2">Number of Works for Lokarpan</th>
								<th colspan="2">Shramdaan</th>
								<th rowspan="2">Agro forestry / Horticultural Plantation Number of Sapling </th>
								<th rowspan="2">Photos</th>
							</tr>
							<tr>
								<th>Male</th>
								<th>Female</th>
								<th>Central Level</th>
								<th>State Level</th>
								<th>Legislative Assembly</th>
								<th>Legislative Council</th>
								<th>No. of Locations</th>
								<th>No. of people participated</th>
							</tr>
						</thead>
						
 						<c:set var="st" value="" />
 					 	<c:forEach items="${dataList}" var="data" varStatus="count">
 							<tr>
 								<td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.waterid})"> Edit </button>
								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.waterid}"  name="${data.waterid}" value="${data.waterid}"/></td>
								<td> <c:out value="${data.datetime}" /></td>
 								<%-- <c:choose>
 									<c:when test="${st ne data.stname}">
 										<c:set var="st" value="${data.stname}" />
 										<td> <c:out value="${data.stname}" /></td>
 									</c:when>
 								<c:otherwise>
<!--  										<td></td> -->
 								</c:otherwise>
 								</c:choose> --%>
								<td class="text-left"> <c:out value="${data.distname}" /></td>
 								<td class="text-left"> <c:out value="${data.blockname}" /></td>
								<td class="text-left"> <c:out value="${data.location}" /></td>
								
 								<td class="text-right"> <c:out value="${data.maleparticipants}" /></td>
								<td class="text-right"> <c:out value="${data.femaleparticipants}" /></td>
 								<td class="text-right"> <c:out value="${data.centralministers}" /></td>
								<td class="text-right"> <c:out value="${data.stateministers}" /></td>
 								<td class="text-right"> <c:out value="${data.membersofparliament}" /></td>
 								<td class="text-right"> <c:out value="${data.legassemblymembers}" /></td>
 								<td class="text-right"> <c:out value="${data.legcouncilmembers}" /></td>
								<td class="text-right"> <c:out value="${data.publicreps}" /></td>
 								<td class="text-right"> <c:out value="${data.govofficials}" /></td>
 								
								<td class="text-right"> <c:out value="${data.no_works_bhoomipoojan}" /></td>
 								
 								<td class="text-right"> <c:out value="${data.no_works_lokarpan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.no_people_shramdaan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.area_plantation}" /></td>
								
 								
 								
								<td class="text-right">
<%-- 									<c:out value="${data.image_count}" /> --%>
<%-- 									<a href="#" data-id="${data.waterid}" class="showImage" style="color:blue;"><c:out value="${data.image_count}" /></a> --%>
									<a href="#" data-id="${data.waterid}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${data.image_count}" /></a> 
								</td>
					</tr>
							
					
 						</c:forEach> 
 						<c:if test="${dataListSize eq 0}">
							<tr>
								<td align="center" colspan="17" class="required" style="color:red;">Data Not Found</td>
								<td colspan="16" ></td>
							</tr>
						</c:if>
 						<c:if test="${dataListSize ne 0 && dataListSize >0}">
 						<tr>
								<td> <input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/> </td>
								<td> <input type="button" class="btn btn-info" id="complete" name="complete" value ="Complete"/> </td>
							</tr>
						</c:if>
						
		</table>
		
		
		</div>
		</div>
		
		<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Complete List of Watershed Mahotsav at Project Level Details</h5>
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
								<th colspan="7">Activities</th>
							</tr>
							<tr>
								<th colspan="2">Number of Participants</th>
								<th colspan="2">Number of Ministers</th>
								<th rowspan="2">Member of Parliament</th>
								<th colspan="2">Number of Members</th>
								<th rowspan="2">Number of other Public Representatives</th>
								<th rowspan="2">Number of Government Officials</th>
								<th rowspan="2">Number of Works for Bhoomi Poojan </th>
								<th rowspan="2">Number of Works for Lokarpan</th>
								<th colspan="2">Shramdaan</th>
								<th rowspan="2">Agro forestry / Horticultural Plantation Number of Sapling </th>
								<th rowspan="2">Photos</th>
							</tr>
							<tr>
								<th>Male</th>
								<th>Female</th>
								<th>Central Level</th>
								<th>State Level</th>
								<th>Legislative Assembly</th>
								<th>Legislative Council</th>
								<th>No. of Locations</th>
								<th>No. of people participated</th>
							</tr>
						</thead>
						
 						<c:set var="st" value="" />
 					 	<c:forEach items="${compdataList}" var="data" varStatus="count">
 							<tr>
								<td><c:out value='${count.count}' /> &nbsp;</td>
								<td> <c:out value="${data.datetime}" /></td>
 								
								<td class="text-left"> <c:out value="${data.distname}" /></td>
 								<td class="text-left"> <c:out value="${data.blockname}" /></td>
								<td class="text-left"> <c:out value="${data.location}" /></td>
								
 								<td class="text-right"> <c:out value="${data.maleparticipants}" /></td>
								<td class="text-right"> <c:out value="${data.femaleparticipants}" /></td>
 								<td class="text-right"> <c:out value="${data.centralministers}" /></td>
								<td class="text-right"> <c:out value="${data.stateministers}" /></td>
 								<td class="text-right"> <c:out value="${data.membersofparliament}" /></td>
 								<td class="text-right"> <c:out value="${data.legassemblymembers}" /></td>
 								<td class="text-right"> <c:out value="${data.legcouncilmembers}" /></td>
								<td class="text-right"> <c:out value="${data.publicreps}" /></td>
 								<td class="text-right"> <c:out value="${data.govofficials}" /></td>
 								
								<td class="text-right"> <c:out value="${data.no_works_bhoomipoojan}" /></td>
 								
 								<td class="text-right"> <c:out value="${data.no_works_lokarpan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.no_people_shramdaan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.area_plantation}" /></td>
 								
								<td class="text-right">
<%-- 									<c:out value="${data.image_count}" />  --%>
<%-- 									<a href="#" data-id="${data.waterid}" class="showImage" style="color:blue;"><c:out value="${data.image_count}" /> </a> --%>
									<a href="#" data-id="${data.waterid}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${data.image_count}" /></a> 
								</td>
					</tr>
							
					
 						</c:forEach> 
 						
						<c:if test="${compdataListSize eq 0}">
							<tr>
								<td align="center" colspan="17" class="required" style="color:red;">Data Not Found</td>
								<td colspan="16" ></td>
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