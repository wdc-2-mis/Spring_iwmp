/**
 * 
 */

function validate(input){
  if(/^\s/.test(input.value))
    input.value = '';
}

/************************************************* Page load script start *********************************************** */
	$( document ).ready(function(){ //Document load script start
	$activityid= new Array();
	$draftactivityid= new Array();
	$assetid = new Array();
var index=0;
var count=0;
var oldactivity=new Array();
$('#loading').hide();
/************************************************* Auto Refresh Script ************************************ */


    $(function() {
      // setInterval(doRefresh, 2000);
    });

/***************************************************** End ************************************************* */

/**************************************************** Create Asset Link Click ***************************************** */
$('#createAssetId').click(function(e) {
	$listActivityHeadWiseTbody = $('#listActivityHeadWiseTbody');
	$listActivityHeadWiseTbody.empty();	
	$tbodyListActivityAssetId = $('#tbodyListActivityAssetId');
	$tbodyListActivityAssetId.empty();
	$tbodyTempListActivityAssetId = $('#tbodyTempListActivityAssetId');
	$tbodyTempListActivityAssetId.empty();
	
	$ddlYear = $('#year');
	$ddlYear.empty();
    $ddlYear.append('<option value=""> --Select-- </option>');
	$('#project').val("");
		
	});

/*************************************************** End *********************************************************** */
	/************************************************************************** Create Multiple Asset Id Link Click **************************************************** */	
	$(document).on( 'click', 'a.createAssetIdLinkMultiple', function (e) {
	$linkValue = e.target.getAttribute('data-id').split(',');
	$activityCode = $linkValue[0];
	$aapid = $linkValue[1];
	$plan=$linkValue[2];
	$assetCreated=$linkValue[3];
	$coverarea=$linkValue[4];
	console.log($activityCode+" : "+$aapid);
	$pCode=$('#project option:selected').val();
	$yrCode=$('#scheme option:selected').val();
	$activityCount=0;
	for(var i=0; i <$draftactivityid.length;i++){
		if(parseInt($draftactivityid[i])===parseInt($activityCode))
		$activityCount++;
	}
	console.log('aya');
	console.log("count "+count+" : plan"+$plan+" : index"+index+' : DrftActivityChk'+$draftactivityid+" : act"+$activityCode+" : occ"+$activityCount);
	//alert("count "+count+" : plan"+$plan+" : index"+index+' : DrftActivityChk'+$draftactivityid+" : act"+$activityCode+" : occ"+$activityCount+" : oldact "+oldactivity);
	
	//if(count!=$plan && (parseInt($plan)-$activityCount)>index)
	//if(oldactivity!=($activityCode+"#"+count) && $plan>=count )
	//{
		//count=0;
		$('#loading').show();
	$.ajax({  
            url:"getAssetIdForCreationEpaLivProd",
            type: "post",  
            data: {activityCode:$activityCode,pCode:$pCode,yrCode:$yrCode},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				$('#loading').hide();
				var ddlBlock="";
				var ddlSubActivity="";
				$tbodyListActivityAssetId = $('#tbodyListActivityAssetId');
				//$tbodyListActivityAssetId.empty();		
				if(Object.keys(data).length>0){
					
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							var d=data[key];
							for ( var k in d) {
								if (d.hasOwnProperty(k)) {
									if ( $.inArray(($activityCode+$pCode+$yrCode), $activityid) === -1){
										$activityid.push($activityCode+$pCode+$yrCode);
										//index=$.inArray( ($activityCode+$pCode+$yrCode), $activityid );
										}else{
									//alert('already added activity');
										}
										console.log($activityid+" : "+index);
										ddlBlock="<select id='ddlBlock"+index+"' name='ddlBlock"+index+"' class='ddlBlock form-control' required>";
										ddlBlock+="<option value=''>--Select--</option>";
										var x=d[k].blocklist;
										for ( var i in x) {
											if (x.hasOwnProperty(i)) {
												ddlBlock+="<option value='"+x[i].bcode+"'>"+x[i].blockName+"</option>";
												}	
										}
										ddlBlock+="</select>";
										if($plan==index){
											alert('No. of work can not be greater then No. of activities');
											return false;
										} 
										else
										{										
											if($yrCode==='production')
											{
												//if($activityCode==10 || $activityCode==11 || $activityCode==13 || $activityCode==14 || $activityCode==16 || $activityCode==18 || $activityCode==23 || $activityCode==24)
												if($coverarea==='Y')
												{
													$('#areaco').removeClass('d-none');
													$tbodyListActivityAssetId.append("<tr id='tr"+index+"'><td>"+(index+1)+" </td>  <td><input type='hidden' id='aapid"+index+"' name='aapid"+index+"' value='"+$aapid+"' class='form-control' required /><input type='hidden' id='activity"+index+"' name='activity"+index+"' value='"+$activityCode+"' class='form-control' required />"+d[k].activitydesc+"</td><td>"+ddlBlock+"</td><td><select id='ddlVillage"+index+"' name='ddlVillage"+index+"' class='form-control' required><option value=''>--Select--</option></select></td><td> <input type='text' id='areacovered"+index+"' name='areacovered"+index+"' onfocusin='decimalCheck(event)'  required /> </td><td> <input type='text' id='nearby"+index+"' name='nearby"+index+"' oninput='validate(this)' required /> </td><td><a href='#' data-id='"+($activityCode+$pCode)+"' class='delete'>delete</a></td></tr>");
												}
												else{
													$('#areaco').removeClass('d-none');
													$tbodyListActivityAssetId.append("<tr id='tr"+index+"'><td>"+(index+1)+" </td>  <td><input type='hidden' id='aapid"+index+"' name='aapid"+index+"' value='"+$aapid+"' class='form-control' required /><input type='hidden' id='activity"+index+"' name='activity"+index+"' value='"+$activityCode+"' class='form-control' required />"+d[k].activitydesc+"</td><td>"+ddlBlock+"</td><td><select id='ddlVillage"+index+"' name='ddlVillage"+index+"' class='form-control' required><option value=''>--Select--</option></select></td><td> <input type='hidden' id='areacovered"+index+"' name='areacovered"+index+"' value='0'  /> </td><td> <input type='text' id='nearby"+index+"' name='nearby"+index+"' oninput='validate(this)' required /> </td><td><a href='#' data-id='"+($activityCode+$pCode)+"' class='delete'>delete</a></td></tr>");
												}
									
											}
											else{
									      
									      		$('#areaco').addClass('d-none');	
									      		$tbodyListActivityAssetId.append("<tr id='tr"+index+"'><td>"+(index+1)+" </td>  <td><input type='hidden' id='aapid"+index+"' name='aapid"+index+"' value='"+$aapid+"' class='form-control' required /><input type='hidden' id='activity"+index+"' name='activity"+index+"' value='"+$activityCode+"' class='form-control' required />"+d[k].activitydesc+"</td><td>"+ddlBlock+"</td><td><select id='ddlVillage"+index+"' name='ddlVillage"+index+"' class='form-control' required><option value=''>--Select--</option></select></td><td> <input type='text' id='nearby"+index+"' name='nearby"+index+"' oninput='validate(this)' required /> </td><td><a href='#' data-id='"+($activityCode+$pCode)+"' class='delete'>delete</a></td></tr>");
											}
										}
										
								
								count++;
								index++;
								}	
							}
						}		
					}
				}else{
						$tbodyListActivityAssetId.append("<tr><td>Data not found !</td></tr>");
				}
				$('#draftSave').removeClass('d-none');
			//$tbodyListActivityAssetId.append("<button class='btn btn-info' id='btnSaveAsDraft' name='btnSaveAsDraft' >Save as Draft</button>");
			}
		});
		//}
		
		
	});
	
	/******************************************************** End ***************************************** */
	
	/************************************************************************** Create Single Asset Id Link Click **************************************************** */	
	$(document).on( 'click', 'a.createAssetIdLinkSingle', function (e) {
	$linkValue = e.target.getAttribute('data-id').split(',');
	$(this).hide();
	$activityCode = $linkValue[0];
	$aapid = $linkValue[1];
	$plan=$linkValue[2];
	$coverarea=$linkValue[4];
	console.log($activityCode+" : "+$aapid);
	$pCode=$('#project option:selected').val();
	$yrCode=$('#scheme option:selected').val();
	console.log("count "+count+" : plan"+$plan+" : index"+index);
	$('#loading').show();
	$.ajax({  
            url:"getAssetIdForCreationEpaLivProd",
            type: "post",  
            data: {activityCode:$activityCode,pCode:$pCode,yrCode:$yrCode},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				$('#loading').hide();
				var ddlBlock="";
				$tbodyListActivityAssetId = $('#tbodyListActivityAssetId');
				//$tbodyListActivityAssetId.empty();		
				if(Object.keys(data).length>0){
					
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							var d=data[key];
							for ( var k in d) {
								if (d.hasOwnProperty(k)) {
									if ( $.inArray(($activityCode+$pCode), $activityid) === -1){
										$activityid.push($activityCode+$pCode);
										//index=$.inArray( ($activityCode+$pCode+$yrCode), $activityid );
										}else{
									//alert('already added activity');
										}
										console.log($activityid+" : "+index);
										ddlBlock="<select id='ddlBlock"+index+"' name='ddlBlock"+index+"' class='ddlBlock form-control' required>";
										ddlBlock+="<option value=''>--Select--</option>";
										var x=d[k].blocklist;
										for ( var i in x) {
											if (x.hasOwnProperty(i)) {
												ddlBlock+="<option value='"+x[i].bcode+"'>"+x[i].blockName+"</option>";
												}	
										}
										ddlBlock+="</select>";
										
										$tbodyListActivityAssetId.append("<tr id='tr"+index+"'><td><input type='hidden' id='aapid"+index+"' name='aapid"+index+"' value='"+$aapid+"' class='form-control' required /><input type='hidden' id='activity"+index+"' name='activity"+index+"' value='"+$activityCode+"' class='form-control' required />"+d[k].activitydesc+"</td><td>"+ddlBlock+"</td><td><select id='ddlVillage"+index+"' name='ddlVillage"+index+"' class='form-control' required><option value=''>--Select--</option></select></td><td> <input type='text' id='nearby"+index+"' name='nearby"+index+"' required /> </td><td><a href='#' data-id='"+($activityCode+$pCode)+"' class='delete'>delete</a></td></tr>");
										
								
								index++;
								}	
							}
						}		
					}
				}else{
						$tbodyListActivityAssetId.append("<tr><td>Data not found !</td></tr>");
				}
				$('#draftSave').removeClass('d-none');
			//$tbodyListActivityAssetId.append("<button class='btn btn-info' id='btnSaveAsDraft' name='btnSaveAsDraft' >Save as Draft</button>");
			}
		});
	});
	
	/******************************************************** End ***************************************** */
	
	/************************************************** Project Dropdown change ************************************ */
		$('#project').on('change', function(e) {
		e.preventDefault();
		clearField();
		$tbodyListActivityAssetId = $('#tbodyListActivityAssetId');
		$tbodyListActivityAssetId.empty();	
		$listActivityHeadWiseTbody = $('#listActivityHeadWiseTbody');
		$listActivityHeadWiseTbody.empty();
		$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
		$tbodyfinalListActivityAssetId.empty();
		$pCode=$('#project option:selected').val();
		$.ajax({  
            url:"getYearForAssetId",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						
						$ddlYear = $('#year');
						$ddlYear.empty();
        				$ddlYear.append('<option value=""> --Select-- </option>');
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
		/********************************************************************** End ***************************************************************************** */
		
		/************************************************** Year Dropdown change ************************************ */
		
		$('#activity').on('change', function(e) {
		e.preventDefault();
		clearField();
		$('#loading').show();
		$tbodyListActivityAssetId = $('#tbodyListActivityAssetId');
		$tbodyListActivityAssetId.empty();	
		$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
		$tbodyfinalListActivityAssetId.empty();
		getDraftPlanDetailsByProjYr();
		getPlanDetailsByProjYr();
	
		});
		
		
		$('#scheme').on("change" ,function () { 
			$('#loading').show();
		$pCode=$('#project option:selected').val();
		$yrCode=$('#scheme option:selected').val();
		$.ajax({  
            url:"getActivityEPALivelihoodProduction",
            type: "post",  
            data: {projId:$pCode, headcd:$yrCode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				var $activity = $('#activity');
				$activity.empty();
        		$activity.append('<option value="">--Select Activity--</option>');
        		
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						 $activity.append('<option value='+key+'>' +data[key] + '</option>');
					}
				}
			}
		});
	});	
		
		
		
		
		
		
		
		
		
		
		
		
		/********************************************************************** End ***************************************************************************** */

/********************************************** Delete link click for row remove only ************************************************ */
$('#tbodyListActivityAssetId').on( 'click', 'a.delete', function (e) {
	var del = e.target.getAttribute('data-id');
	var i=0;
	//alert(index);
    if (index >=0) {
		var rowId = $(this).closest('tr').attr('id') ;
		var val= $('#assetid'+parseInt(rowId.match(/\d+/))).val();
		$(this).closest('tr').remove();
		$activityid=arrayRemove($activityid,del);
	for(var x=parseInt(rowId.match(/\d+/));x<parseInt(index);x++){
		$('#assetid'+(parseInt(x)+1)).attr("id","assetid"+x);
		$('#assetid'+x).val(parseInt(val)+parseInt(i));
		$('#aapid'+(parseInt(x)+1)).attr("id","aapid"+x);
		//$('#aapid'+x).val(parseInt(val)+parseInt(i));
		$('#activity'+(parseInt(x)+1)).attr("id","activity"+x);
		//$('#activity'+x).val(parseInt(val)+parseInt(i));
		$('#ddlBlock'+(parseInt(x)+1)).attr("id","ddlBlock"+x);
		$('#ddlVillage'+(parseInt(x)+1)).attr("id","ddlVillage"+x);
		$("#tr"+(parseInt(x)+1)).attr("id","tr"+x);
		i++;
	}
	index--;
	count--;
	if(index===0)
	$('#draftSave').addClass('d-none');
	console.log(index);
	getPlanDetailsByProjYr();
    }
	e.preventDefault();
    return false;
});

/********************************************** Delete link click for row remove from database as well  ************************************************ */
$('#tbodyTempListActivityAssetId').on( 'click', 'a.assetid', function (e) {
	$value = e.target.getAttribute('data-id').split(',');
	$asset = $value[0];
	$activitycd = $value[1];
	$finyr = $('#scheme option:selected').val();
	$projcd = $('#project option:selected').val();
	confirmAlert('Do you want to delete the record ?');
	$("#ok").click(function(){
		$('#popup').modal('hide');
		$draftactivityid=arrayRemove($draftactivityid,$activitycd);
		$assetid=arrayRemove($assetid,$asset);
		$.ajax({  
            url:"deleteAssetEPALivelihoodProducttion",
            type: "post", 
			data:{tempassetid:$asset,finyr:$finyr,projcd:$projcd}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				if(data==='success'){
					alert('Work-Id delete Successfully !');
					getPlanDetailsByProjYr();
					getDraftPlanDetailsByProjYr();
				}
				else{
					//alert('fail');
				}
			}
		});
	});
	e.preventDefault();
    return false;
});

/************************************************** Save as Draft ************************************ */
		$('#draftSave').on('click', function(e) {
		e.preventDefault();
		$nearby="";
		$aapid="";
		$block="";
		$village="";
		$activity="";
		$finyr = $('#scheme option:selected').val();
		$projcd = $('#project option:selected').val();
		$status='D';
		$areacov="";
	for(var x=0;x<parseInt(index);x++){
		if($('#assetid'+x).val()==""){
			successAlert('Please fill Work-Id');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#assetid'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#assetid'+x).focus();
			});
			return false;
		}
		if($('#ddlBlock'+x+' option:selected').val()==""){
			successAlert('Please select block');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#ddlBlock'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#ddlBlock'+x).focus();
			});
			return false;
		}else{
			if($block.length===0)
			$block=$('#ddlBlock'+x+' option:selected').val();
			else
			$block=$block+","+$('#ddlBlock'+x+' option:selected').val();
		}
		if($('#ddlVillage'+x+' option:selected').val()==""){
			successAlert('Please select village');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#ddlVillage'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');				
			$('#ddlVillage'+x).focus();
			});
			return false;
		}else{
			if($village.length===0)
			$village=$('#ddlVillage'+x+' option:selected').val();
			else
			$village=$village+","+$('#ddlVillage'+x+' option:selected').val();
		}
			if($aapid.length===0)
			$aapid=$('#aapid'+x).val();
			else
			$aapid=$aapid+","+$('#aapid'+x).val();
			if($activity.length===0)
			$activity=$('#activity'+x).val();
			else
			$activity=$activity+","+$('#activity'+x).val();
			
			var TCode = document.getElementById('nearby'+x).value;
    		var regex = new RegExp("^[a-zA-Z0-9 \s]+$");
    		//alert(regex.test( TCode ));
    		if( !regex.test( TCode ) ) {
        	
        	//	alert('Only input alphanumeric');
        	//	$('#nearby'+x).focus();
        		successAlert('Please Enter Correct Land Identification!');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#nearby'+x).focus();
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');				
				$('#nearby'+x).focus();
				});
        		return false;
    		}
			
			if($('#nearby'+x).val()==""){
				successAlert('Please fill Land Identification');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#nearby'+x).focus();
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');				
				$('#nearby'+x).focus();
				});
				return false;
			}
			else{
				if($nearby.length===0)
					$nearby=$('#nearby'+x).val();
				else
					$nearby=$nearby+","+$('#nearby'+x).val();
			}
			
			if($finyr==='production')
			{
				/*if($('#areacovered'+x).val()==""){
					successAlert('Please fill Area covered (in Ha)');
					$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#areacovered'+x).focus();
					});  
					$(".close").click(function(){
					$('#popup').modal('hide');				
					$('#areacovered'+x).focus();
					});
					return false;
				}*/
			//	else{
			//	alert('kdy ='+$areacov.length);
					if($areacov.length===0)
						$areacov=$('#areacovered'+x).val();
					else
						$areacov=$areacov+","+$('#areacovered'+x).val();
				//}
			}
		
	}
	
	console.log('aapid: '+$aapid+' finyr '+$finyr+' projid '+$projcd+' activity '+$activity+' vcode '+$village+'near: '+$nearby+' areacov: '+$areacov);
	$('#loading').show();
		$.ajax({  
            url:"saveEPALivelihoodProducttionAssetAsDraft",
            type: "post", 
			data:{finyr:$finyr,projcd:$projcd,aapid:$aapid,activity:$activity,vcode:$village,near:$nearby, areacov:$areacov}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						console.log(data);
						$('#loading').hide();
						if(data==='success'){
							successAlert('Work-Id Saved as Draft Successfully !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='createLivelihoodPrdouctionEPAWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='createLivelihoodPrdouctionEPAWorkId';
			});
						}else{
							successAlert('Unable to Save Work-Id as Draft !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='createLivelihoodPrdouctionEPAWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='createLivelihoodPrdouctionEPAWorkId';
			});
						}
	}
	});
		});
		/********************************************************************** End ***************************************************************************** */
