

function openSidebar() {
  document.getElementById("sidebar").style.width = "250px";
  document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
  document.getElementById("sidebar").style.width = "0";
  document.getElementById("main").style.marginLeft= "0";
  document.getElementById("card-data").style.marginLeft= "0";
  
}

let k = "";
let x = "";
//var irrLab1 = [];	
$irrLab1 = new Array();	
var input = document.getElementsByName('afforestation1[]');

for (let i = 0; i < input.length; i++) {
                let a = input[i];
               k = k + a.value + ",";
            }
      const splits = k.split(',');
     splits.pop(); 
 const fixedUrl = splits.join(',');
 $irrLab1 = fixedUrl.split(',');
 
let hor1 = "";
let hor2 = ""; 
$irrLab2 = new Array();	
var input2 = document.getElementsByName('afforestation2[]');

for (let b = 0; b < input2.length; b++) {
                let x = input2[b];
               hor1 = hor1 + x.value + ",";
            }
      const splits1 = hor1.split(',');
     splits1.pop(); 
 const fixedUrl1 = splits1.join(',');
 $irrLab2 = fixedUrl1.split(',');  
 
 
let SAM1 = "";
let SAM12 = ""; 
$irrLab3 = new Array();	
var input3 = document.getElementsByName('afforestation3[]');

for (let c = 0; c < input3.length; c++) {
                let SAM3 = input3[c];
               SAM1 = SAM1 + SAM3.value + ",";
            }
      const splits2 = SAM1.split(',');
     splits2.pop(); 
 const fixedUrl2 = splits2.join(',');
 $irrLab3 = fixedUrl2.split(',');  

let WH1 = "";
$irrLab4 = new Array();	
var input4 = document.getElementsByName('afforestation4[]');

for (let d = 0; d < input4.length; d++) {
                let WH2 = input4[d];
               WH1 = WH1 + WH2.value + ",";
            }
      const splits3 = WH1.split(',');
     splits3.pop(); 
 const fixedUrl3 = splits3.join(',');
 $irrLab4= fixedUrl3.split(','); 
 
 let WHS1 = "";
$irrLab5 = new Array();	
var input5 = document.getElementsByName('afforestation5[]');

for (let e = 0; e < input5.length; e++) {
                let WHS2 = input5[e];
               WHS1 = WHS1 + WHS2.value + ",";
            }
      const splits4 = WHS1.split(',');
     splits4.pop(); 
 const fixedUrl4 = splits4.join(',');
 $irrLab5= fixedUrl4.split(','); 
 
 let PI1 = "";
$irrLab6 = new Array();	
var input6 = document.getElementsByName('afforestation6[]');

for (let f = 0; f < input6.length; f++) {
                let PI2 = input6[f];
               PI1 = PI1 + PI2.value + ",";
            }
      const splits5 = PI1.split(',');
     splits5.pop(); 
 const fixedUrl5 = splits5.join(',');
 $irrLab6= fixedUrl5.split(','); 
 
 
let PIR1 = "";
$irrLab7 = new Array();	
var input7 = document.getElementsByName('afforestation7[]');

for (let g = 0; g < input7.length; g++) {
                let PIR2 = input7[g];
               PIR1 = PIR1 + PIR2.value + ",";
            }
      const splits6 = PIR1.split(',');
     splits6.pop(); 
 const fixedUrl6 = splits6.join(',');
 $irrLab7= fixedUrl6.split(','); 
 
let TopState = "";
let TopStatePerc = "";

  var topstatename = document.getElementsByName('topstatename[]');
  var topstatesperc = document.getElementsByName('topachievpercent[]');
  
  $topstatedata = new Array();
  $toppercdata = new Array();
  
  for (let s = 0; s < topstatename.length; s++) {
                let TopState2 = topstatename[s];
               TopState = TopState + TopState2.value + ",";
            }
           // alert(TopState);
     const splitstate = TopState.split(',');
     splitstate.pop(); 
 const fixedUrls = splitstate.join(',');
 $topstatedata= fixedUrls.split(',');  
 
// alert($topstatedata)
  for (let p = 0; p < topstatesperc.length; p++) {
                let TopStatePerc2 = topstatesperc[p];
               TopStatePerc = TopStatePerc + TopStatePerc2.value + ",";
            }
     const splitperc = TopStatePerc.split(',');
     splitperc.pop(); 
 const fixedUrlp = splitperc.join(',');
 $toppercdata= fixedUrlp.split(',');  
  
 
 
 
 
