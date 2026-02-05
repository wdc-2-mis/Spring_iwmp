<!DOCTYPE html>
<html>
<head>

<title>Report SMC2 - Watershed Mahotsav Social Media Analysis</title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css" />
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<script type="text/javascript">

$(document).ready(function() {

    // State → District
    $('#state').on('change', function() {
        var stateId = $(this).val();
        $.ajax({
            url: 'getDistrictsByStateForWM',
            type: 'POST',
            data: { state: stateId },
            success: function(data) {
                $('#district').empty().append('<option value="0">--All District--</option>');
                $.each(data, function(key, value) {
                    $('#district').append('<option value="'+value+'">'+key+'</option>');
                });
            }
        });
    });

    // Submit report
    $('#submitBtn').on('click', function() {
        document.mohotsavRpt.action = "getWMSocialMediaCompAnalysis";
        document.mohotsavRpt.method = "post";
        document.mohotsavRpt.submit();
    });
}); 
function dataAvailableFunction() {

	$(document).ready(function () {
	    $('#stWMR').DataTable({
	    	 "paging": true,          // Enable pagination
	         "pageLength": 10,        // Default rows per page
	         "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],  // Dropdown options with "All"
	         "lengthChange": true,    // Allow changing page size
	         "searching": true,       // Enable search box
	         "ordering": false,        // Disable sorting
	         "info": true             // Show info (e.g., "Showing 1 to 10 of 50 entries")
	    });
	});
	}
function submitOrderBy() {
    document.mohotsavRpt.action = "getWMSocialMediaCompAnalysis";
    document.mohotsavRpt.method = "post";
    document.getElementById("mohotsavReport").submit();
}


function downloadPDF(stName,distName,mediaName){
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("mediaName").value=mediaName;
//     document.getElementById("statusName").value=statusName;
//     document.getElementById("fromDate").value=fromDate;
//     document.getElementById("toDate").value=toDate;
    document.mohotsavRpt.action="downloadWMSocialMediaCompAnalysisPDF";
	document.mohotsavRpt.method="post";
	document.mohotsavRpt.submit();
}

function downloadExcel(stName,distName,mediaName){
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
    document.getElementById("mediaName").value=mediaName;
//     document.getElementById("statusName").value=statusName;
//     document.getElementById("fromDate").value=fromDate;
//     document.getElementById("toDate").value=toDate;
    document.mohotsavRpt.action="downloadExcelWMSocialMediaCompAnalysis";
	document.mohotsavRpt.method="post";
	document.mohotsavRpt.submit();
}


