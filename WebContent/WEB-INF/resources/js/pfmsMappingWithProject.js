/************************************************On Select Search Master ********************************************/
$('#srchMaster').on('change',function(){
	$srchMaster = $('#srchMaster').val();
	if($srchMaster ===""){
		window.location.reload();
	}
	$('#project').val("");
	$('#tbodyTranxDetails').empty();
	$('#tbodyCompleteTranxDetails').empty();
	
	
	
});
/************************************************On Click Get Details ********************************************/
$('#getTranxDetail').on('click',function(){
	$srchMaster = $('#srchMaster').val();
	$projId = $('#project').val();
	$projName = $('#project').find(":selected").text();
	if($srchMaster===''){
		$('.srchMasterError').html('Please select Search Level.');
		$('.srchMasterError').css('color','red');
		$('.srchMaster').focus();
		return false;
	}else{
		$('.srchMasterError').html('');
	}
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
            url:"getPfmsTransaction",
            type: "post",  
            data: {masterId:$srchMaster,projId:$projId},
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
				var O = true;
				var D = true;
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							if(data[key].status==='O'){
								O = false;
								$tbodyTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId" name="eatmisdataId" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
								+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
								+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'+'<td>'+$projName+'</td></tr>');
								$('#saveAsDraft').removeClass('d-none');
							}
							if(data[key].status==='D' && data[key].projectId ==$projId){
								D = false;
								$tbodyCompleteTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId1" name="eatmisdataId1" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
								+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
								+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
								+'<td>'+$projName+'</td>'+'<td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteTranx('+data[key].eatmisdataId+')"/></td></tr>');
								$('#complete').removeClass('d-none');
							}
						}
					}
					var OS = Boolean(O);
					var DS = Boolean(D);
					if(OS){
						$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}if(DS){
						$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}
				}else{
					$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
				}
			}
		});
	
});

/************************************************Function to Checked All Transactions ********************************************/
function selects(ele) {
    var checkboxes = document.getElementsByName('eatmisdataId');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

function selectAll(ele) {
    var checkboxes = document.getElementsByName('eatmisdataId1');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}
function selectAllCom(ele) {
    var checkboxes = document.getElementsByName('eatmisdataIdc');
//    alert('check '+checkboxes.length);
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

$(document).on('click','#eatmisdataId',function(){
	 const cbAll = document.querySelector('#checkAllTranx');
	if(cbAll.checked ){
		$('#checkAllTranx').prop('checked', false);
	}
});

$(document).on('click','#eatmisdataId1',function(){
	 const cbAll = document.querySelector('#checkAllTranxId');
	if(cbAll.checked ){
		$('#checkAllTranxId').prop('checked', false);
	}
});

$(document).on('click','#eatmisdataIdc',function(){
	 const cbAll = document.querySelector('#checkAllTranxIdc');
	if(cbAll.checked ){
		$('#checkAllTranxIdc').prop('checked', false);
	}
});

/************************************************On Click SaveAsDraft ********************************************/
$('#saveAsDraft').on('click',function(){
	$srchMaster = $('#srchMaster').val();
	$projId = $('#project').val();
	$projName = $('#project').find(":selected").text();
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	var eatmisdataId = [];
	$("input[name='eatmisdataId']:checked").each(function(){
            eatmisdataId.push(this.value);
        });
		$.ajax({  
            url:"saveAsDraftPfmsTransaction",
            type: "post",  
            data: {projId:$projId, eatmisdataId:eatmisdataId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				if(data==='success'){
					alert('Transactions Saved As Draft Successfully');
				}else{
					alert('Transactions was not Saved As Draft.');
				}
				
				$.ajax({  
           			url:"getPfmsTransaction",
           	 		type: "post",  
            		data: {masterId:$srchMaster,projId:$projId},
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
					var O = true;
					var D = true;
					$('#checkAllTranx').prop('checked', false);
					if(Object.keys(data).length>0){
						for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								if(data[key].status==='O'){
									O = false;
									$tbodyTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId" name="eatmisdataId" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
									+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
									+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'+'<td>'+$projName+'</td></tr>');
									$('#saveAsDraft').removeClass('d-none');
								}if(data[key].status==='D' && data[key].projectId ==$projId){
									D = false;
									$tbodyCompleteTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId1" name="eatmisdataId1" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
									+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
									+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
									+'<td>'+$projName+'</td>'+'<td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteTranx('+data[key].eatmisdataId+')"/></td></tr>');
									$('#complete').removeClass('d-none');
								}
							}
						}
					var OS = Boolean(O);
					var DS = Boolean(D);
					if(OS){
						$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}if(DS){
						$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}
				}else{
					$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
				}
					}
				});
				
			}
		});
	
});

