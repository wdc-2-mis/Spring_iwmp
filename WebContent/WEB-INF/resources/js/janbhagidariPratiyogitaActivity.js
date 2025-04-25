$(function() {

	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dcode = $('#district option:selected').val();
		var $ddlDistrict = $('#projid');
		var $ddlGram = $('#ddlgp');
		var $village = $('#ddlvill');
		
		if ($dcode === "") {
        $ddlDistrict.empty();
        $ddlDistrict.append('<option value="">--Select Project--</option>');
        $ddlGram.empty();
        $ddlGram.append('<option value="">--Select Gram Panchayat--</option>');
        $village.empty();
        $village.append('<option value="">--Select Village--</option>');
        return;
         }

if ($dcode !== "") {
           var $tbody = $("#listvillageGPWiseTbody");
            var $firstRow = $tbody.find("tr").not(':last').first();
            $tbody.find("tr").not(':first').not(':last').remove(); // keep only the original first and Save row

            // Reset first row values (don't touch id/name)
            $firstRow.find("select, input").val("");

            // Replace - with +
            $firstRow.find(".btnRemoveRow")
                .removeClass("btn-danger btnRemoveRow")
                .addClass("btn-success btnAddRow")
                .html("+");
        }
        
		$.ajax({
			url: "getJanbhagidariPratiyogitaProject",
			type: "post",
			data: { dCode: $dcode },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				
					
				$selectedDist = $('#district').val();
				$ddlDistrict = $('#projid');
				$ddlDistrict.empty();
				$ddlDistrict.append('<option value=""> --Select Project-- </option>');
				$ddlGram.empty();
                $ddlGram.append('<option value="">--Select Gram Panchayat--</option>');
                $village.empty();
                $village.append('<option value="">--Select Village--</option>');
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
			var $ddlGram = $('#ddlgp');
		    var $village = $('#ddlvill');
		    
		if ($projectId === "") {
        $ddlGram.empty();
        $ddlGram.append('<option value="">--Select Gram Panchayat--</option>');
        $village.empty();
        $village.append('<option value="">--Select Village--</option>');
        return;
         }
         
         if ($projectId !== "") {
           var $tbody = $("#listvillageGPWiseTbody");
            var $firstRow = $tbody.find("tr").not(':last').first();
            $tbody.find("tr").not(':first').not(':last').remove(); // keep only the original first and Save row

            // Reset first row values (don't touch id/name)
            $firstRow.find("select, input").val("");

            // Replace - with +
            $firstRow.find(".btnRemoveRow")
                .removeClass("btn-danger btnRemoveRow")
                .addClass("btn-success btnAddRow")
                .html("+");
        }
			
			
			$.ajax({
			url: "getGPofJanbhagidariPratiyogita",
			type: "post",
			data: { project: $projectId },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				$selectedProj = $('#projid').val();
				$ddlGram = $('#ddlgp');
				$ddlGram.empty();
				$ddlGram.append('<option value=""> --Select Gram Panchayat-- </option>');
				$village.empty();
                $village.append('<option value="">--Select Village--</option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						if (data[key] == $selectedProj)
							$ddlGram.append('<option value="' + key + '" selected>' + data[key] + '</option>');
						else
							$ddlGram.append('<option value="' + key + '">' + data[key] + '</option>');
					}
				}
			}
		});
	});
	
	$(document).on('change', '[id^=ddlgp]', function (e) {
    e.preventDefault();
    
    var $this = $(this);
    var $row = $this.closest("tr");
    var gcode = $this.val();
    var projectId = $('#projid').val();
    var $village = $row.find('[id^=ddlvill]');

    if (gcode === "") {
        $village.empty().append('<option value="">--Select Village--</option>');
        return;
    }

    $.ajax({
        url: "getVILLofJanbhagidariPratiyogita",
        type: "post",
        data: { gpid: gcode, project: projectId },
        error: function (xhr, status, er) {
            console.log(er);
        },
        success: function (data) {
            $village.empty().append('<option value=""> --Select Village-- </option>');
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    $village.append('<option value="' + key + '">' + data[key] + '</option>');
                }
            }
        }
    });
});

	
	$(document).on("click", ".btnAddRow", function () {
        var $tbody = $("#listvillageGPWiseTbody");
        var $rows = $tbody.find("tr").not(':last'); // exclude Save row
        var rowCount = $rows.length;

        var $newRow = $rows.first().clone();

        // Clear values
        $newRow.find("select, input").val("");

        // Replace + with -
        $newRow.find(".btnAddRow")
            .removeClass("btn-success btnAddRow")
            .addClass("btn-danger btnRemoveRow")
            .html("−");

        // Set new ids and names, skip first row format
        $newRow.find("select, input").each(function () {
            var originalId = $(this).attr("id");
            var originalName = $(this).attr("name");

            if (originalId && originalId !== "ddlgp" && originalId !== "ddlvill") {
                $(this).attr("id", originalId.replace(/\d*$/, '') + rowCount);
            } else if (originalId) {
                $(this).attr("id", originalId + rowCount);
            }

            if (originalName && originalName !== "ddlgp" && originalName !== "ddlvill") {
                $(this).attr("name", originalName.replace(/\d*$/, '') + rowCount);
            } else if (originalName) {
                $(this).attr("name", originalName + rowCount);
            }
        });

        $tbody.find("tr:last").before($newRow);
    });

    // Remove row
    $(document).on("click", ".btnRemoveRow", function () {
        $(this).closest("tr").remove();
    });

