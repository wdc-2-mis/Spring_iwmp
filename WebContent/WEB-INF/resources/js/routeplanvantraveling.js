$(function(){
	
	$(document).on('change', '#district', function(e) {
			e.preventDefault();
			$stCode=$('#district option:selected').val();
			$.ajax({  
	            url:"getWatershedYatraBlock",
	            type: "post", 
				data:{stateCode:$stCode}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success:function(data) {
					$selectedDist=$('#district').val();
					$ddlDistrict = $('#block');
					$ddlDistrict.empty();
	        		$ddlDistrict.append('<option value=""> --Select Block-- </option>');
					for ( var key in data) 
					{
						if (data.hasOwnProperty(key)) 
						{
							if(data[key]==$selectedDist)
								$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
							else
								$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
						}
					}
				}
			});
		});
		
		
		$(document).on('change', '#block', function(e) {
				e.preventDefault();
				$blkCode=$('#block option:selected').val();
				$.ajax({  
		            url:"getWatershedYatraGPs",
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
						for ( var key in data) 
						{
							if (data.hasOwnProperty(key)) 
							{
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
				        url:"getWatershedYatraVillage",
				        type: "post", 
						data:{gpscode:$gpsCode}, 
				        error:function(xhr,status,er){
				                console.log(er);
				        },
				        success:function(data) 
						{
							$selectedDist=$('#grampan').val();
							$ddlDistrict = $('#village');
							$ddlDistrict.empty();
				        	$ddlDistrict.append('<option value=""> --Select Village Name-- </option>');
							for ( var key in data) 
							{
								if (data.hasOwnProperty(key)) 
								{
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
			    url: "getExistingVillageCodes",
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

	$(document).on('change', '#district1', function(e) {
		e.preventDefault();
		$stCode=$('#district1 option:selected').val();
		$.ajax({  
            url:"getWatershedYatraBlock1",
            type: "post", 
			data:{stateCode:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				$selectedDist=$('#district1').val();
				$ddlDistrict = $('#block1');
				$ddlDistrict.empty();
        		$ddlDistrict.append('<option value=""> --Select Block-- </option>');
				for ( var key in data) 
				{
						if (data.hasOwnProperty(key)) 
						{
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
	            url:"getWatershedYatraGPs1",
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
					for ( var key in data) 
					{
							if (data.hasOwnProperty(key))
							{
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
			    url:"getWatershedYatraVillage1",
			    type: "post", 
				data:{gpscode:$gpsCode}, 
			        error:function(xhr,status,er){
			           console.log(er);
			        },
			        success:function(data) 
					{
						$selectedDist=$('#grampan1').val();
						$ddlDistrict = $('#village1');
						$ddlDistrict.empty();
			        	$ddlDistrict.append('<option value=""> --Select Village Name-- </option>');
						for ( var key in data) 
						{
							if (data.hasOwnProperty(key)) 
							{
								if(data[key]==$selectedDist)
									$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
								else
									$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
							}
						}
					}
			});
	});
	
	$(document).on('change', '#village1', function(e) {
	  e.preventDefault();
	  $villageCode = $('#village1 option:selected').val();
	  $.ajax({
	    url: "getExistingVillageCodes",
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
				$("select#village1")[0].selectedIndex = 0;
							
			}
						
	    }
	  });
	});
	
	
				
	$(document).on( 'click', '#btnSavekd', function (e) {
		e.preventDefault();

		$district = $('#district option:selected').val();
		$block = $('#block option:selected').val();
		$grampan = $('#grampan option:selected').val();
		$village = $('#village option:selected').val();
		$location = $('#location').val();
		$datetime = $('#datetime').val();

		$district1 = $('#district1 option:selected').val();
		$block1 = $('#block1 option:selected').val();
		$grampan1 = $('#grampan1 option:selected').val();
		$village1 = $('#village1 option:selected').val();
		$location1 = $('#location1').val();
		$datetime1 = $('#datetime1').val();

		if ($('#district option:selected').val() === '' || typeof $('#district option:selected').val() === 'undefined') {
			alert('Please Select District for Location-01 !');
			$('#district').focus();
			return false;
		}
		if ($('#block option:selected').val() === '' || typeof $('#block option:selected').val() === 'undefined') {
			alert('Please Select Block for Location-01 !');
			$('#block').focus();
			return false;
		}
		if ($('#grampan option:selected').val() === '' || typeof $('#grampan option:selected').val() === 'undefined') {
			alert('Please Select Gram Panchayat for Location-01 !');
			$('#grampan').focus();
			return false;
		}
		if ($('#village option:selected').val() === '' || typeof $('#village option:selected').val() === 'undefined') {
			alert('Please Select Village for Location-01 !');
			$('#village').focus();
			return false;
		}
		
		if ($('#datetime').val() === '') {
			alert('Please enter Date and Time for Location-01 !');
			$('#datetime').focus();
			return false;
		}
		if ($('#location').val() === '') {
			alert('Please enter location for Location-01 !');
			$('#location').focus();
			return false;
		}

		if ($('#district1 option:selected').val() >0) {
		
		if ($('#district1 option:selected').val() === '' || typeof $('#district1 option:selected').val() === 'undefined') {
			alert('Please Select District for Location-02 !');
			$('#district1').focus();
			return false;
		}
		if ($('#block1 option:selected').val() === '' || typeof $('#block1 option:selected').val() === 'undefined') {
			alert('Please Select Block for Location-02 !');
			$('#block1').focus();
			return false;
		}
		if ($('#grampan1 option:selected').val() === '' || typeof $('#grampan1 option:selected').val() === 'undefined') {
			alert('Please Select Gram Panchayat for Location-02 !');
			$('#grampan1').focus();
			return false;
		}
		if ($('#village1 option:selected').val() === '' || typeof $('#village1 option:selected').val() === 'undefined') {
			alert('Please select Village for Location-01 !');
			$('#village1').focus();
			return false;
		}
		if ($('#datetime1').val() === '') {
			alert('Please enter Date and Time for Location-02!');
			$('#datetime1').focus();
			return false;
		}
		if ($('#location1').val() === '') {
			alert('Please enter location for Location-02!');
			$('#location1').focus();
			return false;
		}
		}
		else{
			
			$district1 = 0;
			$block1 = 0;
			$grampan1 = 0;
			$village1 = 0;
			$location1 ="";
			$datetime1 ="";
			
		}
		
		if ($('#district1 option:selected').val() >0) {

		const dateTimeStr = $('#datetime').val();
		const dateObj = new Date(dateTimeStr); // Extract date components 
		const year = dateObj.getFullYear();
		const month = dateObj.getMonth() + 1; // Months are zero-based in JavaScript 
		const day = dateObj.getDate();
		const hours = dateObj.getHours();
		const minutes = dateObj.getMinutes();
		const seconds = dateObj.getSeconds();

		const dateTimeStr1 = $('#datetime1').val();
		const dateObj1 = new Date(dateTimeStr1); // Extract date components 
		const year1 = dateObj1.getFullYear();
		const month1 = dateObj1.getMonth() + 1; // Months are zero-based in JavaScript 
		const day1 = dateObj1.getDate();
		const hours1 = dateObj1.getHours();
		const minutes1 = dateObj1.getMinutes();
		const seconds1 = dateObj1.getSeconds();

		const date1 = `${year}-${month}-${day}`;
		const date2 = `${year1}-${month1}-${day1}`;

		const time1 = '${hours}';
		const time2 = '${hours1}';

		if (date1 !== date2) {
			alert('Please select same date of both Location !');
			$('#datetime').val('');
			$('#datetime1').val('');
			$('#datetime').focus();
			return false;
		}

		if (date1 > date2) {
			alert('Please select same date of both Location !');
			$('#datetime').val('');
			$('#datetime1').val('');
			$('#datetime').focus();
			return false;
		}

		}
		/*if (time1 > time2) {
			alert('Please Select Location-02 Time will be Greater Than the Location-01 Time. !');
			$('#datetime').val('');
			$('#datetime1').val('');
			$('#datetime').focus();
			return false;
		}*/
					
			$.ajax({  
				url:"saveRoutePlanVanTravelingLMS",
				type: "post", 
				data:{district:$district, block:$block, grampan:$grampan, village:$village, location:$location, datetime:$datetime, district1:$district1, block1:$block1, grampan1:$grampan1, village1:$village1, location1:$location1, datetime1:$datetime1}, 
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
								window.location.href='getRoutePlanVanTravelingHeader';
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								window.location.href='getRoutePlanVanTravelingHeader';
							});
						}
						if(data==='fail')
						{
							successAlert('Data not Saved Successfully');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								window.location.href='getRoutePlanVanTravelingHeader';
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								window.location.href='getRoutePlanVanTravelingHeader';
							});
						}
					}
			});
										
	});			
				
				
});				