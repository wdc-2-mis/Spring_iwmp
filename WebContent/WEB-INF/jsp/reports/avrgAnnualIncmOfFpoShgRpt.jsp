<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>

<title>Report PE9- State and District wise Mid Term Evaluation of Average Annual Income of FPOs, Net Income of FPO and SHG members</title>

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
	function exportExcel() {
		document.getAvrgAnnualIncome.action = "downloadExcelAvrgAnnualIncomeReport";
		document.getAvrgAnnualIncome.method = "post";
		document.getAvrgAnnualIncome.submit();
	}

	function downloadPDF() {
		document.getAvrgAnnualIncome.action = "downloadAvrgAnnualIncomeReportPdf";
		document.getAvrgAnnualIncome.method = "post";
		document.getAvrgAnnualIncome.submit();
	}

	function exportDExcel(stcode, stname) {
		document.getElementById("stcode").value = stcode;
		document.getElementById("stname").value = stname;
		document.getAvrgAnnualIncome.action = "downloadExcelDistAvrgAnnualIncomeReport";
		document.getAvrgAnnualIncome.method = "post";
		document.getAvrgAnnualIncome.submit();
	}

	function downloadDPDF(stcode, stname) {
		document.getElementById("stcode").value = stcode;
		document.getElementById("stname").value = stname;
		document.getAvrgAnnualIncome.action = "downloadDistAvrgAnnualIncomePdf";
		document.getAvrgAnnualIncome.method = "post";
		document.getAvrgAnnualIncome.submit();
	}
	
	function downloadPPDF(dcode, stName,distName){
		document.getElementById("dcode").value=dcode;
	    document.getElementById("stName").value=stName;
	    document.getElementById("distName").value=distName;
		document.getAvrgAnnualIncome.action="downloadProjAvrgAnnualIncomePdf";
		document.getAvrgAnnualIncome.method="post";
		document.getAvrgAnnualIncome.submit();
	}

		function exportPExcel(dcode, stName, distName){
		document.getElementById("dcode").value = dcode;
		document.getElementById("stName").value = stName;
		document.getElementById("distName").value = distName;

		let form = document.getElementById("getAvrgAnnualIncome");
		form.action = "downloadExcelProjAvrgAnnualIncomeReport";
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
<c:if test = "${listsize>0}">
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE9 - State-wise Mid Term Evaluation of Average Annual Income of FPOs, Net Income of FPO and SHG members</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-1" ></div>
			<div class="col-10">

	<form:form action="getAverageAnnualIncome" name="getAvrgAnnualIncome" id="getAvrgAnnualIncome" method="get">
		
 	</form:form>
 
<br>
	<div id ="excelpdf">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF()" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="2">S.No.</th>
            <th rowspan="2">State Name</th>
            <th rowspan="2">Total No. of Project</th>
            <th colspan="3" style="text-align: center">Average Annual Income of FPOs</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of FPO Members</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of SHG Members</th>
        </tr>
        <tr>
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
           
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
            
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
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
			
		</tr>
		</thead>
		<tbody id = "avgAnnaulIncomeRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totPreFpoTurnOver" value="0"/>
			<c:set var="totMidFpoTurnOver" value="0"/>
			<c:set var="totControlFpoTurnOver" value="0"/>
			<c:set var="totPreFpoIncome" value="0"/>
			<c:set var="totMidFpoIncome" value="0"/>
			<c:set var="totControlFpoIncome" value="0"/>
			<c:set var="totPreShgAnnaulIncome" value="0"/>
			<c:set var="totMidShgAnnaulIncome" value="0"/>
			<c:set var="totControlShgAnnaulIncome" value="0"/>
			<c:forEach items="${list}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td><a href="getDistwiseAverageAnnualIncome?stcode=${list.stcode}&&stname=${list.stname}">${list.stname}</a></td>
					<td>${list.totproj}</td>
					<td>${list.prefpoturnover}</td>
					<td>${list.midfpoturnover}</td>
					<td>${list.controlfpoturnover}</td>
					
					<td>${list.prefpoincome}</td>
					<td>${list.midfpoincome}</td>
					<td>${list.controlfpoincome}</td>
					<td>${list.preannualincomeshg}</td>
					<td>${list.midannualincomeshg}</td>
					<td>${list.controlannualincomeshg}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totPreFpoTurnOver" value="${totPreFpoTurnOver + list.prefpoturnover}"/>
			<c:set var="totMidFpoTurnOver" value="${totMidFpoTurnOver + list.midfpoturnover}"/>
			<c:set var="totControlFpoTurnOver" value="${totControlFpoTurnOver + list.controlfpoturnover}"/>
			<c:set var="totPreFpoIncome" value="${totPreFpoIncome + list.prefpoincome}"/>
			<c:set var="totMidFpoIncome" value="${totMidFpoIncome + list.midfpoincome}"/>
			<c:set var="totControlFpoIncome" value="${totControlFpoIncome + list.controlfpoincome}"/>
			<c:set var="totPreShgAnnaulIncome" value="${totPreShgAnnaulIncome + list.preannualincomeshg}"/>
			<c:set var="totMidShgAnnaulIncome" value="${totMidShgAnnaulIncome + list.midannualincomeshg}"/>
			<c:set var="totControlShgAnnaulIncome" value="${totControlShgAnnaulIncome + list.controlannualincomeshg}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2" class="table-primary"> Grand Total</td>
				<td class="table-primary">${totproj}</td>
				<td class="table-primary">${totPreFpoTurnOver}</td>
				<td class="table-primary">${totMidFpoTurnOver}</td>
				<td class="table-primary">${totControlFpoTurnOver}</td>
				<td class="table-primary">${totPreFpoIncome}</td>
				<td class="table-primary">${totMidFpoIncome}</td>
				<td class="table-primary">${totControlFpoIncome}</td>
				<td class="table-primary">${totPreShgAnnaulIncome}</td>
				<td class="table-primary">${totMidShgAnnaulIncome}</td>
				<td class="table-primary">${totControlShgAnnaulIncome}</td>
			</tr>
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>

</c:if>
<c:if test ="${distListSize >0}">
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE9 - District-wise Mid Term Evaluation of Average Annual Income of FPOs, Net Income of FPO and SHG members for State '<c:out value ="${stname}"/>'</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-1" ></div>
			<div class="col-10">

	<form:form action="getAverageAnnualIncome" name="getAvrgAnnualIncome" id="getAvrgAnnualIncome" method="get">
    	<input type="hidden" name="stcode" id="stcode" value="" />
    	<input type="hidden" name="stname" id="stname" value="" />
 	</form:form>
 
<br>
	<div id ="excelpdf">
	<button name="exportDExcel" id="exportDExcel" onclick="exportDExcel('${stcode}','${stname}')" class="btn btn-info">Excel</button>
	<button name="exportDPDF" id="exportDPDF" onclick="downloadDPDF('${stcode}','${stname}')" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="2">S.No.</th>
            <th rowspan="2">District Name</th>
            <th rowspan="2">Total No. of Project</th>
            <th colspan="3" style="text-align: center">Average Annual Income of FPOs</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of FPO Members</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of SHG Members</th>
        </tr>
        <tr>
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
           
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
            
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
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
			
		</tr>
		</thead>
		<tbody id = "avgAnnaulIncomeRptTbody">
			<c:set var="totproj" value="0"/>
			<c:set var="totPreFpoTurnOver" value="0"/>
			<c:set var="totMidFpoTurnOver" value="0"/>
			<c:set var="totControlFpoTurnOver" value="0"/>
			<c:set var="totPreFpoIncome" value="0"/>
			<c:set var="totMidFpoIncome" value="0"/>
			<c:set var="totControlFpoIncome" value="0"/>
			<c:set var="totPreShgAnnaulIncome" value="0"/>
			<c:set var="totMidShgAnnaulIncome" value="0"/>
			<c:set var="totControlShgAnnaulIncome" value="0"/>
			<c:forEach items="${distList}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
<%-- 					<td>${list.distname}</td> --%>
					<td><a
						href="getProjwiseAverageAnnualIncome?dcode=<c:out value="${list.dcode}"/>"><c:out
								value="${list.distname}" /></a></td>
					<td>${list.totproj}</td>
					<td>${list.prefpoturnover}</td>
					<td>${list.midfpoturnover}</td>
					<td>${list.controlfpoturnover}</td>
					
					<td>${list.prefpoincome}</td>
					<td>${list.midfpoincome}</td>
					<td>${list.controlfpoincome}</td>
					<td>${list.preannualincomeshg}</td>
					<td>${list.midannualincomeshg}</td>
					<td>${list.controlannualincomeshg}</td>
				</tr>
			<c:set var="totproj" value="${totproj + list.totproj}"/>
			<c:set var="totPreFpoTurnOver" value="${totPreFpoTurnOver + list.prefpoturnover}"/>
			<c:set var="totMidFpoTurnOver" value="${totMidFpoTurnOver + list.midfpoturnover}"/>
			<c:set var="totControlFpoTurnOver" value="${totControlFpoTurnOver + list.controlfpoturnover}"/>
			<c:set var="totPreFpoIncome" value="${totPreFpoIncome + list.prefpoincome}"/>
			<c:set var="totMidFpoIncome" value="${totMidFpoIncome + list.midfpoincome}"/>
			<c:set var="totControlFpoIncome" value="${totControlFpoIncome + list.controlfpoincome}"/>
			<c:set var="totPreShgAnnaulIncome" value="${totPreShgAnnaulIncome + list.preannualincomeshg}"/>
			<c:set var="totMidShgAnnaulIncome" value="${totMidShgAnnaulIncome + list.midannualincomeshg}"/>
			<c:set var="totControlShgAnnaulIncome" value="${totControlShgAnnaulIncome + list.controlannualincomeshg}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2" class="table-primary">Grand Total</td>
				<td class="table-primary">${totproj}</td>
				<td class="table-primary">${totPreFpoTurnOver}</td>
				<td class="table-primary">${totMidFpoTurnOver}</td>
				<td class="table-primary">${totControlFpoTurnOver}</td>
				<td class="table-primary">${totPreFpoIncome}</td>
				<td class="table-primary">${totMidFpoIncome}</td>
				<td class="table-primary">${totControlFpoIncome}</td>
				<td class="table-primary">${totPreShgAnnaulIncome}</td>
				<td class="table-primary">${totMidShgAnnaulIncome}</td>
				<td class="table-primary">${totControlShgAnnaulIncome}</td>
			</tr>
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>


</c:if>
<c:if test ="${projListSize >0}">
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">Report PE9 - Project-wise Mid Term Evaluation of Average Annual Income of FPOs, Net Income of FPO and SHG members for district '<c:out value ="${distName}"/>' of State '<c:out value ="${stName}"/>'</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-1" ></div>
			<div class="col-10">

	<form:form action="getAverageAnnualIncome" name="getAvrgAnnualIncome" id="getAvrgAnnualIncome" method="post">
    	<input type="hidden" name="stcode" id="stcode" value="" />
    	<input type="hidden" name="dcode" id="dcode" value="" />
    	<input type="hidden" name="stName" id="stName" value="" />
    	<input type="hidden" name="distName" id="distName" value="" />
 	</form:form>
 
<br>
	<div id ="excelpdf">
	<button name="exportPExcel" id="exportPExcel" onclick="exportPExcel('${dcode}','${stName}' ,'${distName}')" class="btn btn-info">Excel</button>
	<button name="exportPPDF" id="exportPPDF" onclick="downloadPPDF('${dcode}','${stName}','${distName}')" class="btn btn-info">PDF</button> 
	</div>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			   <tr>
            <th rowspan="2">S.No.</th>
            <th rowspan="2" style="width:200px;">Project Name</th>
            <th colspan="3" style="text-align: center">Average Annual Income of FPOs</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of FPO Members</th>
            <th colspan="3" style="text-align: center">Average Annual Net Income of SHG Members</th>
        </tr>
        <tr>
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
           
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
            
            <th>Pre Project Status(Aggregate)</th>
            <th>Mid Project Status(Aggregate)</th>
            <th>Controlled Area</th>
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
<!-- 			<th class="text-center">12</th> -->
			
		</tr>
		</thead>
		<tbody id = "avgAnnaulIncomeRptTbody">
<%-- 			<c:set var="totproj" value="0"/> --%>
			<c:set var="totPreFpoTurnOver" value="0"/>
			<c:set var="totMidFpoTurnOver" value="0"/>
			<c:set var="totControlFpoTurnOver" value="0"/>
			<c:set var="totPreFpoIncome" value="0"/>
			<c:set var="totMidFpoIncome" value="0"/>
			<c:set var="totControlFpoIncome" value="0"/>
			<c:set var="totPreShgAnnaulIncome" value="0"/>
			<c:set var="totMidShgAnnaulIncome" value="0"/>
			<c:set var="totControlShgAnnaulIncome" value="0"/>
			<c:forEach items="${projList}" var="list" varStatus="sno">
				<tr>
					<td>${sno.count}</td>
					<td><c:choose>
                                   <c:when test="${list.status ne null}">
                                   <a href="javascript:void(0);" onclick="handleProjectClick(${list.proj_id})">
                                            <c:out value="${list.proj_name}" />
                                        </a>
                                   </c:when>
                              <c:otherwise>
                                <c:out value="${list.proj_name}"/>
                              </c:otherwise>
                              </c:choose></td>
					<td>${list.prefpoturnover}</td>
					<td>${list.midfpoturnover}</td>
					<td>${list.controlfpoturnover}</td>
					<td>${list.prefpoincome}</td>
					<td>${list.midfpoincome}</td>
					<td>${list.controlfpoincome}</td>
					<td>${list.preannualincomeshg}</td>
					<td>${list.midannualincomeshg}</td>
					<td>${list.controlannualincomeshg}</td>
				</tr>
			<c:set var="totPreFpoTurnOver" value="${totPreFpoTurnOver + list.prefpoturnover}"/>
			<c:set var="totMidFpoTurnOver" value="${totMidFpoTurnOver + list.midfpoturnover}"/>
			<c:set var="totControlFpoTurnOver" value="${totControlFpoTurnOver + list.controlfpoturnover}"/>
			<c:set var="totPreFpoIncome" value="${totPreFpoIncome + list.prefpoincome}"/>
			<c:set var="totMidFpoIncome" value="${totMidFpoIncome + list.midfpoincome}"/>
			<c:set var="totControlFpoIncome" value="${totControlFpoIncome + list.controlfpoincome}"/>
			<c:set var="totPreShgAnnaulIncome" value="${totPreShgAnnaulIncome + list.preannualincomeshg}"/>
			<c:set var="totMidShgAnnaulIncome" value="${totMidShgAnnaulIncome + list.midannualincomeshg}"/>
			<c:set var="totControlShgAnnaulIncome" value="${totControlShgAnnaulIncome + list.controlannualincomeshg}"/>
			
			</c:forEach>
			<tr style="font-weight:bold; background:#f0f0f0; text-align: center">
				<td colspan="2" class="table-primary">Grand Total</td>
<%-- 				<td>${totproj}</td> --%>
				<td class="table-primary">${totPreFpoTurnOver}</td>
				<td class="table-primary">${totMidFpoTurnOver}</td>
				<td class="table-primary">${totControlFpoTurnOver}</td>
				<td class="table-primary">${totPreFpoIncome}</td>
				<td class="table-primary">${totMidFpoIncome}</td>
				<td class="table-primary">${totControlFpoIncome}</td>
				<td class="table-primary">${totPreShgAnnaulIncome}</td>
				<td class="table-primary">${totMidShgAnnaulIncome}</td>
				<td class="table-primary">${totControlShgAnnaulIncome}</td>
			</tr>
			
		</tbody>
	</table>
			</div>
 		</div>
	</div>


</c:if>
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