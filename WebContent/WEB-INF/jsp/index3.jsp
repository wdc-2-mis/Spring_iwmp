<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<!DOCTYPE html>
 <html lang="hi"> 
<%
response.addHeader("X-Frame-Options", "DENY");
response.addHeader("Referrer-Policy", "origin-when-cross-origin");
response.addHeader("Content-Security-Policy", "self");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
response.setHeader("X-Frame-Options", "SAMEORIGIN");
response.setHeader("X-Powered-By", "unset");
response.setHeader("Server", "unset");
response.addHeader("X-XSS-Protection: 1", "mode=block");
/* response.addHeader("Content-Security-Policy", "self"); */
response.addHeader("referrer", "no referrer");
response.addHeader("X-Content-Type-Options", "nosniff");
response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
%>
<head>
<title><spring:message code="label.title"/></title>
<meta name="description"
	content="Integrated Watershed Management Programme (IWMP) of Department of Land Resources (DoLR).">
	<meta name="robots" content="noarchive">
<meta name="keywords"
	content="iwmp,IWMP Web,Watershed Management Programme,Integrated Watershed Management Programme,Department of Land Resources,DOLR,Watershed Development Component">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-- <link rel="icon" type="image/gif/png"
	href='<c:url value="/resources/images/iwmp.gif" />' /> --%>
	<link rel="shortcut icon" href="#">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/bootstrap.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/custom.css" />">
	<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/all.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/styles.css" />" />

	<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/maticon.css" />" />
	
	<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/reportstyle.css" />" />
	
	<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/pdfViewer.css" />" />

	<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/loginstyle.css" />" />

<script src='<c:url value="/resources/js/jquery.min.js" />'></script>
<script src='<c:url value="/resources/js/bootstrap.min.js" />'></script>

<!-- index last slider -->

<link rel="stylesheet" href="resources/css/bootstrap/slick.min.css" />
	    <link rel="stylesheet" href="resources/css/dashboardstyle.css" /> 
	    <script src="resources/js/slick.min.js"></script>
	      <script src="resources/js/slider-boxes.js"></script>
	       <script src="resources/js/indexparameterslider.js"></script>
	      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>

a:link {
  color: black;
}

/* visited link */
a:visited {
  color: blue;
}

/* mouse over link */
a:hover {
  color: green;
}

/* selected link */
a:active {
  color: pink;
}

body.impaired-view, body.impaired-view * {
    font-size: 1%;
}
.one-slide {
    height:auto;
    }
