/******************************************Calculate Sum for Gross Croped Area******************************/
function calSum(){
	
	var niltosingle = parseFloat(document.getElementById('niltosingle').value);
	var sdcrop = parseFloat(document.getElementById('sdcrop').value);
	var cniltosingle = parseFloat(document.getElementById('cniltosingle').value);
	var csdcrop = parseFloat(document.getElementById('csdcrop').value);
	var psum = niltosingle + sdcrop;
	var csum = cniltosingle + csdcrop;
	document.getElementById('totalArea').value = psum.toFixed(4);
	document.getElementById('ctotalArea').value = csum.toFixed(4);
}

function diffcalSum(){
	
	var rice = parseFloat(document.getElementById('rice').value);
	var wheat = parseFloat(document.getElementById('wheat').value);
	var pulses = parseFloat(document.getElementById('pulses').value);
	var millets = parseFloat(document.getElementById('millets').value);
	var oilseed = parseFloat(document.getElementById('oilseed').value);
	var others = parseFloat(document.getElementById('others').value);
	var crice = parseFloat(document.getElementById('crice').value);
	var cwheat = parseFloat(document.getElementById('cwheat').value);
	var cpulses = parseFloat(document.getElementById('cpulses').value);
	var cmillets = parseFloat(document.getElementById('cmillets').value);
	var coilSeed = parseFloat(document.getElementById('coilseed').value);
	var cothers = parseFloat(document.getElementById('cothers').value);
//	var ms = parseFloat(document.getElementById('midStateShare').value);
	var psum = rice + wheat+ pulses + millets + oilseed + others;
	var csum = crice + cwheat+ cpulses + cmillets + coilSeed + cothers;
	
	document.getElementById('majorCrop').value = psum.toFixed(4);
	document.getElementById('cmajorCrop').value = csum.toFixed(4);
}

/*************************************************************save and update records********************************************/
$(document).on('click', '#view', function(e){ 
	$niltosingle = $('#niltosingle').val();
	$sdcrop = $('#sdcrop').val();
	$plantation = $('#plantation').val();
	$rice = $('#rice').val();
	$wheat = $('#wheat').val();
	$pulses = $('#pulses').val();
	$millets = $('#millets').val();
	$oilseed = $('#oilseed').val();
	$others = $('#others').val();
	
	$cniltosingle = $('#cniltosingle').val();
	$csdcrop = $('#csdcrop').val();
	$cplantation = $('#cplantation').val();
	$crice = $('#crice').val();
	$cwheat = $('#cwheat').val();
	$cpulses = $('#cpulses').val();
	$cmillets = $('#cmillets').val();
	$coilseed = $('#coilseed').val();
	$cothers = $('#cothers').val();
	
	
	if ($niltosingle == '' || $niltosingle == undefined || $niltosingle == null) {
			alert('Please Enter Nil to single crop(ha.) for Project Area');
			$('#niltosingle').focus();
			return false;
		}

		if ($cniltosingle == '' || $cniltosingle == undefined || $cniltosingle == null) {
			alert('Please Enter Nil to single crop(ha.) for Controlled Area');
			$('#cniltosingle').focus();
			return false;
		}
		
		if ($sdcrop == '' || $sdcrop == undefined || $sdcrop == null) {
			alert('Please Enter Single to double or more crop(ha.) for Project Area');
			$('#sdcrop').focus();
			return false;
		}

		if ($csdcrop == '' || $csdcrop == undefined || $csdcrop == null) {
			alert('Please Enter Single to double or more crop(ha.) for Controlled Area');
			$('#csdcrop').focus();
			return false;
		}
		
		if ($plantation == '' || $plantation == undefined || $plantation == null) {
			alert('Please Enter Area under plantation cover for Project Area')
			$('#plantation').focus();
			return false;
		}
		
		if ($cplantation == '' || $cplantation == undefined || $cplantation == null) {
			alert('Please Enter Area under plantation cover for Controlled Area')
			$('#cplantation').focus();
			return false;
		}
		
		
		if ($rice == '' || $rice == undefined || $rice == null) {
			alert('Please Enter Rice Details for Project Area')
			$('#rice').focus();
			return false;
		}
		
		if ($crice == '' || $crice == undefined || $crice == null) {
			alert('Please Enter Rice Details for Controlled Area')
			$('#crice').focus();
			return false;
		}
		
		if ($wheat == '' || $wheat == undefined || $wheat == null) {
			alert('Please Enter Wheat Details for Project Area');
			$('#wheat').focus();
			return false;
		}
		
		if ($cwheat == '' || $cwheat == undefined || $cwheat == null) {
			alert('Please Enter Wheat Details for Controlled Area');
			$('#cwheat').focus();
			return false;
		}
		
		if ($pulses == '' || $pulses == undefined || $pulses == null) {
			alert('Please Enter Pulses Details for Project Area');
			$('#pulses').focus();
			return false;
		}
		
	if ($cpulses == '' || $cpulses == undefined || $cpulses == null) {
		alert('Please Enter Pulses Details for Controlled Area');
		$('#cpulses').focus();
		return false;
	}
	if ($millets == '' || $millets == undefined || $millets == null) {
		alert('Please Enter Millets Details for Project Area');
		$('#millets').focus();
		return false;
	}

	if ($cmillets == '' || $cmillets == undefined || $cmillets == null) {
		alert('Please Enter Millets Details for Controlled Area');
		$('#cmillets').focus();
		return false;
	}
	if ($oilseed == '' || $oilseed == undefined || $oilseed == null) {
		alert('Please Enter Oil Seed  Details for Project Area');
		$('#oilseed').focus();
		return false;
	}
						
	if ($coilseed == '' || $coilseed == undefined || $coilseed == null) {
		alert('Please Enter Oil Seed  Area Details for Controlled Area');
		$('#coilseed').focus();
		return false;
	}
	
	if ($others == '' || $others == undefined || $others == null) {
		alert('Please Enter Others(Specify name of the crop) Details for Project Area');
		$('#others').focus();
		return false;
	}

	if ($cothers == '' || $cothers == undefined || $cothers == null) {
		alert('Please Enter Others(Specify name of the crop) Details for Controlled Area');
		$('#cothers').focus();
		return false;
	}

	if (confirm("Do You Want to Save Cropped Details-2 ?")) {
		formSubmitted = true;    ////    saveprojectProfile
		document.getElementById('croppedDetails2').action = "saveOrUpdateCroppedDetails2";
		document.getElementById('croppedDetails2').method = "post";
		document.getElementById('croppedDetails2').submit();
	}
	else{
		return false;
	}
	
	});
	