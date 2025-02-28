<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/phystyle.css" />">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<%@ page import="app.watershedyatra.bean.PreYatraPreparationBean" %>
<head>
<style>
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        padding-top: 100px;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.8);
    }
    .modal-content {
        margin: auto;
        display: block;
        width: 80%;
        max-width: 600px;
    }
    .close {
        position: absolute;
        top: 15px;
        right: 25px;
        color: white;
        font-size: 35px;
        font-weight: bold;
        cursor: pointer;
    }
</style>

<script>
    function showImage(src) {
        if (!src) {
            alert("No Image Available");
            return;
        }
        document.getElementById("popupImage").src = src;
        document.getElementById("imageModal").style.display = "block";
    }

    function closeImage() {
        document.getElementById("imageModal").style.display = "none";
    }
</script>

<script type="text/javascript">

let allValid = true;
function validation() 
{
	
allValid = true;
$district = $('#district option:selected').val();
$block = $('#block option:selected').val();
$grampan = $('#grampan option:selected').val();
$date = $('#date').val();
$gramphoto1 = $('#gramphoto1').val();
$gramphoto2 = $('#gramphoto2').val();
$district1 = $('#district1 option:selected').val();
$block1 = $('#block1 option:selected').val();
$grampan1 = $('#grampan1 option:selected').val();
$village1 = $('#village1 option:selected').val();
$date1 = $('#date1').val();
$pheriphoto1 = $('#pheriphoto1').val();
$pheriphoto2 = $('#pheriphoto2').val();
$gramsabha_participants = $('#gramsabha_participants').val();
$prabhatpheri_participants = $('#prabhatpheri_participants').val();


if ($('#district option:selected').val() === '' || typeof $('#district option:selected').val() === 'undefined') {
	alert('Please select District');
	$('#district').focus();
	allValid = false;
	return false;
}
if ($block === '' || typeof $block === 'undefined') {
	alert('Please select Block');
	$('#block').focus();
	allValid = false;
	return false;
}
if ($grampan === '' || typeof $grampan === 'undefined') {
	alert('Please select Gram Panchayat');
	$('#grampan').focus();
	allValid = false;
	return false;
}
if ($date === '' || typeof $date === 'undefined') {
	alert('Please select a Date');
	$('#date').focus();
	allValid = false;
	return false;
}
if ($gramsabha_participants === '' || typeof $gramsabha_participants === 'undefined') {
	alert('Please enter the Total Number of Participants');
	$('#gramsabha_participants').focus();
	allValid = false;
	return false;
}
if ($gramphoto1 === '' || typeof $gramphoto1 === 'undefined') {
	alert('Please upload the photo of Gram Sabha Photo1');
	//$('#arExperiencephoto2').focus();
	document.getElementById('gramphoto1').click();
	allValid = false;
	return false;
}
if ($gramphoto2 === '' || typeof $gramphoto2 === 'undefined') {
	alert('Please upload the photo of Gram Sabha Photo2');
	//$('#arExperiencephoto2').focus();
	document.getElementById('gramphoto2').click();
	allValid = false;
	return false;
}




if ($('#district1 option:selected').val() === '' || typeof $('#district1 option:selected').val() === 'undefined') {
	alert('Please select Prabhat Pheri District');
	$('#district1').focus();
	allValid = false;
	return false;
}
if ($block1 === '' || typeof $block1 === 'undefined') {
	alert('Please select Prabhat Pheri Block');
	$('#block1').focus();
	allValid = false;
	return false;
}
if ($grampan1 === '' || typeof $grampan1 === 'undefined') {
	alert('Please select Prabhat Pheri Gram Panchayat');
	$('#grampan1').focus();
	allValid = false;
	return false;
}
if ($village1 === '' || typeof $village1 === 'undefined') {
	alert('Please select Prabhat Pheri Village');
	$('#village1').focus();
	allValid = false;
	return false;
}
if ($date1 === '' || typeof $date1 === 'undefined') {
	alert('Please select Prabhat Pheri Date');
	$('#date1').focus();
	allValid = false;
	return false;
}
if ($prabhatpheri_participants === '' || typeof $prabhatpheri_participants === 'undefined') {
	alert('Please enter the Total Number of Participants');
	$('#prabhatpheri_participants').focus();
	allValid = false;
	return false;
}
if ($pheriphoto1 === '' || typeof $pheriphoto1 === 'undefined') {
	alert('Please upload the photo of Prabhat Pheri Photo1');
	//$('#arExperiencephoto2').focus();
	document.getElementById('pheriphoto1').click();
	allValid = false;
	return false;
}
if ($pheriphoto2 === '' || typeof $pheriphoto2 === 'undefined') {
	alert('Please upload the photo of Prabhat Pheri Photo2');
	//$('#arExperiencephoto2').focus();
	document.getElementById('pheriphoto2').click();
	allValid = false;
	return false;
}


if (allValid) {
	if(confirm("Do you want to save Pre Yatra Preparation?")) {
    document.preyatraprep.action="savePreYatraPreparation";
	document.preyatraprep.method="post";
	document.preyatraprep.submit();
	}
	return true;
}

    return false;
}
</script>
</head>

