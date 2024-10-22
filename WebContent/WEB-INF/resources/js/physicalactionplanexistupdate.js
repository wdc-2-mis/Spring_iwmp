/**
 * 
 */

$(function(){
	$('#createPhysicalAnnualActionPlan').click(function(e) {
	aapid=[];
	$('.error').html('');
	$('#tbodyPhysicalAnnualActionPlan').empty();
	$('#year').empty();
	$('#year').append("<option value=''>--Select Year--</option>");
	var project = $('#project');
	project.empty();
	project.append("<option value=''>--Select Project--</option>");
	$('#startDate').val("");
	var $head = $('#ddlHead');
			$head.empty();
        	$head.append('<option value="">--Select Head--</option>');
		$.ajax({
			url: "getProjectForAnnualPlan",
			type: "post",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				var project = $('#project');
				project.empty();
				project.append("<option value=''>--Select Project--</option>");
				if (Object.keys(data).length > 0) {
					for (var key in data) {
						if (data.hasOwnProperty(key)) {
							project.append("<option value=" + key + ">" + data[key] + "</option>");
						}
					}
				} else {
					//project.append("<option value=''>--Select Project--</option>");
				}
			}
		});
	});
	
	/*************************************************************************** Project CHnage method **************************************************************************** */
	
	$('#project').on("change", function() {
		$('.error').html('');
		$projId = $('#project option:selected').val();
		var url = "getFinYearExistPlan";
		$.ajax({
			url: url,
			type: "post",
			data: { projId: $projId },
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				//console.log(data);
				$('#listActivityHeadWiseTbody').empty();
				$('#listActivityHeadWiseTbody').append('<tr id="tr"><td><select id="ddlHead" name="ddlHead" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" readonly /><!--<select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control"><option value="">--Select Unit--</option></select>--></td><td><input type="text"  id="txtTarget"  name="txtTarget" onkeyup="checkvalue(event)" onblur="checkInput(this)" class="txtTarget form-control" value=""/></td><td></td></tr>');
				var $year = $('#year');
				$year.empty();
				$year.append('<option value="">--Select Year--</option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$year.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}
			}
		});
	});
	
		/********************************************************************** End ***************************************************************************** */
		
		/******************************************************************** Year change script ***************************************************************** */
//		var aapid=[];
//	$('#year').on("change" ,function() {
//		$('.error').html('');
//		$('#loading').show();
//		aapid=[];
//		if($('#year option:selected').val()=='' || $('#year option:selected').val()==null)
//		{
//			$('#startDate').val('');
//		}else{
//		$yr=$('#year option:selected').text().substring(0,$('#year option:selected').text().indexOf('-'));
//		$('#startDate').val('01-04-'+$yr);
//		}
//		$('#listActivityHeadWiseTbody').empty();
//	$('#listActivityHeadWiseTbody').append('<tr id="tr"><td><select id="ddlHead" name="ddlHead" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" readonly /><!--<select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control"><option value="">--Select Unit--</option></select>--></td><td><input type="text"  id="txtTarget" onkeyup="checkvalue(event)" onblur="checkInput(this)" name="txtTarget" class="txtTarget form-control" value=""/></td><td></td></tr>');
//		/** Code to fetch activity on year change */
//		$.ajax({  
//            url:"getHead",
//            type: "post",  
//            data: {},
//            error:function(xhr,status,er){
//                console.log(er);
//            },
//            success: function(data) {
//	//console.log(data);
//	$('#loading').hide();
//	var i = $('#listActivityHeadWiseTbody tr').length ;
//	if(i>1)
//	for(var x=0;x<parseInt(i);x++){
//		if(x==0){
//			var $head = $('#ddlHead');
//			$head.empty();
//        	$head.append('<option value="">--Select Head--</option>');
//			for ( var key in data) {
//				if (data.hasOwnProperty(key)) {
//					$head.append('<option value='+key+'>' +data[key] + '</option>');
//				}
//			}
//		}else{
//			var $head = $('#ddlHead'+x);
//			$head.empty();
//        	$head.append('<option value="">--Select Head--</option>');
//			for ( var key in data) {
//				if (data.hasOwnProperty(key)) {
//					$head.append('<option value='+key+'>' +data[key] + '</option>');
//				}
//			}
//		}
//	}else{
//	var $head = $('#ddlHead');
//			$head.empty();
//        	$head.append('<option value="">--Select Head--</option>');
//			for ( var key in data) {
//				if (data.hasOwnProperty(key)) {
//					$head.append('<option value='+key+'>' +data[key] + '</option>');
//				}
//			}	
//	}
//	
//	
//						
//	}
//	});
//	getRemarks();
//	checkForAlreadyForwardedPlan();
//	
//	
//		});
		/************************************ end ********************************* */
		
		/**************************************** Get Head Data *********************************** */
		function getHeadData() {
		$('.error').html('');
		aapid=[];
		if($('#year option:selected').val()=='' || $('#year option:selected').val()==null)
		{
			$('#startDate').val('');
		}else{
		$yr=$('#year option:selected').text().substring(0,$('#year option:selected').text().indexOf('-'));
		$('#startDate').val('01-04-'+$yr);
		}
		$('#listActivityHeadWiseTbody').empty();
	$('#listActivityHeadWiseTbody').append('<tr id="tr"><td><select id="ddlHead" name="ddlHead" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" readonly /><!--<select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control"><option value="">--Select Unit--</option></select>--></td><td><input type="text"  id="txtTarget" onkeyup="checkvalue(event)" onblur="checkInput(this)" name="txtTarget" class="txtTarget form-control" value=""/></td><td></td></tr>');
		/** Code to fetch activity on year change */
		$.ajax({  
            url:"getHead",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	//console.log(data);
	var i = $('#listActivityHeadWiseTbody tr').length ;
	if(i>1)
	for(var x=0;x<parseInt(i);x++){
		if(x==0){
			var $head = $('#ddlHead');
			$head.empty();
        	$head.append('<option value="">--Select Head--</option>');
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$head.append('<option value='+key+'>' +data[key] + '</option>');
				}
			}
		}else{
			var $head = $('#ddlHead'+x);
			$head.empty();
        	$head.append('<option value="">--Select Head--</option>');
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$head.append('<option value='+key+'>' +data[key] + '</option>');
				}
			}
		}
	}else{
	var $head = $('#ddlHead');
			$head.empty();
        	$head.append('<option value="">--Select Head--</option>');
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$head.append('<option value='+key+'>' +data[key] + '</option>');
				}
			}	
	}
	
	
						
	}
	});
	getRemarks();
	checkForAlreadyForwardedPlan();
	
	
		}
		
		/************************************************ End ******************************************** */
		/************************************* get remarks for PIA ****************************** */
		function getRemarks(){
			$('#loading').show();
			var projectcd=$('#project option:selected').val();
			var yearcd=$('#year option:selected').val();
			$.ajax({  
            url:"getRemarks",
            type: "post", 
            data: {yearcd:yearcd,projectcd:projectcd},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	$("#remarks").val(data);
	}
	});
		}
		
		/******************************************** End ************************************************** */
		
		/*********************************** Check for already forwarded plan ********************* */
		function checkForAlreadyForwardedPlan(){
			var projectcd=$('#project option:selected').val();
			var yearcd=$('#year option:selected').val();
			var $forward = $('.forward');
			$('#loading').show();
			$.ajax({  
            url:"checkForAlreadyForwardedPlan",
            type: "post", 
            data: {yearcd:yearcd,projectcd:projectcd,planid:0},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	/*if(Object.keys(data).length>0){
		
		for ( var key in data) {
		if (data.hasOwnProperty(key)) {
if(key=='RejectedBy'){
	getListofPhysicalActionPlan(false);
		$forward.removeClass("d-none");
		$forward.attr("required");
		$("#forward").removeClass("d-none");
		$("#draftSave").removeClass("d-none");
		$("#btnAdd").removeClass("d-none");
		$('.error').css("background-color","#dc3545");
$('.error').html("Physical Annual Action Plan has been Rejected By "+data[key][0]+". Kindly modify it and resubmit it again. ");
}
		
if(key=='ForwardedTo')		{
	getListofPhysicalActionPlan(true);
		$forward.addClass("d-none");
		$forward.removeAttr("required");
		$("#forward").addClass("d-none");
		$("#draftSave").addClass("d-none");
		$("#btnAdd").addClass("d-none");
		$("#ddlHead").empty();
        $("#ddlHead").append('<option value="">--Select Head--</option>');
$('.error').css("background-color","rgb(199 183 28)");
$('.error').html("Physical Annual Action Plan for project "+$('#project option:selected').text()+" and FY "+$('#year option:selected').text()+" pending for approval at "+data[key][0]+" level");
}

if(key=='ApprovedBy')		{
	getListofPhysicalActionPlan(true);
		$forward.addClass("d-none");
		$forward.removeAttr("required");
		$("#forward").addClass("d-none");
		$("#draftSave").addClass("d-none");
		$("#btnAdd").addClass("d-none");
		$("#ddlHead").empty();
        $("#ddlHead").append('<option value="">--Select Head--</option>');
$('.error').css("background-color","rgb(123 144 57)");
$('.error').html("Physical Annual Action Plan has been completed. For project "+$('#project option:selected').text()+" and FY "+$('#year option:selected').text()+"");
}		
	}
	}
	}else{*/
		getListofPhysicalActionPlan(false);
		
		//$forward.removeClass("d-none");
		//$forward.attr("required");
		$("#forward").removeClass("d-none");
		$("#draftSave").removeClass("d-none");
		$("#btnAdd").removeClass("d-none");
	//}
	}
	});
			
		}
		
		/*********************************** End **************************************************** */
		/*********************************** get User to Forward ******************** */
		function getUserToForward(){
			$.ajax({  
            url:"getUserToForward",
            type: "post", 
            data: { },
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				var $forward = $('.forward');
				$forward.empty();
        		$forward.append('<option value="">--Select--</option>');
				if(Object.keys(data).length>0 ){
					
					for ( var key in data) {
						 if (data.hasOwnProperty(key) && data[key].regid!=null) {
						    $forward.append('<option value='+data[key].regid+'>'+data[key].distname+'('+data[key].userid+')</option>');
							$forward.attr("required","required");
							//$("#forward").removeClass("d-none");
							$forward.removeClass("d-none");
						 }else{
							$forward.addClass("d-none");
							$forward.removeAttr("required");
							/*$("#forward").removeClass("d-none");*/
					}
					}
				}else{
					$forward.addClass("d-none");
					$forward.removeAttr("required");
					/*$("#forward").addClass("d-none");*/
				}
	}
	});	
		}
		
		/************************************ End *************************************** */
		/**************** code for fetching already filled data on year change  */
		function getListofPhysicalActionPlan(planexist){
	var projectcd=$('#project').val();
	var yearcd=$('#year').val();
	$('#loading').show();
	$.ajax({  
            url:"getListofPhysicalActionPlan",
            type: "post", 
            data: {projectcd:projectcd,yearcd:yearcd},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyPhysicalAnnualActionPlan = $('#tbodyPhysicalAnnualActionPlan');
	tbodyPhysicalAnnualActionPlan.empty();
	if(Object.keys(data).length>0 && !planexist){
	 for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(aapid.indexOf(data[key].aapid)===-1)
			aapid.push(data[key].aapid);
			//tbodyPhysicalAnnualActionPlan.append("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
			tbodyPhysicalAnnualActionPlan.append("<tr id='tr"+(parseInt(key)+1)+"'><td>"+data[key].headname+"</td><td>"+data[key].activityname+"</td><td>"+data[key].unitname+"</td><td>"+data[key].plan+"</td><td><input type='button' data-toggle='modal' data-target='#updatePhyActPlan' value='Edit' data-id ='"+data[key].aapid+"' name='editPlan' id = 'editPlan' align='center' /></td></tr>");
		}
			}
		//	$('.remarks').show();
		//	$('#remarks').attr('readonly',false);
		//	$('.confirmation').html('<label>Whether all the activities have been covered for FY('+$("#year option:selected").text()+') as per DPR ? &nbsp;&nbsp;</label>'+
		//	'<input type="checkbox" id="confirmation" value="yes" required/>Yes <label style="color:red">In future you would not be allowed to add activities in Physical Annual Action Plan for FY('+$("#year option:selected").text()+')</label>');
			/*$('.forward').removeClass("d-none");
			$('.forward').removeAttr("required");
			$("#forward").removeClass("d-none");*/
			getUserToForward();
			}else if(Object.keys(data).length>0 && planexist){
				$('#remarks').attr('readonly',true);
				$('.remarks').hide();
				for ( var key in data) {
				if (data.hasOwnProperty(key)) {
			if(aapid.indexOf(data[key].aapid)===-1)
			aapid.push(data[key].aapid);
			tbodyPhysicalAnnualActionPlan.append("<tr><td>"+data[key].headname+"</td><td>"+data[key].activityname+"</td><td>"+data[key].unitname+"</td><td>"+data[key].plan+"</td><td></td></tr>");
			$('.confirmation').html('');
			/*$('.forward').addClass("d-none");
			$('.forward').removeAttr("required");
			$("#forward").addClass("d-none");*/
		}
			}
			}
			else{
				$('.remarks').hide();
				$('#remarks').attr('readonly',false);
			tbodyPhysicalAnnualActionPlan.append("<tr><td colspan='5' class='text-center'>No record found</td></tr>");
			$('.confirmation').html('');
			/*$('.forward').addClass("d-none");
			$('.forward').removeAttr("required");
			$("#forward").addClass("d-none");*/
			}
	}
	});
	}
	/********************************************** End ****************************************** */
	/*************************************** On Click Edit plan ********************************** */
	$(document).on('click','#editPlan' ,function() {
		var projectcd=$('#project').val();
		var yearcd=$('#year').val();
		var aapId = $(this).data("id");
		$('#ddaapid').val(aapId);
		var headcd =0;
		var actcd =0;
		var unitcd =0;
		var plan = 0;
		
		$.ajax({  
            url:"getListofPhysicalActionPlan",
            type: "post", 
            data: {projectcd:projectcd,yearcd:yearcd},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				$ddact = $('#ddactivity');
				$ddact.empty();
				 for ( var key in data) {
					if (data.hasOwnProperty(key)) {
						if(data[key].aapid === aapId){
							headcd = data[key].headcode;
							actcd = data[key].activitycode;
							unitcd = data[key].unitcode;
							plan = data[key].plan;
							$ddact.val(data[key].activitycode);
						}
					}
				}
				
				$.ajax({ 
            	url:"getHead",
            	type: "post",  
            	data: {},
            	error:function(xhr,status,er){
                	console.log(er);
            	},
            	success: function(data) {
				var $head = $('#head');
				$head.empty();
        		$head.append('<option value="">--Select Head--</option>');
				for ( var key in data) {
					if (data.hasOwnProperty(key)) {
						if(key==headcd){
							$head.append('<option value='+key+' selected>' +data[key] + '</option>');
							headcd = key;
						}
						else{
							$head.append('<option value='+key+'>' +data[key] + '</option>');
						}
					}
				}
				
				$.ajax({  
            	url:"getActivity",
            	type: "post",  
            	data: {headId:headcd},
            	error:function(xhr,status,er){
                	console.log(er);
            	},
            	success: function(data) {
				console.log(data);
				$('#loading').hide();
				var $activity = $('#activity');
				$activity.empty();
        		$activity.append('<option value="">--Select Activity--</option>');
				for ( var key in data) {
					if (data.hasOwnProperty(key)) {
						if(key==actcd){
							$activity.append('<option value='+key+' selected>' +data[key] + '</option>');
						}else{
							$activity.append('<option value='+key+'>' +data[key] + '</option>');
						}
					}
				}
				
				$.ajax({  
            	url:"getUnit",
            	type: "post",  
            	data: {activityId:actcd},
            	error:function(xhr,status,er){
                	console.log(er);
            	},
            	success: function(data) {
				console.log(data);
				$('#loading').hide();
				var $unit = $('#unit');
				$unit.val('');
				for ( var key in data) {
					if (data.hasOwnProperty(key)) {
						if(key==unitcd){
							$unit.val(data[key]);
						}else{
							$unit.val(data[key]);
						}
					}
				}
				
				$('#plan').val(plan);
				
				
				}
				});
				
				}
				});
				
				}
				});
				
				
			}
			});
	
	});
	
	/********************************************** End ****************************************** */
	/*************************************** On Click Update plan ********************************** */
