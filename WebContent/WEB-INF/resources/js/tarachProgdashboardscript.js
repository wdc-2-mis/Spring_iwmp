
function openSidebar() {
  document.getElementById("sidebar").style.width = "250px";
 }
function closeNav() {
  document.getElementById("sidebar").style.width = "0";
 }


function myOverFunction(e) {
	 var code = e.id;
	e.style.height = "42px";
   e.style.width = "42px";
   $tbodyTranxState = $('#tbodyTranxState');
   $tbodyTranxState.hide();
   const myArray = code.split(",");
   var dcode = myArray[0];
   var headcode = myArray[1];
   var dname = null;
   $.ajax({  
            url:"getDistrictDBName",
            type: "post", 
           data:{dcode:dcode}, 

            error:function(xhr,status,er){
                console.log(er);
            },
             
		     success:function(data1) {
				console.log(data1);
				for ( var key1 in data1) {
							dname=data1[key1].distname;
							
						}
						}
						});
						
					
  $.ajax({  
            url:"getDistrictAchievement",
            type: "post", 
			data:{dcode:dcode,headcode:headcode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				
				$tbodyTranxDetails = $('#tbodyTranxDetails');
				$tbodyTranxDetails.empty();
				
				if(Object.keys(data).length>0){
		        for ( var key in data) {
			        $tbodyTranxDetails.append('<input type="hidden" id="yeardesc1" name="yeardesc1[]" value="'+data[key].descr+'"><input type="hidden" id="achievement1" name="achievement1[]" value="'+data[key].achiev+'"><input type="hidden" id="progachievment1" name="progachievment1[]" value="'+data[key].progachieve+'">');
			
			}
			//alert('i am out...');
			var year = document.getElementsByName('yeardesc1[]');
			var achieve = document.getElementsByName('achievement1[]');
			var progachieve = document.getElementsByName('progachievment1[]');
			$tbodyTranxDetails.append(getdistrictdata(year, achieve, progachieve,dname));
			//getdistrictdata(year, achieve, progachieve);
			}
				
				else {
					$tbodyTranxDetails.append(getdistrictdata(year, achieve, progachieve,dname));
				}
				}
				});
 
   
}

function myOutFunction(e){
	e.style.height = "32px";
   e.style.width = "32px";
}

function myOverStateFunction(e) {
	 var code = e.id;
	e.style.height = "42px";
   e.style.width = "42px";
   const myArray = code.split(",");
   var scode = myArray[0];
   var headcode = myArray[1];
   
  var sname = null;
   $.ajax({  
            url:"getStateDBName",
            type: "post", 
           data:{scode:scode}, 

            error:function(xhr,status,er){
                console.log(er);
            },
             
		     success:function(data1) {
				console.log(data1);
				for ( var key1 in data1) {
							sname=data1[key1].stname;
							
						}
						}
						});
						
					
  $.ajax({  
            url:"getStateAchievement",
            type: "post", 
			data:{scode:scode,headcode:headcode}, 
            error:function(xhr,status,er){
                console.log(er);
            },
            success:function(data) {
				console.log(data);
				
				$tbodyTranxStateDetails = $('#tbodyTranxStateDetails');
				$tbodyTranxStateDetails.empty();
				
				if(Object.keys(data).length>0){
		        for ( var key in data) {
			        $tbodyTranxStateDetails.append('<input type="hidden" id="yeardesc1" name="yeardesc1[]" value="'+data[key].descr+'"><input type="hidden" id="achievement1" name="achievement1[]" value="'+data[key].achiev+'"><input type="hidden" id="progachievment1" name="progachievment1[]" value="'+data[key].progachieve+'">');
			
			}
			var years = document.getElementsByName('yeardesc1[]');
			var achieves = document.getElementsByName('achievement1[]');
			var progachieves = document.getElementsByName('progachievment1[]');
			$tbodyTranxStateDetails.append(getstatedata(years, achieves, progachieves,sname));
			}
				
				else {
					$tbodyTranxStateDetails.append(getstatedata(years, achieves, progachieves,sname));
				}
				}
				});
 
   
}

function myOutStateFunction(e){
	e.style.height = "32px";
   e.style.width = "32px";
}

