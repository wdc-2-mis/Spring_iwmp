$(function() {
	
	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dcode = $('#district option:selected').val();
		$.ajax({
			url: "getJanbhagidariPratiyogitaProject",
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
	
	
	$('#projid').on('change', function(e) {
		e.preventDefault();
		$pCode=$('#projid option:selected').val();
		$.ajax({  
            url:"getYearForAssetWise",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						
						$ddlYear = $('#year');
						$ddlYear.empty();
        				$ddlYear.append('<option value=""> --Select Financial Year-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							/*if(key==$selectedFYear)
							$ddlYear.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else*/
							$ddlYear.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
	}
	});
});

	
	});