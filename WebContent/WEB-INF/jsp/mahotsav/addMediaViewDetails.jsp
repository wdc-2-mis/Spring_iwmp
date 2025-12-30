<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavheader.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title>Add the Social Media Views Details</title>
<script>
function validatePhoto(input) {
	let checkValid = true;
    const file = input.files[0];
    if (!file) return;

    const maxSizeKB = 300; // 300 KB limit
    const allowedTypes = ["image/jpeg", "image/png"];

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
        // 5. Check filename for special characters
        if (checkValid && !/^[A-Za-z0-9]+\.(jpg|jpeg|png)$/i.test(file.name)) {
            alert("Filename contains special characters or file extension name is incorrect! Please rename the file and upload again.");
            input.value = "";
            checkValid = false;
            return;
        }
 }


let allValid = true;
function validation() 
{
	allValid = true;
	
	var allowedFiles = [".jpg", ".jpeg",".png"];

	$photos_screenshot = $('#photos_screenshot').val();
	
	$no_of_views = $('#no_of_views').val();
	
	$no_of_subscriber = $('#no_of_subscriber').val();
	
	$no_of_likes = $('#no_of_likes').val();
	
	
		if ($photos_screenshot === '' || typeof $photos_screenshot === 'undefined') {
			alert('Please upload Screenshot.');
//	 		$('#photos_screenshot').focus();
			document.getElementById('photos_screenshot').click();
			allValid = false;
			return false;
		}
	if ($no_of_views === '' || typeof $no_of_views === 'undefined') {
		alert('Please enter the Number of Views on the Social Media Post');
		$('#no_of_views').focus();
		allValid = false;
		return false;
	}
	if ($no_of_subscriber === '' || typeof $no_of_subscriber === 'undefined') {
		alert('Please enter the Number of Subscriber');
		$('#no_of_subscriber').focus();
		allValid = false;
		return false;
	}
	if ($no_of_likes === '' || typeof $no_of_likes === 'undefined') {
		alert('Please enter the Number of Likes');
		$('#no_of_likes').focus();
		allValid = false;
		return false;
	}
	
	if (allValid) {
		if(confirm("Do you want to Save/Update Social Media Views Details?")) {
		document.mohotsav.action="saveSocialMediaViewsDetails";
		document.mohotsav.method="post";
		document.mohotsav.submit();
		}
		return true;
	}else{
		return false;
	}
}


function editChangedata(){
	
	if (confirm("Do you want to Complete Social Media Views Details?")) {
			document.mohotsav.action = "comSocialMediaViewsDetails";
			document.mohotsav.method = "post";
			document.mohotsav.submit();
		}
	}

	function closeLargeImagePopup() {
		document.getElementById('largeImagePopup').style.display = 'none';
	}

	function showimage(file) {

		// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/wmMediaViewsScreenshot/' + file;		
		// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/wmMediaViewsScreenshot/' + file;

		//local				
		document.getElementById('largeImage').src = 'resources/images/wmMediaViewsScreenshot/'
				+ file;
		document.getElementById('largeImagePopup').style.display = 'block';
	}
</script>
<style>

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
</style>
</head>
<body>
<c:if test="${result != null}">
	<script>
	    alert("<c:out value='${result}'/>");
	</script>
