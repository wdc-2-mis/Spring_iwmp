/**
 * 
 */
$(function(){
	$('#projectLocationTable')
	.DataTable(
			{
				 "paging":   true,
				 "lengthChange": true,
				 "lengthMenu": [[ -1, 50], ["All", 50 ]]
				
				}
				);
				
		$('#projectMappedDraftTable')
	.DataTable(
			{
				 "paging":   true,
				 "lengthChange": true,
				 "lengthMenu": [[ -1, 50], ["All", 50 ]]
				
				}
				);		
				
			$('#projectMappedTable')
	.DataTable(
			{
				 "paging":   true,
				 "lengthChange": true,
				 "lengthMenu": [[ -1, 50], ["All", 50 ]]
				
				}
				);		
				
	
	
	$('#addProjectLocation').on("click" ,function() {
		$('.error').text('');
		$('#project').val('');
		var $multiblock = $('#multiblock');
		$multiblock.empty();
        $multiblock.append('<option value="">--Select Block--</option>');
		var $block = $('#block');
		$block.empty();
        $block.append('<option value="">--Select Block--</option>');
		var $village = $('#village');
		$village.empty();
        $village.append('<option value="">--Select Village--</option>');
		getDataTable("");	
		$('#draftSave').prop("disabled",false);
		$('#complete').prop("disabled",false);
		removeInputFieldErrorCss('#project');
		removeInputFieldErrorCss('#multiblock');
		removeInputFieldErrorCss('#block');
		removeInputFieldErrorCss('#village');
		});
		
		$('#mappingOfVillageWatershedCommittee').on("click" ,function() {
		$('.error').text('');
		$('#mpdprojname').val('');
		
		
		getDataTable("");
		getDataTable1("");	
		
		
		$('#mpdprojname').prop("mpdprojname",false);
		$('#draftWCSave').prop("draftWCSave",false);
		$('#complete').prop("disabled",false);
		
		});
		
		
	/************************************* Code to get Block value on selection from multiBlock ************************ */
	$('#multiblock').on("click" ,function() {
		var $block = $('#block');
		$block.empty();
        $block.append('<option value="">--Select Block--</option>');
       	$.each($("#multiblock option:selected"), function(){            
		$block.append('<option value='+$(this).val()+'>'+$(this).text()+'</option>');
        });
		});
		
		/********************************* Code to get multiBlock value on basis of Project *************************** */
		$('#project').on("change" ,function() {
			var $village = $('#village');
			$village.empty();
        	$village.append('<option value="">--Select Village--</option>');
			var $block = $('#block');
			$block.empty();
        	$block.append('<option value="">--Select Block--</option>');
        	var $multiblock = $('#multiblock');
        	$multiblock.empty();
        	$multiblock.append('<option value="">--Select Block--</option>');
        	getDataTable("");
        	var url= 'piaBlockProjectWise';
        	var url2 = 'getPreFilledProjectLocationData';
        	var projectId = $('#project option:selected').val();
        	if(projectId==='' || projectId ===null){
			$('.error').text(' Please select project first !');
			$('#project').focus();
			addInputFieldErrorCss('#project');
			e.preventDefault();
			return;
        	}else{
			$('.error').text('');
			removeInputFieldErrorCss('#project');
        	}
		
        	var btnHide=false;
		$.ajax({  
            url:url2,
            type: "post",  
            data: {projId:projectId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
						localStorage.setItem('village',data);
						$tbody = $('#tbody');
						//$tbody.empty();
						for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							var d = data[key];
								for ( var k in d) {
									console.log("hideq "+d[k].status);
									if(d[k].status){
										
										$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+d[k].grampanchayatname+'</td><td>'+d[k].villagename+
													'</td><td>&nbsp;</td>');
									}else{
										$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+d[k].grampanchayatname+'</td><td>'+d[k].villagename+
													'</td><td><a href="#" >delete</a></td>');
										vCode.push(d[k].vcode);
									}
										//btnHide=d[k].status;
									btnHide=false;
									}
							}
						}
						console.log("vCode: "+vCode);
						if(btnHide){
							$('.error').html('You have already assigned location to this project. Data is locked !');
							$('#draftSave').prop("disabled",true);
							$('#complete').prop("disabled",true);
							//alert(btnHide+' kd');
						}
						
						else{
							$('#draftSave').prop("disabled",false);
							$('#complete').prop("disabled",false);
						//	alert(btnHide+' kd1');
							$.ajax({  
            url:url,
            type: "post",  
            data: {projId:projectId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $multiblock = $('#multiblock');
						$multiblock.empty();
        				$multiblock.append('<option value="">--Select Blocks--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $multiblock.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
                }
        });
						}
						getDataTable(data);	
                }
        });
		});
		
		/**********************************************code to mapped of village and watershed committee****************************/
		$rowCount =0;
	
		$('#mpdprojname').on("change" ,function() {
		
		//	alert('kdy');
		var projectId = $('#mpdprojname option:selected').val();
		var url="existingWatershedData";
		var id=1;
		var id1=1;
		var checkstatus=false;
		var checkfinalstatus;
		
		if(projectId==='' || projectId ===null){
			$('.error').text(' Please select project first !');
			$('#mpdprojname').focus();
			addInputFieldErrorCss('#mpdprojname');
			e.preventDefault();
			return;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#mpdprojname');
		}
		$.ajax({  
            url:"getcheckWCStatus",
            type: "post",  
            data: {projId:projectId},
            error:function(xhr,status,er){
                console.log(er);
            },
		success: function(data) {
	console.log(data);
	//checkstatus=data;
	
	for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							var d = data[key];
								for ( var k in d) {
								checkstatus=d[k].status;
								//alert(checkstatus);
								}}}
	
	
	//alert(checkstatus);
//	if(!checkstatus){
	$.ajax({  
            url:url,
            type: "post",  
            data: {projId:projectId},
            error:function(xhr,status,er){
                console.log(er);
            },
		success: function(data) {
			console.log(data);
						$tbody = $('#wsdtbody');
						$tbody.empty();
						var d = data[0];   // transaction data with village
						var d2 = data[1];  //  master wc data of combo
						//	alert(d);
						var myarray =new Array();
						var arr=new Array();
						
						for ( var k in d) {
							checkstatus=d[k].status;
							
							
							$wsSelect = '<select id="wc'+id+'">';
							$hiddenVillage = '<input type="hidden" id="pl'+id+'" value="'+d[k].projectlocid+'" />';
							
						if(d2.length===0)
						$wsSelect+='<option value="" >--Select--</option>';
						
						else
						{
						for(var y in d2){
							if(d2[y].wcid===d[k].wcid1){
								$wsSelect+='<option value="'+d[y].wcid+'"selected>'+d2[y].wcname+'</option>';
								}
							else
								$wsSelect+='<option value="'+d2[y].wcid+'">'+d2[y].wcname+'</option>';
						
						}
						}
						$wsSelect+='</select>';
						$rowCount=id;
							id=id+1;
							
									$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+$hiddenVillage+d[k].villagename+'</td><td>'+$wsSelect+'</td></tr>');
							
								}
						
						/*for( var k in d) 
						{
							checkstatus=d[k].status;
								
							$wsSelect = '<select id="wc'+id+'" multiple="multiple">';
							$hiddenVillage = '<input type="hidden" id="pl'+id+'" value="'+d[k].projectlocid+'" />';
							$wsSelect+='<option value="" >--Select--</option>';
						
							myarray = d[k].wccomma.split(',');
							for(var i = 0; i < myarray.length; i++)
							{
								for(var y in d2)
								{
									if(d2[y].wcid==myarray[i])
									{
									//	alert(d[k].wcid1+' kedar '+d[k].projectlocid+'  kdy ' +d2[y].wcid);
										$wsSelect+='<option value="'+d2[y].wcid+'"selected>'+d2[y].wcname+'</option>';
										i=i+1;
									}
									else{
										$wsSelect+='<option value="'+d2[y].wcid+'">'+d2[y].wcname+'</option>';
									}
								}
							}
							$wsSelect+='</select>';
							$rowCount=id;
								id=id+1;
								
										$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+$hiddenVillage+d[k].villagename+'</td><td>'+$wsSelect+'</td></tr>');
								
									}*/
								
							var projId =$('#mpdprojname').val();
							
	var wcid="";
	var plid="";
	var btnHide=false;
	var row=$rowCount;
	$('#completeWC').prop("disabled",false);
	$('#draftWCSave').prop("disabled",false);	
							$.ajax({  
            url:'watersheddraftdata',
            type: "post",  
            data: {projId:projId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            $tbody = $('#wsdtdraftbody');
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							var d = data[key];
								for ( var k in d) {
								
								   hiddenwcid= '<input type="hidden" id="wsc'+k+'" value="'+d[k].projwcid+'" />';
									$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+d[k].villagename+'</td><td>'+hiddenwcid+d[k].wcname+
													'</td><td><a href="#" >delete</a></td>');
													
									}
							
							}
						}
						
						getDataTable1(data);	
            }
            
            
            
            });
			
		}
		
		});
	/*}else{
		
	$('.error').text('You have already mapped village and watershed committee. ');
	$('#draftWCSave').prop("disabled",true);
	$('#completeWC').prop("disabled",true);*/
	var $wsdtbody = $('#wsdtbody');
		$wsdtbody.empty();
	
	
	
	 
	var projId =$('#mpdprojname').val();
							
	var wcid="";
	var plid="";
	var btnHide=false;
	var row=$rowCount;
	
							$.ajax({  
							
            url:'watershedfinaldata',
            type: "post",  
            data: {projId:projId},
            error:function(xhr,status,er){
                console.log(er);
                //alert('error');
            },
            success: function(data) {
            
            $tbody = $('#wsdtdraftbody');
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							var d = data[key];
								for ( var k in d) {
								
								   //hiddenwcid= '<input type="hidden" id="wsc'+k+'" value="'+d[k].projwcid+'" />';
									$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+d[k].villagename+'</td><td>'+d[k].wcname+
													'</td><td></td>');
												
									}
							
							}
						}
						
						getDataTable1(data);	
            }
            
            
            
            });
