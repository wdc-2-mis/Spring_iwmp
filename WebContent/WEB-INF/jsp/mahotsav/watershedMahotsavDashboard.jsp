<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/dashboardheader.jspf"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> -->
    <title>Watershed Mahotsav Dashboard</title>
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
	height:370px;
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
.field-containercircle {
    display: flex;
    flex-direction: column;
    justify-content: center;   /* centers content vertically */
    align-items: center;       /* centers content horizontally */
    background-color: #EC6C5A;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 50%;        /* makes it a circle */
    width: 250px;              /* equal width */
    height: 250px;             /* equal height */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}  
.field-containercircle1 {
    display: flex;
    flex-direction: column;
    justify-content: center;   /* centers content vertically */
    align-items: center;       /* centers content horizontally */
    background-color: #f8be60;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 50%;        /* makes it a circle */
    width: 250px;              /* equal width */
    height: 250px;             /* equal height */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}  
.field-containercircle2 {
    display: flex;
    flex-direction: column;
    justify-content: center;   /* centers content vertically */
    align-items: center;       /* centers content horizontally */
    background-color: #FFE9E5;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 50%;        /* makes it a circle */
    width: 250px;              /* equal width */
    height: 250px;             /* equal height */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
.field-containercircle3 {
    display: flex;
    flex-direction: column;
    justify-content: center;   /* centers content vertically */
    align-items: center;       /* centers content horizontally */
    background-color: #4CC082;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 50%;        /* makes it a circle */
    width: 250px;              /* equal width */
    height: 250px;             /* equal height */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}  

.field-containercircle4 {
    display: flex;
    flex-direction: column;
    justify-content: center;   /* centers content vertically */
    align-items: center;       /* centers content horizontally */
    background-color: #FFEED3;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 50%;        /* makes it a circle */
    width: 250px;              /* equal width */
    height: 250px;             /* equal height */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}    
  .field-containercircle5 {
    display: flex;
    flex-direction: column;
    justify-content: center;   /* centers content vertically */
    align-items: center;       /* centers content horizontally */
    background-color: #FF6384;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 50%;        /* makes it a circle */
    width: 250px;              /* equal width */
    height: 250px;             /* equal height */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}  
.component-field {
	margin-left: 10px;
	color: red;
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
    display: grid;
    justify-content: center;
   /*  align-items: center; */
    flex-wrap: nowrap;
    width: 1870px;
    hieght:600px;
  }
  .chart_items{
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
  	display: flex;
    justify-content: space-around;
    align-items: center;
    flex-wrap: nowrap;
    width: 1800px;
  }
  
  .pie-items{
  	border: 1px solid #ccc;
  	background-color: #FAF9F6; 
  	padding: 10px; 
  	width: 350px; 
  	height: 400px; 
  	margin: 8px; 
  	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); 
  	border-radius: 8px;
  }
  
   /* Main Frame Container */

.dashboard-frame {
    background-color: #FAF9F6;
    border: 1px solid #ccc;
    border-radius: 15px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    padding: 20px;
    margin: 20px;
    width: 1870px;
    height:600px;
}

/* Frame Title */
.frame-title {
    text-align: center;
    font-size: 1.5rem;
    color: #2C3E50;
    margin-bottom: 20px;
    font-weight: bold;
}

/* Dropdown and Chart Unified Layout */
.frame-content {
    display: flex;
  /*   flex-direction: column; */
    align-items: center;
    gap: 20px; /* Space between dropdown and chart */
    height:600px;
    width:1800px;
}

/* Dropdown Section Styling */
.dropdown-container {
    text-align: center;
}

.dropdown-label {
    font-size: 1rem;
    color: #34495E;
    font-weight: bold;
    margin-right: 10px;
}

.state-dropdown {
    padding: 10px;
    font-size: 1rem;
    border-radius: 5px;
    border: 1px solid #ccc;
    text-align: center;
    cursor: pointer;
    outline: none;
    transition: all 0.3s ease;
}

.state-dropdown:hover {
    border-color: #1ABC9C;
    background-color: #F4F8F9;
}

.chart-wrapper {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px; /* Space between the two charts */
    width: 100%;
    background-color: #F4F8F9;
    height:100%;
}
 /* Chart Styling */
.chart-container1 {
    flex: 1 1 45%; /* Adjust width, 45% ensures two charts in one row */
    min-width: 800px; /* Ensures charts don't get too small */
    max-width: 100% /* Prevents excessive stretching */
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
    align-items: normal;
    justify-content: center;
    position: relative;
    width:1000px;
    height:600px;
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
    .chart-container1 {
        flex: 1 1 100%; /* Each chart takes full width on smaller screens */
    }
}

}
 
 .circle-container {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    overflow: hidden;
    border: 4px solid #0d6efd;
}

.circle-container img {
    width: 100%;
    height: 100%;
    object-fit: cover;
} 

.rounded-circle {
  border-radius: 50% !important;
  margin: 50px;
}
  
</style>
</head>
<body>

