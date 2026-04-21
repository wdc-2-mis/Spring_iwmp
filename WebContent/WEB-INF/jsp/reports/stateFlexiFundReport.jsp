<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>FF1 - State-wise Watershed Flexi Fund Details Report</title>

<html>
<script type="text/javascript">
function downloadPDF(){
    document.getElementById('ffForm').action = "downloadPDFStateWiseFlexiFundRpt";
    document.getElementById('ffForm').method = "post";
    document.getElementById('ffForm').submit();
}

function exportExcel(){
    document.getElementById('ffForm').action = "downloadExcelStateWiseFlexiFundRpt";
    document.getElementById('ffForm').method = "post";
    document.getElementById('ffForm').submit();
}
</script>
<head>
<%
    response.setHeader("Cache-Control", "public, max-age=600");
    response.setHeader("Pragma", "public");
%>
</head>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h5>FF1 - State-wise Watershed Flexi Fund Details Report</h5>
    </div>
    <br>
    <div class="container-fluid">
	<div class="row">
		<div class="col text-left">
                <br>
                <c:if test="${not empty flexiFundReportList}">
                    <button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
                    <button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
                </c:if>
                <p align="right">Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%></p>
   		</div>
   	</div>
   	</div>         
                <form name="ffForm" id="ffForm" method="post">
                    <table class="table table-bordered" id="ffTable">
                        <thead>
                        <tr>
                        <th colspan="20" class="text-right">All Amount is Rs in Lakhs</th>
                        </tr>
                            <tr class="bg-primary text-white">
                                <th rowspan="2" class="text-center">S.No.</th>
                                <th rowspan="2" class="text-center">State Name</th>
                                <th rowspan="2" class="text-center">No. of Projects</th>
                                <th colspan="3" class="text-center">Cactus</th> 
                                <th colspan="3" class="text-center">Spring shed PPR Preparation</th>
                                <th colspan="3" class="text-center">Model Watershed</th>
                                <th colspan="3" class="text-center">Restoration of Waterbodies</th>
                                <th colspan="3" class="text-center">Watershed Janbhagidari Cup (Prize Money)</th>
                                
                                <th rowspan="2" class="text-center">Total Estimated Cost (5+8+11+14+17)</th>
                                <th rowspan="2" class="text-center">Total Expenditure Cost (6+9+12+15+18)</th>
                            </tr>
                            <tr>
                            	<% for(int i = 1; i <= 5; i++) {  %>
            						<th class="text-center">Work</th>
            						<th class="text-center">Estimated Cost</th>
            						<th class="text-center">Expenditure Cost</th>
            					<% } %>
                            </tr>
                            
                            <tr>
                            	<% for(int i = 1; i <= 20; i++) {  %>
            						<th class="text-center"><%= i %></th>
            					<% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${flexiFundReportList}" var="dt" varStatus="sno">
                                <tr>
                                    <td class="text-center"><c:out value="${sno.count}" /></td>
                                    <td class="text-left"><c:out value="${dt.st_name}" /></td>
                                    <td class="text-right"><c:out value="${dt.project}" /></td>
                                    <td class="text-right"><c:out value="${dt.cactus}" /></td>
                                    <td class="text-right"><c:out value="${dt.cactus_est}" /></td>
                                    <td class="text-right"><c:out value="${dt.cactus_exp}" /></td>
                                    <td class="text-right"><c:out value="${dt.springshed}" /></td>
                                    <td class="text-right"><c:out value="${dt.springshed_est}" /></td>
                                    <td class="text-right"><c:out value="${dt.springshed_exp}" /></td>
                                    <td class="text-right"><c:out value="${dt.watershed}" /></td>
                                    <td class="text-right"><c:out value="${dt.watershed_est}" /></td>
                                    <td class="text-right"><c:out value="${dt.watershed_exp}" /></td>
                                    <td class="text-right"><c:out value="${dt.waterbodies}" /></td>
                                    <td class="text-right"><c:out value="${dt.waterbodies_est}" /></td>
                                    <td class="text-right"><c:out value="${dt.waterbodies_exp}" /></td>
                                    <td class="text-right"><c:out value="${dt.janbhagidari}" /></td>
                                    <td class="text-right"><c:out value="${dt.janbhagidari_est}" /></td>
                                    <td class="text-right"><c:out value="${dt.janbhagidari_exp}" /></td>
                                    <td class="text-right"><c:out value="${dt.total_estimation}" /></td>
                                    <td class="text-right"><c:out value="${dt.total_expenditure}" /></td>
                                </tr>
                                
                                <c:set var="totProjects" value="${totProjects + dt.project}" />
                                <c:set var="totCactus" value="${totCactus + dt.cactus}" />
                                <c:set var="totCactusEst" value="${totCactusEst + dt.cactus_est}" />
                                <c:set var="totCactusExp" value="${totCactusExp + dt.cactus_exp}" />
                                <c:set var="totSpringshed" value="${totSpringshed + dt.springshed}" />
                                <c:set var="totSpringshedEst" value="${totSpringshedEst + dt.springshed_est}" />
                                <c:set var="totSpringshedExp" value="${totSpringshedExp + dt.springshed_exp}" />
                                <c:set var="totWatershed" value="${totWatershed + dt.watershed}" />
                                <c:set var="totWatershedEst" value="${totWatershedEst + dt.watershed_est}" />
                                <c:set var="totWatershedExp" value="${totWatershedExp + dt.watershed_exp}" />
                                <c:set var="totWaterbodies" value="${totWaterbodies + dt.waterbodies}" />
                                <c:set var="totWaterbodiesEst" value="${totWaterbodiesEst + dt.waterbodies_est}" />
                                <c:set var="totWaterbodiesExp" value="${totWaterbodiesExp + dt.waterbodies_exp}" />
                                <c:set var="totJanbhagidari" value="${totJanbhagidari + dt.janbhagidari}" />
                                <c:set var="totJanbhagidariEst" value="${totJanbhagidariEst + dt.janbhagidari_est}" />
                                <c:set var="totJanbhagidariExp" value="${totJanbhagidariExp + dt.janbhagidari_exp}" />
                                <c:set var="totEstimation" value="${totEstimation + dt.total_estimation}" />
                                <c:set var="totExpenditure" value="${totExpenditure + dt.total_expenditure}" />
                                
                            </c:forEach>
                            
                            
                            <c:if test="${flexiFundReportListSize > 0}">
                                <tr class="table-primary">
                                    <td colspan="2" align="right"><b>Grand Total</b></td>
                                    <td align="right"><b><c:out value="${totProjects}" /></b></td>
                                    <td align="right"><b><c:out value="${totCactus}" /></b></td>
                                    <td align="right"><b><c:out value="${totCactusEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totCactusExp}" /></b></td>
                                    <td align="right"><b><c:out value="${totSpringshed}" /></b></td>
                                    <td align="right"><b><c:out value="${totSpringshedEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totSpringshedExp}" /></b></td>
                                    <td align="right"><b><c:out value="${totWatershed}" /></b></td>
                                    <td align="right"><b><c:out value="${totWatershedEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totWatershedExp}" /></b></td>
                                    <td align="right"><b><c:out value="${totWaterbodies}" /></b></td>
                                    <td align="right"><b><c:out value="${totWaterbodiesEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totWaterbodiesExp}" /></b></td>
                                    <td align="right"><b><c:out value="${totJanbhagidari}" /></b></td>
                                    <td align="right"><b><c:out value="${totJanbhagidariEst}" /></b></td>
                                    <td align="right"><b><c:out value="${totJanbhagidariExp}" /></b></td>
                                    <td align="right"><b><c:out value="${totEstimation}" /></b></td>
                                    <td align="right"><b><c:out value="${totExpenditure}" /></b></td>
                                </tr>
                            </c:if>
                            
                            
                            <c:if test="${flexiFundReportListSize == 0}">
                                <tr>
                                    <td align="center" colspan="20" class="required" style="color: red;"><b>Data Not Found</b></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </form>
            </div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>