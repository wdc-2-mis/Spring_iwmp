$( document ).ready(function(){
	
	$(document).on( 'change', '#groupType', function (e) {
		$tblSHGDetails =$('#tblSHGDetails');
		$tblSHGDetails.empty();
		$('#noOf').val('');
		if($('#groupType option:selected').text()==='--Select level--'){
		//$('.lblNoOf').html('No. of ');
		$('.noOf').addClass('d-none');
		$('#btnGo').addClass('d-none');
		}
		else{
		$('.lblNoOf').html('No. of '+$('#groupType option:selected').text()+':- ');
		$('.noOf').removeClass('d-none');
		$('#btnGo').removeClass('d-none');
		}
		$('.button').addClass('d-none');
		});
		
	$(document).on( 'click', '#btnGo', function (e) {
		e.preventDefault();
		//alert('no of training conduct'+$('#noOf').val());
		if($('#groupType option:selected').val()===''){
			alert('Please select training level !');
			$('#groupType').focus();
			return false;
		}
		if($('#noOf').val()===''){
			alert('Please enter No. of training conducted at '+$('#groupType option:selected').text());
			$('#noOf').focus();
			return false;
		}
		$addsubject='';
		$coretext = new Array();
		$('#coretext').val('');
		$('.button').removeClass('d-none');
		$noOf = $('#noOf').val();
		
		$.ajax({  
            url:"getTrainingSubject",
            type: "post", 
			data:{}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
            	$tblSHGDetails =$('#tblSHGDetails');
        		$tblSHGTBody ="<tbody>";		
            	//$addsubject='<option value=""> --Select-- </option>';
				for ( var key in data) 
				{
					if (data.hasOwnProperty(key)) 
					{
						$addsubject+='<option value='+key+'>' +data[key] + '</option>';
					}
				}
				//alert('kdy= '+$addsubject);
				$tblSHGTHead ='<br/> <br/><thead ><tr><th class="text-center" >S.No.</th><th  class="text-center">Total no. of Persons trained</th><th class="text-center" width="40">Subject</th><th class="text-center" >Start Date</th><th class="text-center" >End Date</th><th class="text-center">No. of Training days</th></tr>';
				for($i=1;$i<=$noOf;$i++){
				$tblSHGTBody +='<tr><td class="halfwidth">'+$i+'</td><td class="halfwidth"> <input type="text" maxlength="10" size="12" id="nameofperson'+$i+'" name="nameofperson" class="form-control"  onmousedown="numericOnly(event);" /></td>'+
				'<td class="halfwidth"><select  id="subject'+$i+'" name="subject" class="form-control" multiple rows="5" style="text-align:center; " width="40">'+$addsubject+'</select></td>'+
				'<td><input type="text" size="15" class="datepicker"   id="stdatepicker'+$i+'" name=" "stdatepicker"+i+"" required autocomplete = "off" /></td> <td><input type="text" size="15" class="datepicker"   id="datepickerend'+$i+'" name=" "datepickerend"+i+"" required autocomplete = "off" onchange="pickdate(event);"/></td>'+
				'<td class="halfwidth"><input type="text" maxlength="10" size="12" id="trainday'+$i+'" name="trainday" class="form-control" readonly="readonly"></td></tr>';
				}
				$tblSHGTBody +="</tbody><br/> <br/>";
				$tblSHGDetails.html($tblSHGTHead+$tblSHGTBody);
				
				$('select[multiple]').multiselect({
				    columns: 1,
				    placeholder: 'Select Options'
				});
            }
            
		});
	});
		
	$(document).on( 'click', '#btnCancel', function (e) {
		e.preventDefault();
		
	});
		
		/******************************************** Save Button Click ******************************** */
	$(document).on( 'click', '#btnSave', function (e) {
		e.preventDefault();
		
		$group = $('#groupType option:selected').val();
		$noOf = $('#noOf').val();
		
		$noOfperson = new Array();
		$subject = new Array();
		$startdate = new Array();
		$enddate = new Array();
		$trainday = new Array();
		
		for($i =1; $i<=$noOf; $i++)
		{
			if($('#nameofperson'+$i).val()==='')
			{
				alert('Please enter total No. of persons trained !');
				$('#nameofperson'+$i).focus();
				return false;
			}
			if($('#subject'+$i+' option:selected').val()==='' || typeof $('#subject'+$i+' option:selected').val()==='undefined')
			{
				alert('Please select subject !'+$('#subject'+$i).val());
				$('#subject'+$i).focus();
				return false;
			}
			if($('#stdatepicker'+$i).val()==='')
			{
				alert('Please enter start date !');
				$('#stdatepicker'+$i).focus();
				return false;
			}
			if($('#datepickerend'+$i).val()==='')
			{
				alert('Please enter end date !');
				$('#datepickerend'+$i).focus();
				return false;
			}
			if($('#trainday'+$i).val()==='')
			{
				alert('Please enter No. of training days !');
				$('#trainday'+$i).focus();
				return false;
			}
			//alert('select subject ='+$('#subject'+$i+' option:selected').val());
			
			$noOfperson.push($('#nameofperson'+$i).val());
			$trainday.push($('#trainday'+$i).val());
			$subject.push($('#subject'+$i).val().join("#"));
			$startdate.push($('#stdatepicker'+$i).val().toString());
			$enddate.push($('#datepickerend'+$i).val().toString())
			
		}
	//	alert($group+" : "+$noOf+' No. of person '+$noOfperson+' training days '+$trainday+' # subject '+$subject+" : "+$startdate+" : "+$enddate);
		//alert($startdate+" : "+$enddate);
		$.ajax({  
            url:"saveTrainigCapicityBuilding",
            type: "post", 
			data:{level:$group, noOf:$noOf, person:$noOfperson.toString(), training:$trainday.toString(), sub:$subject.toString(), start:$startdate.toString(), end:$enddate.toString()}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
					console.log(data);
					if(data==='success')
					{
						successAlert('Data Saved Successfully !');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							window.location.href='trainingCapicityBuilding';
							}); 
						$(".close").click(function(){
							$('#popup').modal('hide');
							window.location.href='trainingCapicityBuilding';
							});
					}
					if(data==='fail'){
						successAlert('Data not Saved some error !');
						$("#successok").click(function(){
							$('#popup').modal('hide');
							window.location.href='trainingCapicityBuilding';
							});  
							$(".close").click(function(){
							$('#popup').modal('hide');
							window.location.href='trainingCapicityBuilding';
							});
					}
            }
		});
		
		
	});
		
		
	
	});

