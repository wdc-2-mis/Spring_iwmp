$( document ).ready(function(){
	
	$(document).on( 'click', '#btnSave', function (e) {
			e.preventDefault();
			
			
			$profile_id1 = $('#profileid').val();
		//	$postMonsoon1 = $('#postMonsoon').val();
		
				if($('#projectArea:checked').val()!=null && $('#projectArea:checked').val() !='undefined')
				{
					
					$res=$('#projectArea:checked').val();
					
				}
				if($('#controlledArea:checked').val()!=null && $('#controlledArea:checked').val() !='undefined')
				{
					
						$res=$('#controlledArea:checked').val();
					
				}
		
		
			//alert($res+'kdy');
			
			$culturable_wasteland1 = $('#culturable_wasteland').val();
			$whs_constructed_rejuvenated1 = $('#whs_constructed_rejuvenated').val();
			$soil_moisture1 = $('#soil_moisture').val();
			$protective_irrigation1 = $('#protective_irrigation').val();
			$degraded_rainfed1 = $('#degraded_rainfed').val();
			$farmer_income1 = $('#farmer_income').val();
			$farmer_benefited1 = $('#farmer_benefited').val();
			$mandays_generated1 = $('#mandays_generated').val();
			$dug_well1 = $('#dug_well').val();
			$tube_well1 = $('#tube_well').val();
			$fromno1 = $('#fromno').val();
			
			
				if($('#culturable_wasteland').val()==='')
				{
					alert('Please enter Culturable Wasteland');
					$('#culturable_wasteland').focus();
					return false;
				}
				if($('#whs_constructed_rejuvenated').val()==='')
				{
					alert('Please enter WHS !');
					$('#whs_constructed_rejuvenated').focus();
					return false;
				}
				if($('#soil_moisture').val()==='')
				{
					alert('Please enter area of Soil and moisture');
					$('#soil_moisture').focus();
					return false;
				}
				if($('#protective_irrigation').val()==='')
				{
					alert('Please enter Protective Irrigation');
					$('#protective_irrigation').focus();
					return false;
				}
				if($('#degraded_rainfed').val()==='')
				{
					alert('Please enter Degraded land and rainfed Area');
					$('#degraded_rainfed').focus();
					return false;
				}
				if($('#farmer_income').val()==='')
				{
					alert('Please enter farmer income');
					$('#farmer_income').focus();
					return false;
				}
				if($('#farmer_benefited').val()==='')
				{
					alert('Please enter farmer benefited');
					$('#farmer_benefited').focus();
					return false;
				}
				
				if($('#mandays_generated').val()==='')
				{
					alert('Please enter mandays generated');
					$('#mandays_generated').focus();
					return false;
				}
				if($('#dug_well').val()==='')
				{
					alert('Please enter dug well');
					$('#dug_well').focus();
					return false;
				}
				if($('#tube_well').val()==='')
				{
					alert('Please enter tube well');
					$('#tube_well').focus();
					return false;
				}												
		//	alert($profile_id1+','+$culturable_wasteland1);
			$.ajax({  
	            url:"saveMandaysDetails",
	            type: "post", 
				data:{profile_id:$profile_id1, culturable_wasteland:$culturable_wasteland1, whs_constructed_rejuvenated:$whs_constructed_rejuvenated1, soil_moisture:$soil_moisture1, protective_irrigation:$protective_irrigation1, degraded_rainfed:$degraded_rainfed1, farmer_income:$farmer_income1, farmer_benefited:$farmer_benefited1, mandays_generated:$mandays_generated1, dug_well:$dug_well1, tube_well:$tube_well1, fromno:$fromno1, areatype:$res}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success:function(data) {
						console.log(data);
						if(data==='success')
						{
							successAlert('Data Saved Successfully !');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								window.location.href='getMandayDeatails';
								});  
								$(".close").click(function(){
								$('#popup').modal('hide');
								window.location.href='getMandayDeatails';
								});
						}
						if(data==='update')
						{
							successAlert('Data Update Successfully !');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								window.location.href='getMandayDeatails';
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								window.location.href='getMandayDeatails';
							});
						}
						else{
							alert('Some error occurred');
						}
	            }
			});
			
			
		});
	
		$(document).on( 'click', '#execbtnSave', function (e) {
					e.preventDefault();
					
					
					$profile_id1 = $('#profileid').val();
				
					
					$created_work1 = $('#created_work').val();
					$created_work_remark1 = $('#created_work_remark').val();
					$completed_work1 = $('#completed_work').val();
					$completed_work_remark1 = $('#completed_work_remark').val();
					$ongoing_work1 = $('#ongoing_work').val();
					$ongoing_work_remark1 = $('#ongoing_work_remark').val();
					
					$fromno1 = $('#fromno').val();
					
					
						if($('#created_work').val()==='')
						{
							alert('Please enter No. of Created Works');
							$('#created_work').focus();
							return false;
						}
						if($('#completed_work').val()==='')
						{
							alert('Please enter No. of Completed Works');
							$('#completed_work').focus();
							return false;
						}
						if($('#ongoing_work').val()==='')
						{
							alert('Please enter No. of Ongoing Works');
							$('#ongoing_work').focus();
							return false;
						}
																		
				//	alert($profile_id1+','+$culturable_wasteland1);
					$.ajax({  
			            url:"saveExecutionPlanWork",
			            type: "post", 
						data:{profile_id:$profile_id1, created_work:$created_work1, created_work_remark:$created_work_remark1, completed_work:$completed_work1, completed_work_remark:$completed_work_remark1, ongoing_work:$ongoing_work1, ongoing_work_remark:$ongoing_work_remark1, fromno:$fromno1}, 
			            error:function(xhr,status,er){
			                console.log(er);
			            },
			            success:function(data) {
								console.log(data);
								if(data==='success')
								{
									successAlert('Data Saved Successfully !');
									$("#successok").click(function(){
										$('#popup').modal('hide');
										window.location.href='getExecutionPlanWork';
										});  
									$(".close").click(function(){
										$('#popup').modal('hide');
										window.location.href='getExecutionPlanWork';
									});
								}
								if(data==='update')
								{
									successAlert('Data Update Successfully !');
									$("#successok").click(function(){
										$('#popup').modal('hide');
										window.location.href='getExecutionPlanWork';
									});  
									$(".close").click(function(){
										$('#popup').modal('hide');
										window.location.href='getExecutionPlanWork';
									});
								}
								else{
										alert('Some error occurred');
								}
								
			            }
					});
					
					
				});	
	
				$(document).on( 'click', '#shapebtnSave', function (e) {
					e.preventDefault();
									
					$profile_id1 = $('#profileid').val();
					$shape_file_area1 = $('#shape_file_area').val();
					$shape_file_area_remark1 = $('#shape_file_area_remark').val();
					$variation_area1 = $('#variation_area').val();
					$variation_area_remark1 = $('#variation_area_remark').val();
					$fromno1 = $('#fromno').val();
									
									
					if($('#shape_file_area').val()==='')
					{
						alert('Please enter Area of Shape File (ha)');
						$('#shape_file_area').focus();
						return false;
					}
					if($('#variation_area').val()==='')
					{
						alert('Please enter Variation of area');
						$('#variation_area').focus();
						return false;
					}
							
					$.ajax({  
						url:"saveQualityShapeFile",
						type: "post", 
						data:{profile_id:$profile_id1, shape_file_area:$shape_file_area1, shape_file_area_remark:$shape_file_area_remark1, variation_area:$variation_area1, variation_area_remark:$variation_area_remark1, fromno:$fromno1}, 
						error:function(xhr,status,er){
							  console.log(er);
						},
						success:function(data) {
							console.log(data);
							if(data==='success')
							{
								successAlert('Data Saved Successfully !');
								$("#successok").click(function(){
									$('#popup').modal('hide');
									window.location.href='getQualityShapeFile';
								});  
								$(".close").click(function(){
									$('#popup').modal('hide');
									window.location.href='getQualityShapeFile';
								});
							}
							if(data==='update')
							{
								successAlert('Data Update Successfully !');
								$("#successok").click(function(){
									$('#popup').modal('hide');
									window.location.href='getQualityShapeFile';
								});  
								$(".close").click(function(){
									$('#popup').modal('hide');
									window.location.href='getQualityShapeFile';
								});
							}
							else{
								alert('Some error occurred');
							}
						}
					});
									
			});	
			
			$(document).on( 'click', '#geotagbtnSave', function (e) {
					e.preventDefault();
												
					$profile_id1 = $('#profileid').val();
					$geo_tagg_work1 = $('#geo_tagg_work').val();
					$geo_tagg_work_remark1 = $('#geo_tagg_work_remark').val();
					$fromno1 = $('#fromno').val();
												
					if($('#geo_tagg_work').val()==='')
					{
						alert('Please enter total Geo-Tagged Works');
						$('#geo_tagg_work').focus();
						return false;
					}
										
					$.ajax({  
						url:"saveGeoTagDetails",
						type: "post", 
						data:{profile_id:$profile_id1, geo_tagg_work:$geo_tagg_work1, geo_tagg_work_remark:$geo_tagg_work_remark1, fromno:$fromno1}, 
						error:function(xhr,status,er){
								console.log(er);
						},
						success:function(data) {
							console.log(data);
						if(data==='success')
						{
							successAlert('Data Saved Successfully !');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								window.location.href='getStatusGeotagWork';
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								window.location.href='getStatusGeotagWork';
							});
						}
						if(data==='update')
						{
							successAlert('Data Update Successfully !');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								window.location.href='getStatusGeotagWork';
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								window.location.href='getStatusGeotagWork';
							});
						}
						else{
							alert('Some error occurred');
						}
					}
				});
												
			});	
	
	
	
	
});	