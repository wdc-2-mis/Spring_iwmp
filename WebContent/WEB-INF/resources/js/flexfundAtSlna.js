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
	rowFilesMap.clear();
    rowCounter = 0;
    
    $('#draftTbody').empty();
    $('#completeTbody').empty();
    
    $('#draftSection').hide();
    $('#completeSection').hide();
	
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
                    
                    <span class="deleteDraftPhoto"
                          data-index="${files.length - 1}"
                          data-id="${photo.photoId}"
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
                    ${getFilteredActivityOptions(item.actId.toString(), "draft", rowKey)}
                </select>
            </td>

            <td><input type="text" value="${item.workDesc}" class="form-control"></td>
            <td><input type="text" name="totalest[]" value="${item.est_cost}" class="form-control"></td>
            <td><input type="text" name="cost[]" value="${item.ffCost}" class="form-control"></td>

            <td>
                <div class="customFileWrapper">
                    <button type="button" class="btn btn-secondary browseDraftBtn">Browse</button>
                    <span class="fileCount">${files.length} file(s) selected</span>
                    <input type="file" class="photoDraftInput d-none"/>
                </div>

                <div class="photoPreview">${photosHtml}</div>
                <small class="text-danger photoError"></small>
            </td>
           <td> <textarea name="remark[]" autocomplete="off" rows="2" cols="22" maxlength="200">${item.remark}</textarea> </td>
        </tr>`;

        $('#draftTbody').append(row);

        rowId++;
    });
    refreshAllDropdowns();
}


$(document).on('click', '.deleteDraftPhoto', function () {

    let row = $(this).closest('tr');
    let rowId = row.data('row-id');
    let photoId = $(this).data('id');

    let files = rowFilesMap.get(rowId) || [];

    if (!confirm("Delete this photo permanently?")) return;

    $.ajax({
        url: "deleteFlexiFundPhoto",
        type: "POST",
        data: { photoId: photoId },

        success: function (res) {

            if (res === "success") {

                // ✅ REMOVE USING photoId (NOT INDEX)
                files = files.filter(f => f.photoId !== photoId);

                // ✅ Update global set
                globalImageSet.clear();
                files.forEach(f => {
                    let actual = getActualFileName(f.name);
                    globalImageSet.add(actual);
                });

                // ✅ Update map
                rowFilesMap.set(rowId, files);

                // ✅ Re-render properly
                renderPreview(row, files);

                alert("Photo deleted successfully");
                location.reload();

            } else {
                alert("Failed to delete photo");
            }
        }
    });
});

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
            <td>${item.actName}</td>
            <td>${item.workDesc}</td>
            <td>${item.est_cost}</td>
            <td>${item.ffCost}</td>
            <td>${photosHtml}</td>
            <td>${item.remark}</td>
        </tr>`;

        $('#completeTbody').append(row);
    });
}

