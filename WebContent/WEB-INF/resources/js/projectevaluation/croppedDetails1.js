/******************************************Calculate Sum for Gross Croped Area******************************/
function calSum(){
	
	var prekharif = parseFloat(document.getElementById('prekharif').value);
	var prerabi = parseFloat(document.getElementById('prerabi').value);
	var prethird = parseFloat(document.getElementById('prethirdCrop').value);
	var midkharif = parseFloat(document.getElementById('midkharif').value);
	var midrabi = parseFloat(document.getElementById('midrabi').value);
	var midthird = parseFloat(document.getElementById('midthirdCrop').value);
	var ckharif = parseFloat(document.getElementById('ckharif').value);
	var crabi = parseFloat(document.getElementById('crabi').value);
	var cthirdCrop = parseFloat(document.getElementById('cthirdCrop').value);
//	var ms = parseFloat(document.getElementById('midStateShare').value);
	var presum = prekharif + prerabi + prethird;
	var midsum = midkharif + midrabi + midthird;
	var csum = ckharif + crabi + cthirdCrop;
	document.getElementById('precropedArea').value = presum.toFixed(4);
	document.getElementById('midcropedArea').value = midsum.toFixed(4);
	document.getElementById('ccropedArea').value = csum.toFixed(4);
}

function diffcalSum(){
	
	var precereals = parseFloat(document.getElementById('precereals').value);
	var prepulses = parseFloat(document.getElementById('prepulses').value);
	var preoilSeed = parseFloat(document.getElementById('preoilSeed').value);
	var premillets = parseFloat(document.getElementById('premillets').value);
	var preothers = parseFloat(document.getElementById('preothers').value);
	
	var midcereals = parseFloat(document.getElementById('midcereals').value);
	var midpulses = parseFloat(document.getElementById('midpulses').value);
	var midoilSeed = parseFloat(document.getElementById('midoilSeed').value);
	var midmillets = parseFloat(document.getElementById('midmillets').value);
	var midothers = parseFloat(document.getElementById('midothers').value);
	
	var ccereals = parseFloat(document.getElementById('ccereals').value);
	var cpulses = parseFloat(document.getElementById('cpulses').value);
	var coilSeed = parseFloat(document.getElementById('coilSeed').value);
	var cmillets = parseFloat(document.getElementById('cmillets').value);
	var cothers = parseFloat(document.getElementById('cothers').value);
//	var ms = parseFloat(document.getElementById('midStateShare').value);
	var presum = precereals + prepulses + preoilSeed + premillets + preothers;
	var midsum = midcereals + midpulses + midoilSeed + midmillets + midothers;
	var csum = ccereals + cpulses + coilSeed + cmillets + cothers;
	document.getElementById('prediffCrop').value = presum.toFixed(4);
	document.getElementById('middiffCrop').value = midsum.toFixed(4);
	document.getElementById('cdiffCrop').value = csum.toFixed(4);
}