/************************************************** Complete Button Click ************************************
		$('#forwardToWCDC').on('click', function(e) {
		e.preventDefault();
		$('#loading').show();
		console.log($assetid);
		$finyr = $('#scheme option:selected').val();
		$projcd = $('#project option:selected').val();
		
		alert('Do you want to complete the record ?');
	//	$("#ok").click(function(){
		//	$('#popup').modal('hide');
			
		$.ajax({  
            url:"completeAssetEPALivelihoodProducttion",
            type: "post",  
            data: {tempassetid:$assetid.toString(),finyr:$finyr,projcd:$projcd},
            error:function(xhr,status,er){
                console.log(er);
				},
				success: function(data) {
				console.log(data);
				$('#loading').hide();
			successAlert('Work-Id Completed Successfully !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='createLivelihoodPrdouctionEPAWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='createLivelihoodPrdouctionEPAWorkId';
			});
			}
		});
		
		//});
		
		});   */
		
		
		$('#forwardToWCDC').on('click', function(e) {
			e.preventDefault();
			$('#loading').show();
			console.log($assetid);
			$finyr = $('#scheme option:selected').val();
			$projcd = $('#project option:selected').val();
			$user = $('#user option:selected').val();
			if($user==='' || $user===null){
			//	alert('Please select User to forward');
				successAlert('Please select User to forward !');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				});
				$('#user').focus();
				return false;
			}
			$.ajax({  
	            url:"forwardAssetEPALivelihoodProducttionPIA",
	            type: "post",  
	            data: {tempassetid:$assetid.toString(),sentto:$user,finyr:$finyr,projcd:$projcd},
	            error:function(xhr,status,er){
	                console.log(er);
					$('.error').append(' There is some error please try again !');
				successAlert('Work-Id Forward Failed !');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				window.location.href='createLivelihoodPrdouctionEPAWorkId';
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				window.location.href='createLivelihoodPrdouctionEPAWorkId';
				});
	            },success: function(data) {
					console.log(data);
					$('#loading').hide();
				successAlert('Work-Id Forwarded to WCDC Successfully !');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				window.location.href='createLivelihoodPrdouctionEPAWorkId';
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				window.location.href='createLivelihoodPrdouctionEPAWorkId';
				});
					}
					});
			});
			
		
		$('#forwardToSLNA').on('click', function(e) {
		e.preventDefault();
		$('#loading').show();
		$.ajax({  
            url:"forwardAssetPIA",
            type: "post",  
            data: {tempassetid:$assetid.toString(),sentto:0},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
			successAlert('Work-Id Forward Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='createAssetId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='createAssetId';
			});
            },success: function(data) {
				console.log(data);
				$('#loading').hide();
				successAlert('Work-Id Forwarded to WCDC Successfully !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='createAssetId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='createAssetId';
			});
				}
				});
		});
		
		/********************************* End *********************************************** */
		
		/*********************************** List of Completed Asset ************************************* */
		$('#getCompleteAsset').on('click', function(e) {
		e.preventDefault();
		$schemed = $('#head option:selected').val();
		$projcd = $('#projectpi option:selected').val();
	//	alert('proj='+$projcd+' , scheme='+$schemed)
		$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
		$tbodyfinalListActivityAssetId.empty();
		$('#loading').show();
		$('#diproject').val('0');
		$('#districtslna').val('0');
		$('#disproject').val('0');
		var i = 1;
			//alert("starting");
			$.ajax({  
            url:"getCompletedAssetList",
            type: "POST",
            data:{scheme:$schemed,projcd:$projcd},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           console.log(data);
           $('#loading').hide();
				var tblData="";
					$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
						$tbodyfinalListActivityAssetId.empty();
						if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td>"+d[k].projdesc+"</td><td>"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td></tr>";
			i++
			}else
			tblData+="<tr><td></td><td></td><td>"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td></tr>";
		}	
		}
							}
							}	
						
	}
           else{
		tblData="<tr><td></td><td colspan='6' class='text-center'>Data not found !</td></tr>";
	}
	$tbodyfinalListActivityAssetId.append(tblData);
            }
            });
            });
            /********************************* End *********************************************** */
        
