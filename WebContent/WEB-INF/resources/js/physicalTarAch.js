
$(function(){

	/****************************** State Dropdown change ********************************** */
	$(document).on('change', '#state', function(e) {
	//$('#state').on('change', function(e){
		e.preventDefault();
		$stCode=$('#state option:selected').val();
		$.ajax({  
            url:"getDistrictDataNew",
            type: "post", 
			data:{stateCode:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedDist=$('#distCode').val();
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
		$(document).on('change', '#project', function(e) {
		e.preventDefault();
		$pCode=$('#project option:selected').val();
		$.ajax({  
            url:"getYearForPhysicalActionAchievementReport",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedFYear=$('#fYear').val();
						$ddlFromYear = $('#fromyear');
						$ddlFromYear.empty();
        				$ddlFromYear.append('<option value="0"> --All-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedFYear)
							$ddlFromYear.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else
							$ddlFromYear.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
	}
	});
		});
		
	
		
		/*************************************************** From Year Change ********************************************** */
		$(document).on('click', '#btnGetReport', function(e) {
		e.preventDefault();
		$state=$('#state option:selected').val();
		$district=$('#district option:selected').val();
		$project=$('#project option:selected').val();
		$fromYear=$('#fromyear option:selected').val();
		$toYear=$('#toyear option:selected').val();
		$.ajax({  
            url:"getPhysicalActionPlanReport",
            type: "post", 
			data:{state:$state,district:$district,project:$project,fromYear:$fromYear,toYear:$toYear}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				var finyr="";
				
				console.log(data);
				$tbodyYearWisePhyActPlan=$('#tbodyYearWisePhyActPlan');
				$tbodyYearWisePhyActPlanData=$('#tbodyYearWisePhyActPlanData');
				$tbodyYearWisePhyActPlan.empty();
				$tbodyYearWisePhyActPlanData.empty();
				$yearHead="<tr><td></td><td></td><td></td><td></td>";
				$count=4;
				$plan="";
				$yearData="";
				var head =[];
				var activity =[];
				var yearArray = [];
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							
								if(key==='headActivity'){
									var count =1;
								var a =data['headActivity'];
								console.log(a);
							for ( var k in a) {
								if (a.hasOwnProperty(k)) {//alert(k);
									var x=a[k];
									for ( var i in x) {
								if (x.hasOwnProperty(i)) {
									$tbodyYearWisePhyActPlanData.append("<tr><td>"+count+"</td><td>"+x[i].headname+"</td><td>"+x[i].activityname+"</td><td>"+x[i].unitname+"</td><td>"+x[i].plan+"</td><td></td></tr>");
									
															}
													}
													}
													count++;
								}
								}
						}
					}
					//alert($count);
					$('#yearHead').prop("colspan",$count);
					$tbodyYearWisePhyActPlan.append($yearHead+"<td></td></tr>");
				
			}else{
				$tbodyYearWisePhyActPlan.append("No Data Found");
			}
	}
	});
		});
	/********************************************** Page Load Script ************************************* */	
	var selectedState = $('#state option:selected').val();
	if(selectedState!=null && selectedState!='' && selectedState>0){
	$("#state").trigger("change");
	$('#district option:selected').val($('#distCode').val());
	$("#district").trigger("change");
	$('#project option:selected').val($('#projId').val());
	$("#project").trigger("change");
	$('#fromyear option:selected').val($('#fYear').val());
	$("#fromyear").trigger("change");
	$('#toyear option:selected').val($('#tYear').val());
	$("#toyear").trigger("change");
	}
	
	});
	
	