let e1TopState = "";
let e1TopStatePerc = "";

  var e1topstatename = document.getElementsByName('topstatename2[]');
  var e1topstatesperc = document.getElementsByName('topachievpercent2[]');
  
  $e1topstatedata = new Array();
  $e1toppercdata = new Array();
  
  for (let e1s = 0; e1s < e1topstatename.length; e1s++) {
                let e1TopState2 = e1topstatename[e1s];
               e1TopState = e1TopState + e1TopState2.value + ",";
            }
     const e1splitstate = e1TopState.split(',');
     e1splitstate.pop(); 
 const e1fixedUrls = e1splitstate.join(',');
 $e1topstatedata= e1fixedUrls.split(',');  
 
  for (let e1p = 0; e1p < e1topstatesperc.length; e1p++) {
                let e1TopStatePerc2 = e1topstatesperc[e1p];
               e1TopStatePerc = e1TopStatePerc + e1TopStatePerc2.value + ",";
            }
     const e1splitperc = e1TopStatePerc.split(',');
     e1splitperc.pop(); 
 const e1fixedUrlp = e1splitperc.join(',');
 $e1toppercdata= e1fixedUrlp.split(',');  
  


  
  
 
 let e2TopState = "";
let e2TopStatePerc = "";

  var e2topstatename = document.getElementsByName('topstatename3[]');
  var e2topstatesperc = document.getElementsByName('topachievpercent3[]');
  
  $e2topstatedata = new Array();
  $e2toppercdata = new Array();
  
  for (let e2s = 0; e2s < e2topstatename.length; e2s++) {
                let e2TopState2 = e2topstatename[e2s];
               e2TopState = e2TopState + e2TopState2.value + ",";
            }
     const e2splitstate = e2TopState.split(',');
     e2splitstate.pop(); 
 const e2fixedUrls = e2splitstate.join(',');
 $e2topstatedata= e2fixedUrls.split(',');  
 
  for (let e2p = 0; e2p < e2topstatesperc.length; e2p++) {
                let e2TopStatePerc2 = e2topstatesperc[e2p];
               e2TopStatePerc = e2TopStatePerc + e2TopStatePerc2.value + ",";
            }
     const e2splitperc = e2TopStatePerc.split(',');
     e2splitperc.pop(); 
 const e2fixedUrlp = e2splitperc.join(',');
 $e2toppercdata= e2fixedUrlp.split(',');  
 
  
let e3TopState = "";
let e3TopStatePerc = "";

  var e3topstatename = document.getElementsByName('topstatename4[]');
  var e3topstatesperc = document.getElementsByName('topachievpercent4[]');
  
  $e3topstatedata = new Array();
  $e3toppercdata = new Array();
  
  for (let e3s = 0; e3s < e3topstatename.length; e3s++) {
                let e3TopState2 = e3topstatename[e3s];
               e3TopState = e3TopState + e3TopState2.value + ",";
            }
     const e3splitstate = e3TopState.split(',');
     e3splitstate.pop(); 
 const e3fixedUrls = e3splitstate.join(',');
 $e3topstatedata= e3fixedUrls.split(',');  
 
  for (let e3p = 0; e3p < e3topstatesperc.length; e3p++) {
                let e3TopStatePerc2 = e3topstatesperc[e3p];
               e3TopStatePerc = e3TopStatePerc + e3TopStatePerc2.value + ",";
            }
     const e3splitperc = e3TopStatePerc.split(',');
     e3splitperc.pop(); 
 const e3fixedUrlp = e3splitperc.join(',');
 $e3toppercdata= e3fixedUrlp.split(','); 
   
 let e4TopState = "";