$(document).on('change', '.workid', function () {
    const $thisRow = $(this).closest('tr');
    const currentGp = $thisRow.find('.ddlgp').val();
    const currentVill = $thisRow.find('.ddlvill').val();
    const currentWork = $(this).val();

    if (!currentGp || !currentVill || !currentWork) return;

    let isDuplicate = false;

    $('#listvillageGPWiseTbody tr').each(function () {
        const $row = $(this);
        if ($row[0] === $thisRow[0]) return; 

        const gp = $row.find('.ddlgp').val();
        const vill = $row.find('.ddlvill').val();
        const work = $row.find('.workid').val();

        if (gp === currentGp && vill === currentVill && work === currentWork) {
            isDuplicate = true;
            return false; 
        }
    });

    if (isDuplicate) {
        alert('Duplicate Type of Work not allowed for the same Gram Panchayat and Village.');
        $(this).val(''); 
        $(this).focus();
    }
    
    if (!isDuplicate) {
     $.ajax({
        url: 'checkDuplicateWorkEntry',  // your backend controller URL
        method: 'POST',
        data: {
            villageId: currentVill,
            workId: currentWork
        },
        success: function(response) {
            if (response === 'duplicate') {
                alert('This  Gram Panchayat, Village "Type of Work"  already exists. Kindly select other!');
                $thisRow.find('.workid').val('').focus();
            }
        },
        error: function(err) {
            console.error("Error checking duplicate in database:", err);
        }
    });
}
});

$(document).on('change', '.compWork', function () {
    var selectedVal = $(this).val();
    var $row = $(this).closest('tr');
    var $completedDate = $row.find('.completedDate');

    if (selectedVal === 'Y') {
        var today = new Date().toISOString().split('T')[0]; 
        var dateInput = $('<input>', {
            type: 'date',
            name: 'completedDate[]',
            class: 'form-control completed-date',
            autocomplete: 'off'
        });
        $completedDate.html(dateInput);
    } else {
        $completedDate.empty();
    }
});

$(document).on('input', '.villagers, .ngos, .corporate', function () {
    const $row = $(this).closest('tr');  // Find the specific row that was changed
    let villagers = parseFloat($row.find('.villagers').val()) || 0;
    let ngos = parseFloat($row.find('.ngos').val()) || 0;
    let corporate = parseFloat($row.find('.corporate').val()) || 0;

    let total = villagers + ngos + corporate;

    if (total > 100) {
        alert("Villagers, NGOs, Corporate percentage cannot exceed 100.");
        $(this).val('');  // Reset the current field's value
        $(this).focus();
    }
});


