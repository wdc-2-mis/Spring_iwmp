<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/phystyle.css'/>">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/exif-js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js"></script>

<script>

// show Add button
document.addEventListener("change", function(e) {
    if (e.target.classList.contains("photo-input")) {
        $("#addPhotoBtn").show();
    }
});

// Add photo field
function addPhotoField() {

    let container = document.getElementById("photoContainer");
    let count = container.querySelectorAll(".photo-input").length;

    if (count >= 6) {
        alert("Maximum 6 photographs allowed.");
        return;
    }

    let div = document.createElement("div");
    div.className = "d-flex align-items-center mb-1";

    div.innerHTML = `
        <input type="file" name="photos" class="form-control photo-input"
               accept="image/*" onchange="validatePhoto(this)" required/>

        <button type="button" class="btn btn-danger btn-sm ml-2"
                onclick="removePhotoField(this)">X</button>

        <input type="hidden" name="latitude" class="latitude">
        <input type="hidden" name="longitute" class="longitude">
        <input type="hidden" name="photoTimestamp" class="photoTimestamp">
    `;

    container.appendChild(div);
}

// Remove photo
function removePhotoField(btn) {
    btn.closest("div").remove();
}

// DMS → decimal conversion
function convertDMSToDD(dms, ref) {
    let d = dms[0].numerator / dms[0].denominator;
    let m = dms[1].numerator / dms[1].denominator;
    let s = dms[2].numerator / dms[2].denominator;
    let dd = d + m/60 + s/3600;
    return (ref === "S" || ref === "W") ? -dd : dd;
}

let imageHashRecord = {};

// main validation + exif
function validatePhoto(input) {

    let file = input.files[0];
    if (!file) return;

    const maxSizeKB = 300;
    const allowed = ["image/jpeg", "image/png"];

    // type validation
    if (!allowed.includes(file.type)) {
        alert("Only JPG/PNG allowed");
        input.value = "";
        return;
    }

    // size validation
    if ((file.size / 1024) > maxSizeKB) {
        alert("File size exceeds 300 KB. Please choose a smaller file.");
        input.value = "";
        return;
    }
    var fileName = file.name;
    var invalidChars = /[^a-zA-Z0-9_.]/;
    var startsInvalid = /^[._]/.test(fileName);

    if (invalidChars.test(fileName) || startsInvalid) {
        alert("Filename contains invalid characters! Rename the file and upload again.");
        input.value = "";
        return;
    }
    
    if ( !/^[A-Za-z0-9]+\.(jpg|jpeg|png)$/i.test(fileName)) {
        alert("Filename contains special characters or file extension name is incorrect! Please rename the file and upload again.");
        input.value = "";
        return;
    }
    // HASH GENERATION
    getHash(file, function(hash) {
    	  let fileName = file.name;

    	    // 1️⃣ BLOCK IF SAME FILE NAME EXISTS (regardless of hash)
    	    if (imageHashRecord[fileName]) {
    	        alert("This image is already uploaded! Please upload a different image.");
    	        input.value = "";
    	        return;
    	    }
//         if (Object.values(imageHashRecord).includes(hash)) {
//             alert("This image is already uploaded! Please upload a different image.");
//             input.value = "";
//             return;
//         }

        // EXIF extraction
        EXIF.getData(file, function() {

            var fileDiv = input.parentElement;

            var latInput = fileDiv.querySelector(".latitude");
            var lngInput = fileDiv.querySelector(".longitude");
            var tsInput = fileDiv.querySelector(".photoTimestamp");

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

            // TIMESTAMP (Option B Logic)
            var dateTimeOriginal = EXIF.getTag(this, "DateTimeOriginal");
            if (dateTimeOriginal) {
                var parts = dateTimeOriginal.split(" "); 
                if (parts.length === 2) {
                    var datePart = parts[0].replace(/:/g, "-");
                    tsInput.value = datePart + " " + parts[1];
                } else {
                    tsInput.value = "";
                }
            } else {
                tsInput.value = "";
            }
            if (!latInput.value || !lngInput.value || !tsInput.value) {
                if (!confirm("This photo does NOT contain GPS or timestamp information.\nDo you still want to upload?")) {
                    input.value = "";  // clear selected file
                    return;
                }
            }
            imageHashRecord[file.name] = hash;

        });

    });
}

// hash function
function getHash(file, callback) {
    let reader = new FileReader();
    reader.onload = function(e) {
        let wa = CryptoJS.lib.WordArray.create(e.target.result);
        let hash = CryptoJS.SHA256(wa).toString();
        callback(hash);
    };
    reader.readAsArrayBuffer(file);
}


