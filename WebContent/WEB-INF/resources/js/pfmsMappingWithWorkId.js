
//on keyup
$(document).on("keyup", ".counts input", function() {
  var total_qty = parseInt($(this).closest("tr").find("td:eq(5)").text())
  var selector = $(this).closest("tr");
  var total = 0;
  //loop through counts div which is visible
  selector.find(".counts:visible input").each(function() {
    //sum 
    total += ($(this).val() != "") && ($(this).val() !== undefined) ? parseInt($(this).val()) : 0
  })
  //compare
  
   
  if (total > total_qty) {
	   var $this = $(this),
      id    = $this[0].id;
      var ctx = document.getElementById(id).value="";
       alert("Current expenditure is: " + total + " Please enter equal to Total amount: " + total_qty);
   }

})




$(".form-control option").each(function(i) {
  //append textboxes for each options
  $(".textboxes").append("<div class='counts'><label>" + $(this).text() + " </label><input type=text name='product_counts' id=" + i + " placeholder='Enter counts ..'></div>")
})
//onchnage of select
$(".form-control").on("change", function() {
  $(".counts").hide() //hide all counts divs
  //loop through selctd options
  $(this).find("option:selected").each(function() {
    var index = $(this).index(); //get indx of options 0,1..etc
    $("#" + index).closest("div.counts").show() //show that divs 
  });
})

$('#srchMaster').on('change',function(){
	$srchMaster = $('#srchMaster').val();
	if($srchMaster ===""){
		window.location.reload();
	}
	$('#project').val("");
	$('#tbodyTranxDetails').empty();
	$('#tbodyCompleteTranxDetails').empty();
	
	
	
});
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

function updateCheckBox(opts) {
		 /*var chks = document.getElementsByName("eatmisdataId");
		if(opts.value != '')
		for (var i = 0; i <= chks.length - 1; i++) {
		chks[i].disabled = false;
		}
		else 
            for (var i = 0; i <= chks.length - 1; i++) {
                chks[i].disabled = true;
                chks[i].checked = false;
                
            }*/
var chks = document.getElementsByName("eatmisdataId");

$rowId = opts.id.match(/[\d\.]+/g);
if(opts.value != ''){

	$('#eatmisdataId'+$rowId).attr("disabled", false);
}
else{
	$('#eatmisdataId'+$rowId).attr("disabled", true);
}
}


