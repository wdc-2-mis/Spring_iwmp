<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/mahotsavheader.jspf" %>




<script>
    function downloadPDF() {
        document.getRpt.action = "downloadWMSocialMediaReportPDF";
        document.getRpt.method = "post";
        document.getRpt.submit();
    }
    
    function downloadExcel() {
        document.getRpt.action = "downloadExcelWMSocialMediaReport";
        document.getRpt.method = "post";
        document.getRpt.submit();
    }
</script>

<div class="container mt-5">

    <form name="getRpt">

    <c:if test="${not empty WMList}">
        <div class="card shadow mt-4 p-4">
        
            <h4 class="text-center text-primary mb-4">State-wise Social Media Competition</h4>

            <div class="nav-item text-left mb-2">
            <button type="button" name="exportExcel" id="exportExcel" class="btn pdf-gradient" onclick="downloadExcel()"> Excel </button>
            <button type="button"  name="exportPDF" id="exportPDF" class="btn pdf-gradient" onclick="downloadPDF()">PDF</button>
            
            <p align="right">  Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
            </div>

            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>S.No.</th>
                            <th>State Name</th>
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

                        <c:forEach items="${WMList}" var="row" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>
                                    <a href="getDistWMSocialMediaReport?stcd=${row.stcode}&stName=${row.stname}">
                                        <c:out value="${row.stname}" />
                                    </a>
                                </td>
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

    <c:if test="${empty WMList}">
        <div class="alert alert-info mt-4 text-center">
            No data found for selected filters.
        </div>
    </c:if>

    </form> 

</div>

<br>
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/mahotsavfooter.jspf"%>
</footer>
