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
<script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>


<meta charset="ISO-8859-1">
<title>Watershed Yatra Program</title>

<script type="text/javascript">

function editChancel(){
	
	
    document.saveWatershed.action="getWatershedYatraHeader";
	document.saveWatershed.method="get";
	document.saveWatershed.submit();
}



function toggleFileUpload() {
 var filmYes = document.getElementById('FilmYes').checked; 
 var fileUploadSection = document.getElementById('fileUploadSection'); 
 if (filmYes) { fileUploadSection.style.display = 'block'; 
} else
{ 
fileUploadSection.style.display = 'none'; 
} 
 }
 
function toggleFileUpload2() {
	 var shapathYes = document.getElementById('shapathYes').checked; 
	 var fileUploadSection2 = document.getElementById('fileUploadSection2'); 
	 if (shapathYes) { fileUploadSection2.style.display = 'block'; 
	} else
	{ 
	fileUploadSection2.style.display = 'none'; 
	} 
	 }
function validateDecimal(input, decimalPlaces) {
    // Allow only numbers and one decimal point
    const regex = new RegExp(`^\\d*(\\.\\d{0,${decimalPlaces}})?$`);
    if (!regex.test(input.value)) {
      input.value = input.value.slice(0, -1); // Remove the last invalid character 
    }
  }
