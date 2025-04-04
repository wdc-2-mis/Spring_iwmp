/******************************************Calculate Sum for Gross Croped Area******************************/
function calSum(){
	
	var niltosingle = parseFloat(document.getElementById('niltosingle').value);
	var sdcrop = parseFloat(document.getElementById('sdcrop').value);
	var cniltosingle = parseFloat(document.getElementById('cniltosingle').value);
	var csdcrop = parseFloat(document.getElementById('csdcrop').value);
	var psum = (isNaN(niltosingle)?0.0:niltosingle) + (isNaN(sdcrop)?0.0:sdcrop);
	var csum = (isNaN(cniltosingle)?0.0:cniltosingle) + (isNaN(csdcrop)?0.0:csdcrop);
	document.getElementById('totalArea').value = psum.toFixed(4);
	document.getElementById('ctotalArea').value = csum.toFixed(4);
}


/*************************************************************save and update records********************************************/
$(document).on('click', '#view', function(e){ 
	
	$diversifiedcrops = $('#diversifiedcrops').val();
	$niltosingle = $('#niltosingle').val();
	$sdcrop = $('#sdcrop').val();
	$WHSConReju = $('#WHSConReju').val();
	$soilandmoiscrops = $('#soilandmoiscrops').val();
	$degradedrainfed = $('#degradedrainfed').val();
	
	$cdiversifiedcrops = $('#cdiversifiedcrops').val();
	$cniltosingle = $('#cniltosingle').val();
	$csdcrop = $('#csdcrop').val();
	$cWHSConReju = $('#cWHSConReju').val();
	$csoilandmoiscrops = $('#csoilandmoiscrops').val();
	$cdegradedrainfed = $('#cdegradedrainfed').val();
	
	if ($diversifiedcrops == '' || $diversifiedcrops == undefined || $diversifiedcrops == null) {
			alert('Please Enter diversified crops/ change in cropping system (Ha.) for Project Area');
			$('#diversifiedcrops').focus();
			return false;
		}
	
	if ($cdiversifiedcrops == '' || $cdiversifiedcrops == undefined || $cdiversifiedcrops == null) {
			alert('Please Enter diversified crops/ change in cropping system (Ha.) for Controlled Area');
			$('#cdiversifiedcrops').focus();
			return false;
		}
		
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
		
		if ($WHSConReju == '' || $WHSConReju == undefined || $WHSConReju == null) {
			alert('Please Enter No. of Water Harvesting Structure (WHS) constructed /rejuvenated for Project Area')
			$('#WHSConReju').focus();
			return false;
		}
		
		if ($cWHSConReju == '' || $cWHSConReju == undefined || $cWHSConReju == null) {
			alert('Please Enter No. of Water Harvesting Structure (WHS) constructed /rejuvenated for Controlled Area')
			$('#cWHSConReju').focus();
			return false;
		}
		
		
		if ($soilandmoiscrops == '' || $soilandmoiscrops == undefined || $soilandmoiscrops == null) {
			alert('Please Enter Area Covered with soil and Moisture (Ha.) for Project Area')
			$('#soilandmoiscrops').focus();
			return false;
		}
		
		if ($csoilandmoiscrops == '' || $csoilandmoiscrops == undefined || $csoilandmoiscrops == null) {
			alert('Please Enter Area Covered with soil and Moisture (Ha.) for Controlled Area')
			$('#csoilandmoiscrops').focus();
			return false;
		}
		
		if ($degradedrainfed == '' || $degradedrainfed == undefined || $degradedrainfed == null) {
			alert('Please Enter Area of degraded land covered /rainfed area developed (Ha.) for Project Area');
			$('#degradedrainfed').focus();
			return false;
		}
		
		if ($cdegradedrainfed == '' || $cdegradedrainfed == undefined || $cdegradedrainfed == null) {
			alert('Please Enter Area of degraded land covered /rainfed area developed (Ha.) for Controlled Area');
			$('#cdegradedrainfed').focus();
			return false;
		}
		
		

	if (confirm("Do You Want to Save Cropped Details-2 ?")) {
		formSubmitted = true;    
		document.getElementById('croppedDetails2').action = "saveOrUpdateCroppedDetails2";
		document.getElementById('croppedDetails2').method = "post";
		document.getElementById('croppedDetails2').submit();
	}
	else{
		return false;
	}
	
	});
	