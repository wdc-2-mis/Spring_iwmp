<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE9 - State-wise Mid Term Project Evaluation</title>

<html>
<script type="text/javascript">

function downloadPDF(){
		document.getPEDetails.action="downloadPDFStMidProjEvlWorkDetails";
		document.getPEDetails.method="post";
		document.getPEDetails.submit();
}

function exportExcel(){
		document.getPEDetails.action="downloadExcelStMidProjEvlWorkDetails";
		document.getPEDetails.method="post";
		document.getPEDetails.submit();
}

</script>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h4 style="text-decoration:underline;">Report PE9 - State-wise Mid Term Project Evaluation of Geotagged Work Details</h4>
    </div>
    <br>
    <div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">
			<br>
    <c:if test="${not empty stateMidPrjEvlWrkDetailsList}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelStMidProjEvlWrkDetails" name="getPEDetails" id="getPEDetails" method="post">
        
                <table class="table" id="stMidPE" >
                    <thead>
                        <tr>
                            <th rowspan="2" class="text-center">S.No.</th>
                            <th rowspan="2" class="text-center">State Name</th>
                            <th rowspan="2" class="text-center">Total Projects</th>
                            <th colspan="3" class="text-center">Total Works</th>
                            <th rowspan="2" class="text-center">Total Area of Shape Files</th>
                            <th rowspan="2" class="text-center">Total No. of Geotagged Works</th>
                        </tr>
                        <tr>
                            <th class="text-center">Created</th>
                            <th class="text-center">Ongoing</th>
                            <th class="text-center">Completed</th>
                        </tr>
                        <tr>
                            <c:forEach var="i" begin="1" end="8">
                				<th class="text-center">${i}</th>
            				</c:forEach>
                        </tr>
                    </thead>
                      <tbody id="tbodyStMidProjEvolRpt">
						<c:forEach items="${stateMidPrjEvlWrkDetailsList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
<%-- 								<td class="text-left"><c:out value="${dt.st_name}" /></td> --%>
								<td><a href = "distMidProjEvlWorkDetailsRpt?stcd=${dt.st_code}&stName=${dt.st_name}"><c:out value='${dt.st_name}'/></a></td>
								<td class="text-right"><c:out value="${dt.total_project}" /></td>
								<td class="text-right"><c:out value="${dt.created_work}" /></td>
								<td class="text-right"><c:out value="${dt.ongoing_work}" /></td>
								<td class="text-right"><c:out value="${dt.completed_work}" /></td>
								<td class="text-right"><c:out value="${dt.shape_file_area}" /></td>
								<td class="text-right"><c:out value="${dt.geo_tagg_work}" /></td>
							</tr>
							
							<c:set var="totProj" value="${totProj + dt.total_project}" />
							<c:set var="totCreated" value="${totCreated + dt.created_work}" />
							<c:set var="totOngoing" value="${totOngoing + dt.ongoing_work}" />
							<c:set var="totCompleted" value="${totCompleted + dt.completed_work}" />
							<c:set var="totShape" value="${totShape + dt.shape_file_area}" />
							<c:set var="totGeotag" value="${totGeotag + dt.geo_tagg_work}" />
							
						</c:forEach>
						<c:if test="${stateMidPrjEvlWrkDetailsListSize>0}">
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totProj}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCreated}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOngoing}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCompleted}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totShape}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGeotag}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${stateMidPrjEvlWrkDetailsListSize==0}">
							<tr>
								<td align="center" colspan="8" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
            
    </form>
    </div>
    </div>
    </div>
</div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>
