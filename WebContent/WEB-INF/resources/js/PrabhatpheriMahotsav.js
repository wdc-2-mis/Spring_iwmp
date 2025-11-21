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
