<!DOCTYPE html>
<html>
<head>

<title>Report WM5 - Watershed Mahotsav Social Media Analysis</title>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jspf/mahotsavReportheader.jspf"%>
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
                $('#block').html('<option value="0">--All Block--</option>');
                $('#village').html('<option value="0">--All Village--</option>');
            }
        });
    });

    // Submit report
    $('#submitBtn').on('click', function() {
        document.mohotsavRpt.action = "wmSocialMediaAnalysisReport";
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
// $('#orderBy').change(function(){
//     $('#submitBtn').click(); // triggers form submit with selected orderBy
// });

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

		<div class="card shadow mt-1 p-5">

			<div class="offset-md-3 col-6 formheading" style="text-align: center;">
				<h4 class="text-center text-primary mb-4 text-decoration-underline">Report WM5 - Watershed Mahotsav Social Media Analysis</h4>
			</div>


			<form name="mohotsavRpt" id="mohotsavReport" action="wmSocialMediaAnalysisReport" method="post">
			
			<input type="hidden" name="stName" id="stName" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
			<input type="hidden" name="media" id="media" value="" />

				<div class="row mb-3">

					<div class="col-md-2">
						<label>State <span class="required">*</span></label> <select
							name="state" id="state" class="form-select">
							<option value="0">--All State--</option>
							<c:forEach items="${stateList}" var="lists">
								<option value="${lists.key}"
									${lists.key eq state ? 'selected' : ''}>${lists.value}</option>
							</c:forEach>
						</select>
						<div id="stateError" class="error-text">Please select state</div>
					</div>


					<div class="col-md-2">
						<label>District <span class="required">*</span></label> <select
							name="district" id="district" class="form-select">
							<option value="0">--All District--</option>
							<c:forEach items="${districtList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq district ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
						<div id="districtError" class="error-text">Please select
							district</div>
					</div>

					<div class="col-md-2">
    					<label>Platform <span class="required">*</span></label>
    					<select name="platform" id="platform" class="form-select">
      					  <option value="0">--All Platform--</option>
        					<c:forEach items="${platformList}" var="p">
            					<option value="${p.key}">${p.value}</option>
        							</c:forEach>
   						 </select>
						</div>


					<div class="col-md-2 d-flex align-items-end">
						<button type="button" id="submitBtn" class="btn btn-primary px-5">Get</button>
					</div>
				</div><br>
			</form>
<!-- 			<div class="col-md-2"> -->
<!--     <label>Order By <span class="required">*</span></label> -->
<!--     <select name="orderBy" id="orderBy" class="form-select"> -->
<%--         <option value="views" ${orderBy eq 'views' ? 'selected' : ''}>Views</option> --%>
<%--         <option value="likes" ${orderBy eq 'likes' ? 'selected' : ''}>Likes</option> --%>
<!--     </select> -->
<!-- </div> -->
			<div class="nav-item text-left mb-2">
<%-- 				<c:if test="${not empty wmList}"> --%>
<!-- 					<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()">Excel</button> -->
<!-- 					<button type="button" name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button> -->
<%-- 				</c:if> --%>
				<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
			</div>

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
						<th style="text-align: center; vertical-align: middle;">No. of Views</th>
						<th  style="text-align: center; vertical-align: middle;">No. of Likes</th>
						<th  style="text-align: center; vertical-align: middle;">No. of Comments</th>
						<th  style="text-align: center; vertical-align: middle;">No. of Shares</th>
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
                        </tr>
				</thead>

				<tbody id="tbodyWMSocialMediaRpt">
    <c:forEach items="${wmList}" var="dt" varStatus="s">
        <tr>
        	<td class="text-left">${s.index + 1}</td>
			<td class="text-left">${dt.stname}</td>
            <td class="text-left">${dt.dist_name}</td>
            <td class="text-left">${dt.user_reg_no}</td>
            <td class="text-left">${dt.reg_name}</td>
           <td class="text-center">
    <c:set var="media" value="${fn:toLowerCase(dt.media_name)}" />

    <c:choose>
        <c:when test="${fn:contains(media,'facebook')}">
            <span class="facebook">${dt.media_name}</span>
        </c:when>

        <c:when test="${fn:contains(media,'instagram')}">
            <span class="instagram">${dt.media_name}</span>
        </c:when>

        <c:when test="${fn:contains(media,'youtube')}">
            <span class="youtube">${dt.media_name}</span>
        </c:when>

        <c:when test="${fn:contains(media,'twitter')}">
            <span class="twitter">${dt.media_name}</span>
        </c:when>

        <c:when test="${fn:contains(media,'linkedin')}">
            <span class="linkedin">${dt.media_name}</span>
        </c:when>

        <c:otherwise>
            <span>${dt.media_name}</span>
        </c:otherwise>
    </c:choose>
</td>

            
<%--             <td class="text-left">${dt.media_name}</td> --%>
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


            
<%--             <td class="text-left">${dt.media_url}</td> --%>
            <td class="text-end">${dt.no_of_views}</td>
            <td class="text-end">${dt.no_of_likes}</td>
            <td class="text-end">${dt.no_of_comments}</td>
            <td class="text-end">${dt.no_of_shares}</td>
        </tr>
    </c:forEach>

    <c:if test="${empty wmListSize}">
        <tr>
            <td colspan="11" class="text-center text-danger">
                No records found
            </td>
        </tr>
    </c:if>
</tbody>

			</table>

		</div>
	</div>

	<footer class="text-center">
		<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
	</footer>

</body>
</html>
