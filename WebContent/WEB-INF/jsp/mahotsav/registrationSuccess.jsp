<%@ page contentType="text/html;charset=UTF-8" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<div class="modal fade show" style="display:block;" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content border-success">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title">Registration Successful</h5>
      </div>
      <div class="modal-body text-center">
        <p class="fs-5">Your registration is successful!</p>
        <h4 class="text-success fw-bold">Registration No: ${regNo}. Do you want to upload another video?</h4>
      </div>
      <div class="modal-footer justify-content-center">
        <button class="btn btn-primary" onclick="window.location.href='uploadAnotherVideo?regNo=${regNo}'">Yes</button>
        <button class="btn btn-secondary" onclick="window.location.href='registerMahotsav'">No</button>
      </div>
    </div>
  </div>
</div>

