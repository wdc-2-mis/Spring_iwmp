/**
 * 
 */

$(function(){
	
	/*************************************************************************** Project CHnage method **************************************************************************** */
	
	$('#project').on("change" ,function() {
		$('.error').html('');
		$projId = $('#project option:selected').val();
		$('#loading').show();
		var url ="getFinYearProjectWise";
		$.ajax({  
            url:url,
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var $year = $('#year');
						$year.empty();
        				$year.append('<option value="">--Select Year--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $year.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	}
	});
		});
		/********************************************************************** End ***************************************************************************** */

/******************************************************************** Year change script ***************************************************************** */
	$('#year').on("change" ,function() {
		
	$('.error').html('');
		$projId = $('#project option:selected').val();
		$finYr = $(this).val();
		$('#loading').show();
		var url ="getActivityDetail";
		$.ajax({  
            url:url,
            type: "post",  
            data: {projId:$projId,finYr:$finYr},
            error:function(xhr,status,er){
                console.log(er.message);
            },
            success: function(data) {
	
	var listActivityHeadWiseTbody = $('#listActivityHeadWiseTbody');
	listActivityHeadWiseTbody.empty();
	$head = new Array();
	console.log(data);
	$count=1;
	$textCount=1;
	if(Object.keys(data).length>0){
		for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$ind=1;
					
					var val = data[key];
				for ( var k in val) {
				if (val.hasOwnProperty(k)) {
					
					var arr = val[k].split('#');
					if(typeof arr[3]==='undefined' || arr[3]==='')
					$value=0;
					else
					$value=arr[3];
					if(arr[0]!=''){
						
				if ( $.inArray(key, $head) === -1){
					$head.push(key);
					listActivityHeadWiseTbody.append("<tr><td>"+$count+".</td><td>"+key+" </td><td></td></tr>");
					listActivityHeadWiseTbody.append("<tr><td></td><td><input id='moutcomeid"+arr[1]+"' name='moutcomeid' type='hidden' value='"+arr[1]+"' /><input id='outcomedetailsid"+arr[0]+"' name='outcomedetailsid' type='hidden' value='"+arr[0]+"' />"+$count+'.'+$ind+' '+arr[2]+" </td><td><input id='target"+$textCount+"' name='target' mousedown='decimalCheck(event)' type='text' value='"+$value+"' class='form-control target'/></td></tr>");
					$textCount++;
					
					}else{
						listActivityHeadWiseTbody.append("<tr><td></td><td><input id='moutcomeid"+arr[1]+"' name='moutcomeid' type='hidden' value='"+arr[1]+"' /><input id='outcomedetailsid"+arr[0]+"' name='outcomedetailsid' type='hidden' value='"+arr[0]+"' />"+$count+'.'+$ind+' '+arr[2]+" </td><td><input id='target"+$textCount+"' name='target' mousedown='decimalCheck(event)' type='text' value='"+$value+"' class='form-control target'/></td></tr>");
						$textCount++;
					}
				
				$ind++;
				}else{
				listActivityHeadWiseTbody.append("<tr><td>"+$count+".</td><td><input id='moutcomeid"+arr[1]+"' name='moutcomeid' type='hidden' value='"+arr[1]+"' /><input id='outcomedetailsid"+arr[0]+"' name='outcomedetailsid' type='hidden' value='"+arr[0]+"' />"+key+" </td><td><input id='target"+$textCount+"' name='target' type='text' mousedown='decimalCheck(event)' value='"+$value+"' class='form-control target' /></td></tr>");
				$textCount++;
				}
				}
				
			}
			$count++;
				}
			}
		}else{
			listActivityHeadWiseTbody.append("<tr><td colspan='8' class='text-center'>Data not Found !</td></tr>");
		}
	$('#loading').hide();
	}
	});
	$('#loading').show();
	var ddlUser = $('#ddlUser');
	ddlUser.empty();
	$.ajax({  
            url:'getUserToForwardForOutcome',
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er.message);
            },
            success: function(data) {
			$('#loading').hide();
			if(Object.keys(data).length>0){
				ddlUser.removeClass('d-none');
				for ( var key in data) {
					if (data.hasOwnProperty(key)) {
						ddlUser.append('<option value='+key+'>'+data[key]+'</option>');		
					}
				}
			}else{
				ddlUser.addClass('d-none');
			}
			}
	
		});
		$('#loading').show();
		$.ajax({  
            url:'getForwardedOutcome',
            type: "post",  
            data: {projId:$projId,finYr:$finYr},
            error:function(xhr,status,er){
                console.log(er.message);
            },
            success: function(data) {
	$('#loading').hide();
	console.log(data);
	if(data==='Reject'){
		//$('#draftSave').addClass('d-none');
		//$('#btnForward').addClass('d-none');
		$('.error').css('color','white');
		$('.error').css('background-color','red');
		$('.error').html('The Outcome Target for F.Y 20'+$finYr+'-'+(parseInt($finYr)+1)+' of Project '+$('#project option:selected').text()+' has been rejected. Please modify it and forward again.');
		
	}else if(data==='Complete'){
		$('#draftSave').addClass('d-none');
		$('#btnForward').addClass('d-none');
		$('.error').css('color','white');
		$('.error').css('background-color','green');
		$('.error').html('The Outcome Target for F.Y 20'+$finYr+'-'+(parseInt($finYr)+1)+' of Project '+$('#project option:selected').text()+' has been Completed.');
		
	}else if(data==='Forward'){
		$('#draftSave').addClass('d-none');
		$('#btnForward').addClass('d-none');
		$('.error').css('color','white');
		$('.error').css('background-color','red');
		$('.error').html('The Outcome Target for F.Y 20'+$finYr+'-'+(parseInt($finYr)+1)+' of Project '+$('#project option:selected').text()+' has been already forwarded.');
		
	}else{
		$('#draftSave').removeClass('d-none');
		$('#btnForward').removeClass('d-none');
	}
	
	}
	
	});
	
		});
		/************************************ end ********************************* */
	
	/******************************************************************** Button Click Save As Draft script ***************************************************************** */
	$('#draftSave').on("click" ,function(e) {
		e.preventDefault();
		var regexp= /^\d+(?:\.\d\d?)?$/;
		$outcome="";
		$outcomedetail="";
		$target="";
		$flag=false;
		$projId = $('#project option:selected').val();
		$finYr = $('#year option:selected').val();
		$('input[name="moutcomeid"]').each(function(){
			if($outcome==='')
				$outcome=$(this).val();
			else
				$outcome+=','+$(this).val();
		});
		
		var i = $('input[name="target"]').length ;
		
		$('input[name="outcomedetailsid"]').each(function(){
			if($outcomedetail==='' && $(this).val()=='')
				$outcomedetail=' ';
			else if($outcomedetail!='' && $(this).val()=='')
				$outcomedetail+=','+' ';
			else if($outcomedetail==='' && $(this).val()!='')
				$outcomedetail=$(this).val();
			else
				$outcomedetail+=','+$(this).val();
		});
		
		/*$('input[name="target"]').each(function(){
			
			if($target==='' && $(this).val()=='')
				$target=' ';
			else if($target==='')
				$target=$(this).val();
			else
				$target+=','+$(this).val();
		});*/
		for(var x=1;x<=parseInt(i);x++){
		var	targetval=$('#target'+x).val();
			//alert(targetval+"  :  "+'#target'+x);
			if (targetval=== "" || targetval===' '){
		successAlert("Target required");
		addInputFieldErrorCss('#target'+x);
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});
		return false;
			}else{
				removeInputFieldErrorCss('#target'+x);
			if(targetval.indexOf('.')>=0)
			var afterDecimalLength = (targetval.toString().split(".")[1]).length;
			else
			afterDecimalLength=0;
			if(targetval.indexOf('.')==0)
			targetval="0"+targetval;
			var result=regexp.test(targetval);
			//alert(result+" : "+result+" : "+$targetval.indexOf('.')+" : "+afterDecimalLength);
		if(!result || (targetval.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 ){
		successAlert('Please enter valid positive value upto two decimal point');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});
			return false;
		}
		
		if($target==='' && targetval==='')
				$target=' ';
			else if($target==='')
				$target=targetval;
			else
				$target+=','+targetval;
				
			}
			}
	// alert($outcome+"#"+$outcomedetail+'#'+$target);
	$('#loading').show();
	var url ="saveAsDraftOutcomeTarget";
		$.ajax({  
            url:url,
            type: "post",  
            data: {outcome:$outcome,outcomedetail:$outcomedetail,target:$target,projId:$projId,finYr:$finYr},
            error:function(xhr,status,er){
                console.log(er.message);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(data==='success'){
		$('.error').html('Data has been saved as draft successfully');
		$('.error').css('color','white');
		$('.error').css('background-color','green');
		
	}else{
		$('.error').html('There is some error whle saving. Please try again !');
	}
	
	}
	});
	
		});
		/************************************ end ********************************* */
		
		/******************************************************************** Button Click Forward script ***************************************************************** */
	$('#btnForward').on("click" ,function(e) {
		e.preventDefault();
		$outcome="";
		$outcomedetail="";
		$target="";
		var regexp= /^\d+(?:\.\d\d?)?$/;
		$projId = $('#project option:selected').val();
		$finYr = $('#year option:selected').val();
		$('input[name="moutcomeid"]').each(function(){
			if($outcome==='')
				$outcome=$(this).val();
			else
				$outcome+=','+$(this).val();
		});
		$('input[name="outcomedetailsid"]').each(function(){
			if($outcomedetail==='' && $(this).val()=='')
				$outcomedetail=' ';
			else if($outcomedetail!='' && $(this).val()=='')
				$outcomedetail+=','+' ';
			else if($outcomedetail==='' && $(this).val()!='')
				$outcomedetail=$(this).val();
			else
				$outcomedetail+=','+$(this).val();
		});
		var i = $('input[name="target"]').length ;
		/*$('input[name="target"]').each(function(){
			if($target==='' && $(this).val()=='')
				$target=' ';
			else if($target==='')
				$target=$(this).val();
			else
				$target+=','+$(this).val();
		});*/
		for(var x=1;x<=parseInt(i);x++){
		var	targetval=$('#target'+x).val();
			//alert(targetval+"  :  "+'#target'+x);
			if (targetval=== "" || targetval===' '){
		successAlert("Target required");
		addInputFieldErrorCss('#target'+x);
		$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});
		return false;
			}else{
				removeInputFieldErrorCss('#target'+x);
			if(targetval.indexOf('.')>=0)
			var afterDecimalLength = (targetval.toString().split(".")[1]).length;
			else
			afterDecimalLength=0;
			if(targetval.indexOf('.')==0)
			targetval="0"+targetval;
			var result=regexp.test(targetval);
			//alert(result+" : "+result+" : "+$targetval.indexOf('.')+" : "+afterDecimalLength);
		if(!result || (targetval.indexOf('.')>=0 && afterDecimalLength==0) || afterDecimalLength>2 ){
		successAlert('Please enter valid positive value upto two decimal point');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#target'+x).focus();
			});
			return false;
		}
		
		if($target==='' && targetval==='')
				$target=' ';
			else if($target==='')
				$target=targetval;
			else
				$target+=','+targetval;
				
			}
			}
		
		if(typeof $('#ddlUser option:selected').val()==='undefined')
		$sentto = null;
		else
		$sentto = $('#ddlUser option:selected').val();
		confirmAlert('Do you want to Forward the Target Data ?');
		$("#ok").click(function(){
		$('#popup').modal('hide');
		$('#loading').show();
	var url ="forwardOutcomeTargetFromPIA";
		$.ajax({  
            url:url,
            type: "post",  
            data: {outcome:$outcome,outcomedetail:$outcomedetail,target:$target,projId:$projId,finYr:$finYr,sentTo:$sentto},
            error:function(xhr,status,er){
                console.log(er.message);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	if(data==='success'){
				successAlert("Data has been forwarded successfully !");
				$('.error').html('Data has been forwarded successfully');
				$('.error').css('color','white');
				$('.error').css('background-color','green');
				$('#draftSave').addClass('d-none');
				$('#btnForward').addClass('d-none');
			}else{
				successAlert("Target not Forwarded, Please try again !");
				$('.modal-body').css("color","red");
				$('.error').html('There is some error while forwarding. Please try again !');
			}
			$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	/*if(data==='success'){
		$('.error').html('Data has been forwarded successfully');
		$('.error').css('color','white');
		$('.error').css('background-color','green');
		$('#draftSave').addClass('d-none');
		$('#btnForward').addClass('d-none');
	}else{
		$('.error').html('There is some error while forwarding. Please try again !');
	}*/
	
	}
	});
	});
	
		});
		/************************************ end ********************************* */
		
		/************************************************************************** Action link click for WCDC *************************************** */
	$('#tbodyAapAchievement').on( 'click', 'a.action', function (e) {
		 // $('#loading').show();
$targetid= e.target.getAttribute('data-id');
$.ajax({  
            url:"checkForAlreadyForwardedOutcome",
            type: "post", 
            data: {targetid:$targetid},
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
			//alert(data[key].action+" : "+data[key].currentlyat+" : "+data[key].status+" : "+data[key].usertype);
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
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Outcome Target</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="ddlAction" id="ddlAction" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$targetid+'" name="target" id="targetid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info" onclick="submitaction()" >Submit</button>');
			}else{
				$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('');
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Outcome Target</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('');
						$('#popupreport .modal-body').html('<p style="color:red;text-align:center">Outcome Target has been '+$result+' </p>');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>');
			}
			
			}
			}
	}else{
	$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('<h5>Approval/Rejection of Outcome Target</h5> <span id="errormessage" ></span>');
						$('#popupreport .modal-body').html('<label>Action:- </label><select name="ddlAction" id="ddlAction" class="form-control"><option value="F">Approved</option><option value="R">Reject</option></select>'+ 
						'<label>Remarks:- </label><textarea  rows="5" class="form-control" name="remarks" id="remarks" style="height:100%"  name="reason" id="reason" value=" " ></textarea>'+
						'<input type="hidden" value="'+$targetid+'" name="targetid" id="targetid" />');
						$('#popupreport .modal-footer').html('');
						$('#popupreport .modal-footer').html(' <button id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button>'+
						'<button id="submitaction" name="submitaction" class="btn btn-info" onclick="submitaction()" >Submit</button>');	
	}
	CKEDITOR.replace('remarks');
	}
	});	
		
		});
		