</c:if>
<div class="container mt-5">

    <form:form autocomplete="off" method="post" name="mohotsav" id="mohotsav" action="saveSocialMediaViewsDetails" modelAttribute="useruploadsl" enctype="multipart/form-data">
		<input type="hidden" name="regno" id="regno" value="${regno}"/>
		<input type="hidden" name="videoid" id="videoid" value="${videoid}"/>
       	<input type="hidden" id="mediatype" name="mediatype" value="${mediatype}"/>
    	<c:if test = "${empty viewsList}">
        <div class="card shadow mt-1 p-5">
        	
            <h4 class="text-center text-primary mb-4"><u>Add the Social Media Views Details</u></h4>
			<label> <span style="color: blue;">Note:- The Image size must be under 300KB.</span></label>
			<div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <tbody>
                        <tr>
							<td>
								<c:choose>
										<c:when test="${mediatype == 'P' || mediatype == 'PB'}">
            								Upload Screenshot of Social Media Image Details 
           									 <span style="color: red;">*</span>
										</c:when>
										<c:otherwise>
            								Upload Screenshot of Social Media Video Details 
            								<span style="color: red;">*</span>
										</c:otherwise>
									</c:choose>
							</td>
									

									<td>
								<div class="photo-block" data-name="photos_screenshot">

									<label><b>Upload Screenshot</b></label>

									<div class="photoContainer">
										<div class="d-flex align-items-center mb-1">
											<input type="file" name="photos_screenshot"
												id="photos_screenshot" class="form-control photo-input"
												accept="image/*" onchange="validatePhoto(this)" required />
										</div>
									</div>
									<small class="text-danger photoError"></small>

								</div>
							</td>

						</tr>
						<tr>
							<td>Number of Views<span style="color: red;">*</span></td>
							<td colspan=2><input type="text" id="no_of_views"
								name="no_of_views" autocomplete="off" pattern="^\d{10}$"
							 oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							

						</tr>
						<tr>
							<td>Number of Subscriber<span style="color: red;">*</span></td>
							<td><input type="text" id="no_of_subscriber"
								name="no_of_subscriber" autocomplete="off" pattern="^\d{10}$"
								oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							
						</tr>
						<tr>
							<td>Number of Likes<span style="color: red;">*</span> </td>
							<td colspan=2><input type="text" id="no_of_likes" name="no_of_likes" autocomplete="off"
								pattern="^\d{10}$" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
							
						</tr>
                    </tbody>
                    
                </table>
                <div class="form-row">
					<div class="form-group col-8">
						<label for="btnGetDetails"> &nbsp;</label>
     					<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Save"/>
     				</div>
     			</div> 
            </div>
        </div>
        </c:if>
        
        <c:if test = "${not empty viewsList}">
        <div class="card shadow mt-1 p-5">
					<c:choose>
						<c:when test="${status eq 'D'}">
							<h4 class="text-center text-primary mb-4">
								<u>Draft Wise Social Media Views Details</u>
							</h4>
							<label> <span style="color: blue;">Note:- The
									Image size must be under 300KB.</span></label>
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<tbody>
										<tr>
											<td><c:choose>
													<c:when test="${mediatype == 'P' || mediatype == 'PB'}">
            								Upload Screenshot of Social Media Image Details 
           									 <span style="color: red;">*</span>
													</c:when>
													<c:otherwise>
            								Upload Screenshot of Social Media Video Details 
            								<span style="color: red;">*</span>
													</c:otherwise>
												</c:choose></td>
											<td>
												<div class="photo-block" data-name="photos_screenshot">

													<label><b>Upload Screenshot</b></label>

													<div class="photoContainer">
														<div class="d-flex align-items-center mb-1">
															<input type="file" name="photos_screenshot"	id="photos_screenshot" class="form-control photo-input"
																accept="image/*" onchange="validatePhoto(this)" required />
														</div>
													</div>
													<small class="text-danger photoError"></small>

												</div>
											</td>

										</tr>
										<tr>
											<td>Number of Views<span style="color: red;">*</span></td>
											<td colspan=2><input type="text" id="no_of_views" name="no_of_views" autocomplete="off" pattern="^\d{10}$"
												oninput="this.value=this.value.replace(/[^0-9]/g,'');" value = "${noofview}" required /></td>


										</tr>
										<tr>
											<td>Number of Subscribers<span style="color: red;">*</span></td>
											<td><input type="text" id="no_of_subscriber"
												name="no_of_subscriber" autocomplete="off" pattern="^\d{10}$"
												oninput="this.value=this.value.replace(/[^0-9]/g,'');" value = "${noofsubs}" required /></td>

										</tr>
										<tr>
											<td>Number of Likes<span style="color: red;">*</span>
											</td>
											<td colspan=2><input type="text" id="no_of_likes" name="no_of_likes" autocomplete="off" pattern="^\d{10}$"
												oninput="this.value=this.value.replace(/[^0-9]/g,'');" value = "${nooflikes}" required /></td>

										</tr>
									</tbody>

								</table>
								<div class="form-row">
									<div class="form-group col-8">
										<label for="btnGetDetails"> &nbsp;</label> <input
											type="button" class="btn btn-info" id="submitbtn"
											name="submitbtn" onclick="validation();" value="Update" />
									</div>
								</div>


									<h4 class="text-center text-primary mb-4">
										<u>Complete the Social Media Url View Details</u>
									</h4>
									<div class="table-responsive">
										<table class="table table-bordered table-striped">
											<thead>
												<tr>
													<th>Number of Views</th>
													<th>Number of Subscribers</th>
													<th>Number of Likes</th>
												<th><c:choose>
														<c:when test="${mediatype == 'P' || mediatype == 'PB'}">
            												Screenshot of Social Media Image Details 
           									 				<span style="color: red;">*</span>
														</c:when>
														<c:otherwise>
            												Screenshot of Social Media Video Details 
            												<span style="color: red;">*</span>
														</c:otherwise>
													</c:choose></th>
												<th>Action</th>
												</tr>
												<tr>
													<th>1</th>
													<th>2</th>
													<th>3</th>
													<th>4</th>
													<th>5</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td class="text-center"><c:out value="${noofview}" /></td>
													<td class="text-center"><c:out value="${noofsubs}" /></td>
													<td class="text-center"><c:out value="${nooflikes}" /></td>
													<td class="text-center"><a href="#" class="showImage"
														data-toggle="modal" style="color: blue;"
														onclick="showimage('${file}')">Screenshot</a></td>
													<td><input type="button" class="btn btn-info"
														id="complete" name="complete" value="Complete"
														onclick="editChangedata()" /></td>
												</tr>
											</tbody>
										</table>
									</div>

							</div>
						</c:when>
						<c:otherwise>
							<h4 class="text-center text-primary mb-4">
								<u>Completed Social Media Views Details</u>
							</h4>
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<tbody>
										<tr>
											<td><c:choose>
													<c:when test="${mediatype == 'P' || mediatype == 'PB'}">
            											Screenshot of Social Media Image Details 
           									 			<span style="color: red;">*</span>
													</c:when>
													<c:otherwise>
            											Screenshot of Social Media Video Details 
            											<span style="color: red;">*</span>
													</c:otherwise>
												</c:choose></td>
											<td class="text-right">
												<a href="#" class="showImage" data-toggle="modal" style ="color: blue;" onclick ="showimage('${file}')">Screenshot</a> 
											</td>

										</tr>
										<tr>
											<td>Number of Views</td>
											<td colspan=2><input type="text" id="no_of_views" name="no_of_views" autocomplete="off" pattern="^\d{10}$"
												oninput="this.value=this.value.replace(/[^0-9]/g,'');" value = "${noofview}" disabled/></td>


										</tr>
										<tr>
											<td>Number of Subscribers</td>
											<td><input type="text" id="no_of_subscriber"
												name="no_of_subscriber" autocomplete="off" pattern="^\d{10}$"
												oninput="this.value=this.value.replace(/[^0-9]/g,'');" value = "${noofsubs}" disabled/></td>

										</tr>
										<tr>
											<td>Number of Likes	</td>
											<td colspan=2><input type="text" id="no_of_likes" name="no_of_likes" autocomplete="off" pattern="^\d{10}$"
												oninput="this.value=this.value.replace(/[^0-9]/g,'');" value = "${nooflikes}" disabled/></td>

										</tr>
									</tbody>

								</table>
								
							</div>
						</c:otherwise>
					</c:choose>



				</div>
        </c:if>
   </form:form> 
   

</div>
<!-- 	<div id="imagePopup" class="popup" style="display:none;"> -->
<!-- 		<div class="popup-content"> -->
<!-- 			<span class="close" onclick="closePopup()">&times;</span> -->
<!-- 			<div id="imageList" class="image-container"></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div id="largeImagePopup" class="popup" style="display: none;">
		<div class="large-image-popup-content">
			<span class="close" onclick="closeLargeImagePopup()">&times;</span>
			<img id="largeImage" src="" alt="Large Image" />
		</div>
		
	</div>

<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>
</body>
</html>