//	$(document).on('click','#planUpdate',function(e){
//		var projectcd=$('#project').val();
//		var yearcd=$('#year').val();
//		var aapId = $('#ddaapid').val();
//		var actcd =$('#activity').val();
//		var plan = $('#plan').val();
//		
////		if(plan ===''||plan ===0){
////			$('#planError').val('Plan can not be blank.');
////			$('#plan').focus();
////			return;
////		}
//		
//				$.ajax({  
//            url:"updateAsExisting",
//            type: "post",  
//            data: {plans:plan,activities:actcd,yearcd:yearcd,projectcd:projectcd},
//            error:function(xhr,status,er){
//                console.log(er);
//            },
//            success: function(data) {
////            	console.log(data);
//            	$('#loading').hide();
//            	if(data==='success')
//				{
//					alert("Activity of Plan Update successfully !");
//					$.ajax({
//						url: "getListofPhysicalActionPlan",
//						type: "post",
//						data: { projectcd: projectcd, yearcd: yearcd },
//						error: function(xhr, status, er) {
//							console.log(er);
//						},
//						success: function(data) {
//							$('#loading').hide();
//							var tbodyPhysicalAnnualActionPlan = $('#tbodyPhysicalAnnualActionPlan');
//							tbodyPhysicalAnnualActionPlan.empty();
//							$('#project option:selected').val(projectcd);
//							$('#year option:selected').val(yearcd);
//							alert('check');
//							if (Object.keys(data).length > 0) {
//								for (var key in data) {
//									if (data.hasOwnProperty(key)) {
//										tbodyPhysicalAnnualActionPlan.append("<tr id='tr" + (parseInt(key) + 1) + "'><td>" + data[key].headname + "</td><td>" + data[key].activityname + "</td><td>" + data[key].unitname + "</td><td>" + data[key].plan + "</td><td><input type='button' data-toggle='modal' data-target='#updatePhyActPlan' value='Edit' data-id ='" + data[key].aapid + "' name='editPlan' id = 'editPlan' align='center' /></td></tr>");
//									}
//								}
//							}
//							else {
//								$('.remarks').hide();
//								$('#remarks').attr('readonly', false);
//								tbodyPhysicalAnnualActionPlan.append("<tr><td colspan='5' class='text-center'>No record found</td></tr>");
//								$('.confirmation').html('');
//							}
//						}
//					});
//				}
//            	else if(data==='less'){
//            		alert("Data not Update. Selected Activity of Plan is less then Previous Plan !");
//            	}
//            	else{
//            		alert("Data not Update. Please Select Already Saved Activities!");
//            	}
//            	
//            }
//		});
//		
//		
//		
//		
//	})
	
	
	
	
	
	/********************************************** End ****************************************** */	
	
	/********************************************** On click get Button ****************************************** */	
