$(function() {
let rowFilesMap = new Map();
let rowCounter = 0;
let globalImageSet = new Set();

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
globalImageSet.clear();
    $.ajax({
        url: "getFlexiFundData",
        type: "POST",
        data: { projid, panchayat },

        success: function (res) {

    if (res.status === "NEW") {

        $('#entrySection').show();
        $('#draftSection').hide();
        $('#completeSection').hide();

        resetForm();
    }

    else if (res.status === "EXIST") {

        // Load draft & complete
        loadDraftTable(res.draftData);
        loadCompleteTable(res.completeData);

        // Check remaining activities
        let totalActivities = activityList.length;
        let draftCount = res.draftData.length;
        let completeCount = res.completeData.length;

        let remaining = totalActivities - (draftCount + completeCount);

        if (remaining > 0) {
            $('#entrySection').show();
            resetForm(); // fresh rows
        } else {
            $('#entrySection').hide();
        }

        // Show draft only if exists
        if (draftCount > 0) {
            $('#draftSection').show();
        } else {
            $('#draftSection').hide();
        }

        // Show complete only if exists
        if (completeCount > 0) {
            $('#completeSection').show();
        } else {
            $('#completeSection').hide();
        }
    }
}
    });
    
    

});


function convertDMSToDD(dms, ref) {

    function toDecimal(val) {
        if (typeof val === "object") {
            return val.numerator / val.denominator;
        }
        return val;
    }

    let degrees = toDecimal(dms[0]);
    let minutes = toDecimal(dms[1]);
    let seconds = toDecimal(dms[2]);

    let dd = degrees + (minutes / 60) + (seconds / 3600);

    if (ref === "S" || ref === "W") {
        dd = dd * -1;
    }

    return dd;
}

function getActualFileName(name) {
    return name.includes('_') ? name.split('_').pop() : name;
}

function loadDraftTable(data) {

    $('#draftTbody').empty();

    // ✅ Clear only draft rows (not entry rows)
    for (let key of rowFilesMap.keys()) {
        if (key.startsWith("draft_")) {
            rowFilesMap.delete(key);
        }
    }

    let rowId = 0;

    data.forEach(item => {

        let photosHtml = '';
        let files = [];

        let rowKey = "draft_" + rowId;

        if (item.photos && item.photos.length > 0) {

            item.photos.forEach(photo => {

                let actualName = getActualFileName(photo.photoUrl);
                globalImageSet.add(actualName);

                files.push({
                    name: photo.photoUrl,
                    existing: true
                });

                photosHtml += `
                <div style="display:inline-block;position:relative;">
                    <img src="getImage?name=${photo.photoUrl}" 
                         class="previewImg"
                         style="width:60px;height:60px;margin:5px;cursor:pointer;">
                    
                    <span class="deletePhoto"
                          data-index="${files.length - 1}"
                          data-name="${photo.photoUrl}"
                          style="position:absolute;top:0;right:0;color:red;cursor:pointer;">
                          ❌
                    </span>
                </div>`;
            });
        }

        // ✅ Always set fresh (important)
        rowFilesMap.set(rowKey, files);

        let row = `
        <tr data-row-id="${rowKey}" data-db-id="${item.ffId}">
            <td><input type="checkbox" class="draftCheckbox"></td>

            <td>
                <select class="form-control activityDropdown">
                    ${getFilteredActivityOptions(item.actId)}
                </select>
            </td>

            <td><input type="text" value="${item.workDesc}" class="form-control"></td>
            <td><input type="text" value="${item.est_cost}" class="form-control"></td>
            <td><input type="text" value="${item.ffCost}" class="form-control"></td>

            <td>
                <div class="customFileWrapper">
                    <button type="button" class="btn btn-secondary browseBtn">Browse</button>
                    <span class="fileCount">${files.length} file(s) selected</span>
                    <input type="file" class="photoInput d-none"/>
                </div>

                <div class="photoPreview">${photosHtml}</div>
                <small class="text-danger photoError"></small>
            </td>
        </tr>`;

        $('#draftTbody').append(row);

        rowId++;
    });
}