</script>
<style>
.facebook  { color:#1877F2; font-weight:600; }
.instagram { color:#C13584; font-weight:600; }
.youtube   { color:#FF0000; font-weight:600; }
.twitter   { color:#1DA1F2; font-weight:600; }
.linkedin  { color:#0A66C2; font-weight:600; }


/* Add margin above the DataTables controls */
div.dataTables_wrapper div.dataTables_length, div.dataTables_wrapper div.dataTables_filter
	{
	margin-bottom: 20px; /* gap between controls and table */
}

/* Add margin above the pagination/info section */
div.dataTables_wrapper div.dataTables_info, div.dataTables_wrapper div.dataTables_paginate
	{
	margin-top: 20px; /* gap between table and pagination */
}

</style>
<!-- b -->
</head>

<body>
<c:if test="${wmListSize !=0}">

		<script>
			// Call the function automatically when page loads
			dataAvailableFunction();
		</script>

	</c:if>
	<div class="maindiv">

		

			<div class="col formheading" style="text-decoration: underline;"><h4>Report SMC2 - Watershed Mahotsav Social Media Analysis</h4> </div>

			<br>
			<form name="mohotsavRpt" id="mohotsavReport" action="getWMSocialMediaCompAnalysis" method="post">
			
			<input type="hidden" name="stName" id="stName" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
			<input type="hidden" name="mediaName" id="mediaName" value="" />
<!-- 			<input type="hidden" name="statusName" id="statusName" value="" /> -->
<!-- 			<input type="hidden" name="fromDate" id="fromDate" value="" /> -->
<!-- 			<input type="hidden" name="toDate" id="toDate" value="" /> -->

				<div class="row">

					<div class="form-group col-3">
						<label>State <span class="required">*</span></label> <select
							name="state" id="state" class="form-select" onchange ="getDistrictList()">
							<option value="0">--All State--</option>
							<c:forEach items="${stateList}" var="lists">
								<option value="${lists.key}"
									${lists.key eq state ? 'selected' : ''}>${lists.value}</option>
							</c:forEach>
						</select>
					</div>


					<div class="form-group col-3">
						<label>District <span class="required">*</span></label> <select
							name="district" id="district" class="form-select">
							<option value="0">--All District--</option>
							<c:forEach items="${districtList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq district ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group col-3">
    					<label>Platform <span class="required">*</span></label>
    					<select name="platform" id="platform" class="form-select">
      					  <option value="0">--All Platform--</option>
        					<c:forEach items="${platformList}" var="p">
            					<option value="${p.key}" ${p.key eq platform?'selected':''}>${p.value}</option>
        							</c:forEach>
   						 </select>
					</div>
					<div class="form-group col-3">
    					<label>Validation Status <span class="required">*</span></label>
    					<select name="status" id="status" class="form-select">
      					  <option value="All">--All Status--</option>
      					  <c:forEach items="${statusList}" var="p">
            				<option value="${p.key}" ${p.key eq status?'selected':''}>${p.value}</option>
        				  </c:forEach>
   						 </select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2">
    					<label>From Date <span class="required">*</span></label>
    					<input type="date" name="userdate" id="userdate" class="form-control activity" style="width: 100%;" value="${fromdate}"/>
					</div>
					<div class="col-md-2">
    					<label>To Date <span class="required">*</span></label>
    					<input type="date" name="userdateto" id="userdateto" class="form-control activity" style="width: 100%;" value="${todate}"/>
					</div>
					
					<div class="col-md-2 d-flex align-items-end">
						<button type="button" id="submitBtn" class="btn btn-primary px-5">Get</button>
					</div>
				</div><br>
				<div class="nav-item text-left mb-2">
    				<label class="me-2">Order By</label>
    				<select name="orderBy" id="orderBy" class="form-select d-inline-block w-auto" onchange="submitOrderBy()">
        				<option value="">--Default--</option>
        				<option value="viewsAsc" ${orderBy eq 'viewsAsc' ? 'selected' : ''}>Views (Low -> High)</option>
       		 			<option value="viewsDesc" ${orderBy eq 'viewsDesc' ? 'selected' : ''}>Views (High -> Low)</option>
        				<option value="likesAsc" ${orderBy eq 'likesAsc' ? 'selected' : ''}>Likes (Low -> High)</option>
        				<option value="likesDesc" ${orderBy eq 'likesDesc' ? 'selected' : ''}>Likes (High -> Low)</option>
    				</select>
				</div>
				<br>
				<div class="nav-item text-left mb-2">
				<c:if test="${wmListSize > 0}">
					<button type="button" name="exportExcel" id="exportExcel" class="btn btn-info" onclick="downloadExcel('${stName}','${distName}','${mediaName}')">Excel</button>
					<button type="button" name="exportPDF" id="exportPDF" class="btn btn-info" onclick="downloadPDF('${stName}','${distName}','${mediaName}')">PDF</button>
				</c:if>
				<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
				</div>
			</form>
			

			<table class="table table-bordered table-striped" id="stWMR">
				<thead>
					<tr>
						<th  style="text-align: center; vertical-align: middle;">S.No.</th>
						<th  style="text-align: center; vertical-align: middle;">State Name</th>
						<th  style="text-align: center; vertical-align: middle;">District Name</th>
						<th  style="text-align: center; vertical-align: middle;">Registration Number</th>
						<th  style="text-align: center; vertical-align: middle;">Name</th>
						<th  style="text-align: center; vertical-align: middle;">Platform</th>
						<th  style="text-align: center; vertical-align: middle;">Link</th>
						<th style="text-align: center; vertical-align: middle;">Number of Views</th>
						<th  style="text-align: center; vertical-align: middle;">Number of Likes</th>
						<th  style="text-align: center; vertical-align: middle;">Number of Comments</th>
						<th  style="text-align: center; vertical-align: middle;">Created Date</th>
						<th  style="text-align: center; vertical-align: middle;">Updated Date</th>
						<th  style="text-align: center; vertical-align: middle;">Status</th>
<!-- 						<th  style="text-align: center; vertical-align: middle;">View Details</th> -->
						
					</tr>
<tr>
                            <th>1</th>
                            <th>2</th>
                            <th>3</th>
                            <th>4</th>
                            <th>5</th>
                            <th>6</th>
                            <th>7</th>
                            <th>8</th>
                            <th>9</th>
                            <th>10</th>
                            <th>11</th>
                             <th>12</th>
                            <th>13</th>
<!--                             <th>14</th> -->
                        </tr>
				</thead>

				<tbody id="tbodyWMSocialMediaRpt">
				<c:if test="${wmListSize > 0}">
    <c:forEach items="${wmList}" var="dt" varStatus="s">
        <tr>
        	<td class="text-left">${s.index + 1}</td>
			<td class="text-left">${dt.stname}</td>
            <td class="text-left">${dt.distname}</td>
            <td class="text-left">${dt.reg_no}</td>
            <td class="text-left">${dt.reg_name}</td>
           <td class="text-center">
    <c:set var="media" value="${dt.platform}" />

    <c:choose>
        <c:when test="${fn:contains(media,'Facebook')}">
            <span class="facebook">${dt.platform}</span>
        </c:when>

        <c:when test="${fn:contains(media,'Instagram')}">
            <span class="instagram">${dt.platform}</span>
        </c:when>

        <c:when test="${fn:contains(media,'YouTube')}">
            <span class="youtube">${dt.platform}</span>
        </c:when>

        <c:when test="${fn:contains(media,'Twitter')}">
            <span class="twitter">${dt.platform}</span>
        </c:when>

        <c:when test="${fn:contains(media,'Linkedin')}">
            <span class="linkedin">${dt.platform}</span>
        </c:when>

        <c:otherwise>
            <span>${dt.platform}</span>
        </c:otherwise>
    </c:choose>
</td>

            
<td class="text-center">
    <c:if test="${not empty dt.media_url}">
        <a href="${dt.media_url}" target="_blank" title="View Media">
            <i class="fas fa-eye fa-lg text-primary"></i>
        </a>
    </c:if>
    <c:if test="${empty dt.media_url}">
        N/A
    </c:if>
</td>
            
            <td class="text-end">${dt.no_of_views}</td>
            <td class="text-end">${dt.no_of_likes}</td>
            <td class="text-end">${dt.no_of_comments}</td>
            <td class="text-end"><fmt:formatDate value="${dt.created_date}" pattern="dd-MM-yyyy"/></td>
            <td class="text-end"><fmt:formatDate value="${dt.updated_date}" pattern="dd-MM-yyyy"/></td>
            <td class="text-end">
            <c:choose>
    			<c:when test="${dt.status == 'Valid'}">
        			<i class="fa-solid fa-check-circle" style="color:green;"></i>
    			</c:when>
    			<c:when test="${dt.status == 'Invalid'}">
       	 			<i class="fa-solid fa-times-circle" style="color:red;"></i>
    			</c:when>
    			<c:otherwise>
        			<i class="fa-solid fa-clock" style="color:orange;"></i>
    			</c:otherwise>
			</c:choose>
			</td>
<!--             <td></td> -->
        </tr>
    </c:forEach>
    </c:if>

    <c:if test="${wmListSize eq 0}">
        <tr>
            <td colspan="13" class="text-center text-danger">
                No records found
            </td>
        </tr>
    </c:if>
</tbody>

			</table>

	</div>

	<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>

</body>
</html>