.card-body {
    display: block;
}
.index-slide-last {
    background: #00332e;
    background: linear-gradient(135deg,#e3f3f0 0,#e8f5f3 100%);
}
.testimonial-index {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: inherit;
}
</style>

</head>

<body>


 <div class="container-fluid gap-index">
<div class="row">
<div class="col-1" >
				
			</div>
			<div class=" col-9" >
				
			</div>
			<div class=" col-2" >
			<span style="border: 1px solid black;cursor:pointer;background:#000; color:#fff;" class="decrease">A-</span>
			<span style="border: 1px solid black;cursor:pointer;background:#000; color:#fff;" class="reset">A</span>
			<span style="border: 1px solid black;cursor:pointer;background:#000; color:#fff;" class="increase">A+</span>
			<span style="border: 1px solid black;cursor:pointer;color:#000;"><a href="${pageContext.request.contextPath}/index3?lang=en">English</a></span> 
			<span style="border: 1px solid black;cursor:pointer;color:#000;"><a href="${pageContext.request.contextPath}/index3?lang=hi">Hindi</a></span>	
			</div>
</div>
</div>
<div class="container-fluid top-header-index">
<div class="row">

			<div class="col-1" style="margin-left:20px;" >
				<a class="navbar-brand" href="#"> <img
					src="<c:url value="/resources/images/tiranga_national_emblem.png" />"
					class=" babbar-sher" width="70" height="100" alt="">
				</a>
			</div>
			<div class=" col-8 inline-title-index"  style="margin-top:3px;">
				<a class="navbar-brand" href="#"> <span class="title1">Watershed
						Development Component-Pradhan Mantri Krishi Sinchayee Yojana 2.0 - MIS</span><br /> 
						
			<!-- ********************For Production Page*********************************** -->			
						<span class="title2">Department of Land
						Resources (भूमि संसाधन विभाग)</span><br /> 
						
			<!-- ********************Uncomment For Testing Page*********************************** -->			
			 <!-- <span class="title2">Department of Land
						Resources (भूमि संसाधन विभाग) &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span style="color:red;font-size:24px;">(TESTING SITE)</span> </span><br /> 
			  -->			
					<span class="title2">Ministry
						of Rural Development (ग्रामीण विकास मंत्रालय)      </span><br />	
						 <span
					class="title2">Government of India (भारत सरकार)  </span>
				</a>
			</div>
			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<div class=" col-2"  >
				<a class="" href="#"> <img
					src="<c:url value="/resources/images/g20-logo.png" />"
					class="g20-logo" width="120" height="100" alt="">
				</a>
				 <a class="" href="#"> <img
					src="<c:url value="/resources/images/digi-india3.png" />"
					class="digi-india" width="100" height="100">
				</a> 
			</div>
		</div>

</div>

 <nav class="navbar navbar-expand-lg navbar-light nav-menu-index" >
		<a class="navbar-brand" href="#"></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav navbar-nav-index mr-auto">
				<li class="nav-item active">
				<a class="nav-link" href="./"><span class="material-icons"  style="font-size:24px;line-height:16px;">home</span></a></li>
				<li class="nav-item"><a class="nav-link" href="aboutus" style="font-size:17px; line-height:16px;"><i class="material-icons">supervisor_account</i><spring:message code="label.about"/></a></li>
				<li class="nav-item"><a class="nav-link" href="reports" style="font-size:16px; line-height:16px;"><span class="material-icons">assignment</span><spring:message code="label.report"/></a></li>
				<li class="nav-item"><a class="nav-link" href="dolrDashBoard" style="font-size:16px; line-height:16px;"><span class="material-icons">leaderboard</span><spring:message code="label.dashboard"/></a></li>
				<li class="nav-item"><a class="nav-link" href="tejasBharatMap" style="font-size:16px; line-height:16px;"><span class="material-icons">map</span><spring:message code="label.map"/></a>
				
				<%-- 
				id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">leaderboard</span> <spring:message code="label.dashboard"/> </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="dolrDashBoard"><spring:message code="label.dolrlevel"/></a> <a
							class="dropdown-item" href="#"><spring:message code="label.statelevel"/></a>
					</div> --%></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"> <span class="material-icons">language</span><spring:message code="label.sites"/> </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="http://dolr.gov.in/"target="_blank" rel="noopener noreferrer"><spring:message code="label.dolr"/></a> 
						<a class="dropdown-item" href="https://rural.gov.in/" target="_blank" rel="noopener noreferrer"><spring:message code="label.mord"/></a> 
						<a class="dropdown-item" href="https://pfms.nic.in/" target="_blank" rel="noopener noreferrer"><spring:message code="label.pfms"/></a> 
						<a class="dropdown-item" href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/" target="_blank" rel="noopener noreferrer"><spring:message code="label.sristi"/></a>
						<a class="dropdown-item" href="https://iwmpmis.nic.in/" target="_blank" rel="noopener noreferrer"><spring:message code="label.wdc1.0"/></a>
					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">wysiwyg</span> <spring:message code="label.document"/> </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">

						<a class="dropdown-item" href="reference/UserManual.pdf"><spring:message code="label.usermanual"/></a> 
						<a class="dropdown-item" href="reference/WDCPMKSY2.0_Guidelines.pdf"><spring:message code="label.guidelines"/></a>
						    <!-- <a class="dropdown-item"
							href="CommonGuidelines2008English.pdf">Common Guidelines 2008
							(English)</a> <a class="dropdown-item"
							href="CommonGuidelines2008Hindi.pdf">Common Guidelines 2008
							(Hindi)</a> -->

					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">contact_page</span> <spring:message code="label.contactus"/> </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">

						<a class="dropdown-item" href="technicalsupport"><spring:message code="label.techsupport"/></a> 
						<a class="dropdown-item" href="dolrSupport"><spring:message code="label.dolrsupport"/></a> 
						<a class="dropdown-item" href="slnacoordinatorsdetails">List
							of SLNA MIS-Coordinators</a>

					</div></li>
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">wysiwyg</span> <spring:message code="label.frequentlyasked"/></a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">

						<a target="_blank" class="dropdown-item" href="reference/FAQs-MIS-WDC.pdf"><spring:message code="label.freqasked"/></a> <!-- <a class="dropdown-item"
							href="CommonGuidelines2008English.pdf">Common Guidelines 2008
							(English)</a> <a class="dropdown-item"
							href="CommonGuidelines2008Hindi.pdf">Common Guidelines 2008
							(Hindi)</a> -->

					</div></li>	
					
					<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class='material-icons'>videocam</span> <spring:message code="label.video"/></a>
					
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">

						<a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=JQl4V2EjvPI">Project Location</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=GT9chndoKf0">Base Line Survey Details Part-1(Plot Wise Detail)</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=AeaKDxsBNMg">Base Line Survey Details Part-2</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=BDfMIxTh3mU">Physical Action Plan And Achievement Part-1</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=p_vGeO3lVy4">Physical Action Plan And Achievement Part-2</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=WXJYNWOTyZ0">Physical Action Plan And Achievement Part-3</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=6hQg_fkKgnA">Physical Action Plan And Achievement Part-4</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=lJpO4dANR4Y">Physical Action Plan And Achievement Part-5</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=O3965CqAjWw">Project Implementation- Updation of Plotwise Details</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=kf-oNZKjBBw">Project Implementation Outcome Details</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=8U4cSiMPHHc">Progress Implementation - Ground Water Table and Newly Created SHGs Details</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=QImO4CCAYpE">Project Implementation - User Groups and Newly FPO Created</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=TfQte4IEPA8">Project Implementation EPAs or Livelihood Activities or Production System</a>
                        <a target="_blank" class="dropdown-item" href="https://www.youtube.com/watch?v=ne2vQ-vXaf0">Mapping of PFMS Transaction with Project and WorkId of the Projects</a>
                        
                        
                        
                        
                   </div></li>	
					<!-- ********************For Testing Page*********************************** -->
					<!-- &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<h2 style="color:red;"><b>TESTING SITE</b></h2>
			 -->
			</ul>
			<ul class="navbar-nav navbar-nav-index navbar-right">
				<c:choose>
					<c:when test="${sessionScope.loginid eq null }">
						<li class="nav-item"><a class="nav-link" href="login"><span class="material-icons">login</span><spring:message code="label.loginn"/>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="register"><span class="material-icons">perm_identity</span><spring:message code="label.register"/></a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a class="nav-link" href="#">Hi,
								${sessionScope.loginid}</a></li>
						<li class="nav-item"><a class="nav-link" href="logout">
								Logout</a></li>
					</c:otherwise>
				</c:choose>
			</ul>


		</div>
	</nav>
	
<div id="carouselExampleControls" class="carousel" data-ride="carousel">
	
	<div class="carousel-inner" >
		 <div class="carousel-item active">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide10.png" />"
				alt="First slide">

		</div> 
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/Picture15.jpg" />"
				alt="Eighth slide">
		</div>
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/Picture18.jpg" />"
				alt="Fourth slide">
		</div>
		<div class="carousel-item">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide55.png" />"
				alt="Third slide">
		</div>
		 
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide57.png" />"
				alt="Fifth slide">
		</div>
		<%-- <div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/Picture13.jpg" />"
				alt="Sixth slide">
		</div> --%>
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/Picture14.jpg" />"
				alt="Seventh slide">
		</div>
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/Picture15.jpg" />"
				alt="Eighth slide">
		</div>
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide99.png" />"
				alt="Second slide">
		</div>
		 
		
	</div>
	<a class="carousel-control-prev" href="#carouselExampleControls"
		role="button" data-slide="prev"> <span
		class="carousel-control-prev-icon" aria-hidden="true"></span> <span
		class="sr-only">Previous</span>
	</a> <a class="carousel-control-next" href="#carouselExampleControls"
		role="button" data-slide="next"> <span
		class="carousel-control-next-icon" aria-hidden="true"></span> <span
		class="sr-only">Next</span>
	</a>
	
</div>

<!-- <div class="container-fluid indexFadeEffect">
</div> -->

<div class="indexFadeEffect container-fluid"
	style=" padding-bottom:1px; padding-top:1px;">
	
	
	<div class="container-fluid">
<div class="row" style="boredr:1px solid red;"></div>
	<div class="row" style="width:100%">
		<div class="MultiCarousel" data-items="1,3,5,6" data-slide="1" id="MultiCarousel"  data-interval="1000">
          <div class="MultiCarousel-inner">
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" 
					src="<c:url value="/resources/images/circle/employment.png" />"
					alt="First slide">
					<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="soilmoisture" name="soilmoisture" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="soilmoisture" id="soilmoisture" value="${area_soilmoisture_activities_achie}" /><c:out	value="${area_soilmoisture_activities_achie}"></c:out></a></p> <p>Area Covered with Soil and Moisture conservation activities (in ha.) <br/></p>
                    </div>
                </div>
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" 
					src="<c:url value="/resources/images/circle/mandays.jpg" />"
					alt="First slide">
					<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="afforestation" name="afforestation" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="afforestation" id="afforestation" value="${area_afforestation_horticulture_achie}" /><c:out	value="${area_afforestation_horticulture_achie}"></c:out></a></p> <p>Area brought under Plantation (Horticulture and Afforestation) (in ha.)  ) <br/></p>
                    </div>
                </div>
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/MConservation.jpg" />"
					alt="First slide">
					<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="waterreno" name="waterreno" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="waterreno" id="waterreno" value="${water_created_renovated_achie}" /><c:out value="${water_created_renovated_achie}" /></a>  </p><p>Water Harvesting Structure newly created and rejuvenated (in no.)<br/></p>
                    </div>
                </div>
               
                
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="protectiveirr" name="protectiveirr" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="protectiveirr" id="protectiveirr" value="${protective_irrigation_achie}" /><c:out	value="${protective_irrigation_achie}"></c:out></a></p><p>Additional Area brought under Protective Irrigation (in ha.)</p>
                    </div>
                </div>
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/harvesting.png" />"
					alt="First slide">
				<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="mandays" name="mandays" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="mandays" id="mandays" value="${man_days_gen}" /><c:out	value="${man_days_gen}"></c:out></a></p><p>Employment Generated (in mandays) </p>
                    </div>
                </div>
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/irrigation.png" />"
					alt="First slide">
				<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="farmerbenef" name="farmerbenef" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="farmerbenef" id="farmerbenef" value="${farmer_benefitted_achie}" /><c:out	value="${farmer_benefitted_achie}"></c:out> </a></p><p>Farmers Benefitted (in No.) </p>
                    </div>
                </div>
                 <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/plantation.png" />"
					alt="First slide">
					<p style="font-size:1.4vw;margin-bottom:0;"><a href="" class="degradedr" name="degradedr" data-toggle="modal" onclick="showdata(this.name);"><input type="hidden" name="degradedr" id="degradedr" value="${degraded_rainfed}" /><c:out	value="${degraded_rainfed}"></c:out></a></p><p>Area of Degraded Land covered and Rainfed area developed (in ha.) </p>
                    </div>
                </div> 
                
            </div>
            <button class="btn btn-primary leftLst"><</button>
            <button class="btn btn-primary rightLst">></button>
        </div>
	</div>
	</div>
	
	<!-- 
	<div class="container-fluid">