<h2 class="panel-title" style ="text-align: center; margin: 10px; color: blue;">Watershed Mahotsav Dashboard</h2>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
 $(document).ready(function(){
	 $('#loading').hide();
 });
 </script>
 <!-- <div class="panel-body" style ="text-align: center; margin: 20px;"></div> -->
 <p style ="text-align: right; margin: 20px; font-weight: bold;">As on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<div>

		<c:forEach var="entry" items="${map}">
        	<c:if test ="${entry.key eq 'all'}">
        	<div class="component-container">
            <!-- <h3 class ="component-field">Watershed Mahotsav Program</h3> -->
          <!--   <div class="panel-underline"></div> -->
             <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
               
                 <c:forEach var="entry" items="${lists}">
                 
                 <div class="rounded-circle overflow-hidden" style="width:200px;height:200px; ">
    					<img src="<c:url value="/resources/images/mahotsav/register.png" />" class="w-100 h-100" style="object-fit:cover;">
    				<p align="center"> <b>  People Participating in Social Media : ${entry.total_registered_user} </b> </p> 
				</div>
                 </c:forEach> 
                 
                 <div class="rounded-circle overflow-hidden" style="width:200px;height:200px;">
    					<img src="<c:url value="/resources/images/mahotsav/inauguration.png" />" class="w-100 h-100" style="object-fit:cover;">
    			<p align="center"><b> No. of States Inauguration Program : <a href="javascript:void(0);" onclick="showWyPopup()"> ${bean.ignaugstate}</a> </b></p> 
				</div>
				
				 <div class="rounded-circle overflow-hidden" style="width:200px;height:200px;">
    					<img src="<c:url value="/resources/images/mahotsav/janbhagidaricup.png" />" class="w-100 h-100" style="object-fit:cover;">
    				<p align="center"><b> No. of Watershed Janbhagidari Cup : ${bean.awarddistribution}</b></p>
				</div>
				
				 <div class="rounded-circle overflow-hidden" style="width:200px;height:200px;">
    					<img src="<c:url value="/resources/images/mahotsav/prapheri.png" />" class="w-100 h-100" style="object-fit:cover;">
    				<p align="center"><b> No. of Prabhat Pheri Organized : ${bean.prapheriorg}</b></p>
				</div>
				
				 <div class="rounded-circle overflow-hidden" style="width:200px;height:200px;">
    					<img src="<c:url value="/resources/images/mahotsav/projectlvl.png" />" class="w-100 h-100" style="object-fit:cover;">
    				<p align="center"> <b> No. of States Project Level Program : <a href="javascript:void(0);" onclick="showPopup()"> ${bean.totprojstates}</a> </b>
    					
    				</p>
				</div>
                 
               
                    
                </c:forEach>
            </div>

             </div>
            </c:if>
       
    </c:forEach>  


<%-- 

		<c:forEach var="entry" items="${map}">
        	<c:if test ="${entry.key eq 'all'}">
        	<div class="component-container">
            <h3 class ="component-field">Watershed Mahotsav Program</h3>
          <!--   <div class="panel-underline"></div> -->
            <div class="field-row">
                <c:forEach var="bean" items="${entry.value}">
                
                 <c:forEach var="entry" items="${lists}">
					<div class="field-containercircle">
                        <div class="field-title">No. of People Participating (Register) </div>
                        <div class="field-value">${entry.total_registered_user}
                        </div>
                    </div>
                 </c:forEach> 
                 <div class="field-containercircle1">
                        <div class="field-title">No. of States Inauguration Program</div>
                        <div class="field-value"><a href="stateWMInaugurationReport" target="_blank" >${bean.ignaugstate}</a>  ${bean.ignaugstate} </div>
                    </div>
                    <div class="field-containercircle3">
                        <div class="field-title">No. of Watershed Janbhagidari Cup</div>
                        <div class="field-value">${bean.awarddistribution}</div>
                    </div>
                     <div class="field-containercircle4">
                        <div class="field-title"> No. of Prabhat Pheri Organized</div>
                        <div class="field-value">${bean.prapheriorg}</div>
                    </div>
                    <div class ="pie-items">
						<div class ="pie-div5pp">
							<canvas id="wtrPieChartpp"></canvas>
						</div>
					</div>
                    <div class="field-containercircle2">
                        <div class="field-title">No. of People Participated in Prabhat Pheri </div>
                        <div class="field-value">${bean.prapheriparticipant}</div>
                    </div>
                    <div class="field-containercircle5">
                        <div class="field-title">No. of States Project Level Program </div>
                        <div class="field-value">
                        <a href="projectWMProgramReport" target="_blank" >${bean.totprojstates}</a>   ${bean.totprojstates}
                        </div>
                    </div>
                 
                    
                </c:forEach>
            </div>
             </div>
            </c:if>
       
    </c:forEach>  
 --%>


	<div>
	
	<!-- 	<h3 class ="component-field">Social Media Competition</h3>
			<div class="field-row">
				
             </div> -->
	      
                    <div class="piechart-container">
                   
						<div class ="pie-items">
							<div class ="pie-div1">
								<canvas id="prticpntsChrt"></canvas>
							</div>
						</div>
						<div class ="pie-items">
							<div class ="pie-div2">
								<canvas id="awrdPieChart"></canvas>
							</div>
						</div>
						<div class ="pie-items">
							<div class ="pie-div3">
								<canvas id="plntPieChart"></canvas>
							</div>
						</div>
						<div class ="pie-items">
							<div class ="pie-div5pp">
								<canvas id="wtrPieChartpp"></canvas>
							</div>
						</div>
						<%-- <div class ="pie-items">
							<div class ="pie-div4">
								<canvas id="ingPieChart"></canvas>
							</div>
						</div> --%>
					</div>
				
					
					
					
				<script>
	var video = ${lists[0].total_video_uploaded}
    var photo = ${lists[0].total_photo_uploaded}
    
    const partData = [video, photo];
	// Participants Gauge Chart
    new Chart(document.getElementById('prticpntsChrt'), {
      type: 'pie',
      data: {
        labels: ['Videos', 'Photos'],
        datasets: [{
        data: partData,
          backgroundColor: ['#2196f3', '#ffc107'],
        }]
      },
      options: {
      	responsive: true,
          plugins: {
            title: {
                  display: true,
                  text: 'Social Media',
                  font: {
                      size: 30 // Adjust this value to increase or decrease the font size
                  }
              }
          },
        }
    });
    
    var container = document.querySelector('.pie-div1');
    if (container) {
        var totalElement = document.createElement('div');
        totalElement.style.textAlign = 'center';
        totalElement.style.marginTop = '20px';
        totalElement.style.fontSize = '16px';
        totalElement.innerHTML = `<strong>Total No. of Media Uploaded: 
        	<c:out value ="${lists[0].total_video_uploaded + lists[0].total_photo_uploaded}"/></strong>`;
        container.appendChild(totalElement);
    } else {
        console.error("Container element '.piechart-container' not found.");
    }
        
        var labels = ["Facebook", "YouTube", "Instagram", "Twitter", "Linkedin"];
        // Create Pie Chart for "wtr"
        new Chart(document.getElementById('awrdPieChart'), {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Social Media Reels Uploaded',
                    data: [
                    		<c:out value="${lists[0].fbv}" />,
                            <c:out value="${lists[0].ytv}" />,
                            <c:out value="${lists[0].igv}" />,
                            <c:out value="${lists[0].xv}" />,
                            <c:out value="${lists[0].liv}" />
                        ],
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Social Media Reels',
                        font: {
                            size: 30 // Adjust this value to increase or decrease the font size
                        }
                    }
                }
            }
        });
        
        var container = document.querySelector('.pie-div2');
        if (container) {
            var totalElement = document.createElement('div');
            totalElement.style.textAlign = 'center';
            totalElement.style.marginTop = '20px';
            totalElement.style.fontSize = '16px';
            totalElement.innerHTML = `<strong>Total Social Media Reels: 
            	<c:out value ="${lists[0].fbv + lists[0].ytv + lists[0].igv + lists[0].xv + lists[0].liv}"/></strong>`;
            container.appendChild(totalElement);
        } else {
            console.error("Container element '.piechart-container' not found.");
        }
        
        
        var labels = ["Facebook", "YouTube", "Instagram", "Twitter", "Linkedin"];
        // Create Pie Chart for "wtr"
        new Chart(document.getElementById('plntPieChart'), {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Social Media Photo',
                    data: [
                    		<c:out value="${lists[0].fbp}" />,
                            <c:out value="${lists[0].ytp}" />,
                            <c:out value="${lists[0].igp}" />,
                            <c:out value="${lists[0].xp}" />,
                            <c:out value="${lists[0].lip}" />
                        ],
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Social Media Photo',
                        font: {
                            size: 30 // Adjust this value to increase or decrease the font size
                        }
                    }
                }
            }
        });
        
        var container = document.querySelector('.pie-div3');
        if (container) {
            var totalElement = document.createElement('div');
            totalElement.style.textAlign = 'center';
            totalElement.style.marginTop = '20px';
            totalElement.style.fontSize = '16px';
            totalElement.innerHTML = `<strong>Total Social Media Photo : 
            	<c:out value ="${lists[0].fbp + lists[0].ytp + lists[0].igp + lists[0].xp + lists[0].lip}"/></strong>`;
            container.appendChild(totalElement);
        } else {
            console.error("Container element '.piechart-container' not found.");
        }
        
       
    </script>
    
	</div>
	
   
    
   
    
