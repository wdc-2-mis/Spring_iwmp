


$(function(){
$('#project1').on("change" ,function() {
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
	//console.log(data);
	/*window.location.href="outcomeaddpara";
	*/					var $financial = $('#financial');
						$financial.empty();
        				$financial.append('<option value="">--Select Year--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $financial.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	}
	});
		});
		
	
	$('#draftSave').on('click',function(e) {
		e.preventDefault();
		if($('#project').val()===''){
				alert('please select Project');
				$('#project').focus();
				return false;
			}
		
		if($('#month').val()===''){
				alert('please select Month');
				$('#month').focus();
				return false;
			}
			
		if($('#financial').val()===''){
				alert('please select Financial Year');
				$('#financial').focus();
				return false;
			}
		
		if($('#degradedrainf').val()===''){
				alert('please fill degraded/rainfed data');
				$('#degradedrainf').focus();
				return false;
			}
		if($('#mandays').val()===''){
				alert('please fill no. of Mandays generated');
				$('#mandays').focus();
				return false;
			}		
         if($('#sc').val()===''){
				alert('please fill SC data ');
				$('#sc').focus();
				return false;
			}
		if($('#st').val()===''){
				alert('please fill ST data ');
				$('#st').focus();
				return false;
			}
		if($('#others').val()===''){
				alert('please fill Others data ');
				$('#others').focus();
				return false;
			}
		if($('#female').val()===''){
				alert('please fill female data ');
				$('#female').focus();
				return false;
			}
		if($('#smallfarmer').val()===''){
				alert('please fill small farmer data ');
				$('#smallfarmer').focus();
				return false;
			}
		if($('#marginalfarmer').val()===''){
				alert('please fill marginal farmer data ');
				$('#marginalfarmer').focus();
				return false;
		}	
		if($('#landless').val()===''){
				alert('please fill landless data ');
				$('#landless').focus();
				return false;
		}
		if($('#bpl').val()===''){
				alert('please fill bpl data ');
				$('#bpl').focus();
				return false;
		}				
        else
        if (confirm('Are you sure to wants to Save this Record ?')) {
		$project = $('#project').val();
		$month = $('#month').val();
		$financial = $('#financial').val();
		$degradedrainf = $('#degradedrainf').val();
		$noofman = $('#mandays').val();
		$sc = $('#sc').val();
		$st = $('#st').val();
		$others = $('#others').val();
		$female = $('#female').val();
		$smallfarmer = $('#smallfarmer').val();
		$marginalfarmer = $('#marginalfarmer').val();
		$landless = $('#landless').val();
		$bpl = $('#bpl').val();
		$outcome2_id = $('#outcome2_id').val();
		$status = 'D';
		if($outcome2_id == null)
		{
		$outcome2_id = 0;
		}
		$.ajax({ 
			url:"saveOutcomePara",
            type: "post", 
			data:{projId:$project, month:$month, year:$financial, degradedrainf:$degradedrainf, noofman:$noofman, sc:$sc, st:$st, Others:$others, female:$female, smallfarmer:$smallfarmer, marginalfarmer:$marginalfarmer, landless:$landless, bpl:$bpl, outcome2_id:$outcome2_id, status:$status}, 
            error:function(xhr,status,er){
                console.log(er);
            },

		     success:function(data){
			
			
			successAlert('Data inserted Successfully!');
					$("#successok").click(function()
					 {
			        $('#popup').modal('hide');
                    	window.location.href='outcomeaddpara';		    
});
		}
			});
		
		}
		});	
	
	$('#delete').on('click',function(e) {
	var del = $('#del').val();
	
	confirmAlert('Are You Sure You Want to Delete This Record!!');
					$("#ok").click(function(){
			$('#popup').modal('hide');
			$.ajax({  
            url:"delOutcomeParadraftdata",
            type: "post",  
            data: {draftid:del},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						//alert("hi:" +data);
						if(data==='success'){
                        alert('Data Deleted Successfully!');
						window.location.href='outcomeaddpara';
						}
						else
						{
							alert('Issue on deleting data!');
							window.location.href='outcomeaddpara';
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
	
	$('#complete').on('click',function(e) {
		e.preventDefault();
		if($('#project').val()===''){
				alert('please select Project');
				$('#project').focus();
				return false;
			}
		
		if($('#month').val()===''){
				alert('please select Month');
				$('#month').focus();
				return false;
			}
			
		if($('#financial').val()===''){
				alert('please select Financial Year');
				$('#financial').focus();
				return false;
			}
		
		if($('#degradedrainf').val()===''){
				alert('please fill degraded/rainfed data');
				$('#degradedrainf').focus();
				return false;
			}
		
				
         if($('#sc').val()===''){
				alert('please fill SC data ');
				$('#sc').focus();
				return false;
			}
		if($('#st').val()===''){
				alert('please fill ST data ');
				$('#st').focus();
				return false;
			}
		if($('#others').val()===''){
				alert('please fill Others data ');
				$('#others').focus();
				return false;
			}
		if($('#female').val()===''){
				alert('please fill female data ');
				$('#female').focus();
				return false;
			}
		if($('#smallfarmer').val()===''){
				alert('please fill small farmer data ');
				$('#smallfarmer').focus();
				return false;
			}
		if($('#marginalfarmer').val()===''){
				alert('please fill marginal farmer data ');
				$('#marginalfarmer').focus();
				return false;
		}	
		if($('#landless').val()===''){
				alert('please fill landless data ');
				$('#landless').focus();
				return false;
		}
		if($('#bpl').val()===''){
				alert('please fill bpl data ');
				$('#bpl').focus();
				return false;
		}				
        else
        if (confirm('Are you sure to wants to Complete this Record ?')) {
		$project = $('#project').val();
		$month = $('#month').val();
		$financial = $('#financial').val();
		$degradedrainf = $('#degradedrainf').val();
		$noofman = $('#mandays').val();
		$sc = $('#sc').val();
		$st = $('#st').val();
		$others = $('#others').val();
		$female = $('#female').val();
		$smallfarmer = $('#smallfarmer').val();
		$marginalfarmer = $('#marginalfarmer').val();
		$landless = $('#landless').val();
		$bpl = $('#bpl').val();
		$outcome2_id = $('#outcome2_id').val();
		$status = 'C';
		if($outcome2_id == null)
		{
		$outcome2_id = 0;
		}
		$.ajax({ 
			url:"saveOutcomePara",
            type: "post", 
			data:{projId:$project, month:$month, year:$financial, degradedrainf:$degradedrainf, noofman:$noofman, sc:$sc, st:$st, Others:$others, female:$female, smallfarmer:$smallfarmer, marginalfarmer:$marginalfarmer, landless:$landless, bpl:$bpl, outcome2_id:$outcome2_id, status:$status}, 
            error:function(xhr,status,er){
                console.log(er);
            },

		     success:function(data){
			
			
			successAlert('Data completed Successfully!');
					$("#successok").click(function()
					 {
			        $('#popup').modal('hide');
                    	window.location.href='outcomeaddpara';		    
});
		}
			});
		
		}
		});	
		
		$('#view1').on('click',function(e) {
		e.preventDefault();
			$project = $('#project').val();
			$month = $('#month').val();
	    	$year = $('#financial').val();
		      $.ajax({  
            url:'fetchOutcomeParam',
            type: "post",  
            data: {projId:$project, month:$month, year:$year},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            console.log(data);
            var i = 1;	        
            $tbody = $('#outcomedraftbody');
$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
										$tblData+='<tr><td>'+i+'</td><td>'+d[k].project+'</td><td>'+d[k].month+'</td><td>'+d[k].finyear+'</td><td>'+d[k].degraded_land+'</td><td>'+d[k].rainfed+'</td><td>'+d[k].man_day_gen+'</td><td colspan="6">'+d[k].farmer_sc+'</td><td>'+d[k].farmer_st+'</td><td>'+d[k].others+
													'</td><td>'+d[k].farmer_female+'</td><td>'+d[k].farmer_small+'</td><td>'+d[k].farmer_mirginal+'</td><td>'+d[k].farmer_landless+'</td><td>'+d[k].farmer_bpl+'</td><td><a href="#" data-id='+d[k].outcome2_id+' class="delete">Delete</a></td><td><a href="#" data-id='+d[k].outcome2_id+' class="complete">Complete</a></td>';
									i++
									}
									
									$tbody.append($tblData);
									}
							
						}
		}
		
		     });
            
	$.ajax({  
            url:'outcomefinaldata',
            type: "post",  
            data: {projId:$project, month:$month, year:$financial},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {/*console.log(data);*/
            var i = 1;	        
            $tbody = $('#outcomefinalbody');
$tblData="";
            $tbody.empty();
					for ( var key in data) {
						  	if (data.hasOwnProperty(key)) {
							$tblData="";
							var d = data[key];
								for ( var k in d) {
									$tblData+='<tr><td>'+i+'</td><td>'+d[k].project+'</td><td>'+d[k].month+'</td><td>'+d[k].finyear+'</td><td>'+d[k].degraded_land+'</td><td>'+d[k].rainfed+'</td><td>'+d[k].man_day_gen+'</td><td colspan="6">'+d[k].farmer_sc+'</td><td>'+d[k].farmer_st+'</td><td>'+d[k].others+
													'</td><td>'+d[k].farmer_female+'</td><td>'+d[k].farmer_small+'</td><td>'+d[k].farmer_mirginal+'</td><td>'+d[k].farmer_landless+'</td><td>'+d[k].farmer_bpl+'</td>';
									i++
									}
									$tbody.append($tblData);
									/*$tbody.append('<tr><td  colspan=18></td></tr>');*/
									
							}
						
						}
			 
		}
          });		
			
			});
		
		});