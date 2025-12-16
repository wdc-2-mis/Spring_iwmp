
	
	$(document).on( 'change', '#project', function (e) {
		$('#loading').show();
		$projId = $('#project').val();
//		alert('check '+$projId);
			$.ajax({
				url: "getBlockListFrmProj",
				type: "post",
				data: {projid: $projId},
				error: function(xhr, status, er) {
					console.log(er);
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					$ddlblock = $('#block');
					$ddlblock.empty();
					$ddlblock.append("<option value=''>---Select Block---</option>");
					if (Object.keys(data).length > 0) {
						for (var key in data) {
							if (data.hasOwnProperty(key)) {
								$ddlblock.append("<option value='" + key + "'>" + data[key] + "</option>");
							}
						}
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
	


$(document).on('click', '.showImage', function(e) {
			
			$waterId = e.target.getAttribute('data-id');
			$.ajax({
				type: 'POST',
				url: "getImageMahotsavProjLvlId",
				data: { waterId: $waterId },
				error: function(xhr, status, er) {
					console.log(er);
				},
				success: function(data) {
//					var imageContainer = $('.image-container');
//					imageContainer.empty();
					let list = '<ul>';
					for (let i = 0; i < data.length; i++) {
						if (data[i] != null) 
						{
						//PRD
//							list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/PRD/mahotsavdoc/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
						//TEST
						//	list += '<li><img src="https://wdcpmksy.dolr.gov.in/filepath/TESTING/mahotsavdoc/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';
						//Local
							list += '<li><img src="resources/images/projectLevel/' + data[i] + '" alt="Image" onclick="openLargeImage(\'' + data[i] + '\', ' + i + ', ' + data.length + ')" /></li>';

						}
					}
					list += '</ul>';
					document.getElementById('imageList').innerHTML = list;
					document.getElementById('imagePopup').style.display = 'block';
				}
			});
		});