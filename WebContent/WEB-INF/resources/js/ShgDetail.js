/**
 * 
 */
function selectAll1(ele) {
    var checkboxes = document.getElementsByName('shgid');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

$(document).on('click','.shgid ',function(){
	 const cbAll = document.querySelector('#checkAllTrans');
	if(cbAll.checked){
		$('#checkAllTrans').prop('checked', false);
	}
});

function selectAll(ele) {
    var checkboxes = document.getElementsByName('shg_detail_id1');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

$(document).on('click','.shg_detail_id1 ',function(){
	 const cbAll = document.querySelector('#checkAllTranxId');
	if(cbAll.checked){
		$('#checkAllTranxId').prop('checked', false);
	}
});


    function checkMinimumLength(inputField) {
        var val = $('#acount'+inputField).val();;
        if (val.length < 3 ||val.length > 18) {
			$('#accontError'+inputField).html('*Please fill the valid Account Number.');
			$('#accontError'+inputField).css('color','red');
            $('#acount'+inputField).val('');
            $('.acount'+inputField).focus();
        }else{
			$('#accontError'+inputField).html('');
		}
    }
	
/********************************************get SHG Name List****************************************/
$('#getShgDetail').on('click',function(){
	
	$schmIdDetails = "";
	$projId = $('#project').val();
	$grp = $('#grp').val();
	var i = 1;
	var i1 = 1;
	var i2 = 1;
	if($projId===''){
		$('.projectError').html('Please select Project.');
		$('.projectError').css('color','red');
		$('.project').focus();
		return false;
	}else{
		$('.projectError').html('');
	}
	if($grp===''){
		$('.projectErrors').html('Please select Head.');
		$('.projectErrors').css('color','red');
		$('.grp').focus();
		return false;
	}else{
		$('.projectErrors').html('');
	}
//	alert('ads');
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	$.ajax({  
            url:"getListShgDetails",
            type: "post",  
            data: {projId:$projId, grp:$grp},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						$tbodyShgDetails = $('#tbodyShgDetails');
						$tbodyShgDetails.empty();
						$tbodyCompleteSHGDetails = $('#tbodyCompleteSHGDetails');
						$tbodyCompleteSHGDetails.empty();
						$tbodyFinalCompleteSHGDetails = $('#tbodyFinalCompleteSHGDetails');
						$tbodyFinalCompleteSHGDetails.empty();
						
						if (Object.keys(data).length > 0) {
							for (var key in data) {
								if (data[key].status == 'A') {																		
									$tbodyShgDetails.append('<tr><td><input type="checkbox" id="shgid' + data[key].shg_detail_id + '" name="shgid" value="' + data[key].shg_detail_id + '"/>'   + '</td>'
									+ '<td>' + (i)+ '</td>'
									 + '<td>' + data[key].name + '</td>'
									 + '<td>' + data[key].scheme_description + '</td>'
									  + '<td>' + data[key].reg_date + '</td>'
										+ '<td><input type = "text" name ="acount" id="acount' + data[key].shg_detail_id + '" class="acount' + data[key].shg_detail_id + '"  oninput="this.value = this.value.replace(/[^A-Za-z0-9]/g, \'\');" minlength="3" maxlength="18" onpaste="return false;" onblur="checkMinimumLength('+ data[key].shg_detail_id+')"/><span id="accontError'+ data[key].shg_detail_id+'"></span></td>'
										+ '<td><input type = "text"  name ="ifsc" id="ifsc' + data[key].shg_detail_id + '" class="ifsc' + data[key].shg_detail_id + '" oninput="this.value = this.value.toUpperCase();" maxlength="11" onpaste="return false;"/></td></tr>');
									i++;

									$('#saveAsDraft').removeClass('d-none');
								}
								if(data[key].status=='D'){
									$tbodyCompleteSHGDetails.append(
										'<tr><td><input type="checkbox" id="shg_detail_id1' + data[key].shg_detail_id + '" name="shg_detail_id1" value="' + data[key].shg_detail_id + '"/>'  + '</td>'
									+ '<td>' + (i1)+ '</td>'
									 + '<td>' + data[key].name + '</td>'
									+ '<td>' + data[key].scheme_description + '</td>'
									  + '<td>' + data[key].reg_date + '</td>'
										+ '<td>'+ data[key].account_detail+'</td>'
										+ '<td>' + data[key].ifsc_code+'</td><td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteShgId(' + data[key].shg_detail_id + ')"/></td></tr>'
									)
									i1++;
									$('#complete').removeClass('d-none');
								}
								if(data[key].status=='C'){
									$tbodyFinalCompleteSHGDetails.append(
										'<tr><td>'+(i2) + '</td>'
										+'<td>'+data[key].name + '</td>'
										 + '<td>' + data[key].scheme_description + '</td>'
									  + '<td>' + data[key].reg_date + '</td>'
										+ '<td>'+ data[key].account_detail+'</td>'
										+ '<td>' + data[key].ifsc_code+' </td></tr>'
									)
									i2++;
									$('#complete').removeClass('d-none');
								}
								}
								
						}
						else{
					$tbodyShgDetails.append('<tr><td colspan="6" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteSHGDetails.append('<tr><td colspan="6" class="text-center">Data not found !</td></tr>');
					$tbodyFinalCompleteSHGDetails.append('<tr><td colspan="6" class="text-center">Data not found</td></tr>');
				}
						}
            
		});
									
					
	
});


/******************************************************ON CLICK Save As Draft*******************************************************/

$('#saveAsDraft').on('click',function(){
	var shgid = [];
	var shg_detail_id = new Array();
	var account_detail = new Array();
	var ifsc_code = new Array();
	
	$projId = $('#project').val();
	$grp = $('#grp').val();
	$flag=true;
	$id = '';
	
	$("input[name='shgid']:checked").each(function(){
            shg_detail_id.push(this.value);
         account_detail.push($('#acount'+this.value).val());
         ifsc_code.push($('#ifsc'+this.value).val());
         
         if($('#acount'+this.value).val()==''){
			 $flag = false;
			 $id = $('#acount'+this.value);
		 }
		 
         if($('#ifsc'+this.value).val()==''){
			 $flag = false;
			 $id = $('#ifsc'+this.value);
		 }
        
         
   });
   
   if(!$flag){
	   alert('Please Fill the Account Details Before Save.');
	   $id.focus();
	   $flag = true;
	   return false;
   }
   
if(shg_detail_id=='' || shg_detail_id == undefined || shg_detail_id == null)
{
	alert('Please Click at least One Check Box.')
	$flag=false;
	return false;
}
if(account_detail=='' || account_detail == undefined || account_detail == null)
{
	alert('Please fill Account Details')
	$flag=false;
	return false;
}

if(ifsc_code=='' || ifsc_code == undefined || ifsc_code == null)
{
	alert('Please fill IFSC Code')
	$flag=false;
	return false;
}


else{
	
	confirmAlert("Do you want to Save Selected Details");
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
			
	$.ajax({  
		
            url:"saveDraftShgDetails",
            type: "post",  
            data: {shg_detail_id:shg_detail_id.toString(),account_detail:account_detail.toString(),ifsc_code:ifsc_code.toString()},
            error:function(xhr,status,er){
                console.log(er);
            },
	success: function(data) {
		$('#loading').hide();
		if(data==='success'){
			alert('Account Detail Saved As Draft Successfully');
			window.location.href='getShgDetails';
			}
		else{
			alert('There is some Problem while Saving Data..');
			window.location.href='getShgDetails';
		}
		}
		
	});
	});
}
});