function refreshAllDropdowns() {
    // Refresh entry row dropdowns
    $('#tbodyReport tr').each(function() {
        let dropdown = $(this).find('.activityDropdown');
        let dropdownRowId = $(this).data('row-id');
        let selectedValue = dropdown.val();
        if (dropdown.length) {
            dropdown.html(getFilteredActivityOptions(selectedValue, "entry", dropdownRowId));
        }
    });
    
    // Refresh draft row dropdowns
    $('#draftTbody tr').each(function() {
        let dropdown = $(this).find('.activityDropdown');
        let dropdownRowId = $(this).data('row-id');
        let selectedValue = dropdown.val();
        if (dropdown.length) {
            dropdown.html(getFilteredActivityOptions(selectedValue, "draft", dropdownRowId));
        }
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
    let countLabel = row.find('.fileCount');
    countLabel.text(files.length + " file(s) selected");
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

function getFilteredActivityOptions(currentValue = "", context = "entry", currentRowId = null) {
    
    let usedActivities = new Set();
    
    // ===== 1. CHECK COMPLETE ROWS (Always excluded from everywhere) =====
    $('#completeTbody tr').each(function() {
        let activityName = $(this).find('td:first').text().trim();
        if (activityName && activityName !== "") {
            let foundAct = activityList.find(act => act.name === activityName);
            if (foundAct) {
                usedActivities.add(foundAct.id.toString());
            }
        }
    });
    
    // ===== 2. CHECK DRAFT ROWS =====
    $('#draftTbody tr').each(function() {
        let activityVal = $(this).find('.activityDropdown').val();
        let rowId = $(this).data('row-id');
        
        // If we're editing a draft row, skip its own value
        if (context === "draft" && currentRowId === rowId) {
            return;
        }
        
        if (activityVal && activityVal !== "") {
            usedActivities.add(activityVal.toString());
        }
    });
    
    // ===== 3. CHECK NEW ENTRY ROWS (Only for new entry context) =====
    if (context === "entry") {
        $('#tbodyReport tr').each(function() {
            let activityVal = $(this).find('.activityDropdown').val();
            let rowId = $(this).data('row-id');
            
            // Skip current row
            if (currentRowId === rowId) {
                return;
            }
            
            if (activityVal && activityVal !== "") {
                usedActivities.add(activityVal.toString());
            }
        });
    }
    
    // Generate dropdown options
    let options = '<option value="">--Select Activity--</option>';
    
    activityList.forEach(function (act) {
        let actId = act.id.toString();
        
        // Show if not used OR it's the current value
        if (!usedActivities.has(actId) || actId === currentValue) {
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

    // ❌ Activity not selected
    if (!activity) {
        currentRow.find('.activityDropdown').addClass('is-invalid');
        alert("Please select activity first");
        return;
    }

    currentRow.find('.activityDropdown').removeClass('is-invalid');
    currentRow.find('.photoError').text("");

    let availableActivities = getAvailableActivityCount();
    let currentRows = $('#tbodyReport tr').length;

   if (currentRows >= availableActivities) {
    alert("Only " + availableActivities + " activity(s) left to add");
    return;
}

    // 🔥 NEW VALIDATION (IMPORTANT)
    let remainingActivities = getRemainingActivitiesCount();


    if (remainingActivities <= 1) {
        alert("Only one activity left. Please use current row instead of adding new.");
        return;
    }

    // ✅ Convert + to -
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
        <td><textarea name="remark[]" rows="2" cols="22" maxlength="200"></textarea></td>
        <td>
            <button type="button" class="btn btn-success addRow">+</button>
        </td>
    </tr>`;

    $('#tbodyReport').append(newRow);
});

function getRemainingActivitiesCount() {
    let selected = [];

    $('#tbodyReport .activityDropdown').each(function () {
        let val = $(this).val();
        if (val) {
            selected.push(val);
        }
    });

    return activityList.length - selected.length;
}

function getAvailableActivityCount() {
    let selectedActivities = getSelectedActivities();

    let count = 0;

    activityList.forEach(function (act) {
        let actId = act.id.toString();

        if (!selectedActivities.includes(actId)) {
            count++;
        }
    });

    return count;
}

$(document).on('change', '.activityDropdown', function () {
    $(this).removeClass('is-invalid');
    
    let row = $(this).closest('tr');
    let currentRowId = row.data('row-id');
    let currentValue = $(this).val();
    
    // Determine if this is entry or draft row
    let isDraftRow = currentRowId && currentRowId.toString().startsWith('draft_');
    let context = isDraftRow ? "draft" : "entry";
    
    row.find('.photoError').text("");
    
    // Refresh ALL entry row dropdowns
    $('#tbodyReport tr').each(function() {
        let dropdown = $(this).find('.activityDropdown');
        let dropdownRowId = $(this).data('row-id');
        let selectedValue = dropdown.val();
        
        dropdown.html(getFilteredActivityOptions(selectedValue, "entry", dropdownRowId));
    });
    
    // Refresh ALL draft row dropdowns
    $('#draftTbody tr').each(function() {
        let dropdown = $(this).find('.activityDropdown');
        let dropdownRowId = $(this).data('row-id');
        let selectedValue = dropdown.val();
        
        dropdown.html(getFilteredActivityOptions(selectedValue, "draft", dropdownRowId));
    });
});

$(document).on('click', '.browseBtn', function () {
    $(this).closest('.customFileWrapper').find('.photoInput').click();
});

$(document).on('click', '.browseDraftBtn', function () {
    $(this).closest('.customFileWrapper').find('.photoDraftInput').click();
});

$(document).on('click', '.removeRow', function () {
    let currentRow = $(this).closest('tr');
    currentRow.remove();

    // Always ensure last row has ADD button
    let lastRow = $('#tbodyReport tr:last');
    if (lastRow.length) {
        lastRow.find('td:last').html(`
            <button type="button" class="btn btn-success addRow">+</button>
        `);
    }

    // Refresh ALL dropdowns after row removal
    // Refresh entry row dropdowns
    $('#tbodyReport tr').each(function() {
        let dropdown = $(this).find('.activityDropdown');
        let dropdownRowId = $(this).data('row-id');
        let selectedValue = dropdown.val();
        dropdown.html(getFilteredActivityOptions(selectedValue, "entry", dropdownRowId));
    });
    
    // Refresh draft row dropdowns
    $('#draftTbody tr').each(function() {
        let dropdown = $(this).find('.activityDropdown');
        let dropdownRowId = $(this).data('row-id');
        let selectedValue = dropdown.val();
        dropdown.html(getFilteredActivityOptions(selectedValue, "draft", dropdownRowId));
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
        let remark = $(this).find('input[name="remark[]"]');

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
    let remark = $(this).find('textarea[name="remark[]"]').val() || '';

    formData.append("activity[]", activity);
    formData.append("details[]", details);
    formData.append("totalest[]", totalest);
    formData.append("cost[]", cost);
    formData.append("remark[]", remark);
});

// ✅ PHOTO DATA (SEPARATE LOOP)
rowFilesMap.forEach((files, rowId) => {

    let numericRowId = rowId;
    if (typeof rowId === 'string' && rowId.startsWith('entry_')) {
        numericRowId = parseInt(rowId.split('_')[1]);
        console.log("Converting rowId:", rowId, "->", numericRowId);
    } else if (rowId === undefined || rowId === null) {
        numericRowId = 0;
    }

    files.forEach((file) => {
        formData.append("photos[]", file);
        formData.append("latitude[]", file.latitude || '');
        formData.append("longitude[]", file.longitude || '');
        formData.append("photoRowIndex[]", numericRowId);  // Use numericRowId here
    });
    console.log("Final RowId Sent:", numericRowId);
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
    globalImageSet.clear();

    $('#tbodyReport').html(`
        <tr data-row-id="0">
            <td>
                <select name="activity[]" class="form-control activityDropdown" style="width:250px;">
                    ${getFilteredActivityOptions("", "entry", 0)}
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
            <td><textarea id="remark" name="remark[]" autocomplete="off" rows="2" cols="22" maxlength="200"></textarea></td>
            <td>
                <button type="button" class="btn btn-success addRow">+</button>
            </td>
        </tr>
    `);

    rowCounter = 0;
    
    setTimeout(function() {
        refreshAllDropdowns();
    }, 100);
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

$(document).on('change', '.photoDraftInput', function () {

    let row = $(this).closest('tr');
    let rowId = row.data('row-id');
    let dbId = row.data('db-id'); // 🔥 IMPORTANT (flexi_fund_id)
    let errorSpan = row.find('.photoError');
    let countLabel = row.find('.fileCount');


    errorSpan.text("");

    let file = this.files[0];
    if (!file) return;

    let fileName = file.name;

    // ================= VALIDATION =================

    let allowedTypes = ['image/jpeg', 'image/jpg', 'image/png'];

    if (!allowedTypes.includes(file.type)) {
        errorSpan.text("Only JPG, JPEG, PNG allowed");
        $(this).val('');
        return;
    }

    if (fileName.includes(" ")) {
        errorSpan.text("No spaces allowed in filename");
        $(this).val('');
        return;
    }

    let regex = /^[a-zA-Z0-9._-]+$/;

    if (!regex.test(fileName)) {
        errorSpan.text("Invalid filename");
        $(this).val('');
        return;
    }

    if (file.size > 400 * 1024) {
        errorSpan.text("Max size 400KB");
        $(this).val('');
        return;
    }

    let actualName = getActualFileName(file.name);

    if (globalImageSet.has(actualName)) {
        errorSpan.text("Image already exists");
        $(this).val('');
        return;
    }

    let files = rowFilesMap.get(rowId) || [];

// 🔥 COUNT EXISTING IMAGES FROM UI
let existingCount = row.find('.photoPreview img').length;

// 🔥 TOTAL COUNT
let totalPhotos = files.length + existingCount;

if (totalPhotos >= 6) {
    errorSpan.text("Maximum 6 photos allowed");
    $(this).val('');
    return;
}

    let isDuplicate = files.some(f =>
        getActualFileName(f.name) === actualName
    );

    if (isDuplicate) {
        errorSpan.text("Duplicate in same row");
        $(this).val('');
        return;
    }

    // ================= EXIF =================

    EXIF.getData(file, function () {

        let lat = EXIF.getTag(this, "GPSLatitude");
        let lon = EXIF.getTag(this, "GPSLongitude");
        let latRef = EXIF.getTag(this, "GPSLatitudeRef");
        let lonRef = EXIF.getTag(this, "GPSLongitudeRef");

        let latitude = "", longitude = "";

        if (lat && lon) {
            latitude = convertDMSToDD(lat, latRef);
            longitude = convertDMSToDD(lon, lonRef);
        }

        // ================= AJAX UPLOAD =================

        let formData = new FormData();
        formData.append("photo", file);
        formData.append("flexiFundId", dbId);
        formData.append("latitude", latitude);
        formData.append("longitude", longitude);

        $.ajax({
            url: "uploadFlexiFundPhoto",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,

            success: function (res) {

    if (res && res.photoId) {

        // ✅ Add to map
        let newFile = {
            name: res.photoUrl,
            existing: true,
            photoId: res.photoId
        };

        files.push(newFile);
        rowFilesMap.set(rowId, files);

        // ✅ Global set
        globalImageSet.add(getActualFileName(res.photoUrl));

        // ✅ UI update
        countLabel.text(files.length + " file(s) selected");

        renderPreview(row, files);

        errorSpan.text("");

        // 🔥 NEW PART
        alert("Photo added successfully");

        // small delay (better UX)
        setTimeout(function () {
            window.location.href = 'flexiFundAtSlna';
        }, 500);

    } else {
        errorSpan.text("Upload failed");
    }
}

            
        });

    });

    $(this).val('');
});

$(document).on('click', '#updateDraft, #updateComplete', function (e) {

    e.preventDefault();
    let isDraft = $(this).attr('id') === 'updateDraft';
    let status = ($(this).attr('id') === 'updateDraft') ? 'D' : 'C';
    let actionText = isDraft ? "update this as a Draft?" : "mark this as Complete?";
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

    if (!confirm("Do you want to " + actionText)) {
        return; // User clicked 'Cancel', so stop the function
    }
    let formData = new FormData();

    selectedRows.forEach(function (row) {

        let rowId = $(row).attr('data-db-id'); // 🔥 IMPORTANT: DB ID (not draft_1)

        let activity = row.find('.activityDropdown').val();
        let details = row.find('input[type="text"]').eq(0).val();
        let totalest = row.find('input[type="text"]').eq(1).val();
        let cost = row.find('input[type="text"]').eq(2).val();
        let remark = row.find('textarea[name="remark[]"]').val() || ''; 

        formData.append("rowIds[]", rowId);
        formData.append("activity[]", activity);
        formData.append("details[]", details);
        formData.append("totalest[]", totalest);
        formData.append("cost[]", cost);
        formData.append("remark[]", remark);


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