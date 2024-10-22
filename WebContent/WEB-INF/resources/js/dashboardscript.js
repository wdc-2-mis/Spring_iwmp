
$label1 = new Array();
$value1 = new Array();
$label2 = new Array();
$value2 = new Array();
$label3 = new Array();
$value3 = new Array();
$label = new Array();
$value = new Array();
$label4 = new Array();
$value4 = new Array();
$(document).ready(function() {
//	const charts = document.querySelectorAll(".chart");
//	$.ajax({
//		url: "getprojectstatewise",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(DolrDashboardBean) {
//			for (key in DolrDashboardBean) {
//				if ($label1 === '')
//					$label1.push(key);
//
//				else
//					$label1.push(key);
//			}
//			for (key in DolrDashboardBean) {
//				if ($value1 === '')
//					$value1.push(DolrDashboardBean[key]);
//
//				else
//					$value1.push(DolrDashboardBean[key]);
//			}
//
//			charts.forEach(function(chart) {
//				var ctx = chart.getContext('2d');
//				var myChart = new Chart(ctx, {
//					type: 'bar',
//					data: {
//						labels: $label1,
//						datasets: [
//							{
//								axis: 'x',
//								label: "# of States(100 % Central Share)",
//								data: $value1,
//								backgroundColor: [
//
//									'#3e95cd', '#8e5ea2', '#3cba9f', '#e8c3b9', '#c45850', '#EE4622', '#EE4422', '#EE9722', '#C70039',
//									'rgba(255, 99, 132, 0.2)',
//									'rgba(255, 159, 64, 0.2)',
//									'rgba(255, 205, 86, 0.2)',
//									'rgba(75, 192, 192, 0.2)',
//									'rgba(54, 162, 235, 0.2)',
//									'rgba(153, 102, 255, 0.2)',
//									'rgba(201, 203, 207, 0.2)'
//								],
//								borderColor: [
//									'#3e95cd', '#8e5ea2', '#3cba9f', '#e8c3b9', '#c45850', '#EE4622', '#EE4422', '#EE9722', '#C70039',
//									'rgb(255, 99, 132)',
//									'rgb(255, 159, 64)',
//									'rgb(255, 205, 86)',
//									'rgb(75, 192, 192)',
//									'rgb(54, 162, 235)',
//									'rgb(153, 102, 255)',
//									'rgb(201, 203, 207)'
//								],
//								borderWidth: 1,
//							},
//						],
//					},
//					options: {
//						indexAxis: 'x',
//						scales: {
//							yAxes: [{
//								label: "asdasdasd",
//								ticks: {
//									beginAtZero: true
//
//								}
//							}]
//						}
//					}
//				});
//
//
//
//			});
//		}
//
//	});
//	//alert($label);
//
//
//	//----
//	const charts11 = document.querySelectorAll(".chart1");
//	$.ajax({
//		url: "getprojectstatewisecentral90",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(DolrDashboardBean) {
//			for (key in DolrDashboardBean) {
//				if ($label2 === '')
//					$label2.push(key);
//
//				else
//					$label2.push(key);
//			}
//			for (key in DolrDashboardBean) {
//				if ($value2 === '')
//					$value2.push(DolrDashboardBean[key]);
//
//				else
//					$value2.push(DolrDashboardBean[key]);
//			}
//
//			charts11.forEach(function(chart1) {
//				var ctx = chart1.getContext('2d');
//				var myChart = new Chart(ctx, {
//					type: 'bar',
//					data: {
//						labels: $label2,
//						datasets: [
//							{
//								axis: 'x',
//								label: "# of States (90% Central Share)",
//								data: $value2,
//								backgroundColor: [
//									'#A71D4E', '#DA8BA7', '#87C1C8', '#0A3F46', '#8291CF',
//									'rgba(255, 99, 132, 0.2)',
//									'rgba(255, 159, 64, 0.2)',
//									'rgba(255, 205, 86, 0.2)',
//									'rgba(75, 192, 192, 0.2)',
//									'rgba(54, 162, 235, 0.2)',
//									'rgba(153, 102, 255, 0.2)',
//									'rgba(201, 203, 207, 0.2)'
//								],
//								borderColor: [
//									'#A71D4E', '#DA8BA7', '#87C1C8', '#0A3F46', '#8291CF',
//									'rgb(255, 99, 132)',
//									'rgb(255, 159, 64)',
//									'rgb(255, 205, 86)',
//									'rgb(75, 192, 192)',
//									'rgb(54, 162, 235)',
//									'rgb(153, 102, 255)',
//									'rgb(201, 203, 207)'
//								],
//								borderWidth: 1,
//							},
//						],
//					},
//					options: {
//						indexAxis: 'x',
//					}
//				});
//
//
//
//			});
//		}
//
//	});
//	//----
//
//	//----
//	const charts12 = document.querySelectorAll(".chart2");
//	$.ajax({
//		url: "getprojectstatewisecentral60",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(DolrDashboardBean) {
//			for (key in DolrDashboardBean) {
//				if ($label3 === '')
//					$label3.push(key);
//
//				else
//					$label3.push(key);
//			}
//			for (key in DolrDashboardBean) {
//				if ($value3 === '')
//					$value3.push(DolrDashboardBean[key]);
//
//				else
//					$value3.push(DolrDashboardBean[key]);
//			}
//
//			charts12.forEach(function(chart2) {
//				var ctx = chart2.getContext('2d');
//				var myChart = new Chart(ctx, {
//					type: 'bar',
//					data: {
//						labels: $label3,
//						datasets: [
//							{
//								axis: 'x',
//								label: "# of States (60% Central Share)",
//								data: $value3,
//								backgroundColor: [
//									'#8291CF', '#8e5ea2', '#3cba9f', '#e8c3b9', '#c45850',
//									'rgba(255, 99, 132, 0.2)',
//									'rgba(255, 159, 64, 0.2)',
//									'rgba(255, 205, 86, 0.2)',
//									'rgba(75, 192, 192, 0.2)',
//									'rgba(54, 162, 235, 0.2)',
//									'rgba(153, 102, 255, 0.2)',
//									'rgba(201, 203, 207, 0.2)'
//								],
//								borderColor: [
//									'#8291CF', '#8e5ea2', '#3cba9f', '#e8c3b9', '#c45850',
//									'rgb(255, 99, 132)',
//									'rgb(255, 159, 64)',
//									'rgb(255, 205, 86)',
//									'rgb(75, 192, 192)',
//									'rgb(54, 162, 235)',
//									'rgb(153, 102, 255)',
//									'rgb(201, 203, 207)'
//								],
//								borderWidth: 1,
//							},
//						],
//					},
//					options: {
//						indexAxis: 'x',
//					}
//				});
//
//
//
//			});
//		}
//
//	});

/************************************************100% Completion of Classification Land as on Baseline****************************************************/
	
$lab1 = new Array();
$val1 = new Array();
$val2 = new Array();
$val3 = new Array();
$val4 = new Array();
$val5 = new Array();
	$.ajax({
		url: "getTopTenStLandClassPrcntg",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$lab1.push(data[key].stname);
				$val1.push(data[key].cultivable_land_prcntg);
				$val2.push(data[key].degraded_land_prcntg);
				$val3.push(data[key].rainfed_land_prcntg);
				$val4.push(data[key].forest_land_prcntg);
				$val5.push(data[key].other_land_prcntg);
			}
var ctx = document.getElementById("chartContainer1").getContext('2d');
var myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $lab1,
    datasets: [{
      label: 'Cutivable Wasteland',
      backgroundColor: "#663300",
      data: $val1,
    }, {
      label: 'Degraded Land',
      backgroundColor: "#b35900",
      data: $val2,
    }, {
      label: 'Rainfed',
      backgroundColor: "#e67300",
      data: $val3,
    }, {
      label: 'Forest Land',
      backgroundColor: "#ff9933",
      data: $val4,
    }, {
      label: 'Others',
      backgroundColor: "#ffbf80",
      data: $val5,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});

/******************************100% Completion of Classification Land as on Date**********************************************/
$lab2 = new Array();
$value11 = new Array();
$value12 = new Array();
$value13 = new Array();
$value14 = new Array();
$value15 = new Array();
	$.ajax({
		url: "getCompLandClassPrcntg",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$lab2.push(data[key].stname);
				$value11.push(data[key].cultivable_land_prcntg_ach);
				$value12.push(data[key].degraded_land_prcntg_ach);
				$value13.push(data[key].rainfed_land_prcntg_ach);
				$value14.push(data[key].forest_land_prcntg_ach);
				$value15.push(data[key].other_land_prcntg_ach);
			}
var ctx = document.getElementById("chartContainerAch").getContext('2d');
var myChartAch = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $lab2,
    datasets: [{
      label: 'Cutivable Wasteland',
      backgroundColor: "#663300",
      data: $value11,
    }, {
      label: 'Degraded Land',
      backgroundColor: "#b35900",
      data: $value12,
    }, {
      label: 'Rainfed',
      backgroundColor: "#e67300",
      data: $value13,
    }, {
      label: 'Forest Land',
      backgroundColor: "#ff9933",
      data: $value14,
    }, {
      label: 'Others',
      backgroundColor: "#ffbf80",
      data: $value15,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});

/************************************************100% Completion of Irrigation Status as on Baseline****************************************************/
$irrLab1 = new Array();
$irrVal1 = new Array();
$irrVal2 = new Array();
$irrVal3 = new Array();

	$.ajax({
		url: "getCompIrriStatusPrcntg",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$irrLab1.push(data[key].stname);
				$irrVal1.push(data[key].protective_irrigation_prcntg);
				$irrVal2.push(data[key].assured_irrigation_prcntg);
				$irrVal3.push(data[key].no_irrigation_prcntg);
				
			}
var ctx = document.getElementById("chartContainerIrri").getContext('2d');
var myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $irrLab1,
    datasets: [{
      label: 'Protective Irrigation',
      backgroundColor: "#007acc",
      data: $irrVal1,
    }, {
      label: 'Assured Irrigation',
      backgroundColor: "#33adff",
      data: $irrVal2,
    }, {
      label: 'No Irrigation',
      backgroundColor: "#80ccff",
      data: $irrVal3,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});

/**********************************************100% Completion of Irrigation Status as on Date***************************************************/
$irrAchLab = new Array();
$irrAchVal1 = new Array();
$irrAchVal2 = new Array();
$irrAchVal3 = new Array();
	$.ajax({
		url: "getCompIrriStatusAchPrcntg",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$irrAchLab.push(data[key].stname);
				$irrAchVal1.push(data[key].protective_irrigation_prcntg);
				$irrAchVal2.push(data[key].assured_irrigation_prcntg);
				$irrAchVal3.push(data[key].no_irrigation_prcntg);
				
			}
var ctx = document.getElementById("chartContainerIrriAch").getContext('2d');
var myChartAch = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $irrAchLab,
    datasets: [{
      label: 'Protective Irrigation',
      backgroundColor: "#007acc",
      data: $irrAchVal1,
    }, {
      label: 'Assured Irrigation',
      backgroundColor: "#33adff",
      data: $irrAchVal2,
    }, {
      label: 'No Irrigation',
      backgroundColor: "#80ccff",
      data: $irrAchVal3,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});

/************************************************100% Completion of No. of Crops as on Baseline****************************************************/
$crpLab1 = new Array();
$crpVal1 = new Array();
$crpVal2 = new Array();
$crpVal3 = new Array();
$crpVal4 = new Array();

	$.ajax({
		url: "getCompNoOfCropPrcntg",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$crpLab1.push(data[key].stname);
				$crpVal1.push(data[key].no_crop);
				$crpVal2.push(data[key].single_crop);
				$crpVal3.push(data[key].double_crop);
				$crpVal4.push(data[key].multiple_crop);
				
			}
var ctx = document.getElementById("chartContainerCrop").getContext('2d');
var myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $crpLab1,
    datasets: [{
      label: 'No Crop',
      backgroundColor: "#4d4d33",
      data: $crpVal1,
    }, {
      label: 'Single Crop',
      backgroundColor: "#7a7a52",
      data: $crpVal2,
    }, {
      label: 'Double Crop',
      backgroundColor: "#a3a375",
      data: $crpVal3,
    }, {
      label: 'Multiple Crop',
      backgroundColor: "#ccccb3",
      data: $crpVal4,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});

/**********************************************100% Completion of No. of Crops as on Date***************************************************/
$crpAchLab = new Array();
$crpAchVal1 = new Array();
$crpAchVal2 = new Array();
$crpAchVal3 = new Array();
$crpAchVal4 = new Array();
	$.ajax({
		url: "getCompNoOfCropAchPrcntg",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$crpAchLab.push(data[key].stname);
				$crpAchVal1.push(data[key].no_crop_ach);
				$crpAchVal2.push(data[key].single_crop_ach);
				$crpAchVal3.push(data[key].double_crop_ach);
				$crpAchVal4.push(data[key].multiple_crop_ach);
				
			}
var ctx = document.getElementById("chartContainerCropAch").getContext('2d');
var myChartAch = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $crpAchLab,
    datasets: [{
      label: 'No Crop',
      backgroundColor: "#4d4d33",
      data: $crpAchVal1,
    }, {
      label: 'Single Crop',
      backgroundColor: "#7a7a52",
      data: $crpAchVal2,
    }, {
      label: 'Double Crop',
      backgroundColor: "#a3a375",
      data: $crpAchVal3,
    }, {
      label: 'Multiple Crop',
      backgroundColor: "#ccccb3",
      data: $crpAchVal4,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});


/**********************************************Group Bar Chart***************************************************/
//$lineLab = new Array();
//$lineVal1 = new Array();
//$lineVal2 = new Array();
//	$.ajax({
//		url: "getStwiseTotalExpndtrPrcntg",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(data) {
//			for (var key in data) {
//				$lineLab.push(data[key].stname);
//				$lineVal1.push(data[key].total_expen_prcnt);
//				$lineVal2.push(data[key].treasury_receipt_prcnt);
//			}
//var ctx = document.getElementById("groupBarChart1").getContext('2d');
//var myGroupBarChart = new Chart(ctx, {
//  type: 'bar',
//  data: {
//    labels: $lineLab,
//    datasets: [{
//      label: 'Total Expenditure',
//      data: $lineVal1,
//      backgroundColor: "pink",
//      borderColor: "red",
//      borderWidth: 1
//     },
//     {
//	  label: 'Treasury Receipt',
//      data: $lineVal2,
//      backgroundColor: "lightblue",
//      borderColor: "blue",
//      borderWidth: 1
//    }],
//  },
//  options:{
//  responsive: true,
//  legend: {
//    position: "top"
//  },
//  title: {
//    display: true,
//    text: "Chart.js Bar Chart"
//  },
//  scales: {
//    x: {
//		ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//	},
//    y: {
//      ticks: {
//        beginAtZero: true,
//        font: {
//			weight: 'bold'
//		}
//      }
//    }
//  }
//}
//});
//}
//});

///************************************************Top Ten State wise below 100% Completion of Classification Land****************************************************/
//	
//$tenLab = new Array();
//$tenVal1 = new Array();
//$tenVal2 = new Array();
//$tenVal3 = new Array();
//$tenVal4 = new Array();
//$tenVal5 = new Array();
//	$.ajax({
//		url: "getTopTenNonCompLandPrcntg",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(data) {
//			for (var key in data) {
//				$tenLab.push(data[key].stname);
//				$tenVal1.push(data[key].cultivable_land_prcntg);
//				$tenVal2.push(data[key].degraded_land_prcntg);
//				$tenVal3.push(data[key].rainfed_land_prcntg);
//				$tenVal4.push(data[key].forest_land_prcntg);
//				$tenVal5.push(data[key].other_land_prcntg);
//			}
//var ctx = document.getElementById("topTenStLand").getContext('2d');
//var myChart = new Chart(ctx, {
//  type: 'bar',
//  data: {
//    labels: $tenLab,
//    datasets: [{
//      label: 'Cutivable Wasteland',
//      backgroundColor: "#663300",
//      data: $tenVal1,
//    }, {
//      label: 'Degraded Land',
//      backgroundColor: "#b35900",
//      data: $tenVal2,
//    }, {
//      label: 'Rainfed',
//      backgroundColor: "#e67300",
//      data: $tenVal3,
//    }, {
//      label: 'Forest Land',
//      backgroundColor: "#ff9933",
//      data: $tenVal4,
//    }, {
//      label: 'Others',
//      backgroundColor: "#ffbf80",
//      data: $tenVal5,
//    }],
//  },
//options: {
//    tooltips: {
//      displayColors: true,
//      callbacks:{
//        mode: 'x',
//      },
//    },
//    scales: {
//      x: {
//        stacked: true,
//        ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//      },
//       y: {
//        stacked: true,
//        ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//      }
//    },
//    responsive: true
//  }
//});
//}
//});
//
//
///************************************************Top Ten State wise below 100% Completion of Irrigation Status****************************************************/
//$tenirrLab1 = new Array();
//$tenirrVal1 = new Array();
//$tenirrVal2 = new Array();
//$tenirrVal3 = new Array();
//
//	$.ajax({
//		url: "getTopTenNonCompIrriPrcntg",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(data) {
//			for (var key in data) {
//				$tenirrLab1.push(data[key].stname);
//				$tenirrVal1.push(data[key].protective_irrigation_prcntg);
//				$tenirrVal2.push(data[key].assured_irrigation_prcntg);
//				$tenirrVal3.push(data[key].no_irrigation_prcntg);
//				
//			}
//var ctx = document.getElementById("topTenStIrri").getContext('2d');
//var myChart = new Chart(ctx, {
//  type: 'bar',
//  data: {
//    labels: $tenirrLab1,
//    datasets: [{
//      label: 'Protective Irrigation',
//      backgroundColor: "#007acc",
//      data: $tenirrVal1,
//    }, {
//      label: 'Assured Irrigation',
//      backgroundColor: "#33adff",
//      data: $tenirrVal2,
//    }, {
//      label: 'No Irrigation',
//      backgroundColor: "#80ccff",
//      data: $tenirrVal3,
//    }],
//  },
//options: {
//    tooltips: {
//      displayColors: true,
//      callbacks:{
//        mode: 'x',
//      },
//    },
//    scales: {
//      x: {
//        stacked: true,
//        ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//      },
//       y: {
//        stacked: true,
//        ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//      }
//    },
//    responsive: true
//  }
//});
//}
//});
//
//
///************************************************Top Ten State wise below 100% Completion of No. of Crops****************************************************/
//$tencrpLab1 = new Array();
//$tencrpVal1 = new Array();
//$tencrpVal2 = new Array();
//$tencrpVal3 = new Array();
//$tencrpVal4 = new Array();
//
//	$.ajax({
//		url: "getTopTenNonCompCropPrcntg",
//		type: "post",
//		data: {},
//		error: function(xhr, status, er) {
//			console.log(er);
//		},
//		success: function(data) {
//			for (var key in data) {
//				$tencrpLab1.push(data[key].stname);
//				$tencrpVal1.push(data[key].no_crop);
//				$tencrpVal2.push(data[key].single_crop);
//				$tencrpVal3.push(data[key].double_crop);
//				$tencrpVal4.push(data[key].multiple_crop);
//				
//			}
//var ctx = document.getElementById("topTenStCrop").getContext('2d');
//var myChart = new Chart(ctx, {
//  type: 'bar',
//  data: {
//    labels: $tencrpLab1,
//    datasets: [{
//      label: 'No Crop',
//      backgroundColor: "#4d4d33",
//      data: $tencrpVal1,
//    }, {
//      label: 'Single Crop',
//      backgroundColor: "#7a7a52",
//      data: $tencrpVal2,
//    }, {
//      label: 'Double Crop',
//      backgroundColor: "#a3a375",
//      data: $tencrpVal3,
//    }, {
//      label: 'Multiple Crop',
//      backgroundColor: "#ccccb3",
//      data: $tencrpVal4,
//    }],
//  },
//options: {
//    tooltips: {
//      displayColors: true,
//      callbacks:{
//        mode: 'x',
//      },
//    },
//    scales: {
//      x: {
//        stacked: true,
//        ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//      },
//       y: {
//        stacked: true,
//        ticks:{
//			font: {
//				weight: 'bold'
//			}
//		}
//      }
//    },
//    responsive: true
//  }
//});
//}
//});

/************************Group Bar Chart for Total Sanction Amount and Treasury Receipt and Expenditure****************************/
$grpBarLab = new Array();
$grpBarVal1 = new Array();
$grpBarVal2 = new Array();
$grpBarVal3 = new Array();
$grpBarVal4 = new Array();
	$.ajax({
		url: "getStwiseSancExpndtrRcpt",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$grpBarLab.push(data[key].stname);
				$grpBarVal1.push(data[key].sanctioned_amt);
				$grpBarVal2.push(data[key].total_expen);
				$grpBarVal3.push(data[key].treasury_receipt);
				$grpBarVal4.push(data[key].sanctioned_amt - data[key].total_expen - data[key].treasury_receipt);
			}
var ctx = document.getElementById("groupBarChart2").getContext('2d');
var myGroupBarChart2 = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $grpBarLab,
    datasets: [{
      label: 'Project Cost',
      data: $grpBarVal1,
      backgroundColor: "#86b300",
      borderColor: "green",
      borderWidth: 1
     },
     {
	  label: 'Expenditure',
      data: $grpBarVal2,
      backgroundColor: "#bfff00",
      borderColor: "cyan",
      borderWidth: 1
    }],
  },
  options:{
  responsive: true,
  legend: {
    position: "top"
  },
  title: {
    display: true,
    text: "Chart.js Bar Chart"
  },
  scales: {
	x: {
		ticks:{
			font: {
				weight: 'bold'
			}
		}
	},
    y: {
      ticks: {
        beginAtZero: true,
        font: {
			weight: 'bold'
		}
      },
      title: {
        display: true,
        text: 'Rs.(in lakhs)',
         font: {
                 size: 15,
                 weight: 'bold'
         }
      }
    }
  }
}
});

var ctx = document.getElementById("groupBarChart3").getContext('2d');
var myGroupBarChart3 = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $grpBarLab,
    datasets: [{
      label: 'Project Cost',
      data: $grpBarVal1,
      backgroundColor: "#0099cc",
      borderColor: "brown",
      borderWidth: 1
     },
     {
	  label: 'Treasury Receipt',
      data: $grpBarVal3,
      backgroundColor: "#1ac6ff",
      borderColor: "grey",
      borderWidth: 1
    }],
  },
  options:{
  responsive: true,
  legend: {
    position: "top"
  },
  scales: {
    x: {
		ticks:{
			font: {
				weight: 'bold'
			}
		}
	},
    y: {
      ticks: {
        beginAtZero: true,
        font: {
		    fontColor: '#ff9933',
			weight: 'bold'
		}
      },
      title: {
        display: true,
        text: 'Rs.(in lakhs)',
         font: {
                 size: 15,
                 weight: 'bold'
         }
      }
    }
  }
}
});

var ctx = document.getElementById("groupBarChart1").getContext('2d');
var myGroupBarChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $grpBarLab,
    datasets: [{
      label: 'Expenditure',
      backgroundColor: "#cc2900",
      data: $grpBarVal2,
    }, {
      label: 'Treasury Receipt',
      backgroundColor: "#ff8080",
      data: $grpBarVal3,
    }, {
      label: 'Project Cost',
      backgroundColor: "#ffe6e6",
      data: $grpBarVal4,
    }],
  },
options: {
    tooltips: {
      displayColors: true,
      callbacks:{
        mode: 'x',
      },
    },
    scales: {
      x: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      },
       y: {
        stacked: true,
        ticks:{
			font: {
				weight: 'bold'
			}
		}
      }
    },
    responsive: true
  }
});
}
});


