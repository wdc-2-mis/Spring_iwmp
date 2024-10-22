
	$(function(){
		
		$(':input[type="number"]').change(function(){
     this.value = parseFloat(this.value).toFixed(2);
});

    $("button").click(function(e) {
	e.preventDefault();
$inputDistrict = $('#inputDistrict').val();
$inputBlocks = $('#inputBlocks').val();
$inputmircowatersheds = $('#inputmircowatersheds').val();
$inputgeoarea = $('#inputgeoarea').val();
$inputuntreatarea = $('#inputuntreatarea').val();
$inputiwmpProjects = $('#inputiwmpProjects').val();
$inputWaterShedP = $('#inputWaterShedP').val();
$inputAssIrrigation = $('#inputAssIrrigation').val();
$inputwdciwmpProjects = $('#inputwdciwmpProjects').val();
$fired_button = $(this).val();
//alert($fired_button);
if($('#inputDistrict').val()===''){
			$('#inputDistrict').css("border-color", "red");
			successAlert('Please enter no. of District!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputDistrict').focus();
						});
			
			return false;
			}
	
	if($('#inputBlocks').val()===''){
			$('#inputBlocks').css("border-color", "red");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter no. of Blocks!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputBlocks').focus();
						});
			return false;
			}
	 
	
	 if($('#inputmircowatersheds').val()===''){
			$('#inputmircowatersheds').css("border-color", "red");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter no.  Of Micro-watersheds!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputmircowatersheds').focus();
						});
			return false;
			}
	
	if($('#inputgeoarea').val()===''){
			$('#inputgeoarea').css("border-color", "red");
			$('#inputmircowatersheds').css("border-color", "green");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter Total Geographical area!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputgeoarea').focus();
						});
		  return false;
			}
	
	if($('#inputuntreatarea').val()===''){
			$('#inputuntreatarea').css("border-color", "red");
			$('#inputgeoarea').css("border-color", "green");
			$('#inputmircowatersheds').css("border-color", "green");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter Total Untreatable area!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputuntreatarea').focus();
						});
				return false;
			}
	
	if($('#inputiwmpProjects').val()===''){
			$('#inputiwmpProjects').css("border-color", "red");
			$('#inputuntreatarea').css("border-color", "green");
			$('#inputgeoarea').css("border-color", "green");
			$('#inputmircowatersheds').css("border-color", "green");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter pre-WDC-PMKSY projects!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputiwmpProjects').focus();
						});
			return false;
			}
	
	if($('#inputWaterShedP').val()===''){
			$('#inputWaterShedP').css("border-color", "red");
			$('#inputiwmpProjects').css("border-color", "green");
			$('#inputuntreatarea').css("border-color", "green");
			$('#inputgeoarea').css("border-color", "green");
			$('#inputmircowatersheds').css("border-color", "green");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter watershed programmes!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
					$('#inputWaterShedP').focus();	
						});
			return false;
			}
      if($('#inputAssIrrigation').val()===''){
			$('#inputAssIrrigation').css("border-color", "red");
			$('#inputWaterShedP').css("border-color", "green");
			$('#inputiwmpProjects').css("border-color", "green");
			$('#inputuntreatarea').css("border-color", "green");
			$('#inputgeoarea').css("border-color", "green");
			$('#inputmircowatersheds').css("border-color", "green");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			successAlert('Please enter area with assured irrigation!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputAssIrrigation').focus();
						});
			return false;
			}
			if($('#inputwdciwmpProjects').val()===''){
			$('#inputAssIrrigation').css("border-color", "red");
			$('#inputWaterShedP').css("border-color", "green");
			$('#inputiwmpProjects').css("border-color", "green");
			$('#inputuntreatarea').css("border-color", "green");
			$('#inputgeoarea').css("border-color", "green");
			$('#inputmircowatersheds').css("border-color", "green");
			$('#inputBlocks').css("border-color", "green");
			$('#inputDistrict').css("border-color", "green");
			$('#inputwdciwmpProjects').css("border-color", "green");
			successAlert('Please enter area covered under wdc/iwmp project!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#inputAssIrrigation').focus();
						});
			return false;
			}
			
	if($fired_button==='d'){
		confirmAlert('Are You Sure You Want to Save This Record!');
					}
					else{
					confirmAlert('Are You Sure You Want to Complete This Record!');	
					}
					$("#ok").click(function()
					
					{
	$.ajax({  
            url:"saveStateProfile",
            type: "post", 
			data:{status:$fired_button, district:$inputDistrict ,blocks:$inputBlocks,mircowatersheds:$inputmircowatersheds,geoarea:$inputgeoarea, untreatarea:$inputuntreatarea, iwmpProjects:$inputiwmpProjects, WaterShedP:$inputWaterShedP, AssIrrigation:$inputAssIrrigation, areacoverwdc:$inputwdciwmpProjects}, 
            error:function(xhr,status,er){
                console.log(er);
            },

		     success:function(data) {
			
			if(data==='success'){
				successAlert('Data Inserted Successfully!');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='addstateprofile';
				});
			}
			else{
				successAlert('Error on Data Inserting!');
				$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='addstateprofile';
				});
			}
			}
			});
	});
	
	
	 });
});
	function isNumericWithPoint(argvalue)
{
		   var argvalue1 = argvalue.value.toString(); 
		   //argvalue1.toFixed(5) ;     
		   var validChars = "0123456789.";
		   var startFrom = 0;	
		   var noOfDecimals=0;	
		   for (var n = startFrom; n < argvalue1.length; n++) {
			   if (argvalue1.substring(n, n+1).indexOf(".") == 0){             
				   noOfDecimals=noOfDecimals+1;
			   }
		   	   if (noOfDecimals>1){             
			     argvalue.value=argvalue1.substring(0, n);
				 return ;
		       }
			 if (validChars.indexOf(argvalue1.substring(n, n+1)) == -1){             
				 argvalue.value=argvalue1.substring(0, n);
				 return ;
		     }
		   }
		}
	
	function isNumeric(argvalue)
{
		   var argvalue1 = argvalue.value.toString(); 
		   //argvalue1.toFixed(5) ;     
		   var validChars = "0123456789";
		   var startFrom = 0;	
		   var noOfDecimals=0;	
		   for (var n = startFrom; n < argvalue1.length; n++) {
			   if (argvalue1.substring(n, n+1).indexOf(".") == 0){             
				   noOfDecimals=noOfDecimals+1;
			   }
		   	   if (noOfDecimals>1){             
			     argvalue.value=argvalue1.substring(0, n);
				 return ;
		       }
			 if (validChars.indexOf(argvalue1.substring(n, n+1)) == -1){             
				 argvalue.value=argvalue1.substring(0, n);
				 return ;
		     }
		   }
		} 
        
    function check(box)
{ 
	var val = box.value ;
	if(val.indexOf(".") != -1)
	{
		if(!/\.[0-9]{1,5}$/.test(val))
		{
		successAlert('Value should be upto five decimal numbers required!');
			$("#successok").click(function(){
						$('#popup').modal('hide');
						box.focus();
						});
		
		return false;
		}
	}
	return true;
}   
   
	