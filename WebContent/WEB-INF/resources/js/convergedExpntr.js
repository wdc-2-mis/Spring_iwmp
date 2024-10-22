
$(function(){

	/****************************** State Dropdown change ********************************** */
	$(document).on('change', '#state', function(e) {
	//$('#state').on('change', function(e){
		e.preventDefault();
		$stcd=$('#state option:selected').val();
		$.ajax({  
            url:"getDistrictDataNew",
            type: "post", 
			data:{stateCode:$stcd}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				$selectedDist=$('#dcode').val();
				$ddlDistrict = $('#district');
				$ddlDistrict.empty();
        		$ddlDistrict.append('<option value="0"> --All-- </option>');
				for ( var key in data) {
					if (data.hasOwnProperty(key)) {
						if(data[key]==$selectedDist)
							$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
						else
							$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
					}
				}
			}
		});
	
		var selectedState = $('#state option:selected').val();
		if(selectedState==0){
			$.ajax({  
            	url:"getProjectPhysicalActionPlan",
           		type: "post", 
				data:{dCode:0}, 
            	error:function(xhr,status,er){
                	console.log(er);
            	},
            	success:function(data) {
					$selectedProject=$('#projid').val();
					$ddlProject = $('#project');
					$ddlProject.empty();
        			$ddlProject.append('<option value="0"> --All-- </option>');
				}
			});
		}
	});
		
	/****************************************** District Dropdown change *************************************** */
	$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dcode=$('#district option:selected').val();
		$.ajax({  
            url:"getProjectPhysicalActionPlan",
            type: "post", 
			data:{dCode:$dcode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				$selectedProject=$('#projid').val();
				$ddlProject = $('#project');
				$ddlProject.empty();
        		$ddlProject.append('<option value="0"> --All-- </option>');
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
		
		/************************************************** Project Dropdown change ************************************ */
//		$(document).on('change', '#project', function(e) {
//		e.preventDefault();
//		$projid=$('#project option:selected').val();
//		$.ajax({  
//            url:"getAllSchemes",
//            type: "post", 
//			data:{pCode:$projid}, 
//            error:function(xhr,status,er){
//                console.log(er);
//            },
//            success:function(data) {
//						$selectedScheme=$('#sid').val();
//						$ddlscheme = $('#scheme');
//						$ddlscheme.empty();
//        				$ddlscheme.append('<option value="0"> --All-- </option>');
//						 for ( var key in data) {
//						    if (data.hasOwnProperty(key)) {
//							if(key==$selectedScheme)
//							$ddlscheme.append('<option value="'+key+'" selected>' +data[key] + '</option>');
//							else
//							$ddlscheme.append('<option value='+key+'>' +data[key] + '</option>');
//							}
//							}
//	}
//	});
//		});
		
		/************************************************** Scheme Dropdown change ************************************ */
//		$(document).on('change', '#scheme', function(e) {
//		e.preventDefault();
//		$sid=$('#scheme option:selected').val();
//		$.ajax({  
//            url:"getYearForPhysicalActionAchievementReport",
//            type: "post", 
//			data:{sCode:$sid}, 
//            error:function(xhr,status,er){
//                console.log(er);
//            },
//            success:function(data) {
//						$selectedFYear=$('#finyr').val();
//						$ddlFinYear = $('#finYear');
//						$ddlFinYear.empty();
//        				$ddlFinYear.append('<option value="0"> --All-- </option>');
//						 for ( var key in data) {
//						    if (data.hasOwnProperty(key)) {
//							if(key==$selectedFYear)
//							$ddlFinYear.append('<option value="'+key+'" selected>' +data[key] + '</option>');
//							else
//							$ddlFinYear.append('<option value='+key+'>' +data[key] + '</option>');
//							}
//							}
//	}
//	});
//		});
		
		/*************************************************** From Year Change ********************************************** */
	$(document).on('click', '#btnGetReport', function(e) {
		e.preventDefault();
		$state=$('#state option:selected').val();
		$district=$('#district option:selected').val();
		$project=$('#project option:selected').val();
		$finYear=$('#finyear option:selected').val();
		$scheme=$('#scheme option:selected').val();
		$.ajax({  
            url:"getConvergedExpndtrReport",
            type: "post", 
			data:{state:$state,district:$district,project:$project,scheme:$scheme,finYear:$finYear}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
			}
		});
	});
	/********************************************** Page Load Script ************************************* */	
	var selectedState = $('#state option:selected').val();
	if(selectedState!=null && selectedState!='' && selectedState>0){
		$("#state").trigger("change");
		$('#district option:selected').val($('#dcode').val());
		$("#district").trigger("change");
		$('#project option:selected').val($('#projid').val());
		$("#project").trigger("change");
		$('#scheme option:selected').val($('#sid').val());
		$("#scheme").trigger("change");
		$('#finYear option:selected').val($('#finyr').val());
		$("#finYear").trigger("change");
	}
	
	});
	
	