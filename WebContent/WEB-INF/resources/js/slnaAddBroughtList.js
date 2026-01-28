$('#district').on('change', function (e) {
    e.preventDefault();

    const dCode = $(this).val();
    const $ddlProject = $('#pendingProject');

    // Reset the project dropdown
    $ddlProject.empty().append('<option value="">--Select Project--</option>');

    if (!dCode) {
        return; // No district selected
    }

    $.ajax({
        url: "getAddBroughtFamerProjFromDistrict",
        type: "POST",
        data: { dCode: dCode },
        dataType: "json",
        beforeSend: function() {
            $ddlProject.append('<option disabled>Loading...</option>');
        },
        success: function (data) {
            console.log(data);

            // Clear and re-populate project dropdown
            $ddlProject.empty().append('<option value="">--Select Project--</option>');
            $ddlProject.empty().append('<option value="0">--All Project--</option>');
            if (data && Object.keys(data).length > 0) {
                $.each(data, function (key, value) {
                    $ddlProject.append(`<option value="${key}">${value}</option>`);
                });
            } else {
                $ddlProject.append('<option disabled>No data found for this District project</option>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching projects:", error);
            $('.error').text('There was an error fetching project data. Please try again!');
        }
    });
});

$('#getPendingAsset').on('click', function (e) {
    e.preventDefault();

    const district = $('#district').val();
    const project = $('#pendingProject').val();
    const year = $('#financial').val();

    if (!district || !project || !year) {
        alert('Please select District, Project and Financial Year');
        return false;
    }

    $.ajax({
        url: 'getPendingAdditionalFarmerCrop',
        type: 'POST',
        data: {
            district: district,
            projectId: project,
            year: year
        },
        dataType: 'json',
        success: function (data) {

            const $tbody = $('#farmerTableBody');
            $tbody.empty();

            if (!data || data.length === 0) {
                $tbody.append(`
                    <tr>
                        <td colspan="8" class="text-center text-danger">
                            No pending data found
                        </td>
                    </tr>
                `);
                return;
            }
$('#actionButtons').show();
            $.each(data, function (i, row) {
                $tbody.append(`
                    <tr data-id="${row.additional_brought_id}">
                        <td>
                            <input type="checkbox" class="chkRow">
                        </td>
                        <td>${row.project}</td>
                        <td>${row.finyear}</td>
                        <td>${row.achiev_type}</td>
                        <td>${row.diversified}</td>
                        <td>${row.chnagesingle}</td>
                        <td>${row.change_corp}</td>
                        <td>
                            <textarea class="form-control remark"
                                placeholder="Remark required for Reject"></textarea>
                        </td>
                        
                    </tr>
                `);
            });
        },
        error: function () {
            alert('Error while fetching pending crop data');
        }
    });
});

$('#chkSelectAll').on('change', function () {
    $('.chkRow').prop('checked', this.checked);
});

$('#btnApprove').on('click', function () {
    processSelectedRows('APPROVE');
});

$('#btnReject').on('click', function () {
    processSelectedRows('REJECT');
});

function processSelectedRows(action) {

    const selectedRows = $('.chkRow:checked');

    if (selectedRows.length === 0) {
        alert('Please select at least one record');
        return;
    }

    let ids = [];
    let remarks = [];
    let valid = true;

    selectedRows.each(function () {

        const row = $(this).closest('tr');
        const id = row.attr('data-id');
        const remark = row.find('.remark').val().trim();

        if (action === 'REJECT' && remark === '') {
            alert('Remark is mandatory for rejection');
            row.find('.remark').focus();
            valid = false;
            return false; // break loop
        }

        ids.push(id);
        remarks.push(remark);
    });

    if (!valid) return;

    if (!confirm('Are you sure you want to ' + action + ' selected record(s)?')) {
        return;
    }

    $.ajax({
        url: 'updateAdditionalFarmerCropStatus',
        type: 'POST',
        traditional: true,   
        data: {
            action: action,
            ids: ids,
            remarks: remarks
        },
        success: function (res) {
            if (res === 'SUCCESS') {
                alert('Data ' +action + ' successfully');
                selectedRows.closest('tr').remove();

                if ($('#farmerTableBody tr').length === 0) {
                    $('#actionButtons').hide();
                }
            } else {
                alert('Operation failed. Please try again.');
            }
        },
        error: function () {
            alert('Server error occurred');
        }
    });
}
