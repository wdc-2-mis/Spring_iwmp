<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavheader.jspf" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
.required { color: red; margin-left: 3px; }
.error-text { color: red; font-size: 0.85em; display: none; margin-top: 4px; }
body { background: #f8f9fa; font-family: "Segoe UI", Arial, sans-serif; }
.form-label { font-weight: 600; color: #0d6efd; }
.table th { background-color: #0d6efd; color: white; text-align: center; }
.table td { vertical-align: middle; }
</style>

<script>
$(document).ready(function(){

    // Load districts dynamically
    $("#state").change(function(){
        var stateCode = $(this).val();
        if(stateCode && stateCode !== "0"){
            $.ajax({
                url: "getDistrictByState",
                type: "GET",
                data: { stateCode: stateCode },
                success: function(data){
                    $("#district").empty();
                    $("#district").append('<option value="0">--ALL--</option>');
                    $.each(data, function(key, value){
                        $("#district").append('<option value="'+key+'">'+value+'</option>');
                    });
                },
                error: function(){
                    alert("Error fetching districts.");
                }
            });
        } else {
            $("#district").empty().append('<option value="0">--ALL--</option>');
        }
    });

    // Handle Get Report button click
    $("#getReportBtn").click(function(){
        var state = $("#state").val();
        var district = $("#district").val();
        // Redirect with parameters
        window.location.href = "getSocialMediaReport?state=" + state + "&district=" + district;
    });
});
</script>

<div class="container mt-5">
    <div class="card shadow p-4">
        <h4 class="text-center text-primary mb-4">Social Media Information Report</h4>

        <form id="reportForm">
            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label">State <span class="required">*</span></label>
                    <select name="state" id="state" class="form-select">
                        <option value="0">--ALL--</option>
                        <c:forEach items="${stateList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq state ? 'selected' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="form-label">District <span class="required">*</span></label>
                    <select name="district" id="district" class="form-select">
                        <option value="0">--ALL--</option>
                        <c:forEach items="${districtList}" var="lists">
                            <option value="${lists.key}" ${lists.key eq district ? 'selected' : ''}>${lists.value}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4 d-flex align-items-end">
                    <button type="button" id="getReportBtn" class="btn btn-primary w-100">
                        Get Report
                    </button>
                </div>
            </div>
        </form>
    </div>

    <!-- ====== LIST / REPORT TABLE ====== -->
    <c:if test="${not empty socialMediaList}">
        <div class="card shadow mt-4 p-4">
            <h5 class="text-primary mb-3">Social Media Records</h5>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>S.No.</th>
                            <th>State Name</th>
                            <th>District Name</th>
                            <th>No. of Facebook</th>
                            <th>No. of YouTube</th>
                            <th>No. of Instagram</th>
                            <th>No. of Twitter</th>
                            <th>No. of Linked In</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${socialMediaList}" var="row" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${row.stname}</td>
                                <td>district</td>
                                <td>${row.no_facebook}</td>
                                <td>${row.no_youtube}</td>
                                <td>${row.no_instagram}</td>
                                <td>${row.no_twitter}</td>
                                <td>${row.no_linkedin}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

    <c:if test="${empty socialMediaList}">
        <div class="alert alert-info mt-4 text-center">
            No data found for selected filters.
        </div>
    </c:if>

</div>

<br>
<footer class="text-center">
    <%-- <%@ include file="/WEB-INF/jspf/mahotsavfooter.jspf" %> --%>
</footer>