$('#districtslna').on('change', function(e) {
		e.preventDefault();
		
		$dCode=$('#districtslna option:selected').val();
		
		$.ajax({  
            url:"getprojectfordis",
            type: "post", 
			data:{dCode:$dCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			console.log(data);
			$disproject = $('#disproject');
						$disproject.empty();
        				$disproject.append('<option value=""> --Select-- </option>');
                        $disproject.append('<option value="0"> --ALL-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							$disproject.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
			}
			});
			});
			
		$('#disproject').on('change', function(e) {
		e.preventDefault();
		var i = 1;
		$dCode=$('#districtslna option:selected').val();
		$pCode=$('#disproject option:selected').val();
		// alert($dCode + $pCode);
		if($pCode==='' || $pCode===null){
		//	alert('Please select User to forward');
			successAlert('Project Should not be blank!');
			}	
		$.ajax({  
            url:"getalreadyassetdata",
            type: "post", 
			data:{dCode:$dCode,pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
success:function(data) {
	console.log(data);
				var tblData="";
					$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
						$tbodyfinalListActivityAssetId.empty();
						if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td>"+d[k].projdesc+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td></tr>";
			i++
			}else
			tblData+="<tr><td></td><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td></tr>";
		}	
		}
							}
							}	
						
	}
           else{

		tblData="<tr><td></td><tdcolspan='8' class='text-center'>Data not found !</td></tr>";

	}
	$tbodyfinalListActivityAssetId.append(tblData);
            }
	});
	});
	
	$('#diproject').on('change', function(e) {
		e.preventDefault();
		var i = 1;
		$pCode=$('#diproject option:selected').val();
		// alert($dCode + $pCode);
			
		$.ajax({  
            url:"getalreadyassetdidata",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
success:function(data) {
	console.log(data);
				var tblData="";
					$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
						$tbodyfinalListActivityAssetId.empty();
						if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td>"+d[k].projdesc+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td></tr>";
			i++
			}else
			tblData+="<tr><td></td><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td></tr>";
		}	
		}
							}
							}	
						
	}
           else{

		tblData="<tr><td></td><tdcolspan='8' class='text-center'>Data not found !</td></tr>";

	}
	$tbodyfinalListActivityAssetId.append(tblData);
            }
	});
	});
            
		/*********************************** List of Forwarded Asset ************************************* */
		
		$('#viewforwardedAssetEPL').click(function(e) {
			$('#loading').show();
			var tbodyForwardedListActivityAssetId = $('#tbodyForwardedListActivityAssetId');
	tbodyForwardedListActivityAssetId.empty();
	$head = $('#heads option:selected').val();
	$projcd = $('#projectfwd option:selected').val();
	
	
	/*var projectFwd = $('#projectfwd');
	projectFwd.empty();
	projectFwd.append('<option value="0">--All--</option>');
	$.ajax({  
            url:"getProjectForForwardedAsset",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
		
		projectFwd.append('<option value='+key+'>'+data[key]+'</option>');
		}
			}
	}else{
		//projectFwd.append('<option value="'+key+'">"'+data[key]+'"</option>');
	}
	}
	});*/
	$.ajax({  
            url:"viewforwardedAssetEPL",
            type: "post",  
            data: {head:$head, projcd:$projcd},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyForwardedListActivityAssetId.append("<tr><td>"+key+"</td><td>"+d[k].tempassetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].forwardedTo+"</td></tr>");
			else
			tbodyForwardedListActivityAssetId.append("<tr><td></td><td>"+d[k].tempassetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].forwardedTo+"</td></tr>");
		}
		}
		
		}
			}
	}else{
		tbodyForwardedListActivityAssetId.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	
	/************************************************************************** End ******************************************************************* */
	
	/*********************************** List of Rejected Asset ************************************* */
		
		$('#viewRejectedAsset').click(function(e) {
			$('#loading').show();
			var tbodyRejectedListActivityAssetId = $('#tbodyRejectedListActivityAssetId');
	tbodyRejectedListActivityAssetId.empty();
	$.ajax({  
            url:"viewrejectedAsset",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyRejectedListActivityAssetId.append("<tr><td>"+key+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].tempassetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].subactivitydesc+"</td><td>"+d[k].remarks+"</td><td>"+d[k].rejectedBy+"</td></tr>");
			else
			tbodyRejectedListActivityAssetId.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].tempassetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].subactivitydesc+"</td><td>"+d[k].remarks+"</td><td>"+d[k].rejectedBy+"</td></tr>");
		}
		}
		
		}
			}
	}else{
		tbodyRejectedListActivityAssetId.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	
	/************************************************************************** End ******************************************************************* */
	
	
	/*********************************** List of Pending Asset ************************************* */
		$('#listOfAsset').on('click', function(e) {
		e.preventDefault();
		$pendingProject = $('#pendingProject');
		$pendingProject.val("0");
		$pendingDistrict = $('#pendingDistrict');
		$pendingDistrict.val("0");
            });
            /********************************* End *********************************************** */

/************************************** Select All script for WCDC ******************************* */
$('#chkSelectAll').on('click',function(){
	$chkValue=0;
        if(this.checked){
            $('.chkIndividual').each(function(){
                this.checked = true;
$chkValue++;
            });
        }else{
             $('.chkIndividual').each(function(){
                this.checked = false;
            });
$chkValue=0;
        }
if($(".chkIndividual").prop('checked')){
$('#btnWCDCForward').removeClass('disabled');
	$('#btnWCDCReject').removeClass('disabled');	
	$('#btnWCDCForward').removeAttr("disabled");
	$('#btnWCDCReject').removeAttr("disabled");
}else{
	$('#btnWCDCForward').addClass('disabled');
	$('#btnWCDCReject').addClass('disabled');
	$('#btnWCDCForward').attr('disabled', 'disabled');
	$('#btnWCDCReject').attr('disabled', 'disabled');
}
    });
    
    
 $("input:checkbox").not('#chkSelectAll').each(function() {
	$chkValue= 0;
$(this).on('click',function(){ //alert($(this).val());
        if($('.chkIndividual:checked').length == $('.chkIndividual').length){
            $('#chkSelectAll').prop('checked',true);
        }else{
            $('#chkSelectAll').prop('checked',false);
        }
if(this.checked)
$chkValue++;
if(!this.checked)
$chkValue--;
if($chkValue>0){
$('#btnWCDCForward').removeClass('disabled');
	$('#btnWCDCReject').removeClass('disabled');
	$('#btnWCDCForward').removeAttr("disabled");
	$('#btnWCDCReject').removeAttr("disabled");
}else{
	$('#btnWCDCForward').addClass('disabled');
	$('#btnWCDCForward').attr('disabled', 'disabled');
	$('#btnWCDCReject').addClass('disabled');
	$('#btnWCDCReject').attr('disabled', 'disabled');
}
});

});
    

/*********************************************** End ************************************ */

/************************************************* WCDC Forward buton CLck ****************************** */
$('#btnWCDCForward').on('click',function(e){
	e.preventDefault();
	var finalAssetid=new Array();
	$('.remarks').prop('required',false);
	$scheme = $('#schemed option:selected').val();

	 $('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetid.push($(this).val());	
		}
      });
confirmAlert('Do you want to '+ $(this).html()+' work(s) '+finalAssetid+ '?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
$.ajax({  
            url:"completeAssetEPALivelihoodProduction",
            type: "post",  
            data: {assetid:finalAssetid.toString(), scheme:$scheme},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	//alert(data);
	if(data==='success'){
		alert('Work_Id Completed successfully ');
			/*alert('Work '+ $(this).html()+'ed Successfully !');*/
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcActionOnAsset';
			});*/
	}
	if(data==='successSLNA'){
		alert('Work-Id Completed Successfully !');
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='slnaActionOnAsset';
			});*/
	} 
	if(data==='failWCDC'){
			alert('Work Forward Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		}
	if(data==='failSLNA'){
			alert('Work Completion Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		}
	}
	});
	});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	
	});


