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
	
	$(document).on('change', '#grampan', function(e) {
						e.preventDefault();
						$gpsCode=$('#grampan option:selected').val();
						$.ajax({  
				            url:"getWatershedYatraAtPiaVillage",
				            type: "post", 
							data:{gpscode:$gpsCode}, 
				            error:function(xhr,status,er){
				                console.log(er);
				            },
				            success:function(data) {
										$selectedDist=$('#grampan').val();
										$ddlDistrict = $('#village');
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
					
					
					$(document).on('change', '#village', function(e) {
					  e.preventDefault();
					  $villageCode = $('#village option:selected').val();
					  $.ajax({
					    url: "getExistingWatershedYatraVillageCodes",
					    type: "post",
					    data: {villageCode: $villageCode},
					    error: function(xhr, status, er) {
					      console.log(er);
					    },
					    success: function(data) 
					    {
							if(data==='success')
							{
								alert('Village already exists. Please select a different village. !');
								$("select#village")[0].selectedIndex = 0;
											
							}
										
					    }
					  });
					});
	
	
	
});	