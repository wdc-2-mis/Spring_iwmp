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
	allValid = true;
	
	$(".photo-block").each(function () {
        let container = $(this).find(".photoContainer");
        let photos = container.find("input[type='file']");
        let errorDiv = $(this).find(".photoError");
        let uploaded = 0;
     // clear old errors
        errorDiv.html("");
        photos.each(function () {
            if ($(this).val() !== "") {
                uploaded++;
            }
        });
        // If user uploaded photos then validate min/max
        if (uploaded > 0) {
            let label = $(this).find("label").text();
            let min = 2
            if (uploaded < min) {
            	errorDiv.html("Please upload minimum " + min + " photos.");
            	alert(`Minimum `+min+` photos required for this activity. Please add more photos`);
            	$(this).data("name")==="photos_bhoomipoojan"?$('#photos_bhoomipoojan').focus():$(this).data("name")==="photos_lokarpan"?$('#photos_lokarpan').focus():
            		$(this).data("name")==="photos_shramdaan"?$('#photos_shramdaan').focus():$('#photos_forestry').focus()
                allValid = false;
                return false; // break loop
            }
        }
    });
	
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
// 	if ($no_works_bhoomipoojan > 0) {
// 		if ($bhoomipoojan_photo1 === '' || typeof $bhoomipoojan_photo1 === 'undefined') {
// 			alert('Please upload photo for Bhoomi Poojan');
// //	 		$('#bhoomipoojan_photo1').focus();
// 			document.getElementById('photos_bhoomipoojan').click();
// 			allValid = false;
// 			return false;
// 		}
// 	}
	if ($no_works_lokarpan === '' || typeof $no_works_lokarpan === 'undefined') {
		alert('Please enter the Number of Works of Lokarpan');
		$('#no_works_lokarpan').focus();
		allValid = false;
		return false;
	}
// 	if ($no_works_lokarpan > 0) {
// 		if ($lokarpan_photo1 === '' || typeof $lokarpan_photo1 === 'undefined') {
// 			alert('Please upload photo for Lokarpan');
// //	 		$('#lokarpan_photo1').focus();
// 			document.getElementById('photos_lokarpan').click();
// 			allValid = false;
// 			return false;
// 		}
// 	}
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
// 	if ($no_location_shramdaan > 0) {
// 		if ($shramdaan_photo1 === '' || typeof $shramdaan_photo1 === 'undefined') {
// 			alert('Please upload photo for Shramdaan');
// //	 		$('#shramdaan_photo1').focus();
// 			document.getElementById('photos_shramdaan').click();
// 			allValid = false;
// 			return false;
// 		}
// 	}
	if ($area_plantation === '' || typeof $area_plantation === 'undefined') {
		alert('Please enter the Plantation Area in hectares');
		$('#area_plantation').focus();
		allValid = false;
		return false;
	}
// 	if ($area_plantation > 0) {
// 		if ($plantation_photo1 === '' || typeof $plantation_photo1 === 'undefined') {
// 			alert('Please upload photo for Plantation');
// //	 		$('#plantation_photo1').focus();
// 			document.getElementById('photos_forestry').click();
// 			allValid = false;
// 			return false;
// 		}
// 	}

// 	if(($plantation_photo1 ==='' || typeof $plantation_photo1 ==='undefined') && ($shramdaan_photo1 ==='' || typeof $shramdaan_photo1 ==='undefined') && ($lokarpan_photo1 ==='' || typeof $lokarpan_photo1 ==='undefined') && ($bhoomipoojan_photo1 ==='' || typeof $bhoomipoojan_photo1 ==='undefined'))
// 	{
// 		alert('If you choose not to upload any new photos, your previously uploaded images will remain unchanged!');
// 	}
// 	else{
	
// 	// For each activity block
//     document.querySelectorAll(".photo-block").forEach(block => {

//         let container = block.querySelector(".photoContainer");
//         let errorDiv = block.querySelector(".photoError");
//         let inputs = container.querySelectorAll("input[type='file']");
//         let totalFiles = 0;
//         let minPhotos = 2;
//         errorDiv.innerHTML = ""; // clear old errors
//         inputs.forEach(inp => {
//             if (inp.files.length > 0) {
//                 totalFiles++;
//             }
//         });

//         let activityInput = block.closest("tr").querySelector("input[type='text']");
//         let activityValue = activityInput ? parseInt(activityInput.value || 0) : 0;
//         if (activityValue > 0 && totalFiles < minPhotos) {
//             errorDiv.innerHTML = "Please upload minimum " +minPhotos+" photos.";
//             alert(`Minimum `+minPhotos+` photos required for this activity.`);
//             allValid = false;
//             return false;
//         }

