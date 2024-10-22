
 $(document).on('focus', '.datepicker', function(e){ 
	$(this).datepicker({
    	 changeMonth: true,
         changeYear: true,
         dateFormat: "dd-mm-yy",
         yearRange: "2000:2041"
        });

});



$( document ).ready(function(){

	$(document).on( 'change', '#groupType', function (e) {
		
		$tblFPODetails =$('#tblSHGDetails');
		$tblFPODetails.empty();
		$('#noOf').val('');
		$('#projectFPODraftTable').val('');
		$('#projectFPOFinalTable').val('');
		$('#tblFPODetails').val('');
		$('#btnSave').val('');
		
		if($('#groupType option:selected').text()==='--Select Type--'){
		$('.lblNoOf').html('No. of ');
		$('.noOf').addClass('d-none');
		$('#btnGo').addClass('d-none');
		$('#projectFPODraftTable').addClass('d-none');
		$('#projectFPOFinalTable').addClass('d-none');
		$('#tblFPODetails').addClass('d-none');
		$('#btnSave').addClass('d-none');
		}
		else{
		$('.lblNoOf').html('No. of '+$('#groupType option:selected').text()+':- ');
		$('.noOf').removeClass('d-none');
		$('#btnGo').removeClass('d-none');
		$('#projectFPODraftTable').removeClass('d-none');
		$('#projectFPOFinalTable').removeClass('d-none');
		
		var projId =$('#project').val();
        $group = $('#groupType option:selected').val();
        $.ajax({  
            url:'fpodraftdata',
            type: "post",  
            data: {projId:projId, group:$group},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {/*console.log(data);*/

            var i = 1;	        
            $tbody = $('#fpodraftbody');
$tblData="";
            $tbody.empty();
if(Object.keys(data).length>0){
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
										$tblData+='<tr><td>'+i+'</td><td>'+d[k].fpo_name+'</td><td>'+d[k].dept_org+'</td><td>'+d[k].registration_no+'</td><td>'+d[k].registration_date+'</td><td>'+d[k].no_of_members+'</td><td>'+d[k].coreactivity+'</td><td>'+d[k].turnover+
													'</td><td>'+d[k].no_of_farmer_associated+'</td>';
									i++
									}
									
									$tbody.append($tblData);
									/*$tblData+='<td><a href="#" data-id='+d[k].fpo_id+' class="delete">Delete</a></td><td><a href="#" data-id='+d[k].fpo_id+' class="complete">Complete</a></td></tr>';
									*/
									$tbody.append('<td colspan=4></td><td style="text-align:right"><a href="#" data-id='+d[k].fpo_id+' class="delete">Delete</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="#" data-id='+d[k].fpo_id+' class="complete">Complete</a></td></tr>');
							        $tbody.append('<tr><td  colspan=8></td></tr>');
							}
							
						}
						}
			 else{
						$tbody.append("<td colspan=4></td><td  style='text-align:center'>Data not found !           </td>");
						}
		}
          });
		
		
		$.ajax({  
            url:'fpofinaldata',
            type: "post",  
            data: {projId:projId, group:$group},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {/*console.log(data);*/
            var i = 1;	        
            $tbody = $('#fpofinalbody');
$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
										$tblData+='<tr><td>'+i+'</td><td>'+d[k].fpo_name+'</td><td>'+d[k].dept_org+'</td><td>'+d[k].registration_no+'</td><td>'+d[k].registration_date+'</td><td>'+d[k].no_of_members+'</td><td>'+d[k].coreactivity+'</td><td>'+d[k].turnover+
													'</td><td>'+d[k].no_of_farmer_associated+'</td>';
									i++
									}
									$tbody.append($tblData);
									$tbody.append('<tr><td  colspan=9></td></tr>');
									
							}
						
						}
			 
		}
          });

		}
		$('.button').addClass('d-none');
		});
	
	
	$('#projectFPODraftTable tbody').on( 'click', 'a.delete', function (e) {
	var del = e.target.getAttribute('data-id');
	
	confirmAlert('Are You Sure You Want to Delete This Record!');
					$("#ok").click(function(){
			$('#popup').modal('hide');
			$.ajax({  
            url:"delFPOdraftdata",
            type: "post",  
            data: {fpoid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						
						if(data==='success'){
				        alert('Data Deleted Successfully!');
                         window.location.href='';
						}
						else
						{
							alert('Issue on deleting data!');
							window.location.href='';
						}
						$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='';
					});
						}
							});
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
			
	
} );

	$('#projectFPODraftTable tbody').on( 'click', 'a.complete', function (e) {
	var del = e.target.getAttribute('data-id');
	
	confirmAlert('Are You Sure You Want to Complete This Record!');
					$("#ok").click(function(){
			$('#popup').modal('hide');
			$.ajax({  
            url:"finalSaveFPOdraftdata",
            type: "post",  
            data: {fpoid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						
						if(data==='success'){
				        alert('Data Completed Successfully!');
						window.location.href='';
						}
						else
						{
							alert('Issue on completing data!');
							window.location.href='';
						}
						$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='';
					});
						}
							});
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
			
	
} );

	
	$(document).on( 'click', '#btnGo', function (e) {
		e.preventDefault();
		
		if($('#project').val()==''){
			alert('please select Project');
			$('#project').focus();
			return false;
		}
		
		if($('#groupType option:selected').val()===''){
			alert('please select Group Type');
			$('#groupType').focus();
			return false;
		}
		if($('#noOf').val()===''){
			alert('please enter number of '+$('#groupType option:selected').text());
			$('#noOf').focus();
			return false;
		}
		
        else
$('#tblFPODetails').removeClass('d-none');
		$('#btnSave').removeClass('d-none');
        
        $.ajax({  
            url:"getDepartmentorg",
            type: "post", 
			data:{}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			//console.log(data);
			if(Object.keys(data).length>0){
				$fpoDepartmentOption='<option value="">---Select---</option>';
				for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$fpoDepartmentOption+='<option value="'+key+'">'+data[key]+'</option>';
							}
						}
				}
				
        $.ajax({  
            url:"getCoreActivity",
            type: "post",  
            error:function(xhr,status,er){
                console.log(xhr.responseText);
                alert('error'+er);
            },
        success: function(data) {
	    console.log(data);
        var a='';
        $rowCount =0;
		for ( var key in data) {
		if (data.hasOwnProperty(key)) {
		 a+= '<option value='+key+'>' +data[key]+'</option>';
		}
		
		}
		if($('#groupType option:selected').val()!='group'){
        $coretext = new Array();
		$('#coretext').val('');
		$('.button').removeClass('d-none');
		$noOf = $('#noOf').val();
		$tblFPODetails =$('#tblFPODetails');
		$tblSHGTBody ="<tbody>";
		$tblSHGTHead ='<thead ><tr><th class="text-center" rowspan="1">S.No.</th><th rowspan="2" class="text-center">Name of FPO</th><th class="text-center" rowspan="3" style="width: 350px;">Department/ Organisation/ Scheme</th><th rowspan="2" class="text-center">Registration No.</th><th rowspan="1" class="text-center">Date of Registration</th><th rowspan="2" class="text-center">No. of members of FPO</th><th class="text-center" rowspan="3" style="width: 350px;">Core Activity</th><th class="text-center" rowspan="2" style="width: 150px;">Avg. turnover of FPO(in rs.)</th><th class="text-center" rowspan="2" style="width: 150px;">No of Farmer associated with FPO</th></tr></thead>';
		for($i=1;$i<=$noOf;$i++){
		$tblSHGTBody +='<tr><td>'+$i+'</td><td><input class="col-11" style="width: auto;" type="text" id="nameoffpo'+$i+'" name="nameoffpo" class="form-control input"></td><td><select id="dept_org'+$i+'"  class="form-control">'+$fpoDepartmentOption+'</select></td><td><input class="col-lg-11" style="width: 150px;" type="text" id="regno'+$i+'" name="regno" class="form-control input"></td><td><input type="text"  id="datepicker'+$i+'" name="datepicker" class="datepicker" style="width: 120px;"></td><td><input type="text" class="col-11" style="width: 150px;" id="noofmembers'+$i+'" name="noofmembers" class="form-control input" onmousedown="numericOnly(event)"></td><td><select id="coreactivity'+$i+'"  name="multicheckbox" class="form-control" multiple >'+a+'</select></td><td class="halfwidth"><input type="text" id="avgturnover'+$i+'" name="avgturnover" class="col-11" style="width: 140px;" onmousedown="decimalCheck(event)"></td><td class="halfwidth"><input type="text" id="farmasso'+$i+'" name="farmasso" class="col-11" style="width: 140px;" onmousedown="numericOnly(event)"></td></tr>';
		}
		}
		else{
		$tblSHGTHead ='<thead ><tr><th class="text-center" rowspan="1">S.No.</th><th rowspan="1" class="text-center">Name of FPO</th><th class="text-center" rowspan="3" style="width: 350px;">Department /Organisation /Scheme</th><th rowspan="1" class="text-center">Registration No.</th><th rowspan="1" class="text-center">Date of Registration</th><th rowspan="2" class="text-center">No. of members of FPO</th><th class="text-center" rowspan="3" style="width: 350px;">Core Activity</th><th class="text-center" rowspan="2" style="width: 150px;">Avg. turnover of FPO(in rs.)</th><th class="text-center" rowspan="2" style="width: 150px;">No of Farmer associated with FPO</th></tr></thead>';
		for($i=1;$i<=$noOf;$i++){
		$tblSHGTBody +='<tr><td>'+$i+'</td><td><input type="text" id="nameoffpo'+$i+'" name="nameoffpo" class="form-control input"></td><td><select id="dept_org'+$i+'"  class="form-control">'+$fpoDepartmentOption+'</select></td><td><input type="text" id="regno'+$i+'" name="regno" class="form-control input"></td><td><input type="text"  id="datepicker'+$i+'" name="datepicker" class="datepicker"></td><td><input type="text" id="noofmembers'+$i+'" name="noofmembers" class="form-control input" onmousedown="numericOnly(event)"></td><td><select id="coreactivity'+$i+'"  name="multicheckbox" class="form-control" multiple >'+a+'</select></td><td class="halfwidth"><input type="text" id="avgturnover'+$i+'" name="avgturnover" class="form-control input" onmousedown="decimalCheck(event)"></td><td class="halfwidth"><input type="text" id="farmasso'+$i+'" name="farmasso" class="form-control input" onmousedown="numericOnly(event)"></td></tr>';
		}
		}
		
		
		$tblSHGTBody +="</tbody>";
		$tblFPODetails.html($tblSHGTHead+$tblSHGTBody);
		$('select[multiple]').multiselect({
    columns: 1,
    placeholder: 'Select Options'
    
});
		}
		
 	   });
 	   
 	   }
 	   });
		});
		
		
		$(document).on( 'click', '#btnCancel', function (e) {
		e.preventDefault();
		});
			
			
	$(document).on( 'click', '#btnSave', function (e) {
	e.preventDefault();
	 $activity=new Array();
     $fponame = new Array();
     $regnum = new Array();
     $regdate = new Array();
     $noofmem = new Array();
     $fpoavg = new Array();
     $nooffarm = new Array();
     $deptorg = new Array();
     var projId =$('#project').val();
     $group = $('#groupType option:selected').val();
     $nofpo = $('#noOf').val();
     
	$rowCount =$('select[multiple]'). length;
	var row=$rowCount;
	for(var k=1;k<=row;k++){
		
		if($('#nameoffpo'+k).val()===''){
			alert('please enter name of FPO!');
			$('#nameoffpo'+k).focus();
			return false;
			}
		
		if($('#dept_org'+k).val()===''){
			alert('please enter Department /Organisation / Scheme!');
			$('#dept_org'+k).focus();
			return false;
			}	
			
		if($('#regno'+k).val()===''){
			alert('please enter Registration No.!');
			$('#regno'+k).focus();
			return false;
			}	
		
		if($('#datepicker'+k).val()===''){
			alert('please enter Date of Registration!');
			$('#datepicker'+k).focus();
			return false;
			}
			
		if($('#noofmembers'+k).val()===''){
			alert('please enter No. of members of FPO!');
			$('#noofmembers'+k).focus();
			return false;
			}	
			
		if($('#coreactivity'+k).val()===''){
			alert('please select atleast one Core Activity!');
			$('#coreactivity'+k).focus();
			return false;
			}
		
		if($('#avgturnover'+k).val()===''){
			alert('please enter Average turnover of FPO!');
			$('#avgturnover'+k).focus();
			return false;
			}
			
		if($('#farmasso'+k).val()===''){
			alert('please enter No of Farmer associated with FPO!');
			$('#farmasso'+k).focus();
			return false;
			}
					
		$fponame.push($('#nameoffpo'+k).val());
		$deptorg.push($('#dept_org'+k).val());
		$regnum.push($('#regno'+k).val());
		$regdate.push($('#datepicker'+k).val());
		$noofmem.push($('#noofmembers'+k).val());
		$activity.push($('#coreactivity'+k).val().join("#"));
		$fpoavg.push($('#avgturnover'+k).val());
		$nooffarm.push($('#farmasso'+k).val());	
		
	}
	$.ajax({  
            url:"saveFPOdata",
            type: "post", 
			data:{projId:projId,group:$group,no:$nofpo,name:$fponame.toString(), deptorg:$deptorg.toString(), regno:$regnum.toString(), regdt:$regdate.toString(), noofmembers:$noofmem.toString(), activity:$activity.toString(), avg:$fpoavg.toString(), farm:$nooffarm.toString()}, 
            error:function(xhr,status,er){
                console.log(er);
            },

		     success:function(data) {//alert(data);

$('.form-inline')[0].reset();
$('.noOf').addClass('d-none');
		$('#btnGo').addClass('d-none');
		$('#tblFPODetails').addClass('d-none');
		//$('#btnCancel').addClass('d-none');
		$('#btnSave').addClass('d-none');
			if(data==='success'){
				
				if($group==='newFPO')
				{
					successAlert('Newly Created FPO data inserted Successfully!');
					$("#successok").click(function()
					 {
			        $('#popup').modal('hide');
                    $.ajax({  
            url:'fpodraftdata',
            type: "post",  
            data: {projId:projId, group:$group},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {/*console.log(data);*/
            var i = 1;	        
            $tbody = $('#fpodraftbody');
$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
										$tblData+='<tr><td>'+i+'</td><td>'+d[k].fpo_name+'</td><td>'+d[k].dept_org+'</td><td>'+d[k].registration_no+'</td><td>'+d[k].registration_date+'</td><td>'+d[k].no_of_members+'</td><td>'+d[k].coreactivity+'</td><td>'+d[k].turnover+
													'</td><td>'+d[k].no_of_farmer_associated+'</td>';
									i++
									}
									
									$tbody.append($tblData);
									/*$tblData+='<td><a href="#" data-id='+d[k].fpo_id+' class="delete">Delete</a></td><td><a href="#" data-id='+d[k].fpo_id+' class="complete">Complete</a></td></tr>';
									*/
									$tbody.append('<td colspan=4></td><td style="text-align:right"><a href="#" data-id='+d[k].fpo_id+' class="delete">Delete</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="#" data-id='+d[k].fpo_id+' class="complete">Complete</a></td></tr>');
							        $tbody.append('<tr><td  colspan=8></td></tr>');
							}
							
						}
		}
          });
			});
					
				}
				if($group==='oldFPO')
				{
					successAlert('Existing FPO data inserted Successfully!');
					$("#successok").click(function()
					 {
			        $('#popup').modal('hide');
                    $.ajax({  
            url:'fpodraftdata',
            type: "post",  
            data: {projId:projId, group:$group},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {/*console.log(data);*/
            var i = 1;	        
            $tbody = $('#fpodraftbody');
$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
										$tblData+='<tr><td>'+i+'</td><td>'+d[k].fpo_name+'</td><td>'+d[k].dept_org+'</td><td>'+d[k].registration_no+'</td><td>'+d[k].registration_date+'</td><td>'+d[k].no_of_members+'</td><td>'+d[k].coreactivity+'</td><td>'+d[k].turnover+
													'</td><td>'+d[k].no_of_farmer_associated+'</td>';
									i++
									}
									
									$tbody.append($tblData);
									/*$tblData+='<td><a href="#" data-id='+d[k].fpo_id+' class="delete">Delete</a></td><td><a href="#" data-id='+d[k].fpo_id+' class="complete">Complete</a></td></tr>';
									*/
									$tbody.append('<td colspan=4></td><td style="text-align:right"><a href="#" data-id='+d[k].fpo_id+' class="delete">Delete</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="#" data-id='+d[k].fpo_id+' class="complete">Complete</a></td></tr>');
							        $tbody.append('<tr><td  colspan=8></td></tr>');
							}
							
						}
		}
          });
			});
					
				}
				}
			else
			alert('Error on inserting data!');
			return false;
	}		
});

});
});



		
		