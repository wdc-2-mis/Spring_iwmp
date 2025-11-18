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

    if (inputs.length >= 15) {
        alert("Maximum 15 photographs allowed for this activity.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="${block.dataset.name}" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
    `;

    container.appendChild(div);
}

// Remove field
function removePhotoField(btn) {
    btn.closest("div").remove();
}


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

        if (totalFiles > 15) {
            errorDiv.innerHTML = "Maximum 15 photos allowed.";
            valid = false;
        }

    });

    if (!valid) return false;

    document.getElementById("inauguration").submit();
}

</script>
</head>
<body>
<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Mahotsav - Inauguration Program</h4> </div>
		<label>
			<span style="color:blue;">Note:- The image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
<!-- 		<form name="inauguration" id="inauguration" modelAttribute="inauguration" action="saveInaugurationDetails" method="post" enctype="multipart/form-data"> -->
		<!-- <form name="inauguration" id="inauguration" modelAttribute="WatershedYatraInauguaration" enctype="multipart/form-data"> -->
		<form:form autocomplete="off" method="post" name="inauguration" id="inauguration" action="saveInaugurationDetails" modelAttribute="useruploadign" enctype="multipart/form-data">
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
     		<td>Number of Works for Bhoomi Poojan</td>
     		<td colspan=2><input type="text" id="no_works_bhoomipoojan" name="no_works_bhoomipoojan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<!-- <td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_bhoomipoojan" name="tot_works_bhoomipoojan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_bhoomipoojan">

    <label><b>Upload Photographs (Min 2, Max 15):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_bhoomipoojan" class="form-control photo-input" accept="image/*" required />
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
     		<td>Number of Works for Lokarpan</td>
     		<td colspan=2><input type="text" id="no_works_lokarpan" name="no_works_lokarpan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<!-- <td>Cost of Total works (in Lakh)<br><input type="text" id="tot_works_lokarpan" name="tot_works_lokarpan" autocomplete="off"
								onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_lokarpan">

    <label><b>Upload Photographs (Min 2, Max 15):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_lokarpan" class="form-control photo-input" accept="image/*" required />
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
     		<td>Shramdaan</td>
     		<td>Number of Locations<br><input type="text" id="no_location_shramdaan" name="no_location_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>No. of people participated<br><input type="text" id="no_people_shramdaan" name="no_people_shramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /><br/> <br/>
			<!-- Number of Man Hours<br><input type="text" id="man" name="man" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /> --></td>
			<td>
<div class="photo-block" data-name="photos_shramdaan">

    <label><b>Upload Photographs (Min 2, Max 15):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_shramdaan" class="form-control photo-input" accept="image/*" required />
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
     		<td>Agro forestry / Horticultural Plantation - Area (in ha.)</td>
     		<td colspan=2><input type="text" id="area_plantation" name="area_plantation" autocomplete="off" onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     		<!-- <td>No. of Agro forestry / Horticultural Plants (No. of Sapling)<br><input type="text" id="noPlantation" name="no_plantation" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td> -->
			<td>
<div class="photo-block" data-name="photos_forestry">

    <label><b>Upload Photographs (Min 2, Max 15):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_forestry" class="form-control photo-input" accept="image/*" required />
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
     		<td>Number of Projects Awarded for Janbhagidari Cup 2025</td>
     		<td colspan=2><!-- Number of Watershed Margdarshaks<br> --><input type="text" id="no_awards" name="no_awards" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>
<div class="photo-block" data-name="photos_janbhagidari">

    <label><b>Upload Photographs (Min 2, Max 15):</b></label>

    <div class="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos_janbhagidari" class="form-control photo-input" accept="image/*" required />
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