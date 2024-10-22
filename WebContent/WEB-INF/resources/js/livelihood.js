
/**

*

*//**

*

*/

$(function(){

$('.delete').click(function(e) {

var del = e.target.getAttribute('data-id');

confirmAlert('Do you want to delete this Record ?');

$("#ok").click(function(){

//$('#popup').modal('toggle');

$.ajax({

url:"delete",

type: "post",

data: {id:del},

error:function(xhr,status,er){

console.log(er);

},

success: function(data) {

if(data==='success'){

successAlert('Activity is successfully Deleted');

$("#successok").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

$(".close").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

}else{

successAlert('This Activity can not be Deleted ');

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

$('.deleteAll').click(function(e) {

//alert("delete All");

var array = [];

$("input:checkbox[name=type]:checked").each(function() {

array.push($(this).val());

$.ajax({

url:"delete",

type: "post",

data: {id:$(this).val()},

error:function(xhr,status,er){

console.log(er);

},

success: function(data) {

if(data==='success'){

successAlert('Activity is successfully Deleted');

$("#successok").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

$(".close").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

}else{

successAlert('This Activity can not be Deleted ');

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

if(array.length == 0){

alert("please select atleast one check box")

}

/*

var del = e.target.getAttribute('data-id');

confirmAlert('Do you want to delete this Activity Record ?');

$("#ok").click(function(){


$.ajax({

url:"delete",

type: "post",

data: {id:del},

error:function(xhr,status,er){

console.log(er);

},

success: function(data) {

if(data==='success'){

successAlert('Activity is successfully Deleted');

$("#successok").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

$(".close").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

}else{

successAlert('This Activity can not be Deleted ');

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

});*/

});

$('.confirm').click(function(e) {

var del = e.target.getAttribute('data-id');

confirmAlert('Do you want to Complete this Record ?');

$("#ok").click(function(){

//$('#popup').modal('toggle');

$.ajax({

url:"complete",

type: "post",

data: {id:del},

error:function(xhr,status,er){

console.log(er);

},

success: function(data) {

if(data==='success'){

successAlert('Activity is successfully Completed');

$("#successok").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

$(".close").click(function(){

$('#popup').modal('hide');

window.location.href="addlivelihood";

});

}else{

successAlert('This Activity can not be Completed ');

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

$("#tbleLivelihoodDetails").on('input', '.txtCal', function (e) {

//alert('ji');

// alert(event.target.id);

// var del = e.target.id;

// alert(del);

let text =e.target.id;

const myArray = text.split("[");

let text1 =myArray[1];

const myArray1 = text1.split("]");

let index=myArray1[0];

var calculated_total_sum = getNum(document.getElementById('lsc['+index+']').value)+getNum(document.getElementById('lst['+index+']').value)+getNum(document.getElementById('lother['+index+']').value);
document.getElementById('ltotal['+index+']').value=calculated_total_sum;

});

$("#tbleProductionDetails").on('input', '.txtPal', function (e) {

let text =e.target.id;

const myArray = text.split("[");

let text1 =myArray[1];

const myArray1 = text1.split("]");

let index=myArray1[0];

var calculated_total_sum = getNum(document.getElementById('psc['+index+']').value)+getNum(document.getElementById('pst['+index+']').value)+getNum(document.getElementById('pother['+index+']').value);

document.getElementById('ptotal['+index+']').value=calculated_total_sum;

});

jQuery(document).delegate('a.delete-record', 'click', function(e) {

e.preventDefault();

var didConfirm = confirm("Are you sure You want to delete");

if (didConfirm == true) {

var id = jQuery(this).attr('data-id');

var targetDiv = jQuery(this).attr('targetDiv');

jQuery('#rec-' + id).remove();

//regnerate index number on table

$('#tbl_posts_body tr').each(function(index) {

//alert(index);

$(this).find('span.sn').html(index+1);

});

return true;

} else {

return false;

}

});

jQuery(document).delegate('a.add-record', 'click', function(e) {

e.preventDefault();

alert('hi');

var content = jQuery('#sample tr'),

size = jQuery('#tbl_posts >tbody >tr').length + 1,

element = null,

element = content.clone();

element.attr('id', 'rec-'+size);

element.find('.delete-record').attr('data-id', size);

element.appendTo('#tbl_posts_body');

element.find('.sn').html(size);

});


$('#btnWCDCForward').on('click',function(e){
	e.preventDefault();
	var finalAssetid=new Array();
	//$('.remarks').prop('required',false);
	$scheme = $('#headType option:selected').val();

	 $('.chkIndividual').each(function(){
         if($(this).prop('checked')){
			 finalAssetid.push($(this).val());	
			if(finalAssetid==""){
				alert('Please select Activity to complete.');
				return false;
			}
		}
      });
confirmAlert('Do you want to Complete ?');
			$("#ok").html('Yes');
			$("#cancel").html('No');
			$("#ok").click(function(){
			$('#popup').modal('hide');
			$('#loading').show();
$.ajax({  
            url:"completeAllEPALivelihoodProduction",
            type: "post",  
            data: {assetid:finalAssetid.toString(), scheme:$scheme},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
	console.log(data);
	$('#loading').hide();
	//alert(data);
	if(data==='success'){
		confirmAlert('Completed successfully ');
			/*alert('Work '+ $(this).html()+'ed Successfully !');*/
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='addlivelihood';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='wcdcActionOnAsset';
			});*/
	}
	if(data==='fail'){
		alert('Not Selected !');
			/*$("#successok").click(function(){
			$('#popup').modal('hide');*/
			window.location.href='addlivelihood';
			/*});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href='slnaActionOnAsset';
			});*/
	} 
	
	}
	});
	});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			});
	
	});






});

window.setTimeout(function() {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
        $(this).remove(); 
    });
}, 5000);
	
$('table .editl').on('click', function() {
	 var id = $(this).parent().find('#id').val();
	 $.ajax({
	type: 'GET',
	url: "livelihooddatafind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
            success: function(RPCLivelihoodBean){
		
		for (key in RPCLivelihoodBean){
			      
			$('#activity').val(RPCLivelihoodBean[key].livelihood_coreactivity_desc);
			$('#livepost').val(RPCLivelihoodBean[key].post_avg_income);
			$('#activityId').val(id);
			}
			}
			
            });
});	 

$('table .editp').on('click', function() {
	 var id = $(this).parent().find('#id').val();
	 $.ajax({
	type: 'GET',
	url: "productiondatafind",
	data: {id:id},
	error:function(xhr,status,er){
                console.log(er);
            },
            success: function(RPCLivelihoodBean){
		
		for (key in RPCLivelihoodBean){
			      
			$('#pactivity').val(RPCLivelihoodBean[key].productivity_coreactivity_desc);
			$('#prodpost').val(RPCLivelihoodBean[key].post_avg_income);
			$('#pactivityId').val(id);
			}
			}
			
            });
});	 

function getNum(str) {

return /[-+]?[0-9]*\.?[0-9]+/.test(str)?parseFloat(str):0;

}