<body>
	<div class="maindiv">
	
		<div class="col formheading" style=""><h4><u>Pre- Yatra Preparation</u></h4> </div>
		<label>
		<span style="color:blue;">Note:- The Image size must be under 300KB with dimensions of 400 x 400 pixels,Geo-referenced and Time-stamped.</span>
		</label>
		<c:if test="${not empty result}">
             <script>alert("${result}");</script>
        </c:if>
		<form:form autocomplete="off" name="preyatraprep" id="preyatraprep" modelAttribute="preyatraprep" action="savePreYatraPreparation" method="post" enctype="multipart/form-data">
		
             <div class="card-header" style="height:60px"> 		
			<p style="font-size: 20px;"><b>1. Whether Watershed Yatra subject discussed in Gram Sabha/Special Gram Sabha</b> </p>
			</div>
			<div class="card-body">
			
			<div class="form-row">
				<div class="form-group col-3">
					
						<label for="state">	<b> State Name:</b> </label>
						<span class="projectError"></span> <br/>
						<c:out value="${stateName}"></c:out>
					
				</div>
	    		<div class="form-group col-3">
	      			<label for="district"><b>District:</b> </label>
	      			<span class="projectError"></span>
	      			<select class="form-control district" id="district" name="district" required>
	    				<option value="">--Select District--</option>
	    				<c:forEach items="${distList}" var="dist"> 
						<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
						</c:forEach>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Block:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="block" name="block" required>
	    				<option value="">--Select Block--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Gram Panchayat Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="grampan" name="grampan" required>
	    				<option value="">--Select Gram Panchayat--</option>
	    			</select>
	    		</div>
	    			<div class="form-group col-3">
                       <label for="date"><b>Date:</b> </label>
                    <input type="date" name="date" id="date" class="form-control" required/>
                     </div>
	    			
				<div class="form-group col-3">
               <label for="gramsabha_participants"><b>Total No. of Participants:</b> </label>
               <input type="text" class="form-control activity" name="gramsabha_participants" id="gramsabha_participants" autocomplete="off" pattern="^\d{10}$" maxlength="6" oninput="this.value=this.value.replace(/[^0-9]/g,'');" style="height: 58% ;width: 100%;" required />
               </div>

                <div class="form-group col-3">
                  <label for="photo1"><b>Upload Photo 1:</b> </label>
                  <input type="file" name="gramphoto1" id="gramphoto1" class="form-control" accept="image/*" required onchange="validatePhoto(this)"/>
                  <input type="hidden" id="gramphoto1_lat" name="gramphoto1_lat">
                  <input type="hidden" id="gramphoto1_lng" name="gramphoto1_lng">
                  <input type="hidden" id="gramphoto1_time" name="gramphoto1_time">
                </div>

               <div class="form-group col-3">
                <label for="photo2"><b>Upload Photo 2:</b> </label>
                <input type="file" name="gramphoto2" id="gramphoto2" class="form-control" accept="image/*" required onchange="validatePhoto(this)"/>
                <input type="hidden" id="gramphoto2_lat" name="gramphoto2_lat">
                <input type="hidden" id="gramphoto2_lng" name="gramphoto2_lng">
                <input type="hidden" id="gramphoto2_time" name="gramphoto2_time">
               </div>
               

	    		</div>
	    		</div>
	    		
	    		<br/>
			
			<div class="card-header" style="height:60px">
			<p style="font-size: 20px;"><b>2. Prabhat Pheri</b></p> 
			</div>
			<div class="card-body">
			<div class="form-row">
				<div class="form-group col-3">
						<label for="state">	<b> State Name:</b> </label>
						<span class="projectError"></span> <br/>
						<c:out value="${stateName}"></c:out>
					
				</div>
	    		<div class="form-group col-3">
	      			<label for="district"><b>District:</b> </label>
	      			<span class="projectError"></span>
	      			<select class="form-control district1" id="district1" name="district1" required>
	    				<option value="">--Select District--</option>
	    				<c:forEach items="${distList}" var="dist"> 
						<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
						</c:forEach>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Block:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="block1" name="block1" required>
	    				<option value="">--Select Block--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Gram Panchayat Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="grampan1" name="grampan1" required>
	    				<option value="">--Select Gram Panchayat Name--</option>
	    			</select>
	    		</div>
	    		<div class="form-group col-3">
	    			<label for="activity"><b>Village Name:</b> </label>
	      			<span class="activityError"></span>
	      			<select class="form-control activity" id="village1" name="village1" required>
	    				<option value="">--Select Village Name--</option>
	    			</select>
	    		</div>
	    		
	    		<div class="form-group col-3">
                <label for="date"><b>Date:</b> </label>
                <input type="date" name="date1" id="date1" class="form-control" required/>
                </div>
	    		
	    		<div class="form-group col-3">
               <label for="prabhatpheri_participants"><b>Total No. of Participants:</b> </label>
               <input type="text" class="form-control activity" name="prabhatpheri_participants" id="prabhatpheri_participants" autocomplete="off" pattern="^\d{10}$" maxlength="6" oninput="this.value=this.value.replace(/[^0-9]/g,'');" style="height: 58% ;width: 100%;" required />
               </div>
	    		
	    		<div class="form-group col-3">
                  <label for="photo1"><b>Upload Photo 1:</b> </label>
                  <input type="file" name="pheriphoto1" id="pheriphoto1" class="form-control" accept="image/*" required onchange="validatePhoto(this)"/>
                  <input type="hidden" id="pheriphoto1_lat" name="pheriphoto1_lat">
                  <input type="hidden" id="pheriphoto1_lng" name="pheriphoto1_lng">
                  <input type="hidden" id="pheriphoto1_time" name="pheriphoto1_time">
                </div>

               <div class="form-group col-3">
                <label for="photo2"><b>Upload Photo 2:</b> </label>
                <input type="file" name="pheriphoto2" id="pheriphoto2" class="form-control" accept="image/*" required onchange="validatePhoto(this)"/>
                <input type="hidden" id="pheriphoto2_lat" name="pheriphoto2_lat">
                <input type="hidden" id="pheriphoto2_lng" name="pheriphoto2_lng">
                <input type="hidden" id="pheriphoto2_time" name="pheriphoto2_time">
               </div>
               
               <hr/>
               
               
               </div>
               <div class="form-group">
    <label for="remarks"><b>Remarks:</b></label>
    <textarea class="form-control" id="remarks" name="remarks" rows="3" placeholder="Enter any additional remarks..." style="width: 100%;"></textarea>