$(document).on('focus', '.datepicker', function(e){ 
	$(this).datepicker({
    	 changeMonth: true,
         changeYear: true,
         dateFormat: "dd-mm-yy",
         yearRange: "2015:2041"
        });

});
function pickdate(e)
{
	//alert('=kdy='+parseInt(e.target.id.replace(/[^0-9.]/g, "")));
	$id= parseInt(e.target.id.replace(/[^0-9.]/g, ""));
	var	start = $('#stdatepicker'+$id).val(); // $('#stdatepicker'+$i).val();
	var end =  $('#datepickerend'+$id).val();  //  $('#datepickerend'+$i).val();
	
	var startParts = start.split("-");
	var startDate = new Date(+startParts[2], startParts[1] - 1, +startParts[0]); 
	var endParts = end.split("-");
	var endDate = new Date(+endParts[2], endParts[1] - 1, +endParts[0]); 
	
	var total_seconds = Math.abs(endDate - startDate) / 1000;

    //calculate days difference by dividing total seconds in a day
    var days_difference = Math.floor (total_seconds / (60 * 60 * 24));
    var totaldays=(days_difference+1);
  //  alert("Number of days between dates from current date <br>" + startDate + " and <br>" + endDate + " are: <br>" + (days_difference+1)+ " days");
	
	$('#trainday'+$id).val(totaldays);   //days;
	
	
	
	
	
}