let formSubmitted = false;
let allValid = true;
function validation() 
{
	if (formSubmitted) return false;
	allValid = true;
	var allowedFiles = [".jpg", ".jpeg",".png"];
	
	/* $district = $('#district option:selected').val();
	$block = $('#block option:selected').val();
	$grampan = $('#grampan option:selected').val();
	$village = $('#village option:selected').val();
	$datetime = $('#datetime').val();*/
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
	$manhour = $('#manhour').val();
	$plantationArea = $('#plantationArea').val();
	$nofagrohorti = $('#nofagrohorti').val();
	$noOfwatershed = $('#noOfwatershed').val();
	$remarks = $('#remarks').val();
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

	var photocheckar = document.getElementById("photocheckar");
	var photocheckjal = document.getElementById("photocheckjal");
	var photocheckfilm = document.getElementById("photocheckfilm");

	var photocheckquiz = document.getElementById("photocheckquiz");

	var photocheckcul = document.getElementById("photocheckcul");

	var photocheckbhu = document.getElementById("photocheckbhu");

	var photochecklok = document.getElementById("photochecklok");

	var photochecksdn = document.getElementById("photochecksdn");

	var photocheckplt = document.getElementById("photocheckplt");

	var photocheckawd = document.getElementById("photocheckawd");


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
	if (photocheckar.checked) {
	if($arExperience>0){
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
	}
	else{
		alert('Please enter AR Experience greater than 0');
		document.getElementById('arExperience').click();
		return false;
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
	}
	}
	if ($shapathYes === '' || typeof $shapathYes === 'undefined') {
		alert('Please select Shapath Shramdan');
		$('#shapathYes').focus();
		allValid = false;
		return false;
	}
	if (photocheckjal.checked) {
	if ($shapathYes === 'true') {
	if ($shapathYesphoto1 === '' || typeof $shapathYesphoto1 === 'undefined') {
		alert('Please upload the photo of Shapath ');
		//$('#shapathYesphoto1').focus();
		document.getElementById('shapathYesphoto1').click();
		allValid = false;
		return false;
	}
	if ($shapathYesphoto2 === '' || typeof $shapathYesphoto2 === 'undefined') {
		alert('Please upload the photo of Shapath ');
		//$('#shapathYesphoto2').focus();
		document.getElementById('shapathYesphoto2').click();
		allValid = false;
		return false;
	}
	}
	}
	if ($FilmYes === '' || typeof $FilmYes === 'undefined') {
		alert('Please select Film on Watershed Yatra');
		$('#FilmYes').focus();
		allValid = false;
		return false;
	}
	if (photocheckfilm.checked) {
	if ($FilmYes === 'true') {
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
	}
	}
	if ($quizParticipants === '' || typeof $quizParticipants === 'undefined') {
		alert('Please enter Number of People participated in Quiz');
		$('#quizParticipants').focus();
		allValid = false;
		return false;
	}
	if (photocheckquiz.checked) {
	if ($quizParticipants>0) {
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
	}
	else{
		alert('Please enter Quiz Program greater than 0 participated ');
		document.getElementById('photocheckquiz').click();
		return false;
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
	}
	}
	if ($culturalActivity === '' || typeof $culturalActivity === 'undefined') {
		alert('Please select Cultural Activity based on Watershed theme');
		$('#culturalActivity').focus();
		allValid = false;
		return false;
	}
	if (photocheckcul.checked) {
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
	if (photocheckbhu.checked) {
	if ($bhoomiWorks>0) {
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
	}
	else{
		
		alert('Please enter  Bhoomi Poojan ');
		document.getElementById('bhoomiWorks').click();
		return false;
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
	}
	}
	if ($lokWorks === '' || typeof $lokWorks === 'undefined') {
		alert('Please enter Number of Works of Lokarpan');
		$('#lokWorks').focus();
		allValid = false;
		return false;
	}
	if ($costWorks === '' || typeof $costWorks === 'undefined') {
		alert('Please enter Number of cost of Total Works of Lokarpan');
		$('#costWorks').focus();
		allValid = false;
		return false;
	}
	if (photochecklok.checked) {
	if($lokWorks>0){
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
	}
	else{
		
		alert('Please enter no. of Works Lokarpan greater than 0');
		$('#lokWorks').focus();
		return false;
		
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
	}
	}
	if ($locShramdaan === '' || typeof $locShramdaan === 'undefined') {
		alert('Please enter Number of Locations of Shramdaan');
		$('#locShramdaan').focus();
		allValid = false;
		return false;
	}
	if ($locShramdaanps === '' || typeof $locShramdaanps === 'undefined') {
		alert('Please enter Number of people participated of Shramdaan');
		$('#locShramdaanps').focus();
		allValid = false;
		return false;
	}
	if ($manhour === '' || typeof $locShramdaanps === 'undefined') {
		alert('Please enter Number of Man hour');
		$('#manhour').focus();
		allValid = false;
		return false;
	}
	if (photochecksdn.checked) {
	if ($locShramdaan>0){
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
	}
else{
		
		alert('Please enter greater than 0 of Shramdaan Number of Locations');
		$('#locShramdaan').focus();
		return false;
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
	}
	}
	if ($plantationArea === '' || typeof $plantationArea === 'undefined') {
		alert('Please enter the plantation area');
		$('#plantationArea').focus();
		allValid = false;
		return false;
	}
	if ($nofagrohorti === '' || typeof $nofagrohorti === 'undefined') {
		alert('Please enter the No. of Agro forestry / Horticultural Plants');
		$('#nofagrohorti').focus();
		allValid = false;
		return false;
	}
	if (photocheckplt.checked) {
	if ($plantationArea>0){ 
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
	}
	else{
		
		alert('Please enter Plantation Area greater than 0 ');
		$('#plantationArea').focus();
		return false;
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
	}
	}
	if ($noOfwatershed === '' || typeof $noOfwatershed === 'undefined') {
		alert('Please enter the Number of Watershed Margdarshaks');
		$('#noOfwatershed').focus();
		allValid = false;
		return false;
	}
	if (photocheckawd.checked) {
	if ($noOfwatershed>0){
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
	}
	else{
		
		alert('Please enter greater than 0 of Award Distribution Number of "Watershed Margdarshaks"');
		$('#photocheckawd').focus();
		return false;
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
	}
	}
	if (allValid) {
		if(confirm("Do you want to Update Watershed Yatra at Village Level?")) {
	    formSubmitted = true; 
		document.saveWatershed.action="updateWatershedYatraAtVillage";
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
    var fileSize = file.size; // Get the file size in bytes
    var maxFileSize = 300 * 1024; // Max size: 100KB (in bytes)
    var maxWidth = 300; // Max width in pixels
    var maxHeight = 400; // Max height in pixels

    // Check if the file is an image
    if (!fileType.startsWith('image/')) {
        alert('Only image files are allowed');
        input.value = ''; // Clear the file input
        document.getElementById(inputId).focus();
        return; // Stop further execution
    }

    // Check if the file size exceeds 100KB
    if (fileSize > maxFileSize) {
        alert('File size exceeds 300 KB. Please choose a smaller file.');
        input.value = ''; // Clear the file input
        document.getElementById(inputId).focus();
        return; // Stop further execution
    }

    var reader = new FileReader();
    reader.onload = function(e) {
        var img = new Image();
        img.onload = function() {
            var width = img.width;
            var height = img.height;

            // Check if the image dimensions exceed the max width or height
            if (width > maxWidth || height > maxHeight) {
                alert('Image dimensions exceed the allowed size of ' + maxWidth + 'x' + maxHeight + ' pixels.');
                input.value = ''; // Clear the file input
                document.getElementById(inputId).focus();
                return; // Stop further execution
            }

        };

        img.src = e.target.result;
    };

    reader.readAsDataURL(file);
}



function closePopup() {
    document.getElementById('imagePopup').style.display = 'none';
  }


function closeLargeImagePopup() {
    document.getElementById('largeImagePopup').style.display = 'none';
}

function openLargeImage(imageSrc, index, total) {
	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/WatershedYatraVillage/' + imageSrc;			//PRD
// 	document.getElementById('largeImage').src = 'https://wdcpmksy.dolr.gov.in/filepath/TESTING/vanyatradoc/WatershedYatraVillage/' + imageSrc;	//TEST
// 	document.getElementById('largeImage').src = 'resources/images/WatershedYatraVillage/' + imageSrc;												//Local
	document.getElementById('largeImagePopup').style.display = 'block';
	currentIndex = index;
	totalImages = total;
}

function closeLargeImagePopup() {
	document.getElementById('largeImagePopup').style.display = 'none';
}

function showNextImage() {
	if (currentIndex < totalImages - 1) {
		currentIndex++;
		let nextImageSrc = $('.image-container img')[currentIndex].src;
		document.getElementById('largeImage').src = nextImageSrc;
	}
}

function showPrevImage() {
	if (currentIndex > 0) {
		currentIndex--;
		let prevImageSrc = $('.image-container img')[currentIndex].src;
		document.getElementById('largeImage').src = prevImageSrc;
	}
}

function toggleUploadSection(checkboxId, inputIds) {
    const checkbox = document.getElementById(checkboxId);
    const inputs = inputIds.split(',').map(id => document.getElementById(id));
    
    const arExperience = document.getElementById('arExperience');
    const quizParticipants = document.getElementById('quizParticipants');
    const bhoomiWorks = document.getElementById('bhoomiWorks');
    const lokWorks = document.getElementById('lokWorks');
    const locShramdaan = document.getElementById('locShramdaan');
    const plantationArea = document.getElementById('plantationArea');
    const noOfwatershed = document.getElementById('noOfwatershed');
   
    // Check specific conditions based on the checkbox
    if (checkboxId === 'photocheckar' && arExperience.value === '0') {
        alert('Please ensure No. of People who availed experience are greater than 0 before uploading photographs.');
        document.getElementById('arExperience').focus();
        checkbox.checked = false;
        return;
    } 
    else if (checkboxId === 'photocheckquiz' && quizParticipants.value === '0') {
        alert('Please ensure the No. of Number of People participated in Quiz greater than 0 before uploading photographs.');
        document.getElementById('quizParticipants').focus();
        checkbox.checked = false;
        return;
    }
    else if (checkboxId === 'photocheckbhu' && bhoomiWorks.value === '0') {
        alert('Please ensure the No. of Bhoomi Poojan Works are greater than 0 before uploading photographs.');
        document.getElementById('bhoomiWorks').focus();
        checkbox.checked = false;
        return;
    }
    else if (checkboxId === 'photochecklok' && lokWorks.value === '0') {
        alert('Please ensure the No. of  Lokarpan Works is greater than 0 before uploading photographs.');
        document.getElementById('lokWorks').focus();
        checkbox.checked = false;
        return;
    }
    else if (checkboxId === 'photochecksdn' && locShramdaan.value === '0') {
        alert('Please ensure the No. of Shramdaan Locations is greater than 0 before uploading photographs.');
        document.getElementById('locShramdaan').focus();
        checkbox.checked = false;
        return;
    }
    else if (checkboxId === 'photocheckplt' && plantationArea.value === '0.00') {
        alert('Please ensure the Area (in ha.) Plantation is greater than 0.00 before uploading photographs.');
        document.getElementById('plantationArea').focus();
        checkbox.checked = false;
        return;
    }
    
    else if (checkboxId === 'photocheckawd' && noOfwatershed.value === '0') {
        alert('Please ensure the Award Distribution(Felicitation) is greater than 0 before uploading photographs.');
        document.getElementById('noOfwatershed').focus();
        checkbox.checked = false;
        return;
    }
    
    inputs.forEach(input => {
        input.disabled = !checkbox.checked;
    });
}
	
	
</script>


<style>
input[type=text] {
	width: 100px;
	
}

#imagePopup {
display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ */
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}

/* Popup content */
.popup-content {
  background-color: #fefefe;
  margin-left: 300px;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px; /* Increased max-width */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
}

/* Close button */
.close {
  color: #aaa;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 3;
}
.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}


/* Image list */
.image-container ul {
  list-style-type: none;
  padding: 30px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); /* Adjust minmax values as needed */
  gap: 10px; /* Adds equal space between images */
}

