<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE11 - District-wise Mid Term Project Evaluation</title>

<html>
<script type="text/javascript">

function downloadPDF(stcd, stName){
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	document.getPEDetails.action="downloadPDFDistMidProjEvlWorkDetails";
	document.getPEDetails.method="post";
	document.getPEDetails.submit();
}

function exportExcel(stcd, stName){
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	document.getPEDetails.action="downloadExcelDistMidProjEvlWorkDetails";
	document.getPEDetails.method="post";
	document.getPEDetails.submit();
}

</script>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h5>Report PE11 - District-wise Mid Term Project Evaluation of Geotagged Work Details</h5>
    </div>
    <br>
    <div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">
			<br>
    <c:if test="${not empty distMidPrjEvlWrkDetailsList}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${stName}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}', '${stName}')" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelDistMidProjEvlWorkDetails" name="getPEDetails" id="getPEDetails" method="post">
        
        	<input type="hidden" name="stcd" id="stcd" value="" />
			<input type="hidden" name="stName" id="stName" value="" />
		
                <table class="table" id="distMidPE" >
                    <thead>
                    	<tr>
                    		<th colspan="8">State Name : ${stName}</th>
                    	</tr>
                        <tr>
                            <th rowspan="2" class="text-center">S.No.</th>
                            <th rowspan="2" class="text-center">District Name</th>
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
                      <tbody id="tbodyDistMidProjEvolRpt">
						<c:forEach items="${distMidPrjEvlWrkDetailsList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td align="left">
								<c:choose>
                                   <c:when test="${dt.completed_work > 0}">
                                   <a href="projMidProjEvolWorkDtlRpt?distcd=${dt.dcode}&dName=${dt.distname}&stName=${stName}">
                                   <c:out value="${dt.distname}"/>
                                </a>
                                   </c:when>
                              <c:otherwise>
                                <c:out value="${dt.distname}"/>
                              </c:otherwise>
                              </c:choose>
                              </td>
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
						<c:if test="${distMidPrjEvlWrkDetailsListSize>0}">
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
						<c:if test="${distMidPrjEvlWrkDetailsListSize==0}">
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
