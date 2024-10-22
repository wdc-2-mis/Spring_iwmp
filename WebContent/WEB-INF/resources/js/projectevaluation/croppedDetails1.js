/******************************************Calculate Sum for Gross Croped Area******************************/
function calSum(){
	
	var kharif = parseFloat(document.getElementById('kharif').value);
	var rabi = parseFloat(document.getElementById('rabi').value);
	var third = parseFloat(document.getElementById('thirdCrop').value);
	var ckharif = parseFloat(document.getElementById('ckharif').value);
	var crabi = parseFloat(document.getElementById('crabi').value);
	var cthirdCrop = parseFloat(document.getElementById('cthirdCrop').value);
//	var ms = parseFloat(document.getElementById('midStateShare').value);
	var psum = kharif + rabi + third;
	var csum = ckharif + crabi + cthirdCrop;
	document.getElementById('cropedArea').value = psum.toFixed(4);
	document.getElementById('ccropedArea').value = csum.toFixed(4);
}

function diffcalSum(){
	
	var cereals = parseFloat(document.getElementById('cereals').value);
	var pulses = parseFloat(document.getElementById('pulses').value);
	var oilSeed = parseFloat(document.getElementById('oilSeed').value);
	var millets = parseFloat(document.getElementById('millets').value);
	var others = parseFloat(document.getElementById('others').value);
	var ccereals = parseFloat(document.getElementById('ccereals').value);
	var cpulses = parseFloat(document.getElementById('cpulses').value);
	var coilSeed = parseFloat(document.getElementById('coilSeed').value);
	var cmillets = parseFloat(document.getElementById('cmillets').value);
	var cothers = parseFloat(document.getElementById('cothers').value);
//	var ms = parseFloat(document.getElementById('midStateShare').value);
	var psum = cereals + pulses + oilSeed + millets + others;
	var csum = ccereals + cpulses + coilSeed + cmillets + cothers;
	document.getElementById('diffCrop').value = psum.toFixed(4);
	document.getElementById('cdiffCrop').value = csum.toFixed(4);
}