/****************************************** End *********************************************************** */

/************************************************* WCDC Reject buton CLck ****************************** */
$('#btnWCDCReject').on('click',function(e){
	e.preventDefault();
	var finalAssetid=new Array();
	var remarks = new Array();
	$flag=false;
	$('.remarks').prop('required',true);
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 if($('#remarks'+$(this).val()).val()===''){
				$('.error').html('Please give some remarks for rejection');
				addInputFieldErrorCss('#remarks'+$(this).val());
				$('#remarks'+$(this).val()).focus();
				$flag=false;
				return false;
			}else{
				$('.error').html('');
				removeInputFieldErrorCss('#remarks'+$(this).val());
				finalAssetid.push($(this).val());
			 	remarks.push($('#remarks'+$(this).val()).val());
				$flag=true;
				//remarks.splice($(this).val(), 0, $('#remarks'+$(this).val()).val());
			}	
		}
      });
	if($flag)
		confirmAlert('Do you want to reject work(s) '+finalAssetid+ '?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
		$.ajax({  
            url:"rejectAssetEPALivelihoodProduction",
            type: "post",  
            data: {assetid:finalAssetid.toString(),remarks:remarks.toString(),scheme:$scheme},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(data==='successWCDC'){
			alert('Work-Id Rejected and back forwarded to PIA !');
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcActionOnAsset';
			});*/
	}
	if(data==='successSLNA'){
			alert('Work-Id Rejected and back forwarded to PIA !');
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='slnaActionOnAsset';
			});*/
	} 
	if(data==='failWCDC'){
			alert('Work-Id Rejection Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		}
	if(data==='failSLNA'){
			alert('Work-Id Rejection Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		}
	}
	});
	});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	});

