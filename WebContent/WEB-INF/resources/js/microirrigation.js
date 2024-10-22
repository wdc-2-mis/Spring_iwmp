$( document ).ready(function(){

/***************************** Project Change Script *************************************** */
$(document).on( 'change', '#project', function (e) {
	$('#loading').show();
	if($('.plotnoError').html()!=""){
		$('.plotnoError').html('');
	}
	$projId = $('#project').val();
	if($projId ===""){
		window.location.reload();
	}
	
	$.ajax({  
            url:"getVillageOfProjects",
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlVillage=$('#village');
				$ddlVillage.empty();
				$ddlVillage.append("<option value=''>---Select Village [GP Name]---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlVillage.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
  orderByNameDDL($ddlVillage);
			}
		});
	
});	


});


/***************************** Village Change Script *************************************** */
$(document).on( 'change', '#village', function (e) {
	$('#loading').show();
	if($('.plotnoError').html()!=""){
		$('.plotnoError').html('');
	}
	$villageId = $('#village').val();
	if($villageId ===""){
		window.location.reload();
	}
	
	$.ajax({  
            url:"getPlotNoOfProject",
            type: "post",  
            data: {villageId:$villageId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlPlotno=$('#plotno');
				$ddlPlotno.empty();
				$ddlPlotno.append("<option value=''>---Select Plot No.---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlPlotno.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
  orderByNameDDL($ddlVillage);
			}
		});
	
});	

/***************************** Plot No. Change Script *************************************** */
$(document).on( 'change', '#plotno', function (e) {

$('#loading').show();
	if($('.plotnoError').html()!=""){
		$('.plotnoError').html('');
	}
	$plotnoId = $('#plotno').val();
	if($plotnoId ===""){
		window.location.reload();
	}
	$.ajax({  
            url:"getPlotAreaOfProject",
            type: "post",  
            data: {plotnoId:$plotnoId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(VillGramPanBean) {
            $('#loading').hide();
				
				var $unit = $('#projectArea');
				var $status = $('#irrgationstatus');
				var $microirrigation = $('#microirr');
				
				$unit.empty();
				$status.empty();
				$microirrigation.empty();
				
				for (key in VillGramPanBean){
				$unit.val(VillGramPanBean[key].area);
				$status.val(VillGramPanBean[key].description);
				
				var checkstatus = VillGramPanBean[key].micro_status;
				
				if(checkstatus==='D')
				{
				$('#mirrgation .message').hide();
				$microirrigation.val(VillGramPanBean[key].micro_irrigation);
				$('#draftSave').prop("disabled",false);
				$('#complete').prop("disabled",false);
				$('#microirr').prop("disabled",false);
				}
				else if(checkstatus==='C')
				{
				$('#mirrgation .message').show();
            	$('#mirrgation .message').html("Micro Irrigation Area is already Completed.");
				$microirrigation.val(VillGramPanBean[key].micro_irrigation);
				$('#microirr').prop("disabled",true);
				$('#draftSave').prop("disabled",true);
				$('#complete').prop("disabled",true);
				} 
				else{
				$('#mirrgation .message').hide();
				$microirrigation.val(VillGramPanBean[key].micro_irrigation)=="";
				$('#draftSave').prop("disabled",false);
				$('#complete').prop("disabled",false);
				$('#microirr').prop("disabled",false);
				}
				
				
				
				}
				
  
			}
		});
	e.preventDefault();
});	

/***************************** Save As Draft *************************************** */
$('#draftSave').on('click', function(e) {
   $proj = $('#project option:selected').val();
   $vill = $('#village option:selected').val();
   $plotno = $('#plotno option:selected').val();
   $projectArea = parseFloat($('#projectArea').val());
   $irrgationstatus = $('#irrgationstatus').val();
   $microirr = parseFloat($('#microirr').val());
  
   $microstatus = 'D';
   
   if($('#project option:selected').val()==""){
			successAlert('Please select project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#project').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#project').focus();
			});
			return false;
		}
	
	if($('#village option:selected').val()==""){
			successAlert('Please select village');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#village').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#village').focus();
			});
			return false;
		}
		
		if($('#plotno option:selected').val()==""){
			successAlert('Please select Plot No.');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#plotno').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#plotno').focus();
			});
			return false;
		}
		
		if($('#projectArea').val()==""){
			successAlert('Plot Area Should not be blank');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#projectArea').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#projectArea').focus();
			});
			return false;
		}
		
		if($('#irrgationstatus').val()==""){
			successAlert('Irrigation Status Should not be blank');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#irrgationstatus').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#irrgationstatus').focus();
			});
			return false;
		}
		
		if($('#microirr').val()==""){
			successAlert('Micro Irrigation Should not be blank');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#microirr').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#microirr').focus();
			});
			return false;
		}
		if($microirr > $projectArea)
		{
		successAlert('Micro Irrigation Should not be greater than Plot Area');
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#microirr').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#microirr').focus();
			});
			return false;
		}
		else{
		if(confirm("Do you want to save this record ?"))
		{
	$.ajax({  
            url:"updateMicroIrr",
            type: "post", 
			data:{microI:$microirr, plotno:$plotno, microstatus:$microstatus}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
           			if(data){
						successAlert('Data saved as Draft successfully !');
					     }else{
							successAlert('There is some error. Please try again !')
						}
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='microIrrigation';
					});		
	}
            });
}
}
e.preventDefault();
});

/***************************** Save As Complete *************************************** */
$('#complete').on('click', function(e) {
   $proj = $('#project option:selected').val();
   $vill = $('#village option:selected').val();
   $plotno = $('#plotno option:selected').val();
   $projectArea = $('#projectArea').val();
   $irrgationstatus = $('#irrgationstatus').val();
   $microirr = $('#microirr').val();
   $microstatus = 'C';
   
   
   if($('#project option:selected').val()==""){
			successAlert('Please select project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#project').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#project').focus();
			});
			return false;
		}
	
	else if($('#village option:selected').val()==""){
			successAlert('Please select village');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#village').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#village').focus();
			});
			return false;
		}
		
		else if($('#plotno option:selected').val()==""){
			successAlert('Please select Plot No.');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#plotno').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#plotno').focus();
			});
			return false;
		}
		
		else if($('#projectArea').val()==""){
			successAlert('Plot Area Should not be blank');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#projectArea').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#projectArea').focus();
			});
			return false;
		}
		
		else if($('#irrgationstatus').val()==""){
			successAlert('Irrigation Status Should not be blank');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#irrgationstatus').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#irrgationstatus').focus();
			});
			return false;
		}
		
		else if($('#microirr').val()==""){
			successAlert('Micro Irrigation Should not be blank');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#microirr').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#microirr').focus();
			});
			return false;
		}
		else if(parseInt($microirr) > parseInt($projectArea))
		{
		successAlert('Micro Irrigation Should not be greater than Plot Area');
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#microirr').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#microirr').focus();
			});
			return false;
		}
		else{
		if(confirm("Do you want to Complete this record ?"))
		{
	$.ajax({  
            url:"updateMicroIrr",
            type: "post", 
			data:{microI:$microirr, plotno:$plotno, microstatus:$microstatus}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
           			if(data){
						successAlert('Data Complete successfully! !');
					     }else{
							successAlert('There is some error. Please try again !')
						}
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='microIrrigation';
					});		
	}
            });
}
}
e.preventDefault();
});
	