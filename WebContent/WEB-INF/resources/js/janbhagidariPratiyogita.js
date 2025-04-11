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
			
				/*	$.ajax({
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
									alert('Data Already Exists. Please Select a Different Project !');
									$("select#projid")[0].selectedIndex = 0;
												
								}
											
						    }
						  });   */

			
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
	});			
				
				
		$("#listvillageGPWiseTbody").on('click','.ddlgp',function () { 
				//$('#loading').show();
				$projectId = $('#projid option:selected').val();
				var $this = $(this);
				var gCode=[];
				
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
				id=id.substring(id.lastIndexOf("p")+1,id.length);
				//gCode.push($(this).val());
			//	$('#name_ngo'+id).val("");
			//	alert(id);
				
					$.ajax({  
				        url:"getVILLofJanbhagidariPratiyogita",
				        type: "post",  
				        data: {gpid:gCode.toString(), project:$projectId},
				        error:function(xhr,status,er){
				            console.log(er);
				        },
				        success: function(data) {
				console.log(data);
				$('#loading').hide();
				var $activity = $('#ddlvill'+id);
						$activity.empty();
				    	$activity.append('<option value="">--Select Village--</option>');
						for ( var key in data) {
							if (data.hasOwnProperty(key)) {
									$activity.append('<option value='+key+'>' +data[key] + '</option>');
							}
						}

				}
				});
				});			
				
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
	
	
var scntDiv = $('#listvillageGPWiseTbody');
var i = $('#listvillageGPWiseTbody tr').length;
var j = 1;
var arr = [];

