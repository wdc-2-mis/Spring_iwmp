<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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

<script type="text/javascript">

$(document).ready(function () {
    $('#updateMonthForm').submit(function (e) {
        e.preventDefault();
        var projid = $("#projid").val();
        
        var district = $("#dcode").val();
        var distName = $("#distName").val();
        var projName = $("#projName").val();
        var finyear = $("#fincd").val();
        var finName = $("#finyr").val();
        
        // Get the selected month value from dropdown instead of request parameters
        var monthid = $("#updmonth").val(); 
        var monthName = $("#updmonth option:selected").length ? $("#updmonth option:selected").text() : "N/A";


 
 if (!monthid) {
            alert("Please select Month!");
            $("#updmonth").focus();
            return;
        }
 $.ajax({
     type: 'POST',
     url: 'updateMonth',
     data: {projid: projid, monthid: monthid},
     dataType: 'json',
     success: function (response) {
         if (response.status === "success") {
             alert(response.message);
             var redirectUrl = "getProjectProfile?project=" + encodeURIComponent(projid) +
             "&district=" + encodeURIComponent(district) +
             "&distName=" + encodeURIComponent(distName) +
             "&projName=" + encodeURIComponent(projName) +
             "&finyear=" + encodeURIComponent(finyear) +
             "&finName=" + encodeURIComponent(finName) +
             "&month=" + encodeURIComponent(monthid) +
             "&monthName=" + encodeURIComponent(monthName);
             window.location.href = redirectUrl;  
         } else {
             alert("Error: " + response.message); 
         }
     },
     error: function () {
         alert("An error occurred while updating the month.");
     }
 });

 function getQueryParam(param) {
     var urlParams = new URLSearchParams(window.location.search);
     return urlParams.get(param) || "";
 }
     

});
    
    $('#updateAgency').submit(function (e) {
        e.preventDefault();
        var projid = $("#projid").val();
        
        var district = $("#dcode").val();
        var distName = $("#distName").val();
        var projName = $("#projName").val();
        var finyear = $("#fincd").val();
        var finName = $("#finyr").val();
        var updateAgencyName = $("#updateAgencyName").val().trim(); 
        var monthid = $("#monthid").val(); 
        var monthName = $("#monthname").val() ;

 
if (!updateAgencyName) {
    alert("Agency name cannot be empty!");
    return; 
}


  $.ajax({
     type: 'POST',
     url: 'updateAgency',
     data: {projid: projid, updateAgencyName: updateAgencyName},
     dataType: 'json',
     success: function (response) {
         if (response.status === "success") {
             alert(response.message);
             var redirectUrl = "getProjectProfile?project=" + encodeURIComponent(projid) +
             "&district=" + encodeURIComponent(district) +
             "&distName=" + encodeURIComponent(distName) +
             "&projName=" + encodeURIComponent(projName) +
             "&finyear=" + encodeURIComponent(finyear) +
             "&finName=" + encodeURIComponent(finName) +
             "&month=" + encodeURIComponent(monthid) +
             "&monthName=" + encodeURIComponent(monthName);
             window.location.href = redirectUrl;  
         } else {
             alert("Error: " + response.message); 
         }
     },
     error: function () {
         alert("An error occurred while updating the month.");
     }
 }); 

 function getQueryParam(param) {
     var urlParams = new URLSearchParams(window.location.search);
     return urlParams.get(param) || "";
 }
     

});   
});



</script>
</head>

<body>
<div class="maindiv">

	<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
   <td></td>
   <a href="getProfileStart" style="position: absolute; left: 0;">
    <img src="<c:url value='/resources/images/home_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
  </a>
  
  <h4 style="margin: 0;">
    <u>Mid Term Project Evaluation</u>
  </h4>
</div>
	 <hr/>
    <div class="tabs-container">  
        <b> &nbsp;&nbsp;&nbsp; State: &nbsp; <c:out value='${stName}' /> , &nbsp;&nbsp;&nbsp; District: &nbsp; <c:out value='${distName}' /> , &nbsp;&nbsp;&nbsp; Project: &nbsp; <c:out value='${projName}'  />, &nbsp;&nbsp;&nbsp; Financial Year: &nbsp; <c:out value='${finyr}' />, &nbsp;&nbsp;&nbsp; Month: &nbsp; <c:out value='${monthname}' /><a href="#" class="edit" data-toggle="modal" data-target="#editMonth" data-projid="${projid}" data-month="${monthid}"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>, </b>

<b>
    &nbsp;&nbsp;&nbsp; Name of Project Evaluation Agency: &nbsp; <c:out value='${pagency}' />
    
    <% if ("true".equals(request.getAttribute("projectProfileConfirmed"))) { %>
    <a href="#" class="edit" data-toggle="modal" data-target="#editAgency" data-projid="${projid}">
        <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
    </a>
<% } %>

