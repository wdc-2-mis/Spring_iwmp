$(function(){
  /*$('#marquee-vertical').marquee();  
  $('#marquee-horizontal').marquee({direction:'horizontal', delay:0, timing:50});  
*/

var resize = new Array('p', 'a', 'div', 'ul', 'li', 'span', 'body', 'nav', 'button', 'input', 'h1', 'h2', 'h3','h4', 'h5', 'h6', 'ol');
  resize = resize.join(',');

  //resets the font size when "reset" is clicked
  var resetFont = $(resize).css('font-size');
  $(".reset").click(function() {
    $(resize).css('font-size', resetFont);
  });

  //increases font size when "+" is clicked
  $(".increase").click(function() {
    var originalFontSize = $(resize).css('font-size');
    var originalFontNumber = parseFloat(originalFontSize, 10);
    var newFontSize = originalFontNumber * 1.2;
    $(resize).css('font-size', newFontSize);
    return false;
  });

  //decrease font size when "-" is clicked

  $(".decrease").click(function() {
    var originalFontSize = $(resize).css('font-size');
    var originalFontNumber = parseFloat(originalFontSize, 10);
    var newFontSize = originalFontNumber * 0.8;
    $(resize).css('font-size', newFontSize);
    return false;
  });

/************************************************** Font Size adjust script end ************************************************************************* */

/*****************************************************Script for Page redirection ************************************************************************ */
 $(".success-stories").click(function() {
    window.location.href="successStories";
    return false;
  });

$(".video").click(function() {
    window.location.href="gallery";
    return false;
  });

$(".whatsnew").click(function() {
    window.location.href="whatsNew";
    return false;
  });

/********************************************************** Page redirection script ends ********************************************************************* */

/*********************************************************** Script for gallery page ************************************************************************* */
 /*$(".fancybox").fancybox({
	        openEffect: "none",
	        closeEffect: "none"
	    });
	    
	    $(".zoom").hover(function(){
			
			$(this).addClass('transition');
		}, function(){
	        
			$(this).removeClass('transition');
		});*/
		
/*************************************************************** Gallery page script end ************************************************************************* */



});

/*************************************************************** Report Search script ************************************************************************* */

/****************************************************** Download Script ************************************* */
$(document).on( 'click', 'a.download', function (e) {
           				 //Set the File URL.
           				 e.preventDefault();
            			var v=e.target.getAttribute('data-id').split(',');
           				var url =v[0];
           				var fileName = v[1];
            			var filepath = url;
            			$('<form action="downloadFile" method="post"><input type="hidden" value="'+filepath+'" name="url" id="url" /><input type="hidden" value="'+fileName+'" name="fileName" id="fileName" /></form>').appendTo('body').submit();
        		});

function searchReport() {
	 var input, filter, ul, li, a, a1,i;
	input = document.getElementById("mySearch");
	filter = input.value.toUpperCase();
	div = document.getElementById("myMenu");
	a = div.getElementsByTagName("a");
	for (i = 0; i < a.length; i++) {
	  txtValue = a[i].textContent || a[i].innerText;
	  if (txtValue.toUpperCase().indexOf(filter) > -1) {
	    a[i].style.display = "";
	  } else {
	     a[i].style.display = "none"; 
	    

	  }
	 
	}
	}
	
	
	/*************************************************************** Report Search script end ************************************************************************* */
	/*************************************************************** Select All Project Script *****************************************************/
	$(document).on('click', '#project', function() {
//$('#project option').prop('selected', true);
//$('#project option[value=" "]').prop('selected', false);
var selectedValue = $("#project option:selected").text();
	if(selectedValue==='--Select All--'){
		$('#project option').prop('selected', true);
		$('#project option[value=" "]').prop('selected', false);
	}
});
/********************************************************************* Error Message Script ********************************************************/
function addInputFieldErrorCss(field){
	$(field).css("border","1px solid red");
	$(field).css("box-shadow","0 0 10px red");
}

function removeInputFieldErrorCss(field){
	$(field).css("border","1px solid #ced4da");
	$(field).css("box-shadow","");
}
/*********************************************************************** Common function to remove element from array ************************************* */
function arrayRemove(arr, value) { 
        return arr.filter(function(ele){ 
            return ele != value; 
        });
    }
/******************************************************** common function to get unique array value ***************************************************************** */
function getUnique(array){
        var uniqueArray = [];
        console.log("length of array "+array.length);
        // Loop through array values
        for(i=0; i < array.length; i++){
            if(uniqueArray.indexOf(array[i]) === -1) {//console.log("value "+array[i]+" found "+$.inArray( array[i], uniqueArray ));
                uniqueArray.push(array[i]);
            }
        }
console.log("unique array "+uniqueArray);
        return uniqueArray;
    }

/***************************************************************** Common Function to print Excel from HTML Table ***************************** */

