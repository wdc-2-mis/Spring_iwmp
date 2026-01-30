<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/phystyle.css'/>">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/watershedPunarutthanPlan.js" />'></script> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/exif-js"></script>

<script type="text/javascript">
function editChangedata(plan_id){
	
	document.getElementById('plan_id').value=plan_id
    document.mahotsav.action="getWatershedPunarutthanPlanEditaa";
	document.mahotsav.method="post";
	document.mahotsav.submit();
}
function validateDecimal(input, decimalPlaces) {
    // Allow only numbers and one decimal point
    const regex = new RegExp(`^\\d*(\\.\\d{0,${decimalPlaces}})?$`);
    if (!regex.test(input.value)) {
      input.value = input.value.slice(0, -1); // Remove the last invalid character 
    }
  }
//IMAGE VALIDATION FUNCTION
function checkImage(input, inputId) {
    var file = input.files[0];
    if(!file) return;

    var fileType = file.type;
    var fileSize = file.size; // bytes
    var maxFileSize = 300 * 1024; // 300 KB
    var maxWidth = 400; // pixels
    var maxHeight = 400; // pixels

    // Check file type
    if (!fileType.startsWith('image/')) {
        alert('Only image files are allowed');
        input.value = ''; // Clear input
        document.getElementById(inputId).focus();
        return false;
    }

    // Check file size
    if (fileSize > maxFileSize) {
        alert('File size exceeds 300 KB. Please choose a smaller file.');
        input.value = '';
        document.getElementById(inputId).focus();
        return false;
    }

    // Check dimensions
    var reader = new FileReader();
//     reader.onload = function(e) {
//         var img = new Image();
//         img.onload = function() {
//             var width = img.width;
//             var height = img.height;
//             if (width > maxWidth || height > maxHeight) {
//                 alert('Image dimensions exceed allowed size of ' + maxWidth + 'x' + maxHeight + ' pixels.');
//                 input.value = '';
//                 document.getElementById(inputId).focus();
//                 return false;
//             }
//         };
//         img.src = e.target.result;
//     };
    reader.readAsDataURL(file);

}

// Convert EXIF DMS to decimal degrees
function convertDMSToDD(dms, ref) {
    if (!dms) return null;
    var degrees = dms[0];
    var minutes = dms[1];
    var seconds = dms[2];
    var dd = degrees + minutes / 60 + seconds / 3600;
    if (ref == "S" || ref == "W") dd = dd * -1;
    return dd;
}

