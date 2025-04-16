<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE1 - State-wise Mid Term Project Evaluation</title>

<html>
<script type="text/javascript">

function downloadPDF(){
		document.getPEDetails.action="downloadPDFStMidProjEvoluation";
		document.getPEDetails.method="post";
		document.getPEDetails.submit();
}

function exportExcel(){
		document.getPEDetails.action="downloadExcelStMidProjEvoluation";
		document.getPEDetails.method="post";
		document.getPEDetails.submit();
}

</script>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h4 style="text-decoration:underline;">Report PE1 - State-wise Mid Term Project Evaluation Entry Status</h4>
    </div>
    
    <c:if test="${not empty stateMidPrjEvlList}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelStMidProjEvoluation" name="getPEDetails" id="getPEDetails" method="post">
        <div class="form-row">
            <div class="form-group col-12">
                <table class="table" id="stMidPE">
                    <thead>
                        <tr>
                            <th rowspan="2" class="text-center">S.No.</th>
                            <th rowspan="2" class="text-center">State Name</th>
                            <th rowspan="2" class="text-center">Project</th>
                            <th rowspan="2" class="text-center">District</th>
                            <th rowspan="2" class="text-center">Block</th>
                            <th rowspan="2" class="text-center">Gram Panchayat</th>
                            <th rowspan="2" class="text-center">Village</th>
                            <th colspan="3" class="text-center">Project Evaluation Status</th>
                        </tr>
                        <tr>
                        	<th class="text-center">Completed</th>
                            <th class="text-center">Under Process</th>
                            <th class="text-center">Not Entered</th>
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
                        </tr>
                    </thead>
                      <tbody id="tbodyStMidProjEvolRpt">
						<c:forEach items="${stateMidPrjEvlList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td class="text-left"><c:out value="${dt.st_name}" /></td>
								<td class="text-right"><c:out value="${dt.total_project}" /></td>
								<td class="text-right"><c:out value="${dt.district}" /></td>
								<td class="text-right"><c:out value="${dt.block}" /></td>
								<td class="text-right"><c:out value="${dt.gp}" /></td>
								<td class="text-right"><c:out value="${dt.village}" /></td>
								<td class="text-right"><c:out value="${dt.completed}" /></td>
								<td class="text-right"><c:out value="${dt.process}" /></td>
								<td class="text-right"><c:out value="${dt.not_entered}" /></td>
							</tr>
							
							<c:set var="totProj" value="${totProj + dt.total_project}" />
							<c:set var="totDist" value="${totDist + dt.district}" />
							<c:set var="totBlk" value="${totBlk + dt.block}" />
							<c:set var="totGP" value="${totGP + dt.gp}" />
							<c:set var="totVlg" value="${totVlg + dt.village}" />
							<c:set var="totComplete" value="${totComplete + dt.completed}" />
							<c:set var="totProcess" value="${totProcess + dt.process}" />
							<c:set var="totNotEntr" value="${totNotEntr + dt.not_entered}" />
							
						</c:forEach>
						<c:if test="${stateMidPrjEvlListSize>0}">
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totProj}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totDist}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totBlk}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGP}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totVlg}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totComplete}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totProcess}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totNotEntr}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${stateMidPrjEvlListSize==0}">
							<tr>
								<td align="center" colspan="10" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>
