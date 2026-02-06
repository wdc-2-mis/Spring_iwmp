<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report WP1 - State-wise Watershed Punarutthan Details</title>

<html>
<script type="text/javascript">

function downloadPDF(){
		document.getPunarutthan.action="downloadPDFStPunarutthanRpt";
		document.getPunarutthan.method="post";
		document.getPunarutthan.submit();
}

function exportExcel(){
		document.getPunarutthan.action="downloadExcelStPunarutthanRpt";
		document.getPunarutthan.method="post";
		document.getPunarutthan.submit();
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
        <h5>Report WP1 - State-wise Watershed Punarutthan Details</h5>
    </div>
    <br>
    <div class ="card">
		<div class="row">
			<div class="col-1" ></div>
			<div class="col-10">
			<br>
    <c:if test="${not empty punarutthanRptStDataList}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelStPunarutthanRpt" name="getPunarutthan" id="getPunarutthan" method="post">
        
                <table class="table" id="stPunarutthan" >
                    <thead>
                        <tr>
                            <th rowspan="3" class="text-center">S.No.</th>
                            <th rowspan="3" class="text-center">State Name</th>
                            <th rowspan="3" class="text-center">Total number of structures identified for maintenance</th>
                            <th colspan="4" class="text-center">Planning</th>
                            
                            <th rowspan="3" class="text-center">Total number of structures where maintenance work Implemented</th>
                            <th rowspan="3" class="text-center">Total number of structures where maintenance work Not Implemented <br> (3-8)</th>
                            <th colspan="4" class="text-center">Implementation</th>
                        </tr>
                        <tr>
                        	<th colspan="4" class="text-center">Estimated cost (Rs in Lakhs) for maintenance works</th>
                        	<th colspan="4" class="text-center">Expenditure (Rs in Lakhs) for maintenance works</th>
                        </tr>
                        <tr>
                        	<th class="text-center">Total Estimated cost</th>
                            <th class="text-center">Cost from WDF</th>
                            <th class="text-center">Cost from VB G RAM G/MGNREGA</th>
                            <th class="text-center">Cost from Other source (if any)</th>
                            
                            <th class="text-center">Total Expenditure (11+12+13)</th>
                            <th class="text-center">Expenditure from WDF</th>
                            <th class="text-center">Expenditure from VB G RAM G/MGNREGA</th>
                            <th class="text-center">Expenditure from Other source <br> (if any)</th>
                        </tr>
                        <tr>
                            <th class="text-center">1</th>
                            <th class="text-center">2</th>
                            <th class="text-center">3</th>
                            <th class="text-center">4</th> 
                            <th class="text-center">5</th>
                            <th class="text-center">6</th>
                            <th class="text-center">7</th>
                            <th class="text-center">8</th>
                            <th class="text-center">9</th>
                            <th class="text-center">10</th>
                            <th class="text-center">11</th>
                            <th class="text-center">12</th>
                            <th class="text-center">13</th>
                        </tr>
                    </thead>
                      <tbody id="tbodyStPunarutthan">
						<c:forEach items="${punarutthanRptStDataList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
<%-- 								<td class="text-left"><c:out value="${dt.stname}" /></td> --%>
								<td><a href = "getDistPunarutthanReport?stcd=${dt.st_code}&stName=${dt.stname}"><c:out value='${dt.stname}'/></a></td>
 								<td class="text-right"><c:out value="${dt.plan_work}" /></td>
								<td class="text-right"><c:out value="${dt.totalcost}" /></td>
								<td class="text-right"><c:out value="${dt.wdf_cost}" /></td>
								<td class="text-right"><c:out value="${dt.mgnrega_cost}" /></td>
								<td class="text-right"><c:out value="${dt.other_cost}" /></td>
								<td class="text-right"><c:out value="${dt.impl_work}" /></td>
								<td class="text-right"><c:out value="${dt.not_impl}" /></td>
								<td class="text-right"><c:out value="${dt.totalexp}" /></td>
								<td class="text-right"><c:out value="${dt.wdf_exp}" /></td>
								<td class="text-right"><c:out value="${dt.mgnrega_exp}" /></td>
								<td class="text-right"><c:out value="${dt.other_exp}" /></td>
							</tr>
							
	 						<c:set var="totPlan" value="${totPlan + dt.plan_work}" />
							<c:set var="totCost" value="${totCost + dt.totalcost}" />
							<c:set var="totWdfCost" value="${totWdfCost + dt.wdf_cost}" />
							<c:set var="totMgnregaCost" value="${totMgnregaCost + dt.mgnrega_cost}" />
							<c:set var="totOtherCost" value="${totOtherCost + dt.other_cost}" />
							<c:set var="totImpl" value="${totImpl + dt.impl_work}" />
							<c:set var="totNotImpl" value="${totNotImpl + dt.not_impl}" />
							<c:set var="totExp" value="${totExp + dt.totalexp}" />
							<c:set var="totWdfExp" value="${totWdfExp + dt.wdf_exp}" />
							<c:set var="totMgnregaExp" value="${totMgnregaExp + dt.mgnrega_exp}" />
							<c:set var="totOtherExp" value="${totOtherExp + dt.other_exp}" />
							
						</c:forEach>
						<c:if test="${punarutthanRptStDataListSize>0}">
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPlan}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totWdfCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMgnregaCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOtherCost}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totImpl}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totNotImpl}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totExp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totWdfExp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMgnregaExp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOtherExp}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${punarutthanRptStDataListSize==0}">
							<tr>
								<td align="center" colspan="13" class="required" style="color: red;"><b>Data Not Found</b></td>
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