/****************************************************************on Click delete button***************************************************/
function deleteShgId(id){
	var shgid = [];
	var shg = new Array();
	$flag=true;
	
	$shg_detail_id = $('#shg_detail_id1'+id).val();
	$projId = $('#project').val();
	var i = 1;
//	$("input[name='shg_detail_id1']:checked").each(function(){
//		
//            shg.push(this.value);
//            
//            });
//	if(shg=='' || shg == undefined || shg == null)
//{
//	alert('Please Click at least One Check Box.')
//	$flag=false;
//	return false;
//}
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
            url:"deleteSHGDetails",
            type: "post",  
            data: {shg_detail_id:id},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
		$('#loading').hide();
		if(data==='success'){
			alert('Account Details deleted Successfully');
			window.location.href='getShgDetails';
			}
		else{
			alert('There is some Problem while Deleting Data..');
			window.location.href='getShgDetails';
		}
		}

				});
				}
				
/*************************************************************Complete button*********************************************************/
$('#complete').on('click', function() {
	
	var shg_detail_id1 = [];
	$("input[name='shg_detail_id1']:checked").each(function() {
		shg_detail_id1.push(this.value);
	});
	
	if (shg_detail_id1 == '') {
		alert('please select atleast one check box')
		$flag = false;
		return false;
	}
	else {
		confirmAlert("Do You want to Complete Selected Details");
		$("#ok").html('Yes');
		$("#cancel").html('No');
		$("#ok").click(function() {
			$('#popup').modal('hide');
			$('#loading').show();
			$.ajax({
				url: "completeShgDetails",
				type: "post",
				data: {shg_detail_id1: shg_detail_id1},
				error: function(xhr, status, er) {
					console.log(er);
					$('.error').append(' There is some error please try again !');
				},
				success: function(data) {
					console.log(data);
					$('#loading').hide();
					if (data === 'success') {
						alert('Account Details Completed Successfully');
						window.location.href='getShgDetails';
					} else {
						alert('There was an issue to Complete the Data');
						window.location.href='getShgDetails';
					}

				}
			});
		});
	}
});


