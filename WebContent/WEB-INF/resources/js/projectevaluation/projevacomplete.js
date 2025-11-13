$(document).on('click', '#view', function(e){
	
	var projProfId = document.getElementById('projProfId').value
	const summary = document.forms["projectProfiledata"]["evaluationSummary"].value.trim();
    const grades = document.forms["projectProfiledata"]["grade"];
    let gradeSelected = false;
    let selectedGrade = ""; 
    for (let i = 0; i < grades.length; i++) {
        if (grades[i].checked) {
            gradeSelected = true;
            selectedGrade = grades[i].value;
            break;
        }
    }
    if (summary === "" || !gradeSelected) {
        alert("Please fill Summary of evaluation summary and select a grade!");
        return false; 
    }
    
    else{
	confirmAlert('Are You Sure You Want to Complete these Record!');
					$("#ok").click(function(){
			$('#popup').modal('hide');
			$.ajax({  
            url:"completeprojEvaldata",
            type: "post",  
            data: {projProfId:projProfId, summary: summary, grade: selectedGrade},
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
			}
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	
	
});	