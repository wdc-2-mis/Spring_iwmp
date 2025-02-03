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
			            url:"getWatershedYatraVillage",
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
				

				$(document).on('click', '.showImage', function(e) {
					//var watershedYatraId = $('#watershedYatraId').val();
					$watershedYatraId= e.target.getAttribute('data-id');
					//alert('kdy'+watershedYatraId);
					//alert('kdy1'+$achid);
					$.ajax({
						type: 'POST',
						url: "getImageWatershedYatraId",
						data: { watershedYatraId:$watershedYatraId},
						error: function(xhr, status, er) {
							console.log(er);
						},
						success: function(data) {
							var imageContainer = $('.image-container');
							imageContainer.empty();
							let list = '<ul>';
							for (let i = 0; i < data.length; i++) {
								if (data[i] != null) {
									
									//PRD
//						list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/WatershedYatraVillage/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';

									//TEST
//						list += '<li><img src="https://wdcpmksy.dolr.gov.in/TEST/filepath/TESTING/vanyatradoc/WatershedYatraVillage/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
															
									//Local
									list += '<li><img src="resources/images/WatershedYatraVillage/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
															
								}
							}
							list += '</ul>';
							document.getElementById('imageList').innerHTML = list;
							document.getElementById('imagePopup').style.display = 'block';
						}
					});
				});


				
				
				$(document).on('click', '#submitbtn1', function (e) {
				    e.preventDefault();

				    // Collect all the form data
				    var formData = new FormData();
				    formData.append('datetime', $('#datetime').val());
				    formData.append('district', $('#district option:selected').val());
				    formData.append('block', $('#block option:selected').val());
				    formData.append('grampan', $('#grampan option:selected').val());
				    formData.append('village', $('#village option:selected').val());
				    formData.append('locShramdaanps', $('#locShramdaanps').val());
				    formData.append('locShramdaan', $('#locShramdaan').val());
				    formData.append('lokWorks', $('#lokWorks').val());
				    formData.append('costWorks', $('#costWorks').val());
				    formData.append('bhoomiCost', $('#bhoomiCost').val());
				    formData.append('bhoomiWorks', $('#bhoomiWorks').val());
				    formData.append('culturalActivity', $('#culturalActivity option:selected').val());
				    formData.append('quizParticipants', $('#quizParticipants').val());
				    formData.append('FilmYes', $('#FilmYes').val());
				    formData.append('shapathYes', $('#shapathYes').val());
				    formData.append('govOfficials', $('#govOfficials').val());
				    formData.append('publicReps', $('#publicReps').val());
				    formData.append('centralMinisters', $('#centralMinisters').val());
				    formData.append('stateMinisters', $('#stateMinisters').val());
				    formData.append('membersOfParliament', $('#membersOfParliament').val());
				    formData.append('legAssemblyMembers', $('#legAssemblyMembers').val());
				    formData.append('location', $('#location').val());
				    formData.append('plantationArea', $('#plantationArea').val());
				    formData.append('maleParticipants', $('#maleParticipants').val());
				    formData.append('femaleParticipants', $('#femaleParticipants').val());
				    formData.append('arExperience', $('#arExperience').val());
				    formData.append('legCouncilMembers', $('#legCouncilMembers').val());
				    formData.append('nofagrohorti', $('#nofagrohorti').val());
				    formData.append('noOfwatershed', $('#noOfwatershed').val());

				    // Append the file
				    var fileInput = $('#plantation_path1')[0].files[0]; // Assuming the file input has an ID of "plantation_path1"
				    if (fileInput) {
				        formData.append('plantation_path1', fileInput);
				    }
alert(fileInput);
				    // Make the AJAX call
				    $.ajax({
				        url: "saveWatershedYatraVillage",
				        type: "POST",
				        data: formData,
				        contentType: false, // Important for file upload
				        processData: false, // Important for file upload
				        error: function (xhr, status, er) {
				            console.log(er);
				        },
				        success: function (data) {
				            console.log(data);
				            if (data === 'success') {
				                successAlert('Data Saved Successfully !');
				                $("#successok").click(function () {
				                    $('#popup').modal('hide');
				                    window.location.href = 'getWatershedYatraHeader';
				                });
				                $(".close").click(function () {
				                    $('#popup').modal('hide');
				                    window.location.href = 'getWatershedYatraHeader';
				                });
				            }
				            if (data === 'fail') {
				                successAlert('Data not Saved Successfully');
				                $("#successok").click(function () {
				                    $('#popup').modal('hide');
				                    window.location.href = 'getWatershedYatraHeader';
				                });
				                $(".close").click(function () {
				                    $('#popup').modal('hide');
				                    window.location.href = 'getWatershedYatraHeader';
				                });
				            }
				        }
				    });
				});

														
										});		