<div class="row" style="boredr:1px solid red;"></div>
	<div class="row" style="width:100%">
		<div class="MultiCarousel" data-items="1,3,5,6" data-slide="1" id="MultiCarousel"  data-interval="1000">
            <div class="MultiCarousel-inner">
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" 
					src="<c:url value="/resources/images/circle/employment.png" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;"><a href="" class="whsnew" data-toggle="modal"  name="whsnew" id = "whsnew" onclick="showdata(this.name);"><input type="hidden" name="whsnew" id="total" value="${whsnew}" /><c:out	value="${whsnew}"/></a></p> <p><spring:message code="label.waterharvestingstructure"/> <br/></p>
                    </div>
                </div>
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" 
					src="<c:url value="/resources/images/circle/mandays.jpg" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;"><a href="" class="whsrenovate" data-toggle="modal" id ="whsrenovate" name="whsrenovate" onclick="showdata(this.name);"><input type="hidden" name="whsrenovate" id="total" value="${whsrenovate}" /><c:out	value="${whsrenovate}"></c:out></a></p> <p><spring:message code="label.waterharvestingstracturereno"/> <br/></p>
                    </div>
                </div>
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/MConservation.jpg" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;" id="afforestation"><a href="" class="afforestation" name = "afforestation" data-toggle="modal"  id = "afforestation" onclick="showdata(this.name);"><input type="hidden" name="afforestation" id="total" value="${afforestation}" /><c:out	value="${afforestation}"></c:out></a></a> </p><p><spring:message code="label.areabroughtunderafforestation"/></p>
                    </div>
                </div>
               
                <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/farmerbenefited.png" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;" id="horticulture"><a href="" class="horticulture" data-toggle="modal" name="horticulture" id = "horticulture" onclick="showdata(this.name);"><input type="hidden" name="horticulture" id="total" value="${horticulture}" /><c:out	value="${horticulture}"></c:out><fmt:formatNumber type="number" minFractionDigits="4" value="${horticulture}"/></a> </p><p><spring:message code="label.areabroughtunderhorti"/></p>
                    </div>
                </div>
                 <!-- <script>
                var afforestationtotal = 0,
                afforestationmax = $('#afforestationhidden').val(),
                afforestationstats = $('#afforestation').text(afforestationtotal),
                afforestationdelta=0;
            function incrementafforestation() { 
                var v = afforestationstats.text();

                if ( parseInt(v) < (parseInt(afforestationmax)-parseInt(afforestationtotal)) ) {//alert('ll');
                	afforestationstats.text( parseInt(v) + 1 );           
                } else {//alert('el');
                	afforestationdelta = Math.floor( 5*10 / ( afforestationmax - afforestationtotal ) );  
                	afforestationtotal = parseInt(afforestationtotal)+1;
                }

                setTimeout(incrementafforestation, afforestationdelta);
            }


            var horticulturetotal = 0,
            horticulturemax =99999;// $('#horticulturehidden').val(),
            horticulturestats = $('#horticulture').text(horticulturetotal),
            horticulturedelta=0;
            function incrementhorticulture() { 
            var v = horticulturestats.text();

            if ( parseInt(v) < (parseInt(horticulturemax)-parseInt(horticulturetotal)) ) {//alert('ll');
                if(v<500)
            	horticulturestats.text( parseInt(v) + 1 );
            	if(v>500) 
            		horticulturestats.text( parseInt(v) + 1 );          
            } else {//alert('el');
            	horticulturedelta = Math.floor( 5*10 / ( horticulturemax - horticulturetotal ) );  
            	horticulturetotal = parseInt(horticulturetotal)+1;
            }

            setTimeout(incrementhorticulture, horticulturedelta);
        }

        incrementhorticulture();
            incrementafforestation();
                </script> -->
                <%-- <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<p style="font-size:.8vw;margin-bottom:0;"><a href="" class="degradedland" data-toggle="modal" name="degradedland" id ="degradedland" onclick="showdata(this.name);"><input type="hidden" name="degradedland" id="total" value="${degradedland}" /><fmt:formatNumber type="number" minFractionDigits="4" value="${degradedland}"/><c:out	value="${degradedland}"></c:out></a></p><p><spring:message code="label.areaofdegraded"/></p>
                    </div>
                </div> --%>
                <%-- <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/harvesting.png" />"
					alt="First slide">
				<p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="rainfed" data-toggle="modal" name="rainfed" id ="rainfed" onclick="showdata(this.name);"><input type="hidden" name="rainfed" id="total" value="${rainfed}" /><fmt:formatNumber type="number" minFractionDigits="4" value="${rainfed}"/><c:out	value="${rainfed}"></c:out></a></p><p><spring:message code="label.rainfedarea"/> </p>
                    </div>
                </div> --%>
                <%-- <div class="item" style="font-family: 'Poppins', sans-serif !important;">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/irrigation.png" />"
					alt="First slide">
				<p style="font-size:.8vw;margin-bottom:0;">	<a href="" class="nilsingle" data-toggle="modal" name="nilsingle" id ="nilsingle" onclick="showdata(this.name);"><input type="hidden" name="nilsingle" id="total" value="${nilsingle}" /><fmt:formatNumber type="number" minFractionDigits="4" value="${nilsingle}"/><c:out	value="${nilsingle}"></c:out></a> </p><p><spring:message code="label.areabroughtfromnil"/> </p>
                    </div>
                </div> --%>
                <%-- <div class="item" style="">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/plantation.png" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;"><c:out	value="${mandays}"></c:out></p><p>No. of mandays generated (in mandays) </p>
                    </div>
                </div> --%>
                <%-- <div class="item" style="">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;"><c:out	value="${croppedarea}"></c:out></p> <p>Increased in cropped area(in ha) </p>
                    </div>
                </div> --%>
               <%--  <div class="item" style="">
                    <div class="pad15 indexbox">
                    <img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
					<p style="font-size:.8vw;margin-bottom:0;"><c:out	value="${farmersbenefited}"></c:out> </p><p>No. Of farmers benefitted </p>
                    </div>
                </div> --%>
               <!--  <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div>
                <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div>
                <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div>
                <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div>
                <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div>
                <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div>
                <div class="item">
                    <div class="pad15">
                        <p class="lead">Multi Item Carousel</p>
                        <p>₹ 1</p>
                        <p>₹ 6000</p>
                        <p>50% off</p>
                    </div>
                </div> 
            </div>
            <button class="btn btn-primary leftLst"><</button>
            <button class="btn btn-primary rightLst">></button>
        </div>
	</div>
	</div> -->
	
	
	
 <!-- <marquee behavior="scroll" direction="left">  -->
