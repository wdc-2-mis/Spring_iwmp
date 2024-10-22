$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$('table .delete').on('click', function() {
    var id = $(this).parent().find('#id').val();
   $('#deleteEmployeeModal #id').val(id);
		});
	
	window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);
	
	$('#findseqno').on('click', function(e) {
	e.preventDefault();
			
			$.ajax({  
            url:"getheadseqno",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(PhysicalHeaddataBean) {
            for (key in PhysicalHeaddataBean){
            $('#seqno').val(PhysicalHeaddataBean[key].seqno.toFixed(2));
            }
            
            }
		
		});
	});
		
	$('table .edit').on('click', function() {
    var id = $(this).parent().find('#id').val();
    $.ajax({
	type: 'GET',
	url: "phydatafind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(PhysicalHeaddataBean){
	for (key in PhysicalHeaddataBean){
		    $('#hdesc').val(PhysicalHeaddataBean[key].headdesc);
		    $("#status option[value="+PhysicalHeaddataBean[key].status+"]").attr('selected', 'selected');
			$("#bline option[value="+PhysicalHeaddataBean[key].bls_required+"]").attr('selected', 'selected');
			$('#seqno1').val(PhysicalHeaddataBean[key].seqno.toFixed(2));
			$('#headId').val(id); 
		}
		
	}
	
	});
	});
		
//$('#phyheadtable').dataTable();

$('#seqno').keypress(function(event) {
//	alert('hi');
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