/****************************************** End *********************************************************** */


/*****************************************Get Pending Asset ************************ */

$('#getPendingAsset').on('click',function(e){
	e.preventDefault();
	$pCode = $('#pendingProject option:selected').val();
	$scheme = $('#schemed option:selected').val();
	
	var tbodyTempListActivityAssetId = $('#tbodyTempListActivityAssetId');
	tbodyTempListActivityAssetId.empty();
	$('#loading').show();
	$.ajax({  
            url:"getPendingAssetEPALivelihoodProduction",
            type: "post",  
            data: {pCode:$pCode,scheme:$scheme},
            error:function(xhr,status,er){
	$('#loading').hide();
                console.log(er);
				$('.error').html('');
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				//alert(data);
				$('#loading').hide();
				if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var link="";
		//	if(data[key].usertype==='SL')
			link='Complete';
		//	else
		//	link='Forward';
			tbodyTempListActivityAssetId.append("<tr><td><input type='checkbox' class='chkIndividual' id='chkIndividual'"+data[key].tempassetid+"' name='chkIndividual'"+data[key].tempassetid+"' value='"+data[key].tempassetid+"' onclick='checkBoxClicked($(this));'/></td><td>"+data[key].projdesc+"</td><td>"+data[key].tempassetid+"</td><td>"+data[key].activitydesc+"</td><td>"+data[key].bname+"</td><td>"+data[key].vname+"</td><td><textarea maxlength='1000' id='remarks"+data[key].tempassetid+"' name='remarks"+data[key].tempassetid+"' class='remarks'></textarea></td><td>"+
			"<a href='#' data-id='"+data[key].tempassetid+"' class='forwardLink'>"+link+"</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' data-id='"+data[key].tempassetid+"' class='rejectLink'>Reject</a></td></tr>");
		}
			}
	}else{
		tbodyTempListActivityAssetId.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
	}
				
			}
		});
	
});

