<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src='<c:url value="/resources/js/flexfundUpdationAtSlna.js" />'></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

<title>Flexi-Fund Updation</title>

<style>
.previewImg {
    cursor: pointer !important;

.btn-group-custom {
    display: flex;
    gap: 5px;
    justify-content: center;
    align-items: center;
}

.btn-group-custom button {
    width: 35px;
    height: 35px;
    padding: 0;
    font-size: 18px;
}

}

.navBtn {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    font-size: 35px;
    background: rgba(0,0,0,0.5);
    color: #fff;
    border: none;
    padding: 10px;
    cursor: pointer;
}

.prevBtn { left: 10px; }
.nextBtn { right: 10px; }


</style>

</head>
<body>
<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Flexi-Fund Updation</h4> </div>
	 
			<div class="row">
			<div class="form-group col-3">
    <label for="state"><b>State Name:</b></label><br/>
    <c:out value="${stateName}" /><br/>
</div>

<div class="form-group col-3">
    <label for="district"><b>District Name: <span style="color:red;">*</span></b></label><br/>
  <span class="projectError"></span>
        <select class="form-control district" id="district" name="district">
            <option value="">--Select District--</option>
            <c:forEach items="${distList}" var="dist"> 
                <option value="${dist.key}"><c:out value="${dist.value}" /></option>
            </c:forEach>
        </select>
    

</div>

<div class="form-group col-3">
    <label for="projid"><b>Project Name: <span style="color:red;">*</span></b></label><br/>

     <span class="activityError"></span>
        <select class="form-control activity" id="projid" name="projid">
            <option value="">--Select Project--</option>
        </select>
</div>

<div class="form-group col-3">
    <label for="panchayat"><b>Gram Panchayat: <span style="color:red;">*</span></b></label><br/>
    <span class="panchatError"></span>
        <select class="form-control panchayat" id="panchayat" name="panchayat">
            <option value="">--Select Gram Panchayat--</option>
        </select>
</div>

<div class="form-group col">
<div id="completeSection" style="margin-top:30px;">
    <div class="form-row col-md-12">
    <label style="color: red;">Note:- From Last Expenditure to till Date</label>
    </div>
    <table class="table table-bordered">
        <thead>
            <tr>
            <th>Select</th>
                <th style="width:250px;">Activity Name</th>
                <th style="width:250px;">Details of Work Done</th>
                <th>Total Estimated Cost(As per plan)</th>
                <th>Date of Last Expenditure</th>
                <th>Expenditure till last Exp. date(Rs. in Lakhs)</th>
                <th>Expenditure from last Exp. date to till date(Rs. in Lakhs)</th>
                <th style="width:250px;">Photos</th>
                <th style="width:150px;">Remarks</th>
                <th style="width:200px;">Status</th>
                <th class="dateHeader" style="display:none;">Completion Date</th>
                
            </tr>
        </thead>
        <tbody id="completeTbody"></tbody>
    </table>
   <!--  <div class="text-center mt-3">
    <button id="saveProgress" class="btn btn-success">
        Save
    </button>
</div> -->
</div>


</div>
</div>

</div>

<div id="imgModal" class="modal fade" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">

      <div class="modal-header">
        <h5 class="modal-title">Image Preview</h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <div class="modal-body text-center position-relative">

        <button class="navBtn prevBtn">&#10094;</button>

        <img id="modalImg" style="width:100%">

        <button class="navBtn nextBtn">&#10095;</button>

      </div>

    </div>
  </div>
</div>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	
</body>
</html>
