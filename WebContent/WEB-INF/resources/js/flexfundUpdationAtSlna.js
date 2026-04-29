$(function() {
	let currentImages = [];
    let currentIndex = 0;

	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dcode = $('#district option:selected').val();
		$.ajax({
			url: "getFlexFundProject",
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
	
	$(document).on('change', '#projid', function(e) {
		e.preventDefault();
		$pcode = $('#projid option:selected').val();
		$.ajax({
			url: "getFlexFundGramPanchayat",
			type: "post",
			data: { pCode: $pcode },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				console.log(data);
				$selectedProj = $('#projid').val();
				$ddlPanchayat = $('#panchayat');
				$ddlPanchayat.empty();
				$ddlPanchayat.append('<option value=""> --Select Gram Panchayat-- </option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						if (data[key] == $selectedProj)
							$ddlPanchayat.append('<option value="' + data[key] + '" selected>' + key + '</option>');
						else
							$ddlPanchayat.append('<option value="' + data[key] + '">' + key + '</option>');
					}
				}
			}
		});
	});
	
	
	$(document).on('change', '#panchayat', function () {

    let projid = $('#projid').val();
    let panchayat = $(this).val();

    if (!projid || !panchayat) return;

    $.ajax({
        url: "getFlexiFundCompleteData",   
        type: "POST",
        data: { projid: projid, panchayat: panchayat },

        success: function (res) {

            let tbody = $('#completeTbody');
            tbody.empty();

            if (!res || res.length === 0) {
                tbody.append(`
                    <tr>
                        <td colspan="8" class="text-center text-danger">
                            No records found
                        </td>
                    </tr>
                `);
                return;
            }

            res.forEach(item => {

                let photosHtml = '';

                if (item.photos && item.photos.length > 0) {

        let imagesJson = JSON.stringify(item.photos).replace(/"/g, '&quot;');

        item.photos.forEach((photo, index) => {
            photosHtml += `
                <img src="getImage?name=${photo.photoUrl}" 
                     class="previewImg openModalImg" 
                     data-index="${index}"
                     data-images="${imagesJson}"
                     style="width:60px;height:60px;margin:5px;">
            `;
        });
    }
    
let compDate = item.compDate 
    ? new Date(item.compDate).toLocaleDateString('en-CA') 
    : '';
                let row = `
<tr data-ff-id="${item.ffId}" data-comp-date="${compDate}">
    <td><input type="checkbox" class="rowCheckbox"></td>
    <td>${item.actName}</td>
    <td>${item.workDesc}</td>
    <td class="estCost">${item.est_cost}</td>
    <td>${item.comp_Date}</td>
    <td>
    <input type="text" class="form-control oldCost" 
           value="${item.ffCost}" readonly>
</td>
    
   <td>
         <input type="text" class="form-control newCostInput" placeholder="Enter amount">
       
    </td>
 <input type="hidden" class="form-control" name = "ff-Id" value="${item.ffId}">
    <td>${photosHtml}</td>

    <td>${item.remark || ''}</td>

    <td>
        <select class="form-control statusDropdown" ${item.status === 'C' ? 'disabled' : ''}>
            <option value="O" ${item.status === 'O' ? 'selected' : ''}>Ongoing</option>
            <option value="C" ${item.status === 'C' ? 'selected' : ''}>Complete</option>
        </select>
    </td>


    <td class="dateCell" style="${item.status === 'C' ? '' : 'display:none;'}">
        <input type="date" class="form-control completionDate" 
               value="${item.completionDate || ''}" 
               ${item.status === 'C' ? 'disabled' : ''}>
    </td>


</tr>

`;
        tbody.append(row);
                
            });
             tbody.append(`
    <tr>
        <td colspan="11" class="text-center">
            <button id="saveProgress" class="btn btn-success">Save</button>
        </td>
    </tr>
`);
        },

        error: function () {
            alert("Error loading data");
        }
    });
   

});


$(document).on('input', '.newCostInput', function () {

    let row = $(this).closest('tr');

    let newVal = $(this).val();

    let regex = /^\d*(\.\d{0,4})?$/;

    if (!regex.test(newVal)) {
        this.value = this.dataset.lastValid || '';
        return;
    } else {
        this.dataset.lastValid = newVal;
    }

    let oldCost = parseFloat(row.find('.oldCost').val()) || 0;
    let estCost = parseFloat(row.find('.estCost').text()) || 0;
    let newCost = parseFloat(newVal) || 0;

    if ((oldCost + newCost) > estCost) {

        alert("Total expenditure cannot exceed Estimated Cost");

        $(this).val('');
        this.dataset.lastValid = '';

        return;
    }
});

$(document).on('change', '.statusDropdown', function () {
    let row = $(this).closest('tr');
    let status = $(this).val();

    let dateCell = row.find('.dateCell');
    let dateInput = row.find('.completionDate');

    let compDate = row.data('comp-date'); // yyyy-MM-dd
    let today = new Date().toISOString().split('T')[0];

    if (status === 'C') {
        dateCell.show();

       if (compDate) {
            dateInput.attr('min', compDate);
            dateInput.attr('max', today);
        } else {
            dateInput.removeAttr('min');
            dateInput.attr('max', today);
        }

        if (!dateInput.val()) {
            dateInput.val(today);
        }

        $('.dateHeader').show();
    } else {
        dateCell.hide();
        dateInput.val('');

        let anyComplete = false;
        $('.statusDropdown').each(function () {
            if ($(this).val() === 'C') {
                anyComplete = true;
                return false;
            }
        });

        if (!anyComplete) {
            $('.dateHeader').hide();
        }
    }
});

$(document).on('click', '.previewImg', function () {
    let src = $(this).attr('src');
    $('#modalImg').attr('src', src);
    $('#imgModal').modal('show');
});




$(document).on('click', '#saveProgress', function () {

    let ffIds = [];
    let newCosts = [];
    let statuses = [];
    let completionDates = [];

    
    let isValid = true;

    let checkedRows = $('#completeTbody').find('.rowCheckbox:checked');

    if (checkedRows.length === 0) {
        alert("Please select at least one record");
        return;
    }

    checkedRows.each(function () {

        let row = $(this).closest('tr');

        let ffId = row.data('ff-id');
        let status = row.find('.statusDropdown').val();

        let oldCost = parseFloat(row.find('td:eq(5)').text()) || 0;
        let estCost = parseFloat(row.find('.estCost').text()) || 0;
        let newCost = parseFloat(row.find('.newCostInput').val()) || 0;

        let completionDate = row.find('.completionDate').val();

        
        if (!newCost || newCost <= 0) {
            alert("Please enter expenditure for selected row");
            isValid = false;
            return false;
        }

        if ((oldCost + newCost) > estCost) {
            alert("Total expenditure cannot exceed Estimated Cost");
            isValid = false;
            return false;
        }

        if (status === 'C' && !completionDate) {
            alert("Please select completion date");
            isValid = false;
            return false;
        }

        ffIds.push(ffId);
        newCosts.push(newCost);
        statuses.push(status);
        completionDates.push(completionDate || "");

    });

    if (!isValid) return;

    if (!confirm("Do you want to save selected records?")) return;

    // ================= AJAX =================
    $.ajax({
        url: "saveFlexiFundProgress",
        type: "POST",
        data: {
            "ffId[]": ffIds,
            "ffCost[]": newCosts,
            "status[]": statuses,
            "completionDate[]": completionDates
        },

        traditional: true, 

        success: function (res) {

            if (res === "success") {
                alert("Records saved successfully");
                window.location.href = 'flexiFundUpdationAtSlna'; // reload
            } else {
                alert("Save failed");
            }
        },

        error: function () {
            alert("Server error");
        }
    });
});

$(document).on('click', '.openModalImg', function () {

    currentImages = JSON.parse($(this).attr('data-images'));
    currentIndex = parseInt($(this).attr('data-index'));

    showImage();

    $('#imgModal').modal('show');
});

function showImage() {
    let imgName = currentImages[currentIndex].photoUrl;
    $('#modalImg').attr('src', 'getImage?name=' + imgName);
}

$(document).on('click', '.nextBtn', function () {
    currentIndex = (currentIndex + 1) % currentImages.length;
    showImage();
});

$(document).on('click', '.prevBtn', function () {
    currentIndex = (currentIndex - 1 + currentImages.length) % currentImages.length;
    showImage();
});

	});