function fnExcelReport(tableID,filename,header,colLength,jsDate,headerRow)
{
	
	var footerHtml = "<table><tr></tr>* All area in hactare"+
	    "<tr><td colspan='"+colLength/2+"' style='text-align:center'>Report Generated by WDC-PMKSY software on: "+jsDate+"</td><td colspan='"+colLength/2+"' style='text-align:center'>Source: https://wdcpmksy.gov.in</td></tr></table>";
	var headerHtml = header;
    var tab_text=headerHtml+"<table border='2px'>";
    var textRange; var j=0;
    tab = document.getElementById(tableID); // id of table

    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
		if(headerRow>=1)
		tab_text=tab_text+"<tr bgcolor='#87AFC6'>";
		headerRow--;
        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<a[^>]*>|<\/a>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params
 	tab_text=tab_text+footerHtml;
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,filename+".xls");
    }  
    else {                //other browser not tested on IE 11
        sa = //window.open('data:application/vnd.ms-excel,'  + encodeURIComponent(tab_text)+",filename=filename.xls"); 
window.open('data:application/vnd.ms-excel,'  + encodeURIComponent(tab_text), '_parent', 'location=yes,height=570,width=520,scrollbars=yes,status=yes,name=filename.xls');
		/*var a = document.createElement('a');
		var data_type = 'data:application/vnd.ms-excel';
		a.href = data_type + ', ' + tab_text;
        //setting the file name
        a.download = 'exported_table_Hello.xls';
        //triggering the function
        a.click();*/ 
}

    return (sa);
}
function fnExcelReport1(tableID,filename,header,colLength,jsDate,headerRow)
{
	
	var footerHtml = "<table><tr></tr>"+
	    "<tr><td colspan='"+colLength/2+"' style='text-align:center'>Report Generated by WDC-PMKSY software on: "+jsDate+"</td><td colspan='"+colLength/2+"' style='text-align:center'>Source: https://wdcpmksy.gov.in</td></tr></table>";
	var headerHtml = header;
    var tab_text=headerHtml+"<table border='2px'>";
    var textRange; var j=0;
    tab = document.getElementById(tableID); // id of table

    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
		if(headerRow>=1)
		tab_text=tab_text+"<tr bgcolor='#87AFC6'>";
		headerRow--;
        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<a[^>]*>|<\/a>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params
 	tab_text=tab_text+footerHtml;
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,filename+".xls");
    }  
    else {                //other browser not tested on IE 11
        sa = //window.open('data:application/vnd.ms-excel,'  + encodeURIComponent(tab_text)+",filename=filename.xls"); 
window.open('data:application/vnd.ms-excel,'  + encodeURIComponent(tab_text), '_parent', 'location=yes,height=570,width=520,scrollbars=yes,status=yes,name=filename.xls');
		/*var a = document.createElement('a');
		var data_type = 'data:application/vnd.ms-excel';
		a.href = data_type + ', ' + tab_text;
        //setting the file name
        a.download = 'exported_table_Hello.xls';
        //triggering the function
        a.click();*/ 
}

    return (sa);
}
function exportTableToExcel(tableID, filename,header,footer){
    var downloadLink;
   
    var dataType = 'application/vnd.ms-excel';
    var tableSelect = document.getElementById(tableID);
    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
    var headerHtml = header;
	var footerHtml = footer;
    var tableHTML = headerHtml.replace(/ /g, '%20')+tableHTML+footerHtml.replace(/ /g, '%20');
    // Specify file name
    filename = filename?filename+'.xls':'excel_data.xls';
    
    // Create download link element
    downloadLink = document.createElement("a");
    
    document.body.appendChild(downloadLink);
    
    if(navigator.msSaveOrOpenBlob){
        var blob = new Blob(['\ufeff', tableHTML], {
            type: dataType
        });
        navigator.msSaveOrOpenBlob( blob, filename);
    }else{
        // Create a link to the file
        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
    
        // Setting the file name
        downloadLink.download = filename;
        
        //triggering the function
        downloadLink.click();
    }


}

/******************************************************************** Common function to print pdf from HTML table/DataTable ******************************* */

