/**
 * 
 */

$( document ).ready(function(){

/***************************** Project Change Script *************************************** */
$(document).on( 'change', '#project', function (e) {
	$('#loading').show();
	if($('.plotnoError').html()!=""){
		$('.plotnoError').html('');
	}
	$projId = $('#project').val();
	if($projId ===""){
		window.location.reload();
	}
	
	$.ajax({  
            url:"getVillageOfProject",
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlVillage=$('#village');
				$ddlVillage.empty();
				$ddlVillage.append("<option value=''>---Select Village [GP Name]---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlVillage.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
  orderByNameDDL($ddlVillage);
			}
		});
	
});	


/************************************************* End ************************************************* */


/**************************************************** Village Change Script ******************************* */

$(document).on( 'change', '#village', function (e) {
	$('#loading').show();
	if($('.plotnoError').html()!=""){
		$('.plotnoError').html('');
	}
	$ddlOwnership=$('#ddlOwnership');
	$.ajax({  
            url:"getOwnership",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlOwnership.empty();
				$ddlOwnership.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlOwnership.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
			}
		});
		
		$ddlLandClass=$('#ddlLandClass');
		$.ajax({  
            url:"getLandClassification",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlLandClass.empty();
				$ddlLandClass.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlLandClass.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
			}
		});
		
		
		$ddlIrrigationStatus=$('#ddlIrrigationStatus');
	$.ajax({  
            url:"getIrrigationStatus",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlIrrigationStatus.empty();
				$ddlIrrigationStatus.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlIrrigationStatus.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
			}
		});
	
});

/*************************************************************** End ***************************************************** */

/**************************************************** Ownership Change Script ******************************* */

$(document).on( 'change', '#ddlOwnership', function (e) {
	$('#loading').show();
	$val = $('#ddlOwnership option:selected').text();
	
	if($val==='Private'){
		$('.ownername').removeClass('d-none');
	}else{
		$('.ownername').addClass('d-none');
	}
	
	
	$('#loading').hide();
});

/*************************************************************** End ***************************************************** */

/**************************************************** Classification of Land Change Script ******************************* */

$(document).on( 'change', '#ddlLandClass', function (e) {
	$('.otherDetailsDiv').empty();
	$('#loading').show();
	$cropDeatilDiv = $('.cropDeatilDiv');
	$('#tblCropDeatilTbody').empty();
	$cropDeatilDiv.addClass('d-none');
	$val = $('#ddlLandClass option:selected').text();
	console.log($val);
//	Add by Yogesh line no 151 to 155
	if($val==='Others'){
		$('.landSubClass').removeClass('d-none');
	}else{
		$('.landSubClass').addClass('d-none');
	}
	if($val==='Forest Land'){
		$('#draftSave').removeClass('d-none');
		$('.forestlandType').removeClass('d-none');
		$('.noofCrop').addClass('d-none');
		$ddlForestlandType=$('#ddlForestlandType');
		$.ajax({  
            url:"getForestlandType",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlForestlandType.empty();
				$ddlForestlandType.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlForestlandType.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
			}
		});
	}else{
		$('#draftSave').addClass('d-none');
		$('.forestlandType').addClass('d-none');
		$('.noofCrop').removeClass('d-none');
		$ddlNoofCrop=$('#ddlNoofCrop');
		$.ajax({  
            url:"getNoofCrop",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlNoofCrop.empty();
				$ddlNoofCrop.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlNoofCrop.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
			}
		});
	}
	/*if($val==='Wasteland / Degraded Land'){
		$('.landSubClass').removeClass('d-none');
		
		$ddlLandSubClass=$('#ddlLandSubClass');
		$.ajax({  
            url:"getLandSubClassification",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlLandSubClass.empty();
				$ddlLandSubClass.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlLandSubClass.append("<option value='"+key+"'>"+data[key]+"</option>");
						}
					}
				}
			}
		});
	}else{
		$('.landSubClass').addClass('d-none');
		
	}*/
	$('#loading').hide();
	
	
});

/*************************************************************** End ***************************************************** */




/**************************************************** No of Crop Change Script ******************************* */