/**************************************************** End *********************************************** */

/*****************************************Get Forwarded Asset ************************ */

$('#getForwardedAsset').on('click',function(e){
	e.preventDefault();
	$pCode = $('#project option:selected').val();
	if($pCode==='')
	$pCode = $('#projectfwd option:selected').val();
	$dCode = $('#district option:selected').val();
	$('.error').html('');
	if(typeof $dCode==='undefined')
	$dCode=0;
	var tbodyForwardedListActivityAssetId = $('#tbodyForwardedListActivityAssetId');
	tbodyForwardedListActivityAssetId.empty();
	$('#loading').show();
	$.ajax({  
            url:"getForwardedAssetUserWiseForProject",
            type: "post",  
            data: {pCode:$pCode,dCode:$dCode},
            error:function(xhr,status,er){
	$('#loading').hide();
                console.log(er);
				$('.error').html('');
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				//alert(data);
				$('#loading').hide();
				$pdesc=new Array();
				if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			$subactivity =(typeof data[key].subactivitydesc==='undefined'?'':data[key].subactivitydesc);
			if($.inArray(data[key].projdesc, $pdesc) === -1){
				$pdesc.push(data[key].projdesc);
				tbodyForwardedListActivityAssetId.append("<tr><td>"+data[key].projdesc+"</td><td>"+data[key].finyrdesc+"</td><td>"+data[key].tempassetid+"</td><td>"+data[key].activitydesc+"</td><td>"+data[key].bname+"</td><td>"+data[key].vname+"</td><td>"+$subactivity +"</td><td>"+data[key].forwardedTo+"</td></tr>");
			}
			else
			tbodyForwardedListActivityAssetId.append("<tr><td></td><td>"+data[key].finyrdesc+"</td><td>"+data[key].tempassetid+"</td><td>"+data[key].activitydesc+"</td><td>"+data[key].bname+"</td><td>"+data[key].vname+"</td><td>"+$subactivity +"</td><td>"+data[key].forwardedTo+"</td></tr>");
		}
			}
	}else{
		tbodyForwardedListActivityAssetId.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
	}
				
			}
		});
	
});

/**************************************************** End *********************************************** */

/************************************ District DropDown Change To get Related Project ********************************* */
$('#district').on('change',function(e){
	e.preventDefault();
	$dCode=$(this).val();
	$.ajax({  
            url:"getProjectFromDistrict",
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
				$ddlProject.append('<option value="0">--All--</option>');
				if(Object.keys(data).length>0){
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$ddlProject.append('<option value='+key+'>' +data[key] + '</option>');
					}
				}
			}
				}
				});
	});


/***************************************************** End *************************** */


	});
	
	/********************************************************* Page Load Script End ************************************** */
	/************************************************* WCDC Reject link CLck ****************************** */
