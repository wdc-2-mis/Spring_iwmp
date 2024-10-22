<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

    <title>Report ME5- Project and Year wise Current Status of Plan and Achievement</title>

    <script type="text/javascript">
        function downloadPDF( statename,distName,dcode) {
        	
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadProjWiseCurrentStatusPlanAchievePDF";
            document.getReport.method = "post";
            document.getReport.submit();
        }

        function exportExcel(statename,distName,dcode) {
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadExcelProjWiseCurrentStatusPlanAchieve";
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
             Report ME5- Project and Year wise Current Status of Plan and Achievement for District   '<c:out value="${distName}"/>'   of State   '<c:out value="${statename}"/>'  
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
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2022-23)</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2023-24)</th>
      		<th colspan="2" style="text-align:center; vertical-align: middle; width: 14%;">Financial Year(2024-25)</th>
      	</tr>	
      	<tr>	
      		<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Achievement Created</th>
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
			<th class="text-center">8</th>
		</tr>
                                <c:set var="count" value="1" />
                                <c:forEach items="${listt}" var="bls" varStatus="sno">
                                    <tr>
                     <td class="text-left"><c:out value="${sno.count}" /></td>
                     <td align="left"><c:out value='${bls.proj_name}' /></td>
					<td align="right"><c:out value='${bls.total_project_plan2022}' /></td>
					<td align="right"><c:out value='${bls.total_project_achievement2022}' /></td>
					<td align="right"><c:out value='${bls.total_project_plan2023}' /></td>
					<td align="right"><c:out value='${bls.total_project_achievement2023}' /></td>
					<td align="right"><c:out value='${bls.total_project_plan2024}' /></td>
					<td align="right"><c:out value='${bls.total_project_achievement2024}' /></td>

					
					
                                        <c:set var="totalplan2022" value="${totalplan2022 + bls.total_project_plan2022}" />
                                        <c:set var="totalachievement2022" value="${totalachievement2022 + bls.total_project_achievement2022}" />
                                        <c:set var="totalplan2023" value="${totalplan2023 + bls.total_project_plan2023}" />
                                        <c:set var="totalachievement2023" value="${totalachievement2023 + bls.total_project_achievement2023}" />
                                        <c:set var="totalplan2024" value="${totalplan2024 + bls.total_project_plan2024}" />
                                        <c:set var="totalachievement2024" value="${totalachievement2024 + bls.total_project_achievement2024}" />
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totalplan2022}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totalachievement2022}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totalplan2023}" /></b></td>
                                    <td align="right" class="table-primary"><b> <c:out value="${totalachievement2023}" /></b></td>
                                    <td align="right" class="table-primary"><b> <c:out value="${totalplan2024}" /></b></td>
                                    <td align="right" class="table-primary"><b> <c:out value="${totalachievement2024}" /></b></td>
										
                                
                                </tr>

                                <c:if test="${listtSize==0}">
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