$(document).on( 'change', '#ddlNoofCrop', function (e) {
	$('#loading').show();
	$seasonArray = new Array();
$cropTypeValue = new Array();
$season1 = new Array();
$season2 = new Array();
$season3 = new Array();
$singleCropTypeVal = new Array();
$totalCropArea=0;
$season1Area= 0;
$season2Area= 0;
$season3Area= 0;
	$lable= '<h5 class="text-center labeltext font-weight-bold"></h5>';
	$ddlSeason = '<label for="startDate">Season:</label><select id="ddlSeason" name="ddlSeason" class="ddlSeason form-control col-md-6"><option value="">--Select--</option></select><br/>';
	$val = $('#ddlNoofCrop option:selected').text();
	$single_crop = '<table class="tblOtherDetails table" id="single_crop" name="single_crop"><thead><tr><th class="text-center">Crop Type</th><th class="text-center">Crop Area (in ha)<br/>(col-A)</th>'+
	'<th class="text-center">Crop production per Hectare (in Quintal)<br/>(col-B)'+
	'</th><th class="text-center">Avg. Income per Quintal (in Rs.)<br/>(col-C)</th><th class="text-center">Total Income (in Rs.)<br/>(col-A*col-B*col-C)</th><th>Action</th></tr></thead>'+
	'<tbody id="tblOtherDetailsTbody">'+
	'<tr><td><select id="ddlCropType" name="ddlCropType" class="ddlCropType form-control"><option value="">--Select--</option></select><span id="cropTypeError"></span></td>'+
	'<td><input type="text" id="cropArea" name="cropArea" class="cropArea form-control"  onblur="getTotalIncome()" onfocusin="decimalToFourPlace(event)"/><span id="cropAreaError"></span></td>'+
	'<td><input type="text" id="cropProduction" name="cropProduction" class="cropProduction form-control" onfocusin="decimalCheck(event)"  onblur="getTotalIncome()"/><span id="cropProductionError"></span></td>'+
	'<td><input type="text" id="avgIncome" name="avgIncome" class="avgIncome form-control" onfocusin="decimalCheck(event)" onblur="getTotalIncome()" /><span id="avgIncomeError"></span></td>'+
	'<td><input type="text" id="totalIncome" name="totalIncome" class="totalIncome form-control" readonly /></td>'+
	'<td><a href="#" id="add" name="add" class="add btn btn-info" >Add Crop Detail</a></td></tr></tbody></table>';
	$cropDeatilDiv = $('.cropDeatilDiv');
	$('#tblCropDeatilTbody').empty();
	$('.otherDetailsDiv').empty();
	$('.otherDetailsDiv').append($lable);
	console.log('a'+$val+'b');
	$val = $.trim($val);
	$('#disSeason').removeClass('d-none');
	if($val!='No Crop'){
		$('#draftSave').addClass('d-none');
		$('.otherDetailsDiv').removeClass('d-none');
		if($val==='Single Crop'){
		$('#disSeason').addClass('d-none');	
		$cropDeatilDiv.addClass("d-none");
		$('#single_crop').removeClass('d-none');
		$('.otherDetailsDiv').append($single_crop);
		getCropType();
		}
		if($val==='Double Crop'){
		$cropDeatilDiv.addClass("d-none");
		$('.otherDetailsDiv').append($ddlSeason);
		$('#single_crop').removeClass('d-none');
		$('.otherDetailsDiv').append($single_crop);
		getCropType();
		$ddlSeason=$('#ddlSeason');
		$.ajax({  
            url:"getSeasonList",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				$('#loading').hide();
				$ddlSeason.empty();
				$ddlSeason.append("<option value=''>---Select---</option>");
				$count=0;
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							if($count<=1){
							$ddlSeason.append("<option value='"+key+"'>"+data[key]+"</option>");
							if ( $.inArray(key, $seasonArray) === -1){
								$seasonArray.push(key);
							}else{
								//alert('already added activity');
							}
							}
							$count++;
						}
					}
				}
			}
		});
		}if($val==='Multiple Crop'){
		$cropDeatilDiv.addClass("d-none");
		$('.otherDetailsDiv').append($ddlSeason);
		$('#single_crop').removeClass('d-none');
		$('.otherDetailsDiv').append($single_crop);
		getCropType();
		$ddlSeason=$('#ddlSeason');
		$.ajax({  
            url:"getSeasonList",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlSeason.empty();
				$ddlSeason.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$ddlSeason.append("<option value='"+key+"'>"+data[key]+"</option>");
							if ( $.inArray(key, $seasonArray) === -1){
							$seasonArray.push(key);
							}else{
									//alert('already added activity');
							}
						}
					}
				}
			}
		});
		}
		$('.labeltext').html('For '+$val);
	}else{
		$('.otherDetailsDiv').addClass('d-none');
		$('.labeltext').html('');
		$cropDeatilDiv.addClass("d-none");
		$('#draftSave').removeClass('d-none');
	}
	
	
	$('#loading').hide();
});

/*************************************************************** End ***************************************************** */

/*************************************************** Function to get Crop Type ****************************************** */

function getCropType(){
	$ddlCropType= $('.ddlCropType');
	//alert($cropTypeValue);
	$seasonValue = $.trim($('#ddlSeason option:selected').text());
	$val = $('#ddlNoofCrop option:selected').text();
$val = $.trim($val);
if($val!='No Crop'){
	
	if($val==='Single Crop'){
	$cropTypeValue =	$singleCropTypeVal;
		}else{
	if($seasonValue==='Rabi'){
		$cropTypeValue =$season1;		
	}else if($seasonValue==='Kharif'){
		$cropTypeValue =$season2;
	}else if($seasonValue==='Summer'){
		$cropTypeValue =$season3;
	}else{
		$cropTypeValue = new Array();
	}
	}
	}
	$.ajax({  
            url:"getCropType",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {console.log(data);
				$('#loading').hide();
				$ddlCropType.empty();
				$ddlCropType.append("<option value=''>---Select---</option>");
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							if ( $.inArray(key, $cropTypeValue) === -1){
								//$cropTypeValue.push(key);
								$ddlCropType.append("<option value='"+key+"'>"+data[key]+"</option>");
							}else{
								//alert('already added activity');
							}
							
						}
					}
				}
			}
		});
}
	
