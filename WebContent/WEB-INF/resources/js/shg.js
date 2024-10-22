/**
 * 
 */


$(document).on('focus', '.datepicker', function(e){ 
	$(this).datepicker({
    	 changeMonth: true,
         changeYear: true,
         dateFormat: "dd-mm-yy",
         yearRange: "2000:2041"
        });

});

$( document ).ready(function(){
	
	/******************************** Group Type Change Script ************************************ */
	$(document).on( 'change', '#groupType', function (e) {
		e.preventDefault();
		$tblSHGDetails =$('#tblSHGDetails');
		$tblSHGDetails.empty();
		$('#noOf').val('');
		if($('#groupType option:selected').text()==='--Select Type--'){
		$('.lblNoOf').html('No. of ');
		$('.noOf').addClass('d-none');
		$('#btnGo').addClass('d-none');
		}
		else{
		$('.lblNoOf').html('No. of '+$('#groupType option:selected').text()+':- ');
		$('.noOf').removeClass('d-none');
		$('#btnGo').removeClass('d-none');
		}
		$('.button').addClass('d-none');
		$projectId = $('#project').val();
		$gType = $(this).val();
		$tblSHGTHead ='';
		$.ajax({  
            url:"getSHGDraftData",
            type: "post", 
			data:{project:$projectId,group:$gType}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			$i=1;
			$tblSHGDetails =$('#tblSHGDetailsFinal');
			$tblSHGTBody ="<tbody id='tbodySHGDetailsFinal'>";
			if(Object.keys(data).length>0){
				$ddlOption=null;
				for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							if($('#groupType option:selected').val()!='group'){
		$tblSHGTHead ='<thead ><tr><th class="text-center" rowspan="2">S.No.</th><th rowspan="2" class="text-center">Name of SHG</th><th rowspan="2" class="text-center">Department/ Scheme</th><th rowspan="2" class="text-center">Date of registration</th><th rowspan="2" class="text-center">Amount of Revolving Fund (in Rs.)</th><th class="text-center" colspan="5">Total Members</th><th class="text-center" rowspan="2">Core Activity</th><th class="text-center" rowspan="2">No. of SHG members having PM Bima Yojana</th><th class="text-center" rowspan="2">Thrift & Credit (Y/N)</th><th class="text-center" rowspan="2">Avg. turnover of SHG</th><th class="text-center" rowspan="2">Avg. Income Per Annum</th><th class="text-center" rowspan="2">Whether Fedrated</th><th class="text-center" rowspan="2" colspan="2">Action</th></tr>'+
		'<tr><th class="text-center" style="width: 100px; height :38px">SC</th><th class="text-center" style="width: 100px; height :38px">ST</th><th class="text-center" style="width: 100px; height :38px">Others</th><th class="text-center">Total</th><th class="text-center" style="width: 100px; height :38px">Women(Out of Total)</th></tr></thead>';
		}else{
		$tblSHGTHead ='<thead class="text-center"><tr><th class="text-center" rowspan="2">S.No.</th><th class="text-center" rowspan="2">Name of User Groups</th><th class="text-center" colspan="5">Total Members</th><th class="text-center" rowspan="2" colspan="2">Action</th></tr>'+
		'<tr><th class="text-center" >SC</th><th class="text-center">ST</th><th class="text-center">Others</th><th class="text-center">Total</th><th class="text-center">Women(Out of Total)</th></tr></thead>';
		}
							$rowspan=0;
							$span=0;
							var d=data[key];
							for(var k in d){
								$span++;
								}
							for(var k in d){
								if (d.hasOwnProperty(k)) {
									$fedrated ='';
									$theftCredited ="";
									if(d[k].fedrated)
									$fedrated ='Yes';
									else if(!d[k].fedrated)
									$fedrated ='No';
									$theftCredited = d[k].thriftCredit===true?'Yes':'No';
									$turnover = d[k].avg_turnover>0?d[k].avg_turnover:'';
									$avgincome = d[k].avg_income>0?d[k].avg_income:'';
									if($rowspan===0){
									if($('#groupType option:selected').val()==='group'){
										$tblSHGTBody +='<tr id="'+$i+'"><td>'+$i+'</td><td>'+d[k].name+'</td><td>'+d[k].sc+'</td><td>'+d[k].st+'</td><td>'+d[k].others+'</td><td>'+d[k].total+'</td><td>'+d[k].women+'</td><td rowspan="'+$span+'"><a href="#" data-id="'+d[k].shg_id+','+$span+'" class="delete">Delete</a></td><td rowspan="'+$span+'"><a href="#" data-id="'+d[k].shg_id+','+$span+'" class="complete">Complete</a></td></tr>';
										}else{
								$tblSHGTBody +='<tr id="'+$i+'"><td>'+$i+'</td><td>'+d[k].name+'</td><td>'+d[k].department_name+'</td><td>'+d[k].regdate+'</td><td>'+d[k].revolvingAmount+'</td><td>'+d[k].sc+'</td><td>'+d[k].st+'</td><td>'+d[k].others+'</td><td>'+d[k].total+'</td><td>'+d[k].women+'</td><td>'+d[k].shg_coreactivity_desc+'</td><td>'+d[k].pm_bima_yojna+'</td><td>'+$theftCredited+'</td><td>'+$turnover+'</td><td>'+$avgincome+'</td><td>'+$fedrated+'</td><td rowspan="'+$span+'"><a href="#" data-id="'+d[k].shg_id+','+$span+'" class="delete">Delete</a></td><td rowspan="'+$span+'"><a href="#" data-id="'+d[k].shg_id+','+$span+'" class="complete">Complete</a></td></tr>';
								}
								}else{
									if($('#groupType option:selected').val()==='group'){
										$tblSHGTBody +='<tr id="'+$i+'"><td>'+$i+'</td><td>'+d[k].name+'</td><td>'+d[k].sc+'</td><td>'+d[k].st+'</td><td>'+d[k].others+'</td><td>'+d[k].total+'</td><td>'+d[k].women+'</td></tr>';
									}else{	
								$tblSHGTBody +='<tr id="'+$i+'"><td>'+$i+'</td><td>'+d[k].name+'</td><td>'+d[k].department_name+'</td><td>'+d[k].regdate+'</td><td>'+d[k].revolvingAmount+'</td><td>'+d[k].sc+'</td><td>'+d[k].st+'</td><td>'+d[k].others+'</td><td>'+d[k].total+'</td><td>'+d[k].women+'</td><td>'+d[k].shg_coreactivity_desc+'</td><td>'+d[k].pm_bima_yojna+'</td><td>'+$theftCredited+'</td><td>'+$turnover+'</td><td>'+$avgincome+'</td><td>'+$fedrated+'</td></tr>';
							}
							}
							$i++;
							$rowspan++;
							}
							}
							$tblSHGTBody +='<tr><td colspan="14"></td></tr>';
							}
						}
				}
				
		$tblSHGTBody +="</tbody>";
		$tblSHGDetails.html($tblSHGTHead+$tblSHGTBody);
		
			}
			
			});
		
		});
		
		
		/************************************************* Button Go Click Script ******************************** */
		$ddlCoreActivityOption="";
		$ddlDepartmentOption ="";
		$(document).on( 'click', '#btnGo', function (e) {
		e.preventDefault();
		if($('#groupType option:selected').val()===''){
			alert('please select Group Type');
			$('#groupType').focus();
			return false;
		}
		if($('#project option:selected').val()===''){
			alert('please select Project');
			$('#project').focus();
			return false;
		}
		if($('#noOf').val()===''){
			alert('please enter number of '+$('#groupType option:selected').text());
			$('#noOf').focus();
			return false;
		}
		$coretext = new Array();
		$('#coretext').val('');
		$('.button').removeClass('d-none');
		$noOf = $('#noOf').val();
		
		$.ajax({  
            url:"getSHGDepartment",
            type: "post", 
			data:{}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			//console.log(data);
			if(Object.keys(data).length>0){
				$ddlDepartmentOption='<option value="">---Select---</option>';
				for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlDepartmentOption+='<option value="'+key+'">'+data[key]+'</option>';
							}
						}
				}
				
				$.ajax({  
            url:"getSHGCoreActivity",
            type: "post", 
			data:{}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {
			$tblSHGDetails =$('#tblSHGDetails');
			$tblSHGTBody ="<tbody id='tbodySHGDetails'>";
			if(Object.keys(data).length>0){console.log(data);
				$ddlCoreActivityOption=null;
				for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlCoreActivityOption+='<option value="'+data[key]+'">'+key+'</option>';
							}
						}
				}
				if($('#groupType option:selected').val()!='group'){
		$tblSHGTHead ='<thead ><tr><th class="text-center" rowspan="2">S.No.</th><th rowspan="2" class="text-center">Name of SHG</th><th rowspan="2" class="text-center">Department/ Scheme</th><th rowspan="2" class="text-center">Date of registration</th><th rowspan="2" class="text-center">Amount of Revolving Fund (in Rs.)</th><th class="text-center" colspan="5">Total Members</th><th class="text-center" rowspan="2">Core Activity</th><th class="text-center" rowspan="2">No. of SHG members having PM Bima Yojana</th><th class="text-center" rowspan="2">Thrift & Credit (Y/N)</th><th class="text-center avgturnover" rowspan="2">Avg. turnover of SHG</th><th class="text-center avgincome" rowspan="2">Avg. Income Per Annum</th><th class="text-center fedrated" rowspan="2">Whether Fedrated</th></tr>'+
		'<tr><th class="text-center">SC</th><th class="text-center">ST</th><th class="text-center">Others</th><th class="text-center">Total</th><th class="text-center">Women(Out of Total)</th></tr></thead>';
		for($i=1;$i<=$noOf;$i++){
		$tblSHGTBody +='<tr><td>'+$i+'</td><td><input type="text" id="nameofshg'+$i+'" name="nameofshg'+$i+'" class="form-control input"></td><td><select id="department'+$i+'" name="department'+$i+'" class="form-control">'+$ddlDepartmentOption+'</select></td><td><input type="text" id="regdate'+$i+'" name="regdate'+$i+'" class="form-control datepicker"></td><td><input type="text" id="revolve_amount'+$i+'" name="revolve_amount'+$i+'" class="form-control input"></td><td class="halfwidth"><input type="text" id="tmsc'+$i+'"  name="tmsc'+$i+'" class="add" style="width: 100px; height :38px" onblur="getTotal(tmsc'+$i+')" onmousedown="numericOnly(event)"></td><td class="halfwidth"><input type="text" id="tmst'+$i+'" name="tmst'+$i+'" class="add" style="width: 100px; height :38px" onblur="getTotal(tmst'+$i+')" onmousedown="numericOnly(event)"></td><td class="halfwidth"><input type="text" id="tmothers'+$i+'" name="tmothers'+$i+'" class="add" style="width: 100px; height :38px" onblur="getTotal(tmothers'+$i+')" onmousedown="numericOnly(event)"></td><td class="halfwidth"><label id="tmt'+$i+'" name="tmt'+$i+'" style="width: 100px; height :38px" class="form-control-plaintext input"></label></td><td class="halfwidth"><input type="text" id="tmwomen'+$i+'" name="tmwomen'+$i+'" style="width: 100px; height :38px" class="add" onblur="checkwomenvalue(tmwomen'+$i+')" onmousedown="numericOnly(event)"></td><td><select id="coreactivity'+$i+'" name="coreactivity" class="form-control" multiple >'+$ddlCoreActivityOption+'</select></td><td class="col-1"><input type="text" id="pmbimayojna'+$i+'" name="pmbimayojna'+$i+'" style="width: 100px; height :38px" class="add" onmousedown="decimalCheck(event)"></td><td class="halfwidth"><select id="threft_credit'+$i+'" name="threft_credit'+$i+'" class="form-control" style="width: 100px; height :38px" onchange="threftCreditChange(this)"><option value="">--Select--</option><option value="true">Yes</option><option value="false">No</option></select></td><td style="width: 100px; height :38px" class="col-1 avgturnover'+$i+'"><input style="width: 100px; height :38px" type="text" id="avgturnover'+$i+'" name="avgturnover'+$i+'" class="form-control input" onmousedown="decimalCheck(event)"></td><td class="col-1 avgincome'+$i+'"><input type="text" id="avgincome'+$i+'" name="avgincome'+$i+'" class="add" style="width: 100px; height :38px" onmousedown="decimalCheck(event)"></td><td class="halfwidth"><select id="fedrated'+$i+'" name="fedrated'+$i+'" class="form-control fedrated"><option value="">--Select--</option><option value="y">Yes</option><option value="n">No</option></select></td></tr>';
		}
		}else{
		$tblSHGTHead ='<thead class="text-center" ><tr><th class="text-center" rowspan="2">S.No.</th><th class="text-center" rowspan="2">Name of User Groups</th><th class="text-center" colspan="5">Total Members</th></tr>'+
		'<tr><th class="text-center">SC</th><th class="text-center" >ST</th><th class="text-center">Others</th><th class="text-center">Total</th><th class="text-center">Women(Out of Total)</th></tr></thead>';
		for($i=1;$i<=$noOf;$i++){
		$tblSHGTBody +='<tr><td>'+$i+'</td><td><input type="text" id="nameofug'+$i+'" name="nameofug'+$i+'" class="form-control input"></td><td class="halfwidth"><input type="text" id="tmsc'+$i+'" name="tmsc'+$i+'" class="form-control input" onblur="getTotal(tmsc'+$i+')" onmousedown="numericOnly(event)"></td><td class="halfwidth"><input type="text" id="tmst'+$i+'" name="tmst'+$i+'" class="form-control input" onblur="getTotal(tmst'+$i+')" onmousedown="numericOnly(event)"></td><td class="halfwidth"><input type="text" id="tmothers'+$i+'" name="tmothers'+$i+'" class="form-control input" onblur="getTotal(tmothers'+$i+')" onmousedown="numericOnly(event)"></td><td class="halfwidth"><label id="tmt'+$i+'" name="tmt" class="form-control-plaintext input"></label></td><td class="halfwidth"><input type="text" id="tmwomen'+$i+'" name="tmwomen'+$i+'" class="form-control input" onblur="checkwomenvalue(tmwomen'+$i+')" onmousedown="numericOnly(event)"></td></tr>';
		}
		}
		$tblSHGTBody +="</tbody>";
		$tblSHGDetails.html($tblSHGTHead+$tblSHGTBody);
		$('select[multiple]').multiselect({
    		columns: 1,
   			placeholder: 'Select Options'
		});			
		}
			});
				
			//console.log($ddlDepartmentOption+'ddlDepartmentOption');
			}
			
		});
		
		
		
		});
		
		$(document).on( 'click', '#btnCancel', function (e) {
		e.preventDefault();
		$('#tblSHGDetails').empty();
		$('.button').addClass('d-none');
		$('.noOf').addClass('d-none');
		$('#btnGo').addClass('d-none');
		$('#groupType').val('');
		$('#project').val('');
		
		});
		
		
		
		/************************** Save Button Click ******************************** */
		
		$(document).on( 'click', '#btnSave', function (e) {
		e.preventDefault();
		$projectCd = $('#project option:selected').val();
		$group = $('#groupType option:selected').val();
		$noshg = $('#noOf').val();
		$shgname = new Array();
		$nameofug = new Array();
		$totalsc = new Array();
		$totalst = new Array();
		$totalothers = new Array();
		$totalwomen = new Array();
		$activity = new Array();
		$avgturnover = new Array();
		$avgincome = new Array();
		$pmbima = new Array();
		$fedrated = new Array();
		$finalactivity="";
		$department = new Array();
		$regdate = new Array();
		$revolve_amount = new Array();
		$threft_credit = new Array();
		
		
		for($i =1; $i<=$noshg; $i++){
			
			if($('#nameofshg'+$i).val()===''){
			alert('please enter name of shg');
			$('#nameofshg'+$i).focus();
			return false;
			}
			if($('#tmsc'+$i).val()===''){
			alert('please enter sc count');
			$('#tmsc'+$i).focus();
			return false;
			}
			if($('#tmst'+$i).val()===''){
			alert('please enter st count');
			$('#tmst'+$i).focus();
			return false;
			}
			if($('#tmothers'+$i).val()===''){
			alert('please enter others count');
			$('#tmothers'+$i).focus();
			return false;
			}
			if($('#tmwomen'+$i).val()===''){
			alert('please enter women count');
			$('#tmwomen'+$i).focus();
			return false;
			}
			
			if($('#coreactivity'+$i+' option:selected').val()===''){
			alert('please select core activity');
			$('#coreactivity'+$i).focus();
			return false;
			}else if(typeof $('#coreactivity'+$i+' option:selected').val()==='undefined'){}			
			else
			$activity.push($('#coreactivity'+$i).val().join("#"));
			console.log('a'+$('#avgturnover'+$i).val()+'b');
			if($('#avgturnover'+$i).val()==='' && $('#avgturnover'+$i).val()===null){
			alert('please enter avg turnover111');
			$('#avgturnover'+$i).focus();
			return false;
			}else if(typeof $('#avgturnover'+$i).val()==='undefined'){}			
			else
			$avgturnover.push($('#avgturnover'+$i).val());
			
			if($('#avgincome'+$i).val()==='' && $('#avgincome'+$i).val()===null){
			alert('please enter avg income');
			$('#avgincome'+$i).focus();
			return false;
			}else  if(typeof $('#avgincome'+$i).val()==='undefined'){}			
			else
			$avgincome.push($('#avgincome'+$i).val());
			
			if($('#pmbimayojna'+$i).val()===''){
			alert('please enter pm bima yojna');
			$('#pmbimayojna'+$i).focus();
			return false;
			}else  if(typeof $('#pmbimayojna'+$i).val()==='undefined'){}			
			else
			$pmbima.push($('#pmbimayojna'+$i).val());
			
			if($('#fedrated'+$i+' option:selected').val()==='' && $('#fedrated'+$i).val()===null){
			alert('please select fedrated');
			$('#fedrated'+$i).focus();
			return false;
			}else  if(typeof $('#fedrated'+$i).val()==='undefined'){}			
			else
			$fedrated.push($('#fedrated'+$i).val());
			
			if(typeof $('#nameofshg'+$i).val() != 'undefined')
			$shgname.push($('#nameofshg'+$i).val());
			else
			$nameofug.push($('#nameofug'+$i).val());
			$totalsc.push($('#tmsc'+$i).val());
			$totalst.push($('#tmst'+$i).val());
			$totalothers.push($('#tmothers'+$i).val());
			$totalwomen.push($('#tmwomen'+$i).val());
			
			if($('#department'+$i+' option:selected').val()===''){
			alert('please select department');
			$('#department'+$i).focus();
			return false;
			}else  if(typeof $('#department'+$i+' option:selected').val()==='undefined'){}			
			else
			$department.push($('#department'+$i+' option:selected').val());
			
			if($('#regdate'+$i).val()===''){
			alert('please enter date of registration');
			$('#regdate'+$i).focus();
			return false;
			}else  if(typeof $('#regdate'+$i).val()==='undefined'){}			
			else
			$regdate.push($('#regdate'+$i).val());
			
			if($('#revolve_amount'+$i).val()===''){
			alert('please enter revolve amount');
			$('#revolve_amount'+$i).focus();
			return false;
			}else  if(typeof $('#revolve_amount'+$i).val()==='undefined'){}			
			else
			$revolve_amount.push($('#revolve_amount'+$i).val());
			
			if($('#threft_credit'+$i+' option:selected').val()===''){
			alert('please select thrift & credit');
			$('#threft_credit'+$i).focus();
			return false;
			}else  if(typeof $('#threft_credit'+$i+' option:selected').val()==='undefined'){}			
			else
			$threft_credit.push($('#threft_credit'+$i+' option:selected').val());
			
			
		}
		//alert($shgname+" : "+$totalsc+" : "+$totalst+" : "+$totalothers+" : "+$totalwomen+" : "+$activity+" : "+$avgturnover+" : "+$avgincome+" : "+$pmbima+" : "+$fedrated+" : "+$department+" : "+$regdate+" : "+$revolve_amount+" : "+$threft_credit);
		$.ajax({  
            url:"saveDraftSHG",
            type: "post", 
			data:{project:$projectCd,group:$group,no:$noshg,name:$shgname+$nameofug.toString(),sc:$totalsc.toString(),st:$totalst.toString(),others:$totalothers.toString(),women:$totalwomen.toString(),activity:$activity.toString(),turnover:$avgturnover.toString(),income:$avgincome.toString(),pmbima:$pmbima.toString(),fedrated:$fedrated.toString(),department:$department.toString(),regdate:$regdate.toString(),revolve_amount:$revolve_amount.toString(),threft_credit:$threft_credit.toString()}, 
            error:function(xhr,status,er){
                console.log(er);
            },
		     success:function(data) {//alert(data);
			if(data==='success'){
				if($group==='newSHG'){
				alert('New SHG data has been added successfully !');
				window.location.href='newshg';
				}
				if($group==='oldSHG'){
				alert('Existing SHG data has been added successfully !');
				window.location.href='eshg';
				}
				if($group==='group'){
				alert('User Group data has been added successfully !');
				window.location.href='ug';
				}
			}else
			alert('There is some problem while creating/updating SHG ! Please try again');
			}
			
			});
		});
		
		/********************************************* End ******************************************* */
		/********************************************* Delete Hover ********************************* */
		$(document).on( 'mouseover', 'a.delete', function (e) {
			$del = e.target.getAttribute('data-id');
			//alert($del);
			$del=$del.split(",");
			$del = $del[1];
			
			var trid = $(this).closest('tr').attr('id');
			
			for($j=trid;$j<=((parseInt($del)+parseInt(trid))-1);$j++){//alert($j);
				$('#'+$j).css("background-color", "#b97f8f");
			}
		});
		
		$(document).on( 'mouseout', 'a.delete', function (e) {
			$del = e.target.getAttribute('data-id');
			//alert($del);
			$del=$del.split(",");
			$del = $del[1];
			
			var trid = $(this).closest('tr').attr('id');
						
			for($j=trid;$j<=((parseInt($del)+parseInt(trid))-1);$j++){//alert($j);
				$('#'+$j).css("background-color", "#adcdd2");
			}
		});	
			
		/*********************************************** Delete Hover End ************************************** */
		
		
		/********************************************* Delete Link Click ********************************* */
		$(document).on( 'click', 'a.delete', function (e) {
			$del1 = e.target.getAttribute('data-id');
			//alert($del);
			$del=$del1.split(",");
			$del2 = $del[0];
			//alert($del2);
			$group = $('#groupType option:selected').val();
			confirmAlert('Do you want to delete the record ?');
		$("#ok").click(function(){
		$.ajax({  
            url:"deleteSHG",
            type: "post", 
			data:{shgid:$del2,group:$group}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				if(data==='success'){
					successAlert('Data Deleted Successfully');
					$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='';
					});  
					$(".close").click(function(){
						$('#popup').modal('hide');
						window.location.href='';
					});
			$('#groupType').trigger("change");
		}else{
		successAlert('Data Not Deleted.');
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
			
		/********************************************* Delete Link Click End ********************************* */
		
		/********************************************* Complete Hover ********************************* */
		$(document).on( 'mouseover', 'a.complete', function (e) {
			$del = e.target.getAttribute('data-id');
			//alert($del);
			$del1=$del.split(",");
			$del2 = $del1[1];
			
			var trid = $(this).closest('tr').attr('id');
			
			for($j=trid;$j<=((parseInt($del)+parseInt(trid))-1);$j++){//alert($j);
				$('#'+$j).css("background-color", "rgb(156 203 159)");
			}
		});
		
		$(document).on( 'mouseout', 'a.complete', function (e) {
			$del = e.target.getAttribute('data-id');
			//alert($del);
			$del1=$del.split(",");
			$del2 = $del1[1];
			
			var trid = $(this).closest('tr').attr('id');
						
			for($j=trid;$j<=((parseInt($del2)+parseInt(trid))-1);$j++){//alert($j);
				$('#'+$j).css("background-color", "#adcdd2");
			}
		});	
			
		/*********************************************** Complete Hover End ************************************** */
		
		/********************************************* Complete Link Click ********************************* */
		
		$(document).on( 'click', 'a.complete', function (e) {
			$shgid = e.target.getAttribute('data-id');
			$shgid1=$shgid.split(",");
			$shgid2 = $shgid1[0];
			confirmAlert('Do you want to Complete the record ?');
		$("#ok").click(function(){
		$.ajax({  
            url:"completeSHG",
            type: "post", 
			data:{shgid:$shgid2}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				if(data==='success'){
					successAlert('Data Completed Successfully.');
						$("#successok").click(function(){
							$('#popup').modal('hide');
						});  
						$(".close").click(function(){
							$('#popup').modal('hide');
						});
						$('#groupType').trigger("change");
		}else{
					successAlert('Data Not Completed.');
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
		
		/********************************************* Complete Link Click End********************************* */
	
	});
	
	
	/************************************* Page Load Script Ends **************************************** */
	$id="";
	$coretext = new Array();
		function activityClick(e,val){
		e.preventDefault();
		
		if(val.id===$id){}else{
			$coretext = new Array();
			$id=val.id;
		}
		//alert($(e.target).text()+" : "+$coretext);
		if($(e.target).text()==='---Select---'){
			
		}else
		if($((('#'+val.id)+' option:selected').toString()).length===1){
			$coretext = new Array();
			$coretext.push($(e.target).text());
		}else
		if($.inArray($(e.target).text(), $coretext) === -1 && $((('#'+val.id)+' option:selected').toString()).length>1){
		$coretext.push($(e.target).text());
		}else if($.inArray($(e.target).text(), $coretext) >= 0){
		$coretext=arrayRemove($coretext,$(e.target).text());///remove array issue
		}
		//alert($.inArray($(e.target).text(), $coretext));
		$('#'+val.id+'text').html($coretext.join("#"));	
		}
	
	
	
	function getTotal(tag){
		$index = tag.id.replace( /[^\d.]/g, '' ).trim();
		console.log( $('#tmsc'+$index).val()+" : "+ $('#tmst'+$index).val()+" : "+$('#tmothers'+$index).val()+" : "+$index);
		if(typeof $('#tmsc'+$index).val()==='undefined' || $('#tmsc'+$index).val()==='')
			$tmsc=0;
		else
			$tmsc=$('#tmsc'+$index).val();
			
		if(typeof $('#tmst'+$index).val()==='undefined' || $('#tmst'+$index).val()==='')
			$tmst=0;
		else
			$tmst=$('#tmst'+$index).val();
			
		if(typeof $('#tmothers'+$index).val()==='undefined'|| $('#tmothers'+$index).val()==='')
			$tmothers=0;
		else
			$tmothers=$('#tmothers'+$index).val();
		
		$('#tmt'+$index).html(parseInt($tmsc)+parseInt($tmst)+parseInt($tmothers));
	}
	
	function checkwomenvalue(tag){
		$index = tag.id.replace( /[^\d.]/g, '' ).trim();
		//console.log(tag.value+":::"+$('#tmt'+$index).html());
		if(parseInt(tag.value)>parseInt($('#tmt'+$index).html())){
		$('.errormessage').html('no of women can not be greater than total');
		tag.focus();
		return false;
		}else{
			$('.errormessage').html('');
		}
		
	}
	
	function threftCreditChange(e){
		$rowId = e.id.match(/[\d\.]+/g);
		//alert(e.id+"-----"+e.value+"--------------"+$rowId);
		if(e.value==='true'){
			/*$('#avgturnover'+$rowId).val('');
		$('#avgincome'+$rowId).val('');*/
		$('.avgturnover'+$rowId).removeClass('d-none');
		$('.avgincome'+$rowId).removeClass('d-none');
		//$('.avgturnover').removeClass('d-none');
		//$('.avgincome').removeClass('d-none');
		$('#avgturnover'+$rowId).removeClass('d-none');
		$('#avgincome'+$rowId).removeClass('d-none');
		$('#fedrated'+$rowId).removeClass('d-none');
		//$('.fedrated').removeClass('d-none');
		$('#avgturnover'+$rowId).prop('disabled', false);
		$('#avgincome'+$rowId).prop('disabled', false);
		$('#fedrated'+$rowId).prop('disabled', false);
		}else{
		$('#fedrated'+$rowId).addClass('d-none');
		//$('.fedrated').addClass('d-none');
		$('#avgturnover'+$rowId).addClass('d-none');
		$('#avgincome'+$rowId).addClass('d-none');
		$('.avgturnover'+$rowId).addClass('d-none');
		$('.avgincome'+$rowId).addClass('d-none');
		//$('.avgturnover').addClass('d-none');
		//$('.avgincome').addClass('d-none');
		$('#avgturnover'+$rowId).prop('disabled', true);
		$('#avgincome'+$rowId).prop('disabled', true);
		$('#fedrated'+$rowId).prop('disabled', true);
		}
		
	}
	
	