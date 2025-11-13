$(function() {

	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dcode = $('#district option:selected').val();
		$.ajax({
			url: "getJanbhagidariPratiyogitaProject",
			type: "post",
			data: { dCode: $dcode },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				
				$('#listvillageGPWiseTbody').empty();
				
				$('#listvillageGPWiseTbody').append('<tr id="tr"><td>Name of NGO &nbsp; <input type="text" class="name_ngo form-control" name="name_ngo" id="name_ngo" class="name_ngo form-control" autocomplete="off" style="width: 100%; max-width: 400px;" required /></td><td> Name of Gram Panchyat to be covered by NGO &nbsp; <select id="ddlgp" name="ddlgp" class="ddlgp form-control" multiple="multiple"><option value="">--Select Gram Panchayat--</option></select></td><td>Name of Villages to be covered by NGO &nbsp; <select id="ddlvill" name="ddlvill" class="ddlvill form-control" multiple="multiple"><option value="">--Select Village--</option></select></td> <td style="vertical-align: bottom;"> <button type="button" class="btn btn-success btnAddRow">+</button></td></tr>');
					
				
				$selectedDist = $('#district').val();
				$ddlDistrict = $('#projid');
				$ddlDistrict.empty();
				$ddlDistrict.append('<option value=""> --Select Project-- </option>');
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
	
	
	$(document).on( 'change', '#projid', function (e) {
			e.preventDefault();
			$projectId = $('#projid option:selected').val();
			
					$.ajax({
						    url: "getExistingProjectCodes",
						    type: "post",
						    data: {pCode: $projectId},
						    error: function(xhr, status, er) {
						      console.log(er);
						    },
						    success: function(data) 
						    {
								if(data==='success')
								{
									alert('Data Already Exists. Now You can add NGO Details Only !');
									//alert('Data Already Exists. Please select different project');
									//$("select#projid")[0].selectedIndex = 0;
								}
						    }
						  });   

			
				$.ajax({  
		            url:"getJanbhagidariPratiyogitaProjectData",
		            type: "post", 
					data:{project:$projectId}, 
		            error:function(xhr,status,er){
		                console.log(er);
		            },
				    success:function(data) {
					console.log(data);

					$('#listvillageGPWiseTbody').empty();
					$('#listvillageGPWiseTbody').append('<tr id="tr"><td>Name of NGO &nbsp; <input type="text" class="name_ngo form-control" name="name_ngo" id="name_ngo" class="name_ngo form-control" autocomplete="off" style="width: 100%; max-width: 400px;" required /></td><td> Name of Gram Panchyat to be covered by NGO &nbsp; <select id="ddlgp" name="ddlgp" class="ddlgp form-control" multiple="multiple"><option value="">--Select Gram Panchayat--</option></select></td><td>Name of Villages to be covered by NGO &nbsp; <select id="ddlvill" name="ddlvill" class="ddlvill form-control" multiple="multiple"><option value="">--Select Village--</option></select></td> <td style="vertical-align: bottom;"> <button type="button" class="btn btn-success btnAddRow">+</button></td></tr>');
								
				
					if(Object.keys(data).length>0)
					{
						console.log(data);
						for ( var key in data) 
						{
							if (data.hasOwnProperty(key)) 
							{ 
								$('#nogp').val(data[key].gpcount); 
								$('#novillage').val(data[key].villagecount); 
								$('#projarea').val(data[key].projectarea); 
								$('#projoutlay').val(data[key].proj_outlay); 
								$('#funoutlay').val(data[key].fund_outlay); 
								$('#projexp').val(data[key].fund_expenditure); 
								$('#expper').val(data[key].fund_per_exp); 
								
								
							}
						}
					}
					}
				});
				
				
				
					$.ajax({  
				        url:"getGPofJanbhagidariPratiyogita",
				        type: "post",  
				        data: {project:$projectId},
				        error:function(xhr,status,er){
				            console.log(er);
				        },
				        success: function(data) {
   							 	$('#loading').hide();
    							var i = $('#listvillageGPWiseTbody tr').length;

							    // Populate ddlgp in each row
							    for (var x = 0; x < i; x++) {
							        var $head = (x === 0) ? $('#ddlgp') : $('#ddlgp' + x);
							        $head.empty();
							        $head.append('<option value="">--Select Gram Panchayat--</option>');
							        for (var key in data) {
							            if (data.hasOwnProperty(key)) {
							                $head.append('<option value="'+key+'">'+data[key]+'</option>');
							            }
							        }
							    }

						    // Also populate swckgp
						    var $swckgp = $('#swckgp');
						    $swckgp.empty();
						    $swckgp.append('<option value="">--Select Gram Panchayat--</option>');
						    for (var key in data) {
						        if (data.hasOwnProperty(key)) {
						            $swckgp.append('<option value="'+key+'">'+data[key]+'</option>');
						        }
						    }
						}
					});
					
					
					
					$.ajax({  
						url:"getJanbhagidariPratiyogitaProjectExist",
						type: "post", 
						data:{project:$projectId}, 
						error:function(xhr,status,er){
							    console.log(er);
						},
						success:function(data) {
							console.log(data);

							if(Object.keys(data).length>0)
							{
								$('#nogp').prop('readonly', true);
								$('#novillage').prop('readonly', true);
								$('#projarea').prop('readonly', true); 
								$('#projoutlay').prop('readonly', true); 
								$('#funoutlay').prop('readonly', true); 
								$('#projexp').prop('readonly', true);
								$('#expper').prop('readonly', true);
							}
							else{
								$('#nogp').prop('readonly', false);
								$('#novillage').prop('readonly', false); 
								$('#projarea').prop('readonly', false); 
								$('#projoutlay').prop('readonly', false); 
								$('#funoutlay').prop('readonly', false); 
								$('#projexp').prop('readonly', false);
								$('#expper').prop('readonly', false);
							}
						}
				});
					
					
					
					
					
	});			
				
	
$(document).on('blur', '.name_ngo', function () {
    var $input = $(this); // 👈 Save the reference to the input element
    var ngoName = $input.val().trim();
    var projectId = $('#projid').val();

    if (!ngoName) return;

    $.ajax({
        url: "checkNGOName",
        type: "POST",
        data: {
            ngoName: ngoName,
            projectId: projectId
        },
        success: function (data) {
            if (data) {
                alert("This NGO Name already exists. Please Enter a Different NGO Name.");
                $input.val("");  // 👈 Clear the input field
                $input.addClass('is-invalid');  // Add red border
                $input.attr('title', 'Duplicate NGO name in database!');
                toggleActionButtons(true);  // Disable buttons
            } else {
                $input.removeClass('is-invalid');
                $input.removeAttr('title');
                toggleActionButtons(false);  // Enable buttons
            }
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
});

				
	var scntDiv = $('#listvillageGPWiseTbody');
var i = $('#listvillageGPWiseTbody tr').length;
var j = 1;
var arr = [];

// Function to check for duplicate NGO names and return the duplicates
function getDuplicateNGONames() {
    const ngoNames = [];

    $('#listvillageGPWiseTbody input[name^="name_ngo"]').each(function () {
        let val = $(this).val().trim().toLowerCase();
        if (val !== "") {
            ngoNames.push(val);
        }
    });

    const nameCount = ngoNames.reduce((acc, name) => {
        acc[name] = (acc[name] || 0) + 1;
        return acc;
    }, {});

    // Get all duplicates by filtering out those with count greater than 1
    const duplicates = Object.keys(nameCount).filter(name => nameCount[name] > 1);

    return duplicates.length > 0 ? duplicates : false;
}

// Function to disable or enable buttons
function toggleActionButtons(disable) {
    $('.btnAddRow').prop('disabled', disable);
    $('.btnRemoveRow').prop('disabled', disable);
}

// Live duplicate check for red border without alert (for typing)
$(document).on('input', '.name_ngo', function () {
    let currentVal = $(this).val().trim().toLowerCase();
    let count = 0;

    $('.name_ngo').each(function () {
        let val = $(this).val().trim().toLowerCase();
        if (val === currentVal && val !== "") {
            count++;
        }
    });

    if (count > 1) {
        $(this).addClass('is-invalid');
        $(this).attr('title', 'Duplicate NGO name entered!');
    } else {
        $(this).removeClass('is-invalid');
        $(this).removeAttr('title');
    }

    // Check for duplicates after typing
    let duplicates = getDuplicateNGONames();
    if (duplicates) {
        alert("Duplicate NGO names found: " + duplicates.join(", "));
        toggleActionButtons(true);  // Disable buttons

        // Remove the last duplicate NGO name
        $('.name_ngo').each(function () {
            let val = $(this).val().trim().toLowerCase();
            if (duplicates.includes(val)) {
                // Check if the value is the last duplicate and clear it
                if ($('.name_ngo').filter(function () { return $(this).val().trim().toLowerCase() === val; }).last()[0] === this) {
                    $(this).val("");  // Clear the duplicate NGO name
                }
            }
        });
    } else {
        toggleActionButtons(false); // Enable buttons if no duplicates
    }
});

// Add Row
$(document).on('click', '.btnAddRow', function(e) {
    e.preventDefault();

    // Check for duplicates before adding a row
    let duplicates = getDuplicateNGONames();
    if (duplicates) {
        alert("Duplicate NGO names found: " + duplicates.join(", "));
        return false;
    }

    var $projectId = $('#projid').val();
    arr[0] = '';
    arr[j] = i;
    j++;

    let newRow = `<tr id="tr${i}">
        <td>Name of NGO &nbsp;
            <input type="text" class="name_ngo form-control" name="name_ngo${i}" id="name_ngo${i}" autocomplete="off" style="width: 100%; max-width: 400px;" required />
        </td>
        <td>Name of Gram Panchyat to be covered by NGO &nbsp;
            <select id="ddlgp${i}" name="ddlgp${i}" class="ddlgp form-control" multiple="multiple">
                <option value="">--Select Gram Panchayat--</option>
            </select>
        </td>
        <td>Name of Villages to be covered by NGO &nbsp;
            <select id="ddlvill${i}" name="ddlvill${i}" class="ddlvill form-control" multiple="multiple">
                <option value="">--Select Village--</option>
            </select>
        </td>
        <td style="vertical-align: bottom;">
            <button type="button" class="btn btn-danger btn-sm btnRemoveRow">−</button>
        </td>
    </tr>`;

    scntDiv.append(newRow);

    // Disable buttons if there are duplicates
    duplicates = getDuplicateNGONames();
    if (duplicates) {
        alert("Duplicate NGO names found: " + duplicates.join(", "));
        toggleActionButtons(true);  // Disable buttons
        return false;
    }

    if ($projectId !== '') {
        $.ajax({
            url: "getGPofJanbhagidariPratiyogita",
            type: "post",
            data: { project: $projectId },
            error: function (xhr, status, er) {
                console.log(er);
            },
            success: function (data) {
                var $head = $('#ddlgp' + i);
                $head.empty();
                $head.append('<option value="">--Select Gram Panchayat--</option>');
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        $head.append('<option value=' + key + '>' + data[key] + '</option>');
                    }
                }
                i++;
            }
        });
    } else {
        i++;
    }
});

// Remove Row
$(document).on('click', '.btnRemoveRow', function () {
    $(this).closest('tr').remove();

    // Check for duplicates after removal
    let duplicates = getDuplicateNGONames();
    if (duplicates) {
        alert("Duplicate NGO names found: " + duplicates.join(", "));
        toggleActionButtons(true);  // Disable buttons
    } else {
        toggleActionButtons(false); // Enable buttons
    }

    updateSummaryStats();
});

// Gram Panchayat change event
$("#listvillageGPWiseTbody").on('click', '.ddlgp', function () {
    var $projectId = $('#projid option:selected').val();
    var $this = $(this);
    var gCode = [];

    var selectedOptions = $this.find("option:selected");
    gCode = selectedOptions.map(function () {
        return $(this).val();
    }).get();

    var gpNames = selectedOptions.map(function () {
        return $(this).text();
    }).get().join(", ");

    var $td = $this.closest('td');
    $td.find('.selected-gp-list').remove();

    $td.append(
        '<div class="selected-gp-list" style="margin-top: 5px; font-size: 14px; color: #333;">' +
        '<strong>Selected Gram Panchayat:</strong> ' + gpNames +
        '</div>'
    );

    var id = $(this).attr('id');
    id = id.substring(id.lastIndexOf("p") + 1, id.length);

    $.ajax({
        url: "getVILLofJanbhagidariPratiyogita",
        type: "post",
        data: { gpid: gCode.toString(), project: $projectId },
        error: function (xhr, status, er) {
            console.log(er);
        },
        success: function (data) {
            $('#loading').hide();
            var $activity = $('#ddlvill' + id);
            $activity.empty();
            $activity.append('<option value="">--Select Village--</option>');
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    $activity.append('<option value=' + key + '>' + data[key] + '</option>');
                }
            }
        }
    });
});

// Village change event
$("#listvillageGPWiseTbody").on('change', '.ddlvill', function () {
    var $this = $(this);
    var selectedOptions = $this.find("option:selected");

    var villageNames = selectedOptions.map(function () {
        return $(this).text();
    }).get().join(", ");

    var $td = $this.closest('td');
    $td.find('.selected-village-list').remove();

    $td.append(
        '<div class="selected-village-list" style="margin-top: 5px; font-size: 14px; color: #333;">' +
        '<strong>Selected Villages:</strong> ' + villageNames +
        '</div>'
    );
});

// SWCKGP change event
$(document).on('change', '#swckgp', function () {
    var selected = [];
    $('#swckgp option:selected').each(function () {
        if ($(this).val() !== '') {
            selected.push($(this).text());
        }
    });

    $('.selected-swckgp-list').html(
        selected.length > 0
            ? '<strong>Selected GP(s):</strong> ' + selected.join(', ')
            : ''
    );
});

		
			
			
			$(document).on('click', '#saveAsDraft', function(e) {
    e.preventDefault();

    let allValid = true;

    const $dcode = $('#district').val();
    const $projectId = $('#projid').val();
    const $nogp = $('#nogp').val();
    const $novillage = $('#novillage').val();
    const $projarea = $('#projarea').val();
    const $projoutlay = $('#projoutlay').val();
    const $funoutlay = $('#funoutlay').val();
    const $projexp = $('#projexp').val();
    const $expper = $('#expper').val();
    const $swckgp = $('#swckgp').val();
    
     
    if (!$dcode) { alert('Please select District'); $('#district').focus(); return false; }
    if (!$projectId) { alert('Please select project'); $('#projid').focus(); return false; }
    if (!$nogp) { alert('Please enter Total No. of Gram Panchayat'); $('#nogp').focus(); return false; }
    if (!$novillage) { alert('Please enter Total No. of Villages'); $('#novillage').focus(); return false; }
    if (!$projarea) { alert('Please enter Total area allocated for project (ha.)'); $('#projarea').focus(); return false; }
    if (!$projoutlay) { alert('Please enter Total Project Outlay (Rs. In Lakh)'); $('#projoutlay').focus(); return false; }

    let ngoname = [];
    let vill = [];

    $('#listvillageGPWiseTbody tr').each(function(index) {
        const $row = $(this);
        const $ngoName = $row.find('.name_ngo').val()?.trim();
        const $selectedGPs = $row.find('.ddlgp').val(); // multiple
        const $selectedVillages = $row.find('.ddlvill').val(); // multiple

        if (!$ngoName) {
            alert('Please fill NGO Name in row ' + (index + 1));
            $row.find('.name_ngo').focus();
            allValid = false;
            return false;
        }

        if (!$selectedGPs || $selectedGPs.length === 0) {
            alert('Please select at least one Gram Panchayat in row ' + (index + 1));
            $row.find('.ddlgp').focus();
            allValid = false;
            return false;
        }

        if (!$selectedVillages || $selectedVillages.length === 0) {
            alert('Please select at least one Village in row ' + (index + 1));
            $row.find('.ddlvill').focus();
            allValid = false;
            return false;
        }

        ngoname.push($ngoName);
        vill.push($selectedVillages.join("#"));
    });

    if (!allValid) return false;

    if (!$swckgp || $swckgp.length === 0) {
        alert('Please select SWCK Account at Gram Panchayat / Watershed Committee Level in Nationalized Bank');
        $('#swckgp').focus();
        return false;
    }

    if (!$funoutlay) { alert('Please enter Total Project Outlay'); $('#funoutlay').focus(); return false; }
    if (!$projexp) { alert('Please enter Total Project Expenditure'); $('#projexp').focus(); return false; }
    if (!$expper) { alert('Please enter Percentage of Expenditure (%)'); $('#expper').focus(); return false; }

    // ✅ Duplicate NGO name check (case insensitive)
    const lowerCaseMap = {};
    const duplicates = [];

    ngoname.forEach(function(name) {
        const lower = name.toLowerCase();
        lowerCaseMap[lower] = (lowerCaseMap[lower] || 0) + 1;
        if (lowerCaseMap[lower] === 2) {
            duplicates.push(name);
        }
    });

    if (duplicates.length > 0) {
        alert('You have entered duplicate NGO name(s): ' + duplicates.join(', '));
        return false;
    }

    // ✅ Proceed with AJAX
    $.ajax({
        url: "saveJanbhagidariPratiyogita",
        type: "post",
        data: {
            vill: vill.toString(),
            ngoname: ngoname.toString(),
            dcode: $dcode,
            proj: $projectId,
            nogp: $nogp,
            novillage: $novillage,
            projarea: $projarea,
            projoutlay: $projoutlay,
            funoutlay: $funoutlay,
            projexp: $projexp,
            expper: $expper,
            swckgp: $swckgp.join(',')
        },
        error: function(xhr, status, er) {
            console.log(er);
        },
        success: function(data) {
            $('#loading').hide();
            if (data === 'success') {
                alert('Saved As Draft Successfully');
                window.location.href = 'janbhagidariPratiyogita';
            } else if (data === 'duplicate') {
                alert('You have already entered NGO Name for this project');
            } else {
                alert('Data Not Saved');
                window.location.href = 'janbhagidariPratiyogita';
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
								url:"deleteJanbhagidariPratiyogita",
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
										window.location.href='janbhagidariPratiyogita';
									}
									else{
										alert('Please check at least One Check Box, Data not Complete!');
										window.location.href='janbhagidariPratiyogita';
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
									url:"completeJanbhagidariPratiyogita",
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
											window.location.href='janbhagidariPratiyogita';
										}
										else{
												alert('Please check at least One Check Box, Data not Complete!');
												window.location.href='janbhagidariPratiyogita';
										} 
									}
							});
						}
				});		
					
	
				$(document).on('change', '#datein', function(e) {
						e.preventDefault();
						const currentDate = new Date();
						const dateToCheck = new Date(this.value);
						        
						currentDate.setHours(0, 0, 0, 0);
						dateToCheck.setHours(0, 0, 0, 0);

						if (currentDate < dateToCheck) {
						            alert("Project Inception Date cannot be Greater than the Current Date.");
						            document.getElementById('datein').value = '';
						            document.getElementById('datein').focus();
						}
				});		
				
				$(document).on('change', '#datecom', function(e) {
					e.preventDefault();

					const currentDate = new Date(); // Get the current date
					const dateToCheck = new Date(this.value);
					currentDate.setHours(0, 0, 0, 0);
					dateToCheck.setHours(0, 0, 0, 0);
					
					currentDate.setFullYear(currentDate.getFullYear() + 6); // Add 6 years
					const formattedDate = currentDate.toISOString().split('T')[0]; // Extract YYYY-MM-DD format
					const current5 = new Date(formattedDate);
					if (current5 <= dateToCheck) 
					{
							alert("Proposed Project Completion Date cannot be Greater than the 5 Year of Current Date.");
							document.getElementById('datecom').value = '';
							document.getElementById('datecom').focus();
					}
					/*if (currentDate >= dateToCheck) 
					{
							alert("Proposed Project Completion Date Cannot be less than of Current Date.");
							document.getElementById('datecom').value = '';
							document.getElementById('datecom').focus();
					}*/
					
				});			
	$("#listvillageGPWiseTbody").on('input', '.name_ngo', function () {
    updateSummaryStats();
});

$("#listvillageGPWiseTbody").on('change', '.ddlgp', function () {
    updateSummaryStats();
});

$("#listvillageGPWiseTbody").on('change', '.ddlvill', function () {
    updateSummaryStats();
});
});	

function updateSummaryStats() {
    // 1. Count NGOs
    var ngoCount = 0;
    $('.name_ngo').each(function() {
        if ($(this).val().trim() !== '') {
            ngoCount++;
        }
    });

    // 2. Count Gram Panchayats
    var gpCount = 0;
    $('.ddlgp').each(function() {
        gpCount += $(this).find('option:selected').length;
    });

    // 3. Count Villages
    var villCount = 0;
    $('.ddlvill').each(function() {
        villCount += $(this).find('option:selected').length;
    });

    // Set the values in summary fields
    $('#ngoCount').val(ngoCount);
    $('#gpCount').val(gpCount);
    $('#villCount').val(villCount);
}