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