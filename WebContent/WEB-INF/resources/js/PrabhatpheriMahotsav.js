$(function() {
	
	$(document).on('change', '#district1', function(e) {
				e.preventDefault();
				$stCode = $('#district1 option:selected').val();
				$.ajax({
					url: "getWatershedYatraBlock",
					type: "post",
					data: { stateCode: $stCode },
					error: function(xhr, status, er) {
						console.log(er);
					},
					success: function(data) {
						$selectedDist = $('#district1').val();
						$ddlDistrict = $('#block1');
						$ddlDistrict.empty();
						$ddlDistrict.append('<option value=""> --Select Block-- </option>');
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
			
			
			$(document).on('change', '#block1', function(e) {
							e.preventDefault();
							$gpsCode=$('#block1 option:selected').val();
							$.ajax({  
							        url:"getWMPrabhatPheriVillage",
							        type: "post", 
									data:{bcode:$gpsCode}, 
							        error:function(xhr,status,er){
							                console.log(er);
							        },
							        success:function(data) 
									{
										$selectedDist=$('#block1').val();
										$ddlDistrict = $('#village1');
										$ddlDistrict.empty();
							        	$ddlDistrict.append('<option value=""> --Select Village Name-- </option>');
										for ( var key in data) 
										{
											if (data.hasOwnProperty(key)) 
											{
												if(data[key]==$selectedDist)
													$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
												else
													$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
											}
										}
									}
								});
					});
	
	
	
	
	
	
	
	
	
	
// ==================== SELECT ALL CHECKBOX ====================
$(document).on('change', '#chkSelectAllkd', function () {
    $('.chkIndividualkd').prop('checked', $(this).prop('checked'));
});

// ==================== COMPLETE BUTTON CLICK ====================
$(document).on('click', '#complete', function (e) {
    e.preventDefault();

    var finalAssetid = [];

    // Collect all selected checkbox IDs
    $('.chkIndividualkd:checked').each(function () {
        finalAssetid.push($(this).val());
    });

    // Validation: No checkbox selected
    if (finalAssetid.length === 0) {
        alert("Please check at least One Check Box, Data not Complete!");
        return false;
    }

    // Confirm action
    if (confirm("Do you want to Complete ?")) {

        $.ajax({
            url: "completeWMPrabhatPheri",
            type: "post",
            data: { ppid: finalAssetid.toString() },

            error: function (xhr, status, er) {
                console.log("Error: " + er);
            },

            success: function (data) {
                console.log("Response: " + data);

                if (data === 'success') {
                    alert("Completed Successfully.");
                } else {
                    alert("Something went wrong!");
                }

                // Refresh page
                window.location.href = "getWMPrabhatPheri";
            }
        });
    }
});


//======================== Delete Prabhat Pheri ======================
$(document).ready(function () {
    $("#delete").click(function () {

        var selectedIds = [];

        $(".chkIndividualkd:checked").each(function () {
            selectedIds.push($(this).val());
        });

        if (selectedIds.length === 0) {
            alert("Please select at least one record to delete.");
            return;
        }

        if (!confirm("Are you sure you want to delete selected records?")) {
            return;
        }

        $.ajax({
            type: "POST",
            url: "deleteWMPrabhatPheri",
            traditional: true,
            data: { ppid: selectedIds },
            success: function (response) {
                alert(response);
                location.reload();
            },
            error: function () {
                alert("Error! Something went wrong.");
            },
			success: function (data) {
			               console.log("Response: " + data);

			               if (data === 'success') {
			                   alert("Deleted Successfully.");
			               } else {
			                   alert("Something went wrong!");
			               }

			               // Refresh page
			               window.location.href = "getWMPrabhatPheri";
			           }
        });
    });
});


	$(document).on('click', '#village1', function (e) {
        var vCode = this.value;
       
        $.ajax({
            url: 'checkVillageWMP',
            type: 'POST',
            data: {
                vCode: vCode
            },
            success: function(exists) {
                if (exists) {
					alert('Village already exists. Please select a different Village. !');
						$("select#village1")[0].selectedIndex = 0;
					
                }
            }
        });
    });
	
	});	