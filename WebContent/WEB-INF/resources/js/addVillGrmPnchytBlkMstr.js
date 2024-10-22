/************************************************On Select State ********************************************/
$('#state').on('change',function(){
	$state = $('#state').val();
	if($state ===""){
		$district = $('#district');
		$district.empty();
		$district.append('<option value="">--Select--</option>');
		return;	
	}
	$.ajax({  
            url:"getDistrictsList",
            type: "post",  
            data: {stateCode:$state},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$district = $('#district');
				$block = $('#block');
				$district.empty();
				$district.append('<option value="">--Select--</option>');
				$block.empty();
				$block.append('<option value="" selected>--Select--</option>');
				if(Object.keys(data).length>0){
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$district.append('<option value="'+data[key]+'">'+key+'</option>');
				}
			}
			}
		}
	});
	
});

/************************************************On blur lgdCode ********************************************/
$(document).on('blur','#lgdCode',function(){
	$addMaster = $('#addMaster').val();
	$lgdCode = $('#lgdCode').val();
//	$.ajax({
//		url:"checkAlreadyExistlgdCode",
//		type: "post",
//		data:{lgdCode:$lgdCode,masterId:$addMaster},
//		error:function(xhr,status,er){
//			console.log(er);
//			$('.error').append('There is some error please try again !');
//		},
//		success:function(data){
//			console.log(data);
//			if(data===1){
//				alert('lgdCode is already exist');
//				$('#villGrmBlk').attr('disabled','disabled');
//				$('#saveAsDraft').attr('disabled','disabled');
//			}else{
//				$('#villGrmBlk').removeAttr('disabled');
//				$('#saveAsDraft').removeAttr('disabled');
//			}
//		}
//		
//	});
//	$state = $('#state').val();
//	$district = $('#district').val();
	$.ajax({
		url:"getTableDatabylgdCode",
		type: "post",
		data:{lgdCode:$lgdCode,masterId:$addMaster},
		error:function(xhr,status,er){
			console.log(er);
			$('.error').append('There is some error please try again !');
		},
		success:function(data){
			console.log(data);
			$tbody = $('#tbodylgdCodeList');
			$tbody.empty();
			var i = 1;
			if(Object.keys(data).length>0){
				alert('lgdCode is already exist');
				$('#villGrmBlk').attr('disabled','disabled');
				$('#saveAsDraft').attr('disabled','disabled');
				for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					if($addMaster==='1'){
						$tbody.append('<tr><td>'+i+'</td><td>'+data[key].statename+'</td><td>'+data[key].statelgdcode+'</td><td>'+data[key].districtname+'</td><td>'+data[key].districtlgdcode+'</td><td>'+data[key].blockname+'</td><td>'
						+data[key].blocklgdcode+'</td><td>'+data[key].grampanchayatname+'</td><td>'+data[key].grampanchayatlgdcode+'</td><td>'+data[key].villagename+'</td><td>'+$lgdCode+'</td><td>'+data[key].status+'</td></tr>');
					}
					if($addMaster==='2'){
						$tbody.append('<tr><td>'+i+'</td><td>'+data[key].statename+'</td><td>'+data[key].statelgdcode+'</td><td>'+data[key].districtname+'</td><td>'+data[key].districtlgdcode+'</td><td>'+data[key].blockname+'</td><td>'
						+data[key].blocklgdcode+'</td><td>'+data[key].grampanchayatname+'</td><td>'+data[key].grampanchayatlgdcode+'</td><td>'+data[key].status+'</td></tr>');
					}
					if($addMaster==='3'){
						$tbody.append('<tr><td>'+i+'</td><td>'+data[key].statename+'</td><td>'+data[key].statelgdcode+'</td><td>'+data[key].districtname+'</td><td>'+data[key].districtlgdcode+'</td><td>'+data[key].blockname+'</td><td>'
						+data[key].blocklgdcode+'</td><td>'+data[key].status+'</td></tr>');
					}
					i++;
				}
			}
			}else{
				$('#villGrmBlk').removeAttr('disabled');
				$('#saveAsDraft').removeAttr('disabled');
			}
		}
		
	});
});