</div>
<!-- Popup Modal -->
<div id="popwyup" style="display:none; position:fixed; top:0%; left:18%; width:70%; background:#fff; border:1px solid #ccc; padding:20px; z-index:1000;overflow:auto; height:100vh;">
    <div style="text-align:right;">
<!--         <button onclick="closeWyPopup()">Close</button> -->
         <span onclick="closeWyPopup()" style="cursor:pointer; font-size:16px; font-weight:bold;">&#10006;</span>
    </div>
    <h3 style="text-align: center;">State Wise Watershed Mahotsav Inauguration Program Data</h3>
    <table border="1" style="width:100%; border-collapse:collapse;">
        <thead>
            <tr>
                <th>S.No.</th>
                <th>State Name</th>
                <th>Total No. of People Participated</th>
                <th>Total No. of Works for Bhoomi Poojan</th>
                <th>Total No. of Works for Lokarpan</th>
                <th>Total No. of Shramdaan Location</th>
                <th>Total No. of Shramdaan Participants</th>
                <th>Total No. of Sapling Planted</th>
                <th>Total No. of Awarded for Janbhagidari Cup 2025</th>
            </tr>
        </thead>
        <tbody>
        	<c:set var ="totalparticipants" value ="0"/>
        	<c:set var ="bhoomipoojan" value ="0"/>
        	<c:set var ="tlokarpan" value ="0"/>
        	<c:set var ="tshramdaan" value ="0"/>
        	<c:set var ="tshramdaanpart" value ="0"/>
        	<c:set var ="awarddistribution" value ="0"/>
        	<c:set var ="tplantation" value ="0"/>
            <c:forEach var="data1" items="${bean1}" varStatus="count">
                <tr>
                    <td><c:out value='${count.count}' />
                    <%-- <td><a href="javascript:void(0);" onclick="showDWyPopup(${data1.stcode})">${data1.st_name}</a></td> --%>
                    <td>${data1.st_name}</td>
                    <td class="text-right">${data1.total_participants}</td>
                    <td class="text-right">${data1.bhoomi_poojan}</td>
                    <td class="text-right">${data1.lokarpan}</td>
                    <td class="text-right">${data1.shramdaan}</td>
                    <td class="text-right">${data1.shramdaan_participate}</td>
                    <td class="text-right">${data1.plantation}</td>
                    <td class="text-right">${data1.award_janbhagidari}</td>
                    
                </tr>
            <c:set var ="totalparticipants" value ="${totalparticipants + data1.total_participants}"/>
        	<c:set var ="bhoomipoojan" value ="${bhoomipoojan + data1.bhoomi_poojan}"/>
        	<c:set var ="tlokarpan" value ="${tlokarpan + data1.lokarpan}"/>
        	<c:set var ="tshramdaan" value ="${tshramdaan + data1.shramdaan}"/>
        	<c:set var ="tshramdaanpart" value ="${tshramdaanpart + data1.shramdaan_participate}"/>
        	<c:set var ="tplantation" value ="${tplantation + data1.plantation}"/>
        	<c:set var ="awarddistribution" value ="${awarddistribution + data1.award_janbhagidari}"/>
        	
            </c:forEach>
            <tr>
            	<th colspan ="2" style="text-align: center">Grand Total</th>
            	<th style="text-align: right;">${totalparticipants}</th>
            	<th style="text-align: right;">${bhoomipoojan}</th>
            	<th style="text-align: right;">${tlokarpan}</th>
            	<th style="text-align: right;">${tshramdaan}</th>
            	<th style="text-align: right;">${tshramdaanpart}</th>
            	<th style="text-align: right;">${tplantation}</th>
            	<th style="text-align: right;">${awarddistribution}</th>
            </tr>
        </tbody>
    </table>
