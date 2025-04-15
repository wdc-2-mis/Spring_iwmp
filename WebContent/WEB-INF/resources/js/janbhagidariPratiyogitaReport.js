$( document ).ready(function(){
	
	

		$('#janbhagidariPratiyogitaReport').on( 'click', 'a.activity', function (e) 
		{
			$activity = e.target.getAttribute('data-id');
		
			$.ajax({  
	            url:"getListofNGONameWithGPandVillage",
	            type: "post",  
	            data: {projid:$activity},
	            error:function(xhr,status,er){
	                console.log(er);
					$('.error').append(' There is some error please try again !');
	            },
	            success: function(data) {
						var tblData="";
						$('#popupreport').modal('toggle');
						$('#popupreport').modal('show');
						$('#popupreport #popupreporttitle').html('List of NGO');
						var i=1;
						var name="";
						if(Object.keys(data).length>0)
						{
							for ( var key in data) 
							{
								if (data.hasOwnProperty(key)) 
								{
									if(name!=data[key].ngo_name)
										tblData+="<tr><td>"+data[key].ngo_name+"</td><td>"+data[key].gpname+"</td><td>"+data[key].villname+"</td></tr>";
									else
										tblData+="<tr><td></td><td>"+data[key].gpname+"</td><td>"+data[key].villname+"</td></tr>";
								
										name=data[key].ngo_name;
								}
							}
						}
						
						$('#popupreport .modal-body').html('<form id="frmjanbhagidariPratiyogitaReport" ><center><table  style="width:100%">'+
							'<thead><tr><th>NGO Name</th><th>NGO Gram Panchayat</th><th>NGO Village</th>'+
							'</tr></thead><tbody>'+tblData+'</tbody></table></center>');
							
						$('#popupreport .modal-footer').html('<button type="button" id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button></form>');			
	            }  
			});
		});
	
	

		$('#janbhagidariPratiyogitaReport').on( 'click', 'a.swck', function (e) 
				{
					$activity = e.target.getAttribute('data-id');
				
					$.ajax({  
			            url:"getListofswckGPand",
			            type: "post",  
			            data: {projid:$activity},
			            error:function(xhr,status,er){
			                console.log(er);
							$('.error').append(' There is some error please try again !');
			            },
			            success: function(data) {
								var tblData="";
								$('#popupreport').modal('toggle');
								$('#popupreport').modal('show');
								$('#popupreport #popupreporttitle').html('List of Gram Panchayat where SWCK Account');
								var i=1;
								
								if(Object.keys(data).length>0)
								{
									for ( var key in data) 
									{
										if (data.hasOwnProperty(key)) 
										{
												tblData+="<tr><td>"+data[key].gpname+"</td></tr>";
										}		
									}
								}
								
								$('#popupreport .modal-body').html('<form id="frmjanbhagidariPratiyogitaReport" ><center><table  style="width:100%">'+
									'<thead><tr><th>Gram Panchayat Name</th>'+
									'</tr></thead><tbody>'+tblData+'</tbody></table></center>');
									
								$('#popupreport .modal-footer').html('<button type="button" id="cancel" name="cancel" class="btn btn-danger" data-dismiss="modal">Close</button></form>');			
			            }  
					});
				});
			
	
	
});	
	