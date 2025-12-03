<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/phystyle.css'/>">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/PrabhatpheriMahotsav.js" />'></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/exif-js"></script>

<script type="text/javascript">
function editChangedata(prabhatpheri_id){
	
	document.getElementById('prabhatpheri_id').value=prabhatpheri_id
    document.mahotsav.action="getWMPrabhatPheriEdit";
	document.mahotsav.method="post";
	document.mahotsav.submit();
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
        if (inputs.length >= 6) {
            alert("Maximum 6 photographs can be uploaded.");
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
    	var dateField = document.getElementById("date1").value;
        if (!dateField) {
            alert("Please select a date for Prabhat Pheri.");
            document.getElementById("date1").focus();
            return false;
        }

        // 2. District validation
        var district = document.getElementById("district1").value;
        if (!district) {
            alert("Please select a district.");
            document.getElementById("district1").focus();
            return false;
        }

        // 3. Block validation
        var block = document.getElementById("block1").value;
        if (!block) {
            alert("Please select a block.");
            document.getElementById("block1").focus();
            return false;
        }

        // 4. Village validation
        var village = document.getElementById("village1").value;
        if (!village) {
            alert("Please select a village.");
            document.getElementById("village1").focus();
            return false;
        }

        // 5. Male participants validation
        var male = document.getElementById("male_participants").value;
        if (!male || parseInt(male) <= 0) {
            alert("Please enter a valid number of male participants.");
            document.getElementById("male_participants").focus();
            return false;
        }

        // 6. Female participants validation
        var female = document.getElementById("female_participants").value;
        if (!female || parseInt(female) <= 0) {
            alert("Please enter a valid number of female participants.");
            document.getElementById("female_participants").focus();
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
        if (totalFiles < 2) {
            alert("Minimum 2 photographs required.");
            return false;
        }
        if (totalFiles > 6) {
            alert("Maximum 6 photographs allowed.");
            return false;
        }
        document.getElementById("mahotsav").submit();
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
        <h4><u>Watershed Mahotsav - Promotional Activity (Prabhat Pheri)</u></h4>
    </div>
    <label>
        <span style="color:blue;">Note:- The image size must be under 300KB with Geo-referenced and Time-stamped.</span>
    </label>

    <c:if test="${not empty result}">
        <script>alert("${result}");</script>
    </c:if>

    <form:form autocomplete="off" name="mahotsav" id="mahotsav"
               action="saveWMPrabhatPheri" method="post"
               enctype="multipart/form-data">
<input type="hidden" id="prabhatpheri_id" name="prabhatpheri_id" />
        <div class="card-body">
            <div class="form-group col-3">
                <label for="date"><b>Date:</b><span style="color: red;">*</span> </label>
                <input type="date" name="date1" id="date1" class="form-control" required/>
            </div>

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

<!--                 <div class="form-group col-3"> -->
<!--                     <label for="district"><b>District:</b></label> -->
<!--                     <select class="form-control" id="district1" name="district1" required> -->
<!--                         <option value="">--Select District--</option> -->
<%--                         <c:forEach items="${distList}" var="dist"> --%>
<%--                             <option value="<c:out value='${dist.key}'/>"><c:out value="${dist.value}"/></option> --%>
<%--                         </c:forEach> --%>
<!--                     </select> -->
<!--                 </div> -->

<!--                 <div class="form-group col-3"> -->
<!--                     <label for="block"><b>Block:</b></label> -->
<!--                     <select class="form-control" id="block1" name="block1"> -->
<!--                         <option value="">--Select Block--</option> -->
<!--                     </select> -->
<!--                 </div> -->

<div class="form-group col-3">
    			<label for="activity"><b>Block:</b><span style="color: red;">*</span> </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block1" name="block1" >
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

                <div class="form-group col-3">
                    <label for="village"><b>Village Name:</b><span style="color:red;">*</span></label>
                    <select class="form-control" id="village1" name="village1" required>
                        <option value="">--Select Village Name--</option>
                    </select>
                </div>
            </div>

            <table id="tblReport" class="table">
                <tr>
                    <th colspan="4" class="text-left">Participation :</th>
                </tr>
                <tr>
                    <td>Number of Participants<span style="color:red;">*</span></td>
                    <td>Male<br>
                        <input type="text" id="male_participants" name="male_participants" autocomplete="off"
                               maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required/>
                    </td>
                    <td>Female<br>
                        <input type="text" id="female_participants" name="female_participants" autocomplete="off"
                               maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required/>
                    </td>
                    <td>
                        <label><b>Upload Photographs (Minimum 2, Maximum 6):</b></label>
                        <div id="photoContainer">
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
         <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Draft List of Watershed Mahotsav - Promotional Activity (Prabhat Pheri) Details</h5>
         <table class="table table-bordered table-striped table-highlight w-auto" id="prabhatpheriTable">
                        <thead class ="theadlist" id = "theadlist">
                            <tr>
                            	<th>Action</th>
                                <th>S.No.  &nbsp; <input type="checkbox" id="chkSelectAllkd" name="chkSelectAllkd" /></th> 
                                <th>Date</th>
                                <th>District Name</th>
                                <th>Block Name</th>
                                <th>Village Name</th>
                                <th>No. of Male Participants</th>
                                <th>No. of Female Participants</th>
                                <th>No. of Photos</th>
                            </tr>
                          
                        </thead>
                        
                        <c:set var="st" value="" />
 						<c:forEach items="${dataDList}" var="data" varStatus="count">
 							<tr>
 								<td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.prabhatpheri_id})"> Edit </button> 
								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.prabhatpheri_id}"  name="${data.prabhatpheri_id}" value="${data.prabhatpheri_id}"/></td>
								<td><c:out value="${data.date}" /></td>
 								<td><c:out value="${data.distname}" /></td>
 								<td><c:out value="${data.blockname}" /></td>
 								<td><c:out value="${data.villagename}" /></td>
 								<td class="text-right"><c:out value="${data.male_participants}" /></td>
								<td class="text-right"><c:out value="${data.female_participants}" /></td>
								<td class="text-right"><c:out value="${data.photo_count}" /></td>
