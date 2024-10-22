
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jspf/dashboardheader.jspf"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- <link rel="stylesheet" href="resources/css/dashboardbootstrap.min.css" /> -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" />
<link rel="stylesheet"
	href="resources/css/bootstrap/dataTables.bootstrap5.min.css" />
<!-- <link rel="stylesheet" href="resources/css/bootstrap/bootstrap_new.min.css" /> -->
<link rel="stylesheet" href="resources/css/bootstrap/slick.min.css" />
<link rel="stylesheet" href="resources/css/dashboardstyle.css" />
<link rel="stylesheet" href="resources/css/font/font-awesome.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>DOLR DashBoard</title>
<script>
 $(document).ready(function(){
	 $('#loading').hide();
 });
 </script>
</head>
<body>

	<section class="orange-fade p-5 margin-top-xl pos-r">
		<!--  <div class="container"> -->
		<div class="row">
			<div class="col-sm-12">

				<div class="mt-7 pos-r">
					<div class="carousel-controls testimonial-carousel-controls">
						<div class="control prev">
							<i class="fa fa-chevron-left" aria-hidden="false">&nbsp;</i>
						</div>
						<div class="control next">
							<i class="fa fa-chevron-right">&nbsp;</i>
						</div>
					</div>
					<div class="testimonial-carousel">
					
					
						
						

						

						
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Lynx"
										src="<c:url value="/resources/images/dashboard5.jpg" />" />
								</div>

								<div class="message text-center text-gray">Area Covered with Soil and Moisture conservation activities</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="soilmoisture" name="soilmoisture" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="soilmoisture" id="soilmoisture" value="${area_soilmoisture_activities_achie}" /><c:out	value="${area_soilmoisture_activities_achie}"></c:out></a></p></div>
             
							</div>
						</div>


						<%-- <div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard3.jpg" />" />
								</div>

								<div class="message text-center text-gray">Area of Degrade
									Land Treated (in ha)</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="degradedland" data-toggle="modal" name="degradedland" id ="degradedland" onclick="showdata(this.name);"><input type="hidden" name="degradedland" id="total" value="${degradedland}" /><fmt:formatNumber type="number" minFractionDigits="4" value="${degradedland}"/></a></p></div>

							</div>
						</div> --%>
						
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-2 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard6.jpg" />" />
								</div>

								<div class="message text-center text-gray">Area brought under Plantation (Horticulture and Afforestation)</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="afforestation" name="afforestation" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="afforestation" id="afforestation" value="${area_afforestation_horticulture_achie}" /><c:out	value="${area_afforestation_horticulture_achie}"></c:out></a></p></div>

							</div>
						</div>
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard4.jpg" />" />
								</div>

								<div class="message text-center text-gray">Water Harvesting Structure newly created and rejuvenated
									<input type="hidden" id="id" value="whsrenovate">
									</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;"><a href="" class="waterreno" name="waterreno" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="waterreno" id="waterreno" value="${water_created_renovated_achie}" /><c:out value="${water_created_renovated_achie}" /></a></p></div>



							</div>
						</div>
						
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard2.png" />" />
								</div>

								<div class="message text-center text-gray">
									Employment Generated (in mandays)
									<div class="separator"><p style="font-size:.8vw;margin-bottom:0;"><a href="" class="mandays" name="mandays" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="mandays" id="mandays" value="${man_days_gen}" /><c:out	value="${man_days_gen}"></c:out></a> </p></div>
									
								</div>
							</div>
						</div>
						
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard1.jpg" />" />
								</div>

								<div class="message text-center text-gray"
									class="img-responsive">
									Farmers Benefitted (in No.)
									<div class="separator"><p style="font-size:.8vw;margin-bottom:0;" id="afforestation"><a href="" class="farmerbenef" name="farmerbenef" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="farmerbenef" id="farmerbenef" value="${farmer_benefitted_achie}" /><c:out	value="${farmer_benefitted_achie}"></c:out> </a><%-- <input type="hidden" id="afforestationhidden"  value="${afforestation}" /> --%>  </p></div>
									
								</div>
							</div>
						</div>
						
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard3.jpg" />" />
								</div>

								<div class="message text-center text-gray">Area of Degraded Land covered and Rainfed area developed</a>
									<input type="hidden" id="id" value="whsnew">
									</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;"><a href="" class="degradedr" name="degradedr" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="degradedr" id="degradedr" value="${degraded_rainfed}" /><c:out	value="${degraded_rainfed}"></c:out></a></p></div>

							</div>
						</div>
						
						<%-- <div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard2.png" />" />
								</div>

								<div class="message text-center text-gray">Area of Rainfed
									Area Developed (in ha)</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="rainfed" data-toggle="modal" name="rainfed" id ="rainfed" onclick="showdata(this.name);"><input type="hidden" name="rainfed" id="total" value="${rainfed}" /><fmt:formatNumber type="number" minFractionDigits="4" value="${rainfed}"/></a></p></div>

							</div>
						</div> --%>
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-2 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard6.jpg" />" />
								</div>

								<div class="message text-center text-gray">Additional Area brought under Protective Irrigation</div>
								<div class="separator"><p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="protectiveirr" name="protectiveirr" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="protectiveirr" id="protectiveirr" value="${protective_irrigation_achie}" /><c:out	value="${protective_irrigation_achie}"></c:out></a> </p></div>

							</div>
						</div>
                      <%-- <div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard2.png" />" />
								</div>

								<div class="message text-center text-gray">Area of Forest
									Culturable Wasteland Developed(in ha)</div>
								<div class="separator">&nbsp;</div>

							</div>
						</div>

						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-0 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard2.png" />" />
								</div>

								<div class="message text-center text-gray">Area of
									Non-Forest Culturable Wasteland Developed(in ha)</div>
								<div class="separator">&nbsp;</div>

							</div>
						</div> 
						
						<div class="one-slide white">
							<div class="testimonial w-100 h-100  p-2 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard6.jpg" />" />
								</div>

								<div class="message text-center text-gray">Area covered
									under diversified crops/change in cropping pattern (in ha)</div>
								<div class="separator">&nbsp;</div>

							</div>
						</div>
						
						--%>


						
						
						<%-- <div class="one-slide white">
							<div class="testimonial w-100 h-100  p-2 text-center">
								<div class="brand">
									<img alt="Oneblood"
										src="<c:url value="/resources/images/dashboard6.jpg" />" />
								</div>

								<div class="message text-center text-gray">Area covered
									under diversified crops/change in cropping pattern (in ha)</div>
								<div class="separator">&nbsp;</div>

							</div>
						</div> --%>
					</div>
				</div>
			</div>
		</div>
	</section>