function loadCompleteTable(data) {

    $('#completeTbody').empty();

    data.forEach(item => {

        let photosHtml = '';

        if (item.photos) {
            item.photos.forEach(photo => {
                photosHtml += `
                <img src="getImage?name=${photo.photoUrl}" 
                     class="previewImg"
                     style="width:60px;height:60px;margin:5px;cursor:pointer;">`;
            });
        }

        let row = `
        <tr>
            <td>${item.actId}</td>
            <td>${item.workDesc}</td>
            <td>${item.est_cost}</td>
            <td>${item.ffCost}</td>
            <td>${photosHtml}</td>
        </tr>`;

        $('#completeTbody').append(row);
    });
}


function fixAddRemoveButtons(tbodyId) {

    let rows = $(tbodyId + ' tr');

    rows.find('.addRow')
        .removeClass('btn-success addRow')
        .addClass('btn-danger removeRow')
        .text('-');

    rows.last().find('.removeRow')
        .removeClass('btn-danger removeRow')
        .addClass('btn-success addRow')
        .text('+');
}

function isDuplicateFile(file) {

    for (let [key, files] of rowFilesMap.entries()) {

        for (let f of files) {
            if (f.name === file.name && f.size === file.size) {
                return true;
            }
        }
    }

    return false;
}

$(document).on('click', '.deletePhoto', function () {

    let row = $(this).closest('tr');
    let rowId = row.data('row-id');
    let index = $(this).data('index');

    let files = rowFilesMap.get(rowId) || [];

    let removedFile = files[index];

    if (removedFile) {
        let actualName = getActualFileName(removedFile.name);
        globalImageSet.delete(actualName);
    }

    files.splice(index, 1);
    rowFilesMap.set(rowId, files);

    renderPreview(row, files);
});
	
	$(document).on('click', '.previewImg', function () {
    let src = $(this).attr('src');
    $('#modalImg').attr('src', src);
    $('#imgModal').modal('show');
});
	
	function renderPreview(row, files) {

    let previewDiv = row.find('.photoPreview');
    previewDiv.empty();

    files.forEach((file, index) => {

        let imgSrc = "";

        // ✅ Existing image (from DB)
        if (file.existing) {
            imgSrc = "getImage?name=" + file.name;
        } 
        // ✅ New uploaded image
        else {
            imgSrc = URL.createObjectURL(file);
        }

        let html = `
        <div style="display:inline-block; position:relative; margin:5px;">
            <img src="${imgSrc}" 
                 class="previewImg"
                 style="width:60px;height:60px;cursor:pointer;">
            
            <span class="deletePhoto" data-index="${index}"
                  style="position:absolute; top:0; right:0; cursor:pointer; color:red;">
                  ❌
            </span>
        </div>`;

        previewDiv.append(html);
    });
}
	
	function getSelectedActivities() {
    let selected = [];

    $('.activityDropdown').each(function () {
        let val = $(this).val();
        if (val) {
            selected.push(val);
        }
    });

    return selected;
}

function getFilteredActivityOptions(currentValue = "") {
    let selectedActivities = getSelectedActivities();
    let options = '<option value="">--Select Activity--</option>';

    activityList.forEach(function (act) {

        let actId = act.id.toString();

        if (!selectedActivities.includes(actId) || actId == currentValue) {

            options += `<option value="${act.id}" 
                ${act.id == currentValue ? 'selected' : ''}>
                ${act.name}
            </option>`;
        }
    });

    return options;
}

