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
<script src='<c:url value="/resources/js/VillageWatershed.js" />'></script>

<meta charset="ISO-8859-1">
<title>Watershed Yatra Program</title>

<script type="text/javascript">

function validateDecimal(input, decimalPlaces) {
    // Allow only numbers and one decimal point
    const regex = new RegExp(`^\\d*(\\.\\d{0,${decimalPlaces}})?$`);
    if (!regex.test(input.value)) {
      input.value = input.value.slice(0, -1); // Remove the last invalid character
    }
  }
function toggleOtherField() {
    const select = document.getElementById('culturalActivity');
    const otherDiv = document.getElementById('otherActivityDiv');

    // Check if the selected option text is "Others"
    const selectedText = select.options[select.selectedIndex].text.trim();
    if (selectedText === "Others") {
        otherDiv.style.display = "block"; // Show the text box
    } else {
        otherDiv.style.display = "none"; // Hide the text box
    }
}

let formSubmitted = false;
let allValid = true;
function validation() 
{
	if (formSubmitted) return false;
	allValid = true;
	var allowedFiles = [ ".docx",".doc", ".pdf", ".ppt", ".pptx", ".jpg", ".jpeg",".png",".xlsx","xls"];
	
	$district = $('#district option:selected').val();
	$block = $('#block option:selected').val();
	$grampan = $('#grampan option:selected').val();
	$village = $('#village option:selected').val();
	$datetime = $('#datetime').val();
	$location = $('#location').val();
	$maleParticipants = $('#maleParticipants').val();
	$femaleParticipants = $('#femaleParticipants').val();
	$centralMinisters = $('#centralMinisters').val();
	$stateMinisters = $('#stateMinisters').val();
	$membersOfParliament = $('#membersOfParliament').val();
	$legAssemblyMembers = $('#legAssemblyMembers').val();
	$legCouncilMembers = $('#legCouncilMembers').val();
	$publicReps = $('#publicReps').val();
	$govOfficials = $('#govOfficials').val();
	
	$arExperience=$('#arExperience').val();
	$shapathYes = $('input[name="shapathYes"]:checked').val();
	$FilmYes = $('input[name="FilmYes"]:checked').val();
	$quizParticipants = $('#quizParticipants').val();
	$culturalActivity = $('#culturalActivity').val();
	$otherActivity = $('#otherActivity').val();
	$bhoomiWorks = $('#bhoomiWorks').val();
	$bhoomiCost = $('#bhoomiCost').val();
	$lokWorks = $('#lokWorks').val();
	$costWorks = $('#costWorks').val();
	$locShramdaan = $('#locShramdaan').val();
	$locShramdaanps = $('#locShramdaanps').val();
	$plantationArea = $('#plantationArea').val();
	$nofagrohorti = $('#nofagrohorti').val();
	$noOfwatershed = $('#noOfwatershed').val();
	
	$arExperiencephoto1 = $('#arExperiencephoto1').val();
	$arExperiencephoto2 = $('#arExperiencephoto2').val();
	$shapathYesphoto1 = $('#shapathYesphoto1').val();
	$shapathYesphoto2 = $('#shapathYesphoto2').val();
	$FilmYesphoto1 = $('#FilmYesphoto1').val();
	$FilmYesphoto2 = $('#FilmYesphoto2').val();
	$quizParticipantsphoto1 = $('#quizParticipantsphoto1').val();
	$quizParticipantsphoto2 = $('#quizParticipantsphoto2').val();
	$culturalActivityphoto1 = $('#culturalActivityphoto1').val();
	$culturalActivityphoto2 = $('#culturalActivityphoto2').val();
	$bhoomiCostphoto1 = $('#bhoomiCostphoto1').val();
	$bhoomiCostphoto2 = $('#bhoomiCostphoto2').val();
	$lokWorksphoto1 = $('#lokWorksphoto1').val();
	$lokWorksphoto2 = $('#lokWorksphoto2').val();
	$locShramdaanpsphoto1 = $('#locShramdaanpsphoto1').val();
	$locShramdaanpsphoto2 = $('#locShramdaanpsphoto2').val();
	$plantationAreaphoto1 = $('#plantationAreaphoto1').val();
	$plantationAreaphoto2 = $('#plantationAreaphoto2').val();
	$noOfwatershedphoto1 = $('#noOfwatershedphoto1').val();
	$noOfwatershedphoto2 = $('#noOfwatershedphoto2').val();
	

	if ($datetime === '' || typeof $datetime === 'undefined') {
		alert('Please select a Date and Time');
		$('#datetime').focus();
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
	if ($grampan === '' || typeof $grampan === 'undefined') {
		alert('Please select Gram Panchayat');
		$('#grampan').focus();
		allValid = false;
		return false;
	}
	if ($village === '' || typeof $village === 'undefined') {
		alert('Please select Village');
		$('#village').focus();
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
		alert('Please enter the Number Of Male Participants/Villagers');
		$('#maleParticipants').focus();
		allValid = false;
		return false;
	}
	if ($femaleParticipants === '' || typeof $femaleParticipants === 'undefined') {
		alert('Please enter the Number Of Female Participants/Villagers');
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
	if ($membersOfParliament === '' || typeof $membersOfParliament === 'undefined') {
		alert('Please enter the Number of Members of Parliament');
		$('#membersOfParliament').focus();
		allValid = false;
		return false;
	}
	if ($legAssemblyMembers === '' || typeof $legAssemblyMembers === 'undefined') {
		alert('Please enter the Number of Legislative Assembly Members');
		$('#legAssemblyMembers').focus();
		allValid = false;
		return false;
	}
	if ($legCouncilMembers === '' || typeof $legCouncilMembers === 'undefined') {
		alert('Please enter the Number of Legislative Council Members');
		$('#legCouncilMembers').focus();
		allValid = false;
		return false;
	}
	if ($publicReps === '' || typeof $publicReps === 'undefined') {
		alert('Please enter the Number of other Public Representatives');
		$('#publicReps').focus();
		allValid = false;
		return false;
	}
	if ($govOfficials === '' || typeof $govOfficials === 'undefined') {
		alert('Please enter the Number of Government Officials');
		$('#govOfficials').focus();
		allValid = false;
		return false;
	}
	
	if ($arExperience === '' || typeof $arExperience === 'undefined') {
		alert('Please enter the Number of People who availed experience');
		$('#arExperience').focus();
		allValid = false;
		return false;
	}
	if ($arExperiencephoto1 === '' || typeof $arExperiencephoto1 === 'undefined') {
		alert('Please upload the photo of AR Experience');
		//$('#arExperiencephoto1').focus();
		document.getElementById('arExperiencephoto1').click();
		allValid = false;
		return false;
	}
	
	if ($arExperiencephoto2 === '' || typeof $arExperiencephoto2 === 'undefined') {
		alert('Please upload the photo of AR Experience');
		//$('#arExperiencephoto2').focus();
		document.getElementById('arExperiencephoto2').click();
		allValid = false;
		return false;
	}
	
	if ($shapathYes === '' || typeof $shapathYes === 'undefined') {
		alert('Please select Shapath Shramdan');
		$('#shapathYes').focus();
		allValid = false;
		return false;
	}
	if ($shapathYesphoto1 === '' || typeof $shapathYesphoto1 === 'undefined') {
		alert('Please upload the photo of Shapath Shramdan');
		//$('#shapathYesphoto1').focus();
		document.getElementById('shapathYesphoto1').click();
		allValid = false;
		return false;
	}
	if ($shapathYesphoto2 === '' || typeof $shapathYesphoto2 === 'undefined') {
		alert('Please upload the photo of Shapath Shramdan');
		//$('#shapathYesphoto2').focus();
		document.getElementById('shapathYesphoto2').click();
		allValid = false;
		return false;
	}
	if ($FilmYes === '' || typeof $FilmYes === 'undefined') {
		alert('Please select Film on Watershed Yatra');
		$('#FilmYes').focus();
		allValid = false;
		return false;
	}
	if ($FilmYesphoto1 === '' || typeof $FilmYesphoto1 === 'undefined') {
		alert('Please upload the photo of Film on Watershed Yatra');
		//$('#FilmYesphoto1').focus();
		document.getElementById('FilmYesphoto1').click();
		allValid = false;
		return false;
	}
	if ($FilmYesphoto2 === '' || typeof $FilmYesphoto2 === 'undefined') {
		alert('Please upload the photo of Film on Watershed Yatra');
		//$('#FilmYesphoto2').focus();
		document.getElementById('FilmYesphoto2').click();
		allValid = false;
		return false;
	}
	
	if ($quizParticipants === '' || typeof $quizParticipants === 'undefined') {
		alert('Please enter Number of People participated in Quiz');
		$('#quizParticipants').focus();
		allValid = false;
		return false;
	}
	if ($quizParticipantsphoto1 === '' || typeof $quizParticipantsphoto1 === 'undefined') {
		alert('Please upload the photo of Quiz Program');
	//	$('#quizParticipantsphoto1').focus();
		document.getElementById('quizParticipantsphoto1').click();
		allValid = false;
		return false;
	}
	if ($quizParticipantsphoto2 === '' || typeof $quizParticipantsphoto2 === 'undefined') {
		alert('Please upload the photo of Quiz Program');
		//$('#quizParticipantsphoto2').focus();
		document.getElementById('quizParticipantsphoto2').click();
		allValid = false;
		return false;
	}
	
	if ($culturalActivity === '' || typeof $culturalActivity === 'undefined') {
		alert('Please select Cultural Activity based on Watershed theme');
		$('#culturalActivity').focus();
		allValid = false;
		return false;
	}
	if ($culturalActivity === '4') {
	    if ($otherActivity === '' || typeof $otherActivity === 'undefined') {
	        alert('Please specify the Other Cultural Activity based on Watershed theme');
	        $('#otherActivity').focus();
	        allValid = false;
	        return false;
	    }
	}

	if ($culturalActivityphoto1 === '' || typeof $culturalActivityphoto1 === 'undefined') {
		alert('Please upload the photo of Cultural Activity based on Watershed theme');
		//$('#culturalActivityphoto1').focus();
		document.getElementById('culturalActivityphoto1').click();
		allValid = false;
		return false;
	}
	if ($culturalActivityphoto2 === '' || typeof $culturalActivityphoto2 === 'undefined') {
		alert('Please upload the photo of Cultural Activity based on Watershed theme');
		//$('#culturalActivityphoto2').focus();
		document.getElementById('culturalActivityphoto2').click();
		allValid = false;
		return false;
	}
	
	if ($bhoomiWorks === '' || typeof $bhoomiWorks === 'undefined') {
		alert('Please enter Number of Works of Bhoomi Poojan');
		$('#bhoomiWorks').focus();
		allValid = false;
		return false;
	}
	if ($bhoomiCost === '' || typeof $bhoomiCost === 'undefined') {
		alert('Please enter Cost of Total works of Bhoomi Poojan');
		$('#bhoomiCost').focus();
		allValid = false;
		return false;
	}
	if ($bhoomiCostphoto1 === '' || typeof $bhoomiCostphoto1 === 'undefined') {
		alert('Please upload the photo of Bhoomi Poojan');
		//$('#bhoomiCostphoto1').focus();
		document.getElementById('bhoomiCostphoto1').click();
		allValid = false;
		return false;
	}
	if ($bhoomiCostphoto2 === '' || typeof $bhoomiCostphoto2 === 'undefined') {
		alert('Please upload the photo of Bhoomi Poojan');
	//	$('#bhoomiCostphoto2').focus();
		document.getElementById('bhoomiCostphoto2').click();
		allValid = false;
		return false;
	}
	if ($lokWorks === '' || typeof $lokWorks === 'undefined') {
		alert('Please enter Number of Works of Lokarpan');
		$('#lokWorks').focus();
		allValid = false;
		return false;
	}
	if ($costWorks === '' || typeof $costWorks === 'undefined') {
		alert('Please enter Number of cost of Works of Lokarpan');
		$('#costWorks').focus();
		allValid = false;
		return false;
	}
	if ($lokWorksphoto1 === '' || typeof $lokWorksphoto1 === 'undefined') {
		alert('Please upload the photo of Lokarpan');
		//$('#lokWorksphoto1').focus();
		document.getElementById('lokWorksphoto1').click();
		allValid = false;
		return false;
	}
	if ($lokWorksphoto2 === '' || typeof $lokWorksphoto2 === 'undefined') {
		alert('Please upload the photo of Lokarpan');
		//$('#lokWorksphoto2').focus();
		document.getElementById('lokWorksphoto2').click();
		allValid = false;
		return false;
	}
	if ($locShramdaan === '' || typeof $locShramdaan === 'undefined') {
		alert('Please enter Number of Locations of Shramdaan');
		$('#locShramdaan').focus();
		allValid = false;
		return false;
	}
	if ($locShramdaanps === '' || typeof $locShramdaanps === 'undefined') {
		alert('Please enter Number of Cost of people participated of Shramdaan');
		$('#locShramdaanps').focus();
		allValid = false;
		return false;
	}
	if ($locShramdaanpsphoto1 === '' || typeof $locShramdaanpsphoto1 === 'undefined') {
		alert('Please upload the photo of Shramdan');
		//$('#locShramdaanpsphoto1').focus();
		document.getElementById('locShramdaanpsphoto1').click();
		allValid = false;
		return false;
	}
	if ($locShramdaanpsphoto2 === '' || typeof $locShramdaanpsphoto2 === 'undefined') {
		alert('Please upload the photo of Shramdan');
		//$('#locShramdaanpsphoto2').focus();
		document.getElementById('locShramdaanpsphoto2').click();
		allValid = false;
		return false;
	}
	
	if ($plantationArea === '' || typeof $plantationArea === 'undefined') {
		alert('Please enter the plantation area');
		$('#plantationArea').focus();
		allValid = false;
		return false;
	}
	if ($nofagrohorti === '' || typeof $nofagrohorti === 'undefined') {
		alert('Please enter the No. of Agro forsetry / Horticultural Plants');
		$('#nofagrohorti').focus();
		allValid = false;
		return false;
	}

	if ($plantationAreaphoto1 === '' || typeof $plantationAreaphoto1 === 'undefined') {
		alert('Please upload the photo of Plantation area');
		//$('#plantationAreaphoto1').focus();
		document.getElementById('plantationAreaphoto1').click();
		allValid = false;
		return false;
	}
	if ($plantationAreaphoto2 === '' || typeof $plantationAreaphoto2 === 'undefined') {
		alert('Please upload the photo of Plantation area');
		//$('#plantationAreaphoto2').focus();
		document.getElementById('plantationAreaphoto2').click();
		allValid = false;
		return false;
	}
	if ($noOfwatershed === '' || typeof $noOfwatershed === 'undefined') {
		alert('Please enter the Number of Watershed Margdarshaks');
		$('#noOfwatershed').focus();
		allValid = false;
		return false;
	}

	if ($noOfwatershed === '' || typeof $noOfwatershed === 'undefined') {
		alert('Please enter the Number of Watershed Margdarshaks');
		$('#noOfwatershed').focus();
		allValid = false;
		return false;
	}
	if ($noOfwatershedphoto1 === '' || typeof $noOfwatershedphoto1 === 'undefined') {
		alert('Please upload the photo of Award Distribution');
		//$('#noOfwatershedphoto1').focus();
		document.getElementById('noOfwatershedphoto1').click();
		allValid = false;
		return false;
	}
	if ($noOfwatershedphoto2 === '' || typeof $noOfwatershedphoto2 === 'undefined') {
		alert('Please upload the photo of Award Distribution');
	//	$('#noOfwatershedphoto2').focus();
		document.getElementById('noOfwatershedphoto2').click();
		allValid = false;
		return false;
	}
	if (allValid) {
		if(confirm("Do you want to save Watershed Yatra at Village Level?")) {
	    formSubmitted = true; 
		document.saveWatershed.action="saveWatershedYatraVillage";
		document.saveWatershed.method="post";
		document.saveWatershed.submit();
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


<style>
input[type=text] {
  width: 100px; /* Increased width */
  height: 35px; /* Retained height */
  padding: 12px 15px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid black;
  border-radius: 2px;
}

/* .form-group label { */
/*     font-size: 1.1rem; */
/*     font-weight: 600; */
/*     color: black; /* Watercolor blue for labels */ */
/*     display: block; */
/*     margin-bottom: 5px; */
}


</style> 

</head>
<body>
<c:if test="${result != null}">
	<script>
	    alert("<c:out value='${result}'/>");
	</script>
</c:if>
	<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Watershed Yatra at Village Level</h4> </div>
		<form:form autocomplete="off" method="post" name="saveWatershed" id="saveWatershed" action="saveWatershedYatraVillage" modelAttribute="useruploadsl" enctype="multipart/form-data">
			
			<hr/>
			  <div class="row">
    			<div class="form-group col-3">
      		  <label for="datetime">Date and Time: </label>
       		 <input type="datetime-local" name="datetime" id="datetime" class="form-control activity" style="width: 100%;" />
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-3">
			<c:if test="${userType== 'SL' }"><br/>
				<b>State Name:</b></br> <c:out value="${stateName}"></c:out>
			</c:if>
			</div>
    		<div class="form-group col-3">
      			<label for="district">District: </label>
      			<span class="activityError"></span>
      			<select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Block: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="block" name="block" >
    				<option value="">--Select Block--</option>
    			</select>
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Gram Panchayat Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="grampan" name="grampan" >
    				<option value="">--Select Gram Panchayat Name--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    			<label for="activity">Village Name: </label>
      			<span class="activityError"></span>
      			<select class="form-control activity" id="village" name="village" >
    				<option value="">--Select Village Name--</option>
    			</select>
    		</div>
    		
    		<div class="form-group col-3">
    <label for="activity">Location (Nearby/Milestone)</label>
    <input type="text" class="form-control activity" name="location" id="location" style="width: 100%; max-width: 800px;" />
</div>

    		
    		</div>
    		
     		<div class="form-row">
     <div class="form-group col">
     
     <table id = "tblReport" class = "table">
     	<tr>
     		<th colspan=4 class="text-left">Participation :</th>
     	</tr>
     	<tr>
     		<td colspan=2>Number Of Participants/Villagers</td>
     		<td>Male<br><input type="text" id="maleParticipants" name="maleParticipants" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="femaleParticipants" name="femaleParticipants" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Ministers</td>
     		<td>Central Level<br><input type="text" id="centralMinisters" name="centralMinisters" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateMinisters" name="stateMinisters" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td colspan=2>Number of Member of Parliament</td>
     		<td colspan=2><input type="text" id="membersOfParliament" name="membersOfParliament" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Members</td>
     		<td>Legislative Assembly<br><input type="text" id="legAssemblyMembers" name="legAssemblyMembers" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="legCouncilMembers" name="legCouncilMembers" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of other Public Representatives</td>
     		<td colspan=2><input type="text" id="publicReps" name="publicReps" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	<tr>
     		<td colspan=2>Number of Government Officials</td>
     		<td colspan=2><input type="text" id="govOfficials" name="govOfficials" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<th colspan=4 class="text-left">Activities :</th>
     	</tr>
     	<tr>
     		<td>AR Experience</td>
     		<td colspan=2>Number of People who availed experience<br><input type="text" id="arExperience" name="arExperience" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="arExperiencephoto1" id="arExperiencephoto1" onchange="checkImage(this, 'arExperiencephoto1')" required />
        <input type="file" name="arExperiencephoto2" id="arExperiencephoto2" onchange="checkImage(this, 'arExperiencephoto2')" required />
    </td>
     	</tr>
     	<tr>
     		<td>"Bhoomi aur Jal Sanrakshan" Shapath Shramdan</td>
     		<td><input type="radio" id="shapathYes" name="shapathYes" value="true">Yes</td>
			<td><input type="radio" id="shapathNo" name="shapathYes" value="false">No</td>
     		
     		<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="shapathYesphoto1" id="shapathYesphoto1" onchange="checkImage(this, 'shapathYesphoto1')" required />
        <input type="file" name="shapathYesphoto2" id="shapathYesphoto2" onchange="checkImage(this, 'shapathYesphoto2')" required />
    </td>
     	</tr>
     	<tr>
     		<td>Film on Watershed Yatra</td>
     		<td><input type="radio" id="FilmYes" name="FilmYes" value="true">Yes</td>
     		<td><input type="radio" id="FilmNo" name="FilmYes" value="false">No</td>
     		<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="FilmYesphoto1" id="FilmYesphoto1" onchange="checkImage(this, 'FilmYesphoto1')" required />
        <input type="file" name="FilmYesphoto2" id="FilmYesphoto2" onchange="checkImage(this, 'FilmYesphoto2')" required />
    </td>
     	</tr>
     		<tr>
     		<td>Quiz Program</td>
     		<td colspan=2>Number of People participated in Quiz<br><input type="text" id="quizParticipants" name="quizParticipants" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="quizParticipantsphoto1" id="quizParticipantsphoto1" onchange="checkImage(this, 'quizParticipantsphoto1')" required />
        <input type="file" name="quizParticipantsphoto2" id="quizParticipantsphoto2" onchange="checkImage(this, 'quizParticipantsphoto2')" required />
    </td>
     	</tr>
<tr>
    <td>Cultural Activity based on Watershed theme</td>
   <td colspan="2">
    Street play/folk dance/songs/Others<br>
    <div class="form-group col-8">
        <span class="activityError"></span>
        <select id="culturalActivity" name="culturalActivity" style="width: 100%; max-width: 300px;" required class="form-control activity" onchange="toggleOtherField()">
            <option value="">--Select cultural activity--</option>
            <c:forEach items="${cultMap}" var="cult"> 
                <option value="<c:out value='${cult.key}'/>"><c:out value="${cult.value}" /></option>
            </c:forEach>
        </select>
    </div>
    <div id="otherActivityDiv" class="form-group col-8" style="display: none; margin-top: 10px;">
        <label for="otherActivity">Please specify:</label>
        <input type="text" id="otherActivity" name="otherActivity" class="form-control" placeholder="other activity" style="max-width: 300px;">
    </div>
</td>


    <td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="culturalActivityphoto1" id="culturalActivityphoto1" onchange="checkImage(this, 'culturalActivityphoto1')" required />
        <input type="file" name="culturalActivityphoto2" id="culturalActivityphoto2" onchange="checkImage(this, 'culturalActivityphoto2')" required />
    </td>
</tr>
     
     	<tr>
     		<td>Bhoomi Poojan</td>
     		<td>Number of Works<br><input type="text" id="bhoomiWorks" name="bhoomiWorks" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Cost of Total works (in Lakh)<br><input type="text" id="bhoomiCost" name="bhoomiCost" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="bhoomiCostphoto1" id="bhoomiCostphoto1" onchange="checkImage(this, 'bhoomiCostphoto1')" required />
        <input type="file" name="bhoomiCostphoto2" id="bhoomiCostphoto2" onchange="checkImage(this, 'bhoomiCostphoto2')" required />
    </td>
     	</tr>
     	<tr>
     		<td>Lokarpan</td>
     		<td>Number of Works<br><input type="text" id="lokWorks" name="lokWorks" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Cost of Total works (in Lakh)<br><input type="text" id="costWorks" name="costWorks" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
									<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="lokWorksphoto1" id="lokWorksphoto1" onchange="checkImage(this, 'lokWorksphoto1')" required />
        <input type="file" name="lokWorksphoto2" id="lokWorksphoto2" onchange="checkImage(this, 'lokWorksphoto2')" required />
    </td>
     	</tr>
     	<tr>
     		<td>Shramdaan</td>
     		<td>Number of Locations<br><input type="text" id="locShramdaan" name="locShramdaan" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Number of people participated<br><input type="text" id="locShramdaanps" name="locShramdaanps" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
									<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="locShramdaanpsphoto1" id="locShramdaanpsphoto1" onchange="checkImage(this, 'locShramdaanpsphoto1')" required />
        <input type="file" name="locShramdaanpsphoto2" id="locShramdaanpsphoto2" onchange="checkImage(this, 'locShramdaanpsphoto2')" required />
    </td>
     	</tr>
     	<tr>
     		<td>Plantation</td>
     		<td>
  Area (in ha.)<br><input type="text" id="plantationArea"  name="plantationArea"  autocomplete="off"  maxlength="10" 
  oninput="validateDecimal(this, 2)" required />
</td>
<!--      		<td>Area (in ha.)<br><input type="text" id="plantationArea" name="plantationArea" autocomplete="off" -->
<!-- 								maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td> -->
     		<td>No. of Agro forestry / Horticultural Plants<br><input type="text" id="nofagrohorti" name="nofagrohorti" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="plantationAreaphoto1" id="plantationAreaphoto1" onchange="checkImage(this, 'plantationAreaphoto1')" required />
        <input type="file" name="plantationAreaphoto2" id="plantationAreaphoto2" onchange="checkImage(this, 'plantationAreaphoto2')" required />
    </td>
								
     	</tr>
     	<tr>
     		<td>Award Distribution</td>
     		<td colspan=2>Number of "Watershed Margdarshaks"<br><input type="text" id="noOfwatershed" name="noOfwatershed" autocomplete="off"
								 maxlength="10" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Uploading of photographs (limit 2 photos per activity)<br>
        <input type="file" name="noOfwatershedphoto1" id="noOfwatershedphoto1" onchange="checkImage(this, 'noOfwatershedphoto1')" required />
        <input type="file" name="noOfwatershedphoto2" id="noOfwatershedphoto2" onchange="checkImage(this, 'noOfwatershedphoto2')" required />
    </td>
     	</tr>
     	

     </table>
        <div class="form-row">
				<div class="form-group col-8">
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Submit"/>
     			</div>
     		</div> 
     </div>
		</div>

     	
		</form:form>
	</div>
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>