//	}
	}
	
	});
		
		
		});
		var table = $('#projectMappedTable').DataTable();
		var table = $('#projectMappedDraftTable').DataTable();
		/********************************************* code to get village value from block change ******************************** */
		$('#block').on("change" ,function() {
		var url= 'getVillageBlockProjWise';
		var bcode = $('#block option:selected').val();
		var projid = $('#project option:selected').val();
		$.ajax({  
            url:url,
            type: "post",  
            data: {bcode:bcode, project:projid},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
						var $village = $('#village');
						$village.empty();
        				$village.append('<option value="">--Select Village--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
							var chkBox='<input type="checkbox" id='+key+' />';
							//$village.append('<input type="checkbox" id='+key+' />');
						                       $village.append('<option value='+key+'>' +data[key] + '</option>');
//console.log($village);
						                    }
						                }
//$('#village').multiSelect();
//$('#village').selectpicker();
                }
        });
		});
		var table = $('#projectLocationTable').DataTable();
 
$('#projectLocationTable tbody').on( 'click', 'a.delete', function (e) {
	if(confirm('Do you want to delete the record ?')){
		 table.row( $(this).parents('tr') ).remove().draw();
	var del = e.target.getAttribute('data-id');
	$.ajax({  
            url:"delwatersheddraftdata",
            type: "post",  
            data: {pwccode:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
	                   successAlert('Data Deleted Successfully!');
				        $("#successok").click(function(){
						$('#popup').modal('hide');
						});
						console.log(data);
						}
							});
 	var filtered = arrayRemove(vCode, del);
	vCode=filtered;
	}
} );


	var table1 = $('#projectMappedDraftTable').DataTable();
	
	$('#projectMappedDraftTable tbody').on( 'click', 'a.delete', function (e) {
	table1.row( $(this).parents('tr') ).remove().draw();
	var del = e.target.getAttribute('data-id');
	
	$.ajax({  
            url:"delwatersheddraftdata",
            type: "post",  
            data: {pwccode:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
	                   successAlert('Data Deleted Successfully!');
				        $("#successok").click(function(){
						$('#popup').modal('hide');
						});
						console.log(data);
						}
							});
 	var filtered = arrayRemove(projId, del);
	projId=filtered;
} );
	});
	
	
	
	