$(document).on('click', '.rejectLink', function(e) {
	e.preventDefault();
	$assetid = e.target.getAttribute('data-id');
	$('.remarks').prop('required',true);
	$remarks="";
	$scheme = $('#schemed option:selected').val();
	$flag=false;
	if($('#remarks'+$assetid).val()===''){
				$('.error').html('Remarks is mandatory for rejection');
				addInputFieldErrorCss('#remarks'+$assetid);
				$('#remarks'+$assetid).focus();
				$flag=false;
				return false;
			}else{
				$('.error').html('');
				removeInputFieldErrorCss('#remarks'+$assetid);
			 	$remarks=$('#remarks'+$assetid).val();
			confirmAlert('Do you want to reject Work-Id '+$assetid+ '?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
			$.ajax({  
				url:"rejectAssetEPALivelihoodProduction",
				type: "post",  
				data: {assetid:$assetid,remarks:$remarks, scheme:$scheme},
				error:function(xhr,status,er){
					console.log(er);
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if(data==='successWCDC')
					{
						alert('Work-Id Rejected and back frowarded to PIA !');
						/*$("#successok").click(function(){
						$('#popup').modal('hide');*/
						window.location.href='wcdcEPALivelihoodProductionWorkId';
						/*});  
						$(".close").click(function(){
						$('#popup').modal('hide');
						window.location.href='wcdcActionOnAsset';
						});*/
					}
	if(data==='successSLNA'){
			alert('Work-Id Rejected and back-forwarded to PIA !');
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='slnaActionOnAsset';
			});*/
		
		}
	if(data==='failWCDC'){
			alert('Work-Id Rejection Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		
		}
	if(data==='failSLNA'){
			alert('Work-Id Rejection Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		
		}
	}
	});
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
			}
	
	});

/****************************************** End *********************************************************** */

	/************************************************* WCDC Forward link CLck ****************************** */
$(document).on('click', '.forwardLink', function(e) {
	e.preventDefault();
	$assetid = e.target.getAttribute('data-id');
	$('.remarks').prop('required',false);
	$scheme = $('#schemed option:selected').val();
	$remarks=$('#remarks'+$assetid).val();
	confirmAlert('Do you want to '+ $(this).html()+' work-id '+$assetid+ '?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
	$.ajax({  
            url:"completeAssetEPALivelihoodProduction",
            type: "post",  
            data: {assetid:$assetid,remarks:$remarks, scheme:$scheme},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(data==='success'){
		
		alert('Work-Id Completed Successfully !');
		
		/*	alert('Work-Id Forwarded to SLNA Successfully !');*/
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcActionOnAsset';
			});*/
		}
	if(data==='successSLNA'){
			alert('Work-Id Completed Successfully !');
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='slnaActionOnAsset';
			});*/
		
		}
	if(data==='failWCDC'){
			alert('Work Forward Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		
		}
	if(data==='failSLNA'){
			alert('Work-Id Forward Failed !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcEPALivelihoodProductionWorkId';
			});
		
		}
		
	}
	});
	});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	});

/****************************************** End *********************************************************** */

	/********************************************** Clear Field Method ************************************* */
	function clearField(){
		$activityid= new Array();
		 $activityid.length = 0;
		console.log('clear'+$activityid.length);
	}
	
	/************************************************************* End ************************************* */
	
	/********************************** Dynamic created checkbox clicked event ********************************** */
	$chkValue= 0;
		function checkBoxClicked(t){
        if($('.chkIndividual:checked').length == $('.chkIndividual').length){
            $('#chkSelectAll').prop('checked',true);
        }else{
            $('#chkSelectAll').prop('checked',false);
        }
if(t.is(':checked'))
$chkValue++;
if(!t.is(':checked'))
$chkValue--;
if($chkValue>0){
$('#btnWCDCForward').removeClass('disabled');
	$('#btnWCDCReject').removeClass('disabled');
	$('#btnWCDCForward').removeAttr("disabled");
	$('#btnWCDCReject').removeAttr("disabled");
}else{
	$('#btnWCDCForward').addClass('disabled');
	$('#btnWCDCForward').attr('disabled', 'disabled');
	$('#btnWCDCReject').addClass('disabled');
	$('#btnWCDCReject').attr('disabled', 'disabled');
}
		}
		
		/*************************************************** End ************************************* */
	
