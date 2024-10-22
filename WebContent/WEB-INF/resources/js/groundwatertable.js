$( document ).ready(function(){
	
	/******************************** Group Type Change Script ************************************ */
	/*$(document).on( 'change', '#groupType', function (e) {
		
	//alert(''+$('#groupType option:selected').val());
		if($('#groupType option:selected').val()==='project')
		{
			$('.year').removeClass('d-none');
		}
		else{
		
			$('.year').addClass('d-none');
		}
	});*/
	
	
	$(document).on( 'change', '#groupType', function (e) {
		e.preventDefault();
		$projectId = $('#project').val();
		$gType = $(this).val();
		
		if($('#groupType option:selected').val()==='project')
		{
			$('.year').removeClass('d-none');
		}
		else{
		
			$('.year').addClass('d-none');
			$.ajax({  
	            url:"getGroundWaterTableDraft",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:0}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
			     success:function(data) {
				console.log(data);
			
				if(Object.keys(data).length>0){
					console.log(data);
					for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								
							//	alert(data[key].proj_id);
								$('#gwtid').val(data[key].groundwater_id); 
								$('#gwtdtlid').val(data[key].groundwater_detail_id); 
								$('#preMonsoon').val(data[key].depth_premonsoon); 
								$('#postMonsoon').val(data[key].depth_postmonsoon); 
								
								}
							}
					}
			
				}
				
				});

		}
		
		
		
				
		});

	
	/******************************* Year Change Script************************************/
	$(document).on( 'change', '.year', function (e) {
		e.preventDefault();
		$projectId = $('#project').val();
		$gType = $('#groupType').val();
		$year = $(this).val();
		$('#preMonsoon').val('');
		$('#postMonsoon').val('');
		
			$.ajax({  
	            url:"getGroundWaterTableDraft",
	            type: "post", 
				data:{project:$projectId,group:$gType,fyear:$year}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
			     success:function(data) {
				console.log(data);
			
				if(Object.keys(data).length>0){
					console.log(data);
					for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								
							//	alert(data[key].proj_id);
								$('#gwtid').val(data[key].groundwater_id); 
								$('#gwtdtlid').val(data[key].groundwater_detail_id); 
								$('#preMonsoon').val(data[key].depth_premonsoon); 
								$('#postMonsoon').val(data[key].depth_postmonsoon); 
								
								$('#ph').val(data[key].ph);
								$('#alkalinity').val(data[key].alkalinity);
								$('#hardness').val(data[key].hardness);
								$('#calcium').val(data[key].calcium);
								$('#chloride').val(data[key].chloride);
								$('#nitrate').val(data[key].nitrate);
								$('#dissolved').val(data[key].dissolved_solid);
								$('#fluoride').val(data[key].fluoride);
								
								
								}
							}
					}
			
				}
				
				});

		
		
		
				
		});

	
	
	
	/******************************************** Save Button Click ******************************** */
	$(document).on( 'click', '#btnSave', function (e) {
		e.preventDefault();
		
		$atline1 = $('#groupType option:selected').val();
		$project1 = $('#project option:selected').val();
		$fyear1 = $('#fyear option:selected').val();
		$preMonsoon1 = $('#preMonsoon').val();
		$postMonsoon1 = $('#postMonsoon').val();
		$gwtid = $('#gwtid').val()===''?0: $('#gwtid').val();
		$gwtdtlid = $('#gwtdtlid').val()===''?0: $('#gwtdtlid').val();
		
		$ph1 = $('#ph').val();
		$alkalinity1 = $('#alkalinity').val();
		$hardness1 = $('#hardness').val();
		$calcium1 = $('#calcium').val();
		$chloride1 = $('#chloride').val();
		$nitrate1 = $('#nitrate').val();
		$dissolved1 = $('#dissolved').val();
		$fluoride1 = $('#fluoride').val();
	//	alert($fluoride1+' kd '+$calcium1);
			if($('#project option:selected').val()==='' || typeof $('#project option:selected').val()==='undefined')
			{
				alert('Please select project !');
				$('#project').focus();
				return false;
			}
			if($('#groupType option:selected').val()==='' || typeof $('#groupType option:selected').val()==='undefined')
			{
				alert('Please select at the time of !');
				$('#groupType').focus();
				return false;
			}
			if($('#groupType option:selected').val()==='project' )
			{
				if($('#fyear option:selected').val()==='' || typeof $('#fyear option:selected').val()==='undefined')
				{
					alert('Please select Financial year !');
					$('#fyear').focus();
					return false;
				}
			}
			if($('#preMonsoon').val()==='')
			{
				alert('Please enter Pre Monsoon !');
				$('#preMonsoon').focus();
				return false;
			}
			/*if($('#postMonsoon').val()==='')
			{
				alert('Please enter Post Monsoon !');
				$('#postMonsoon').focus();
				return false;
			}*/
			
			//alert('select subject ='+$('#subject'+$i+' option:selected').val());
	
		$.ajax({  
            url:"saveGroundWaterTable",
            type: "post", 
			data:{atline:$atline1, project:$project1, fyear:$fyear1, preMonsoon:$preMonsoon1, postMonsoon:$postMonsoon1, gwtid1:$gwtid, gwtdtlid1:$gwtdtlid, ph:$ph1, alkalinity:$alkalinity1, hardness:$hardness1, calcium:$calcium1, chloride:$chloride1, nitrate:$nitrate1, dissolved:$dissolved1, fluoride:$fluoride1}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
					console.log(data);
					if(data==='success')
					{
						successAlert('Data Saved Successfully !');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							window.location.href='groundWaterTable';
							});  
							$(".close").click(function(){
							$('#popup').modal('hide');
							window.location.href='groundWaterTable';
							});
					}
					if(data==='fail'){
						successAlert('Ground water table data already completed please select other option');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							window.location.href='groundWaterTable';
							});  
							$(".close").click(function(){
							$('#popup').modal('hide');
							window.location.href='groundWaterTable';
							});
					}
            }
		});
		
		
	});
	
	
	
	/****************************** State Dropdown change ********************************** */
	$(document).on('change', '#state', function(e) {
	//$('#state').on('change', function(e){
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
						if($selectedDist==0){
							$ddlDistrict.append('<option value="0" selected> --SelectAll-- </option>');
						}
						else{
							$ddlDistrict.append('<option value="0" > --SelectAll-- </option>');
						}
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
		$stCode=$('#state option:selected').val();
		$dCode=$('#district option:selected').val();
		$.ajax({  
	        url:"getProjBystCodedCode",
	        type: "post", 
			data:{stCode:$stCode,dCode:$dCode}, 
	        error:function(xhr,status,er){
	            console.log(er);
	        },
	        success:function(data) {
						$selectedProject=$('#projId').val();
						$ddlProject = $('#project');
						$ddlProject.empty();
//	    				$ddlProject.append('<option value="0"> --All-- </option>');
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
	
	
	
	var selectedState = $('#state option:selected').val();
	if(selectedState!=null && selectedState!='' && selectedState>0){
	$("#state").trigger("change");
	$('#district option:selected').val($('#distCode').val());
	$("#district").trigger("change");
	$('#project option:selected').val($('#projId').val());
	$("#project").trigger("change");
	}
	
	
	
	
	
	
	
	
	
	
});