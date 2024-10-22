$(function(){
	
	$('#chkSelectAllkd').on('click',function(){
		$chkValue=0;
        if(this.checked)
        {
            $('.chkIndividualkd').each(function(){
                this.checked = true;
				$chkValue++;
            });
        }
        else{
             $('.chkIndividualkd').each(function(){
                this.checked = false;
            });
			$chkValue=0;
        }
		if($(".chkIndividualkd").prop('checked')){
			$('#btncomplete').removeClass('disabled');
			$('#delete').removeClass('disabled');	
			$('#btncomplete').removeAttr("disabled");
			$('#delete').removeAttr("disabled");
		}
		else{
			$('#btncomplete').addClass('disabled');
			$('#delete').addClass('disabled');
			$('#btncomplete').attr('disabled', 'disabled');
			$('#delete').attr('disabled', 'disabled');
		}
    });
    
    $('#chkSelectAll').on('click',function(){
		$chkValue=0;
        if(this.checked)
        {
            $('.chkIndividual').each(function(){
                this.checked = true;
				$chkValue++;
            });
        }
        else{
             $('.chkIndividual').each(function(){
                this.checked = false;
            });
			$chkValue=0;
        }
		if($(".chkIndividual").prop('checked')){
				$('#btnWCDCForward').removeClass('disabled');
				$('#btnWCDCForward').removeAttr("disabled");
		}
		else{
				$('#btnWCDCForward').addClass('disabled');
				$('#btnWCDCForward').attr('disabled', 'disabled');
		}
    });
    
  /*  
    $("input:checkbox").not('#chkSelectAll').each(function() {
		$chkValue= 0;
		$(this).on('click',function(){ //alert($(this).val());
        	if($('.chkIndividual:checked').length == $('.chkIndividual').length){
            	$('#chkSelectAll').prop('checked',true);
        	}
        	else{
            	$('#chkSelectAll').prop('checked',false);
        	}
			if(this.checked)
				$chkValue++;
			if(!this.checked)
				$chkValue--;
			if($chkValue>0){
				alert($chkValue);
				$('#btnWCDCForward').removeClass('disabled');
				$('#btnWCDCForward').removeAttr("disabled");
			}
			else{
				$('#btnWCDCForward').addClass('disabled');
				$('#btnWCDCForward').attr('disabled', 'disabled');
			}
		});
	});*/
    
     $("#nrmwork").on("blur", function() { 
		 
           $nrmwork=$('#nrmwork').val();
           
           $.ajax({  
            url:"checkNRMValidORNot",
            type: "post",  
            data: {nrmworkid:$nrmwork},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
			//	alert(data);
				if(data==='fail'){
					$('#nrmwork').val('');
					alert('Please Enter Correct NRM Work-Id');
				}
			}
		});
		
		
		$.ajax({  
            url:"getWorkIdWiseNRMWorkDraft",
            type: "post", 
			data:{nrmwork:$nrmwork}, 
            error:function(xhr,status,er){
                console.log(er);
            },
             success: function(data) {
				console.log(data);
				$('#loading').hide();
				var i = 1;
				var k=0;
				var tblData="";
				var headname="";
				$tbodyassetWiseStatusId = $('#listofDraftDatakd');
				$tbodyassetWiseStatusId.empty();
			if(Object.keys(data).length>0)
			{  
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						
						if(data[key].otherHead=='e')
						{
							headname="Entry point Activities(EPAs)";
						}
						if(data[key].otherHead=='l')
						{
							headname="Livelihood Activities";
						}
						if(data[key].otherHead=='p')
						{
							headname="Production System";
						}
						tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividualkd' id='chkIndividualkd"+data[key].nrmworkOtherworkId+"'  name='chkIndividualkd"+data[key].nrmworkOtherworkId+"' value='"+data[key].nrmworkOtherworkId+"'/> &nbsp; &nbsp; <input type='hidden' id = 'assetid"+data[key].assetid+"' name ='assetid"+data[key].assetid+"' value="+data[key].assetid+"> "+data[key].assetid+"</td><td>"+data[key].headdesc+"</td><td>"+data[key].activitydesc+"</td> <td>"+data[key].dname+", " +data[key].bname+", "+data[key].vname+"</td> <td>"+headname+"</td> <td>"+data[key].otherworkId+"</td></tr>";
					}
				i++;
			//	k=k+data[key].assetach;
				}
				tblData+="<tr><td colspan='2'><button id='btncomplete' name='btncomplete' class='btn btn-info disabled' disabled='disabled' >Complete</button></td><td colspan='1'><button id='delete' name='delete' class='btn btn-info disabled' disabled='disabled' >Delete</button></td></tr>";
			}
			else{
					tblData='<tr class="text-center"><td colspan="9"> Data not found !</td></tr>';
			}
			$tbodyassetWiseStatusId.append(tblData);
		}
		});
		
		
		
		
		
		
     }); 
    
    
	
	$('#project').on("change" ,function() {
		$('.error').html('');
		$projId = $('#project option:selected').val();
		var url ="getFinYearProjectWise";
		$.ajax({  
            url:url,
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	
				var $year = $('#year');
				$year.empty();
        		$year.append('<option value="">--Select Year--</option>');
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						  $year.append('<option value='+key+'>' +data[key] + '</option>');
					}
				}
			}
		});
		
		
		$.ajax({  
            url:"getmappingNRMWorkDraft",
            type: "post", 
			data:{pCode:$projId}, 
            error:function(xhr,status,er){
                console.log(er);
            },
             success: function(data) {
				console.log(data);
				$('#loading').hide();
				var i = 1;
				var k=0;
				var tblData="";
				var headname="";
				$tbodyassetWiseStatusId = $('#listofDraftDatakd');
				$tbodyassetWiseStatusId.empty();
			if(Object.keys(data).length>0)
			{  
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						
						if(data[key].otherHead=='e')
						{
							headname="Entry point Activities(EPAs)";
						}
						if(data[key].otherHead=='l')
						{
							headname="Livelihood Activities";
						}
						if(data[key].otherHead=='p')
						{
							headname="Production System";
						}
						tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividualkd' id='chkIndividualkd"+data[key].nrmworkOtherworkId+"'  name='chkIndividualkd"+data[key].nrmworkOtherworkId+"' value='"+data[key].nrmworkOtherworkId+"'/> &nbsp; &nbsp; <input type='hidden' id = 'assetid"+data[key].assetid+"' name ='assetid"+data[key].assetid+"' value="+data[key].assetid+"> "+data[key].assetid+"</td><td>"+data[key].headdesc+"</td><td>"+data[key].activitydesc+"</td> <td>"+data[key].dname+", " +data[key].bname+", "+data[key].vname+"</td> <td>"+headname+"</td> <td>"+data[key].otherworkId+"</td></tr>";
					}
				i++;
			//	k=k+data[key].assetach;
				}
				tblData+="<tr><td colspan='2'><button id='btncomplete' name='btncomplete' class='btn btn-info disabled' disabled='disabled' >Complete</button></td><td colspan='1'><button id='delete' name='delete' class='btn btn-info disabled' disabled='disabled' >Delete</button></td></tr>";
			}
			else{
					tblData='<tr class="text-center"><td colspan="9"> Data not found !</td></tr>';
			}
			$tbodyassetWiseStatusId.append(tblData);
		}
		});
		
	});
	
	$('#head').on("change" ,function () { 
			$('#loading').show();
		$headVal = $('#head option:selected').val();
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
				var $activity = $('#activity');
				$activity.empty();
        		$activity.append('<option value="0">--All--</option>');
        		
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						 $activity.append('<option value='+key+'>' +data[key] + '</option>');
					}
				}
			}
		});
	});	
	
	
	$('#btnGetAsset').on('click', function(e) {
		e.preventDefault();
	$(".perror").hide();
	$(".uperror").hide();
	
		var i = 1;
		$pCode=$('#project option:selected').val();
		$fYear=$('#year option:selected').val();
		$heads=$('#head option:selected').val();
		$activities=$('#activity option:selected').val();
		$nrmwork=$('#nrmwork').val();
		var url='';
		if($nrmwork===""){
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
		
		if($('#head option:selected').val()==""){
			successAlert('Please select Head');
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
		
		if($('#activity option:selected').val()==""){
			successAlert('Please select Activity');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#activity').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#activity').focus();
			});
			return false;
		}
		url= 'getmappingNRMWork';
		$nrmwork=0;
		}
		else{
			
			url= 'getmappingNRMWork';
			
			$pCode=0;
			$fYear=0;
			$heads=0;
			$activities=0;
		}
		$('#loading').show();
		$.ajax({  
            url:"getmappingNRMWork",
            type: "post", 
			data:{pCode:$pCode, fYear:$fYear,head:$heads,activity:$activities, nrmwork:$nrmwork}, 
            error:function(xhr,status,er){
                console.log(er);
            },
             success: function(data) {
				console.log(data);
				$('#loading').hide();
				var i = 1;
				var k=0;
				var tblData="";
				$tbodyassetWiseStatusId = $('#tbodyassetWiseStatusId');
				$tbodyassetWiseStatusId.empty();
				if(Object.keys(data).length>0)
				{  
					for ( var key in data) 
					{
						if (data.hasOwnProperty(key)) 
						{
							
							tblData+="<tr><td>"+i+"</td><td><input type='checkbox' class='chkIndividual' id='chkIndividual"+data[key].assetid+"'  name='chkIndividual"+data[key].assetid+"' value='"+data[key].assetid+"'/> &nbsp; &nbsp; <input type='hidden' id = 'assetid"+data[key].assetid+"' name ='assetid"+data[key].assetid+"' value="+data[key].assetid+"> "+data[key].assetid+"</td><td>"+data[key].headdesc+"</td><td>"+data[key].activitydesc+"</td> <td>"+data[key].dname+", " +data[key].bname+", "+data[key].vname+"</td> <td><select id='headtype"+data[key].assetid+"' name='headtype"+data[key].assetid+"' class='ddlheadtype form-control' ><option value=''>--Select--</option><option value='e'>Entry point Activities(EPAs)</option><option value='l'>Livelihood Activities for the Asst-less Person</option><option value='p'>Production System</option></select></td> <td><select id='acttype"+data[key].assetid+"' name='acttype"+data[key].assetid+"' class='ddlacttype form-control' ><option value=''>--Select Activity--</option></select></td><td><select id='worktype"+data[key].assetid+"' name='worktype"+data[key].assetid+"' class='ddlworkloc form-control' ><option value=''>Select Work-Id with Location</option></select></td></tr>";
						}
					i++;
				//	k=k+data[key].assetach;
					}
					tblData+="<tr><td colspan='2'><button id='btnWCDCForward' name='btnWCDCForward' class='btn btn-info '  >Save as Draft</button></td></tr>";
				}
				else{
						tblData='<tr class="text-center"><td colspan="10"> Data not found !</td></tr>';
				}
				$tbodyassetWiseStatusId.append(tblData);
			}
            
		});
	});
	
	
	$('#getCompleteAsset').on('click', function(e) {
		e.preventDefault();
		$schemed = $('#headother option:selected').val();
		$projcd = $('#projectpi option:selected').val();
	//	alert('proj='+$projcd+' , scheme='+$schemed)
	
		if($('#projectpi option:selected').val()===""){
			successAlert('Please select Project');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#projectpi').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#projectpi').focus();
			});
			return false;
		}
		if($('#headother option:selected').val()===""){
			successAlert('Please select Head');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			$('#headother').focus();
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			$('#headother').focus();
			});
			return false;
		}
	
		$tbodyfinalListActivityAssetId = $('#tbodyfinalListActivityAssetId');
		$tbodyfinalListActivityAssetId.empty();
		$('#loading').show();
		
		var i = 1;
			//alert("starting");
			$.ajax({  
            url:"getCompletedNRMRelatedOtherWork",
            type: "POST",
            data:{scheme:$schemed,projcd:$projcd},  
            error:function(xhr,status,er){
                console.log(er);
                },
           success: function(data) {
				$('#loading').hide();
				var tbodyCMovement = $('#tbodyfinalListActivityAssetId');
				tbodyCMovement.empty();
				if(Object.keys(data).length>0)
				{
					for ( var key in data) 
					{
						if (data.hasOwnProperty(key)) 
						{
							var d= data[key];
							for ( var k in d) 
							{
								if (d.hasOwnProperty(k)) 
								{
									tbodyCMovement.append("<tr><td>"+i+"</td><td>"+d[k].nrmwork_id+"</td><td>"+d[k].headname+"</td><td>"+d[k].activityname+"</td><td>"+d[k].blockname+","+d[k].grampanchayatname+","+d[k].villagename+"</td><td>"+d[k].epaact+"</td><td>"+d[k].otherwork_id+"</td></tr>");
									i++;
								}
							}
						}
					}
				}
				else{
					tbodyCMovement.append("<tr><td colspan='7' class='text-center'>Data not found !</td></tr>");
				}
			}
          });
       });
	
});
	
	
	
	
	$(document).on('change', '.ddlheadtype', function(e) { 
		$('#loading').show();
		$pCode=$('#project option:selected').val();
		$id=$(this).prop("id");
		$id = $id.replace ( /[^\d.]/g, '' );  
		$headtype=$(this).val();
	//	alert($id+', '+ $headtype);
		$.ajax({  
            url:"getOtherHeadActivity",
            type: "post",  
            data: {headtyp:$headtype, workid:$id},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				
				$acttype = $('#acttype'+$id);
				$acttype.empty();
				$acttype.append("<option value=''>--Select--</option>");		
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$acttype.append("<option value='"+key+"'>"+data[key]+"</option>");
						}		
					}
				}
				else{
					
				}
			}
		});
	});
	
	$(document).on('click', '#btnWCDCForward', function(e){
	e.preventDefault();
	$pCode=$('#project option:selected').val();
	var finalAssetid=new Array();
	var remarks = new Array();
	var otherwork = new Array();
	var i=0;
	var j=0;
	$('.chkIndividual').each(function(){
         	if($(this).prop('checked'))
         	{
				if($('#headtype'+$(this).val()+' option:selected').val()==="")
				{
					successAlert('Please select Other Head');
					$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#headtype'+$(this).val()).focus();
					});  
					$(".close").click(function(){
					$('#popup').modal('hide');
					$('#headtype'+$(this).val()).focus();
					});
					return false;
				}
				if($('#acttype'+$(this).val()+' option:selected').val()==="")
				{
					successAlert('Please select Activity under selected Head');
					$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#acttype'+$(this).val()).focus();
					});  
					$(".close").click(function(){
					$('#popup').modal('hide');
					$('#acttype'+$(this).val()).focus();
					});
					return false;
				}
				if($('#worktype'+$(this).val()+' option:selected').val()==="")
				{
					successAlert('Please select Other Work.');
					$("#successok").click(function(){
					$('#popup').modal('hide');
					$('#worktype'+$(this).val()).focus();
					});  
					$(".close").click(function(){
					$('#popup').modal('hide');
					$('#worktype'+$(this).val()).focus();
					});
					return false;
				}
				else{
					j=j+1;
				}
				
				finalAssetid.push($(this).val());
			 	remarks.push($('#headtype'+$(this).val()+' option:selected').val());
			 	otherwork.push($('#worktype'+$(this).val()+' option:selected').val());
			 	i=i+1;
			}
			
      });
      var check=$('.chkIndividual:checked').length;
   //   alert(i+'=kdy='+$('.chkIndividual:checked').length)
  if($pCode===""){
		
		$pCode=0;
		
	}