/******************************************************************* End ******************************************************* */

/************************************************************** On add row link click Script ************************************** */
$index=0;
$seasonArray = new Array();
$cropTypeValue = new Array();
$season1 = new Array();
$season2 = new Array();
$season3 = new Array();
$season1Area= 0;
$season2Area= 0;
$season3Area= 0;
$season1ProjectArea= 0;
$season2ProjectArea= 0;
$season3ProjectArea= 0;

$singleCropTypeVal = new Array();
$(document).on( 'click', 'a.add', function (e) {
	$cropArea= $('.cropArea').val();
	
	$cropProduction= $('.cropProduction').val();
	$avgIncome= $('.avgIncome').val();
	$totalIncome=$('.totalIncome').val();
	$cropType = $('.ddlCropType option:selected').text();
	$cropTypeVal = $('.ddlCropType').val();

	if($cropTypeVal===''){
		$('#cropTypeError').html('Please select Crop Type');
		$('#cropTypeError').css('color','red');
		$('.ddlCropType').focus();
		return false;
	}else{
		$('#cropTypeError').html('');
		if ( $.inArray($cropTypeVal, $cropTypeValue) === -1){
	$cropTypeValue.push($cropTypeVal);
	}else{
		//alert('already added activity');
	}
	}
	if($cropArea===''){
		$('#cropAreaError').html('Crop Area can not be blank');
		$('#cropAreaError').css('color','red');
		$('.cropArea').focus();
		return false;
	}else{
		$('#cropAreaError').html('');
	}
	if($cropProduction===''){
		$('#cropProductionError').html('Crop Production can not be blank');
		$('#cropProductionError').css('color','red');
		$('.cropProduction').focus();
		return false;
	}else{
		$('#cropProductionError').html('');
	}
	if($avgIncome===''){
		$('#avgIncomeError').html('Avg. Income can not be blank');
		$('#avgIncomeError').css('color','red');
		$('.avgIncome').focus();
		return false;
	}else{
		$('#avgIncomeError').html('');
	}
	if($totalIncome===''){
		$('.totalIncome').focus();
		return false;
	}
	
	
	$ddlSeasonVal=$('#ddlSeason').val();
	if($ddlSeasonVal===''){
		alert('Please select Season !');
		$('#ddlSeason').focus();
		return false;
	}
	
		$cropArea= $('.cropArea').val();
		$('#cropAreaError').html('');
		$projectArea = $('#projectArea').val();
		$totalCropArea=parseFloat($totalCropArea)+parseFloat($cropArea);
		if($totalCropArea>parseFloat($projectArea) ){
			$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
			$('#cropAreaError').css('color','red');
			$('.cropArea').focus();
			return false;
		}

		
	$season = $('#ddlSeason option:selected').text();
	$seasonVal = $('#ddlSeason').val();	
	$cropDeatilDiv = $('.cropDeatilDiv');
	$cropDeatilDiv.removeClass("d-none");
	$tblCropDeatilTbody = $('#tblCropDeatilTbody');
	$index++;
	$val = $('#ddlNoofCrop option:selected').text();
	$val = $.trim($val);
	
	if($val!='No Crop'){
		if($val==='Single Crop'){
$tbody ='<tr id="tr'+$index+'"><td><input type="hidden" id="cropTypeVal'+$index+'" name="cropTypeVal'+$index+'" class="cropTypeVal'+$index+' form-control" value="'+$cropTypeVal+'" />'+
	'<input type="text" id="cropType'+$index+'" name="cropType'+$index+'" class="cropType'+$index+' form-control" value="'+$cropType+'" readonly /></td>'+
	'<td><input type="text" id="cropArea'+$index+'" name="cropArea'+$index+'" class="cropArea'+$index+' form-control" value="'+$cropArea+'" readonly/></td>'+
	'<td><input type="text" id="cropProduction'+$index+'" name="cropProduction'+$index+'" class="cropProduction'+$index+' form-control" value="'+$cropProduction+'" readonly/></td>'+
	'<td><input type="text" id="avgIncome'+$index+'" name="avgIncome'+$index+'" class="avgIncome'+$index+' form-control" value="'+$avgIncome+'" readonly/></td>'+
	'<td><input type="text" id="totalIncome'+$index+'" name="totalIncome'+$index+'" class="totalIncome'+$index+' form-control" value="'+$totalIncome+'"  readonly /></td>'+
	'<td><a href="#" id="delete" name="delete" class="delete" >delete</a></td></tr>';
		}
		else{
$tbody ='<tr id="tr'+$index+'"><td><input type="hidden" id="seasonVal'+$index+'" name="seasonVal'+$index+'" class="seasonVal'+$index+' form-control" value="'+$seasonVal+'" />'+
	'<input type="text" id="season'+$index+'" name="season'+$index+'" class="season'+$index+' form-control" value="'+$season+'" readonly /></td>'+
	'<td><input type="hidden" id="cropTypeVal'+$index+'" name="cropTypeVal'+$index+'" class="cropTypeVal'+$index+' form-control" value="'+$cropTypeVal+'" />'+
	'<input type="text" id="cropType'+$index+'" name="cropType'+$index+'" class="cropType'+$index+' form-control" value="'+$cropType+'" readonly /></td>'+
	'<td><input type="text" id="cropArea'+$index+'" name="cropArea'+$index+'" class="cropArea'+$index+' form-control" value="'+$cropArea+'" readonly/></td>'+
	'<td><input type="text" id="cropProduction'+$index+'" name="cropProduction'+$index+'" class="cropProduction'+$index+' form-control" value="'+$cropProduction+'" readonly/></td>'+
	'<td><input type="text" id="avgIncome'+$index+'" name="avgIncome'+$index+'" class="avgIncome'+$index+' form-control" value="'+$avgIncome+'" readonly/></td>'+
	'<td><input type="text" id="totalIncome'+$index+'" name="totalIncome'+$index+'" class="totalIncome'+$index+' form-control" value="'+$totalIncome+'"  readonly /></td>'+
	'<td><a href="#" id="delete" name="delete" class="delete" >delete</a></td></tr>';
		}
		
	}
	
	
	$tblCropDeatilTbody.append($tbody);
	
	$('.cropArea').val('');
	$('.cropProduction').val('');
	$('.avgIncome').val('');
	$('.totalIncome').val('');
	$('.ddlCropType').val('');
	$('#draftSave').removeClass('d-none');
$oldcropArea=0;
$val = $('#ddlNoofCrop option:selected').text();
$val = $.trim($val);
if($val!='No Crop'){
	
	if($val==='Single Crop'){
		if ( $.inArray($cropTypeVal, $singleCropTypeVal) === -1){
			$singleCropTypeVal.push($cropTypeVal);
	}else{
		//alert('already added activity');
	}
		}else{
$seasonValue = $.trim($('#ddlSeason option:selected').text());
	if($seasonValue==='Rabi'){
		if ( $.inArray($cropTypeVal, $season1) === -1){
	$season1.push($cropTypeVal);
	}else{
		//alert('already added activity');
	}
	$season1Area=parseFloat($season1Area)+parseFloat($cropArea);
	$season1ProjectArea	=$season1ProjectArea+$projectArea;
	}
	if($seasonValue==='Kharif'){
		if ( $.inArray($cropTypeVal, $season2) === -1){
	$season2.push($cropTypeVal);
	}else{
		//alert('already added activity');
	}
	$season2Area=parseFloat($season2Area)+parseFloat($cropArea);
	$season2ProjectArea	=$season2ProjectArea+$projectArea;	
	}
	if($seasonValue==='Summer'){
		if ( $.inArray($cropTypeVal, $season3) === -1){
	$season3.push($cropTypeVal);
	}else{
		//alert('already added activity');
	}
	$season3Area=parseFloat($season3Area)+parseFloat($cropArea);
	$season3ProjectArea	=$season3ProjectArea+$projectArea;	
	}
	}
	}
getCropType();
});

