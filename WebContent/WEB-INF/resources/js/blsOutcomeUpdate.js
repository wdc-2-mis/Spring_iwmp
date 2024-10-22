/**
 * 
 */
$(document).ready(function() {
	
	/***************************** Project Change Script *************************************** */
	$(document).on('change', '#project', function(e) {
		//	$projId = $(this).val();
		$('#plotno').val('');
		$('#projectArea').val('');
		$('#ddlIrrigationStatus').val('');
		$('#ownername').val('');
		$('#ddlLandClass').val('');
		$('#subClassLand').val('');
		$('#ddlNoofCrop').val('');
		$('#ddlForestlandType').val('');
		$tblCropPlotDataBody = $('#tblCropPlotDataBody');
		$tblCropPlotDataBody.empty();
		$('#tblCropDeatilTbody').empty();

		$('#loading').show();
		$projId = $(this).val();
		$ddlVillage = $('#village');
		$.ajax({
			url: "getVillageOfProjectBlsUpdate",
			type: "post",
			data: { projId: $projId },
			error: function(xhr, status, er) {
				console.log(er);
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlVillage.empty();
				$ddlVillage.append("<option value='0'>---Select---</option>");
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlVillage.append("<option value='" + key + "'>" + data[key] + "</option>");
						}
					}
				}
				orderByNameDDL($ddlVillage);
			}
		});

	});


	/************************************************* End ************************************************* */

	/**************************************************** Village Change Script ******************************* */

	$(document).on('change', '#village', function(e) {
		$('#loading').show();
		$ddlOwnership = $('#ddlOwnership');
		$projId = $('#project').val();
		$vCode = $(this).val();
		$.ajax({
			url: "getOwnership",
			type: "post",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlOwnership.empty();
				$ddlOwnership.append("<option value=''>---Select---</option>");
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlOwnership.append("<option value='" + key + "'>" + data[key] + "</option>");
						}
					}
				}
			}
		});

		/***********plot */

		$ddlPlotClass = $('#plotno');
	
		$.ajax({
			url: "getPlotofVillageOfProject",
			type: "post",
			data: { projId: $projId, vCode: $vCode },
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlPlotClass.empty();
				$ddlPlotClass.append("<option value=''>---Select Plot No---</option>");
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlPlotClass.append("<option value='" + key + "'>" + data[key] + "</option>");
						}
					}
				}
			}
		});

		/************ plot end */
		$ddlLandClass = $('#ddlLandClass');
		$.ajax({
			url: "getLandClassification",
			type: "post",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlLandClass.empty();
				$ddlLandClass.append("<option value=''>---Select---</option>");
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlLandClass.append("<option value='" + key + "'>" + data[key] + "</option>");
						}
					}
				}
			}
		});


		$ddlIrrigationStatus = $('#ddlIrrigationStatus');
		$.ajax({
			url: "getIrrigationStatus",
			type: "post",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlIrrigationStatus.empty();
				$ddlIrrigationStatus.append("<option value=''>---Select---</option>");
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlIrrigationStatus.append("<option value='" + key + "'>" + data[key] + "</option>");
						}
					}
				}
			}
		});

	});

	/*************************************************************** End ***************************************************** */
	$dbownership = '';
	$dbLandclass = '';
	$dbNoofCrop = '';

	/************************************************* Plot Change Script ****************************************** */
	$(document).on('change', '#plotno', function(e) {
		e.preventDefault();
		$plotNo = $('#plotno').val();
		$vcode = $('#village').val();
		$forestLandTemp = '';

		/* ---------------Crop Type Data Start -------------- */

		/* ---------------Crop Type Data End-------------- */

		$.ajax({
			url: "getPlotDataOfAVillagetoUpdate",
			type: "post",
			data: { vcode: $vcode, plotno: $plotNo },
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
//								alert('hi 1 '+ data[key].micro_irrigation);
							$('#projectArea').val(data[key].area);
							$('#blsOutDetailId').val(data[key].bls_out_detail_id_pk);
							$('#ddlIrrigationStatus').val(data[key].irrigation_status_id);
							$('#ddlMicroIrrigation').val(data[key].micro_irrigation);
							$('#ddlOwnership').val(data[key].ownership_id);
							$('#subClassLand').val(data[key].land_sub_classification);
							$dbownership = data[key].ownership_id;
							$forestLandTemp = data[key].forest_land_type_id

							$('#ddlLandClass').val(data[key].classification_land_id);
							$dbLandclass = data[key].classification_land_id;
							//////* Classification Land */ 				
							//$('.ddlLandClass').trigger('change');*/
							$('.otherDetailsDiv').empty();
							$('#loading').show();
							$cropDeatilDiv = $('.cropDeatilDiv');
							$('#tblCropDeatilTbody').empty();
							$cropDeatilDiv.addClass('d-none');
							$val = $('#ddlLandClass option:selected').text();
							console.log($val);
							//							alert($val(data[key].area));
							if ($val === 'Others') {
								$('.subClassLand').removeClass('d-none');
							} else {
								$('.subClassLand').addClass('d-none');
							}
							if(data[key].irrigation_status_id == 24){
								$('.ddlMicroIrrigationArea').addClass('d-none')
								$('.ddlMicroIrrigation').addClass('d-none');
							}else{
								$('.ddlMicroIrrigationArea').removeClass('d-none')
								$('.ddlMicroIrrigation').removeClass('d-none');
							}
							
							if ($val === 'Forest Land') {
								//	alert('HI');
								$('#draftSave').removeClass('d-none');
								$('.forestlandType').removeClass('d-none');
								$('.noofCrop').addClass('d-none');
								$ddlForestlandType = $('#ddlForestlandType');
								$.ajax({
									url: "getForestlandType",
									type: "post",
									data: {},
									error: function(xhr, status, er) {
										console.log(er);
										$('#loading').hide();
										$('.error').append(' There is some error please try again !');
									},
									success: function(data) {
										console.log(data);
										$('#loading').hide();
										$ddlForestlandType.empty();
										$ddlForestlandType.append("<option value=''>---Select---</option>");
										if (Object.keys(data).length > 0) {
											for (var key in data) {
												if (data.hasOwnProperty(key)) {
													$ddlForestlandType.append("<option value='" + key + "'>" + data[key] + "</option>");
												}
											}

											//	alert('test'+$forestLandTemp);
											$('#ddlForestlandType').val($forestLandTemp);
											//	alert($('#ddlForestlandType').val());
										}
									}
								});

							} else {
								//$('#draftSave').addClass('d-none');
								$('.forestlandType').addClass('d-none');
								$('.noofCrop').removeClass('d-none');
								$ddlNoofCrop = $('#ddlNoofCrop');
								$.ajax({
									url: "getNoofCrop",
									type: "post",
									data: {},
									error: function(xhr, status, er) {
										console.log(er);
										$('#loading').hide();
										$('.error').append(' There is some error please try again !');
									},
									success: function(data) {
										console.log(data);
										$('#loading').hide();
										$ddlNoofCrop.empty();
										var temp = $('#cropNo').val();
										$ddlNoofCrop.append("<option value=''>---Select---</option>");
										if (Object.keys(data).length > 0) {
											for (var key in data) {
												if (data.hasOwnProperty(key)) {
													$ddlNoofCrop.append("<option value='"+key+"' >" + data[key] + "</option>");
												}
											}
										}
										$('#ddlNoofCrop').val(temp);
										//$('.ddlNoofCrop').trigger('change');


										$seasonArray = new Array();
										$cropTypeValue = new Array();
										$season1 = new Array();
										$season2 = new Array();
										$season3 = new Array();
										$singleCropTypeVal = new Array();
										$totalCropArea=0;
										$season1Area= 0;
										$season2Area= 0;
										$season3Area= 0;

										$val = $('#ddlNoofCrop option:selected').text();
										$lable = '<h5 class="text-center labeltext font-weight-bold"></h5>';
										$ddlSeason = '<label for="startDate">Season:</label><select id="ddlSeason" name="ddlSeason" class="ddlSeason form-control col-md-6"><option value="">--Select--</option></select><br/>';
										$single_crop = '<table class="tblOtherDetails table" id="single_crop" name="single_crop"><thead><tr><th class="text-center">Crop Type</th><th class="text-center">Crop Area (in ha)<br/>(col-A)</th>' +
											'<th class="text-center">Crop production per Hectare (in Quintal)<br/>(col-B)' +
											'</th><th class="text-center">Avg. Income per Quintal (in Rs.)<br/>(col-C)</th><th class="text-center">Total Income (in Rs.)<br/>(col-A*col-B*col-C)</th><th>Action</th></tr></thead>' +
											'<tbody id="tblOtherDetailsTbody">' +
											'<tr><td><select id="ddlCropType" name="ddlCropType" class="ddlCropType form-control"><option value="">--Select--</option></select><span id="cropTypeError"></span></td>' +
											'<td><input type="text" id="cropArea" name="cropArea" class="cropArea form-control"  onblur="getTotalIncome()" onfocusin="decimalToFourPlace(event)"/><span id="cropAreaError"></span></td>' +
											'<td><input type="text" id="cropProduction" name="cropProduction" class="cropProduction form-control"  onblur="getTotalIncome()"/><span id="cropProductionError"></span></td>' +
											'<td><input type="text" id="avgIncome" name="avgIncome" class="avgIncome form-control" onblur="getTotalIncome()" /><span id="avgIncomeError"></span></td>' +
											'<td><input type="text" id="totalIncome" name="totalIncome" class="totalIncome form-control" readonly /></td>' +
											'<td><a href="#" id="add" name="add" class="add btn btn-info" >Add Crop Detail</a></td></tr></tbody></table>';
										$cropDeatilDiv = $('.cropDeatilDiv');
										$('#tblCropDeatilTbody').empty();
										$('.otherDetailsDiv').empty();
										$('.otherDetailsDiv').append($lable);
										console.log('a' + $val + 'b');
										$val = $.trim($val);
										$('#disSeason').removeClass('d-none');

										//alert('b'+$val);

										if ($val != 'No Crop') {
											$('#draftSave').addClass('d-none');
											$('.otherDetailsDiv').removeClass('d-none');
											if ($val === 'Single Crop') {
												$('#disSeason').addClass('d-none');
												$cropDeatilDiv.addClass("d-none");
												$('#single_crop').removeClass('d-none');
												$('.otherDetailsDiv').append($single_crop);
												getCropTypeUpdated();
											}
											if ($val === 'Double Crop') {
												$cropDeatilDiv.addClass("d-none");
												$('.otherDetailsDiv').append($ddlSeason);
												$('#single_crop').removeClass('d-none');
												$('.otherDetailsDiv').append($single_crop);
												getCropType();
												$ddlSeason = $('#ddlSeason');
												$.ajax({
													url: "getSeasonList",
													type: "post",
													data: {},
													error: function(xhr, status, er) {
														console.log(er);
														$('#loading').hide();
														$('.error').append(' There is some error please try again !');
													},
													success: function(data) {
														$('#loading').hide();
														$ddlSeason.empty();
														$ddlSeason.append("<option value=''>---Select---</option>");
														$count = 0;
														if (Object.keys(data).length > 0) {
															for (var key in data) {
																if (data.hasOwnProperty(key)) {
																	if ($count <= 1) {
																		$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
																		if ($.inArray(key, $seasonArray) === -1) {
																			$seasonArray.push(key);
																		} else {
																			//alert('already added activity');
																		}
																	}
																	$count++;
																}
															}
														}
													}
												});
											} if ($val === 'Multiple Crop') {
												//alert('hi');
												$cropDeatilDiv.addClass("d-none");
												$('.otherDetailsDiv').append($ddlSeason);
												$('#single_crop').removeClass('d-none');
												$('.otherDetailsDiv').append($single_crop);
												getCropType();
												$ddlSeason = $('#ddlSeason');
												$.ajax({
													url: "getSeasonList",
													type: "post",
													data: {},
													error: function(xhr, status, er) {
														console.log(er);
														$('#loading').hide();
														$('.error').append(' There is some error please try again !');
													},
													success: function(data) {
														console.log(data);
														$('#loading').hide();
														$ddlSeason.empty();
														$ddlSeason.append("<option value=''>---Select---</option>");
														if (Object.keys(data).length > 0) {
															for (var key in data) {
																if (data.hasOwnProperty(key)) {
																	$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
																	if ($.inArray(key, $seasonArray) === -1) {
																		$seasonArray.push(key);
																	} else {
																		//alert('already added activity');
																	}
																}
															}
														}
													}
												});
											}
											//alert("a"+$val);
											if ($val != '---Select---')
												$('.labeltext').html('For ' + $val);
										} else {
											$('.otherDetailsDiv').addClass('d-none');
											$('.labeltext').html('');
											$cropDeatilDiv.addClass("d-none");
											$('#draftSave').removeClass('d-none');
										}

									}
								});
							}
							////////////* End */

							$('#cropNo').val(data[key].no_of_crop_id);

							//alert('hi 2'+ $('#ddlNoofCrop option').length);
							$('#ddlNoofCrop').val(data[key].no_of_crop_id);
							$dbNoofCrop = data[key].no_of_crop_id;
							$('#ownername').val(data[key].owner_name);

							//$('#ddlOwnership').trigger('change');

							/*888888 Crop No Changed */
							//$('#ddlNoofCrop').trigger('change');
							$seasonArray = new Array();
							$lable = '<h5 class="text-center labeltext font-weight-bold"></h5>';
							$ddlSeason = '<label for="startDate">Season:</label><select id="ddlSeason" name="ddlSeason" class="ddlSeason form-control col-md-6"><option value="">--Select--</option></select><br/>';
							$val = $('#ddlNoofCrop option:selected').text();
							$single_crop = '<table class="tblOtherDetails table" id="single_crop" name="single_crop"><thead><tr><th class="text-center">Crop Type</th><th class="text-center">Crop Area (in ha)<br/>(col-A)</th>' +
								'<th class="text-center">Crop production per Hectare (in Quintal)<br/>(col-B)' +
								'</th><th class="text-center">Avg. Income per Quintal (in Rs.)<br/>(col-C)</th><th class="text-center">Total Income (in Rs.)<br/>(col-A*col-B*col-C)</th><th>Action</th></tr></thead>' +
								'<tbody id="tblOtherDetailsTbody">' +
								'<tr><td><select id="ddlCropType" name="ddlCropType" class="ddlCropType form-control"><option value="">--Select--</option></select><span id="cropTypeError"></span></td>' +
								'<td><input type="text" id="cropArea" name="cropArea" class="cropArea form-control"  onblur="getTotalIncome()" onfocusin="decimalToFourPlace(event)"/><span id="cropAreaError"></span></td>' +
								'<td><input type="text" id="cropProduction" name="cropProduction" class="cropProduction form-control"  onblur="getTotalIncome()"/><span id="cropProductionError"></span></td>' +
								'<td><input type="text" id="avgIncome" name="avgIncome" class="avgIncome form-control" onblur="getTotalIncome()" /><span id="avgIncomeError"></span></td>' +
								'<td><input type="text" id="totalIncome" name="totalIncome" class="totalIncome form-control" readonly /></td>' +
								'<td><a href="#" id="add" name="add" class="add btn btn-info" >Add Crop Detail</a></td></tr></tbody></table>';
							$cropDeatilDiv = $('.cropDeatilDiv');
							$('#tblCropDeatilTbody').empty();
							$('.otherDetailsDiv').empty();
							$('.otherDetailsDiv').append($lable);
							console.log('abcd' + $val + 'b');
							$val = $.trim($val);
							//alert('C'+$val);
							$('#disSeason').removeClass('d-none');
							if ($val != 'No Crop' && $val != '--Select--') {
								//alert('hh');
								$('#draftSave').addClass('d-none');
								$('.otherDetailsDiv').removeClass('d-none');
								if ($val === 'Single Crop') {
									$('#disSeason').addClass('d-none');
									$cropDeatilDiv.addClass("d-none");
									$('#single_crop').removeClass('d-none');
									$('.otherDetailsDiv').append($single_crop);
									getCropType();
								}
								if ($val === 'Double Crop') {
									$cropDeatilDiv.addClass("d-none");
									$('.otherDetailsDiv').append($ddlSeason);
									$('#single_crop').removeClass('d-none');
									$('.otherDetailsDiv').append($single_crop);
									getCropType();
									$ddlSeason = $('#ddlSeason');
									$.ajax({
										url: "getSeasonList",
										type: "post",
										data: {},
										error: function(xhr, status, er) {
											console.log(er);
											$('#loading').hide();
											$('.error').append(' There is some error please try again !');
										},
										success: function(data) {
											$('#loading').hide();
											$ddlSeason.empty();
											$ddlSeason.append("<option value=''>---Select---</option>");
											$count = 0;
											if (Object.keys(data).length > 0) {
												for (var key in data) {
													if (data.hasOwnProperty(key)) {
														if ($count <= 1) {
															$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
															if ($.inArray(key, $seasonArray) === -1) {
																$seasonArray.push(key);
															} else {
																//alert('already added activity');
															}
														}
														$count++;
													}
												}
											}
										}
									});
								} if ($val === 'Multiple Crop') {
									$cropDeatilDiv.addClass("d-none");
									$('.otherDetailsDiv').append($ddlSeason);
									$('#single_crop').removeClass('d-none');
									$('.otherDetailsDiv').append($single_crop);
									getCropType();
									$ddlSeason = $('#ddlSeason');
									$.ajax({
										url: "getSeasonList",
										type: "post",
										data: {},
										error: function(xhr, status, er) {
											console.log(er);
											$('#loading').hide();
											$('.error').append(' There is some error please try again !');
										},
										success: function(data) {
											console.log(data);
											$('#loading').hide();
											$ddlSeason.empty();
											$ddlSeason.append("<option value=''>---Select---</option>");
											if (Object.keys(data).length > 0) {
												for (var key in data) {
													if (data.hasOwnProperty(key)) {
														$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
														if ($.inArray(key, $seasonArray) === -1) {
															$seasonArray.push(key);
														} else {
															//alert('already added activity');
														}
													}
												}
											}
										}
									});
								}
								//alert("b"+$val);
								if ($val != '')
									$('.labeltext').html('For ' + $val);
							} else {
								$('.otherDetailsDiv').addClass('d-none');
								$('.labeltext').html('');
								$cropDeatilDiv.addClass("d-none");
								$('#draftSave').removeClass('d-none');
							}
							/*---------------- End */
							$val = $('#ddlOwnership option:selected').text();

							if ($val === 'Private') {
								$('.ownername').removeClass('d-none');
							} else {
								$('.ownername').addClass('d-none');
							}

						}
					}

					//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
					//$('#plotno').val('');
					//$('#plotno').focus();
				} else {
					$('#projectArea').val('');
					$('#ddlIrrigationStatus').val('');
				}

				//alert('hi 3 '+ $('#cropNo').val());

			}

		});

		$tblCropPlotDataBody = $('#tblCropPlotDataBody');
		$projId = $('#project').val();
		$tbody = '';
		$seasonExist1Area = 0;
		$seasonExist2Area = 0;
		$seasonExist3Area = 0;
		$.ajax({
			url: "getCropDataOfPlotProject",
			type: "post",
			data: { vcode: $vcode, plotno: $plotNo, projId: $projId },
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$index = 0;
				$tblCropPlotDataBody.empty();
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						$index += 1
						//alert(data[key].season);
						if (data[key].season == 'Rabi')
							$seasonExist1Area = parseFloat($seasonExist1Area) + parseFloat(data[key].crop_area);
						if (data[key].season == 'Kharif')
							$seasonExist2Area = parseFloat($seasonExist2Area) + parseFloat(data[key].crop_area);
						if (data[key].season == 'Summer')
							$seasonExist3Area = parseFloat($seasonExist3Area) + parseFloat(data[key].crop_area);

						if (typeof (data[key].season_id) != 'undefined') {
							$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td><td><input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
								'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
								'</td><td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
								'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
								'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value=""/>' +
								'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
								'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
								'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
								'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//								+'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
								;
							$('#existSeason').removeClass('d-none');
						}
						else {
							$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td>' +
								//									<input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
								//									'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
								'<td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
								'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
								'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value=""/>' +
								'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
								'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
								'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
								'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//								+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
								;
							$('#existSeason').addClass('d-none');
						}
					}

					//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
					//$('#plotno').val('');
					//$('#plotno').focus();
				} else {
					//$('#projectArea').val('');
					//$('#ddlIrrigationStatus').val('');
				}
				$tblCropPlotDataBody.append($tbody);

				//alert('hi 3 '+ $('#cropNo').val());

			}

		});


	});

	/***************************************************** End **************************************************** */

	/**************************************************** Classification of Land Change Script ******************************* */

	$(document).on('change', '#ddlLandClass', function(e) {
		/******Delete crop data when change */
		if (confirm('If you change the Classification of Land, all crop data will be modified. Are you sure you want to change Land Classification?')) {

			$('.otherDetailsDiv').empty();
			$('#loading').show();
			$cropDeatilDiv = $('.cropDeatilDiv');
			$('#tblCropDeatilTbody').empty();
			$cropDeatilDiv.addClass('d-none');
			$val = $('#ddlLandClass option:selected').text();
			console.log($val);
			if ($val === 'Others') {
				$('.subClassLand').removeClass('d-none');
			} else {
				$('.subClassLand').addClass('d-none');
			}
			//alert($val);
			if ($val === 'Forest Land') {
				$('#draftSave').removeClass('d-none');
				$('.forestlandType').removeClass('d-none');
				$('.noofCrop').addClass('d-none');
				$ddlForestlandType = $('#ddlForestlandType');
				$.ajax({
					url: "getForestlandType",
					type: "post",
					data: {},
					error: function(xhr, status, er) {
						console.log(er);
						$('#loading').hide();
						$('.error').append(' There is some error please try again !');
					},
					success: function(data) {
						console.log(data);
						$('#loading').hide();
						$ddlForestlandType.empty();
						$ddlForestlandType.append("<option value=''>---Select---</option>");
						if (Object.keys(data).length > 0) {
							for (var key in data) {
								if (data.hasOwnProperty(key)) {
									$ddlForestlandType.append("<option value='" + key + "'>" + data[key] + "</option>");
								}
							}
						}
					}
				});
			} else {
				//$('#draftSave').addClass('d-none');
				$('.forestlandType').addClass('d-none');
				$('.noofCrop').removeClass('d-none');
				$ddlNoofCrop = $('#ddlNoofCrop');
				$.ajax({
					url: "getNoofCrop",
					type: "post",
					data: {},
					error: function(xhr, status, er) {
						console.log(er);
						$('#loading').hide();
						$('.error').append(' There is some error please try again !');
					},
					success: function(data) {
						console.log(data);
						$('#loading').hide();
						$ddlNoofCrop.empty();
						var temp = $('#cropNo').val();
						$ddlNoofCrop.append("<option value=''>---Select---</option>");
						if (Object.keys(data).length > 0) {
							for (var key in data) {
								if (data.hasOwnProperty(key)) {
									$ddlNoofCrop.append("<option value='" + key + "' >" + data[key] + "</option>");
								}
							}
						}
						$('#ddlNoofCrop').val(temp);
						//$('.ddlNoofCrop').trigger('change');


						$seasonArray = new Array();
						$val = $('#ddlNoofCrop option:selected').text();
						$lable = '<h5 class="text-center labeltext font-weight-bold"></h5>';
						$ddlSeason = '<label for="startDate">Season:</label><select id="ddlSeason" name="ddlSeason" class="ddlSeason form-control col-md-6"><option value="">--Select--</option></select><br/>';
						$single_crop = '<table class="tblOtherDetails table" id="single_crop" name="single_crop"><thead><tr><th class="text-center">Crop Type</th><th class="text-center">Crop Area (in ha)<br/>(col-A)</th>' +
							'<th class="text-center">Crop production per Hectare (in Quintal)<br/>(col-B)' +
							'</th><th class="text-center">Avg. Income per Quintal (in Rs.)<br/>(col-C)</th><th class="text-center">Total Income (in Rs.)<br/>(col-A*col-B*col-C)</th><th>Action</th></tr></thead>' +
							'<tbody id="tblOtherDetailsTbody">' +
							'<tr><td><select id="ddlCropType" name="ddlCropType" class="ddlCropType form-control"><option value="">--Select--</option></select><span id="cropTypeError"></span></td>' +
							'<td><input type="text" id="cropArea" name="cropArea" class="cropArea form-control"  onblur="getTotalIncome()" onfocusin="decimalToFourPlace(event)"/><span id="cropAreaError"></span></td>' +
							'<td><input type="text" id="cropProduction" name="cropProduction" class="cropProduction form-control"  onblur="getTotalIncome()"/><span id="cropProductionError"></span></td>' +
							'<td><input type="text" id="avgIncome" name="avgIncome" class="avgIncome form-control" onblur="getTotalIncome()" /><span id="avgIncomeError"></span></td>' +
							'<td><input type="text" id="totalIncome" name="totalIncome" class="totalIncome form-control" readonly /></td>' +
							'<td><a href="#" id="add" name="add" class="add btn btn-info" >Add Crop Detail</a></td></tr></tbody></table>';
						$cropDeatilDiv = $('.cropDeatilDiv');
						$('#tblCropDeatilTbody').empty();
						$('.otherDetailsDiv').empty();
						$('.otherDetailsDiv').append($lable);
						console.log('a' + $val + 'b');
						$val = $.trim($val);
						$('#disSeason').removeClass('d-none');

						//alert($val);

						if ($val != 'No Crop') {
							$('#draftSave').addClass('d-none');
							$('.otherDetailsDiv').removeClass('d-none');
							if ($val === 'Single Crop') {
								$('#disSeason').addClass('d-none');
								$cropDeatilDiv.addClass("d-none");
								$('#single_crop').removeClass('d-none');
								$('.otherDetailsDiv').append($single_crop);
								getCropType();
							}
							if ($val === 'Double Crop') {
								$cropDeatilDiv.addClass("d-none");
								$('.otherDetailsDiv').append($ddlSeason);
								$('#single_crop').removeClass('d-none');
								$('.otherDetailsDiv').append($single_crop);
								getCropType();
								$ddlSeason = $('#ddlSeason');
								$.ajax({
									url: "getSeasonList",
									type: "post",
									data: {},
									error: function(xhr, status, er) {
										console.log(er);
										$('#loading').hide();
										$('.error').append(' There is some error please try again !');
									},
									success: function(data) {
										$('#loading').hide();
										$ddlSeason.empty();
										$ddlSeason.append("<option value=''>---Select---</option>");
										$count = 0;
										if (Object.keys(data).length > 0) {
											for (var key in data) {
												if (data.hasOwnProperty(key)) {
													if ($count <= 1) {
														$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
														if ($.inArray(key, $seasonArray) === -1) {
															$seasonArray.push(key);
														} else {
															//alert('already added activity');
														}
													}
													$count++;
												}
											}
										}
									}
								});
							} if ($val === 'Multiple Crop') {
								//alert('hi');
								$cropDeatilDiv.addClass("d-none");
								$('.otherDetailsDiv').append($ddlSeason);
								$('#single_crop').removeClass('d-none');
								$('.otherDetailsDiv').append($single_crop);
								getCropType();
								$ddlSeason = $('#ddlSeason');
								$.ajax({
									url: "getSeasonList",
									type: "post",
									data: {},
									error: function(xhr, status, er) {
										console.log(er);
										$('#loading').hide();
										$('.error').append(' There is some error please try again !');
									},
									success: function(data) {
										console.log(data);
										$('#loading').hide();
										$ddlSeason.empty();
										$ddlSeason.append("<option value=''>---Select---</option>");
										if (Object.keys(data).length > 0) {
											for (var key in data) {
												if (data.hasOwnProperty(key)) {
													$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
													if ($.inArray(key, $seasonArray) === -1) {
														$seasonArray.push(key);
													} else {
														//alert('already added activity');
													}
												}
											}
										}
									}
								});
							}
							//alert("a"+$val);
							if ($val != '---Select---')
								$('.labeltext').html('For ' + $val);
						} else {
							$('.otherDetailsDiv').addClass('d-none');
							$('.labeltext').html('');
							$cropDeatilDiv.addClass("d-none");
							$('#draftSave').removeClass('d-none');
						}

					}
				});
			}

			$ownership = '';
			$landClassification = '';
			$noOfCrop = '';
			//$season = $('#ddlSeason').val();
			$ownerName = '';
			$forestLandType = '';
			//$('.ddlOwnership').val('');
			//$('.ddlLandClass').val('');
			$('.noofCrop').val('');
			$('.ddlForestlandType').val('');
			//$('.ownername').val('');
		}
		else {
			$('#ddlLandClass').val($dbLandclass);
			$('#ddlLandClass').focus();
			$('#loading').hide();
			return false;
		}

		$('#loading').hide();

	});

	/*************************************************************** End ***************************************************** */
	/**************************************************** No of Crop Change Script ******************************* */

	$(document).on('change', '#ddlNoofCrop', function(e) {
		//	alert('hi');
		$('#loading').show();
		if (confirm('If you change the No of Crop, all crop data will be modified. Are you sure you want to change the No of Crop?')) {

			$blsoutDetailId = $('#blsOutDetailId').val();
			
			
			$.ajax({
				url: "changeCropDeleteStatus",
				type: "post",
				data: { blsOutDetailId: $blsoutDetailId },
				error: function(xhr, status, er) {
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						$tblCropPlotDataBody = $('#tblCropPlotDataBody');
						$projId = $('#project').val();
						$tbody = '';
						$tblCropPlotDataBody.append($tbody);
						$tblCropPlotDataBody = $('#tblCropPlotDataBody');
						$projId = $('#project').val();
						$tbody = '';
						$.ajax({
							url: "getCropDataOfPlotProject",
							type: "post",
							data: { vcode: $vcode, plotno: $plotNo, projId: $projId },
							error: function(xhr, status, er) {
								console.log(er);
								$('#loading').hide();
								$('.error').append(' There is some error please try again !');
							},
							success: function(data) {
								console.log(data);
								$index = 0;
								$tblCropPlotDataBody.empty();
								if (Object.keys(data).length > 0) {
									for (var key in data) {
										$index += 1
										if (typeof (data[key].season_id) != 'undefined') {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td><td><input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'</td><td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+ '<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').removeClass('d-none');
										}
										else {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td>' +
												//									<input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												//									'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'<td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').addClass('d-none');
										}
									}

									//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
									//$('#plotno').val('');
									//$('#plotno').focus();
								} else {
									//$('#projectArea').val('');
									//$('#ddlIrrigationStatus').val('');
								}
								$tblCropPlotDataBody.append($tbody);
								$tblCropPlotDataBody.hide();

							}

						});
					}
					else
						alert('Data Can Not Deleted !');
					
				}
				
				});
			
			

			$seasonArray = new Array();
			$lable = '<h5 class="text-center labeltext font-weight-bold"></h5>';
			$ddlSeason = '<label for="startDate">Season:</label><select id="ddlSeason" name="ddlSeason" class="ddlSeason form-control col-md-6"><option value="">--Select--</option></select><br/>';
			$val = $('#ddlNoofCrop option:selected').text();
			$single_crop = '<table class="tblOtherDetails table" id="single_crop" name="single_crop"><thead><tr><th class="text-center">Crop Type</th><th class="text-center">Crop Area (in ha)<br/>(col-A)</th>' +
				'<th class="text-center">Crop production per Hectare (in Quintal)<br/>(col-B)' +
				'</th><th class="text-center">Avg. Income per Quintal (in Rs.)<br/>(col-C)</th><th class="text-center">Total Income (in Rs.)<br/>(col-A*col-B*col-C)</th><th>Action</th></tr></thead>' +
				'<tbody id="tblOtherDetailsTbody">' +
				'<tr><td><select id="ddlCropType" name="ddlCropType" class="ddlCropType form-control"><option value="">--Select--</option></select><span id="cropTypeError"></span></td>' +
				'<td><input type="text" id="cropArea" name="cropArea" class="cropArea form-control"  onblur="getTotalIncome()" onfocusin="decimalToFourPlace(event)"/><span id="cropAreaError"></span></td>' +
				'<td><input type="text" id="cropProduction" name="cropProduction" class="cropProduction form-control"  onblur="getTotalIncome()"/><span id="cropProductionError"></span></td>' +
				'<td><input type="text" id="avgIncome" name="avgIncome" class="avgIncome form-control" onblur="getTotalIncome()" /><span id="avgIncomeError"></span></td>' +
				'<td><input type="text" id="totalIncome" name="totalIncome" class="totalIncome form-control" readonly /></td>' +
				'<td><a href="#" id="add" name="add" class="add btn btn-info" >Add Crop Detail</a></td></tr></tbody></table>';
			$cropDeatilDiv = $('.cropDeatilDiv');
			$('#tblCropDeatilTbody').empty();
			$('.otherDetailsDiv').empty();
			$('.otherDetailsDiv').append($lable);
			console.log('a' + $val + 'b');
			$val = $.trim($val);
			//alert('C'+$val);
			$('#disSeason').removeClass('d-none');
			if ($val != 'No Crop' && $val != '--Select--') {
				//alert('hh');
				$('#draftSave').addClass('d-none');
				$('.otherDetailsDiv').removeClass('d-none');
				if ($val === 'Single Crop') {
					$('#disSeason').addClass('d-none');
					$cropDeatilDiv.addClass("d-none");
					$('#single_crop').removeClass('d-none');
					$('.otherDetailsDiv').append($single_crop);
					getCropType();
				}
				if ($val === 'Double Crop') {
					$cropDeatilDiv.addClass("d-none");
					$('.otherDetailsDiv').append($ddlSeason);
					$('#single_crop').removeClass('d-none');
					$('.otherDetailsDiv').append($single_crop);
					getCropType();
					$ddlSeason = $('#ddlSeason');
					$.ajax({
						url: "getSeasonList",
						type: "post",
						data: {},
						error: function(xhr, status, er) {
							console.log(er);
							$('#loading').hide();
							$('.error').append(' There is some error please try again !');
						},
						success: function(data) {
							$('#loading').hide();
							$ddlSeason.empty();
							$ddlSeason.append("<option value=''>---Select---</option>");
							$count = 0;
							if (Object.keys(data).length > 0) {
								for (var key in data) {
									if (data.hasOwnProperty(key)) {
										if ($count <= 1) {
											$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
											if ($.inArray(key, $seasonArray) === -1) {
												$seasonArray.push(key);
											} else {
												//alert('already added activity');
											}
										}
										$count++;
									}
								}
							}
						}
					});
				} if ($val === 'Multiple Crop') {
					$cropDeatilDiv.addClass("d-none");
					$('.otherDetailsDiv').append($ddlSeason);
					$('#single_crop').removeClass('d-none');
					$('.otherDetailsDiv').append($single_crop);
					getCropType();
					$ddlSeason = $('#ddlSeason');
					$.ajax({
						url: "getSeasonList",
						type: "post",
						data: {},
						error: function(xhr, status, er) {
							console.log(er);
							$('#loading').hide();
							$('.error').append(' There is some error please try again !');
						},
						success: function(data) {
							console.log(data);
							$('#loading').hide();
							$ddlSeason.empty();
							$ddlSeason.append("<option value=''>---Select---</option>");
							if (Object.keys(data).length > 0) {
								for (var key in data) {
									if (data.hasOwnProperty(key)) {
										$ddlSeason.append("<option value='" + key + "'>" + data[key] + "</option>");
										if ($.inArray(key, $seasonArray) === -1) {
											$seasonArray.push(key);
										} else {
											//alert('already added activity');
										}
									}
								}
							}
						}
					});
				}
				//alert("b"+$val);
				if ($val != '---Select---')
					$('.labeltext').html('For ' + $val);
			} else {
				$('.otherDetailsDiv').addClass('d-none');
				$('.labeltext').html('');
				$cropDeatilDiv.addClass("d-none");
				$('#draftSave').removeClass('d-none');
			}
			$ownership = '';
			$landClassification = '';
			$noOfCrop = '';
			//$season = $('#ddlSeason').val();
			$ownerName = '';
			$forestLandType = '';
			//$('.ddlOwnership').val('');
			//$('.ddlLandClass').val('');
			//$('.noofCrop').val('');
			$('.ddlForestlandType').val('');
			//$('.ownername').val('');
		}
		else {
			$('#ddlNoofCrop').val($dbNoofCrop);
			$('#ddlNoofCrop').focus();
			$('#loading').hide();
			return false;
		}

		$('#loading').hide();

	});

	/*************************************************************** End ***************************************************** */
	/************************************************************** On add row link click Script ************************************** */
	$index = 0;
	$seasonArray = new Array();
	$cropTypeValue = new Array();
	$seasonArray = new Array();
	$season1 = new Array();
	$season2 = new Array();
	$season3 = new Array();
	$singleCropTypeVal = new Array();
	$(document).on('click', 'a.add', function(e) {
		$cropArea = $('.cropArea').val();
		$cropProduction = $('.cropProduction').val();
		$avgIncome = $('.avgIncome').val();
		$totalIncome = $('.totalIncome').val();
		$cropType = $('.ddlCropType option:selected').text();
		$cropTypeVal = $('.ddlCropType').val();

		if ($cropTypeVal === '') {
			$('#cropTypeError').html('Please select Crop Type');
			$('#cropTypeError').css('color', 'red');
			$('.ddlCropType').focus();
			return false;
		} else {
			$('#cropTypeError').html('');
			if ($.inArray($cropTypeVal, $cropTypeValue) === -1) {
				$cropTypeValue.push($cropTypeVal);
			} else {
				//alert('already added activity');
			}
		}
		if ($cropArea === '') {
			$('#cropAreaError').html('Crop Area can not be blank');
			$('#cropAreaError').css('color', 'red');
			$('.cropArea').focus();
			return false;
		} else {
			$('#cropAreaError').html('');
		}
		if ($cropProduction === '') {
			$('#cropProductionError').html('Crop Production can not be blank');
			$('#cropProductionError').css('color', 'red');
			$('.cropProduction').focus();
			return false;
		} else {
			$('#cropProductionError').html('');
		}
		if ($avgIncome === '') {
			$('#avgIncomeError').html('Avg. Income can not be blank');
			$('#avgIncomeError').css('color', 'red');
			$('.avgIncome').focus();
			return false;
		} else {
			$('#avgIncomeError').html('');
		}
		if ($totalIncome === '') {
			$('.totalIncome').focus();
			return false;
		}


		$ddlSeasonVal = $('#ddlSeason').val();
		if ($ddlSeasonVal === '') {
			alert('Please select Season !');
			$('#ddlSeason').focus();
			return false;
		}

		$cropArea = $('.cropArea').val();
		$('#cropAreaError').html('');
		$projectArea = $('#projectArea').val();


		$season = $('#ddlSeason option:selected').text();
		$seasonVal = $('#ddlSeason').val();
		$cropDeatilDiv = $('.cropDeatilDiv');
		$cropDeatilDiv.removeClass("d-none");
		$tblCropDeatilTbody = $('#tblCropDeatilTbody');
		$index++;
		$val = $('#ddlNoofCrop option:selected').text();
		$val = $.trim($val);
		$crpStatus = '';
		$('#tblCropPlotDataBody').find('tr').each(function() {
			$rowId = $(this).closest('tr').attr('id');
			$rowId = $rowId.match(/[\d\.]+/g);
			$crpStatus = $('#cropStatus' + $rowId).val();
		});
		
		//////////////////------------area check-----------------
		if ($val != 'No Crop') {
			if ($val === 'Single Crop') {

			} else {
				//alert('s1--' + $('#ddlSeason option:selected').text() );
				if ($('#ddlSeason option:selected').text() == 'Rabi') {
					if($crpStatus!='D'){
						$totalCropArea = parseFloat($season1Area) + parseFloat($seasonExist1Area);
					}
					//alert('s1'+parseFloat($season1Area) + parseFloat($seasonExist1Area));
					//$projectArea = $season1ProjectArea;
				} else if ($('#ddlSeason option:selected').text() == 'Kharif') {
					if($crpStatus!='D'){
						$totalCropArea = parseFloat($season2Area) + parseFloat($seasonExist2Area);
					}
					//$projectArea = $season2ProjectArea;
				} else if ($('#ddlSeason option:selected').text() == 'Summer') {
					if($crpStatus!='D'){
						$totalCropArea = parseFloat($season3Area) + parseFloat($seasonExist3Area);
					}
					//$projectArea = $season3ProjectArea;
				} else {
					//$cropTypeValue = new Array();
				}
			}
			//alert('t'+$totalCropArea);
		}
		console.log('total crop area--b-' + $totalCropArea + ' crop area b' + $cropArea + 'project areab ---' + $projectArea);
		if (parseFloat($cropArea) > parseFloat($projectArea)) {
			$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
			$('#cropAreaError').css('color', 'red');
			$('.cropArea').focus();
			return false;
		} else {
			if ((parseFloat($totalCropArea) + parseFloat($cropArea)) > parseFloat($projectArea)) {
				$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
				$('#cropAreaError').css('color', 'red');
				$('.cropArea').focus();
				return false;
			} else {
				//$totalCropArea=parseFloat($oldcropArea)+parseFloat($cropArea);
			}

			//$('#cropAreaError').html('else croparea '+$cropArea+' oldcroparea '+$oldcropArea+' totalCropArea '+$totalCropArea+' projectArea '+$projectArea );
		}
		//////////////////------------area check-----------------
		

		if ($val != 'No Crop') {
			if ($val === 'Single Crop') {
				$tbody = '<tr id="tr' + $index + '"><td><input type="hidden" id="cropTypeVal' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + $cropTypeVal + '" />' +
					'<input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + $cropType + '" readonly /></td>' +
					'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + $cropArea + '" readonly/></td>' +
					'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value=""/>' +
					'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + $cropProduction + '" readonly/></td>' +
					'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + $avgIncome + '" readonly/></td>' +
					'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + $totalIncome + '"  readonly /></td>' +
					'<td><a href="#" id="delete" name="delete" class="delete" >delete</a></td></tr>';
			}
			else {
				$tbody = '<tr id="tr' + $index + '"><td><input type="hidden" id="seasonVal' + $index + '" name="seasonVal' + $index + '" class="seasonVal' + $index + ' form-control" value="' + $seasonVal + '" />' +
					'<input type="text" id="season' + $index + '" name="season' + $index + '" class="season' + $index + ' form-control" value="' + $season + '" readonly /></td>' +
					'<td><input type="hidden" id="cropTypeVal' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + $cropTypeVal + '" />' +
					'<input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + $cropType + '" readonly /></td>' +
					'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + $cropArea + '" readonly/></td>' +
					'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value=""/>' +
					'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + $cropProduction + '" readonly/></td>' +
					'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + $avgIncome + '" readonly/></td>' +
					'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + $totalIncome + '"  readonly /></td>' +
					'<td><a href="#" id="delete" name="delete" class="delete" >delete</a></td></tr>';
			}

		}


		$tblCropDeatilTbody.append($tbody);

		$('.cropArea').val('');
		$('.cropProduction').val('');
		$('.avgIncome').val('');
		$('.totalIncome').val('');
		$('.ddlCropType').val('');
		$('#draftSave').removeClass('d-none');
		$oldcropArea = 0;
		$val = $('#ddlNoofCrop option:selected').text();
		$val = $.trim($val);
		if ($val != 'No Crop') {

			if ($val === 'Single Crop') {
				if ($.inArray($cropTypeVal, $singleCropTypeVal) === -1) {
					$singleCropTypeVal.push($cropTypeVal);
				} else {
					//alert('already added activity');
				}
			} else {
				$seasonValue = $.trim($('#ddlSeason option:selected').text());
				if ($seasonValue === 'Rabi') {
					if ($.inArray($cropTypeVal, $season1) === -1) {
						$season1.push($cropTypeVal);
					} else {
						//alert('already added activity');
					}

				}
				if ($seasonValue === 'Kharif') {
					if ($.inArray($cropTypeVal, $season2) === -1) {
						$season2.push($cropTypeVal);
					} else {
						//alert('already added activity');
					}

				}
				if ($seasonValue === 'Summer') {
					if ($.inArray($cropTypeVal, $season3) === -1) {
						$season3.push($cropTypeVal);
					} else {
						//alert('already added activity');
					}

				}
			}
		}
		getCropTypeUpdated();
	});

	/******************************************************************** End ******************************************************** */
	/************************************************************** On Delete row link click Script ************************************** */

	$(document).on( 'click', 'a.delete', function (e) {
	
	
	$index--;
	$rowId = $(this).closest('tr').attr('id') ;
	$rowId = $rowId.match(/[\d\.]+/g);
	$val = $('#ddlNoofCrop option:selected').text();
$val = $.trim($val);
if($val!='No Crop'){
	
	if($val==='Single Crop'){
		$singleCropTypeVal=jQuery.grep($singleCropTypeVal, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});
	$cropTypeValue =	$singleCropTypeVal;
	//$projectArea=$projectArea+parseFloat($('#cropArea'+$rowId).val());
	$totalCropArea=$totalCropArea-parseFloat($('#cropArea'+$rowId).val());
		}else{
if($('#season'+$rowId).val()==='Rabi'){
	$season1=jQuery.grep($season1, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});
$cropTypeValue =$season1;	
	//$season1ProjectArea=$season1ProjectArea+parseFloat($('#cropArea'+$rowId).val());
	$season1Area=$season1Area-parseFloat($('#cropArea'+$rowId).val());
	//$totalCropArea=$season1Area;
	//$projectArea = $season1ProjectArea;
	}else if($('#season'+$rowId).val()==='Kharif'){
		$season2=jQuery.grep($season2, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});	
$cropTypeValue =$season2;
	//$season2ProjectArea=$season2ProjectArea+parseFloat($('#cropArea'+$rowId).val());
	$season2Area=$season2Area-parseFloat($('#cropArea'+$rowId).val());
	//$totalCropArea=$season2Area;
	//$projectArea = $season2ProjectArea;
	}else if($('#season'+$rowId).val()==='Summer'){
		$season3=jQuery.grep($season3, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});	
$cropTypeValue =$season3;
	//$season3ProjectArea=$season3ProjectArea+parseFloat($('#cropArea'+$rowId).val());
	$season3Area=$season3Area-parseFloat($('#cropArea'+$rowId).val());
	
	//$projectArea = $season3ProjectArea;
	}else{
		$cropTypeValue = new Array();
	}
	}
	}
	//alert($cropTypeValue+"----"+$('#season'+$rowId).val()+"===="+$('#cropTypeVal'+$rowId).val()+"====="+$rowId);
	getCropTypeUpdated();
	//$totalCropArea=$totalCropArea-$('#cropArea'+$rowId).val();
	console.log('croptypeval'+$('#cropTypeVal'+$rowId).val());
	$(this).closest('tr').remove();