/*************************************************************Remaining Old SHG Link button*********************************************************/
$(document).ready(function(){
	
	$(SHGRptTbody).on('click','a.oldshg',function(e){
		var del = e.target.getAttribute('data-id');
		var type="oldSHG";
		$.ajax({
			url:"getRemainingSHGDetails",
			type:"post",
			data:{dcode:del, type:type},
			error:function(xhr,status,er){
				console.log(er);
				$('.error').append('There is some error please try again !');
			},
			success:function(ShgDetailBean){
				console.log(ShgDetailBean);
				var tblData="";
				$('#popupreport').modal('toggle');
				$('#popupreport').modal('show');
				$('#popupreport #popupreporttitle').html('List of Existing created SHG Not Entered Account Details');
				var i=1;
				if(Object.keys(ShgDetailBean).length>0){
					for(var key in ShgDetailBean){
						if(ShgDetailBean.hasOwnProperty(key)){
							tblData+="<tr><td>"+i+"</td><td>"+ShgDetailBean[key].proj_name+"</td><td>"+ShgDetailBean[key].name+"</td><td>"+ShgDetailBean[key].scheme_description+"</td><td>"+ShgDetailBean[key].reg_date+"</td></tr>";
						i++;
						}
					}
				}
				else{
					tblData="<tr><td>Data not found !</td></tr>";
				}
				$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S.No.</th><th>Project Name</th>'+
						'<th>SHG Name</th>'+
						'<th>Department/ Scheme</th>'+
						'<th>Date of Registration</th></tr></thead><tbody>'+tblData+'</tbody></table>');
			}
		});
	});



/*************************************************************Remaining New SHG Link button*********************************************************/

	$(SHGRptTbody).on('click','a.newshg',function(e){
		var del = e.target.getAttribute('data-id');
		var type="newSHG";
		$.ajax({
			url:"getRemainingSHGDetails",
			type:"post",
			data:{dcode:del, type:type},
			error:function(xhr,status,er){
				console.log(er);
				$('.error').append('There is some error please try again !');
			},
			success:function(ShgDetailBean){
				console.log(ShgDetailBean);
				var tblData="";
				$('#popupreport').modal('toggle');
				$('#popupreport').modal('show');
				$('#popupreport #popupreporttitle').html('List of Newly created SHG Not Entered Account Details');
				var i=1;
				if(Object.keys(ShgDetailBean).length>0){
					for(var key in ShgDetailBean){
						if(ShgDetailBean.hasOwnProperty(key)){
							tblData+="<tr><td>"+i+"</td><td>"+ShgDetailBean[key].proj_name+"</td><td>"+ShgDetailBean[key].name+"</td><td>"+ShgDetailBean[key].scheme_description+"</td><td>"+ShgDetailBean[key].reg_date+"</td></tr>";
						i++;
						}
					}
				}
				else{
					tblData="<tr><td>Data not found !</td></tr>";
				}
				$('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S.No.</th><th style="width:90%">Project Name</th>'+
						'<th>SHG Name</th>'+
						'<th>Department/ Scheme</th>'+
						'<th>Date of Registration</th></tr></thead><tbody>'+tblData+'</tbody></table>');
			}
		});
	
	});

});