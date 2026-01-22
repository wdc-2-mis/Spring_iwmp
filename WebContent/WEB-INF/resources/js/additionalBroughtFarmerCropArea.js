$(function(){

$(document).on('change', '#project', function () {
    const projectId = $(this).val();

    if (!projectId) {
      $('#financial').html('<option value="">--Select Year--</option>');
      $('.yeark').addClass('d-none');
      return;
    }

    // Show loader if needed
    $('.yeark').removeClass('d-none');

    $.ajax({
      url: 'getFinYearByProject', // new controller method
      type: 'GET',
      data: { project: projectId },
      success: function (data) {
        $('#financial').empty().append('<option value="">--Select Year--</option>');

        $.each(data, function (key, value) {
          $('#financial').append('<option value="' + key + '">' + value + '</option>');
        });

        $('.yeark').removeClass('d-none'); // Show financial year
      },
      error: function (xhr, status, error) {
        console.log('Error fetching financial years: ', error);
      }
    });
    $.ajax({
        url: 'getCompletedDataByProject',   // new controller method
        type: 'GET',
        data: { project: projectId },
        success: function (html) {
            $('#completeTableContainer').html(html); // Replace section with new HTML table
        },
        error: function () {
            console.log('Error fetching completed data');
            $('#completeTableContainer').html('<div class="text-danger">Error loading completed data.</div>');
        }
    });
  });

$('#financial').on('change', function () {
    var financial = $(this).val();

    $('.yeark, .mont, .trk, .montt, .draft, #forwardSLNA').addClass('d-none');
    $('.message').text('').removeClass('badge badge-danger badge-success');
    $('#month').empty().append('<option value="">--Select Half Yearly--</option>');
    $('#groupType').empty().append('<option value="">--Select Type--</option>');
    $('input[type="text"]').val('').prop('readonly', true); // clear inputs and make them readonly

    if (financial) {
        $.ajax({
            url: 'getAchievementTypeByFinancialYear',
            type: 'GET',
            data: { financial: financial },
            success: function (response) {
                var $groupType = $('#groupType');
                $groupType.empty();
                $groupType.append('<option value="">--Select Type--</option>');
                $('.trk, .montt').addClass('d-none');

                if (response.achievementTypes && response.achievementTypes.length > 0) {
                    $.each(response.achievementTypes, function (i, item) {
                        $groupType.append('<option value="' + item.key + '">' + item.value + '</option>');
                    });
                }
                $('#financial').val(financial);
            },
            error: function () {
                console.log('Error while fetching achievement type');
            }
        });
    } else {
        $('#groupType').empty().append('<option value="">--Select Type--</option>');
        $('#month').empty().append('<option value="">--Select Half Yearly--</option>');
    }
});
	
$(document).on('change', '#groupType', function (e) {
    e.preventDefault();

    const projectId = $('#project').val();
    const gType = $('#groupType').val();
    const financial = $('#financial').val();

    $('#additional_brought_id, #diversified, #chnagesingle, #farmer, #changecorp, #pulses, #oilseeds').val('');
    $('.yeark, .mont, .trk, .montt, .draft, #forwardSLNA').addClass('d-none');
    $('.message').text('');

    if (!financial) {
        $('.message')
            .text('Please select a Financial Year first.')
            .addClass('badge badge-danger');
        return;
    }

$.ajax({
        url: 'getFinancialYearPeriod',
        type: 'GET',
        data: { financial },
        success: function (period) {

    if (!period.start_from) {
        $('.message')
            .text('Invalid Financial Year or no data found.')
            .addClass('badge badge-danger');
        return;
    }

            const [sDay, sMonth, sYear] = period.start_from.split('-');
            const [eDay, eMonth, eYear] = period.end_to.split('-');
            const start = new Date(`${sYear}-${sMonth}-${sDay}`);
            const end = new Date(`${eYear}-${eMonth}-${eDay}`);
            const today = new Date();

    const inPeriod = today >= start && today <= end;

    $('input[type="text"]').prop('readonly', false);

    /* ---------- PERIOD NOT STARTED ---------- */
    if (today < start) {
    $('.message')
        .text(`Entry period starts from ${period.start_from} for this financial year.`)
        .removeClass('badge-success')
        .addClass('badge badge-danger');
        
    $('input[type="text"]').prop('readonly', true);
    $('.draft, #forwardSLNA').addClass('d-none');
}

    /* ---------- PERIOD CLOSED ---------- */
  else  if (today > end) {

        $('.message')
            .text(`Entry period closed. Data entry was allowed between ${period.start_from} and ${period.end_to}.`)
            .removeClass('badge-success')
            .addClass('badge badge-danger');

        $('input[type="text"]').prop('readonly', true);
        $('.draft, #forwardSLNA').addClass('d-none');
       
    }
else{
    /* ---------- PERIOD ACTIVE ---------- */
    $('.message').text('').removeClass('badge-danger badge-warning');
}

            if (gType === 'fy') {
                $('.yeark').removeClass('d-none');
                $('#forwardSLNA').addClass('d-none');

                if (parseInt(financial) >= 25) {
                    $('.yeark').removeClass('d-none');
    $('.montt, .trk').removeClass('d-none');

    $('#diversified, #chnagesingle, #changecorp')
        .closest('tr')
        .removeClass('d-none');

                     $.ajax({
                        url: "getAdditionalBroughtYearComplt",
                        type: "POST",
                        data: { project: projectId, group: gType, fyear: financial },
                        success: function (data) {
                            if (Object.keys(data).length > 0) {
                                for (var key in data) {
                                    if (data.hasOwnProperty(key)) {
                                        $('#additional_brought_id').val(data[key].additional_brought_id);
                                        $('#diversified').val(data[key].diversified);
                                        $('#chnagesingle').val(data[key].chnagesingle);
                                        $('#changecorp').val(data[key].change_corp);

                                        $('.draft, #forwardSLNA').addClass('d-none');

                        if ((data[key].status === '' || data[key].status == null) && (data[key].slnastatus === '' || data[key].slnastatus == null)) {
                            $('.draft').removeClass('d-none');
                        }
                        else if (data[key].status === 'D' && (data[key].slnastatus === '' || data[key].slnastatus == null)) {
                            $('.draft').removeClass('d-none');
                            $('#forwardSLNA').removeClass('d-none');
                        }
                        else if (data[key].status === 'D' && data[key].slnastatus === 'S') {
                            alert('Data forwarded to SLNA.');
                            $('.draft').addClass('d-none');
                            $('#forwardSLNA').addClass('d-none');
                                 
                             $('.error').css({
                            "background-color": "rgb(123, 144, 57)",
                            "color": "#fff",
                            "padding": "8px",
                            "border-radius": "5px",
                            "text-align": "center",
                            "margin-top": "10px"
                            }).html("Year-Wise Data has been forwarded to SLNA.");   
                          }
                      else if (data[key].status === 'C') {
     $('.draft').addClass('d-none');
    $('#forwardSLNA').addClass('d-none');

     let $errorBox = $('.error');
    if ($errorBox.length === 0) {
        $errorBox = $('<div class="error"></div>').insertAfter('table, form').first();
    }

    $errorBox.removeClass('d-none');

     $errorBox.css({
        "background-color": "rgb(123 144 57)",
        "padding": "8px",
        "border-radius": "5px",
        "color": "#fff",
        "margin-top": "10px",
        "text-align": "center"
    }).html("Selected Financial Year data has been <b>Completed</b>.");

}

                    }
                }
            }  else {
                                if (inPeriod) $('.draft').removeClass('d-none');
                            }
                        },
                        error: function (xhr, status, err) {
                            console.log("Error loading FY data: ", err);
                        }
                    });

                } 
                else {
                    $('.montt').addClass('d-none');
                    $('.trk').removeClass('d-none');

                    $.ajax({
                        url: "getAdditionalBroughtYearComplt",
                        type: "POST",
                        data: { project: projectId, group: gType, fyear: financial },
                        success: function (data) {
                            if (Object.keys(data).length > 0) {
                                for (var key in data) {
                                    if (data.hasOwnProperty(key)) {
                                        $('#additional_brought_id').val(data[key].additional_brought_id);
                                        $('#farmer').val(data[key].farmer_income);
                                        $('#changecorp').val(data[key].change_corp);


                                        if ((data[key].status === '' || data[key].status == null) && (data[key].slnastatus === '' || data[key].slnastatus == null)) {
                            $('.draft').removeClass('d-none');
                        }
                        else if (data[key].status === 'D' && (data[key].slnastatus === '' || data[key].slnastatus == null)) {
                            $('.draft').removeClass('d-none');
                            $('#forwardSLNA').removeClass('d-none');
                        }
                        else if (data[key].status === 'D' && data[key].slnastatus === 'S') {
                            alert('Data forwarded to SLNA.');
                            $('.draft').addClass('d-none');
                            $('#forwardSLNA').addClass('d-none');
                             $('.error').css({
                            "background-color": "rgb(123, 144, 57)",
                            "color": "#fff",
                            "padding": "8px",
                            "border-radius": "5px",
                            "text-align": "center",
                            "margin-top": "10px"
                            }).html("Year-Wise Data has been forwarded to SLNA."); 
                                
                        }
                         else if (data[key].status === 'C'){
							  $('.complete, .draft, #forwardSLNA').addClass('d-none');
                               alert('Selected Financial Year data has been completed.');
                                 $('.error').css({
                            "background-color": "rgb(123, 144, 57)",
                            "color": "#fff",
                            "padding": "8px",
                            "border-radius": "5px",
                            "text-align": "center",
                            "margin-top": "10px"
                            }).html("Selected Financial Year data has been completed.");  
                                
                                
                                
						 }

                                       
                                    }
                                }
                            } else {
                                if (inPeriod) $('.draft').removeClass('d-none');
                            }
                        },
                        error: function (xhr, status, err) {
                            console.log("Error loading old FY data: ", err);
                        }
                    });
                }
            }

             else if (gType === 'm') {
                $('.mont, .montt').removeClass('d-none');
                $('#forwardSLNA').addClass('d-none');

                if (inPeriod) {
					 $('.draft').removeClass('d-none');

                const $monthDropdown = $('#month');
                $monthDropdown.empty().append('<option value="">--Select Half Yearly--</option>');

                $.ajax({
                    url: 'getHalfYearPeriod',
                    type: 'GET',
                    success: function (data) {
                        for (let key in data) {
                            if (data.hasOwnProperty(key)) {
                                $monthDropdown.append('<option value="' + key + '">' + data[key] + '</option>');
                            }
                        }
                    },
                    error: function () {
                        console.log("Error loading half-year periods");
                    }
                });
            }
            
            else {
        // If outside period, disable inputs
        $('input[type="text"]').prop('readonly', true);
        $('#month').empty().append('<option value="">--Select Half Yearly--</option>');
    }
    } 
            else {
                $('.yeark, .mont, .montt').addClass('d-none');
                $('.draft, #forwardSLNA').addClass('d-none');
                $('#additional_brought_id').val('');
                $("select#month")[0].selectedIndex = 0;
            }
        },
        error: function () {
            $('.message')
                .text('Failed to load Financial Year data. Please try again.')
                .addClass('badge badge-danger');
        }
    });
});



	// ---------------------------------------------------------------------
	// DRAFT SAVE
	// ---------------------------------------------------------------------
	$('#draftSave').on('click', function (e) {
    e.preventDefault();

    if ($('#project').val() === '') {
        alert('Please select Project');
        $('#project').focus();
        return false;
    }
    if ($('#groupType').val() === '') {
        alert('Please select Achievement Type.');
        $('#groupType').focus();
        return false;
    }
    if ($('#financial').val() === '') {
        alert('Please select Financial Year');
        $('#financial').focus();
        return false;
    }

    const financial = $('#financial').val(); // e.g. 2025-26
     const isNewFormat = financial >= 25;

    const groupType = $('#groupType').val();

    // Validation
    if (groupType === 'm') {
        if ($('#month').val() === '') {
            alert('Please select Month');
            $('#month').focus();
            return false;
        }

        if (isNewFormat) {
            if ($('#diversified').val() === '') {
                alert('Please fill Diversified Crops/Change in Cropping System');
                $('#diversified').focus();
                return false;
            }
            if ($('#chnagesingle').val() === '') {
                alert('Please fill No crop/single crop to single/multiple crop');
                $('#chnagesingle').focus();
                return false;
            }
        } else {
            if ($('#pulses').val() === '') {
                alert('Please fill Increase in Pulses Area');
                $('#pulses').focus();
                return false;
            }
            if ($('#oilseeds').val() === '') {
                alert('Please fill Increase in Oilseeds Area');
                $('#oilseeds').focus();
                return false;
            }
        }
    }

    if (groupType === 'fy') {
        if (isNewFormat) {
            if ($('#diversified').val() === '') {
                alert('Please fill Diversified Crops/Change in Cropping System');
                $('#diversified').focus();
                return false;
            }
            if ($('#chnagesingle').val() === '') {
                alert('Please fill No crop/single crop to single/multiple crop');
                $('#chnagesingle').focus();
                return false;
            }
            if ($('#changecorp').val() === '') {
                alert('Please fill Increase in Gross Cropped Area');
                $('#changecorp').focus();
                return false;
            }
        } else {
            if ($('#farmer').val() === '') {
                alert('Please fill Change in Farmer Income data');
                $('#farmer').focus();
                return false;
            }
            if ($('#changecorp').val() === '') {
                alert('Please fill Change in Cropped Area data');
                $('#changecorp').focus();
                return false;
            }
        }
    }

    // Confirm and Save
    if (confirm('Do you want to Save this Record ?')) {
        const data = {
            projId: $('#project').val(),
            month: $('#month').val() || 0,
            year: financial,
            diversified: $('#diversified').val() || 0,
            chnagesingle: $('#chnagesingle').val() || 0,
            farmer: $('#farmer').val() || 0,
            changecorp: $('#changecorp').val() || 0,
            status: 'D',
            additionalid: $('#additional_brought_id').val() || 0,
            atype: groupType,
            pulses: $('#pulses').val() || 0,
            oilseeds: $('#oilseeds').val() || 0
        };

        $.ajax({
            url: "saveAdditionalBroughtFarmerCropArea",
            type: "POST",
            data: data,
            success: function (response) {
                if (response === 'success') {
                    alert('Data Saved Successfully!');
                    window.location.href = 'getAdditionalBroughtFarmerCropArea';
                } else {
                    alert('Data already Completed!');
                    window.location.href = 'getAdditionalBroughtFarmerCropArea';
                }
            },
            error: function (xhr, status, er) {
                console.log("Error:", er);
            }
        });
    }
});


	// ---------------------------------------------------------------------
	// YEAR CHANGE
	// ---------------------------------------------------------------------
	$(document).on('change', '.yeark', function (e) {
		e.preventDefault();
		$projectId = $('#project').val();
		$gType = $('#groupType').val();
		$year = $(this).val();
		$('#additional_brought_id').val('');
		$month=0;

		// ⭐ NEW: Reset visibility
		$('#forwardSLNA').addClass('d-none');
		$('.draft').addClass('d-none');

		$.ajax({  
	        url:"getAdditionalBroughtYearDraft",
	        type: "post", 
			data:{project:$projectId,group:$gType,fyear:$year, mont:$month}, 
	        error:function(xhr,status,er){ console.log(er); },
			success:function(data) {
				if(Object.keys(data).length>0) {
					for ( var key in data) {
						if (data.hasOwnProperty(key)) { 
							const rec = data[key];
							$('#additional_brought_id').val(rec.additional_brought_id); 
							$('#farmer').val(rec.farmer_income); 
							$('#changecorp').val(rec.change_corp); 
							$('.complete').removeClass('d-none');

							// ⭐ NEW: Handle SLNA and completion logic
							if (rec.status === 'D' && rec.slnastatus === 'S') {
								alert('Data forwarded to SLNA.');
								$('.draft').addClass('d-none');
								$('#forwardSLNA').addClass('d-none');
								$('.error').css("background-color","rgb(123 144 57)");
								$('.error').html("Year-Wise Data has been forwarded to SLNA. ");
								
							} else if (rec.status === 'C') {
								alert('Selected Parameter data has been Completed.');
								$('.draft').addClass('d-none');
								$('#forwardSLNA').addClass('d-none');
							} else if (rec.status === 'D' && rec.slnastatus !== 'S') {
								$('.draft').removeClass('d-none');
								$('#forwardSLNA').removeClass('d-none');
							}
						}
					}
				}
			}
		});
		
		if($gType==='fy'){
			$.ajax({  
		        url:"getAdditionalBroughtYearComplt",
		        type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year}, 
		        error:function(xhr,status,er){ console.log(er); },
				success:function(data) {
					if(Object.keys(data).length>0) {
						for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								$('#additional_brought_id').val(data[key].additional_brought_id); 
								$('#farmer').val(data[key].farmer_income); 
								$('#changecorp').val(data[key].change_corp); 
								$('.complete').addClass('d-none');
								$('.draft').addClass('d-none');
								$('#forwardSLNA').addClass('d-none'); // ⭐ NEW
								alert('Selected Parameter data has been Completed, Please wait for next Financial Year.');
							}
						}
					}
					else {
						$('.draft').removeClass('d-none');
					}
				}
			});
		}

		if($gType==='m'){
			$.ajax({  
	            url:"getRemainYearMonth",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year}, 
	            error:function(xhr,status,er){ console.log(er); },
	            success:function(data) {
					$ddlmonth = $('#month');
					$ddlmonth.empty();
					$ddlmonth.append('<option value=""> --Select Half Yearly-- </option>');
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlmonth.append('<option value="'+key+'">' +data[key]+ '</option>');
						}
					}
				}
			});
		}
	});

	// ---------------------------------------------------------------------
	// MONTH CHANGE
	// ---------------------------------------------------------------------
	$(document).on('change', '.mont', function (e) {
    e.preventDefault();

    const projectId = $('#project').val();
    const gType = $('#groupType').val();
    const year = $('#financial').val();
    const month = $('#month').val();

    // Reset UI first
    $('#forwardSLNA').addClass('d-none');
    $('.draft').addClass('d-none');
    $('.error').html('').css("background-color", "");

    if (!month) return;

    // === 1️⃣ Check for Draft / SLNA status ===
    $.ajax({
        url: "getAdditionalBroughtYearDraft",
        type: "POST",
        data: { project: projectId, group: gType, fyear: year, mont: month },
        success: function (data) {
            if (Object.keys(data).length > 0) {
                for (let key in data) {
                    if (data.hasOwnProperty(key)) {
                        const rec = data[key];

                        // Populate fields
                        $('#additional_brought_id').val(rec.additional_brought_id);
                        $('#diversified').val(rec.diversified);
                        $('#chnagesingle').val(rec.chnagesingle);
                        $('#pulses').val(rec.pulses);
                        $('#oilseeds').val(rec.oilseeds);

                        // Button visibility
                        if ((!rec.status || rec.status === '') && (!rec.slnastatus || rec.slnastatus === '')) {
                            // No data yet
                            $('.draft').removeClass('d-none');
                        } 
                        else if (rec.status === 'D' && (!rec.slnastatus || rec.slnastatus === '')) {
                            // Draft saved, not yet sent to SLNA
                            $('.draft').removeClass('d-none');
                            $('#forwardSLNA').removeClass('d-none');
                        } 
                        else if (rec.status === 'D' && rec.slnastatus === 'S') {
                            // Forwarded to SLNA
                            alert('Data forwarded to SLNA.');
                            $('.draft, #forwardSLNA').addClass('d-none');
                            $('.error').css({
                            "background-color": "rgb(123, 144, 57)",
                            "color": "#fff",
                            "padding": "8px",
                            "border-radius": "5px",
                            "text-align": "center",
                            "margin-top": "10px"
                            }).html("Half-Yearly Data has been forwarded to SLNA.");
                          }
                        else if (rec.status === 'C') {
                            // Completed, locked
                            alert('Selected Parameter data has been Completed.');
                            $('.draft, #forwardSLNA').addClass('d-none');
                        }
                    }
                }
            } else {
                // No record exists — new entry
                $('.draft').removeClass('d-none');
            }
        },
        error: function (xhr, status, er) {
            console.log("Error (Draft check):", er);
        }
    });

    // === 2️⃣ Check for Completion ===
    $.ajax({
        url: "getAdditionalBroughtMonthComplt",
        type: "POST",
        data: { project: projectId, group: gType, fyear: year, mont: month },
        success: function (data) {
            if (Object.keys(data).length > 0) {
                // Mark as complete — override draft visibility
                const rec = data[Object.keys(data)[0]];
                $('#additional_brought_id').val(rec.additional_brought_id);
                $('#diversified').val(rec.diversified);
                $('#chnagesingle').val(rec.chnagesingle);
                $('#pulses').val(rec.pulses);
                $('#oilseeds').val(rec.oilseeds);

                $('.draft, #forwardSLNA').addClass('d-none');
                      
                  $('.error').css({
                            "background-color": "rgb(123, 144, 57)",
                            "color": "#fff",
                            "padding": "8px",
                            "border-radius": "5px",
                            "text-align": "center",
                            "margin-top": "10px"
                            }).html("Data has been completed for the selected period.");   
                    
                    
                alert('Selected Parameter data has been Completed.');
            }
        },
        error: function (xhr, status, er) {
            console.log("Error (Completion check):", er);
        }
    });
});


	// ---------------------------------------------------------------------
	// ⭐ NEW: FORWARD TO SLNA HANDLER
	// ---------------------------------------------------------------------
	$('#forwardSLNA').on('click', function(e) {
		e.preventDefault();

		if ($('#project').val() === '') {
			alert('Please select Project');
			$('#project').focus();
			return false;
		}
		if ($('#financial').val() === '') {
			alert('Please select Financial Year');
			$('#financial').focus();
			return false;
		}

		if (!confirm('Are you sure you want to forward this record to SLNA?')) return false;

		const project = $('#project').val();
		const month = $('#month').val() || 0;
		const year = $('#financial').val();
		const additionalId = $('#additional_brought_id').val() || 0;

		$.ajax({
			url: 'AdditionalBroughtFarmerforwardToSLNA',
			type: 'post',
			data: {additionalid: additionalId },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				if (data === 'success') {
					alert('Data forwarded to SLNA successfully!');
					window.location.href = 'getAdditionalBroughtFarmerCropArea';
				} else {
					alert('Issue while forwarding data to SLNA!');
					window.location.href = 'getAdditionalBroughtFarmerCropArea';
				}
			}
		});
	});

	// ---------------------------------------------------------------------
	// EXISTING COMPLETE HANDLER (unchanged except formatting)
	// ---------------------------------------------------------------------
	$('#complete').on('click',function(e) {
		e.preventDefault();
		if($('#project').val()===''){
			alert('please select Project');
			$('#project').focus();
			return false;
		}
		if($('#financial').val()===''){
			alert('please select Financial Year');
			$('#financial').focus();
			return false;
		}
		
		if (confirm('Are you sure to wants to Complete this Record ?')) {
			$project = $('#project').val();
			$month = $('#month').val() || 0;
			$financial = $('#financial').val();
			$additional_id = $('#additional_brought_id').val() || 0;
			$status = 'C';
			
			$.ajax({ 
				url:"completeAdditionalBroughtFarmerCropArea",
				type: "post", 
				data:{projId:$project, month:$month, year:$financial, status:$status, additionalid:$additional_id}, 
				error:function(xhr,status,er){ console.log(er); },
				success:function(data) {
					if(data==='success'){
						alert('Data completed Successfully!');
						window.location.href='getAdditionalBroughtFarmerCropArea';	
					} else {
						alert('Data not completed, technical issue!');
						window.location.href='getAdditionalBroughtFarmerCropArea';	
					}	
				}
			});
		}
	});	

});
