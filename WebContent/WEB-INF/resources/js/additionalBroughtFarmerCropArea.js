


$(function(){

	$(document).on( 'change', '#groupType', function (e) {
		e.preventDefault();
		$projectId = $('#project').val();
		$gType = $('#groupType').val();
	//	alert($gType);
		//alert(('#groupType option:selected').val());
		$('#additional_brought_id').val('');
		$('#diversified').val('');
		$('#chnagesingle').val('');
		$('#farmer').val('');
		$('#changecorp').val('');
		$('#pulses').val('');
		$('#oilseeds').val('');
		
		if($gType==='fy')
		{
			$('.yeark').removeClass('d-none');
			$('.mont').addClass('d-none');
			$('.draft').removeClass('d-none');
			$('.trk').removeClass('d-none');
			$('.montt').addClass('d-none'); 
			$('#additional_brought_id').val('');
			$("select#financial")[0].selectedIndex = 0;
			$('#additional_brought_id').val('');
			$("select#financial")[0].selectedIndex = 0;
		}
		else if($gType==='m')
		{
			$('.yeark').removeClass('d-none');
			$('.trk').addClass('d-none');
			$('.mont').removeClass('d-none');
			$('.draft').removeClass('d-none');
			$('.montt').removeClass('d-none');
			$('#additional_brought_id').val('');
			$("select#financial")[0].selectedIndex = 0;
			$("select#month")[0].selectedIndex = 0;
		}
		else{
		
			$('.yeark').addClass('d-none');
			$('.mont').addClass('d-none');
			$('.draft').addClass('d-none');
			$('.mont').addClass('d-none');
			$('.montt').addClass('d-none');
			$('#additional_brought_id').val('');
			$("select#financial")[0].selectedIndex = 0;
			$("select#month")[0].selectedIndex = 0;
		}
		
		
		$.ajax({  
            url:"getRemainYear",
            type: "post", 
			data:{project:$projectId,group:$gType}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				$selectedDist=$('#distCode').val();
				$ddlDistrict = $('#financial');
				$ddlDistrict.empty();
				$ddlDistrict.append('<option value=""> --Select Year-- </option>');
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						if(data[key]==$selectedDist)
							$ddlDistrict.append('<option value="'+key+'" selected>' +data[key] + '</option>');
						else
							$ddlDistrict.append('<option value="'+key+'">' +data[key]+ '</option>');
					}
				}
			}
		});
				
		});



		
	
	$('#draftSave').on('click',function(e) {
		e.preventDefault();
		
		if($('#project').val()==='')
		{
				alert('please select Project');
				$('#project').focus();
				return false;
		}
			
		if($('#groupType').val()==='')
		{
				alert('please select Achievement Type.');
				$('#groupType').focus();
				return false;
		}
		
		if($('#financial').val()==='')
		{
				alert('please select Financial Year');
				$('#financial').focus();
				return false;
		}
		
		if($('#groupType').val()==='m')
		{
			if($('#month').val()==='')
			{
					alert('please select Month');
					$('#month').focus();
					return false;
			}
		
			if($('#diversified').val()==='')
			{
					alert('please fill diversified crops/change in cropping system data');
					$('#diversified').focus();
					return false;
			}
			if($('#chnagesingle').val()==='')
			{
					alert('please fill No crop/single crop to single/multiple crop');
					$('#chnagesingle').focus();
					return false;
			}
			if($('#pulses').val()==='')
			{
				alert('please fill Increase in Pulses Area');
				$('#pulses').focus();
				return false;
			}
			if($('#oilseeds').val()==='')
			{
				alert('please fill Increase in Oilseeds Area');
				$('#oilseeds').focus();
				return false;
			}
		}
		
		if($('#groupType').val()==='fy')
		{		
	        if($('#farmer').val()==='')
	        {
					alert('please fill Change in farmer income data ');
					$('#farmer').focus();
					return false;
			}
			if($('#changecorp').val()==='')
			{
					alert('please fill Change in Cropped Area data ');
					$('#changecorp').focus();
					return false;
			}
		}				
        
        if (confirm('Do you want to Save this Record ?')) 
        {
			$project = $('#project').val();
			$type=$('#groupType').val();
			$financial = $('#financial').val();
			$month = $('#month').val();
			$diversified = $('#diversified').val();
			$chnagesingle = $('#chnagesingle').val();
			$farmer = $('#farmer').val();
			$changecorp = $('#changecorp').val();
			$status = 'D';
			$additional_brought_id=$('#additional_brought_id').val();
			$pulses = $('#pulses').val();
			$oilseeds = $('#oilseeds').val();
		
			if($month == null || $month =='')
			{
				$month = 0;
			}
			if($diversified == null || $diversified =='')
			{
				$diversified = 0;
			}
			if($chnagesingle == null || $chnagesingle =='')
			{
				$chnagesingle = 0;
			}
			if($farmer == null || $farmer =='')
			{
				$farmer = 0;
			}
			if($changecorp == null || $changecorp =='')
			{
				$changecorp = 0;
			}
			if($additional_brought_id == null || $additional_brought_id =='')
			{
				$additional_brought_id = 0;
			}
			if($pulses == null || $pulses =='')
			{
				$pulses = 0;
			}
			if($oilseeds == null || $oilseeds =='')
			{
				$oilseeds = 0;
			}
									
		$.ajax({ 
			url:"saveAdditionalBroughtFarmerCropArea",
            type: "post", 
			data:{projId:$project, month:$month, year:$financial, diversified:$diversified, chnagesingle:$chnagesingle, farmer:$farmer, changecorp:$changecorp, status:$status, additionalid:$additional_brought_id, atype:$type, pulses:$pulses, oilseeds:$oilseeds}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data)
		     {
				if(data==='success'){
					alert('Data Saved Successfully!');
					window.location.href='getAdditionalBroughtFarmerCropArea';
				/*$("#successok").click(function()
				{
			        $('#popup').modal('hide');
                    		    
				});*/
				}
				else{
					alert('Data already Completed!');
					window.location.href='getAdditionalBroughtFarmerCropArea';
				}
			}
		});
		
		}
});	
		
		$(document).on( 'change', '.yeark', function (e) {
		e.preventDefault();
		$projectId = $('#project').val();
		$gType = $('#groupType').val();
		$year = $(this).val();
		$('#additional_brought_id').val('');
		$month=0;
			$.ajax({  
	            url:"getAdditionalBroughtYearDraft",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year, mont:$month}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
			    success:function(data) {
				console.log(data);
			
				if(Object.keys(data).length>0)
				{
					console.log(data);
					for ( var key in data) 
					{
							if (data.hasOwnProperty(key)) 
							{ 
								$('#additional_brought_id').val(data[key].additional_brought_id); 
								$('#farmer').val(data[key].farmer_income); 
								$('#changecorp').val(data[key].change_corp); 
								$('.complete').removeClass('d-none');
							//	alert('kdy='+data[key].additional_brought_id+'-jdk-'+data[key].farmer_income);
							}
							
					}
				}
				}
			});
			
			if($gType==='fy'){
			//alert('kdy1');
			$.ajax({  
	            url:"getAdditionalBroughtYearComplt",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
			    success:function(data) {
				console.log(data);
		//	alert('kdy2');
				if(Object.keys(data).length>0)
				{
					//alert('kdy');
					console.log(data);
					for ( var key in data) 
					{
							if (data.hasOwnProperty(key)) 
							{
								$('#additional_brought_id').val(data[key].additional_brought_id); 
								$('#farmer').val(data[key].farmer_income); 
								$('#changecorp').val(data[key].change_corp); 
								$('.complete').addClass('d-none');
								$('.draft').addClass('d-none');
								
								$('.error').css("background-color","rgb(123 144 57)");
								$('.error').html("Data has been completed for the selected financial year. ");
								
							}
					}
					alert('Selected Parameter data has been Completed, Please wait for next Financial Year.');
				}
				else{
					$('.draft').removeClass('d-none');
				}
				}
			});
			
			}
			
			if($gType==='m'){
			
			$.ajax({  
            url:"getRemainYearMonth",
            type: "post", 
			data:{project:$projectId,group:$gType,fyear:$year}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				$selectedDist=$('#distCode').val();
				$ddlmonth = $('#month');
				$ddlmonth.empty();
				$ddlmonth.append('<option value=""> --Select Half Yearly-- </option>');
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						if(data[key]==$selectedDist)
							$ddlmonth.append('<option value="'+key+'" selected>' +data[key] + '</option>');
						else
							$ddlmonth.append('<option value="'+key+'">' +data[key]+ '</option>');
					}
				}
			}
		});
			
			}
			
			
			
		});
		
		$(document).on( 'change', '.mont', function (e) {
		e.preventDefault();
		$projectId = $('#project').val();
		$gType = $('#groupType').val();
		$year = $('#financial').val();
		$month=$('#month').val();
			$.ajax({  
	            url:"getAdditionalBroughtYearDraft",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year, mont:$month}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
			    success:function(data) {
				console.log(data);
			
				if(Object.keys(data).length>0)
				{
					console.log(data);
					for ( var key in data) 
					{
							if (data.hasOwnProperty(key)) 
							{
								$('#additional_brought_id').val(data[key].additional_brought_id); 
								$('#diversified').val(data[key].diversified); 
								$('#chnagesingle').val(data[key].chnagesingle); 
								$('.complete').removeClass('d-none');
								
								$('#pulses').val(data[key].pulses); 
								$('#oilseeds').val(data[key].oilseeds); 
							}
					}
				}
				else{
						$('#additional_brought_id').val(''); 
						$('#diversified').val(''); 
						$('#chnagesingle').val('');
						$('.complete').addClass('d-none');
						
						$('#pulses').val(''); 
						$('#oilseeds').val(''); 
				}
				
				}
			});
			
			$.ajax({  
	            url:"getAdditionalBroughtMonthComplt",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year, mont:$month}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
			    success:function(data) {
				console.log(data);
			
				if(Object.keys(data).length>0)
				{
					console.log(data);
					for ( var key in data) 
					{
							if (data.hasOwnProperty(key)) 
							{
								$('#additional_brought_id').val(data[key].additional_brought_id); 
								$('#diversified').val(data[key].diversified); 
								$('#chnagesingle').val(data[key].chnagesingle); 
								$('#pulses').val(data[key].pulses); 
								$('#oilseeds').val(data[key].oilseeds); 
								$('.complete').addClass('d-none');
								$('.draft').addClass('d-none');
								$('.error').css("background-color","rgb(123 144 57)");
								$('.error').html("Data has been completed for the selected financial year and period. ");
							}
					}
					alert('Selected Parameter data has been Completed.');
				}
				else{
					$('.draft').removeClass('d-none');
				//	$('.error').addClass('d-none');
				}
				}
			});
			
			
			
			
			
		});
		
		
		
		
		
		
		
	
	$('#delete').on('click',function(e) {
	var del = $('#del').val();
	
	confirmAlert('Are You Sure You Want to Delete This Record!!');
					$("#ok").click(function(){
			$('#popup').modal('hide');
			$.ajax({  
            url:"delOutcomeParadraftdata",
            type: "post",  
            data: {draftid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						//alert("hi:" +data);
						if(data==='success'){
                        alert('Data Deleted Successfully!');
						window.location.href='outcomeaddpara';
						}
						else
						{
							alert('Issue on deleting data!');
							window.location.href='outcomeaddpara';
						}
						$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='';
					});
						}
							});
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
			
	
} );
	
	$('#complete').on('click',function(e) {
		e.preventDefault();
		if($('#project').val()===''){
				alert('please select Project');
				$('#project').focus();
				return false;
			}
		
		/*if($('#month').val()===''){
				alert('please select Month');
				$('#month').focus();
				return false;
			}*/
			
		if($('#financial').val()===''){
				alert('please select Financial Year');
				$('#financial').focus();
				return false;
			}
		
				
        else
        if (confirm('Are you sure to wants to Complete this Record ?')) {
		$project = $('#project').val();
		$month = $('#month').val();
		$financial = $('#financial').val();
		$additional_id = $('#additional_brought_id').val();
		$status = 'C';
		if($additional_id == null || $additional_id ==='' )
		{
			$additional_id = 0;
		}
		if($month == null || $month ==='')
		{
			$month = 0;
		}
	//	alert($project+'k'+$month+' k '+$financial+' k '+$additional_id+' k '+$status);
		$.ajax({ 
			url:"completeAdditionalBroughtFarmerCropArea",
            type: "post", 
			data:{projId:$project, month:$month, year:$financial, status:$status, additionalid:$additional_id}, 
            error:function(xhr,status,er){
                console.log(er);
            },

		     success:function(data)
		     {
				if(data==='success'){
				alert('Data completed Successfully!');
					window.location.href='getAdditionalBroughtFarmerCropArea';	
				}
				else{
					alert('Data not completed, technical issue!');
					window.location.href='getAdditionalBroughtFarmerCropArea';	
				}	
			}
		});
		
		}
		});	
		
		$('#view1').on('click',function(e) {
		e.preventDefault();
			$project = $('#project').val();
			$month = $('#month').val();
	    	$year = $('#financial').val();
		      $.ajax({  
            url:'fetchOutcomeParam',
            type: "post",  
            data: {projId:$project, month:$month, year:$year},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            console.log(data);
            var i = 1;	        
            $tbody = $('#outcomedraftbody');
			$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
										$tblData+='<tr><td>'+i+'</td><td>'+d[k].project+'</td><td>'+d[k].month+'</td><td>'+d[k].finyear+'</td><td>'+d[k].degraded_land+'</td><td>'+d[k].rainfed+'</td><td>'+d[k].man_day_gen+'</td><td colspan="6">'+d[k].farmer_sc+'</td><td>'+d[k].farmer_st+'</td><td>'+d[k].others+
													'</td><td>'+d[k].farmer_female+'</td><td>'+d[k].farmer_small+'</td><td>'+d[k].farmer_mirginal+'</td><td>'+d[k].farmer_landless+'</td><td>'+d[k].farmer_bpl+'</td><td><a href="#" data-id='+d[k].outcome2_id+' class="delete">Delete</a></td><td><a href="#" data-id='+d[k].outcome2_id+' class="complete">Complete</a></td>';
									i++
									}
									
									$tbody.append($tblData);
									}
							
						}
		}
		
		     });
            
	$.ajax({  
            url:'outcomefinaldata',
            type: "post",  
            data: {projId:$project, month:$month, year:$financial},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {/*console.log(data);*/
            var i = 1;	        
            $tbody = $('#outcomefinalbody');
$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
									$tblData+='<tr><td>'+i+'</td><td>'+d[k].project+'</td><td>'+d[k].month+'</td><td>'+d[k].finyear+'</td><td>'+d[k].degraded_land+'</td><td>'+d[k].rainfed+'</td><td>'+d[k].man_day_gen+'</td><td colspan="6">'+d[k].farmer_sc+'</td><td>'+d[k].farmer_st+'</td><td>'+d[k].others+
													'</td><td>'+d[k].farmer_female+'</td><td>'+d[k].farmer_small+'</td><td>'+d[k].farmer_mirginal+'</td><td>'+d[k].farmer_landless+'</td><td>'+d[k].farmer_bpl+'</td>';
									i++
									}
									$tbody.append($tblData);
									/*$tbody.append('<tr><td  colspan=18></td></tr>');*/
									
							}
						
						}
			 
		}
          });		
			
			});
		
		});