/******************************************************************** End ******************************************************** */	

/**************************************************** No of Crop Change Script ******************************* */

$(document).on( 'change', '#ddlSeason', function (e) {
	$('#loading').show();
	$totalCropArea=0;
	$seasonValue = $.trim($('#ddlSeason option:selected').text());
	
	if($seasonValue==='Rabi'){
		$cropTypeValue =$season1;
		$totalCropArea=$season1Area;		
	}else if($seasonValue==='Kharif'){
		$cropTypeValue =$season2;
		$totalCropArea=$season2Area;
	}else if($seasonValue==='Summer'){
		$cropTypeValue =$season3;
		$totalCropArea=$season3Area;
	}else{
		$cropTypeValue = new Array();
		$totalCropArea=0;
	}
	console.log($seasonValue+$totalCropArea);
	getCropType();
	//$index=1;
	/*$cropDeatilDiv = $('.cropDeatilDiv');
	$tblCropDeatilTbody = $('#tblCropDeatilTbody');
	$tblCropDeatilTbody.empty();
	$cropDeatilDiv.addClass("d-none");
	$('#draftSave').addClass('d-none');*/
	$('#loading').hide();
});	
/********************************************************** End ********************************************************* */

/************************************************************** On Delete row link click Script ************************************** */

$(document).on( 'click', 'a.delete', function (e) {
	
	
	$index--;
	$rowId = $(this).closest('tr').attr('id') ;
	$rowId = $rowId.match(/[\d\.]+/g);
	
	$val = $('#ddlNoofCrop option:selected').text();
$val = $.trim($val);
if($val!='No Crop'){
	
	if($val==='Single Crop'){
		$singleCropTypeVal=jQuery.grep($singleCropTypeVal, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});
	$cropTypeValue =	$singleCropTypeVal;
	//$projectArea=$projectArea+parseFloat($('#cropArea'+$rowId).val());
	$totalCropArea=$totalCropArea-parseFloat($('#cropArea'+$rowId).val());
		}else{
if($('#season'+$rowId).val()==='Rabi'){
	$season1=jQuery.grep($season1, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});
$cropTypeValue =$season1;	
	//$season1ProjectArea=$season1ProjectArea+parseFloat($('#cropArea'+$rowId).val());
	$season1Area=$season1Area-parseFloat($('#cropArea'+$rowId).val());
	//$totalCropArea=$season1Area;
	//$projectArea = $season1ProjectArea;
	}else if($('#season'+$rowId).val()==='Kharif'){
		$season2=jQuery.grep($season2, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});	
$cropTypeValue =$season2;
	//$season2ProjectArea=$season2ProjectArea+parseFloat($('#cropArea'+$rowId).val());
	$season2Area=$season2Area-parseFloat($('#cropArea'+$rowId).val());
	//$totalCropArea=$season2Area;
	//$projectArea = $season2ProjectArea;
	}else if($('#season'+$rowId).val()==='Summer'){
		$season3=jQuery.grep($season3, function(value) {
  return value != $('#cropTypeVal'+$rowId).val();
});	
$cropTypeValue =$season3;
	//$season3ProjectArea=$season3ProjectArea+parseFloat($('#cropArea'+$rowId).val());
	$season3Area=$season3Area-parseFloat($('#cropArea'+$rowId).val());
	
	//$projectArea = $season3ProjectArea;
	}else{
		$cropTypeValue = new Array();
	}
	}
	}
	//alert($cropTypeValue+"----"+$('#season'+$rowId).val()+"===="+$('#cropTypeVal'+$rowId).val()+"====="+$rowId);
	getCropType();
	//$totalCropArea=$totalCropArea-$('#cropArea'+$rowId).val();
	console.log('croptypeval'+$('#cropTypeVal'+$rowId).val());
	$(this).closest('tr').remove();