<!-- 								<td class="text-right"> -->
<%-- 									<a href="#" data-id="${data.prabhatpheri_id}" class="showImage" style="color:blue;"><c:out value="${data.photo_count}" /></a>  --%>
<!-- 								</td> -->
 								
 							</tr>
 						</c:forEach>
 						<c:if test="${dataDListSize gt 0}">
                        <tr>
                                <td> <input type="button" class="btn btn-info" id="delete" name="delete" value ="Delete"/> </td>
                                <td> <input type="button" class="btn btn-info" id="complete" name="complete" value ="Complete"/> </td>
                            </tr>
                            </c:if>
                        <c:if test="${dataDListSize eq 0}">
                            <tr>
                                <td align="center" colspan="8" class="required" style="color:red;">Data Not Found</td>
<!--                                 <td colspan="16" ></td> -->
                            </tr>
                        </c:if>
        </table>
        
        
        </div>
        </div>
        
        <div class="form-row">
         <div class="form-group col">
         <hr/>
         <h5 class="text-center font-weight-bold" style="text-decoration: underline;">Completed List of Watershed Mahotsav - Promotional Activity (Prabhat Pheri) Details</h5>
         <table class="table table-bordered table-striped table-highlight w-auto" id="prabhatpheriTable">
                        <thead class ="theadlist" id = "theadlist">
                            <tr>
                                <th>S.No.</th>
                                <th>Date</th>
                                <th>District Name</th>
                                <th>Block Name</th>
                                <th>Village Name</th>
                                <th>No. of Male Participants</th>
                                <th>No. of Female Participants</th>
                                <th>No. of Photos</th>
                            </tr>
                          
                        </thead>
                        
                        <c:set var="st" value="" />
 						<c:forEach items="${dataCList}" var="data" varStatus="count">
 							<tr>
 							
<%--  								<td><button class="btn btn-warning btn-sm" onclick="editChangedata(${data.watershed_yatra_id})"> Edit </button>  --%>
<%-- 								<td><c:out value='${count.count}' /> &nbsp;<input type="checkbox" class="chkIndividualkd" id="${data.prabhatpheri_id}"  name="${data.prabhatpheri_id}" value="${data.prabhatpheri_id}"/></td> --%>
								<td><c:out value='${count.count}' /> </td>
								<td><c:out value="${data.date}" /></td>
 								<td><c:out value="${data.distname}" /></td>
 								<td><c:out value="${data.blockname}" /></td>
 								<td><c:out value="${data.villagename}" /></td>
 								<td class="text-right"><c:out value="${data.male_participants}" /></td>
								<td class="text-right"><c:out value="${data.female_participants}" /></td>
<%-- 								<td class="text-right"><c:out value="${data.photo_count}" /></td> --%>
								<td class="text-right">
									<a href="#" data-id="${data.prabhatpheri_id}" class="showImage" style="color:blue;"><c:out value="${data.photo_count}" /></a> 
								</td>
 								
 							</tr>
 						</c:forEach>
                        <tr>
                                
                            </tr>
                        <c:if test="${dataCListSize eq 0}">
                            <tr>
                                <td align="center" colspan="8" class="required" style="color:red;">Data Not Found</td>
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
