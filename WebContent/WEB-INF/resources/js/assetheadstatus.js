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

$( function() {
    $( "#datepicker" ).datepicker();
  } );


 $(document).on('focus', '.datepicker', function(e){ 

	$(this).datepicker({
    	 changeMonth: true,
         changeYear: true,
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
		if(id!=0){
			var onGoingDate = new Date($.datepicker.parseDate('dd-mm-yy',$("#datepicker"+id).val()))
			if(onGoingDate>dt){
				successAlert('Please consider Complete Date greater than the Start Date!');
				$("#successok").click(function(){
				$('#popup').modal('hide');
				$(this).focus();
				});  
				$(".close").click(function(){
				$('#popup').modal('hide');
				$(this).focus();
				});
				$(this).datepicker('setDate', null);
			}
		}
    		     },
        });

});

$('#head').on("change" ,function () { 
			$('#loading').show();
		$headVal = $('#head option:selected').val();
		$.ajax({  
            url:"getHeadActivity",
            type: "post",  
            data: {headId:$headVal},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				var $headactivity = $('#headactivity');
				var $tbodyassetWiseHeadStatusId = $('#tbodyassetWiseHeadStatusId');
				
				$headactivity.empty();
				$tbodyassetWiseHeadStatusId.empty();
        		$headactivity.append('<option value="0">--All--</option>');
        		
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						 $headactivity.append('<option value='+key+'>' +data[key] + '</option>');
					}
				}
			}
		});
	});	

$('#chead').on("change" ,function () { 
			$('#loading').show();
		$headVal = $('#chead option:selected').val();
		$.ajax({  
            url:"getHeadActivity",
            type: "post",  
            data: {headId:$headVal},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				var $cheadactivity = $('#cheadactivity');
				$cheadactivity.empty();
				var $tbodyassetWiseCHeadStatusId = $('#tbodyassetWiseCHeadStatusId');
				$tbodyassetWiseCHeadStatusId.empty();
        		
        		$cheadactivity.append('<option value="0">--All--</option>');
        		
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						 $cheadactivity.append('<option value='+key+'>' +data[key] + '</option>');
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
		  
  }else{ 
  	$("#sdatepicker"+i).prop("disabled",true).val("");
}

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

function savelivelihooddata(e){
	e.preventDefault();
	
	var finalAssetLiveid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetLiveid.push($(this).val());	
		}
      });
     
	var form= $("#assetwiseLivestatus").serialize();
	//alert(finalAssetid);
	
$.ajax({  
            url:"saveassetlivelihoodstatus",
            type: "post",  
            data: $("#assetwiselivestatus").serialize()+"&finalAssetid="+finalAssetLiveid,
            error:function(xhr,status,er){
                console.log(er);
             },
            success: function(data) {
	if(data==='true'){
		alert('Livelihood Status Saved Successfully!');
		window.location.href='assetwiseheadstatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwiseheadstatus';
			
		}
	
}
});

}


function saveproductiondata(e){
	e.preventDefault();
	var finalAssetProdid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetProdid.push($(this).val());	
		}
      });
     
	var form= $("#assetwiseProdstatus").serialize();
	//alert(finalAssetid);
		
$.ajax({  
            url:"saveassetproductionstatus",
            type: "post",  
            data: $("#assetwiselivestatus").serialize()+"&finalAssetid="+finalAssetProdid,
            error:function(xhr,status,er){
                console.log(er);
             },
            success: function(data) {
	if(data==='true'){
		alert('Production Status Saved Successfully!');
		window.location.href='assetwiseheadstatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwiseheadstatus';
			
		}
	
}
});

}

function saveepadata(e){
	e.preventDefault();
	var finalAssetEPAid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetEPAid.push($(this).val());	
		}
      });
     
	var form= $("#assetwiseEPAstatus").serialize();
	//alert(finalAssetid);
	
