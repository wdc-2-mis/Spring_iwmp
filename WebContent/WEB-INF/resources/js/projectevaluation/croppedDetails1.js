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
	
	var presum = (isNaN(prekharif)?0.0:prekharif) + (isNaN(prerabi)?0.0:prerabi) + (isNaN(prethird)?0.0:prethird);
	var midsum = (isNaN(midkharif)?0.0:midkharif) + (isNaN(midrabi)?0.0:midrabi) + (isNaN(midthird)?0.0:midthird);
	var csum = (isNaN(ckharif)?0.0:ckharif) + (isNaN(crabi)?0.0:crabi) + (isNaN(cthirdCrop)?0.0:cthirdCrop);
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
	var presum = (isNaN(precereals)?0.0:precereals) + (isNaN(prepulses)?0.0:prepulses) + (isNaN(preoilSeed)?0.0:preoilSeed) + (isNaN(premillets)?0.0:premillets) + (isNaN(preothers)?0.0:preothers);
	var midsum = (isNaN(midcereals)?0.0:midcereals) + (isNaN(midpulses)?0.0:midpulses) + (isNaN(midoilSeed)?0.0:midoilSeed) + (isNaN(midmillets)?0.0:midmillets) + (isNaN(midothers)?0.0:midothers);
	var csum = (isNaN(ccereals)?0.0:ccereals) + (isNaN(cpulses)?0.0:cpulses) + (isNaN(coilSeed)?0.0:coilSeed) + (isNaN(cmillets)?0.0:cmillets) + (isNaN(cothers)?0.0:cothers);
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

	$othercrop = $('#othercrop').val();
	

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
	
	if ($othercrop != '') {
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
	
	var cShg =  parseInt(document.getElementById('cShg').value);
	var cFpo =  parseInt(document.getElementById('cFpo').value);
	var cUg =  parseInt(document.getElementById('cUg').value);
	
	var psum = (isNaN(shg)?0.0:shg) + (isNaN(fpo)?0.0:fpo) + (isNaN(ug)?0.0:ug);
	var csum = (isNaN(cShg)?0.0:cShg) + (isNaN(cFpo)?0.0:cFpo) + (isNaN(cUg)?0.0:cUg);
	document.getElementById('noOfCom').value = psum;
	document.getElementById('cnoOfCom').value = csum;
}

function mpcalSum(){
	
	var mShg = parseInt(document.getElementById('mShg').value);
	var mFpo =  parseInt(document.getElementById('mFpo').value);
	var mUg =  parseInt(document.getElementById('mUg').value);
	
	var cMshg =  parseInt(document.getElementById('cMshg').value);
	var cMfpo =  parseInt(document.getElementById('cMfpo').value);
	var cMug =  parseInt(document.getElementById('cMug').value);
	
	var psum = (isNaN(mShg)?0.0:mShg) + (isNaN(mFpo)?0.0:mFpo) + (isNaN(mUg)?0.0:mUg);
	var csum = (isNaN(cMshg)?0.0:cMshg) + (isNaN(cMfpo)?0.0:cMfpo) + (isNaN(cMug)?0.0:cMug);
	document.getElementById('mnoOfCom').value = psum;
	document.getElementById('cmnoOfCom').value = csum;
}


