<%@ page contentType="text/html;charset=UTF-8"%>



<!DOCTYPE html>
<html>
<head>

<title>Report WM4 - Watershed Mahotsav Social Media</title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavReportheader.jspf"%>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {

    // State → District
    $('#state').on('change', function() {
        var stateId = $(this).val();
        $.ajax({
            url: 'getDistrictsByState',
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

    // District → Block
    $('#district').on('change', function() {
        var stateId = $('#state').val();
        var districtId = $(this).val();
        $.ajax({
            url: 'getBlocksByDistrict',
            type: 'POST',
            data: { state: stateId, district: districtId },
            success: function(data) {
                $('#block').empty().append('<option value="0">--All Block--</option>');
                $.each(data, function(key, value) {
                    $('#block').append('<option value="'+value+'">'+key+'</option>');
                });
                $('#village').html('<option value="0">--All Village--</option>');
            }
        });
    });

    // Block → Village
    $('#block').on('change', function() {
        var blockId = $(this).val();
        $.ajax({
            url: 'getVillagesByBlock',
            type: 'POST',
            data: { block: blockId },
            success: function(data) {
                $('#village').empty().append('<option value="0">--All Village--</option>');
                $.each(data, function(key, value) {
                    $('#village').append('<option value="'+value+'">'+key+'</option>');
                });
            }
        });
    });

    // Submit report
    $('#submitBtn').on('click', function() {
        document.mohotsavRpt.action = "wmSocailMediaReport";
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

function downloadPDF(){
		document.mohotsavRpt.action="downloadPDFStWMInauguration";
		document.mohotsavRpt.method="post";
		document.mohotsavRpt.submit();
}

function exportExcel(){
		document.mohotsavRpt.action="downloadExcelStWMInauguration";
		document.mohotsavRpt.method="post";
		document.mohotsavRpt.submit();
}

</script>

<style>
    /* Add margin above the DataTables controls */
    div.dataTables_wrapper div.dataTables_length,
    div.dataTables_wrapper div.dataTables_filter {
        margin-bottom: 15px;   /* gap between controls and table */
    }

    /* Add margin above the pagination/info section */
    div.dataTables_wrapper div.dataTables_info,
    div.dataTables_wrapper div.dataTables_paginate {
        margin-top: 15px;      /* gap between table and pagination */
    }
</style>

<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>
</head>
<body>
	<div class="maindiv">

		<div class="card shadow mt-1 p-5">

			<div class="offset-md-3 col-6 formheading" style="text-align: center;">
				<h4 class="text-center text-primary mb-4"><u>Report WM4 - Watershed Mahotsav Social Media</u></h4>
			</div>

			<div class="nav-item text-left mb-2">
				<c:if test="${not empty stateWMInaugurationList1}">
					<button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="exportExcel()">Excel</button>
					<button type="button" name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button>
				</c:if>
				<p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
			</div>
			
			<form name="mohotsavRpt" id="mohotsavReport" action="wmSocailMediaReport" method="post">
			
				
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
						<label>Block <span class="required">*</span></label> <select
							name="block" id="block" class="form-select">
							<option value="0">--All Block--</option>
							<c:forEach items="${blockList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq blkd ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
						<div id="blockError" class="error-text">Please select block</div>
					</div>

					<div class="col-md-2">
						<label>Village <span class="required">*</span></label> <select
							name="village" id="village" class="form-select">
							<option value="0">--All Village--</option>
							<c:forEach items="${villageList}" var="lists">
								<option value="${lists.value}"
									${lists.value eq vlg ? 'selected' : ''}>${lists.key}</option>
							</c:forEach>
						</select>
						<div id="villageError" class="error-text">Please select
							village</div>
					</div>
					<div class="col-md-2 d-flex align-items-end">
						<button type="button" id="submitBtn" class="btn btn-primary px-5">Get</button>
				</div></div>
			</form>

			<table class="table table-bordered table-striped" id="stWMR" >
                    <thead>
                     <tr>
						<th style="text-align:center; vertical-align: middle;">S.No.</th>
						<th style="text-align:center; vertical-align: middle;">Registration Number</th>
						<th style="text-align:center; vertical-align: middle;">Name</th>
						<th style="text-align:center; vertical-align: middle;">Contact Number</th>
						<th style="text-align:center; vertical-align: middle;">List of Uploaded Videos</th>
					</tr>

					<tr>
						<% for (int i = 1; i <= 5; i++) { %>
						<th class="text-center"><%= i %></th>
						<% } %>
					</tr>
                    </thead>
                    
                    <tbody id="tbodyWMSocailMediaRpt">
                    
                    	<c:set var="regNo" value="" />
                    	<c:set var="name" value="" />
                    	<c:set var="phno" value="" />
                    	<c:set var="url" value="" />
                    	
						<c:forEach items="${stateWMSocailMediaList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td>
									<c:if test="${dt.user_reg_no ne regNo}">
        								<c:out value="${dt.user_reg_no}" />
   	 								</c:if>
								</td>
								<td>
									<c:if test="${dt.reg_name ne name}">
        								<c:out value="${dt.reg_name}" />
   	 								</c:if>
								</td>
								<td class="text-center">
									<c:if test="${dt.phno ne phno}">
        								<c:out value="${dt.phno}" />
   	 								</c:if>
								</td>
								<td class="text-center">
									<c:if test="${dt.media_url ne url}">
        								<a href="${dt.media_url}" target="_blank">${dt.media_url}</a>
   	 								</c:if>
								</td>
							
							</tr>
							
							<c:set var="regNo" value="${dt.user_reg_no}" />
							<c:set var="name" value="${dt.reg_name}" />
							<c:set var="phno" value="${dt.phno}" />
							<c:set var="url" value="${dt.media_url}" />
							
						</c:forEach>
						
						<c:if test="${stateWMSocailMediaListSize==0}">
							<tr>
								<td align="center" colspan="5" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					<c:if test="${stateWMSocailMediaListSize !=0}">

						<script>
							// Call the function automatically when page loads
							dataAvailableFunction();
						</script>

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
