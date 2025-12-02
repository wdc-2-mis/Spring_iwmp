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

			    let bcode = $('#block1').val().trim();

			    // Reset village dropdown if no block selected
			    if (!bcode) {
			        $('#village1').html('<option value="">--Select Village Name--</option>');
			        return;
			    }

			    $.ajax({
			        url: "getWMPrabhatPheriVillage",
			        type: "POST",
			        data: { bcode: bcode },
			        success: function(data) {
			            let $villageDropdown = $('#village1');
			            $villageDropdown.empty();
			            $villageDropdown.append('<option value="">--Select Village Name--</option>');

			            // Populate with returned map (villageName -> villageCode)
			            $.each(data, function(villageName, villageCode) {
			                $villageDropdown.append('<option value="' + villageCode + '">' + villageName + '</option>');
			            });
			        },
			        error: function(xhr, status, er) {
			            console.error("AJAX Error:", er);
			            console.log("Response:", xhr.responseText);
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
	
	
	$(document).on('click', '.showImage', function(e) {
				
				$ppId = e.target.getAttribute('data-id');
				$.ajax({
					type: 'POST',
					url: "getImageMahotsavPrabhatPheriId",
					data: { ppId: $ppId },
					error: function(xhr, status, er) {
						console.log(er);
					},
					success: function(data) {
						var imageContainer = $('.image-container');
						imageContainer.empty();
						let list = '<ul>';
						for (let i = 0; i < data.length; i++) {
							if (data[i] != null) 
							{
							//PRD
								list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/prabhatpheri/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
							//TEST
							//	list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/Inauguration/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
							//Local
//								list += '<li><img src="resources/images/prabhatpheri/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';

							}
						}
						list += '</ul>';
						document.getElementById('imageList').innerHTML = list;
						document.getElementById('imagePopup').style.display = 'block';
					}
				});
			});
	});	