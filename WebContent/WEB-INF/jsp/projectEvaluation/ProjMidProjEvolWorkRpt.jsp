<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE11 - Project-wise Mid Term Project Evaluation</title>

<html>
<script type="text/javascript">
var stName = "${stName != null ? stName : ''}";

function handleProjectClick(projId) {
    $.ajax({
        url: "checkProjIdExists",
        type: "GET",
        data: { projectId: projId },
        contentType: "application/x-www-form-urlencoded",
        success: function(response) {
            if (response.exists && response.status === "C") {
                var reportdata = "rptdata";
                var url = "getviewcomplete?"
                    + "project=" + response.projId
                    + "&district=" + response.distCode
                    + "&distName=" + encodeURIComponent(response.distName)
                    + "&projName=" + encodeURIComponent(response.projName)
                    + "&finyear=" + response.finYearCode
                    + "&finName=" + encodeURIComponent(response.finYearDesc)
                    + "&month=" + response.monthId
                    + "&monthName=" + encodeURIComponent(response.monthName)
                    + "&reportdata=" + encodeURIComponent(reportdata)
                    + "&stName=" + encodeURIComponent(stName);

                // Load JSP inside iframe and open modal
                document.getElementById("childFrame").src = url;
                document.getElementById("childModal").style.display = "block";
            } else {
                alert("Project not found or not completed.");
            }
        },
        error: function() {
            console.log("Error checking project existence.");
        }
    });
}

function closeChildModal() {
    document.getElementById("childModal").style.display = "none";
    document.getElementById("childFrame").src = "";
}


function downloadPDF(dcode, dName, stName){
    document.getElementById("dcode").value=dcode;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getPEDetails.action="downloadPDFProjMidProjEvolWork";
    document.getPEDetails.method="post";
    document.getPEDetails.submit();
}

function exportExcel(dcode, dName, stName){
    document.getElementById("dcode").value=dcode;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getPEDetails.action="downloadExcelProjMidProjEvolWork";
    document.getPEDetails.method="post";
    document.getPEDetails.submit();
}

</script>
<style>
/* ===== Modal Styling ===== */
.modal1 {
    display: none;
    position: fixed;
    z-index: 2000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.6);
}

.modal1-content {
    background-color: #fff;
    margin: 3% auto;
    border-radius: 8px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.3);
    position: relative;
    width: 90%;
    height: 90%;
}

.close {
    color: #aaa;
    position: absolute;
    top: 10px;
    right: 20px;
    font-size: 28px;
    cursor: pointer;
}
.close:hover {
    color: #000;
}
</style>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h5>Report PE11 - Project-wise Mid Term Project Evaluation of Geotagged Work Details</h5>
    </div>
    <br>
    <div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">
			<br>
    <c:if test="${not empty projMidPrjEvlWorkDtlList}">
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${dcode}', '${dName}', '${stName}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${dcode}', '${dName}', '${stName}')" class="btn btn-info">PDF</button>
	</c:if>
    <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
        
        <form action="downloadExcelDistMidProjEvlWorkDetails" name="getPEDetails" id="getPEDetails" method="post">
        
        	<input type="hidden" name="dcode" id="dcode" value="" />
                    <input type="hidden" name="dName" id="dName" value="" />
                    <input type="hidden" name="stName" id="stName" value="" />
		
                <table class="table" id="distMidPE" >
                    <thead>
                    	<tr>
                    		<th colspan="7">State Name : ${stName}, &nbsp;&nbsp; District Name: ${dName}</th>
                    	</tr>
                        <tr>
                            <th rowspan="2" class="text-center">S.No.</th>
                            <th rowspan="2" class="text-center">Project Name</th>
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
                            <c:forEach var="i" begin="1" end="7">
                				<th class="text-center">${i}</th>
            				</c:forEach>
                        </tr>
                    </thead>
                      <tbody id="tbodyDistMidProjEvolRpt">
						<c:forEach items="${projMidPrjEvlWorkDtlList}" var="dt" varStatus="sno">
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
								<td class="text-right"><c:out value="${dt.created_work}" /></td>
								<td class="text-right"><c:out value="${dt.ongoing_work}" /></td>
								<td class="text-right"><c:out value="${dt.completed_work}" /></td>
								<td class="text-right"><c:out value="${dt.shape_file_area}" /></td>
								<td class="text-right"><c:out value="${dt.geo_tagg_work}" /></td>
							</tr>
							
							<c:set var="totCreated" value="${totCreated + dt.created_work}" />
							<c:set var="totOngoing" value="${totOngoing + dt.ongoing_work}" />
							<c:set var="totCompleted" value="${totCompleted + dt.completed_work}" />
							<c:set var="totShape" value="${totShape + dt.shape_file_area}" />
							<c:set var="totGeotag" value="${totGeotag + dt.geo_tagg_work}" />
							
						</c:forEach>
						<c:if test="${projMidPrjEvlWorkDtlListSize>0}">
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCreated}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totOngoing}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCompleted}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totShape}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totGeotag}" /></b></td>
							</tr>
						</c:if>
						<c:if test="${projMidPrjEvlWorkDtlListSize==0}">
							<tr>
								<td align="center" colspan="7" class="required" style="color: red;"><b>Data Not Found</b></td>
							</tr>
						</c:if>
					</tbody>
                </table>
            
    </form>
    </div>
    </div>
    </div>
</div>
<div id="childModal" class="modal1">
    <div class="modal1-content">
        <span class="close" onclick="closeChildModal()">&times;</span>
        <iframe id="childFrame" src="" width="100%" height="95%" frameborder="0"></iframe>
    </div>
</div>

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>