</b>

 <div class="modal fade" id="editAgency" tabindex="-1" role="dialog" aria-labelledby="editAgencyLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="updateAgency">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Project Evaluation Agency</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="projid" value="${projid}" />
                    <input type="hidden" id="projName" name="projName" value="<c:out value='${projName}' />">
                    <input type="hidden" id="dcode" name="dcode" value="<c:out value='${dcode}' />">
                    <input type="hidden" id="finyr" name="finyr" value="<c:out value='${finyr}' />">
                    <input type="hidden" id="fincd" name="fincd" value="<c:out value='${fincd}' />">
                    <input type="hidden" id="distName" name="distName" value="<c:out value='${distName}' />">
                     <input type="hidden" id="monthid" name="monthid" value="<c:out value='${monthid}' />">
                      <input type="hidden" id="monthname" name="monthname" value="<c:out value='${monthname}' />">
                    
                    <label for="agencyName">Agency Name:</label>
                    <input type="text" name="updateAgencyName" id="updateAgencyName" class="form-control" value="${pagency}" autocomplete = "off" />
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Update</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>
 
 
        <a href="projectProfile?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>&pagency=<c:out value="${pagency}"/>" class="tab-link">Project Profile
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
        <a href="croppedDetails3?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("croppedDetails2Confirmed") == null) { %>read-only<% } %>">
        Cropped Details-3
        <%
                if ("true".equals(request.getAttribute("croppedDetails3Confirmed"))) {
            %>
                <span class="tab-confirmation">Confirm</span>
            <% } %>
        </a>
        
       <a href="getMandayDeatails?dcode=<c:out value="${dcode}"/>&pcode=<c:out value="${projid}"/>&dname=<c:out value="${distName}"/>&pname=<c:out value="${projName}"/>&mcode=<c:out value="${monthid}"/>&mname=<c:out value="${monthname}"/>&fcode=<c:out value="${fincd}"/>&fname=<c:out value="${finyr}"/>" class="tab-link <% if (request.getAttribute("croppedDetails3Confirmed") == null) { %>read-only<% } %>">
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
    <div id="editMonth" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Month</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="updateMonthForm">
                    <input type="hidden" id="projid" name="projid" value="<c:out value='${projid}' />">
                    <input type="hidden" id="projName" name="projName" value="<c:out value='${projName}' />">
                    <input type="hidden" id="dcode" name="dcode" value="<c:out value='${dcode}' />">
                    <input type="hidden" id="finyr" name="finyr" value="<c:out value='${finyr}' />">
                    <input type="hidden" id="fincd" name="fincd" value="<c:out value='${fincd}' />">
                    <input type="hidden" id="distName" name="distName" value="<c:out value='${distName}' />">
                    
                    
                    <div class="form-group">
                       <b>District Name: &nbsp; <c:out value='${distName},' /></b>
                    </div>
                    <div class="form-group">
                    <b> <label>Project Name:</label>
                        &nbsp; <c:out value='${projName},'  /></b>
                    </div>
                    <div class="form-group">
                    <b> <label>Financial Year:</label>
                        &nbsp; <c:out value='${finyr},'  /></b>
                    </div>
                    <div class="form-group">
                    <b> <label>Current Month:</label>
                        &nbsp; <c:out value='${monthname},' /></b>
                    </div>
                    
                        
                    <div class="form-group">
                        <label><b>Change Month:</b></label>
                         <select class="form-control month" name="updmonth" id="updmonth">
              		<option value="">--Select Month--</option>
              		
                  	 			<c:forEach var="lists" items="${monthList}">
                                <option value="<c:out value='${lists.key}'/>" 
                                    <c:if test="${lists.key eq month}">selected="selected"</c:if> >
                                    <c:out value="${lists.value}" />
                                </option>
                            </c:forEach>
					</select>
                    </div>

                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
    </div>
</div>
   <br>
   <%
   if ("true".equals(request.getAttribute("geoTagDetailsConfirmed"))) {
   %>
   <a href="getviewcomplete?district=<c:out value="${dcode}"/>&project=<c:out value="${projid}"/>&distName=<c:out value="${distName}"/>&projName=<c:out value="${projName}"/>&month=<c:out value="${monthid}"/>&monthName=<c:out value="${monthname}"/>&finyear=<c:out value="${fincd}"/>&finName=<c:out value="${finyr}"/>">
 <center><input type="button" name="view"  id = "view" value="View & Complete" class="btn btn-info"/></center>
   
 </a>
   <% } %>
   
    
</div>
    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
</body>