//	$(document).on('click','#getFinalList',function(e){
//		
//		
//		
//	
//	var projectcd=$('#project').val();
//	var yearcd=$('#year').val();
//	if(projectcd==''){
//		alert('please select the project.');
//		$('#project').focus();
//		return;
//	}
//	if(yearcd==''){
//		alert('please select the year.');
//		$('#year').focus();
//		return;
//	}
//	
//	$('#loading').show();
//	$.ajax({  
//            url:"getListofPhysicalActionPlan",
//            type: "post", 
//            data: {projectcd:projectcd,yearcd:yearcd},
//            error:function(xhr,status,er){
//                console.log(er);
//            },
//            success: function(data) {
//	$('#loading').hide();
//	var tbodyPhysicalAnnualActionPlan = $('#tbodyPhysicalAnnualActionPlan');
//	tbodyPhysicalAnnualActionPlan.empty();
//	if(Object.keys(data).length>0){
//	 for ( var key in data) {
//		if (data.hasOwnProperty(key)) {
//			tbodyPhysicalAnnualActionPlan.append("<tr id='tr"+(parseInt(key)+1)+"'><td>"+data[key].headname+"</td><td>"+data[key].activityname+"</td><td>"+data[key].unitname+"</td><td>"+data[key].plan+"</td><td><input type='button' data-toggle='modal' data-target='#updatePhyActPlan' value='Edit' data-id ='"+data[key].aapid+"' name='editPlan' id = 'editPlan' align='center' /></td></tr>");
//		}
//			}
//			}
//			else{
//				$('.remarks').hide();
//				$('#remarks').attr('readonly',false);
//			tbodyPhysicalAnnualActionPlan.append("<tr><td colspan='5' class='text-center'>No record found</td></tr>");
//			$('.confirmation').html('');
//			}
//	}
//	});
//	});
	
	/********************************************** End ****************************************** */	
		/*************************************** Head change script ********************************** */
		$("#listActivityHeadWiseTbody").on('change','.ddlHead',function () { 
			$('#loading').show();
	var id = $(this).attr('id'); 
	id=id.substring(id.lastIndexOf("d")+1,id.length);
	$headVal = $(this).val();
	$('#txtTarget'+id).val("");
	var $unit = $('#ddlUnit'+id);
	$unit.val('');
		//$unit.empty();
       // $unit.append('<option value="">--Select Unit--</option>');
		$.ajax({  
            url:"getActivity",
            type: "post",  
            data: {headId:$headVal},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	var $activity = $('#ddlActivity'+id);
						$activity.empty();
        				$activity.append('<option value="">--Select Activity--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $activity.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	
	}
	});
	});
	/*************************************************** End ********************************************** */
	
	/*************************************** Activity change script ********************************** */
		$("#listActivityHeadWiseTbody").on('change','.ddlActivity',function () { 
			$('#loading').show();
	var id = $(this).attr('id'); 
	id=id.substring(id.lastIndexOf("y")+1,id.length);
	$activityVal = $(this).val();
	$('#txtTarget'+id).val("");
		$.ajax({  
            url:"getUnit",
            type: "post",  
            data: {activityId:$activityVal},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	var $unit = $('#ddlUnit'+id);
						$unit.val('');
        				//$unit.append('<option value="">--Select Unit--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       //$unit.append('<option value='+key+'>' +data[key] + '</option>');
$unit.val(data[key]);
						                    }
						                }
	
	}
	});
	});
	
	/*************************************************** End ********************************************** */
	
	/********************************************* Add/Remove row in/from table script *********************************** */
	