.image-container li {
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-container img {
  max-width: 100%;
  max-height: 100px;
  border-radius: 5px;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
}

#largeImagePopup {
  display: none; /* Hidden by default */
  position: fixed;
  top: 50%; /* Center the popup vertically */
  left: 50%; /* Center the popup horizontally */
  transform: translate(-50%, -50%); /* Correct centering */
  z-index: 1000;
/*   background-color: rgba(0, 0, 0, 0.6); /* Semi-transparent overlay for the background */ */
  padding: 20px;
  width: 80%; /* Set a width, but limit it to 80% of the screen */
  max-width: 1000px; /* Max width of the popup */
  border-radius: 10px;
}


/* Popup content */
.large-image-popup-content {
  background-color: #fefefe;
  width: 100%;
  height: auto;
  max-height: 80vh; /* Set a max height to avoid overflowing */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  display: flex;
  justify-content: center; /* Center the image horizontally */
  align-items: center; /* Center the image vertically */
  position: relative;
}

/* Large image */
#largeImage {
  width: 100%; /* Ensure it fits inside the popup */
  height: auto;
  max-height: 80vh; /* Restrict height to 80% of the viewport height */
  object-fit: contain; /* Ensure the aspect ratio is maintained */
}

.nav-arrow {
  color: black;
  font-size: 40px;
  font-weight: bold;
  cursor: pointer;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2;
}

