<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
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
<title>Watershed Development Component-Pradhan Mantri Krishi
	Sinchayee Yojana (WDC-PMKSY)</title>
<meta name="description"
	content="Integrated Watershed Management Programme (IWMP) of Department of Land Resources (DoLR).">
<meta name="keywords"
	content="iwmp,IWMP Web,Watershed Management Programme,Integrated Watershed Management Programme,Department of Land Resources,DOLR,Watershed Development Component">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/gif/png"
	href='<c:url value="/resources/images/iwmp.gif" />' />
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
	      <link rel="stylesheet"
	href="resources/css/font/font-awesome4.7.min.css">
<style>
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
			<!-- <span style="border: 1px solid black;cursor:pointer;color:#000;">English/Hindi</span> -->	
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
						Development Component-Pradhan Mantri Krishi Sinchayee Yojana 2.0
						(WDC-PMKSY 2.0)</span><br /> <span class="title2">Department of Land
						Resources (भूमि संसाधन विभाग)  </span><br /> <span class="title2">Ministry
						of Rural Development (ग्रामीण विकास मंत्रालय)      </span><br /> <span
					class="title2">Government of India (भारत सरकार)  </span>
				</a>
			</div>
			<div class=" col-2"  >
				<a class="navbar-brand" href="#"> <img
					src="<c:url value="/resources/images/digi-india3.png" />"
					class="digi-india" width="150" height="100" alt="" style="float:right">
				</a>
			</div>
		</div>

</div>

<%-- 	 <nav class="navbar navbar-light  top-header ">
		<div class="row">
			<div class="col-1 inline-babbar-sher">
				<a class="navbar-brand" href="#"> <img
					src="<c:url value="/resources/images/tiranga_national_emblem.png" />"
					class=" babbar-sher" width="70" height="120" alt="">
				</a>
			</div>
			<div class=" col-10 inline-title">
				<a class="navbar-brand" href="#"> <span class="title1">Watershed
						Development Component-Pradhan Mantri Krishi Sinchayee Yojana
						(WDC-PMKSY)</span><br /> <span class="title2">Department of Land
						Resources( भूमि संसाधन विभाग )</span><br /> <span class="title2">Ministry
						of Rural Development(ग्रामीण विकास मंत्रालय )</span><br /> <span
					class="title2">Government of India( भारत सरकार )</span>
				</a>
			</div>
			<div class=" col-1 inline-digi-india">
				<a class="" href="#"> <img
					src="<c:url value="/resources/images/digi-india3.png" />"
					class=" digi-india" width="150" height="100" alt="">
				</a>
			</div>
		</div>
	</nav> 
 --%>	
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
				<li class="nav-item"><a class="nav-link" href="aboutus" style="font-size:17px; line-height:16px;"><i class="material-icons">supervisor_account</i>ABOUT</a></li>
				<li class="nav-item"><a class="nav-link" href="reports" style="font-size:16px; line-height:16px;"><span class="material-icons">assignment</span>REPORTS</a></li>
				<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">leaderboard</span> DASHBOARD </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="dolrDashBoard">DoLR Level</a> <a
							class="dropdown-item" href="#">State Level</a>
					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"> <span class="material-icons">language</span>SITES </a>
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
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">wysiwyg</span> DOCUMENTS </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">

						<a class="dropdown-item" href="IWMP-MIS-UserManual.pdf">User
							Manual - WDC-PMKSY-MIS</a> <a class="dropdown-item"
							href="CommonGuidelines2008English.pdf">Common Guidelines 2008
							(English)</a> <a class="dropdown-item"
							href="CommonGuidelines2008Hindi.pdf">Common Guidelines 2008
							(Hindi)</a>

					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="font-size:16px; line-height:16px;"><span class="material-icons">contact_page</span> CONTACT US </a>
					<div class="dropdown-menu dropdown-menu-index" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="technicalsupport">Technical
							Support</a> <a class="dropdown-item" href="dolrSupport">DoLR
							Support</a> <a class="dropdown-item" href="slnacoordinators">List
							of SLNA MIS-Coordinators</a>
					</div></li>
				<!-- <li class="nav-item">
        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
      </li> -->
			</ul>
			<!-- <form class="form-inline my-lg-0 navbar-left">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form> -->
			<ul class="navbar-nav navbar-nav-index navbar-right">
				<c:choose>
					<c:when test="${sessionScope.loginid eq null }">
						<li class="nav-item"><a class="nav-link" href="login"><span class="material-icons">login</span>LOGIN
						</a></li>
						<li class="nav-item"><a class="nav-link" href="register"><span class="material-icons">perm_identity</span>REGISTER</a>
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
	<!-- <ol class="carousel-indicators">
		<li data-target="#carouselExampleIndicators" data-slide-to="0"
			class="active"></li>
		<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
		<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
	</ol> -->
	
	<div class="carousel-inner" >
		 <div class="carousel-item active">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide44.png" />"
				alt="First slide">

		</div> 
		<div class="carousel-item ">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide99.png" />"
				alt="Second slide">
		</div>
		<div class="carousel-item">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide33.png" />"
				alt="Third slide">
		</div>
		<%-- <div class="carousel-item">
			<img class="d-block w-100"
				src="<c:url value="/resources/images/slide31.png" />"
				alt="Third slide">
		</div> --%>
		
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
<div class="indexFadeEffect"
	style=" padding-bottom:1px; padding-top:1px;">
	
 <!-- <marquee behavior="scroll" direction="left">  -->
