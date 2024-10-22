/**
 * 
 */

 $( document ).ready(function(){
	
	$('#btnGetActivity').click(function(e) {
		e.preventDefault();
		$pCode = $('#ddlProject option:selected').val();
		$head= new Array();
		$tbodyAddPhysicalAchievement = $('#tbodyAddPhysicalAchievement');
		$tbodyAddPhysicalAchievement.empty();
		$.ajax({  
            url:"getActivityWithBaselineORasOn",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
				
				//$('#ddlUser').removeClass('d-none');
				console.log(data);
				$activityid=1;
				$headid=0;
				if(Object.keys(data).length>0){
					$('.error').html('');
					for ( var key in data) {
						if (data.hasOwnProperty(key)) 
						{
							$('#lblStartDate').html(data[key].startdate);
							$('#lblEndDate').html(data[key].enddate);
							$('#monthId').val(data[key].startmonth);
							$('#finYr').val(data[key].finyr);
		if(data[key].startmonth >0 && data[key].finyr >0){	
			$('#btnForward').removeClass('d-none');						
		$tbodyAddPhysicalAchievement.append("<tr><td>1</td><td>Gross Cropped Area (in ha.)</td><td><input type='text' disabled='true' id='gross_cropped_area' name='gross_cropped_area' value='"+data[key].gross_cropped_area+"' class='form-control'  /></td><td><input type='text' disabled='true' id='gross_cropped_area_achv' name='gross_cropped_area_achv' value='"+data[key].gross_cropped_area_achv+"' class='form-control'  /></td></tr>");
		$tbodyAddPhysicalAchievement.append("<tr><td>2</td><td>Total Income (Rs.)</td><td><input type='text' disabled='true' id='total_income' name='total_income' value='"+data[key].total_income+"' class='form-control'  /></td><td><input type='text' disabled='true' id='total_income_achv' name='total_income_achv' value='"+data[key].total_income_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>3</td><td>Cultivable Wasteland</td><td><input type='text' disabled='true' id='cultivable_wasteland' name='cultivable_wasteland' value='"+data[key].cultivable_wasteland+"' class='form-control'  /></td><td><input type='text' disabled='true' id='cultivable_wasteland_achv' name='cultivable_wasteland_achv' value='"+data[key].cultivable_wasteland_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>4</td><td>Degraded Land</td><td><input type='text' disabled='true' id='degraded_land' name='degraded_land' value='"+data[key].degraded_land+"' class='form-control'  /></td><td><input type='text' disabled='true' id='degraded_land_achv' name='degraded_land_achv' value='"+data[key].degraded_land_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>5</td><td>Rainfed Area</td><td><input type='text' disabled='true' id='rainfed' name='rainfed' value='"+data[key].rainfed+"' class='form-control'  /></td><td><input type='text' disabled='true' id='rainfed_achv' name='rainfed_achv' value='"+data[key].rainfed_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>6</td><td>Other</td><td><input type='text' disabled='true' id='others' name='others' value='"+data[key].others+"' class='form-control'  /></td><td><input type='text' disabled='true' id='others_achv' name='others_achv' value='"+data[key].others_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>7</td><td>Protective Irrigation</td><td><input type='text' disabled='true' id='protective_irrigation' name='protective_irrigation' value='"+data[key].protective_irrigation+"' class='form-control'  /></td><td><input type='text' disabled='true' id='protective_irrigation_achv' name='protective_irrigation_achv' value='"+data[key].protective_irrigation_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>8</td><td>Assured Irrigation</td><td><input type='text' disabled='true' id='assured_irrigation' name='assured_irrigation' value='"+data[key].assured_irrigation+"' class='form-control'  /></td><td><input type='text' disabled='true' id='assured_irrigation_achv' name='assured_irrigation_achv' value='"+data[key].assured_irrigation_achv+"' class='form-control'  /></td></tr>");							
		$tbodyAddPhysicalAchievement.append("<tr><td>9</td><td>No Irrigation</td><td><input type='text' disabled='true' id='no_irrigation' name='no_irrigation' value='"+data[key].no_irrigation+"' class='form-control'  /></td><td><input type='text' disabled='true' id='no_irrigation_achv' name='no_irrigation_achv' value='"+data[key].no_irrigation_achv+"' class='form-control'  /></td></tr>");			
		$tbodyAddPhysicalAchievement.append("<tr><td>10</td><td>Single Crop</td><td><input type='text' disabled='true' id='single_crop' name='single_crop' value='"+data[key].single_crop+"' class='form-control'  /></td><td><input type='text' disabled='true' id='single_crop_achv' name='single_crop_achv' value='"+data[key].single_crop_achv+"' class='form-control'  /></td></tr>");			
		$tbodyAddPhysicalAchievement.append("<tr><td>11</td><td>Double Crop</td><td><input type='text' disabled='true' id='double_crop' name='double_crop' value='"+data[key].double_crop+"' class='form-control'  /></td><td><input type='text' disabled='true' id='double_crop_achv' name='double_crop_achv' value='"+data[key].double_crop_achv+"' class='form-control'  /></td></tr>");			
		$tbodyAddPhysicalAchievement.append("<tr><td>12</td><td>Multiple Crop</td><td><input type='text' disabled='true' id='multiple_crop' name='multiple_crop' value='"+data[key].multiple_crop+"' class='form-control'  /></td><td><input type='text' disabled='true' id='multiple_crop_achv' name='multiple_crop_achv' value='"+data[key].multiple_crop_achv+"' class='form-control'  /></td></tr>");			
		$tbodyAddPhysicalAchievement.append("<tr><td>13</td><td>No Crop</td><td><input type='text' disabled='true' id='no_crop' name='no_crop' value='"+data[key].no_crop+"' class='form-control'  /></td><td><input type='text' disabled='true' id='no_crop_achv' name='no_crop_achv' value='"+data[key].no_crop_achv+"' class='form-control'  /></td></tr>");			
		}
		else{
			
			$('.error').html('Baseline Update Achievement Month can not be Greater than The Current Month. ');
			$tbodyAddPhysicalAchievement.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
			$ddlUser.addClass('d-none');
			$('#btnForward').addClass('d-none');
		}
		
		
					
						}
					}
						
				$.ajax({  
	            url:"getUserToForwardForPhysicalAchievement",
	            type: "post", 
				data:{pCode:$pCode}, 
	            error:function(xhr,status,er){
	                console.log(er);
	            },
		     	success:function(data) {
					console.log(data);
					$ddlUser = $('#ddlUser');
					if(Object.keys(data).length>0)
					{
						$ddlUser.removeClass('d-none');
						$ddlUser.empty();
						$ddlUser.append('<option value="0">--Select User--</option>');
						for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								$ddlUser.append('<option value='+key+'>'+data[key]+'</option>');
					
							}
						}
					}
					else{
						//$ddlUser.addClass('d-none');	
					}
				}
			
			});			
									
			}
				
			/*	else{
					$('#lblStartDate').html('N.A.');
		$('#lblEndDate').html('N.A.');
		$('#btnForward').addClass('d-none');
		//$('#ddlUser').addClass('d-none');
		
		$.ajax({  
            url:"getMinumFinYear",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			 	console.log(data);
			//	alert('kedar '+data.length);
				if(data.length>0){
					$('.error').html('Create a plan, only then can you fill the achievement of an financial year '+data);
				}
				else{
			 		$('.error').html('Achievement month can not be greater than current month. ');
				}
			}
			
			});	
		
					$tbodyAddPhysicalAchievement.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
					$ddlUser.addClass('d-none');
				}  */
			
			}
			});
		
		});
		
		
		$(document).on('click', '#btnForward', function(e) {
		e.preventDefault();
		var regexp= /^\d+(?:\.\d\d?)?$/;
		$pCode = $('#ddlProject option:selected').val();
		
		if(!$('#blsid').is(':checked'))
		{
			
			successAlert("Please click on checkbox, Plot-wise data has been updated wherever there have been changes in the Plots.");
			addInputFieldErrorCss('#blsid');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#blsid').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#blsid').focus();
			});
			return false;
		}
		else{
			$blscon = true;
		}
		
		if(typeof $('#ddlUser option:selected').val()==='undefined')
		$sentto = null;
		else
		$sentto = $('#ddlUser option:selected').val();
		if(parseInt($sentto)===0)
		{
			successAlert("Plase Select User to Forward");
			addInputFieldErrorCss('#ddlUser');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#ddlUser').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#ddlUser').focus();
			});
			return false;
		}
		else{
			removeInputFieldErrorCss('#ddlUser');
		}

		$gross_cropped = $('#gross_cropped_area').val();
		$gross_cropped_achv = $('#gross_cropped_area_achv').val();
		$total_income=$('#total_income').val();
		$total_income_achv=$('#total_income_achv').val();
		$cultivable_wasteland=$('#cultivable_wasteland').val();
		$cultivable_wasteland_achv=$('#cultivable_wasteland_achv').val();
		$degraded_land=$('#degraded_land').val();
		$degraded_land_achv=$('#degraded_land_achv').val();
		$rainfed=$('#rainfed').val();
		$rainfed_achv=$('#rainfed_achv').val();
		$others=$('#others').val();
		$others_achv=$('#others_achv').val();
		$protective_irrigation=$('#protective_irrigation').val();
		$protective_irrigation_achv=$('#protective_irrigation_achv').val();
		$assured_irrigation=$('#assured_irrigation').val();
		$assured_irrigation_achv=$('#assured_irrigation_achv').val();
		$no_irrigation=$('#no_irrigation').val();
		$no_irrigation_achv=$('#no_irrigation_achv').val();
		$single_crop=$('#single_crop').val();
		$single_crop_achv=$('#single_crop_achv').val();
		$double_crop=$('#double_crop').val();
		$double_crop_achv=$('#double_crop_achv').val();
		$multiple_crop=$('#multiple_crop').val();
		$multiple_crop_achv=$('#multiple_crop_achv').val();
		$no_crop=$('#no_crop').val();
		$no_crop_achv=$('#no_crop_achv').val();
		
	/*	
		var i = $('#tbodyAddPhysicalAchievement tr').length ;
		var activity=[];
		var ach=[];
		//alert('kedar'+i);
		if(i>0)
		{ 
			for(var x=0;x<parseInt(i);x++)
			{
				if(x==0)
				{
					if ($('#achievement'+$('#activity'+x).val()).val() === "")
					{
						successAlert("Achievement required");
						addInputFieldErrorCss('#achievement'+$('#activity'+x).val());
						$("#successok").click(function(){
							$('#popup').modal('hide');
							$('#achievement'+$('#activity'+x).val()).focus();
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
							$('#achievement'+$('#activity'+x).val()).focus();
						});
						return false;
					}
					else{
						removeInputFieldErrorCss('#achievement'+$('#activity'+x).val());
						var achievement = $('#achievement'+$('#activity'+x).val()).val();
						if(achievement.indexOf('.')>=0)
							var afterDecimalLength = (achievement.toString().split(".")[1]).length;
						else
							afterDecimalLength=0;
						if(achievement.indexOf('.')==0)
							achievement="0"+achievement;
						var result=regexp.test(achievement);
						//alert("1. result "+" : "+result+" : "+achievement.indexOf('.')+" : "+afterDecimalLength);
						if(!result || (achievement.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 )
						{
							successAlert('Please enter valid positive value upto two decimal point');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								$('#achievement'+$('#activity'+x).val()).focus();
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								$('#achievement'+$('#activity'+x).val()).focus();
							});
							return false;
						}
						activity.push($('#activity'+x).val());
						ach.push($('#achievement'+$('#activity'+x).val()).val());
					}
				}
				else{
					if ($('#achievement'+$('#activity'+x).val()).val() === "")
					{
						successAlert("Achievement required");
						addInputFieldErrorCss('#achievement'+$('#activity'+x).val());
						$("#successok").click(function(){
							$('#popup').modal('hide');
							$('#achievement'+$('#activity'+x).val()).focus();
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
							$('#achievement'+$('#activity'+x).val()).focus();
						});
						return false;
					}
					else{
						var achievement = $('#achievement'+$('#activity'+x).val()).val();
						if(achievement.indexOf('.')>=0)
							var afterDecimalLength = (achievement.toString().split(".")[1]).length;
						else
							afterDecimalLength=0;
						if(achievement.indexOf('.')==0)
							achievement="0"+achievement;
						var result=regexp.test(achievement);
						//alert("2 result "+" : "+result+" : "+achievement.indexOf('.')+" : "+afterDecimalLength);
						if(!result || (achievement.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 )
						{
							successAlert('Please enter valid positive value upto two decimal point');
							$("#successok").click(function(){
								$('#popup').modal('hide');
								$('#achievement'+$('#activity'+x).val()).focus();
							});  
							$(".close").click(function(){
								$('#popup').modal('hide');
								$('#achievement'+$('#activity'+x).val()).focus();
							});
							return false;
						}
						activity.push($('#activity'+x).val());
						ach.push($('#achievement'+$('#activity'+x).val()).val());
						removeInputFieldErrorCss('#achievement'+$('#activity'+x).val());
					}
				}
			}
		} */
		//alert(activity+" : "+ach);
		confirmAlert('Do you want to Forward the Baseline Updated Achievement Data ?');
		$("#ok").click(function(){
			$('#popup').modal('hide');
			$tbodyAddPhysicalAchievement = $('#tbodyAddPhysicalAchievement');
			$tbodyAddPhysicalAchievement.empty();
			
			$.ajax({  
				url:"forwardBaselineAchievementFromPIAtoWCDC",
				type: "post", 
				data:{pCode:$pCode,sentTo:$sentto,blsconf:$blscon,gross:$gross_cropped,grossachiv:$gross_cropped_achv,total:$total_income,totalachiv:$total_income_achv,cultivable:$cultivable_wasteland,cultivableachiv:$cultivable_wasteland_achv,degraded:$degraded_land,degradedachiv:$degraded_land_achv,rainfed:$rainfed,rainfedachiv:$rainfed_achv,others:$others,othersachiv:$others_achv,protective:$protective_irrigation,protectiveachiv:$protective_irrigation_achv,assured:$assured_irrigation,assuredachiv:$assured_irrigation_achv,noirri:$no_irrigation,noirriachiv:$no_irrigation_achv,single:$single_crop,singleachiv:$single_crop_achv,double:$double_crop,doubleachiv:$double_crop_achv,multiple:$multiple_crop,multipleachiv:$multiple_crop_achv,nocrop:$no_crop,nocropachiv:$no_crop_achv}, 
				error:function(xhr,status,er)	
				{
					console.log(er);
				},
				success:function(data) {
					if(data==='success')
					{
						alert("Baseline Updated Achievement Data Forwarded Successfully to SLNA/WCDC!");
						window.location.href='baseLineAchievement';
					}
					else{
						alert("Baseline Updated Achievement Data not Forwarded, Please try again !");
					}
					$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='baseLineAchievement';
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
					});
				}
			});
		});
			
	});
			
	
	$('#tbodyAapAchievement').on( 'click', 'a.action', function (e) {
		 // $('#loading').show();
		$achid= e.target.getAttribute('data-id');
		//alert($achid);

		$.ajax({  
            url:"checkForAlreadyForwardedBaselineAchievement",
            type: "post", 
            data: {achid:$achid},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				if(Object.keys(data).length>0)
				{
					for ( var key in data) 
					{
							if (data.hasOwnProperty(key)) 
							{
								//var d=data[key];
								$result="";
								//alert(data[key].action);
								if(data[key].action==='C' || data[key].status==='Completed')
								$result='already Completed by SLNA';
								if(data[key].action==='F' && data[key].currentlyat==='SLNA' && data[key].status!='Completed')
								$result='already Forwarded to SLNA';
								if(data[key].action==='F' && data[key].currentlyat==='WCDC' && data[key].status!='Completed')
								$result='already Forwarded to WCDC';
								if(data[key].action==='R' && data[key].currentlyat==='WCDC' && data[key].status!='Completed')
								$result='Rejected by SLNA';
								if(data[key].action==='R' && data[key].currentlyat==='PIA' && data[key].status!='Completed')
								$result='Rejected by SLNA/WCDC';
								//alert(data[key].action +', status = '+data[key].status+ ',  currentlyat = '+data[key].currentlyat +', usertype='+data[key].usertype);
								if(data[key].action==='F' && data[key].status!='Completed' && data[key].currentlyat===data[key].usertype)
								{
									$('#popupreport').modal('toggle');
									$('#popupreport').modal('show');
									$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Baseline Achievement</h5> <span id="errormessage" ></span>');
									$('#popupreport .modal-body').html('<label>Action:- </label><select name="ddlAction" id="ddlAction" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
									'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
									'<input type="hidden" value="'+$achid+'" name="achid" id="achid" />');
									$('#popupreport .modal-footer').html('');
									$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
									'<button id="submitaction" name="submitaction" class="btn btn-info"  >Submit</button>');
								}
								else{
									$('#popupreport').modal('toggle');
									$('#popupreport').modal('show');
									$('#popupreport #popupreporttitle').html('');
									$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Baseline Update Achievement</h5> <span id="errormessage" ></span>');
									$('#popupreport .modal-body').html('');
									$('#popupreport .modal-body').html('<p style="color:red;text-align:center">Baseline Update Achievement has been '+$result+' </p>');
									$('#popupreport .modal-footer').html('');
									$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
								}
							}
						}
					}
					else{
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Baseline Update Achievement</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="ddlAction" id="ddlAction" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$achid+'" name="achid" id="achid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info"  >Submit</button>');	
					}

				}
			});	
		
	});	

	$(document).on('click', '#submitaction', function(e) {
		//$('#loading').show();
		$action= $('#ddlAction option:selected').val();
		$remarks=$('#remarks').val();
		$achid=$('#achid').val();
		//alert($action+" : "+$remarks+" : "+$achid);
		if($remarks==="" && $action==='R' )
		{
			$('#errormessage').html("Please give remarks for rejection.");
			$('#errormessage').css("color","red");
			addInputFieldErrorCss('#remarks');	
			$("#remarks").focus();
			return false;
		}
		else{
			removeInputFieldErrorCss('#remarks');
		}

		$.ajax({  
            url:"forwardBaselineUpdateAchievementWCDC",
            type: "post",  
            data: {achid:$achid,action:$action,remarks:$remarks},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
            	$('#loading').hide();
            		//	alert('kdy1');
				console.log(data);
				$('#popupreport').modal('hide');
				if(data==='state')
				{
					if($action==='R')
						alert('Baseline Update Achievement is being rejected and back-forwarded to PIA.');
					else
						alert('Baseline Update Achievement data has been finalized and completed.');
				}
				if(data==='district')
				{
					if($action==='R')
						alert('Baseline Update Achievement is being rejected and back-forwarded to PIA.');
					else
						alert('Baseline Update Achievement data forwarded to SLNA for further approval.');	
				}   
				window.location.href="wcdcActionOnBaselineUpdateAchievement";
            }
		});
	});


