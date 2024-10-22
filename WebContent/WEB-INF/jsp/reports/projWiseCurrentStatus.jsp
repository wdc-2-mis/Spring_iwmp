<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

    <title>Report ME1- Project wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey</title>

    <script type="text/javascript">
        function downloadPDF( statename,distName,dcode) {
        	
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadProjectwiseCurrentStatusPDF";
            document.getReport.method = "post";
            document.getReport.submit();
        }

        function exportExcel(statename,distName,dcode) {
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadExcelProjWiseCurrentStatus";
            document.getReport.method = "post";
            document.getReport.submit();
        }
    </script>
</head>
<body>
    <form action="downloadProjectwiseCurrentStatusPDF" method="post" name="getReport">
   			<input type="hidden" name="statename" id="statename" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
   			<input type="hidden" name="dcode" id="dcode" value="" />
    
    </form>
    
    <div class="container">
        <div class="formheading">
            <h5>
               Report ME1- Project wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed 
               Committees with Villages and No. of Villages Covered under Baseline Survey for District '<c:out value="${distName}" />'  of State '<c:out value="${statename}" />'
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
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 20%;">Project Name</th>
      		<th colspan="4" style="text-align:center; vertical-align: middle; width: 28%;">Project Location</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 7%;">Total Village covered in Baseline Survey</th>
     	</tr>
     	
     	<tr>
     		<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project with Location Details</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Village Covered in Project</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">Total no. of Watershed Committee</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Villages Mapped with Watershed Committee</th>
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
			<th class="text-center">7</th>
		</tr>
                                <c:set var="count" value="1" />
                                <c:forEach items="${bslList}" var="achv" varStatus="sno">
                                    <tr>
                     <td class="text-left"><c:out value="${sno.count}" /></td>
                     <td align="left"><c:out value='${achv.proj_name}' /></td>
                    <td align="right"><c:out value='${achv.totprojloc}' /></td>
					<td align="right"><c:out value='${achv.totalvillproject}' /></td>
					<td align="right"><c:out value='${achv.totalwc}' /></td>
					<td align="right"><c:out value='${achv.totalvillinwc}' /></td>
					<td align="right"><c:out value='${achv.totalvillbasel}' /></td>
					
                                        <c:set var="tottotprojloc" value="${tottotprojloc + achv.totprojloc}" />
                                        <c:set var="tottotalvillproject" value="${tottotalvillproject + achv.totalvillproject}" />
                                        <c:set var="tottotalwc" value="${tottotalwc + achv.totalwc}" />
                                        <c:set var="tottotalvillinwc" value="${tottotalvillinwc + achv.totalvillinwc}" />
                                        <c:set var="tottotalvillbasel" value="${tottotalvillbasel + achv.totalvillbasel}" />
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${tottotprojloc}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${tottotalvillproject}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${tottotalwc}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${tottotalvillinwc}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${tottotalvillbasel}" /></b></td>
                                
                                </tr>

                                <c:if test="${bslListSize==0}">
                                    <tr>
                                        <td align="center" colspan="4" class="required" style="color:red;"><b>Data Not Found</b></td>
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