<div id="demo" class="carousel slide slide2" data-ride="carousel">

  <!-- Indicators -->
  <!-- <ul class="carousel-indicators">
    <li data-target="#demo" data-slide-to="0" class="active"></li>
    <li data-target="#demo" data-slide-to="1"></li>
    <li data-target="#demo" data-slide-to="2"></li>
  </ul> -->
  
  <!-- The slideshow -->
  <div class=" carousel-inner no-padding">
    <div class="carousel-item active">
     <div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/whs.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr/> No. of Water Harvesting Structures Created/ Renovated
				</div>
			</div>
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/horticulture.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr /> Total Area Brought Under Horticulture
				</div>
			</div>
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/areabought.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr /> Area Brought Under Afforestation/ Agriculture/ Pasture etc.
				</div>
			</div> 
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/degradation.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${farmBenifitted}"></c:out><hr /> Area of Degraded Land Treated/ Rainfed Area Developed (in ha)
				</div>
			</div>
		
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/MConservation.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${waterHarvStructure}"></c:out><hr />Area Covered with soil and moisture conservation activity
				</div>
			</div>
		
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/mandays.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${additionalArea}"></c:out><hr /> No. of Mandays generated (in Mandays)
					
				</div>
			</div> 
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/plantation.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaUnderPlantation}"></c:out><hr /> Increase in Farmer`s Income(per annum)(%)
				</div>
			</div>
		
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaofCulturableWasteland}"></c:out><hr /> Increased in cropped area(in ha)
				</div>
			</div>
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/farmerbenefited.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaofCulturableWasteland}"></c:out><hr /> No. Of farmers benefitted
				</div>
			</div> 
    </div>
    <div class="carousel-item">
     <div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/whs.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr/> No. of Water Harvesting Structures Created/ Renovated
				</div>
			</div>
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/horticulture.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr /> Total Area Brought Under Horticulture
				</div>
			</div>
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/areabought.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr /> Area Brought Under Afforestation/ Agriculture/ Pasture etc.
				</div>
			</div> 
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/degradation.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${farmBenifitted}"></c:out><hr /> Area of Degraded Land Treated/ Rainfed Area Developed (in ha)
				</div>
			</div>
		
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/MConservation.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${waterHarvStructure}"></c:out><hr />Area Covered with soil and moisture conservation activity
				</div>
			</div>
		
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/mandays.jpg" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${additionalArea}"></c:out><hr /> No. of Mandays generated (in Mandays)
					
				</div>
			</div> 
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/plantation.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaUnderPlantation}"></c:out><hr /> Increase in Farmer`s Income(per annum)(%)
				</div>
			</div>
		
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaofCulturableWasteland}"></c:out><hr /> Increased in cropped area(in ha)
				</div>
			</div>
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/farmerbenefited.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaofCulturableWasteland}"></c:out><hr /> No. Of farmers benefitted
				</div>
			</div>    
    </div>
    <div class="carousel-item">
     <div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/employment.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr/> No. of Water Harvesting Structures Created/ Renovated
				</div>
			</div>
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/employment.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr /> Total Area Brought Under Horticulture
				</div>
			</div>
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/employment.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${empGenerated}"></c:out><hr /> Area Brought Under Afforestation/ Agriculture/ Pasture etc.
				</div>
			</div> 
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/farmerbenefited.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${farmBenifitted}"></c:out><hr /> Area of Degraded Land Treated/ Rainfed Area Developed (in ha)
				</div>
			</div>
		
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/harvesting.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${waterHarvStructure}"></c:out><hr />Area Covered with soil and moisture conservation activity
				</div>
			</div>
		
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/irrigation.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${additionalArea}"></c:out><hr /> No. of Mandays generated (in Mandays)
					
				</div>
			</div> 
			<div class="box "
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/plantation.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaUnderPlantation}"></c:out><hr /> Increase in Farmer`s Income(per annum)(%)
				</div>
			</div>
		
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaofCulturableWasteland}"></c:out><hr /> Increased in cropped area(in ha)
				</div>
			</div>
			<div class="box"
				style="text-align: center; width:10%; margin:5px;">
				<img class="rounded-circle img-fluid" style="opacity: 1;"
					src="<c:url value="/resources/images/circle/wateland.png" />"
					alt="First slide">
				<div class="text">
					<c:out	value="${areaofCulturableWasteland}"></c:out><hr /> No. Of farmers benefitted
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
</div>
			



		
			
		
			
 </marquee> 
