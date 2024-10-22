<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">

    <title>ES3- Project-wise SHG Account Details</title>

    <script type="text/javascript">
        function downloadPDF( statename,distName,dcode) {
        	
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadProjectShgAPDF";
            document.getReport.method = "post";
            document.getReport.submit();
        }

        function exportExcel(statename,distName,dcode) {
        	document.getElementById("dcode").value=dcode;
        	document.getElementById("statename").value=statename;
        	document.getElementById("distName").value=distName;
            document.getReport.action = "downloadProjectShgAExcel";
            document.getReport.method = "post";
            document.getReport.submit();
        }
    </script>
</head>
<body>
    <form action="downloadProjectShgAPDF" method="post" name="getReport">
   			<input type="hidden" name="statename" id="statename" value="" />
			<input type="hidden" name="distName" id="distName" value="" />
   			<input type="hidden" name="dcode" id="dcode" value="" />
    
    </form>
    
    <div class="container">
        <div class="formheading">
            <h5>
                ES3- Project-wise SHG Account Details for District  '<c:out value="${distName}" />' of State '<c:out value="${statename}" />'
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
                                    <th style="text-align: center;">S.No.</th>
                                    <th style="text-align: left;">Project Name</th>
                                    <th style="text-align: center;">Total No. of SHG</th>
                                    <th style="text-align: center;">Total No. of SHG Account Entered</th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr>
									<th class="text-center">1</th>
									<th class="text-center">2</th>
									<th class="text-center">3</th>
									<th class="text-center">4</th>
							</tr>
                                <c:set var="count" value="1" />
                                <c:forEach items="${AcProList}" var="acnt" varStatus="sno">
                                    <tr>
                                        <td class="text-left"><c:out value="${sno.count}" /></td>
<!--                                         <td><a -->
                                        <td class="text-left"><c:out value="${acnt.proj_name}" /></td>
                                        <td class="text-right"><c:out value="${acnt.no_of_shg}" /></td>
                                        <td class="text-right"><c:out value="${acnt.no_of_shg_entered}" /></td>

                                        <c:set var="totshg" value="${totshg + acnt.no_of_shg}" />
                                        <c:set var="totshgenter" value="${totshgenter + acnt.no_of_shg_entered}" />
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totshg}" /></b></td>
                                    <td align="right" class="table-primary"><b><c:out value="${totshgenter}" /></b></td>
                                </tr>

                                <c:if test="${AcProListSize==0}">
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