</div>



<!-- PopDup Modal -->
<div id="popDwyup" style="display:none; position:fixed; top:0%; left:18%; width:70%; background:#fff; border:1px solid #ccc; padding:20px; z-index:1000;overflow:auto; height:100vh;">
    <div style="text-align:right;">
<!--         <button onclick="closeDWyPopup()">Close</button> -->
         <span onclick="closeDWyPopup()" style="cursor:pointer; font-size:16px; font-weight:bold;">&#10006;</span>
    </div>
    <h3 style="text-align: center;">District Wise Watershed Yatra Activities Data</h3>
    <table class ="district-table1" border="1" style="width:100%; border-collapse:collapse;">
        
    </table>
</div>

<script>
    function showWyPopup() {
        document.getElementById('popwyup').style.display = 'block';
    }

    function closeWyPopup() {
        document.getElementById('popwyup').style.display = 'none';
    }
    function showDWyPopup(stcode) {

        document.getElementById('popDwyup').style.display = 'block';
        // Example AJAX call using jQuery (Ensure jQuery is included)
        $.ajax({
            url: 'getDistrictWatershedYatraDashboardReport', // Controller endpoint
            type: 'post',
            data: {stcode:stcode},
            error: function(xhr, status, error) {
                console.error(error);
            },
            success: function(response) {
                // Handle response (e.g., display data in a modal or refresh part of the page)
                console.log(response);
                let table = $('.district-table1');
                let html = '<thead><tr><th>S.No.</th><th>District Name</th><th>Total Number of People Participated</th><th>Total Numbers of Works for BHOOMI POOJAN</th><th>Total Numbers of Works for LOKARPAN</th><th>Shramdaan on Total Location</th><th>Total Award Distribution</th><th>Total Number of Yatra Location</th><th>Total Number of AR Experienced People</th><th>Total Number of People Participated in Quiz</th><th>Total Number of Sapling Planted</th></tr></thead><tbody>';
                
                let totalParticipants = 0, totalBhoomiPoojan = 0, totalLokarpan = 0, totalShramdaan = 0,
                totalAwardDistribution = 0, totalLocations = 0, totalARExp = 0, totalQuizParticipants = 0, totalPlantation = 0;
                
                response.forEach((data1, index) => {

                	totalParticipants += data1.total_participants || 0;
                    totalBhoomiPoojan += data1.bhoomi_poojan || 0;
                    totalLokarpan += data1.lokarpan || 0;
                    totalShramdaan += data1.shramdaan || 0;
                    totalAwardDistribution += data1.award_distribution || 0;
                    totalLocations += data1.total_locations || 0;
                    totalARExp += data1.ar_exp || 0;
                    totalQuizParticipants += data1.quiz_participants || 0;
                    totalPlantation += data1.plantation || 0;
                	
                    html += `<tr>
                                <td>`+(index + 1)+`</td> <!-- Generates serial number dynamically -->
                                <td>`+data1.dist_name+`</td>
                                <td align="right">`+data1.total_participants+`</td>
                                <td align="right">`+data1.bhoomi_poojan+`</td>
                                <td align="right">`+data1.lokarpan+`</td>
                                <td align="right">`+data1.shramdaan+`</td>
                                <td align="right">`+data1.award_distribution+`</td>
                                <td align="right">`+data1.total_locations+`</td>
                                <td align="right">`+data1.ar_exp+`</td>
                                <td align="right">`+data1.quiz_participants+`</td>
                                <td align="right">`+data1.plantation+`</td>
                            </tr>`;
                });
                
             // Add the grand total row
                html += `<tr>
                            <th colspan="2" style="text-align: center"><strong>Total</strong></th>
                            <th style="text-align: right;"><strong>` + totalParticipants + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalBhoomiPoojan + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalLokarpan + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalShramdaan + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalAwardDistribution + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalLocations + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalARExp + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalQuizParticipants + `</strong></th>
                            <th style="text-align: right;"><strong>` + totalPlantation + `</strong></th>
                        </tr>`;
                
                html += '</tbody>';
                table.html(html);
            }
        });
    }
   
    function closeDWyPopup() {
        document.getElementById('popDwyup').style.display = 'none';
    }
