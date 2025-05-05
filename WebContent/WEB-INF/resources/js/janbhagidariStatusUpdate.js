$(function() {

	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dcode = $('#district option:selected').val();
		var $ddlDistrict = $('#projid');
		
		if ($dcode === "") {
        $ddlDistrict.empty();
        $ddlDistrict.append('<option value="0">--All Project--</option>');
        window.location.href = "janbhagidariStatusUpdate";
        return;
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
				$ddlDistrict.append('<option value=""> --Select-- </option>');
				
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
	
	
	
	$('.chkIndividual').on('click',function(){
	var $row = $(this).closest('tr');
    var $select = $row.find('.compWork');
    var $completedDate = $row.find('.completedDate');
    if ($(this).is(':checked')) {
        $select.prop('disabled', false);
        $select.val('Y');
        var dateInput = $('<input>', {
            type: 'date',
            name: 'completedDate[]',
            class: 'form-control completed-date',
            autocomplete: 'off'
        });
        $select.prop('disabled', true);
        $completedDate.html(dateInput);
    } else {
        $select.prop('disabled', true);
        $select.val('N'); 
        $completedDate.empty();
    }
		
		});
		
	$(document).on('change', '.compWork', function () {
    var selectedVal = $(this).val();
    var $row = $(this).closest('tr');
    var $completedDate = $row.find('.completedDate');

    if (selectedVal === 'Y') {
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

$(document).on('click', '#update', function (e) {
    e.preventDefault();
    
    var finalAssetid=[];
    var compworkval = [];
    var completedDate = [];
    var isValid = true;
		
		$('.chkIndividual').each(function () {
        if ($(this).prop('checked')) {
            var $row = $(this).closest('tr');
            var compVal  = $row.find('.compWork').val();
            var dateVal = $row.find('.completed-date').val(); 

             if (compVal  === 'Y' && (!dateVal || dateVal.trim() === '')) {
                isValid = false;
                $row.find('.completedDate').css('border', '1px solid red'); 
            } else {
                $row.find('.completedDate').css('border', ''); 
            }

            finalAssetid.push($(this).val());
            compworkval.push(compVal);
            completedDate.push(dateVal || '');
        }
    });
    
    
	if (finalAssetid.length === 0) {
        alert('Please select at least one record to update!');
        return;
    }

    if (!isValid) {
        alert('Please fill Work Completed Date for selected Check box.');
        return;
    }	
						if(confirm("Do you want to update Work Completed Date ?"))
					{
						$.ajax({  
								url:"updateJanbhagidariCompletedDate",
								type: "post",  
								data: {assetid:finalAssetid.toString(), compworkval: compworkval.toString(), completedDate: completedDate.toString()},
								error:function(xhr,status,er){
										console.log(er);
								},
								success: function(data) 
								{
									console.log(data);
									$('#loading').hide();
									if(data==='success')
									{
										alert('Work Completed Date Updated Successfully.');
										window.location.href='janbhagidariStatusUpdate';
									}
									else{
										alert('Error in Completed Date. Please try again.');
										window.location.href='janbhagidariStatusUpdate';
									} 
								}
						});
					}
					
    });
});

function filterByProject() {
        var projId = document.getElementById("projid").value;
        var district = document.getElementById("district").value;
         var url = "janbhagidariStatusUpdate";
        if (projId !== ''  && district !== '') {
            window.location.href = url + "?projid=" + projId + "&district=" + district;
        } else {
            window.location.href = url;
        }
    }

