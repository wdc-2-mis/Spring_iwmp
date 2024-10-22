/**
 * 
 */

/****************************** State Dropdown change ********************************** */
	$(document).on('change', '#state', function(e) {
		e.preventDefault();
		$stCode=$('#state option:selected').val();
		$.ajax({  
            url:"getDistrictByStateCode",
            type: "post", 
			data:{id:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedDist=$('#distCode').val();
						$ddlDistrict = $('#district');
						$ddlDistrict.empty();
        				$ddlDistrict.append('<option value=""> --Select-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedDist)
							$ddlDistrict.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else
							$ddlDistrict.append('<option value="'+key+'">' +data[key] + '</option>');
							}
							}
	}
	});
		});
		
		/****************************************** District Dropdown change *************************************** */
		$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dCode=$('#district option:selected').val();
		$.ajax({  
            url:"getProjectPhysicalActionPlan",
            type: "post", 
			data:{dCode:$dCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedProject=$('#projId').val();
						$ddlProject = $('#project');
						$ddlProject.empty();
        				$ddlProject.append('<option value=""> --Select-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedProject)
							$ddlProject.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else
							$ddlProject.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
	}
	});
		});