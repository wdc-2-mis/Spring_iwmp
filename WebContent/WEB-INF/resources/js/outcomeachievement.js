/**
 * 
 */

$( document ).ready(function(){
	
	
	$('#btnGetActivity').click(function(e) {
		e.preventDefault();
		$pCode = $('#ddlProject option:selected').val();
		$head= new Array();
		$tbodyAddOutcomeAchievement = $('#tbodyAddOutcomeAchievement');
		$tbodyAddOutcomeAchievement.empty();
		$.ajax({  
            url:"getActivityWithTargetForOutcomeAchievement",
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
								if(typeof data[key].achievement==='undefined')
									$achievement="<input type='text' id='achievement"+data[key].headcode+data[key].activitycd+"' name='achievement"+data[key].headcode+data[key].activitycd+"' value='0.0' class='form-control' />";
									else
									$achievement="<input type='text' id='achievement"+data[key].headcode+data[key].activitycd+"' name='achievement"+data[key].headcode+data[key].activitycd+"' value='"+data[key].achievement+"' class='form-control' />";
									if(typeof data[key].activitydesc==='undefined')
									$activity ="<input type='hidden' id='head"+key+"' name='head"+key+"' value='"+data[key].headcode+"' /><input type='hidden' id='activity"+key+"' name='activity"+key+"' value='"+data[key].activitycd+"' />";
									else
									$activity ="<input type='hidden' id='head"+key+"' name='head"+key+"' value='"+data[key].headcode+"' /><input type='hidden' id='activity"+key+"' name='activity"+key+"' value='"+data[key].activitycd+"' />"+data[key].activitydesc;
									
									if($.inArray(data[key].headdesc, $head) === -1){//alert("if "+data[key].headdesc+" : " +$head);
										$headid++;
										$activityid=1;
										if($activity==='' || typeof data[key].activitycd==='undefined')
										$act=$activity;
										else
										$act=$headid+"."+$activityid+". "+$activity;
									if($activity==='' || typeof data[key].activitycd==='undefined')
									$tbodyAddOutcomeAchievement.append("<tr><td>"+$headid+". "+data[key].headdesc+"</td><td></td><td>"+(data[key].targetuptoyear-data[key].achievementuptoyear)+"</td><td>"+data[key].target+"</td><td>"+((data[key].target)+(data[key].targetuptoyear-data[key].achievementuptoyear))+"</td><td>"+data[key].achievementthisyr+"</td><td>"+$achievement+"</td></tr>");
									else{
									$tbodyAddOutcomeAchievement.append("<tr><td>"+$headid+". "+data[key].headdesc+"</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
									$tbodyAddOutcomeAchievement.append("<tr><td></td><td>"+$act+"</td><td>"+(data[key].targetuptoyear-data[key].achievementuptoyear)+"</td><td>"+data[key].target+"</td><td>"+((data[key].target)+(data[key].targetuptoyear-data[key].achievementuptoyear))+"</td><td>"+data[key].achievementthisyr+"</td><td>"+$achievement+"</td></tr>");
									}
									$head.push(data[key].headdesc);
									
									}else{
									if(typeof data[key].activitydesc!='undefined'){//alert("el "+data[key].headdesc+" : " +$head);
									//$tbodyAddOutcomeAchievement.append("<tr><td>"+$headid+". "+data[key].headdesc+"</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
									$tbodyAddOutcomeAchievement.append("<tr><td></td><td>"+$headid+"."+$activityid+". "+$activity+"</td><td>"+(data[key].targetuptoyear-data[key].achievementuptoyear)+"</td><td>"+data[key].target+"</td><td>"+((data[key].target)+(data[key].targetuptoyear-data[key].achievementuptoyear))+"</td><td>"+data[key].achievementthisyr+"</td><td>"+$achievement+"</td></tr>");
									
									}
									}/*}
									}*/
									$activityid++;
									}
									}
				}else{
					$('#lblStartDate').html('N.A.');
		$('#lblEndDate').html('N.A.');
		$('#btnForward').addClass('d-none');
		//$('#ddlUser').addClass('d-none');
		$('.error').html('You have already provided the achievement till current month');
					$tbodyAddOutcomeAchievement.append("<tr><td colspan='8' class='text-center'>Data not found !</td></tr>");
				}
			
			}
			});
			
			
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
			$ddlUser.addClass('d-none');	
			}
			}
			
			});
		
		});
		
		
		/************************************** Button Save as Draft Click ********************************** */