$.ajax({  
            url:"saveassetepastatus",
            type: "post",  
            data: $("#assetwiselivestatus").serialize()+"&finalAssetid="+finalAssetEPAid,
            error:function(xhr,status,er){
                console.log(er);
             },
            success: function(data) {
	if(data==='true'){
		alert('EPA Status Saved Successfully!');
		window.location.href='assetwiseheadstatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwiseheadstatus';
			
		}
	
}
});

}

$( "#assetwiselivestatus" ).submit(function( e ){
	e.preventDefault();
	$hCode=$('#head option:selected').val();
	if($hCode == 'l')
	{
		var finalAssetLiveid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetLiveid.push($(this).val());	
		}
      });
     
	var form= $("#assetwiseLivestatus").serialize();
	//alert(finalAssetid);
		
$.ajax({  
            url:"saveassetlivelihoodstatus",
            type: "post",  
            data: $("#assetwiselivestatus").serialize()+"&finalAssetid="+finalAssetLiveid,
            error:function(xhr,status,er){
                console.log(er);
             },
            success: function(data) {
	if(data==='true'){
		alert('Livelihood Status Saved Successfully!');
		window.location.href='assetwiseheadstatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwiseheadstatus';
			
		}
	
}
});

	}
	if($hCode == 'p')
	{
		var finalAssetProdid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetProdid.push($(this).val());	
		}
      });
     
	var form= $("#assetwiseProdstatus").serialize();
	//alert(finalAssetid);
		
$.ajax({  
            url:"saveassetproductionstatus",
            type: "post",  
            data: $("#assetwiselivestatus").serialize()+"&finalAssetid="+finalAssetProdid,
            error:function(xhr,status,er){
                console.log(er);
             },
            success: function(data) {
	if(data==='true'){
		alert('Production Status Saved Successfully!');
		window.location.href='assetwiseheadstatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwiseheadstatus';
			
		}
	
}
});

	}
	if($hCode == 'e')
	{
		var finalAssetEPAid=new Array();
	$('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetEPAid.push($(this).val());	
		}
      });
     
	var form= $("#assetwiseEPAstatus").serialize();
	//alert(finalAssetid);
	
$.ajax({  
            url:"saveassetepastatus",
            type: "post",  
            data: $("#assetwiselivestatus").serialize()+"&finalAssetid="+finalAssetEPAid,
            error:function(xhr,status,er){
                console.log(er);
             },
            success: function(data) {
	if(data==='true'){
		alert('EPA Status Saved Successfully!');
		window.location.href='assetwiseheadstatus';
		}
		if(data==='fail'){
			alert('Issue on Completed Data !');
			window.location.href='assetwiseheadstatus';
			
		}
	
}
});

	}
	});


