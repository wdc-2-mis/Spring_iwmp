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


<div class="container mt-5">
    <c:if test="${not empty WMDList}">
        <div class="card shadow mt-4 p-4">
            <h4 class="text-center text-primary mb-4">District-wise Social Media Information Report</h4>
            <div class="table-responsive">
            
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>S.No.</th>
                            <th>District Name</th>
                            <th>Total Registered User</th>
                            <th>No. of Facebook</th>
                            <th>No. of YouTube</th>
                            <th>No. of Instagram</th>
                            <th>No. of Twitter</th>
                            <th>No. of Linked In</th>
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
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Initialize totals -->
                        <c:set var="totalRegistered" value="0" scope="page" />
                        <c:set var="totalFacebook" value="0" scope="page" />
                        <c:set var="totalYoutube" value="0" scope="page" />
                        <c:set var="totalInstagram" value="0" scope="page" />
                        <c:set var="totalTwitter" value="0" scope="page" />
                        <c:set var="totalLinkedin" value="0" scope="page" />

                        <c:forEach items="${WMDList}" var="row" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${row.dist_name}</td>
                                <td class="text-end">${row.total_registered_user}</td>
								<td class="text-end">${row.no_facebook}</td>
								<td class="text-end">${row.no_youtube}</td>
								<td class="text-end">${row.no_instagram}</td>
								<td class="text-end">${row.no_twitter}</td>
								<td class="text-end">${row.no_linkedin}</td>

                            </tr>

                            <!-- Sum totals -->
                            <c:set var="totalRegistered" value="${totalRegistered + row.total_registered_user}" />
                            <c:set var="totalFacebook" value="${totalFacebook + row.no_facebook}" />
                            <c:set var="totalYoutube" value="${totalYoutube + row.no_youtube}" />
                            <c:set var="totalInstagram" value="${totalInstagram + row.no_instagram}" />
                            <c:set var="totalTwitter" value="${totalTwitter + row.no_twitter}" />
                            <c:set var="totalLinkedin" value="${totalLinkedin + row.no_linkedin}" />
                        </c:forEach>

                        <!-- Grand total row -->
                        <tr class="table-secondary fw-bold">
                            <td colspan="2" class="text-center">Grand Total</td>
                            <td class="text-end">${totalRegistered}</td>
                            <td class="text-end">${totalFacebook}</td>
                            <td class="text-end">${totalYoutube}</td>
                            <td class="text-end">${totalInstagram}</td>
                            <td class="text-end">${totalTwitter}</td>
                            <td class="text-end">${totalLinkedin}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
    <c:if test="${empty WMDList}">
        <div class="alert alert-info mt-4 text-center">
            No data found for selected filters.
        </div>
    </c:if>
</div>

<br>
<footer class="text-center">
    <%-- <%@ include file="/WEB-INF/jspf/mahotsavfooter.jspf" %> --%>
</footer>