/************************************************ ***************************************************************/
$(document).on('click', '#view', function(e){ 
	
	$kharifCrop = $('#kharif').val();
	$rabiCrop = $('#rabi').val();
	$thirdCrop = $('#thirdCrop').val();
	$cereals = $('#cereals').val();
	$pulses = $('#pulses').val();
	$oilSeed = $('#oilSeed').val();
	$millets = $('#millets').val();
	$others = $('#others').val();
	$horticulture = $('#horticulture').val();
	$netSown = $('#netSown').val();
	$cropIntensity = $('#cropIntensity').val();
	$diversifiedCrop = $('#diversifiedCrop').val();
	
	$ckharifCrop = $('#ckharif').val();
	$crabiCrop = $('#crabi').val();
	$cthirdCrop = $('#cthirdCrop').val();
	$ccereals = $('#ccereals').val();
	$cpulses = $('#cpulses').val();
	$coilSeed = $('#coilSeed').val();
	$cmillets = $('#cmillets').val();
	$cothers = $('#cothers').val();
	$chorticulture = $('#chorticulture').val();
	$cnetSown = $('#cnetSown').val();
	$ccropIntensity = $('#ccropIntensity').val();
	$cdiversifiedCrop = $('#cdiversifiedCrop').val();
	

	if ($kharifCrop == '' || $kharifCrop == undefined || $kharifCrop == null) {
		alert('Please Enter Kharif Crop Area Details for Project Area.');
		$('#kharif').focus();
		return false;
	}

	if ($ckharifCrop == '' || $ckharifCrop == undefined || $ckharifCrop == null) {
		alert('Please Enter Kharif Crop Area Details for Controlled Area.');
		$('#ckharif').focus();
		return false;
	}
	
	if ($rabiCrop == '' || $rabiCrop == undefined || $rabiCrop == null) {
		alert('Please Enter Rabi Crop Area Details for Project Area.');
		$('#rabi').focus();
		return false;
	}

	if ($crabiCrop == '' || $crabiCrop == undefined || $crabiCrop == null) {
		alert('Please Enter Rabi Crop Area Details for Controlled Area.');
		$('#crabi').focus();
		return false;
	}
	
	if ($thirdCrop == '' || $thirdCrop == undefined || $thirdCrop == null) {
		alert('Please Enter Third Crop Area Details for Project Area.')
		$('#thirdCrop').focus();
		return false;
	}
	
	if ($cthirdCrop == '' || $cthirdCrop == undefined || $cthirdCrop == null) {
		alert('Please Enter Third Crop Area Details for Controlled Area.')
		$('#cthirdCrop').focus();
		return false;
	}
	
	
	if ($cereals == '' || $cereals == undefined || $cereals == null) {
		alert('Please Enter Cereals Crop Area Details for Project Area.')
		$('#cereals').focus();
		return false;
	}
	
	if ($ccereals == '' || $ccereals == undefined || $ccereals == null) {
		alert('Please Enter Cereals Crop Area Details for Controlled Area.')
		$('#ccereals').focus();
		return false;
	}
	
	if ($pulses == '' || $pulses == undefined || $pulses == null) {
		alert('Please Enter Pulses Crop Area Details for Project Area.');
		$('#pulses').focus();
		return false;
	}
	
	if ($cpulses == '' || $cpulses == undefined || $cpulses == null) {
		alert('Please Enter Pulses Crop Area Details for Controlled Area.');
		$('#cpulses').focus();
		return false;
	}
	
	if ($oilSeed == '' || $oilSeed == undefined || $oilSeed == null) {
		alert('Please Enter OilSeed Crop Area Details for Project Area.');
		$('#oilSeed').focus();
		return false;
	}
	
	if ($coilSeed == '' || $coilSeed == undefined || $coilSeed == null) {
		alert('Please Enter OilSeed Crop Area Details for Controlled Area.');
		$('#coilSeed').focus();
		return false;
	}
	
	if ($millets == '' || $millets == undefined || $millets == null) {
		alert('Please Enter Millets Crop Area Details for Project Area.');
		$('#millets').focus();
		return false;
	}
	
	if ($cmillets == '' || $cmillets == undefined || $cmillets == null) {
		alert('Please Enter Millets Crop Area Details for Controlled Area.');
		$('#cmillets').focus();
		return false;
	}
	
	if ($others == '' || $others == undefined || $others == null) {
		alert('Please Enter Others Crop Area Details for Project Area.');
		$('#others').focus();
		return false;
	}
	
	if ($cothers == '' || $cothers == undefined || $cothers == null) {
		alert('Please Enter Others Crop Area Details for Controlled Area.');
		$('#cothers').focus();
		return false;
	}
	
	if ($horticulture == '' || $horticulture == undefined || $horticulture == null) {
		alert('Please Enter Horticulture Crop Area Details for Project Area.');
		$('#horticulture').focus();
		return false;
	}
	
	if ($chorticulture == '' || $chorticulture == undefined || $chorticulture == null) {
		alert('Please Enter Horticulture Crop Area Details for Controlled Area.');
		$('#chorticulture').focus();
		return false;
	}
	
	if ($netSown == '' || $netSown == undefined || $netSown == null) {
		alert('Please Enter Net Sown Area Details for Project Area.');
		$('#netSown').focus();
		return false;
	}
	
	if ($cnetSown == '' || $cnetSown == undefined || $cnetSown == null) {
		alert('Please Enter Net Sown Area Details for Controlled Area.');
		$('#cnetSown').focus();
		return false;
	}
	
	if ($cropIntensity == '' || $cropIntensity == undefined || $cropIntensity == null) {
		alert('Please Enter Crop Intensity Area Details for Project Area.')
		$('#cropIntensity').focus();
		return false;
	}
	
	if ($ccropIntensity == '' || $ccropIntensity == undefined || $ccropIntensity == null) {
		alert('Please Enter Crop Intensity Area Details for Controlled Area.')
		$('#ccropIntensity').focus();
		return false;
	}
	
	if ($diversifiedCrop == '' || $diversifiedCrop == undefined || $diversifiedCrop == null) {
		alert('Please Enter Diversified Crop Area Details for Project Area.')
		$('#diversifiedCrop').focus();
		return false;
	}
	
	if ($cdiversifiedCrop == '' || $cdiversifiedCrop == undefined || $cdiversifiedCrop == null) {
		alert('Please Enter Diversified Crop Area Details for Controlled Area.')
		$('#cdiversifiedCrop').focus();
		return false;
	}
	

	if (confirm("Do You Want to Save Cropped Details-1 ?")) {
		formSubmitted = true;    ////    saveprojectProfile
		document.getElementById('croppedDetails1').action = "saveOrUpdateCroppedDetails";
		document.getElementById('croppedDetails1').method = "post";
		document.getElementById('croppedDetails1').submit();
	}
	else{
		return false;
	}
	
	});
	
	