function getDataTable1(data){ 
		var i="";
		var dt=[];
		var arr=[];
		var datatable = $('#projectMappedDraftTable').DataTable();
		 datatable.clear();
		for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				var d = data[key];
				for ( var k in d) {
					dt=[];
					hiddenwcid= '<input type="hidden" id="wsc'+k+'" value="'+d[k].projwcid+'" />';
					dt.push(d[k].blockname);
					dt.push(d[k].villagename);
					dt.push(d[k].wcname);
					if(d[k].status)
					dt.push(hiddenwcid);
					else
					dt.push(hiddenwcid+"");
					
					arr.push(dt);
             }
        }
}
datatable.rows.add(arr);
    datatable.draw();
	}



	function getDataTable(data){ 
		var i="";
		var dt=[];
		var arr=[];
		var datatable = $('#projectLocationTable').DataTable();
		 datatable.clear();
		for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				var d = data[key];
				for ( var k in d) {
					dt=[];
					dt.push(d[k].blockname);
					dt.push(d[k].grampanchayatname);
					dt.push(d[k].villagename);
					if(d[k].status)
					dt.push('');
					else
					dt.push("<a href='#' data-id='"+d[k].vcode+"' class='delete'>delete</a>");
					arr.push(dt);
             }
        }
}
datatable.rows.add(arr);
    datatable.draw();
	}
	var allgood=false;
	function validateFields(){
		var villageCode =""+$('#village').val();
		var projectId = $('#project option:selected').val();
		var multiblock = ""+$('#multiblock').val();
		var block = $('#block option:selected').val();
		if(projectId==='' || projectId ===null){
			$('.error').text(' Please select project first !');
			$('#project').focus();
			addInputFieldErrorCss('#project');
			allgood= false;
			return false;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#project');
			allgood= true;
		}
		if(multiblock==='' || multiblock===null ){
			$('.error').text(' Please select block first !');
			$('#multiblock').focus();
			addInputFieldErrorCss('#multiblock');
			allgood= false;
			return false;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#multiblock');
			allgood=true;
		}
		if(block==='' || block===null ){
			$('.error').text(' Please select block first !');
			$('#block').focus();
			addInputFieldErrorCss('#block');
			allgood= false;
			return false;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#block');
			allgood=true;
		}
		if(villageCode==='' || villageCode ===null){
			$('.error').text(' Please select village first !');
			$('#village').focus();
			addInputFieldErrorCss('#village');
			allgood= false;
			return false;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#village');
			allgood=true;
		}
		
	}
	var vCode=[];
	
	function done(e){
		var inputcheck=validateFields();
	
		if(allgood){
			var villageCode =""+ $('#village').val();
		var vc = villageCode.split(',');
		for(var i=0;i<vc.length;i++){
			if(vCode.indexOf(parseInt(vc[i]))>=0){}
			else
			vCode.push(parseInt(vc[i]));
		}
		var unique = getUnique(vCode);
		console.log("vc"+unique);
		vCode=unique;
		var url= 'getVillageByVillageCode';
		$.ajax({  
            url:url,
            type: "post",  
            data: {vcode:unique.toString()},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						localStorage.setItem('village',data);
						$tbody = $('#tbody');
						//$tbody.empty();
						for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							var d = data[key];
								for ( var k in d) {
									$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+d[k].grampanchayatname+'</td><td>'+d[k].villagename+
													'</td><td><a href="#" >delete</a></td>');
									}
							}
						}
						alert('Village added Successfully');
						getDataTable(data);
						$('#complete').prop("disabled",true);
                }
            
        });
		}else{
			
		}
		

		e.preventDefault();
	}
	
	
	function saveDraft(e){
		console.log(vCode+" : "+$('#project option:selected').val());
		var pcode = $('#project option:selected').val();
		var vcode=vCode;
		if(vCode.length===0 || vCode===null){
			$('.error').html('Please select atleast one village. Then try again !');
			addInputFieldErrorCss('#village');
			$('#village').focus();
			e.preventDefault();
			return;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#village');
		}
		var url="saveasdraftexisting";
		$.ajax({  
            url:url,
            type: "post",  
            data: {vCode:vcode.toString(),pcode:pcode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
					
					if(data){
						successAlert('Data saved as Draft successfully !');
					}else{
						successAlert('There is some error. Please try again !')
					}
					$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='addExistingProjectLocation';
					});
                }
        });
