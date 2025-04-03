/******************************************Calculate Sum for Gross Croped Area******************************/

function calSum(){
	
	var preRice = parseFloat(document.getElementById('preRice').value);
	var preWheat = parseFloat(document.getElementById('preWheat').value);
	var prePulses = parseFloat(document.getElementById('prePulses').value);
	var preOilSeed = parseFloat(document.getElementById('preOilSeed').value);
	var preMillets = parseFloat(document.getElementById('preMillets').value);
	var preOther = parseFloat(document.getElementById('preOther').value);
	
	var midRice = parseFloat(document.getElementById('midRice').value);
	var midWheat = parseFloat(document.getElementById('midWheat').value);
	var midPulses = parseFloat(document.getElementById('midPulses').value);
	var midOilSeed = parseFloat(document.getElementById('midOilSeed').value);
	var midMillets = parseFloat(document.getElementById('midMillets').value);
	var midOther = parseFloat(document.getElementById('midOther').value);
	
	var controlRice = parseFloat(document.getElementById('controlRice').value);
	var controlWheat = parseFloat(document.getElementById('controlWheat').value);
	var controlPulses = parseFloat(document.getElementById('controlPulses').value);
	var controlOilSeed = parseFloat(document.getElementById('controlOilSeed').value);
	var controlMillets = parseFloat(document.getElementById('controlMillets').value);
	var controlOther = parseFloat(document.getElementById('controlOther').value);
	
	var presum = (isNaN(preRice)?0.0:preRice) + (isNaN(preWheat)?0.0:preWheat) + (isNaN(prePulses)?0.0:prePulses) + (isNaN(preOilSeed)?0.0:preOilSeed) + (isNaN(preMillets)?0.0:preMillets) + (isNaN(preOther)?0.0:preOther);
	var midsum = (isNaN(midRice)?0.0:midRice) + (isNaN(midWheat)?0.0:midWheat) + (isNaN(midPulses)?0.0:midPulses) + (isNaN(midOilSeed)?0.0:midOilSeed) + (isNaN(midMillets)?0.0:midMillets) + (isNaN(midOther)?0.0:midOther);
	var csum = (isNaN(controlRice)?0.0:controlRice) + (isNaN(controlWheat)?0.0:controlWheat) + (isNaN(controlPulses)?0.0:controlPulses) + (isNaN(controlOilSeed)?0.0:controlOilSeed) + (isNaN(controlMillets)?0.0:controlMillets) + (isNaN(controlOther)?0.0:controlOther);
	document.getElementById('prediffCrop').value = presum.toFixed(4);
	document.getElementById('middiffCrop').value = midsum.toFixed(4);
	document.getElementById('cdiffCrop').value = csum.toFixed(4);
}


