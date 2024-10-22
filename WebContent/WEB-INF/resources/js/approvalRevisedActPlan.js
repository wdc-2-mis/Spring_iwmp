$('#saveActionPlan').on('click', function(e) {
	$dist = $('#district option:selected').val();
	$proj = $('#project option:selected').val();
	$finyr= $('#finyear option:selected').val();
	$dpr = $('#dprstatus option:selected').val();
	$auth = $('#authname').val();
	
	if($('#district option:selected').val()==""){
			successAlert('Please select district');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#district').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#district').focus();
			});
			return false;
		}
	if($('#project option:selected').val()==""){
			successAlert('Please select project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#project').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#project').focus();
			});
			return false;
		}
	if($('#finyear option:selected').val()==""){
			successAlert('Please select action plan');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#finyear').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#finyear').focus();
			});
			return false;
		}
	if($('#dprstatus option:selected').val()==""){
			successAlert('Please select DPR Status');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#dprstatus').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#dprstatus').focus();
			});
			return false;
		}
	if($('#authname').val()==""){
			successAlert('Please fill the Authority Name');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#authname').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#authname').focus();
			});
			return false;
		}
	console.log('dist: '+$dist+' finyr '+$finyr+' projid '+$proj+' dpr '+$dpr+' auth '+$auth);
	$('#loading').show();
	$.ajax({  
            url:"saveActPlan",
            type: "post", 
			data:{dcode:$dist,projid:$proj,finyear:$finyr,dprstatus:$dpr,authname:$auth}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						console.log(data);
						$('#loading').hide();
						if(data==='success'){
							alert('Action Plan Saved as Draft Successfully !');
			  
			
						}else{
							alert('Unable to Save Action Plan as Draft !');
			
						}
	}
	});
});

/************************************ District DropDown Change To get Related Project ********************************* */
//var actPlanId = 0;
$('#district').on('change',function(e){
	e.preventDefault();
	$dCode=$(this).val();
	$.ajax({  
            url:"getProjFrmDist",
            type: "post",  
            data: {dCode:$dCode},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$ddlProject = $('#project');
				$ddlProject.empty();
				$ddlProject.append('<option value="">--select--</option>');
				if(Object.keys(data).length>0){
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$ddlProject.append('<option value='+key+'>' +data[key] + '</option>');
					}
				}
			}
				}
				});
		$.ajax({  
            url:"getAlreadyDistActPlanData",
            type: "post",  
            data: {dCode:$dCode},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$getRevisedActPlanList =$('#tbodyRevisedActPlanListId');
				$getRevisedActPlanList.empty();
				$getRevisedActPlanList.append('');
				if(Object.keys(data).length>0){
					var i = 0;
			for ( var key in data) {
				i++;
				var dprStatus = (data[key].dprstatus==='Y')?'Yes':'No';
				var actPlanId =	data[key].actplanid;
				if (data.hasOwnProperty(key)) {
					if(data[key].status==='C' || dprStatus==='No'){
						$getRevisedActPlanList.append("<tr><td>"+i+"</td><td>"+data[key].projectname+"</td><td>"+data[key].finyeardesc+"</td><td>"+dprStatus+"</td><td>"+data[key].authname+"</td><td>"
     			+"<input type='button' value='Complete' onclick='disableComplete()' disabled />&nbsp;<input type='button' value='Delete' onclick='deleteActPlan("+actPlanId+")' /></td></tr>");
					}else{
						$getRevisedActPlanList.append("<tr><td>"+i+"</td><td>"+data[key].projectname+"</td><td>"+data[key].finyeardesc+"</td><td>"+dprStatus+"</td><td>"+data[key].authname+"</td><td>"
     			+"<input type='button' value='Complete' onclick='disableComplete("+actPlanId+")' />&nbsp;<input type='button' value='Delete' onclick='deleteActPlan("+actPlanId+")' /></td></tr>");
					}
					}
				}
			}
			else{
				$getRevisedActPlanList.append("<tr><td colspan='6' class='text-center'>Data not found !</td></tr>");
				
			}
				}
				});
	});