e.preventDefault();
	}
	
	function completeData(e){
		console.log(vCode+" : "+$('#project option:selected').val());
		var pcode = $('#project option:selected').val();
		var vcode=vCode;
		if(vCode.length===0 || vCode===null){
			$('.error').html('Please select atleast one village. Then try again !');
			addInputFieldErrorCss('#village');
			$('#village').focus();
			e.preventDefault();
			return;
		}else{
			$('.error').text('');
			removeInputFieldErrorCss('#village');
		}
		var url="completeExistingData";
		$.ajax({  
            url:url,
            type: "post",  
            data: {vCode:vcode.toString(),pcode:pcode},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
					
					if(data){
						successAlert('Project Location Assigned/ Completed successfully !');
					}else{
						successAlert('There is some error. Please try again !');
					}
					$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='addExistingProjectLocation';
					});
                }
        });
e.preventDefault();
	}
	
	function saveWCDraft(e){
	var projId =$('#mpdprojname').val();
	$projectCd = $('#mpdprojname option:selected').val();
	var wcid="";
	var plid="";
	var btnHide=false;
	var row=$rowCount;
	var final="";
	$activity = new Array();
	$plid=new Array();
	/*for(var k=1;k<=row;k++){
	//wcid=$('#wc'+k+' option:selected').val();
		
		if($('#wc'+k+' option:selected').val()==='')
		{
			alert('please select watersheed committee');
			$('#wc'+k).focus();
			return false;
		}
		else if(typeof $('#wc'+k+' option:selected').val()==='undefined')
		{
			alert('please select watersheed committee');
			$('#wc'+k).focus();
			return false;
		}			
		else
			$activity.push($('#wc'+k).val().join("$"));
		
		$plid.push($('#pl'+k).val());
	
	}*/
	for(var k=1;k<=row;k++){
		wcid=$('#wc'+k+' option:selected').val();
		plid=$('#pl'+k+'').val();
		if(final.length===0)
		final=wcid+","+plid;
		else
		final+="#"+wcid+","+plid;
		}

		var url="saveaswcdraftExisting";
		$.ajax({  
            url:url,
            type: "post", 
            data: {final:final},
        //    data: {plid:$plid.toString(),activity:$activity.toString(), project:$projectCd},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
					
					if(data){
						successAlert('Data saved as Draft successfully !');
					}else{
						successAlert('There is some error. Please try again !');
					}
					$("#successok").click(function(){
						$('#popup').modal('hide');
					$.ajax({  
            url:'watersheddraftdata',
            type: "post",  
            data: {projId:projId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            $tbody = $('#wsdtdraftbody');
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							var d = data[key];
								for ( var k in d) {
									$tbody.append('<tr><td>'+d[k].blockname+'</td><td>'+d[k].villagename+'</td><td>'+d[k].wcname+
													'</td><td><a href="#" >delete</a></td>');
									}
							
							}
						}
						getDataTable1(data);	
            }
            
            
            
            });
				
					});
                }
        });
       