$(document).on('click', '.addRow', function () {

    let currentRow = $(this).closest('tr');
    let activity = currentRow.find('.activityDropdown').val();

    // ❌ VALIDATION
    if (!activity) {
        currentRow.find('.activityDropdown').addClass('is-invalid');

        let errorSpan = currentRow.find('.photoError');
        alert("Please select activity first");

        return;
    }

    // remove error if valid
    currentRow.find('.activityDropdown').removeClass('is-invalid');
    currentRow.find('.photoError').text("");

    // 🔢 CHECK MAX ACTIVITIES
    let totalActivities = activityList.length;
    let currentRows = $('#tbodyReport tr').length;

    if (currentRows >= totalActivities) {
        alert("You cannot add more rows than total activities");
        return;
    }

    // ✅ Convert current + to -
    $(this)
        .removeClass('btn-success addRow')
        .addClass('btn-danger removeRow')
        .text('-');

    rowCounter++;

let rowId = "entry_" + rowCounter;

let newRow = `
<tr data-row-id="${rowId}">
        <td>
            <select name="activity[]" class="form-control activityDropdown" style="width:300px;">
                ${getFilteredActivityOptions()}
            </select>
        </td>
        <td><input type="text" name="details[]" class="form-control"/></td>
        <td><input type="text" name="totalest[]" class="form-control"/></td>
        <td><input type="text" name="cost[]" class="form-control"/></td>
        <td>
            <div class="customFileWrapper">
                <button type="button" class="btn btn-secondary browseBtn">Browse</button>
                <span class="fileCount">No file selected</span>
                <input type="file" class="photoInput d-none"/>
            </div>
            <small class="text-danger photoError"></small>
            <div class="photoPreview"></div>
        </td>
        <td>
            <button type="button" class="btn btn-success addRow">+</button>
        </td>
    </tr>`;

    $('#tbodyReport').append(newRow);
});

$(document).on('change', '.activityDropdown', function () {
    $(this).removeClass('is-invalid');

    let row = $(this).closest('tr');
    row.find('.photoError').text("");

    // refresh dropdown filter (your existing logic)
    $('#tbodyReport tr').each(function () {
        let dropdown = $(this).find('.activityDropdown');
        let selectedValue = dropdown.val();
        dropdown.html(getFilteredActivityOptions(selectedValue));
    });
});

$(document).on('click', '.browseBtn', function () {
    $(this).closest('.customFileWrapper').find('.photoInput').click();
});

$(document).on('click', '.removeRow', function () {

    $(this).closest('tr').remove();

    // 👉 Always ensure last row has ADD button
    let lastRow = $('#tbodyReport tr:last');

    // Remove existing buttons
    lastRow.find('td:last').html(`
        <button type="button" class="btn btn-success addRow">+</button>
    `);

    // Refresh dropdown filtering
    $('.activityDropdown').each(function () {
        let selectedValue = $(this).val();
        $(this).html(getFilteredActivityOptions(selectedValue));
    });
});