/******************************************Calculate Sum for Community Based Organization******************************/
function pcalSum(){
	
	var shg =  parseInt(document.getElementById('shg').value);
	var fpo =  parseInt(document.getElementById('fpo').value);
	var ug =  parseInt(document.getElementById('ug').value);
	
	var cshg =  parseInt(document.getElementById('cshg').value);
	var cfpo =  parseInt(document.getElementById('cfpo').value);
	var cug =  parseInt(document.getElementById('cug').value);
	var psum = shg + fpo + ug;
	var csum = cshg + cfpo + cug;
	document.getElementById('noOfCom').value = psum;
	document.getElementById('cnoOfCom').value = csum;
}

function mpcalSum(){
	
	var mshg = parseInt(document.getElementById('mshg').value);
	var mfpo =  parseInt(document.getElementById('mfpo').value);
	var mug =  parseInt(document.getElementById('mug').value);
	
	var cmshg =  parseInt(document.getElementById('cmshg').value);
	var cmfpo =  parseInt(document.getElementById('cmfpo').value);
	var cmug =  parseInt(document.getElementById('cmug').value);
	
	var psum = mshg + mfpo + mug;
	var csum = cmshg + cmfpo + cmug;
	document.getElementById('mnoOfCom').value = psum;
	document.getElementById('cmnoOfCom').value = csum;
}


