<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/dashboardheader.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Project Evaluation DashBoard</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


<style>
 /* Boxes */
.component-container {
	display: flex;
	flex-direction: column;
	background-color: #FAF9F6;
	margin: 20px;
	padding: 10px;
	border: 1px solid gray;
	border-radius: 10px;
	width: 1870px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.field-row {
	display: flex;
	flex-wrap: wrap;
	padding-left: 170px;
	padding-right: 170px;
	align-self: center;
}

.field-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-items: left;
    padding-top: 10px;
    margin: 10px;
    padding: 20px;
    width: 280px;
    height: 150px;
    border-radius: 15px;
    background-color: #ffffff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    z-index: 1;
    transition: transform 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
}

/* Color themes for each box */
.field-container:nth-child(1) {
    background: linear-gradient(135deg, #3399FF, #66D9FF); /* Sky Surge */
    color: #333;
}

.field-container:nth-child(2) {
    background: linear-gradient(135deg, #FF6F61, #F9D87A); /* Sunset Ember */
    color: #333;
}

.field-container:nth-child(3) {
    background: linear-gradient(135deg, #33F5C0, #33E0F0); /* Mint Pulse */
    color: #333;
}

.field-container:nth-child(4) {
    background: linear-gradient(135deg, #FFC0CB, #E0B3FF); /* Blush Bloom */
    color: #333;
}

.field-container:nth-child(1):hover {
    background-color: #fff3f0;
    box-shadow: 0 0 20px rgba(255, 110, 64, 0.4);
    transform: scale(1.05);
    border: 3px solid #1a5fa0;
}

.field-container:nth-child(2):hover {
    background-color: #fff0f5;
    box-shadow: 0 0 20px rgba(255, 175, 189, 0.4);
    transform: scale(1.05);
    border: 3px solid #cc3f3f;
}

.field-container:nth-child(3):hover {
    background-color: #f0f9fb;
    box-shadow: 0 0 20px rgba(166, 193, 238, 0.4);
    transform: scale(1.05);
    border: 3px solid #1aa88f;
}

.field-container:nth-child(4):hover {
    background-color: #fceef7;
    box-shadow: 0 0 20px rgba(171, 193, 238, 0.4);
    transform: scale(1.05);
    border: 3px solid #b84acb;
}

.field-title {
	position: relative;
	margin-left: 10px;
    padding-left: 30px;
    font-weight: 550;
}

.field-title::before {
    content: "";
    background: url('resources/images/chart_logo.jpg') no-repeat center center;
    background-size: 15px 15px;
    width: 25px;
    height: 20px;
    position: absolute;
    left: 0;
    top: 12px;
    transform: translateY(-50%);
    background-color: white;
    border-radius: 100%;
    
}
.field-value {
    color: white;
    font-weight: bold;
    font-size: xx-large;
    text-align: center;
    -webkit-text-stroke: 1px rgba(0, 0, 0, 0.2);
    transition: transform 0.3s ease, text-shadow 0.3s ease;
}

.field-container:hover .field-value {
    transform: scale(1.2);
    text-shadow: 0 0 10px rgba(255, 255, 255, 0.7);
    animation: pulse 0.6s ease-in-out;
}

@keyframes pulse {
  0% { transform: scale(1.2); }
  50% { transform: scale(1.25); }
  100% { transform: scale(1.2); }
}


/* Charts & Graphs */
.dashboard-frame {
    background-color: #FAF9F6;
    border: 1px solid #ccc;
    border-radius: 15px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    padding: 20px;
    margin: 20px;
    width: 1870px;
}

.frame-title {
    text-align: center;
    font-size: 1.5rem;
    color: #2C3E50;
    margin-bottom: 20px;
    font-weight: bold;
}

.frame-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
}

.chart-wrapper {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;
    width: 100%;
    background-color: #F4F8F9;
}

.chart-container {
    flex: 1 1 45%;
    min-width: 800px;
    max-width: 100%;
    background-color: #F4F8F9;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    padding: 20px;
    text-align: center;
    margin: auto;
    height: 500px;
} 

.chart-navigation {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
}

.nav-icon {
    width: 30px;
    height: 30px;
    cursor: pointer;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    transition: transform 0.2s ease-in-out;
    opacity: 0.7;
}

.nav-icon:hover {
   transform: scale(1.2);
    opacity: 1;
}

.left-icon {
    left: -25px;
}

.right-icon {
    right: -15px;
}

@media screen and (max-width: 1300px) {
    .chart-container {
        flex: 1 1 100%;
    }
}
  
#gradePieChart {
    display: block;
    margin: 0 auto;
    width: 100%;
    height: 100%;
	padding: 0;
}

canvas {
  transition: opacity 0.3s ease-in-out;
}

</style>

</head>

<script>
// Boxes
    function animateCounter(id, target) {
        let count = 0;
        const step = Math.ceil(target / 50);
        const interval = setInterval(() => {
            count += step;
            if (count >= target) {
                count = target;
                clearInterval(interval);
            }

            const container = document.getElementById(id);
            const link = container.querySelector("a");
            if (link) {
                link.textContent = count;
            } else {
                container.textContent = count;
            }
        }, 30);
    }

    window.onload = function() {
        animateCounter("counter1", parseInt(document.getElementById("counter1").innerText));
        animateCounter("counter2", parseInt(document.getElementById("counter2").innerText));
        animateCounter("counter3", parseInt(document.getElementById("counter3").innerText));
        animateCounter("counter4", parseInt(document.getElementById("counter4").innerText));
    };


//Pie Charts code
$(document).ready(function () {
    const gradeLabels = [];
    const gradeValues = [];
    const backgroundColors = [];

    const gradeMap = {
        'E': { label: 'Excellent', color: '#2ECC71' },
        'G': { label: 'Very Good', color: '#3498DB' },
        'S': { label: 'Satisfactory', color: '#F1C40F' },
        'A': { label: 'Average', color: '#E74C3C' }
    };

    const labelToGradeCode = {
        'Excellent': 'E',
        'Very Good': 'G',
        'Satisfactory': 'S',
        'Average': 'A'
    };

    // Populate pie chart data
    <c:forEach var="item" items="${pieGradeData}">
        gradeLabels.push(gradeMap["${item.grade}"].label);
        gradeValues.push(Number(${item.grade_percentage}));
        backgroundColors.push(gradeMap["${item.grade}"].color);
    </c:forEach>

    const pieCtx = document.getElementById('gradePieChart').getContext('2d');
    const barCtx = document.getElementById('gradeBarChart').getContext('2d');

    let gradeStateLabels = [];
    let gradeStateValues = [];
    let gradeStateIndex = 0;
    const range = 10;
    let hoverTimeout;

    // Initialize pie chart
    const pieChart = new Chart(pieCtx, {
        type: 'pie',
        data: {
            labels: gradeLabels,
            datasets: [{
                data: gradeValues,
                backgroundColor: backgroundColors,
                borderColor: '#fff',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    font: { size: 20 }
                },
                legend: {
                    position: 'right',
                    labels: {
                        font: { size: 14 },
                        color: '#2C3E50'
                    }
                }
            },
            onHover: function (event, chartElement) {
                clearTimeout(hoverTimeout);
                if (chartElement.length > 0) {
                    const index = chartElement[0].index;
                    const label = pieChart.data.labels[index];
                    const gradeCode = labelToGradeCode[label];
                    const color = pieChart.data.datasets[0].backgroundColor[index];
                    const titleElement = document.getElementById('gradeBarTitle');

                    hoverTimeout = setTimeout(() => {
                        fetchStateDataByGrade(gradeCode, color);
                        
                        if (titleElement) {
                            titleElement.textContent = `State-wise Mid Term Project Evaluation `+label+` Performance`;
                        }
                    }, 150);
                }
            }
        }
    });

    // Initialize bar chart with default data (grade "E")
    <c:forEach var="entry" items="${GradeStateData}">
        gradeStateLabels.push("${entry.st_name}");
        gradeStateValues.push(Number(${entry.grade_percentage}));
    </c:forEach>

    const barChart = new Chart(barCtx, {
        type: 'bar',
        data: {
            labels: gradeStateLabels.slice(gradeStateIndex, gradeStateIndex + range),
            datasets: [{
                label: 'Grade Percentage',
                data: gradeStateValues.slice(gradeStateIndex, gradeStateIndex + range),
                backgroundColor: gradeMap['E'].color,
                borderColor: '#fff',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { title: { display: true, text: 'States' } },
                y: { beginAtZero: true, title: { display: true, text: 'Percentage' } }
            },
            plugins: {
                title: {
                    display: true,
                    font: { size: 20 }
                },
                legend: {
                    display: true,
                    labels: {
                      font: { size: 12 },
                      color: '#2C3E50'
                    }
                  }
            }
        }
    });


    // AJAX call to update bar chart based on hovered grade
    function fetchStateDataByGrade(gradeCode, color) {
        $.ajax({
            url: 'getStateDataByGrade',
            type: 'GET',
            dataType: 'json',
            data: { grade: gradeCode },
            success: function (data) {
                gradeStateLabels = data.map(item => item.st_name);
                gradeStateValues = data.map(item => item.grade_percentage);
                gradeStateIndex = 0;

                barChart.data.labels = gradeStateLabels.slice(0, range);
                barChart.data.datasets[0].data = gradeStateValues.slice(0, range);
                barChart.data.datasets[0].backgroundColor = color;
                barChart.update();
            },
            error: function (xhr, status, error) {
                console.error("Error fetching state data:", error);
            }
        });
    }

    // Navigation for grade state bar chart
    $('#nextGradeState').click(() => {
        if (gradeStateIndex + range < gradeStateLabels.length) {
            gradeStateIndex++;
            updateChart(barChart, gradeStateLabels, gradeStateValues, gradeStateIndex);
        }
    });

    $('#prevGradeState').click(() => {
        if (gradeStateIndex > 0) {
            gradeStateIndex--;
            updateChart(barChart, gradeStateLabels, gradeStateValues, gradeStateIndex);
        }
    });


    function updateChart(chart, labels, values, index) {
        chart.canvas.style.opacity = 0.3;
        setTimeout(() => {
            chart.data.labels = labels.slice(index, index + range);
            chart.data.datasets[0].data = values.slice(index, index + range);
            chart.update();
            chart.canvas.style.opacity = 1;
        }, 200);
    }
});


// Graphs code
const range = 10;
let stateChart, districtChart;
let hoverTimeout, lastHoveredStateCode = null;

let stateLabels = [], stateValues = [];
let districtLabels = [], districtValues = [];
let stateIndex = 0, districtIndex = 0;

const stateCodeMap = {
  <c:forEach var="entry" items="${stateWiseData}" varStatus="loop">
    '<c:out value="${entry.st_name}"/>' : <c:out value="${entry.st_code}"/><c:if test="${!loop.last}">,</c:if>
  </c:forEach>
};


function updateChart(chart, labels, values, index) {
	  chart.canvas.style.opacity = 0.3;
	  setTimeout(() => {
	    chart.data.labels = labels.slice(index, index + range);
	    chart.data.datasets[0].data = values.slice(index, index + range);
	    chart.update();
	    chart.canvas.style.opacity = 1;
	  }, 200);
	}
	

function handleStateHover(stateName) {
	  clearTimeout(hoverTimeout);

	  hoverTimeout = setTimeout(() => {
	    const stcd = stateCodeMap[stateName];

	    if (stcd === lastHoveredStateCode) {
	      return;
	    }

	    lastHoveredStateCode = stcd;
	    
	    const titleElement = document.getElementById('districtTitle');
	    if (titleElement) {
	      titleElement.textContent = `District-wise Mid Term Project Evaluation – ` + stateName;
	    }

	    fetchDistrictData(stcd);
	  }, 150);
	}


function fetchDistrictData(stcd) {
	  $('#loading').hide();
	  $.ajax({
	    url: 'getDistrictData',
	    type: 'GET',
	    dataType: 'json',
	    data: { stcd: stcd },
	    success: function(data) {

	    	districtLabels = data.map(item => item.distname);
	        districtValues = data.map(item => item.grade_percentage);
	        districtIndex = 0;

	      updateChart(districtChart, districtLabels, districtValues, districtIndex);
	      $('#loading').hide();
	    },
	    error: function(xhr, status, error) {
	      console.error("AJAX error:", status, error);
	      console.error("Response text:", xhr.responseText);
	      $('#loading').hide();
	    }
	  });
	}
	

$(document).ready(function () {

	$('#loading').hide();
	
  <c:if test="${not empty stateWiseData}">
  <c:forEach var="entry" items="${stateWiseData}">
    stateLabels.push('<c:out value="${entry.st_name}"/>');
    stateValues.push(Number('<c:out value="${entry.grade_percentage}"/>'));
  </c:forEach>
  </c:if>

  <c:if test="${not empty distPEData}">
  <c:forEach var="entry" items="${distPEData}">
    districtLabels.push('<c:out value="${entry.distname}"/>');
    districtValues.push(Number('<c:out value="${entry.grade_percentage}"/>'));
  </c:forEach>
  </c:if>

  const stateCtx = document.getElementById('stateChart').getContext('2d');
  const districtCtx = document.getElementById('districtChart').getContext('2d');

  stateChart = new Chart(stateCtx, {
    type: 'bar',
    data: {
      labels: stateLabels.slice(stateIndex, stateIndex + range),
      datasets: [{
        label: 'Grade wise Percentage',
        data: stateValues.slice(stateIndex, stateIndex + range),
        backgroundColor: 'rgba(54, 162, 235, 0.6)'
      }]
    },
    options: {
      responsive: true,
      onHover: function(event, chartElement) {
    	  event.preventDefault?.();
    	  event.stopPropagation?.();

    	    if (chartElement.length > 0) {
    	      const index = chartElement[0].index + stateIndex;
    	      const stateName = stateLabels[index];
    	      const stcd = stateCodeMap[stateName];
  	    	 handleStateHover(stateName);
    	    }
    	  },
      scales: {
        x: { title: { display: true, text: 'States' } },
        y: { beginAtZero: true, title: { display: true, text: 'Percentage' } }
      }
    }
  });

  districtChart = new Chart(districtCtx, {
    type: 'bar',
    data: {
      labels: districtLabels.slice(districtIndex, districtIndex + range),
      datasets: [{
        label: 'Grade wise Percentage',
        data: districtValues.slice(districtIndex, districtIndex + range),
        backgroundColor: 'rgba(255, 99, 132, 0.6)'
      }]
    },
    options: {
      responsive: true,
      scales: {
        x: { title: { display: true, text: 'Districts' } },
        y: { beginAtZero: true, title: { display: true, text: 'Percentage' } }
      }
    }
  });

  $('#nextState').click(() => {
    if (stateIndex + range  < stateLabels.length) {
      stateIndex++;
      updateChart(stateChart, stateLabels, stateValues, stateIndex);
    }
  });

  $('#prevState').click(() => {
    if (stateIndex > 0) {
      stateIndex--;
      updateChart(stateChart, stateLabels, stateValues, stateIndex);
    }
  });

  $('#nextDistrict').click(() => {
    if (districtIndex + range  < districtLabels.length) {
      districtIndex++;
      updateChart(districtChart, districtLabels, districtValues, districtIndex);
    }
  });

  $('#prevDistrict').click(() => {
    if (districtIndex > 0) {
      districtIndex--;
      updateChart(districtChart, districtLabels, districtValues, districtIndex);
    }
  });   
});

</script>

<body>

<h2 class="panel-title" style ="text-align: center; margin: 10px;">Mid Term Project Evaluation DashBoard</h2>


 <p style ="text-align: right; margin: 20px; font-weight: bold;">As on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 <div class="panel-body" style ="text-align: center; margin: 20px;"></div>

        <div class="component-container">
        	<div class="field-row">
                <c:forEach var="dt" items="${peData}">
                    <div class="field-container">
                        <div class="field-title">Total States</div>
                        <div class="field-value" id="counter1">
                        	<a href="stateMidProjEvoluationRpt" target="_blank" style="color: white; text-decoration: underline; text-decoration-color: white;">
            					${dt.total_state}
        					</a>
                        </div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total States Entered Mid Term Evaluation</div>
                        <div class="field-value"  id="counter2">${dt.entered_states}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Projects</div>
                        <div class="field-value"  id="counter3">${dt.total_project}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Projects Entered Mid Term Evaluation</div>
                        <div class="field-value"  id="counter4">${dt.entered_projects}</div>
                    </div>
                </c:forEach>
            </div>
		</div>


<div class="dashboard-frame">
    <div class="chart-wrapper">
        <!-- Pie Chart Block -->
        <div class="chart-container">
            <h4 class="frame-title">Grade-wise Mid Term Project Evaluation</h4>
            <canvas id="gradePieChart"></canvas>
        </div>


        <div class="chart-container">
            <h4 id="gradeBarTitle" class="frame-title">State-wise Mid Term Project Evaluation Excellent Performance</h4>
            <div class="chart-navigation">
            <img id="prevGradeState" src="<c:url value='/resources/images/back-button.png'/>" alt="Previous" class="nav-icon left-icon" />
    		<canvas id="gradeBarChart"></canvas>
    		<img id="nextGradeState" src="<c:url value='/resources/images/forward-button.png'/>" alt="Next" class="nav-icon right-icon" />
    		</div>
        </div>
    </div>
</div>


	<div class="dashboard-frame">
    <div id="loading" style="display:none; text-align:center; font-weight:bold;">Loading...</div>
    		<div class="frame-content">
				<div class="chart-wrapper">
        			<div class="chart-container">
    					<h4 class="frame-title">State-wise Mid Term Project Evaluation</h4>
    				<div class="chart-navigation">
        				<img id="prevState" src="<c:url value='/resources/images/back-button.png'/>" alt="Previous" class="nav-icon left-icon" />
        				<canvas id="stateChart"></canvas>
        				<img id="nextState" src="<c:url value='/resources/images/forward-button.png'/>" alt="Next" class="nav-icon right-icon" />
    				</div>
					</div>	
    				<div class="chart-container">
    				<h4 id="districtTitle" class="frame-title">District-wise Mid Term Project Evaluation - ${initialStateName}</h4>
    					<div class="chart-navigation">
        				<img id="prevDistrict" src="<c:url value='/resources/images/back-button.png'/>" alt="Previous" class="nav-icon left-icon" />
        				<canvas id="districtChart"></canvas>
        				<img id="nextDistrict" src="<c:url value='/resources/images/forward-button.png'/>" alt="Next" class="nav-icon right-icon" />
    					</div>
					</div>	
			</div>
		</div>
		
		
</div>
<!-- <footer class="text-center"> -->
<%--     <%@include file="/WEB-INF/jspf/footer.jspf"%> --%>
<!-- </footer>    -->
</body>



</html>