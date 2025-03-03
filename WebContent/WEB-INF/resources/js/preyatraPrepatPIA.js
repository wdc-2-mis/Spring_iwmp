$(function(){

	$(document).on('change', '#block', function(e) {
				e.preventDefault();
				$blkCode=$('#block option:selected').val();
				$.ajax({  
		            url:"getWatershedYatraAtPiaGPs",
		            type: "post", 
					data:{blkCode:$blkCode}, 
		            error:function(xhr,status,er){
		                console.log(er);
		            },
		            success:function(data) {
								$selectedDist=$('#block').val();
								$ddlDistrict = $('#grampan');
								$ddlDistrict.empty();
		        				$ddlDistrict.append('<option value=""> --Select Gram Panchayat Name-- </option>');
								 for ( var key in data) {
								   if (data.hasOwnProperty(key)) {
									if(data[key]==$selectedDist)
									$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
									else
									$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
									}
									}
					}
				});
	});	
	
	$(document).on('change', '#block1', function(e) {
					e.preventDefault();
					$blkCode=$('#block1 option:selected').val();
					$.ajax({  
			            url:"getWatershedYatraAtPiaGPs",
			            type: "post", 
						data:{blkCode:$blkCode}, 
			            error:function(xhr,status,er){
			                console.log(er);
			            },
			            success:function(data) {
									$selectedDist=$('#block1').val();
									$ddlDistrict = $('#grampan1');
									$ddlDistrict.empty();
			        				$ddlDistrict.append('<option value=""> --Select Gram Panchayat Name-- </option>');
									 for ( var key in data) {
									   if (data.hasOwnProperty(key)) {
										if(data[key]==$selectedDist)
										$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
										else
										$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
										}
										}
						}
					});
		});	
	
		$(document).on('change', '#grampan1', function(e) {
								e.preventDefault();
								$gpsCode=$('#grampan1 option:selected').val();
								$.ajax({  
						            url:"getWatershedYatraAtPiaVillage",
						            type: "post", 
									data:{gpscode:$gpsCode}, 
						            error:function(xhr,status,er){
						                console.log(er);
						            },
						            success:function(data) {
												$selectedDist=$('#grampan1').val();
												$ddlDistrict = $('#village1');
												$ddlDistrict.empty();
						        				$ddlDistrict.append('<option value=""> --Select Village Name-- </option>');
												 for ( var key in data) {
												   if (data.hasOwnProperty(key)) {
													if(data[key]==$selectedDist)
													$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
													else
													$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
													}
													}
									}
								});
							});

	});