</div>
<div class="card-group">
	<div class="card m-2">
		<div class="card-header text-white " style="background-color:#42b3e5">
			<ul class="nav">
				<li class="active"><a href="#alert" class="indexAlert"
					 data-toggle="tab">Alert</a>
				</li>
				<li><a href="#message"
					class="indexMessage"
					data-toggle="tab">Message</a></li>
				<li><a href="#circular"
					class="indexCircular" data-toggle="tab">Circular</a>
				</li>
			</ul>
		</div>
		<div class="card-body" style="overflow-y: auto; height: 240px;">
			<div class="tabs-wrapper tab-content ">
				<div class="tab-pane active" id="alert">
					<div class="col indexAlertContent">
						<ul class="list-group list-group-flush" id="alert-vertical">
							<c:forEach var="list" items="${alert}">
								<c:if test="${list.key=='alert' }">
									<c:forEach var="alertList" items="${list.value}">
										<li class="list-group-item"><c:out
												value="${alertList.subject }"></c:out></li>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="tab-pane" id="message">
					<div class="col indexAlertContent">
						<ul class="list-group list-group-flush" id="alert-vertical">
							<c:forEach var="list" items="${alert}">
								<c:if test="${list.key=='message' }">
									<c:forEach var="alertList" items="${list.value}">
										<li class="list-group-item"><c:out
												value="${alertList.subject }"></c:out></li>
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
										<li class="list-group-item"><c:out
												value="${alertList.subject }"></c:out></li>
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
				style="height: auto;"> Success Stories / Best Practice
		</div>
		<div class="card-body p-0">
			<div id="carouselExampleIndicators"
				class="carousel slide " data-ride="carousel"
				style="height: 100%">

				<div class="carousel-inner" style="height: 100%">
					
					<ul class="list-group list-group-flush" id="marquee-vertical">
						<c:forEach var="list" items="${stories}">
										<li class="list-group-item ">
										<a href=""><c:out value="${list.subject }"></c:out></a>
										</li>
							</c:forEach>
					

				</ul>
				</div>
				
			</div>
		</div>
		<div class="card-footer p-0 text-right">
			<a href="successStories"
				class="btn btn-sm btn-primary btn-md waves-effect success-stories">More</a>
		</div>
	</div>
	<div class="card m-2">
		<div class="card-header bg-success text-white">
			<img src="assets/images/Upload-Video.png" alt=""
				style="height: 24px;"> Video
		</div>
		<div class="card-body p-0">
			<!-- <iframe class="video-responsive" style="width: 100%;"
				src="https://www.youtube.com/embed/qM6cYWyaVyA" frameborder="0"
				allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
				allowfullscreen></iframe> -->
				
				
				<!-- <iframe style="width: 100%;min-height:100%" 
				 src="https://www.youtube.com/embed/Ejx1olNQ3SI" 
				 title="YouTube video player" frameborder="0" 
				 allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
			<p class="text-muted">Tamil Nadu Watershed Development Agency - Activities & Best Practices</p> -->
			
			<iframe style="width: 100%;min-height:100%" 
				 src="https://www.youtube.com/embed/j-iJh8BYkZY" 
				 title="YouTube video player" frameborder="0" 
				 allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
			<p class="text-muted">बिहार में भूमि संरक्षण योजनाओं से बदली किसानों की तकदीर</p>
		</div>
		<div class="card-footer p-0 text-right">
			<a href="#" target="_blank"
				class="btn btn-sm btn-primary btn-md waves-effect video">More</a>
		</div>
	</div>

