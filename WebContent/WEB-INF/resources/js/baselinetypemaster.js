$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	
	$('table .delete').on('click', function(e) {
   e.preventDefault();
    var id = $(this).parent().find('#id').val();
   
   $('#deletebaselinetypeModal #id').val(id);
		});
	
	
	window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);
		
		$('table .edit').on('click', function() {
	    var id = $(this).parent().find('#id').val();
        $.ajax({
	type: 'GET',
	url: "baselinetypefind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
		 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                     $('#typecode').val(data[key].typecode);
                                             $('#desc').val(data[key].bdescription);
                                             $('#seqno1').val(data[key].seqno.toFixed(2));
                                             $('#baselinetypeId').val(id);                                             
}
						                }
		
	}
	
	});


		});
		
	
	
		
		});