let e4TopStatePerc = "";

  var e4topstatename = document.getElementsByName('topstatename5[]');
  var e4topstatesperc = document.getElementsByName('topachievpercent5[]');
  
  $e4topstatedata = new Array();
  $e4toppercdata = new Array();
  
  for (let e4s = 0; e4s < e4topstatename.length; e4s++) {
                let e4TopState2 = e4topstatename[e4s];
               e4TopState = e4TopState + e4TopState2.value + ",";
            }
     const e4splitstate = e4TopState.split(',');
     e4splitstate.pop(); 
 const e4fixedUrls = e4splitstate.join(',');
 $e4topstatedata= e4fixedUrls.split(',');  
 
  for (let e4p = 0; e4p < e4topstatesperc.length; e4p++) {
                let e4TopStatePerc2 = e4topstatesperc[e4p];
               e4TopStatePerc = e4TopStatePerc + e4TopStatePerc2.value + ",";
            }
     const e4splitperc = e4TopStatePerc.split(',');
     e4splitperc.pop(); 
 const e4fixedUrlp = e4splitperc.join(',');
 $e4toppercdata= e4fixedUrlp.split(','); 
     
  let e5TopState = "";
  let e5TopStatePerc = "";

  var e5topstatename = document.getElementsByName('topstatename6[]');
  var e5topstatesperc = document.getElementsByName('topachievpercent6[]');
  
  $e5topstatedata = new Array();
  $e5toppercdata = new Array();
  
  for (let e5s = 0; e5s < e5topstatename.length; e5s++) {
                let e5TopState2 = e5topstatename[e5s];
               e5TopState = e5TopState + e5TopState2.value + ",";
            }
     const e5splitstate = e5TopState.split(',');
     e5splitstate.pop(); 
 const e5fixedUrls = e5splitstate.join(',');
 $e5topstatedata= e5fixedUrls.split(',');  
 
  for (let e5p = 0; e5p < e5topstatesperc.length; e5p++) {
                let e5TopStatePerc2 = e5topstatesperc[e5p];
               e5TopStatePerc = e5TopStatePerc + e5TopStatePerc2.value + ",";
            }
     const e5splitperc = e5TopStatePerc.split(',');
     e5splitperc.pop(); 
 const e5fixedUrlp = e5splitperc.join(',');
 $e5toppercdata= e5fixedUrlp.split(',');  
   
 let e6TopState = "";
  let e6TopStatePerc = "";

  var e6topstatename = document.getElementsByName('topstatename7[]');
  var e6topstatesperc = document.getElementsByName('topachievpercent7[]');
  
  $e6topstatedata = new Array();
  $e6toppercdata = new Array();
  
  for (let e6s = 0; e6s < e6topstatename.length; e6s++) {
                let e6TopState2 = e6topstatename[e6s];
               e6TopState = e6TopState + e6TopState2.value + ",";
            }
     const e6splitstate = e6TopState.split(',');
     e6splitstate.pop(); 
 const e6fixedUrls = e6splitstate.join(',');
 $e6topstatedata= e6fixedUrls.split(',');  
 
  for (let e6p = 0; e6p < e6topstatesperc.length; e6p++) {
                let e6TopStatePerc2 = e6topstatesperc[e6p];
               e6TopStatePerc = e6TopStatePerc + e6TopStatePerc2.value + ",";
            }
     const e6splitperc = e6TopStatePerc.split(',');
     e6splitperc.pop(); 
 const e6fixedUrlp = e6splitperc.join(',');
 $e6toppercdata= e6fixedUrlp.split(',');  
    
  
  
var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#B1B31C'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart8"), barChartsOptions);
        barChart.render();
  
let e1BelowState = "";
let e1BelowStatePerc = "";

  var e1belowstatename = document.getElementsByName('belowstatename[]');
  var e1belowstatesperc = document.getElementsByName('belowachievpercent[]');
  
  $e1belowstatedata = new Array();
  $e1belowercdata = new Array();
  
  for (let e1b = 0; e1b < e1belowstatename.length; e1b++) {
                let e1BelowState2 = e1belowstatename[e1b];
               e1BelowState = e1BelowState + e1BelowState2.value + ",";
            }
     const e1bsplitstate = e1BelowState.split(',');
     e1bsplitstate.pop(); 
 const e1bfixedUrls = e1bsplitstate.join(',');
 $e1belowstatedata= e1bfixedUrls.split(',');  
 
  for (let e1bp = 0; e1bp < e1belowstatesperc.length; e1bp++) {
                let e1BelowStatePerc2 = e1belowstatesperc[e1bp];
               e1BelowStatePerc = e1BelowStatePerc + e1BelowStatePerc2.value + ",";
            }
     const e1Lsplitperc = e1BelowStatePerc.split(',');
     e1Lsplitperc.pop(); 
 const e1LfixedUrlp = e1Lsplitperc.join(',');
 $e1belowercdata= e1LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e1belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#B1B31C'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e1belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart15"), barChartsOptions);
        barChart.render();  
  
  
let e2BelowState = "";
let e2BelowStatePerc = "";

  var e2belowstatename = document.getElementsByName('belowstatename2[]');
  var e2belowstatesperc = document.getElementsByName('belowachievpercent2[]');
  
  $e2belowstatedata = new Array();
  $e2belowercdata = new Array();
  
  for (let e2b = 0; e2b < e2belowstatename.length; e2b++) {
                let e2BelowState2 = e2belowstatename[e2b];
               e2BelowState = e2BelowState + e2BelowState2.value + ",";
            }
     const e2bsplitstate = e2BelowState.split(',');
     e2bsplitstate.pop(); 
 const e2bfixedUrls = e2bsplitstate.join(',');
 $e2belowstatedata= e2bfixedUrls.split(',');  
 
  for (let e2bp = 0; e2bp < e2belowstatesperc.length; e2bp++) {
                let e2BelowStatePerc2 = e2belowstatesperc[e2bp];
               e2BelowStatePerc = e2BelowStatePerc + e2BelowStatePerc2.value + ",";
            }
     const e2Lsplitperc = e2BelowStatePerc.split(',');
     e2Lsplitperc.pop(); 
 const e2LfixedUrlp = e2Lsplitperc.join(',');
 $e2belowercdata= e2LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e2belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#85AAFA'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e2belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart16"), barChartsOptions);
        barChart.render();  

  