$(document).on('click', '.btnAddRow', function(e) {
  e.preventDefault();
  var $projectId = $('#projid').val();
  arr[0] = '';
  arr[j] = i;
  j++;

  let newRow = `<tr id="tr${i}"><td>Name of NGO &nbsp;<input type="text" class="name_ngo form-control" name="name_ngo${i}" id="name_ngo${i}" autocomplete="off" style="width: 100%; max-width: 400px;" required /></td><td> Name of Gram Panchyat to be covered by NGO &nbsp;<select id="ddlgp${i}" name="ddlgp${i}" class="ddlgp form-control" multiple="multiple"><option value="">--Select Gram Panchayat--</option></select></td><td>Name of Villages to be covered by NGO &nbsp;<select id="ddlvill${i}" name="ddlvill${i}" class="ddlvill form-control" multiple="multiple"><option value="">--Select Village--</option></select></td><td style="vertical-align: bottom;"><button type="button" class="btn btn-danger btn-sm btnRemoveRow">−</button></td></tr>`;

  scntDiv.append(newRow);

  // Load Gram Panchayat options for new row
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
  updateSummaryStats();
});
				
			
			
			$(document).on('click', '#saveAsDraft', function(e){
				e.preventDefault();
				$dcode = $('#district option:selected').val();
				$projectId = $('#projid option:selected').val();
				/*$datein = $('#datein').val();
				$datecom = $('#datecom').val();*/
				$nogp = $('#nogp').val();
				$novillage = $('#novillage').val();
				$projarea = $('#projarea').val();
				$projoutlay = $('#projoutlay').val();
				
				$funoutlay = $('#funoutlay').val();
				$projexp = $('#projexp').val();
				$expper = $('#expper').val();
				$bank = $('input[name="bank"]:checked').val();
				
				var ngoname = [];
				var vill = [];
                var swckgp = null;
				
				/*if ($datein === '' || typeof $datein === 'undefined') {
						alert('Please select project inception date');
						$('#datein').focus();
						allValid = false;
						return false;
				}
				if ($datecom === '' || typeof $datecom === 'undefined') {
						alert('Please select proposed project completion date');
						$('#datecom').focus();
						allValid = false;
						return false;
				}*/
					
				if ($('#district option:selected').val() === '' || typeof $('#district option:selected').val() === 'undefined') {
						alert('Please select District');
						$('#district').focus();
						allValid = false;
						return false;
				}
				if ($projectId === '' || typeof $projectId === 'undefined') {
						alert('Please select project');
						$('#projid').focus();
						allValid = false;
						return false;
				}
					if ($nogp === '' || typeof $nogp === 'undefined') {
						alert('Please Enter Total No. of Gram Panchayat');
						$('#nogp').focus();
						allValid = false;
						return false;
					}
						
					if ($novillage === '' || typeof $novillage === 'undefined') {
						alert('Please Enter Total No. of Villages');
						$('#novillage').focus();
						allValid = false;
						return false;
					}
					
					if ($projarea === '' || typeof $projarea === 'undefined') {
						alert('Please Enter Total area allocated for project (ha.)');
						$('#projarea').focus();
						allValid = false;
						return false;
					}
					if ($projoutlay === '' || typeof $projoutlay === 'undefined') {
						alert('Please Enter Total Project Outlay (Rs. In Lakh)');
						$('#projoutlay').focus();
						allValid = false;
						return false;
					}
					if($('#swckgp').val()==""){
								alert('Please select SWCK Account at Gram Panchayat /Watershed Committee Level in Nationalized Bank');
								$('#swckgp').focus();
								return false;
							}
							
					if ($funoutlay === '' || typeof $funoutlay === 'undefined') {
						alert('Please Enter Total Project Outlay');
						$('#funoutlay').focus();
						allValid = false;
						return false;
					}
					if ($projexp === '' || typeof $projexp === 'undefined') {
						alert('Please Enter Total Project Expenditure');
						$('#projexp').focus();
						allValid = false;
						return false;
					}
					if ($expper === '' || typeof $expper === 'undefined') {
						alert('Please Enter Percentage of Expenditure (%)');
						$('#expper').focus();
						allValid = false;
						return false;
					}	
					/*if ($bank === '' || typeof $bank === 'undefined') {
						alert('Please select account is opened in a nationalized bank');
						$('input[name="bank"]').first().focus();
						allValid = false;
						return false;
					}*/
					var i = $('#listvillageGPWiseTbody tr').length;
					for (var j = 0; j < i; j++) {
						if (j == 0) {
							if($('#name_ngo').val()==""){
								alert('Please fill Ngo Name');
								$('#name_ngo').focus();
									return false;
							}
							if($('#ddlvill option:selected').val()==='' || typeof $('#ddlvill option:selected').val() === 'undefined'){
								
								alert('Please Select  Village');
								$('#ddlvill').focus();
								return false;
							}	
								
						}
						else{
							
							if($('#name_ngo'+j).val()==""){
								alert('Please fill Ngo Name');
								$('#name_ngo'+j).focus();
								return false;
							}
							if($('#ddlvill' + j + ' option:selected').val()==='' || $('#ddlvill' + j + ' option:selected').val()==='undefined'){
															
								alert('Please Select  Village');
								$('#ddlvill'+j).focus();
								return false;
							}	
						}
					}
					
					
							
					for (var j = 0; j < i; j++) {
						if (j == 0) {
								ngoname.push($('#name_ngo').val());

								var selectedVillages = $('#ddlvill option:selected').map(function() {
									        return $(this).val();
								}).get();

								vill.push(selectedVillages.join("#"));
						} 
						else {
								ngoname.push($('#name_ngo' + j).val());

								var selectedVillages = $('#ddlvill' + j + ' option:selected').map(function() {
									     return $(this).val();
								}).get();

								vill.push(selectedVillages.join("#"));
						}
					}
					
					 swckgp = $('#swckgp').val().join(',');
					
					
				//	const lowerCaseNames = ngoname.map(item => item.toLowerCase());
				//	const duplicates = ngoname.filter((item, index) => lowerCaseNames.indexOf(item.toLowerCase()) !== index);
				//	console.log(duplicates); // ["apple"]
					
					const duplicateCheck = ngoname.reduce((acc, item) => {
					    const lowerItem = item.toLowerCase();
					    acc[lowerItem] = (acc[lowerItem] || 0) + 1;
					    return acc;
					}, {});

					const duplicates = Object.keys(duplicateCheck).filter(key => duplicateCheck[key] > 1);
					if (duplicates.length >0){
						alert('You have Enter Duplicate NGO Name');
					return false;
					}
			
				$.ajax({  
			            url:"saveJanbhagidariPratiyogita",
			            type: "post",  
			            data: {vill:vill.toString(), ngoname:ngoname.toString(), dcode:$dcode, proj:$projectId, nogp:$nogp, novillage:$novillage, projarea:$projarea, projoutlay:$projoutlay, funoutlay:$funoutlay, projexp:$projexp, expper:$expper, swckgp:swckgp},
			            error:function(xhr,status,er){
			                console.log(er);
			            },
				success: function(data) {
					$('#loading').hide();
						if(data==='success'){
							alert('Saved As Draft Successfully');
							window.location.href='janbhagidariPratiyogita';
						}
						else if(data==='duplicate'){
							
							alert('You have Alredy Entered NGO Name for This Project');
							//window.location.href='janbhagidariPratiyogita';
						}
						else{
							alert('Data Not Saved');
							window.location.href='janbhagidariPratiyogita';
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