for(var x=parseInt($rowId);x<=parseInt($index);x++){
	$('#tr'+(parseInt(x)+1)).attr("id","tr"+x);
	$('#seasonVal'+(parseInt(x)+1)).attr("id","seasonVal"+x);
	$('#season'+(parseInt(x)+1)).attr("id","season"+x);
	$('#cropTypeVal'+(parseInt(x)+1)).attr("id","cropTypeVal"+x);
	$('#cropType'+(parseInt(x)+1)).attr("id","cropType"+x);
	$('#cropArea'+(parseInt(x)+1)).attr("id","cropArea"+x);
	$('#cropProduction'+(parseInt(x)+1)).attr("id","cropProduction"+x);
	$('#avgIncome'+(parseInt(x)+1)).attr("id","avgIncome"+x);
	$('#totalIncome'+(parseInt(x)+1)).attr("id","totalIncome"+x);
	$("#tr"+(parseInt(x)+1)).attr("id","tr"+x);
}
	if($index==0)
	$('#draftSave').addClass('d-none');
});

	/******************************************************************** End ******************************************************** */

	/*************************************************** Function to get Crop Type ****************************************** */

function getCropTypeUpdated() {
		$vCode =  $('#village').val();
		$plotNo = $('#plotno').val();
		$season = $('#ddlSeason').val();
		if($season== 'undefined' || $season == null)
		{
			$season = '0';
		}
		$ddlCropType = $('.ddlCropType');
		//alert($cropTypeValue);
		$seasonValue = $.trim($('#ddlSeason option:selected').text());
		$val = $('#ddlNoofCrop option:selected').text();
		//alert($val);
		$val = $.trim($val);
		//alert($val);
		if ($val!= 'No Crop') {

			if ($val==='Single Crop') {
				$cropTypeValue = $singleCropTypeVal;
			} else {
				if ($seasonValue=== 'Rabi') {
					$cropTypeValue = $season1;
				} else if ($seasonValue=== 'Kharif') {
					$cropTypeValue = $season2;
				} else if ($seasonValue=== 'Summer') {
					$cropTypeValue = $season3;
				} else {
					$cropTypeValue = new Array();
				}
			}
		}
		$.ajax({
			//omlata
			url: "getcroptypeupdate",
			type: "post",
			data: { projId: $projId, vCode: $vCode ,plotNo: $plotNo, season: $season},
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlCropType.empty();
				$ddlCropType.append("<option value=''>---Select---</option>");
				if (Object.keys(data).length > 0) {
				
					for (var key in data) {
							//alert(key);
						if (data.hasOwnProperty(key)) {
							if ($.inArray(key, $cropTypeValue) === -1) {
								//$cropTypeValue.push(key);
								$ddlCropType.append("<option value='" + key + "'>" + data[key] + "</option>");
							} else {
								//alert('already added activity');
							}

						}
					}
				}
			}
		});
	}

	function getCropType() {
		$ddlCropType = $('.ddlCropType');
		//alert($singleCropTypeVal);
		$seasonValue = $.trim($('#ddlSeason option:selected').text());
		$val = $('#ddlNoofCrop option:selected').text();
		//alert($val);
		$val = $.trim($val);
		//alert($val);
		if ($val!= 'No Crop') {

			if ($val==='Single Crop') {
				$cropTypeValue = $singleCropTypeVal;
			} else {
				if ($seasonValue=== 'Rabi') {
					$cropTypeValue = $season1;
				} else if ($seasonValue=== 'Kharif') {
					$cropTypeValue = $season2;
				} else if ($seasonValue=== 'Summer') {
					$cropTypeValue = $season3;
				} else {
					$cropTypeValue = new Array();
				}
			}
		}
		$.ajax({
			url: "getCropType",
			type: "post",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlCropType.empty();
				$ddlCropType.append("<option value=''>---Select---</option>");
				if (Object.keys(data).length > 0) {
				
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							if ($.inArray(key, $cropTypeValue) === -1) {
								//$cropTypeValue.push(key);
								$ddlCropType.append("<option value='" + key + "'>" + data[key] + "</option>");
							} else {
								//alert('already added activity');
							}

						}
					}
				}
			}
		});
	}

	/******************************************************************* End ******************************************************* */

	/****************************************************** Area check script ********************************************* */
	$totalCropArea = 0;
	$oldcropArea = 0;
	$(document).on('blur', '.cropArea', function(e) {
		$totalCropArea = 0;
		$cropArea = $('.cropArea').val();
		$oldcropArea = $totalCropArea;
		$('#cropAreaError').html('');
		$projectArea = $('#projectArea').val();

		if ($projectArea === '' || typeof $projectArea === 'undefined' || parseFloat($projectArea) === 'NaN') {
			$('.projectAreaError').html('Please provide project area first');
			$('.projectAreaError').css('color', 'red');
			$('#projectArea').focus();
			return false;
		}
		if (parseFloat($cropArea) === 0 || parseFloat($cropArea) === .0 || parseFloat($cropArea) === 0.0) {
			$('#cropAreaError').html('Crop Area can not be zero');
			$('#cropAreaError').css('color', 'red');
			$('.cropArea').focus();
			return false;
		}
		$val = $('#ddlNoofCrop option:selected').text();
		$val = $.trim($val);
		
		if ($val != 'No Crop') {
			if ($val === 'Single Crop') {
				$('#tblCropPlotDataBody').find('tr').each(function() {

					$rowId = $(this).closest('tr').attr('id');
					$rowId = $rowId.match(/[\d\.]+/g);
					$crpArea1 = $('#cropArea' + $rowId).val();
					$crpStatus = $('#cropStatus' + $rowId).val();
					if($crpStatus !='D'){
						$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
					}
				});
				$('#tblCropDeatilTbody').find('tr').each(function() {

					$rowId = $(this).closest('tr').attr('id');
					$rowId = $rowId.match(/[\d\.]+/g);
					$crpArea1 = $('#cropArea' + $rowId).val();
					$crpStatus = $('#cropStatus' + $rowId).val();
					if($crpStatus !='D'){
						$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
					}
				});
			} else {
				//alert('s1--' + $('#ddlSeason option:selected').text() );
				if ($('#ddlSeason option:selected').text() == 'Rabi') {
					$('#tblCropPlotDataBody').find('tr').each(function() {

						$rowId = $(this).closest('tr').attr('id');
						$rowId = $rowId.match(/[\d\.]+/g);
						$seasonId1 = $('#season' + $rowId).val();
						if ($seasonId1 == 'Rabi') {
							$crpArea1 = $('#cropArea' + $rowId).val();
							$crpStatus = $('#cropStatus' + $rowId).val();
							if($crpStatus !='D'){
								$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
							}
						}
					});
					$('#tblCropDeatilTbody').find('tr').each(function() {

						$rowId = $(this).closest('tr').attr('id');
						$rowId = $rowId.match(/[\d\.]+/g);
						$seasonId1 = $('#season' + $rowId).val();
						if ($seasonId1 == 'Rabi') {
							$crpArea1 = $('#cropArea' + $rowId).val();
							$crpStatus = $('#cropStatus' + $rowId).val();
							if($crpStatus !='D'){
								$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
							}
						}
					});
//					$totalCropArea = parseFloat($season1Area) + parseFloat($seasonExist1Area);
					//alert('s1'+parseFloat($season1Area) + parseFloat($seasonExist1Area));
					//$projectArea = $season1ProjectArea;
				} else if ($('#ddlSeason option:selected').text() == 'Kharif') {
					$('#tblCropPlotDataBody').find('tr').each(function() {

						$rowId = $(this).closest('tr').attr('id');
						$rowId = $rowId.match(/[\d\.]+/g);
						$seasonId1 = $('#season' + $rowId).val();
						if ($seasonId1 == 'Kharif') {
							$crpArea1 = $('#cropArea' + $rowId).val();
							$crpStatus = $('#cropStatus' + $rowId).val();
							if($crpStatus !='D'){
								$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
							}
						}
					});
					$('#tblCropDeatilTbody').find('tr').each(function() {

						$rowId = $(this).closest('tr').attr('id');
						$rowId = $rowId.match(/[\d\.]+/g);
						$seasonId1 = $('#season' + $rowId).val();
						if ($seasonId1 == 'Kharif') {
							$crpArea1 = $('#cropArea' + $rowId).val();
							$crpStatus = $('#cropStatus' + $rowId).val();
							if($crpStatus !='D'){
								$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
							}
						}
					});
//					$totalCropArea = parseFloat($season2Area) + parseFloat($seasonExist2Area);
					//$projectArea = $season2ProjectArea;
				} else if ($('#ddlSeason option:selected').text() == 'Summer') {
					$('#tblCropPlotDataBody').find('tr').each(function() {

						$rowId = $(this).closest('tr').attr('id');
						$rowId = $rowId.match(/[\d\.]+/g);
						$seasonId1 = $('#season' + $rowId).val();
						if ($seasonId1 == 'Summer') {
							$crpArea1 = $('#cropArea' + $rowId).val();
							$crpStatus = $('#cropStatus' + $rowId).val();
							if($crpStatus !='D'){
								$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
							}
						}
					});
					$('#tblCropDeatilTbody').find('tr').each(function() {

						$rowId = $(this).closest('tr').attr('id');
						$rowId = $rowId.match(/[\d\.]+/g);
						$seasonId1 = $('#season' + $rowId).val();
						if ($seasonId1 == 'Summer') {
							$crpArea1 = $('#cropArea' + $rowId).val();
							$crpStatus = $('#cropStatus' + $rowId).val();
							if($crpStatus !='D'){
								$totalCropArea = parseFloat($totalCropArea) + parseFloat($crpArea1);
							}
						}
					});
//					$totalCropArea = parseFloat($season3Area) + parseFloat($seasonExist3Area);
					//$projectArea = $season3ProjectArea;
				} else {
					//$cropTypeValue = new Array();
				}
			}
			//alert('t'+$totalCropArea);
		}
		console.log('total crop area--b-' + $totalCropArea + ' crop area b' + $cropArea + 'project areab ---' + $projectArea);
		if (parseFloat($cropArea) > parseFloat($projectArea)) {
			$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
			$('#cropAreaError').css('color', 'red');
			$('.cropArea').focus();
			return false;
		} else {
			if ((parseFloat($totalCropArea) + parseFloat($cropArea)) > parseFloat($projectArea)) {
				$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
				$('#cropAreaError').css('color', 'red');
				$('.cropArea').focus();
				return false;
			} else {
				//$totalCropArea=parseFloat($oldcropArea)+parseFloat($cropArea);
			}

			//$('#cropAreaError').html('else croparea '+$cropArea+' oldcroparea '+$oldcropArea+' totalCropArea '+$totalCropArea+' projectArea '+$projectArea );
		}

	});

	/********************************************************* End ******************************************************** */


	/******************************************************* Treatable Area Check Script ********************************** */

	$(document).on('blur', '#projectArea', function(e) {
		e.preventDefault();
		$projId = $('#project').val();
		$projectArea = $(this).val();
		$.ajax({
			url: "getProjectSanctionedArea",
			type: "post",
			data: { projId: $projId },
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data + '---' + $projectArea);

				if (parseFloat($projectArea) > parseFloat(data)) {
					$('.projectAreaError').html('Plot area can not be greater than sanctioned project area.');
					$('.projectAreaError').css('color', 'red');
					$('#projectArea').focus();
					return false;

				} else {
					$('.projectAreaError').html('');
				}
			}

		});



	});

	/*********************************************************** End ********************************************** */

