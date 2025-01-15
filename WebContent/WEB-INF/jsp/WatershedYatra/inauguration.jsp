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
<script src='<c:url value="/resources/js/inauguration.js" />'></script>
<script type="text/javascript">

let formSubmitted = false;
let allValid = true;
function validation() 
{
	if (formSubmitted) return false;
	allValid = true;
	
	var allowedFiles = [ ".docx",".doc", ".pdf", ".ppt", ".pptx", ".jpg", ".jpeg",".png",".xlsx","xls"];
	
	$district = $('#district option:selected').val();
	$block = $('#block option:selected').val();

	$date = $('#date').val();
	$location = $('#location').val();
	$maleParticipants = $('#maleParticipants').val();
	$femaleParticipants = $('#femaleParticipants').val();
	$centralMinisters = $('#centralMinisters').val();
	$stateMinisters = $('#stateMinisters').val();
	$parliament = $('#parliament').val();
	$assemblyMembers = $('#assemblyMembers').val();
	$councilMembers = $('#councilMembers').val();
	$others = $('#others').val();
	$govOfficials = $('#govOfficials').val();
	$flagOff = $('input[name="flagOff"]:checked').val();
	$flagOffPhotos = $('#flagOffPhotos').val();
	$themeSong = $('input[name="themeSong"]:checked').val();
	$themeSongPhotos = $('#themeSongPhotos').val();
	$noWorksBhoomiPoojan = $('#noWorksBhoomiPoojan').val();
	$totWorksBhoomiPoojan = $('#totWorksBhoomiPoojan').val();
	$bhoomiPoojanPhotos = $('#bhoomiPoojanPhotos').val();
	$noWorksLokarpan = $('#noWorksLokarpan').val();
	$totWorksLokarpan = $('#totWorksLokarpan').val();
	$lokarpanPhotos = $('#lokarpanPhotos').val();
	$noLocationShramdaan = $('#noLocationShramdaan').val();
	$costPeopleShramdaan = $('#costPeopleShramdaan').val();
	$shramdaanPhotos = $('#shramdaanPhotos').val();
	$areaPlantation = $('#areaPlantation').val();
	$noPlantation = $('#noPlantation').val();
	$plantationPhotos = $('#plantationPhotos').val();
	$noAwards = $('#noAwards').val();
	$awardPhotos = $('#awardPhotos').val();

	$flagOffPhoto1 = $('#flagOffPhoto1').val();
	$flagOffPhoto2 = $('#flagOffPhoto2').val();
	$themeSongPhoto1 = $('#themeSongPhoto1').val();
	$themeSongPhoto2 = $('#themeSongPhoto2').val();
	$bhoomiPoojanPhoto1 = $('#bhoomiPoojanPhoto1').val();
	$bhoomiPoojanPhoto2 = $('#bhoomiPoojanPhoto2').val();
	$lokarpanPhoto1 = $('#lokarpanPhoto1').val();
	$lokarpanPhoto2 = $('#lokarpanPhoto2').val();
	$shramdaanPhoto1 = $('#shramdaanPhoto1').val();
	$shramdaanPhoto2 = $('#shramdaanPhoto2').val();
	$plantationPhoto1 = $('#plantationPhoto1').val();
	$plantationPhoto2 = $('#plantationPhoto2').val();
	$awardPhoto1 = $('#awardPhoto1').val();
	$awardPhoto2 = $('#awardPhoto2').val();
	

	if ($date === '' || typeof $date === 'undefined') {
		alert('Please select a Date');
		$('#date').focus();
		allValid = false;
		return false;
	}
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
	if ($location === '' || typeof $location === 'undefined') {
		alert('Please enter Location');
		$('#location').focus();
		allValid = false;
		return false;
	}
	if ($maleParticipants === '' || typeof $maleParticipants === 'undefined') {
		alert('Please enter the Number of Male Participants/Villagers');
		$('#maleParticipants').focus();
		allValid = false;
		return false;
	}
	if ($femaleParticipants === '' || typeof $femaleParticipants === 'undefined') {
		alert('Please enter the Number of Female Participants/Villagers');
		$('#femaleParticipants').focus();
		allValid = false;
		return false;
	}
	if ($centralMinisters === '' || typeof $centralMinisters === 'undefined') {
		alert('Please enter the Number of Central Ministers');
		$('#centralMinisters').focus();
		allValid = false;
		return false;
	}
	if ($stateMinisters === '' || typeof $stateMinisters === 'undefined') {
		alert('Please enter the Number of State Ministers');
		$('#stateMinisters').focus();
		allValid = false;
		return false;
	}
	if ($parliament === '' || typeof $parliament === 'undefined') {
		alert('Please enter the Number of Members of Parliament');
		$('#parliament').focus();
		allValid = false;
		return false;
	}
	if ($assemblyMembers === '' || typeof $assemblyMembers === 'undefined') {
		alert('Please enter the Number of Legislative Assembly Members');
		$('#assemblyMembers').focus();
		allValid = false;
		return false;
	}
	if ($councilMembers === '' || typeof $councilMembers === 'undefined') {
		alert('Please enter the Number of Legislative Council Members');
		$('#councilMembers').focus();
		allValid = false;
		return false;
	}
	if ($others === '' || typeof $others === 'undefined') {
		alert('Please enter the Number of other Public Representatives');
		$('#others').focus();
		allValid = false;
		return false;
	}
	if ($govOfficials === '' || typeof $govOfficials === 'undefined') {
		alert('Please enter the Number of Government Officials');
		$('#govOfficials').focus();
		allValid = false;
		return false;
	}
	if ($flagOff === '' || typeof $flagOff === 'undefined') {
		alert('Please select Flag off of Van');
// 		$('#flagOff').focus();
		$('input[name="flagOff"]').first().focus();
		allValid = false;
		return false;
	}
	
	if ($flagOffPhoto1 === '' || typeof $flagOffPhoto1 === 'undefined') {
		alert('Please upload photo for the Flag off of Van');
// 		$('#flagOffPhoto1').focus();
		document.getElementById('flagOffPhoto1').click();
		allValid = false;
		return false;
	}if ($flagOffPhoto2 === '' || typeof $flagOffPhoto2 === 'undefined') {
		alert('Please upload photo for the Flag off of Van');
// 		$('#flagOffPhoto2').focus();
		document.getElementById('flagOffPhoto2').click();
		allValid = false;
		return false;
	}
	
	if ($themeSong === '' || typeof $themeSong === 'undefined') {
		alert('Please select the Launch of the Theme Song');
// 		$('#themeSong').focus();
		$('input[name="themeSong"]').first().focus();
		allValid = false;
		return false;
	}
	
	if ($themeSongPhoto1 === '' || typeof $themeSongPhoto1 === 'undefined') {
		alert('Please upload photo for the Launch of the Theme Song');
// 		$('#themeSongPhoto1').focus();
		document.getElementById('themeSongPhoto1').click();
		allValid = false;
		return false;
	}if ($themeSongPhoto2 === '' || typeof $themeSongPhoto2 === 'undefined') {
		alert('Please upload photo for the Launch of the Theme Song');
// 		$('#themeSongPhoto2').focus();
		document.getElementById('themeSongPhoto2').click();
		allValid = false;
		return false;
	}
	
	if ($noWorksBhoomiPoojan === '' || typeof $noWorksBhoomiPoojan === 'undefined') {
		alert('Please enter Number of Works of Bhoomi Poojan');
		$('#noWorksBhoomiPoojan').focus();
		allValid = false;
		return false;
	}
	if ($totWorksBhoomiPoojan === '' || typeof $totWorksBhoomiPoojan === 'undefined') {
		alert('Please enter the Cost of the Total Works of Bhoomi Poojan in Lakhs');
		$('#totWorksBhoomiPoojan').focus();
		allValid = false;
		return false;
	}
	
	if ($bhoomiPoojanPhoto1 === '' || typeof $bhoomiPoojanPhoto1 === 'undefined') {
		alert('Please upload photo for Bhoomi Poojan');
// 		$('#bhoomiPoojanPhoto1').focus();
		document.getElementById('bhoomiPoojanPhoto1').click();
		allValid = false;
		return false;
	}if ($bhoomiPoojanPhoto2 === '' || typeof $bhoomiPoojanPhoto2 === 'undefined') {
		alert('Please upload photo for Bhoomi Poojan');
// 		$('#bhoomiPoojanPhoto2').focus();
		document.getElementById('bhoomiPoojanPhoto2').click();
		allValid = false;
		return false;
	}
	
	if ($noWorksLokarpan === '' || typeof $noWorksLokarpan === 'undefined') {
		alert('Please enter the Number of Works of Lokarpan');
		$('#noWorksLokarpan').focus();
		allValid = false;
		return false;
	}
	if ($totWorksLokarpan === '' || typeof $totWorksLokarpan === 'undefined') {
		alert('Please enter the Cost of the Total Works of Lokarpan in Lakhs');
		$('#totWorksLokarpan').focus();
		allValid = false;
		return false;
	}
	
	if ($lokarpanPhoto1 === '' || typeof $lokarpanPhoto1 === 'undefined') {
		alert('Please upload photo for Lokarpan');
// 		$('#lokarpanPhoto1').focus();
		document.getElementById('lokarpanPhoto1').click();
		allValid = false;
		return false;
	}if ($lokarpanPhoto2 === '' || typeof $lokarpanPhoto2 === 'undefined') {
		alert('Please upload photo for Lokarpan');
// 		$('#lokarpanPhoto2').focus();
		document.getElementById('lokarpanPhoto2').click();
		allValid = false;
		return false;
	}
	
	if ($noLocationShramdaan === '' || typeof $noLocationShramdaan === 'undefined') {
		alert('Please enter the Number of Locations of Shramdaan');
		$('#noLocationShramdaan').focus();
		allValid = false;
		return false;
	}
	if ($costPeopleShramdaan === '' || typeof $costPeopleShramdaan === 'undefined') {
		alert('Please enter Cost of people who participated in Shramdaan');
		$('#costPeopleShramdaan').focus();
		allValid = false;
		return false;
	}
	
	if ($shramdaanPhoto1 === '' || typeof $shramdaanPhoto1 === 'undefined') {
		alert('Please upload photo for Shramdaan');
// 		$('#shramdaanPhoto1').focus();
		document.getElementById('shramdaanPhoto1').click();
		allValid = false;
		return false;
	}if ($shramdaanPhoto2 === '' || typeof $shramdaanPhoto2 === 'undefined') {
		alert('Please upload photo for Shramdaan');
// 		$('#shramdaanPhoto2').focus();
		document.getElementById('shramdaanPhoto2').click();
		allValid = false;
		return false;
	}
	
	if ($areaPlantation === '' || typeof $areaPlantation === 'undefined') {
		alert('Please enter the Plantation Area in hectares');
		$('#areaPlantation').focus();
		allValid = false;
		return false;
	}
	if ($noPlantation === '' || typeof $noPlantation === 'undefined') {
		alert('Please enter the Number of Agro forsetry / Horticultural Plants');
		$('#noPlantation').focus();
		allValid = false;
		return false;
	}
	
	if ($plantationPhoto1 === '' || typeof $plantationPhoto1 === 'undefined') {
		alert('Please upload photo for Plantation');
// 		$('#plantationPhoto1').focus();
		document.getElementById('plantationPhoto1').click();
		allValid = false;
		return false;
	}if ($plantationPhoto2 === '' || typeof $plantationPhoto2 === 'undefined') {
		alert('Please upload photo for Plantation');
// 		$('#plantationPhoto2').focus();
		document.getElementById('plantationPhoto2').click();
		allValid = false;
		return false;
	}
	
	if ($noAwards === '' || typeof $noAwards === 'undefined') {
		alert('Please enter the Number of Award Distribution');
		$('#noAwards').focus();
		allValid = false;
		return false;
	}
	
	if ($awardPhoto1 === '' || typeof $awardPhoto1 === 'undefined') {
		alert('Please upload photo for Award Distribution');
// 		$('#awardPhoto1').focus();
		document.getElementById('awardPhoto1').click();
		allValid = false;
		return false;
	}if ($awardPhoto2 === '' || typeof $awardPhoto2 === 'undefined') {
		alert('Please upload photo for Award Distribution');
// 		$('#awardPhoto2').focus();
		document.getElementById('awardPhoto2').click();
		allValid = false;
		return false;
	}
	

if (allValid) {
	if(confirm("Do you want to save Inauguration Programm Details?")) {
    formSubmitted = true; 
	document.inauguration.action="saveInaugurationDetails";
	document.inauguration.method="post";
	document.inauguration.submit();
	}
	return true;
}

    return false;
}