/************************************************On Select Master(Vill/GrmPnnchyt/Block) ********************************************/
$(document).on('change','#addMaster',function(){
	$addMaster = $('#addMaster').val();
	if($addMaster ===""){
		window.location.reload();
	}	
	$('#lgdCode').val('');
	$('#villGrmBlk').val('');
	document.getElementById("lgdCodeLable").style.display = "inline";
	document.getElementById("lgdCode").style.display = "inline";
	$('#villGrmBlk').removeAttr('disabled');
	$('#saveAsDraft').removeAttr('disabled');
	
	$.ajax({
		url:"getAllStatesList",
		type: "post",
		data:{},
		error:function(xhr,status,er){
			console.log(er);
			$('.error').append('There is some error please try again !');
		},
		success:function(data){
			console.log(data);
			$state = $('#state');
			$district = $('#district');
			$block = $('#block');
				$state.empty();
				$state.append('<option value="">--Select--</option>');
				$district.empty();
				$district.append('<option value="" selected>--Select--</option>');
				$block.empty();
				$block.append('<option value="" selected>--Select--</option>');
				if(Object.keys(data).length>0){
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$state.append('<option value="'+data[key]+'">'+key+'</option>');
				}
			}
			}
		}
		
	});
	
	$tbody = $('#tbodylgdCodeList');
	$tbody.empty();
	$thead = $('#theadlist');
	$thead.empty();
	if($addMaster==='1'){
		$('#gramPanchayat').removeAttr('disabled');
		document.getElementById("blockLabel").style.display = "inline";
		document.getElementById("block").style.display = "inline";
		document.getElementById("gpLabel").style.display = "inline";
		document.getElementById("gramPanchayat").style.display = "inline";
		document.getElementById("villLgdCode").style.display = "inline";
		document.getElementById("gpLgdCode").style.display = "none";
		document.getElementById("blkLgdCode").style.display = "none";
		document.getElementById("villGrmBlk").style.display = "inline";
		$thead.append('<tr><th >S.No.</th><th>State Name</th><th>State LGD Code</th><th>District Name</th><th>District LGD Code</th>'
		+'<th class="text-center blockName">Block Name</th>'
		+'<th class="text-center blockLgdCode">Block LGD Code</th>'
		+'<th class="text-center grmPnchytName">Gram Panchayat Name</th>'
		+'<th class="text-center grmPnchytLgdCode">Gram Panchayat LGD Code</th>'
		+'<th class="text-center villName">Village Name</th>'
		+'<th class="text-center villLgdCode">Village LGD Code</th>'
		+'<th>Status</th></tr>');
	}
	if($addMaster==='2'){
		document.getElementById("blockLabel").style.display = "inline";
		document.getElementById("block").style.display = "inline";
		document.getElementById("gpLabel").style.display = "none";
		document.getElementById("gramPanchayat").style.display = "none";
		document.getElementById("villLgdCode").style.display = "none";
		document.getElementById("gpLgdCode").style.display = "inline";
		document.getElementById("blkLgdCode").style.display = "none";
		document.getElementById("villGrmBlk").style.display = "inline";
		$thead.append('<tr><th >S.No.</th><th>State Name</th><th>State LGD Code</th><th>District Name</th><th>District LGD Code</th>'
		+'<th class="text-center blockName">Block Name</th>'
		+'<th class="text-center blockLgdCode">Block LGD Code</th>'
		+'<th class="text-center grmPnchytName">Gram Panchayat Name</th>'
		+'<th class="text-center grmPnchytLgdCode">Gram Panchayat LGD Code</th>'
		+'<th class="text-center villName d-none">Village Name</th>'
		+'<th class="text-center villLgdCode d-none">Village LGD Code</th>'
		+'<th>Status</th></tr>');
	}
	if($addMaster==='3'){
		document.getElementById("blockLabel").style.display = "none";
		document.getElementById("block").style.display = "none";
		document.getElementById("gpLabel").style.display = "none";
		document.getElementById("gramPanchayat").style.display = "none";
		document.getElementById("villLgdCode").style.display = "none";
		document.getElementById("gpLgdCode").style.display = "none";
		document.getElementById("blkLgdCode").style.display = "inline";
		document.getElementById("villGrmBlk").style.display = "inline";
		$thead.append('<tr><th >S.No.</th><th>State Name</th><th>State LGD Code</th><th>District Name</th><th>District LGD Code</th>'
		+'<th class="text-center blockName">Block Name</th>'
		+'<th class="text-center blockLgdCode">Block LGD Code</th>'
		+'<th class="text-center grmPnchytName d-none">Gram Panchayat Name</th>'
		+'<th class="text-center grmPnchytLgdCode d-none">Gram Panchayat LGD Code</th>'
		+'<th class="text-center villName d-none">Village Name</th>'
		+'<th class="text-center villLgdCode d-none">Village LGD Code</th>'
		+'<th>Status</th></tr>');
	}
});