let yearData = "";
let achievData = "";
let proachievData = "";

  var monthdesc = document.getElementsByName('yeardesc[]');
  var achievementData = document.getElementsByName('achievement[]');
  var proachievDesc = document.getElementsByName('progachievment[]');
  $yeardataDesc = new Array();
  $achiedataDesc = new Array();
  $proachievdataDesc = new Array();
  
  for (let i = 0; i < monthdesc.length; i++) {
                let yearData2 = monthdesc[i];
               yearData = yearData + yearData2.value + ",";
            }
     const splityear = yearData.split(',');
     splityear.pop(); 
 const fixedUrls = splityear.join(',');
 $yeardataDesc= fixedUrls.split(',');  
 
  for (let j = 0; j < achievementData.length; j++) {
                let achievData2 = achievementData[j];
               achievData = achievData + achievData2.value + ",";
            }
     const splitachiev = achievData.split(',');
     splitachiev.pop(); 
 const fixedUrlp = splitachiev.join(',');
 $achiedataDesc= fixedUrlp.split(',');  
 
 for (let k = 0; k < proachievDesc.length; k++) {
                let proachievdataDesc2 = proachievDesc[k];
               proachievData = proachievData + proachievdataDesc2.value + ",";
            }
     const splitproachiev = proachievData.split(',');
     splitproachiev.pop(); 
 const fixedUrlpro = splitproachiev.join(',');
 $proachievdataDesc= fixedUrlpro.split(',');  

var ctx = document.getElementById('mixedChart').getContext('2d');
var myChart = new Chart(ctx, {
    
    data: {
        labels: $yeardataDesc,
        datasets: [{
            type: 'bar',
            
            label: 'Monthly Achievement',
            data: $achiedataDesc,
            backgroundColor: [
                'rgb(6, 43, 152)',
            ],
            borderColor: [
                'rgb(239, 132, 87)',
            ],
          
        },
        {
            type: 'line',
            label: 'Progressive Achievement',
            data: $proachievdataDesc,
            fill: false,
            backgroundColor: [
                'rgb(239, 132, 87)',
            ],
            borderColor: [
                'rgb(239, 132, 87)',
            ],
            
        } 
        ],
   dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
   }
   
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});


/*district wise chart start*/
let myChart1=null;
function getdistrictdata(year, achieve, progachieve, dname){
let yearData1 = "";
let achievData1 = "";
let proachievData1 = "";

  var ctx1=null;
  var monthdesc1 = year;
  var achievementData1 = achieve;
  var proachievDesc1 = progachieve;

  /* mixedChart1.empty();*/
 
  $yeardataDesc1 = new Array();
  $achiedataDesc1 = new Array();
  $proachievdataDesc1 = new Array();
  
  //alert(monthdesc1.length);
  for (let i1 = 0; i1 < monthdesc1.length; i1++) {
                let yearData21 = monthdesc1[i1];
               yearData1 = yearData1 + yearData21.value + ",";
            }
     const splityear1 = yearData1.split(',');
     splityear1.pop(); 
 const fixedUrls1 = splityear1.join(',');
 $yeardataDesc1= fixedUrls1.split(',');  
 
  for (let j1 = 0; j1 < achievementData1.length; j1++) {
                let achievData21 = achievementData1[j1];
               achievData1 = achievData1 + achievData21.value + ",";
            }
     const splitachiev1 = achievData1.split(',');
     splitachiev1.pop(); 
 const fixedUrlp1 = splitachiev1.join(',');
 $achiedataDesc1= fixedUrlp1.split(',');  
 
 for (let k1 = 0; k1 < proachievDesc1.length; k1++) {
                let proachievdataDesc21 = proachievDesc1[k1];
               proachievData1 = proachievData1 + proachievdataDesc21.value + ",";
            }
     const splitproachiev1 = proachievData1.split(',');
     splitproachiev1.pop(); 
 const fixedUrlpro1 = splitproachiev1.join(',');
 $proachievdataDesc1= fixedUrlpro1.split(',');  


var ctx1 = document.getElementById('mixedChart1').getContext('2d');

if(myChart1!=null){
	    myChart1.destroy();
}
myChart1 = new Chart(ctx1, {
 
    data: {
        labels: $yeardataDesc1,
        datasets: [{
            type: 'bar',
             label: 'Monthly Achievement',
            data: $achiedataDesc1,
            backgroundColor: [
                'rgb(65, 72, 172)',
            ],
            borderColor: [
                'rgba(46, 39, 245, 0.8)',
            ],
          
        },
        
        {
            type: 'line',
            label: 'Progressive Achievement',
            data: $proachievdataDesc1,
            fill: false,
            backgroundColor: [
                'rgb(145, 61, 47)',
            ],
            borderColor: [
                'rgb(145, 61, 47)',
            ],
            
        } 
        ],
   dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
   }
   
    },
    options: {
      
        scales: {
            y: {
                beginAtZero: true
            }
        
        },
        plugins: {
    title: {
                display: true,
                text: 'District:'+" "+dname,
                fontSize: 30,
            }
    }
    }
});


}