#prevImage {
  left: 20px;
}

#nextImage {
  right: 20px;
}

</style>

</head>
<body>

	<div class="maindiv">
		<div class="col formheading" style="text-decoration: underline;"><h4>Update Watershed Yatra at Village Level</h4> </div>
			<label>
		<span style="color:blue;">Note:- The Image size must be under 300KB with Geo-referenced and Time-stamped.</span>
		</label>
		<form:form autocomplete="off" method="post" name="saveWatershed" id="saveWatershed" action="updateWatershedYatraAtVillage" modelAttribute="useruploadsl" enctype="multipart/form-data">
			
			<hr/>
			<c:forEach items="${dataList}" var="data" varStatus="count">
				<input type="hidden" id="watershed_yatra_id" name="watershed_yatra_id" value="${data.watershed_yatra_id}"/>
				<input type="hidden" id="datetime" name="datetime" value="${data.yatra_date}"/>
				<input type="hidden" id="district" name="district" value="${data.district}"/>
				<input type="hidden" id="block" name="block" value="${data.block}"/>
				<input type="hidden" id="grampan" name="grampan" value="${data.grampan}"/>
				<input type="hidden" id="village" name="village" value="${data.village}"/>
			
			  <div class="row">
    			<div class="form-group col-3">
      		  <label for="datetime">Date and Time: &nbsp; <c:out value="${data.date}" /></label>
       		<!--  <input type="datetime-local" name="datetime" id="datetime" class="form-control activity" style="width: 100%;" /> -->
    		</div>
			</div>
			<div class="row">
			<div class="form-group col-3">
			
				State Name: &nbsp; <c:out value="${stateName}"></c:out>
			
			</div>
    		<div class="form-group col-3">
      			<label for="district">District: </label>
      			<span class="activityError"></span>
      			 <c:out value="${data.distname}" />
      			<%-- <select class="form-control district" id="district" name="district" >
    				<option value="">--Select--</option>
    				<c:forEach items="${distList}" var="dist"> 
					<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:forEach>
    			</select> --%>
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Block: </label>
      			<span class="activityError"></span>
      			<c:out value="${data.blockname}" />
      			<!-- <select class="form-control activity" id="block" name="block" >
    				<option value="">--Select Block--</option>
    			</select> -->
    		</div>
    		<div class="form-group col-3">
    			<label for="activity">Gram Panchayat Name: </label>
      			<span class="activityError"></span>
      			<c:out value="${data.gpname}" />
      			<!-- <select class="form-control activity" id="grampan" name="grampan" >
    				<option value="">--Select Gram Panchayat Name--</option>
    			</select> -->
    		</div>
    		
    		<div class="form-group col-3">
    			<label for="activity">Village Name: </label>
      			<span class="activityError"></span>
      			<c:out value="${data.villagename}" />
      			<!-- <select class="form-control activity" id="village" name="village" >
    				<option value="">--Select Village Name--</option> 
    			</select> -->
    		</div>
    		
    		<div class="form-group col-3">
    <label for="activity">Location (Nearby/Milestone)</label>
    <input type="text" class="form-control activity" name="location" id="location" value="${data.location}" style="width: 100%; max-width: 800px;" />
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
     		<td>Male<br><input type="text" id="maleParticipants" name="maleParticipants" autocomplete="off" value="${data.male_participants}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Female<br><input type="text" id="femaleParticipants" name="femaleParticipants" autocomplete="off" value="${data.female_participants}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Ministers</td>
     		<td>Central Level<br><input type="text" id="centralMinisters" name="centralMinisters" autocomplete="off" value="${data.central_ministers}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>State Level<br><input type="text" id="stateMinisters" name="stateMinisters" autocomplete="off" value="${data.state_ministers}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<td colspan=2>Number of Member of Parliament</td>
     		<td colspan=2><input type="text" id="membersOfParliament" name="membersOfParliament" autocomplete="off" value="${data.parliament}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of Members</td>
     		<td>Legislative Assembly<br><input type="text" id="legAssemblyMembers" name="legAssemblyMembers" autocomplete="off" value="${data.assembly_members}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Legislative Council<br><input type="text" id="legCouncilMembers" name="legCouncilMembers" autocomplete="off" value="${data.council_members}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	<tr>
     		<td colspan=2>Number of other Public Representatives</td>
     		<td colspan=2><input type="text" id="publicReps" name="publicReps" autocomplete="off" value="${data.others}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	<tr>
     		<td colspan=2>Number of Government Officials</td>
     		<td colspan=2><input type="text" id="govOfficials" name="govOfficials" autocomplete="off" value="${data.gov_officials}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     	</tr>
     	
     	<tr>
     		<th colspan=4 class="text-left">Activities &nbsp;  <span style="color:red;">(Do You want to update photo click on the activities-wise check box.) </span> </th>
     	</tr>
     	<tr>
     		<td><input type="checkbox" id="photocheckar" name="photocheckar" onclick="toggleUploadSection('photocheckar', 'arExperiencephoto1,arExperiencephoto2')"/> &nbsp; AR Experience </td>
     		<td colspan=2>Number of People who availed experience<br><input type="text" id="arExperience" name="arExperience" autocomplete="off"
				value="${data.no_of_ar_experience_people}"	maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Upload Photographs<br>
        <input type="file" name="arExperiencephoto1" id="arExperiencephoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
           <input type="hidden" id="arExperiencephoto1_lat" name="arExperiencephoto1_lat">
           <input type="hidden" id="arExperiencephoto1_lng" name="arExperiencephoto1_lng">
           <input type="hidden" id="arExperiencephoto1_time" name="arExperiencephoto1_time">
        <input type="file" name="arExperiencephoto2" id="arExperiencephoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
           <input type="hidden" id="arExperiencephoto2_lat" name="arExperiencephoto2_lat">
           <input type="hidden" id="arExperiencephoto2_lng" name="arExperiencephoto2_lng">
           <input type="hidden" id="arExperiencephoto2_time" name="arExperiencephoto2_time">
           
          
    </td>
     	</tr>
     	<tr>
     		<td><input type="checkbox" id="photocheckjal" name="photocheckjal" onclick="toggleUploadSection('photocheckjal', 'shapathYesphoto1,shapathYesphoto2')"/> &nbsp; "Bhoomi aur Jal Sanrakshan" Shapath </td>
     		<c:if test="${data.bhumi_jal_sanrakshan== 'true' }">
     		<td><input type="radio" id="shapathYes" name="shapathYes" value="true" onclick="toggleFileUpload2()" checked="checked">Yes</td>
			<td><input type="radio" id="shapathNo" name="shapathYes" value="false" onclick="toggleFileUpload2()">No</td>
     		</c:if>
     		<c:if test="${data.bhumi_jal_sanrakshan== 'false' }">
     		<td><input type="radio" id="shapathYes" name="shapathYes" value="true" onclick="toggleFileUpload2()">Yes</td>
			<td><input type="radio" id="shapathNo" name="shapathYes" value="false" onclick="toggleFileUpload2()" checked="checked">No</td>
     		</c:if>
     		
     		<td id="fileUploadSection2" style="display:none;">
       Upload Photographs<br>
        <input type="file" name="shapathYesphoto1" id="shapathYesphoto1" accept="image/*" onchange="validatePhoto(this)"  disabled/>
        <input type="hidden" id="shapathYesphoto1_lat" name="shapathYesphoto1_lat">
        <input type="hidden" id="shapathYesphoto1_lng" name="shapathYesphoto1_lng">
        <input type="hidden" id="shapathYesphoto1_time" name="shapathYesphoto1_time">
        
        <input type="file" name="shapathYesphoto2" id="shapathYesphoto2" accept="image/*" onchange="validatePhoto(this)"  disabled/>
        <input type="hidden" id="shapathYesphoto2_lat" name="shapathYesphoto2_lat">
        <input type="hidden" id="shapathYesphoto2_lng" name="shapathYesphoto2_lng">
         <input type="hidden" id="shapathYesphoto2_time" name="shapathYesphoto2_time">
    </td>
     	</tr>
     	<tr>
  <td><input type="checkbox" id="photocheckfilm" name="photocheckfilm" onclick="toggleUploadSection('photocheckfilm', 'FilmYesphoto1,FilmYesphoto2')"/> &nbsp;Film on Watershed Yatra</td>
  <c:if test="${data.watershed_yatra_film== 'true' }">
  <td><input type="radio" id="FilmYes" name="FilmYes" value="true" onclick="toggleFileUpload()" checked="checked">Yes</td>
  <td><input type="radio" id="FilmNo" name="FilmYes" value="false" onclick="toggleFileUpload()">No</td>
  </c:if>
  <c:if test="${data.watershed_yatra_film== 'false' }">
  <td><input type="radio" id="FilmYes" name="FilmYes" value="true" onclick="toggleFileUpload()">Yes</td>
  <td><input type="radio" id="FilmNo" name="FilmYes" value="false" onclick="toggleFileUpload()" checked="checked">No</td>
  </c:if>
  <td id="fileUploadSection" style="display:none;">
    Upload Photographs<br>
    <input type="file" name="FilmYesphoto1" id="FilmYesphoto1" accept="image/*" onchange="validatePhoto(this)" disabled/>
  		  <input type="hidden" id="FilmYesphoto1_lat" name="FilmYesphoto1_lat">
      	  <input type="hidden" id="FilmYesphoto1_lng" name="FilmYesphoto1_lng">
       	  <input type="hidden" id="FilmYesphoto1_time" name="FilmYesphoto1_time">
         
    <input type="file" name="FilmYesphoto2" id="FilmYesphoto2" accept="image/*" onchange="validatePhoto(this)" disabled/>
    		  <input type="hidden" id="FilmYesphoto2_lat" name="FilmYesphoto2_lat">
      		  <input type="hidden" id="FilmYesphoto2_lng" name="FilmYesphoto2_lng">
       		  <input type="hidden" id="FilmYesphoto2_time" name="FilmYesphoto2_time">
  </td>
