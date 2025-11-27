<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/mahotsavheader.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

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
   .friendly-link {
    color: #0d6efd;             /* Bootstrap primary blue */
    text-decoration: none;
    font-weight: 500;
}
.friendly-link:hover {
    color: #0a58ca;             /* Slightly darker on hover */
    text-decoration: underline;
} 
</style>

<div class="registration-container">
    <h4 class="text-center mb-4 text-primary">Registration Form</h4>
    
     <form id="registrationForm" action="registerMahotsav" method="post" novalidate>
     <input type="hidden" name="regNo" value="">
        <!-- Full Name -->
        <div class="mb-3">
            <label for="name" class="form-label">Full Name<span class="required">*</span></label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name" autocomplete="off">
            <div class="error-text" id="nameError">Please enter your Name.</div>
        </div>

        <!-- Phone -->
        <div class="mb-3">
            <label for="phone" class="form-label">Phone Number<span class="required">*</span></label>
            <input type="text" class="form-control" id="phone" name="phone" maxlength="10" placeholder="Enter your phone number" autocomplete="off">
            <div class="error-text" id="phoneError">Enter a valid 10-digit phone number.</div>
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label for="email" class="form-label">Email ID<span class="required">*</span></label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" autocomplete="off">
            <div class="error-text" id="emailError">Please enter a valid email address.</div>
        </div>
        
    
        <!-- Address -->
        <div class="mb-3">
            <label for="address" class="form-label">Address<span class="required">*</span></label>
            <textarea id="address" class="form-control" name="address" placeholder="Enter your address" rows="3" maxlength="200"></textarea>
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
        
      <div class="text-left mt-3">
    Already registered? 
    <a href="addWMSocialMedia" class="friendly-link">Click here</a>
</div>

        
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

let emailValid = false;
let phoneValid = false;
let lastCheckedEmail = "";
let lastCheckedphone = "";

$(document).ready(function() {

	$('#phone').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, ''); // removes non-numeric characters
    });

     $('#name').on('input', function() {
        this.value = this.value.replace(/\s{2,}/g, ' ');
    });

    $('#name').on('blur', function() {
        let name = $(this).val();
        if (name.startsWith(' ')) {
            $(this).val(name.trimStart()); // remove leading spaces
        }
    });
	
	
	$('#phone').on('blur', function() {
	    let phone = $('#phone').val().trim();
        $('#phoneError').hide();
        phoneValid = false;
        
        if (phone === "") {
            $('#phoneError').text("Enter a valid 10-digit phone number.").show();
            phoneValid = false;
          return;
        }
        
        if (phone === lastCheckedphone && phoneValid) return;
        lastCheckedphone = phone;
          
        $.ajax({
            type: "POST",
            url: "checkPhoneExists",
            data: { phone: phone },
            async: false, // keeps synchronous (blocks until done)
            success: function (response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#phoneError')
                        .text("This phone no is already registered. Kindly use a different one.")
                        .show();
                    phoneValid = false;
                } else {
                    $('#phoneError').hide();
                    phoneValid = true;
                }
            },
            error: function () {
                console.error("Error while checking phone");
                phoneValid = false;
            }
        });
        
	});
    $('#email').on('blur', function() {
        let email = $('#email').val().trim();
        $('#emailError').hide();
        emailValid = false;
        
        if (email === "") {
            $('#emailError').text("Please enter your email address.").show();
            emailValid = false;
          return;
        }

       let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            $('#emailError').text("Please enter a valid email address.").show();
            emailValid = false;
          return;
        }

        if (email === lastCheckedEmail && emailValid) return;
        lastCheckedEmail = email;

        $.ajax({
            type: "POST",
            url: "checkEmailExists",
            data: { email: email },
            async: false, // keeps synchronous (blocks until done)
            success: function (response) {
                console.log("Response:", response);
                if (response.trim() === "exists") {
                    $('#emailError')
                        .text("This email is already registered. Kindly use a different one.")
                        .show();
                    emailValid = false;
                } else {
                    $('#emailError').hide();
                    emailValid = true;
                }
            },
            error: function () {
                console.error("Error while checking email");
                emailValid = false;
            }
        });
    });


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
        if (!phoneValid) {
            $('#phoneError').show();
            isValid = false;
        }
        if (!emailValid) {
            $('#emailError').show();
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault();
        }
    });
});
</script>



<br>
<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>









