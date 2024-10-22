$( document ).ready(function(){
	
	$('#project').on("change" ,function() {
		$('.error').html('');
		$projId = $('#project option:selected').val();
		var url ="getFinYearProjWise";
		$.ajax({  
            url:url,
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	//console.log(data);
	$('#listActivityHeadWiseTbody').empty();
	$('#listActivityHeadWiseTbody').append('<tr id="tr"><td><select id="ddlHead" name="ddlHead" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" readonly /><!--<select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control"><option value="">--Select Unit--</option></select>--></td><td><input type="text"  id="txtTarget"  name="txtTarget" onkeyup="checkvalue(event)" onblur="checkInput(this)" class="txtTarget form-control" value=""/></td><td></td></tr>');
	var $financial = $('#financial');
						$financial.empty();
        				$financial.append('<option value="">--Select Year--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $financial.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	}
	});
		});
		

$chkValue= 0;
		function checkBoxClicked(t){
        if($('.chkIndividual:checked').length == $('.chkIndividual').length){
            $('#chkSelectAll').prop('checked',true);
        }else{
            $('#chkSelectAll').prop('checked',false);
        }
if(t.is(':checked'))
$chkValue++;
if(!t.is(':checked'))
$chkValue--;
if($chkValue>0){
$('#btnWCDCForward').removeClass('disabled');
	$('#btnWCDCReject').removeClass('disabled');
	$('#btnWCDCForward').removeAttr("disabled");
	$('#btnWCDCReject').removeAttr("disabled");
}else{
	$('#btnWCDCForward').addClass('disabled');
	$('#btnWCDCForward').attr('disabled', 'disabled');
	$('#btnWCDCReject').addClass('disabled');
	$('#btnWCDCReject').attr('disabled', 'disabled');
}
		}
			
	
	
	$('#chkSelectAll').on('click',function(){
	$chkValue=0;
        if(this.checked){
            $('.chkIndividual').each(function(){
                this.checked = true;
$chkValue++;
            });
        }else{
             $('.chkIndividual').each(function(){
                this.checked = false;
            });
$chkValue=0;
        }
if($(".chkIndividual").prop('checked')){
$('#btnWCDCForward').removeClass('disabled');
	$('#btnWCDCReject').removeClass('disabled');	
	$('#btnWCDCForward').removeAttr("disabled");
	$('#btnWCDCReject').removeAttr("disabled");
}else{
	$('#btnWCDCForward').addClass('disabled');
	$('#btnWCDCReject').addClass('disabled');
	$('#btnWCDCForward').attr('disabled', 'disabled');
	$('#btnWCDCReject').attr('disabled', 'disabled');
}
    });
    
$("input:checkbox").not('#chkSelectAll').each(function() {
	$chkValue= 0;
$(this).on('click',function(){ //alert($(this).val());
        if($('.chkIndividual:checked').length == $('.chkIndividual').length){
            $('#chkSelectAll').prop('checked',true);
        }else{
            $('#chkSelectAll').prop('checked',false);
        }
if(this.checked)
$chkValue++;
if(!this.checked)
$chkValue--;
if($chkValue>0){
$('#btnWCDCForward').removeClass('disabled');
	$('#btnWCDCReject').removeClass('disabled');
	$('#btnWCDCForward').removeAttr("disabled");
	$('#btnWCDCReject').removeAttr("disabled");
}else{
	$('#btnWCDCForward').addClass('disabled');
	$('#btnWCDCForward').attr('disabled', 'disabled');
	$('#btnWCDCReject').addClass('disabled');
	$('#btnWCDCReject').attr('disabled', 'disabled');
}
});

});

/**********************************************************************************************************************************************************	*/
	$('#getPendingAsset').on('click',function(e){
	e.preventDefault();
	 var i = 1;
	$pCode = $('#pendingProject option:selected').val();
	$('.error').html('');
	var tbodyTempListActivityAssetId = $('#tbodyTempListActivityAssetId');
	tbodyTempListActivityAssetId.empty();
	$('#loading').show();
	$.ajax({  
            url:"getquarterlytargetSLNAdata",
            type: "post",  
            data: {pCode:$pCode},
            error:function(xhr,status,er){
	$('#loading').hide();
                console.log(er);
				$('.error').html('');
				$('.error').append(' There is some error please try again !');
            },
            success: function(WdcpmksyMQuadIndicators) {
				console.log(WdcpmksyMQuadIndicators);
				$('#loading').hide();
				if(Object.keys(WdcpmksyMQuadIndicators).length>0){
	for ( var key in WdcpmksyMQuadIndicators) {
		if (WdcpmksyMQuadIndicators.hasOwnProperty(key)) {
			var link="";
			
			tbodyTempListActivityAssetId.append("<tr><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+WdcpmksyMQuadIndicators[key].proj_id+"#"+WdcpmksyMQuadIndicators[key].fin_yr_cd+"#"+WdcpmksyMQuadIndicators[key].quarter+"' name='chkIndividual"+WdcpmksyMQuadIndicators[key].proj_id+"#"+WdcpmksyMQuadIndicators[key].fin_yr_cd+"#"+WdcpmksyMQuadIndicators[key].quarter+"' value='"+WdcpmksyMQuadIndicators[key].proj_id+"#"+WdcpmksyMQuadIndicators[key].fin_yr_cd+"#"+WdcpmksyMQuadIndicators[key].quarter+"'  onclick='checkBoxClicked($(this));'/></td><td>"+WdcpmksyMQuadIndicators[key].proj_name+"</td><td>"+WdcpmksyMQuadIndicators[key].fin_yr_desc+"</td><td style='text-align:center;'><a class='plandetail' data-id="+WdcpmksyMQuadIndicators[key].proj_id+"#"+WdcpmksyMQuadIndicators[key].fin_yr_cd+"#"+WdcpmksyMQuadIndicators[key].quarter+" href='#'>"+WdcpmksyMQuadIndicators[key].quarter+"</a></td><td><textarea maxlength='1000' id='remarks' name='remarks' class='remarks'></textarea></td><td>"+
			"<a href='#' data-id='"+WdcpmksyMQuadIndicators[key].proj_id+"#"+WdcpmksyMQuadIndicators[key].fin_yr_cd+"#"+WdcpmksyMQuadIndicators[key].quarter+"#"+'C'+"'  class='forwardLink'>Complete</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' data-id='"+WdcpmksyMQuadIndicators[key].proj_id+"#"+WdcpmksyMQuadIndicators[key].fin_yr_cd+"#"+WdcpmksyMQuadIndicators[key].quarter+"#"+'D'+"' class='rejectLink'>Reject</a></td></tr>");
		    i++;
		}
			}
	}else{
		tbodyTempListActivityAssetId.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
	}
				
			}
		});
	
	
	});
	
	$('#tbodyTempListActivityAssetId').on( 'click', 'a.plandetail', function (e) {
	var del = e.target.getAttribute('data-id');
	//alert(del);
	$('#loading').show();
	$.ajax({  
            url:"getSingleQuartersDetails",
            type: "post",  
            data: {achievementdtl:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(WdcpmksyMQuadIndicators) {
	$('#loading').hide();
						console.log(WdcpmksyMQuadIndicators);
						var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('View Quarter Detail Data');
						var i=1;
						if(Object.keys(WdcpmksyMQuadIndicators).length>0){
							
	for ( var key in WdcpmksyMQuadIndicators) {
		if (WdcpmksyMQuadIndicators.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+WdcpmksyMQuadIndicators[key].proj_name+ '<br/> Financial Year: '+WdcpmksyMQuadIndicators[key].fin_yr_desc);
			}
			
			
			
			tblData+="<tr><td>"+WdcpmksyMQuadIndicators[key].indicators_desc+"</td><td>"+WdcpmksyMQuadIndicators[key].quadtervalue+"</td></tr>";
		
		
		
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Indicators Name</th><th>Quarters Detail</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
							});
	});
	
	
	$(document).on('click', '.forwardLink', function(e) {
	e.preventDefault();
	        $assetid = e.target.getAttribute('data-id');
            $quardDtl=$('#remarks').val()+"#"+$assetid;
	        confirmAlert('Do you want to Complete this Quarter?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
			$.ajax({  
            url:"completeSLNAQuarter",
            type: "post",  
            data: {quardDtl:$quardDtl},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	        console.log(data);
	        $('#loading').hide();
			if(data==='success'){
			alert('Quarterly Target Completed Successfully!');
			window.location.href='quarterlytargetslna';
			}
			if(data==='fail'){
			alert('Issue on Completing Data !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});
		
		}
			}
		});	
			
	});
	$(".close").click(function(){
			$('#popup').modal('hide');
			});
	});
	
	$(document).on('click', '.rejectLink', function(e) {
	e.preventDefault();
             
            $assetid = e.target.getAttribute('data-id');
            $quardDtl=$('#remarks').val()+"#"+$assetid;
	      
 confirmAlert('Do you want to Reject this Quarter?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
			$.ajax({  
            url:"completeSLNAQuarter",
            type: "post",  
            data: {quardDtl:$quardDtl},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	        console.log(data);
	        $('#loading').hide();
			if(data==='success'){
			alert('Quarterly Target Rejected and forwarded to PIA!');
			window.location.href='quarterlytargetslna';
			}
			if(data==='fail'){
			alert('Issue on Completing Data !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});
		
		}
			}
		});	
			
	});
	
	$(".close").click(function(){
			$('#popup').modal('hide');
			});
	});
	
	$('#btnWCDCForward').on('click',function(e){
	e.preventDefault();
	var status = 'C';
	var finalAssetid=new Array();
	var remarks = new Array();
	$('.remarks').prop('required',true);
	 $('.chkIndividual').each(function(){
         if($(this).prop('checked')){
	         removeInputFieldErrorCss('#remarks'+$(this).val());
			 finalAssetid.push($('#remarks').val()+$(this).val());	
		remarks.push($('#remarks'+$(this).val()).val());
		}
      });
confirmAlert('Do you want to Complete all Projects Quarters');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
			$.ajax({  
            url:"updateallSLNAQuarters",
            type: "post",  
            data: {assetid:finalAssetid.toString(), status:status},
            error:function(xhr,status,er){
                console.log(er);
            },
success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(data==='success'){
		alert('Quarterly Target Completed Successfully ');
		window.location.href='quarterlytargetslna';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});
		}
}
});
});
$(".close").click(function(){
			$('#popup').modal('hide');
			});
			});
	