function checkImage(input, inputId) {
    var file = input.files[0];
    var fileType = file.type;

    if (!fileType.startsWith('image/')) {
        alert('Only image files are allowed');
       	input.value = ''; // Clear the file input
       	document.getElementById(inputId).focus();
    }
}

</script>

<meta charset="ISO-8859-1">
<title>Inauguration Programm</title>

<style>
input[type=text] {
	width: 100px;
	height: 50px;
	padding: 12px 20px;
	margin: 8px 0;
	box-sizing: border-box;
	border: 2px solid black;
	border-radius: 4px;
}

.form-group label {
	font-size: 1.1rem;
	font-weight: 600;
	color: black; /* Watercolor blue for labels */
	display: block;
	margin-bottom: 5px;
}
</style> 

</head>
<body>
<!--  
<c:if test="${result != null}">
	<script>
	    alert("<c:out value='${result}'/>");
	</script>
</c:if>
-->

<c:if test="${not empty result}">
    <div class="alert alert-info">${result}</div>
</c:if>


	<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Yatra - Inauguration Programm</h4> </div>
<!-- 		<form name="inauguration" id="inauguration" modelAttribute="inauguration" action="saveInaugurationDetails" method="post" enctype="multipart/form-data"> -->
		<!-- <form name="inauguration" id="inauguration" modelAttribute="WatershedYatraInauguaration" enctype="multipart/form-data"> -->
		<form:form autocomplete="off" method="post" name="inauguration" id="inauguration" action="saveInaugurationDetails" modelAttribute="useruploadign" enctype="multipart/form-data">
			  <hr/>
			  <div class="row">
    			<div class="form-group col-3">
    			
      		  <label for="date">Date: </label>
      		  <input type="date" name="date" id="date" class="form-control activity" style="width: 100%;" />
       		 
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-3">
			<c:if test="${userType== 'SL' }"><br/>
				<b> State Name: </b><br/>
				<c:out value="${stateName}"></c:out>
			</c:if>
			</div>
    		<div class="form-group col-3">
      			<label for="district">District: </label>
