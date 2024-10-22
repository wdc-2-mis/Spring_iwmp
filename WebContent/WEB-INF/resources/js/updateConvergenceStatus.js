function selectAll(ele) {
    var checkboxes = document.getElementsByName('workcode');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

$(document).on('click','.workcode',function(){
	 const cbAll = document.querySelector('#checkAllTranxId');
	if(cbAll.checked ){
		$('#checkAllTranxId').prop('checked', false);
	}
});

$(document).on('click','input:radio',function(e) { 
	var i =$(this).attr('data-id');
 if($(this).hasClass('yes'+i)) {
	 document.getElementById("full"+i).checked = true;
  }else if($(this).hasClass('no'+i)) {
	document.getElementById("no"+i).checked = true;
  }

});

/***********************************************On select Head Code**********************************************/
$('#head').on('change',function(){
	$projid = $('#project').val();
	if($projid ==''){
		$('.projectError').html('Please select Project*.');
		$('.projectError').css("color","red");
		$('#project').focus();
	}else{
		$('.projectError').html('');
	}
	$head = $('#head').val();
	$.ajax({
		url: "getActivity",
		type: "post",
		data : {headId:$head},
		error:function(xhr,status,er){
			console.log(er);
			$('.error').append(' There is some error please try again !');
		},
		success:function(data){
			console.log(data);
			var activity = $('#activity');
			activity.empty();
			activity.append("<option value='0'>--All--</option>");
			if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							activity.append("<option value=" + key + ">" + data[key] + "</option>");
						}
					}
			}
		}
		
	});
	
});

/***********************************************On Click Get Button**********************************************/
$('#getConvergenceDetail').on('click',function(){
	
	$schmIdDetails = "";
	$projId = $('#project').val();
	$head = $('#head').val();
	$activity = $('#activity').val();
	var i = 1;
	if($projId===''){
		$('.projectError').html('Please select Project.');
		$('.projectError').css('color','red');
		$('.project').focus();
		return false;
	}else{
		$('.projectError').html('');
	}
	
	$('#update').addClass('d-none');
	$.ajax({  
            url:"getConvergenceStatus",
            type: "post",  
            data: {projId:$projId,head:$head,activity:$activity},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$tbodyConvergence = $('#tbodyConvergence');
				$tbodyConvergence.empty();
				
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if(data[key].cnvrgncestatus=='Y'){
								$tbodyConvergence.append('<tr><td><input type="checkbox" id="workcode'+data[key].statusid+'" name="workcode" class ="workcode" value="'+data[key].statusid+'"/></td>'+'<td>'+data[key].workcode+'</td>'
								+'<td>'+data[key].blockname+'</td>'+'<td>'+data[key].gpname+'</td>'+'<td>'+data[key].villagename+'</td>'
								+'<td style="white-space: nowrap;"><input type="radio" class="yes'+i+'" name = "status'+data[key].statusid+'" value="Y" data-id="'+i+'" id="yes'+i+'" checked = "checked">Yes<input type="radio" class="no'+i+'" name = "status'+data[key].statusid+'" value="N" data-id="'+i+'" id="no'+i+'">NO</td></tr>');
								i++;
						}else{
							$tbodyConvergence.append('<tr><td><input type="checkbox" id="workcode'+data[key].statusid+'" name="workcode" class ="workcode" value="'+data[key].statusid+'"/></td>'+'<td>'+data[key].workcode+'</td>'
								+'<td>'+data[key].blockname+'</td>'+'<td>'+data[key].gpname+'</td>'+'<td>'+data[key].villagename+'</td>'
								+'<td style="white-space: nowrap;"><input type="radio" class="yes'+i+'" name = "status'+data[key].statusid+'" value="Y" data-id="'+i+'" id="yes'+i+'" >Yes<input type="radio" class="no'+i+'" name = "status'+data[key].statusid+'" value="N" data-id="'+i+'" id="no'+i+'" checked = "checked">NO</td></tr>');
								i++;
						}
								
								$('#update').removeClass('d-none');
					}
				}
				else{
					$tbodyConvergence.append('<tr><td colspan="16" class="text-center">Data not found !</td></tr>');
				}
			}
		});
});
/***************************************************************on Click Update Button***********************************************************/
$('#update').on('click',function(){
	var workcode = [];
	var cnvrgstatus = new Array();
	$projId = $('#project').val();
	$flag=true;
	$id = '';
	
	$("input[name='workcode']:checked").each(function(){
            workcode.push(this.value);
          /*workid.push(this.value);*/
         var cstatus = $('input[name="status'+this.value+'"]:checked').val();
         cnvrgstatus.push(cstatus);
   });
   
//   if(!$flag){
//	   alert('Please Select/Fill the all Selection/Input Before Save.');
//	   $flag = true;
//	   return false;
//   }
   
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
else{
	if(confirm('Do You want to Save the Convergence Details')){
		
	
	$.ajax({  
            url:"updateConvergenceStatus",
            type: "post",  
            data: {workcode:workcode.toString(),cnvrgstatus:cnvrgstatus.toString()},
            error:function(xhr,status,er){
                console.log(er);
            },
	success: function(data) {
		$('#loading').hide();
		if(data==='success'){
			alert('Convergence Status of Selected WorkIds Updated.');
			window.location.href='getConvergenceStatus';
			}
		else{
			alert('There is some Problem while Updating Data..');
			window.location.href='getConvergenceStatus';
		}
		
		}
	});
	}
	
}
});