<%-- <div id="demo" class="carousel slide slide2" data-ride="carousel" >

  <!-- The slideshow -->
  <div class=" carousel-inner no-padding">
    <div class="carousel-item active">
     <div class="indexbox index-box" style="">
     
				<img class=" img-fluid w-50" 
					src="<c:url value="/resources/images/circle/employment.png" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${empGenerated}"></c:out></p> <p>No. of Water Harvesting Structures Created </p>
				</div>
			</div>
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/mandays.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${empGenerated}"></c:out></p> <p>No. of Water Harvesting Structures Renovated </p>
				</div>
			</div>
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/MConservation.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${empGenerated}"></c:out> </p><p>Area Brought Under Afforestation/ Agriculture/ Pasture etc.</p>
				</div>
			</div> 
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/farmerbenefited.png" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${farmBenifitted}"></c:out> </p><p>Area Brought Under Horticulture (in ha)</p>
				</div>
			</div>
		
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/harvesting.png" />"
					alt="First slide">
				<div class="text1">
				<p>	<c:out	value="${waterHarvStructure}"></c:out></p><p>Area of degraded land treated/ Rainfed area developed (in ha) </p>
				</div>
			</div>
		
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/irrigation.png" />"
					alt="First slide">
				<div class="text1">
				<p>	<c:out	value="${additionalArea}"></c:out> </p><p>Area brought from nil/ single crop to double or more crop (in ha) </p>
					
				</div>
			</div> 
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/plantation.png" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${areaUnderPlantation}"></c:out></p><p>No. of mandays generated (in mandays) </p>
				</div>
			</div>
		
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${areaofCulturableWasteland}"></c:out></p> <p>Increased in cropped area(in ha) </p>
				</div>
			</div>
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid w-50" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${areaofCulturableWasteland}"></c:out> </p><p>No. Of farmers benefitted </p><p>&nbsp;</p>
				</div>
			</div> 
    </div>
    <div class="carousel-item">
      <div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style=""
					src="<c:url value="/resources/images/dashboard1.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${empGenerated}"></c:out></p> No. of Water Harvesting Structures Created/ Renovated <br/>&nbsp;
				</div>
			</div>
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard2.png" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${empGenerated}"></c:out> </p>Total Area Brought Under Horticulture <br/>&nbsp;
				</div>
			</div>
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard3.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${empGenerated}"></c:out> </p>Area Brought Under Afforestation/ Agriculture/ Pasture etc.
				</div>
			</div> 
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard4.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${farmBenifitted}"></c:out> </p>Area of Degraded Land Treated/ Rainfed Area Developed (in ha)
				</div>
			</div>
		
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard5.jpg" />"
					alt="First slide">
				<div class="text1">
				<p>	<c:out	value="${waterHarvStructure}"></c:out></p>Area Covered with soil and moisture conservation activity <br/>&nbsp;
				</div>
			</div>
		
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard6.jpg" />"
					alt="First slide">
				<div class="text1">
				<p>	<c:out	value="${additionalArea}"></c:out> </p>No. of Mandays generated (in Mandays) <br/>&nbsp;
					
				</div>
			</div> 
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard3.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${areaUnderPlantation}"></c:out></p>Increase in Farmer`s Income(per annum)(%) <br/>&nbsp;
				</div>
			</div>
		
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard4.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${areaofCulturableWasteland}"></c:out></p> Increased in cropped area(in ha) <br/>&nbsp;
				</div>
			</div>
			<div class="indexbox index-box"
				style="">
				<img class=" img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/dashboard5.jpg" />"
					alt="First slide">
				<div class="text1">
					<p><c:out	value="${areaofCulturableWasteland}"></c:out> </p>No. Of farmers benefitted <br/>&nbsp;<br/>&nbsp;
				</div>
			</div>    
    </div>
    
  </div>
  
  <!-- Left and right controls -->
  <a class="carousel-control-prev" href="#demo" data-slide="prev">
    <span class="carousel-control-prev-icon"></span>
  </a>
  <a class="carousel-control-next" href="#demo" data-slide="next">
    <span class="carousel-control-next-icon"></span>
  </a>