//alert(finalAssetid+','+remarks+','+otherwork);
//if(confirm("Do you want to Save. ?")){
	
	if(check>0){
		if(check==j)	{
			
			if(confirm("Do you want to Save. ?")){
			
		$.ajax({  
            url:"saveNRMwithOtherAsset",
            type: "post",  
            data: {assetid:finalAssetid.toString(), headtype:remarks.toString(), otherwork:otherwork.toString(), projcd:$pCode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
			console.log(data);
			$('#loading').hide();
				if(data==='success')
				{
					alert('Data Save Successfully.');
					window.location.href='mappingNRMWork';
				}
				else{
					alert('You have chosen duplicates works !');
					//window.location.href='mappingNRMWork';
				} 
	
			}
		});
		}
	}
	else{
		alert('Please select All Other Head, Other Activity, Other Work-Id for You have Checked those NRM works');
	}
	}
	else{
		alert('Please Check at least One checkbox');
	}
	});	
	
	$(document).on('change', '.ddlacttype', function(e) { 
		//$('#loading').show();
	$pCode=$('#project option:selected').val();
	$id=$(this).prop("id");
	$id = $id.replace ( /[^\d.]/g, '' );  
	$acttype=$(this).val();
	$head=$('#headtype'+$id+' option:selected').val()
	//alert($id);
	
	if($('#acttype'+$id+' option:selected').val()==="")
	{
		$("select#worktype"+$id)[0].selectedIndex = 0;
		successAlert('Please select Activity under selected Head');
		$("#successok").click(function(){
		$('#popup').modal('hide');
		$('#acttype'+$(this).val()).focus();
		});  
		$(".close").click(function(){
		$('#popup').modal('hide');
		$('#acttype'+$(this).val()).focus();
		});
		return false;
	}
	if($pCode===""){
		
		$pCode=0;
		
	}
	
//	$head=$('#project'+$id+' option:selected').val();
//	alert($acttype+', '+ $head);
	$.ajax({  
            url:"getOtherHeadActivityWork",
            type: "post",  
            data: {acttypes:$acttype, headtyp:$head, proj:$pCode, assetid:$id},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				
				$acttype = $('#worktype'+$id);
				$acttype.empty();
				$acttype.append("<option value=''>Select Work-Id with Location</option>");		
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$acttype.append("<option value='"+key+"'>"+data[key]+"</option>");
						}		
					}
				}else{
					alert('Work not found for selected activity. ! ');
					$("select#worktype"+$id)[0].selectedIndex = 0;
					$("select#headtype"+$id)[0].selectedIndex = 0;
					$("select#acttype"+$id)[0].selectedIndex = 0;
					$('#chkIndividual'+$id).prop('checked',false);
					
				}
			}
		});
	});
	
	
	
	
	$(document).on('change', '.ddlworkloc', function(e) { 
	//	$('#loading').show();
	$pCode=$('#project option:selected').val();
	$id=$(this).prop("id");
	$id = $id.replace ( /[^\d.]/g, '' );  
	$workid=$(this).val();
	$head=$('#headtype'+$id+' option:selected').val();
	$check1=$('#chkIndividual'+$id).prop('checked',true);
	$check=$("#chkIndividual"+$id).prop('checked'); 
	$worktype=$('#worktype'+$id+' option:selected').val();
	$checkval=$("#chkIndividual"+$id).val();
	
	if($('#acttype'+$id+' option:selected').val()==="")
	{
		$("select#worktype"+$id)[0].selectedIndex = 0;
		successAlert('Please select Activity under selected Head');
		$("#successok").click(function(){
		$('#popup').modal('hide');
		$('#acttype'+$(this).val()).focus();
		});  
		$(".close").click(function(){
		$('#popup').modal('hide');
		$('#acttype'+$(this).val()).focus();
		});
		return false;
	}
	
	//alert($id+","+ $workid+","+ $head+","+ $check1+","+ $check+","+ $worktype+","+ $checkval);
	
    if($check ==false){
		
		successAlert('Please check checkbox !');
			$("#successok").click(function(){
			$('#popup').modal('hide');
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
			$('#user').focus();
			return false;
	}
//	alert($workid+', '+ $head+', '+$check +', '+$worktype+', '+$checkval);
	$.ajax({  
            url:"checkNRMandOtherWorkMatch",
            type: "post",  
            data: {otherwork:$worktype, headtyp:$head, proj:$pCode,nrmwork:$checkval},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
			//	alert(data);
				if(data==='fail'){
					
					$("select#worktype"+$id)[0].selectedIndex = 0;
					$("select#headtype"+$id)[0].selectedIndex = 0;
					$("select#acttype"+$id)[0].selectedIndex = 0;
					alert('Selected Other Work Location not match of NRM Work Location.\n Please Select Correct Work Location. ');
					$('#chkIndividual'+$id).prop('checked',false);
				}
			}
		});
	});
	
	
	$(document).on('click', '#delete', function(e){
		e.preventDefault();
		$pCode=$('#project option:selected').val();
		var finalAssetid=new Array();

		$('.chkIndividualkd').each(function(){
         	if($(this).prop('checked'))
         	{
				finalAssetid.push($(this).val());
			}
      	});
      
//alert(finalAssetid+','+remarks+','+otherwork);
//alert('Do you want to Delete ?');
		if(confirm("Do you want to Delete ?"))
		{
			$.ajax({  
	            url:"deleteNRMwithOtherAsset",
	            type: "post",  
	            data: {assetid:finalAssetid.toString()},
	            error:function(xhr,status,er){
	                console.log(er);
	            },
	            success: function(data) {
				console.log(data);
				$('#loading').hide();
					if(data==='success')
					{
						alert('Data Delete Successfully.');
						window.location.href='mappingNRMWork';
					}
					else{
						alert('Please check at least One Check Box, Data not Deleted!');
						window.location.href='mappingNRMWork';
					} 
	
				}
			});
		}
	});	
	
	
	$(document).on('click', '#btncomplete', function(e){
	e.preventDefault();
	$pCode=$('#project option:selected').val();
	var finalAssetid=new Array();

	$('.chkIndividualkd').each(function(){
         	if($(this).prop('checked'))
         	{
				finalAssetid.push($(this).val());
			}
     });
      
//alert(finalAssetid+','+remarks+','+otherwork);
	if(confirm("Do you want to complete/Lock data ?"))
	{
		$.ajax({  
            url:"completeNRMwithOtherAsset",
            type: "post",  
            data: {assetid:finalAssetid.toString()},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
			console.log(data);
			$('#loading').hide();
				if(data==='success')
				{
					alert('Data Complete Successfully.');
					window.location.href='mappingNRMWork';
				}
				else{
					alert('Please check at least One Check Box, Data not Complete!');
					window.location.href='mappingNRMWork';
				} 
	
			}
		});
	}
	
	});	

	
	$(document).on('change', '.chkIndividualkd', function(e) 
	{ 
		if($(this).prop('checked'))
		{
			$('#btncomplete').removeClass('disabled');
			$('#delete').removeClass('disabled');	
			$('#btncomplete').removeAttr("disabled");
			$('#delete').removeAttr("disabled");
		}
		else{
			$('#btncomplete').addClass('disabled');
			$('#delete').addClass('disabled');
			$('#btncomplete').attr('disabled', 'disabled');
			$('#delete').attr('disabled', 'disabled');
		}
	});
	
	/*$(document).on('change', '.chkIndividual', function(e) 
	{ 
		if($(this).prop('checked'))
		{
			$('#btnWCDCForward').removeClass('disabled');
			$('#btnWCDCForward').removeAttr("disabled");
		}
		else{
			$('#btnWCDCForward').addClass('disabled');
			$('#btnWCDCForward').attr('disabled', 'disabled');
		}
	});*/
	
	
	
	
	
	
	
		