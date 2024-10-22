$(function() {


$('table .delete').on('click', function(e) {
   e.preventDefault();
    var id = $(this).parent().find('#id').val();
    var model = $(this).parent().find('#model').val();
    
   if(model==='Livelihood')
          {
        $('#deletelivelihoodModal #id').val(id);
		}
	if(model==='Production')
	{
		$('#deleteProductionModal #id').val(id);
	}	
	if(model==='EPA')
	{
		$('#deleteEPAModal #id').val(id);
	}
	if(model==='FPO')
	{
		$('#deleteFPOModal #id').val(id);
	}
	if(model==='SHG')
	{
		$('#deleteSHGModal #id').val(id);
	}
	if(model==='Training')
	{
		$('#deleteTrainingModal #id').val(id);
	}
	if(model==='Preparedness')
	{
		$('#deleteProjectPrepareModal #id').val(id);
	}
	
		});

	window.setTimeout(function() {
		$(".alert").fadeTo(1000, 0).slideUp(1000, function() {
			$(this).remove();
		});
	}, 5000);

	$('table .editl').on('click', function() {
		var id = $(this).parent().find('#id').val();

		$.ajax({
			type: 'POST',
			url: "getlivelihoodData",
			data: { id: id },
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$('#actdescl').val(data[key].actdesc);
						$("#status option[value=" + data[key].isactive + "]").attr('selected', 'selected');
						 $('#subactivityId').val(id);
					}
				}

			}

		});
	});

	$('table .editp').on('click', function() {
		var id = $(this).parent().find('#id').val();

		$.ajax({
			type: 'POST',
			url: "getproductionData",
			data: { id: id},
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$('#actdescp').val(data[key].actdesc);
						$("#status option[value=" + data[key].isactive + "]").attr('selected', 'selected');
						 $('#subactivitypId').val(id);
					}
				}

			}

		});
	});
	
	$('table .edite').on('click', function() {
		var id = $(this).parent().find('#id').val();

		$.ajax({
			type: 'POST',
			url: "getepaData",
			data: { id: id},
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$('#actdesce').val(data[key].actdesc);
						$("#status option[value=" + data[key].isactive + "]").attr('selected', 'selected');
						 $('#subactivityeId').val(id);
					}
				}

			}

		});
	});
	
	$('table .editf').on('click', function() {
		var id = $(this).parent().find('#id').val();

		$.ajax({
			type: 'POST',
			url: "getfpoData",
			data: { id: id},
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$('#actdescf').val(data[key].actdesc);
						$("#status option[value=" + data[key].isactive + "]").attr('selected', 'selected');
						 $('#subactivityfId').val(id);
					}
				}

			}

		});
	});
	
	$('table .edits').on('click', function() {
		var id = $(this).parent().find('#id').val();

		$.ajax({
			type: 'POST',
			url: "getshgData",
			data: { id: id},
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$('#actdescs').val(data[key].actdesc);
						$("#status option[value=" + data[key].isactive + "]").attr('selected', 'selected');
						 $('#subactivitysId').val(id);
					}
				}

			}

		});
	});
	
	$('table .editt').on('click', function() {
		var id = $(this).parent().find('#id').val();

		$.ajax({
			type: 'POST',
			url: "gettrainingData",
			data: { id: id},
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$('#actdesct').val(data[key].actdesc);
						$("#status option[value=" + data[key].isactive + "]").attr('selected', 'selected');
						 $('#subactivitytId').val(id);
					}
				}

			}

		});
	});
	
	
	$('table .editPreparedness').on('click', function() {
		
	    var id = $(this).parent().find('#id').val();
	   // alert('kedar'+id);
        $.ajax({
	type: 'POST',
	url: "getPreparednessMasterFind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
		 for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				$('#prepareDesc').val(data[key].prepareDesc);
				$('#shortDesc').val(data[key].shortDesc);
				$('#projectMprepareId').val(data[key].projectMprepareId); 
				$('#selectedDesc1').val(data[key].selectedDesc1); 
				$('#selectedDesc2').val(data[key].selectedDesc2); 
				$('#sequence').val(data[key].sequence); 
				$("#status option[value="+data[key].active+"]").attr('selected', 'selected');
			}
		 }
		
	}
	
	});
});
	
	
	
	
	
	
});

function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}