<!--       			<span class="projectError"></span> -->
      			<select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="block">Block: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block" name="block" >
    				<option value="">--Select Block--</option>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="location">Location (Nearby/Milestone):</label>
      			<input type="texts" class="form-control activity" name="location" id="location" autocomplete="off" />
    		</div>
    		
    		</div>
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=3 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td>Number of Participants/Villagers</td>
     		<td>Male<br><input type="text" id="maleParticipants" name="maleParticipants" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="femaleParticipants" name="femaleParticipants" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Ministers</td>
     		<td>Central Level<br><input type="text" id="centralMinisters" name="centralMinisters" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateMinisters" name="stateMinisters" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td>Number of Member of Parliament</td>
     		<td colspan=2><input type="text" id="parliament" name="parliament" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Members</td>
     		<td>Legislative Assembly<br><input type="text" id="assemblyMembers" name="assemblyMembers" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="councilMembers" name="councilMembers" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of other Public Representatives</td>
     		<td colspan=2><input type="text" id="others" name="others" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td>Number of Government Officials</td>
     		<td colspan=2><input type="text" id="govOfficials" name="govOfficials" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	</table>
     	<table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
     	<tr>
     		<td>Flag off of Van</td>
     		<td><input type="radio" id="flagOffYes" name="flagOff" value="true" autocomplete="off" />Yes</td>
     		<td><input type="radio" id="flagOffNo" name="flagOff" value="false" autocomplete="off" />No</td>
     		<td>Upload Photographs<br><input type="file" id="flagOffPhoto1" name="flagOffPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'flagOffPhoto1')" required /><br/>
     							<input type="file" id="flagOffPhoto2" name="flagOffPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'flagOffPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td>Launch of Theme Song</td>
     		<td><input type="radio" id="themeSongYes" name="themeSong" value="true" autocomplete="off" />Yes</td>
     		<td><input type="radio" id="themeSongNo" name="themeSong" value="false" autocomplete="off" />No</td>
     		<td>Upload Photographs<br><input type="file" id="themeSongPhoto1" name="themeSongPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'themeSongPhoto1')" required /><br/>
     							<input type="file" id="themeSongPhoto2" name="themeSongPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'themeSongPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td>Bhoomi Poojan</td>
     		<td>Number of Works<br><input type="text" id="noWorksBhoomiPoojan" name="noWorksBhoomiPoojan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Cost of Total works (in Lakh)<br><input type="text" id="totWorksBhoomiPoojan" name="totWorksBhoomiPoojan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br><input type="file" id="bhoomiPoojanPhoto1" name="bhoomiPoojanPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'bhoomiPoojanPhoto1')" required /><br/>
								<input type="file" id="bhoomiPoojanPhoto2" name="bhoomiPoojanPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'bhoomiPoojanPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td>Lokarpan</td>
     		<td>Number of Works<br><input type="text" id="noWorksLokarpan" name="noWorksLokarpan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Cost of Total works (in Lakh)<br><input type="text" id="totWorksLokarpan" name="totWorksLokarpan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br><input type="file" id="lokarpanPhoto1" name="lokarpanPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'lokarpanPhoto1')" required /><br/>
								<input type="file" id="lokarpanPhoto2" name="lokarpanPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'lokarpanPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td>Shramdaan</td>
     		<td>Number of Locations<br><input type="text" id="noLocationShramdaan" name="noLocationShramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Cost of people participated<br><input type="text" id="costPeopleShramdaan" name="costPeopleShramdaan" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br><input type="file" id="shramdaanPhoto1" name="shramdaanPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'shramdaanPhoto1')" required /><br/>
								<input type="file" id="shramdaanPhoto2" name="shramdaanPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'shramdaanPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td>Plantation</td>
     		<td>Area (in ha.)<br><input type="text" id="areaPlantation" name="areaPlantation" autocomplete="off" onfocusin="decimalToFourPlace(event)" maxlength="10" required /></td>
     		<td>No. of Agro forsetry / Horticultural Plants<br><input type="text" id="noPlantation" name="noPlantation" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br><input type="file" id="plantationPhoto1" name="plantationPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'plantationPhoto1')" required /><br/>
								<input type="file" id="plantationPhoto2" name="plantationPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'plantationPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td>Award Distribution</td>
     		<td colspan=2>Number of Watershed Margdarshaks<br><input type="text" id="noAwards" name="noAwards" autocomplete="off"
								pattern="^\d{10}$" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Upload Photographs<br><input type="file" id="awardPhoto1" name="awardPhoto1" autocomplete="off" accept="image/*" onchange="checkImage(this, 'awardPhoto1')" required /><br/>
								<input type="file" id="awardPhoto2" name="awardPhoto2" autocomplete="off" accept="image/*" onchange="checkImage(this, 'awardPhoto2')" required /></td>
     	</tr>
     	<tr>
     		<td colspan=4 class="text-left">
<!--      			<button class="btn btn-primary" type="submit">Save</button> -->
<!--      			<input type="button" name="click" id="click" value="Save" class="btn btn-info" onclick="savedata();" /> -->
     			<!-- <input type="button" name="click" id="click" value="Save" class="btn btn-info" onclick="validation();" /> -->
     			<input type="button" class="btn btn-info" id="view" onclick="validation();" name="view" value='Save' />
     		</td>
     	</tr>
     </table>
     </div>
		</div>
<!-- 		<div class="form-row"> -->
<!-- 				<div class="form-group col"> -->
<!--      				<input type="button" class="d-none btn btn-info" id="update" name="update" value ="update"/> -->
<!--      			</div> -->
<!--      		</div> -->
     	
		</form:form>
		
   
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>