<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<%@ page import="app.watershedyatra.bean.PreYatraPreparationBean" %>

<script>
//Show Add More button when a file is chosen
document.addEventListener("change", function(e) {
    if (e.target.classList.contains("photo-input")) {
        document.getElementById("addPhotoBtn").style.display = "inline-block";
    }
});

// Add new file upload field
function addPhotoField() {
    var container = document.getElementById("photoContainer");
    var inputs = container.getElementsByClassName("photo-input");

    if (inputs.length >= 12) {
        alert("Maximum 12 photographs can be uploaded.");
        return;
    }

    var div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos" class="form-control photo-input" accept="image/*" required />
        <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
    `;

    container.appendChild(div);
}

// Remove a photo field
function removePhotoField(btn) {
    btn.parentElement.remove();
}

function validation() {

    var photoInputs = document.getElementsByClassName("photo-input");
    var totalFiles = 0;
    var errorDiv = document.getElementById("photoError");
    errorDiv.innerHTML = "";

    // Count files and check size
    for (var i = 0; i < photoInputs.length; i++) {
        var files = photoInputs[i].files;

        if (files.length > 0) totalFiles++;

        for (var j = 0; j < files.length; j++) {
            if (files[j].size > 300 * 1024) {
                errorDiv.innerHTML = "Each image must be under 300KB. File " + (j+1) + " is too large.";
                return false;
            }
        }
    }

    if (totalFiles < 2) {
        errorDiv.innerHTML = "Please upload at least 2 photographs.";
        return false;
    }

    if (totalFiles > 15) {
        errorDiv.innerHTML = "You can upload only a maximum of 15 photographs.";
        return false;
    }

    document.getElementById("preyatraprep").submit();
}



</script>

<body>
	<div class="maindiv">
	
		<div class="col formheading" style=""><h4><u>Watershed Mahotsav - Promotional Activity (Prabhat Pheri)</u></h4> </div>
		<label>
		<span style="color:blue;">Note:- The image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
		<c:if test="${not empty result}">
             <script>alert("${result}");</script>
        </c:if>
		<form:form autocomplete="off" name="preyatraprep" id="preyatraprep" modelAttribute="preyatraprep" action="savePreYatraPreparation" method="post" enctype="multipart/form-data">
		
            
			
			
			<div class="card-body">
			<div class="form-group col-3">
                <label for="date"><b>Date:</b> </label>
                <input type="date" name="date1" id="date1" class="form-control" required/>
                </div>
                <br/>
			<div class="form-row">
				<div class="form-group col-3">
						<label for="state">	<b> State Name:</b> </label>
						<span class="projectError"></span> <br/>
						<c:out value="${stateName}"></c:out>
					
				</div>
	    		<div class="form-group col-3">
			<c:if test="${userType== 'SL' }">
			<label for="district"><b>District:</b> </label>
	      			<span class="projectError"></span>
	      			<select class="form-control district1" id="district1" name="district1" required>
	    				<option value="">--Select District--</option>
	    				<c:forEach items="${distList}" var="dist"> 
						<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
						</c:forEach>
	    			</select>
			</c:if>
			<c:if test="${userType== 'PI' }">
			<label for="district"><b>District:</b> </label> </br> <c:out value="${distName}"></c:out>
			<input type="hidden" id="district1" name="district1" value="${distCode}">
			</c:if>
			
			</div>

	    		
	    		<div class="form-group col-3">
	    		<c:if test="${userType== 'SL' }">
	    			<label for="activity"><b>Block:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="block1" name="block1" required>
	    				<option value="">--Select Block--</option>
	    			</select>
	    		</c:if>	
	    		<c:if test="${userType== 'PI' }">
	    		<label for="activity">Block: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block1" name="block1" >
    				<option value="">--Select Block--</option>
    				<c:forEach items="${blkList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
	    		</c:if>	
	    		</div>

	    			<div class="form-group col-3">
	    			<label for="activity"><b>Village Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="village1" name="village1" required>
	    				<option value="">--Select Village Name--</option>
	    			</select>
	    		</div>
	    		
	    		 <table id = "tblReport" class = "table">
	    		 <tr>
	    		<th colspan=4 class="text-left">Participation :</th>
	    		</tr>
	    		
	    		<tr>
     		<td>Number of Participants</td>
     		<td>Male<br><input type="text" id="male_participants" name="male_participants" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="female_participants" name="female_participants" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	
               <td>
               <label><b>Upload Photographs (Minimum 2, Maximum 12):</b></label>

    <!-- Container for all upload fields -->
    <div id="photoContainer">
        <div class="d-flex align-items-center mb-1">
            <input type="file" name="photos" class="form-control photo-input"
                   accept="image/*" required />
        </div>
    </div>

    <!-- Add More Button (appears after selecting files) -->
    <button type="button" id="addPhotoBtn" class="btn btn-sm btn-primary mt-2" style="display:none;"
            onclick="addPhotoField()">Add More</button>

    <small class="text-danger" id="photoError"></small>
               </td>
               </tr>
               </table>
               
               </div>
            
		</div>
		
		
    	<div class="form-group text-center">
               <input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Submit"/>
            </div>
	    		</form:form>
	    		
	
	 
	  			</div>
	    		<footer class=" text-center">
	            <%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	
	
	    		</body>