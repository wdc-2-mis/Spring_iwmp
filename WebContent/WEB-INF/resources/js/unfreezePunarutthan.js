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
		
		$(document).on('click', '#unfreeze', function(e) {
				e.preventDefault();
				var finalAssetid = new Array();
				$selecttype = $('#typeutthan').val();
				$('.chkIndividualkd').each(function() {
					if ($(this).prop('checked')) {
						finalAssetid.push($(this).val());
					}
				});

				if (confirm("Do you want to Unfreeze ?")) {  
					$.ajax({
						url: "unfreezeWatershedPunarutthan",
						type: "post",
						data: { assetid: finalAssetid.toString(), typeutthan:$selecttype },
						error: function(xhr, status, er) {
							console.log(er);
						},
						success: function(data) {
							console.log(data);
							$('#loading').hide();
							if (data === 'success') {
								alert('Unfreeze Successfully.');
								window.location.href = 'unfreezePunarutthan';
							}
							else {
								alert('Data not Unfreeze!');
								window.location.href = 'unfreezePunarutthan';
							}
						}
					});
				}
			});
	
	
	
});		