<!-- 	<div class="row"> -->

<!-- 		<div class="col-md-5 mb-3"> -->
<!-- 			<div class="card h-100"> -->
<!-- 				<div class="card-header"> -->
<!-- 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> -->
<!-- 					<b>State Wise Projects (Central Share 60%)</b> -->
<!-- 				</div> -->
<!-- 				<div class="card-body"> -->
<%-- 					<canvas class="chart2" width="400" height="200"></canvas> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->


<!-- 		<div class="col-md-4 mb-3"> -->
<!-- 			<div class="card h-100"> -->
<!-- 				<div class="card-header"> -->
<!-- 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> -->
<!-- 					<b>State Wise Projects(Central Share 90%)</b> -->
<!-- 				</div> -->
<!-- 				<div class="card-body"> -->
<%-- 					<canvas class="chart1" width="400" height="200"></canvas> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class="col-md-3 mb-3"> -->
<!-- 			<div class="card h-100"> -->
<!-- 				<div class="card-header"> -->
<!-- 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> -->
<!-- 					<b>State Wise Projects(Central Share 100%)</b> -->
<!-- 				</div> -->
<!-- 				<div class="card-body"> -->
<%-- 					<canvas class="chart" width="400" height="200"></canvas> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 				<div class="col-md-5 mb-3"> -->
<!-- 					<div class="card h-100"> -->
<!-- 						<div class="card-header"> -->
<!-- 							<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> Line -->
<!-- 							Chart -->
<!-- 						</div> -->
<!-- 						<div class="card-body"> -->
<%-- 							<canvas class="linechart" width="400" height="200"></canvas> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

<!-- 				<tr> -->
<!-- 				</tr> -->

<!-- 		 		<div class="col-md-1 mb-6"></div> -->

<!-- 			<div class="col-md-4 mb-6"> -->
<!-- 					<div class="card h-100"> -->
<!-- 						<div class="card-header"> -->
<!-- 							<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> Pie -->
<!-- 							Chart -->
<!-- 						</div> -->
<!-- 						<div class="pie"> -->
<%-- 							<canvas class="piechart" width="400" height="200"></canvas> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="col-md-3 mb-6"> -->

<!-- 					<div class="card h-100"> -->
<!-- 						<div class="card-header"> -->
<!-- 							<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> -->
<!-- 							Donut Chart -->
<!-- 						</div> -->
<!-- 						<div class="pieContainer"> -->
<%-- 							<canvas class="donutchart" width="400" height="200"></canvas> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

