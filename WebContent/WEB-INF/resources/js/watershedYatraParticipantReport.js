$(function(){

$(document).on('change', '#userdate', function(e) {
		e.preventDefault();
	
		const specifiedDate = new Date('2025-01-04');
		const enteredDate = new Date(this.value);
			    
		enteredDate.setHours(0, 0, 0, 0);
		specifiedDate.setHours(0, 0, 0, 0);

		if (enteredDate < specifiedDate) 
		{
			alert("The input date cannot be less than 05/01/2025");
			document.getElementById('userdate').value = '';
			document.getElementById('userdate').focus();
		}
	});
	
});	
	
	