//     });
// 	}

	if (allValid) {
		if(confirm("Do you want to update Watershed Mahotsav at Project Level?")) {
		document.saveWatershed.action="updateWatershedMahotsavProjLvlDetails";
		document.saveWatershed.method="post";
		document.saveWatershed.submit();
		}
		return true;
	}else{
		return false;
	}
//     document.getElementById("saveWatershed").submit();
}

function editChancel(){
	
    document.saveWatershed.action="getWatershedMahotsavAtProjLvl";
	document.saveWatershed.method="get";
	document.saveWatershed.submit();
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
		<div class="col formheading" style="text-decoration: underline;"><h4>Update Watershed Mahotsav at Project Level</h4> </div>
			<label>
		<span style="color:blue;">Note:- The Image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
		<form:form autocomplete="off" method="post" name="saveWatershed" id="saveWatershed" action="updateWatershedMahotsavProjLvlDetails" modelAttribute="useruploadsl" enctype="multipart/form-data">
			<hr/>
			<c:forEach items="${dataList}" var="data" varStatus="count">
				<input type="hidden" id="waterid" name="waterid" value="${data.waterid}"/>
				<input type="hidden" id="datetime" name="datetime" value="${data.datetime}"/>
				<input type="hidden" id="district" name="district" value="${data.district}"/>
				<input type="hidden" id="block" name="block" value="${data.block}"/>
				<div class="row">
					<div class="form-group col-3">
						<label for="date">Date: &nbsp; <c:out value="${data.datetime}" /></label>
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
     		<th colspan=4 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td colspan=2>Number Of Participants/Villagers<span style="color: red;">*</span></td>
     		<td>Male<br><input type="text" id="maleparticipants" name="maleparticipants" autocomplete="off" value="${data.maleparticipants}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="femaleparticipants" name="femaleparticipants" autocomplete="off" value="${data.femaleparticipants}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Ministers<span style="color: red;">*</span></td>
     		<td>Central Level<br><input type="text" id="centralministers" name="centralministers" autocomplete="off" value="${data.centralministers}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateministers" name="stateministers" autocomplete="off" value="${data.stateministers}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td colspan=2>Number of Member of Parliament<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="membersofparliament" name="membersofparliament" autocomplete="off" value="${data.membersofparliament}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Members<span style="color: red;">*</span></td>
     		<td>Legislative Assembly<br><input type="text" id="legassemblymembers" name="legassemblymembers" autocomplete="off" value="${data.legassemblymembers}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="legcouncilmembers" name="legcouncilmembers" autocomplete="off" value="${data.legcouncilmembers}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of other Public Representatives<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="publicreps" name="publicreps" autocomplete="off" value="${data.publicreps}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	<tr>
     		<td colspan=2>Number of Government Officials<span style="color: red;">*</span></td>
     		<td colspan=2><input type="text" id="govofficials" name="govofficials" autocomplete="off" value="${data.govofficials}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
						<tr>
							<td>Number of Works for Bhoomi Poojan<span style="color: red;">*</span> <input type="hidden" name="bhoomipoojan" id="bhoomipoojan" value="1"/></td>
							<td colspan=2><input type="text" id="no_works_bhoomipoojan"
								name="no_works_bhoomipoojan" autocomplete="off" value="${data.no_works_bhoomipoojan}"
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
											<input type="hidden" id="bhoomipoojan_lat" name="photos_bhoomipoojan_lat" value = "0"/> 
											<input type="hidden" id="bhoomipoojan_lng" name="photos_bhoomipoojan_lng" value = "0"/> 
											<input type="hidden" id="bhoomipoojan_time"	name="photos_bhoomipoojan_time" value = "0"/>
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
							<td colspan=2><input type="text" id="no_works_lokarpan" name="no_works_lokarpan" autocomplete="off" value="${data.no_works_lokarpan}"
							pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
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
							<input type="text" id="no_location_shramdaan" name="no_location_shramdaan" autocomplete="off" value="${data.no_location_shramdaan}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							<td>Number of people participated<br>
							<input type="text" id="no_people_shramdaan"	name="no_people_shramdaan" autocomplete="off" value="${data.no_people_shramdaan}"
							pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
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
							<td colspan=2><input type="text" id="area_plantation" name="area_plantation" autocomplete="off" value="${data.area_plantation}"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
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
						<tr>
     					<td colspan=4 class="text-left">
							<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Update"/>
     						<input type="button" class="btn btn-info" id="cancelbtn" name="cancelbtn" onclick="editChancel();"  value ="Cancel"/>
     					</td>
     					</tr>
					</table>
     </div>
		</div>
	<br/>
     	</c:forEach>	
		</form:form>
	</div> 
	
	
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>