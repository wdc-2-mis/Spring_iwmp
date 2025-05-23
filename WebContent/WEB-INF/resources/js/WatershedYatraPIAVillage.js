$(function(){

	$(document).on( 'change', '#level', function (e) {
		e.preventDefault();
						
		$gType = $('#level').val();
						
		if($gType==='state')
		{
			$('.yeark').addClass('d-none');
			$('.mont').addClass('d-none');
							
			$("select#district")[0].selectedIndex = 0;
			$("select#block")[0].selectedIndex = 0;
		}
		else if($gType==='district')
		{
			$('.yeark').removeClass('d-none');
			$('.mont').addClass('d-none');
							
			$("select#district")[0].selectedIndex = 0;
			$("select#block")[0].selectedIndex = 0;
		}
		else{
						
			$('.yeark').removeClass('d-none');
			$('.mont').removeClass('d-none');
							
			$("select#district")[0].selectedIndex = 0;
			$("select#block")[0].selectedIndex = 0;
		}			
				
	});	
	
	$(document).on( 'click', '#btnSave', function (e) {
		e.preventDefault();
						
		$level = $('#level option:selected').val();
		$district = $('#district').val();
		$block = $('#block option:selected').val();
						
		$name = $('#name').val();
		$designation = $('#designation').val();
		$mob = $('#mob').val();
		$email = $('#email').val();
		
		if($('#level option:selected').val()==='' || typeof $('#level option:selected').val()==='undefined')
		{
			alert('Please select level !');
			$('#level').focus();
			return false;
		}
		
		if($('#level option:selected').val()==='block' )
		{
			if($('#block option:selected').val()==='' || typeof $('#block option:selected').val()==='undefined')
			{
				alert('Please Select Block !');
				$('#block').focus();
				return false;
			}
		}
		if($('#name').val()==='')
		{
			alert('Please Enter Name !');
			$('#name').focus();
			return false;
		}
		if($('#designation').val()==='')
		{
			alert('Please Enter Designation !');
			$('#designation').focus();
			return false;
		}
		if($('#mob').val()==='')
		{
			alert('Please Enter Mobile !');
			$('#mob').focus();
			return false;
		}
		if($('#email').val()==='')
		{
			alert('Please Enter Email Id !');
			$('#email').focus();
			return false;
		}
		if($('#district option:selected').val()==='')
		{					
			$district = 0;
		}	
		if($('#block option:selected').val()==='')
		{					
			$block = 0;
		}
					
		$.ajax({  
				url:"saveNodalOfficerLMS",
				type: "post", 
				data:{level:$level, district:$district, block:$block, name:$name, designation:$designation, mob:$mob, email:$email}, 
				      error:function(xhr,status,er){
				      console.log(er);
				},
				success:function(data) 
				{
					console.log(data);
					if(data==='success')
					{
						successAlert('Data Saved Successfully !');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							window.location.href='getNodalOfficerHeader';
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
							window.location.href='getNodalOfficerHeader';
						});
					}
					else if(data==='fail')
					{
						successAlert('Data not Saved Successfully, Email Already Register.');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							window.location.href='getNodalOfficerHeader';
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
							window.location.href='getNodalOfficerHeader';
						});
					}
				 }
			});
						
						
		});				
				
		$('#chkSelectAllkd').on('click',function(){
				$chkValue=0;
		        if(this.checked)
		        {
		            $('.chkIndividualkd').each(function(){
		                this.checked = true;
						$chkValue++;
		            });
		        }
		        else{
		             $('.chkIndividualkd').each(function(){
		                this.checked = false;
		            });
					$chkValue=0;
		        }
		});
			
		$(document).on('click', '#updateapprove', function(e){
				e.preventDefault();
			//	$pCode=$('#project option:selected').val();
				var finalAssetid=new Array();
				$('.chkIndividualkd').each(function(){
			         	if($(this).prop('checked'))
			         	{
							finalAssetid.push($(this).val());
						}
			     });
			//alert(finalAssetid+','+remarks+','+otherwork);
				if(confirm("Do you want to complete/Approved ?"))
				{
					$.ajax({  
			            url:"completeApproveNodalOfficer",
			            type: "post",  
			            data: {assetid:finalAssetid.toString()},
			            error:function(xhr,status,er){
			                console.log(er);
			            },
			            success: function(data) {
						console.log(data);
						$('#loading').hide();
							if(data==='success')
							{
								alert('Nodal Officer Approve Successfully.');
								window.location.href='getNodalOfficerHeader';
							}
							else{
								alert('Please check at least One Check Box, Data not Complete!');
								window.location.href='getNodalOfficerHeader';
							} 
				
						}
					});
				}
		});
				
		$(document).on('click', '#delete', function(e){
					e.preventDefault();
					var finalAssetid=new Array();

					$('.chkIndividualkd').each(function(){
						if($(this).prop('checked'))
						{
							finalAssetid.push($(this).val());
						}
					});
							      
					if(confirm("Do you want to Delete ?"))
					{
						$.ajax({  
							    url:"deleteApproveNodalOfficer",
							    type: "post",  
							    data: {assetid:finalAssetid.toString()},
							    error:function(xhr,status,er){
							         console.log(er);
							    },
							    success: function(data) 
								{
									console.log(data);
									$('#loading').hide();
									if(data==='success')
									{
										alert('Nodal Officer deleted Successfully.');
										window.location.href='getNodalOfficerHeader';
									}
									else{
										alert('Please check at least One Check Box, Data not Complete!');
										window.location.href='getNodalOfficerHeader';
									} 
								}
						});
					}
				});				
	
});		