</div> --%>
			
 </marquee> 
</div>
<div class="card-group">
	<div class="card m-2">
		<div class="card-header text-white " style="background-color:#42b3e5">
			<ul class="nav">
				<li class="active"><a href="#alert" class="indexAlert"
					 data-toggle="tab"><spring:message code="label.alert"/></a>
				</li>
				<li><a href="#message"
					class="indexMessage"
					data-toggle="tab"><spring:message code="label.message"/></a></li>
				<li><a href="#circular"
					class="indexCircular" data-toggle="tab"><spring:message code="label.circular"/></a>
				</li>
			</ul>
		</div>
		<div class="card-body" style="overflow-y: auto; height: 240px; padding-top: 0;">
			<div class="tabs-wrapper tab-content ">
				<div class="tab-pane active" id="alert">
					<div class="col indexAlertContent">
						<ul class="list-group list-group-flush" id="alert-vertical">
							<c:forEach var="list" items="${alert}">
								<c:if test="${list.key=='alert' }">
									<c:forEach var="alertList" items="${list.value}">
										<li class="list-group-item alert">
										<c:if test="${alertList.isNew eq true }"><img src="<c:url value="/resources/images/new.gif" />" alt="isNew"/></c:if><c:out
												value=" ${alertList.subject }"></c:out>
												<c:if test="${alertList.path ne null }"><a href="#" class="download" data-id="${alertList.path},${alertList.fileName}">download</a></c:if></li>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
				<script type="text/javascript">
				
    </script>
				<div class="tab-pane" id="message">
					<div class="col indexAlertContent">
						<ul class="list-group list-group-flush" id="alert-vertical">
							<c:forEach var="list" items="${alert}">
								<c:if test="${list.key=='message' }">
									<c:forEach var="alertList" items="${list.value}">
										<li class="list-group-item alert">
										<c:if test="${alertList.isNew eq true }"><img src="<c:url value="/resources/images/new.gif" />" alt="isNew"/></c:if><c:out
												value=" ${alertList.subject }"></c:out></li>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="tab-pane" id="circular">
					<div class="col indexAlertContent">
						<ul class="list-group list-group-flush" id="alert-vertical">
							<c:forEach var="list" items="${alert}">
								<c:if test="${list.key=='circular' }">
									<c:forEach var="alertList" items="${list.value}">
										<li class="list-group-item alert">
										<c:if test="${alertList.isNew eq true }"><img src="<c:url value="/resources/images/new.gif" />" alt="isNew"/></c:if><c:out
												value=" ${alertList.subject }"></c:out></li>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>

			</div>
		</div>
		<div class="card-footer p-0">
			<!--<small class="text-muted">Last updated 3 mins ago</small>-->
		</div>
	</div>
	<div class="card m-2">
		 <div class="card-header text-white bg-secondary"
			style="background: darkcyan !important;">
			<img src="assets/images/Success-Stories.png" alt="" class=""
				style="height: 24px;"> <spring:message code="label.successstories"/>
		</div> 
		<div class="card-body p-0">
			<div id="carouselExampleIndicators"
				class="carousel slide " data-ride="carousel"
				style="height: 100%">

				<div class="carousel-inner" style="height: 100%">
					
					<ul class="list-group list-group-flush" id="marquee-vertical">
						<c:forEach var="list" items="${stories}">
										<li class="list-group-item alert">
										<c:if test="${list.isNew eq true }"><img src="<c:url value="/resources/images/new.gif" />" alt="isNew"/></c:if>
										<a href="successStories"><c:out value="${list.subject }"></c:out></a>
										</li>
							</c:forEach> 
					

				</ul>
				</div>
				
			</div>
		</div>
		<%-- <div class="card-footer p-0 text-right">
			<a href="successStories"
				class="btn btn-sm btn-primary btn-md waves-effect success-stories"><spring:message code="label.more"/></a>
		</div> --%>
	</div>
	<div class="card m-2">
		<%-- <div class="card-header bg-success text-white">
			<img src="assets/images/Upload-Video.png" alt=""
				style="height: 24px;"> <spring:message code="label.videosgallery"/>
		</div> --%>
		<div class="card-body p-0">
			
			<iframe style="width: 100%;min-height:100%" 
				 src="https://www.youtube.com/embed/j-iJh8BYkZY" 
				 title="YouTube video player" frameborder="0" 
				 allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
			<p class="text-muted">बिहार में भूमि संरक्षण योजनाओं से बदली किसानों की तकदीर</p>
		</div>
		
		<div class="card-footer p-0 text-right">
			<a href="#" target="_blank"
				class="btn btn-sm btn-primary btn-md waves-effect video"><spring:message code="label.more"/></a>
		</div>
	</div>