</div>



<div class="container-fluid" style="background-color: #fff;padding: 2%; color: #000;">
	<div class="row">
		
		<div class="col-sm-6 text-justify"
			style="background-color: #fff; min-height: 225px; border: 1px solid #e2e2e2">
			<img src="<c:url value="/resources/images/logo - Copy.png" />"
				class="img-thumbnail"
				style="width: 100px; height: 100px; margin-right: 10px; margin-bottom: 10px; float: left;" />
			<h4>
				<b>Watershed Development Component of PMKSY </b>
			</h4>
			Government of India is committed to accord high priority to water
			conservation and its management. To this effect <b>Pradhan Mantri
				Krishi Sinchayee Yojana (PMKSY)</b> has been formulated with the vision
			of extending the coverage of irrigation <b>‘Har Khet ko pani’</b> and
			improving water use efficiency <b>‘More crop per drop'</b> in a
			focused manner with end to end solution on source creation,
			distribution, management, field application and extension activities.
			The Cabinet Committee on Economic Affairs chaired by Hon’ble Prime
			Minister has accorded approval of Pradhan Mantri Krishi Sinchayee
			Yojana (PMKSY) in its meeting held on 1st July, 2015. <br/>
			PMKSY has been formulated amalgamating ongoing schemes viz. Accelerated 
			Irrigation Benefit Programme (AIBP) of the Ministry of Water Resources, 
			River Development & Ganga Rejuvenation (MoWR,RD&GR),<b> Integrated Watershed 
			Management Programme (IWMP) of Department of Land Resources (DoLR) </b>and the 
			On Farm Water Management (OFWM) of Department of Agriculture and Cooperation (DAC). 
			PMKSY has been approved for implementation across the country with an outlay of Rs.
			 50,000 crore in five years. For 2015-16, an outlay of Rs.5300 crore has been made 
			 which includes Rs. 1800 crore for DAC; Rs. 1500 crore for DoLR; Rs. 2000 crore for 
			 MoWR(Rs. 1000 crore for AIBP; Rs. 1000 crores for PMKSY).<a
				href="aboutus">readmore</a>
		</div>
		<div class="col-sm-6"><div class="container-fluid"
	style="background: url(./resources/images/circle-bg.jpg); border-top: 2px solid #e2e2e2;padding-top:2%; padding-bottom:2%;">

	<div class="row">
		<div class="col-12 text-center">
				<h5>
					<font color="#000">Images Gallery</font>
				</h5>
				
		</div>
		
		<!-- Gallery Content -->
		<c:forEach var="list" items="${images}">
			<c:forEach var="files" items="${list.value}">
				<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							<%-- src="FileServlet?path=${files.path.replace('\\', '/')}${files.fileName}" --%>
							 src="http://wdcpmksy.dolr.gov.in/filepath/PRD/CircularMessageAlert/${files.fileName}" 
							
							alt="${files.subject}">
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
<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="<c:url value="/resources/images/dashboard1.jpg" />"
							alt="">
						<div class="overlay">
							<a href="<c:url value="/resources/images/dashboard1.jpg" />" class="icon" title="View">
								<p>Image</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="<c:url value="/resources/images/dashboard3.jpg" />"
							alt="">
						<div class="overlay">
							<a href="<c:url value="/resources/images/dashboard3.jpg" />" class="icon" title="View">
								<p>Image</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="<c:url value="/resources/images/dashboard4.jpg" />"
							alt="">
						<div class="overlay">
							<a href="<c:url value="/resources/images/dashboard4.jpg" />" class="icon" title="View">
								<p>Image</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="<c:url value="/resources/images/dashboard4.jpg" />"
							alt="">
						<div class="overlay">
							<a href="<c:url value="/resources/images/dashboard4.jpg" />" class="icon" title="View">
								<p>Image</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="<c:url value="/resources/images/dashboard5.jpg" />"
							alt="">
						<div class="overlay">
							<a href="<c:url value="/resources/images/dashboard5.jpg" />" class="icon" title="View">
								<p>Image</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