<!-- 	</div> -->
	<div class ="row">
				<div class="col-md-10 mb-15">
					<div class="card h-100">
						<div class="card-header">
							<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
							<b>State wise Total Project Cost and Treasury Receipt.</b>
						</div>
						<div class="card-body">
							<canvas id="groupBarChart3" ></canvas>
						</div>
					</div>
				</div>
	
	</div>
	<br>
	<div class ="row">
				<div class="col-md-10 mb-15">
					<div class="card h-100">
						<div class="card-header">
							<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
							<b>State wise Total Project Cost and Expenditure.</b>
						</div>
						<div class="card-body">
							<canvas id="groupBarChart2" ></canvas>
						</div>
					</div>
				</div>
	</div>
	<br>
	<div class ="row">
				<div class="col-md-10 mb-15">
					<div class="card h-100">
						<div class="card-header">
							<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
							<b>State wise Project Cost, Treasury Receipt and Expenditure.</b>
						</div>
						<div class="card-body">
							<canvas id="groupBarChart1" ></canvas>
						</div>
					</div>
				</div>
	
	</div>
	<br>
	<div class ="row">
		<div class="col-md-10 mb-15"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>State-Wise Percentage of Plot Area Covered Under Baseline Survey.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="prcntgArea"></canvas>
						</div>
		 			</div> 
		 		</div> 
	</div>
	<br>
	<div class="row">
		 		<div class="col-md-6 mb-6"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>Percentage Distribution of Baseline Area as per Land Classification for States with 100% Baseline Survey Completion as on Baseline.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="chartContainer1"></canvas>
						</div>
		 			</div> 
		 		</div> 
		 		
		 		<div class="col-md-6 mb-6"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>Percentage Distribution of Baseline Area as per Land Classification for States with 100% Baseline Survey Completion as on Date.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="chartContainerAch"></canvas>
						</div>
		 			</div> 
		 		</div> 

	</div>
	<br>
	<div class="row">
		 		<div class="col-md-6 mb-6"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>Percentage Distribution of Baseline Area as per Irrigation Status for States with 100% Baseline Survey Completion as on Baseline.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="chartContainerIrri"></canvas>
						</div>
		 			</div> 
		 		</div> 
		 		
		 		<div class="col-md-6 mb-6"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>Percentage Distribution of Baseline Area as per Irrigation Status for States with 100% Baseline Survey Completion as on Date.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="chartContainerIrriAch"></canvas>
						</div>
		 			</div> 
		 		</div> 
	</div>
	<br>
	<div class="row">
		 		<div class="col-md-6 mb-6"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>Percentage Distribution of Baseline Area as per No. of Crops for States with 100% Baseline Survey Completion as on Baseline.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="chartContainerCrop"></canvas>
						</div>
		 			</div> 
		 		</div> 
		 		
		 		<div class="col-md-6 mb-6"> 
		 			<div class="card h-100"> 
		 				<div class="card-header"> 
		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span> 
		 					<b>Percentage Distribution of Baseline Area as per No. of Crops for States with 100% Baseline Survey Completion as on Date.</b>
		 				</div> 
						<div class="wrapper">
							<canvas id="chartContainerCropAch"></canvas>
						</div>
		 			</div> 
		 		</div> 
	</div>
<!-- 	<br> -->
<!-- 	<div class ="row"> -->
<!-- 		<div class="col-md-10 mb-15">  -->
<!-- 		 			<div class="card h-100">  -->
<!-- 		 				<div class="card-header">  -->
<!-- 		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span>  -->
<!-- 		 					<b>Percentage Distribution of Baseline Area as per Land Classification for States.</b> -->
<!-- 		 				</div>  -->
<!-- 						<div class="wrapper"> -->
<%-- 							<canvas id="topTenStLand"></canvas> --%>
<!-- 						</div> -->
<!-- 		 			</div>  -->
<!-- 		 		</div>  -->
<!-- 	</div> -->
<!-- 	<br> -->
<!-- 	<div class ="row"> -->
<!-- 		 		<div class="col-md-10 mb-15">  -->
<!-- 		 			<div class="card h-100">  -->
<!-- 		 				<div class="card-header">  -->
<!-- 		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span>  -->
<!-- 		 					<b>Percentage Distribution of Baseline Area as per Irrigation Status States.</b> -->
<!-- 		 				</div>  -->
<!-- 						<div class="wrapper"> -->
<%-- 							<canvas id="topTenStIrri"></canvas> --%>
<!-- 						</div> -->
<!-- 		 			</div>  -->
<!-- 		 		</div>  -->
<!-- 	</div> -->
<!-- 	<br> -->
<!-- 	<div class ="row"> -->
<!-- 		 		<div class="col-md-10 mb-15">  -->
<!-- 		 			<div class="card h-100">  -->
<!-- 		 				<div class="card-header">  -->
<!-- 		 					<span class="me-2"><i class="bi bi-bar-chart-fill"></i></span>  -->
<!-- 		 					<b>Percentage Distribution of Baseline Area as per No. of Crops for States.</b> -->
<!-- 		 				</div>  -->
<!-- 						<div class="wrapper"> -->
<%-- 							<canvas id="topTenStCrop"></canvas> --%>
<!-- 						</div> -->
<!-- 		 			</div>  -->
<!-- 		 		</div> -->
		
<!-- 	</div> -->
	
	
	<script src="resources/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chart.js@4.0.1/dist/chart.umd.min.js"></script>
	<script src="resources/js/jquery-3.5.1.js"></script>
	<script src="resources/js/jquery-3.2.1.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/slick.min.js"></script>
	<script src="resources/js/jquery.dataTables.min.js"></script>
	<script src="resources/js/dataTables.bootstrap5.min.js"></script>
	<script src="resources/js/slider-boxes.js"></script>

	<script src="resources/js/dashboardscript.js"></script>
	
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>
