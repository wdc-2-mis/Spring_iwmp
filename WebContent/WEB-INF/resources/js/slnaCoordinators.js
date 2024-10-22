/************************************************On click Edit Button ********************************************/
$(document).on('click','#edit', function(){
	
	var stcode = $(this).data("id");
	var stname = $('#ddstate'+stcode).val();
	var crname = $('#ddname'+stcode).val();
	var cremail = $('#ddemail'+stcode).val();
	var crmobile = $('#ddmobile'+stcode).val();
	
	$stcd = $('#ddstcd');
	$stcd.empty();
	$stcd.val(stcode);
	
	$state = $('#state');
	$state.empty();
	$state.val(stname);
	$name = $('#name');
	$name.empty();
	$name.val(crname);
	$email = $('#email');
	$email.empty();
	$email.val(cremail);
	$mobile = $('#mobile');
	$mobile.empty();
	$mobile.val(crmobile);
});