</div>


 <div class="container-fluid" style="background-color: #fff;padding: 2%; color: #000;">
	<div class="row">
		
		<div class="col-sm-6 text-justify"
			style="background-color: #fff; min-height: 225px; border: 1px solid #e2e2e2;">
			<img src="<c:url value="/resources/images/logo - Copy.png" />"
				class="img-thumbnail"
				style="width: 100px; height: 100px; margin-right: 10px; margin-bottom: 10px; float: left;" />
			<h4>
				<b><spring:message code="label.wdcpmksy"/></b>
			</h4>
			<p style=""><spring:message code="label.wdcdetail"/><a href="aboutus">&nbsp&nbsp&nbsp<spring:message code="label.readmore"/></a></p>
			
		</div>
		<div class="col-sm-6"><div class="container-fluid"
	style="background: url(./resources/images/circle-bg.jpg); border-top: 2px solid #e2e2e2;padding-top:2%; padding-bottom:2%;">

	<div class="row">
		<div class="col-12 text-center">
				<h5>
					 <font color="#000"><spring:message code="label.photo"/></font> 
				</h5>
		</div>
		
		<!-- Gallery Content -->
		<c:forEach var="list" items="${images}">
			<c:forEach var="files" items="${list.value}">
				<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="http://wdcpmksy.dolr.gov.in/filepath/PRD/CircularMessageAlert/${files.fileName}"
							alt="${files.subject}" style="min-height:150px">
						<div class="overlay">
							<a href="gallery?code=${files.stCode}" class="icon" title="View">
								<p>${files.stName}</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:forEach>

		<!-- Gallery Content Ends -->
	</div>