/************************************************ ***************************************************************/
$(document).on('click', '#view', function(e){ 
	
	$prePlantationCover = $('#prePlantationCover').val();
	$preRice = $('#preRice').val();
	$preWheat = $('#preWheat').val();
	$prePulses = $('#prePulses').val();
	$preOilSeed = $('#preOilSeed').val();
	$preMillets = $('#preMillets').val();
	$preOther = $('#preOther').val();
	$preCulturableWasteland = $('#preCulturableWasteland').val();
	$preProtectiveIrrigation = $('#preProtectiveIrrigation').val();

	$midPlantationCover = $('#midPlantationCover').val();
	$midRice = $('#midRice').val();
	$midWheat = $('#midWheat').val();
	$midPulses = $('#midPulses').val();
	$midOilSeed = $('#midOilSeed').val();
	$midMillets = $('#midMillets').val();
	$midOther = $('#midOther').val();
	$midCulturableWasteland = $('#midCulturableWasteland').val();
	$midProtectiveIrrigation = $('#midProtectiveIrrigation').val();
	
	$controlPlantationCover = $('#controlPlantationCover').val();
	$controlRice = $('#controlRice').val();
	$controlWheat = $('#controlWheat').val();
	$controlPulses = $('#controlPulses').val();
	$controlOilSeed = $('#controlOilSeed').val();
	$controlMillets = $('#controlMillets').val();
	$controlOther = $('#controlOther').val();
	$controlCulturableWasteland = $('#controlCulturableWasteland').val();
	$controlProtectiveIrrigation = $('#controlProtectiveIrrigation').val();

	if ($prePlantationCover == '' || $prePlantationCover == undefined || $prePlantationCover == null) {
		alert('Please Enter the Pre Area under plantation cover.');
		$('#prePlantationCover').focus();
		return false;
	}
	
	if ($midPlantationCover == '' || $midPlantationCover == undefined || $midPlantationCover == null) {
		alert('Please Enter the Mid Area under plantation cover.');
		$('#midkharif').focus();
		return false;
	}

	if ($controlPlantationCover == '' || $controlPlantationCover == undefined || $controlPlantationCover == null) {
		alert('Please Enter the Controlled Area under plantation cover.');
		$('#controlPlantationCover').focus();
		return false;
	}
	
	if ($preRice == '' || $preRice == undefined || $preRice == null) {
		alert('Please Enter Pre Rice Area.');
		$('#preRice').focus();
		return false;
	}
	
	if ($midRice == '' || $midRice == undefined || $midRice == null) {
		alert('Please Enter Mid Rice Area.');
		$('#midRice').focus();
		return false;
	}

	if ($controlRice == '' || $controlRice == undefined || $controlRice == null) {
		alert('Please Enter Rice Controlled Area.');
		$('#controlRice').focus();
		return false;
	}
	
	if ($preWheat == '' || $preWheat == undefined || $preWheat == null) {
		alert('Please Enter Pre Wheat Area.')
		$('#preWheat').focus();
		return false;
	}
	
	if ($midWheat == '' || $midWheat == undefined || $midWheat == null) {
		alert('Please Enter Mid Wheat Area.')
		$('#midWheat').focus();
		return false;
	}
	
	if ($controlWheat == '' || $controlWheat == undefined || $controlWheat == null) {
		alert('Please Enter Wheat Controlled Area.')
		$('#controlWheat').focus();
		return false;
	}
	
	if ($prePulses == '' || $prePulses == undefined || $prePulses == null) {
		alert('Please Enter Pre Pulses Area.')
		$('#prePulses').focus();
		return false;
	}
	
	if ($midPulses == '' || $midPulses == undefined || $midPulses == null) {
		alert('Please Enter Mid Pulses Area.')
		$('#midPulses').focus();
		return false;
	}
	
	if ($controlPulses == '' || $controlPulses == undefined || $controlPulses == null) {
		alert('Please Enter Pulses Controlled Area.')
		$('#controlPulses').focus();
		return false;
	}
	
	if ($preOilSeed == '' || $preOilSeed == undefined || $preOilSeed == null) {
		alert('Please Enter Pre Oil Seeds Area.');
		$('#preOilSeed').focus();
		return false;
	}
	
	if ($midOilSeed == '' || $midOilSeed == undefined || $midOilSeed == null) {
		alert('Please Enter Mid Oil Seeds Area.');
		$('#midOilSeed').focus();
		return false;
	}
	
	if ($controlOilSeed == '' || $controlOilSeed == undefined || $controlOilSeed == null) {
		alert('Please Enter Oil Seeds Controlled Area.');
		$('#controlOilSeed').focus();
		return false;
	}
	
	if ($preMillets == '' || $preMillets == undefined || $preMillets == null) {
		alert('Please Enter Pre Millets Area.');
		$('#preMillets').focus();
		return false;
	}
	
	if ($midMillets == '' || $midMillets == undefined || $midMillets == null) {
		alert('Please Enter Mid Millets Area.');
		$('#midMillets').focus();
		return false;
	}
	
	if ($controlMillets == '' || $controlMillets == undefined || $controlMillets == null) {
		alert('Please Enter Millets Controlled Area.');
		$('#controlMillets').focus();
		return false;
	}
	
	if ($preOther == '' || $preOther == undefined || $preOther == null) {
		alert('Please Enter Pre Others Crops Area.');
		$('#preOther').focus();
		return false;
	}
	
	if ($midOther == '' || $midOther == undefined || $midOther == null) {
		alert('Please Enter Mid Others Crops Area.');
		$('#midOther').focus();
		return false;
	}
	
	if ($controlOther == '' || $controlOther == undefined || $controlOther == null) {
		alert('Please Enter Others Crop Controlled Area.');
		$('#controlOther').focus();
		return false;
	}
	
	if ($preCulturableWasteland == '' || $preCulturableWasteland == undefined || $preCulturableWasteland == null) {
		alert('Please Enter Pre Area of culturable wasteland.');
		$('#preCulturableWasteland').focus();
		return false;
	}
	
	if ($midCulturableWasteland == '' || $midCulturableWasteland == undefined || $midCulturableWasteland == null) {
		alert('Please Enter Mid Area of culturable wasteland.');
		$('#midCulturableWasteland').focus();
		return false;
	}
	
	if ($controlCulturableWasteland == '' || $controlCulturableWasteland == undefined || $controlCulturableWasteland == null) {
		alert('Please Enter Area of culturable wasteland for Controlled Area.');
		$('#controlCulturableWasteland').focus();
		return false;
	}
	
	if ($preProtectiveIrrigation == '' || $preProtectiveIrrigation == undefined || $preProtectiveIrrigation == null) {
		alert('Please Enter Pre Area under protective Irrigation.');
		$('#preProtectiveIrrigation').focus();
		return false;
	}
	
	if ($midProtectiveIrrigation == '' || $midProtectiveIrrigation == undefined || $midProtectiveIrrigation == null) {
		alert('Please Enter Mid Area under protective Irrigation.');
		$('#midProtectiveIrrigation').focus();
		return false;
	}
	
	if ($controlProtectiveIrrigation == '' || $controlProtectiveIrrigation == undefined || $controlProtectiveIrrigation == null) {
		alert('Please Enter Area under protective Irrigation for Controlled Area.');
		$('#controlProtectiveIrrigation').focus();
		return false;
	}
	

	if (confirm("Do You Want to Save Cropped Details-3 ?")) {
		formSubmitted = true;    ////    saveprojectProfile
		document.getElementById('croppedDetails3').action = "saveOrUpdateCroppedDetails3";
		document.getElementById('croppedDetails3').method = "post";
		document.getElementById('croppedDetails3').submit();
	}
	else{
		return false;
	}
	
	});