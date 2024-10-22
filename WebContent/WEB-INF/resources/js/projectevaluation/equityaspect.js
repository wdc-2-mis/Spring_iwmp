$(document).ready(function() {

    /******************************************** Save Button Click ******************************** */
    $(document).on('click', '#view', function(e) {
        e.preventDefault();
      //  $projectProfileId = 200;
//        $fundUtilizationPrestatus = 546;
//        $fundUtilizationMidstatus = 506;
//        $fundUtilizationRemark = "dgfsa";
        
        $projectProfileId1 = $('#profileid').val();
//        alert("dfs1 "+$projectProfileId1);
        $waterCommittee = $('#watershedCom').val();
//        alert("dfs2 "+$centralSharePrestatus);
        $waterCommitteeRemark = $('#rmkWatershedCom').val();
//        alert("dfs3 "+$centralShareMidstatus);
        $fpoShgVli = $('#fpoShgVli').val();
//        alert("dfs4 "+$centralShareRemark);
        $fpoShgVliRemark = $('#rmkfpoShgVli').val();
//        alert("dfs5 "+$stateSharePrestatus);
        $livelihoodOption = $('#livelihood').val();
//        alert("dfs6 "+$stateShareMidstatus);
        $livelihoodOptionRemark = $('#rmklivelihood').val();
//        alert("dfs7 "+$stateShareRemark);
        $projectControlled = $('#preTotalFund').val();
//        alert("dfs8 "+$totalFundPrestatus);
       
        $totalFundMidstatus = $('#midTotalFund').val();
//        alert("dfs9 "+$totalFundMidstatus);
        $totalFundRemark = $('#rmkTotalFund').val();
//        alert("dfs10 "+$totalFundRemark);
        $totalFundPlannedPrestatus = $('#preConPlannedFund').val();
//        alert("dfs11 "+$totalFundPlannedPrestatus);
        $totalFundPlannedMidstatus = $('#midConPlannedFund').val();
//        alert("dfs12 "+$totalFundPlannedMidstatus);
        $totalFundPlannedRemark = $('#rmkConPlannedFund').val();
//        alert("dfs13 "+$totalFundPlannedRemark);
        $totalExpenditurePrestatus = $('#preExCon').val();
//        alert("df14 "+$totalExpenditurePrestatus);
        $totalExpenditureMidstatus = $('#midExCon').val();
//        alert("dfs15 "+$totalExpenditureMidstatus);
        $totalExpenditureRemark = $('#rmkExCon').val();
//        alert("dfs16 "+$totalExpenditureRemark);
		$fromno1 = $('#fromno').val();
//		alert("dfs17 "+$fromno1);
       
        	

      	if ($centralSharePrestatus === "" && $centralShareMidstatus === "" && $stateSharePrestatus === "" 
      		&& $stateShareMidstatus === "" && $totalFundPrestatus === "" && $totalFundMidstatus === "" 
      		&& $totalFundPlannedPrestatus === "" && $totalFundPlannedMidstatus === "" 
      		&& $totalExpenditurePrestatus === "" && $totalExpenditureMidstatus === "") 
        	{
	       		alert("Please fill all the details");
	      		return; 
	   		}
		
		if($centralSharePrestatus==='')
		{
			alert('Please enter pre-project status of sanctioned central share');
			$('#preCentralShare').focus();
			return false;
		}
		if($centralShareMidstatus==='')
		{
			alert('Please enter mid-project status of sanctioned central share');
			$('#midCentralShare').focus();
			return false;
		}
		if($stateSharePrestatus==='')
		{
			alert('Please enter pre-project status of sanctioned state share');
			$('#preStateShare').focus();
			return false;
		}
		if($stateShareMidstatus==='')
		{
			alert('Please enter mid-project status of sanctioned state share');
			$('#midStateShare').focus();
			return false;
		}
		if($totalFundPlannedPrestatus==='')
		{
			alert('Please enter pre-project status of planned fund through convergence');
			$('#preConPlannedFund').focus();
			return false;
		}
		if($totalFundPlannedMidstatus==='')
		{
			alert('Please enter mid-project status of planned fund through convergence');
			$('#midConPlannedFund').focus();
			return false;
		}
		if($totalExpenditurePrestatus==='')
		{
			alert('Please enter pre-project status of incurred expenditure through convergence');
			$('#preExCon').focus();
			return false;
		}
		if($totalExpenditureMidstatus==='')
		{
			alert('Please enter mid-project status of incurred expenditure through convergence');
			$('#midExCon').focus();
			return false;
		}
		
		
//		if ($centralSharePrestatus === "" || $centralShareMidstatus === "" || $stateSharePrestatus === "" 
//      		|| $stateShareMidstatus === "" || $totalFundPrestatus === "" || $totalFundMidstatus === "" 
//      		|| $totalFundPlannedPrestatus === "" || $totalFundPlannedMidstatus === "" 
//      		|| $totalExpenditurePrestatus === "" || $totalExpenditureMidstatus === "") 
//        	{
//	       		alert("Please fill the details");
//	      		return; 
//	   		}
	   		

        $.ajax({
            url: "saveEquityAspect",
            type: "post",
            data: {
                projectProfileId: $projectProfileId1,
//                preFundUtilized: $fundUtilizationPrestatus,
//                midFundUtilized: $fundUtilizationMidstatus,
//                rmkFundUtilized: $fundUtilizationRemark,
                preCentralShare: $centralSharePrestatus,
                midCentralShare: $centralShareMidstatus,
                rmkCentralShare: $centralShareRemark,
                preStateShare: $stateSharePrestatus,
                midStateShare: $stateShareMidstatus,
                rmkStatelShare: $stateShareRemark,
                preTotalFund: $totalFundPrestatus,
                midTotalFund: $totalFundMidstatus,
                rmkTotalFund: $totalFundRemark,
                preConPlannedFund: $totalFundPlannedPrestatus,
                midConPlannedFund: $totalFundPlannedMidstatus,
                rmkConPlannedFund: $totalFundPlannedRemark,
                preExCon: $totalExpenditurePrestatus,
                midExCon: $totalExpenditureMidstatus,
                rmkExCon: $totalExpenditureRemark,
                fromno:$fromno1
            },
            error: function(xhr, status, er) {
                console.log(er);
            },
            success: function(data) {
                console.log(data);
                if (data === 'success') {
					alert('Data Saved Successfully!');
//                   
                        window.location.href = 'getEquityAspect';
                   
                }
                
                if (data === 'update') {
					alert('Data Updated Successfully!');
//                    successAlert('Data Saved Successfully!');
                   
                        window.location.href = 'getEquityAspect';
                   
                }
                
                
                
                if (data === 'fail') {
					alert('Error occured! Data not saved.');
//                 
                        window.location.href = 'getEquityAspect';
                  
                }
            }
        });


    });

});