$('#viewMovement').click(function(e) {
	var proj=[];
	var yr=[];
	$('#loading').show();
	$.ajax({  
            url:"viewBaselineAchMovement",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
			$('#loading').hide();
			var tbodyMovement = $('#tbodyAchievementMovement');
			tbodyMovement.empty();
			if(Object.keys(data).length>0)
			{
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						var d= data[key];
						for ( var k in d) 
						{
							if (d.hasOwnProperty(k)) 
							{
								if(parseInt(k)===0)
								tbodyMovement.append("<tr><td>"+key+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td><td style='text-align:center;'><a class='plandetail' data-id="+d[k].achievementid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].achievementid+" href='#'>view</a></td></tr>");
								else
								tbodyMovement.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td><td style='text-align:center;'><a class='plandetail' data-id="+d[k].achievementid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].achievementid+" href='#'>view</a></td></tr>");
							}
						}
					}
				}
		}
		else{
		tbodyMovement.append("<tr><td colspan='7' class='text-center'>Data not found !</td></tr>");
		}
	}
	});
	});
	
	
	$('#CompletedAchPlan').click(function(e) {
		var proj=[];
		var yr=[];
		$('#loading').show();
		$.ajax({  
            url:"CompletedBaselineAchievement",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				$('#loading').hide();
				var tbodyCMovement = $('#tbodyCAchievementMovement');
				tbodyCMovement.empty();
				if(Object.keys(data).length>0)
				{
					for ( var key in data) 
					{
						if (data.hasOwnProperty(key)) 
						{
							var d= data[key];
							for ( var k in d) 
							{
								if (d.hasOwnProperty(k)) 
								{
									if(parseInt(k)===0)
									tbodyCMovement.append("<tr><td>"+d[k].projectdesc+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td></tr>");
									else
									tbodyCMovement.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td></tr>");
								}
							}
						}
					}
				}
				else{
					tbodyCMovement.append("<tr><td colspan='6' class='text-center'>Data not found !</td></tr>");
				}
			}
		});
	});
	
	$('#tbodyAchievementMovement').on( 'click', 'a.plandetail', function (e) {
			var del = e.target.getAttribute('data-id');
			$('#loading').show();
			$.ajax({  
            	url:"getBaselineupdateAchievementDetails",
            	type: "post",  
            	data: {achievementid:del},
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
						$('#popupreport #popupreporttitle').html('Movement of Baseline Update Achievement');
						var i=1;
						if(Object.keys(data).length>0)
						{
							for ( var key in data) 
							{
								if (data.hasOwnProperty(key)) 
								{
									if(parseInt(i)===1)
									{
										i=0;
										$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key][0].projectdesc+ ',  FY: '+data[key][0].finyrdesc+' and Month: '+data[key][0].monthdesc+'   <br/><span style="color:red"> Current Status: '+data[key][0].status+"</span>");
									}
									var d=data[key];
									for ( var k in d) 
									{
										if (d.hasOwnProperty(k)) 
										{
										/*	if(parseInt(k)===0)
											tblData+="<tr><td>"+d[k].headdesc+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].unitdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].achievement+"</td></tr>";
											else
											tblData+="<tr><td></td><td>"+d[k].activitydesc+"</td><td>"+d[k].unitdesc+"</td><td></td>"+d[k].monthdesc+"<td>"+d[k].achievement+"</td></tr>";
										*/
				tblData+="<tr><td>1</td><td>Gross Cropped Area (in ha.)</td><td>"+d[k].gross_cropped_area+"</td><td>"+d[k].gross_cropped_area_achv+"</td></tr>";
				tblData+="<tr><td>2</td><td>Total Income (Rs.)</td><td>"+d[k].total_income+"</td><td>"+d[k].total_income_achv+"</td></tr>";
				tblData+="<tr><td>3</td><td>Cultivable Wasteland</td><td>"+d[k].cultivable_wasteland+"</td><td>"+d[k].cultivable_wasteland_achv+"</td></tr>";
				tblData+="<tr><td>4</td><td>Degraded Land</td><td>"+d[k].degraded_land+"</td><td>"+d[k].degraded_land_achv+"</td></tr>";
				tblData+="<tr><td>5</td><td>Rainfed Area</td><td>"+d[k].rainfed+"</td><td>"+d[k].rainfed_achv+"</td></tr>";
				tblData+="<tr><td>6</td><td>Other</td><td>"+d[k].others+"</td><td>"+d[k].others_achv+"</td></tr>";
				tblData+="<tr><td>7</td><td>Protective Irrigation</td><td>"+d[k].protective_irrigation+"</td><td>"+d[k].protective_irrigation_achv+"</td></tr>";
				tblData+="<tr><td>8</td><td>Assured Irrigation</td><td>"+d[k].assured_irrigation+"</td><td>"+d[k].assured_irrigation_achv+"</td></tr>";
				tblData+="<tr><td>9</td><td>No Irrigation</td><td>"+d[k].no_irrigation+"</td><td>"+d[k].no_irrigation_achv+"</td></tr>";
				tblData+="<tr><td>10</td><td>Single Crop</td><td>"+d[k].single_crop+"</td><td>"+d[k].single_crop_achv+"</td></tr>";
				tblData+="<tr><td>11</td><td>Double Crop</td><td>"+d[k].double_crop+"</td><td>"+d[k].double_crop_achv+"</td></tr>";
				tblData+="<tr><td>12</td><td>Multiple Crop</td><td>"+d[k].multiple_crop+"</td><td>"+d[k].multiple_crop_achv+"</td></tr>";
				tblData+="<tr><td>13</td><td>No Crop</td><td>"+d[k].no_crop+"</td><td>"+d[k].no_crop_achv+"</td></tr>";
				
										}	
									}
								}		
	  						}
						}
						else{
								tblData="<tr><td>Data not found !</td></tr>";
						}
						$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S.No.</th><th>Name of Baseline Indicator</th><th>Baseline Survey</th><th>As on Date</th>'+
						'</tr></thead><tbody>'+tblData+'</tbody></table>');
					}
				});
	});
	
	
	$('#tbodyAchievementMovement').on( 'click', 'a.movement', function (e) {
			var del = e.target.getAttribute('data-id');
			$('#loading').show();
			$.ajax({  
	            url:"getBaselineupdateAchievementMovementDetails",
	            type: "post",  
	            data: {achievementid:del},
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
						$('#popupreport #popupreporttitle').html('Movement of Baseline Update Achievement');
						var i=1;
						if(Object.keys(data).length>0)
						{
							for ( var key in data) 
							{
								if (data.hasOwnProperty(key)) 
								{
								if(parseInt(i)===1)
								{
								i=0;
								$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key][0].projectdesc+ ' ,  FY: '+data[key][0].finyrdesc+'  and  Month: '+data[key][0].monthdesc+'<br/><span style="color:red"> Current Status: '+data[key][0].status+"</span>");
								}
								var d=data[key];
								var action='';
								for ( var k in d) 
								{
									if (d.hasOwnProperty(k)) 
									{
										if(d[k].action==='F')
										{
											action='Forwarded';
										}
										else if(d[k].action==='R')
										{
											action='Rejected';
										}
										else if(d[k].action==='C')
										{
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
					}
					else{
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
	
		
		
});		
		