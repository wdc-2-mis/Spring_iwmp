$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	$('table .delete').on('click', function() {
    var id = $(this).parent().find('#id').val();
    var idoutcomed = $(this).parent().find('#idoutcomed').val();
    $('#deleteEmployeeModal #id').val(id);
    $('#deleteEmployeeModal #idoutcomed').val(idoutcomed);
		});
	
	window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);
	
	$('#findseqno').on('click', function(e) {
	e.preventDefault();
			
			$.ajax({  
            url:"getoutcomeseqno",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(WdcpmksyOutcomeBean) {
            for (key in WdcpmksyOutcomeBean){
            	//alert('kd');
            $('#seq_no').val(WdcpmksyOutcomeBean[key].seq_no.toFixed(2));
            }
            
            }
		
		});
	});
		
	$('#finddseqno').on('click', function(e) {
		e.preventDefault();
				
				$.ajax({  
	            url:"getoutcomedseqno",
	            type: "POST",
	            data:{},  
	            error:function(xhr,status,er){
	                console.log(er);
	                },
	            success: function(WdcpmksyOutcomeBean) {
	            for (key in WdcpmksyOutcomeBean){
	            	//alert('kd');
	            $('#dseq_no').val(WdcpmksyOutcomeBean[key].seq_no.toFixed(2));
	            }
	            
	            }
			
			});
		});
	
	$('table .edit').on('click', function() {
	//$('#editForm').reset();    WdcpmksyOutcomeBean
	//	alert('hi kd');
	 var id = $(this).parent().find('#id').val();
    $.ajax({  
            url:"getfinyearcode",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $fin_yr_cd1 = $('#fin_yr_cd1');
						$fin_yr_cd1.empty();
					    $fin_yr_cd1.append('<option value=""> ----select Year----</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $fin_yr_cd1.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		}
			
	
	});
	
	
	
	$.ajax({  
            url:"getmonthcode",
            type: "POST",
            data:{},  
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
           var $month_id1 = $('#month_id1');
						$month_id1.empty();
        				$month_id1.append('<option value=""> --select month--</option>');
						 for ( var key in data) {
						                    if (data.hasOwnProperty(key)) {
						                       $month_id1.append('<option value='+key+'>' +data[key] + '</option>');
						                    }
						                }
		}
			
	
	});
    $.ajax({
	type: 'GET',
	url: "outcomedatafind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
	
	success: function(WdcpmksyOutcomeBean){
		
		for (key in WdcpmksyOutcomeBean)
		{
			$('#doutcomeid').val(WdcpmksyOutcomeBean[key].doutcomeid);
			$('#outcome_desc1').val(WdcpmksyOutcomeBean[key].outcome_desc);
			$("#fin_yr_cd1 option[value="+WdcpmksyOutcomeBean[key].fin_yr_cd+"]").attr("selected", "selected");
		    $("#detail_exist1 option[value="+WdcpmksyOutcomeBean[key].detail_exist+"]").attr('selected', 'selected');
		    $("#month_id1 option[value="+WdcpmksyOutcomeBean[key].month_id+"]").attr('selected', 'selected');
		    $('#seqno1').val(WdcpmksyOutcomeBean[key].seq_no.toFixed(2));
		    $("#bls_required option[value="+WdcpmksyOutcomeBean[key].bls_required+"]").attr('selected', 'selected');
		}	
	}
	
	});
	
	});

		
//$('#phyheadtable').dataTable();

$('#seq_no').keypress(function(event) {
	//alert('hi');
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





$('table .update').on('click', function() {
//$('#editForm').reset();    WdcpmksyOutcomeBean
	
 var id = $(this).parent().find('#id').val();
$.ajax({  
        url:"getOutcomeHeadcode",
        type: "POST",
        data:{},  
        error:function(xhr,status,er){
            console.log(er);
            },
        success: function(data) {
       var doutcomeidh = $('#doutcomeidh');
					doutcomeidh.empty();
				    doutcomeidh.append('<option value="">--select Outcome--</option>');
					 for ( var key in data) {
					                    if (data.hasOwnProperty(key)) {
					                       doutcomeidh.append('<option value='+key+'>' +data[key] + '</option>');
					                       
					                    }
					                }
	}
		

});

$.ajax({
type: 'GET',
url: "outcomedatadetailfind",
data: {id:id},
error:function(xhr,status,er){
            console.log(er);
        },

success: function(WdcpmksyOutcomeBean){
	
	for (key in WdcpmksyOutcomeBean)
	{
		$('#doutcomedid').val(WdcpmksyOutcomeBean[key].doutcomeid);
		$('#outcome_detail_desc1').val(WdcpmksyOutcomeBean[key].outcome_detail_desc);
		$("#doutcomeidh option[value="+WdcpmksyOutcomeBean[key].outcome_id_detail+"]").attr('selected', 'selected');
		$('#dseq_no1').val(WdcpmksyOutcomeBean[key].dseq_no);
	}	
}

});

});









});