var scntDiv = $('#listActivityHeadWiseTbody');
var i=0;
i = $('#listActivityHeadWiseTbody tr').length ;
var j = 1;
var arr = [];

$('#btnAdd').click(function(e) {
	//alert('kdy addbtn = '+i);
	arr[0] ='';
	arr[j] =i;
	j++;
    scntDiv.append('<tr id="tr'+i+'"><td><select id="ddlHead'+i+'" name="ddlHead'+i+'" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity'+i+'" name="ddlActivity'+i+'" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit'+i+'" name="ddlUnit'+i+'" class="ddlUnit form-control" readonly /><!--<select id="ddlUnit'+i+'" name="ddlUnit'+i+'" class="ddlUnit form-control"><option value="">--Select Unit--</option></select>--></td><td><input type="text" id="txtTarget'+i+'" onkeyup="checkvalue(event)" onblur="checkInput(this)" name="txtTarget'+i+'" class="txtTarget form-control" value=""/></td><td><a href="#" class="delete">delete</a></td></tr>');   
/*$table.row.add( [
            '<td><select id="ddlHead" name="ddlHead" class="form-control"><option value="">--Select Head--</option></select></td>','<td><select id="ddlActivity" name="ddlActivity" class="form-control"><option value="">--Select Activity--</option></select></td>','<td><select id="ddlUnit" name="ddlUnit" class="form-control"><option value="">--Select Unit--</option></select></td>','<td><input type="text" id="txtTarget" name="txtTarget" class="form-control" value=""/></td>','<td></td>'
        ] ).draw( false );*/
if($('#year option:selected').val()!==''){
$.ajax({  
            url:"getHead",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	var $head = $('#ddlHead'+i);
						$head.empty();
        				$head.append('<option value="">--Select Head--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $head.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
i=i+1;
	}
	});	
}
else{
	i=1;
}

	
e.preventDefault();
    return false;
});

//Remove button
$(document).on('click', '.delete', function(e) {
    if (i > 1) {
        $(this).closest('tr').remove();
		//scntDiv.row( $(this).parents('tr') ).remove();
        i--;
var rowId = $(this).closest('tr').attr('id') ;
for(var x=parseInt(rowId.match(/\d+/));x<parseInt(i);x++){
	rowCount=i-1;
	$('#ddlHead'+(parseInt(x)+1)).attr("id","ddlHead"+x);
	$('#ddlActivity'+(parseInt(x)+1)).attr("id","ddlActivity"+x);
	$('#ddlUnit'+(parseInt(x)+1)).attr("id","ddlUnit"+x);
	$('#txtTarget'+(parseInt(x)+1)).attr("id","txtTarget"+x);
	$("#tr"+(parseInt(x)+1)).attr("id","tr"+x);
	}
    }
e.preventDefault();
    return false;
});