let e3BelowState = "";
let e3BelowStatePerc = "";

  var e3belowstatename = document.getElementsByName('belowstatename3[]');
  var e3belowstatesperc = document.getElementsByName('belowachievpercent3[]');
  
  $e3belowstatedata = new Array();
  $e3belowercdata = new Array();
  
  for (let e3b = 0; e3b < e3belowstatename.length; e3b++) {
                let e3BelowState2 = e3belowstatename[e3b];
               e3BelowState = e3BelowState + e3BelowState2.value + ",";
            }
     const e3bsplitstate = e3BelowState.split(',');
     e3bsplitstate.pop(); 
 const e3bfixedUrls = e3bsplitstate.join(',');
 $e3belowstatedata= e3bfixedUrls.split(',');  
 
  for (let e3bp = 0; e3bp < e3belowstatesperc.length; e3bp++) {
                let e3BelowStatePerc2 = e3belowstatesperc[e3bp];
               e3BelowStatePerc = e3BelowStatePerc + e3BelowStatePerc2.value + ",";
            }
     const e3Lsplitperc = e3BelowStatePerc.split(',');
     e3Lsplitperc.pop(); 
 const e3LfixedUrlp = e3Lsplitperc.join(',');
 $e3belowercdata= e3LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e3belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#EF981B'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e3belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart17"), barChartsOptions);
        barChart.render();  
 
let e4BelowState = "";
let e4BelowStatePerc = "";

  var e4belowstatename = document.getElementsByName('belowstatename4[]');
  var e4belowstatesperc = document.getElementsByName('belowachievpercent4[]');
  
  $e4belowstatedata = new Array();
  $e4belowercdata = new Array();
  
  for (let e4b = 0; e4b < e4belowstatename.length; e4b++) {
                let e4BelowState2 = e4belowstatename[e4b];
               e4BelowState = e4BelowState + e4BelowState2.value + ",";
            }
     const e4bsplitstate = e4BelowState.split(',');
     e4bsplitstate.pop(); 
 const e4bfixedUrls = e4bsplitstate.join(',');
 $e4belowstatedata= e4bfixedUrls.split(',');  
 
  for (let e4bp = 0; e4bp < e4belowstatesperc.length; e4bp++) {
                let e4BelowStatePerc2 = e4belowstatesperc[e4bp];
               e4BelowStatePerc = e4BelowStatePerc + e4BelowStatePerc2.value + ",";
            }
     const e4Lsplitperc = e4BelowStatePerc.split(',');
     e4Lsplitperc.pop(); 
 const e4LfixedUrlp = e4Lsplitperc.join(',');
 $e4belowercdata= e4LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e4belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#6ebfe7'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e4belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart18"), barChartsOptions);
        barChart.render(); 
  
  let e5BelowState = "";
  let e5BelowStatePerc = "";

  var e5belowstatename = document.getElementsByName('belowstatename5[]');
  var e5belowstatesperc = document.getElementsByName('belowachievpercent5[]');
  
  $e5belowstatedata = new Array();
  $e5belowercdata = new Array();
  
  for (let e5b = 0; e5b < e5belowstatename.length; e5b++) {
                let e5BelowState2 = e5belowstatename[e5b];
               e5BelowState = e5BelowState + e5BelowState2.value + ",";
            }
     const e5bsplitstate = e5BelowState.split(',');
     e5bsplitstate.pop(); 
 const e5bfixedUrls = e5bsplitstate.join(',');
 $e5belowstatedata= e5bfixedUrls.split(',');  
 
  for (let e5bp = 0; e5bp < e5belowstatesperc.length; e5bp++) {
                let e5BelowStatePerc2 = e5belowstatesperc[e5bp];
               e5BelowStatePerc = e5BelowStatePerc + e5BelowStatePerc2.value + ",";
            }
     const e5Lsplitperc = e5BelowStatePerc.split(',');
     e5Lsplitperc.pop(); 
 const e5LfixedUrlp = e5Lsplitperc.join(',');
 $e5belowercdata= e5LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e5belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#EE87BD'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e5belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart19"), barChartsOptions);
        barChart.render(); 
  
  
  let e6BelowState = "";
  let e6BelowStatePerc = "";

  var e6belowstatename = document.getElementsByName('belowstatename6[]');
  var e6belowstatesperc = document.getElementsByName('belowachievpercent6[]');
  
  $e6belowstatedata = new Array();
  $e6belowercdata = new Array();
  
  for (let e6b = 0; e6b < e6belowstatename.length; e6b++) {
                let e6BelowState2 = e6belowstatename[e6b];
               e6BelowState = e6BelowState + e6BelowState2.value + ",";
            }
     const e6bsplitstate = e6BelowState.split(',');
     e6bsplitstate.pop(); 
 const e6bfixedUrls = e6bsplitstate.join(',');
 $e6belowstatedata= e6bfixedUrls.split(',');  
 
  for (let e6bp = 0; e6bp < e6belowstatesperc.length; e6bp++) {
                let e6BelowStatePerc2 = e6belowstatesperc[e6bp];
               e6BelowStatePerc = e6BelowStatePerc + e6BelowStatePerc2.value + ",";
            }
     const e6Lsplitperc = e6BelowStatePerc.split(',');
     e6Lsplitperc.pop(); 
 const e6LfixedUrlp = e6Lsplitperc.join(',');
 $e6belowercdata= e6LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e6belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#DAA520'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e6belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart20"), barChartsOptions);
        barChart.render(); 
        
        let e7BelowState = "";
  let e7BelowStatePerc = "";

  var e7belowstatename = document.getElementsByName('belowstatename7[]');
  var e7belowstatesperc = document.getElementsByName('belowachievpercent7[]');
  
  $e7belowstatedata = new Array();
  $e7belowercdata = new Array();
  
  for (let e7b = 0; e7b < e7belowstatename.length; e7b++) {
                let e7BelowState2 = e7belowstatename[e7b];
               e7BelowState = e7BelowState + e7BelowState2.value + ",";
            }
     const e7bsplitstate = e7BelowState.split(',');
     e7bsplitstate.pop(); 
 const e7bfixedUrls = e7bsplitstate.join(',');
 $e7belowstatedata= e7bfixedUrls.split(',');  
 
  for (let e7bp = 0; e7bp < e7belowstatesperc.length; e7bp++) {
                let e7BelowStatePerc2 = e7belowstatesperc[e7bp];
               e7BelowStatePerc = e7BelowStatePerc + e7BelowStatePerc2.value + ",";
            }
     const e7Lsplitperc = e7BelowStatePerc.split(',');
     e7Lsplitperc.pop(); 
 const e7LfixedUrlp = e7Lsplitperc.join(',');
 $e7belowercdata= e7LfixedUrlp.split(',');  
     

