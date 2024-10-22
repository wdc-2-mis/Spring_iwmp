<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

    <title>Report ME2- Project-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details</title>

    <script type="text/javascript">
        function downloadPDF( statename,distName,dcode) {
        	
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadProjectWiseCurrentStatusOtherPDF";
            document.getReport.method = "post";
            document.getReport.submit();
        }

        function exportExcel(statename,distName,dcode) {
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadExcelprojWiseCurrentStatusOther";
            document.getReport.method = "post";
            document.getReport.submit();
        }
    </script>
</head>
<body>
    <form action="downloadProjectWiseCurrentStatusOtherPDF" method="post" name="getReport">
   			<input type="hidden" name="statename" id="statename" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
   			<input type="hidden" name="dcode" id="dcode" value="" />
    
    </form>
    
    <div class="container">
        <div class="formheading">
            <h5>
               Report ME2- Project-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of 
               Projects with Baseline Ground Water Details for District '<c:out value="${distName}"/>' of State '<c:out value="${statename}"/>'  
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
      		<th style="text-align:center; vertical-align: middle; width: 15%;">Project Name</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">Total no. of Village Covered in Project</th>
      		<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project for Permission given</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project Plan Created</th>
			<th style="text-align:center; vertical-align: middle; width: 7%;">No. of Project with Baseline Ground Water</th> 
			<!-- <th style="text-align:center; vertical-align: middle; width: 7%;">No. of WCDC Account Created</th>  --> 
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
<!-- 			<th class="text-center">7</th> -->
		</tr>
                                <c:set var="count" value="1" />
                                <c:forEach items="${bslListt}" var="achv" varStatus="sno">
                                    <tr>
                     <td class="text-left"><c:out value="${sno.count}" /></td>
                     <td align="left"><c:out value='${achv.proj_name}' /></td>
                    <td align="right"><c:out value='${achv.covered_village}' /></td>
					<td align="right"><c:out value='${achv.tot_proj_permission}' /></td>
					<td align="right"><c:out value='${achv.total_project_plan}' /></td>
					<td align="right"><c:out value='${achv.tot_proj_basel_ground}' /></td>
					
                                        <c:set var="totcoveredvillage" value="${totcoveredvillage + achv.covered_village}" />
                                        <c:set var="totalprojpermission" value="${totalprojpermission + achv.tot_proj_permission}" />
                                        <c:set var="totalprojectplan" value="${totalprojectplan + achv.total_project_plan}" />
                                        <c:set var="totprojbaselground" value="${totprojbaselground + achv.tot_proj_basel_ground}" />
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totcoveredvillage}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totalprojpermission}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totalprojectplan}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totprojbaselground}" /></b></td>
<%--                                     <td align="right" class="table-primary"><b><c:out value="${tottotalvillbasel}" /></b></td> --%>
                                
                                </tr>

                                <c:if test="${bslListtSize==0}">
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