/**********************************************Project Area Bar Chart***************************************************/
$barAreaLab = new Array();
$barAreaVal1 = new Array();
	$.ajax({
		url: "getTopTenStwiseBlsCmpltd",
		type: "post",
		data: {},
		error: function(xhr, status, er) {
			console.log(er);
		},
		success: function(data) {
			for (var key in data) {
				$barAreaLab.push(key);
				$barAreaVal1.push(data[key]);
			}
var ctx = document.getElementById("prcntgArea").getContext('2d');
var myBarChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: $barAreaLab,
    datasets: [{
      label: 'Total Percentage Area',
      data: $barAreaVal1,
      backgroundColor: "#007acc",
      borderColor: "#005c99",
      borderWidth: 1
     }],
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});
}
});



});



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
							$('#popupreport #popupreporttitle').html('STATE WISE DATA');
							var i=1;
							if(Object.keys(DolrDashboardBean).length>0){
								
		for ( var key in DolrDashboardBean) {
			if (DolrDashboardBean.hasOwnProperty(key)) {
				if(parseInt(i)===1){
				i=0;
				$('#popupreport #popupreporttitle').append();
				}
				tblData+="<tr><td style='color:blue;'><u><a class='nav-link' name = "+DolrDashboardBean[key].headerdesc+"  onclick='showDistrict(this.id, this.name);' id="+DolrDashboardBean[key].st_code+"><input type='hidden' name='data' id='data' value="+name+">"+DolrDashboardBean[key].stname+"</a></u></td><td>"+DolrDashboardBean[key].headerdesc+"</td></tr>";
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
							$('#popupreport').modal('toggle');
							$('#popupreport').modal('show');
							$('#popupreport #popupreporttitle').html('DISTRICT WISE DATA');
							var i=1;
							if(Object.keys(DolrDashboardBean).length>0){
								
		for ( var key in DolrDashboardBean) {
			if (DolrDashboardBean.hasOwnProperty(key)) {
				if(parseInt(i)===1){
				i=0;
				$('#popupreport #popupreporttitle').append();
				}
				tblData+="<tr><td>"+DolrDashboardBean[key].dist_name+"</a></td><td>"+DolrDashboardBean[key].headerdesc+"</td></tr>";
			}		
				}
		}else{
			tblData="<tr><td>Data not found !</td></tr>";
		}
		$('#popupreport .modal-body').html('<table class="" >'+
							'<thead><tr><th style="width:40%"><center>District Name</center></th><th>'+DolrDashboardBean[key].headdesc+'</th></tr></thead><tbody>'+tblData+'</tbody><tr><th><center>Total</center></th><th><center>'+totaldata+'</center></th></tr></table>');
							
							}  
	            
	            
	  });
	}	