$('#getTranxDetail').on('click',function(){
	
	$workIdDetails = "";
	$workIdDetailextra = "";
	$projId = $('#project').val();
	$yearId = $('#year').val();
	
	if($projId===''){
		$('.projectError').html('Please select Project.');
		$('.projectError').css('color','red');
		$('.project').focus();
		
		return false;
	}else{
		$('.projectError').html('');
	}
	
	if($yearId===''){
		$('.yearError').html('Please select Financial Year.');
		$('.yearError').css('color','red');
		$('.year').focus();
		
		return false;
	}else{
		$('.yearError').html('');
	}
	
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	$.ajax({  
            url:"getWorkIdDetail",
            type: "post", 
           data:{projId:$projId}, 

            error:function(xhr,status,er){
                console.log(er);
            },
             
		     success:function(data1) {
				
				for ( var key1 in data1) {
						
							$workIdDetails+='<option value="'+key1+'">'+data1[key1]+'</option>';
							
						}	
					
		
	$('#loading').show();
	$.ajax({  
            url:"getPfmsWorkIdTransaction",
            type: "post",  
            data: {projId:$projId, yearId:$yearId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            
            success: function(data) {
				$('#loading').hide();
				$index = 0;
				$tbodyTranxDetails = $('#tbodyTranxDetails');
				$tbodyTranxDetails.empty();
				$tbodyCompleteTranxDetails = $('#tbodyCompleteTranxDetails');
				$tbodyCompleteTranxDetails.empty();
				var O = true;
				var D = true;
				if(Object.keys(data).length>0){
					for ( var key in data) {
						
						/*$workIdDetails='<option value="">---Select---</option>';*/
					$eatmisdataId = data[key].eatmisdataId
							//alert($eatmisdataId);
							
					
				
					if(data[key].wistatus==='O'){
		$index += 1
								O = false;
								$tbodyTranxDetails.append('<tr id="'+$index+'"><td><input type="checkbox" id="eatmisdataId'+data[key].eatmisdataId+'" name="eatmisdataId" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
								+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
								+'<td>'+data[key].invoiceNo+'</td>'+'<td><input type="hidden" id="amount'+$index+'" value='+data[key].totalAmount+'></input>'+data[key].totalAmount+'</td>'+'<td>'+data[key].projectName+'</td>'+
								'<td><select class="selectpicker" title="Select Workid" name = "selValue"  id="workid'+data[key].eatmisdataId+'" multiple data-live-search="true">'+$workIdDetails+'</select></td><td><div class="textboxes"></div></td></tr>');
							
			                    
								$('#saveAsDraft').removeClass('d-none');
							}
							
							if(data[key].wistatus==='D'){
						
								D = false;
								$tbodyCompleteTranxDetails.append('<tr id="'+$index+'"><td><input type="checkbox" id="eatmisdataId1" name="eatmisdataId1" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
								+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
								+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
								+'<td>'+data[key].projectName+'</td>'+'<td style="text-align:center;"><a class="finyr" data-id="'+data[key].eatmisdataId+'" href="#">view</a></td>'+'<td><input type = "button" id ="delete" name = "delete" value = "Delete" onclick = "javascript:deleteTranx('+data[key].eatmisdataId+')"/></td></tr>');
								$('#complete').removeClass('d-none');
							
							}
					}
				
				var OS = Boolean(O);
					var DS = Boolean(D);
					if(OS){
						$tbodyTranxDetails.append('<tr><td colspan="9" class="text-center">Data not found !</td></tr>');
					}if(DS){
						$tbodyCompleteTranxDetails.append('<tr><td colspan="9" class="text-center">Data not found !</td></tr>');
					}
				}
				
				else{
					$tbodyTranxDetails.append('<tr><td colspan="9" class="text-center">Data not found !</td></tr>');
					$tbodyCompleteTranxDetails.append('<tr><td colspan="9" class="text-center">Data not found !</td></tr>');
				}
			
			
			
			$('select[name=selValue]').selectpicker();
			                    getexpenditure();
			
			
			}
			
		});
	
	},
					async:false
	
					});
	
});

function getexpenditure(){
	$(".selectpicker").on("change", function() {
  var sr_no = $(this).closest("tr").find("td:eq(0)").text(); 
  var sr_id = $(this).closest("tr").attr("id");//get row no
   var selector = $(this).closest("tr");
  selector.find(".counts:not(:visible) input").val("") //empty value
  selector.find(".counts").hide() //hide counts
  //loop through selctd options
  $(this).find("option:selected").each(function() {
    var index = $(this).index(); //get indx of options 0,1..etc
    selector.find("input[data-id=" + index + "]").closest("div[data-srno=" + sr_no + "]").show() //show that divs 
  });
})

		$("tr td select.selectpicker").each(function() {
		
  var sr_no = $(this).closest("tr").find("td:eq(0)").text();
  var sr_id = $(this).closest("tr").attr("id");
  var selector = $(this).closest("tr")
  $(this).find("option").each(function(i) {
     var  expid = $(this).text().split("#");  
     var expid1 = expid[0].split(" ").join("");
     selector.find(".textboxes").append("<div class='counts' data-srno=" + sr_no + "><label>" + $(this).text() + " </label><input type=text id='product"+sr_id+expid1+"' name='productcounts"+expid[0]+"'  data-id=" + i + " required onmousedown='decimalCheck(event)' placeholder='Add Expenditure ..' ></div>")

  })

})

}

$('#saveAsDraft').on('click',function(e){
	var eatmisdataId = [];
	 $activity=new Array();
	var workid = new Array();
	//var count = new Array();
	var index = new Array();
	$product_counts = new Array();
	var wistatus = 'D';
	$flag=false;
	var total_qty = 0;
	var work = new Array();
	$totalwork = new Array();
	var column = 0;
	var exp = 0;
	var expvalue = 0;
	var count = 0;
	var amountcount = 0;
	$flag=true;
	/*for(var k=1;k<=row;k++){
		$activity.push($('#product_counts'+k).val());
		}*/
		
		
   var transaction =$("input[name='eatmisdataId']:checked").val();
  
  $("input[name='eatmisdataId']:checked").each(function(){
            eatmisdataId.push(this.value);
          /*workid.push(this.value);*/
         workid.push($('#workid'+this.value).val().join("#"));
        var trid = $(this).closest('tr').attr('id');
       // alert(trid); 
    if($('#workid'+this.value).val()==''){
			 $flag = false;
			 $id = $('#workid'+this.value);
		 }
      column += 1;
      work = $('#workid'+this.value).val();
	  $totalwork.push(work.length);
	  exp = 0;
	  
	  for(var l=0;l<work.length; l++)
       {
		     amountcount = 0;
		     if($('#product'+trid+work[l]).val()===''){
			    amountcount = 1;
			   $idE = $('#product'+trid+work[l]);
			   }
		   
		   if(exp ==0){
			   exp = $('#product'+trid+work[l]).val() ;
			   
		   }else{
		   
		   exp = exp+'#'+$('#product'+trid+work[l]).val() ;
		   
		   }
		   total_qty=0;
		   total_qty =  $('#amount'+trid).val()
		   expvalue = parseFloat(expvalue)+parseFloat($('#product'+trid+work[l]).val()) ;
		}
		
		$activity.push(exp);
		if(total_qty > expvalue)
		{
			count = 1;
			
		}
 
  });
   if(!$flag){
	   alert('Please Select/Fill the all Selection/Input Before Save.');
	   $id.focus();
	   $flag = true;
	   return false;
   }
   if(count > 0)
   {
	        alert('Expenditure value cannot be less than Total Amount');
			$flag=false;
	        return false;
   }
  //alert($activity);
  if(transaction=='' || transaction==undefined)
{
	alert('Please Select atleast One Transaction id')
	$flag=false;
	return false;
}
  if(workid=='')
{
	alert('Please Select atleast One Work id')
	$flag=false;
	return false;
}

  if(amountcount > 0)
  {
	  alert('please fill blank Expenditure Amount.')
	  idE.focus();
	  $flag=false;
	return false;
  }
  



else{
	$.ajax({  
            url:"saveasdraftpfmsworkid",
            type: "post",  
            data: {workid:workid.toString(),eatmisdataId:eatmisdataId.toString(), expenditure:$activity.toString(), totalworks: $totalwork.toString()},
            error:function(xhr,status,er){
                console.log(er);
            },
	success: function(data) {
		$('#loading').hide();
		if(data==='success'){
			alert('Transactions Saved As Draft Successfully');
			window.location.href='pfmsMappingWithWorkId';
			}
		else{
			alert('There is some Problem while Saving Data..');
			window.location.href='pfmsMappingWithWorkId';
		}
		}
	});
	
	}
	
	});
	
function deleteTranx(id){
	$srchMaster = $('#srchMaster').val();
	$eatmisdataId = id;
	$('#saveAsDraft').addClass('d-none');
	$('#complete').addClass('d-none');
	$.ajax({  
            url:"deletePfmsworkidTransaction",
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
					window.location.href='pfmsMappingWithWorkId';
				}else{
					alert('There Was an issue to Delete Transaction.');
					window.location.href='pfmsMappingWithWorkId';
				}
				}
				});
				
				}	
				
