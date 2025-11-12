<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavheader.jspf" %>

<!-- Bootstrap & jQuery -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
    .registration-container {
        max-width: 550px;
        margin: 60px auto;
        background: #fff;
        border-radius: 20px;
        box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
        padding: 40px 45px;
        transition: all 0.3s ease-in-out;
    }

    .registration-container:hover {
        transform: scale(1.01);
    }

    .registration-container .form-label {
        font-weight: 600;
        color: #0d6efd;
    }

    .registration-container .required {
        color: red;
        margin-left: 3px;
    }

    .registration-container .btn-primary,
    .registration-container .btn-success {
        border-radius: 10px;
        padding: 10px 0;
        font-weight: 600;
        letter-spacing: 0.5px;
    }

    .registration-container #msgBox {
        font-size: 0.95rem;
        font-weight: 500;
    }

    .registration-container #detailsSection {
        background: #f1f8ff;
        border-radius: 10px;
        padding: 15px;
        margin-top: 15px;
    }

    .registration-container .detail-label {
        font-weight: 600;
        color: #0d47a1;
    }
</style>

<div class="registration-container">
    <h3 class="text-center mb-4 text-primary fw-bold">Add Social Media Form</h3>

    <form id="registrationForm" method="post">
        <!-- Registration No -->
        <div class="mb-4">
            <label for="name" class="form-label">Registration No.<span class="required">*</span></label>
            <input type="text" class="form-control form-control-lg" id="name" name="name" placeholder="Enter your Registration No." autocomplete="off">
            <div class="form-text text-danger d-none" id="nameError">Please enter your Registration No.</div>
        </div>

        <!-- Verify Button -->
        <button type="button" class="btn btn-primary w-100 mb-3" id="verifyBtn">
            <i class="bi bi-check-circle me-1"></i> Verify Registration
        </button>

        <!-- User Details -->
        <div id="detailsSection" class="mt-3" style="display:none;">
            <div class="mb-2"><span class="detail-label">Name:</span> <span id="regName"></span></div>
            <div class="mb-2"><span class="detail-label">Phone:</span> <span id="phno"></span></div>
            <div class="mb-2"><span class="detail-label">Email:</span> <span id="email"></span></div>
            <div class="mb-2"><span class="detail-label">Address:</span> <span id="address"></span></div>

            <button type="button" class="btn btn-success w-100 mt-3" id="nextBtn">
                <i class="bi bi-arrow-right-circle me-1"></i> Proceed to Upload
            </button>
        </div>

        <!-- Message Area -->
        <div id="msgBox" class="text-center mt-4"></div>
    </form>
</div>

<script>
$(document).ready(function(){
    // Handle Verify Registration Button Click
    $("#verifyBtn").click(function(){
        let regNo = $("#name").val().trim();

        if(regNo === ""){
            $("#nameError").removeClass("d-none");
            return;
        } else {
            $("#nameError").addClass("d-none");
        }

        // Disable button during request
        $("#verifyBtn").prop("disabled", true).text("Verifying...");

        $.post("verifyRegistration", { regNo: regNo }, function(resp){
            $("#verifyBtn").prop("disabled", false).text("Verify Registration");

            if(resp === "invalidReg"){
                $("#msgBox").html("<span class='text-danger'> Invalid Registration Number!</span>");
                $("#detailsSection").hide();
            } else if(resp === "error"){
                $("#msgBox").html("<span class='text-danger'> Something went wrong. Please try again.</span>");
                $("#detailsSection").hide();
            } else {
                // Parse user details (name|phno|email|address)
                let data = resp.split("|");
                $("#regName").text(data[0]);
                $("#phno").text(data[1]);
                $("#email").text(data[2]);
                $("#address").text(data[3]);

                $("#msgBox").html("<span class='text-success'> Registration verified successfully!</span>");
                $("#detailsSection").fadeIn(400);
                $("#verifyBtn").hide(); // ✅ Hides the Verify button after success

                // Handle Next Button → redirect with regNo
                $("#nextBtn").off("click").on("click", function(){
                    window.location.href = "uploadAnotherVideo?regNo=" + encodeURIComponent(regNo);
                });
            }
        }).fail(function(){
            $("#verifyBtn").prop("disabled", false).text("Verify Registration");
            $("#msgBox").html("<span class='text-danger'> Server not responding. Please try again later.</span>");
        });
    });
});
</script>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/mahotsavfooter.jspf" %>
</footer>