$(document).on('click', '#draft, #complete', function (e) {

    e.preventDefault();

    let btnId = $(this).attr('id');
    let status = (btnId === 'draft') ? 'D' : 'C';

    let confirmMsg = (status === 'D') 
        ? "Are you sure you want to draft this records?"
        : "Are you sure you want to complete this records?";

    // ================= GET VALUES =================
    let district = $('#district').val();
    let projid = $('#projid').val();
    let panchayat = $('#panchayat').val();

    // ================= VALIDATION (FOR BOTH) =================
    if (!district) {
        alert('Please select District');
        $('#district').focus();
        return false;
    }

    if (!projid) {
        alert('Please select Project');
        $('#projid').focus();
        return false;
    }

    if (!panchayat) {
        alert('Please select Gram Panchayat');
        $('#panchayat').focus();
        return false;
    }

    let isRowValid = true;

    $('#tbodyReport tr').each(function (index) {

        let activity = $(this).find('.activityDropdown');
        let details = $(this).find('input[name="details[]"]');
        let totalest = $(this).find('input[name="totalest[]"]');
        let cost = $(this).find('input[name="cost[]"]');

        if (!activity.val()) {
            alert('Please select Activity in row ' + (index + 1));
            activity.focus();
            isRowValid = false;
            return false;
        }

        if (!details.val().trim()) {
            alert('Please enter Details in row ' + (index + 1));
            details.focus();
            isRowValid = false;
            return false;
        }
        
        if (!totalest.val() || isNaN(totalest.val()) || Number(totalest.val()) <= 0) {
            alert('Please enter Total Estimated Cost in row ' + (index + 1));
            totalest.focus();
            isRowValid = false;
            return false;
        }

        if (!cost.val() || isNaN(cost.val()) || Number(cost.val()) <= 0) {
            alert('Please enter Expenditure in row ' + (index + 1));
            cost.focus();
            isRowValid = false;
            return false;
        }
    });

    if (!isRowValid) return false;

    // ================= CONFIRMATION =================
    if (!confirm(confirmMsg)) {
        return false;
    }

    // ================= FORMDATA =================
    let formData = new FormData();

formData.append("district", district);
formData.append("projid", projid);
formData.append("panchayat", panchayat);
formData.append("status", status);

// ✅ ROW DATA
$('#tbodyReport tr').each(function (index) {

    let activity = $(this).find('.activityDropdown').val();
    let details = $(this).find('input[name="details[]"]').val();
    let totalest = $(this).find('input[name="totalest[]"]').val();
    let cost = $(this).find('input[name="cost[]"]').val();

    formData.append("activity[]", activity);
    formData.append("details[]", details);
    formData.append("totalest[]", totalest);
    formData.append("cost[]", cost);
});

// ✅ PHOTO DATA (SEPARATE LOOP)
rowFilesMap.forEach((files, rowId) => {

    if (rowId === undefined || rowId === null) {
        rowId = 0;  // fallback for first row
    }

    files.forEach((file) => {

        formData.append("photos[]", file);
        formData.append("latitude[]", file.latitude || '');
        formData.append("longitude[]", file.longitude || '');
        formData.append("photoRowIndex[]", rowId);
    });
console.log("Final RowId Sent:", rowId);
});



for (let pair of formData.entries()) {
    console.log(pair[0] + ":", pair[1]);
}

    // ================= AJAX =================
    $.ajax({
        url: "saveflexifund",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,

        error: function (xhr, status, er) {
            console.log(er);
            alert('There is some error, please try again!');
        },

        success: function (data) {
            console.log(data);

            if (data === 'success') {
                alert('Data Saved Successfully!');
                window.location.href = 'flexiFundAtSlna';
            }

            if (data === 'fail') {
                alert('Data not saved!');
            }
        }
    });

});


$(document).on("input", "input[name='totalest[]'], input[name='cost[]']", function () {

    const regex = /^\d*(\.\d{0,4})?$/;

    if (!regex.test(this.value)) {
        this.value = this.dataset.lastValidValue || "";
    } else {
        this.dataset.lastValidValue = this.value;
    }
});
function resetForm() {

    rowFilesMap.clear();

    $('#tbodyReport').html(`
        <tr data-row-id="0">
            <td>
                <select name="activity[]" class="form-control activityDropdown" style="width:300px;">
                    ${getFilteredActivityOptions()}
                </select>
            </td>
            <td><input type="text" name="details[]" class="form-control"/></td>
            <td><input type="text" name="totalest[]" class="form-control" maxlength="10"/></td>
            <td><input type="text" name="cost[]" class="form-control" maxlength="10"/></td>
            <td>
                <div class="customFileWrapper">
                    <button type="button" class="btn btn-secondary browseBtn">Browse</button>
                    <span class="fileCount text-info ml-2">No file selected</span>
                    <input type="file" class="photoInput d-none"/>
                </div>
                <small class="text-danger photoError"></small>
                <div class="photoPreview"></div>
            </td>
            <td>
                <button type="button" class="btn btn-success addRow">+</button>
            </td>
        </tr>
    `);

    rowCounter = 0;
}

