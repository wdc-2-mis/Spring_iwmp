
$(function() {

	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$stCode = $('#district option:selected').val();
		$.ajax({
			url: "getWatershedYatraBlock",
			type: "post",
			data: { stateCode: $stCode },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				$selectedDist = $('#district').val();
				$ddlDistrict = $('#block');
				$ddlDistrict.empty();
				$ddlDistrict.append('<option value=""> --Select Block-- </option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						if (data[key] == $selectedDist)
							$ddlDistrict.append('<option value="' + data[key] + '" selected>' + key + '</option>');
						else
							$ddlDistrict.append('<option value="' + data[key] + '">' + key + '</option>');
					}
				}
			}
		});
	});


	//	$(document).on('change', '#block', function(e) {
	//			e.preventDefault();
	//			$blkCode=$('#block option:selected').val();
	//			$.ajax({  
	//	            url:"getWatershedYatraGPs",
	//	            type: "post", 
	//				data:{blkCode:$blkCode}, 
	//	            error:function(xhr,status,er){
	//	                console.log(er);
	//	            },
	//	            success:function(data) {
	//							$selectedDist=$('#block').val();
	//							$ddlDistrict = $('#grampan');
	//							$ddlDistrict.empty();
	//	        				$ddlDistrict.append('<option value=""> --Select Gram Panchayat Name-- </option>');
	//							 for ( var key in data) {
	//							   if (data.hasOwnProperty(key)) {
	//								if(data[key]==$selectedDist)
	//								$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
	//								else
	//								$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
	//								}
	//								}
	//				}
	//			});
	//		});
	//			
	//		$(document).on('change', '#grampan', function(e) {
	//					e.preventDefault();
	//					$gpsCode=$('#grampan option:selected').val();
	//					$.ajax({  
	//			            url:"getWatershedYatraVillage",
	//			            type: "post", 
	//						data:{gpscode:$gpsCode}, 
	//			            error:function(xhr,status,er){
	//			                console.log(er);
	//			            },
	//			            success:function(data) {
	//									$selectedDist=$('#grampan').val();
	//									$ddlDistrict = $('#village');
	//									$ddlDistrict.empty();
	//			        				$ddlDistrict.append('<option value=""> --Select Village Name-- </option>');
	//									 for ( var key in data) {
	//									   if (data.hasOwnProperty(key)) {
	//										if(data[key]==$selectedDist)
	//										$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
	//										else
	//										$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
	//										}
	//										}
	//						}
	//					});
	//				});

	/********************************************** Page Load Script ************************************* */
	//	var selectedState = $('#state option:selected').val();
	//	if(selectedState!=null && selectedState!='' && selectedState>0){
	//		$("#state").trigger("change");
	//		$('#district option:selected').val($('#dcode').val());
	//		$("#district").trigger("change");
	//		$('#project option:selected').val($('#projid').val());
	//		$("#project").trigger("change");
	//	}
	//	
	//	});tblReport

	/******************************************** Onclick ************************************************ */
	$(document).on('click', '#click11', function(e) {

		e.preventDefault();

		$district = $('#district option:selected').val();
		$block = $('#block option:selected').val();

		$date = $('#date').val();
		$location = $('#location').val();
		$maleParticipants = $('#maleParticipants').val();
		$femaleParticipants = $('#femaleParticipants').val();
		$centralMinisters = $('#centralMinisters').val();
		$stateMinisters = $('#stateMinisters').val();
		$parliament = $('#parliament').val();
		$assemblyMembers = $('#assemblyMembers').val();
		$councilMembers = $('#councilMembers').val();
		$others = $('#others').val();
		$govOfficials = $('#govOfficials').val();
		$flagOff = $('input[name="flagOff"]:checked').val() === 'true';
		$flagOffPhotos = $('#flagOffPhotos').val();
		$themeSong = $('input[name="themeSong"]:checked').val() === 'true';
		$themeSongPhotos = $('#themeSongPhotos').val();
		$noWorksBhoomiPoojan = $('#noWorksBhoomiPoojan').val();
		$totWorksBhoomiPoojan = $('#totWorksBhoomiPoojan').val();
		$bhoomiPoojanPhotos = $('#bhoomiPoojanPhotos').val();
		$noWorksLokarpan = $('#noWorksLokarpan').val();
		$totWorksLokarpan = $('#totWorksLokarpan').val();
		$lokarpanPhotos = $('#lokarpanPhotos').val();
		$noLocationShramdaan = $('#noLocationShramdaan').val();
		$costPeopleShramdaan = $('#costPeopleShramdaan').val();
		$shramdaanPhotos = $('#shramdaanPhotos').val();
		$areaPlantation = $('#areaPlantation').val();
		$noPlantation = $('#noPlantation').val();
		$plantationPhotos = $('#plantationPhotos').val();
		$noAwards = $('#noAwards').val();
		$awardPhotos = $('#awardPhotos').val();



		if ($date === '' || typeof $date === 'undefined') {
			alert('Please select Date');
			$('#date').focus();
			return false;
		}
		if ($('#district option:selected').val() === '' || typeof $('#district option:selected').val() === 'undefined') {
			alert('Please select District');
			$('#district').focus();
			return false;
		}
		if ($block === '' || typeof $block === 'undefined') {
			alert('Please select Block');
			$('#block').focus();
			return false;
		}
		if ($location === '' || typeof $location === 'undefined') {
			alert('Please enter Location');
			$('#location').focus();
			return false;
		}
		if ($maleParticipants === '' || typeof $maleParticipants === 'undefined') {
			alert('Please enter Number Of Male Participants/Villagers');
			$('#maleParticipants').focus();
			return false;
		}
		if ($femaleParticipants === '' || typeof $femaleParticipants === 'undefined') {
			alert('Please enter Number Of Female Participants/Villagers');
			$('#femaleParticipants').focus();
			return false;
		}
		if ($centralMinisters === '' || typeof $centralMinisters === 'undefined') {
			alert('Please enter Number of Central Ministers');
			$('#centralMinisters').focus();
			return false;
		}
		if ($stateMinisters === '' || typeof $stateMinisters === 'undefined') {
			alert('Please enter Number of State Ministers');
			$('#stateMinisters').focus();
			return false;
		}
		if ($parliament === '' || typeof $parliament === 'undefined') {
			alert('Please enter Number of Member of Parliament');
			$('#parliament').focus();
			return false;
		}
		if ($assemblyMembers === '' || typeof $assemblyMembers === 'undefined') {
			alert('Please enter Number of Legislative Assembly Members');
			$('#assemblyMembers').focus();
			return false;
		}
		if ($councilMembers === '' || typeof $councilMembers === 'undefined') {
			alert('Please enter Number of Legislative Council Members');
			$('#councilMembers').focus();
			return false;
		}
		if ($others === '' || typeof $others === 'undefined') {
			alert('Please enter Number of other Public Representatives');
			$('#others').focus();
			return false;
		}
		if ($govOfficials === '' || typeof $govOfficials === 'undefined') {
			alert('Please enter Number of Government Officials');
			$('#govOfficials').focus();
			return false;
		}
		if ($flagOff === '' || typeof $flagOff === 'undefined') {
			alert('Please select Flag off of Van');
			$('#flagOff').focus();
			return false;
		}
		if ($themeSong === '' || typeof $themeSong === 'undefined') {
			alert('Please select Launch of Theme Song');
			$('#themeSong').focus();
			return false;
		}
		if ($noWorksBhoomiPoojan === '' || typeof $noWorksBhoomiPoojan === 'undefined') {
			alert('Please enter Number of Works of Bhoomi Poojan');
			$('#noWorksBhoomiPoojan').focus();
			return false;
		}
		if ($totWorksBhoomiPoojan === '' || typeof $totWorksBhoomiPoojan === 'undefined') {
			alert('Please enter Cost of Total Works of Bhoomi Poojan');
			$('#totWorksBhoomiPoojan').focus();
			return false;
		}
		if ($noWorksLokarpan === '' || typeof $noWorksLokarpan === 'undefined') {
			alert('Please enter Number of Works of Lokarpan');
			$('#noWorksLokarpan').focus();
			return false;
		}
		if ($totWorksLokarpan === '' || typeof $totWorksLokarpan === 'undefined') {
			alert('Please enter Cost of Total Works of Lokarpan');
			$('#totWorksLokarpan').focus();
			return false;
		}
		if ($noLocationShramdaan === '' || typeof $noLocationShramdaan === 'undefined') {
			alert('Please enter Number of Locations of Shramdaan');
			$('#noLocationShramdaan').focus();
			return false;
		}
		if ($costPeopleShramdaan === '' || typeof $costPeopleShramdaan === 'undefined') {
			alert('Please enter Cost of people participated of Shramdaan');
			$('#costPeopleShramdaan').focus();
			return false;
		}
		if ($areaPlantation === '' || typeof $areaPlantation === 'undefined') {
			alert('Please enter Plantation Area');
			$('#areaPlantation').focus();
			return false;
		}
		if ($noPlantation === '' || typeof $noPlantation === 'undefined') {
			alert('Please enter Number of Agro forsetry / Horticultural Plants');
			$('#noPlantation').focus();
			return false;
		}
		if ($noAwards === '' || typeof $noAwards === 'undefined') {
			alert('Please enter Number of Award Distribution');
			$('#noAwards').focus();
			return false;
		}


		$.ajax({
			url: "saveInaugurationDetails1",
			type: "post",
			data: {
				district: $district, block: $block, date: $date, location: $location, maleParticipants: $maleParticipants, femaleParticipants: $femaleParticipants,
				centralMinisters: $centralMinisters, stateMinisters: $stateMinisters, parliament: $parliament, assemblyMembers: $assemblyMembers, councilMembers: $councilMembers,
				others: $others, govOfficials: $govOfficials, flagOff: $flagOff, flagOffPhotos: $flagOffPhotos, themeSong: $themeSong, themeSongPhotos: $themeSongPhotos,
				noWorksBhoomiPoojan: $noWorksBhoomiPoojan, totWorksBhoomiPoojan: $totWorksBhoomiPoojan, bhoomiPoojanPhotos: $bhoomiPoojanPhotos, noWorksLokarpan: $noWorksLokarpan,
				totWorksLokarpan: $totWorksLokarpan, lokarpanPhotos: $lokarpanPhotos, noLocationShramdaan: $noLocationShramdaan, costPeopleShramdaan: $costPeopleShramdaan,
				shramdaanPhotos: $shramdaanPhotos, areaPlantation: $areaPlantation, noPlantation: $noPlantation, plantationPhotos: $plantationPhotos, noAwards: $noAwards, awardPhotos: $awardPhotos
			},
			
			error: function(xhr, status, er) {
				console.log(er);
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				if (data === 'success') {
					alert('Data Saved Successfully !');
					//						
					window.location.href = 'inaugurationLocation';

				}
				if (data === 'fail') {
					alert('Data not Saved Successfully');
					//						
					window.location.href = 'inaugurationLocation';

				}
			}
		});
	});









});