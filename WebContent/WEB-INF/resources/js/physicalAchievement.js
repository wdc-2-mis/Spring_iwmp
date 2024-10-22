/**
 * 
 */

/************************************************* Page Load Script Start ******************************************* */


$( document ).ready(function(){
	
	
	$('#btnGetActivity').click(function(e) {
		e.preventDefault();
		$pCode = $('#ddlProject option:selected').val();
		$head= new Array();
		$tbodyAddPhysicalAchievement = $('#tbodyAddPhysicalAchievement');
		$tbodyAddPhysicalAchievement.empty();
		$.ajax({  
            url:"getActivityWithTarget",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			$('#btnForward').removeClass('d-none');
		//$('#ddlUser').removeClass('d-none');
			console.log(data);
			$activityid=1;
			$headid=0;
			if(Object.keys(data).length>0){
				$('.error').html('');
				for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$('#lblStartDate').html(data[key].startdate);
		$('#lblEndDate').html(data[key].enddate);
		$('#monthId').val(data[key].startmonth);
		$('#finYr').val(data[key].finyr);
	
							/*var d=data[key];
							for ( var k in d) {
								if (d.hasOwnProperty(k)) {*/
								$activity="";
							//	alert('asset achive='+data[key].achievementthismonth);
								if(parseInt(data[key].assettype)>0){
									if(data[key].unitdesc==='nos' ){
									$achievement="<input type='text' disabled='true' id='achievement"+data[key].activitycd+"' name='achievementdisable' value='"+data[key].asset_achiev+"' class='form-control' pattern='^[0-9]+' onfocusin='numericOnly(event)'/>";
									$activity ="<input type='hidden' id='activity"+key+"' name='activity"+key+"' value='"+data[key].activitycd+"' /><a href='#' class='activity' data-id='"+data[key].activitycd+"' >"+data[key].activitydesc+"</a>";
									}
									else{
										
										$achievement="<input type='text' disabled='true' id='achievement"+data[key].activitycd+"' name='achievementdisable' value='"+data[key].asset_achiev+"' class='form-control' onfocusin='decimalCheck(event)' />";
										$activity ="<input type='hidden' id='activity"+key+"' name='activity"+key+"' value='"+data[key].activitycd+"' /><a href='#' class='activity' data-id='"+data[key].activitycd+"' >"+data[key].activitydesc+"</a>";
									}
							//	alert(data[key].unitdesc + ' kdy ');
								}else{
									if(data[key].unitdesc==='nos' ){
									$achievement="<input type='text' id='achievement"+data[key].activitycd+"' name='achievementmain' onkeyup='checkvalue(event)' value='"+data[key].asset_achiev+"' class='form-control achievementmain' pattern='^[0-9]+' onfocusin='numericOnly(event)'/>";
									$activity ="<input type='hidden' id='activity"+key+"' name='activity"+key+"' value='"+data[key].activitycd+"' />"+data[key].activitydesc;
									}
									else{
										$achievement="<input type='text' id='achievement"+data[key].activitycd+"' name='achievementmain' onkeyup='checkvalue(event)' value='"+data[key].asset_achiev+"' class='form-control achievementmain' onfocusin='decimalCheck(event)' />";
										$activity ="<input type='hidden' id='activity"+key+"' name='activity"+key+"' value='"+data[key].activitycd+"' />"+data[key].activitydesc;
									}
								//	alert(data[key].unitdesc + ' kdy1 ');
								}
									if($.inArray(data[key].headdesc, $head) === -1){
										$headid++;
										$activityid=1;
									$tbodyAddPhysicalAchievement.append("<tr><td>"+$headid+". "+data[key].headdesc+"</td><td>"+$headid+"."+$activityid+". "+$activity+"</td><td>"+data[key].unitdesc+"</td><td>"+data[key].qtyplanneduptoyear+"</td><td>"+data[key].qtyplannedthisyear+"</td><td>"+((data[key].qtyplannedthisyear)+(data[key].qtyplanneduptoyear))+"</td><td>"+data[key].achievementthisyr+"</td><td>"+$achievement+"</td></tr>");
									$head.push(data[key].headdesc);
								//	alert(data[key].unitdesc + ' kdy ');
									}
									else{
									$tbodyAddPhysicalAchievement.append("<tr><td></td><td>"+$headid+"."+$activityid+". "+$activity+"</td><td>"+data[key].unitdesc+"</td><td>"+data[key].qtyplanneduptoyear+"</td><td>"+data[key].qtyplannedthisyear+"</td><td>"+((data[key].qtyplannedthisyear)+(data[key].qtyplanneduptoyear))+"</td><td>"+data[key].achievementthisyr+"</td><td>"+$achievement+"</td></tr>");
								//	alert(data[key].unitdesc + ' kdy1 ');
									}/*}
									}*/
									$activityid++;
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
			if(Object.keys(data).length>0){
			$ddlUser.removeClass('d-none');
			$ddlUser.empty();
			$ddlUser.append('<option value="0">--Select User--</option>');
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$ddlUser.append('<option value='+key+'>'+data[key]+'</option>');
					
					}
				}
			}else{
			//$ddlUser.addClass('d-none');	
			}
			}
			
			});			
									
									
									
				}else{
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
				}
			
			}
			});
			
			
		
		});
		
