/**
 * 
 */

$(function(){
	
		/*************************************** Head change script ********************************** */
		$("#listActivityHeadWiseTbody").on('change','.ddlHead',function () {
		//	alert('kedar');
			$('#loading').show();
	var id = $(this).attr('id'); 
	id=id.substring(id.lastIndexOf("d")+1,id.length);
	$headVal = $(this).val();
	$('#txtTarget'+id).val("");
	var $unit = $('#ddlUnit'+id);
	$unit.val('');
		//$unit.empty();
       // $unit.append('<option value="">--Select Unit--</option>');
		$.ajax({  
            url:"getActivity",
            type: "post",  
            data: {headId:$headVal},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	var $activity = $('#ddlActivity'+id);
						$activity.empty();
        				$activity.append('<option value="">--Select Activity--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $activity.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	
	}
	});
	});
	/*************************************************** End ********************************************** */
	
	/*************************************** Activity change script ********************************** */
		$("#listActivityHeadWiseTbody").on('change','.ddlActivity11',function () { 
			$('#loading').show();
	var id = $(this).attr('id'); 
	id=id.substring(id.lastIndexOf("y")+1,id.length);
	$activityVal = $(this).val();
	$('#txtTarget'+id).val("");
		$.ajax({  
            url:"getUnit",
            type: "post",  
            data: {activityId:$activityVal},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	var $unit = $('#ddlUnit'+id);
						$unit.val('');
        				//$unit.append('<option value="">--Select Unit--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       //$unit.append('<option value='+key+'>' +data[key] + '</option>');
$unit.val(data[key]);
						                    }
						                }
	
	}
	});
	});
	
	/*************************************************** End ********************************************** */

/******************************************* View Head/Activity click *************************** */

$('#viewHeadActivity').click(function(e) {
	$('#loading').show();
	var i=1;
	var headCode=1;
	var activityCode=1;
		$.ajax({  
            url:"viewHeadActivity",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var tbodyMovement = $('#tbodyHeadActivity');
	tbodyMovement.empty();
	if(Object.keys(data).length>0){
	for ( var key in data) {
		if (data.hasOwnProperty(key)) {
			activityCode=1;
			var d= data[key];
			for ( var k in d) {
		if (d.hasOwnProperty(k)) {
			if(parseInt(k)===0)
			tbodyMovement.append("<tr><td>"+i+"</td><td>"+headCode+". "+key+"</td><td>"+headCode+"."+activityCode+" "+d[k]+"</td></tr>");
			else
			tbodyMovement.append("<tr><td>"+i+"</td><td></td><td>"+headCode+"."+activityCode+" "+d[k]+"</td></tr>");
		}
		activityCode++;
		i++;
		}
		headCode++;
		}
			}
	}else{
		tbodyMovement.append("<tr><td colspan='7' class='text-center'>Data not found !</td></tr>");
	}
	}
	});
	});
	
$('#Save').click(function(e) {
	e.preventDefault();
	$('#loading').show();
	
	if($('#ddlHead option:selected').val()==""){
			successAlert('Please select head');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#ddlHead').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#ddlHead').focus();
			});
			return false;
		}
		if($('#ddlActivity option:selected').val()==""){
			successAlert('Please select activity');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#ddlActivity').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#ddlActivity').focus();
			});
			return false;
		}
		if($('#parameterDesc').val()==""){
			successAlert('Enter parameter');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#parameterDesc').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#parameterDesc').focus();
			});
			return false;
		}
		if($('#ddlUnit option:selected').val()==""){
			successAlert('Please select unit');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#ddlUnit').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#ddlUnit').focus();
			});
			return false;
		}
		var ahead=$('#ddlHead option:selected').val();
		var aActivity=$('#ddlActivity option:selected').val();
		var parameterDesc=$('#parameterDesc').val();
		var aUnit=$('#ddlUnit option:selected').val();
		
		$.ajax({  
            url:"saveAssetParameter",
            type: "post",  
            data: {ahead:ahead,aActivity:aActivity,parameterDesc:parameterDesc,aUnit:aUnit},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	console.log(data);
            	$('#loading').hide();
            	if(data==='success'){
            		successAlert("Data saved successfully !");
            		$("#successok").click(function(){
            			$('#popup').modal('hide');
            		});  
            		$(".close").click(function(){
            			$('#popup').modal('hide');
            		});
            		//	getHeadData();
            	}
            	else{
            		successAlert("There is some error while saving data. Please try again!");
            		$("#successok").click(function(){
            			$('#popup').modal('hide');
            		});  
            		$(".close").click(function(){
            			$('#popup').modal('hide');
            		});
            	}
            }
	});
		
	});

	
	});


	
	