<div class="col-md-3 col-6" style="padding: 1%">
					<div class="img-hover-zoom--slowmo">
						<img class="img-fluid img-thumbnail"
							src="<c:url value="/resources/images/dashboard6.jpg" />"
							alt="">
						<div class="overlay">
							<a href="<c:url value="/resources/images/dashboard6.jpg" />" class="icon" title="View">
								<p>Image</p> <span class="material-icons">
									search </span>
							</a>
						</div>
					</div>
				</div>
		<!-- Gallery Content Ends -->
	</div>
</div></div>
		
	</div>
</div>

<div class="container-fluid" >
<div class="row" style="padding:1%">
<div class="offset-md-2"></div>
<%-- <div class="col-1" ><a href="http://www.nic.in/" target="_blank">
   			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/nic-logo.jpg" />' alt="NIC Logo" >
   			</a></div>
<div class="col-1" ><a href="http://apps.nic.in/" target="_blank">
   			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/egovapp.png" />' alt="Appstore" >
  			</a></div>
<div class="col-1" ><a href="http://india.gov.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/indiagov.png" />' alt="India.gov.in">
			</a></div>
<div class="col-1" ><a href="http://digitalindia.gov.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/digiindia.png" />' alt="Digital India">
			</a></div>
<div class="col-1" ><a href="https://www.mygov.in/" target="_blank">
    		<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/mygov.jpg" />' alt="MyGov">
   		 	</a> </div>
<div class="col-1" ><a href="https://www.data.gov.in/" target="_blank">
    		<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/datagov.png" />'  alt="DataGov">
   		 	</a> </div> --%>
 
 
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
	              <div class="brand"><a href="https://prayas.nic.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/prayas.png" />' alt="India.gov.in">
			</a></div>
	              </div>
	            </div>
	             <div class="one-slide">
	              <div class="testimonial w-50 h-50  p-0 text-center">
	              <div class="brand"><a href="https://dolr.gov.in/" target="_blank">
			<img class="img-fluid img-thumbnail" src='<c:url value="/resources/images/footer/dolr.png" />' alt="India.gov.in">
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
<div class="col" ><li><a href="./">Home</a></li><li><a href="aboutus">About Us</a></li><li><a href="#">Contact Us</a></li></div>
<div class="col" ><li><a href="reports">Reports</a></li><li><a href="#">Dashboard</a></li><li><a href="#">Documents</a></li></div>
<div class="col" ><li><a href="#">Sites</a></li><li><a href="gallery">Gallery</a></li><li><a href="successStories">Success Stories</a></li></div>
<div class="col" ><li><a href="siteMap">Site Map</a></li><li><a href="whatsNew">Whats New</a></li><li><a href="faq">FAQ</a></li></div>
<div class="col" ><li><a href="login">Login</a></li><li><a href="register">Register</a></li></div>
<input type="hidden" value="${pageContext.request.servletPath}" class="path"/>