</tr>

     		<tr>
     		<td><input type="checkbox" id="photocheckquiz" name="photocheckquiz" onclick="toggleUploadSection('photocheckquiz', 'quizParticipantsphoto1,quizParticipantsphoto2')" /> &nbsp; Quiz Program</td>
     		<td colspan=2>Number of People participated in Quiz<br><input type="text" id="quizParticipants" name="quizParticipants" autocomplete="off"
						value="${data.quiz_participants}" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td>
       Upload Photographs<br>
        <input type="file" name="quizParticipantsphoto1" id="quizParticipantsphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
              <input type="hidden" id="quizParticipantsphoto1_lat" name="quizParticipantsphoto1_lat">
      		  <input type="hidden" id="quizParticipantsphoto1_lng" name="quizParticipantsphoto1_lng">
       		  <input type="hidden" id="quizParticipantsphoto1_time" name="quizParticipantsphoto1_time">
        <input type="file" name="quizParticipantsphoto2" id="quizParticipantsphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
   			  <input type="hidden" id="quizParticipantsphoto2_lat" name="quizParticipantsphoto2_lat">
      		  <input type="hidden" id="quizParticipantsphoto2_lng" name="quizParticipantsphoto2_lng">
       		  <input type="hidden" id="quizParticipantsphoto2_time" name="quizParticipantsphoto2_time">
    </td>
     	</tr>
