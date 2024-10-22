$( document ).ready(function(){
	
	$('#tbodyMovement').on( 'click', 'a.notyearly', function (e) {
		
		$finyr=$('#year option:selected').val();
		var del = e.target.getAttribute('data-id');
		var type="year";
		$.ajax({  
            url:"getNotAdditionalParameter",
            type: "post",  
            data: {dcode:del, type:type, finyr:$finyr},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(AdditionalBroughtFarmerCropAreaBean) {
            console.log(AdditionalBroughtFarmerCropAreaBean);
            var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('List of Projects Not Entered Yearly Achievement');
						var i=1;
						if(Object.keys(AdditionalBroughtFarmerCropAreaBean).length>0){
							for ( var key in AdditionalBroughtFarmerCropAreaBean) {
		if (AdditionalBroughtFarmerCropAreaBean.hasOwnProperty(key)) {
			
			tblData+="<tr><td>"+i+"</td><td style='width:90%'>"+AdditionalBroughtFarmerCropAreaBean[key].project+"</td></tr>";
			i++;
			}
			}
						}
							
							else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
           $('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S No.</th><th style="width:90%">Project Name</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
            
            });
		});
	
	$('#tbodyMovement').on( 'click', 'a.nothalfyearly', function (e) {
		
		$finyr=$('#year option:selected').val();
		var del = e.target.getAttribute('data-id');
		var type="halfyear";
		$.ajax({  
            url:"getNotAdditionalParameter",
            type: "post",  
            data: {dcode:del, type:type, finyr:$finyr},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(AdditionalBroughtFarmerCropAreaBean) {
            console.log(AdditionalBroughtFarmerCropAreaBean);
            var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('List of Projects Not Entered Half Yearly Achievement');
						var i=1;
						if(Object.keys(AdditionalBroughtFarmerCropAreaBean).length>0){
							for ( var key in AdditionalBroughtFarmerCropAreaBean) {
		if (AdditionalBroughtFarmerCropAreaBean.hasOwnProperty(key)) {
			
			tblData+="<tr><td>"+i+"</td><td style='width:90%'>"+AdditionalBroughtFarmerCropAreaBean[key].project+"</td></tr>";
			i++;
			}
			}
						}
							
							else{
		tblData="<tr><td>Data not found !</td></tr>";
	}
           $('#popupreport .modal-body').html('<table class="" >'+
						'<thead><tr><th>S No.</th><th style="width:90%">Project Name</th></tr></thead><tbody>'+tblData+'</tbody></table>');
						}
            
            });
		});
	
	
	});