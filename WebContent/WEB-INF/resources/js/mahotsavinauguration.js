$(function() {

	$(document).on('change', '#district', function(e) {
			e.preventDefault();
			$stCode = $('#district option:selected').val();
			$.ajax({
				url: "getWatershedYatraBlock",
				type: "post",
				data: { stateCode: $stCode },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					$selectedDist = $('#district').val();
					$ddlDistrict = $('#block');
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


		$(document).on('change', '#block', function(e) {
			e.preventDefault();
			$villageCode = $('#block option:selected').val();
			$.ajax({
				url: "getExistingBlockInaguraCodes",
				type: "post",
				data: { villageCode: $villageCode },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					if (data === 'success') {
						alert('Block already exists. Please select a different Block. !');
						$("select#block")[0].selectedIndex = 0;

					}

				}
			});
	});
$('#chkSelectAllkd').on('click', function() {
		$chkValue = 0;
		if (this.checked) {
			$('.chkIndividualkd').each(function() {
				this.checked = true;
				$chkValue++;
			});
		}
		else {
			$('.chkIndividualkd').each(function() {
				this.checked = false;
			});
			$chkValue = 0;
		}
	});

	$(document).on('click', '#delete', function(e) {
		e.preventDefault();
		var finalAssetid = new Array();

		$('.chkIndividualkd').each(function() {
			if ($(this).prop('checked')) {
				finalAssetid.push($(this).val());
			}
		});

		if (confirm("Do you want to Delete ?")) {
			$.ajax({
				url: "deleteMahotsavInaugurationDetails",
				type: "post",
				data: { assetid: finalAssetid.toString() },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						alert('Deleted Successfully.');
						window.location.href = 'registerInauguration';
					}
					else {
						alert('Please check at least One Check Box, Data not Delete!');
						window.location.href = 'registerInauguration';
					}
				}
			});
		}
	});



	$(document).on('click', '#complete', function(e) {
		e.preventDefault();
		var finalAssetid = new Array();

		$('.chkIndividualkd').each(function() {
			if ($(this).prop('checked')) {
				finalAssetid.push($(this).val());
			}
		});

		if (confirm("Do you want to Complete ?")) {
			$.ajax({
				url: "completeMahotsavInaugurationDetails",
				type: "post",
				data: { assetid: finalAssetid.toString() },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						alert('Complete Successfully.');
						window.location.href = 'registerInauguration';
					}
					else {
						alert('Please check at least One Check Box, Data not Complete!');
						window.location.href = 'registerInauguration';
					}
				}
			});
		}
	});
	
	
	
});	