<tr>
    <td><input type="checkbox" id="photocheckcul" name="photocheckcul" onclick="toggleUploadSection('photocheckcul', 'culturalActivityphoto1,culturalActivityphoto2')"/> &nbsp; Cultural Activity based on Watershed theme</td>
   <td colspan="2">
    Street play/folk dance/songs/Others<br>
    <div class="form-group col-8">
        <span class="activityError"></span>
        <select id="culturalActivity" name="culturalActivity" style="width: 100%; max-width: 300px;" required class="form-control activity" onchange="toggleOtherField()">
            <option value="">--Select cultural activity--</option>
            <c:forEach items="${cultMap}" var="cult"> 
            	<c:if test="${cult.key eq data.cultural_activity_id}">
                	<option value="<c:out value='${cult.key}'/>" selected="selected"><c:out value="${cult.value}" /></option>
               </c:if> 
               <c:if test="${cult.key ne data.cultural_activity_id}">
                	<option value="<c:out value='${cult.key}'/>"><c:out value="${cult.value}" /></option>
               </c:if> 
            </c:forEach>
        </select>
    </div>
<!--     <div id="otherActivityDiv" class="form-group col-8" style="display: none; margin-top: 10px;"> -->
<!--         <label for="otherActivity">Please specify:</label> -->
<!--         <input type="text1" id="otherActivity" name="otherActivity" class="form-group col-12" pla ceholder="other activity" style="max-width: 200px;"> -->
<!--     </div> -->
</td>


    <td >
        Upload Photographs<br>
        <input type="file" name="culturalActivityphoto1" id="culturalActivityphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
       				<input type="hidden" id="culturalActivityphoto1_lat" name="culturalActivityphoto1_lat">
      			    <input type="hidden" id="culturalActivityphoto1_lng" name="culturalActivityphoto1_lng">
       		       <input type="hidden" id="culturalActivityphoto1_time" name="culturalActivityphoto1_time">
   
        <input type="file" name="culturalActivityphoto2" id="culturalActivityphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
              <input type="hidden" id="culturalActivityphoto2_lat" name="culturalActivityphoto2_lat">
      		  <input type="hidden" id="culturalActivityphoto2_lng" name="culturalActivityphoto2_lng">
       		  <input type="hidden" id="culturalActivityphoto2_time" name="culturalActivityphoto2_time">
   
    </td>