$(document).on('click', '#saveAsDraft', function (e) {
    e.preventDefault();

    const $dcode = $('#district').val();
    const $projectId = $('#projid').val();

    if (!$dcode) {
        alert('Please select District');
        $('#district').focus();
        return false;
    }

    if (!$projectId) {
        alert('Please select Project');
        $('#projid').focus();
        return false;
    }

    let vill = [], workList = [], estValueList = [], villagersList = [],
        ngosList = [], corporateList = [], compWorkList = [], completedDateList = [];

    let allValid = true;
    let processedRows = 0;

    $('#listvillageGPWiseTbody tr').each(function (index) {
        const $row = $(this);

        const $selectedGPs = $row.find('.ddlgp').val();
        const $selectedVillages = $row.find('.ddlvill').val();
        const $selectedWorks = $row.find('.workid').val();
        const $estvalue = $row.find('.estvalue').val();
        const $villagers = $row.find('.villagers').val();
        const $ngos = $row.find('.ngos').val();
        const $corporate = $row.find('.corporate').val();
        const $compWork = $row.find('.compWork').val();
        const $completedDate = $row.find('.completed-date').val();

        // Skip completely empty rows
        if (!($selectedGPs || $selectedVillages || $selectedWorks || $estvalue || $villagers || $ngos || $corporate || $compWork)) {
            return true; // continue to next row
        }

        processedRows++; // count only partially or fully filled rows

        // Validation
        if (!$selectedGPs) {
            alert('Please select Gram Panchayat in row ' + (index + 1));
            $row.find('.ddlgp').focus();
            allValid = false;
        } else if (!$selectedVillages) {
            alert('Please select Village in row ' + (index + 1));
            $row.find('.ddlvill').focus();
            allValid = false;
        } else if (!$selectedWorks) {
            alert('Please select Type of Work in row ' + (index + 1));
            $row.find('.workid').focus();
            allValid = false;
        } else if (!$estvalue || isNaN($estvalue)) {
            alert('Please enter a valid Estimated Value in row ' + (index + 1));
            $row.find('.estvalue').focus();
            allValid = false;
        } else if (!$villagers || isNaN($villagers)) {
            alert('Please enter a valid Villagers Contribution Percentage in row ' + (index + 1));
            $row.find('.villagers').focus();
            allValid = false;
        } else if (!$ngos || isNaN($ngos)) {
            alert('Please enter a valid NGOs Contribution Percentage in row ' + (index + 1));
            $row.find('.ngos').focus();
            allValid = false;
        } else if (!$corporate || isNaN($corporate)) {
            alert('Please enter a valid Corporates Contribution Percentage in row ' + (index + 1));
            $row.find('.corporate').focus();
            allValid = false;
        } else if (!$compWork) {
            alert('Please select Work Completed in row ' + (index + 1));
            $row.find('.compWork').focus();
            allValid = false;
        } else if ($compWork === 'Y' && !$completedDate) {
            alert('Please select Completed Date in row ' + (index + 1));
            $row.find('.completed-date').focus();
            allValid = false;
        }

        // Skip pushing data if any validation failed for this row
        if (!allValid) return false;

        // Push valid row data
        vill.push($selectedVillages);
        workList.push($selectedWorks);
        estValueList.push($estvalue);
        villagersList.push($villagers);
        ngosList.push($ngos);
        corporateList.push($corporate);
        compWorkList.push($compWork);
        completedDateList.push($compWork === 'Y' ? $completedDate : '');
    });

    if (!allValid) return false;

    if (processedRows === 0) {
        alert('Please fill at least one row before saving.');
        return false;
    }

    
    $.ajax({
        url: "saveJanbhagidariPratiyogitaActivity",
        type: "post",
        data: {
            dcode: $dcode,
            proj: $projectId,
            vill: vill.toString(),
            workList: workList.toString(),
            estValueList: estValueList.toString(),
            villagersList: villagersList.toString(),
            ngosList: ngosList.toString(),
            corporateList: corporateList.toString(),
            compWorkList: compWorkList.toString(),
            completedDateList: completedDateList.toString()
        },
        error: function (xhr, status, er) {
            console.log(er);
        },
        success: function (data) {
            $('#loading').hide();
            if (data === 'success') {
                alert('Saved As Draft Successfully');
                window.location.href = 'janbhagidariPratiyogitaActivity';
            } else {
                alert('Data Not Saved');
                window.location.href = 'janbhagidariPratiyogitaActivity';
            }
        }
    });
});

    
    $('#chkSelectAll').on('click',function(){
					$chkValue=0;
					if(this.checked)
					{
						$('.chkIndividual').each(function(){
							this.checked = true;
							$chkValue++;
						});
					}
					else{
							$('.chkIndividual').each(function(){
								this.checked = false;
							});
							$chkValue=0;
					}
				});
				
				
	$(document).on('click', '#delete', function(e){
					e.preventDefault();
					var finalAssetid=new Array();

					$('.chkIndividual').each(function(){
							if($(this).prop('checked'))
							{
								finalAssetid.push($(this).val());
							}
					});
					if(finalAssetid.length > 0) {													      
					if(confirm("Do you want to Delete ?"))
					{
						$.ajax({  
								url:"deleteJanbhagidariActivity",
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
										window.location.href='janbhagidariPratiyogitaActivity';
									}
									else{
										alert('Error in Deletion. Please try again.');
										window.location.href='janbhagidariPratiyogitaActivity';
									} 
								}
						});
					}
					}
					else {
            alert('Please select at least one record to delete!');
        }
				});	
				
		$(document).on('click', '#complete', function(e){
					e.preventDefault();
					var finalAssetid=new Array();

					$('.chkIndividual').each(function(){
							if($(this).prop('checked'))
							{
								finalAssetid.push($(this).val());
							}
					});
					if(finalAssetid.length > 0) {													      
					if(confirm("Do you want to Complete ?"))
					{
						$.ajax({  
								url:"completeJanbhagidariActivity",
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
										alert('Data Completed Successfully.');
										window.location.href='janbhagidariPratiyogitaActivity';
									}
									else{
										alert('Error in Completion. Please try again.');
										window.location.href='janbhagidariPratiyogitaActivity';
									} 
								}
						});
					}
					}
					else {
            alert('Please select at least one record to delete!');
        }
				});					
});	