/************************************************ ***************************************************************/
$(document).on('click', '#viewProd', function(e){ 
	
	$preMilch = $('#preMilch').val();
	$midMilch = $('#midMilch').val();
	$cMilch = $('#cMilch').val();
	
	$preFodder = $('#preFodder').val();
	$midFodder = $('#midFodder').val();
	$cFodder = $('#cFodder').val();
	
	$preRuralUrban = $('#preRuralUrban').val();
	$midRuralUrban = $('#midRuralUrban').val();
	$cRuralUrban = $('#cRuralUrban').val();
	
	$spring = $('#spring').val();
	$cSpring = $('#cSpring').val();
	
	$benefit = $('#benefit').val();
	$cBenefit = $('#cBenefit').val();
	
	$shg = $('#shg').val();
	$cShg = $('#cShg').val();
	
	$fpo = $('#fpo').val();
	$cFpo = $('#cFpo').val();
	
	$ug = $('#ug').val();
	$cUg = $('#cUg').val();
	
	$mShg = $('#mShg').val();
	$cMshg = $('#cMshg').val();
	
	$mFpo = $('#mFpo').val();
	$cMfpo = $('#cMfpo').val();
	
	$mUg = $('#mUg').val();
	$cMug = $('#cMug').val();
	
	$preTrunOverFpo = $('#preTrunOverFpo').val();
	$midTrunOverFpo = $('#midTrunOverFpo').val();
	$cTrunOverFpo = $('#cTrunOverFpo').val();
	
	$preIncomeFpo = $('#preIncomeFpo').val();
	$midIncomeFpo = $('#midIncomeFpo').val();
	$cIncomeFpo = $('#cIncomeFpo').val();
	
	$preAnnualIncomeShg = $('#preAnnualIncomeShg').val();
	$midAnnualIncomeShg = $('#midAnnualIncomeShg').val();
	$cAnnualIncomeShg = $('#cAnnualIncomeShg').val();
	

	
	if ($preMilch == '' || $preMilch == undefined || $preMilch == null) {
		alert('Please Enter Pre-Project Status of Milk Production Details for Project Area.');
		$('#preMilch').focus();
		return false;
	}
	
	if ($midMilch == '' || $midMilch == undefined || $midMilch == null) {
		alert('Please Enter Mid-Project Status of Milk Production Details for Project Area.');
		$('#midMilch').focus();
		return false;
	}
	
	if ($cMilch == '' || $cMilch == undefined || $cMilch == null) {
		alert('Please Enter Milk Production Details for Controlled Area.');
		$('#cMilch').focus();
		return false;
	}


	if ($preFodder == '' || $preFodder == undefined || $preFodder == null) {
		alert('Please Enter Pre-Project Status of Fodder Production Details for Project Area.');
		$('#preFodder').focus();
		return false;
	}
	
	if ($midFodder == '' || $midFodder == undefined || $midFodder == null) {
		alert('Please Enter Mid-Project Status of Fodder Production Details for Project Area.');
		$('#midFodder').focus();
		return false;
	}
	
	if ($cFodder == '' || $cFodder == undefined || $cFodder == null) {
		alert('Please Enter Fodder Production Details for Controlled Area.');
		$('#cFodder').focus();
		return false;
	}


	if ($preRuralUrban == '' || $preRuralUrban == undefined || $preRuralUrban == null) {
		alert('Please Enter Pre-Project Status of Annual Migration Details for Project Area.');
		$('#preRuralUrban').focus();
		return false;
	}
	
	if ($midRuralUrban == '' || $midRuralUrban == undefined || $midRuralUrban == null) {
		alert('Please Enter Mid-Project Status of Annual Migration Details for Project Area.');
		$('#midRuralUrban').focus();
		return false;
	}
	
	if ($cRuralUrban == '' || $cRuralUrban == undefined || $cRuralUrban == null) {
		alert('Please Enter Annual Migration Details for Controlled Area.');
		$('#cRuralUrban').focus();
		return false;
	}
	if ($preTrunOverFpo == '' || $preTrunOverFpo == undefined || $preTrunOverFpo == null) {
			alert('Please Enter Pre-Project Turnovers Status of FPOs Details for Project Area.');
			$('#preTrunOverFpo').focus();
			return false;
		}
		
		if ($midTrunOverFpo == '' || $midTrunOverFpo == undefined || $midTrunOverFpo == null) {
			alert('Please Enter Mid-Project Turnovers Status of FPOs Details for Project Area.');
			$('#midTrunOverFpo').focus();
			return false;
		}
		
		if ($cTrunOverFpo == '' || $cTrunOverFpo == undefined || $cTrunOverFpo == null) {
			alert('Please Enter Turnovers of FPOs Details for Controlled Area.');
			$('#cTrunOverFpo').focus();
			return false;
		}
		if ($preIncomeFpo == '' || $preIncomeFpo == undefined || $preIncomeFpo == null) {
				alert('Please Enter Pre-Project Income Status of FPO Member Details for Project Area.');
				$('#preIncomeFpo').focus();
				return false;
			}
			
			if ($midIncomeFpo == '' || $midIncomeFpo == undefined || $midIncomeFpo == null) {
				alert('Please Enter Mid-Project Income Status of FPO Member Details for Project Area.');
				$('#midIncomeFpo').focus();
				return false;
			}
			
			if ($cIncomeFpo == '' || $cIncomeFpo == undefined || $cIncomeFpo == null) {
				alert('Please Enter Income of FPO Member Details for Controlled Area.');
				$('#cIncomeFpo').focus();
				return false;
			}


			if ($preAnnualIncomeShg == '' || $preAnnualIncomeShg == undefined || $preAnnualIncomeShg == null) {
				alert('Please Enter Pre-Project Income Status of SHG Member Details for Project Area.');
				$('#preAnnualIncomeShg').focus();
				return false;
			}
			
			if ($midAnnualIncomeShg == '' || $midAnnualIncomeShg == undefined || $midAnnualIncomeShg == null) {
				alert('Please Enter Mid-Project Income Status of SHG Member Details for Project Area.');
				$('#midAnnualIncomeShg').focus();
				return false;
			}
			
			if ($cAnnualIncomeShg == '' || $cAnnualIncomeShg == undefined || $cAnnualIncomeShg == null) {
				alert('Please Enter Income of SHG Member Details for Controlled Area.');
				$('#cAnnualIncomeShg').focus();
				return false;
			}
			

	if ($spring == '' || $spring == undefined || $spring == null) {
		alert('Please Enter No. of Springs Details for Project Area.');
		$('#spring').focus();
		return false;
	}
	
	if ($cSpring == '' || $cSpring == undefined || $cSpring == null) {
		alert('Please Enter No. of Springs Details for Controlled Area.');
		$('#cSpring').focus();
		return false;
	}
	

	if ($benefit == '' || $benefit == undefined || $benefit == null) {
		alert('Please Enter No. of Person Benefitted Details for Project Area.');
		$('#benefit').focus();
		return false;
	}
	
	if ($cBenefit == '' || $cBenefit == undefined || $cBenefit == null) {
		alert('Please Enter No. of Person Benefitted Details for Controlled Area.');
		$('#cBenefit').focus();
		return false;
	}


	if ($shg == '' || $shg == undefined || $shg == null) {
		alert('Please Enter SHG Details for Project Area.');
		$('#shg').focus();
		return false;
	}
	
	if ($cShg == '' || $cShg == undefined || $cShg == null) {
		alert('Please Enter SHG Details for Controlled Area.');
		$('#cShg').focus();
		return false;
	}


	if ($fpo == '' || $fpo == undefined || $fpo == null) {
		alert('Please Enter FPO Details for Project Area.');
		$('#fpo').focus();
		return false;
	}
	
	if ($cFpo == '' || $cFpo == undefined || $cFpo == null) {
		alert('Please Enter FPO Details for Controlled Area.');
		$('#cFpo').focus();
		return false;
	}


	if ($ug == '' || $ug == undefined || $ug == null) {
		alert('Please Enter UG Details for Project Area.');
		$('#ug').focus();
		return false;
	}
	
	if ($cUg == '' || $cUg == undefined || $cUg == null) {
		alert('Please Enter UG Details for Controlled Area.');
		$('#cUg').focus();
		return false;
	}


	if ($mShg == '' || $mShg == undefined || $mShg == null) {
		alert('Please Enter No. of SHG Members Details for Project Area.');
		$('#mShg').focus();
		return false;
	}
	
	if ($cMshg == '' || $cMshg == undefined || $cMshg == null) {
		alert('Please Enter No. of SHG Members Details for Controlled Area.');
		$('#cMshg').focus();
		return false;
	}


	if ($mFpo == '' || $mFpo == undefined || $mFpo == null) {
		alert('Please Enter No. of FPO Members Details for Project Area.');
		$('#mFpo').focus();
		return false;
	}
	
	if ($cMfpo == '' || $cMfpo == undefined || $cMfpo == null) {
		alert('Please Enter No. of FPO Members Details for Controlled Area.');
		$('#cMfpo').focus();
		return false;
	}


	if ($mUg == '' || $mUg == undefined || $mUg == null) {
		alert('Please Enter No. of UG Members Details for Project Area.');
		$('#mUg').focus();
		return false;
	}
	
	if ($cMug == '' || $cMug == undefined || $cMug == null) {
		alert('Please Enter No. of UG Members Details for Controlled Area.');
		$('#cMug').focus();
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