for(var x=parseInt($rowId);x<=parseInt($index);x++){
	$('#tr'+(parseInt(x)+1)).attr("id","tr"+x);
	$('#seasonVal'+(parseInt(x)+1)).attr("id","seasonVal"+x);
	$('#season'+(parseInt(x)+1)).attr("id","season"+x);
	$('#cropTypeVal'+(parseInt(x)+1)).attr("id","cropTypeVal"+x);
	$('#cropType'+(parseInt(x)+1)).attr("id","cropType"+x);
	$('#cropArea'+(parseInt(x)+1)).attr("id","cropArea"+x);
	$('#cropProduction'+(parseInt(x)+1)).attr("id","cropProduction"+x);
	$('#avgIncome'+(parseInt(x)+1)).attr("id","avgIncome"+x);
	$('#totalIncome'+(parseInt(x)+1)).attr("id","totalIncome"+x);
	$("#tr"+(parseInt(x)+1)).attr("id","tr"+x);
}
	if($index==0)
	$('#draftSave').addClass('d-none');
});

/******************************************************************** End ******************************************************** */


/************************************************************** SaveasDraft Button click Script ************************************** */

$(document).on( 'click', '#draftSave', function (e) {
	e.preventDefault();
	$noOfCrops =$('#ddlNoofCrop').find(":selected").text();
	if(($noOfCrops.trim()==='Double Crop'||$noOfCrops.trim()==='Multiple Crop')?confirm('Have you filled all season data of selected village ?'):true){
	$cropTypeVal = new Array();
	$seasonVal = new Array();
	$cropArea = new Array();
	$cropProduction = new Array();
	$avgIncome = new Array();
	
	$('#tblCropDeatilTbody').find('tr').each(function(){
		$rowId = $(this).closest('tr').attr('id') ;
	$rowId = $rowId.match(/[\d\.]+/g);
		$cropTypeVal.push($('#cropTypeVal'+$rowId).val());
		if($seasonArray.length>0)
		$seasonVal.push($('#seasonVal'+$rowId).val());
		$cropArea.push($('#cropArea'+$rowId).val());
		$cropProduction.push($('#cropProduction'+$rowId).val());
		$avgIncome.push($('#avgIncome'+$rowId).val());
	});
if(!($($seasonVal).not($seasonArray).length === 0 &&  $($seasonArray).not($seasonVal).length === 0)){
	alert('Please fill all season data first. Then try again !');
//	$('#draftSave').removeAttr('disabled');
	$('#ddlSeason').focus();
	return false;
}

	$projId = $('#project').val();
	$vcode = $('#village').val();
	$plotNo = $('#plotno').val();
	$projectArea= $('#projectArea').val();
	$irrigationStatus = $('#ddlIrrigationStatus').val();
	$ownership = $('#ddlOwnership').val();
	$landClassification = $('#ddlLandClass').val();
//	line no. 753 added by yogesh
	$landSubClassification = $('#landSubClass').val();
	$noOfCrop = $('#ddlNoofCrop').val();
	//$season = $('#ddlSeason').val();
	$ownerName = $('#ownername').val();
	$forestLandType=$('#ddlForestlandType').val();
	
	if($projId===''){
	$('.projError').html('Please select Project');
	$('#project').focus();
	return false;
	}else{
		$('.projError').html('');
	}
	
	if($vcode===''){
	$('.villageError').html('Please select Village');
	$('#village').focus();
	return false;
	}else{
		$('.villageError').html('');
	}
	
	if($plotNo==='' ){
	alert('please enter the value for PlotNo');
	$('.plotnoError').html('Please insert Plot No');
	$('.plotnoError').css('color','red');
	$('#plotno').val('');
	$('#plotno').focus();
	return false;
	}else{
		$('.plotnoError').html('');
	}
	
	if($projectArea===''){
		alert('please enter the value for PlotArea');
		$('.projectAreaError').html('Please enter the Project Area');
		$('.projectAreaError').css('color','red');
		$('#plotno').val('');
		$('#projectArea').focus();
		return false;
	}else{
		$('.projectAreaError').html('');
	}
	
	if($irrigationStatus===''){
	$('.ddlIrrigationStatusError').html('Please select Irrigation Status');
	$('.ddlIrrigationStatusError').css('color','red');
	$('#ddlIrrigationStatus').focus();
	return false;
	}else{
		$('.ddlIrrigationStatusError').html('');
	}
	if($ownership===''){
	$('.ownershipError').html('Please select Ownership');
	$('.ownershipError').css('color','red');
	$('#ddlOwnership').focus();
	return false;
	}else{
		$('.ownershipError').html('');
		if($('#ddlOwnership option:selected').text()==='Private' && $ownerName===''){
	$('.ownernameError').html('Please Enter Owner Name');
	$('.ownernameError').css('color','red');
	$('#ownername').focus();
	return false;
	}else{
		$('.ownernameError').html('');
	}
	}
	if($landClassification===''){
	$('.landClassificationError').html('Please select Land Classification');
	$('.landClassificationError').css('color','red');
	$('#ddlLandClass').focus();
	return false;
	}else{
		if($('#ddlLandClass option:selected').text()==='Others' && $landSubClassification===''){
	$('.landSubClassError').html('Please Enter SubClassification of Land');
	$('.landSubClassError').css('color','red');
	$('#landSubClass').focus();
	return false;
	}
	if($('#ddlLandClass option:selected').text()==='Forest Land' && $forestLandType===''){
	$('.ddlForestlandTypeError').html('Please select Forest Land Type');
	$('.ddlForestlandTypeError').css('color','red');
	$('#ddlForestlandType').focus();
	return false;
	}
	if($('#ddlLandClass option:selected').text()!='Forest Land' && $noOfCrop===''){
	$('.ddlNoofCropError').html('Please select No. of Crop');
	$('.ddlNoofCropError').css('color','red');
	$('#ddlNoofCrop').focus();
	return false;
	}
	if($('#ddlLandClass option:selected').text()!='Forest Land' && $forestLandType!=''){
		$forestLandType = null;
	}
	}
	
	
	if(typeof $landSubClassification === 'undefined')
	$landSubClassification=null;
	
	if(typeof $forestLandType === 'undefined')
	$forestLandType=null;
	
	if(typeof $ownerName === 'undefined')
	$ownerName=null;
	
	if(typeof $seasonVal === 'undefined')
	$seasonVal=null;
	
	$('#draftSave').attr('disabled','disabled');
	
	console.log('cropType: '+$cropTypeVal+', cropArea: '+$cropArea+', cropProduction: '+$cropProduction+', avgIncome: '+$avgIncome+', project: '+$projId+', vcode: '+$vcode+', plotno: '+$plotNo+', projectArea: '+$projectArea+', irrigationStatus: '+$irrigationStatus+', ownership: '+$ownership+', landClassification: '+$landClassification+', landSubClassification: '+$landSubClassification+', noOfCrop: '+$noOfCrop+', season: '+$seasonVal+', ownerName: '+$ownerName+', ForestLandType: '+$forestLandType);
	$('.error').html('');
	//alert('KDY');   
	
	$.ajax({  
            url:"getPlotDataOfAVillage",
            type: "post",  
            data: {vcode:$vcode,plotno:$plotNo,projid:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
			console.log(data);
			if(Object.keys(data).length>0){
					$('.plotnoError').html('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
					$('.plotnoError').css('color','red');
					$('#plotno').val('');
					$('#plotno').focus();
					return false;
			}else{
				$.ajax({  
            		url:"saveAsDraftBLS",
            		type: "post",  
            		data: {projId:$projId,vcode:$vcode,plotNo:$plotNo,projectArea:$projectArea,irrigationStatus:$irrigationStatus,ownership:$ownership,ownerName:$ownerName,landClassification:$landClassification,landSubClassification:$landSubClassification,noOfCrop:$noOfCrop,season:$seasonVal.toString(),cropType:$cropTypeVal.toString(),cropArea:$cropArea.toString(),cropProduction:$cropProduction.toString(),avgIncome:$avgIncome.toString(),forestLandType:$forestLandType},
            			error:function(xhr,status,er){
                			console.log(er);
							$('#loading').hide();
							$('.error').append(' There is some error please try again !');
            			},
           				success: function(data) {console.log(data);
						$('#loading').hide();
						if(data==='success'){
							alert('Data saved successfully !');
							window.location.href='blsout';
						}
			
					else
						alert('Data not saved !');
				}
			});
				}
			}

		});
		}
	
});

/******************************************************************** End ******************************************************** */

/****************************************************** Area check script ********************************************* */
$totalCropArea=0;
$oldcropArea=0;
$(document).on( 'blur', '.cropArea', function (e) {
	
	$cropArea= $('.cropArea').val();
	$oldcropArea=$totalCropArea;
	$('#cropAreaError').html('');
	$projectArea = $('#projectArea').val();
	if(parseFloat($cropArea)===0 || parseFloat($cropArea)===.0 || parseFloat($cropArea)===0.0){
		$('#cropAreaError').html('Crop Area can not be zero');
		$('#cropAreaError').css('color','red');
		$('.cropArea').val('');
		$('.cropArea').focus();
		return false;
	}
	if($projectArea ==='' || typeof $projectArea  ==='undefined' || parseFloat($projectArea)==='NaN' ){
		$('.projectAreaError').html('Please provide project area first');
		$('.projectAreaError').css('color','red');
		$('#projectArea').focus();
		return false;
	}
	
	$val = $('#ddlNoofCrop option:selected').text();
	$val = $.trim($val);
if($val!='No Crop'){
	if($val==='Single Crop'){
		
		}else{
if($('#ddlSeason option:selected').text()==='Rabi'){
	$totalCropArea=$season1Area;
	//$projectArea = $season1ProjectArea;
	}else if($('#ddlSeason option:selected').text()==='Kharif'){
		$totalCropArea=$season2Area;
	//$projectArea = $season2ProjectArea;
	}else if($('#ddlSeason option:selected').text()==='Summer'){
		$totalCropArea=$season3Area;
	//$projectArea = $season3ProjectArea;
	}else{
		//$cropTypeValue = new Array();
	}
	}
	}
	console.log('total crop area---'+$totalCropArea+' crop area '+$cropArea+ 'project area ---'+$projectArea);
	if(parseFloat($cropArea)>parseFloat($projectArea) ){
		$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
		$('#cropAreaError').css('color','red');
		$('.cropArea').focus();
		return false;
	}else{
		if((parseFloat($totalCropArea)+parseFloat($cropArea))>parseFloat($projectArea)){
		$('#cropAreaError').html('Crop Area can not be greater than Plot Area');
		$('#cropAreaError').css('color','red');
		$('.cropArea').focus();
		return false;
		}else{
			//$totalCropArea=parseFloat($oldcropArea)+parseFloat($cropArea);
		}

		//$('#cropAreaError').html('else croparea '+$cropArea+' oldcroparea '+$oldcropArea+' totalCropArea '+$totalCropArea+' projectArea '+$projectArea );
		}
	
});

/********************************************************* End ******************************************************** */

/************************************************* Plot Check Script ****************************************** */
$(document).on( 'blur', '#plotno', function (e) {
//	alert('kdy');
	e.preventDefault();
	let pattern = /[\\.,$=#`~@!"['^&:;%_>?+<|()\t ]/;
	$plotNo = $('#plotno').val();
	$vcode = $('#village').val();
	$proj = $('#project').val();
	if($vcode===''){
		$('.plotnoError').append('<p style="color: red;"> please select the project and village first</p>');
		$('#plotno').val('');
		$('#village').focus();
		return false;
	}else{
		$('.error').html('');
	}
	
	if(pattern.test($plotNo)){
		$('.plotnoError').html('Please insert the valid PlotNo, space and special character are not allowed except of "-","*" and "/"');
		$('.plotnoError').css('color','red');
		$('#plotno').val('');
		$('#plotno').focus();
		return false;
	}else{
		$('.plotnoError').html('');
	}
	$.ajax({  
            url:"getPlotDataOfAVillage",
            type: "post",  
            data: {vcode:$vcode,plotno:$plotNo,projid:$proj},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
//				$('#plotno').val('');
//				$('#plotno').focus();
            },
            success: function(data) {
	console.log(data);
	if(Object.keys(data).length>0){
					/*for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$('#projectArea').val(data[key].area);
							$('#ddlIrrigationStatus').val(data[key].irrigation_status_id);
							$('#ddlOwnership').val(data[key].ownership_id);
							$('#ddlLandClass').val(data[key].classification_land_id);
							$('.ddlLandClass').trigger('change');
							$('#ddlNoofCrop').val(data[key].no_of_crop_id);
							$('#ownername').val(data[key].owner_name);
							$('#ddlForestlandType').val(data[key].classification_land_id);
						}
					}*/
					
					alert('Data for Plot No '+$plotNo+' has been already saved as draft. Kindly delete it first, to enter it`s detail again.');
					$('#plotno').val('');
					$('#plotno').focus();
				}else{
					$('#projectArea').val('');
				//	alert('kedar');
					$('#ddlIrrigationStatus').val('');
				}

}

});
	
	
});	

/***************************************************** End **************************************************** */

/******************************************************* To Check Decimal Value in Project Area ********************************** */
//$('#projectArea').on('input', function () {
//        this.value = this.value.match(/^\d+\.?\d{0,4}/);
//    });

/******************************************************* Treatable Area Check Script ********************************** */

$(document).on( 'blur', '#projectArea', function (e) {
	e.preventDefault();
	$projId = $('#project').val();
	$projectArea = $(this).val();
	$plotArea = $('#projectArea').val();
//	alert($plotArea);
	if(parseFloat($plotArea)===0){
		$('.projectAreaError').html('Please enter the valid Project Area');
		$('.projectAreaError').css('color','red');
		$('#projectArea').val('');
		$('#projectArea').focus();
		return false;
	}else{
		$('.projectAreaError').html('');
	}
	$.ajax({  
            url:"getProjectSanctionedArea",
            type: "post",  
            data: {projId:$projId},
            error:function(xhr,status,er){
                console.log(er);
				$('#loading').hide();
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
            	console.log(data+'---'+$projectArea);
		if(0>parseFloat(data)){
			$('.projectAreaError').html('Plot area already exceed the limit as per project sanctioned area.');
			$('.projectAreaError').css('color','red');
			$('#projectArea').focus();
			return false;
		}
	
		if(0<parseFloat(data))
		if(parseFloat($projectArea)>parseFloat(data)){
			$('.projectAreaError').html('Plot area can not be greater than sanctioned project area.');
			$('.projectAreaError').css('color','red');
			$('#projectArea').focus();
			return false;
		
		}else{
			$('.projectAreaError').html('');
		}
		}
		
		});
	
	
	
});

/*********************************************************** End ********************************************** */

});