/*State data start    */
let myChart3=null;
function getstatedata(years, achieves, progachieves, sname){
let yearDatas = "";
let achievDataS = "";
let proachievDataS = "";

  var ctxS1=null;
  var monthdescS = years;
  var achievementDataS = achieves;
  var proachievDescS = progachieves;

  /* mixedChart1.empty();*/
 
  $yeardataDescS = new Array();
  $achiedataDescS = new Array();
  $proachievdataDescS = new Array();
  
  //alert(monthdesc1.length);
  for (let iS1 = 0; iS1 < monthdescS.length; iS1++) {
                let yearDataS21 = monthdescS[iS1];
               yearDatas = yearDatas + yearDataS21.value + ",";
            }
     const splityearS1 = yearDatas.split(',');
     splityearS1.pop(); 
 const fixedUrlS1 = splityearS1.join(',');
 $yeardataDescS= fixedUrlS1.split(',');  
 
  for (let jS1 = 0; jS1 < achievementDataS.length; jS1++) {
                let achievDataS21 = achievementDataS[jS1];
               achievDataS = achievDataS + achievDataS21.value + ",";
            }
     const splitachievS1 = achievDataS.split(',');
     splitachievS1.pop(); 
 const fixedUrlpS1 = splitachievS1.join(',');
 $achiedataDescS1= fixedUrlpS1.split(',');  
 
 for (let kS1 = 0; kS1 < proachievDescS.length; kS1++) {
                let proachievdataDescS21 = proachievDescS[kS1];
               proachievDataS = proachievDataS + proachievdataDescS21.value + ",";
            }
     const splitproachievS1 = proachievDataS.split(',');
     splitproachievS1.pop(); 
 const fixedUrlproS1 = splitproachievS1.join(',');
 $proachievdataDescS1= fixedUrlproS1.split(',');  


var ctxS1 = document.getElementById('mixedChart3').getContext('2d');

if(myChart3!=null){
	    myChart3.destroy();
}
myChart3 = new Chart(ctxS1, {
 
    data: {
        labels: $yeardataDescS,
        datasets: [{
            type: 'bar',
             label: 'Monthly Achievement',
            data: $achiedataDescS1,
            backgroundColor: [
                'rgb(65, 72, 172)',
            ],
            borderColor: [
                'rgba(46, 39, 245, 0.8)',
            ],
          
        },
        
        {
            type: 'line',
            label: 'Progressive Achievement',
            data: $proachievdataDescS1,
            fill: false,
            backgroundColor: [
                'rgb(145, 61, 47)',
            ],
            borderColor: [
                'rgb(145, 61, 47)',
            ],
            
        } 
        ],
   dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
   }
   
    },
    options: {
      
        scales: {
            y: {
                beginAtZero: true
            }
        
        },
        plugins: {
    title: {
                display: true,
                text: 'State:'+" "+sname,
                fontSize: 30,
            }
    }
    }
});


}


let yearData2 = "";
let achievData2 = "";
let proachievData2 = "";

  var monthdesc2 = document.getElementsByName('yeardesc2[]');
  var achievementData2 = document.getElementsByName('achievement2[]');
  var proachievDesc2 = document.getElementsByName('progachievment2[]');
  $yeardataDesc2 = new Array();
  $achiedataDesc2 = new Array();
  $proachievdataDesc2 = new Array();
  
  for (let i2 = 0; i2 < monthdesc2.length; i2++) {
                let yearData22 = monthdesc2[i2];
               yearData2 = yearData2 + yearData22.value + ",";
            }
     const splityear2 = yearData2.split(',');
     splityear2.pop(); 
 const fixedUrls2 = splityear2.join(',');
 $yeardataDesc2= fixedUrls2.split(',');  
 
  for (let j2 = 0; j2 < achievementData2.length; j2++) {
                let achievData22 = achievementData2[j2];
               achievData2 = achievData2 + achievData22.value + ",";
            }
     const splitachiev2 = achievData2.split(',');
     splitachiev2.pop(); 
 const fixedUrlp2 = splitachiev2.join(',');
 $achiedataDesc2= fixedUrlp2.split(',');  
 
 for (let k2 = 0; k2 < proachievDesc2.length; k2++) {
                let proachievdataDesc22 = proachievDesc2[k2];
               proachievData2 = proachievData2 + proachievdataDesc22.value + ",";
            }
     const splitproachiev2 = proachievData2.split(',');
     splitproachiev2.pop(); 
 const fixedUrlpro2 = splitproachiev2.join(',');
 $proachievdataDesc2= fixedUrlpro2.split(',');  

var ctx2 = document.getElementById('mixedChart2').getContext('2d');
var myChart2 = new Chart(ctx2, {
    
    data: {
        
        labels: $yeardataDesc2,
        datasets: [{
            type: 'bar',
             label: 'Monthly Achievement',
            data: $achiedataDesc2,
            backgroundColor: [
                'rgba(6, 182, 158, 0.8)',
            ],
            borderColor: [
                'rgba(46, 39, 245, 0.8)',
            ],
          
        },
        {
            type: 'line',
            label: 'Progressive Achievement',
            data: $proachievdataDesc2,
            fill: false,
            backgroundColor: [
                'rgba(13, 169, 64, 0.8)',
            ],
            borderColor: [
                'rgba(13, 169, 64, 0.8)',
            ],
            
        } 
        ],
   dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
   }
   
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }


});

