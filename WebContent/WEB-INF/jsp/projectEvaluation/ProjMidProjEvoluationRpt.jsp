<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Report PE1 - Project-wise Mid Term Project Evaluation</title>

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
    document.getPEDetails.action="downloadPDFProjMidProjEvoluation";
    document.getPEDetails.method="post";
    document.getPEDetails.submit();
}

function exportExcel(dcode, dName, stName){
    document.getElementById("dcode").value=dcode;
    document.getElementById("dName").value=dName;
    document.getElementById("stName").value=stName;
    document.getPEDetails.action="downloadExcelProjMidProjEvoluation";
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
        <h5>Report PE1 - Project-wise Mid Term Project Evaluation Entry Status</h5>
    </div>
    <br>
    <div class="card">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8">
                <br>
                <c:if test="${not empty projMidPrjEvlList}">
                    <button name="exportExcel" id="exportExcel" onclick="exportExcel('${dcode}', '${dName}', '${stName}')" class="btn btn-info">Excel</button>
                    <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${dcode}', '${dName}', '${stName}')" class="btn btn-info">PDF</button>
                </c:if>
                <p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
                
                <form action="downloadExcelProjMidProjEvoluation" name="getPEDetails" id="getPEDetails" method="post">
                    <input type="hidden" name="dcode" id="dcode" value="" />
                    <input type="hidden" name="dName" id="dName" value="" />
                    <input type="hidden" name="stName" id="stName" value="" />
                    
                    <table class="table" id="projMidPE">
                        <thead>
                            <tr>
                                <th colspan="5">State Name : ${stName}, &nbsp;&nbsp; District Name: ${dName}</th>
                            </tr>
                            <tr>
                                <th class="text-center">S.No.</th>
                                <th class="text-center">Project Name</th>
                                <th class="text-center">Block</th>
                                <th class="text-center">Gram Panchayat</th>
                                <th class="text-center">Village</th>
                            </tr>
                            <tr>
                                <th class="text-center">1</th>
                                <th class="text-center">2</th>
                                <th class="text-center">3</th>
                                <th class="text-center">4</th>
                                <th class="text-center">5</th>
                            </tr>
                        </thead>
                        <tbody id="tbodyProjMidProjEvolRpt">
                            <c:forEach items="${projMidPrjEvlList}" var="pt" varStatus="sno">
                                <tr>
                                    <td class="text-left"><c:out value="${sno.count}" /></td>
                                    <td class="text-left">
                                        <a href="javascript:void(0);" onclick="handleProjectClick(${pt.project})">
                                            <c:out value="${pt.projname}" />
                                        </a>
                                    </td>
                                    <td class="text-right"><c:out value="${pt.block}" /></td>
                                    <td class="text-right"><c:out value="${pt.gp}" /></td>
                                    <td class="text-right"><c:out value="${pt.village}" /></td>
                                </tr>
                                <c:set var="totBlk" value="${totBlk + pt.block}" />
                                <c:set var="totGP" value="${totGP + pt.gp}" />
                                <c:set var="totVlg" value="${totVlg + pt.village}" />
                            </c:forEach>

                            <c:if test="${projMidPrjEvlListSize > 0}">
                                <tr> 
                                    <td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totBlk}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totGP}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totVlg}" /></b></td>
                                </tr>
                            </c:if>

                            <c:if test="${projMidPrjEvlListSize == 0}">
                                <tr>
                                    <td align="center" colspan="5" class="required" style="color: red;">
                                        <b>Data Not Found</b>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody> 
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- ========= Modal Popup for Child JSP ========= -->
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