// Format current timestamp as "yyyy-MM-dd HH:mm:ss"
function getCurrentTimestamp() {
    var now = new Date();
    var year = now.getFullYear();
    var month = ("0" + (now.getMonth()+1)).slice(-2);
    var day = ("0" + now.getDate()).slice(-2);
    var hours = ("0" + now.getHours()).slice(-2);
    var minutes = ("0" + now.getMinutes()).slice(-2);
    var seconds = ("0" + now.getSeconds()).slice(-2);
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

$(document).ready(function() {


 // EXTRACT GPS & PHOTO TIMESTAMP FROM IMAGE
document.addEventListener("change", function(e) {
    if (e.target.classList.contains("photo-input")) {

        // --- START: IMAGE VALIDATION ---
        var inputId = e.target.id || 'photoInput';
        checkImage(e.target, inputId);  // Call your checkImage function
        // --- END: IMAGE VALIDATION ---

        var file = e.target.files[0];
     // === NEW VALIDATIONS START ===

     // Track uploaded filenames
     if (!window.uploadedFiles) {
         window.uploadedFiles = new Set();
     }

     var fileName = file.name;

     
     var invalidChars = /[^a-zA-Z0-9_.-]/;
     if (invalidChars.test(fileName) || /^[._]/.test(fileName)) {
         alert("Filename contains invalid characters! Please rename the file and upload again.");
         e.target.value = ""; // reset file input
         return;
     }

     // 5. Check filename for special characters
     if ( !/^[A-Za-z0-9]+\.(jpg|jpeg|png)$/i.test(fileName)) {
         alert("Filename contains special characters or file extension name is incorrect! Please rename the file and upload again.");
         e.target.value = "";
         return;
     }
     // Check if this image already uploaded (duplicate)
     if (window.uploadedFiles.has(fileName)) {
         alert("This image is already uploaded! Please upload a different image.");
         e.target.value = ""; // reset file input
         return;
     } else {
         window.uploadedFiles.add(fileName); // Store filename
     }


        var fileDiv = e.target.parentElement;
        var latInput = fileDiv.querySelector(".latitude");
        var lngInput = fileDiv.querySelector(".longitude");
        var tsInput = fileDiv.querySelector(".photoTimestamp");

        if (!file) return;

        EXIF.getData(file, function() {
            // GPS
            var lat = EXIF.getTag(this, "GPSLatitude");
            var latRef = EXIF.getTag(this, "GPSLatitudeRef");
            var lng = EXIF.getTag(this, "GPSLongitude");
            var lngRef = EXIF.getTag(this, "GPSLongitudeRef");

            if (lat && lng) {
                latInput.value = convertDMSToDD(lat, latRef);
                lngInput.value = convertDMSToDD(lng, lngRef);
            } else {
                latInput.value = "";
                lngInput.value = "";
            }

            // Photo timestamp
            var dateTimeOriginal = EXIF.getTag(this, "DateTimeOriginal");
            if (dateTimeOriginal) {
                var parts = dateTimeOriginal.split(" "); // ["YYYY:MM:DD", "HH:MM:SS"]
                if(parts.length === 2){
                    var datePart = parts[0].replace(/:/g, "-"); // YYYY-MM-DD
                    tsInput.value = datePart + " " + parts[1];   // YYYY-MM-DD HH:MM:SS
                } else {
                    tsInput.value = "";
                }
            } else {
                tsInput.value = "";
            }
            // ALERT IF GPS OR TIMESTAMP MISSING
            if (!latInput.value || !lngInput.value || !tsInput.value) {
                if (!confirm("This photo does NOT contain GPS or timestamp.\nContinue?")) {
                    input.value = ""; // Clear the input if user cancels
                    latInput.value = "";
                    lngInput.value = "";
                    tsInput.value = "";
                    return;
                }
            }
        });

        // Show Add More button
        document.getElementById("addPhotoBtn").style.display = "inline-block";
    }
});


    // Add photo input field
    window.addPhotoField = function() {
        var container = document.getElementById("photoContainer");
        var inputs = container.getElementsByClassName("photo-input");
        if (inputs.length >= 2) {
            alert("Maximum 2 photographs can be uploaded.");
            return;
        }
        var div = document.createElement("div");
        div.className = "d-flex align-items-center mb-1";
        div.innerHTML = `
            <input type="file" name="photos" class="form-control photo-input" accept="image/*" required />
            <input type="hidden" name="latitude[]" class="latitude">
            <input type="hidden" name="longitute[]" class="longitude">
            <input type="hidden" name="photoTimestamp[]" class="photoTimestamp">
            <button type="button" class="btn btn-danger btn-sm ml-2" onclick="removePhotoField(this)">X</button>
        `;
        container.appendChild(div);
    }

    window.removePhotoField = function(btn) {
        btn.parentElement.remove();
    }

    // Validation
    window.validation = function() {
    	
        var block = document.getElementById("project").value;
        if (!block) {
            alert("Please select a project.");
            document.getElementById("project").focus();
            return false;
        }

        var village = document.getElementById("village1").value;
        if (!village) {
            alert("Please select a village.");
            document.getElementById("village1").focus();
            return false;
        }

        var structure = document.getElementById("structure").value;
        if (!structure) {
            alert("Please select a structure.");
            document.getElementById("structure").focus();
            return false;
        }
        
        var work = document.getElementById("work").value;
        if (!work || parseInt(work) <= 0) {
            alert("Please enter No. of Maintenance work to do");
            document.getElementById("work").focus();
            return false;
        }

        var wdf = document.getElementById("wdf").value;
        if (!wdf || wdf <= 0) {
            alert("Please enter Estimated Cost from WDF");
            document.getElementById("wdf").focus();
            return false;
        }
        
        var mgnrega = document.getElementById("mgnrega").value;
        if (!mgnrega || mgnrega <= 0) {
            alert("Please enter Estimated Cost from VB G RAM G / MGNREGA");
            document.getElementById("mgnrega").focus();
            return false;
        }
        
        var other = document.getElementById("other").value;
        if (!other || other <= 0) {
            alert("Please enter Estimated Cost from Other Source");
            document.getElementById("other").focus();
            return false;
        }
        
        
     // 7. Photo validation (count & size)
        var photoInputs = document.getElementsByClassName("photo-input");
        var totalFiles = 0;
        for (var i = 0; i < photoInputs.length; i++) {
            var files = photoInputs[i].files;
            if (files.length > 0) totalFiles++;
            for (var j = 0; j < files.length; j++) {
                if (files[j].size > 300 * 1024) {
                    alert("Photo '" + files[j].name + "' exceeds 300KB. Please select a smaller image.");
                    return false;
                }
            }
        }
        if (totalFiles < 1) {
            alert("Minimum 1 photographs required.");
            return false;
        }
        if (totalFiles > 2) {
            alert("Maximum 2 photographs allowed.");
            return false;
        }
        if(confirm("Do you want to save Watershed Punarutthan Planing ?")) {
       		 document.getElementById("mahotsav").submit();
        }
    }

});

function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }
  