/*************************************************** Function to get Total Income ****************************************** */

function getTotalIncome(){
	$cropArea= $('.cropArea').val();
	$cropProduction= $('.cropProduction').val();
	$avgIncome= $('.avgIncome').val();
	
	$('.totalIncome').val(parseFloat($cropArea*$cropProduction*$avgIncome).toFixed(2));
}
	
/******************************************************************* End ******************************************************* */	

/***************************************************** Excel Button Click ************************************* */
function exportExcel(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note='';//"* All area in hactare and All amounts are Rs. in Lakh";
	var headerRow =2;
	var tableName = "#tblReport";
	var colLength=11;//$(tableName+" tr:nth-child(1) th").length;
	
	/*if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";*/

	var fileName="Format SB1- Plot-wise Details of BaseLine Survey";
	 
	var reportName = "Format SB1- Plot-wise Details of BaseLine Survey";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    "<tr><td>State: "+$('#state option:selected').text()+"</td></tr></table>";
	  
	 //exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
fnExcelReport('tblReport', fileName,headerHtml,colLength,jsDate,headerRow);
}

function exportExcelDistrict(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note='';//"* All area in hactare and All amounts are Rs. in Lakh";
	var headerRow =2;
	var tableName = "#tblReport";
	var colLength=11;//$(tableName+" tr:nth-child(1) th").length;
	
	/*if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";*/

	var fileName="Format SB1- Plot-wise Details of BaseLine Survey";
	 
	var reportName = "Format SB1- Plot-wise Details of BaseLine Survey";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    "</table>";
	  
	 //exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
fnExcelReport('tblReport', fileName,headerHtml,colLength,jsDate,headerRow);
}

