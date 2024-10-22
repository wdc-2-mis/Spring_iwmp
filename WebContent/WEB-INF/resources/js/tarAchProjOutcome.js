$(function(){
	
	
	/****************************** State Dropdown change ********************************** */
	$('#state').on('change', function(e) {
		//e.preventDefault();
		$stCode=$('#state option:selected').val();
		$.ajax({  
            url:"getDistrictByStateCode",
            type: "post", 
			data:{id:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedDist=$('#distCode').val();
						//alert('cal'+$selectedDist);
						$ddlDistrict = $('#district');
						$ddlDistrict.empty();
        				if($stCode==0)
                        {$ddlDistrict.append('<option value="0"> --All-- </option>');} 
                        else{  				
                        $ddlDistrict.append('<option value=""> --Select-- </option>');
                        $ddlDistrict.append('<option value="0"> --All-- </option>');
                        }
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedDist)
							$ddlDistrict.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else
							$ddlDistrict.append('<option value="'+key+'">' +data[key] + '</option>');
							}
							}
	}
	});
		});
		
		/****************************************** District Dropdown change *************************************** */
		$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dCode=$('#district option:selected').val();
		$('#loading').show();
		$.ajax({  
            url:"getProjNACByDcode",
            type: "post", 
			data:{dCode:$dCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
	$('#loading').hide();
						$selectedProject=$('#projId').val();
						$ddlProject = $('#project');
						$ddlProject.empty();
						 if($dCode==0){$ddlProject.append('<option value="0"> --All-- </option>');}
                       {$ddlProject.append('<option value=""> --Select-- </option>');}    				
                       for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedProject)
							$ddlProject.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else
							$ddlProject.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
	}
	});
		});
		
		/************************************************** Project Dropdown change ************************************ */
		$(document).on('change', '#project', function(e) {
		e.preventDefault();
		$pCode=$('#project option:selected').val();
		$.ajax({  
            url:"getFromYearFortarAchProjOutcome",
            type: "post", 
			data:{pCode:$pCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
	                   $selectedFYear=$('#fYear').val();
						$ddlFromYear = $('#fromyear');
						$ddlFromYear.empty();
        				$ddlFromYear.append('<option value=""> --Select-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedFYear)
							$ddlFromYear.append('<option value="'+key+'" selected>' +data[key] + '</option>');
							else
							$ddlFromYear.append('<option value='+key+'>' +data[key] + '</option>');
							}
							}
	}
	});
		});
		/*************************************************** From Year Change ********************************************** */
		$(document).on('change', '#fromyear', function(e) {
		e.preventDefault();
		$fromYear=$('#fromyear option:selected').val();
		if($fromYear===""){
		$fromYear=$('#fYear').val();
		}
		$projId = $('#project option:selected').val();
		$.ajax({  
            url:"getToYearFortarAchProjOutcome",
            type: "post", 
			data:{fromYear:$fromYear,projId:$projId}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
					        $selectedTYear=$('#tYear').val();
							$ddlToYear = $('#toyear');
							$ddlToYear.empty();
	        				$ddlToYear.append('<option value=""> --Select-- </option>');
							 for ( var key in data) {
								if (data.hasOwnProperty(key)) {
								if(key==$selectedTYear)
								$ddlToYear.append('<option value="'+key+'" selected>' +data[key] + '</option>');
								else
								$ddlToYear.append('<option value='+key+'>' +data[key] + '</option>');
								}
								}
	}
	});
		});
		
		
	
	
	var selectedState = $('#state option:selected').val();
	if(selectedState!=null && selectedState!='' && selectedState>0){
	$("#state").trigger("change");
	$('#district option:selected').val($('#distCode').val());
	$("#district").trigger("change");
	$('#project option:selected').val($('#projId').val());
	$("#project").trigger("change");
	$('#fromyear option:selected').val($('#fromYear').val());
	$("#fromyear").trigger("change");
	$('#toyear option:selected').val($('#toYear').val());
	$("#toyear").trigger("change");
	}
	});
	/***************************************************** PDF Button Click ************************************* */
	function exportPDF(){
		
	var table="";
	var reportName = "Format O1- Target/Outcome of Projects Periodically";
	var tableName = "#tblReport";
	var columnSpan =true;
	var colLength=$(tableName+" tr:nth-child(2) th").length;
	var specialNote = true;
	var tbodyRowCount=0;
	var note="";
	if(specialNote)
		tbodyRowCount=$(tableName+" tbody tr").length+1;
	
	//Comment below 2(two) lines if you want to put custom note here or change it accordingly.
	if(typeof $('.text-danger').html() != 'undefined')
		note=$('.text-danger').html();
		
	
	
	var td="";
	for(var i=1;i<colLength;i++){
		td=td+"<td></td>";
	}
 	
	$(tableName+' tbody tr:last').after('<tr><td width="" class="text-danger"></td>'+td+'</tr>');

var headerHtml =" State: "+$('#state option:selected').text().trim()+" District: "+$('#district option:selected').text()+" \r\nProject: "+
$('#project option:selected').text()+" \r\nFrom: "+$('#fromyear option:selected').text()+" \r\nTo: "+$('#toyear option:selected').text();

	if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
		createDataTable(reportName,tableName,columnSpan,colLength,tbodyRowCount,headerHtml);
		}
	//createDataTable(headerTitle,reportName,tableName);
	//trigger pdf button of datatable
	table=$(tableName).DataTable().buttons(0,1).trigger();
	table.buttons('.buttons-pdf').nodes().css("display", "none");
	table.buttons('.buttons-excel').nodes().css("display", "none");
	$(tableName).css("width","100%");
	$(tableName).removeClass('dataTable');
	$('#tblReport tbody tr:last').remove();
}
/***************************************************** Excel Button Click ************************************* */
function exportExcel(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note="";
	
	var tableName = "#tblReport";
	var colLength=$(tableName+" tr:nth-child(2) th").length;
	
	if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";

	var fileName="Months wise Physical Achievement of a Project";
	 
	var reportName = "Format O1- Target/Outcome of Projects Periodically";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    "<tr><td>State: "+$('#state option:selected').text()+"</td><td>District: "+$('#district option:selected').text()+"</td></tr>"+
	    "<tr><td>Project: "+$('#project option:selected').text()+"</td></tr>"+
	    "<tr><td>From: "+$('#fromyear option:selected').text()+"</td></tr>"+
        "<tr><td>To: "+$('#toyear option:selected').text()+"</td></tr>"+
	    "<tr><td></td></tr></table>";
	var footerHtml = "<table><tr></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>Report Generated by IWMP software on: "+jsDate+"</td></tr></table>";  
	 exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
}
	/*********************************************END*****************************************************************/	
		
		
		var expanded = false;

function showCheckboxes() {
  var checkboxes = document.getElementById("checkboxes");
  if (!expanded) {
    checkboxes.style.display = "block";
    expanded = true;
  } else {
    checkboxes.style.display = "none";
    expanded = false;
  }
}