/****************************************************** Block Dropdown Change *********************** */
	$(document).on('change', '.ddlBlock', function(e) {
		$('#loading').show();
	$pCode=$('#project option:selected').val();
	$id=$(this).prop("id");
	$id = $id.replace ( /[^\d.]/g, '' );
	$bCode=$(this).val();
	$.ajax({  
            url:"getVillageFromProjectLocationBlockWise",
            type: "post",  
            data: {pCode:$pCode,bCode:$bCode},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				var tblData="";
				var ddlBlock="";
				$ddlVillage = $('#ddlVillage'+$id);
				$ddlVillage.empty();
				$ddlVillage.append("<option value=''>--Select--</option>");		
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlVillage.append("<option value='"+key+"'>"+data[key]+"</option>");
						}		
					}
				}else{
					
				}
			}
		});
	});
	
	/************************************************************ End ************************************************* */
	
	
	/************************************************* Get Plan Details By ProjYr ******************************************* */
	function getPlanDetailsByProjYr(){
	// For Final List of Activity
				$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
				$tbodyfinalListActivityAssetId.empty();
				$pCode=$('#project option:selected').val();
				$yrCode=$('#scheme option:selected').val();
				$activity=$('#activity option:selected').val();
				$headindex=1;
				$activityindex=1;
				
		$.ajax({  
            url:"getlivelihoodPrdouctionEPAbyProjScheme",
            type: "post", 
			data:{projId:$pCode,yrCode:$yrCode,act:$activity}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				$('#loading').hide();
				var tblData="";
					$listActivityHeadWiseTbody = $('#listActivityHeadWiseTbody');
						$listActivityHeadWiseTbody.empty();
						if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			
			if (typeof  $.inArray(d[k].activitycode, $draftactivityid) === -1){
				if(d[k].noActivities!=0){
					//alert('kdy=' +1-d[k].assetcreated);
				if((1-d[k].assetcreated)>0){
			$remainingPlan=1-d[k].assetcreated;
			$link ="<a class='createAssetIdLinkSingle' href='#' data-id='"+d[k].activitycode+","+d[k].epaDetailId+","+(1-d[k].assetcreated)+"'>create work</a>";
			}else{
				$remainingPlan=0;
			$link="";
			}
			}else{
				$remainingPlan=0;
			$link="";
			}
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].activityname+"</td><td>"+d[k].noActivities+"</td><td class='text-center'>"+d[k].assetcreated+"</td><td>"+$link+"</td></tr>";
			else
			tblData+="<tr><td>"+d[k].activityname+"</td><td>"+d[k].noActivities+"</td><td class='text-center'>"+d[k].assetcreated+"</td><td>"+$link+"</td></tr>";
			}
			else if (typeof $.inArray(d[k].activitycode, $draftactivityid) === -1){
				//if((parseInt(d[k].plan)-d[k].assetcreated)>0)
			//	alert('kdy2=' +1-d[k].assetcreated);
			$link ="<a class='createAssetIdLinkMultiple' href='#' data-id='"+d[k].activitycode+","+d[k].epaDetailId+","+(parseInt(d[k].noActivities)-d[k].assetcreated)+","+d[k].assetcreated+","+d[k].covera+"'>create work</a>";
			//else
			//$link="2";
			$remainingPlan=(parseInt(d[k].noActivities)-d[k].assetcreated);
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].activityname+"</td><td>"+d[k].noActivities+"</td><td class='text-center'>"+d[k].assetcreated+"</td><td>"+$link+"</td></tr>";
			else
			tblData+="<tr><td>"+d[k].activityname+"</td><td>"+d[k].noActivities+"</td><td class='text-center'>"+d[k].assetcreated+"</td><td>"+$link+"</td></tr>";
			}
			else{
				$link ="<a class='createAssetIdLinkMultiple' href='#' data-id='"+d[k].activitycode+","+d[k].epaDetailId+","+(parseInt(d[k].noActivities)-d[k].assetcreated)+","+d[k].assetcreated+","+d[k].covera+"'>create work</a>";
			//	$link ="<a class='createAssetIdLinkSingle' href='#' data-id='"+d[k].activitycode+","+d[k].epaDetailId+","+(1-d[k].assetcreated)+"'>create work</a>";
			if(parseInt(k)===0)
			tblData+="<tr><td>"+d[k].activityname+"</td><td>"+d[k].noActivities+"</td><td class='text-center'>"+d[k].assetcreated+"</td><td>"+$link+"</td></tr>";
			else
			tblData+="<tr><td>"+d[k].activityname+"</td><td>"+d[k].noActivities+"</td><td class='text-center'>"+d[k].assetcreated+"</td><td>"+$link+"</td></tr>";
			}
			//$link ="<a class='createAssetIdLink' href='#' data-id='"+d[k].activitycode+","+d[k].aapid+"'>create asset </a>";
			
		//$activityindex++;	
		}
			
		}
						//$headindex++;
					//	$activityindex=1;
							}
							}	
						
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$listActivityHeadWiseTbody.append(tblData);
	}
	});
	}
	/********************************************************* End ***************************************************** */
	
	
	/********************************************************* Get Draft Plan Details By ProjYr  ********************************************** */
	function getDraftPlanDetailsByProjYr(){
	// For Final List of Activity
	$pCode=$('#project option:selected').val();
	$yrCode=$('#scheme option:selected').val();
	$activity=$('#activity option:selected').val();
	$link="";
	$distApproval=null;
	$chkArray = new Array();
	$planCount=0;
	$aset="";
	$.ajax({  
            url:"getlivelihoodPrdouctionEPAbyProjSchemeDraft",
            type: "post", 
			data:{projId:$pCode,yrCode:$yrCode,act:$activity}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				var tblData="";
					$tbodyTempListActivityAssetId = $('#tbodyTempListActivityAssetId');
						$tbodyTempListActivityAssetId.empty();
						if(Object.keys(data).length>0)
						{
							for ( var key in data) 
							{
							    if (data.hasOwnProperty(key)) 
							    {
							    	var d=data[key];
							    	for ( var k in d) 
							    	{
							    		if (d.hasOwnProperty(k)) 
							    		{
							    			$draftactivityid.push(d[k].activitycd);
							    			$assetid.push(d[k].asseteid);
							    			$aset=d[k].asseteid;
							    			$link ="<a class='assetid' href='#' data-id='"+d[k].asseteid+","+d[k].activitycd+"'>Delete</a>";
							    			//$distApproval=d[k].distApprovalReq;
							    			if ( $yrCode==='production')
							    			{
												$('#areacov').removeClass('d-none');
								    			if(parseInt(k)===0)
								    				tblData+="<tr><td>"+d[k].asseteid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].areacovered+"</td><td>"+d[k].nearby+"</td><td>"+$link+"</td></tr>";
								    			else
								    				tblData+="<tr><td>"+d[k].asseteid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].areacovered+"</td><td>"+d[k].nearby+"</td><td>"+$link+"</td></tr>";
							    		 	}
							    		 	else{
												$('#areacov').addClass('d-none');	
											 	if(parseInt(k)===0)
								    				tblData+="<tr><td>"+d[k].asseteid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].nearby+"</td><td>"+$link+"</td></tr>";
								    			else
								    				tblData+="<tr><td>"+d[k].asseteid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td>"+d[k].nearby+"</td><td>"+$link+"</td></tr>";
										 	}
							    		 }	
							    	 }
							    }
							}	
						
						}
						else{
							tblData="<tr><td colspan='5' class='text-center'>Data not found !</td></tr>";
						}
						$tbodyTempListActivityAssetId.append(tblData);
						if($aset===""){
							$('#user').addClass('d-none');
							$('#user').removeAttr("required");
							$('#forwardToWCDC').addClass('d-none');
							
						}
						else {
							$('#user').removeClass('d-none');
							$('#user').attr("required","required");
							getUserToForward();
							$('#forwardToWCDC').removeClass('d-none');
						}
	
            }
		});
	
	}
	/************************************************* End *********************************************** */
	
	/*********************************** get User to Forward ******************** */
		function getUserToForward(){
			$.ajax({  
            url:"getUserDistEpaLive",
            type: "post", 
            data: { },
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				var $forward = $('#user');
				$forward.empty();
        		$forward.append('<option value="">--Select--</option>');
				if(Object.keys(data).length>0 ){
					
					for ( var key in data) {
						 if (data.hasOwnProperty(key) && key!=null) {
						    $forward.append('<option value='+key+'>'+data[key]+'</option>');
							$forward.attr("required","required");
						 }else{
							
					}
					}
				}else{
					
				}
	}
	});	
		}
		
		/************************************ End *************************************** */
		
		function doRefresh(){
        $.ajax({  
            url:"refreshData",
            type: "post", 
            data: { },
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	
	if(data>0){
		$('.reject').html(data);
		$('.reject').removeClass('d-none');
	}
	
	}
	});
    }
