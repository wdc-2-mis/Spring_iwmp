<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

    <title>Report ME3- Project wise Current Status of Baseline Survey and Net Treatable Area</title>

    <script type="text/javascript">
        function downloadPDF( statename,distName,dcode) {
        	
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadProjWiseStatusBaselNetTreatAreaPDF";
            document.getReport.method = "post";
            document.getReport.submit();
        }

        function exportExcel(statename,distName,dcode) {
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadExcelProjWiseStatusBaselNetTreatArea";
            document.getReport.method = "post";
            document.getReport.submit();
        }
    </script>
</head>
<body>
    <form action="downloadProjWiseStatusBaselNetTreatAreaPDF" method="post" name="getReport">
   			<input type="hidden" name="statename" id="statename" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
   			<input type="hidden" name="dcode" id="dcode" value="" />
    
    </form>
    <div class="container">
        <div class="formheading">
            <h5>
              Report ME3- Project wise Current Status of Baseline Survey and Net Treatable Area for District   '<c:out value="${distName}"/>'   of State   '<c:out value="${statename}"/>'  
            </h5>
        </div>
        <br>
       				<button name="exportExcel" id="exportExcel" onclick="exportExcel('${statename}','${distName}','${dcode}')" class="btn btn-info">Excel</button> 
                    <button name="exportPDF" id="exportPDF" onclick="downloadPDF('${statename}','${distName}','${dcode}')" class="btn btn-info">PDF</button>
                    <p align="right">Report as on: <%=app.util.Util.dateToString(null, "dd/MM/yyyy hh:mm aaa")%></p>
        <div class="card">
            <div class="row">
                <div class="col-12" id="exportHtmlToPdf">
                    <div class="canvas_div_pdf">
                        <table class="table table-bordered table-striped table-highlight w-auto" id="dtBasicExample">
                           	<thead>
    	<tr>
      		<th style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Project Area Sanctioned</th>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">Total Project Area Covered for Baseline Survey</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total Area of Plots Covered for Baseline Survey</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;"><b>%</b> of Total Area Covered for Plot-Wise Baseline Survey</th>
		</tr>
  	</thead>
  	<tbody>
    	<tr>
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<th class="text-center">3</th>
			<th class="text-center">4</th>
			<th class="text-center">5</th>
			<th class="text-center">6</th>
		</tr>
                                <c:set var="count" value="1" />
                                <c:forEach items="${blListt}" var="bls" varStatus="sno">
                                    <tr>
                     <td class="text-left"><c:out value="${sno.count}" /></td>
                     <td align="left"><c:out value='${bls.proj_name}' /></td>
                    <td align="right"><c:out value='${bls.project_area}' /></td>
					<td align="right"><c:out value='${bls.proj_area_coverbasel}' /></td>
					<td align="right"><c:out value='${bls.plot_area_basel}' /></td>
					<td align="right"><fmt:formatNumber value="${(bls.plot_area_basel / bls.project_area) * 100}" type="number" minFractionDigits="2" maxFractionDigits="2" />%
		</td>

					
					
                                        <c:set var="projectarea" value="${projectarea + bls.project_area}" />
                                        <c:set var="projareacoverbasel" value="${projareacoverbasel + bls.proj_area_coverbasel}" />
                                        <c:set var="plotareabasel" value="${plotareabasel + bls.plot_area_basel}" />
<%--                                         <c:set var="percentagearea" value="${percentagearea + bls.percentage_area}" /> --%>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${projectarea}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${projareacoverbasel}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${plotareabasel}" /></b></td>
                                    <td align="right" class="table-primary"><b>
    			<fmt:formatNumber value="${(plotareabasel / projectarea) * 100}" type="number" minFractionDigits="2" maxFractionDigits="2" />%
										</b></td>
										
                                
                                </tr>

                                <c:if test="${blListtSize==0}">
                                    <tr>
                                        <td align="center" colspan="6" class="required" style="color:red;"><b>Data Not Found</b></td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
    </div>
</body>
</html>