</tr>
     
     	<tr>
     		<td><input type="checkbox" id="photocheckbhu" name="photocheckbhu" onclick="toggleUploadSection('photocheckbhu', 'bhoomiCostphoto1,bhoomiCostphoto2')"/> &nbsp; Bhoomi Poojan</td>
     		<td>Number of Works<br><input type="text" id="bhoomiWorks" name="bhoomiWorks" autocomplete="off" value="${data.no_works_bhoomipoojan}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
			<td>Cost of Total works (in Lakh)<br><input type="text" id="bhoomiCost" name="bhoomiCost" autocomplete="off" value="${data.tot_works_bhoomipoojan}"
								 maxlength="10" oninput="validateDecimal(this, 2)" required /></td>
								<td >
       Upload Photographs<br>
        <input type="file" name="bhoomiCostphoto1" id="bhoomiCostphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
       			 <input type="hidden" id="bhoomiCostphoto1_lat" name="bhoomiCostphoto1_lat">
      		     <input type="hidden" id="bhoomiCostphoto1_lng" name="bhoomiCostphoto1_lng">
       		    <input type="hidden" id="bhoomiCostphoto1_time" name="bhoomiCostphoto1_time">
   
        <input type="file" name="bhoomiCostphoto2" id="bhoomiCostphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
   				 <input type="hidden" id="bhoomiCostphoto2_lat" name="bhoomiCostphoto2_lat">
      	   	     <input type="hidden" id="bhoomiCostphoto2_lng" name="bhoomiCostphoto2_lng">
       		     <input type="hidden" id="bhoomiCostphoto2_time" name="bhoomiCostphoto2_time">
   
    </td>
     	</tr>
     	<tr>
     		<td><input type="checkbox" id="photochecklok" name="photochecklok" onclick="toggleUploadSection('photochecklok', 'lokWorksphoto1,lokWorksphoto2')"/> &nbsp;Lokarpan</td>
     		<td>Number of Works<br><input type="text" id="lokWorks" name="lokWorks" autocomplete="off" value="${data.no_works_lokarpan}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Cost of Total works (in Lakh)<br><input type="text" id="costWorks" name="costWorks" autocomplete="off" value="${data.tot_works_lokarpan}"
								 maxlength="10" oninput="validateDecimal(this, 2)" required /></td>
									<td >
        Upload Photographs<br>
        <input type="file" name="lokWorksphoto1" id="lokWorksphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
                 <input type="hidden" id="lokWorksphoto1_lat" name="lokWorksphoto1_lat">
      	   	     <input type="hidden" id="lokWorksphoto1_lng" name="lokWorksphoto1_lng">
       		     <input type="hidden" id="lokWorksphoto1_time" name="lokWorksphoto1_time">
   
        <input type="file" name="lokWorksphoto2" id="lokWorksphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
                 <input type="hidden" id="lokWorksphoto2_lat" name="lokWorksphoto2_lat">
      	   	     <input type="hidden" id="lokWorksphoto2_lng" name="lokWorksphoto2_lng">
       		     <input type="hidden" id="lokWorksphoto2_time" name="lokWorksphoto2_time">
    </td>
     	</tr>
     	<tr>
     		<td><input type="checkbox" id="photochecksdn" name="photochecksdn" onclick="toggleUploadSection('photochecksdn', 'locShramdaanpsphoto1,locShramdaanpsphoto2')"/> &nbsp; Shramdaan</td>
     		<td>Number of Locations<br><input type="text" id="locShramdaan" name="locShramdaan" autocomplete="off" value="${data.no_location_shramdaan}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
     		<td>Number of people participated<br><input type="text" id="locShramdaanps" name="locShramdaanps" autocomplete="off" value="${data.no_people_shramdaan}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /><br>
								 Number of Man Hours<br><input type="text" id="manhour" name="manhour" autocomplete="off" value="${data.manhour}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required />
								 </td>

	<td >Upload Photographs<br>
        <input type="file" name="locShramdaanpsphoto1" id="locShramdaanpsphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
         		<input type="hidden" id="locShramdaanpsphoto1_lat" name="locShramdaanpsphoto1_lat">
      	   	     <input type="hidden" id="locShramdaanpsphoto1_lng" name="locShramdaanpsphoto1_lng">
       		     <input type="hidden" id="locShramdaanpsphoto1_time" name="locShramdaanpsphoto1_time">
        <input type="file" name="locShramdaanpsphoto2" id="locShramdaanpsphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
     				<input type="hidden" id="locShramdaanpsphoto2_lat" name="locShramdaanpsphoto2_lat">
      	   	        <input type="hidden" id="locShramdaanpsphoto2_lng" name="locShramdaanpsphoto2_lng">
       		        <input type="hidden" id="locShramdaanpsphoto2_time" name="locShramdaanpsphoto2_time">
    </td>
     	</tr>
     	<tr>
     		<td><input type="checkbox" id="photocheckplt" name="photocheckplt" onclick="toggleUploadSection('photocheckplt', 'plantationAreaphoto1,plantationAreaphoto2')"/> &nbsp; Plantation</td>
     		<td>
  Area (in ha.)<br><input type="text" id="plantationArea"  name="plantationArea"  autocomplete="off"  maxlength="10" value="${data.area_plantation}"
  oninput="validateDecimal(this, 2)" required />