$(document).on('click', '.deleteannualactionplan', function(e) {
	if(confirm('Do you want to delete activity ?')){
	var del = e.target.getAttribute('data-id');
	/*aapid = jQuery.grep(aapid, function(value) {
  	return value != del;
	});*/
	aapid=arrayRemove(aapid,del);
	
    $(this).closest('tr').remove();
$.ajax({  
            url:"deleteActivityFromAnnualActionPlan",
            type: "post",  
            data: {aapid:del},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	if(data==='success'){
			alert('Delete succefully');
		getListofPhysicalActionPlan(false);
		
		window.location.href='piaPhyActPlan';
	}else{
		alert('Not Deleted!');
	}
	}
	});	
	}
	
});
/************************************************************* End ********************************************************* */
	
/*************************************************** Save as Draft Script *************************************************** */
$('#draftSave').click(function(e) {
	e.preventDefault();
	
	var i = $('#listActivityHeadWiseTbody tr').length ;
	var regexp= /^\d+(?:\.\d\d?)?$/;// regex to check input value upto two(2) decimal places with ten(10) precedence value./^[0-9]\d{1,8}(\.\d{1,2})?$/
//	alert('kdy '+i);
	if($('#ddlHead option:selected').val()==""){
			successAlert('Please select head');
			$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#ddlHead').focus();
			});  
			$(".close").click(function(){
				$('#popup').modal('hide');
				$('#ddlHead').focus();
			});
			return false;
		}
	
		if($('#ddlActivity option:selected').val()==""){
			successAlert('Please select activity');
			$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#ddlActivity').focus();
			});  
			$(".close").click(function(){
				$('#popup').modal('hide');
				$('#ddlActivity').focus();
			});
			return false;
		}
		
		if($('#ddlUnit').val()==""){
			successAlert('Please select unit');
			$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#ddlUnit').focus();
			});  
			$(".close").click(function(){
				$('#popup').modal('hide');
				$('#ddlUnit').focus();
			});
			return false;
		}
		
		if($('#txtTarget').val().trim()==""){
			successAlert('Please fill target');
			$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#txtTarget').focus();
			});  
			$(".close").click(function(){
				$('#popup').modal('hide');
				$('#txtTarget').focus();
			});
			return false;
		}
		
		else{
			
				var target = $('#txtTarget').val();
				if(target.indexOf('.')==0)
				target="0"+target;
				
				if(target.indexOf('.')>=0)
				var afterDecimalLength = (target.toString().split(".")[1]).length;
				
				else
				afterDecimalLength=0;
				
				var result=regexp.test(target);
		//	alert(result+" : "+afterDecimalLength+" : "+target+" : "+target.indexOf('.')+" : "+target.toString().split(".")[1]);
				if(!result || (target.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 || target<=0 )
				{   
					successAlert('Please enter valid positive value upto two decimal point');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#txtTarget').focus();
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
						$('#txtTarget').focus();
					});
					return false;
				}
				var onlyNumRegex = /^[\d]*$/;
		//alert('yogesh....'+onlyNumRegex.test(target));
				for(var j=0; j<arr.length; j++)
				{
					var targetID = $('#txtTarget'+arr[j]).val();
					if($('#ddlUnit'+arr[j]).val()=='nos' && !onlyNumRegex.test(targetID))
					{ 
							successAlert('Please enter only number when unit "nos" can not be decimal number in plan');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								$('#txtTarget'+arr[j]).focus();
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								$('#txtTarget'+arr[j]).focus();
							});
							return false;
					}
				}
		}
		
		if(i>1)
		{
			for(var x=1;x<parseInt(i);x++)
			{
				if($('#ddlHead'+x+' option:selected').val()=="")
				{
					successAlert('Please select head');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#ddlHead'+x).focus();
					});  
					
					$(".close").click(function(){
						$('#popup').modal('hide');
						$('#ddlHead'+x).focus();
					});
					return false;
				}
				if($('#ddlActivity'+x+' option:selected').val()=="")
				{
					successAlert('Please select activity');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#ddlActivity'+x).focus();
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
						$('#ddlActivity'+x).focus();
					});
					return false;
				}
				if($('#ddlUnit'+x).val()==""){
					successAlert('Please select unit');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#ddlUnit'+x).focus();
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
						$('#ddlUnit'+x).focus();
					});
					return false;
				}
				if($('#txtTarget'+x).val()==""){
					successAlert('Please fill target');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#txtTarget'+x).focus();
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');				
						$('#txtTarget'+x).focus();
					});
					return false;
				}
				else{
					
					var target = $('#txtTarget'+x).val();
				//	alert('kdyvr'+target);
					if(target.indexOf('.')>=0)
						var afterDecimalLength = (target.toString().split(".")[1]).length;
					else
						afterDecimalLength=0;
					if(target.indexOf('.')==0)
						target="0"+target;
					
					var result=regexp.test(target);
					
			//		alert(afterDecimalLength+" : "+target+" : "+target.indexOf('.')+" : "+target.toString().split(".")[1]);
					if(!result || (target.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 || target<=0 )
					{
						successAlert('Please enter valid positive value upto two decimal point');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							$('#txtTarget'+x).focus();
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
							$('#txtTarget'+x).focus();
						});
						return false;
					}
					//alert('kdy13 '+i);
				}
			}
		}
		else{
			//alert('kdy12 '+i);
			if($('#ddlHead option:selected').val()=="")
			{
				successAlert('Please select head');
				$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#ddlHead').focus();
				});  
				$(".close").click(function(){
					$('#popup').modal('hide');
					$('#ddlHead').focus();
				});
				return false;
			}
			if($('#ddlActivity option:selected').val()=="")
			{
				successAlert('Please select activity');
				$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#ddlActivity').focus();
				});  
				$(".close").click(function(){
					$('#popup').modal('hide');
					$('#ddlActivity').focus();
				});
				return false;
			}
			if($('#ddlUnit').val()==""){
				successAlert('Please select unit');
				$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#ddlUnit').focus();
				});  
				$(".close").click(function(){
					$('#popup').modal('hide');
					$('#ddlUnit').focus();
				});
				return false;
			}
			if($('#txtTarget').val()==""){
				successAlert('Please fill target');
				$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#txtTarget').focus();
				});  
				$(".close").click(function(){
					$('#popup').modal('hide');
					$('#txtTarget').focus();
				});
				return false;
			}
			else{
			//	alert('kdy9 '+i);
				var target = $('#txtTarget').val();
				if(target.indexOf('.')>=0)
					var afterDecimalLength = (target.toString().split(".")[1]).length;
				else
					afterDecimalLength=0;
				if(target.indexOf('.')==0)
					target="0"+target;
				var result=regexp.test(target);
			//alert(decimalLength+" : "+target+" : "+target.indexOf('.')+" : "+target.toString().split(".")[1]);
				if(!result || (target.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 || target<=0 ){
					successAlert('Please enter valid positive value upto two decimal point');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						$('#txtTarget').focus();
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
						$('#txtTarget').focus();
					});
					return false;
				}
			}
		}
	//	alert('kdy10 '+i);
		var activity=[];
		var plan =[];
		if(i>1){
			for(var x=0;x<parseInt(i);x++)
			{
				if(x==0)
				{
					activity.push($('#ddlActivity option:selected').val());
					plan.push($('#txtTarget').val());
				}
				else{
					
					if ($.inArray($('#ddlActivity'+x+' option:selected').val(), activity) != -1)
					{
						successAlert("You have already planned for <b style='color:red;'>'"+$('#ddlActivity'+x+' option:selected').text()+"'</b> activity. Please select different one.")
						addInputFieldErrorCss('#ddlActivity'+x);
						$("#successok").click(function(){
							$('#popup').modal('hide');
							$('#ddlActivity'+x).focus();
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
							$('#ddlActivity'+x).focus();
						});
						return false;
					}
					else{
						activity.push($('#ddlActivity'+x+' option:selected').val());
						plan.push($('#txtTarget'+x).val());
						removeInputFieldErrorCss('#ddlActivity'+x);
					}
				}
			}
		}
		else{
			activity.push($('#ddlActivity option:selected').val());
			plan.push($('#txtTarget').val());
		}
		var projectcd=$('#project option:selected').val();
		var yearcd = $('#year option:selected').val();
		
			confirmAlert('Do you want to Update Activity of Plan. ?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
		$.ajax({  
            url:"updateAsExisting",
            type: "post",  
            data: {plans:""+plan,activities:""+activity,yearcd:yearcd,projectcd:projectcd},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	console.log(data);
            	$('#loading').hide();
            	if(data==='success')
            	{
            		alert("Activity of Plan Update successfully !");
            		/*$("#successok").click(function(){
            			$('#popup').modal('hide');
            		});  
            		$(".close").click(function(){
            			$('#popup').modal('hide');
            		});*/
            		getHeadData();
		/*getListofPhysicalActionPlan(false);
		$('#listActivityHeadWiseTbody').empty();
		$('#listActivityHeadWiseTbody').append('<tr id="tr"><td><select id="ddlHead" name="ddlHead" class="ddlHead form-control"><option value="">--Select Head--</option></select></td><td><select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control"><option value="">--Select Activity--</option></select></td><td><input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" readonly /><!--<select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control"><option value="">--Select Unit--</option></select>--></td><td><input type="text" id="txtTarget" name="txtTarget" class="form-control" value=""/></td><td></td></tr>');*/
            	}
            	else if(data==='less'){
            		alert("Data not Update. Selected Activity of Plan is less then Previous Plan !");
            		/*$("#successok").click(function(){
            			$('#popup').modal('hide');
            		});  
            		$(".close").click(function(){
            			$('#popup').modal('hide');
            		});*/
            	}
            	else{
            		alert("Data not Update. Please Select Already Saved Activities!");
            		/*$("#successok").click(function(){
            			$('#popup').modal('hide');
            		});  
            		$(".close").click(function(){
            			$('#popup').modal('hide');
            		});*/
            	}
            }
		});
		
		});
		
	});


