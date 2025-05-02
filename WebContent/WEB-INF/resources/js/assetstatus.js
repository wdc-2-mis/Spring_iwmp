
var id = 0;
var rId = 0;
var rid = [];
var k = 0;
var j = 0;
var cid =[];
var today = new Date();
var day = (today.getDate()<10)?"0"+today.getDate():today.getDate();
var month = ((today.getMonth()+1)<10)?"0"+(today.getMonth()+1):(today.getMonth()+1);
var date = day+'-'+month+'-'+today.getFullYear(); 
 $(document).on('focus', '.datepicker', function(e){ 
	$(this).datepicker({
    	 changeMonth: true,
         changeYear: true, 
         maxDate: date, 
         dateFormat: "dd-mm-yy",
         yearRange: "2000:2041",
         onClose: function(){
	var dt=new Date($.datepicker.parseDate('dd-mm-yy', $(this).val()));
	var todaydt=new Date($.datepicker.parseDate('dd-mm-yy',date));
	
	
	//if($(this).val()>date)
	if (dt>todaydt){
		successAlert('Please consider date before or equal to the current date!');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				$(this).focus();
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				$(this).focus();
				});
				$(this).datepicker('setDate', null);
//				return false;
		}
    		     },
        });

});
  
 
 	var yrdt1 = new Date();
	var onGoingDate = new Date();
	var crntDate = new Date($.datepicker.parseDate('dd-mm-yy', "10-01-"+today.getFullYear()));
 $(document).on('click', '#complete', function(e){ 
	
	var yrdt = '';
	let mnth = 12;
	var confirmAssignee = false;
	let cnt = 0;

	$.ajax({
		url: "getAllFinYearDetails",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				if (data.hasOwnProperty(key)) {
					if (key == 12 && cnt ==0) {
						confirmAssignee = true;
						yrdt = 20 + '' + data[key];
					} else {
						confirmAssignee = false;
						cnt = 1;
						if (Number(key) < Number(mnth) || (Number(key) >3 && Number(mnth) <4)) {
							mnth = key;
							yrdt = 20 + '' + data[key];
						}
					}
				}
			}
//			alert('curntdate '+mnth +' '+yrdt);
			if (confirmAssignee) {
				yrdt1 = 01 + "-" + 12 + "-" + yrdt;
			} else {	
				if (mnth < 4 && yrdt < today.getFullYear()) {
					yrdt = parseInt(yrdt) + 1;
				}else if(mnth < 4){
					mnth = 4;
				}
				var m = mnth.length == 1 ? 0 + '' + mnth : mnth;
				//					alert('check1 '+mnth.length);
				yrdt1 = 01 + "-" + m + "-" + yrdt;
			}
			yrdt1 = new Date($.datepicker.parseDate('dd-mm-yy', yrdt1));
			onGoingDate = new Date($.datepicker.parseDate('dd-mm-yy', $("#sdatepicker" + id).val()));
			//					alert('cjeck4 '+onGoingDate);
			if (onGoingDate > yrdt1) {
				yrdt1 = onGoingDate;
			}
//			alert('check03 '+yrdt1);
			$('#cdatepicker'+id).datepicker({
						changeMonth: true,
						changeYear: true,
						minDate: yrdt1,
						maxDate: date,
						dateFormat: "dd-mm-yy",
						yearRange: "2000:2041",
						onClose: function() {
							var dt = new Date($.datepicker.parseDate('dd-mm-yy', $(this).val()));
//							alert('cjeck1 '+dt);
							if (id != 0) {
								onGoingDate = new Date($.datepicker.parseDate('dd-mm-yy', $("#sdatepicker" + id).val()));
								if (onGoingDate > dt) {
									successAlert('Please consider Complete Date greater than the Start Date!');
									$("#successok").click(function() {
										$('#popup').modal('hide');
										$(this).focus();
									});
									$(".close").click(function() {
										$('#popup').modal('hide');
										$(this).focus();
									});
									$(this).datepicker('setDate', null);
								}
							}
						},

					});




		}


	});
});