function isDuplicateWithExisting(fileName, files) {

    let newName = fileName.split('_').pop(); // get actual name

    return files.some(f => {

        let existingName = f.name || "";

        // for DB images
        if (f.existing) {
            existingName = existingName.split('_').pop();
        }

        return existingName === newName;
    });
}

	
	$(document).on('change', '.photoInput', function () {

    let row = $(this).closest('tr');
    let rowId = row.data('row-id');
    let errorSpan = row.find('.photoError');
    let countLabel = row.find('.fileCount');

    errorSpan.text("");
    let file = this.files[0];
    if (!file) return;

    let fileName = file.name;

    // ================= BASIC VALIDATION =================

    let allowedTypes = ['image/jpeg', 'image/jpg', 'image/png'];

    if (!allowedTypes.includes(file.type)) {
        errorSpan.text("Only JPG, JPEG, PNG files are allowed");
        $(this).val('');
        return;
    }

    if (fileName.includes(" ")) {
        errorSpan.text("File name should not contain spaces");
        $(this).val('');
        return;
    }

    let regex = /^[a-zA-Z0-9._-]+$/;

    if (!regex.test(fileName)) {
        errorSpan.text("File name should not contain special characters");
        $(this).val('');
        return;
    }

    let maxSize = 400 * 1024;

    if (file.size > maxSize) {
        errorSpan.text("Image size must be less than 400 KB");
        $(this).val('');
        return;
    }


    // ================= 🔥 DUPLICATE CHECK (PUT HERE) =================

    let actualName = getActualFileName(file.name);

    if (globalImageSet.has(actualName)) {
        errorSpan.text("This image is already uploaded");
        $(this).val('');
        return;
    }

    // ================= EXIF + SAVE =================

    
    let files = rowFilesMap.get(rowId) || [];
    
if (files.length >= 6) {
    errorSpan.text("Maximum 6 photos allowed");
    $(this).val('');
    return;
}
    let isDuplicate = files.some(f => {
        let existingName = f.existing ? getActualFileName(f.name) : getActualFileName(f.name);
        return existingName === actualName;
    });

    if (isDuplicate) {
        errorSpan.text("Duplicate in same row");
        $(this).val('');
        return;
    }

    EXIF.getData(file, function () {

        let lat = EXIF.getTag(this, "GPSLatitude");
        let lon = EXIF.getTag(this, "GPSLongitude");
        let latRef = EXIF.getTag(this, "GPSLatitudeRef");
        let lonRef = EXIF.getTag(this, "GPSLongitudeRef");

        if (lat && lon) {
            file.latitude = convertDMSToDD(lat, latRef);
            file.longitude = convertDMSToDD(lon, lonRef);
        }

        // ✅ IMPORTANT: ADD TO EXISTING ARRAY (NOT REPLACE)
        files.push(file);

        // ✅ UPDATE MAP
        rowFilesMap.set(rowId, files);

        // ✅ UPDATE GLOBAL SET
        globalImageSet.add(actualName);

        // ✅ UI UPDATE
        countLabel.text(files.length + " file(s) selected");

        renderPreview(row, files);

        errorSpan.text("");
    });

    $(this).val('');
});

$(document).on('click', '#updateDraft, #updateComplete', function (e) {

    e.preventDefault();

    let status = ($(this).attr('id') === 'updateDraft') ? 'D' : 'C';

    let selectedRows = [];

    $('#draftTbody tr').each(function () {

        if ($(this).find('.draftCheckbox').is(':checked')) {
            selectedRows.push($(this));
        }
    });

    if (selectedRows.length === 0) {
        alert("Please select at least one record");
        return;
    }

    let formData = new FormData();

    selectedRows.forEach(function (row) {

        let rowId = $(row).attr('data-db-id'); // 🔥 IMPORTANT: DB ID (not draft_1)

        let activity = row.find('.activityDropdown').val();
        let details = row.find('input[type="text"]').eq(0).val();
        let totalest = row.find('input[type="text"]').eq(1).val();
        let cost = row.find('input[type="text"]').eq(2).val();
alert(rowId);

        formData.append("rowIds[]", rowId);
        formData.append("activity[]", activity);
        formData.append("details[]", details);
        formData.append("totalest[]", totalest);
        formData.append("cost[]", cost);


        let files = rowFilesMap.get(row.data('row-id')) || [];

        files.forEach(file => {
        formData.append("photos[]", file);
        formData.append("photoRowIndex[]", rowId);
    });
    });

    formData.append("status", status);

    $.ajax({
        url: "updateFlexiFund",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,

        success: function (res) {
            if (res === "success") {
                alert("Data updated successfully");
                location.reload();
            } else {
                alert("Issue on updated records");
            }
        }
    });
});

});