/************************************************On Click Save Button ********************************************/
$(document).on('click','#saveAsDraft',function(e){
	e.preventDefault();
	$addMaster = $('#addMaster').val();
	$state = $('#state').val();
	$district = $('#district').val();
	$block = $('#block').val();
	$grmPnchyt = $('#gramPanchayat').val();
	$lgdCode = $('#lgdCode').val();
	$villGrmBlk = $('#villGrmBlk').val();
	
	if($addMaster==='' ){
	$('.addMasterError').html('please select the value for Add Master');
	$('.addMasterError').css('color','red');
	$('#addMaster').focus();
	return false;
	}else{
		$('.addMasterError').html('');
	}
	
	if($state==='' ){
	$('.stateError').html('please select the State');
	$('.stateError').css('color','red');
	$('#state').focus();
	return false;
	}else{
		$('.stateError').html('');
	}
	
	if($district==='' ){
	$('.districtError').html('please select the District');
	$('.districtError').css('color','red');
	$('#district').focus();
	return false;
	}else{
		$('.districtError').html('');
	}
	
	if($block==='' && ($addMaster==='1' || $addMaster==='2')){
	$('.blockError').html('please select the Block');
	$('.blockError').css('color','red');
	$('#block').focus();
	return false;
	}else{
		$('.blockError').html('');
	}
	
	if($grmPnchyt==='' && $addMaster==='1' ){
	$('.gramPanchayatError').html('please select the Gram Panchayat');
	$('.gramPanchayatError').css('color','red');
	$('#gramPanchayat').focus();
	return false;
	}else{
		$('.gramPanchayatError').html('');
	}
	
	if($lgdCode==='' ){
	$('.lgdCodeError').html('please insert the LGD Code');
	$('.lgdCodeError').css('color','red');
	$('#lgdCode').focus();
	return false;
	}else{
		$('.lgdCodeError').html('');
	}
	
	if($villGrmBlk==='' ){
	$('.villGrmBlkError').html('please fill the text field');
	$('.villGrmBlkError').css('color','red');
	$('#villGrmBlk').focus();
	return false;
	}else{
		$('.villGrmBlkError').html('');
	}

	if($addMaster ==='2'){
		$grmPnchyt = '0';
	}if($addMaster ==='3'){
		$grmPnchyt = '0';
		$block = '0';
	}
	
//	if(typeof $grmPnchyt === 'undefined')
//		$grmPnchyt=null;
		
	$.ajax({
		url :"saveVillGrmBlkAsDraft",
		type: "post",
		data: {masterId:$addMaster,state:$state,district:$district,block:$block,grmPnchyt:$grmPnchyt,lgdCode:$lgdCode,villGrmBlk:$villGrmBlk},
		error:function(xhr,status,er){
			$('.error').append('There is some error please try again !')
		},
		success:function(data){
			console.log(data);
			$('#loading').hide();
			if(data ==='success'){
				if($addMaster==='1'){
					alert('Data for Village '+$villGrmBlk+' is successfully saved');
				}if($addMaster==='2'){
					alert('Data for Gram Panchyat '+$villGrmBlk+' is successfully saved');
				}if($addMaster==='3'){
					alert('Data for Block '+$villGrmBlk+' is successfully saved');
				}
				window.location.href='addBlockGramPanchayatVillage';
			}
			else
				alert('Data not saved !');
		}
		
		
	});
	
});

/************************************************On Select District ********************************************/
$('#district').on('change',function(){
	$addMaster = $('#addMaster').val();
	$state = $('#state').val();
	$district = $('#district').val();
	if($district ===""){
		$block = $('#block');
		$block.empty();
		$block.append('<option value="">--Select--</option>');
		return;
	}
		$.ajax({  
            url:"getBlocksList",
            type: "post",  
            data: {distCode:$district},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$block = $('#block');
				$gramPanchayat = $('#gramPanchayat');
				$block.empty();
				$block.append('<option value="">--Select--</option>');
				$gramPanchayat.empty();
				$gramPanchayat.append('<option value="" selected>--Select--</option>');
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$block.append('<option value="'+data[key]+'">'+key+'</option>');
						}
					}
				}
			}
		});
	
});

/************************************************On Select Block ********************************************/
$('#block').on('change',function(){
	$addMaster = $('#addMaster').val();
	$block = $('#block').val();
	if($block ===""){
		$gramPanchayat = $('#gramPanchayat');
		$gramPanchayat.empty();
		$gramPanchayat.append('<option value="">--Select--</option>');
		return;
	}
		$.ajax({  
            url:"getGpList",
            type: "post",  
            data: {blockCode:$block},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$gramPanchayat = $('#gramPanchayat');
				$gramPanchayat.empty();
				$gramPanchayat.append('<option value="">--Select--</option>');
				if(Object.keys(data).length>0){
					for ( var key in data) {
						if (data.hasOwnProperty(key)) {
							$gramPanchayat.append('<option value="'+data[key]+'">'+key+'</option>');
						}
					}
				}
			}
		});
	
});

