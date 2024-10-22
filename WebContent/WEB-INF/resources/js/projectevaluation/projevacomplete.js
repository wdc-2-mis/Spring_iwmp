$(document).on('click', '#view', function(e){
	
	var projProfId = document.getElementById('projProfId').value
	confirmAlert('Are You Sure You Want to Complete these Record!');
					$("#ok").click(function(){
			$('#popup').modal('hide');
			$.ajax({  
            url:"completeprojEvaldata",
            type: "post",  
            data: {projProfId:projProfId},
            error:function(xhr,status,er){
                console.log(er);
				$('.error').append(' There is some error please try again !');
            },
            success: function(data) {
						console.log(data);
						
						if(data==='success'){
				        alert('Data Completed Successfully!');
						window.location.href='getProfileStart';
						}
						else
						{
							alert('Issue on completing data!');
							window.location.href='';
						}
						$("#successok").click(function(){
						$('#popup').modal('hide');
						window.location.href='getProfileStart';
					});
						}
							});
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	
	
});	