$(document).on('click', '#btnForward', function(e) {
		e.preventDefault();
		var regexp= /^\d+(?:\.\d\d?)?$/;
		$pCode = $('#ddlProject option:selected').val();
		if(typeof $('#ddlUser option:selected').val()==='undefined')
		$sentto = null;
		else
		$sentto = $('#ddlUser option:selected').val();
		if(parseInt($sentto)===0){
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
		}else{
			removeInputFieldErrorCss('#ddlUser');
		}
		
		
		var i = $('#tbodyAddOutcomeAchievement tr').length ;
		var activity=[];
		var head=[];
		var ach=[];
		if(i>1){
	for(var x=0;x<parseInt(i);x++){
		if(x==0){
			if ($('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).val() === ""){
		successAlert("Achievement required");
		addInputFieldErrorCss('#achievement'+$('#activity'+x).val());
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});
		return false;
			}else{
				removeInputFieldErrorCss('#achievement'+$('#head'+x).val()+$('#activity'+x).val());
				var achievement = $('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).val();
			if(achievement.indexOf('.')>=0)
			var afterDecimalLength = (achievement.toString().split(".")[1]).length;
			else
			afterDecimalLength=0;
			if(achievement.indexOf('.')==0)
			achievement="0"+achievement;
			var result=regexp.test(achievement);
			//alert(result+" : "+result+" : "+achievement.indexOf('.')+" : "+afterDecimalLength);
		if(!result || (achievement.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 ){
		successAlert('Please enter valid positive value upto two decimal point');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});
			return false;
		}
			if($('#activity'+x).val()==='undefined')
			activity.push('0');
			else
			activity.push($('#activity'+x).val());
			head.push($('#head'+x).val());
			ach.push($('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).val());
			}
		}
		else{
		if ($('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).val() === ""){
		successAlert("Achievement required");
		addInputFieldErrorCss('#achievement'+$('#head'+x).val()+$('#activity'+x).val());
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});
		return false;
			}else{
				var achievement = $('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).val();
			if(achievement.indexOf('.')>=0)
			var afterDecimalLength = (achievement.toString().split(".")[1]).length;
			else
			afterDecimalLength=0;
			if(achievement.indexOf('.')==0)
			achievement="0"+achievement;
			var result=regexp.test(achievement);
			//alert(decimalLength+" : "+target+" : "+target.indexOf('.')+" : "+target.toString().split(".")[1]);
		if(!result || (achievement.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 ){
		successAlert('Please enter valid positive value upto two decimal point');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).focus();
			});
			return false;
		}
		
				if($('#activity'+x).val()==='undefined')
			activity.push('0');
			else
			activity.push($('#activity'+x).val());
			head.push($('#head'+x).val());
				ach.push($('#achievement'+$('#head'+x).val()+$('#activity'+x).val()).val());
				removeInputFieldErrorCss('#achievement'+$('#activity'+x).val());
			}
	}
		}
		}
		
		//alert('pCode: '+$pCode+'head: '+head+' activity: '+activity+' ach: '+ach +' sentTo: '+$sentto);
		confirmAlert('Do you want to Forward the Achievement Data ?');
		$("#ok").click(function(){
		$tbodyAddOutcomeAchievement = $('#tbodyAddOutcomeAchievement');
		$tbodyAddOutcomeAchievement.empty();
		$('#popup').modal('hide');
		$.ajax({  
            url:"forwardOutcomeAchievementFromPIA",
            type: "post", 
			data:{pCode:$pCode,head:""+head,activity:""+activity,ach:""+ach,sentTo:$sentto}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			//alert(data);
			if(data==='success'){
				successAlert("Achievement Forwarded Successfully !");
				$('.error').html('Achievement has been forwarded successfully');
				$('.error').css('color','white');
				$('.error').css('background-color','green');
			}else{
				successAlert("Achievement not Forwarded, Please try again !");
				$('.modal-body').css("color","red");
				$('.error').html('Achievement not Forwarded, Please try again !');
			}
			$("#successok").click(function(){
			$('#popup').modal('hide');
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
	$('#tbodyOutcomeAchievement').on( 'click', 'a.action', function (e) {
		 // $('#loading').show();
$achid= e.target.getAttribute('data-id');
//alert($achid);

$.ajax({  
            url:"checkForAlreadyForwardedOutcomeAchievement",
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
	CKEDITOR.replace('remarks');
	}
	});	
		
		});
		
/************************************************* End ************************************** */


/************************************************** District Forward Click *********************************** */
	$(document).on('click', '#submitaction', function(e) {
		//$('#loading').show();
		$action= $('#ddlAction option:selected').val();
		$remarks= CKEDITOR.instances['remarks'].getData(); 
		$achid=$('#achid').val();
//alert($action+" : "+$remarks+" : "+$achid);
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
            url:"forwardOutcomeAchievementByWCDCSLNA",
            type: "post",  
            data: {achid:$achid,action:$action,remarks:$remarks},
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
							successAlert('Data of Achievement is being rejected.');
							else
							successAlert('Achievement data has been finalized and completed.');
							//$('#errormessage').html("Data has been finalized and completed.");
							
						}
						if(data==='district'){
						//$('#errormessage').html("Data forwarded to SLNA for further approval.");
						if($action==='R')
							successAlert('Data of Achievement is being rejected.');
							else
						successAlert('Achievement data forwarded to SLNA for further approval.');	
						}
						$("#successok").click(function(){
$('#popup').modal('hide');
window.location.href="outcomeAchievementWCDCSLNA";
});
						
	}
	});
	});
	
	/********************************************************************** End ******************************************************** */
	
	/******************************************** View Movement Tab Click Script ************************************ */

		$('#viewMovement').click(function(e) {
	//	$('#loading').show();
	$.ajax({  
            url:"viewOutcomeAchievementMovement",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyAchievementMovement = $('#tbodyAchievementMovement');
	tbodyAchievementMovement.empty();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(typeof d[k].remarks==='undefined')
			$remarks='';
			else
			$remarks=d[k].remarks;
			if(parseInt(k)===0)
			tbodyAchievementMovement.append("<tr><td>"+key+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+$remarks+"</td><td style='text-align:center;'><a class='detail' data-id="+d[k].achievementid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].achievementid+" href='#'>view</a></td></tr>");
			else
			tbodyAchievementMovement.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].monthdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+$remarks+"</td><td style='text-align:center;'><a class='detail' data-id="+d[k].achievementid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].achievementid+" href='#'>view</a></td></tr>");
		}
		}
		
		}
			}
	}else{
		tbodyAchievementMovement.append("<tr><td colspan='7' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	
	/************************************************************************** End ************************************************************/
	
	/***************************************** Outcome Detail Link click  ************************* */
	
	$('#tbodyAchievementMovement').on( 'click', 'a.detail', function (e) {
	var del = e.target.getAttribute('data-id');
	//alert(del);
	$('#loading').show();
	$.ajax({  
            url:"getOutcomeAchievementDetails",
            type: "post",  
            data: {achId:del},
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
						$('#popupreport #popupreporttitle').html('Details of Outcome Achievement');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[0].projectdesc+ ', FY: '+data[0].finyrdesc+' and Month: '+data[0].monthdesc+'<br/><span style="color:red"> Current Status: '+data[0].status+"</span>");
			}
			
			if(typeof data[key].activitydesc==='undefined')
			tblData+="<tr><td>"+data[key].headdesc+"</td><td></td><td>"+data[key].achievement+"</td></tr>";
			else
			tblData+="<tr><td>"+data[key].headdesc+"</td><td>"+data[key].activitydesc+"</td><td>"+data[key].achievement+"</td></tr>";
		
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" style="width:100%" >'+
						'<thead><tr><th>Name of Head</th><th>Name of Activity</th><th style="text-align:center;"><span >Achievement '+
						'</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
							});
	});
	
	/***************************************** Outcome Detail Link click Ends ************************* */
	
	/*************************************** Movement detail link click Script ***************************** */
	
	$('#tbodyAchievementMovement').on( 'click', 'a.movement', function (e) {
	var del = e.target.getAttribute('data-id');
	$('#loading').show();
	$.ajax({  
            url:"getOutcomeAchievementMovementDetails",
            type: "post",  
            data: {achId:del},
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
						$('#popupreport #popupreporttitle').html('Movement of Outcome Achievement Data');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key].projectdesc+ ', FY: '+data[key].finyrdesc+' and Month: '+data[0].monthdesc+'<br/><span style="color:red"> Current Status: '+data[key].status+"</span>");
			}
			
			var action='';
			if(data[key].action==='F'){
				action='Forwarded';
			}else if(data[key].action==='R'){
				action='Rejected';
			}else if(data[key].action==='C'){
				action='Completed';
			}
			if(parseInt(key)===0)
			tblData+="<tr><td>"+data[key].sentfrom+"</td><td>"+data[key].sentto+"</td><td>"+data[key].senton+"</td><td>"+action+"</td><td class='remarks'>"+data[key].remarks+"</td></tr>";
			else
			tblData+="<tr><td>"+data[key].sentfrom+"</td><td>"+data[key].sentto+"</td><td>"+data[key].senton+"</td><td>"+action+"</td><td class='remarks'>"+data[key].remarks+"</td></tr>";
			
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
	
	/*************************** End ******************************************** */
	
	/************************************************************** edit Button click Script ************************************** */

	$(document).on('click', '#editCrop', function(e) {

		var myModal = $('#editbaselinecomdata');

		e.preventDefault();


		var $rowId = $(this).attr('data-id');
		$.ajax({
			type: 'POST',
			//getcroptype,  getSeasonList getcroptype getCropType
			url: "getSeasonList",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {

				var $actdesc = $('#season');
				$actdesc.empty();
				$actdesc.append('<option value=""> --Select Season--</option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$actdesc.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}
			}


		});

		$.ajax({
			type: 'POST',
			url: "getCropType",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				console.log(data);
				var $actdesc = $('#ctype');
				$actdesc.empty();
				$actdesc.append('<option value=""> --Select Crop Type-- </option>');
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$actdesc.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}
			}


		});
		$.ajax({
			url: "baselineDraftdatafind",
			type: "get",
			data: { id: $rowId },
			error: function(xhr, status, er) {
				console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
			},
			success: function(data) {
				console.log(data);
				for (var key in data) {
					//	alert('data[key].bls_out_main_id_pk' +data[key].bls_out_main_id_pk);
					if (data.hasOwnProperty(key)) {
						$('#areahacError').html('');
						if (typeof (data[key].season_id) == 'undefined') {
							$('#season').addClass("d-none");
							$('#lblSeason').addClass("d-none");
						}
						else {
							$('#season').removeClass("d-none");
							$('#lblSeason').removeClass("d-none");
						}
						$("#season option[value=" + data[key].season_id + "]").attr('selected', 'selected');
						$("#ctype option[value=" + data[key].crop_type_id + "]").attr('selected', 'selected');
						$('#areahac').val(data[key].area);
						$('#crop_prod').val(data[key].crop_production);
						$('#avg_income').val(data[key].avg_income);
						$('#bsl_crop_id').val(data[key].bls_out_detail_tranx_id_pk);
						$('#bsl_detail_id').val(data[key].bls_out_detail_id_pk);
						$('#plot_no').val(data[key].plot_no);
						$('#vcode').val(data[key].vcode);
						$('#proj_id').val(data[key].proj_id);
					}
				}

			}
		});
		//location.reload();
		myModal.modal({ toggle: true });
		myModal.modal({ show: true });

	});

	/******************************************************************** End ******************************************************** */
	
	/******************edit update crop data**************************************************** */
	$(document).on('click', '#cropUpdate', function(e) {
		e.preventDefault();
		if ($('#areahac').val() === '') {
			alert('please enter Area');
			$('#areahac').focus();
			return false;
		}
		if ($('#ctype').val() === '') {
			alert('please Select Crop Type');
			$('#ctype').focus();
			return false;
		}

		if ($('#crop_prod').val() === '') {
			alert('please enter Crop Production per ha');
			$('#crop_prod').focus();
			return false;
		}

		if ($('#avg_income').val() === '') {
			alert('please enter Avg. income per Quintal');
			$('#avg_income').focus();
			return false;
		}
		if (confirm('Are you sure to wants to Update this Record ?')) {
			$bsl_crop_id = $('#bsl_crop_id').val();
			$bsl_detail_id = $('#bsl_detail_id').val();
			$season = $('#season').val();
			$ctype = $('#ctype').val();
			$areahac = $('#areahac').val();
			$crop_prod = $('#crop_prod').val();
			$avg_income = $('#avg_income').val();
			$plotNo = $('#plot_no').val();
			$vcode = $('#vcode').val();
			$projId = $('#proj_id').val();



			$.ajax({
				url: "updateCropDraftData",
				type: "post",
				data: { bsl_crop_id: $bsl_crop_id, bsl_detail_id: $bsl_detail_id, season: $season, ctype: $ctype, areahac: $areahac, crop_prod: $crop_prod, avg_income: $avg_income},
				error: function(xhr, status, er) {
					//alert(er);
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);

					$('#loading').hide();
					if (data === 'success') {
						$('#editbaselinecomdata').modal('hide');
						successAlert('Crop Record is successfully updated');
						$("#successok").click(function() {
							$('#popup').modal('hide');
						});
						document.delcompBase.action="getBaselineNewDraft";
						document.delcompBase.method="post";
						document.delcompBase.submit();
					}
					

					else
						alert('Data Can Not Updated !');
				}
			});
		}

	});

	/******************************************************************** End ******************************************************** */
	
	/****************************************************** Area check script ********************************************* */
