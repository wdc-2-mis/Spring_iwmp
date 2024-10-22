
$(function(){

	/****************************** State Dropdown change ********************************** */
	$(document).on('change', '#state', function(e) {
	//$('#state').on('change', function(e){
		e.preventDefault();
		$stCode=$('#state option:selected').val();
		$.ajax({  
            url:"getDistrictDataNew",
            type: "post", 
			data:{stateCode:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedDist=$('#distCode').val();
						$ddlDistrict = $('#district');
						$ddlDistrict.empty();
        				$ddlDistrict.append('<option value="0"> --All-- </option>');
						 for ( var key in data) {
						   if (data.hasOwnProperty(key)) {
							if(data[key]==$selectedDist)
							$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
							else
							$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
							}
							}
	}
	});
		});
		
		/****************************************** District Dropdown change *************************************** */
		$(document).on('change', '#district', function(e) {
		e.preventDefault();
		$dCode=$('#district option:selected').val();
		$.ajax({  
            url:"getProjectPhysicalActionPlan",
            type: "post", 
			data:{dCode:$dCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
						$selectedProject=$('#projId').val();
						$ddlProject = $('#project');
						$ddlProject.empty();
        				$ddlProject.append('<option value="0"> --All-- </option>');
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
            url:"getYearForPhysicalActionAchievementReport",
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
		$(document).on('click', '#btnGetReport', function(e) {
		e.preventDefault();
		$state=$('#state option:selected').val();
		$district=$('#district option:selected').val();
		$project=$('#project option:selected').val();
		$fromYear=$('#fromyear option:selected').val();
		$toYear=$('#toyear option:selected').val();
		$.ajax({  
            url:"getPhysicalActionPlanReport",
            type: "post", 
			data:{state:$state,district:$district,project:$project,fromYear:$fromYear,toYear:$toYear}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				var finyr="";
				
				console.log(data);
				$tbodyYearWisePhyActPlan=$('#tbodyYearWisePhyActPlan');
				$tbodyYearWisePhyActPlanData=$('#tbodyYearWisePhyActPlanData');
				$tbodyYearWisePhyActPlan.empty();
				$tbodyYearWisePhyActPlanData.empty();
				$yearHead="<tr><td></td><td></td><td></td><td></td>";
				$count=4;
				$plan="";
				$yearData="";
				var head =[];
				var activity =[];
				var yearArray = [];
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							
							/*if(key==='yearData'){
							var d =data['yearData'];
							for ( var k in d) {
								if (d.hasOwnProperty(k)) {
									alert($count);
									$yearHead=$yearHead+"<td>"+k+"</td>";
									var x=d[k];
									if($count>4){
									for ( var i in x) {
								if (x.hasOwnProperty(i)) {
									$yearData="<td>"+x[i].plan+"</td>";
									
									/** --------------------- *
									
									var count =1;
								var a =data['headActivity'];
								console.log(a);
							for ( var k in a) {
								if (a.hasOwnProperty(k)) {//alert(k);
									var x1=a[k];
									for ( var i in x1) {
								if (x1.hasOwnProperty(i)) {
									if(x[i].headname===x1[i].headname && x[i].activityname===x[i].activityname){
									$tbodyYearWisePhyActPlanData.append("<tr><td>"+count+"</td><td>"+x1[i].headname+"</td><td>"+x1[i].activityname+"</td><td>"+x1[i].unitname+"</td><td></td><td></td></tr>");
									$('#tbodyYearWisePhyActPlanData').find('tr').each(function(){
       											 $(this).find('td').eq($count).after('<td>'+x[i].plan+'</td>');
   											});
															}else{
									$tbodyYearWisePhyActPlanData.append("<tr><td>"+count+"</td><td>"+x[i].headname+"</td><td>"+x[i].activityname+"</td><td>"+x[i].unitname+"</td><td>"+x[i].plan+"</td><td></td></tr>");							
															}
															}
													}
													}
													count++;
								}
								
									
									/**** ------------------------ *
											$('#tbodyYearWisePhyActPlanData').find('tr').each(function(){
       											 $(this).find('td').eq($count).after('<td>'+x[i].plan+'</td>');
   											});
								//$tbodyYearWisePhyActPlanData.append("<tr><td>"+count+"</td><td>"+x[i].headname+"</td><td>"+x[i].activityname+"</td><td>"+x[i].unitname+"</td><td>"+x[i].plan+"</td><td></td></tr>");
									//$tbodyYearWisePhyActPlanData.append("<tr><td>"+i+"</td><td>"+x[i].headname+"</td><td>"+x[i].activityname+"</td><td>"+x[i].unitname+"</td><td>"+d[k].plan+"</td><td></td></tr>");
									
															}
													}
													}else{
													// if count is less than or equals to 4	
													}
											$count=$count+1;		
									}
								}
								}*/
								if(key==='headActivity'){
									var count =1;
								var a =data['headActivity'];
								console.log(a);
							for ( var k in a) {
								if (a.hasOwnProperty(k)) {//alert(k);
									var x=a[k];
									for ( var i in x) {
								if (x.hasOwnProperty(i)) {
									$tbodyYearWisePhyActPlanData.append("<tr><td>"+count+"</td><td>"+x[i].headname+"</td><td>"+x[i].activityname+"</td><td>"+x[i].unitname+"</td><td>"+x[i].plan+"</td><td></td></tr>");
									
															}
													}
													}
													count++;
								}
								}
						}
					}
					//alert($count);
					$('#yearHead').prop("colspan",$count);
					$tbodyYearWisePhyActPlan.append($yearHead+"<td></td></tr>");
				
			}else{
				$tbodyYearWisePhyActPlan.append("No Data Found");
			}
	}
	});
		});
	/********************************************** Page Load Script ************************************* */	
	var selectedState = $('#state option:selected').val();
	if(selectedState!=null && selectedState!='' && selectedState>0){
	$("#state").trigger("change");
	$('#district option:selected').val($('#distCode').val());
	$("#district").trigger("change");
	$('#project option:selected').val($('#projId').val());
	$("#project").trigger("change");
	$('#fromyear option:selected').val($('#fYear').val());
	$("#fromyear").trigger("change");
	$('#toyear option:selected').val($('#tYear').val());
	$("#toyear").trigger("change");
	}
	
	});
	
	/***************************************************** PDF Button Click ************************************* */
//	function exportPDF(){
//	var table="";
//	var reportName = "Format A1- Physical Target/Achievement of Projects in a Year(Month Wise)";
//	var tableName = "#tblReport";
//	var columnSpan =true;
//	var colLength=$(tableName+" tr:nth-child(3) th").length;
//	var specialNote = true;
//	var tbodyRowCount=0;
//	var note="";
//
//	if(specialNote)
//		tbodyRowCount=$(tableName+" tbody tr").length+1;
//	
//	//Comment below 2(two) lines if you want to put custom note here or change it accordingly.
//	if(typeof $('.text-danger').html() != "undefined")
//		note=$('.text-danger').html();
//		
//	
//	
//	var td="";
//	for(var i=1;i<colLength;i++){
//		td=td+"<td></td>";
//	}
// 	
//	$(tableName+' tbody tr:last').after('<tr><td width="" class="text-danger">'+note+'</td>'+td+'</tr>');
//
//var headerHtml =" State: "+$('#state option:selected').text().trim()+" District: "+$('#district option:selected').text()+" \r\nProject: "+
//$('#project option:selected').text()+" \r\nFrom: "+$('#fromyear option:selected').text();
//
//	if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
//		createDataTable(reportName,tableName,columnSpan,colLength,tbodyRowCount,headerHtml);
//		}
//	//createDataTable(headerTitle,reportName,tableName);
//	//trigger pdf button of datatable
//	table=$(tableName).DataTable().buttons(0,1).trigger();
//	table.buttons('.buttons-pdf').nodes().css("display", "none");
//	table.buttons('.buttons-excel').nodes().css("display", "none");
//	$(tableName).css("width","100%");
//	$(tableName).removeClass('dataTable');
//	$('#tblReport tbody tr:last').remove();
//}
//
//
///***************************************************** Excel Button Click ************************************* */
//function exportExcel(){
//	var now = new Date();
//	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
//	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
//	var specialNote = true;
//	var note="";
//	
//	var tableName = "#tblReport";
//	var colLength=$(tableName+" tr:nth-child(2) th").length;
//	
//	if(specialNote && typeof $('.text-danger').html() != "undefined")
//	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";
//
//	var fileName="Months wise Physical Achievement of a Project";
//	 
//	var reportName = "Format A1- Physical Target/Achievement of Projects in a Year(Month Wise)";
//	var headerHtml = "<table>"+
//	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
//	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
//	    "<tr><td>State: "+$('#state option:selected').text()+"</td><td>District: "+$('#district option:selected').text()+"</td></tr>"+
//	    "<tr><td>Project: "+$('#project option:selected').text()+"</td></tr>"+
//	    "<tr><td>From: "+$('#fromyear option:selected').text()+"</td>"+
//	    "<tr><td></td></tr></table>";
//	var footerHtml = "<table><tr></tr>"+note+
//	    "<tr><td colspan='"+colLength+"' style='text-align:center'>Report Generated by IWMP software on: "+jsDate+"</td></tr></table>";  
//	 exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
//}