/***************************************************** End *************************** *//***/
/***************************************************** on click complete button *************************** *//***/
function disableComplete(planId) {
//	e.preventDefault();
	$dist = $('#district option:selected').val();
	$actPlanId = planId;
	$.ajax({  
            url:"completeStatus",
            type: "post",  
            data: {actPlanId:$actPlanId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
             success:function(data) {
						console.log(data);
						$('#loading').hide();
						if(data==='success'){
							$.ajax({  
            				url:"getAlreadyDistActPlanData",
            				type: "post",  
           					data: {dCode:$dist},
            				error:function(xhr,status,er){
                				console.log(er);
								$('.error').append(' There is some error please try again !');
            				},
            				success: function(data) {
								console.log(data);
								$getRevisedActPlanList =$('#tbodyRevisedActPlanListId');
								$getRevisedActPlanList.empty();
								$getRevisedActPlanList.append('');
								if(Object.keys(data).length>0){
									var i = 0;
									for ( var key in data) {
										i++;
										var dprStatus = (data[key].dprstatus==='Y')?'Yes':'No';
										var actPlanId =	data[key].actplanid;
										if (data.hasOwnProperty(key)) {
											if(data[key].status==='C' || dprStatus==='No'){
												$getRevisedActPlanList.append("<tr><td>"+i+"</td><td>"+data[key].projectname+"</td><td>"+data[key].finyeardesc+"</td><td>"+dprStatus+"</td><td>"+data[key].authname+"</td><td>"
     								+"<input type='button' value='Complete' onclick='disableComplete()' disabled />&nbsp;<input type='button' value='Delete' onclick='deleteActPlan("+actPlanId+")' /></td></tr>");
											}else{
												$getRevisedActPlanList.append("<tr><td>"+i+"</td><td>"+data[key].projectname+"</td><td>"+data[key].finyeardesc+"</td><td>"+dprStatus+"</td><td>"+data[key].authname+"</td><td>"
     								+"<input type='button' value='Complete' onclick='disableComplete("+actPlanId+")' />&nbsp;<input type='button' value='Delete' onclick='deleteActPlan("+actPlanId+")' /></td></tr>");
											}
										}
									}
								}else{
								$getRevisedActPlanList.append("<tr><td colspan='6' class='text-center'>Data not found !</td></tr>");
				
								}
							}
						});
							
					}
				}
			});
	
	}
/***************************************************** End *************************** *//***/
/***************************************************** on click delete button *************************** *//***/
function deleteActPlan(planId) {
//	e.preventDefault();
	$dist = $('#district option:selected').val();
	$actPlanId = planId;
	$.ajax({  
            url:"deleteActPlan",
            type: "post",  
            data: {actPlanId:$actPlanId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
             success:function(data) {
						console.log(data);
						$('#loading').hide();
						if(data==='success'){
						//	window.location.href='approvalRevisedActPlan';
						$.ajax({  
            				url:"getAlreadyDistActPlanData",
            				type: "post",  
           					data: {dCode:$dist},
            				error:function(xhr,status,er){
                				console.log(er);
								$('.error').append(' There is some error please try again !');
            				},
            				success: function(data) {
								console.log(data);
								$getRevisedActPlanList =$('#tbodyRevisedActPlanListId');
								$getRevisedActPlanList.empty();
								$getRevisedActPlanList.append('');
								if(Object.keys(data).length>0){
									var i = 0;
									for ( var key in data) {
										i++;
										var dprStatus = (data[key].dprstatus==='Y')?'Yes':'No';
										var actPlanId =	data[key].actplanid;
										if (data.hasOwnProperty(key)) {
											if(data[key].status==='C' || dprStatus==='No'){
												$getRevisedActPlanList.append("<tr><td>"+i+"</td><td>"+data[key].projectname+"</td><td>"+data[key].finyeardesc+"</td><td>"+dprStatus+"</td><td>"+data[key].authname+"</td><td>"
     								+"<input type='button' value='Complete' onclick='disableComplete()' disabled />&nbsp;<input type='button' value='Delete' onclick='deleteActPlan("+actPlanId+")' /></td></tr>");
											}else{
												$getRevisedActPlanList.append("<tr><td>"+i+"</td><td>"+data[key].projectname+"</td><td>"+data[key].finyeardesc+"</td><td>"+dprStatus+"</td><td>"+data[key].authname+"</td><td>"
     								+"<input type='button' value='Complete' onclick='disableComplete("+actPlanId+")' />&nbsp;<input type='button' value='Delete' onclick='deleteActPlan("+actPlanId+")' /></td></tr>");
											}
										}
									}
								}else{
								$getRevisedActPlanList.append("<tr><td colspan='6' class='text-center'>Data not found !</td></tr>");
				
								}
							}
						});
					}
				}
			});
	
	}
/***************************************************** End *************************** *//***/