<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavheader.jspf"%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
    .required { color: red; margin-left: 3px; }
    .error-text { color: red; font-size: 0.85em; display: none; margin-top: 4px; }
    body {
        background: #f8f9fa;
        font-family: "Segoe UI", Arial, sans-serif;
    }
    .form-container {
        max-width: 650px;
        margin: 40px auto;
        background: #fff;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }
    .form-label {
        font-weight: 600;
        color: #0d6efd;
    }
</style>

<script type="text/javascript">
function showChangedata(){
    document.mohotsav.action="registerMahotsav";
    document.mohotsav.method="post";
    document.mohotsav.submit();
}

$(document).ready(function() {

    // Reset dependent dropdowns
    $('#state').on('change', function() {
        $('#district').html('<option value="">--Select District--</option>');
        $('#block').html('<option value="">--Select Block--</option>');
        $('#village').html('<option value="">--Select Village--</option>');
        showChangedata();
    });

    $('#district').on('change', function() {
        $('#block').html('<option value="">--Select Block--</option>');
        $('#village').html('<option value="">--Select Village--</option>');
        showChangedata();
    });

    $('#block').on('change', function() {
        $('#village').html('<option value="">--Select Village--</option>');
        showChangedata();
    });

    let fbValid = true;
    $('#facebook').on('blur', function() {
	    let facebook = $('#facebook').val().trim();
        $('#fbError').hide();
        fbValid = true;
        
        if (facebook === "") {
            // blank Facebook is allowed
            fbValid = true;
            return;
        }
        
        const fbPattern = /^(https?:\/\/)?(www\.)?facebook\.com\/.+$/i;
        if (!fbPattern.test(facebook)) {
            $('#fbError')
                .text("Please enter a valid Facebook URL (facebook.com/...)")
                .show();
            fbValid = false;
            return;
        }
        
        $.ajax({
            type: "POST",
            url: "checkmediaExists",
            data: { media: facebook },
            async: false, // synchronous check before allowing form submission
            success: function(response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#fbError')
                        .text("This Facebook video is already registered. Kindly use a different one.")
                        .show();
                    fbValid = false;
                }
            },
            error: function() {
                console.error("Error while checking Facebook URL");
                fbValid = false;
            }
        });
    });
    
    let ytValid = true;
    $('#youtube').on('blur', function() {
	    let youtube = $('#youtube').val().trim();
        $('#ytError').hide();
        ytValid = true;
        
        if (youtube === "") {
            // blank Facebook is allowed
            ytValid = true;
            return;
        }
        
        const ytPattern = /^(https?:\/\/)?(www\.)?youtube\.com\/.+$/i;
        if (!ytPattern.test(youtube)) {
            $('#ytError')
                .text("Please enter a valid Youtube URL (youtube.com/...)")
                .show();
            ytValid = false;
            return;
        }

        $.ajax({
            type: "POST",
            url: "checkmediaExists",
            data: { media: youtube },
            async: false, // keeps synchronous (blocks until done)
            success: function (response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#ytError')
                        .text("This video  is already registered. Kindly use a different one.")
                        .show();
                    
                    ytValid = false;
                } 
            },
            error: function () {
                console.error("Error while checking url");
                ytValid = false;
            }
        });
        
	});
    
    let igValid = true;
    $('#instagram').on('blur', function() {
	    let instagram = $('#instagram').val().trim();
        $('#igError').hide();
        igValid = true;
        
        if (instagram === "") {
            // blank Facebook is allowed
            igValid = true;
            return;
        }
        
        const igPattern = /^(https?:\/\/)?(www\.)?instagram\.com\/.+$/i;
        if (!igPattern.test(instagram)) {
            $('#igError')
                .text("Please enter a valid Instagram URL (instagram.com/...)")
                .show();
            igValid = false;
            return;
        }

        $.ajax({
            type: "POST",
            url: "checkmediaExists",
            data: { media: instagram },
            async: false, // keeps synchronous (blocks until done)
            success: function (response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#igError')
                        .text("This video  is already registered. Kindly use a different one.")
                        .show();
                    
                    igValid = false;
                } 
            },
            error: function () {
                console.error("Error while checking url");
                igValid = false;
            }
        });
        
	});
    
    let xValid = true;
    $('#twitter').on('blur', function() {
	    let twitter = $('#twitter').val().trim();
        $('#xError').hide();
        xValid = true;
        
        if (twitter === "") {
            // blank Facebook is allowed
            xValid = true;
            return;
        }
        
        const xPattern = /^(https?:\/\/)?(www\.)?x\.com\/.+$/i;
        if (!xPattern.test(twitter)) {
            $('#xError')
                .text("Please enter a valid X URL (x.com/...)")
                .show();
            xValid = false;
            return;
        }

        $.ajax({
            type: "POST",
            url: "checkmediaExists",
            data: { media: twitter },
            async: false, // keeps synchronous (blocks until done)
            success: function (response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#xError')
                        .text("This video  is already registered. Kindly use a different one.")
                        .show();
                    
                    xValid = false;
                } 
            },
            error: function () {
                console.error("Error while checking url");
                xValid = false;
            }
        });
        
	});
    
    let liValid = true;
    $('#linkedin').on('blur', function() {
	    let linkedin = $('#linkedin').val().trim();
        $('#liError').hide();
        liValid = true;
        
        if (linkedin === "") {
            // blank Facebook is allowed
            liValid = true;
            return;
        }
        
        const liPattern = /^(https?:\/\/)?(www\.)?linkedin\.com\/.+$/i;
        if (!liPattern.test(linkedin)) {
            $('#liError')
                .text("Please enter a valid linkedin URL (linkedin.com/...)")
                .show();
            liValid = false;
            return;
        }

        $.ajax({
            type: "POST",
            url: "checkmediaExists",
            data: { media: linkedin },
            async: false, // keeps synchronous (blocks until done)
            success: function (response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#liError')
                        .text("This video  is already registered. Kindly use a different one.")
                        .show();
                    
                    liValid = false;
                } 
            },
            error: function () {
                console.error("Error while checking url");
                liValid = false;
            }
        });
        
	});
    // Enable submit only when consent checkbox checked
    /* $('#consentCheck').on('change', function() {
        $('#submitBtn').prop('disabled', !this.checked);
    }); */

    // Submit button click
    $('#submitBtn').click(function(e) {
        e.preventDefault();

        if (!$('#consentCheck').is(':checked')) {
            alert("Please check the consent checkbox before submitting.");
            return; // Stop form submission
        }
        
        let valid = true;
        $('.error-text').hide();

        // Dropdown validations
        if (!$('#state').val()) { $('#stateError').show(); valid = false; }
        if (!$('#district').val()) { $('#districtError').show(); valid = false; }
        if (!$('#block').val()) { $('#blockError').show(); valid = false; }
        if (!$('#village').val()) { $('#villageError').show(); valid = false; }

        const facebook = $('input[name="facebook"]').val().trim();
        const youtube = $('input[name="youtube"]').val().trim();
        const instagram = $('input[name="instagram"]').val().trim();
        const twitter = $('input[name="twitter"]').val().trim();
        const linkedin = $('input[name="linkedin"]').val().trim();

        // URL patterns for each platform
        const fbPattern = /^(https?:\/\/)?(www\.)?facebook\.com\/.+$/i;
        const ytPattern = /^(https?:\/\/)?(www\.)?(youtube\.com|youtu\.be)\/.+$/i;
        const igPattern = /^(https?:\/\/)?(www\.)?instagram\.com\/.+$/i;
        const twPattern = /^(https?:\/\/)?(www\.)?x\.com\/.+$/i;
        const liPattern = /^(https?:\/\/)?(www\.)?linkedin\.com\/.+$/i;

        // At least one URL required
        if (!facebook && !youtube && !instagram && !twitter && !linkedin) {
            $('#videoAlert').text("Please enter at least one social media video URL").show();
            valid = false;
        }

        // Domain-specific validation
        if (facebook && !fbPattern.test(facebook)) {
            $('#videoAlert').text("Please enter a valid Facebook URL (facebook.com/...)").show();
            valid = false;
        } else if (youtube && !ytPattern.test(youtube)) {
            $('#videoAlert').text("Please enter a valid YouTube URL (youtube.com/...)").show();
            valid = false;
        } else if (instagram && !igPattern.test(instagram)) {
            $('#videoAlert').text("Please enter a valid Instagram URL (instagram.com/...)").show();
            valid = false;
        } else if (twitter && !twPattern.test(twitter)) {
            $('#videoAlert').text("Please enter a valid Twitter URL (x.com/...)").show();
            valid = false;
        } else if (linkedin && !liPattern.test(linkedin)) {
            $('#videoAlert').text("Please enter a valid LinkedIn URL (linkedin.com/...)").show();
            valid = false;
        }
        
        if (!fbValid) {
            $('#fbError').show();
            valid = false;
        }
        
        if(!ytValid){
          $('#ytError').show();
            valid = false;
        }
         if (!igValid) {
            $('#igError').show();
            valid = false;
        }
       
        
        if (!xValid) {
            $('#xError').show();
            valid = false;
        }
       
        
        if (!liValid) {
            $('#liError').show();
            valid = false;
        }
       
        // Show modal if valid
        if (valid) {
            $('#confirmationModal').modal('show');
        }
    });

   $('#yesBtn').click(function() {
        $('#confirmationModal').modal('hide');
        document.mohotsav.action = "submitRegistration";
        document.mohotsav.method = "post";
        document.mohotsav.submit();
    });

    $('#noBtn').click(function() {
         $('#confirmationModal').modal('hide');
    });

});
</script>

