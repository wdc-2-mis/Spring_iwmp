$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	
	$('table .edit').on('click', function() {
	//alert('hi');
    var id = $(this).parent().find('#id').val();
    var lvl = $(this).parent().find('#lvl').val();
 $.ajax({
	type: 'GET',
	url: "mstrvilldatafind",
	data: {id:id, lvl:lvl},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
	$('#lgdv').val(data[0]);
	$('#villageName1').val(data[1]);
	$('#levelv').val(data[2]);
	$.ajax({
		type: 'POST',
		url: "getGPListWithLgdCode",
		data: {id:id, lvl:lvl},
		error:function(xhr,status,er){
                console.log(er);
            },
	
		success: function(d){
		$grmPnchyt = $('#grmPnchyt');
		$grmPnchyt.empty();
		$grmPnchyt.append('<option value ="0">--Select--</option>');
		for ( var k in d) {
			if (d.hasOwnProperty(k)) {
//				alert(d[k]==data[3]);
				if(d[k]==data[3]){
					$grmPnchyt.append('<option value ="'+d[k]+'" selected>'+k+'</option>');
				}else{
					$grmPnchyt.append('<option value ="'+d[k]+'">'+k+'</option>');
				}
				
			}
		}
		
		}
	});
		
	}
	
	});
	});
	
$('table .editdistrict').on('click', function() {
	//alert('hi');
    var id = $(this).parent().find('#id').val();
    var lvl = $(this).parent().find('#lvl').val();
 $.ajax({
	type: 'GET',
	url: "mstrvilldatafind",
	data: {id:id, lvl:lvl},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
	$('#lgdd').val(data[0]);
	$('#districtNamed').val(data[1]);
	$('#leveld').val(data[2]);
		
	}
	
	});
	});
	
	$('table .editblock').on('click', function() {
	//alert('hi');
    var id = $(this).parent().find('#id').val();
    var lvl = $(this).parent().find('#lvl').val();
 $.ajax({
	type: 'GET',
	url: "mstrvilldatafind",
	data: {id:id, lvl:lvl},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
	$('#lgdb').val(data[0]);
	$('#districtNameb').val(data[1]);
	$('#levelb').val(data[2]);
	$.ajax({
		type: 'POST',
		url: "getGPListWithLgdCode",
		data: {id:id, lvl:lvl},
		error:function(xhr,status,er){
                console.log(er);
            },
	
		success: function(d){
		$distCode = $('#distCode');
		$distCode.empty();
		$distCode.append('<option value ="0">--Select--</option>');
		for ( var k in d) {
			if (d.hasOwnProperty(k)) {
				if(d[k]==data[3]){
					$distCode.append('<option value ="'+d[k]+'" selected>'+k+'</option>');
				}else{
					$distCode.append('<option value ="'+d[k]+'">'+k+'</option>');
				}
				
			}
		}
		
		}
	});
		
	}
	
	});
	});
	
	$('table .editgram').on('click', function() {
	//alert('hi');
    var id = $(this).parent().find('#id').val();
    var lvl = $(this).parent().find('#lvl').val();
 $.ajax({
	type: 'GET',
	url: "mstrvilldatafind",
	data: {id:id, lvl:lvl},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
	$('#lgdg').val(data[0]);
	$('#districtNameg').val(data[1]);
	$('#levelg').val(data[2]);
	$.ajax({
		type: 'POST',
		url: "getGPListWithLgdCode",
		data: {id:id, lvl:lvl},
		error:function(xhr,status,er){
                console.log(er);
            },
	
		success: function(d){
		$blkCode = $('#blkCode');
		$blkCode.empty();
		$blkCode.append('<option value ="0">--Select--</option>');
		for ( var k in d) {
			if (d.hasOwnProperty(k)) {
				if(d[k]==data[3]){
					$blkCode.append('<option value ="'+d[k]+'" selected>'+k+'</option>');
				}else{
					$blkCode.append('<option value ="'+d[k]+'">'+k+'</option>');
				}
				
			}
		}
		
		}
	});
		
	}
	
	});
	});
	});
	
		function getBlock() {
		var selectedValue = $("#district").val();
		var slctSubcat = $('#block'), option = "";
		var lvl = $('#dl').val();
		slctSubcat.empty();
		if(lvl==='4')
			slctSubcat.append('<option value="0"> --Select Blocks--</option>');
		else
			slctSubcat.append('<option value="0"> --All Blocks--</option>');
		$.ajax({
			type : 'POST',
			url : "getBlocksList",
			data : {
				"distCode" : selectedValue
			},

			success : function(data) {

				for ( var key in data) {

					option = option + "<option value="+data[key] + ">" + key
							+ "</option>";
				}
				slctSubcat.append(option);
			},
			error : function(xhr, status, er) {
				alert("error");
				console.log(er);
			}

		})
	};
	
	function getDistrict(){
		$state = $("#state").val();
		var lvl = $('#dl').val();
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
//				$block = $('#block');
				$district.empty();
				if(lvl==='4')
					$district.append('<option value="0">--Select District--</option>');
				else
					$district.append('<option value="0">--SelectAll--</option>');
//				$block.empty();
//				$block.append('<option value="" selected>--Select--</option>');
				if(Object.keys(data).length>0){
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
					$district.append('<option value="'+data[key]+'">'+key+'</option>');
				}
			}
			}
		}
	});
		
		
		
		
	}
	
	function levelselection() {
		$('#tbodyvillGrmBlkModifctn').empty();
		$.ajax({  
            url:"getAllStatesList",
            type: "post",  
            data: {},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
				console.log(data);
				$state = $('#state');
				$state.empty();
				$state.append('<option value="">--Select State--</option>');
				if(Object.keys(data).length>0){
			for ( var key in data) {
				if (data.hasOwnProperty(key)) {
//					alert(data[key]);
					$state.append('<option value="'+data[key]+'">'+key+'</option>');
				}
			}
			}
		}
	});
		var block = $('#block');
		$district = $('#district');
		//alert("You have selected Country: " + obj.value); dl
		//alert('hi'+document.getElementById("dl").value);
		if (document.getElementById("dl").value  == 1) {
			document.getElementById("block").value = 0;
			document.getElementById("district").value = 0;
			document.getElementById("block").disabled = true;
		}
		else if (document.getElementById("dl").value  == 4) {
			$district.empty();
			$district.append('<option value="0">--Select District--</option>');
			block.empty();
			block.append('<option value="0"> --Select Blocks--</option>');
			
		}
		 else {
			document.getElementById("block").value = 0;
			document.getElementById("district").value = 0;
			document.getElementById("block").disabled = false;
			$district.empty();
			$district.append('<option value="0">--SelectAll--</option>');
			block.empty();
			block.append('<option value="0"> --SelectAll--</option>');
		}

	}
	
	