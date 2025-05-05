<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<title>Report PE3 - District-wise Mid Term Project Evaluation of Cropped Area Details</title>

<html>
<script type="text/javascript">

function downloadPDF(stcd, stName){
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	document.getPEDetails.action="downloadPDFDistMidProjEvlCropDetails";
	document.getPEDetails.method="post";
	document.getPEDetails.submit();
}

function exportExcel(stcd, stName){
	document.getElementById("stcd").value=stcd;
	document.getElementById("stName").value=stName;
	document.getPEDetails.action="downloadExcelDistMidProjEvlCropDetails";
	document.getPEDetails.method="post";
	document.getPEDetails.submit();
}

</script>
<body>
<div class="maindiv">
    <div class="offset-md-3 col-6 formheading" style="text-align: center;">
        <h4 style="text-decoration:underline;">Report PE3 - District-wise Mid Term Project Evaluation of Cropped Area Details</h4>
    </div>
    <br>
    <div class="container-fluid">
	
	<c:if test="${not empty distMidPrjEvlCrpDetailsList1}" >
		<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stcd}', '${stName}')" class="btn btn-info">Excel</button>
		<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcd}', '${stName}')" class="btn btn-info">PDF</button>
	</c:if>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	
	<div class="row">
	<div class="col text-center">
    
        <form action="downloadExcelDistMidProjEvlCropDetails" name="getPEDetails" id="getPEDetails" method="post">
        	
        	<input type="hidden" name="stcd" id="stcd" value="" />
			<input type="hidden" name="stName" id="stName" value="" />
		
                <table class="table" id="distMidPECrpData" >
                    <thead>
                    	<tr>
                    		<th colspan="21">State Name : ${stName}</th>
                    	</tr>
                        <tr>
                            <th rowspan="3" class="text-center">S.No.</th>
                            <th rowspan="3" class="text-center">District Name</th>
                            <th rowspan="3" class="text-center">Project</th>
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
                        	
                        	<c:forEach var="i" begin="1" end="21">
                				<th class="text-center">${i}</th>
            				</c:forEach>
                        	
                        	
<%--                        	<% for(int i = 1; i < 22; i++) { %>
                				<th class="text-center"><%= i %></th>
            				<% } %> 									--%>
                        	
                        </tr>
                    </thead>
                      <tbody id="tbodyDistMidProjEvolRpt">
						<c:forEach items="${distMidPrjEvlCrpDetailsList}" var="dt" varStatus="sno">
							<tr>
								<td class="text-left"><c:out value="${sno.count}" /></td>
								<td class="text-left"><c:out value="${dt.distname}" /></td>
								<td class="text-right"><c:out value="${dt.total_project}" /></td> 
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
							
							<c:set var="totProj" value="${totProj + dt.total_project}" />
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
						<c:if test="${distMidPrjEvlCrpDetailsListSize>0}">
							<tr>
								<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
								<td align="right" class="table-primary"><b><c:out value="${totProj}" /></b></td>
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
						<c:if test="${distMidPrjEvlCrpDetailsListSize==0}">
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

<footer class="text-center">
    <%@ include file="/WEB-INF/jspf/footer2.jspf" %>
</footer>

</body>
</html>
