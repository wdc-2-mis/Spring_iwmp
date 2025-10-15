<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE7-  Project-wise Mid Term Evaluation of Mandays Details</title>

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

function downloadPDF(dcode, stName,distName){
	document.getElementById("dcode").value=dcode;
    document.getElementById("stName").value=stName;
    document.getElementById("distName").value=distName;
	document.getmandays.action="downloadProjMandaysDetailsReportPdf";
	document.getmandays.method="post";
	document.getmandays.submit();
}

	function exportExcel(dcode, stName, distName){
	document.getElementById("dcode").value = dcode;
	document.getElementById("stName").value = stName;
	document.getElementById("distName").value = distName;

	let form = document.getElementById("getmandays");
	form.action = "downloadExcelProjMandaysDetailsReport";
		form.method = "post";
		form.submit();
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
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE7-  Project-wise Mid Term Evaluation of Mandays Details for District  '<c:out value="${distName }"> </c:out>'  of State  '<c:out value="${stName }"></c:out>'</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">
<form:form action="getProjMandaysDetailsReport" name="getmandays" id="getmandays" method="post">   
    <input type="hidden" name="dcode" id="dcode" value="" />
    <input type="hidden" name="stName" id="stName" value="" />
    <input type="hidden" name="distName" id="distName" value="" />
</form:form>
 
<br>
	<c:if test="${not empty manPList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${dcode}','${stName}','${distName}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${dcode}','${stName}','${distName}')" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="3">S.No.</th>
            <th rowspan="3">Project Name</th>
<!--             <th rowspan="3">Total No. of Project</th> -->
            <th colspan="3" style="text-align: center">Farmer`s Average Household Income per Annum (Rs. in Lakhs)</th>
            <th colspan="2" style="text-align: center">No. of Farmers Benefited</th>
            <th colspan="2" style="text-align: center">No. of Persondays Generated (man-days)</th>
        </tr>
        <tr>
            <th rowspan="2">Pre - Project Status(Aggregate)</th>
            <th rowspan="2">Mid - Project Status(Aggregate)</th>
            <th rowspan="2">Controlled Area</th>
            <th>Project Area</th>
            <th>Controlled Area</th>
            <th>Project Area</th>
            <th>Controlled Area</th>
        </tr>
        
		</thead>
		<tbody id = "convergedWorksRptTbody">
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
<!-- 				<th class="text-center">10</th> -->
			</tr>
			<c:forEach items="${manPList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
						<td class="text-left"><c:choose>
                                   <c:when test="${dt.status ne null}">
                                   <a href="javascript:void(0);" onclick="handleProjectClick(${dt.proj_id})">
                                            <c:out value="${dt.proj_name}" />
                                        </a>
                                   </c:when>
                              <c:otherwise>
                                <c:out value="${dt.proj_name}"/>
                              </c:otherwise>
                              </c:choose></td>
					<td class="text-right"><c:out value="${dt.pre_farmer_income}" /></td>
					<td class="text-right"><c:out value="${dt.mid_farmer_income}" /></td>
					<td class="text-right"><c:out value="${dt.control_farmer_income}" /></td>
					<td class="text-right"><c:out value="${dt.farmer_benefited}" /></td>
					<td class="text-right"><c:out value="N/A" /></td>
					<td class="text-right"><c:out value="${dt.mandays_generated}" /></td>
					<td class="text-right"><c:out value="N/A" /></td>
					
				</tr>
				
 				<c:set var = "totproj" 
  				value = "${totproj + dt.total_project}" />  
 				<c:set var = "prefarmerincome"  
  				value = "${prefarmerincome + dt.pre_farmer_income}" /> 
  				<c:set var = "midfarmerincome"  
  				value = "${midfarmerincome + dt.mid_farmer_income}" />  
  				<c:set var = "controlfarmerincome"  
  				value = "${controlfarmerincome + dt.control_farmer_income}" /> 
  				<c:set var = "farmerbenefited"  
 				value = "${farmerbenefited + dt.farmer_benefited}" />
 				<c:set var = "controlfarmerbenefited"  
 				value = "${controlfarmerbenefited + dt.control_farmer_benefited}" />
 				<c:set var = "mandaysgenerated"  
 				value = "${mandaysgenerated + dt.mandays_generated}" />
 				<c:set var = "controlmandaysgenerated"  
 				value = "${controlmandaysgenerated + dt.control_mandays_generated}" />

			</c:forEach>
			<c:if test="${manPListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${prefarmerincome}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${midfarmerincome}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${controlfarmerincome}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${farmerbenefited}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="N/A" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${mandaysgenerated}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="N/A" /></b></td>
					
				</tr>
			</c:if>
			<c:if test="${manPListSize==0}">
				<tr>
					<td align="center" colspan="10" class="required" style="color:red;"><b>Data Not Found</b></td>
				</tr>
			</c:if>
		</tbody>
	</table>
			</div>
 		</div>
	</div>

<div id="childModal" class="modal1">
    <div class="modal1-content">
        <span class="close" onclick="closeChildModal()">&times;</span>
        <iframe id="childFrame" src="" width="100%" height="95%" frameborder="0"></iframe>
    </div>
</div>
	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>