</div>
		</div>
    	<br/>
    	
    	<div class="form-group text-center">
               <input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Submit"/>
            </div>
	    		</form:form>
	    		<div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold"><u>Draft List of Pre Yatra Preparation (<c:out value="${stateName}"></c:out>)</u></h5>
	    		<table border="1">
   <thead>
    <tr>
        <th rowspan="2">S.No<input type="checkbox" id="chkSelectAll" name="chkSelectAll" /></th>
        <th colspan="2" rowspan="2">Action</th>
        <th rowspan="2">District</th>
        <th rowspan="2">Block</th>
        <th rowspan="2">Gram Panchayat</th>
        <th rowspan="2">Village</th>
        <th rowspan="2">Activity Type</th>
        <th rowspan="2">Entry Date</th>
        <th rowspan="2">Total No. of Participants</th>
        <th colspan="4" style="text-align:center;">Photo 1 Details</th>
        <th colspan="4" style="text-align:center;">Photo 2 Details</th>
        <th rowspan="2">Remarks</th>
        
        
    </tr>
    <tr>
        <th>Photo</th>
        <th>longitude</th>
        <th>latitude</th>
        <th>Date</th>
        <th>Photo</th>
        <th>longitude</th>
        <th>latitude</th>
        <th>Date</th>
    </tr>
    </thead>
    <c:choose>
        <c:when test="${not empty records}">
            <c:forEach var="record" items="${records}" varStatus="loop">
                <tr>
                    <td>${loop.count}&nbsp;<input type="checkbox" class="chkIndividual" id="${record.prep_id}"  name="${record.prep_id}" value="${record.prep_id}"
                    data-photo1="${record.photo1}" data-photo2="${record.photo2}"/></td>  <%-- Correct serial number --%>
                    <td><button class="btn btn-warning btn-sm" onclick="editRecord(${record.prep_id})"> Edit </button></td>
                    <td><button class="btn btn-danger btn-sm" onclick="deleteRecord(${record.prep_id}, '${record.photo1}', '${record.photo2}')"> Delete </button></td>
                    <td>${record.districtname}</td>
                    <td>${record.blockname}</td>
                    <td>${record.gramname}</td>
                    <td>${record.villagename}</td>
                    <td>${record.yatratype}</td>
                    <td>${record.entrydate}</td>
                    <td>${record.participants}</td>
                    
                    <td> <button onclick="showImage('https://wdcpmksy.dolr.gov.in/filepath/PRD/preyatraprep/${record.photo1}')">View</button></td>
                    <td>${record.photo1long}</td>
                    <td>${record.photo1lang}</td>
                    <td>${record.photo1time}</td>
                     <td> <button onclick="showImage('https://wdcpmksy.dolr.gov.in/filepath/PRD/preyatraprep/${record.photo2}')">View</button></td>
                    <td>${record.photo2long}</td>
                    <td>${record.photo2lang}</td>
                    <td>${record.photo2time}</td>
                    <td>${record.remarks}</td>
                    
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="16" style="text-align: center; font-weight: bold;">Data Not Found</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
<div id="imageModal" class="modal">
    <span class="close" onclick="closeImage()">&times;</span>
    <img class="modal-content" id="popupImage">