/**************************************************** End ******************************************************************** */	
	
	
/***************************************************** Forward to District Button clicked **************************************** */
$('#forward').click(function(e) {
	
	$user = $("#user option:selected").val();
	$year = $("#year option:selected").val();
	$project = $("#project option:selected").val();
	$remarks = $("#remarks").val();
	var $forward = $('.forward');
	//alert($user+" : "+$year+" : "+$project+" : "+$remarks);
	e.preventDefault();
	if(aapid.length===0){
		successAlert("There is no activity selected for approval. Please select according to DPR");
		$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
		return false;
	}else{
		
	}
	if($("#confirmation").prop("checked") == true){
		//alert("Checkbox is checked.");
		removeInputFieldErrorCss('#confirmation');
        }else if($("#confirmation").prop("checked") == false){
	successAlert("Please tick on confirmation, Whether all the activities have been covered as per DPR ?");
	$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	$("#confirmation").focus();
	addInputFieldErrorCss('#confirmation');
	return false;
	}
	if($user==="" && !$("#user").hasClass("d-none")  ){
	successAlert("Please Select User");
	$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	addInputFieldErrorCss('#user');	
	$("#user").focus();
	return false;
	}else{
		removeInputFieldErrorCss('#user');
	}
	
	//alert("user "+$user+" year "+$year+" project "+$project);
	if(confirm("Whether all the activities have been covered for FY("+$("#year option:selected").text()+") as per DPR ?")){
		$('#loading').show();
	$.ajax({  
            url:"forward",
            type: "post",  
            data: {user:$user,yearcd:$year,projectcd:$project,remarks:$remarks},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	if(data==='success'){
		if(!$("#user").hasClass("d-none")){
		successAlert('Your request has been forwarded to '+$("#user option:selected").text()+ ' for approval. Kindly contact to them for approval.');
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('.error').html("Physical Annual Action Plan for project "+$('#project option:selected').text()+" and FY "+$('#year option:selected').text()+" forwarded for approval");
			getListofPhysicalActionPlan(true);
			$forward.addClass("d-none");
		$forward.removeAttr("required");
		$("#forward").addClass("d-none");
		$("#draftSave").addClass("d-none");
		$("#btnAdd").addClass("d-none");
		$("#ddlHead").empty();
        $("#ddlHead").append('<option value="">--Select Head--</option>');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('.error').html("Physical Annual Action Plan for project "+$('#project option:selected').text()+" and FY "+$('#year option:selected').text()+" forwarded for approval");
			getListofPhysicalActionPlan(true);
			$forward.addClass("d-none");
		$forward.removeAttr("required");
		$("#forward").addClass("d-none");
		$("#draftSave").addClass("d-none");
		$("#btnAdd").addClass("d-none");
		$("#ddlHead").empty();
        $("#ddlHead").append('<option value="">--Select Head--</option>');
			});
			}
		else{
		successAlert('Your request has been forwarded to SLNA for approval. Kindly contact to them for approval.');
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('.error').html("Physical Annual Action Plan for project "+$('#project option:selected').text()+" and FY "+$('#year option:selected').text()+" forwarded for approval");
			getListofPhysicalActionPlan(true);
			$forward.addClass("d-none");
		$forward.removeAttr("required");
		$("#forward").addClass("d-none");
		$("#draftSave").addClass("d-none");
		$("#btnAdd").addClass("d-none");
		$("#ddlHead").empty();
        $("#ddlHead").append('<option value="">--Select Head--</option>');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('.error').html("Physical Annual Action Plan for project "+$('#project option:selected').text()+" and FY "+$('#year option:selected').text()+" forwarded for approval");
			getListofPhysicalActionPlan(true);
			$forward.addClass("d-none");
		$forward.removeAttr("required");
		$("#forward").addClass("d-none");
		$("#draftSave").addClass("d-none");
		$("#btnAdd").addClass("d-none");
		$("#ddlHead").empty();
        $("#ddlHead").append('<option value="">--Select Head--</option>');
			});
		//checkForAlreadyForwardedPlan();
		}
		
$('.error').css("background-color","rgb(199 183 28)");
	}else if(data==='usernotfound'){
		successAlert('There is no user to forward. Kindly contact to SLNA.');
		$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	}else{
		successAlert('There is some error, Please try again.');
		$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	}
	
	}
	});
	}
	});
