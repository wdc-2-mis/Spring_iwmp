/*$('#district').on('change', function(e) {
		e.preventDefault();
		
		$dCode=$('#district option:selected').val();
		if (!$dCode) {  
		$('#project').empty();
		$('#project').append('<option value=""> --Select Project-- </option>');
        $('#district').css('border', '4px solid red');  
        $('#district').focus();  
        return false;  
    } else {
        $('#district').css('border', '4px solid green'); 
        $('#project').css('border', '4px solid red'); 
    }
		$.ajax({  
            url:"getprojectforProjEva",
            type: "post", 
			data:{dCode:$dCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			console.log(data);
			var $districtProject = $('#project');
			            
						$districtProject.empty();
        				$districtProject.append('<option value=""> --Select Project-- </option>');
                         for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							$districtProject.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
			}
			});
			});
			
	$('#project').on('change', function(e) {
		e.preventDefault();
	$pCode = $('#project option:selected').val();
	  if (!$pCode) { 
	  $('#project').css('border', '4px solid red');  
        $('#project').focus();  
        return false;  
	}else {
        $('#project').css('border', '4px solid green');  
    }
	
	});*/
			
/*$(document).ready(function() {
    $('#view').on('click', function(e) {
        e.preventDefault();

        var $dCode = $('#district').val();
        var $pCode = $('#project').val();

        if (!$dCode) {
            $('#district').css('border', '2px solid red');
            $('#district').focus();
            return false;
        } else {
            $('#district').css('border', '');
        }

        if (!$pCode) {
            $('#project').css('border', '2px solid red');
            $('#project').focus();
            return false;
        } else {
            $('#project').css('border', '');
        }

        $('#loading').show();

        $.ajax({
            url: "getprojEvoRptdata", 
            type: "POST",
            data: { pCode: $pCode },
            success: function(data) {
                $('#loading').hide();

                var tbody = $('#tbodyProjEvolRpt');
                tbody.empty();

                 if (data && Object.keys(data).length > 0) {
                    var i = 1;
                    $.each(data, function(key, item) {
                        tbody.append('<tr><td>' + i + '</td><td>' + item.distname + '</td><td>' + item.projname + '</td><td>' + item.fin_yr + '</td><td>' + item.monthname + '</td><td><a href="#" class="btn btn-primary">Action</a></td></tr>');
                        i++;
                    });
                } else {
                     tbody.append("<tr><td colspan='6' class='text-center'>Data not found!</td></tr>");
                }
            },
            error: function(xhr, status, error) {
                $('#loading').hide();
                console.error(error);
                $('.error').html('There was an error. Please try again.');
            }
        });
    });
    
    $("#project").change(function() {
        var selectedProject = $(this).val();
        alert(selectedProject);
         if (selectedProject) {
			 $.ajax({
                url: "checkProjIdExists",  // URL mapped to Spring Controller
                type: "POST",
                data: { projectId: selectedProject },
                success: function(response) {
                    if (response.exists) { 
						var baseUrl = "";
						if (response.status === "D") {
                            baseUrl = "getProjectProfile";
                        } else if (response.status === "C") {
                            baseUrl = "getfinalProjectProfile";
                        } else {
                            return; // Do nothing if status is not D or C
                        }
                        
                        var url = baseUrl
                                + "projId=" + response.projId
                                + "&distCode=" + response.distCode
                                + "&distName=" + encodeURIComponent(response.distName)
                                + "&projName=" + encodeURIComponent(response.projName)
                                + "&finYearCode=" + response.finYearCode
                                + "&finYearDesc=" + encodeURIComponent(response.finYearDesc)
                                + "&monthId=" + response.monthId
                                + "&monthName=" + encodeURIComponent(response.monthName);
                        
                        window.location.href = url;
                    }
                },
                error: function() {
                    console.log("Error checking project existence.");
                }
            });
        }
    });
*/
$(document).ready(function() {
$("#finyear").change(function() {
    var selectedYear = $(this).val();
    if (selectedYear) {
        $.ajax({
            url: "getMonthsByFinYear",  // Controller URL
            type: "GET",
            data: { finYear: selectedYear },
            success: function(response) {
                var $monthDropdown = $("#month");
                $monthDropdown.empty(); 
                $monthDropdown.append('<option value="">--Select Month--</option>');

                if (response && response.length > 0) {
                    $.each(response, function(index, monthObj) {
                        // Assuming response = [{key: '01', value: 'January'}, ...]
                        $monthDropdown.append(
                            $('<option>', {
                                value: monthObj.key,
                                text: monthObj.value
                            })
                        );
                    });
                }
            },
            error: function() {
                console.log("Error loading months for selected financial year.");
            }
        });
    }
});
});
    






		