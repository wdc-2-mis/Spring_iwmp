<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/dashboardheader.jspf"%>

<!DOCTYPE html>
<html>
<head>
    <title>Watershed Dashboard</title>
     <style>
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
	display: flex;
	flex-direction: column;
	background-color: white;
	margin: 10px;
	padding-top: 10px;
	border: 1px solid #ddd;
	border-radius: 10px;
	width: 280px;
	height: 150px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.component-field {
	margin-left: 10px;
	color: Green;
	align-self: center;
}

.field-title {
	position: relative;
	margin-left: 10px;
    padding-left: 20px; /* Adjust padding to create space for the logo */ 
}

.field-title::before {
    content: "";
    background: url('resources/images/chart_logo.jpg') no-repeat center center;
    background-size: 15px 15px; /* Adjust the size */
    width: 15px;  /* Adjust width as needed */
    height: 15px; /* Adjust height as needed */
    position: absolute;
    left: 0;
    top: 12px;
    transform: translateY(-50%);
}

.field-value {
	color: cornflowerblue;
	font-weight: bold;
	font-size: xx-large;
	text-align: center;
}

.panel-underline {
	border-top: 2px solid black;
	margin-left: 10px;
}

 .chart-container {
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-wrap: nowrap;
  }
  .chart-item {
    text-align: center;
    margin: 10px;
  }
  .chart-title {
    font-weight: bold;
    margin-bottom: 10px;
  }
  #chart_div1{
  	border: 1px solid #ccc;
  	background-color: #FAF9F6; 
  	padding: 15px; 
  	width: 500px; 
  	height: 600px; 
  	margin: 10px; 
  	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); 
  	border-radius: 10px;
  }
  #chart_div2{
  	border: 1px solid #ccc;
  	background-color: #FAF9F6; 
  	padding: 15px; 
  	width: 500px; 
  	height: 600px; 
  	margin: 10px; 
  	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); 
  	border-radius: 10px;
  }
  .piechart-container{
  	border: 1px solid #ccc;
  	background-color: #FAF9F6; 
  	padding: 15px; 
  	width: 500px; 
  	height: 600px; 
  	margin: 10px; 
  	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); 
  	border-radius: 10px;
  }
</style>
</head>
<body>

<h2 class="panel-title" style ="text-align: center; margin: 10px;">Watershed Yatra Dashboard</h2>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/solid-gauge.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
 $(document).ready(function(){
	 $('#loading').hide();
 });
 </script>
 <div class="panel-body" style ="text-align: center; margin: 20px;"></div>
<div>
    <c:forEach var="entry" items="${map}">
        <div class="component-container">
            <c:if test ="${entry.key eq 'wtr'}">
            <h3 class ="component-field">Watershed Yatra Activities</h3>
            <div class="panel-underline"></div>
            <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
                    <div class="field-container">
                        <div class="field-title">Total States</div>
                        <div class="field-value">${bean.totstates}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of People Participated</div>
                        <div class="field-value">${bean.totparticipants}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Numbers of Works for BHOOMI POOJAN</div>
                        <div class="field-value">${bean.totbhumipujanworks}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Numbers of Works for LOKARPAN</div>
                        <div class="field-value">${bean.totlokarpanworks}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Shramdaan on Total Location</div>
                        <div class="field-value">${bean.totshramdannlocationno}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Award Distribution</div>
                        <div class="field-value">${bean.totawarddistribution}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of Yatra Location</div>
                        <div class="field-value">${bean.totyatraloc}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of AR Experienced People</div>
                        <div class="field-value">${bean.totarexperiencedpeople}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of People Participated in Quiz</div>
                        <div class="field-value">${bean.totquizparticipants}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of Sapling Planted</div>
                        <div class="field-value">${bean.totplantation}</div>
                    </div>
                    
                </c:forEach>
            </div>
            </c:if>

            <c:if test ="${entry.key eq 'pre'}">
            <h3 class ="component-field">Pre Yatra</h3>
            <div class="panel-underline"></div>
            <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
                    <div class="field-container">
                        <div class="field-title">Total States</div>
                        <div class="field-value">${bean.totstates}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of Gram Sabha Organized</div>
                        <div class="field-value">${bean.totgrabsabha}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of People Participated in Gram Sabha</div>
                        <div class="field-value">${bean.gramsabha_participants}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of Prabhat Pheri Organized</div>
                        <div class="field-value">${bean.totprabhatpheri}</div>
                    </div>
                    <div class="field-container">
                        <div class="field-title">Total Number of People Participated in Prabhat Pheri</div>
                        <div class="field-value">${bean.prabhatpheri_participants}</div>
                    </div>
                </c:forEach>
            </div>
            </c:if>
        </div>
    </c:forEach>