$(document).on( 'blur', '#areahac', function (e) {
	if ($('#areahac').val() === '') {
			$('#areahacError').html('please enter Area');
			$('#areahacError').css('color','red');
			return false;
		}
	$areahac = $('#areahac').val();
	$season = $('#season').val();
	$ctype = $('#ctype').val();
	$plotno = $('#plot_no').val();
	$vcode = $('#vcode').val();
	$projId = $('#proj_id').val();
	$bsl_crop_id = $('#bsl_crop_id').val();
	$bsl_detail_id = $('#bsl_detail_id').val();
		$.ajax({
				url: "getDraftwiseCheckCropArea",
				type: "post",
				data: { bsl_crop_id: $bsl_crop_id, bsl_detail_id: $bsl_detail_id, season: $season, areahac: $areahac, ctype: $ctype,
				 plotno: $plotno, vcode: $vcode, projId: $projId},
				error: function(xhr, status, er) {
					console.log(er);
					$('#loading').hide();
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);

					$('#loading').hide();
					if (data === 'success') {
						$('#areahacError').html('');
						return false;
					}
					else{
						$('#areahacError').html('Crop Area can not be greater than Plot Area');
						$('#areahacError').css('color','red');
						$('.areahac').val("");
						$('.areahac').focus();
					}
				
				}
			});
	
	
		});
		
	/******************************************************************** End ******************************************************** */
	
	/***************************** Project Change Script *************************************** */
$(document).on( 'change', '#ddlProject', function (e) {
	$('#loading').show();
	if($('.plotnoError').html()!=""){
		$('.plotnoError').html('');
	}
	$projId = $('#ddlProject').val();
	if($projId ===""){
		window.location.reload();
	}
	
	$.ajax({  
            url:"getVillageOfProject",
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlVillage=$('#village');
				$ddlVillage.empty();
				$ddlVillage.append("<option value='0'>---All---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlVillage.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
  orderByNameDDL($ddlVillage);
			}
		});
	
});	


/************************************************* End ************************************************* */
	
	});