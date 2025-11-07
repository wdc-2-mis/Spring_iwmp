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
    .btn-next {
        display: none;
        width: 100%;
    }
</style>

<div class="registration-container">
    <h4 class="text-center mb-4 text-primary">Registration Form</h4>
    
    <form id="registrationForm" action="otherMahotsav" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Full Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter your name" required>
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label">Phone Number</label>
            <input type="tel" class="form-control" id="phone" name="phone" placeholder="Enter your phone number" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email ID</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
        </div>

         <div class="mb-3">
            <label for="address" class="form-label">Address</label>
           <!--  <input type="text" class="form-control" id="address" placeholder="Enter your Address" required> -->
            <textarea id="address" class="form-control" name="userAddres" placeholder="Enter your Address" rows=3 style="height:10%" maxlength="200"></textarea>
        </div>
        <!-- Google reCAPTCHA widget -->
        <div class="mb-4 text-center">
             <div class="g-recaptcha" 
                 data-sitekey="6LfYFwQsAAAAAAVxzTr3vCHseqAtbLiD53hfgJc7" 
                 data-callback="recaptchaVerified"
                 data-expired-callback="recaptchaExpired">
            </div> 
            
        </div>

        <button type="submit" class="btn btn-primary btn-next" id="nextBtn">Next Page</button>
    </form>
</div>

<script>
function recaptchaVerified() {
    $('#nextBtn').fadeIn();
}

function recaptchaExpired() {
    $('#nextBtn').fadeOut();
}
</script>

<br>
<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>