</td>
     		<td>No. of Agro forestry / Horticultural Plants (No. of Sapling)<br><input type="text" id="nofagrohorti" name="nofagrohorti" autocomplete="off"
						value="${data.no_plantation}" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Upload Photographs<br>
        <input type="file" name="plantationAreaphoto1" id="plantationAreaphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
                    <input type="hidden" id="plantationAreaphoto1_lat" name="plantationAreaphoto1_lat">
      	   	        <input type="hidden" id="plantationAreaphoto1_lng" name="plantationAreaphoto1_lng">
       		        <input type="hidden" id="plantationAreaphoto1_time" name="plantationAreaphoto1_time">
        <input type="file" name="plantationAreaphoto2" id="plantationAreaphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
   				    <input type="hidden" id="plantationAreaphoto2_lat" name="plantationAreaphoto2_lat">
      	   	        <input type="hidden" id="plantationAreaphoto2_lng" name="plantationAreaphoto2_lng">
       		        <input type="hidden" id="plantationAreaphoto2_time" name="plantationAreaphoto2_time">
    </td>
								
     	</tr>
     	<tr>
     		<td><input type="checkbox" id="photocheckawd" name="photocheckawd" onclick="toggleUploadSection('photocheckawd', 'noOfwatershedphoto1,noOfwatershedphoto2')"/> &nbsp; Award Distribution(Felicitation)</td>
     		<td colspan=2>Number of "Watershed Margdarshaks"<br><input type="text" id="noOfwatershed" name="noOfwatershed" autocomplete="off" value="${data.no_awards}"
								 maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/g,'');" required /></td>
								<td >
        Upload Photographs<br>
        <input type="file" name="noOfwatershedphoto1" id="noOfwatershedphoto1" accept="image/*" onchange="validatePhoto(this)" disabled />
        			 <input type="hidden" id="noOfwatershedphoto1_lat" name="noOfwatershedphoto1_lat">
      	   	         <input type="hidden" id="noOfwatershedphoto1_lng" name="noOfwatershedphoto1_lng">
       		         <input type="hidden" id="noOfwatershedphoto1_time" name="noOfwatershedphoto1_time">
        <input type="file" name="noOfwatershedphoto2" id="noOfwatershedphoto2" accept="image/*" onchange="validatePhoto(this)" disabled />
  					 <input type="hidden" id="noOfwatershedphoto2_lat" name="noOfwatershedphoto2_lat">
      	   	         <input type="hidden" id="noOfwatershedphoto2_lng" name="noOfwatershedphoto2_lng">
       		         <input type="hidden" id="noOfwatershedphoto2_time" name="noOfwatershedphoto2_time">
    </td>
     	</tr>
     	<tr>
   				 <td>Remarks</td>
   					 <td colspan="4">
   					  <input type="text" id="remarks" name="remarks" value="${data.remarks}" style="width: 100%; max-width: 500px;"> 
    				</td>
</tr>

     </table>
    
        <div class="form-row">
				<div class="form-group col-8"> 
				<label for="btnGetDetails"> &nbsp;</label>
     				<input type="button" class="btn btn-info" id="submitbtn" name="submitbtn" onclick="validation();"  value ="Update"/>
     				<input type="button" class="btn btn-info" id="cancelbtn" name="cancelbtn" onclick="editChancel();"  value ="Cancel"/>
     			</div>
     		</div> 
     </div>
		</div>
	<br/>
<!--      		<br/> -->
<!--      		<br/> -->
<!--      		<br/> -->
     		
     		
      </c:forEach>
     	
     	 
     	
     	
     	
     	
     	
		</form:form>
	</div> 
	
	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>