</div>

	 </div>
	 </div>   		
	 <div class="form-group text-center">
     				<input type="button" class="btn btn-info" id="completePreYatra" name="updatepreYatra" value ="Complete"/>
     				<input type="button" class="btn btn-info" id="deletePreYatra" name="deletePreYatra" value ="Delete"/>
     			</div>
	 
	 
	 <div class="form-row">
	     <div class="form-group col">
	     <hr/>
	     <h5 class="text-center font-weight-bold"><u>Complete List of Pre Yatra Preparation (<c:out value="${stateName}"></c:out>)</u></h5>
	    		<table border="1">
   <thead>
    <tr>
        <th rowspan="2">S.No</th>
        <th rowspan="2">Action</th>
        <th rowspan="2">District</th>
        <th rowspan="2">Block</th>
        <th rowspan="2">Gram Panchayat</th>
        <th rowspan="2">Village</th>
        <th rowspan="2">Activity Type</th>
        <th rowspan="2">Entry Date</th>
        <th rowspan="2">Total No. of Participants</th>
        <th colspan="4" style="text-align:center;">Photo 1 Details</th>
        <th colspan="4" style="text-align:center;">Photo 2 Details</th>
        <th rowspan="2">Remarks</th>
        
    </tr>
    <tr>
        <th>Photo</th>
        <th>longitude</th>
        <th>latitude</th>
        <th>Date</th>
        <th>Photo</th>
        <th>longitude</th>
        <th>latitude</th>
        <th>Date</th>
    </tr>
    </thead>
    <c:choose>
        <c:when test="${not empty comprecords}">
            <c:forEach var="comprecords" items="${comprecords}" varStatus="loop">
                <tr>
                    <td>${loop.count}</td>  <%-- Correct serial number --%>
                    <td><button class="btn btn-warning btn-sm" onclick="editCRecord(${comprecords.prep_id})"> Edit </button></td>
                    <td>${comprecords.districtname}</td>
                    <td>${comprecords.blockname}</td>
                    <td>${comprecords.gramname}</td>
                    <td>${comprecords.villagename}</td>
                    <td>${comprecords.yatratype}</td>
                    <td>${comprecords.entrydate}</td>
                    <td>${comprecords.participants}</td>
                    
                    <td> <button onclick="showImage('https://wdcpmksy.dolr.gov.in/filepath/PRD/preyatraprep/${comprecords.photo1}')">View</button></td>
                    <td>${comprecords.photo1long}</td>
                    <td>${comprecords.photo1lang}</td>
                    <td>${comprecords.photo1time}</td>
                     <td> <button onclick="showImage('https://wdcpmksy.dolr.gov.in/filepath/PRD/preyatraprep/${comprecords.photo2}')">View</button></td>
                    <td>${comprecords.photo2long}</td>
                    <td>${comprecords.photo2lang}</td>
                    <td>${comprecords.photo2time}</td>
                    <td>${comprecords.remarks}</td>
                    
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="16" style="text-align: center; font-weight: bold;">Data Not Found</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
<div id="imageModal" class="modal">
    <span class="close" onclick="closeImage()">&times;</span>
    <img class="modal-content" id="popupImage">
</div>

	 </div>
	 </div>   
	    			</div>
	    		<footer class=" text-center">
	            <%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
	<script src='<c:url value="/resources/js/preyatraPrep.js" />'></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
	
	    		</body>
	    		