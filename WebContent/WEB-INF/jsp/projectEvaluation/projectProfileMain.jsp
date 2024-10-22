<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        padding: 20px;
    }

    .tabs-container {
        display: flex;
        flex-direction: column;
        width: 100%; /* Full width */
        background-color: #ffffff;
        box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        margin: auto;
        max-width: 1200px; /* Maximum width for the container */
        border-radius: 8px;
    }

    .tab-link {
        padding: 20px;
        background-color: #d5e1ed;
        color: #000000;
        text-decoration: none;
         border-radius: 0;
        transition: background-color 0.3s, transform 0.3s;
        font-size: 18px;
        position: relative;
        border-bottom: 2px solid #0056b3;
        flex: 1;
        cursor: pointer;
        margin: 5px 0;
    }

    .tab-link:hover {
        background-color: #bfd0e0;
    }

    .tab-link.active {
        background-color: #0056b3;
        color: #ffffff;
        font-weight: bold;
    }

    .tab-link:after {
        content: '\25BA'; /* FontAwesome down arrow */
        font-family: "Font Awesome 5 Free";
        font-weight: 900;
        position: absolute;
        right: 15px;
        top: 50%;
        transform: translateY(-50%);
        transition: transform 0.3s;
    }

    .tab-link.active:after {
        transform: translateY(-50%) rotate(180deg);
    }
    .tab-confirmation {
        color: green;
        font-weight: bold;
        margin-left: 10px;
    }

    .read-only {
        pointer-events: none; 
        opacity: 0.6; 
        background-color: #f8f9fa; 
    }
    
</style>
</head>

<body>
<div class="maindiv">

	<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
   <td></td>
   <a href="getProfileStart" style="position: absolute; left: 0;">
    <img src="<c:url value='/resources/images/home_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
  </a>
  
  <h4 style="margin: 0;">
    <u>Project Evaluation</u>
  </h4>
</div>
	 <hr/>
    <div class="tabs-container">  
        <b> District Name: &nbsp; <c:out value='${distName}' /> , &nbsp;&nbsp;&nbsp; Project Name: &nbsp; <c:out value='${projName}'  />, &nbsp;&nbsp;&nbsp; Month Name: &nbsp; <c:out value='${monthname}' />, &nbsp;&nbsp;&nbsp; Financial Year: &nbsp; <c:out value='${finyr}' /></b>
       <hr/>
        <a href="projectProfile?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link">Project Profile
        <%
                if ("true".equals(request.getAttribute("projectProfileConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
        
        
        <a href="indicatorsEvaluation?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("projectProfileConfirmed") == null) { %>read-only<% } %>">
            Indicators for Evaluation
            
            <%
                if ("true".equals(request.getAttribute("evaluationDetailConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
            
            
            
        </a>
        <a href="fundUtilization?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("evaluationDetailConfirmed") == null) { %>read-only<% } %>">
        Fund Utilization 
        
        <%
                if ("true".equals(request.getAttribute("fundUtilizationConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
        <a href="croppedDetails1?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("fundUtilizationConfirmed") == null) { %>read-only<% } %>">
        Cropped Details-1 
        
        <%
                if ("true".equals(request.getAttribute("croppedDetails1Confirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
        <a href="croppedDetails2?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("croppedDetails1Confirmed") == null) { %>read-only<% } %>">
        Cropped Details-2
        <%
                if ("true".equals(request.getAttribute("croppedDetails2Confirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        </a>
        
       <a href="getMandayDeatails?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("croppedDetails2Confirmed") == null) { %>read-only<% } %>">
        No. of Man-days Details
        
        <%
                if ("true".equals(request.getAttribute("manDaysDetailConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
         <a href="productionDetails?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("manDaysDetailConfirmed") == null) { %>read-only<% } %>">
        Production Details
        
        <%
                if ("true".equals(request.getAttribute("productionDetailsConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
        
        
        <a href="ecologicalPerspective?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>"class="tab-link <%if(request.getAttribute("productionDetailsConfirmed")==null){ %>read-only<%} %>">
        Ecological Perspective 
        <%if("true".equals(request.getAttribute("ecoPerspectiveConfirmed"))){
        	%>
        	<span class="tab-confirmation">Confirm</span>
        <% } %>
        
        </a>
        <a href="equityAspect?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("ecoPerspectiveConfirmed") == null) { %>read-only<% } %>">
        Equity Aspect
        
        <%
                if ("true".equals(request.getAttribute("equityAspectConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
        
        <a href="getExecutionPlanWork?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("equityAspectConfirmed") == null) { %>read-only<% } %>">
        Execution of Planned Works against Targets
        <%
                if ("true".equals(request.getAttribute("executionOfPlannedWorkConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        </a>
        <a href="getQualityShapeFile?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("executionOfPlannedWorkConfirmed") == null) { %>read-only<% } %>">
        Quality of Project Shape Files
        
         <%
                if ("true".equals(request.getAttribute("qualityShapeFileConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        
        </a>
        <a href="getStatusGeotagWork?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("qualityShapeFileConfirmed") == null) { %>read-only<% } %>">
        Status of Geo-tagging of Works
        
           <%
                if ("true".equals(request.getAttribute("geoTagDetailsConfirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        
        
        </a>
   </div>
   <br>
   <%
   if ("true".equals(request.getAttribute("geoTagDetailsConfirmed"))) {
   %>
   <a href="getviewcomplete?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>">
   <center><input type="button" name="view"  id = "view" value="View & Complete" class="btn btn-info"/></center>
   </a>
   <% } %>
   
    
</div>
    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
</body>