function exportExcelProject(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note='';//"* All area in hactare and All amounts are Rs. in Lakh";
	var headerRow =2;
	var tableName = "#tblReport";
	var colLength=11;//$(tableName+" tr:nth-child(1) th").length;
	
	/*if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";*/

	var fileName="Format SB1- Plot-wise Details of BaseLine Survey";
	 
	var reportName = "Format SB1- Plot-wise Details of BaseLine Survey";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    "</table>";
	  
	 //exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
fnExcelReport('tblReport', fileName,headerHtml,colLength,jsDate,headerRow);
}

function exportExcelDetail(){
	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var specialNote = true;
	var note='';//"* All area in hactare and All amounts are Rs. in Lakh";
	var headerRow =2;
	var tableName = "#tblReport";
	var colLength=11;//$(tableName+" tr:nth-child(1) th").length;
	
	/*if(specialNote && typeof $('.text-danger').html() != "undefined")
	 note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";*/

	var fileName="Format SB1- Plot-wise Details of BaseLine Survey";
	 
	var reportName = "Format SB1- Plot-wise Details of BaseLine Survey";
	var headerHtml = "<table>"+
	 "<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
	    "<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
	    "</table>";
	  
	 //exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);
fnExcelReport1('tblReport', fileName,headerHtml,colLength,jsDate,headerRow);
}