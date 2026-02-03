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
			
			/*$.ajax({
			            url: 'checkMahotsavInaugurationExits',
			            type: 'POST',
			            data: {},
			            success: function(exists) {
			                if (exists) {
								alert('State Data Already Exists. Only One Inauguration Program Entry Allow !');
									$("select#district")[0].selectedIndex = 0;
									$("select#block")[0].selectedIndex = 0;
			                }
			            }
			});*/
			
		});
		
		$(document).on('change', '#project', function(e) {
					e.preventDefault();
					$pjCode = $('#project option:selected').val();
					$.ajax({
						url: "getPunarutthanVillage",
						type: "post",
						data: { pCode: $pjCode },
						error: function(xhr, status, er) {
							console.log(er);
						},
						success: function(data) {
							$selectedDist = $('#project').val();
							$village = $('#village1');
							$village.empty();
							$village.append('<option value=""> --Select Village-- </option>');
							for (var key in data) {
								if (data.hasOwnProperty(key)) {
									if (data[key] == $selectedDist)
										$village.append('<option value="' + data[key] + '" selected>' + key + '</option>');
									else
										$village.append('<option value="' + data[key] + '">' + key + '</option>');
								}
							}
						}
					});
					
					
		});


		$(document).on('change', '#village11', function(e) {
			e.preventDefault(); //getExistingPunarutthanPlanVillageCodes
			$villageCode = $('#village1 option:selected').val();
			$.ajax({
				url: "getExistingPunarutthanPlanVillageCodes",
				type: "post",
				data: { villageCode: $villageCode },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					if (data === 'success') {
						alert('Village already exists. Please select a different Village. !');
						$("select#village1")[0].selectedIndex = 0;

					}

				}
			});
	});
$('#chkSelectAllkd').on('click', function() {
		$chkValue = 0;
		if (this.checked) {
			$('.chkIndividualkd').each(function() {
				this.checked = true;
				$chkValue++;
			});
		}
		else {
			$('.chkIndividualkd').each(function() {
				this.checked = false;
			});
			$chkValue = 0;
		}
	});

	$(document).on('click', '#delete', function(e) {
		e.preventDefault();
		var finalAssetid = new Array();

		$('.chkIndividualkd').each(function() {
			if ($(this).prop('checked')) {
				finalAssetid.push($(this).val());
			}
		});

		if (confirm("Do you want to Delete ?")) {
			$.ajax({
				url: "deletePunarutthanPlanDetails",
				type: "post",
				data: { assetid: finalAssetid.toString() },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						alert('Deleted Successfully.');
						window.location.href = 'getWatershedPunarutthanPlan';
					}
					else {
						alert('Please check at least One Check Box, Data not Delete!');
						window.location.href = 'getWatershedPunarutthanPlan';
					}
				}
			});
		}
	});



	$(document).on('click', '#complete', function(e) {
		e.preventDefault();
		var finalAssetid = new Array();

		$('.chkIndividualkd').each(function() {
			if ($(this).prop('checked')) {
				finalAssetid.push($(this).val());
			}
		});

		if (confirm("Do you want to Complete ?")) {
			$.ajax({
				url: "completePunarutthanPlanDetails",
				type: "post",
				data: { assetid: finalAssetid.toString() },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						alert('Complete Successfully.');
						window.location.href = 'getWatershedPunarutthanPlan';
					}
					else {
						alert('Please check at least One Check Box, Data not Complete!');
						window.location.href = 'getWatershedPunarutthanPlan';
					}
				}
			});
		}
	});
	


		$(document).on('click', '.showImage', function(e) {
			
			$inaugId = e.target.getAttribute('data-id');
			$.ajax({  // getImageWatershedPunarutthanPlan
				type: 'POST',
				url: "getImageWatershedPunarutthanPlan",
				data: { planid: $inaugId },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					var imageContainer = $('.image-container');
					imageContainer.empty();
					let list = '<ul>';
					for (let i = 0; i < data.length; i++) {
						if (data[i] != null) 
						{
						//PRD
							list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/punarutthan/planing/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
						//TEST
						//	list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/punarutthan/planing/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
						//Local
						//	list += '<li><img src="resources/images/inauguration/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';

						}
					}
					list += '</ul>';
					document.getElementById('imageList').innerHTML = list;
					document.getElementById('imagePopup').style.display = 'block';
				}
			});
		});
		
		$(document).on('click', '#deleteimpl', function(e) {
				e.preventDefault();
				var finalAssetid = new Array();

				$('.chkIndividualkd').each(function() {
					if ($(this).prop('checked')) {
						finalAssetid.push($(this).val());
					}
				});

				if (confirm("Do you want to Delete ?")) {
					$.ajax({
						url: "deletePunarutthanImplementation",
						type: "post",
						data: { assetid: finalAssetid.toString() },
						error: function(xhr, status, er) {
							console.log(er);
						},
						success: function(data) {
							console.log(data);
							$('#loading').hide();
							if (data === 'success') {
								alert('Deleted Successfully.');
								window.location.href = 'getWatershedPunarutthanPlanImplement';
							}
							else {
								alert('Please check at least One Check Box, Data not Delete!');
								window.location.href = 'getWatershedPunarutthanPlanImplement';
							}
						}
					});
				}
			});



			$(document).on('click', '#completeimpl', function(e) {
				e.preventDefault();
				var finalAssetid = new Array();

				$('.chkIndividualkd').each(function() {
					if ($(this).prop('checked')) {
						finalAssetid.push($(this).val());
					}
				});

				if (confirm("Do you want to Complete ?")) {
					$.ajax({
						url: "completePunarutthanImplementation",
						type: "post",
						data: { assetid: finalAssetid.toString() },
						error: function(xhr, status, er) {
							console.log(er);
						},
						success: function(data) {
							console.log(data);
							$('#loading').hide();
							if (data === 'success') {
								alert('Complete Successfully.');
								window.location.href = 'getWatershedPunarutthanPlanImplement';
							}
							else {
								//alert('Please check at least One Check Box, Data not Complete!');
								window.location.href = 'getWatershedPunarutthanPlanImplement';
							}
						}
					});
				}
			});
			
	
});	
