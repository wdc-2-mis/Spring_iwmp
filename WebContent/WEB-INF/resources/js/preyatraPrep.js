$(document).ready(function () {
    function fetchData(selector, url, dataKey, target, defaultText) {
        $(document).on('change', selector, function (e) {
            e.preventDefault();
            let selectedValue = $(this).val();
            if (!selectedValue) return;
            
            $.ajax({
                url: url,
                type: "post",
                data: { [dataKey]: selectedValue },
                error: function (xhr, status, er) {
                    console.log(er);
                },
                success: function (data) {
                    let $targetDropdown = $(target);
                    $targetDropdown.empty();
                    $targetDropdown.append(`<option value=""> -- ${defaultText} -- </option>`);
                    
                    $.each(data, function (key, value) {
                        $targetDropdown.append(`<option value="${value}">${key}</option>`);
                    });
                }
            });
        });
    }

    fetchData('#district', "getWatershedYatraBlock", "stateCode", "#block", "Select Block");
    fetchData('#block', "getWatershedYatraGPs", "blkCode", "#grampan", "Select Gram Panchayat");
    fetchData('#grampan', "getWatershedYatraVillage", "gpscode", "#village", "Select Village");

    fetchData('#district1', "getWatershedYatraBlock1", "stateCode", "#block1", "Select Block");
    fetchData('#block1', "getWatershedYatraGPs1", "blkCode", "#grampan1", "Select Gram Panchayat");
    fetchData('#grampan1', "getWatershedYatraVillage1", "gpscode", "#village1", "Select Village");

    $('#village1').change(function () {
        let villageCode = $(this).val();
        if (!villageCode) return;

        $.ajax({
            url: "getExistingVillageCodes",
            type: "post",
            data: { villageCode: villageCode },
            error: function (xhr, status, er) {
                console.log(er);
            },
            success: function (data) {
                if (data === 'success') {
                    alert('Village already exists. Please select a different village.');
                    $(this).val('');
                }
            }
        });
    });

    

function validatePhoto(input, photoId, maxSizeKB, maxWidth, maxHeight) {
    if (input.files && input.files[0]) {
        let file = input.files[0];
        let fileSizeKB = file.size / 1024;

        // Validate file size
        if (fileSizeKB > maxSizeKB) {
            alert("File size exceeds " + maxSizeKB + " KB. Please upload a smaller image.");
            input.value = "";
            return;
        }

        let img = new Image();
        img.src = URL.createObjectURL(file);
        img.onload = function () {
            // Validate image dimensions
            if (this.width > maxWidth || this.height > maxHeight) {
                alert("Image dimensions should not exceed " + maxWidth + "x" + maxHeight);
                input.value = "";
                return;
            }
        };

        // Extract metadata using EXIF.js
        EXIF.getData(file, function () {
            let latitude = EXIF.getTag(this, "GPSLatitude");
            let longitude = EXIF.getTag(this, "GPSLongitude");
            let dateTimeOriginal = EXIF.getTag(this, "DateTimeOriginal");

            // Convert GPS coordinates to decimal format
            function convertDMSToDD(dms, ref) {
                if (!dms) return null;
                let degrees = dms[0].numerator / dms[0].denominator;
                let minutes = dms[1].numerator / dms[1].denominator / 60;
                let seconds = dms[2].numerator / dms[2].denominator / 3600;
                let decimal = degrees + minutes + seconds;
                return ref === "S" || ref === "W" ? -decimal : decimal;
            }

            let latRef = EXIF.getTag(this, "GPSLatitudeRef") || "N";
            let lngRef = EXIF.getTag(this, "GPSLongitudeRef") || "E";
            let lat = convertDMSToDD(latitude, latRef);
            let lng = convertDMSToDD(longitude, lngRef);

            // Set extracted values in hidden input fields
            document.getElementById(photoId + "_lat").value = lat || "Not Available";
            document.getElementById(photoId + "_lng").value = lng || "Not Available";
            document.getElementById(photoId + "_time").value = dateTimeOriginal || "Not Available";

            console.log("Extracted Data:", { lat, lng, dateTimeOriginal });

            // Check for duplicate photos
            let photo1File = document.getElementById("gramphoto1").files[0];
            let photo2File = document.getElementById("gramphoto2").files[0];
            let photo3File = document.getElementById("pheriphoto1").files[0];
            let photo4File = document.getElementById("pheriphoto2").files[0];

            let duplicateFiles = [];

            function checkDuplicate(photoFile1, photoFile2, inputId) {
                if (photoFile1 && photoFile2 && photoFile1.name === photoFile2.name) {
                    alert(`The same photo cannot be uploaded twice. Please select a different photo.`);
                    duplicateFiles.push(inputId);
                }
            }

            checkDuplicate(photo1File, photo3File, "pheriphoto1");
            checkDuplicate(photo1File, photo2File, "gramphoto2");
            checkDuplicate(photo2File, photo3File, "pheriphoto1");
            checkDuplicate(photo1File, photo4File, "pheriphoto2");
            checkDuplicate(photo2File, photo4File, "pheriphoto2");
            checkDuplicate(photo3File, photo4File, "pheriphoto2");

            // Clear duplicate photo inputs
            duplicateFiles.forEach(id => {
                document.getElementById(id).value = "";
            });

            // Alert if photo does not have longitude and latitude
            if (!lat || !lng) {
                if (!confirm("This photo does not contain longitude and latitude information. Are you sure you want to upload it?")) {
                    input.value = "";
                }
            }
        });
    }
}


// Attach validation to file inputs
$('#gramphoto1, #gramphoto2, #pheriphoto1, #pheriphoto2').change(function () {
    validatePhoto(this, this.id, 300, 300, 400);
});

document.getElementById("grampan").addEventListener("change", function() {
        var gramCode = this.value;
        var preyatra_type = 'gramSabha'
        $.ajax({
            url: 'checkGramPanchayat',
            type: 'POST',
            data: {
                gramCode: gramCode,
                preyatraType:preyatra_type
            },
            success: function(exists) {
                if (exists) {
					alert(' Gram Panchayat already exists. Please select a different  Gram Panchayat. !');
						$("select#grampan")[0].selectedIndex = 0;
					
                }
            }
        });
    });
    
document.getElementById("village1").addEventListener("change", function() {
        var vCode = this.value;
        var preyatra_type = 'prabhatPheri'
        $.ajax({
            url: 'checkVillage',
            type: 'POST',
            data: {
                vCode: vCode,
                preyatraType:preyatra_type
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