$( document ).ready(function(){
	$('#view').on('click', function(e) {
		e.preventDefault();
	$(".perror").hide();
	$(".uperror").hide();
	
		var i = 1;
		$pCode=$('#project option:selected').val();
		$hCode=$('#head option:selected').val();
		$hActCode=$('#headactivity option:selected').val();
		
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
		if($('#head option:selected').val()==""){
			successAlert('Please select Head Type');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#head').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#head').focus();
			});
			return false;
		}
		if($('#headactivity option:selected').val()==""){
			successAlert('Please select Head Activity');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#headactivity').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#headactivity').focus();
			});
			return false;
		}
		if($hCode == 'l')
		{
		$('#loading').show();
		$.ajax({  
            url:"getassetwiseheadstatusdata",
            type: "post", 
			data:{pCode:$pCode, hCode:$hCode, hActCode:$hActCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
            $('#loading').hide();
	        console.log(data); 
		var tblData="";
		
					$tbodyassetWiseHeadStatusId = $('#tbodyassetWiseHeadStatusId');
						$tbodyassetWiseHeadStatusId.empty();
		if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
							var x = false;
								for ( var k in d) {
				if(d[k].sdate!=null)
							{
		if (d.hasOwnProperty(k))
		if(d[k].status=='O')
		{
			
			if(parseInt(k)===0){
				
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' onkeydown='return false' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' onkeydown='return false' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td></tr>";
		    i++
		
		}	
		 /*if(d[k].status=='C')
			{
			
			if(parseInt(k)===0){
				
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"'  readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' disabled='disabled' value='O' data-id='"+i+"' id='onGoing'>OnGoing<input type='radio'  class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete' checked='checked'>Complete<input type='radio' disabled='disabled' data-id='"+i+"' value='F' id = 'f"+d[k].assetid+"' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='readonly'  readonly='readonly' size='10' id='cdatepicker"+i+"' value = "+d[k].cdate+" name='cdate"+d[k].assetid+"'  required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' disabled='disabled' required='required' autocomplete = 'off'></textarea></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' disabled='disabled' value='O' data-id='"+i+"' id='onGoing'>OnGoing<input type='radio'  class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete' checked='checked'>Complete<input type='radio' disabled='disabled' data-id='"+i+"' value='F' id = 'f"+d[k].assetid+"' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='readonly'  readonly='readonly' size='10' id='cdatepicker"+i+"' value = "+d[k].cdate+" name='cdate"+d[k].assetid+"'  required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' disabled='disabled' required='required'  autocomplete = 'off'></textarea></td></tr>";
		    i++
		
		}	
			if(d[k].status=='F')
			{
			
			if(parseInt(k)===0){
				
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'   size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' disabled='disabled' value='O' data-id='"+i+"' id='onGoing' disabled>OnGoing<input type='radio' class='complete"+i+"' disabled='disabled' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete' disabled>Complete<input type='radio'  data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose' checked='checked'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly required='required'   autocomplete = 'off'>"+d[k].reason+"</textarea></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'   size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' disabled='disabled' value='O' data-id='"+i+"' id='onGoing' disabled>OnGoing<input type='radio' class='complete"+i+"' disabled='disabled' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete' disabled>Complete<input type='radio'  data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose' checked='checked'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly required='required' autocomplete = 'off'>"+d[k].reason+"</textarea></td></tr>";
		    i++
		
		}*/
			
		}
		else
			{
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker'  size='10' id='sdatepicker"+i+"'  data-id='"+i+"' name='sdate"+d[k].assetid+"' onkeydown='return false' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' onkeydown='return false' disabled autocomplete = 'off' required/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required='required' autocomplete = 'off'></textarea> </td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker' size='10' id='sdatepicker"+i+"' data-id='"+i+"'  name='sdate"+d[k].assetid+"' onkeydown='return false' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' onkeydown='return false' disabled autocomplete = 'off' required/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required='required' autocomplete = 'off'></textarea> </td></tr>";
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

				
				tblData+="<tr><td colspan='10'><center><input type='submit' id='submit' value='Save/Update'/><center></td></tr>";

			}else{
				//alert("hi");

				
				tblData+="<tr><td colspan='10'><center><button id='submit1' value='Save/Update' onclick='updatedata(event);'/>Save/Update<center></td></tr>";

			}
			
							}
								
							}	
				
	}
          
 else{
 
		tblData="<tr><td colspan='10' class='text-center'>Data not found !</td></tr>";
		

	}
	$tbodyassetWiseHeadStatusId.append(tblData);
            }
	
	});
	
	}
		if($hCode == 'p')
		{
		$('#loading').show();
		$.ajax({  
            url:"getassetwiseproheadstatusdata",
            type: "post", 
			data:{pCode:$pCode, hCode:$hCode, hActCode:$hActCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
            $('#loading').hide();
	        console.log(data); 
		var tblData="";
					$tbodyassetWiseHeadStatusId = $('#tbodyassetWiseHeadStatusId');
						$tbodyassetWiseHeadStatusId.empty();
		if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
							var x = false;
								for ( var k in d) {
				if(d[k].sdate!=null)
							{
		if (d.hasOwnProperty(k))
		if(d[k].status=='O')
		{
			
			if(parseInt(k)===0){
				
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' onkeydown='return false' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' onkeydown='return false' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td></tr>";
		    i++
		
		}	
			
		}
		else
			{
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker'  size='10' id='sdatepicker"+i+"'  data-id='"+i+"' name='sdate"+d[k].assetid+"' onkeydown='return false' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required autocomplete = 'off'></textarea> </td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker' size='10' id='sdatepicker"+i+"' data-id='"+i+"'  name='sdate"+d[k].assetid+"' onkeydown='return false' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required autocomplete = 'off'></textarea> </td></tr>";
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

				
				tblData+="<tr><td colspan='10'><center><input type='submit' id='submit' value='Save/Update'/><center></td></tr>";

			}else{
				
				tblData+="<tr><td colspan='10'><center><button id='submit1' value='Save/Update' onclick='updatedata(event);'/>Save/Update<center></td></tr>";

			}
			
							}
							
							}	
					
	}
          
 else{
 
		tblData="<tr><td colspan='10' class='text-center'>Data not found !</td></tr>";

	}
	$tbodyassetWiseHeadStatusId.append(tblData);
            }
	});
	
	}
			if($hCode == 'e')
		{
		$('#loading').show();
		$.ajax({  
            url:"getassetwiseepaheadstatusdata",
            type: "post", 
			data:{pCode:$pCode, hCode:$hCode, hActCode:$hActCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
            $('#loading').hide();
	        console.log(data); 
		var tblData="";
					$tbodyassetWiseHeadStatusId = $('#tbodyassetWiseHeadStatusId');
						$tbodyassetWiseHeadStatusId.empty();
		if(Object.keys(data).length>0){
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							var d=data[key];
							var x = false;
								for ( var k in d) {
				if(d[k].sdate!=null)
							{
		if (d.hasOwnProperty(k))
		if(d[k].status=='O')
		{
			
			if(parseInt(k)===0){
				
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' onkeydown='return false'readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' disabled='disabled' onkeydown='return false' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' checked  disabled='disabled' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"' onclick='checkBoxClicked($(this));'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='disable'  size='10' id='datepicker"+i+"' value = "+d[k].sdate+" name='sdate"+d[k].assetid+"' onkeydown='return false' readonly='readonly' autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='hidden' name ='statusid' value="+d[k].statusid+"><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"'  name='cdate"+d[k].assetid+"' disabled='disabled' onkeydown='return false' required='required' autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' disabled='disabled' name='reason"+d[k].assetid+"' id='reason"+i+"'  required='required' autocomplete = 'off'></textarea></td></tr>";
		    i++
		
		}	
			
		}
		else
			{
			if(parseInt(k)===0){
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+"> "+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker'  size='10' id='sdatepicker"+i+"'  data-id='"+i+"' name='sdate"+d[k].assetid+"' onkeydown='return false' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required autocomplete = 'off'></textarea> </td></tr>";
		    }else
			tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+d[k].assetid+"' data-id='"+i+"' name='chkIndividual"+d[k].assetid+"' value='"+d[k].assetid+"'/></td><td><input type='hidden' id = 'assetid' name ='assetid' value="+d[k].assetid+">"+d[k].assetid+"</td><td>"+d[k].activitydesc+"</td><td>"+d[k].bname+"</td><td>"+d[k].vname+"</td><td><input type='text' class='datepicker' size='10' id='sdatepicker"+i+"' data-id='"+i+"'  name='sdate"+d[k].assetid+"' onkeydown='return false' required disabled autocomplete = 'off'/></td><td style='white-space: nowrap;'><input type='radio' class='onGoing' name = 'status"+d[k].assetid+"' value='O' data-id='"+i+"' id='onGoing' checked='checked'>OnGoing<input type='radio' class='complete"+i+"' value='C' data-id='"+i+"' name = 'status"+d[k].assetid+"' id='complete'>Complete<input type='radio' data-id='"+i+"' value='F' class='forClose"+i+"' name = 'status"+d[k].assetid+"' id='forClose'>ForClosed</td><td><input type='text' class='datepicker'  size='10' id='cdatepicker"+i+"' name='cdate"+d[k].assetid+"' onkeydown='return false' disabled='disabled' required autocomplete = 'off'/></td><td><textarea rows='4' size='15' height='25' name='reason"+d[k].assetid+"' id='reason"+i+"' readonly='readonly' required autocomplete = 'off'></textarea> </td></tr>";
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

				
				tblData+="<tr><td colspan='10'><center><input type='submit' id='submit' value='Save/Update'/><center></td></tr>";

			}else{
				//alert("hi");

				
				tblData+="<tr><td colspan='10'><center><button id='submit1' value='Save/Update' onclick='updatedata(event);'/>Save/Update<center></td></tr>";

			}
			
							}
							
							}	
					
	}
          
 else{
 
		tblData="<tr><td colspan='10' class='text-center'>Data not found !</td></tr>";

	}
	$tbodyassetWiseHeadStatusId.append(tblData);
            }
	});
	
	}
	});
	});	
	