/************************************************ ***************************************************************/
$(document).on('click', '#view', function(e){ 
	
	$prekharifCrop = $('#prekharif').val();
	$prerabiCrop = $('#prerabi').val();
	$prethirdCrop = $('#prethirdCrop').val();
	$precereals = $('#precereals').val();
	$prepulses = $('#prepulses').val();
	$preoilSeed = $('#preoilSeed').val();
	$premillets = $('#premillets').val();
	$preothers = $('#preothers').val();
	$prehorticulture = $('#prehorticulture').val();
	$prenetSown = $('#prenetSown').val();
	$precropIntensity = $('#precropIntensity').val();
//	$diversifiedCrop = $('#diversifiedCrop').val();

	$midkharifCrop = $('#midkharif').val();
	$midrabiCrop = $('#midrabi').val();
	$midthirdCrop = $('#midthirdCrop').val();
	$midcereals = $('#midcereals').val();
	$midpulses = $('#midpulses').val();
	$midoilSeed = $('#midoilSeed').val();
	$midmillets = $('#midmillets').val();
	$midothers = $('#midothers').val();
	$midhorticulture = $('#midhorticulture').val();
	$midnetSown = $('#midnetSown').val();
	$midcropIntensity = $('#midcropIntensity').val();
	
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
//	$cdiversifiedCrop = $('#cdiversifiedCrop').val();
	

	if ($prekharifCrop == '' || $prekharifCrop == undefined || $prekharifCrop == null) {
		alert('Please Enter Pre Kharif Crop Area Details for Project Area.');
		$('#prekharif').focus();
		return false;
	}
	
	if ($midkharifCrop == '' || $midkharifCrop == undefined || $midkharifCrop == null) {
		alert('Please Enter Mid Kharif Crop Area Details for Project Area.');
		$('#midkharif').focus();
		return false;
	}

	if ($ckharifCrop == '' || $ckharifCrop == undefined || $ckharifCrop == null) {
		alert('Please Enter Kharif Crop Area Details for Controlled Area.');
		$('#ckharif').focus();
		return false;
	}
	
	if ($prerabiCrop == '' || $prerabiCrop == undefined || $prerabiCrop == null) {
		alert('Please Enter Pre Rabi Crop Area Details for Project Area.');
		$('#prerabi').focus();
		return false;
	}
	
	if ($midrabiCrop == '' || $midrabiCrop == undefined || $midrabiCrop == null) {
		alert('Please Enter Mid Rabi Crop Area Details for Project Area.');
		$('#midrabi').focus();
		return false;
	}

	if ($crabiCrop == '' || $crabiCrop == undefined || $crabiCrop == null) {
		alert('Please Enter Rabi Crop Area Details for Controlled Area.');
		$('#crabi').focus();
		return false;
	}
	
	if ($prethirdCrop == '' || $prethirdCrop == undefined || $prethirdCrop == null) {
		alert('Please Enter Pre Third Crop Area Details for Project Area.')
		$('#prethirdCrop').focus();
		return false;
	}
	
	if ($midthirdCrop == '' || $midthirdCrop == undefined || $midthirdCrop == null) {
		alert('Please Enter Mid Third Crop Area Details for Project Area.')
		$('#midthirdCrop').focus();
		return false;
	}
	
	if ($cthirdCrop == '' || $cthirdCrop == undefined || $cthirdCrop == null) {
		alert('Please Enter Third Crop Area Details for Controlled Area.')
		$('#cthirdCrop').focus();
		return false;
	}
	
	if ($precereals == '' || $precereals == undefined || $precereals == null) {
		alert('Please Enter Pre Cereals Crop Area Details for Project Area.')
		$('#precereals').focus();
		return false;
	}
	
	if ($midcereals == '' || $midcereals == undefined || $midcereals == null) {
		alert('Please Enter Mid Cereals Crop Area Details for Project Area.')
		$('#midcereals').focus();
		return false;
	}
	
	if ($ccereals == '' || $ccereals == undefined || $ccereals == null) {
		alert('Please Enter Cereals Crop Area Details for Controlled Area.')
		$('#ccereals').focus();
		return false;
	}
	
	if ($prepulses == '' || $prepulses == undefined || $prepulses == null) {
		alert('Please Enter Pre Pulses Crop Area Details for Project Area.');
		$('#prepulses').focus();
		return false;
	}
	
	if ($midpulses == '' || $midpulses == undefined || $midpulses == null) {
		alert('Please Enter Mid Pulses Crop Area Details for Project Area.');
		$('#midpulses').focus();
		return false;
	}
	
	if ($cpulses == '' || $cpulses == undefined || $cpulses == null) {
		alert('Please Enter Pulses Crop Area Details for Controlled Area.');
		$('#cpulses').focus();
		return false;
	}
	
	if ($preoilSeed == '' || $preoilSeed == undefined || $preoilSeed == null) {
		alert('Please Enter Pre OilSeed Crop Area Details for Project Area.');
		$('#preoilSeed').focus();
		return false;
	}
	
	if ($midoilSeed == '' || $midoilSeed == undefined || $midoilSeed == null) {
		alert('Please Enter Mid OilSeed Crop Area Details for Project Area.');
		$('#midoilSeed').focus();
		return false;
	}
	
	if ($coilSeed == '' || $coilSeed == undefined || $coilSeed == null) {
		alert('Please Enter OilSeed Crop Area Details for Controlled Area.');
		$('#coilSeed').focus();
		return false;
	}
	
	if ($premillets == '' || $premillets == undefined || $premillets == null) {
		alert('Please Enter Pre Millets Crop Area Details for Project Area.');
		$('#premillets').focus();
		return false;
	}
	
	if ($midmillets == '' || $midmillets == undefined || $midmillets == null) {
		alert('Please Enter Mid Millets Crop Area Details for Project Area.');
		$('#midmillets').focus();
		return false;
	}
	
	if ($cmillets == '' || $cmillets == undefined || $cmillets == null) {
		alert('Please Enter Millets Crop Area Details for Controlled Area.');
		$('#cmillets').focus();
		return false;
	}
	
	if ($preothers == '' || $preothers == undefined || $preothers == null) {
		alert('Please Enter Pre Others Crop Area Details for Project Area.');
		$('#preothers').focus();
		return false;
	}
	
	if ($midothers == '' || $midothers == undefined || $midothers == null) {
		alert('Please Enter Mid Others Crop Area Details for Project Area.');
		$('#midothers').focus();
		return false;
	}
	
	if ($cothers == '' || $cothers == undefined || $cothers == null) {
		alert('Please Enter Others Crop Area Details for Controlled Area.');
		$('#cothers').focus();
		return false;
	}
	
	if ($prehorticulture == '' || $prehorticulture == undefined || $prehorticulture == null) {
		alert('Please Enter Pre Horticulture Crop Area Details for Project Area.');
		$('#prehorticulture').focus();
		return false;
	}
	
	if ($midhorticulture == '' || $midhorticulture == undefined || $midhorticulture == null) {
		alert('Please Enter Mid Horticulture Crop Area Details for Project Area.');
		$('#midhorticulture').focus();
		return false;
	}
	
	if ($chorticulture == '' || $chorticulture == undefined || $chorticulture == null) {
		alert('Please Enter Horticulture Crop Area Details for Controlled Area.');
		$('#chorticulture').focus();
		return false;
	}
	
	if ($prenetSown == '' || $prenetSown == undefined || $prenetSown == null) {
		alert('Please Enter Pre Net Sown Area Details for Project Area.');
		$('#prenetSown').focus();
		return false;
	}
	
	if ($midnetSown == '' || $midnetSown == undefined || $midnetSown == null) {
		alert('Please Enter Mid Net Sown Area Details for Project Area.');
		$('#midnetSown').focus();
		return false;
	}
	
	if ($cnetSown == '' || $cnetSown == undefined || $cnetSown == null) {
		alert('Please Enter Net Sown Area Details for Controlled Area.');
		$('#cnetSown').focus();
		return false;
	}
	
	if ($precropIntensity == '' || $precropIntensity == undefined || $precropIntensity == null) {
		alert('Please Enter Pre Crop Intensity Area Details for Project Area.')
		$('#precropIntensity').focus();
		return false;
	}
	
	if ($midcropIntensity == '' || $midcropIntensity == undefined || $midcropIntensity == null) {
		alert('Please Enter Mid Crop Intensity Area Details for Project Area.')
		$('#midcropIntensity').focus();
		return false;
	}
	
	if ($ccropIntensity == '' || $ccropIntensity == undefined || $ccropIntensity == null) {
		alert('Please Enter Crop Intensity Area Details for Controlled Area.')
		$('#ccropIntensity').focus();
		return false;
	}
	
//	if ($diversifiedCrop == '' || $diversifiedCrop == undefined || $diversifiedCrop == null) {
//		alert('Please Enter Diversified Crop Area Details for Project Area.')
//		$('#diversifiedCrop').focus();
//		return false;
//	}
//	
//	if ($cdiversifiedCrop == '' || $cdiversifiedCrop == undefined || $cdiversifiedCrop == null) {
//		alert('Please Enter Diversified Crop Area Details for Controlled Area.')
//		$('#cdiversifiedCrop').focus();
//		return false;
//	}
	

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