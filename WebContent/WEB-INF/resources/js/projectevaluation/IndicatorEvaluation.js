/**
 * 
 */

function selectEval() {
    // Collect values from form inputs
	var profile_id = $('#profileid').val();
	var fromno = $('#fromno').val();
    var admiMechanism = $('#am').val(); // Administrative Mechanism
    var admiMechanismRemark = $('#amd').val(); // Administrative Mechanism Remark
    var dprSlna = $('input[name="dpr"]:checked').val(); // DPR approval status
    var dprSlnaRemark = $('#dprremark').val(); // DPR Remark
    var allManpower = $('input[name="mp"]:checked').val(); // Manpower status
    var allManpowerRemark = $('#mpremark').val(); // Manpower Remark
    var wcdc = $('#wdc').val(); // WCDC Level
    var wcdcRemark = $('#wdcd').val(); // WCDC Remark
    var pia = $('#pi').val(); // PIA Level
    var piaRemark = $('#pid').val(); // PIA Remark
    var wc = $('#wc').val(); // Watershed Committee Level
    var wcRemark = $('#wcd').val(); // Watershed Committee Remark
	
//	alert(allManpower+'dfdsf')
	$flag=true;
	if(wcdc==='')
		wcdc=0;
	
	if(pia==='')
		pia=0;
		
	if(wc==='')
		wc=0;


if (allManpower === 'true') {
    if (wcdc === '' || wcdc === undefined || wcdc === null || wcdc===0) {
        alert('Please fill wcdc Details');
        $flag = false; 
        return false;
    }
	if (pia === '' || pia === undefined || pia === null || pia===0) {
	       alert('Please fill pia Details');
	       $flag = false; 
	       return false;
	   }
	   
	   if (wc === '' || wc === undefined || wc === null || wc===0) {
	          alert('Please fill wc Details');
	          $flag = false; 
	          return false;
	      }
	
}


		   if(admiMechanism=='' || admiMechanism == undefined || admiMechanism == null)
		   {
		   	alert('Please fill Admin Mechanism Details')
		   	$flag=false;
		   	return false;
		   }
		   
		   
    $.ajax({
        url: "saveIndicatorEvaluationDetails", 
        type: "post",
        data: {
			profile_id:profile_id,
			fromno:fromno,
            wcdc: wcdc,
            wc: wc,
            pia: pia,
            admiMechanism: admiMechanism,
            admiMechanismRemark: admiMechanismRemark,
            dprSlna: dprSlna,
            dprSlnaRemark: dprSlnaRemark,
            allManpower: allManpower,
            allManpowerRemark: allManpowerRemark,
            wcdcRemark: wcdcRemark,
            piaRemark: piaRemark,
            wcRemark: wcRemark
        },
        success: function(response) {
            if (response.trim() === 'success') {
                alert("Data saved successfully!");
                window.location.href = 'indicatorsEvaluation'; 
            } 				
			if(response==='update')
			{
				alert('Data Updated Successfully !');
											
				window.location.href='indicatorsEvaluation';
										
			}
			else{
				alert('Some error occurred');
			}
        },
        error: function(xhr, status, error) {
            console.log("AJAX Error: " + status + " " + error);
            alert("An error occurred while saving data.");
        }
    });
}

// Document ready function to attach event listener
$(document).ready(function() {
    $('#nextButton').click(function() {
        selectEval();
    });
});
