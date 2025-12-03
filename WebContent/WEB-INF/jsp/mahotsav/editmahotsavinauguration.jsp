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
<script src='<c:url value="/resources/js/mahotsavinauguration.js" />'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

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
function addPhotoField(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >= 10) {
        alert("Maximum 10 photographs allowed for this activity.");
        return;
    }
    
    let newIndex = inputs.length + 1;
    
    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_janbhagidari" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_janbhagidari_lat" name="photos_janbhagidari_lat" value="0"/>
	    <input type="hidden" id="photos_janbhagidari_lng" name="photos_janbhagidari_lng" value="0"/>
	    <input type="hidden" id="photos_janbhagidari_time" name="photos_janbhagidari_time" value="0"/>
    `;

    container.appendChild(div);
}
function addPhotoField1(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=6) {
        alert("Maximum 6 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_bhoomipoojan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_bhoomipoojan_lat" name="photos_bhoomipoojan_lat" value="0"/>
        <input type="hidden" id="photos_bhoomipoojan_lng" name="photos_bhoomipoojan_lng" value="0"/>
        <input type="hidden" id="photos_bhoomipoojan_time" name="photos_bhoomipoojan_time" value="0"/>
    `;

    container.appendChild(div);
}
function addPhotoField2(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=6) {
        alert("Maximum 6 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_lokarpan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_lokarpan_lat" name="photos_lokarpan_lat" value="0"/>
	    <input type="hidden" id="photos_lokarpan_lng" name="photos_lokarpan_lng" value="0"/>
	    <input type="hidden" id="photos_lokarpan_time" name="photos_lokarpan_time" value="0"/>
    `;

    container.appendChild(div);
}
function addPhotoField3(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=6) {
        alert("Maximum 6 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_shramdaan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_shramdaan_lat" name="photos_shramdaan_lat" value="0"/>
	    <input type="hidden" id="photos_shramdaan_lng" name="photos_shramdaan_lng" value="0"/>
	    <input type="hidden" id="photos_shramdaan_time" name="photos_shramdaan_time" value="0"/>
    `;

    container.appendChild(div);
}
function addPhotoField4(btn) {

    let block = btn.closest(".photo-block");
    let container = block.querySelector(".photoContainer");
    let inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >=6) {
        alert("Maximum 6 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_forestry" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)"  required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        <input type="hidden" id="photos_forestry_lat" name="photos_forestry_lat" value="0"/>
	    <input type="hidden" id="photos_forestry_lng" name="photos_forestry_lng" value="0"/>
	    <input type="hidden" id="photos_forestry_time" name="photos_forestry_time" value="0"/>
    `;

    container.appendChild(div);
}
// Remove field
function removePhotoField(btn) {
    btn.closest("div").remove();
}

let formSubmitted = false;

function validation() {
    let isValid = true;
    
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
	
	$no_works_bhoomipoojan = $('#no_works_bhoomipoojan').val();
	$no_works_lokarpan = $('#no_works_lokarpan').val();
	$no_location_shramdaan = $('#no_location_shramdaan').val();
	$no_people_shramdaan = $('#no_people_shramdaan').val();
	$area_plantation = $('#area_plantation').val();
	$no_awards = $('#no_awards').val();
	
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
	
	if ($no_works_bhoomipoojan === '' || typeof $no_works_bhoomipoojan === 'undefined') {
		alert('Please enter Number of Works of Bhoomi Poojan');
		$('#no_works_bhoomipoojan').focus();
		allValid = false;
		return false;
	}
	 
	if ($no_works_lokarpan === '' || typeof $no_works_lokarpan === 'undefined') {
		alert('Please enter the Number of Works of Lokarpan');
		$('#no_works_lokarpan').focus();
		allValid = false;
		return false;
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
	
	if ($area_plantation === '' || typeof $area_plantation === 'undefined') {
		alert('Please enter the Plantation Area in hectares');
		$('#area_plantation').focus();
		allValid = false;
		return false;
	}
	
	if ($no_awards === '' || typeof $no_awards === 'undefined') {
		alert('Please enter the Number of Award Distribution');
		$('#no_awards').focus();
		allValid = false;
		return false;
	}
	 

    $(".photo-block").each(function () {
        let container = $(this).find(".photoContainer");
        let photos = container.find("input[type='file']");
        let uploaded = 0;
        let errorDiv = $(this).find(".photoError");
        errorDiv.html("");
        photos.each(function () {
            if ($(this).val() !== "") {
                uploaded++;
            }
        });

        // If user uploaded photos then validate min/max
        if (uploaded > 0) {
            let label = $(this).find("label").text();

            let min = 2, max = 6;

            // Janbhagidari rule: min 4 max 10
            if ($(this).data("name") === "photos_janbhagidari") {
                min = 4;
                max = 10;
            }

            if (uploaded < min || uploaded > max) {
            	errorDiv.html("Please upload minimum " + min + " photos.");
                alert(label + "\nPlease upload minimum " + min + " photos required for this activity.");
                isValid = false;
                return false; // break loop
            }
        }
    });

    if (!isValid) return false;

    // Prevent double submission
    if (formSubmitted) return false;

    if (confirm("Do you want to Update Inauguration Program Details?")) {
        formSubmitted = true;
        document.inauguration.action = "updateMahotsavInaugurationDetails";
        document.inauguration.method = "post";
        document.inauguration.submit();
    }

    return true;
}


function editChangedata(inaugurationid){
	
	document.getElementById('inauguaration_id').value=inaugurationid;
    document.inauguration.action="getMahotsavInaugurationEdit";
	document.inauguration.method="post";
	document.inauguration.submit();
}

function editChancel(){
	
    document.inauguration.action="registerInauguration";
	document.inauguration.method="get";
	document.inauguration.submit();
}


//Store image hashes to prevent duplicates
let imageRecords = {};

// VALIDATE PHOTO
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
        return;
    }
    
     var invalidChars = /[^a-zA-Z0-9_.]/;
     if (checkValid && (invalidChars.test(file.name) || /^[._]/.test(file.name))) {
         alert("Filename contains invalid characters! Please rename the file and upload again.");
         input.value = "";
         checkValid = false
         return;
     }
     
     // 5. Check filename for special characters
     if (checkValid && !/^[A-Za-z0-9]+\.(jpg|jpeg|png)$/i.test(file.name)) {
         alert("Filename contains special characters or file extension name is incorrect! Please rename the file and upload again.");
         input.value = "";
         checkValid = false;
         return;
     }
    
      let sizeKB = file.size / 1024;
    if (sizeKB > maxSizeKB) {
        alert("Image size must be 300 KB or less.\nYour image size: " + Math.round(sizeKB) + " KB");
        input.value = "";
        checkValid = false;
        return;
    }
    
 
    getImageHash(file, function(hash) {
        if (imageRecords[file.name] === hash) {
            alert("This image is already uploaded! Please upload a different image.");
            input.value = "";
            checkValid = false;
            return;
        }
        
        // 5. Read EXIF metadata (GPS + timestamp)
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
            
            let parentDiv = input.closest('div');
            let latInput = parentDiv.querySelector('input[id$="_lat"]');
            let lngInput = parentDiv.querySelector('input[id$="_lng"]');
            let timeInput = parentDiv.querySelector('input[id$="_time"]');
            
            if (latInput) latInput.value = latitude || "0";
            if (lngInput) lngInput.value = longitude || "0";
            if (timeInput) timeInput.value = time || "0";
            
          // alert('kdy2='+latInput);
           
            // 7. Warn if GPS or timestamp missing
            if (!latitude || !longitude || !time) {
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

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }
  
function openLargeImage(imageSrc, index, total) {
	document.getElementById('imagePopup').style.display = 'none';
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/Inauguration/' + imageSrc;			//PRD
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/vanyatradoc/Inauguration/' + imageSrc;	//TEST
 //	document.getElementById('largeImage').src = 'resources/images/Inauguration/' + imageSrc;												//Local
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
</head>
<body>
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
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Mahotsav - Updation of Inauguration Program</h4> </div>
		<label>
			<span style="color:blue;">Note:- The image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
<!-- 		<form name="inauguration" id="inauguration" modelAttribute="inauguration" action="saveInaugurationDetails" method="post" enctype="multipart/form-data"> -->
		<!-- <form name="inauguration" id="inauguration" modelAttribute="WatershedYatraInauguaration" enctype="multipart/form-data"> -->
		<form:form autocomplete="off" method="post" name="inauguration" id="inauguration" action="updateMahotsavInaugurationDetails" modelAttribute="useruploadign" enctype="multipart/form-data">
			  <hr/>
			 <c:forEach items="${dataList}" var="data" varStatus="count">
				<input type="hidden" id="inauguaration_id" name="inauguaration_id" value="${data.inauguaration_id}"/>
				<input type="hidden" id="date" name="date" value="${data.date}"/>
				<input type="hidden" id="district" name="district" value="${data.district}"/>
				<input type="hidden" id="block" name="block" value="${data.block}"/>
			  
			  <div class="row">
    			<div class="form-group col-3">
    			
      		  	<label for="date">Date: &nbsp; <c:out value="${data.date}" /></label>
       		 
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-3">
			State Name: &nbsp; <c:out value="${stateName}"></c:out>
			</div>
    		<div class="form-group col-3">
      			<label for="district">District: </label>
      			<span class="activityError"></span>
      			 <c:out value="${data.distname}" />
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Block: </label>
      			<span class="activityError"></span>
      			<c:out value="${data.blockname}" />
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Location (Nearby/Milestone)<span style="color: red;">*</span></label>
    			<input type="text" class="form-control activity" name="location" id="location" value="${data.location}" style="width: 100%; max-width: 800px;" />
    		</div>
    		
    		
    		
    		</div>
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=3 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td>Number of Participants<span style="color: red;">*</span></td>
     		<td>Male<br><input type="text" id="male_participants" name="male_participants" autocomplete="off" value="${data.male_participants}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="female_participants" name="female_participants" autocomplete="off" value="${data.female_participants}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Ministers<span style="color: red;">*</span></td>
     		<td>Central Level<br><input type="text" id="central_ministers" name="central_ministers" autocomplete="off" value="${data.central_ministers}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateMinisters" name="state_ministers" autocomplete="off" value="${data.state_ministers}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td>Number of Member of Parliament<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="parliament" name="parliament" autocomplete="off" value="${data.parliament}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Members<span style="color: red;">*</span></td>
     		<td>Legislative Assembly<br><input type="text" id="assembly_members" name="assembly_members" autocomplete="off" value="${data.assembly_members}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="council_members" name="council_members" autocomplete="off" value="${data.council_members}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of other Public Representatives<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="others" name="others" autocomplete="off" value="${data.others}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Government Officials<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="gov_officials" name="gov_officials" autocomplete="off" value="${data.gov_officials}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	</table>
     	<table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
     	
     	<tr>
     		<td>Number of Works for Bhoomi Poojan<span style="color: red;">*</span> <input type="hidden" name="bhoomipoojan" id="bhoomipoojan" value="1"/></td>
     		<td colspan=2><input type="text" id="no_works_bhoomipoojan" name="no_works_bhoomipoojan" autocomplete="off" value="${data.no_works_bhoomipoojan}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<!-- <td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_bhoomipoojan" name="tot_works_bhoomipoojan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td> -->
			<td>
			<div class="photo-block" data-name="photos_bhoomipoojan">
			
			    <label><b>Upload Photographs (Min 2, Max 6):</b></label>
			
			    <div class="photoContainer">
			        <div class="d-flex align-items-center mb-1">
			            <input type="file" name="photos_bhoomipoojan" id="photos_bhoomipoojan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
			            <input type="hidden" id="photos_bhoomipoojan_lat" name="photos_bhoomipoojan_lat" value="0"/>
		                <input type="hidden" id="photos_bhoomipoojan_lng" name="photos_bhoomipoojan_lng" value="0"/>
		                <input type="hidden" id="photos_bhoomipoojan_time" name="photos_bhoomipoojan_time" value="0"/>
			        </div>
			    </div>
			
			    <button type="button" class="btn btn-sm btn-primary mt-2 addPhotoBtn"
			            style="display:none;"
			            onclick="addPhotoField1(this)">
			        + Add More
			    </button>
			
			    <small class="text-danger photoError"></small>
			
			</div>
			</td>

     	</tr>
     	<tr>
     		<td>Number of Works for Lokarpan<span style="color: red;">*</span> <input type="hidden" name="lokarpan" id="lokarpan" value="2"/></td>
     		<td colspan=2><input type="text" id="no_works_lokarpan" name="no_works_lokarpan" autocomplete="off" value="${data.no_works_lokarpan}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<!-- <td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_lokarpan" name="tot_works_lokarpan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_lokarpan">

    <label><b>Upload Photographs (Min 2, Max 6):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_lokarpan" id="photos_lokarpan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
            <input type="hidden" id="photos_lokarpan_lat" name="photos_lokarpan_lat" value="0"/>
		    <input type="hidden" id="photos_lokarpan_lng" name="photos_lokarpan_lng" value="0"/>
		    <input type="hidden" id="photos_lokarpan_time" name="photos_lokarpan_time" value="0"/>
        </div>
    </div>

    <button type="button" class="btn btn-sm btn-primary mt-2 addPhotoBtn"
            style="display:none;"
            onclick="addPhotoField2(this)">
        + Add More
    </button>

    <small class="text-danger photoError"></small>

</div>
</td>

     	</tr>
     	<tr>
     		<td>Shramdaan<span style="color: red;">*</span> <input type="hidden" name="shramdaan" id="shramdaan" value="3"/></td>
     		<td>Number of Locations<br><input type="text" id="no_location_shramdaan" name="no_location_shramdaan" autocomplete="off" value="${data.no_location_shramdaan}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>No. of people participated<br><input type="text" id="no_people_shramdaan" name="no_people_shramdaan" autocomplete="off" value="${data.no_people_shramdaan}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required />
			<!-- Number of Man Hours<br><input type="text" id="man" name="man" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /> --></td>
			<td>
<div class="photo-block" data-name="photos_shramdaan">

    <label><b>Upload Photographs (Min 2, Max 6):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_shramdaan" id="photos_shramdaan" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
            <input type="hidden" id="photos_shramdaan_lat" name="photos_shramdaan_lat" value="0"/>
		    <input type="hidden" id="photos_shramdaan_lng" name="photos_shramdaan_lng" value="0"/>
		    <input type="hidden" id="photos_shramdaan_time" name="photos_shramdaan_time" value="0"/>
        </div>
    </div>

    <button type="button" class="btn btn-sm btn-primary mt-2 addPhotoBtn"
            style="display:none;"
            onclick="addPhotoField3(this)">
        + Add More
    </button>

    <small class="text-danger photoError"></small>

</div>
</td>
     	</tr>
     	<tr>
     		<td>Agro forestry / Horticultural Plantation Number of Sapling <span style="color: red;">*</span><input type="hidden" name="forestry" id="forestry" value="4"/></td>
     		<td colspan=2><input type="text" id="area_plantation" name="area_plantation" autocomplete="off" value="${data.forestry_horticulture}"
     		pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<!-- <td>No. of Agro forestry / Horticultural Plants (No. of Sapling)<br><input type="text" id="noPlantation" name="no_plantation" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_forestry">

    <label><b>Upload Photographs (Min 2, Max 6):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_forestry" id="photos_forestry" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
            <input type="hidden" id="photos_forestry_lat" name="photos_forestry_lat" value="0"/>
		    <input type="hidden" id="photos_forestry_lng" name="photos_forestry_lng" value="0"/>
		    <input type="hidden" id="photos_forestry_time" name="photos_forestry_time" value="0"/>
        </div>
    </div>

    <button type="button" class="btn btn-sm btn-primary mt-2 addPhotoBtn"
            style="display:none;"
            onclick="addPhotoField4(this)">
        + Add More
    </button>

    <small class="text-danger photoError"></small>

</div>
</td>
     	</tr>
     	<tr>
     		<td>Number of Projects Awarded for Janbhagidari Cup 2025<span style="color: red;">*</span> <input type="hidden" name="awarded" id="awarded" value="5"/></td>
     		<td colspan=2><input type="text" id="no_awards" name="no_awards" autocomplete="off" value="${data.no_awards}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>
<div class="photo-block" data-name="photos_janbhagidari">

    <label><b>Upload Photographs (Min 4, Max 10):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_janbhagidari" id="photos_janbhagidari" class="form-control photo-input" accept="image/*" onchange="validatePhoto(this)" required />
            <input type="hidden" id="photos_janbhagidari_lat" name="photos_janbhagidari_lat" value="0"/>
		    <input type="hidden" id="photos_janbhagidari_lng" name="photos_janbhagidari_lng" value="0"/>
		    <input type="hidden" id="photos_janbhagidari_time" name="photos_janbhagidari_time" value="0"/>
        </div>
    </div>

    <button type="button" class="btn btn-sm btn-primary mt-2 addPhotoBtn"
            style="display:none;"
            onclick="addPhotoField(this)">
        + Add More
    </button>

    <small class="text-danger photoError"></small>

</div>
</td>
     	</tr>
     	
     	<tr>
     		<td colspan=4 class="text-left">
					<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Update"/>
     				<input type="button" class="btn btn-info" id="cancelbtn" name="cancelbtn" onclick="editChancel();"  value ="Cancel"/>
     		</td>
     	</tr>
     </table>
     </div>
		</div>  
		</c:forEach>
		</form:form>
		
	</div>
	
	<%-- <div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Draft List of Watershed Mahotsav - Inauguration Program Details</h5>
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
								<th rowspan="2">Number of Projects Awarded for Janbhagidari Cup 2025 </th>
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
 							<td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.inauguaration_id})"> Edit </button>
								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.inauguaration_id}"  name="${data.inauguaration_id}" value="${data.inauguaration_id}"/></td>
								<td> <c:out value="${data.date}" /></td>
 								<c:choose>
 									<c:when test="${st ne data.stname}">
 										<c:set var="st" value="${data.stname}" />
 										<td> <c:out value="${data.stname}" /></td>
 									</c:when>
 								<c:otherwise>
<!--  										<td></td> -->
 								</c:otherwise>
 								</c:choose>
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
 								
								<td class="text-right"> <c:out value="${data.no_works_bhoomipoojan}" /></td>
 								
 								<td class="text-right"> <c:out value="${data.no_works_lokarpan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.no_people_shramdaan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.forestry_horticulture}" /></td>
								
 								<td class="text-right"> <c:out value="${data.no_awards}" /></td>
 								
 								
								<td class="text-right">
									<a href="#" data-id="${data.inauguaration_id}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${data.image_count}" /></a> 
								</td>
					</tr>
							
					
 						</c:forEach> 
 						
 						<tr>
								
								<td> <input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/> </td>
								<td> <input type="button" class="btn btn-info" id="complete" name="complete" value ="Complete"/> </td>
							</tr>
						<c:if test="${dataListSize eq 0}">
							<tr>
								<td align="center" colspan="17" class="required" style="color:red;">Data Not Found</td>
								<td colspan="16" ></td>
							</tr>
						</c:if>
		</table>
		
		
		</div>
		</div>
		
		<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Complete List of Watershed Mahotsav - Inauguration Program Details</h5>
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
								<th rowspan="2">Number of Projects Awarded for Janbhagidari Cup 2025 </th>
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
								<td> <c:out value="${data.date}" /></td>
 								<c:choose>
 									<c:when test="${st ne data.stname}">
 										<c:set var="st" value="${data.stname}" />
 										<td> <c:out value="${data.stname}" /></td>
 									</c:when>
 								<c:otherwise>
<!--  										<td></td> -->
 								</c:otherwise>
 								</c:choose>
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
 								
								<td class="text-right"> <c:out value="${data.no_works_bhoomipoojan}" /></td>
 								
 								<td class="text-right"> <c:out value="${data.no_works_lokarpan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.no_location_shramdaan}" /></td>
								<td class="text-right"> <c:out value="${data.no_people_shramdaan}" /></td>
								
 								<td class="text-right"> <c:out value="${data.forestry_horticulture}" /></td>
								
 								<td class="text-right"> <c:out value="${data.no_awards}" /></td>
 								
 								
								<td class="text-right">
									<a href="#" data-id="${data.inauguaration_id}" class="showImage" data-toggle="modal" style ="color: blue;"><c:out value="${data.image_count}" /></a> 
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
		</div> --%>
	
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>