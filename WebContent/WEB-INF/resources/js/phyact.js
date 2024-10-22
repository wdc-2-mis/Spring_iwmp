$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$('table .delete').on('click', function(e) {
   
    var id = $(this).parent().find('#id').val();
   
   $('#deletePhyactModal #id').val(id);
		});
		
		/*$('table .morealert').on('click', function(e) {
			alert("hi");
		var id = $(this).parent().find('#id').val();
		$('#deletePhyactModalnew #id').val(id);
        
       
		});
		*/
		

		
		
		window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);
		
		$('#headcode').on('change', function(e) {
		e.preventDefault();
			
			$.ajax({  
            url:"getseqno",
            type: "POST",
            data:{headcode:$(this).val()},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(PhysicalActBean) {
            for (key in PhysicalActBean){
            $('#seqno').val(PhysicalActBean[key].seqno.toFixed(2));
            }
            
            }
		
		});
		});
		
	$('#add').on('click', function(e) {
		e.preventDefault();
			
			$.ajax({  
            url:"getheadcode",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $headcode = $('#headcode');
						$headcode.empty();
        				$headcode.append('<option value=""> --Select Head Data--</option>');
					    
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $headcode.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		
		}
			
	
	});
	
	$.ajax({  
            url:"getuomcode",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $uomcode = $('#uomcode');
						$uomcode.empty();
        				$uomcode.append('<option value=""> --Select UOM Code--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $uomcode.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		}
			
	
	});
	});
		
		
	$('table .edit').on('click', function() {
	//$('#editForm').reset();
	//	alert('hi kd');
	 var id = $(this).parent().find('#id').val();
    $.ajax({  
            url:"getheadcode",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $headcode = $('#headcode1');
						$headcode.empty();
					    $headcode.append('<option value=""> --Select Head Data--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $headcode.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		}
			
	
	});
	
	
	
	$.ajax({  
            url:"getuomcode",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $uomcode = $('#uomcode1');
						$uomcode.empty();
        				$uomcode.append('<option value=""> --Select UOM Code--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $uomcode.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		}
			
	
	});
    $.ajax({
	type: 'GET',
	url: "phyactdatafind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(PhysicalActBean){
		
		for (key in PhysicalActBean){
		
			$('#adesc').val(PhysicalActBean[key].actdesc);
			$("#headcode1 option[value="+PhysicalActBean[key].headcode+"]").attr("selected", "selected");
		    $("#status option[value="+PhysicalActBean[key].status+"]").attr('selected', 'selected');
		    $("#uomcode1 option[value="+PhysicalActBean[key].unitcode+"]").attr('selected', 'selected');
		    $('#seqno1').val(PhysicalActBean[key].seqno.toFixed(2));
		    $('#activityId').val(id);
		    $("#assets1 option[value="+PhysicalActBean[key].asset1+"]").attr('selected', 'selected');
		}	
	}
	
	});
	
	});



 $('#seqno').keypress(function(event) {
      if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
        ((event.which < 48 || event.which > 57) &&
          (event.which != 0 && event.which != 8))) {
        event.preventDefault();
      }
  
      var text = $(this).val();
  
      if ((text.indexOf('.') != -1) &&
        (text.substring(text.indexOf('.')).length > 2) &&
        (event.which != 0 && event.which != 8) &&
        ($(this)[0].selectionStart >= text.length - 2)) {
        event.preventDefault();
      }
    });

$('#seqno1').keypress(function(event) {
      if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
        ((event.which < 48 || event.which > 57) &&
          (event.which != 0 && event.which != 8))) {
        event.preventDefault();
      }
  
      var text = $(this).val();
  
      if ((text.indexOf('.') != -1) &&
        (text.substring(text.indexOf('.')).length > 2) &&
        (event.which != 0 && event.which != 8) &&
        ($(this)[0].selectionStart >= text.length - 2)) {
        event.preventDefault();
      }
    });
    


});

