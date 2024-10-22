$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$('table .delete').on('click', function(e) {
    var id = $(this).parent().find('#id').val();
    $('#deleteUOMModal #id').val(id);
		});
	
window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);

	$('#adduomcode').on('click', function(e) {
	e.preventDefault();
			
			$.ajax({  
            url:"getuomcodee",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(UOMDataBean) {
            for (key in UOMDataBean){
            $('#uomcode').val(UOMDataBean[key].unitcode);
            }
            
            }
		
		});
	});
	
	
	$('table .edit').on('click', function() {
	var id = $(this).parent().find('#id').val();
	     $.ajax({  
            url:"editUOMModal",
            type: "POST",
            data:{id:id},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(UOMDataBean) {
            for (key in UOMDataBean){
            $('#uomcode1').val(UOMDataBean[key].unitcode);
            $('#uomdesc1').val(UOMDataBean[key].unitdesc);
            $('#activityId').val(id);
            }
            }
		
		});
	});
	
	});