</script>
<!-- Popup Modal -->
<div id="popup" style="display:none; position:fixed; top:0%; left:18%; width:70%; background:#fff; border:1px solid #ccc; padding:20px; z-index:1000; height:100vh; overflow:auto;">
    <div style="text-align:right;">
        <span onclick="closePopup()" style="cursor:pointer; font-size:16px; font-weight:bold;">&#10006;</span>
    </div>
    <h3 style="text-align: center;">State Wise Watershed Mahotsav Project Level Program Data</h3>
    <table border="1" style="width:100%; border-collapse:collapse;">
        <thead>
            <tr>
                <th>S.No.</th>
                <th>State Name</th>
                <th>Total No. of Location Cover</th>
               	<th>Total No. of People Participated</th>
                <th>Total No. of Works for Bhoomi Poojan</th>
                <th>Total No. of Works for Lokarpan</th>
                <th>Total No. of Shramdaan Location</th>
                <th>Total No. of Shramdaan Participants</th>
                <th>Total No. of Sapling Planted</th>
            </tr>
        </thead>
        <tbody>
        
        	<c:set var ="totalparticipants" value ="0"/>
        	<c:set var ="bhoomipoojan" value ="0"/>
        	<c:set var ="tlokarpan" value ="0"/>
        	<c:set var ="tshramdaan" value ="0"/>
        	<c:set var ="tshramdaanpart" value ="0"/>
        	<c:set var ="nolocation" value ="0"/>
        	<c:set var ="tplantation" value ="0"/>

            <c:forEach var="data" items="${bean}" varStatus="count">
                <tr>
                    <td><c:out value='${count.count}' />
                    <%-- <td><a href="javascript:void(0);" onclick="showDPopup(${data.stcode})">${data.stname}</a></td> --%>
                    <td>${data.st_name}</td>
                    <td class="text-right">${data.total_locations}</td>
                    <td class="text-right">${data.total_participants}</td>
                    <td class="text-right">${data.bhoomi_poojan}</td>
                    <td class="text-right">${data.lokarpan}</td>
                    <td class="text-right">${data.shramdaan}</td>
                    <td class="text-right">${data.shramdaan_participate}</td>
                    <td class="text-right">${data.plantation}</td>
                    

                </tr>
                <c:set var ="totalparticipants" value ="${totalparticipants + data.total_participants}"/>
	        	<c:set var ="bhoomipoojan" value ="${bhoomipoojan + data.bhoomi_poojan}"/>
	        	<c:set var ="tlokarpan" value ="${tlokarpan + data.lokarpan}"/>
	        	<c:set var ="tshramdaan" value ="${tshramdaan + data.shramdaan}"/>
	        	<c:set var ="tshramdaanpart" value ="${tshramdaanpart + data.shramdaan_participate}"/>
	        	<c:set var ="tplantation" value ="${tplantation + data.plantation}"/>
	        	<c:set var ="nolocation" value ="${nolocation + data.total_locations}"/>

            </c:forEach>
            <tr>
            	<th colspan ="2" style="text-align: center">Grand Total</th>
            	<th style="text-align: right;">${nolocation}</th>
            	<th style="text-align: right;">${totalparticipants}</th>
            	<th style="text-align: right;">${bhoomipoojan}</th>
            	<th style="text-align: right;">${tlokarpan}</th>
            	<th style="text-align: right;">${tshramdaan}</th>
            	<th style="text-align: right;">${tshramdaanpart}</th>
            	<th style="text-align: right;">${tplantation}</th>
            	
            </tr>
        </tbody>
    </table>
</div>


<!-- PopDup Modal -->
<div id="popDup" style="display:none; position:fixed; top:0%; left:18%; width:70%; background:#fff; border:1px solid #ccc; padding:20px; z-index:1000; height:100vh; overflow:auto;" >
		<div style="text-align: right;">
			<span onclick="closeDPopup()" style="cursor: pointer; font-size: 16px; font-weight: bold;">&#10006;</span>
		</div>
		<h3 style="text-align: center;">District Wise Pre Yatra Details Data</h3>
    <table class ="district-table" border="1" style="width:100%; border-collapse:collapse;">
        
    </table>
</div>

<script>
function showPopup() {
    document.getElementById('popup').style.display = 'block';
}

function closePopup() {
    document.getElementById('popup').style.display = 'none';
}
function showDPopup(stcode) {
    document.getElementById('popDup').style.display = 'block';
    // Example AJAX call using jQuery (Ensure jQuery is included)
    $.ajax({
        url: "getDistWisePreYatraData", // Controller endpoint
        type: "post",
        data: {stcode:stcode},
        error: function(xhr, status, error) {
            console.error(error);
        },
        success: function(data) {
            // Handle response (e.g., display data in a modal or refresh part of the page)
            console.log(data);
            $table = $('.district-table');
            var i = 0;
            var totgram = 0;
            var gramPrticpnts = 0;
            var totPrbhtphri = 0;
            var prbhtPhriPrticpnts = 0;
            let html = '<thead><tr><th>S.No.</th><th>District Name</th><th>Total Gram Sabhas</th><th>Gram Sabha Participants</th><th>Total Prabhat Pheris</th><th>Prabhat Pheri Participants</th></tr></thead><tbody>';
            for ( var key in data) {
            	i = i+1;
                html += `<tr>
                            <td>`+i+`</td> 
                            <td>`+data[key].distname+`</td>
                            <td align="right">`+data[key].totgrabsabha+`</td>
                            <td align="right">`+data[key].gramsabha_participants+`</td>
                            <td align="right">`+data[key].totprabhatpheri+`</td>
                            <td align="right">`+data[key].prabhatpheri_participants+`</td>
                        </tr>`;
                totgram = data[key].totgrabsabha + totgram;
                gramPrticpnts = data[key].gramsabha_participants + gramPrticpnts;
                totPrbhtphri = data[key].totprabhatpheri + totPrbhtphri;
                prbhtPhriPrticpnts = data[key].prabhatpheri_participants + prbhtPhriPrticpnts;
                
            }
            html += `<th colspan = "2" style="text-align: center">Total</th> 
            <th style="text-align: right">`+totgram+`</th>
            <th style="text-align: right">`+gramPrticpnts+`</th>
            <th style="text-align: right">`+totPrbhtphri+`</th>
            <th style="text-align: right">`+prbhtPhriPrticpnts+`</th>
            </tbody>`;
            $table.html(html);
        }
        
    });
}

