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
 <%-- <script src='<c:url value="/resources/js/VillageWatershed.js" />'></script>  --%>
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
        <input type="hidden" id="bhoomipoojan_lat" name="bhoomipoojan_lat"/>
        <input type="hidden" id="bhoomipoojan_lng" name="bhoomipoojan_lng"/>
        <input type="hidden" id="bhoomipoojan_time" name="bhoomipoojan_time"/>
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
        <input type="hidden" id="lokarpan_lat" name="lokarpan_lat"/>
	    <input type="hidden" id="lokarpan_lng" name="lokarpan_lng"/>
	    <input type="hidden" id="lokarpan_time" name="lokarpan_time"/>
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
        <input type="hidden" id="shramdaan_lat" name="shramdaan_lat"/>
	    <input type="hidden" id="shramdaan_lng" name="shramdaan_lng"/>
	    <input type="hidden" id="shramdaan_time" name="shramdaan_time"/>
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
        <input type="hidden" id="forestry_lat" name="forestry_lat"/>
	    <input type="hidden" id="forestry_lng" name="forestry_lng"/>
	    <input type="hidden" id="forestry_time" name="forestry_time"/>
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
    const requiredWidth = 300;
    const requiredHeight = 400;


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
    let reader = new FileReader();
	reader.onload = function (e) {
    let img = new Image();
    img.onload = function () {
        if (checkValid && (img.width !== requiredWidth || img.height !== requiredHeight)) {
            alert(`Image dimensions must be ` +requiredWidth +` x `+ requiredHeight +` pixels.\nYour image: ` +img.width+` x `+img.height);
            input.value = "";
            checkValid = false;
            return;
        	}
   	 	};
    	img.src = e.target.result; // use base64 data URL from FileReader
		};
	reader.readAsDataURL(file);



    // 4. Check duplicate image by hashing
    getImageHash(file, function(hash) {
        if (checkValid && imageRecords[file.name] === hash) {
            alert("This image is already uploaded! Please upload a different image.");
            input.value = "";
            checkValid = false;
            return;
        }

        // 5. Check filename for special characters
        if (checkValid && /[^\w.-]/.test(file.name)) {
            alert("Filename contains special characters! Please rename the file and upload again.");
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
            let prefix = input.name;   // e.g. "photos_bhoomipoojan"
            
            document.getElementById(prefix.replace("photos_", "") + "_lat").value = latitude || "";
            document.getElementById(prefix.replace("photos_", "") + "_lng").value = longitude || "";
            document.getElementById(prefix.replace("photos_", "") + "_time").value = time || "";
//             alert('kdy_lat= '+document.getElementById("bhoomipoojan_lat").value +' kdy_lon= '+document.getElementById("bhoomipoojan_lng").value);
            // 8. Warn if GPS or timestamp missing
            
            if (checkValid && (!latitude || !longitude || !time)) {
                if (!confirm("This photo does NOT contain GPS or timestamp information.\nDo you still want to upload?")) {
                    input.value = "";
                    checkValid = false;
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
	$maleParticipants = $('#maleParticipants').val();
	$femaleParticipants = $('#femaleParticipants').val();
	$centralMinisters = $('#centralMinisters').val();
	$stateMinisters = $('#stateMinisters').val();
	$membersOfParliament = $('#membersOfParliament').val();
	$legAssemblyMembers = $('#legAssemblyMembers').val();
	$legCouncilMembers = $('#legCouncilMembers').val();
	$publicReps = $('#publicReps').val();
	$govOfficials = $('#govOfficials').val();
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
		$('#maleParticipants').focus();
		allValid = false;
		return false;
	}
	if ($femaleParticipants === '' || typeof $femaleParticipants === 'undefined') {
		alert('Please enter the Number Of Female Participants/Villagers');
		$('#femaleParticipants').focus();
		allValid = false;
		return false;
	}
	if ($centralMinisters === '' || typeof $centralMinisters === 'undefined') {
		alert('Please enter the Number of Central Ministers');
		$('#centralMinisters').focus();
		allValid = false;
		return false;
	}
	if ($stateMinisters === '' || typeof $stateMinisters === 'undefined') {
		alert('Please enter the Number of State Ministers');
		$('#stateMinisters').focus();
		allValid = false;
		return false;
	}
	if ($membersOfParliament === '' || typeof $membersOfParliament === 'undefined') {
		alert('Please enter the Number of Members of Parliament');
		$('#membersOfParliament').focus();
		allValid = false;
		return false;
	}
	if ($legAssemblyMembers === '' || typeof $legAssemblyMembers === 'undefined') {
		alert('Please enter the Number of Legislative Assembly Members');
		$('#legAssemblyMembers').focus();
		allValid = false;
		return false;
	}
	if ($legCouncilMembers === '' || typeof $legCouncilMembers === 'undefined') {
		alert('Please enter the Number of Legislative Council Members');
		$('#legCouncilMembers').focus();
		allValid = false;
		return false;
	}
	if ($publicReps === '' || typeof $publicReps === 'undefined') {
		alert('Please enter the Number of other Public Representatives');
		$('#publicReps').focus();
		allValid = false;
		return false;
	}
	if ($govOfficials === '' || typeof $govOfficials === 'undefined') {
		alert('Please enter the Number of Government Officials');
		$('#govOfficials').focus();
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

</script>


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
      		  <label for="datetime">Date and Time: </label>
       		 <input type="datetime-local" name="datetime" id="datetime" class="form-control activity" style="width: 100%;" />
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-3">
			
				State Name:</br> <c:out value="${stateName}"></c:out>
			
			</div>
    		<div class="form-group col-3">
      			District: </br> <c:out value="${distName}"></c:out>
      			
      		<input type="hidden" id="district" name="district" value="${distCode}">
      			
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Block: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block" name="block" >
    				<option value="">--Select Block--</option>
    				<c:forEach items="${blkList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    <label for="activity">Location (Nearby/Milestone)</label>
    <input type="text" class="form-control activity" name="location" id="location" onblur="myOverFunction(this)" style="width: 100%; max-width: 800px;" />
</div>

    		
    		</div>
    		
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td colspan=2>Number Of Participants/Villagers</td>
     		<td>Male<br><input type="text" id="maleParticipants" name="maleParticipants" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="femaleParticipants" name="femaleParticipants" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Ministers</td>
     		<td>Central Level<br><input type="text" id="centralMinisters" name="centralMinisters" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateMinisters" name="stateMinisters" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td colspan=2>Number of Member of Parliament</td>
     		<td colspan=2><input type="text" id="membersOfParliament" name="membersOfParliament" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Members</td>
     		<td>Legislative Assembly<br><input type="text" id="legAssemblyMembers" name="legAssemblyMembers" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="legCouncilMembers" name="legCouncilMembers" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of other Public Representatives</td>
     		<td colspan=2><input type="text" id="publicReps" name="publicReps" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	<tr>
     		<td colspan=2>Number of Government Officials</td>
     		<td colspan=2><input type="text" id="govOfficials" name="govOfficials" autocomplete="off"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
						<tr>
							<td>Number of Works for Bhoomi Poojan <input type="hidden" name="bhoomipoojan" id="bhoomipoojan" value="1"/></td>
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
											<input type="hidden" id="bhoomipoojan_lat"
												name="bhoomipoojan_lat"> <input type="hidden"
												id="bhoomipoojan_lng" name="bhoomipoojan_lng"> <input
												type="hidden" id="bhoomipoojan_time"
												name="bhoomipoojan_time">
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
							<td>Number of Works for Lokarpan <input type="hidden" name="lokarpan" id="lokarpan" value="2"/></td>
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
											<input type="hidden" id="lokarpan_lat" name="lokarpan_lat">
											<input type="hidden" id="lokarpan_lng" name="lokarpan_lng">
											<input type="hidden" id="lokarpan_time" name="lokarpan_time">
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
							<td>Shramdaan <input type="hidden" name="shramdaan" id="shramdaan" value="3"/></td>
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
											<input type="hidden" id="shramdaan_lat" name="shramdaan_lat">
											<input type="hidden" id="shramdaan_lng" name="shramdaan_lng">
											<input type="hidden" id="shramdaan_time"
												name="shramdaan_time">
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
							<td>Agro forestry / Horticultural Plantation - Number of Saplings <input type="hidden" name="forestry" id="forestry" value="4"/></td>
							<td colspan=2><input type="text" id="area_plantation"
								name="area_plantation" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
							<td>
								<div class="photo-block" data-name="photos_forestry">

									<label><b>Upload Photographs (Min 2, Max 10):</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_forestry"
												id="photos_forestry" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
											<input type="hidden" id="forestry_lat" name="forestry_lat">
											<input type="hidden" id="forestry_lng" name="forestry_lng">
											<input type="hidden" id="forestry_time" name="forestry_time">
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
        <div class="form-row">
				<div class="form-group col-8">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Save"/>
     			</div>
     		</div> 
     </div>
		</div>
	<br/>
     		
		</form:form>
	</div> 
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>