// validation before submission
let submitted = false;

function validation() {

    if (submitted) return false;

    if ($("#male_participants").val() === '') {
        alert("Enter male participants");
        return false;
    }
    if ($("#female_participants").val() === '') {
        alert("Enter female participants");
        return false;
    }
 // Check if any photo input has a file
    let photosSelected = false;
    document.querySelectorAll(".photo-input").forEach(function(input) {
        if (input.files.length > 0) {
            photosSelected = true;
        }
    });

    if (!photosSelected) {
        alert("You have not selected any photos! Only data update will happen.");
    }

    let count = 0;
    document.querySelectorAll(".photo-input").forEach(p => {
        if (p.files.length > 0) count++;
    });

    if (count > 0 && count < 2) {
        alert("Upload minimum 2 photos");
        return false;
    }

    if (confirm("Do you want to Update Prabhat Pheri Details?")) {
        submitted = true;
        document.mahotsav.action = "updateWMPrabhatPheri";
        document.mahotsav.method = "post";
        document.mahotsav.submit();
    }
}

// function editCancel() {
//     document.mahotsav.action = "getWMPrabhatPheri";
//     document.mahotsav.submit();
// }
function editCancel() {
    window.location.href = "getWMPrabhatPheri";
}


</script>
</head>

<body>

<div class="maindiv">

    <div class="col formheading">
        <h4><u>Watershed Mahotsav - Promotional Activity (Prabhat Pheri)</u></h4>
    </div>

    <label><span style="color:blue;">
        Note:- Image must be under 300KB with Geo-reference & Time-stamp.
    </span></label>
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
<form:form method="post" name="mahotsav" id="mahotsav"
           action="updateWMPrabhatPheri" modelAttribute="useruploadign"
           enctype="multipart/form-data">

        <hr/>

        <c:forEach items="${dataList}" var="data">

            <input type="hidden" name="prabhatpheri_id" value="${data.prabhatpheri_id}"/>
            <input type="hidden" name="date1" value="${data.date}"/>
            <input type="hidden" name="district1" value="${data.district1}"/>
            <input type="hidden" name="block1" value="${data.block1}"/>
            <input type="hidden" name="village1" value="${data.village1}"/>

            <div class="row mt-3">
                <div class="form-group col-3">
                    <label>Date:</label> <c:out value="${data.date}" />
                </div>
            </div>

            <div class="row">
                <div class="form-group col-3"><label>State:</label> <c:out value="${stateName}"/></div>
                <div class="form-group col-3"><label>District:</label> <c:out value="${data.distname}"/></div>
                <div class="form-group col-3"><label>Block:</label> <c:out value="${data.blockname}"/></div>
                <div class="form-group col-3"><label>Village:</label> <c:out value="${data.villagename}"/></div>
            </div>

            <table id="tblReport" class="table">
                <tr><th colspan="4" class="text-left">Participation :</th></tr>

                <tr>
                    <td>Number of Participants <span style="color:red;">*</span></td>

                    <td>Male<br>
                        <input type="text" id="male_participants" name="male_participants"
                               value="${data.male_participants}" maxlength="5"
                               oninput="this.value=this.value.replace(/[^0-9]/g,'');" required/>
                    </td>

                    <td>Female<br>
                        <input type="text" id="female_participants" name="female_participants"
                               value="${data.female_participants}" maxlength="5"
                               oninput="this.value=this.value.replace(/[^0-9]/g,'');" required/>
                    </td>

                    <td>
                        <label><b>Upload Photographs (Minimum 2, Maximum 6):</b></label>

                        <div id="photoContainer">

                            <div class="d-flex align-items-center mb-1">

                                <input type="file" name="photos" class="form-control photo-input"
                                       accept="image/*" onchange="validatePhoto(this)" required/>

                                <input type="hidden" name="latitude" class="latitude">
                                <input type="hidden" name="longitute" class="longitude">
                                <input type="hidden" name="photoTimestamp" class="photoTimestamp">

                            </div>

                        </div>

                        <button type="button" id="addPhotoBtn"
                                class="btn btn-sm btn-primary mt-2" style="display:none;"
                                onclick="addPhotoField()">Add More</button>

                    </td>
                </tr>
            </table>

            <div class="form-group text-left">
                <input type="button" class="btn btn-info" onclick="validation();" value="Update"/>
                <input type="button" class="btn btn-info" onclick="editCancel();" value="Cancel"/>
            </div>

        </c:forEach>

    </form:form>
</div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>