</div>
</div>
	<div class="panel-heading">
		<br/><span>Data presented in this site has been updated by respective State Governments/UT Administration and Department of Land Resources, Ministry of Rural Development(MoRD), Government of India.</span>
<br/><span>Website hosted and maintained by National Informatics Center. Best viewed on Google Chrome Version 50 or above and Mozilla Firefox 50 or above versions of these browsers with resolution 1024 X 768</span>
<br/><span>Copyright 2019 © Department of Land Resources. All Rights Reserved.</span>

	</div>
  	<%-- <div class="panel-body">
  		
  		<div class="row">
  			<div class="col-xs-6 col-2">
  			<a href="http://www.nic.in/" target="_blank">
   			<img class="nic-logo footerimg" src='<c:url value="/resources/images/nic_logo_footer.jpg" />' alt="NIC Logo" >
   			</a>
   			</div>
   		
   			<div class="col-xs-6 col-2">
			<a href="http://apps.nic.in/" target="_blank">
   			<img class="img-responsive footerimg" src='<c:url value="/resources/images/app_store.jpg" />' alt="Appstore" >
  			</a>
  			</div>
  		
  			<div class="clearfix visible-xs"></div>
  			<div class="col-xs-6 col-2">
  			<a href="http://india.gov.in/" target="_blank">
			<img class="img-responsive footerimg" src='<c:url value="/resources/images/indiagov.jpg" />' alt="India.gov.in">
			</a>
			</div>
		
			<div class="col-xs-6 col-2">
   			<a href="http://digitalindia.gov.in/" target="_blank">
			<img class="img-responsive footerimg" src='<c:url value="/resources/images/digitalindia.png" />' alt="Digital India">
			</a>
			</div>
		
		 	<div class="clearfix visible-xs"></div>
			<div class="col-xs-6 col-2">
			<a href="https://www.mygov.in/" target="_blank">
    		<img class="img-responsive footerimg" src='<c:url value="/resources/images/mygov.jpg" />' alt="MyGov">
   		 	</a>  	
   			</div>
   			
   			<div class="clearfix visible-xs"></div>
			<div class="col-xs-6 col-2">
			<a href="https://www.data.gov.in/" target="_blank">
    		<img class="img-responsive footerimg" src='<c:url value="/resources/images/data-gov.jpg" />' width="130" height="37"  alt="DataGov">
   		 	</a>  	
   			</div>
   		 
   			
   		</div>
   			
  	</div> --%>
</div>

<!-- <script src="js/pdf.min.js"></script>
<script src="js/pdf.worker.min.js"></script> -->

<script src='<c:url value="resources/js/pdf228.min.js" />'></script> 
<script src='<c:url value="/resources/js/successStories.js" />'></script>
<script src='<c:url value="/resources/js/custom.js" />'></script>
<script src='<c:url value="/resources/js/jquery.marquee.js" />'></script>
<script src='<c:url value="/resources/js/login.js" />'></script>
<script src='<c:url value="/resources/js/registation.js" />'></script>
<script src='<c:url value="/resources/js/SHA512.js" />'></script>
<script type="text/javascript" src="<c:url value="/resources/js/lastModify.js" />"></script>

<!-- ------------------ Script to convert html table into datatable with pdf excel button -->
<!-- <link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css"> -->
 <!--<script src="https://code.jquery.com/jquery-3.5.1.js"></script> -->
<script src='<c:url value="resources/js/jquery25.dataTables.min.js" />'></script>
<script src='<c:url value="resources/js/1.7.1dataTables.buttons.min.js" />'></script>
<script src='<c:url value="resources/js/3.1.3jszip.min.js" />'></script>
<script src='<c:url value="resources/js/1.53pdfmake.min.js" />'></script>
<script src='<c:url value="resources/js/1.53vfs_fonts.js" />'></script>
<script src='<c:url value="resources/js/1.7.1buttons.html5.min.js" />'></script>


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