/************************************************ ***************************************************************/
$(document).on('click', '#viewProd', function(e){ 
	
	$milch = $('#milch').val();
	$fodder = $('#fodder').val();
	$ruralUrban = $('#ruralUrban').val();
	$spring = $('#spring').val();
	$benefit = $('#benefit').val();
	$shg = $('#shg').val();
	$fpo = $('#fpo').val();
	$ug = $('#ug').val();
	$mshg = $('#mshg').val();
	$mfpo = $('#mfpo').val();
	$mug = $('#mug').val();
	$trunoverFpo = $('#trunoverFpo').val();
	$incomeFpo = $('#incomeFpo').val();
	$annualIncomeShg = $('#annualIncomeShg').val();
	
	$cmilch = $('#cmilch').val();
	$cfodder = $('#cfodder').val();
	$cruralUrban = $('#cruralUrban').val();
	$cspring = $('#cspring').val();
	$cbenefit = $('#cbenefit').val();
	$cshg = $('#cshg').val();
	$cfpo = $('#cfpo').val();
	$cug = $('#cug').val();
	$cmshg = $('#cmshg').val();
	$cmfpo = $('#cmfpo').val();
	$cmug = $('#cmug').val();
	$ctrunoverFpo = $('#ctrunoverFpo').val();
	$cincomeFpo = $('#cincomeFpo').val();
	$cannualIncomeShg = $('#cannualIncomeShg').val();
	
	if ($milch == '' || $milch == undefined || $milch == null) {
		alert('Please Enter Milk Production Details for Project Area.');
		$('#milch').focus();
		return false;
	}
	
	if ($cmilch == '' || $cmilch == undefined || $cmilch == null) {
		alert('Please Enter Milk Production Details for Controlled Area.');
		$('#cmilch').focus();
		return false;
	}

	if ($fodder == '' || $fodder == undefined || $fodder == null) {
		alert('Please Enter fodder Production Details for Project Area.');
		$('#fodder').focus();
		return false;
	}
	
	if ($cfodder == '' || $cfodder == undefined || $cfodder == null) {
		alert('Please Enter fodder Production Details for Controlled Area.');
		$('#cfodder').focus();
		return false;
	}

	if ($ruralUrban == '' || $ruralUrban == undefined || $ruralUrban == null) {
		alert('Please Enter Annual Migration Details for Project Area.');
		$('#ruralUrban').focus();
		return false;
	}
	
	if ($cruralUrban == '' || $cruralUrban == undefined || $cruralUrban == null) {
		alert('Please Enter Annual Migration Details for Controlled Area.');
		$('#cruralUrban').focus();
		return false;
	}

	if ($spring == '' || $spring == undefined || $spring == null) {
		alert('Please Enter No. of Springs Details for Project Area.');
		$('#spring').focus();
		return false;
	}
	
	if ($cspring == '' || $cspring == undefined || $cspring == null) {
		alert('Please Enter No. of Springs Details for Controlled Area.');
		$('#cspring').focus();
		return false;
	}

	if ($benefit == '' || $benefit == undefined || $benefit == null) {
		alert('Please Enter No. of Person Benefitted Details for Project Area.');
		$('#benefit').focus();
		return false;
	}
	
	if ($cbenefit == '' || $cbenefit == undefined || $cbenefit == null) {
		alert('Please Enter No. of Person Benefitted Details for Controlled Area.');
		$('#cbenefit').focus();
		return false;
	}

	if ($shg == '' || $shg == undefined || $shg == null) {
		alert('Please Enter SHG Details for Project Area.');
		$('#shg').focus();
		return false;
	}
	
	if ($cshg == '' || $cshg == undefined || $cshg == null) {
		alert('Please Enter SHG Details for Controlled Area.');
		$('#cshg').focus();
		return false;
	}

	if ($fpo == '' || $fpo == undefined || $fpo == null) {
		alert('Please Enter FPO Details for Project Area.');
		$('#fpo').focus();
		return false;
	}
	
	if ($cfpo == '' || $cfpo == undefined || $cfpo == null) {
		alert('Please Enter FPO Details for Controlled Area.');
		$('#cfpo').focus();
		return false;
	}

	if ($ug == '' || $ug == undefined || $ug == null) {
		alert('Please Enter UG Details for Project Area.');
		$('#ug').focus();
		return false;
	}
	
	if ($cug == '' || $cug == undefined || $cug == null) {
		alert('Please Enter UG Details for Controlled Area.');
		$('#cug').focus();
		return false;
	}

	if ($mshg == '' || $mshg == undefined || $mshg == null) {
		alert('Please Enter No. of SHG Members Details for Project Area.');
		$('#mshg').focus();
		return false;
	}
	
	if ($cmshg == '' || $cmshg == undefined || $cmshg == null) {
		alert('Please Enter No. of SHG Members Details for Controlled Area.');
		$('#cmshg').focus();
		return false;
	}

	if ($mfpo == '' || $mfpo == undefined || $mfpo == null) {
		alert('Please Enter No. of FPO Members Details for Project Area.');
		$('#mfpo').focus();
		return false;
	}
	
	if ($cmfpo == '' || $cmfpo == undefined || $cmfpo == null) {
		alert('Please Enter No. of FPO Members Details for Controlled Area.');
		$('#cmfpo').focus();
		return false;
	}

	if ($mug == '' || $mug == undefined || $mug == null) {
		alert('Please Enter No. of UG Members Details for Project Area.');
		$('#mug').focus();
		return false;
	}
	
	if ($cmug == '' || $cmug == undefined || $cmug == null) {
		alert('Please Enter No. of UG Members Details for Controlled Area.');
		$('#cmug').focus();
		return false;
	}

	if ($trunoverFpo == '' || $trunoverFpo == undefined || $trunoverFpo == null) {
		alert('*Please Enter Turnovers of FPOs Details for Project Area.');
		$('#trunoverFpo').focus();
		return false;
	}
	
	if ($ctrunoverFpo == '' || $ctrunoverFpo == undefined || $ctrunoverFpo == null) {
		alert('*Please Enter Turnovers of FPOs Details for Controlled Area.');
		$('#ctrunoverFpo').focus();
		return false;
	}

	if ($incomeFpo == '' || $incomeFpo == undefined || $incomeFpo == null) {
		alert('Please Enter Income of FPO Member Details for Project Area.');
		$('#incomeFpo').focus();
		return false;
	}
	
	if ($cincomeFpo == '' || $cincomeFpo == undefined || $cincomeFpo == null) {
		alert('Please Enter Income of FPO Member Details for Controlled Area.');
		$('#cincomeFpo').focus();
		return false;
	}

	if ($annualIncomeShg == '' || $annualIncomeShg == undefined || $annualIncomeShg == null) {
		alert('Please Enter Income of SHG Member Details for Project Area.');
		$('#annualIncomeShg').focus();
		return false;
	}
	
	if ($cannualIncomeShg == '' || $cannualIncomeShg == undefined || $cannualIncomeShg == null) {
		alert('Please Enter Income of SHG Member Details for Controlled Area.');
		$('#cannualIncomeShg').focus();
		return false;
	}
	
	if (confirm("Do You Want to Save Production Details ?")) {
		formSubmitted = true;    ////    saveprojectProfile
		document.getElementById('productionDetails').action = "saveOrUpdateProductionDetails";
		document.getElementById('productionDetails').method = "post";
		document.getElementById('productionDetails').submit();
	}
	else{
		return false;
	}
	
	});