var checkDel = false;
	/**************************************************** Ownership Change Script ******************************* */
	$(document).on('change', '#ddlOwnership', function(e) {
		$('#loading').show();
		$val = $('#ddlOwnership option:selected').text();

		if ($val === 'Private') {
			$('.ownername').removeClass('d-none');
		} else {
			$('.ownername').addClass('d-none');
		}

		if (confirm('If you change the ownership, all crop data will be modified. Are you sure you want to change ownership?')) {
			checkDel = true;
			$('#ddlLandClass').val('');
			$('#ddlNoofCrop').val('');
			$('.otherDetailsDiv').addClass('d-none');
			$('.labeltext').html('');
			$cropDeatilDiv.addClass("d-none");
		}
		else {
			$('#ddlOwnership').val($dbownership);
			$('#ddlOwnership').focus();
			$('#loading').hide();
			return false;
		}

		$('#loading').hide();
	});

	/*************************************************************** End ***************************************************** */

	/************************************************************** edit Button click Script ************************************** */

	$(document).on('click', '#editCrop', function(e) {

		var myModal = $('#editbaselinecomdata');

		e.preventDefault();


		var $rowId = $(this).attr('data-id');
		//alert($rowId);
		$.ajax({
			type: 'POST',
			//getcroptype,  getSeasonList getcroptype getCropType
			url: "getSeasonList",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {

				var $actdesc = $('#season');
				$actdesc.empty();
				$actdesc.append('<option value=""> --Select Season--</option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$actdesc.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}
			}


		});

		$.ajax({
			type: 'POST',
			url: "getCropType",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				console.log(data);
				var $actdesc = $('#ctype');
				$actdesc.empty();
				$actdesc.append('<option value=""> --Select Crop Type-- </option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$actdesc.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}
			}


		});
		$.ajax({
			url: "baselinecdatafind",
			type: "get",
			data: { id: $rowId },
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				for (var key in data) {
					//alert(data[key].season_id);
					//	alert('data[key].bls_out_main_id_pk' +data[key].bls_out_main_id_pk);
					if (data.hasOwnProperty(key)) {
						if (typeof (data[key].season_id) == 'undefined') {
							$('#season').addClass("d-none");
							$('#lblSeason').addClass("d-none");
						}
						else {
							$('#season').removeClass("d-none");
							$('#lblSeason').removeClass("d-none");
						}
						$("#season option[value=" + data[key].season_id + "]").attr('selected', 'selected');
						$("#ctype option[value=" + data[key].crop_type_id + "]").attr('selected', 'selected');
						$('#areahac').val(data[key].area);
						$('#crop_prod').val(data[key].crop_production);
						$('#avg_income').val(data[key].avg_income);
						$('#bsl_crop_id').val(data[key].bls_out_detail_crop_id_pk);
						$('#plot_no').val(data[key].plot_no);
						$('#vcode').val(data[key].vcode);
						$('#proj_id').val(data[key].proj_id);
					}
				}
				$tblCropPlotDataBody = $('#tblCropPlotDataBody');
				$projId = $('#project').val();
				$tbody = '';
				$.ajax({
					url: "getCropDataOfPlotProject",
					type: "post",
					data: { vcode: $vcode, plotno: $plotNo, projId: $projId },
					error: function(xhr, status, er) {
						console.log(er);
						$('#loading').hide();
						$('.error').append(' There is some error please try again !');
					},
					success: function(data) {
						console.log(data);
						$index = 0;
						$tblCropPlotDataBody.empty();
						if (Object.keys(data).length > 0) {
							for (var key in data) {
								$index += 1
								if (typeof (data[key].season_id) != 'undefined') {
									$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td><td><input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
										'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
										'</td><td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
										'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
										'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
										'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
										'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
										'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
										'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//										+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
										;
									$('#existSeason').removeClass('d-none');
								}
								else {
									$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td>' +
										//									<input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
										//									'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
										'<td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
										'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
										'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
										'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
										'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
										'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
										'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//										+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
										;
									$('#existSeason').addClass('d-none');
								}
							}

							//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
							//$('#plotno').val('');
							//$('#plotno').focus();
						} else {
							//$('#projectArea').val('');
							//$('#ddlIrrigationStatus').val('');
						}
						$tblCropPlotDataBody.append($tbody);

						//alert('hi 3 '+ $('#cropNo').val());

					}

				});

			}
		});
		//location.reload();
		myModal.modal({ toggle: true });
		myModal.modal({ show: true });

	});

	/******************************************************************** End ******************************************************** */

	/************************************************************** Delete Button click Script ************************************** */

	$(document).on('click', '#deleteCrop', function(e) {
		e.preventDefault();
		if (confirm('Are you sure to wants to Delete this Record ?')) {

			var $rowId = $(this).attr('data-id');
			//	$('#tblCropPlotDataBody').find('tr').each(function(){
			//		$rowId = $(this).closest('tr').attr('id') ;
			//		$rowId = $rowId.match(/[\d\.]+/g);
			//		
			//	});


			$.ajax({
				url: "deleteCropData",
				type: "post",
				data: { rowId: $rowId },
				error: function(xhr, status, er) {
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						successAlert('Crop Data Deleted successfully !');
						$("#successok").click(function() {
							$('#popup').modal('hide');
						});
						//alert('Data Deleted successfully !');
						//window.location.href='blsoutupdate';
						$tblCropPlotDataBody = $('#tblCropPlotDataBody');
						$projId = $('#project').val();
						$tbody = '';
						$.ajax({
							url: "getCropDataOfPlotProject",
							type: "post",
							data: { vcode: $vcode, plotno: $plotNo, projId: $projId },
							error: function(xhr, status, er) {
								console.log(er);
								$('#loading').hide();
								$('.error').append(' There is some error please try again !');
							},
							success: function(data) {
								$seasonExist1Area = 0;
								$seasonExist2Area = 0;
								$seasonExist3Area = 0;
								$('#ddlSeason').val('');
								console.log(data);
								$index = 0;
								$tblCropPlotDataBody.empty();
								if (Object.keys(data).length > 0) {
									for (var key in data) {
										$index += 1
										if (data[key].season == 'Rabi')
											$seasonExist1Area = parseFloat($seasonExist1Area) + parseFloat(data[key].crop_area);
										if (data[key].season == 'Kharif')
											$seasonExist2Area = parseFloat($seasonExist2Area) + parseFloat(data[key].crop_area);
										if (data[key].season == 'Summer')
											$seasonExist3Area = parseFloat($seasonExist3Area) + parseFloat(data[key].crop_area);
										if (typeof (data[key].season_id) != 'undefined') {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td><td><input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'</td><td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').removeClass('d-none');
										}
										else {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td>' +
												//									<input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												//									'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'<td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').addClass('d-none');
										}
									}

									//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
									//$('#plotno').val('');
									//$('#plotno').focus();
								} else {
									//$('#projectArea').val('');
									//$('#ddlIrrigationStatus').val('');
								}
								$tblCropPlotDataBody.append($tbody);

								//alert('hi 3 '+ $('#cropNo').val());

							}

						});
					}

					if (data == 'fail')
						alert('This Crop Can not Deleted as it is the last Record !');

				}
			});
		}

	});

	/******************************************************************** End ******************************************************** */


	/******************edit update crop data**************************************************** */
	$(document).on('click', '#cropUpdate', function(e) {
		e.preventDefault();
		$totarea = $('#projectArea').val();
		if ($('#areahac').val() === '') {
			alert('please enter Area');
			$('#areahac').focus();
			return false;
		}
		if ($('#ctype').val() === '') {
			alert('please Select Crop Type');
			$('#ctype').focus();
			return false;
		}

		if ($('#crop_prod').val() === '') {
			alert('please enter Crop Production per ha');
			$('#crop_prod').focus();
			return false;
		}

		if ($('#avg_income').val() === '') {
			alert('please enter Avg. income per Quintal');
			$('#avg_income').focus();
			return false;
		}
		if (confirm('Are you sure to wants to Update this Record ?')) {
			$bsl_crop_id = $('#bsl_crop_id').val();
			$season = $('#season').val();
			$ctype = $('#ctype').val();
			$areahac = $('#areahac').val();
			$crop_prod = $('#crop_prod').val();
			$avg_income = $('#avg_income').val();
			$plotNo = $('#plot_no').val();
			$vcode = $('#vcode').val();
			$projId = $('#proj_id').val();
			$status = $('#status').val();



			//alert('v'+$vcode);
			$.ajax({
				url: "updateCropData",
				type: "post",
				data: { bsl_crop_id: $bsl_crop_id, season: $season, ctype: $ctype, areahac: $areahac, crop_prod: $crop_prod, avg_income: $avg_income, status: $status },
				error: function(xhr, status, er) {
					//alert(er);
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);

					$('#loading').hide();
					if (data === 'success') {
						//alert('Data Deleted successfully !');
						//	alert('v'+$vcode);
						//window.location.href='blsoutupdate';
						$tblCropPlotDataBody = $('#tblCropPlotDataBody');
						$projId = $('#project').val();
						$tbody = '';
						$.ajax({
							url: "getCropDataOfPlotProject",
							type: "post",
							data: { vcode: $vcode, plotno: $plotNo, projId: $projId },
							error: function(xhr, status, er) {
								console.log(er);
								$('#loading').hide();
								$('.error').append(' There is some error please try again !');
							},
							success: function(data) {
								console.log(data);
								$index = 0;
								$tblCropPlotDataBody.empty();
								if (Object.keys(data).length > 0) {
									for (var key in data) {
										$index += 1
										if (typeof (data[key].season_id) != 'undefined') {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td><td><input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'</td><td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').removeClass('d-none');
										}
										else {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td>' +
												//									<input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												//									'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'<td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').addClass('d-none');
										}
									}

									//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
									//$('#plotno').val('');
									//$('#plotno').focus();
								} else {
									//$('#projectArea').val('');
									//$('#ddlIrrigationStatus').val('');
								}
								$tblCropPlotDataBody.append($tbody);

								//alert('hi 3 '+ $('#cropNo').val());

							}

						});
						//						var myModal = $('#editbaselinecomdata');
						//						myModal.modal({ show: false });
						$('#editbaselinecomdata').modal('hide');
						//alert('Crop Record is successfully updated');
						successAlert('Crop Record is successfully updated');
						$("#successok").click(function() {
							$('#popup').modal('hide');
						});
					}

					else
						alert('Data Can Not Updated !');
				}
			});
		}

	});

	/******************************************************************** End ******************************************************** */

	/************************************************************** SaveasDraft Button click Script ************************************** */

	$(document).on('click', '#draftSave', function(e) {
		e.preventDefault();
		$cropTypeVal = new Array();
		$seasonVal = new Array();
		$seasonValnew = new Array();
		$cropArea = new Array();
		$cropProduction = new Array();
		$avgIncome = new Array();
		
			/****** */
			$('#tblCropPlotDataBody').find('tr').each(function() {
				//		alert('hi');
				$rowId = $(this).closest('tr').attr('id');
				$rowId = $rowId.match(/[\d\.]+/g);
				$cropStatus = $('#cropStatus'+$rowId).val();
				if ($cropStatus != 'D') {
					if ($seasonArray.length > 0)
						$seasonVal.push($('#seasonId' + $rowId).val());
				}
				if($cropStatus == 'D'){
					
					$blsoutDetailId1 = $('#blsOutDetailId').val(); 
					$.ajax({
						url: "changeownershipOrcropno",
						type: "post",
						data: { blsOutDetailId: $blsoutDetailId1 },
						error: function(xhr, status, er) {
							console.log(er);
							$('#loading').hide();
							$('.error').append(' There is some error please try again !');
						},
						success: function(data) {
							console.log(data);
							$('#loading').hide();
							if (data === 'success') {
							}
						}
					});
				}



			});
			/* ******/
			//alert('hi'+$rowId);
			$('#tblCropDeatilTbody').find('tr').each(function() {
				$rowId = $(this).closest('tr').attr('id');
				$rowId = $rowId.match(/[\d\.]+/g);
				$cropTypeVal.push($('#cropTypeVal' + $rowId).val());
				if ($seasonArray.length > 0) {
					$seasonVal.push($('#seasonVal' + $rowId).val());
					$seasonValnew.push($('#seasonVal' + $rowId).val());
				}
				$cropStatus = $('#cropStatus'+$rowId).val();
				if ($cropStatus == 'D') {
					$blsoutDetailId = $('#blsOutDetailId').val(); 
					$.ajax({
						url: "changeownershipOrcropno",
						type: "post",
						data: { blsOutDetailId: $blsoutDetailId },
						error: function(xhr, status, er) {
							console.log(er);
							$('#loading').hide();
							$('.error').append(' There is some error please try again !');
						},
						success: function(data) {
							console.log(data);
							$('#loading').hide();
							if (data === 'success') {
							}
						}
					});
				}
				else{
					$cropArea.push($('#cropArea' + $rowId).val());
					$cropProduction.push($('#cropProduction' + $rowId).val());
					$avgIncome.push($('#avgIncome' + $rowId).val());
				}

			});
			
		if (confirm('Have you filled all season data of selected village ?')) {
			

			
			console.log($seasonVal + '----' + $seasonArray);
			if (!($($seasonVal).not($seasonArray).length === 0 && $($seasonArray).not($seasonVal).length === 0)) {
				alert('Please fill all season data first. Then try again !');
				$('#ddlSeason').focus();
				return false;
			}
			$blsoutDetailId = $('#blsOutDetailId').val();
//			alert('check '+$blsoutDetailId);
			$projId = $('#project').val();
			$vcode = $('#village').val();
			$plotNo = $('#plotno').val();
			$projectArea = $('#projectArea').val();
			$irrigationStatus = $('#ddlIrrigationStatus').val();
			$ownership = $('#ddlOwnership').val();
			$landClassification = $('#ddlLandClass').val();
			//			next line added by Yogesh
			$landSubClassification = $('#subClassLand').val();
			$noOfCrop = $('#ddlNoofCrop').val();
			//$season = $('#ddlSeason').val();
			$ownerName = $('#ownername').val();
			$forestLandType = $('#ddlForestlandType').val();
			$microIrrigation = $('#ddlMicroIrrigation').val();

			if ($projId === '') {
				$('.projError').html('Please select Project');
				$('#project').focus();
				return false;
			} else {
				$('.projError').html('');
			}

			if ($vcode === '') {
				$('.villageError').html('Please select Village');
				$('#village').focus();
				return false;
			} else {
				$('.villageError').html('');
			}

			if ($plotNo === '') {
				$('.plotnoError').html('Please enter PlotNo');
				$('.plotnoError').css('color', 'red');
				$('#plotno').focus();
				return false;
			} else {
				$('.plotnoError').html('');
			}

			if ($projectArea === '') {
				$('.projectAreaError').html('Please enter Project Area');
				$('.projectAreaError').css('color', 'red');
				$('#projectArea').focus();
				return false;
			} else {
				$('.projectAreaError').html('');
			}

			if ($irrigationStatus === '') {
				$('.ddlIrrigationStatusError').html('Please select Irrigation Status');
				$('.ddlIrrigationStatusError').css('color', 'red');
				$('#ddlIrrigationStatus').focus();
				return false;
			} else {
				$('.ddlIrrigationStatusError').html('');
			}

			if ($ownership === '') {
				$('.ownershipError').html('Please select Ownership');
				$('.ownershipError').css('color', 'red');
				$('#ddlOwnership').focus();
				return false;
			} else {
				$('.ownershipError').html('');
				if ($('#ddlOwnership option:selected').text() === 'Private' && $ownerName === '') {
					$('.ownernameError').html('Please Enter Owner Name*');
					$('.ownernameError').css('color', 'red');
					$('#ownername').focus();
					return false;
				} else {
					$('.ownernameError').html('');
				}
			}

			if ($landClassification === '') {
				$('.landClassificationError').html('Please select Land Classification');
				$('.landClassificationError').css('color', 'red');
				$('#ddlLandClass').focus();
				return false;
			} else {
				if ($('#ddlLandClass option:selected').text() === 'Others' && $landSubClassification === '') {
					$('.landSubClassError').html('Please Enter SubClassification of Land*');
					$('.landSubClassError').css('color', 'red');
					$('#landSubClass').focus();
					return false;
				}
				if ($('#ddlLandClass option:selected').text() === 'Forest Land' && $forestLandType === '') {
					$('.ddlForestlandTypeError').html('Please select Forest Land Type*');
					$('.ddlForestlandTypeError').css('color', 'red');
					$('#ddlForestlandType').focus();
					return false;
				}
				if ($('#ddlLandClass option:selected').text() != 'Forest Land' && $noOfCrop === '') {
					$('.ddlNoofCropError').html('Please select No. of Crop');
					$('.ddlNoofCropError').css('color', 'red');
					$('#ddlNoofCrop').focus();
					return false;
				}
			}
			if ($irrigationStatus == 24) {
				$microIrrigation =0;
			} 
			
			if ($microIrrigation === '') {
				$('.ddlMicroIrrigationError').html('Please fill Micro-Irrigation');
				$('.ddlMicroIrrigationError').css('color', 'red');
				$('#ddlMicroIrrigation').focus();
				return false;
			} else {
				$('.ddlMicroIrrigationError').html('');
			}
			
			


			if (typeof $landSubClassification === 'undefined')
				$landSubClassification = null;

			if (typeof $forestLandType === 'undefined')
				$forestLandType = null;

			if (typeof $ownerName === 'undefined')
				$ownerName = null;

			if (typeof $seasonVal === 'undefined')
				$seasonVal = null;

			console.log('cropType: ' + $cropTypeVal + ' cropArea: ' + $cropArea + ' cropProduction ' + $cropProduction + ' avgIncome ' + $avgIncome + ' project ' + $projId + ' vcode ' + $vcode + ' plotno ' + $plotNo + ' projectArea ' + $projectArea + ' irrigationStatus ' + $irrigationStatus + ' ownership ' + $ownership + ' landClassification ' + $landClassification + 'landSubClassification' + $landSubClassification + ' noOfCrop ' + $noOfCrop + ' season ' + $seasonVal + ' ownerName ' + $ownerName + ' ForestLandType ' + $forestLandType);
			$('.error').html('');
			//alert('Hi');
			$.ajax({
				url: "updateBLS",
				type: "post",
				data: { projId: $projId, vcode: $vcode, plotNo: $plotNo, projectArea: $projectArea, irrigationStatus: $irrigationStatus, ownership: $ownership, ownerName: $ownerName, landClassification: $landClassification, landSubClassification: $landSubClassification, noOfCrop: $noOfCrop, season: $seasonValnew.toString(), cropType: $cropTypeVal.toString(), cropArea: $cropArea.toString(), cropProduction: $cropProduction.toString(), avgIncome: $avgIncome.toString(), forestLandType: $forestLandType, blsoutDetailId: $blsoutDetailId, microIrrigation: $microIrrigation },
				error: function(xhr, status, er) {
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
//					alert(data);
					if (data === 'success') {
//						successAlert('Baseline Data Updated successfully !');
//						$("#successok").click(function() {
//							$('#popup').modal('hide');
//						});
						alert('Data saved successfully !');
						window.location.href = 'blsoutupdate';
						/*$('#ddlSeason').val('');
						$cropDeatilDiv = $('.cropDeatilDiv');
						$tblCropDeatilTbody = $('#tblCropDeatilTbody');
						$tblCropDeatilTbody.empty();
						$cropDeatilDiv.addClass("d-none");
						$('#draftSave').addClass('d-none');*/

						/*$('#plotno').val('');
						$('#projectArea').val('');
						$('#ddlIrrigationStatus').val('');
						$('#ddlOwnership').val('');
						$('#ddlLandClass').val('');
						$('#ddlNoofCrop').val('');
						$('#ownername').val('');
						$('#ddlForestlandType').val('');*/
					}

					else
						alert('Data not saved !');
				}
			});
		}

	});

	/******************************************************************** End ******************************************************** */

	/******************************************************Edit Area check script ********************************************* */
	$totalCropArea = 0;
	$oldcropArea = 0;
	$(document).on('blur', '.areahac', function(e) {
		$cropArea = $('.areahac').val();
		$('#areahacError').html('');
		if ($('#areahac').val() === '') {
			$('#areahacError').html('Crop Area can not be Blank');
			$('#areahacError').css('color', 'red');
			$('.areahac').focus();
			return false;
		}
		$oldcropArea = $cropArea;

		$projectArea = $('#projectArea').val();
		if (parseFloat($cropArea) === 0 || parseFloat($cropArea) === .0 || parseFloat($cropArea) === 0.0) {
			$('#areahacError').html('Crop Area can not be zero');
			$('#areahacError').css('color', 'red');
			$('.areahac').focus();
			return false;
		}

		if (parseFloat($cropArea) > parseFloat($projectArea)) {
			$('#areahacError').html('Crop Area can not be greater than Plot Area');
			$('#areahacError').css('color', 'red');
			$('.areahac').val('');
			$('.areahac').focus();
			return false;
		} else {
			if ((parseFloat($totalCropArea) + parseFloat($cropArea)) > parseFloat($projectArea)) {
				$('#areahacError').html('Crop Area can not be greater than Plot Area');
				$('#areahacError').css('color', 'red');
				$('.areahac').val('');
				$('.areahac').focus();
				return false;
			} else {

				$totalCropArea = parseFloat($totalCropArea) + parseFloat($cropArea);
			}

			//$('#cropAreaError').html('else croparea '+$cropArea+' oldcroparea '+$oldcropArea+' totalCropArea '+$totalCropArea+' projectArea '+$projectArea );
		}

	});

	/********************************************************* End ******************************************************** */
	