</div></div>
		
	</div>
</div>


 


<div class="container-fluid" >
<div class="offset-md-2 col-md-3"></div>
<section class="index-slide-last p-4 margin-top-xl pos-r">
	 <!--  <div class="container"> -->
	   	<div class="row">
			<div class="col-sm-12">
	        
	       <div class="mt-6 pos-r">
	          <div class="carousel-controls testimonial-carousel-controls">
	            <div class="control prev"><i class="fa fa-chevron-left" aria-hidden="false">&nbsp;</i></div>
	            <div class="control next"><i class="fa fa-chevron-right">&nbsp;</i></div>
	          </div>
	          <div class="testimonial-carousel">
	            <div class="one-slide" >
	              <div class="testimonial w-50 h-100  p-0 text-center">
	              <div class="brand"><a href="https://data.gov.in/" target="_blank">
    		<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/datagov.png" />'  alt="DataGov">
   		 	</a></div>
	             </div>
	            </div>
	            
	            
	            
	            <div class="one-slide ">
	              <div class="testimonial w-50 h-100  p-0 text-center">
	              <div class="brand"><a href="https://www.mygov.in/" target="_blank">
    		<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/mygov.jpg" />' alt="MyGov">
   		 	</a></div>
	            </div>
	            </div>
	            <div class="one-slide ">
	              <div class="testimonial w-50 h-100  p-0 text-center">
	               <div class="brand"><a href="http://digitalindia.gov.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/digiindia.png" />' alt="Digital India">
			</a></div>
	              </div>
	            </div>
	            
	        
	            <div class="one-slide ">
	              <div class="testimonial w-50 h-100  p-0 text-center">
	              <div class="brand"><a href="http://india.gov.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/indiagov.png" />' alt="India.gov.in">
			</a></div>
	              </div>
	            </div>
	            <div class="one-slide ">
	              <div class="testimonial w-50 h-100  p-0 text-center">
	               <div class="brand"><a href="https://apps.gov.in/" target="_blank">
   			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/egovapp.png" />' alt="Appstore" >
  			</a></div>
	               
	              </div>
	            </div>
	            <div class="one-slide ">
	              <div class="testimonial w-50 h-100  p-2 text-center">
	              <div class="brand"><a href="http://www.nic.in/" target="_blank">
   			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/nic-logo.jpg" />' alt="NIC Logo" >
   			</a></div>
	              
	              </div>
	            </div>
	             <div class="one-slide ">
	              <div class="testimonial w-50 h-100  p-0 text-center">
	              <div class="brand"><a href="https://prayas.darpan.nic.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/prayas.png" />' alt="India.gov.in">
			</a></div>
	              </div>
	            </div>
	             <div class="one-slide">
	              <div class="testimonial w-50 h-50  p-0 text-center">
	              <div class="brand"><a href="https://dolr.gov.in/" target="_blank" rel="noopener noreferrer">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/dolr.png" />' alt="India.gov.in">
			</a></div>
	              </div>
	            </div>
	            <div class="one-slide">
	              <div class="testimonial w-50 h-100  p-0 text-center">
	              <div class="brand"><a href="https://bhuvan-app1.nrsc.gov.in/wdc2.0/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/logo_bhuvan.png" />' alt="srishtidrishti">
			</a></div>
	              </div>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	</section> 
	</div>
