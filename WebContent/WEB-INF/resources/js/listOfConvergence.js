function selects(ele) {
    var checkboxes = document.getElementsByName('workcode');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

function selectAll(ele) {
    var checkboxes = document.getElementsByName('workcode1');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

$(document).on('click','#workcode',function(){
	 const cbAll = document.querySelector('#checkAllTranx');
	if(cbAll.checked ){
		$('#checkAllTranx').prop('checked', false);
	}
});

$(document).on('click','.workcode1',function(){
	 const cbAll = document.querySelector('#checkAllTranxId');
	if(cbAll.checked){
		$('#checkAllTranxId').prop('checked', false);
	}
});

$(document).on('click','input:radio',function(e) { 
	var i =$(this).attr('data-id');
 if($(this).hasClass('full'+i)) {
	 document.getElementById("full"+i).checked = true;
	 var wdc = document.querySelector('input[name=wdcExpndtr'+i+']');
	 wdc.value = 0;
	 document.querySelector('input[name=wdcExpndtr'+i+']').disabled = true;
  }else if($(this).hasClass('partial'+i)) {
	
	document.getElementById("partial"+i).checked = true;
	var wdc = document.querySelector('input[name=wdcExpndtr'+i+']');
	 wdc.value = '';
	document.querySelector('input[name=wdcExpndtr'+i+']').disabled = false;
  }

});

function checkWdcNumber(workcode) { 
	var status = $('input[name="status'+workcode+'"]:checked').val();
	var wdcexp = $('#wdcExpndtr'+workcode).val();
	if((wdcexp == 0 || wdcexp == '') && status =='P'){
		alert('Expenditure can not be Zero if Convergence Status is Partial');
		$('#wdcExpndtr'+workcode).val('');
	}
}
function checkSchNumber(workcode) { 
	var schexp = $('#cnvrgExpndtr'+workcode).val();
		
	if(schexp ==0 || schexp ==''){
		alert('Expenditure Under Convergence Scheme can not be Zero or Blank');
		$('#cnvrgExpndtr'+workcode).val('');
	}
}



/********************************************get Convergence Work List****************************************/
$('#getTranxDetail').on('click',function(){
	
	$schmIdDetails = "";
	$projId = $('#project').val();
	var i = 1;
	if($projId===''){
		$('.projectError').html('Please select Project.');
		$('.projectError').css('color','red');
		$('.project').focus();
		return false;
	}else{
		$('.projectError').html('');
	}
	
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	$.ajax({  
            url:"getListConvergenceWork",
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$tbodyTranxDetails = $('#tbodyTranxDetails');
				$tbodyTranxDetails.empty();
				$tbodyCompleteTranxDetails = $('#tbodyCompleteTranxDetails');
				$tbodyCompleteTranxDetails.empty();
				
				if(Object.keys(data).length>0){
					for ( var key in data) {
						$.ajax({
							url: "getConvergenceScheme",
							type: "post",
							data: {},
							error: function(xhr, status, er) {
								console.log(er);
							},
							success: function(data1) {
								console.log(data1);
								for (var key1 in data1) {
									$schmIdDetails += '<option   value="">--select--</option>'+'<option   value="' + key1 + '">' + data1[key1] + '</option>';
								}
							},
							async:false
						});
//									alert('check '+$schmIdDetails);
						if(data[key].cvrgdcstatus=='A'){
								$tbodyTranxDetails.append('<tr><td><input type="checkbox" id="workcode'+data[key].workcode+'" name="workcode" value="'+data[key].workcode+'"/>'+data[key].workcode+'</td>'
								+'<td>'+data[key].headname+'</td>'+'<td>'+data[key].actname+'</td>'+'<td>'+data[key].status+'</td>'
								+'<td>'+data[key].achievement+'</td>'+'<td>'+data[key].unitname+'</td>'+'<td style="white-space: nowrap;"><input type="radio" class="full'+i+'" name = "status'+data[key].workcode+'" value="F" data-id="'+i+'" id="full'+i+'" >Full<input type="radio" class="partial'+i+'" name = "status'+data[key].workcode+'" value="P" data-id="'+i+'" id="partial'+i+'" checked = "checked">Partial</td>'+
								'<td><select id="schmid'+data[key].workcode+'" class="form-control">'+$schmIdDetails+'</select></td>'
								+'<td><input type = "number" name ="wdcExpndtr'+i+'" id="wdcExpndtr'+data[key].workcode+'" class="form-control" onblur = "checkWdcNumber('+data[key].workcode+')" onfocusin="decimalCheck(event);" maxlength="20" onpaste="return false;"/></td>'
								+'<td><input type = "number" nane ="cnvrgExpndtr'+i+'" id="cnvrgExpndtr'+data[key].workcode+'" class="form-control" onblur = "checkSchNumber('+data[key].workcode+')" onfocusin="decimalCheck(event);" maxlength="20" onpaste="return false;"/></td></tr>');
								i++;
								
								$('#saveAsDraft').removeClass('d-none');
						}
						var cnrstatus = data[key].cnvrgstatus=="F"?"Full":"Partial";
						if(data[key].cvrgdcstatus=='D'){
							$tbodyCompleteTranxDetails.append('<tr><td><input type="checkbox" id="workcode1'+data[key].cnvrgwrkid+'" name="workcode1" class ="workcode1" value="'+data[key].cnvrgwrkid+'"/>'+data[key].workcode+'</td>'
							+'<td>'+data[key].headname+'</td><td>'+data[key].actname+'</td><td>'+data[key].status+'</td>'+'<td>'+data[key].achievement+'</td><td>'+data[key].unitname+'</td>'
							+'<td style="white-space: nowrap;">'+cnrstatus+'</td>'
							+'<td>'+data[key].schemename+'</td><td>'+data[key].expndwdc+'</td><td>'+data[key].expndcnvrgsch+'</td><td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteWorkId('+data[key].cnvrgwrkid+')"/></td></tr>')
								$('#complete').removeClass('d-none');
						}
						$schmIdDetails = "";
					}
				}
				else{
					$tbodyTranxDetails.append('<tr><td colspan="10" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteTranxDetails.append('<tr><td colspan="11" class="text-center">Data not found !</td></tr>');
				}
			}
		});
	
	
});
/******************************************************ON CLICK Save As Draft*******************************************************/

$('#saveAsDraft').on('click',function(){
	var workcode = [];
	var cnvrgstatus = new Array();
	var cnvrgschmeid = new Array();
	var expndwdc = new Array();
	var expndcnvrgschm = new Array();
	$projId = $('#project').val();
	$flag=true;
	$id = '';
	
	$("input[name='workcode']:checked").each(function(){
            workcode.push(this.value);
          /*workid.push(this.value);*/
         var cstatus = $('input[name="status'+this.value+'"]:checked').val();
         cnvrgstatus.push(cstatus);
         cnvrgschmeid.push($('#schmid'+this.value).val());
         if($('#schmid'+this.value).val()==''){
			 $flag = false;
			 $id = $('#schmid'+this.value);
		 }
         expndwdc.push($('#wdcExpndtr'+this.value).val());
         if($('#wdcExpndtr'+this.value).val()==''){
			 $flag = false;
			 $id = $('#wdcExpndtr'+this.value);
		 }
         expndcnvrgschm.push($('#cnvrgExpndtr'+this.value).val());
         if($('#cnvrgExpndtr'+this.value).val()==''){
			 $flag = false;
			 $id = $('#cnvrgExpndtr'+this.value);
		 }
         
   });
   
   if(!$flag){
	   alert('Please Select/Fill the all Selection/Input Before Save.');
//	   $id.focus();
	   $flag = true;
	   return false;
   }
   
if(workcode=='' || workcode == undefined || workcode == null)
{
	alert('Please Click at least One Check Box.')
	$flag=false;
	return false;
}
if(cnvrgstatus=='' || cnvrgstatus == undefined || cnvrgstatus == null)
{
	alert('Please Select At least One Radio Button to Select Convergence')
	$flag=false;
	return false;
}
if(cnvrgschmeid=='' || cnvrgschmeid == undefined || cnvrgschmeid == null)
{
	alert('Please Select atleast One Convergence Scheme')
	$('#schmid'+this.value).focus();
	$flag=false;
	return false;
}
if(expndwdc=='' || expndwdc == undefined || expndwdc == null)
{
	alert('Please Input the Expenditure Wdc');
	$('#wdcExpndtr'+this.value).focus();
	$flag=false;
	return false;
}
if(expndcnvrgschm=='' || expndcnvrgschm == undefined || expndcnvrgschm == null)
{
	alert('Please Input The Expenditure of Convergence Scheme.');
	$('#cnvrgExpndtr'+this.value).focus();
	$flag=false;
	return false;
}
else{
	if(confirm('Do You want to Save the Convergence Details')){
		
	
	$.ajax({  
            url:"saveasdraftcnvrgdtl",
            type: "post",  
            data: {workcode:workcode.toString(),cnvrgstatus:cnvrgstatus.toString(),cnvrgschmeid:cnvrgschmeid.toString(),expndwdc:expndwdc.toString(),expndcnvrgschm:expndcnvrgschm.toString()},
            error:function(xhr,status,er){
                console.log(er);
            },
	success: function(data) {
		$('#loading').hide();
		if(data==='success'){
			alert('Convergence Details of Selected WorkIds Saved As Draft Successfully');
			window.location.href='getListConvergenceWork';
			}
		else{
			alert('There is some Problem while Saving Data..');
			window.location.href='getListConvergenceWork';
		}
		
		}
	});
	}
	
}
});
/****************************************************************on Click delete button***************************************************/
function deleteWorkId(id){
	$workcode = $('#workcode'+id).val();
	$projId = $('#project').val();
	var i = 1;
	if($projId===''){
		$('.projectError').html('Please select Project.');
		$('.projectError').css('color','red');
		$('.project').focus();
		return false;
	}else{
		$('.projectError').html('');
	}
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	$.ajax({  
            url:"deleteCnvrgnceWorkCode",
            type: "post",  
            data: {workcode:id},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				if(data==='success'){
					alert('Convergence Details of Selected WorkId Deleted Successfully');
				}else{
					alert('There Was an issue to Delete WorkId.');
				}
				$.ajax({
					url: "getListConvergenceWork",
					type: "post",
					data: { projId: $projId},
					error: function(xhr, status, er) {
						console.log(er);
						$('.error').append(' There is some error please try again !');
					},
					success: function(data) {
						console.log(data);
						$tbodyTranxDetails = $('#tbodyTranxDetails');
						$tbodyTranxDetails.empty();
						$tbodyCompleteTranxDetails = $('#tbodyCompleteTranxDetails');
						$tbodyCompleteTranxDetails.empty();
						$('#checkAllTranx').prop('checked', false);
						if (Object.keys(data).length > 0) {
							for (var key in data) {
								$.ajax({
									url: "getConvergenceScheme",
									type: "post",
									data: {},
									error: function(xhr, status, er) {
										console.log(er);
									},
									success: function(data1) {
										console.log(data1);
										for (var key1 in data1) {
											$schmIdDetails += '<option   value="">--select--</option>' + '<option   value="' + key1 + '">' + data1[key1] + '</option>';
										}
									},
									async: false
								});
								//									alert('check '+$schmIdDetails);
								if (data[key].cvrgdcstatus == 'A') {
									$tbodyTranxDetails.append('<tr><td><input type="checkbox" id="workcode' + data[key].workcode + '" name="workcode" value="' + data[key].workcode + '"/>' + data[key].workcode + '</td>'
										+ '<td>' + data[key].headname + '</td>' + '<td>' + data[key].actname + '</td>' + '<td>' + data[key].status + '</td>'
										+ '<td>' + data[key].achievement + '</td>' + '<td>' + data[key].unitname + '</td>' + '<td style="white-space: nowrap;"><input type="radio" class="full' + i + '" name = "status' + data[key].workcode + '" value="F" data-id="' + i + '" id="full' + i + '" >Full<input type="radio" class="partial' + i + '" name = "status' + data[key].workcode + '" value="P" data-id="' + i + '" id="partial' + i + '" checked = "checked">Partial</td>' +
										'<td><select id="schmid' + data[key].workcode + '" class="form-control">' + $schmIdDetails + '</select></td>'
										+'<td><input type = "number" name ="wdcExpndtr'+i+'" id="wdcExpndtr'+data[key].workcode+'" class="form-control" onblur = "checkWdcNumber('+data[key].workcode+')" onfocusin="decimalCheck(event);" maxlength="20" onpaste="return false;"/></td>'
										+'<td><input type = "number" nane ="cnvrgExpndtr'+i+'" id="cnvrgExpndtr'+data[key].workcode+'" class="form-control" onblur = "checkSchNumber('+data[key].workcode+')" onfocusin="decimalCheck(event);" maxlength="20" onpaste="return false;"/></td></tr>');
									i++;

									$('#saveAsDraft').removeClass('d-none');
								}
								var cnrstatus = data[key].cnvrgstatus == "F" ? "Full" : "Partial";
								if (data[key].cvrgdcstatus == 'D') {
									$tbodyCompleteTranxDetails.append('<tr><td><input type="checkbox" id="workcode1' + data[key].cnvrgwrkid + '" name="workcode1" class ="workcode1" value="' + data[key].cnvrgwrkid + '"/>' + data[key].workcode + '</td>'
										+ '<td>' + data[key].headname + '</td><td>' + data[key].actname + '</td><td>' + data[key].status + '</td>' + '<td>' + data[key].achievement + '</td><td>' + data[key].unitname + '</td>'
										+ '<td style="white-space: nowrap;">' + cnrstatus + '</td>'
										+ '<td>' + data[key].schemename + '</td><td>' + data[key].expndwdc + '</td><td>' + data[key].expndcnvrgsch + '</td><td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteWorkId(' + data[key].cnvrgwrkid + ')"/></td></tr>')
									$('#complete').removeClass('d-none');
								}
								$schmIdDetails = "";
							}
						}
						else {
							$tbodyTranxDetails.append('<tr><td colspan="10" class="text-center">Data not found !</td></tr>');
							$tbodyCompleteTranxDetails.append('<tr><td colspan="11" class="text-center">Data not found !</td></tr>');
						}
					}
				});
				}
				});
				
}	

/*************************************************************Complete button*********************************************************/
$('#complete').on('click', function() {
	
	var workcode1 = [];
	$("input[name='workcode1']:checked").each(function() {
		workcode1.push(this.value);
	});
	
	if (workcode1 == '') {
		alert('please select atleast one Work Code id')
		$flag = false;
		return false;
	}
	else {
		if(confirm('Do You want to Complete the Convergence Details')){
			$.ajax({
				url: "completeConvergenceWorkCode",
				type: "post",
				data: {workcode1: workcode1},
				error: function(xhr, status, er) {
					console.log(er);
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						alert('Convergence Details of Selected WorkIds Completed Successfully');
						window.location.href='getListConvergenceWork';
					} else {
						alert('There was an issue to Complete transactiond');
						window.location.href='getListConvergenceWork';
					}

				}
			});
		}
	}
});	
	
	
	
	