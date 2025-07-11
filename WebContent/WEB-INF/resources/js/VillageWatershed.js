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
							alert('Village already exists. Please select a different village !');
							$("select#village")[0].selectedIndex = 0;
						}
					//	if(data==='successkd')
					//	{
					//		alert('Village already exists. Do you want to again entry for this village !');
							//$("select#village")[0].selectedIndex = 0;
					//	}
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
					url:"deleteWatershedYatraDetails",
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
							alert('Deleted Data Successfully.');
							window.location.href='getWatershedYatraHeader';
						}
						else{
							alert('Please check at least One Check Box, Data not Complete!');
							window.location.href='getWatershedYatraHeader';
						} 
					}
			});
		}
	});	
				
	$(document).on('click', '#complete', function(e){
			e.preventDefault();
			var finalAssetid=new Array();

			$('.chkIndividualkd').each(function(){
					if($(this).prop('checked'))
					{
						finalAssetid.push($(this).val());
					}
			});
																													      
			if(confirm("Do you want to Complete ?"))
			{
				$.ajax({  
						url:"completeWatershedYatraDetails",
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
								alert('Completed Successfully.');
								window.location.href='getWatershedYatraHeader';
							}
							else{
									alert('Please check at least One Check Box, Data not Complete!');
									window.location.href='getWatershedYatraHeader';
							} 
						}
				});
			}
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
						list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/vanyatradoc/WatershedYatraVillage/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';

									//TEST
//						list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/vanyatradoc/WatershedYatraVillage/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
															
									//Local
//									list += '<li><img src="resources/images/WatershedYatraVillage/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
															
								}
							}
							list += '</ul>';
							document.getElementById('imageList').innerHTML = list;
							document.getElementById('imagePopup').style.display = 'block';
						}
					});
				});

				function getImageHash(file, callback) {
				    const reader = new FileReader();
				    reader.onload = function (e) {
				        const data = e.target.result;
				        const hash = CryptoJS.MD5(CryptoJS.enc.Latin1.parse(data)).toString(CryptoJS.enc.Hex);
				        callback(hash);
				    };
				    reader.readAsBinaryString(file);
				}

				const imageRecords = {}; // Store image records with filename as key and hash as value

				function validatePhoto(input, photoId, maxSizeKB, maxWidth, maxHeight) {
				    if (input.files && input.files[0]) {
				        let file = input.files[0];
				        let fileSizeKB = file.size / 1024;
						let allowedTypes = ["image/jpeg", "image/png"];
			
						if (!allowedTypes.includes(file.type)) {
						    alert("Invalid file type. Only JPEG and PNG images are allowed.");
						    input.value = "";
						    return;
						}
				        // Validate file size
				        if (fileSizeKB > maxSizeKB) {
				            alert("File size exceeds " + maxSizeKB + " KB. Please upload a smaller image.");
				            input.value = "";
				            return;
				        }

				        let specialCharPattern = /[^\w.-]/;

				        // Check if file name contains special characters
				        if (specialCharPattern.test(file.name)) {
				            alert("File name contains special characters. Please rename the file without special characters and try again.");
				            input.value = "";
				            return;
				        }

				        getImageHash(file, function (hash) {
				            if (imageRecords[file.name] && imageRecords[file.name] === hash) {
				                alert("This image has been already uploaded. Please upload a different image.");
				                input.value = "";
				                return;
				            }

				            imageRecords[file.name] = hash;

				            let img = new Image();
				            img.src = URL.createObjectURL(file);
				            img.onload = function () {
				                // Validate image dimensions
				                if (this.width > maxWidth || this.height > maxHeight) {
				                    alert("Image dimensions should not exceed " + maxWidth + "x" + maxHeight);
				                    input.value = "";
				                    return;
				                }
				            };

				            // Extract metadata using EXIF.js
				            EXIF.getData(file, function () {
				                let latitude = EXIF.getTag(this, "GPSLatitude");
				                let longitude = EXIF.getTag(this, "GPSLongitude");
				                let dateTimeOriginal = EXIF.getTag(this, "DateTimeOriginal");

				                // Convert GPS coordinates to decimal format
				                function convertDMSToDD(dms, ref) {
				                    if (!dms) return null;
				                    let degrees = dms[0].numerator / dms[0].denominator;
				                    let minutes = dms[1].numerator / dms[1].denominator / 60;
				                    let seconds = dms[2].numerator / dms[2].denominator / 3600;
				                    let decimal = degrees + minutes + seconds;
				                    return ref === "S" || ref === "W" ? -decimal : decimal;
				                }

				                let latRef = EXIF.getTag(this, "GPSLatitudeRef") || "N";
				                let lngRef = EXIF.getTag(this, "GPSLongitudeRef") || "E";
				                let lat = convertDMSToDD(latitude, latRef);
				                let lng = convertDMSToDD(longitude, lngRef);

				                // Set extracted values in hidden input fields
				                document.getElementById(photoId + "_lat").value = lat || "Not Available";
				                document.getElementById(photoId + "_lng").value = lng || "Not Available";
				                document.getElementById(photoId + "_time").value = dateTimeOriginal || "Not Available";

				                if (!lat || !lng) {
				                    if (!confirm("This photo does not contain longitude and latitude information. Are you sure you want to upload it?")) {
				                        input.value = "";
				                    }
				                }
				                console.log("Extracted Data:", { lat, lng, dateTimeOriginal });
				            });
				        });
				    }
				}

				// Attach validation to file inputs
				$('#arExperiencephoto1,#arExperiencephoto2,#shapathYesphoto1,#shapathYesphoto2,#FilmYesphoto1,#FilmYesphoto2,#quizParticipantsphoto1,#quizParticipantsphoto2,#culturalActivityphoto1,#culturalActivityphoto2,#bhoomiCostphoto1,#bhoomiCostphoto2,#lokWorksphoto1,#lokWorksphoto2,#locShramdaanpsphoto1,#locShramdaanpsphoto2,#plantationAreaphoto1,#plantationAreaphoto2,#noOfwatershedphoto1,#noOfwatershedphoto2').change(function () {
				    validatePhoto(this, this.id, 300, 400, 400);
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
										
										
										
	function myOverFunction(e) {
		
		$villageCode = $('#village option:selected').val();
		$loc= $('#location').val();
		//alert($loc);
						  $.ajax({
						    url: "getExistingWatershedYatraVillageLoction",
						    type: "post",
						    data: {villageCode: $villageCode, loct:$loc },
						    error: function(xhr, status, er) {
						      console.log(er);
						    },
						    success: function(data) 
						    {
								if(data==='success')
								{
									alert('Location can not be same, you have already enter.');
									$('#location').val('');
								}
								
						    }
						  });
										   
	}
										