var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e7belowercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#F6881B'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e7belowstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart21"), barChartsOptions);
        barChart.render(); 
  
 var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e1toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: ['#85AAFA'],
		    plotOptions: {
          bar: {
			  /*barHeight: '100%',*/
            distributed: false,
            horizontal: true,
            columnWidth: '40%',
            dataLabels: {
              position: 'bottom'
            },

          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex] 
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        stroke: {
			width: 1,
          colors: ['#000000']

			},
        
        xaxis: {
          categories: $e1topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#01120B",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart9"), barChartsOptions);
        barChart.render();
  
  var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e2toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#EF981B'
			],
			
		    plotOptions: {
          bar: {
			distributed: false,
            horizontal: true,
            columnWidth: '40%',
           /* endingShape: 'rounded',*/
          dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },
        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e2topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				

				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart10"), barChartsOptions);
        barChart.render();
  
   var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e3toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			 '#6ebfe7'
			],
			
		    plotOptions: {
          bar: {
			  distributed: false,
            horizontal: true,
            columnWidth: '40%',
            dataLabels: {
              position: 'bottom'
            },

          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },

        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e3topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			show: false,

		labels:{
			style: {
				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart11"), barChartsOptions);
        barChart.render();
  
  var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e4toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#EE87BD'
			],
			
		    plotOptions: {
          bar: {
			  distributed: false,
            horizontal: true,
            columnWidth: '40%',
            dataLabels: {
              position: 'bottom'
            },
          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },

        
        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e4topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,
			style: {
				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart12"), barChartsOptions);
        barChart.render();
  
  var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e5toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#DAA520'
			],
			
		    plotOptions: {
          bar: {
			  distributed: false,
            horizontal: true,
            columnWidth: '40%',
            /*endingShape: 'rounded',*/
            dataLabels: {
              position: 'bottom'
            },

          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },

        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e5topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,

			style: {
				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart13"), barChartsOptions);
        barChart.render();
  
  var barChartsOptions = {
	      series: [{
			   name: 'Percentage',
		  data: $e6toppercdata,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#F6881B'
			],
			
		    plotOptions: {
          bar: {
			  distributed: false,
            horizontal: true,
            columnWidth: '40%',
            /*endingShape: 'rounded',*/
             dataLabels: {
              position: 'bottom'
            },

          },
        },
        dataLabels: {
          enabled: true,
          textAnchor: 'start',
          style: {
            colors: ['#000000']
          },
          formatter: function (val, opt) {
            return opt.w.globals.labels[opt.dataPointIndex]
          },
          offsetX: 0,
          dropShadow: {
            enabled: false
          }

        },

        stroke: {
			colors: ['#f3f3f3','transparent']
			},
        xaxis: {
          categories: $e6topstatedata,
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			show: false,

			style: {
				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart14"), barChartsOptions);
        barChart.render();
  
 let tar = "";
 let ach = "";
 
  var tarinput =  document.getElementsByName('targetdata[]');
  var achinput = document.getElementsByName('achievementdata[]');
 
 
 var tarinput =  document.getElementsByName('targetdata[]');
 var achinput = document.getElementsByName('achievementdata[]');
 $tardata = new Array();
 $achdata = new Array();
 
 for (let t = 0; t < tarinput.length; t++) {
                let tar2 = tarinput[t];
               tar = tar + tar2.value + ",";
            }
     const splitst = tar.split(',');
     splitst.pop(); 
 const fixedUrlt = splitst.join(',');
 $tardata= fixedUrlt.split(',');        
 
 
 
 for (let ac = 0; ac < achinput.length; ac++) {
                let ach2 = achinput[ac];
               ach = ach + ach2.value + ",";
            }           
 const splitsach = ach.split(',');
     splitsach.pop(); 
 const fixedUrlach = splitsach.join(',');
 $achdata= fixedUrlach.split(','); 
 
 
 
 
 
 
 
//Area Charts
        var areaChartOptions = {
          series: [{
          name: 'Target',
          data: $tardata
        }, {
          name: 'Achievement',
          data: $achdata
        }],
          chart: {
		  type: "area",
		  background: "transparent",
          height: 400,
         stacked: false,
         toolbar:{
			  show: false,
		  },
        },
        colors: ["#00ab57", "#d50000"],
        labels: ['Area brought under Afforestation / Agriculture / Pasture etc.', 'Area brought under Horticulture','Area covered under Soil and Moisture conservation activities','Water Harvesting Structure (New created)','Water Harvesting Structure (Renovated)', 'Area brought under Protective Irrigation (Command area - New created WHS)','Area brought under Protective Irrigation (Command area - Renovated WHS)'],
        dataLabels:{
			enabled: false,
			
		},
		fill: {
			gradient: {
				opacityFrom: 0.8,
				opacityTo: 0.4,
				shadeIntensity:3,
				stops: [0, 100],
				type: "vertical",
			},
			type: "gradient",
		},
		grid: {
			borderColor: "#55596e",
			
        yaxis: 
          {
			  lines:{
				  show: true,
			  },
		  },
		  xaxis:{
			  lines:{
				  show: true,
			  },
		  },
		  },
		  legend:{
			  labels:{
				  colors: "#000000",
			  },
		  show:true,
		  position:"top",
		  },
		markers:{
			size:6,
			strokeColors: "#1b2635",
			strokeWidth:3,
		},
		stroke:{
			curve: "smooth"
		},
		xaxis:{
			axisBorder:{
				color: "#55596e",
				show: true,
			},
		axisTicks: {
			color: "#55596e",
				show: true,
		},
		labels: {
			offsetY:5,
			style:{
				colors: "#000000",
			},
		},
		},	
        yaxis: [
          {
           title: {
              text: '(Target)',
              style:{
				  color: "#000000",
			 
            },
          },
          labels:{
			  style: {
				  colors: "#000000",
			  },
		  },
		  },
          {
            opposite: true,
            title: {
              text: '(Achievement)',
              style:{
				colors: "#000000",
			},
            },
          labels:{
			  style: {
				  colors: "#000000",
			  },
			  },
			  },
        ],
        tooltip: {
          shared: true,
          intersect: false,
          theme:"dark",
        }
        };

        var areaChart = new ApexCharts(document.querySelector("#area-chart"), areaChartOptions);
        areaChart.render();  
  
 // bar chart start 
 /*var barChartsOptions = {
	      series: [{
			   name: 'Achievement',
		  data: $irrLab1,
        }
        ],
         
          chart: {
          type: 'bar',
          height: 350,
          toolbar:{
			  show: false,
		  },
        },
        colors: [
			'#2e7d32',
			
			],
			
		    plotOptions: {
          bar: {
			  distributed: false,
            horizontal: false,
            columnWidth: '55%',
            endingShape: 'rounded',
          },
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
			show: true,
			width: 2,
			colors: ['transparent']
			},
			 grid: {
          row: {
            colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
            opacity: 0.5
          },
        },
        xaxis: {
          categories: ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'],
        labels: {
			style: {
				colors: "#000000",
		},
		},
		},
		yaxis:{
			
		labels:{
			style: {
				colors: "#000000"
			},
		},
		},
		fill: {
			opacity: 1
		},
		grid:{
			borderColor: "#55596e",
			yaxis: {
				lines:{
					show: true,
				},
			},
			xaxis:{
				lines: {
					show: true,
				},
			},
		},
		legend:{
		labels: {
			colors: "#000000",
			},
			show: true,
			position: "bottom",
		},
		tooltip: {
			shared: true,
          intersect: false,
          theme:"dark",
		}
        
        };

        var barChart = new ApexCharts(document.querySelector("#bar-chart1"), barChartsOptions);
        barChart.render();
        
*/
// Full 12 months data
let fullCategories = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData = $irrLab1;   // assume $irrLab1 = [valApr, valMay, ..., valMar]

// Show window size = 5 months
let windowSize = 5;
let startIndex = 0;

// Function to render chart with sliced data
function renderChart() {
  let sliceCategories = fullCategories.slice(startIndex, startIndex + windowSize);
  let sliceData = fullData.slice(startIndex, startIndex + windowSize);

  var barChartsOptions = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#2e7d32'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  // destroy previous chart before re-render
  if (window.barChart1) {
    window.barChart1.destroy();
  }

  window.barChart1 = new ApexCharts(document.querySelector("#bar-chart1"), barChartsOptions);
  window.barChart1.render();
}

// Initial render
renderChart();

// Button click events
document.getElementById("prevBtn").addEventListener("click", function () {
  if (startIndex > 0) {
    startIndex--;
    renderChart();
  }
});

document.getElementById("nextBtn").addEventListener("click", function () {
  if (startIndex + windowSize < fullCategories.length) {
    startIndex++;
    renderChart();
  }
});

  /*end of bar chart 1*/ 
  
  /*start of bar chart 2*/     
   let fullCategories2 = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData2 = $irrLab2;   // assume $irrLab2 = [valApr, valMay, ..., valMar]

let windowSize2 = 5;
let startIndex2 = 0;

function renderChart2() {
  let sliceCategories = fullCategories2.slice(startIndex2, startIndex2 + windowSize2);
  let sliceData = fullData2.slice(startIndex2, startIndex2 + windowSize2);

  var barChartsOptions2 = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#4f35a1'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  if (window.barChart2) {
    window.barChart2.destroy();
  }

  window.barChart2 = new ApexCharts(document.querySelector("#bar-chart2"), barChartsOptions2);
  window.barChart2.render();
}

// Initial render
renderChart2();

// Button click events
document.getElementById("prevBtn2").addEventListener("click", function () {
  if (startIndex2 > 0) {
    startIndex2--;
    renderChart2();
  }
});

document.getElementById("nextBtn2").addEventListener("click", function () {
  if (startIndex2 + windowSize2 < fullCategories2.length) {
    startIndex2++;
    renderChart2();
  }
});

/*end of bar chart 2*/    
    
/* start bar chart 3*/

let fullCategories3 = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData3 = $irrLab3;   // assume $irrLab2 = [valApr, valMay, ..., valMar]

let windowSize3 = 5;
let startIndex3 = 0;

function renderChart3() {
  let sliceCategories = fullCategories3.slice(startIndex3, startIndex3 + windowSize3);
  let sliceData = fullData3.slice(startIndex3, startIndex3 + windowSize3);

  var barChartsOptions3 = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#BA4A00'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  if (window.barChart3) {
    window.barChart3.destroy();
  }

  window.barChart3 = new ApexCharts(document.querySelector("#bar-chart3"), barChartsOptions3);
  window.barChart3.render();
}

// Initial render
renderChart3();

// Button click events
document.getElementById("prevBtn3").addEventListener("click", function () {
  if (startIndex3 > 0) {
    startIndex3--;
    renderChart3();
  }
});

document.getElementById("nextBtn3").addEventListener("click", function () {
  if (startIndex3 + windowSize3 < fullCategories3.length) {
    startIndex3++;
    renderChart3();
  }
});

/*end of bar char 3*/    
    
/*start of bar chart 4*/
let fullCategories4 = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData4 = $irrLab4;   // assume $irrLab2 = [valApr, valMay, ..., valMar]

let windowSize4 = 5;
let startIndex4 = 0;

function renderChart4() {
  let sliceCategories = fullCategories4.slice(startIndex4, startIndex4 + windowSize4);
  let sliceData = fullData4.slice(startIndex4, startIndex4 + windowSize4);

  var barChartsOptions4 = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#378FBB'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  if (window.barChart4) {
    window.barChart4.destroy();
  }

  window.barChart4 = new ApexCharts(document.querySelector("#bar-chart4"), barChartsOptions4);
  window.barChart4.render();
}

// Initial render
renderChart4();

// Button click events
document.getElementById("prevBtn4").addEventListener("click", function () {
  if (startIndex4 > 0) {
    startIndex4--;
    renderChart4();
  }
});

document.getElementById("nextBtn4").addEventListener("click", function () {
  if (startIndex4 + windowSize4 < fullCategories4.length) {
    startIndex4++;
    renderChart4();
  }
});
       
/*end of bar chart 4*/


/*start of bar chart 5*/
let fullCategories5 = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData5 = $irrLab5;  

let windowSize5 = 5;
let startIndex5 = 0;

function renderChart5() {
  let sliceCategories = fullCategories5.slice(startIndex5, startIndex5 + windowSize5);
  let sliceData = fullData5.slice(startIndex5, startIndex5 + windowSize5);

  var barChartsOptions5 = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#DB7093'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  if (window.barChart5) {
    window.barChart5.destroy();
  }

  window.barChart5 = new ApexCharts(document.querySelector("#bar-chart5"), barChartsOptions5);
  window.barChart5.render();
}

// Initial render
renderChart5();

// Button click events
document.getElementById("prevBtn5").addEventListener("click", function () {
  if (startIndex5 > 0) {
    startIndex5--;
    renderChart5();
  }
});

document.getElementById("nextBtn5").addEventListener("click", function () {
  if (startIndex5 + windowSize5 < fullCategories5.length) {
    startIndex5++;
    renderChart5();
  }
});
/*end of bar chart 5*/
          
   /*start of bar chart 6*/
   let fullCategories6 = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData6 = $irrLab6;  

let windowSize6 = 5;
let startIndex6 = 0;

function renderChart6() {
  let sliceCategories = fullCategories6.slice(startIndex6, startIndex6 + windowSize6);
  let sliceData = fullData6.slice(startIndex6, startIndex6 + windowSize6);

  var barChartsOptions6 = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#DAA520'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  if (window.barChart6) {
    window.barChart6.destroy();
  }

  window.barChart6 = new ApexCharts(document.querySelector("#bar-chart6"), barChartsOptions6);
  window.barChart6.render();
}

// Initial render
renderChart6();

// Button click events
document.getElementById("prevBtn6").addEventListener("click", function () {
  if (startIndex6 > 0) {
    startIndex6--;
    renderChart6();
  }
});

document.getElementById("nextBtn6").addEventListener("click", function () {
  if (startIndex6 + windowSize6 < fullCategories6.length) {
    startIndex6++;
    renderChart6();
  }
});
   /*end of bar chart 6*/    
       
   /*start of bar chart 7*/
   let fullCategories7 = ['April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'March'];
let fullData7 = $irrLab7;  

let windowSize7 = 5;
let startIndex7 = 0;

function renderChart7() {
  let sliceCategories = fullCategories7.slice(startIndex7, startIndex7 + windowSize7);
  let sliceData = fullData7.slice(startIndex7, startIndex7 + windowSize7);

  var barChartsOptions7 = {
    series: [{
      name: 'Achievement',
      data: sliceData,
    }],
    chart: {
      type: 'bar',
      height: 350,
      toolbar: { show: false },
    },
    colors: ['#800000'],
    plotOptions: {
      bar: {
        distributed: false,
        horizontal: false,
        columnWidth: '55%',
        endingShape: 'rounded',
      },
    },
    dataLabels: { enabled: false },
    stroke: {
      show: true,
      width: 2,
      colors: ['transparent']
    },
    xaxis: {
      categories: sliceCategories,
      labels: { style: { colors: "#000000" } }
    },
    yaxis: {
      labels: { style: { colors: "#000000" } }
    },
    fill: { opacity: 1 },
    grid: {
      borderColor: "#55596e",
      yaxis: { lines: { show: true } },
      xaxis: { lines: { show: true } },
    },
    legend: {
      labels: { colors: "#000000" },
      show: true,
      position: "bottom",
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: "dark",
    }
  };

  if (window.barChart7) {
    window.barChart7.destroy();
  }

  window.barChart7 = new ApexCharts(document.querySelector("#bar-chart7"), barChartsOptions7);
  window.barChart7.render();
}

// Initial render
renderChart7();

// Button click events
document.getElementById("prevBtn7").addEventListener("click", function () {
  if (startIndex7 > 0) {
    startIndex7--;
    renderChart7();
  }
});

document.getElementById("nextBtn7").addEventListener("click", function () {
  if (startIndex7 + windowSize7 < fullCategories7.length) {
    startIndex7++;
    renderChart7();
  }
});
   /*end of bar chart 7*/   
    
    
  var pieChartsoptions = {
          series: [44, 55, 41, 17, 15],
          chart: {
          width: 380,
          type: 'donut',
        },
        plotOptions: {
          pie: {
            startAngle: -90,
            endAngle: 270
          }
        },
        dataLabels: {
          enabled: false
        },
        fill: {
          type: 'gradient',
        },
        legend: {
          formatter: function(val, opts) {
            return val + " - " + opts.w.globals.series[opts.seriesIndex]
          }
        },
        title: {
          text: 'Gradient Donut with custom Start-angle'
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
        };

        var piechart = new ApexCharts(document.querySelector("#bar-chartB1"), pieChartsoptions);
        piechart.render();  
        