</div>

	<div class="chart-container">
		<div class="chart-item">
			<div id="chart_div1"></div>
		</div>
		<div class="chart-item">
			<div id="chart_div2"></div>
		</div>

		<div class="piechart-container">
			<canvas id="participantsChart"></canvas>
		</div>
	</div>

<script type="text/javascript">
$('#loading').hide();
document.addEventListener('DOMContentLoaded', function () {
    var totplannedloc = ${list[0].totplannedloc};
    var completedyatraloc = ${list[0].completedyatraloc};
    var totplannedact = ${list[0].totplannedact};
    var totcompletedact = ${list[0].totcompletedact};

    Highcharts.chart('chart_div1', {
        chart: {
            type: 'solidgauge',
            height: '110%'
        },
        title: {
            text: 'Completed Yatra Location',
            style: {
                fontSize: '24px'
            }
        },
        pane: {
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },
        yAxis: {
            min: 0,
            max: totplannedloc,
            stops: [
                [0.1, '#55BF3B'], // green
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#DF5353'] // red
            ],
            lineWidth: 0,
            tickWidth: 0,
            minorTickInterval: null,
            tickAmount: 2,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },
        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        },
        series: [{
            name: 'Completed Yatra Location',
            data: [completedyatraloc],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:25px">{y}</span><br/>' +
                    '<span style="font-size:12px;opacity:0.4">Completed</span></div>'
            },
            tooltip: {
                valueSuffix: ''
            }
        }]
    });

    Highcharts.chart('chart_div2', {
        chart: {
            type: 'solidgauge',
            height: '110%'
        },
        title: {
            text: 'Completed Activities',
            style: {
                fontSize: '24px'
            }
        },
        pane: {
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },
        yAxis: {
            min: 0,
            max: totplannedact,
            stops: [
                [0.1, '#55BF3B'], // green
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#DF5353'] // red
            ],
            lineWidth: 0,
            tickWidth: 0,
            minorTickInterval: null,
            tickAmount: 2,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },
        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        },
        series: [{
            name: 'Completed Activities',
            data: [totcompletedact],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:25px">{y}</span><br/>' +
                    '<span style="font-size:12px;opacity:0.4">Completed</span></div>'
            },
            tooltip: {
                valueSuffix: ''
            }
        }]
    });
});
</script>

    <script>
        // Data from the backend
        var data = {
            labels: [
                "Male Participants",
                "Female Participants",
                "Central Ministers",
                "State Ministers",
                "Parliament Members",
                "Legislative Assembly Members",
                "Legislative Council Members",
                "Other Public Representatives",
                "Government Officials"
            ],
            datasets: [{
                label: 'Participants Data',
                data: [
                    <c:out value="${pList[0].maleparticipants}" />,
                    <c:out value="${pList[0].femaleparticipants}" />,
                    <c:out value="${pList[0].centralminister}" />,
                    <c:out value="${pList[0].stateminister}" />,
                    <c:out value="${pList[0].parliamentmembers}" />,
                    <c:out value="${pList[0].legislativeassemblymembers}" />,
                    <c:out value="${pList[0].legislativecouncilmembers}" />,
                    <c:out value="${pList[0].otherpublicrepresentatives}" />,
                    <c:out value="${pList[0].govofficials}" />
                ],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                    'rgba(199, 199, 199, 0.2)',
                    'rgba(83, 102, 255, 0.2)',
                    'rgba(255, 120, 170, 0.4)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(199, 199, 199, 1)',
                    'rgba(83, 102, 255, 1)',
                    'rgba(255, 120, 170, 1)'
                ],
                borderWidth: 1
            }]
        };

        // Configure and render the chart
        var ctx = document.getElementById('participantsChart').getContext('2d');
        var participantsChart = new Chart(ctx, {
            type: 'pie',
            data: data,
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Data For All Participants',
                        font: {
                            size: 30 // Adjust this value to increase or decrease the font size
                        }
                    }
                }
            }
        });
        
        // Append the total below the chart
        var container = document.querySelector('.piechart-container');
        if (container) {
            var totalElement = document.createElement('div');
            totalElement.style.textAlign = 'center';
            totalElement.style.marginTop = '20px';
            totalElement.style.fontSize = '20px';
            totalElement.innerHTML = `<strong>Total Participants: 
            	<c:out value ="${pList[0].maleparticipants + pList[0].femaleparticipants + pList[0].centralminister + pList[0].stateminister + pList[0].parliamentmembers 
            		+ pList[0].legislativeassemblymembers + pList[0].legislativecouncilmembers + pList[0].otherpublicrepresentatives + pList[0].govofficials}"/></strong>`;
            container.appendChild(totalElement);
        } else {
            console.error("Container element '.piechart-container' not found.");
        }
    </script>

	<footer class="text-center">
    <%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>