e.preventDefault();
	
	}
	
	function SavecompleteWCData(e){
		
	var TableRow = $('.projectMappedTable tr').length ;
	var draftTableRow = $('.projectMappedDraftTable tr').length ;
	var row=draftTableRow-1;
	var plid="";
	var projwcid="";
	var mpdprojname=$('#mpdprojname option:selected').val();
	//var mpdprojname = $(this).parent().find('#mpdprojname').val();
	//alert('kedar'+mpdprojname);
	//alert(row);
	for(var k=0;k<row;k++){
	plid=$('#wsc'+k).val();
	projwcid+=plid+"#"
	}
	//alert(projwcid);
	/*if(TableRow!=draftTableRow)
	{
	successAlert('Please Select all the watershed Committee !');
	$("#successok").click(function(){
			$('#popup').modal('hide');
			});
	}
	else{*/
		if(confirm("Do you want to complete Data ?"))
	var url="completeWCdataExisting";
		$.ajax({  
            url:url,
            type: "post",  
            data: {projwcid:projwcid, projid:mpdprojname},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
					
					if(data){
						successAlert('Data successfully completed!');
					}else{
						successAlert('There is some error. Please try again !')
					}
					$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='addExistingProjectLocation';
					});
                }
        });
       // }
e.preventDefault();
	}
    
	
	