/******************************* Activity Link Click ******************************************* */
	$assetArray = new Array();
	$('#tbodyAddPhysicalAchievement').on( 'click', 'a.activity', function (e) 
	{
		$activity = e.target.getAttribute('data-id');
		$pCode = $('#ddlProject option:selected').val();
		$assetParameterList=new Array();
		$assetParameterIdList=new Array();
		$assetList = new Array();
		$assetArray = new Array();
		$.ajax({  
            url:"getAssetofActivity",
            type: "post",  
            data: {activityCode:$activity,pCode:$pCode},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
					var tblData="";
					$('#popupreport').modal('toggle');
					$('#popupreport').modal('show');
					$('#popupreport #popupreporttitle').html('Work-Id of Selected Activity');
					var i=1;
					var param='';
					var paramid='';
					var paramtext='';
					$idval= new Array();
					$ach=0;
					if(Object.keys(data).length>0)
					{
						for ( var key in data) 
						{
							if (data.hasOwnProperty(key)) 
							{
								$assetParameterList=data[key].assetParameter.toString().split(",");
								//$assetParameterIdList=data[key].assetParameterId.toString().split(",");
								$assetParameterAchievement=data[key].assetParameterAchievement.toString().split(",");
							}
						}
						for ( var key in data) 
						{
							if (data.hasOwnProperty(key)) 
							{
								$assetArray.push(data[key].assetid);
								paramtext='';
								//for(var i=0; i<$assetParameterIdList.length;i++){
								//	paramid=$assetParameterIdList[i].toString().split(" ").join("");
								$achData=data[key].assetParameterAchievement;
								var array = $.map($achData, function(value, index) {
									return [value];
								});
								//alert(array.length);
								if(array.length>0)
								{
									for(var jk in $achData)
									{
										$ach+=$achData[jk];
										//if(jk===data[key].assetid+'_'+paramid)
										if($assetParameterList[0]==="Completed")
										{
											paramtext+='<td><input type="text" value="'+$achData[jk]+'" id="'+paramid+'_'+data[key].assetid+'" name="achievement" class="'+paramid+' form-control" readonly/></td>';
										}
										else{
											paramtext+='<td><input type="text" value="'+$achData[jk]+'" id="'+paramid+'_'+data[key].assetid+'" name="achievement" class="'+paramid+' form-control" /></td>';
										}
									}
								}
								else{
									if($assetParameterList[0]==="Completed")
									{
										if(data[key].unit==='nos')
										{
											paramtext+='<td><input type="text" value="0" id="'+paramid+'_'+data[key].assetid+'" name="achievement" class="'+paramid+' form-control" pattern="^[0-9]+" onfocusin="numericOnly(event)" readonly /></td>';
										}
										else
											paramtext+='<td><input type="text" value="0" id="'+paramid+'_'+data[key].assetid+'" name="achievement" class="'+paramid+' form-control" onfocusin="decimalCheck(event)" readonly /></td>';
									}
									else{
										if(data[key].unit==='nos')
										{
											paramtext+='<td><input type="text" value="0" id="'+paramid+'_'+data[key].assetid+'" name="achievement" class="'+paramid+' form-control" autocomplete="off" pattern="^[0-9]+" onfocusin="numericOnly(event)" onblur="callBlurFunction('+data[key].assetid+')"/></td>';
										}
										else
											paramtext+='<td><input type="text" value="0" id="'+paramid+'_'+data[key].assetid+'" name="achievement" class="'+paramid+' form-control" autocomplete="off" onfocusin="decimalCheck(event)" onblur="callBlurFunction('+data[key].assetid+')"/></td>';
									}
								}
								if(parseInt(key)===0)
								{
									tblData+="<tr><td>"+data[key].assetid+"</td><td>"+data[key].headdesc+"</td><td>"+data[key].activitydesc+"</td><td>"+data[key].bname+"</td><td>"+data[key].vname+"</td><td>"+data[key].nearby+"</td>"+paramtext+"</tr>";
								}
								else{
									tblData+="<tr><td>"+data[key].assetid+"</td><td></td><td>"+data[key].activitydesc+"</td><td>"+data[key].bname+"</td><td>"+data[key].vname+"</td><td>"+data[key].nearby+"</td>"+paramtext+"</tr>";
								}
							}		
						}
						tblData+="<tr><td colspan='5'></td><td>Total: </td><td id='total'>"+$ach.toFixed(2)+"</td></tr>";
					}
					else{
						tblData="<tr><td colspan='9' class='text-center'>No Work-Id Found !</td></tr>";
					}
					for(var k in $assetParameterList)
					{
						param+='<th>'+$assetParameterList[k]+'</th>';
				
					}
					$('#popupreport .modal-body').html('<form id="frmSaveAssetAchievement" ><center><table  style="width:100%">'+
						'<thead><tr><th>Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>'+
						'Block</th><th>Village</th><th>Land Identification</th>'+param+'</tr></thead><tbody>'+tblData+'</tbody></table></center>');
						
					$('#popupreport .modal-footer').html('<button type="button" id="btnOk" name="btnOk" data-id="'+$activity+'" class="btn btn-info" >OK</button><button type="button" id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button></form>');			
            }  // end success
		});
	});
	