function openLargeImage(imageSrc, index, total) {
	document.getElementById('imagePopup').style.display = 'none';
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/prabhatpheri/' + imageSrc;		
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/prabhatpheri/' + imageSrc;
//  	document.getElementById('largeImage').src = 'resources/images/prabhatpheri/' + imageSrc;											
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
<body>
<div class="maindiv">
    <div class="col formheading">
        <h4><u>Add/View Watershed Punarutthan Planing</u></h4>
    </div>
    <label>
        <span style="color:blue;">Note:- The image size must be under 300KB with Geo-referenced and Time-stamped.</span>
    </label>

    <c:if test="${not empty result}">
        <script>alert("${result}");</script>
    </c:if>

    <form:form autocomplete="off" name="mahotsav" id="mahotsav" action="saveWatershedPunarutthanPlan" method="post" modelAttribute="useruploadign" enctype="multipart/form-data">
			
        <div class="card-body">

            <div class="form-row">
              <div class="form-group col-3">
                    <label for="state"><b>State Name:</b></label><br/>
                    <c:out value="${stateName}"/>
                    <input type="hidden" name="stCode" value="${stCode}" />
              </div>
                
              <div class="form-group col-3">
      			<label for="district"><b>District: </b></label><br/><c:out value="${distName}"></c:out>
      			<input type="hidden" id="district1" name="district1" value="${distCode}">
    		</div>

			<div class="form-group col-3">
    			<label for="activity"><b>Project:</b><span style="color: red;">*</span> </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="project" name="project" >
    				<option value="">--Select Project--</option>
    				<c:forEach items="${projList}" var="dist"> 
						<option value="<c:out value="${dist.key}"/>" ><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>

                <div class="form-group col-3">
                    <label for="village"><b>Village Name:</b><span style="color:red;">*</span></label>
                    <select class="form-control" id="village1" name="village1" required>
                        <option value="">--Select Village Name--</option>
                    </select>
                </div>
            </div>

            <table id="tblReport" class="table">
                <tr>
                    <th colspan="4" class="text-left"></th>
                </tr>
                <tr>
                    <td>Name of Structure<span style="color:red;">*</span></td>
                    
                    <td> 
	                    <select id="structure" name="structure" style="height:40px" >
	    				<option value="" >--Select Structure--</option>
	    				<c:forEach items="${StructureList}" var="dist"> 
							<option value="<c:out value="${dist.key}"/>" ><c:out value="${dist.value}" /></option>
						</c:forEach>
	    				</select>
                      </td>
                  </tr>  
                  <tr>  
                    <td>Maintenance work to do<span style="color:red;">*</span></td>
                    <td>
                    	<textarea id="work" name="work" rows="3" cols="50" required></textarea>
                    
                    </td>
                    </tr>
                    <tr>
                    		<th colspan="4" class="text-left">Total Estimated Cost (Rs. in Lakhs)  </th>
                	</tr>
                    <tr>
                    <td>Estimated Cost from WDF<span style="color:red;">*</span></td>
                    
                     <td>   <input type="text" id="wdf" name="wdf" autocomplete="off"
                               maxlength="12" oninput="validateDecimal(this, 2)" required/>
                    </td>
                    </tr>
                    <tr>
                    <td>Estimated Cost from VB G RAM G / MGNREGA<span style="color:red;">*</span></td>
                    
                     <td>   <input type="text" id="mgnrega" name="mgnrega" autocomplete="off"
                               maxlength="12" oninput="validateDecimal(this, 2)" required/>
                    </td>
                    </tr>
                    <tr>
                    <td>Other Source<span style="color:red;">*</span></td>
                    
                     <td>   <input type="text" id="other" name="other" autocomplete="off"
                               maxlength="12" oninput="validateDecimal(this, 2)" required/>
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <label>Upload Photographs<span style="color:red;">*</span><!--  (Minimum 1, Maximum 2) -->:</label></td>
                     <td>   <div id="photoContainer">
                            <div class="d-flex align-items-center mb-1">
                                <input type="file" name="photos" class="form-control photo-input" accept="image/*" required/>
                                <input type="hidden" name="latitude[]" class="latitude">
                                <input type="hidden" name="longitute[]" class="longitude">
                                <input type="hidden" name="photoTimestamp[]" class="photoTimestamp">
                            </div>
                        </div>
                        <button type="button" id="addPhotoBtn" class="btn btn-sm btn-primary mt-2" style="display:none;" onclick="addPhotoField()">Add More</button>
                        <small class="text-danger" id="photoError"></small>
                    </td>
                </tr>
            </table>
        </div>

        <div class="form-group text-left">
            <input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();" value="Submit"/>
        </div>

    </form:form>
</div>
<div class="form-row">
         <div class="form-group col">
         <hr/>
         <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Draft List of Watershed Punarutthan Planing Details</h5>
         <table class="table table-bordered table-striped table-highlight w-auto" id="prabhatpheriTable">
                        <thead class ="theadlist" id = "theadlist">
                            <tr>
                            	<!-- <th>Action</th> -->
                                <th>S.No.  &nbsp; <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /></th> 
                                <th>District Name</th>
                                <th>Project Name</th>
                                <th>Village Name</th>
                                <th>Structure Name</th>
                                <th>No. of Maintenance work to do</th>
                                <th>Estimated Cost from WDF</th>
                                <th>Estimated Cost from VB G RAM G / MGNREGA</th>
                                <th>Other Source</th>
                                <th>Total Estimated Cost</th>
                            </tr>
                          
                        </thead>
                        <c:set var="dist" value="" />
                        <c:set var="proj" value="" />
                        <c:forEach items="${dataList1}" var="data" varStatus="count">
 							<tr>
 								<%-- <td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.plan_id})"> Edit </button> --%>
								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.plan_id}"  name="${data.plan_id}" value="${data.plan_id}"/></td>
 								
   								<c:choose>
  									<c:when test="${dist ne data.distname}">
  										<c:set var="dist" value="${data.distname}" />
  										<td> <c:out value="${dist}" /></td>
  									</c:when>
  								<c:otherwise> 
  										<td></td>
 								</c:otherwise>
 								</c:choose> 
 								
 								<c:choose>
  									<c:when test="${proj ne data.proj_name}">
  										<c:set var="proj" value="${data.proj_name}" />
  										<td> <c:out value="${proj}" /></td>
  									</c:when>
  								<c:otherwise> 
  										<td></td>
 								</c:otherwise>
 								</c:choose> 
 								
								<%-- <td class="text-left"> <c:out value="${data.distname}" /></td>
 								<td class="text-left"> <c:out value="${data.proj_name}" /></td> --%>
 								<td class="text-right"> <c:out value="${data.villagename}" /></td>
								<td class="text-right"> <c:out value="${data.structurename}" /></td>
 								<td class="text-right"> <c:out value="${data.no_work}" /></td>
								<td class="text-right"> <c:out value="${data.wdf}" /></td>
 								<td class="text-right"> <c:out value="${data.mgnrega}" /></td>
 								<td class="text-right"> <c:out value="${data.other}" /></td>
 								<td class="text-right"> <c:out value="${data.totalcost}" /></td>
								
 								
							<%--	<td class="text-right">
									<a href="#" data-id="${data.plan_id}" class="showImage" data-toggle="modal" style ="color: blue;">Image</a> 
								</td> --%>
					</tr>
 						</c:forEach> 
 						<c:if test="${dataListSize1 gt 0}">
 						<tr>
								<td> <input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/> </td>
								<td> <input type="button" class="btn btn-info" id="complete" name="complete" value ="Complete"/> </td>
							</tr>
						</c:if>	
						<c:if test="${dataListSize1 eq 0}">
							<tr>
								<td align="center" colspan="10" class="required" style="color:red;">Data Not Found</td>
							</tr>
						</c:if>
        </table>
        
        
        </div>
        </div>
        
        <div class="form-row">
         <div class="form-group col">
         <hr/>
         <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Completed List of Watershed Punarutthan Planing Details</h5>
         <table class="table table-bordered table-striped table-highlight w-auto" >
                        <thead class ="theadlist" id = "theadlist">
                            <tr>
                                <th>S.No.</th>
                                <th>District Name</th>
                                <th>Project Name</th>
                                <th>Village Name</th>
                                <th>Structure Name</th>                                
                                <th>No. of Maintenance work to do</th>
                                <th>Estimated Cost from WDF</th>
                                <th>Estimated Cost from VB G RAM G / MGNREGA</th>
                                <th>Other Source</th>
                                <th>Total Estimated Cost</th>
                            </tr>
                          
                        </thead>
                          <c:set var="dist1" value="" />
                          <c:set var="proj1" value="" />
                        <c:forEach items="${comdataList1}" var="data" varStatus="count">
 							<tr>
 								<%-- <td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.plan_id})"> Edit </button> --%>
								<td><c:out value='${count.count}' /> <%-- &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.plan_id}"  name="${data.plan_id}" value="${data.plan_id}"/> --%></td>
 								
   								<c:choose>
  									<c:when test="${dist1 ne data.distname}">
  										<c:set var="dist1" value="${data.distname}" />
  										<td> <c:out value="${dist1}" /></td>
  									</c:when>
  								<c:otherwise> 
  										<td></td>
 								</c:otherwise>
 								</c:choose> 
 								<c:choose>
  									<c:when test="${proj1 ne data.proj_name}">
  										<c:set var="proj1" value="${data.proj_name}" />
  										<td> <c:out value="${proj1}" /></td>
  									</c:when>
  								<c:otherwise> 
  										<td></td>
 								</c:otherwise>
 								</c:choose> 
								<%-- <td class="text-left"> <c:out value="${data.distname}" /></td> 
 								<td class="text-left"> <c:out value="${data.proj_name}" /></td>--%>
 								<td class="text-right"> <c:out value="${data.villagename}" /></td>
								<td class="text-right"> <c:out value="${data.structurename}" /></td>
 								<td class="text-right"> <c:out value="${data.no_work}" /></td>
								<td class="text-right"> <c:out value="${data.wdf}" /></td>
 								<td class="text-right"> <c:out value="${data.mgnrega}" /></td>
 								<td class="text-right"> <c:out value="${data.other}" /></td>
 								<td class="text-right"> <c:out value="${data.totalcost}" /></td>
								
 								
							<%--	<td class="text-right">
									<a href="#" data-id="${data.plan_id}" class="showImage" data-toggle="modal" style ="color: blue;">Image</a> 
								</td> --%>
					</tr>
 						</c:forEach> 
 						<c:if test="${comdataListSize1 eq 0}">
							<tr>
								<td align="center" colspan="10" class="required" style="color:red;">Data Not Found</td>
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


<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});


</script>
<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>
</body>
