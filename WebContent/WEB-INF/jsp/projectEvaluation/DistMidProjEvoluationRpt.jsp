<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE1 - District-wise Mid Term Project Evaluation</title>

<html>
<script type="text/javascript">

function downloadPDF(stcd, stName){
		document.getElementById("stcd").value=stcd;
		document.getElementById("stName").value=stName;
		document.getPEDetails.action="downloadPDFDistMidProjEvoluation";
		document.getPEDetails.method="post";
		document.getPEDetails.submit();
}

function exportExcel(stcd, stName){
		document.getElementById("stcd").value=stcd;
		document.getElementById("stName").value=stName;
		document.getPEDetails.action="downloadExcelDistMidProjEvoluation";
		document.getPEDetails.method="post";
		document.getPEDetails.submit();
}

</script>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h5>Report PE1 - District-wise Mid Term Project Evaluation Entry Status</h5>
    </div>
     <br>
    <div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">
			<br>
    <c:if test="${not empty distMidPrjEvlList}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${stName}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}', '${stName}')" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelDistMidProjEvoluation" name="getPEDetails" id="getPEDetails" method="post">
        
        <input type="hidden" name="stcd" id="stcd" value="" />
		<input type="hidden" name="stName" id="stName" value="" />
	
        
                <table class="table" id="distMidPE">
                    <thead>
                    	<tr>
                    		<th colspan="13">State Name : ${stName}</th>
                    	</tr>
                        <tr>
                            <th rowspan="2" class="text-center">S.No.</th>
                            <th rowspan="2" class="text-center">District Name</th>
                            <th rowspan="2" class="text-center">Project</th>
                            <th rowspan="2" class="text-center">Block</th>
                            <th rowspan="2" class="text-center">Gram Panchayat</th>
                            <th rowspan="2" class="text-center">Village</th>
                            <th colspan="3" class="text-center">Project Evaluation Status</th>
                            <th colspan="4" class="text-center">Project Grade</th>
                        </tr>
                        <tr>
                        	<th class="text-center">Completed</th>
                            <th class="text-center">Under Process</th>
                            <th class="text-center">Not Entered</th>
                            <th class="text-center">Excellent</th>
                            <th class="text-center">Very Good</th>
                            <th class="text-center">Satisfactory</th>
                            <th class="text-center">Average</th>
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
                      <tbody id="tbodyDistMidProjEvolRpt">
						<c:forEach items="${distMidPrjEvlList}" var="dt" varStatus="sno">
							<tr> 
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td class="text-left"><c:out value="${dt.distname}" /></td>
								<td class="text-right"><c:out value="${dt.total_project}" /></td>
								<td class="text-right"><c:out value="${dt.block}" /></td>
								<td class="text-right"><c:out value="${dt.gp}" /></td>
								<td class="text-right"><c:out value="${dt.village}" /></td>
								<td align="right">
                                <c:choose>
                                   <c:when test="${dt.completed > 0}">
                                   <a href="projMidProjEvoluationRpt?distcd=${dt.dcode}&dName=${dt.distname}&stName=${stName}">
                                   <c:out value="${dt.completed}"/>
                                </a>
                                   </c:when>
                              <c:otherwise>
                                <c:out value="${dt.completed}"/>
                              </c:otherwise>
                              </c:choose>
                              </td>
								<td class="text-right"><c:out value="${dt.process}" /></td>
								<td class="text-right"><c:out value="${dt.not_entered}" /></td>
								<td class="text-right"><c:out value="${dt.grade_e}" /></td>
								<td class="text-right"><c:out value="${dt.grade_g}" /></td>
								<td class="text-right"><c:out value="${dt.grade_s}" /></td>
								<td class="text-right"><c:out value="${dt.grade_a}" /></td>
							</tr>
							
							<c:set var="totProj" value="${totProj + dt.total_project}" />
							<c:set var="totBlk" value="${totBlk + dt.block}" />
							<c:set var="totGP" value="${totGP + dt.gp}" />
							<c:set var="totVlg" value="${totVlg + dt.village}" />
							<c:set var="totComplete" value="${totComplete + dt.completed}" />
							<c:set var="totProcess" value="${totProcess + dt.process}" />
							<c:set var="totNotEntr" value="${totNotEntr + dt.not_entered}" /> 
							<c:set var="totGradeE" value="${totGradeE + dt.grade_e}" />
							<c:set var="totGradeG" value="${totGradeG + dt.grade_g}" />
							<c:set var="totGradeS" value="${totGradeS + dt.grade_s}" />
							<c:set var="totGradeA" value="${totGradeA + dt.grade_a}" />
							
						</c:forEach>
						<c:if test="${distMidPrjEvlListSize>0}">
 							<tr> 
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totProj}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totBlk}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGP}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totVlg}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totComplete}" /></b></td> 
								<td align="right" class="table-primary"><b><c:out value="${totProcess}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totNotEntr}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGradeE}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGradeG}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGradeS}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGradeA}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${distMidPrjEvlListSize==0}">
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