//	/***************************** Project Change Script *************************************** */
//	$(document).on('change', '#ddlSeason', function(e) {
//		//alert('hi');
//		$('#loading').show();
//		//$ddlOwnership = $('#ddlOwnership');
//		$projId = $('#project').val();
//		$vCode =  $('#village').val();
//		
//		//alert('proj' + $projId + ' vcode '+$vCode);
//		$.ajax({
//			url: "getOwnership",
//			type: "post",
//			data: {},
//			error: function(xhr, status, er) {
//				console.log(er);
//				$('#loading').hide();
//				$('.error').append(' There is some error please try again !');
//			},
//			success: function(data) {
//				console.log(data);
//				$('#loading').hide();
//				$ddlOwnership.empty();
//				$ddlOwnership.append("<option value=''>---Select---</option>");
//				if (Object.keys(data).length > 0) {
//					for (var key in data) {
//						if (data.hasOwnProperty(key)) {
//							$ddlOwnership.append("<option value='" + key + "'>" + data[key] + "</option>");
//						}
//					}
//				}
//			}
//		});
//		
//		});
		
			/*****************************Season Change Script *************************************** */
	$(document).on('change', '#ddlSeason', function(e) {
		//alert('hi');
		$('#loading').show();
		//$ddlOwnership = $('#ddlOwnership');
		$projId = $('#project').val();
		$vCode =  $('#village').val();
		$plotNo = $('#plotno').val();
		$season = $('#ddlSeason').val();
		$cropTypeVal = new Array();
		$seasonVal = new Array();
			$seasonValnew = new Array();

		$('#tblCropDeatilTbody').find('tr').each(function() {
				$rowId = $(this).closest('tr').attr('id');
				$rowId = $rowId.match(/[\d\.]+/g);
				if($season==$('#seasonVal' + $rowId).val())
				{
					$cropTypeVal.push($('#cropTypeVal' + $rowId).val());
					if ($seasonArray.length > 0) {
						$seasonVal.push($('#seasonVal' + $rowId).val());
						$seasonValnew.push($('#seasonVal' + $rowId).val());
					}
				}
				

			});
		const fruits = $cropTypeVal;
	
		//alert('proj' + $projId + ' vcode '+$vCode +' plotNo'+$plotNo +' season'+$season);
		$.ajax({
			url: "getcroptypeupdate",
			type: "post",
			data: { projId: $projId, vCode: $vCode ,plotNo: $plotNo, season: $season},
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				$('#loading').hide();
				$ddlCropType.empty();
				$ddlCropType.append("<option value=''>---Select---</option>");
				if (Object.keys(data).length > 0) {
					//const fruits = $cropTypeVal;
					for (var key in data) {
						//var index = fruits.indexOf(key);
						//alert(fruits);
						if (data.hasOwnProperty(key)) {
								var index = fruits.indexOf(key);
							if(index==-1)
								$ddlCropType.append("<option value='" + key + "'>" + data[key] + "</option>");
						}
					}
				}
			}
		});
		
		});
		
	
});