/******************************** Activity Link Click End ******************************************* */


/*********************************** PopUp Ok Button Click ************************************* */

$(document).on('click', '#btnOk', function(e){
	$total=0;
	$value='';
	$activity = e.target.getAttribute('data-id');
	$finYr = $('#finYr').val();
	$monthId = $('#monthId').val();
	for(var k in $assetArray)
	{
		if($('#_'+$assetArray[k]).val()=='')
		{
			$id="#"+$('#_'+$assetArray[k]).attr('id');
			//alert('required '+$id);
			$($id).focus();
			return false;
		}
        $total =  (parseFloat($total) + parseFloat($('#_'+$assetArray[k]).val()));
        if($value==='')
            $value="_"+$assetArray[k]+"_"+$('#_'+$assetArray[k]).val();
        else
        	$value+=','+"_"+$assetArray[k]+"_"+$('#_'+$assetArray[k]).val();
	}	
	$.ajax({  
            url:"saveAssetAchievement",
            type: "post",  
            data: {value:$value,finYr:$finYr,monthId:$monthId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
            	$('#popupreport').modal('hide');
            }
	});
	$('#achievement'+$activity).val($total.toFixed(2));
	
});

/************************************ PopUp Ok Button Click End ************************************* */


/************************************** Button Save as Draft Click ********************************** */
$(document).on('click', '#btnForward', function(e) {
		e.preventDefault();
		var regexp= /^\d+(?:\.\d\d?)?$/;
		$pCode = $('#ddlProject option:selected').val();
		
		if(!$('#blsid').is(':checked')){
			
		//	successAlert("Please certified that Plot-wise data has been updated wherever there have been changes in the Plots.");
			addInputFieldErrorCss('#blsid');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#blsid').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#blsid').focus();
			});
		//	return false;
		$blscon = false;
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
		}
		//alert(activity+" : "+ach);
		confirmAlert('Do you want to Forward the Achievement Data ?');
		$("#ok").click(function(){
			$('#popup').modal('hide');
			$tbodyAddPhysicalAchievement = $('#tbodyAddPhysicalAchievement');
			$tbodyAddPhysicalAchievement.empty();
			
			$.ajax({  
				url:"forwardAchievementFromPIA",
				type: "post", 
				data:{pCode:$pCode,activity:""+activity,ach:""+ach,sentTo:$sentto, blsconf:$blscon}, 
				error:function(xhr,status,er)	
				{
					console.log(er);
				},
				success:function(data) {
					if(data==='success')
					{
						//successAlert("Achievement Forwarded Successfully to SLNA/WCDC!");
						alert("Achievement Forwarded Successfully to SLNA/WCDC!");
						window.location.href='physicalAchievement';
					}
					else{
						alert("Achievement not Forwarded, Please try again !");
					}
					$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='physicalAchievement';
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
					});
				}
			});
		});
			
	});
			
