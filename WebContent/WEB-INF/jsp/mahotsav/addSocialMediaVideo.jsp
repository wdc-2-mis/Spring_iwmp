<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavheader.jspf" %>

<!DOCTYPE html>
<html>
<head>
<title>Add Social Media Form</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f4f6f9;
        margin: 0;
        padding: 0;
    }

    .registration-container {
        max-width: 500px;
        margin: 60px auto;
        background: #fff;
        border-radius: 15px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        padding: 35px;
    }

    h3 {
        text-align: center;
        color: #0d6efd;
        margin-bottom: 30px;
    }

    label {
        font-weight: bold;
        color: #333;
    }

    .required {
        color: red;
    }

    input {
        width: 100%;
        padding: 12px;
        font-size: 16px;
        margin-top: 6px;
        margin-bottom: 8px;
        border-radius: 6px;
        border: 1px solid #ccc;
    }

    input:readonly {
        background: #eee;
    }

    button {
        width: 100%;
        padding: 12px;
        font-size: 16px;
        border-radius: 6px;
        border: none;
        cursor: pointer;
        margin-top: 10px;
    }

button.btn-inline {
    width: auto;
    padding: 6px 12px;
    font-size: 14px;
    margin-top: 0;
}

    .btn-primary {
        background: #0d6efd;
        color: white;
    }

    .btn-warning {
        background: #f0ad4e;
        color: white;
    }

    button:disabled {
        background: #999;
        cursor: not-allowed;
    }

    .text-danger { color: red; }
    .text-success { color: green; }
    .text-info { color: blue; }

    .form-text {
        font-size: 14px;
        display: none;
    }

    #otpSection {
        margin-top: 25px;
        display: none;
    }

    a {
        text-decoration: none;
        color: #0d6efd;
        font-size: 14px;
    }

    .text-center {
        text-align: center;
    }
    
    
    
.modal {
  display: none; /* Hidden by default */
  position: fixed;
  z-index: 1000;
  left: 0; top: 0;
  background-color: rgba(0,0,0,0.6);
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 100%;
  max-width: 90%;
  position: relative;
}

.close {
  position: absolute;
  top: 8px;
  right: 12px;
  color: #666;
  font-size: 25px;   /* smaller size */
  font-weight: normal;
  cursor: pointer;
  background: #f0f0f0;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  line-height: 26px;
  text-align: center;
  transition: background 0.3s, color 0.3s;
}

.close:hover {
  background: #ddd;
  color: #000;
}

    
</style>

</head>
<body>

<div class="registration-container">
    <h3>Submit your Review for your Social Media Competition</h3>

    <form id="registrationForm">

        <!-- Registration No -->
        <label>Registration No <span class="required">*</span></label>
        <input type="text" id="regNo" placeholder="Enter Registration No" autocomplete="off">
        <div id="regNoError" class="form-text text-danger">Please enter Registration No</div>

        <!-- Email -->
        <label>Email Id <span class="required">*</span></label>
        <input type="email" id="emailId" placeholder="Enter Email Id" autocomplete="off">
        <div id="emailError" class="form-text text-danger">Please enter Email Id</div>

        <button type="button" class="btn-primary" id="signInBtn">Sign In</button>

        <!-- OTP Section -->
        <div id="otpSection">
            <label>Enter OTP <span class="required">*</span></label>
            <input type="text" id="otpInput" placeholder="Enter OTP" autocomplete="off">

            <button type="button" class="btn-warning" id="verifyOtpBtn">Verify OTP</button>

            <div class="text-center" style="margin-top:10px;">
                <a href="#" id="resendOtp">Resend OTP</a>
            </div>

            <div id="otpMsg" class="text-center" style="margin-top:15px;"></div>
        </div>

        <div id="msgBox" class="text-center" style="margin-top:20px;"></div>.

</form>
</div>



	
<!-- Help Button Section -->
<div class="mt-3" style="text-align:center;">
  <h4 style="display:inline; font-style:italic; margin-right:8px;">
    How to Submit Your Review:
  </h4>
  <button type="button" id="btnHelpVideo" 
          class="btn btn-info btn-inline">
    Watch Video
  </button>
</div>

<br>

<!-- Popup Modal -->
<div id="modalHelpVideo" class="modal" role="dialog" aria-modal="true">
  <div class="modal-content">
    <button class="close" id="btnCloseModal" aria-label="Close">&times;</button>
    <h2><b>Step-by-Step Tutorial</b></h2>
    <video controls preload="metadata" style="width:100%; height:auto; max-height:85vh;">
      <source src="${pageContext.request.contextPath}/resources/video/Reviews.mp4" type="video/mp4">
      Your browser does not support the video tag.
    </video>
  </div>
</div>
		


<script>
window.onload = function () {

    // ⏳ Allowed window: 30 Jan 2026 12:00 AM IST → 31 Jan 2026 12:00 AM IST
    var allowedFrom = new Date("2026-01-30T00:00:00+05:30");
    var allowedUntil = new Date("2026-01-31T00:00:00+05:30");
    var now = new Date();

    if (now < allowedFrom) {
        // Before opening
        var msg = document.createElement("div");
        msg.innerHTML = "<b style='color:red;'>This form will be active from 30 January 2026, 12:00 AM</b>";
        msg.style.textAlign = "center";
        msg.style.marginBottom = "20px";

        document.querySelector(".registration-container")
                .insertBefore(msg, document.querySelector("form"));

        disableForm();
    } else if (now >= allowedUntil) {
        // After closing
        var msg = document.createElement("div");
        msg.innerHTML = "<b style='color:red;'>Entries are now closed. Thank you for your participation</b>";
        msg.style.textAlign = "center";
        msg.style.marginBottom = "20px";

        document.querySelector(".registration-container")
                .insertBefore(msg, document.querySelector("form"));

        disableForm();
    }

    // 🔙 no-back logic
    if (!sessionStorage.getItem("noBack")) {
        sessionStorage.setItem("noBack", "true");
    }
    history.pushState(null, null, location.href);
    window.onpopstate = function () {
        history.go(1);
    };
};