/*************************************************** Function to get Total Income ****************************************** */

function getTotalIncome() {
	$cropArea = $('.cropArea').val();
	$cropProduction = $('.cropProduction').val();
	$avgIncome = $('.avgIncome').val();
	$('.totalIncome').val(($cropArea * $cropProduction * $avgIncome).toFixed(2));

}

/**************************************check micro-irrigation area against plot area ****************************************/
$(document).on('blur','.ddlMicroIrrigation',function(e){
	$plotArea = $('#projectArea').val();
	$microIrrArea = $('#ddlMicroIrrigation').val();
	if ($('#ddlMicroIrrigation').val() === '') {
			$('.ddlMicroIrrigationError').html('Micro-Irrigation Area can not be Blank');
			$('.ddlMicroIrrigationError').css('color', 'red');
			$('#ddlMicroIrrigation').focus();
			return false;
		}
	if (parseFloat($microIrrArea) === 0 || parseFloat($microIrrArea) === .0 || parseFloat($microIrrArea) === 0.0) {
		alert('Micro-Irrigation Area can not be zero');
		$('.ddlMicroIrrigationError').html('Micro-Irrigation Area can not be zero');
		$('.ddlMicroIrrigationError').css('color', 'red');
		$('#ddlMicroIrrigation').val('');
//		$('.ddlMicroIrrigation').focus();
		return false;
	}
		
//	alert('check ' +$plotArea);
	if(parseFloat($microIrrArea) > parseFloat($plotArea)){
		$('.ddlMicroIrrigationError').html('Micro-Irrigation Area can not be greater than Plot Area');
			$('.ddlMicroIrrigationError').css('color', 'red');
			$('#ddlMicroIrrigation').val('');
			$('.ddlMicroIrrigation').focus();
			return false;
	}
	
	
});