<div class="container mt-5">
    <div class="card shadow p-4">
        <h4 class="text-center text-primary mb-4">Content Description (Reels / Photography)</h4>

        <form name="mohotsav" id="videoForm" action="submitRegistration" method="post">
            <input type="hidden" name="name" value="${name}">
            <input type="hidden" name="phone" value="${phone}">
            <input type="hidden" name="email" value="${email}">
            <input type="hidden" name="address" value="${address}">
            <input type="hidden" name="regNo" value="${regNo}">

            <div class="row mb-3">
                <div class="col-md-4">
                    <label>State <span class="required">*</span></label>
                    <select name="state" id="state" class="form-select">
                        <option value="">--Select State--</option>
                        <c:forEach items="${stateList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq state ? 'selected' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                    <div id="stateError" class="error-text">Please select state</div>
                </div>

                <div class="col-md-4">
                    <label>District <span class="required">*</span></label>
                    <select name="district" id="district" class="form-select">
                        <option value="">--Select District--</option>
                        <c:forEach items="${districtList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq district ? 'selected' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                    <div id="districtError" class="error-text">Please select district</div>
                </div>

                <div class="col-md-4">
                    <label>Block <span class="required">*</span></label>
                    <select name="block" id="block" class="form-select">
                        <option value="">--Select Block--</option>
                        <c:forEach items="${blockList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq blkd ? 'selected' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                    <div id="blockError" class="error-text">Please select block</div>
                </div>

                <div class="col-md-4 mt-3">
                    <label>Village <span class="required">*</span></label>
                    <select name="village" id="village" class="form-select">
                        <option value="">--Select Village--</option>
                        <c:forEach items="${villageList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq vlg ? 'selected' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                    <div id="villageError" class="error-text">Please select village</div>
                </div>

                <div class="col-md-4 mt-3">
                    <label>Longitude</label>
                    <input type="text" name="longitude" class="form-control" placeholder="Enter Longitude">
                </div>

                <div class="col-md-4 mt-3">
                    <label>Latitude</label>
                    <input type="text" name="latitude" class="form-control" placeholder="Enter Latitude">
                </div>
            </div>

            <h4 class="text-center text-primary mb-3">List of URL's</h4>
            <div id="videoAlert" class="error-text text-left"></div>
            <div class="mb-3"><label>1. Facebook </label><div id="fbError" class="error-text text-left"></div><input type="url" name="facebook" id="facebook" class="form-control" placeholder="Enter Facebook video URL" autocomplete="off"></div>
            <div class="mb-3"><label>2. YouTube</label><div id="ytError" class="error-text text-left"></div><input type="url" name="youtube" id = "youtube" class="form-control" placeholder="Enter YouTube video URL" autocomplete="off"></div>
            <div class="mb-3"><label>3. Instagram</label><div id="igError" class="error-text text-left"></div><input type="url" name="instagram" id="instagram" class="form-control" placeholder="Enter Instagram video URL" autocomplete="off"></div>
            <div class="mb-3"><label>4. Twitter</label><div id="xError" class="error-text text-left"></div><input type="url" name="twitter" id="twitter" class="form-control" placeholder="Enter X video URL" autocomplete="off"></div>
            <div class="mb-3"><label>5. LinkedIn</label><div id="liError" class="error-text text-left"></div><input type="url" name="linkedin" id="linkedin" class="form-control" placeholder="Enter LinkedIn video URL" autocomplete="off"></div>

            <div class="form-check mb-4 mt-3">
                <input class="form-check-input" type="checkbox" id="consentCheck">
                <label class="form-check-label" for="consentCheck">
                    I give consent to DoLR / State Agency to use my pictures or reels for publicity or educational purpose. Winners give consent to agency for publicity.
                </label>
            </div>

            <div class="text-center">
                <button type="button" id="submitBtn" class="btn btn-success px-5">Submit</button>
            </div>
        </form>
    </div>
</div>

<!-- Confirmation Modal -->
 <div class="modal fade" id="confirmationModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title">Confirmation</h5>
      </div>
      <div class="modal-body text-center">
        <p>Do you want to Save?</p>
      </div>
      <div class="modal-footer justify-content-center">
        <button type="button" class="btn btn-success" id="yesBtn">Yes</button>
        <button type="button" class="btn btn-secondary" id="noBtn">No</button>
      </div>
      
      
    </div>
  </div>
</div> 

<br>
<footer class="text-center">
    <%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>