$('#btnWCDCReject').on('click',function(e){
	e.preventDefault();
	var status = 'D';
	var finalAssetid=new Array();
	var remarks = new Array();
	$('.remarks').prop('required',true);
	 $('.chkIndividual').each(function(){
         if($(this).prop('checked')){
	         removeInputFieldErrorCss('#remarks'+$(this).val());
			 finalAssetid.push($('#remarks').val()+$(this).val());	
		remarks.push($('#remarks'+$(this).val()).val());
		}
      });
	confirmAlert('Do you want to Reject selected Quarters');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
			$.ajax({  
            url:"updateallSLNAQuarters",
            type: "post",  
            data: {assetid:finalAssetid.toString(), status:status},
            error:function(xhr,status,er){
                console.log(er);
            },
success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(data==='success'){
		alert('Quarter Target Rejected and forward to PIA! ');
		window.location.href='quarterlytargetslna';
		}
		if(data==='fail'){
			alert('Issue on Rejected Data !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='quarterlytargetslna';
			});
		}
}
});
});
	
	
	$(".close").click(function(){
			$('#popup').modal('hide');
			});
	});
	
	$('#tbodyMovement').on( 'click', 'a.movement', function (e) {

var del = e.target.getAttribute('data-id');
    $('#loading').show();
$.ajax({  
            url:"getCompletedQuadDetails",
            type: "post",  
            data: {quarterdtl:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(WdcpmksyMQuadIndicators) {
	$('#loading').hide();
						console.log(WdcpmksyMQuadIndicators);
						var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('View Quarter Detail Data');
						var i=1;
						if(Object.keys(WdcpmksyMQuadIndicators).length>0){
							
	for ( var key in WdcpmksyMQuadIndicators) {
		if (WdcpmksyMQuadIndicators.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+WdcpmksyMQuadIndicators[key].proj_name+ '<br/> Financial Year: '+WdcpmksyMQuadIndicators[key].fin_yr_desc +'<br/> <br/>0* This Quarter is not Completed');
			}
			tblData+="<tr><td>"+WdcpmksyMQuadIndicators[key].indicators_desc+"</td><td>"+WdcpmksyMQuadIndicators[key].first_quad+"</td><td>"+WdcpmksyMQuadIndicators[key].second_quad+"</td><td>"+WdcpmksyMQuadIndicators[key].third_quad+"</td><td>"+WdcpmksyMQuadIndicators[key].fourth_quad+"</td></tr>";
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Indicators Name</th><th>First Quarters</th><th>Second Quarters</th><th>Third Quarters</th><th>Fourth Quarters</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
			});				  

	});
	});
	