function closeDPopup() {
    document.getElementById('popDup').style.display = 'none';
}
</script>
	

	<%-- <div class="piechart-container">
		<div class ="pie-items">
			<div class ="pie-div1">
				<canvas id="prticpntsChrt"></canvas>
			</div>
		</div>
		<div class ="pie-items">
			<div class ="pie-div2">
				<canvas id="awrdPieChart"></canvas>
			</div>
		</div>
		<div class ="pie-items">
			<div class ="pie-div3">
				<canvas id="plntPieChart"></canvas>
			</div>
		</div>
		<div class ="pie-items">
			<div class ="pie-div4">
				<canvas id="ingPieChart"></canvas>
			</div>
		</div>
	</div> --%>



	 <div class="piechart-container">
		
		<div class ="pie-items">
			<div class ="pie-div5">
				<canvas id="wtrPieChart"></canvas>
			</div>
		</div>
		
		<div class ="pie-items">
			<div class ="pie-div6">
				<canvas id="shrmPieChart"></canvas>
			</div>
		</div>
		<div class="pie-items">
			<div class="chart-div1">
				<canvas id="locationChart"></canvas>
			</div>
		</div>
		<div class="pie-items">
			<div class="chart-div2">
				<canvas id="activityChart"></canvas>
			</div>
		</div>
	</div> 
	<script>
    
    // Labels for the pie chart
  
    var labels = ["Participants", "No. of Member / Ministers", "Public Representatives", "Government Officials"];
    // Create Pie Chart for "wtr"
    new Chart(document.getElementById('wtrPieChart'), {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Inauguration',
                data: [
                		<c:out value="${listpart[0].malei}" />,
                        <c:out value="${listpart[0].memberi}" />,
                        <c:out value="${listpart[0].otheri}" />,
                        <c:out value="${listpart[0].officialsi}" />
                    ],
                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Inauguration',
                    font: {
                        size: 30 // Adjust this value to increase or decrease the font size
                    }
                }
            }
        }
    });
    
    var container = document.querySelector('.pie-div5');
    if (container) {
        var totalElement = document.createElement('div');
        totalElement.style.textAlign = 'center';
        totalElement.style.marginTop = '20px';
        totalElement.style.fontSize = '16px';
        totalElement.innerHTML = `<strong>Participants : 
        	<c:out value ="${listpart[0].malei + listpart[0].memberi + listpart[0].otheri + listpart[0].officialsi}"/></strong>`;
        container.appendChild(totalElement);
    } else {
        console.error("Container element '.piechart-container' not found.");
    }
    
    var labels = ["Male", "Female"];
    // Create Pie Chart for "wtr"
    new Chart(document.getElementById('wtrPieChartpp'), {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Prabhat Pheri',
                data: [
                		<c:out value="${listpart[0].malepr}" />,
                        <c:out value="${listpart[0].femalepr}" />
                    ],
                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Prabhat Pheri',
                    font: {
                        size: 30 // Adjust this value to increase or decrease the font size
                    }
                }
            }
        }
    });
    
    var container = document.querySelector('.pie-div5pp');
    if (container) {
        var totalElement = document.createElement('div');
        totalElement.style.textAlign = 'center';
        totalElement.style.marginTop = '20px';
        totalElement.style.fontSize = '16px';
        totalElement.innerHTML = `<strong>Participants : 
        	<c:out value ="${listpart[0].malepr + listpart[0].femalepr}"/></strong>`;
        container.appendChild(totalElement);
    } else {
        console.error("Container element '.piechart-container' not found.");
    }
    
    
    var labels = ["Participants", "No. of Member / Ministers", "Public Representatives", "Government Officials"];
    // Create Pie Chart for "wtr"
    new Chart(document.getElementById('shrmPieChart'), {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Project Level',
                data: [
                		<c:out value="${listpart[0].malep}" />,
                        <c:out value="${listpart[0].memberp}" />,
                        <c:out value="${listpart[0].otherp}" />,
                        <c:out value="${listpart[0].officialsp}" />
                    ],
                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Project Level',
                    font: {
                        size: 30 // Adjust this value to increase or decrease the font size
                    }
                }
            }
        }
    });
    
    var container = document.querySelector('.pie-div6');
    if (container) {
        var totalElement = document.createElement('div');
        totalElement.style.textAlign = 'center';
        totalElement.style.marginTop = '20px';
        totalElement.style.fontSize = '16px';
        totalElement.innerHTML = `<strong>Participants : 
        	<c:out value ="${listpart[0].malep + listpart[0].memberp + listpart[0].otherp + listpart[0].officialsp}"/></strong>`;
        container.appendChild(totalElement);
    } else {
        console.error("Container element '.piechart-container' not found.");
    }
</script>

	<%-- <div class="chart-container">
 		<div class="chart_items"> 
 			<div class="chart-div3">
 				<canvas id="participantsChart"></canvas> 
			</div> 
 		</div> 
	</div> --%>

