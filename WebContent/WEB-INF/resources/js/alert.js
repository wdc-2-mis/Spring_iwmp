/**
 * 
 */

function confirmAlert(message){
	$('#popup').modal({
    backdrop: 'static',
    keyboard: false
})
$('#popup').modal('toggle');
$('#popup').modal('show');
var sher = '<div class="col-2" ><img src="./resources/images/tiranga_national_emblem.png" class=" babbar-sher" width="30" height="40" alt=""></div>';
var digiindia = '<div class="col-2" ><img src="./resources/images/digi-india3.png" class=" babbar-sher" width="40" height="40" alt=""></div>'
$('.modal-title').html('WDC-PMKSY');
$('.modal-body').html(message);
$('.modal-body').css("color","red");
$('.modal-header').css("border-bottom","2px solid #485d729c");
$('.modal-footer').css("border-top","2px solid #485d729c");
//$('#popup').modal('hide');
$("#successok").hide();
$("#cancel").show();
$("#ok").show();
}

function successAlert(message){
//$('#popup').modal('toggle');
$('#popup').modal('show');
$('.modal-title').html('WDC-PMKSY');
$('.modal-body').css("color","green");
$('.modal-body').html(message);
$('.modal-header').css("border-bottom","2px solid #485d729c");
$('.modal-footer').css("border-top","2px solid #485d729c");
//$("#ok").attr("id","successok");
$("#cancel").hide();
$("#ok").hide();
$("#successok").show();
}
