$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$('table .delete').on('click', function(e) {
   e.preventDefault();
    var id = $(this).parent().find('#id').val();
   
   $('#deletePhysubactModal #id').val(id);
		});
		
		window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);
		
		$('#headcode').on('change', function(e) {
		e.preventDefault();
			$actdesc = $('#actdesc');
			$.ajax({  
            url:"getactdesc",
            type: "POST",
            data:{headcode:$(this).val()},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
	$actdesc.empty();
           for (var key in data){
	       if (data.hasOwnProperty(key)) {
		 $actdesc.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
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
	});	
		
		$('table .edit').on('click', function() {
	    var id = $(this).parent().find('#id').val();
       //alert(id);    

	
	$.ajax({  
            url:"getactdesc",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $actdesc = $('#actdesc1');
						$actdesc.empty();
					    $actdesc.append('<option value=""> --Select Activity Data--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $actdesc.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		}
			
	
	});
	
	 $.ajax({
	type: 'GET',
	url: "physubactdatafind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
		 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                     $('#headcode1').val(data[key].headdesc);
                                             $('#sbactdesc1').val(data[key].subactivitydesc);
                                             $('#actdesc1').val(data[key].actdesc);
                                             $('#seqno1').val(data[key].seqno.toFixed(2));
                                             $('#subactivityId').val(id);                                             
                                             $("#status option[value="+data[key].status+"]").attr('selected', 'selected');
                                    }
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