$('#project').on('change', function(e) {
		e.preventDefault();
		$tbodyassetWiseStatusId = $('#tbodyassetWiseStatusId');
		$tbodyassetWiseStatusId.empty();
		$pCode=$('#project option:selected').val();
		$.ajax({  
            url:"getYearForAssetWise",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						
						$ddlYear = $('#year');
						$ddlYear.empty();
        				$ddlYear.append('<option value=""> --Select Financial Year-- </option>');
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



$('#cproject').on('change', function(e) {
		e.preventDefault();
		$tbodyassetWiseCStatusId = $('#tbodyassetWiseCStatusId');
		$tbodyassetWiseCStatusId.empty();
		$pCode=$('#cproject option:selected').val();
		$.ajax({  
            url:"getYearForAssetWise",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						
						$ddlYear = $('#cyear');
						$ddlYear.empty();
        				$ddlYear.append('<option value=""> --Select Financial Year-- </option>');
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

$('#fproject').on('change', function(e) {
		e.preventDefault();
		$tbodyassetWiseFStatusId = $('#tbodyassetWiseFStatusId');
		$tbodyassetWiseFStatusId.empty();
		$pCode=$('#fproject option:selected').val();
		$.ajax({  
            url:"getYearForAssetWise",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						
						$ddlYear = $('#foryear');
						$ddlYear.empty();
        				$ddlYear.append('<option value=""> --Select Financial Year-- </option>');
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

$(document).on('click','input:radio',function(e) { 
	var i =$(this).attr('data-id');

	 if($(this).hasClass('complete'+i)) {
		  id = i;
		  cid[j]= id;
	      $("#cdatepicker"+i).prop("disabled",false);
	      j++;
  }else{ 
	$("#cdatepicker"+i).prop("disabled",true).val("");
}

 if($(this).hasClass('forClose'+i)) { 
	rId = i;
	rid[k] = rId;
     $("#reason"+i).prop("disabled",false);
     k++;
  }else{ 
	$("#reason"+i).prop("disabled",true).val("");
}

});


$(document).on('click','input:checkbox',function(e) {
var i =$(this).attr('data-id');
var status =  this.checked;

if (status) {
		 $("#sdatepicker"+i).prop("disabled",false);
		 $("#convergence"+i).prop("disabled",false);
		 
	     
  }else{ 
  	$("#sdatepicker"+i).prop("disabled",true).val("");
	$("#convergence"+i).prop("disabled",true);
}

});



$( "#assetwisestatus" ).submit(function( e ){
	e.preventDefault();
	
	var finalAssetid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetid.push($(this).val());	
		}
      });
     
 //   alert(finalAssetid); 
	var form= $("#assetwisestatus").serialize();
	//alert(finalAssetid);
//	alert('Do you want to Save these Work-Id Status');
	
		$('#loading').show();
$.ajax({  
            url:"saveassetstatus",
            type: "post",  
            data: $("#assetwisestatus").serialize()+"&finalAssetid="+finalAssetid,
            error:function(xhr,status,er){
                console.log(er);
             },
             
            success: function(data) {
				 console.log(data);
				 $('#loading').hide();
	if(data==='true'){
		alert('Data Saved Successfully!');
		window.location.href='assetwisestatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwisestatus';
			
		}
	
}
});

//});
});

$( document ).ready(function(){
	$('#viewcomplete').on('click', function(e) {
		e.preventDefault();
	
	$(".perror").hide();
	$(".uperror").hide();
	
		var i = 1;
		$pCode=$('#cproject option:selected').val();
		$fCode=$('#cyear option:selected').val();
		
		
		if($('#cproject option:selected').val()==""){
			successAlert('Please select Project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#cproject').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#cproject').focus();
			});
			return false;
		}
		if($('#cyear option:selected').val()==""){
			successAlert('Please select Financial Year');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#cyear').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#cyear').focus();
			});
			return false;
		}
$('#loading').show();
	$.ajax({  
            url:"getassetwisecompletedata",
            type: "post", 
			data:{pCode:$pCode, fCode:$fCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(AssetIdBean) {
            $('#loading').hide();
	        console.log(AssetIdBean); 
		var tblData="";
		
					$tbodyassetWiseCStatusId = $('#tbodyassetWiseCStatusId');
						$tbodyassetWiseCStatusId.empty();
		if(Object.keys(AssetIdBean).length>0){
						 for ( var key in AssetIdBean) {
						   
			tblData+="<tr><td>"+i+"</td><td>"+AssetIdBean[key].asseteid+"</td><td>"+AssetIdBean[key].headdesc+"</td><td>"+AssetIdBean[key].activitydesc+"</td><td>"+AssetIdBean[key].bname+"</td><td>"+AssetIdBean[key].vname+"</td><td>"+AssetIdBean[key].sdate+"</td><td>"+AssetIdBean[key].cdate+"</td><td>"+AssetIdBean[key].convergence+"</td></tr>";
		    i++
						}	
	}
  else{
 	tblData="<tr><td colspan='9' class='text-center'>Data not found !</td></tr>";
	}
	$tbodyassetWiseCStatusId.append(tblData);
            }
	});	
	});
	});

$( document ).ready(function(){
	$('#viewforclosed').on('click', function(e) {
		e.preventDefault();
	
	$(".perror").hide();
	$(".uperror").hide();
	
		var i = 1;
		$pCode=$('#fproject option:selected').val();
		$fCode=$('#foryear option:selected').val();
		
		//alert($fCode);
		
		if($('#fproject option:selected').val()==""){
			successAlert('Please select Project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#fproject').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#fproject').focus();
			});
			return false;
		}
		if($('#foryear option:selected').val()==""){
			successAlert('Please select Financial Year');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#foryear').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#foryear').focus();
			});
			return false;
		}
$('#loading').show();
	$.ajax({  
            url:"getassetwiseforcloaseddata",
            type: "post", 
			data:{pCode:$pCode, fCode:$fCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(AssetIdBean) {
            $('#loading').hide();
	        console.log(AssetIdBean); 
		var tblData="";
		
					$tbodyassetWiseFStatusId = $('#tbodyassetWiseFStatusId');
						$tbodyassetWiseFStatusId.empty();
		if(Object.keys(AssetIdBean).length>0){
						 for ( var key in AssetIdBean) {
						   
			tblData+="<tr><td>"+i+"</td><td>"+AssetIdBean[key].asseteid+"</td><td>"+AssetIdBean[key].headdesc+"</td><td>"+AssetIdBean[key].activitydesc+"</td><td>"+AssetIdBean[key].bname+"</td><td>"+AssetIdBean[key].vname+"</td><td>"+AssetIdBean[key].sdate+"</td><td>"+AssetIdBean[key].reason+"</td><td>"+AssetIdBean[key].convergence+"</td></tr>";
		    i++
						}	
	}
  else{
 	tblData="<tr><td colspan='9' class='text-center'>Data not found !</td></tr>";
	}
	$tbodyassetWiseFStatusId.append(tblData);
            }
	});	
	});
	});


function updatedata(e){
	
	if(id!=0){
	for(var i=0;i<cid.length;i++){
		if($("#cdatepicker"+cid[i]).val()==null ||$("#cdatepicker"+cid[i]).val()==''||$("#cdatepicker"+cid[i]).val()=='undefind'){
		successAlert('Please choose completion date!');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#cdatepicker'+cid[i]).focus();
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				$('#cdatepicker'+cid[i]).focus();
				});
				return false;
		}
	
	}
	}
	if(rId!=0){
		for(var i = 0;i<rid.length;i++){
			
			if($("#reason"+rid[i]).val()==null ||$("#reason"+rid[i]).val()==''||$("#reason"+rid[i]).val()=='undefind'){
				successAlert('Please fill the reason box!');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				$('#reason'+rid[i]).focus();
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				$('#reason'+rid[i]).focus();
				});
				return false;
		}
		}
	}
	
	e.preventDefault();
	var form= $("#assetwisestatus").serialize();
	alert('Do you want to Update Work-Id Status');
			/*$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');*/
	$('#loading').show();
	$.ajax({  
            url:"updateassetstatus",
            type: "post",  
            data: form,
            error:function(xhr,status,er){
                console.log(er);
             },
success: function(data){
	$('#loading').hide();
	
	if(data==='true'){
		alert('Data Updated Successfully!');
		window.location.href='assetwisestatus';
		}
		if(data==='fail'){
			alert('Issue on Updated Data !');
			window.location.href='assetwisestatus';
			
		}
}
	
	});
