
function closeNav() {
  document.getElementById("sidebar").style.width = "0";
    
}

function openSidebar() {
  document.getElementById("sidebar").style.width = "250px";
 }

var cultivable = document.getElementById('cultivable_wasteland').value;
var degraded = document.getElementById('degraded_land').value;
var rainfed = document.getElementById('rainfed').value;
var forest = document.getElementById('forest_land').value;
var others = document.getElementById('others').value;

var ctx = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        datasets: [{
            label: 'Baseline',
            data: [cultivable, degraded, rainfed, forest, others],
            backgroundColor: [
                'rgba(41,155,99,1)',
                'rgba(54,162,235,1)',
                'rgba(255,206,86,1)',
                'rgba(120,46,139,1)',
                'rgba(189,17,106,0.8)'
                
            ],
            borderColor:[
				'rgba(41,155,99,1)',
                'rgba(54,162,235,1)',
                'rgba(255,206,86,1)',
                'rgba(120,46,139,1)',
                'rgba(255,206,86,1)',
                'rgba(189,17,106,0.8)'
			],
            borderWidth:1
        }],
    labels: ['Cultivable', 'Degraded', 'Rainfed', 'Forest Land', 'Others'],
        
    },
    options: {
        responsive: true,
    }
});

var cultivableD = document.getElementById('cultivableondate').value;
var degradedD = document.getElementById('degradedondate').value;
var rainfedD = document.getElementById('rainfedondate').value;
var forestD = document.getElementById('forestondate').value;
var othersD = document.getElementById('othersondate').value;


var ctx = document.getElementById('myChartBaseline').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        datasets: [{
            label: 'Baseline',
            data: [cultivableD, degradedD, rainfedD, forestD, othersD],
            backgroundColor: [
                'rgba(41,155,99,1)',
                'rgba(54,162,235,1)',
                'rgba(255,206,86,1)',
                'rgba(120,46,139,1)',
                'rgba(189,17,106,0.8)'
                
            ],
            borderColor:[
				'rgba(41,155,99,1)',
                'rgba(54,162,235,1)',
                'rgba(255,206,86,1)',
                'rgba(120,46,139,1)',
                'rgba(189,17,106,0.8)'
			],
            borderWidth:1
        }],
    labels: ['Cultivable', 'Degraded', 'Rainfed', 'Forest Land', 'Others'],
        
    },
    options: {
        responsive: true,
    }
});

var protectiveBase = document.getElementById('protectiveBase').value;
var assuredBase = document.getElementById('assuredBase').value;
var noirrBase = document.getElementById('noirrBase').value;
var otherBase = document.getElementById('otherBase').value;


var ctxi = document.getElementById('irrgationBaseline').getContext('2d');
var myChartI = new Chart(ctxi, {
    type: 'doughnut',
    data: {
        labels: ['Protective', 'Assured', 'No-Irrigation', 'Others'],
        datasets: [{
            label: 'Baseline',
            data: [protectiveBase,assuredBase,noirrBase,otherBase],
            backgroundColor: [
                'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
                
            ],
            borderColor:[
				'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
			],
            borderWidth:1
        }],
    
        
    },
    options: {
        responsive: true,
    }
});

var protectiveDate = document.getElementById('protectiveDate').value;
var assuredDate = document.getElementById('assuredDate').value;
var noirrDate = document.getElementById('noirrDate').value;
var otherDate = document.getElementById('otherDate').value;


var ctx1 = document.getElementById('myChartirrigation').getContext('2d');
var myChart2 = new Chart(ctx1, {
    type: 'doughnut',
    data: {
        labels: ['Protective', 'Assured', 'No-Irrigation', 'Others'],
        datasets: [{
            label: 'Baseline',
            data: [protectiveDate, assuredDate, noirrDate, otherDate],
            backgroundColor: [
                'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
                
            ],
            borderColor:[
				'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
			],
            borderWidth:1
        }],
    
        
    },
    options: {
        responsive: true,
    }
});


var privateBase = document.getElementById('privateBase').value;
var govtBase = document.getElementById('govtBase').value;
var communityBase = document.getElementById('communityBase').value;
var otherBase = document.getElementById('otherBase').value;


var ctx1 = document.getElementById('myChartownership').getContext('2d');
var myChart2 = new Chart(ctx1, {
    type: 'pie',
    data: {
        labels: ['Private', 'Government', 'Community', 'Others'],
        datasets: [{
            label: 'Baseline',
            data: [privateBase, govtBase, communityBase, otherBase],
            backgroundColor: [
                'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
                
            ],
            borderColor:[
				'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
			],
            borderWidth:1
        }],
    
        
    },
    options: {
        responsive: true,
    }
});

var privateDate = document.getElementById('privateDate').value;
var govtDate = document.getElementById('govtDate').value;
var communityDate = document.getElementById('communityDate').value;
var otherODate = document.getElementById('otherODate').value;


var ctx1 = document.getElementById('myChartownerDate').getContext('2d');
var myChart2 = new Chart(ctx1, {
    type: 'pie',
    data: {
        labels: ['Private', 'Government', 'Community', 'Others'],
        datasets: [{
            label: 'Baseline',
            data: [privateDate, govtDate, communityDate, otherODate],
            backgroundColor: [
                'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
                
            ],
            borderColor:[
				'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
			],
            borderWidth:1
        }],
    
        
    },
    options: {
        responsive: true,
    }
});

var nocropBase = document.getElementById('nocropBase').value;
var singlecropBase = document.getElementById('singlecropBase').value;
var doublecropBase = document.getElementById('doublecropBase').value;
var multicropBase = document.getElementById('multicropBase').value;


var ctx1 = document.getElementById('myChartcropBase').getContext('2d');
var myChart2 = new Chart(ctx1, {
    type: 'doughnut',
    data: {
        labels: ['No Crops', 'Single Crops', 'Double Crops', 'Multiple Crops'],
        datasets: [{
            label: 'Baseline',
            data: [nocropBase, singlecropBase, doublecropBase, multicropBase],
            backgroundColor: [
                'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
                
            ],
            borderColor:[
				'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
			],
            borderWidth:1
        }],
    
        
    },
    options: {
        responsive: true,
    }
});

var nocropDate = document.getElementById('nocropDate').value;
var singlecropDate = document.getElementById('singlecropDate').value;
var doublecropDate = document.getElementById('doublecropDate').value;
var multicropDate = document.getElementById('multicropDate').value;


var ctx1 = document.getElementById('myChartcropDate').getContext('2d');
var myChart2 = new Chart(ctx1, {
    type: 'doughnut',
    data: {
        labels: ['No Crops', 'Single Crops', 'Double Crops', 'Multiple Crops'],
        datasets: [{
            label: 'Baseline',
            data: [nocropDate, singlecropDate, doublecropDate, multicropDate],
            backgroundColor: [
                'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
                
            ],
            borderColor:[
				'rgba(205, 4, 35, 0.88)',
                'rgba(31, 205, 4, 0.88)',
                'rgba(4, 145, 205, 0.88)',
                'rgba(69, 4, 205, 0.88)'
			],
            borderWidth:1
        }],
    
        
    },
    options: {
        responsive: true,
    }
});