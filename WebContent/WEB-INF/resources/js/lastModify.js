/**
 * 
 */
var page=$('.path').val();
function ConvertStringToHex(str) {
              var arr = [];
              for (var i = 0; i < str.length; i++) {
                     arr[i] = ("00" + str.charCodeAt(i).toString(16)).slice(-4);
              }
              return "\\u" + arr.join("\\u");
       }

$(function (){ 
	 var arr = ConvertStringToHex(page);
$('.path').val(arr);
//alert(unescape(arr.replace(/\\/g, "%")));
	$.ajax({  
        url:"lastModify",
        type: "get",  
        data: {page:page},
        error:function(xhr,status,er){
            console.log(er);
        },
        success: function(data) {
var modiDate=new Date(data);
$mm = ((modiDate.getMonth()+1)+"").length===1?"0"+(modiDate.getMonth()+1):(modiDate.getMonth()+1);
$dd = (modiDate.getDate()+"").length===1?"0"+modiDate.getDate():modiDate.getDate();
var showAs=$mm+ "-" +$dd+ "-" +modiDate.getFullYear();
var Seconds
if (modiDate.getSeconds() < 10) {
    Seconds = "0" + modiDate.getSeconds();
  } else {
    Seconds = modiDate.getSeconds();
  }
var CurTime =  modiDate.getHours() + ":" + modiDate.getMinutes() + ":" + Seconds
$('.lastModify').html(data);
$('.lastModify').css("font-size",".6vw");
$('.lastModify').css("font-weight","999");
        }
	});
	});
	
	
/**********************************************Home Page Slider***************************************************/

function showdata(name) {
	var totaldata = null;

	if(name == 'soilmoisture'){
	totaldata =  $("input[name=soilmoisture]").val();
	}
	if(name == 'afforestation'){
	totaldata =  $("input[name=afforestation]").val();
	}
	if(name == 'waterreno'){
	totaldata =  $("input[name=waterreno]").val();
	}
	if(name == 'protectiveirr'){
	totaldata =  $("input[name=protectiveirr]").val();
	}
	if(name == 'mandays'){
	totaldata =  $("input[name=mandays]").val();
	}
	if(name == 'farmerbenef'){
	totaldata =  $("input[name=farmerbenef]").val();
	}
	if(name == 'degradedr'){
	totaldata =  $("input[name=degradedr]").val();
	}


	$('#loading').show();
	$.ajax({  
	            url:"getwhshomedata",
	            type: "post",  
	            data: {id:name},
	            error:function(xhr,status,er){
	                console.log(er);
					$('.error').append(' There is some error please try again !');
	            },
	            
	          success: function(DolrDashboardBean) {
	           $('#loading').hide();
		console.log(DolrDashboardBean);
							var tblData="";
							$('#popupreport').modal('toggle');
							$('#popupreport').modal('show');
							$('#popupreport #popupreporttitle').html();
							var i=1;
							if(Object.keys(DolrDashboardBean).length>0){
								
		for ( var key in DolrDashboardBean) {
			if (DolrDashboardBean.hasOwnProperty(key)) {
				if(parseInt(i)===1){
				i=0;
				$('#popupreport #popupreporttitle').append();
				}
				tblData+="<tr><td style='color:blue;'><u><a class='nav-link' data-dismiss='popupreport' name = "+DolrDashboardBean[key].headerdesc+"  onclick='showDistrict(this.id, this.name);' id="+DolrDashboardBean[key].st_code+"><input type='hidden' name='data' id='data' value="+name+">"+DolrDashboardBean[key].stname+"</a></u></td><td>"+DolrDashboardBean[key].headerdesc+"</td></tr>";
			}		
				}
		}else{
			tblData="<tr><td>Data not found !</td></tr>";
		}
		$('#popupreport .modal-body').html('<table class="" >'+
							'<thead><tr><th style="width:40%"><center>State Name</center></th><th>'+DolrDashboardBean[key].headdesc+'</th></tr></thead><tbody>'+tblData+'</tbody><tr><th><center>Total</center></th><th><center>'+totaldata+'</center></th></tr></table>');
							
							}  
	            
	            
	  });          
	}	
	
function showDistrict(id,name) {
	var activity = $('#data').val();
	var totaldata = name;
	$('#loading').show();
	$.ajax({  
	            url:"getcircledisrictdata",
	            type: "post",  
	            data: {id:id, activity:activity},
	            error:function(xhr,status,er){
	                console.log(er);
					$('.error').append(' There is some error please try again !');
	            },
	            
	          success: function(DolrDashboardBean) {
	           $('#loading').hide();
		console.log(DolrDashboardBean);
							var tblData="";
							$('#popupDreport').modal('toggle');
							$('#popupDreport').modal('show');
							$('#popupDreport #popupDreporttitle').html('DISTRICT WISE DATA');
							var i=1;
							if(Object.keys(DolrDashboardBean).length>0){
								
		for ( var key in DolrDashboardBean) {
			if (DolrDashboardBean.hasOwnProperty(key)) {
				if(parseInt(i)===1){
				i=0;
				$('#popupDreport #popupDreporttitle').append();
				}
				tblData+="<tr><td>"+DolrDashboardBean[key].dist_name+"</a></td><td>"+DolrDashboardBean[key].headerdesc+"</td></tr>";
			}		
				}
		}else{
			tblData="<tr><td>Data not found !</td></tr>";
		}
		$('#popupDreport .modal-body').html('<table class="" >'+
							'<thead><tr><th style="width:40%"><center>District Name</center></th><th>'+DolrDashboardBean[key].headdesc+'</th></tr></thead><tbody>'+tblData+'</tbody><tr><th><center>Total</center></th><th><center>'+totaldata+'</center></th></tr></table>');
							
							}  
	            
	            
	  });
	}	
	
	
	
/*function lastModified()

{
	
  var modiDate=new Date(val);

  var showAs=modiDate.getDate()+ "-" +(modiDate.getMonth()+1)+ "-" +modiDate.getFullYear();

return showAs

}

function GetTime(val)

{

  var modiDate = new Date(val);

  var Seconds

    if (modiDate.getSeconds() < 10) {

    Seconds = "0" + modiDate.getSeconds();

  } else {

    Seconds = modiDate.getSeconds();

  }

  var modiDate = new Date(val);

  var CurTime =  modiDate.getHours() + ":" + modiDate.getMinutes() + ":" + Seconds

  return CurTime

}

  document.write ("Last updated on ")

  document.write (lastModified() + " @ " + GetTime(val) );

  document.write (" [D M Y 24 Hour Clock]")

document.write ("");*/