<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavheader.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Google reCAPTCHA JS API -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<style>
    body {
        background: #f8f9fa;
        font-family: "Segoe UI", Arial, sans-serif;
    }
    .registration-container {
        max-width: 550px;
        margin: 40px auto;
        background: #fff;
        border-radius: 15px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        padding: 30px 40px;
    }
    .form-label {
        font-weight: 600;
        color: #0d6efd;
    }
    .required {
        color: red;
        margin-left: 3px;
    }
    .btn-next {
        display: none;
        width: 100%;
    }
    .error-text {
    color: red;
    font-size: 0.9rem;
    margin-top: 4px;
    display: none;
}
    
</style>

<div class="registration-container">
    <h4 class="text-center mb-4 text-primary">Registration Form</h4>
    
     <form id="registrationForm" action="registerMahotsav" method="post" novalidate>
        <!-- Full Name -->
        <div class="mb-3">
            <label for="name" class="form-label">Full Name<span class="required">*</span></label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name">
            <div class="error-text" id="nameError">Please enter your Name.</div>
        </div>

        <!-- Phone -->
        <div class="mb-3">
            <label for="phone" class="form-label">Phone Number<span class="required">*</span></label>
            <input type="tel" class="form-control" id="phone" name="phone" maxlength="10" placeholder="Enter your phone number">
            <div class="error-text" id="phoneError">Enter a valid 10-digit phone number.</div>
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label for="email" class="form-label">Email ID<span class="required">*</span></label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email">
            <div class="error-text" id="emailError">Please enter a valid email address.</div>
        </div>

        <!-- Address -->
        <div class="mb-3">
            <label for="address" class="form-label">Address<span class="required">*</span></label>
            <textarea id="address" class="form-control" name="userAddres" placeholder="Enter your address" rows="3" maxlength="200"></textarea>
            <div class="error-text" id="addressError">Please enter your address.</div>
        </div>

        <!-- reCAPTCHA -->
        <div class="mb-4 text-center">
            <div class="g-recaptcha" 
                 data-sitekey="6LfYFwQsAAAAAAVxzTr3vCHseqAtbLiD53hfgJc7" 
                 data-callback="recaptchaVerified"
                 data-expired-callback="recaptchaExpired"></div>
            <div class="error-text text-center" id="captchaError">Please verify you are not a robot.</div>
        </div>

        <button type="submit" class="btn btn-primary btn-next" id="nextBtn">Next Page</button>
    </form>

</div>

<script>
function recaptchaVerified() {
    $('#nextBtn').fadeIn();
    $('#captchaError').hide();
}

function recaptchaExpired() {
    $('#nextBtn').fadeOut();
}

$('#registrationForm').on('submit', function(e) {
    let isValid = true;

    $('.error-text').hide();

    let name = $('#name').val().trim();
    let phone = $('#phone').val().trim();
    let email = $('#email').val().trim();
    let address = $('#address').val().trim();
    let captcha = grecaptcha.getResponse();

     if (name === "") {
        $('#nameError').show();
        isValid = false;
    }

    if (!/^[0-9]{10}$/.test(phone)) {
        $('#phoneError').show();
        isValid = false;
    }

    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        $('#emailError').show();
        isValid = false;
    }

    if (address === "") {
        $('#addressError').show();
        isValid = false;
    }

    if (captcha === "") {
        $('#captchaError').show();
        isValid = false;
    }

    if (!isValid) {
        e.preventDefault();
    }
});

</script>

<br>
<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>









