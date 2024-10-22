
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
	$(document).on('click', '#btnGetReport', function(e) {
		e.preventDefault();
		$state=$('#state option:selected').val();
		$district=$('#district option:selected').val();
		$project=$('#project option:selected').val();
		$.ajax({  
            url:"getConvergenceWorkDetails",
            type: "post", 
			data:{state:$state,district:$district,project:$project}, 
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
	}
	
	});
	
	/******************************************** Onclick ************************************************ */
	$('#convergedWorksRptTbody').on( 'click', 'a.remain', function (e) {
		
		var del = e.target.getAttribute('data-id');
		$.ajax({  
            url:"getRemainingConvergedWorks",
            type: "post",  
            data: {dcode:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(ConvergenceWorksBean) {
            console.log(ConvergenceWorksBean);
            var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('List of Remaining Convergence Work Details');
						var i=1;
						if(Object.keys(ConvergenceWorksBean).length>0){
							for ( var key in ConvergenceWorksBean) {
		if (ConvergenceWorksBean.hasOwnProperty(key)) {
			
			tblData+="<tr><td>"+i+"</td><td>"+ConvergenceWorksBean[key].proj_name+"</td><td>"+ConvergenceWorksBean[key].headname+"</td><td>"+ConvergenceWorksBean[key].actname+"</td><td>"+ConvergenceWorksBean[key].workcode+"</td></tr>";
			i++;
			}
			}
						}
							
							else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
           $('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S.No.</th><th>Project Name</th><th>Head Name</th><th>Activity Name</th><th>Work Code</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
            
            });
		});