<script>$('.slide2').carousel({
  interval: false,
});</script>

<!-- Footer -->
<footer class=" text-center">


<link rel="stylesheet" href='<c:url  value="/resources/css/custom.css"/>' >

<div class="container-fluid"
	style="padding: 0.5%; background-image: linear-gradient(180deg, #dff1ee, #fff); background-repeat: no-repeatborder:2px solid blue;">
	<div class="row"></div>
</div>

<div  class="panel-footer panel-default">
<div class="container-fluid">
<div class="row">
<div class="offset-md-1"></div>
<div class="col" ><li><a href="./"><spring:message code="label.home"/></a></li><li><a href="aboutus"><spring:message code="label.aboutus"/></a></li></div>
<div class="col" ><li><a href="reports"><spring:message code="label.report"/></a></li><li><a href="whatsNew"><spring:message code="label.whatsnew"/></a></li></div>
<div class="col" ><li><a href="gallery"><spring:message code="label.gallery"/></a></li><li><a href="successStories"><spring:message code="label.successsty"/></a></li></div>
<div class="col" ><li><a href="login"><spring:message code="label.login"/></a></li><li><a href="register"><spring:message code="label.register"/></a></li></div>

<input type="hidden" value="${pageContext.request.servletPath}" class="path"/>


</div>
</div>
	<div class="panel-heading">
		<br/><span><spring:message code="label.footerdtlL1"/></span>
<br/><span><spring:message code="label.footerdtlL2"/></span>
<br/><span><spring:message code="label.footerdtlL3"/></span>

	</div>
  	
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.2.228/pdf.min.js"></script> 
<script src='<c:url value="/resources/js/successStories.js" />'></script>
<script src='<c:url value="/resources/js/custom.js" />'></script>
<script src='<c:url value="/resources/js/jquery.marquee.js" />'></script>
<script src='<c:url value="/resources/js/login.js" />'></script>
<script src='<c:url value="/resources/js/registation.js" />'></script>
<script src='<c:url value="/resources/js/SHA512.js" />'></script>
<script type="text/javascript" src="<c:url value="/resources/js/lastModify.js" />"></script>

<!-- ------------------ Script to convert html table into datatable with pdf excel button -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
 <!--<script src="https://code.jquery.com/jquery-3.5.1.js"></script> -->
<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>


<script>
/* $(document).ready(function() {
    function disablePrev() { window.history.forward() }
    window.onload = disablePrev();
    window.onpageshow = function(evt) { if (evt.persisted) disableBack() }
 }); */
</script>
	<%@include file="/WEB-INF/jspf/hitCount.jspf"%>
</footer>
</body>
</html>