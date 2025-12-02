<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavheader.jspf" %>

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
    .registration-container:hover { transform: scale(1.01); }
    .registration-container .form-label { font-weight: 600; color: #0d6efd; }
    .registration-container .required { color: red; margin-left: 3px; }

    #detailsSection {
        background: #f1f8ff;
        border-radius: 10px;
        padding: 15px;
        margin-top: 15px;
    }
    .detail-label { font-weight: 600; color: #0d47a1; }
</style>

<div class="registration-container">
    <h3 class="text-center mb-4 text-primary fw-bold">
        Add Social Media Form
    </h3>

    <form id="registrationForm">

        <!-- Registration No -->
        <div class="mb-4">
            <label for="regNo" class="form-label">Registration No.<span class="required">*</span></label>
            <input type="text" class="form-control form-control-lg"
                   id="regNo" name="regNo"
                   placeholder="Enter your Registration No." autocomplete="off">

            <div class="form-text text-danger d-none" id="regNoError">
                Please enter your Registration No.
            </div>
        </div>

        <!-- Email -->
        <div class="mb-4">
            <label for="emailId" class="form-label">Email Id<span class="required">*</span></label>
            <input type="email" class="form-control form-control-lg"
                   id="emailId" name="email"
                   placeholder="Enter your Email Id" autocomplete="off">

            <div class="form-text text-danger d-none" id="emailError">
                Please enter your Email Id.
            </div>
        </div>

        <!-- Verify Button -->
        <button type="button" class="btn btn-primary w-100 mb-3" id="verifyBtn">
            Verify Registration
        </button>

        <!-- User Details -->
        <div id="detailsSection" style="display:none;">
            <div class="mb-2"><span class="detail-label">Name:</span> <span id="regName"></span></div>
            <div class="mb-2"><span class="detail-label">Phone:</span> <span id="phno"></span></div>
            <div class="mb-2"><span class="detail-label">Email:</span> <span id="email"></span></div>
            <div class="mb-2"><span class="detail-label">Address:</span> <span id="address"></span></div>

            <button type="button" class="btn btn-success w-100 mt-3" id="nextBtn">
                Proceed to Upload
            </button>
        </div>

        <!-- Message Area -->
        <div id="msgBox" class="text-center mt-4"></div>

    </form>
</div>

<script>
$(document).ready(function(){

    $("#verifyBtn").click(function(){

        let regNo = $("#regNo").val().trim();
        let email = $("#emailId").val().trim();

        // Validation
        if(regNo === ""){
            $("#regNoError").removeClass("d-none");
            return;
        } else {
            $("#regNoError").addClass("d-none");
        }

        if(email === ""){
            $("#emailError").removeClass("d-none");
            return;
        } else {
            $("#emailError").addClass("d-none");
        }

        $("#verifyBtn").prop("disabled", true).text("Verifying...");

        $.post("verifyRegistration", {
            regNo: regNo,
            email: email
        }, function(resp){

            $("#verifyBtn").prop("disabled", false).text("Verify Registration");

            if(resp === "invalid"){
                $("#msgBox").html("<span class='text-danger'>Invalid Registration No or Email!</span>");
                $("#detailsSection").hide();

            } else if(resp === "error"){
                $("#msgBox").html("<span class='text-danger'>Something went wrong. Try again.</span>");
                $("#detailsSection").hide();

            } else {

                let data = resp.split("|");

                $("#regName").text(data[0]);
                $("#phno").text(data[1]);
                $("#email").text(data[2]);
                $("#address").text(data[3]);

                $("#msgBox").html("<span class='text-success'>Registration verified successfully!</span>");
                $("#detailsSection").fadeIn(400);
                $("#verifyBtn").hide();

                $("#nextBtn").off("click").on("click", function(){
                    window.location.href = "uploadAnotherVideo?regNo=" + encodeURIComponent(regNo);
                });
            }

        }).fail(function(){
            $("#verifyBtn").prop("disabled", false).text("Verify Registration");
            $("#msgBox").html("<span class='text-danger'>not responding!</span>");
        });

    });

});
</script>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/mahotsavfooter.jspf" %>
</footer>