$( document ).ready(function(){
	$('#viewcomplete').on('click', function(e) {
		e.preventDefault();
	$(".perror").hide();
	$(".uperror").hide();
	
		var i = 1;
		$pCode=$('#cproject option:selected').val();
		$hCode=$('#chead option:selected').val();
		$headactivity=$('#cheadactivity option:selected').val();
		
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
		if($('#chead option:selected').val()==""){
			successAlert('Please select Head Type');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#chead').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#chead').focus();
			});
			return false;
		}
		if($('#cheadactivity option:selected').val()==""){
			successAlert('Please select Head Activity');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#cheadactivity').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#cheadactivity').focus();
			});
			return false;
		}
$('#loading').show();
	$.ajax({  
            url:"getassetwiseheadcompletedata",
            type: "post", 
			data:{pCode:$pCode, hCode:$hCode, headactivity:$headactivity}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(AssetIdBean) {
            $('#loading').hide();
	        console.log(AssetIdBean); 
		var tblData="";
		
					$tbodyassetWiseCHeadStatusId = $('#tbodyassetWiseCHeadStatusId');
						$tbodyassetWiseCHeadStatusId.empty();
		if(Object.keys(AssetIdBean).length>0){
						 for ( var key in AssetIdBean) {
						   
			tblData+="<tr><td>"+i+"</td><td>"+AssetIdBean[key].asseteid+"</td><td>"+AssetIdBean[key].activitydesc+"</td><td>"+AssetIdBean[key].bname+"</td><td>"+AssetIdBean[key].vname+"</td><td>"+AssetIdBean[key].sdate+"</td><td>"+AssetIdBean[key].cdate+"</td></tr>";
		    i++
						}	
	}
  else{
 	tblData="<tr><td colspan='7' class='text-center'>Data not found !</td></tr>";
	}
	$tbodyassetWiseCHeadStatusId.append(tblData);
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
		$hCode=$('#fhead option:selected').val();
		
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
		if($('#fhead option:selected').val()==""){
			successAlert('Please select Head Type');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#fhead').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#fhead').focus();
			});
			return false;
		}
$('#loading').show();
	$.ajax({  
            url:"getassetwiseheadforcloseddata",
            type: "post", 
			data:{pCode:$pCode, hCode:$hCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(AssetIdBean) {
            $('#loading').hide();
	        console.log(AssetIdBean); 
		var tblData="";
		
					$tbodyassetWiseFHeadStatusId = $('#tbodyassetWiseFHeadStatusId');
						$tbodyassetWiseFHeadStatusId.empty();
		if(Object.keys(AssetIdBean).length>0){
						 for ( var key in AssetIdBean) {
						   
			tblData+="<tr><td>"+i+"</td><td>"+AssetIdBean[key].asseteid+"</td><td>"+AssetIdBean[key].activitydesc+"</td><td>"+AssetIdBean[key].bname+"</td><td>"+AssetIdBean[key].vname+"</td><td>"+AssetIdBean[key].sdate+"</td><td>"+AssetIdBean[key].reason+"</td></tr>";
		    i++
						}	
	}
  else{
 	tblData="<tr><td colspan='7' class='text-center'>Data not found !</td></tr>";
	}
	$tbodyassetWiseFHeadStatusId.append(tblData);
            }
	});	
	});
	});