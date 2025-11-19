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

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos_janbhagidari" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
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
        <input type="file" name="photos_bhoomipoojan" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
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
        <input type="file" name="photos_lokarpan" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
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
        <input type="file" name="photos_shramdaan" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
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
        <input type="file" name="photos_forestry" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
    `;

    container.appendChild(div);
}
// Remove field
function removePhotoField(btn) {
    btn.closest("div").remove();
}

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
	
	$no_works_bhoomipoojan = $('#no_works_bhoomipoojan').val();
	$bhoomipoojan_photo1 = $('#photos_bhoomipoojan').val();
	
	$no_works_lokarpan = $('#no_works_lokarpan').val();
	$lokarpan_photo1 = $('#photos_lokarpan').val();
	
	$no_location_shramdaan = $('#no_location_shramdaan').val();
	$no_people_shramdaan = $('#no_people_shramdaan').val();
	$shramdaan_photo1 = $('#photos_shramdaan').val();
	
	$area_plantation = $('#area_plantation').val();
	$plantation_photo1 = $('#photos_forestry').val();
	
	$no_awards = $('#no_awards').val();
	$award_photo1 = $('#photos_janbhagidari').val();
	
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
	
	if ($no_awards === '' || typeof $no_awards === 'undefined') {
		alert('Please enter the Number of Award Distribution');
		$('#no_awards').focus();
		allValid = false;
		return false;
	}
	if ($no_awards > 0) {
		if ($award_photo1 === '' || typeof $award_photo1 === 'undefined') {
			alert('Please upload photo for Award Distribution');
//	 		$('#award_photo1').focus();
			document.getElementById('photos_janbhagidari').click();
			allValid = false;
			return false;
		}
	}
	if (allValid) {
		if(confirm("Do you want to save Inauguration Program Details?")) {
		    formSubmitted = true; 
			document.inauguration.action="saveMahotsavInaugurationDetails";
			document.inauguration.method="post";
			document.inauguration.submit();
		}
		return true;
	}

    return false;
}






/* 
// Full validation
function validation() {

    let errorMessages = document.querySelectorAll(".photoError");

    // Clear all errors
    errorMessages.forEach(e => e.innerHTML = "");

    let valid = true;

    // For each activity block
    document.querySelectorAll(".photo-block").forEach(block => {

        let container = block.querySelector(".photoContainer");
        let errorDiv = block.querySelector(".photoError");
        let inputs = container.querySelectorAll("input[type='file']");
        let totalFiles = 0;

        inputs.forEach(inp => {
            if (inp.files.length > 0) {
                totalFiles++;

                for (let f of inp.files) {
                    if (f.size > 300 * 1024) {
                        errorDiv.innerHTML = "Each image must be under 300KB.";
                        valid = false;
                    }
                }
            }
        });

        if (totalFiles < 2) {
            errorDiv.innerHTML = "Please upload minimum 2 photos.";
            valid = false;
        }

        if (totalFiles >= 6) {
            errorDiv.innerHTML = "Maximum 15 photos allowed.";
            valid = false;
        }

    });

    if (!valid) return false;

    document.getElementById("inauguration").submit();
} */

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
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Mahotsav - Inauguration Program</h4> </div>
		<label>
			<span style="color:blue;">Note:- The image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
<!-- 		<form name="inauguration" id="inauguration" modelAttribute="inauguration" action="saveInaugurationDetails" method="post" enctype="multipart/form-data"> -->
		<!-- <form name="inauguration" id="inauguration" modelAttribute="WatershedYatraInauguaration" enctype="multipart/form-data"> -->
		<form:form autocomplete="off" method="post" name="inauguration" id="inauguration" action="saveMahotsavInaugurationDetails" modelAttribute="useruploadign" enctype="multipart/form-data">
			  <hr/>
			  
		<input type="hidden" name="inaugurationId" id="inaugurationId" />
			  
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
     		<td>Number of Participants</td>
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
     		<td>Number of Works for Bhoomi Poojan <input type="hidden" name="bhoomipoojan" id="bhoomipoojan" value="1"/></td>
     		<td colspan=2><input type="text" id="no_works_bhoomipoojan" name="no_works_bhoomipoojan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<!-- <td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_bhoomipoojan" name="tot_works_bhoomipoojan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td> -->
			<td>
			<div class="photo-block" data-name="photos_bhoomipoojan">
			
			    <label><b>Upload Photographs (Min 2, Max 6):</b></label>
			
			    <div class="photoContainer">
			        <div class="d-flex align-items-center mb-1">
			            <input type="file" name="photos_bhoomipoojan" id="photos_bhoomipoojan" class="form-control photo-input" accept="image/*" required />
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
     		<td>Number of Works for Lokarpan <input type="hidden" name="lokarpan" id="lokarpan" value="2"/></td>
     		<td colspan=2><input type="text" id="no_works_lokarpan" name="no_works_lokarpan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<!-- <td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_lokarpan" name="tot_works_lokarpan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_lokarpan">

    <label><b>Upload Photographs (Min 2, Max 6):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_lokarpan" id="photos_lokarpan" class="form-control photo-input" accept="image/*" required />
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
     		<td>Shramdaan <input type="hidden" name="shramdaan" id="shramdaan" value="3"/></td>
     		<td>Number of Locations<br><input type="text" id="no_location_shramdaan" name="no_location_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>No. of people participated<br><input type="text" id="no_people_shramdaan" name="no_people_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /><br/> <br/>
			<!-- Number of Man Hours<br><input type="text" id="man" name="man" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /> --></td>
			<td>
<div class="photo-block" data-name="photos_shramdaan">

    <label><b>Upload Photographs (Min 2, Max 6):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_shramdaan" id="photos_shramdaan" class="form-control photo-input" accept="image/*" required />
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
     		<td>Agro forestry / Horticultural Plantation Number of Sapling <input type="hidden" name="forestry" id="forestry" value="4"/></td>
     		<td colspan=2><input type="text" id="area_plantation" name="area_plantation" autocomplete="off" onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     		<!-- <td>No. of Agro forestry / Horticultural Plants (No. of Sapling)<br><input type="text" id="noPlantation" name="no_plantation" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_forestry">

    <label><b>Upload Photographs (Min 2, Max 6):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_forestry" id="photos_forestry" class="form-control photo-input" accept="image/*" required />
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
     		<td>Number of Projects Awarded for Janbhagidari Cup 2025 <input type="hidden" name="awarded" id="awarded" value="5"/></td>
     		<td colspan=2><!-- Number of Watershed Margdarshaks<br> --><input type="text" id="no_awards" name="no_awards" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>
<div class="photo-block" data-name="photos_janbhagidari">

    <label><b>Upload Photographs (Min 4, Max 10):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_janbhagidari" id="photos_janbhagidari" class="form-control photo-input" accept="image/*" required />
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
	
	
	
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>