/********************************************************* End ****************************************************************** */	

/**************************************************** View Movement Code ********************************************************* */

$('#viewMovement').click(function(e) {
	var proj=[];
	var yr=[];
	$('#loading').show();
	$.ajax({  
            url:"viewMovement",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyMovement = $('#tbodyMovement');
	tbodyMovement.empty();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyMovement.append("<tr><td>"+key+"</td><td>"+d[k].finyr+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlevel+"</td><td class='remarks'>"+d[k].remarks+"</td><td style='text-align:center;'><a class='finyr' data-id="+d[k].planid+" href='#'>view</a></td><td style='text-align:center;'><a class='status' data-id="+d[k].planid+" href='#'>view</a></td></tr>");
			else
			tbodyMovement.append("<tr><td></td><td>"+d[k].finyr+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlevel+"</td><td class='remarks'>"+d[k].remarks+"</td><td style='text-align:center;'><a class='finyr' data-id="+d[k].planid+" href='#'>view</a></td><td style='text-align:center;'><a class='status' data-id="+d[k].planid+" href='#'>view</a></td></tr>");
		}
		}
		
		}
			}
	}else{
		tbodyMovement.append("<tr><td colspan='7' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	
	/************************************************************************** End ******************************************************************* */
	
	/************************************************************************** Fin Year Link Click **************************************************** */
	
	$('#tbodyMovement').on( 'click', 'a.finyr', function (e) {
	var del = e.target.getAttribute('data-id');
	//alert(del);
	$('#loading').show();
	$.ajax({  
            url:"getPlanDetails",
            type: "post",  
            data: {planid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
	$('#loading').hide();
						console.log(data);
						var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('Movement of Physical Annual Action Plan');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key][0].projectdesc+ ' and FY: '+data[key][0].yrdesc+'<br/><span style="color:red"> Current Status: '+data[key][0].status+"</span>");
			}
			var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].headname+"</td><td>"+d[k].activityname+"</td><td>"+d[k].unitname+"</td><td>"+d[k].plan+"</td></tr>";
			else
			tblData+="<tr><td></td><td>"+d[k].activityname+"</td><td>"+d[k].unitname+"</td><td>"+d[k].plan+"</td></tr>";
		}	
		}
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Name of Head</th><th>Name of Activity</th><th>Unit</th><th style="text-align:center;"><span >Plan</span><br/>Quantity/ Area to be '+
						'developed</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
							});
	});
	
	$('#tbodyMovement').on( 'click', 'a.status', function (e) {
	var del = e.target.getAttribute('data-id');
	$('#loading').show();
	$.ajax({  
            url:"getPlanMovementDetails",
            type: "post",  
            data: {planid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
	$('#loading').hide();
						console.log(data);
						var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('Movement of Physical Annual Action Plan');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key][0].projectdesc+ ' and FY: '+data[key][0].finyr+'<br/><span style="color:red"> Current Status: '+data[key][0].status+"</span>");
			}
			var d=data[key];
			var action='';
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(d[k].action==='F'){
				action='Forwarded';
			}else if(d[k].action==='R'){
				action='Rejected';
			}else if(d[k].action==='C'){
				action='Completed';
			}
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].sentfrom+"</td><td>"+d[k].sentto+"</td><td>"+d[k].senton+"</td><td>"+action+"</td><td class='remarks'>"+d[k].remarks+"</td></tr>";
			else
			tblData+="<tr><td>"+d[k].sentfrom+"</td><td>"+d[k].sentto+"</td><td>"+d[k].senton+"</td><td>"+action+"</td><td class='remarks'>"+d[k].remarks+"</td></tr>";
		}	
		}
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Sent From</th><th>Sent To</th><th>Sent On</th><th>Action</th><th style="text-align:center;">Remarks'+
						'</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
						}
							});
	});
	
	
	$('#tbodyWCDCSLNAMovement').on( 'click', 'a.finyr', function (e) {
	var del = e.target.getAttribute('data-id');
	$('#loading').show();
	$.ajax({  
            url:"getPlanDetails",
            type: "post",  
            data: {planid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
	$('#loading').hide();
						console.log(data);
						var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('Movement of Physical Annual Action Plan xyz');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key][0].projectdesc+ ' and FY: '+data[key][0].yrdesc+'<br/><span style="color:red"> Current Status: '+data[key][0].status+"</span>");
			}
			var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].headname+"</td><td>"+d[k].activityname+"</td><td>"+d[k].unitname+"</td><td>"+d[k].plan+"</td></tr>";
			else
			tblData+="<tr><td></td><td>"+d[k].activityname+"</td><td>"+d[k].unitname+"</td><td>"+d[k].plan+"</td></tr>";
		}	
		}
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Name of Head</th><th>Name of Activity</th><th>Unit</th><th style="text-align:center;"><span >Plan</span><br/>Quantity/ Area to be '+
						'developed</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
						}
							});
	});

