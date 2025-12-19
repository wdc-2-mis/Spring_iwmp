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
		if(confirm("Do you want to save Social Media Views Details?")) {
		document.mohotsav.action="saveSocialMediaViewsDetails";
		document.mohotsav.method="post";
		document.mohotsav.submit();
		}
		return true;
	}else{
		return false;
	}
}
   
   
</script>
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
    
        <div class="card shadow mt-1 p-5">
        
            <h4 class="text-center text-primary mb-4"><u>Add the Social Media Views Details</u></h4>
			<label> <span style="color: blue;">Note:- The Image size must be under 300KB.</span></label>
			<div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <tbody>
                        <tr>
							<td>Upload Screenshot of Social Media Photos/Videos Details <span style="color: red;">*</span></td>
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
   </form:form> 

</div>

<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>
</body>
</html>
