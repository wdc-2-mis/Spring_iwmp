$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	
	$('table .delete').on('click', function(e) {
   e.preventDefault();
    var id = $(this).parent().find('#id').val();
   var schemetype=$(this).parent().find('#schemetype').val();
   $('#deleteDeptSchemeModal #id').val(id);
   $('#deleteDeptSchemeModal #schemetype').val(schemetype);
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
	url: "deptschememasterfind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(data){
		 for ( var key in data) {
			if (data.hasOwnProperty(key)) {
				$('#desc').val(data[key].schemeDescription);
				$('#seqno1').val(data[key].seqno);
				$('#deptschemeId').val(id); 
				$("#status option[value="+data[key].status+"]").attr('selected', 'selected');
			}
		 }
		
	}
	
	});
});
		
});