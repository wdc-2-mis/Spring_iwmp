<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details</title>

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

function downloadPDF(dcode, stName, distName){
	document.getElementById("dcode").value=dcode;
	document.getElementById("stName").value=stName;
	document.getElementById("distName").value=distName;
	document.getPEDetails.action="downloadPDFProjMidProjEvlCropDetails";
	document.getPEDetails.method="post";
	document.getPEDetails.submit();
}

function exportExcel(dcode, stName, distName){
	document.getElementById("dcode").value=dcode;
	document.getElementById("stName").value=stName;
	document.getElementById("distName").value=distName;
	document.getPEDetails.action="downloadExcelProjMidProjEvlCropDetails";
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
        <h5>Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details for District  '<c:out value="${distName}"/>' of State '<c:out value="${stName}"/>'</h5>
    </div>
    <br>
    <div class="container-fluid">
	
	<c:if test="${not empty cropPList}" >
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${dcode}','${stName}','${distName}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${dcode}','${stName}','${distName}')" class="btn btn-info">PDF</button>
	</c:if>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	
	<div class="row">
	<div class="col text-center">
    
        <form action="downloadExcelProjMidProjEvlCropDetails" name="getPEDetails" id="getPEDetails" method="post">
        	
			<input type="hidden" name="dcode" id="dcode" value="" />
    	    <input type="hidden" name="stName" id="stName" value="" />
    		<input type="hidden" name="distName" id="distName" value="" />
		
                <table class="table" id="distMidPECrpData" >
                    <thead>
                    	<tr>
                    		<th colspan="20">State Name : ${stName}</th>
                    	</tr>
                        <tr>
                            <th rowspan="3" class="text-center">S.No.</th>
                            <th rowspan="3" class="text-center">Project Name</th>
                            <th colspan="9" class="text-center">Gross Cropped Area (ha.)</th>
                            <th colspan="3" class="text-center">Total Gross Cropped Area (ha.)</th>
                            <th colspan="3" class="text-center">Area under Plantation Cover (ha.)</th>
                            <th colspan="3" class="text-center">Area of Culturable Wasteland (ha.)</th>
                        </tr>
                        <tr>
                        	<th colspan="3" class="text-center">Kharif Crop</th>
                            <th colspan="3"class="text-center">Rabi Crop</th>
                            <th colspan="3" class="text-center">Third Crop</th>
                            
                            <c:forEach var="i" begin="1" end="3">
                				<th rowspan="2" class="text-center">Pre Project Status (Aggregate)</th>
                            	<th rowspan="2" class="text-center">Mid Project Status (Aggregate)</th>
                            	<th rowspan="2" class="text-center">Controlled Area</th>
            				</c:forEach>
                        </tr>
                        <tr>
                        	<c:forEach var="i" begin="1" end="3">
                				<th class="text-center">Pre Project Status (Aggregate)</th>
                            	<th class="text-center">Mid Project Status (Aggregate)</th>
                            	<th class="text-center">Controlled Area</th>
            				</c:forEach>
                        </tr>
                        <tr>
                        	
                        	<c:forEach var="i" begin="1" end="20">
                				<th class="text-center">${i}</th>
            				</c:forEach>
                        	
                        	
<%--                        	<% for(int i = 1; i < 22; i++) { %>
                				<th class="text-center"><%= i %></th>
            				<% } %> 									--%>
                        	
                        </tr>
                    </thead>
                      <tbody id="tbodyDistMidProjEvolRpt">
						<c:forEach items="${cropPList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td align="left">
								<c:choose>
                                   <c:when test="${dt.status ne null}">
                                   <a href="javascript:void(0);" onclick="handleProjectClick(${dt.proj_id})">
                                            <c:out value="${dt.proj_name}" />
                                        </a>
                                   </c:when>
                              <c:otherwise>
                                <c:out value="${dt.proj_name}"/>
                              </c:otherwise>
                              </c:choose>
                              </td>
								<td class="text-right"><c:out value="${dt.pre_kharif}" /></td>
								<td class="text-right"><c:out value="${dt.mid_kharif}" /></td>
								<td class="text-right"><c:out value="${dt.ctl_kharif}" /></td>
								<td class="text-right"><c:out value="${dt.pre_rabi}" /></td>
								<td class="text-right"><c:out value="${dt.mid_rabi}" /></td>
								<td class="text-right"><c:out value="${dt.ctl_rabi}" /></td>
								<td class="text-right"><c:out value="${dt.pre_thrdcrp}" /></td>
								<td class="text-right"><c:out value="${dt.mid_thrdcrp}" /></td>
								<td class="text-right"><c:out value="${dt.ctl_thrdcrp}" /></td>
								<td class="text-right"><c:out value="${dt.pre_total}" /></td>
								<td class="text-right"><c:out value="${dt.mid_total}" /></td>
								<td class="text-right"><c:out value="${dt.ctl_total}" /></td>
								<td class="text-right"><c:out value="${dt.pre_plt}" /></td>
								<td class="text-right"><c:out value="${dt.mid_plt}" /></td>
								<td class="text-right"><c:out value="${dt.ctl_plt}" /></td>
								<td class="text-right"><c:out value="${dt.pre_clt}" /></td>
								<td class="text-right"><c:out value="${dt.mid_clt}" /></td>
								<td class="text-right"><c:out value="${dt.ctl_clt}" /></td>
							</tr>
							
							<c:set var="totPreKharif" value="${totPreKharif + dt.pre_kharif}" />
							<c:set var="totMidKharif" value="${totMidKharif + dt.mid_kharif}" />
							<c:set var="totCtlKharif" value="${totCtlKharif + dt.ctl_kharif}" />
							<c:set var="totPreRabi" value="${totPreRabi + dt.pre_rabi}" />
							<c:set var="totMidRabi" value="${totMidRabi + dt.mid_rabi}" />
							<c:set var="totCtlRabi" value="${totCtlRabi + dt.ctl_rabi}" />
							<c:set var="totPreThrdCrp" value="${totPreThrdCrp + dt.pre_thrdcrp}" />
							<c:set var="totMidThrdCrp" value="${totMidThrdCrp + dt.mid_thrdcrp}" />
							<c:set var="totCtlThrdCrp" value="${totCtlThrdCrp + dt.ctl_thrdcrp}" />
							<c:set var="totPreTotal" value="${totPreTotal + dt.pre_total}" />
							<c:set var="totMidTotal" value="${totMidTotal + dt.mid_total}" />
							<c:set var="totCtlTotal" value="${totCtlTotal + dt.ctl_total}" />
							<c:set var="totPrePlt" value="${totPrePlt + dt.pre_plt}" />
							<c:set var="totMidPlt" value="${totMidPlt + dt.mid_plt}" />
							<c:set var="totCtlPlt" value="${totCtlPlt + dt.ctl_plt}" />
							<c:set var="totPreClt" value="${totPreClt + dt.pre_clt}" />
							<c:set var="totMidClt" value="${totMidClt + dt.mid_clt}" />
							<c:set var="totCtlClt" value="${totCtlClt + dt.ctl_clt}" />
							
						</c:forEach>
						<c:if test="${cropListPSize>0}">
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPreKharif}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMidKharif}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCtlKharif}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPreRabi}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMidRabi}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCtlRabi}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPreThrdCrp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMidThrdCrp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCtlThrdCrp}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPreTotal}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMidTotal}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCtlTotal}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPrePlt}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMidPlt}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCtlPlt}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totPreClt}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totMidClt}" /></b></td>
								<td align="right" class="table-primary"><b><c:out value="${totCtlClt}" /></b></td>
							</tr> 
						</c:if>
						<c:if test="${cropListPSize==0}">
							<tr>
								<td align="center" colspan="21" class="required" style="color: red;"><b>Data Not Found</b></td>
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
