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

    // Enable submit only when consent checkbox checked
    $('#consentCheck').on('change', function() {
        $('#submitBtn').prop('disabled', !this.checked);
    });

    // Submit button click
    $('#submitBtn').click(function(e) {
        e.preventDefault();

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
        const twPattern = /^(https?:\/\/)?(www\.)?twitter\.com\/.+$/i;
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
            $('#videoAlert').text("Please enter a valid Twitter URL (twitter.com/...)").show();
            valid = false;
        } else if (linkedin && !liPattern.test(linkedin)) {
            $('#videoAlert').text("Please enter a valid LinkedIn URL (linkedin.com/...)").show();
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
            <div class="mb-3"><label>1. Facebook</label><input type="url" name="facebook" class="form-control" placeholder="Enter Facebook video URL"></div>
            <div class="mb-3"><label>2. YouTube</label><input type="url" name="youtube" class="form-control" placeholder="Enter YouTube video URL"></div>
            <div class="mb-3"><label>3. Instagram</label><input type="url" name="instagram" class="form-control" placeholder="Enter Instagram video URL"></div>
            <div class="mb-3"><label>4. Twitter</label><input type="url" name="twitter" class="form-control" placeholder="Enter Twitter video URL"></div>
            <div class="mb-3"><label>5. LinkedIn</label><input type="url" name="linkedin" class="form-control" placeholder="Enter LinkedIn video URL"></div>

            <div class="form-check mb-4 mt-3">
                <input class="form-check-input" type="checkbox" id="consentCheck">
                <label class="form-check-label" for="consentCheck">
                    I give consent to DoLR / State Agency to use my pictures or reels for publicity or educational purpose. Winners give consent to agency for publicity.
                </label>
            </div>

            <div class="text-center">
                <button type="button" id="submitBtn" class="btn btn-success px-5" disabled>Submit</button>
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