<script type="text/javascript">
var ibhumipujan = Number("${listpart[0].ibhumipujan}");
var ilokarpan = Number("${listpart[0].ilokarpan}");
var ishramdannlocation = Number("${listpart[0].ishramdannlocation}");
var ishramdanparticipate = Number("${listpart[0].ishramdanparticipate}");
var iplantation = Number("${listpart[0].iplantation}");
    
    var pbhumipujan = ${listpart[0].pbhumipujan};
    var plokarpan = ${listpart[0].plokarpan};
    var pshramdannlocation = ${listpart[0].pshramdannlocation};
    var pshramdanparticipate = ${listpart[0].pshramdanparticipate};
    var pplantation = ${listpart[0].pplantation};

        // Prepare datasets
        const locData = [ibhumipujan, ilokarpan, ishramdannlocation, ishramdanparticipate, iplantation];
        const actData = [pbhumipujan, plokarpan, pshramdannlocation, pshramdanparticipate, pplantation];

        // Location Gauge Chart
        new Chart(document.getElementById('locationChart'), {
          type: 'doughnut',
          data: {
            labels: ['Bhoomi Poojan Works', 'Lokarpan Works', 'Shramdaan Location', 'Shramdaan People Participated', 'Agro forestry / Horticultural Plantation No. of Sapling'],
            datasets: [{
              data: locData,
              backgroundColor: ['#4caf50', '#f44336', '#4BC0C0', '#9966FF', '#FF9F40'],
            }]
          },
          options: {
        	responsive: true,
            plugins: {
              title: {
                    display: true,
                    text: 'Inauguration Activities',
                    font: {
                        size: 30 // Adjust this value to increase or decrease the font size
                    }
                }
            },
            cutout: '60%',
          }
        });
        
        var totalInaugurationActivities =
            ibhumipujan +
            ilokarpan +
            ishramdannlocation +
            ishramdanparticipate +
            iplantation;

        var totalProjectActivities =
            pbhumipujan +
            plokarpan +
            pshramdannlocation +
            pshramdanparticipate +
            pplantation;
        
        var container = document.querySelector('.chart-div1');
        

        if (container) {
            var totalElement = document.createElement('div');
            totalElement.style.textAlign = 'center';
            totalElement.style.marginTop = '20px';
            totalElement.style.fontSize = '16px';
            totalElement.innerHTML = `<strong>Total Activities : \${totalInaugurationActivities}</strong>`;
            container.appendChild(totalElement);
        } else {
            console.error("Container element '.chart-div1' not found.");
        }


        // Activity Gauge Chart
        new Chart(document.getElementById('activityChart'), {
          type: 'doughnut',
          data: {
            labels: ['Bhoomi Poojan Works', 'Lokarpan Works', 'Shramdaan Location', 'Shramdaan People Participated', 'Agro forestry / Horticultural Plantation No. of Sapling'],
            datasets: [{
              data: actData,
              backgroundColor: ['#2196f3', '#ffc107', '#4caf50', '#f44336', '#4BC0C0'],
            }]
          },
          options: {
          	responsive: true,
              plugins: {
                title: {
                      display: true,
                      text: 'Project Level Activities',
                      font: {
                          size: 30 // Adjust this value to increase or decrease the font size
                      }
                  }
              },
              cutout: '60%',
            }
        });
        
        var container = document.querySelector('.chart-div2');
        if (container) {
            var totalElement = document.createElement('div');
            totalElement.style.textAlign = 'center';
            totalElement.style.marginTop = '20px';
            totalElement.style.fontSize = '16px';
            totalElement.innerHTML = `<strong>Total Activities : \${totalProjectActivities}</strong>`;
            container.appendChild(totalElement);
        } else {
            console.error("Container element '.piechart-container' not found.");
        }
        
     
        
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
            type: 'doughnut',
            data: data,
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Participants Details',
                        font: {
                            size: 30 // Adjust this value to increase or decrease the font size
                        }
                    }
                },
                cutout: '60%',
            }
        });
        
        // Append the total below the chart
        var container = document.querySelector('.chart-div3');
        if (container) {
            var totalElement = document.createElement('div');
            totalElement.style.textAlign = 'center';
            totalElement.style.marginTop = '20px';
            totalElement.style.fontSize = '16px';
            totalElement.innerHTML = `<strong>Total Participants: 
            	<c:out value ="${pList[0].maleparticipants + pList[0].femaleparticipants + pList[0].centralminister + pList[0].stateminister + pList[0].parliamentmembers 
            		+ pList[0].legislativeassemblymembers + pList[0].legislativecouncilmembers + pList[0].otherpublicrepresentatives + pList[0].govofficials}"/></strong>`;
            container.appendChild(totalElement);
        } else {
            console.error("Container element '.piechart-container' not found.");
        }
      

</script>
<%-- 
<div class="dashboard-frame">
    <h3 class="frame-title">Day Wise Watershed Yatra Total Participants & Covered Location Overview</h3>
    
    <div class="frame-content">
        <!-- Dropdown Section -->
        <div class="dropdown-container">
            <label for="stateDropdown" class="dropdown-label">Select State:</label>
            <select name="stCode" id="stateDropdown" class="state-dropdown">
                <c:forEach var="state" items="${stateList}">
                    <option value="${state.key}" ${state.key == param.stCode ? 'selected' : ''}>${state.value}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Chart Section -->
        <div class="chart-wrapper">
        <div class="chart-container1">
    <h4 class="frame-title">Total Participants</h4>
    <div class="chart-navigation">
        <img id="prevIconParticipants" src="<c:url value='/resources/images/back-button.png'/>" alt="Previous" class="nav-icon left-icon" />
        <canvas id="participantChart"></canvas>
        <img id="nextIconParticipants" src="<c:url value='/resources/images/forward-button.png'/>" alt="Next" class="nav-icon right-icon" />
    </div>
</div>

<div class="chart-container1">
    <h4 class="frame-title">Covered Locations</h4>
    <div class="chart-navigation">
        <img id="prevIconLocations" src="<c:url value='/resources/images/back-button.png'/>" alt="Previous" class="nav-icon left-icon" />
        <canvas id="locationChart1"></canvas>
        <img id="nextIconLocations" src="<c:url value='/resources/images/forward-button.png'/>" alt="Next" class="nav-icon right-icon" />
    </div>
</div>


    </div>
    </div>
</div> --%>

