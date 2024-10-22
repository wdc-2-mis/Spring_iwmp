/**
 * 
 */
 
 $(function(){
	$('.delete').click(function(e) {
		var del = e.target.getAttribute('data-id');
		confirmAlert('Do you want to delete the '+del+' menu ?');
		$("#ok").click(function(){
			//$('#popup').modal('toggle');
		$.ajax({  
            url:"deleteMenu",
            type: "post",  
            data: {menuId:del},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	if(data==='success'){
            		successAlert('Record is successfully Deleted');
        			 $("#successok").click(function(){
        			$('#popup').modal('hide');
        			window.location.href="mainmenu";
        			});  
        			$(".close").click(function(){
        			$('#popup').modal('hide');
        			window.location.href="mainmenu";
        			}); 	
    	}else{
    		successAlert('Record can not Deleted as it is referenced by other submenus');
			  $("#successok").click(function(){
			$('#popup').modal('hide');
			//window.location.href="mainmenu";
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			//window.location.href="mainmenu";
			});  
    				}
            }
		});
		});
	});
});