/**************************************************** End ************************************************************************** */
	
	/************************************************************************** Action link click for WCDC *************************************** */
	$('#tbodyWCDCSLNAMovement').on( 'click', 'a.action', function (e) {
		  $('#loading').show();
		/*var $row = $(this).closest("tr");    
    $row.find("td").each(function(i,e) {
      alert($(e).text()+': '+i);
    });
    alert($row.find('td:eq(0)').text());*/
$planid= e.target.getAttribute('data-id');
//alert($planid);
//$data=checkAlreadyForwarededPlan($planid);
//alert("data "+$data);
$.ajax({  
            url:"checkForAlreadyForwardedPlan",
            type: "post", 
            data: {yearcd:0,projectcd:0,planid:$planid},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d=data[key];
			if(d[1]){
				$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('<h5>Movement of Physical Annual Action Plan</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="action" id="action" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$planid+'" name="planid" id="planid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info" onclick="submitaction()" >Submit</button>');
			}else{
				$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('');
						$('#popupreport #popupreporttitle').html('<h5>Movement of Physical Annual Action Plan</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('');
						$('#popupreport .modal-body').html('<span style="color:red;text-align:centre">Plan has been '+key+" "+data[key]+'</span>');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
			}
			
			}
			}
	}else{
	$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('<h5>Movement of Physical Annual Action Plan</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="action" id="action" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$planid+'" name="planid" id="planid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info" onclick="submitaction()" >Submit</button>');	
	}
//	CKEDITOR.replace('remarks');
	}
	});	
		
		});
		
/************************************************* End ************************************** */

/******************************************* View Head/Activity click *************************** */

$('#viewHeadActivity').click(function(e) {
	$('#loading').show();
	var i=1;
	var headCode=1;
	var activityCode=1;
		$.ajax({  
            url:"viewHeadActivity",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyMovement = $('#tbodyHeadActivity');
	tbodyMovement.empty();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			activityCode=1;
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyMovement.append("<tr><td>"+i+"</td><td>"+headCode+". "+key+"</td><td>"+headCode+"."+activityCode+" "+d[k]+"</td></tr>");
			else
			tbodyMovement.append("<tr><td>"+i+"</td><td></td><td>"+headCode+"."+activityCode+" "+d[k]+"</td></tr>");
		}
		activityCode++;
		i++;
		}
		headCode++;
		}
			}
	}else{
		tbodyMovement.append("<tr><td colspan='7' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	
	
	/********************************************************************** End *********************************************** */
$(".txtTarget").inputFilter(function(value) {
  return /^\d*[.,]?\d{0,2}$/.test(value); });

		
	});
	/********************************************************************* Document load script end ************************************************ */
	
(function($) {
  $.fn.inputFilter = function(inputFilter) {
    return this.on("input keydown keyup", function() {//input keydown keyup mousedown mouseup select contextmenu drop
      if (inputFilter(this.value)) {
        this.oldValue = this.value;
        this.oldSelectionStart = this.selectionStart;
        this.oldSelectionEnd = this.selectionEnd;
      } else if (this.hasOwnProperty("oldValue")) {
        this.value = this.oldValue;
        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
      } else {
        this.value = "";
      }
    });
  };
}(jQuery));



	/************************************************** District Forward Click *********************************** */
	function submitaction(){
		$('#loading').show();
		$action= $('#action option:selected').val();
		$remarks = $("#remarks").val();
// $remarks= CKEDITOR.instances['remarks'].getData(); 
$planid=$('#planid').val();
//alert($action+" : "+$remarks+" : "+$planid);
if($remarks==="" && $action==='R' ){
	$('#errormessage').html("Please give remarks for rejection.");
	$('#errormessage').css("color","red");
	addInputFieldErrorCss('#remarks');	
	$("#remarks").focus();
	return false;
	}else{
		removeInputFieldErrorCss('#remarks');
	}
	$.ajax({  
            url:"forwardByWCDC",
            type: "post",  
            data: {planid:$planid,action:$action,remarks:$remarks},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
	$('#loading').hide();
						console.log(data);
						$('#popupreport').modal('hide');
						if(data==='state'){
							if($action==='R')
							successAlert('Data of Annual Action Plan is being rejected.');
							else
							successAlert('Physical Annual Action Plan data has been finalized and completed.');
							//$('#errormessage').html("Data has been finalized and completed.");
							
						}
						if(data==='district'){
						//$('#errormessage').html("Data forwarded to SLNA for further approval.");
						if($action==='R')
							successAlert('Data of Annual Action Plan is being rejected.');
							else
						successAlert('Physical Annual Action Plan data forwarded to SLNA for further approval.');	
						}
						$("#successok").click(function(){
$('#popup').modal('hide');
window.location.href="wcdcslnaPhyActPlan";
});
						
	}
	});
	}
	
	/********************************************************************** End ******************************************************** */
	
	/********************************************************************** Check for Already Forarded Plan *************************************/
	
	function checkAlreadyForwarededPlan(planid){
		$('#loading').show();
		var res='';
	$.ajax({  
            url:"checkForAlreadyForwardedPlan",
            type: "post", 
            data: {yearcd:0,projectcd:0,planid:planid},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	console.log(data);
	
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			res=key+" "+data[key];
			}
			}
	console.log("result "+res);
	return res;
	}
	});	
	
	}
	/*************************************************************** End ***************************************** */
	
	/************************************************* Script to validate Target Field ********************************* */
	
	function checkvalue(e){
	//if the letter is not digit then display error and don't type anything
	$(".txtTarget").inputFilter(function(value) { //alert(value);
		  return /^\d*[.,]?\d{0,2}$/.test(value); });
	  //^\s*(?=.*[1-9])\d*(?:\.\d{1,2})?\s*$
	   
		
}

function checkInput(input){
if(input.value==="0")
	{
	input.value="";
	return false;
	}
}

/********************************************************** End ******************************************************* */
	