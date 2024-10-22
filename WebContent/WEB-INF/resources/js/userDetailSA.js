/*window.onload = function() {
	
    var user='${userType}';
    var type='${type}';
  
    if(user!='')
    	{
    	  //document.getElementById("userType").value='${userType}';
    	}
 
 if(user=='sl')
	 {
	 document.getElementById("dist").style.display = '';
	 document.getElementById("blc1").style.display = 'none';
	 document.getElementById("blc2").style.display = 'none';
	 document.getElementById("blc3").style.display = 'none';
	 document.getElementById("blc4").style.display = 'none';
	 document.getElementById("blc5").style.display = 'none';
	 }
if(user=='pia')
{
	 document.getElementById("dist").style.display = 'none';
	 document.getElementById("blc1").style.display = '';
	 document.getElementById("blc2").style.display = '';
	 document.getElementById("blc3").style.display = '';
	 document.getElementById("blc4").style.display = '';
	 document.getElementById("blc5").style.display = '';
}
};*/
function show(){
	 var user= document.getElementById("userType").value;
	 if(user=='sl')
		 {
		 document.getElementById("dist").style.display = '';
		 document.getElementById("blc1").style.display = 'none';
		 document.getElementById("blc2").style.display = 'none';
		 document.getElementById("blc3").style.display = 'none';
		 document.getElementById("blc4").style.display = 'none';
		 document.getElementById("blc5").style.display = 'none';
		 }
	 if(user=='pia')
	 {
		 document.getElementById("dist").style.display = 'none';
		 document.getElementById("blc1").style.display = '';
		 document.getElementById("blc2").style.display = '';
		 document.getElementById("blc3").style.display = '';
		 document.getElementById("blc4").style.display = '';
		 document.getElementById("blc5").style.display = '';
	 }
}

function validate(e){
	var userType = $('#userType').val();
	var state = $('#state').val();
	var status = $('#status').val();
	if(userType==='')
	{
		alert('Please select user type ');
		$('#userType').focus();
		e.preventDefault();
	}
	 if(state==='')
	{
		alert('Please select State ');
		$('#state').focus();
		e.preventDefault();
	}
	 if(status==='')
	{
		alert('Please select Status ');
		$('#status').focus();
		e.preventDefault();
	}
	else{
		
		document.userdetailsa.action="acitiveUserDetailsSA";
		document.userdetailsa.method="post";
		document.userdetailsa.submit();
	}
	return false;
}

function showReport(e){
	e.preventDefault();
	var state = $('#state').val();
	var status = $('#status').val();
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	else if(status==='')
	{
		alert('Please select Status ');
		$('#status').focus();
		e.preventDefault();
	}
	else{
		
		document.lastActivity.action="lastActivity";
		document.lastActivity.method="post";
		document.lastActivity.submit();
	}
	return false;
}

/***************************************************** PDF Button Click ************************************* */
function exportPDF(){
var table="";
var reportName = "List of Blocks, GP, and Villages";
var tableName = "#tblReport";
var columnSpan =false;
var colLength=$("#tblReport tr:first th").length;
var specialNote = false;
	var tbodyRowCount=0;
	var note="";

	if(specialNote)
		tbodyRowCount=$(tableName+" tbody tr").length+1;
	
	//Comment below 2(two) lines if you want to put custom note here or change it accordingly.
	if(typeof $('.text-danger').html() != "undefined")
		note=$('.text-danger').html();
		
	
	
	var td="";
	for(var i=1;i<colLength;i++){
		td=td+"<td></td>";
	}
 	
	$(tableName+' tbody tr:last').after('<tr><td width="" class="text-danger">'+note+'</td>'+td+'</tr>');

var headerHtml =" State: "+$('#state option:selected').text().trim()+" District: "+$('#district option:selected').text()+" \r\nBlock: "+
$('#block option:selected').text()+" \r\nGram Panchayat: "+$('#gp option:selected').text().trim("--");

if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
	createDataTable(reportName,tableName,columnSpan,colLength,tbodyRowCount,headerHtml);
	}
//createDataTable(headerTitle,reportName,tableName);
//trigger pdf button of datatable
table=$(tableName).DataTable().buttons(0,1).trigger();
table.buttons('.buttons-pdf').nodes().css("display", "none");
table.buttons('.buttons-excel').nodes().css("display", "none");
}


/***************************************************** Excel Button Click ************************************* */
function exportExcel(){

	var now = new Date();
	var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
	var tableName = "#tblReport";
 	var colLength=$(tableName+" tr:first th").length;
	var specialNote = false;
	var note="";
		if(specialNote && typeof $('.text-danger').html() != "undefined")
	 		note = "<tr><td colspan="+colLength+">"+$('.text-danger').html()+"</td></tr>";
	
 	var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
	var reportName = "List of Blocks, GP, and Villages";
	var fileName="List of Blocks, GP, and Villages";
 	var headerHtml = "<table>"+
		"<tr><td colspan='"+colLength+"' style='text-align:center'>"+headerTitle+"</td></tr>"+
   	 	"<tr><td colspan='"+colLength+"' style='text-align:center'>"+reportName+"</td></tr>"+
		"<tr><td>State: "+$('#state option:selected').text()+"</td><td>District: "+$('#district option:selected').text()+"</td></tr>"+
	    "<tr><td>Block: "+$('#block option:selected').text()+"</td><td>Gram Panchayat: "+$('#gp option:selected').text()+"</td></tr>"+
	    "<tr><td></td></tr></table>";
    var footerHtml = "<table><tr></tr>"+note+
    	"<tr><td colspan='"+colLength+"' style='text-align:center'>Report Generated by IWMP software on: "+jsDate+"</td></tr></table>";  
 	exportTableToExcel('tblReport', fileName,headerHtml,footerHtml);

}