/**************************************on change irrigation status  ****************************************/
$(document).on('change','.ddlIrrigationStatus',function(e){
	$irrigationStatus = $('#ddlIrrigationStatus').val();
	if($irrigationStatus == 24){
		$('#ddlMicroIrrigation').val('0');
		$('.ddlMicroIrrigationArea').addClass('d-none')
		$('.ddlMicroIrrigation').addClass('d-none');
	}else{
		$('.ddlMicroIrrigationArea').removeClass('d-none')
		$('.ddlMicroIrrigation').removeClass('d-none');
	}
});	

/**************************************on change Forest Land type status  ****************************************/
$(document).on('change','.ddlForestlandType',function(e){
	
	if (confirm('If you change the No of Crop, all crop data will be modified. Are you sure you want to change the No of Crop?')) {

			$blsoutDetailId = $('#blsOutDetailId').val();
			
			
			$.ajax({
				url: "changeCropDeleteStatus",
				type: "post",
				data: { blsOutDetailId: $blsoutDetailId },
				error: function(xhr, status, er) {
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						$tblCropPlotDataBody = $('#tblCropPlotDataBody');
						$projId = $('#project').val();
						$tbody = '';
						$tblCropPlotDataBody.append($tbody);
						$tblCropPlotDataBody = $('#tblCropPlotDataBody');
						$projId = $('#project').val();
						$tbody = '';
						$.ajax({
							url: "getCropDataOfPlotProject",
							type: "post",
							data: { vcode: $vcode, plotno: $plotNo, projId: $projId },
							error: function(xhr, status, er) {
								console.log(er);
								$('#loading').hide();
								$('.error').append(' There is some error please try again !');
							},
							success: function(data) {
								console.log(data);
								$index = 0;
								$tblCropPlotDataBody.empty();
								if (Object.keys(data).length > 0) {
									for (var key in data) {
										$index += 1
										if (typeof (data[key].season_id) != 'undefined') {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td><td><input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'</td><td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+ '<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').removeClass('d-none');
										}
										else {
											$tbody += '<tr id=tr' + $index + '><td>' + $index + '</td>' +
												//									<input type="text" id="season' + $index + '" name="cropTypeVal' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season + '" readonly/>' +
												//									'<input type="hidden" id="seasonId' + $index + '" name="seasonId' + $index + '" class="cropTypeVal' + $index + ' form-control" value="' + data[key].season_id + '" readonly/>' +
												'<td><input type="text" id="cropType' + $index + '" name="cropType' + $index + '" class="cropType' + $index + ' form-control" value="' + data[key].crop_type + '" readonly /></td>' +
												'<td><input type="text" id="cropArea' + $index + '" name="cropArea' + $index + '" class="cropArea' + $index + ' form-control" value="' + data[key].crop_area + '" readonly/></td>' +
												'<input type="hidden" id="cropStatus' + $index + '" name="cropStatus' + $index + '" class="cropStatus' + $index + ' form-control" value="' + data[key].del_status + '"/>' +
												'<td><input type="text" id="cropProduction' + $index + '" name="cropProduction' + $index + '" class="cropProduction' + $index + ' form-control" value="' + data[key].crop_production + '" readonly/></td>' +
												'<td><input type="text" id="avgIncome' + $index + '" name="avgIncome' + $index + '" class="avgIncome' + $index + ' form-control" value="' + data[key].avg_income + '" readonly/></td>' +
												'<td><input type="text" id="totalIncome' + $index + '" name="totalIncome' + $index + '" class="totalIncome' + $index + ' form-control" value="' + (data[key].avg_income * data[key].crop_area * data[key].crop_production).toFixed(2) + '"  readonly /></td>' +
												'<td><a href="#" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="deleteCrop" name="deleteCrop" class="deleteCrop" >Delete </a></td>' 
//												+	'<td><a href="#editbaselinecomdata" data-id=' + data[key].bls_out_detail_tranx_id_pk + ' id="editCrop" name="editCrop" class="editCrop" >edit </a></td></tr>'
												;
											$('#existSeason').addClass('d-none');
										}
									}

									//alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
									//$('#plotno').val('');
									//$('#plotno').focus();
								} else {
									//$('#projectArea').val('');
									//$('#ddlIrrigationStatus').val('');
								}
								$tblCropPlotDataBody.append($tbody);
								$tblCropPlotDataBody.hide();

							}

						});
					}
					else
						alert('Data Can Not Deleted !');
					
				}
				
				});
		}
});	