/************************************ Button Save as Draft Click End ******************************************* */

/************************************************************************** Action link click for WCDC *************************************** */
	$('#tbodyAapAchievement').on( 'click', 'a.action', function (e) {
		 // $('#loading').show();
$achid= e.target.getAttribute('data-id');
//alert($achid);

$.ajax({  
            url:"checkForAlreadyForwardedAchievement",
            type: "post", 
            data: {achid:$achid},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
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
			if(data[key].action==='F' && data[key].status!='Completed' && data[key].currentlyat===data[key].usertype){
				$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Achievement</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="ddlAction" id="ddlAction" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$achid+'" name="achid" id="achid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info"  >Submit</button>');
			}else{
				$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('');
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Achievement</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('');
						$('#popupreport .modal-body').html('<p style="color:red;text-align:center">Achievement has been '+$result+' </p>');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
			}
			
			}
			}
	}else{
	$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Achievement</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="ddlAction" id="ddlAction" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$achid+'" name="achid" id="achid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info"  >Submit</button>');	
	}
//	CKEDITOR.replace('remarks');
	}
	});	
		
		});
		
/************************************************* End ************************************** */

/************************************************** District Forward Click *********************************** */
	$(document).on('click', '#submitaction', function(e) {
		//$('#loading').show();
		$action= $('#ddlAction option:selected').val();
	//	$remarks= CKEDITOR.instances['remarks'].getData(); 
		$remarks=$('#remarks').val();
		$achid=$('#achid').val();
//alert($action+" : "+$remarks+" : "+$achid);
if($remarks==="" && $action==='R' ){
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
            url:"forwardAchievementByWCDC",
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
						if(data==='state'){
							if($action==='R')
							alert('Data of Achievement is being rejected and back-forwarded to PIA.');
							else
							alert('Achievement data has been finalized and completed.');
							//$('#errormessage').html("Data has been finalized and completed.");
							
						}
						if(data==='district'){
						//$('#errormessage').html("Data forwarded to SLNA for further approval.");
						if($action==='R')
							alert('Data of Achievement is being rejected and back-forwarded to PIA.');
						else
							alert('Achievement data forwarded to SLNA for further approval.');	
						}

						window.location.href="wcdcActionOnPhyAch";

						
            }
	});
	});
	
	/********************************************************************** End ******************************************************** */
	
	/**************************************************** View Movement Code ********************************************************* */