//});
}





$( document ).ready(function(){
	$('#view').on('click', function(e) {
		e.preventDefault();
	$(".perror").hide();
	$(".uperror").hide();
	
		var i = 1;
		$pCode=$('#project option:selected').val();
		$fYear=$('#year option:selected').val();
		
		if($('#project option:selected').val()==""){
			successAlert('Please select Project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#project').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#project').focus();
			});
			return false;
		}
		if($('#year option:selected').val()==""){
			successAlert('Please select Financial Year');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#year').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#year').focus();
			});
			return false;
		}
		$('#loading').show();
		$.ajax({  
            url:"getassetwisestatusdata",
            type: "post", 
			data:{pCode:$pCode, fYear:$fYear}, 
            error:function(xhr,status,er){
                console.log(er);
            },
success:function(data) {
$('#loading').hide();
	console.log(data);
				var tblData="";
					$tbodyassetWiseStatusId = $('#tbodyassetWiseStatusId');
						$tbodyassetWiseStatusId.empty();
						if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
							var x = false;
								for ( var k in d) {
				if(d[k].sdate!=null)
							{
		if (d.hasOwnProperty(k)) 
		if(d[k].convergence==='N')
		    {
			$nval='selected';
			$yval='';
			}
			else 
			if(d[k].convergence==='Y')
			{
			$yval='selected';
			$nval='';
			}
		if(d[k].status=='O')
		{
			
			if(parseInt(k)===0){
				
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].headdesc+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='sdatepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' readonly='readonly'  autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='cdatepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td><td><select class = 'disable' style='pointer-events: none;' name ='convergence"+d[k].assetid+"' id = 'convergence' "+i+" required='required'><option value=''>--select--</option><option value='Y' "+$yval+" >Yes</option><option value='N' "+$nval+" >No</option></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].headdesc+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='sdatepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' readonly='readonly'   autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='cdatepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td><td><select class = 'disable' style='pointer-events: none;' name ='convergence"+d[k].assetid+"' id = 'convergence' "+i+" required='required'><option value=''>--select--</option><option value='Y' "+$yval+">Yes</option><option value='N' "+$nval+">No</option></td></tr>";
		    i++
		
		}	
		
			}
			
			else
			{
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].headdesc+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker'  size='10' id='sdatepicker"+i+"'  data-id='"+i+"' name='sdate"+d[k].assetid+"' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='cdatepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' disabled='disabled' required autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required autocomplete = 'off'></textarea> </td><td><select name ='convergence"+d[k].assetid+"' id = 'convergence"+i+"' disabled='true' required><option value=''>--select--</option><option value='Y'>Yes</option><option value='N'>No</option></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].headdesc+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker' size='10' id='sdatepicker"+i+"' data-id='"+i+"'  name='sdate"+d[k].assetid+"' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='cdatepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' disabled='disabled' required autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required autocomplete = 'off'></textarea> </td><td><select name ='convergence"+d[k].assetid+"' id = 'convergence"+i+"' disabled='true' required><option value=''>--select--</option><option value='Y'>Yes</option><option value='N'>No</option></td></tr>";
		    i++
		
		
		}
							if( d[k].sdate===undefined || d[k].sdate==='')
		{

			x = true;
}
							}
							

		
		
		
	var op = Boolean(x);
			//alert(op);
			if(op){

				
				tblData+="<tr><td colspan='10'><center><input type='submit' id='submit' value='Save/Update' /><center></td></tr>";

			}else{
				//alert("hi");

				
				tblData+="<tr><td colspan='10'><center><button id='submit1' value='Save/Update' onclick='updatedata(event);'/>Save/Update<center></td></tr>";

			}
			
							}
							
							}	
					
	}
          
 else{
 
		tblData="<tr><td colspan='11' class='text-center'>Data not found !</td></tr>";

	}
	$tbodyassetWiseStatusId.append(tblData);
            }
	});
	});
  
	});
	