/************************************************On Click Delete ********************************************/
function deleteTranx(id){
	$srchMaster = $('#srchMaster').val();
	$projId = $('#project').val();
	$projName = $('#project').find(":selected").text();
	$eatmisdataId = id;
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
		$.ajax({  
            url:"deletePfmsTransaction",
            type: "post",  
            data: {eatmisdataId:$eatmisdataId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				if(data==='success'){
					alert('Transaction Deleted Successfully');
				}else{
					alert('There was an issue to delete transaction.');
				}
				$.ajax({  
           			url:"getPfmsTransaction",
           	 		type: "post",  
            		data: {masterId:$srchMaster,projId:$projId},
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
					var O = true;
					var D = true;
					if(Object.keys(data).length>0){
						for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								if(data[key].status==='O'){
									O = false;
									$tbodyTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId" name="eatmisdataId" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
									+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
									+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'+'<td>'+$projName+'</td></tr>');
									$('#saveAsDraft').removeClass('d-none');
								}if(data[key].status==='D'  && data[key].projectId ==$projId){
									D = false;
									$tbodyCompleteTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId1" name="eatmisdataId1" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
									+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
									+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
									+'<td>'+$projName+'</td>'+'<td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteTranx('+data[key].eatmisdataId+')"/></td></tr>');
									$('#complete').removeClass('d-none');
								}
							}
						}
					var OS = Boolean(O);
					var DS = Boolean(D);
					if(OS){
						$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}if(DS){
						$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}
				}else{
					$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
				}
				}
			});
				
			}
		});
}

/************************************************On Click Complete ********************************************/
$('#complete').on('click',function(){
	$projId = $('#project').val();
	$srchMaster = $('#srchMaster').val();
	$projName = $('#project').find(":selected").text();
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	var eatmisdataId = [];
	$("input[name='eatmisdataId1']:checked").each(function(){
            eatmisdataId.push(this.value);
        });
		$.ajax({  
            url:"completePfmsTransaction",
            type: "post",  
            data: {eatmisdataId:eatmisdataId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				if(data==='success'){
					alert('Transactions Completed Successfully');
				}else{
					alert('There was an issue to Complete transactiond');
				}
				$.ajax({  
           			url:"getPfmsTransaction",
           	 		type: "post",  
            		data: {masterId:$srchMaster,projId:$projId},
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
					var O = true;
					var D = true;
					$('#checkAllTranxId').prop('checked', false);
					if(Object.keys(data).length>0){
						for ( var key in data) {
							if (data.hasOwnProperty(key)) {
								if(data[key].status==='O'){
									O = false;
									$tbodyTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId" name="eatmisdataId" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
									+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
									+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'+'<td>'+$projName+'</td></tr>');
									$('#saveAsDraft').removeClass('d-none');
								}if(data[key].status==='D' && data[key].projectId ==$projId){
									D = false;
									$tbodyCompleteTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataId1" name="eatmisdataId1" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
									+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
									+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
									+'<td>'+$projName+'</td>'+'<td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteTranx('+data[key].eatmisdataId+')"/></td></tr>');
									$('#complete').removeClass('d-none');
								}
							}
						}
					var OS = Boolean(O);
					var DS = Boolean(D);
					if(OS){
						$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}if(DS){
						$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}
				}else{
					$tbodyTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
				}
					}
				});
				
			}
		});
});