function createDataTable(reportName,tableName,columnSpan,columns,tbodyRowCount,headerHtml,pageorientation,psize) {
	//alert(reportName+" : "+tableName+" : "+columnSpan+" : "+columns+" : "+tbodyRowCount+" : "+headerHtml);
			var now = new Date();
			var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
			var col=[];
			
			var headerTitle = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)";
			/* $('#tblReport').append('<caption style="caption-side: bottom">A fictional company\'s staff table. Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana (WDC-PMKSY)</caption>'); */
			for(var i=0;i<columns;i++)
				col.push(i);
		var table =	$(tableName)
					.DataTable(
							{
								dom : 'Bfrtip',
								bSort: false,
							    bPaginate: false,
							    bFilter: false,
								bInfo : false,
								
								"buttons" : [
										{
											extend : 'excelHtml5',
											title : headerTitle,
											messageTop : reportName,
											messageBottom : 'Report Generated by WDC-PMKSY software on: '+jsDate,
											text : 'Excel',
											className: 'btn btn-info',
											filename:reportName
										},
										{
											text : 'Custom PDF',
											extend : 'pdfHtml5',
											filename : reportName,
											orientation : pageorientation, //landscape,portrait
											pageSize : psize, //A3 , A5 , A6 , legal , letter
											exportOptions : {
												columns : col,//':visible',
												search : 'applied',
												order : 'applied',
												stripNewlines: true
											},
											customize: function(pdfDocument) {
												//pdfDocument.content[0].text = reportName;
												pdfDocument.content[0].text = "Report: "+reportName+" \r\n"+headerHtml;
												pdfDocument.content[0].alignment='Middle';
												pdfDocument.content[0].fontSize=10;
												var logo = 'data:image/png;base64,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';
									            pdfDocument.content[1].table.headerRows = 2;
									         // Set the font size fot the entire document
												pdfDocument.defaultStyle.fontSize = 7;
											// Set the fontsize for the table header
												pdfDocument.styles.tableHeader.fontSize = 8;
												var now = new Date();
												var jsDate = now.getDate()+ '-'+ (now.getMonth() + 1)+ '-'+ now.getFullYear();
												//Color the text cell
												if(tbodyRowCount>0)
										        pdfDocument.content[1].table.body[tbodyRowCount][0].color = 'red';	
												// Create a header object with 2 columns // Left side: Logo // Middle: brandname // Right side: A document title
												pdfDocument['header'] = (function() {
													return {
														columns : [
																
																{
																	alignment : 'Middle',//Middle,left,center
																	italics : false,
																	text : headerTitle,
																	fontSize : 13,
																	margin : [5,0 ]
																}],
														margin : 20
													}
												});
												
												// Create a footer object with 2 columns // Left side: report creation date // Right side: current page and total pages
												pdfDocument['footer'] = (function(
														page, pages) {
													return {
														columns : [
																{
																	alignment : 'left',
																	text : [
																			'Report generated by WDC-PMKSY software on: ',
																			{
																				text : jsDate.toString()
																			} ]
																},
																{
																	alignment : 'center',
																	text : [
																			'page ',
																			{
																				text : page.toString()
																			},
																			' of ',
																			{
																				text : pages.toString()
																			} ]
																},
																{
																	alignment : 'left',
																	text : [ 'Source: https://wdcpmksy.gov.in ' ]
																} ],
														margin : [10,0,0,5]
													}
												});
												if(columnSpan){
												var firstHeaderRow = [];
									            $(tableName).find("thead>tr:first-child>th").each(
									              function(index, element) {
									                var colSpan = element.getAttribute("colSpan");
									                firstHeaderRow.push({
									                  text: element.innerHTML,
									                  style: "tableHeader",
									                  colSpan: colSpan
									                });
									                for (var i = 0; i < colSpan - 1; i++) {
									                  firstHeaderRow.push({});
									                }
									              });
									            pdfDocument.content[1].table.body.unshift(firstHeaderRow);
												}
									           

									          }
											
										} ]
							
							});
			$('.dataTables_length').addClass('bs-select');
		}
		
		
		function numericOnly(event){//onfocusin="numericOnly(event)"
	$(('#'+event.target.id).toString()).inputFilter(function(value) { 
		  return $numericonly.test(value); });

	}
	
	function decimalCheck(e){//onfocusin="decimalCheck(event)"
	$(('#'+e.target.id).toString()).inputFilter(function(value) { 
		  return $decimaluptotwo.test(value); });
	  //^\s*(?=.*[1-9])\d*(?:\.\d{1,2})?\s*$
	}
	
	function decimalToFourPlace(e){//onfocusin="decimalToFourPlace(event)"
		$(('#'+e.target.id).toString()).inputFilter(function(value) { 
			$decimaluptofour = /^[0-9]*(?:\.[0-9]{0,4})?$/;
		  return $decimaluptofour.test(value); });
	}
		
		(function($) {
  $.fn.inputFilter = function(inputFilter) {
    return this.on("input", function() {//input keydown keyup mousedown mouseup select contextmenu drop
      if (inputFilter(this.value)) {
        this.oldValue = this.value;
        this.oldSelectionStart = this.selectionStart;
        this.oldSelectionEnd = this.selectionEnd;
      } else if (this.hasOwnProperty("oldValue")) {
        this.value = this.oldValue;
        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
      } else {
        this.value = "";
      }
    });
  };
}(jQuery));


function orderByNameDDL(tag){
	tag.html(tag.find('option').sort(function(x, y) {
    // to change to descending order switch "<" for ">"
    return $(x).text() > $(y).text() ? 1 : -1;
  }));
}


function ShowContent(d) 
{
	var dd = document.getElementById(d);
	var width = dd.style.width;
	var index = width.indexOf("px");
	document.getElementById("waitDiv").style.display = "";
	width = width.substring(0,index);
	dd.style.left = ((document.body.clientWidth-width)/2) + "px";
	dd.style.display = "";
}