// Helper to disable inputs/buttons/links
function disableForm() {
    document.querySelectorAll("input").forEach(el => {
        el.readOnly = true;
    });

    document.querySelectorAll("button").forEach(btn => {
        btn.disabled = true;
    });

    var resend = document.getElementById("resendOtp");
    if(resend) {
        resend.style.pointerEvents = "none";
        resend.style.color = "#999";
    }
}
</script>



<script>
function $(id){ return document.getElementById(id); }

// SIGN IN
$("signInBtn").onclick = function(){

    $("msgBox").innerHTML = "";

    let regNo = $("regNo").value.trim();
    let email = $("emailId").value.trim();

    if(regNo === ""){
        $("regNoError").style.display = "block";
        return;
    } else $("regNoError").style.display = "none";

    if(email === ""){
        $("emailError").style.display = "block";
        return;
    } else $("emailError").style.display = "none";

    this.disabled = true;
    this.innerText = "Verifying...";

    fetch("verifyRegistration", {
        method: "POST",
        headers: {"Content-Type":"application/x-www-form-urlencoded"},
        body: "regNo=" + encodeURIComponent(regNo) + "&email=" + encodeURIComponent(email)
    })
    .then(res => res.text())
    .then(resp => {

    $("signInBtn").disabled = false;
    $("signInBtn").innerText = "Sign In";

    if(resp === "NOT_ALLOWED"){
        $("msgBox").innerHTML =
          "<span class='text-danger'>⏳ Form will be active from 30 January 2026, 12:00 AM</span>";
        return;
    }

    if(resp === "invalid" || resp === "invalidReg"){
        $("msgBox").innerHTML =
          "<span class='text-danger'>Invalid Registration No or Email</span>";
        return;
    }

    $("regNo").readOnly = true;
    $("emailId").readOnly = true;
    $("signInBtn").style.display = "none";
    $("otpSection").style.display = "block";

    sendOtp();
})

    .catch(() => {
        $("signInBtn").disabled = false;
        $("signInBtn").innerText = "Sign In";
        $("msgBox").innerHTML = "<span class='text-danger'>Server not responding</span>";
    });
};

// SEND OTP
function sendOtp(){
    $("otpMsg").innerHTML = "<span class='text-info'>Sending OTP...</span>";

    fetch("sendOtp", { method:"POST" })
    .then(res => res.json())
    .then(res => {
        if(res.status === "SENT")
            $("otpMsg").innerHTML = "<span class='text-success'>OTP sent to your email</span>";
        else
            $("otpMsg").innerHTML = "<span class='text-danger'>Failed to send OTP</span>";
    })
    .catch(() => {
        $("otpMsg").innerHTML = "<span class='text-danger'>OTP server error</span>";
    });
}

// VERIFY OTP
$("verifyOtpBtn").onclick = function(){

    let otp = $("otpInput").value.trim();

    if(otp === ""){
        $("otpMsg").innerHTML = "<span class='text-danger'>Please enter OTP</span>";
        return;
    }

    $("otpMsg").innerHTML = "<span class='text-info'>Verifying OTP...</span>";

    fetch("verifyOtp", {
        method:"POST",
        headers:{"Content-Type":"application/x-www-form-urlencoded"},
        body:"otp=" + encodeURIComponent(otp)
    })
    .then(res => res.json())
    .then(res => {
        if(res.status === "VERIFIED"){
            $("otpMsg").innerHTML = "<span class='text-success'>OTP Verified</span>";
            setTimeout(() => {
            	var form = document.createElement("form");
                form.method = "POST";
                form.action = "viewWMMediaUrlDetails";

                var input = document.createElement("input");
                input.type = "hidden";
                input.name = "regno";
                input.value = $("regNo").value;   

                form.appendChild(input);
                document.body.appendChild(form);

                form.submit();

            }, 800);
        } else {
            $("otpMsg").innerHTML = "<span class='text-danger'>Invalid OTP</span>";
        }
    })
    .catch(() => {
        $("otpMsg").innerHTML = "<span class='text-danger'>OTP verification failed</span>";
    });
};

// RESEND OTP
$("resendOtp").onclick = function(e){
    e.preventDefault();
    sendOtp();
};


</script>


<script>
document.getElementById("btnHelpVideo").onclick = function() {
  var modal = document.getElementById("modalHelpVideo");
  modal.style.display = "flex"; 
  var video = modal.querySelector("video");
  video.play();
};

document.getElementById("btnCloseModal").onclick = function() {
  var modal = document.getElementById("modalHelpVideo");
  modal.style.display = "none";
  modal.querySelector("video").pause();
};

window.onclick = function(event) {
  var modal = document.getElementById("modalHelpVideo");
  if (event.target === modal) {
    modal.style.display = "none";
    modal.querySelector("video").pause();
  }
};
</script>


<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/mahotsavfooter.jspf" %>
</footer>

</body>


</html>
