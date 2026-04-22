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
<script src='<c:url value="/resources/js/flexfundAtSlna.js" />'></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

<title>Flexi-Fund of WDCPMKSY2.0</title>

<script>
var activityList = [
    <c:forEach items="${activity}" var="act" varStatus="loop">
        {id: "${act.key}", name: "${act.value}"}${!loop.last ? ',' : ''}
    </c:forEach>
];

</script>

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



</style>
</head>

<body>
<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Flexi-Fund Details of WDCPMKSY2.0</h4> </div>
		
		<form:form autocomplete="off" method="post" name="flexifund" id="flexifund" action="saveflexifund" modelAttribute="flexifund" >
		<input type="hidden" name="status" id="status">
			  <hr/>
			  
			 
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

<!-- ================= MAIN FORM TABLE ================= -->
<div class="form-group col">
<div id="entrySection">
<table class="table table-bordered">
    <thead>
        <tr>
            <th>Activity Name <span style="color:red;">*</span></th>
            <th>Details of Work Done <span style="color:red;">*</span></th>
            <th>Total Estimated Cost(As per plan) <span style="color:red;">*</span></th>
            <th>Expenditure till date(Rs. in Lakhs)<span style="color:red;">*</span></th>
            <th style="width:250px;">Photos (Max 6)</th>
            <th>Remarks</th>
            <th>Add</th>
        </tr>
    </thead>

    <tbody id="tbodyReport">
        <tr data-row-id="0">
            <td>
                <select name="activity[]" class="form-control activityDropdown" style="width:250px;">
                    <option value="">--Select Activity--</option>
                    <c:forEach items="${activity}" var="act">
                        <option value="${act.key}">${act.value}</option>
                    </c:forEach>
                </select>
            </td>

            <td><input type="text" name="details[]" class="form-control"/></td>
            <td><input type="text" name="totalest[]" class="form-control"/></td>
            <td><input type="text" name="cost[]" class="form-control"/></td>

            <td>
                <div class="customFileWrapper">
                    <button type="button" class="btn btn-secondary browseBtn">Browse</button>
                    <span class="fileCount">No file selected</span>
                    <input type="file" name="photos" class="photoInput d-none" multiple/>
                </div>

                <div class="photoPreview"></div>
            </td>
             <td>
            <textarea id="remark" name="remark[]" autocomplete="off" rows="2" cols="22" maxlength="200"></textarea>
            </td>
            <td>
                <button type="button" class="btn btn-success addRow">+</button>
                <button type="button" class="btn btn-danger removeRow" style="display:none;">-</button>
            </td>
        </tr>
    </tbody>
</table>
<div class="text-center">
    <button type="button" id="draft" class="btn btn-primary">Draft</button>
    <!-- <button type="button" id="complete" class="btn btn-success">Complete</button> -->
</div>
</div>
<!-- ================= DRAFT DATA ================= -->
<div id="draftSection" style="display:none; margin-top:30px;">
    <div class="col formheading" style="text-decoration: underline;"><h4>Flexi-Fund Draft Details</h4> </div>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Select</th>
                <th style="width:300px;">Activity Name</th>
                <th style="width:200px;">Details of Work Done</th>
                <th>Total Estimated Cost(As per plan)</th>
                <th>Expenditure till date(Rs. in Lakhs)</th>
                <th style="width:300px;">Photos</th>
                <th>Remarks</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody id="draftTbody"></tbody>
    </table>
    <div class="text-center">
    <button type="button" id="updateDraft" class="btn btn-primary">Update</button>
    <button type="button" id="updateComplete" class="btn btn-success">Complete</button>
</div>
</div>



<!-- ================= COMPLETE DATA ================= -->
<div id="completeSection" style="display:none; margin-top:30px;">
    <div class="col formheading" style="text-decoration: underline;"><h4>Flexi-Fund Completed Details</h4> </div>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th style="width:250px;">Activity</th>
                <th style="width:250px;">Details</th>
                <th>Total Est.(Rs. in Lakhs)</th>
                <th>Expenditure(Rs. in Lakhs)</th>
                <th style="width:300px;">Photos</th>
                <th style="width:200px;">Remarks</th>
            </tr>
        </thead>
        <tbody id="completeTbody"></tbody>
    </table>
</div>


</div>   	
 


</div>
</form:form>
</div>

<div id="imgModal" class="modal fade" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">

      <div class="modal-header">
        <h5 class="modal-title">Image Preview</h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <div class="modal-body text-center">
        <img id="modalImg" style="width:100%">
      </div>

    </div>
  </div>
</div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	
 
	
	</body>
</html>