$('#viewMovement').click(function(e) {
	var proj=[];
	var yr=[];
	$('#loading').show();
	$.ajax({  
            url:"viewAchMovement",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyMovement = $('#tbodyAchievementMovement');
	tbodyMovement.empty();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyMovement.append("<tr><td>"+key+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td><td style='text-align:center;'><a class='plandetail' data-id="+d[k].achievementid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].achievementid+" href='#'>view</a></td></tr>");
			else
			tbodyMovement.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td><td style='text-align:center;'><a class='plandetail' data-id="+d[k].achievementid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].achievementid+" href='#'>view</a></td></tr>");
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
	
	/**************************************************** Completed Achievement Plan ********************************************************* */
	$('#CompletedAchPlan').click(function(e) {
	var proj=[];
	var yr=[];
	$('#loading').show();
	$.ajax({  
            url:"CompletedAchPlan",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyCMovement = $('#tbodyCAchievementMovement');
	tbodyCMovement.empty();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyCMovement.append("<tr><td>"+key+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td></tr>");
			else
			tbodyCMovement.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+d[k].remarks+"</td></tr>");
		}
		}
		
		}
			}
	}else{
		tbodyCMovement.append("<tr><td colspan='6' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	/************************************************************************** End ******************************************************************* */
	
	/************************************************************************** plandetail Link Click **************************************************** */
	
	$('#tbodyAchievementMovement').on( 'click', 'a.plandetail', function (e) {
	var del = e.target.getAttribute('data-id');
	//alert(del);
	$('#loading').show();
	$.ajax({  
            url:"getAchievementDetails",
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
						$('#popupreport #popupreporttitle').html('Movement of Physical Achievement');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key][0].projectdesc+ ' and FY: '+data[key][0].finyrdesc+'<br/><span style="color:red"> Current Status: '+data[key][0].status+"</span>");
			}
			var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].headdesc+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].unitdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].achievement+"</td></tr>";
			else
			tblData+="<tr><td></td><td>"+d[k].activitydesc+"</td><td>"+d[k].unitdesc+"</td><td></td>"+d[k].monthdesc+"<td>"+d[k].achievement+"</td></tr>";
		}	
		}
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Name of Head</th><th>Name of Activity</th><th>Unit</th><th>Month</th><th style="text-align:center;">Achievement during month '+
						'</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
							});
	});
	
	/******************************************************* End ************************************************************************************************ */
	
	/************************************************************************** movement Link Click **************************************************** */
	$('#tbodyAchievementMovement').on( 'click', 'a.movement', function (e) {
	var del = e.target.getAttribute('data-id');
	$('#loading').show();
	$.ajax({  
            url:"getAchievementMovementDetails",
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
						$('#popupreport #popupreporttitle').html('Movement of Physical Achievement');
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
	
	/******************************************************* End ************************************************************************************************ */
	
	/********************************************************** Field Validation ************************************************ */
	/*$(document).on('keypress', $("input[name='achievement']"), function(e) {
		var text = $("input[name='achievement']").val();
		if ((event.which != 46 || text.indexOf('.') != -1) &&
        ((event.which < 48 || event.which > 57) &&
          (event.which != 0 && event.which != 8))) {
        event.preventDefault();
      }
  
      
      if ((text.indexOf('.') != -1) &&
        (text.substring(text.indexOf('.')).length > 2) &&
        (event.which != 0 && event.which != 8) &&
        ($("input[name='achievement']")[0].selectionStart >= text.length - 2)) {
        event.preventDefault();
      }
	});
	
	$(document).on('keypress', $("input[name='achievementmain']"), function(e) {
		var text = $("input[name='achievementmain']").val();
		if ((event.which != 46 || text.indexOf('.') != -1) &&
        ((event.which < 48 || event.which > 57) &&
          (event.which != 0 && event.which != 8))) {
        event.preventDefault();
      }
  
      
      if ((text.indexOf('.') != -1) &&
        (text.substring(text.indexOf('.')).length > 2) &&
        (event.which != 0 && event.which != 8) &&
        ($("input[name='achievementmain']")[0].selectionStart >= text.length - 2)) {
        event.preventDefault();
      }
	});*/
	
	
	/********************************************************* End ************************************** */
	
	
	});
	
function callBlurFunction(tag){
	$total =0;
	$("input[name='achievement']").each(function(e){
		$total +=parseFloat($(this).val());
	});
	$('#total').html($total);
	
}

function functioncheck(val){
	
$("input[name='achievement']").each(function(e){
		var text = $(this).val();
		if ((event.which != 46 || text.indexOf('.') != -1) &&
        ((event.which < 48 || event.which > 57) &&
          (event.which != 0 && event.which != 8))) {
        event.preventDefault();
      }
  
      
      if ((text.indexOf('.') != -1) &&
        (text.substring(text.indexOf('.')).length > 2) &&
        (event.which != 0 && event.which != 8) &&
        ($(this)[0].selectionStart >= text.length - 2)) {
        event.preventDefault();
      }
	});
}

function checkvalue(e){
	//if the letter is not digit then display error and don't type anything
	$(".achievementmain").inputFilter(function(value) { //alert(value);
		  return /^\d*[.,]?\d{0,2}$/.test(value); });
	  //^\s*(?=.*[1-9])\d*(?:\.\d{1,2})?\s*$
	   
		
}

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
/**************************************************** Page Load Script End ********************************************* */