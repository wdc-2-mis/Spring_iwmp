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
						    var finalAssetid = new Array();

						    $('.chkIndividualkd').each(function(){
						        if($(this).prop('checked')) {
						            finalAssetid.push($(this).val());
						        }
						    });

						    if(finalAssetid.length === 0) {
						        alert('Please check at least One Check Box, Data not Complete!');
						    } else {
						        if(confirm("Do you want to Delete ?")) {
						            $.ajax({  
						                url: "deleteWatershedYatraPIADetails",
						                type: "post",  
						                data: {assetid: finalAssetid.toString()},
						                error: function(xhr, status, er){
						                    console.log(er);
						                },
						                success: function(data) {
						                    console.log(data);
						                    $('#loading').hide();
						                    if(data === 'success') {
						                        alert('Deleted Data and Uploaded File Successfully.');
						                        window.location.href = 'getWatershedYatraAtPiaHeader';
						                    } else {
						                        alert('Issue on Deleted Data!');
						                        window.location.href = 'getWatershedYatraAtPiaHeader';
						                    } 
						                }
						            });
						        }
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
																																			      
									if(finalAssetid.length === 0) {
										        alert('Please check at least One Check Box, Data not Complete!');
										    } 
											else{
												if(confirm("Do you want to Complete ?"))
									{
										$.ajax({  
												url:"completeWatershedYatraPIADetails",
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
														window.location.href='getWatershedYatraAtPiaHeader';
													}
													else{
															alert('Issue on Completed Record, Data not Complete!');
															window.location.href='getWatershedYatraAtPiaHeader';
													} 
												}
										});
									}
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

});	



				