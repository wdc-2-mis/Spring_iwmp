$(function() {
	
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
				url: "deleteMahotsavProjLvlDetails",
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
						window.location.href = 'getWatershedMahotsavAtProjLvl';
					}
					else {
						alert('Please check at least One Check Box, Data not Delete!');
						window.location.href = 'getWatershedMahotsavAtProjLvl';
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
				url: "completeMahotsavProjLvlDetails",
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
						window.location.href = 'getWatershedMahotsavAtProjLvl';
					}
					else {
						alert('Please check at least One Check Box, Data not Complete!');
						window.location.href = 'getWatershedMahotsavAtProjLvl';
					}
				}
			});
		}
	});
	
});