/************************************************* End ************************************** */

/******************************************** View Movement Tab Click Script ************************************ */

		$('#viewMovement').click(function(e) {
		$('#loading').show();
	$.ajax({  
            url:"viewOutcomeTargetMovement",
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
			if(typeof d[k].remarks==='undefined')
			$remarks='';
			else
			$remarks=d[k].remarks;
			if(parseInt(k)===0)
			tbodyMovement.append("<tr><td>"+key+"</td><td>"+d[k].finyrdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+$remarks+"</td><td style='text-align:center;'><a class='detail' data-id="+d[k].targetid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].targetid+" href='#'>view</a></td></tr>");
			else
			tbodyMovement.append("<tr><td></td><td>"+d[k].finyrdesc+"</td><td>"+d[k].status+"</td><td>"+d[k].currentlyat+"</td><td class='remarks'>"+$remarks+"</td><td style='text-align:center;'><a class='detail' data-id="+d[k].targetid+" href='#'>view</a></td><td style='text-align:center;'><a class='movement' data-id="+d[k].targetid+" href='#'>view</a></td></tr>");
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
	
	/************************************************************************** End ************************************************************/
	
	/***************************************** Outcome Detail Link click  ************************* */
	
	$('#tbodyMovement').on( 'click', 'a.detail', function (e) {
	var del = e.target.getAttribute('data-id');
	//alert(del);
	$('#loading').show();
	$.ajax({  
            url:"getOutcomeTargetDetails",
            type: "post",  
            data: {targetid:del},
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
						$('#popupreport #popupreporttitle').html('Movement of Outcome Target');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[0].projectdesc+ ' and FY: '+data[0].finyrdesc+'<br/><span style="color:red"> Current Status: '+data[0].status+"</span>");
			}
			
			if(typeof data[key].outcomeactivity==='undefined')
			tblData+="<tr><td>"+data[key].outcomehead+"</td><td></td><td>"+data[key].target+"</td></tr>";
			else
			tblData+="<tr><td>"+data[key].outcomehead+"</td><td>"+data[key].outcomeactivity+"</td><td>"+data[key].target+"</td></tr>";
		
		}		
			}
	}else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
	$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>Name of Head</th><th>Name of Activity</th><th style="text-align:center;"><span >Target '+
						'</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
							});
	});
	
	/***************************************** Outcome Detail Link click Ends ************************* */
	
	/*************************************** Movement detail link click Script ***************************** */
	
	$('#tbodyMovement').on( 'click', 'a.movement', function (e) {
	var del = e.target.getAttribute('data-id');
	$('#loading').show();
	$.ajax({  
            url:"getOutcomeTargetMovementDetails",
            type: "post",  
            data: {targetid:del},
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
						$('#popupreport #popupreporttitle').html('Movement of Outcome Target Data');
						var i=1;
						if(Object.keys(data).length>0){
							
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			if(parseInt(i)===1){
			i=0;
			$('#popupreport #popupreporttitle').append('<br/>For Project: '+data[key].projectdesc+ ' and FY: '+data[key].finyrdesc+'<br/><span style="color:red"> Current Status: '+data[key].status+"</span>");
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
	
	
	});
	
	/********************************************** Page Load Script Ends ******************************************* */
	
	/************************************************** District Forward Click *********************************** */
	function submitaction(){
		$('#loading').show();
		$action= $('#ddlAction option:selected').val();
$remarks= CKEDITOR.instances['remarks'].getData(); 
$targetid=$('#targetid').val();
//alert($action+" : "+$remarks+" : "+$targetid);
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
            url:"actionByWCDCSLNA",
            type: "post",  
            data: {targetid:$targetid,action:$action,remarks:$remarks},
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
							successAlert('Data of Outcome Target is being rejected.');
							else
							successAlert('Outcome Target data has been finalized and completed.');
							//$('#errormessage').html("Data has been finalized and completed.");
							
						}
						if(data==='district'){
						//$('#errormessage').html("Data forwarded to SLNA for further approval.");
						if($action==='R')
							successAlert('Data of Outcome Target is being rejected.');
							else
						successAlert('Outcome Target data forwarded to SLNA for further approval.');	
						}
						$("#successok").click(function(){
$('#popup').modal('hide');
window.location.href="outcomeTargetWCDCSLNA";
});
						
	}
	});
	}
	
	/********************************************************************** End ******************************************************** */
	

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