<!-- <script>
$(document).ready(function () {
    let chartLabels = [];
    let chartParticipants = [];
    let chartSumTotalParticipants = [];
    let chartCoveredLocations = [];

    <c:forEach var="entry" items="${chartData}">
        chartLabels.push('<c:out value="${entry.key}"/>');
        chartParticipants.push(Number('<c:out value="${entry.value}"/>'));
    </c:forEach>;

    <c:forEach var="entry2" items="${sumTotalChartData}">
    chartSumTotalParticipants.push(Number('<c:out value="${entry2.value}"/>'));
    </c:forEach>;

    <c:forEach var="entry1" items="${chartLocData}">
        chartCoveredLocations.push(Number('<c:out value="${entry1.value}"/>'));
    </c:forEach>;

    let startIndexParticipants = 0;
    let startIndexLocations = 0;
    const range = 10;

    function updateChartRange(chart, labels, data1, data2, startIndex) {
        chart.data.labels = labels.slice(startIndex, startIndex + range);
        chart.data.datasets[0].data = data1.slice(startIndex, startIndex + range);
        chart.data.datasets[1].data = data2.slice(startIndex, startIndex + range);
        chart.update();
    }
    
    function updateChartRangeLoc(chart, labels, data, startIndex) {
        chart.data.labels = labels.slice(startIndex, startIndex + range);
        chart.data.datasets[0].data = data.slice(startIndex, startIndex + range);
        chart.update();
    }

    // Initialize Participant Chart
     const ctx = document.getElementById('participantChart').getContext('2d');
    const participantChart = new Chart(ctx, {
        type: 'bar', // Base chart type
        data: {
            labels: chartLabels.slice(0, range),
            datasets: [
                {
                    type: 'bar',
                    label: 'Total Participants (Daily)',
                    data: chartParticipants.slice(0, range),
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgb(54, 162, 235)',
                    borderWidth: 2
                },
                {
                    type: 'line',
                    label: 'Cumulative Participants (Sum Total)',
                    data: chartSumTotalParticipants.slice(0, range),
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderWidth: 3, // Thicker line for visibility
                    pointRadius: 4, // Bigger points for clarity
                    pointBackgroundColor: 'red',
                    fill: false
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                x: { title: { display: true, text: 'Date' } },
                y: { title: { display: true, text: 'Participants' }, beginAtZero: true }
            }
        }
    });


    // Initialize Locations Chart
    const ctx2 = document.getElementById('locationChart1').getContext('2d');
    const locationChart = new Chart(ctx2, {
        type: 'line',
        data: {
            labels: chartLabels.slice(0, range),
            datasets: [{
                label: 'Covered Locations',
                data: chartCoveredLocations.slice(0, range),
                borderColor: 'rgba(255, 99, 132, 1)',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderWidth: 2,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { title: { display: true, text: 'Date' } },
                y: { title: { display: true, text: 'Covered Locations' }, beginAtZero: true }
            }
        }
    });

    // Participants Navigation
    $('#nextIconParticipants').click(() => {
        if (startIndexParticipants + range < chartLabels.length) {
            startIndexParticipants++;
            updateChartRange(participantChart, chartLabels, chartParticipants, chartSumTotalParticipants, startIndexParticipants);
        }
    });

    $('#prevIconParticipants').click(() => {
        if (startIndexParticipants > 0) {
            startIndexParticipants--;
            updateChartRange(participantChart, chartLabels, chartParticipants, chartSumTotalParticipants, startIndexParticipants);
        }
    });

    // Locations Navigation
    $('#nextIconLocations').click(() => {
        if (startIndexLocations + range < chartLabels.length) {
            startIndexLocations++;
            updateChartRangeLoc(locationChart, chartLabels, chartCoveredLocations, startIndexLocations);
        }
    });

    $('#prevIconLocations').click(() => {
        if (startIndexLocations > 0) {
            startIndexLocations--;
            updateChartRangeLoc(locationChart, chartLabels, chartCoveredLocations, startIndexLocations);
        }
    });

    // AJAX call when the user selects a new state
    $('#stateDropdown').change(function () {
        let selectedState = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'getParticipantData',
            data: { stateCode: selectedState },
            dataType: 'json',
            success: function (response) {
                if (!response.chartData || Object.keys(response.chartData).length === 0) {
                    alert("No data available for the selected state.");
                    $('#stateDropdown').val(lastValidState);
                    return;
                }

                chartLabels = Object.keys(response.chartData);
                chartParticipants = Object.values(response.chartData).map(Number);
                chartSumTotalParticipants = Object.values(response.sumTotalChartData).map(Number);
                chartCoveredLocations = Object.values(response.chartLocData || {}).map(Number);
                startIndexParticipants = 0;
                startIndexLocations = 0;
                lastValidState = selectedState;
                updateChartRange(participantChart, chartLabels, chartParticipants, chartSumTotalParticipants, startIndexParticipants);
                updateChartRangeLoc(locationChart, chartLabels, chartCoveredLocations, startIndexLocations);
            },
            error: function () {
                alert('Error fetching data. Please try again.');
            }
        });
    });
});


</script> -->


<%-- <div class="dashboard-frame">
    <div id="loading" style="display:none; text-align:center; font-weight:bold;">Loading...</div>
    		<div class="frame-content">
				<div class="chart-wrapper">
        			<div class="chart-container">
    					<h4 class="frame-title">State-wise Participants at Project Level</h4>
    				<div class="chart-navigation">
        				<img id="prevState" src="<c:url value='/resources/images/back-button.png'/>" alt="Previous" class="nav-icon left-icon" />
        				<canvas id="stateChart"></canvas>
        				<img id="nextState" src="<c:url value='/resources/images/forward-button.png'/>" alt="Next" class="nav-icon right-icon" />
    				</div>
					</div>	
			</div>
		</div>
		
		
</div>
 --%>


<script>

$(document).ready(function () {
	const range = 30;
	let stateChart, districtChart;
	let hoverTimeout, lastHoveredStateCode = null;

	let stateLabels = [], stateValues = [];
	let districtLabels = [], districtValues = [];
	let stateIndex = 0, districtIndex = 0;

	$('#loading').hide();
	
  <c:if test="${not empty stateWiseData}">
  <c:forEach var="entry" items="${stateWiseData}">
    stateLabels.push('<c:out value="${entry.st_name}"/>');
    stateValues.push(Number('<c:out value="${entry.totparticipants}"/>'));
  </c:forEach>
  </c:if>

  const stateCtx = document.getElementById('stateChart').getContext('2d');
 // const districtCtx = document.getElementById('districtChart').getContext('2d');

  stateChart = new Chart(stateCtx, {
    type: 'bar',
    data: {
      labels: stateLabels.slice(stateIndex, stateIndex + range),
      datasets: [{
        label: 'No. of Participants',
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
        y: { beginAtZero: true, title: { display: true, text: 'Participants' } }
      }
    }
  });

 /*  $('#nextState').click(() => {
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
  }); */

 
});




</script>
 
 
 
	<footer class="text-center">
    <%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>