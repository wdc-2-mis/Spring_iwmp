/**
 * 
 */

/****************************** State Dropdown change ********************************** */
	$(document).on('change', '#assetstate', function(e) {
		e.preventDefault();
		$stCode=$('#assetstate option:selected').val();
		if($stCode===''){
			$('#assetstate').focus();
			$('.error-assetstate').html('Please select State');
			return false;
		}else{
			$('.error-assetstate').html('');
		}
		$('#loading').show();
		$.ajax({  
            url:"getDistrictDataNew",
            type: "post", 
			data:{stateCode:$stCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
	$('#loading').hide();
						$selectedDist=$('#distCode').val();
						$ddlDistrict = $('#assetdistrict');
						$ddlDistrict.empty();
        				$ddlDistrict.append('<option value=""> --Select-- </option>');
						$ddlDistrict.append('<option value=0> --All-- </option>');
						 for ( var key in data) {
						    if (data.hasOwnProperty(key)) {
							if(key==$selectedDist)
							$ddlDistrict.append('<option value="'+data[key]+'" selected>' +key + '</option>');
							else
							$ddlDistrict.append('<option value="'+data[key]+'">' +key+ '</option>');
							}
							}
	}
	});
		});
		
		/****************************************** District Dropdown change *************************************** */
		$(document).on('change', '#assetdistrict', function(e) {
		e.preventDefault();
		$stCode=$('#assetstate option:selected').val();
		$dCode=$('#assetdistrict option:selected').val();
		if($dCode===''){
			$('#assetdistrict').focus();
			$('.error-assetdistrict').html('Please select District');
			return false;
		}else{
			$('.error-assetdistrict').html('');
		}
		$('#loading').show();
		$.ajax({  
            url:"getProjBystCodedCode",
            type: "post", 
			data:{stCode:$stCode,dCode:$dCode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
	$('#loading').hide();
						$selectedProject=$('#projId').val();
						$ddlProject = $('#assetproject');
						$ddlProject.empty();
        				$ddlProject.append('<option value=""> --Select-- </option>');
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

/*************************************************************************** Project CHnage method **************************************************************************** */
	
	$('#assetproject').on("change" ,function() {
		$('.error').html('');
		$projId = $('#assetproject option:selected').val();
		if($projId===''){
			$('#assetproject').focus();
			$('.error-assetproject').html('Please select Project');
			return false;
		}else{
			$('.error-assetproject').html('');
		}
		$('#loading').show();
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
	$('#loading').hide();
						var $year = $('#year');
						$year.empty();
        				$year.append('<option value="">--Select Year--</option>');
						$year.append('<option value=0>--All--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $year.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	}
	});
		});
		/********************************************************************** End ***************************************************************************** */
	
	$('#year').on("change" ,function() {
		$('.error').html('');
		$year = $('#year option:selected').val();
		if($year===''){
			$('#assetproject').focus();
			alert('Please select Year');
			return false;
		}
		
		$('#loading').show();
		var url ="getFinYearMonth";
		$.ajax({  
            url:url,
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
						var $mnth = $('#month');
						$mnth.empty();
						
					if($year >0){
						$mnth.append('<option value="">--Select Month--</option>');
						 for ( var key in data) 
						 {
						     if (data.hasOwnProperty(key)) {
						           $mnth.append('<option value='+key+'>' +data[key] + '</option>');
						     }
						 }
					}
					else{
						$mnth.append('<option value="0">--ALL--</option>');   
					}
		
		
        				
	}
	});
		});	

/*************************************************************************** Head CHnage method **************************************************************************** */
	
	$('#head').on("change" ,function() {
		$('.error').html('');
		$headId = $('#head option:selected').val();
		if($headId===''){
			$('#head').focus();
			$('.error-head').html('Please select Head');
			return false;
		}else{
			$('.error-head').html('');
		}
		$('#loading').show();
		var url ="getActivity";
		$.ajax({  
            url:url,
            type: "post",  
            data: {headId:$headId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	//console.log(data);
	$('#loading').hide();
	var $activity = $('#activity');
						$activity.empty();
						$activity.append('<option value="">--Select Activity--</option>');
        				$activity.append('<option value=0>--All--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $activity.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	}
	});
		});
		/********************************************************************** End ***************************************************************************** */
		
/*************************************************************************** Activity CHnage method **************************************************************************** */
	
	$('#activity').on("change" ,function() {
		$('.error').html('');
		$activityId = $('#activity option:selected').val();
		if($activityId===''){
			$('#activity').focus();
			$('.error-activity').html('Please select Activity');
			return false;
		}else{
			$('.error-activity').html('');
		}
		$('#loading').show();
		var url ="getSubActivity";
		$.ajax({  
            url:url,
            type: "post",  
            data: {activityId:$activityId},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	$('#loading').hide();
	var $subactivity = $('#subactivity');
						$subactivity.empty();
						$subactivity.append('<option value="">--Select SubActivity--</option>');
        				$subactivity.append('<option value=0>--All--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $subactivity.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
	}
	});
		});
		/********************************************************************** End ***************************************************************************** */
		
		/*************************************************************************** Activity CHnage method **************************************************************************** */
	
	$('#btnGetAssetReport').on("click" ,function(e) {
		e.preventDefault();
		$stCode = $('#assetstate option:selected').val();
		$distCode = $('#assetdistrict option:selected').val();
		$projId = $('#assetproject option:selected').val();
		$fyCode = $('#year option:selected').val();
		$headCode = $('#head option:selected').val();
		$activityCode = $('#activity option:selected').val();
		$subActivityCode = $('#subactivity option:selected').val();
		$month = $('#month option:selected').val();
		$status = $('#status option:selected').val();
		if($stCode===''){
			$('#assetstate').focus();
			$('.error-assetstate').html('Please select State');
			return false;
		}else{
			$('.error-assetstate').html('');
		}
		if($distCode===''){
			$('#assetdistrict').focus();
			$('.error-assetdistrict').html('Please select District');
			return false;
		}else{
			$('.error-assetdistrict').html('');
		}
		if($projId===''){
			$('#assetproject').focus();
			$('.error-assetproject').html('Please select Project');
			return false;
		}else{
			$('.error-assetproject').html('');
		}
		if($fyCode===''){ 
			$('#year').focus();
			$('.error-year').html('Please select FY');
			return false;
		}else{
			$('.error-year').html('');
		}
		
		if($month===''){ 
			$('#month').focus();
			$('.error-month').html('Please select month');
			return false;
		}else{
			$('.error-month').html('');
		}
		
		if($headCode===''){
			$('#head').focus();
			$('.error-head').html('Please select Head');
			return false;
		}else{
			$('.error-head').html('');
		}
		if($activityCode===''){
			$('#activity').focus();
			$('.error-activity').html('Please select Activity');
			return false;
		}else{
			$('.error-activity').html('');
		}
		if($subActivityCode===''){
			$('#subactivity').focus();
			$('.error-subactivity').html('Please select Sub-Activity');
			return false;
		}else{
			$('.error-subactivity').html('');
		}
		$('#loading').show();
		var url ="getAssetReport";
		$.ajax({  
            url:url,
            type: "post",  
            data: {stCode:$stCode,distCode:$distCode,projId:$projId,fyCode:$fyCode,headCode:$headCode,activityCode:$activityCode,subActivityCode:$subActivityCode, monthid:$month, statuss:$status},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
				console.log(data);
				$('#loading').hide();
				var i = 1;
				var k=0;
				var $assetreportTbody = $('#assetreportTbody');
				$assetreportTbody.empty();
			if(Object.keys(data).length>0){
				for ( var key in data) {
					$subactivity = (typeof data[key].subactivitydesc==='undefined'?'':data[key].subactivitydesc);
					if (data.hasOwnProperty(key)) {
						$assetreportTbody.append('<tr><td>'+i+'</td><td>' +data[key].stname + '</td><td>'+data[key].distname+'</td><td>'+data[key].bname+'</td><td>'+data[key].vname+'</td><td>'+data[key].projdesc+'</td><td>'+data[key].finyrdesc+'</td><td>'+data[key].headdesc+'</td><td>'+data[key].activitydesc+'</td><td>'+$subactivity+'</td><td>'+data[key].assetid+'</td><td class="text-center">'+data[key].assetach+'</td><td>'+data[key].unit+'</td><td>'+data[key].statuskd+'</td><td>'+data[key].statusdate+'</td></tr>');
					}
				i++;
				k=k+data[key].assetach;
				}
				$assetreportTbody.append('<tr class="text-center"><td colspan="9"></td><td>Total</td><td> Works=&nbsp;'+(i-1)+'</td><td>'+k.toFixed(2)+'</td><td></td><td></td><td></td></tr>');
			}else{
					$assetreportTbody.append('<tr class="text-center"><td colspan="15"> Data not found !</td></tr>');
			}
	}
	});
		});
		/********************************************************************** End ***************************************************************************** */

	function exportExcel(){
		var now = new Date();
		var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
		var tableName = "#tblReport";
		 var colLength=$(tableName+" tr:first th").length;
		 var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
		var reportName = "Format A2- Physical Achievement of Activities/ Work";
		var fileName="Format A2";
		 var headerHtml = "<table>"+
		 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
		    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
		    
		    "<tr><td></td></tr></table>";
		    var footerHtml = "<table>"+
		    "<tr><td colspan='"+colLength+"' style='text-align:center'>Report Generated by WDC-PMKSY software on: "+jsDate+"</td></tr></table>";  
		 exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
		}
	
	