/************************************************On Click Get Completed Transactions Details ********************************************/
$('#getComTranxDetail').on('click',function(){
	$state = $('#state').val();
	$district = $('#district').val();
	$projId = $('#project').val();
	$projName = $('#project').find(":selected").text();
	
	if($state===''){
		$('.stateError').html('Please select State.');
		$('.stateError').css('color','red');
		$('.state').focus();
		return false;
	}else{
		$('.stateError').html('');
	}
	
	if($district===''){
		$('.districtError').html('Please select District.');
		$('.districtError').css('color','red');
		$('.district').focus();
		return false;
	}else{
		$('.districtError').html('');
	}
	
	if($projId===''){
		$('.projectError').html('Please select Project.');
		$('.projectError').css('color','red');
		$('.project').focus();
		return false;
	}else{
		$('.projectError').html('');
	}
	
	
	$('#completed').addClass('d-none');
		$.ajax({  
            url:"getCompletedPfmsTransaction",
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$tbodyCompletedTranxDetails = $('#tbodyCompletedTranxDetails');
				$tbodyCompletedTranxDetails.empty();
				var D = true;
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							if(data[key].status==='C' && data[key].projectId ==$projId){
								D = false;
								$tbodyCompletedTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataIdc" name="eatmisdataIdc" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
								+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
								+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
								+'<td>'+$projName+'</td></tr>');
								$('#completed').removeClass('d-none');
							}
						}
					}
					var DS = Boolean(D);
					if(DS){
						$tbodyCompletedTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
					}
				}else{
					$tbodyCompletedTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
				}
			}
		});
	
});

/************************************************On Click UpdateAsDraft ********************************************/
$('#completed').on('click',function(){
	$projId = $('#project').val();
	$projName = $('#project').find(":selected").text();
	$('#completed').addClass('d-none');
	var eatmisdataId = [];
	$("input[name='eatmisdataIdc']:checked").each(function(){
//			alert('check '+this.value);
            eatmisdataId.push(this.value);
        });
		$.ajax({  
            url:"saveAsDraftPfmsTransaction",
            type: "post",  
            data: {projId:$projId, eatmisdataId:eatmisdataId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				if(data==='success'){
					alert('Transactions Unfreeze Successfully');
				}else{
					alert('Transactions was not Unfreeze Successfully.');
				}
				
				$.ajax({
					url: "getCompletedPfmsTransaction",
					type: "post",
					data: {projId: $projId},
					error: function(xhr, status, er) {
						console.log(er);
						$('.error').append(' There is some error please try again !');
					},
					success: function(data) {
						console.log(data);
						$tbodyCompletedTranxDetails = $('#tbodyCompletedTranxDetails');
						$tbodyCompletedTranxDetails.empty();
						var D = true;
						if (Object.keys(data).length > 0) {
							for (var key in data) {
								if (data.hasOwnProperty(key)) {
									if (data[key].status === 'C' && data[key].projectId == $projId) {
										D = false;
										$tbodyCompletedTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataIdc" name="eatmisdataIdc" value="' + data[key].eatmisdataId + '"/>' + data[key].tranxId + '</td>'
											+ '<td>' + data[key].date + '</td>' + '<td>' + data[key].componentCode + '</td>' + '<td>' + data[key].componentName + '</td>'
											+ '<td>' + data[key].invoiceNo + '</td>' + '<td>' + data[key].totalAmount + '</td>'
											+ '<td>' + $projName + '</td></tr>');
										$('#completed').removeClass('d-none');
									}
								}
							}
							var DS = Boolean(D);
							if (DS) {
								$tbodyCompletedTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
							}
						} else {
							$tbodyCompletedTranxDetails.append('<tr><td colspan="8" class="text-center">Data not found !</td></tr>');
						}
					}
				});
				
			}
		});
	
});