$('#complete').on('click',function(){
	$projId = $('#project').val();
	$srchMaster = $('#srchMaster').val();
	var eatmisdataId = [];
	$("input[name='eatmisdataId1']:checked").each(function(){
            eatmisdataId.push(this.value);
        });
if(eatmisdataId=='')
{
	alert('please select atleast one transaction id')
	$flag=false;
	return false;
}
else{
		confirmAlert("Do You want to Complete Selected Transaction");
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
		$.ajax({  
            url:"completePfmsWorkTransaction",
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
					window.location.href='pfmsMappingWithWorkId';
				}else{
					alert('There was an issue to Complete transactiond');
					window.location.href='pfmsMappingWithWorkId';
				}
				}
				});
				});
				}
				});		
				
$('#tbodyCompleteTranxDetails').on( 'click', function (e) {
	var del = e.target.getAttribute('data-id');
	$.ajax({  
            url:"getWorkIdExpDetail",
            type: "post",  
            data: {id:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(PfmsTransactionBean) {
            console.log(PfmsTransactionBean);
            var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('List of Work Id, Expenditure');
						var i=1;
						if(Object.keys(PfmsTransactionBean).length>0){
							for ( var t in PfmsTransactionBean) {
		if (PfmsTransactionBean.hasOwnProperty(t)) {
			
			tblData+="<tr><td>"+i+"</td><td style='width:40%'>"+PfmsTransactionBean[t].wicode+"</td><td style='width:40%'>"+PfmsTransactionBean[t].expenditure+"</td></tr>";
			i++;
			}
			}
						}
							
							else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
           $('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S No.</th><th style="width:40%">Work Id</th><th style="width:40%">Expenditure Amount</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
            
            });
	});	
	
/************************************************On Click Get Completed Transactions Details ********************************************/
$('#getComTranxDetail').on('click',function(){
	$state = $('#state').val();
	$district = $('#district').val();
	$projId = $('#project').val();
	$finYrCd = $('#year').val();
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
	
	if($finYrCd===''){
		$('.yearError').html('Please select Financial Year.');
		$('.yearError').css('color','red');
		$('.year').focus();
		return false;
	}else{
		$('.yearError').html('');
	}
	
	
	$('#completed').addClass('d-none');
		$.ajax({  
            url:"getCompPfmsTranMapWithWorkId",
            type: "post",  
            data: {projId:$projId, yearId:$finYrCd},
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
							if(data[key].wistatus==='C' && data[key].projectId ==$projId){
								D = false;
								$tbodyCompletedTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataIdc" name="eatmisdataIdc" value="'+data[key].eatmisdataId+'"/>'+data[key].tranxId+'</td>'
								+'<td>'+data[key].date+'</td>'+'<td>'+data[key].componentCode+'</td>'+'<td>'+data[key].componentName+'</td>'
								+'<td>'+data[key].invoiceNo+'</td>'+'<td>'+data[key].totalAmount+'</td>'
								+'<td>'+$projName+'</td>'+'<td>'+data[key].eatmisdataId+'</td></tr>');
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
	$finYrCd = $('#year').val();
	$projName = $('#project').find(":selected").text();
	$('#completed').addClass('d-none');
	var eatmisdataId = [];
	$("input[name='eatmisdataIdc']:checked").each(function(){
//			alert('check '+this.value);
            eatmisdataId.push(this.value);
        });
		$.ajax({  
            url:"UpdateAsDraftPfmsTransaction",
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
					url: "getCompPfmsTranMapWithWorkId",
					type: "post",
					data: { projId: $projId, yearId: $finYrCd },
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
									if (data[key].wistatus === 'C' && data[key].projectId == $projId) {
										D = false;
										$tbodyCompletedTranxDetails.append('<tr><td><input type="checkbox" id="eatmisdataIdc" name="eatmisdataIdc" value="' + data[key].eatmisdataId + '"/>' + data[key].tranxId + '</td>'
											+ '<td>' + data[key].date + '</td>' + '<td>' + data[key].componentCode + '</td>' + '<td>' + data[key].componentName + '</td>'
											+ '<td>' + data[key].invoiceNo + '</td>' + '<td>' + data[key].totalAmount + '</td>'
											+ '<td>' + $projName + '</td>' + '<td>' + data[key].eatmisdataId + '</td></tr>');
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
			