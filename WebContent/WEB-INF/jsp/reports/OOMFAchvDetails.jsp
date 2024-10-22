<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<title>ES1- State-wise OOMF Additional Achievement Entry Status</title>

<script type="text/javascript">

function showReport(e) 
{
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var fincode = document.getElementById("year").value;
    
    document.getElementById("finName").value=finName;
    document.getElementById("fincode").value=fincode;
    
    if(document.getElementById("year").value=='')
	{
		alert('Please select Financial Year !');
		$('#year').focus();
		e.preventDefault();
	}
	else
	{
	document.getOOMFDetails.action="getOOMFAchvDetails";
	document.getOOMFDetails.method="post";
	document.getOOMFDetails.submit();
	}
	return false;
}

function downloadPDF(finyr, finName){
		document.getElementById("fincode").value=finyr;
		document.getElementById("finName").value=finName;
		document.getOOMFDetails.action="downloadPDFOOMFAchvDetails";
		document.getOOMFDetails.method="post";
		document.getOOMFDetails.submit();
}

function exportExcel(finyr, finName){
		document.getElementById("fincode").value=finyr;
		document.getElementById("finName").value=finName;
		document.getOOMFDetails.action="downloadExcelOOMFAchvDetails";
		document.getOOMFDetails.method="post";
		document.getOOMFDetails.submit();
}
	
</script>


<body>
<br>
	<div class="offset-md-3 col-6 formheading" style="text-align:center;">
		<h5>
			<label id="head">ES1- State-wise OOMF Additional Achievement Entry Status</label>
		</h5>
	</div>
<br>
	<div class ="card">
		<div class="row">
			<div class="col-2" ></div>
			<div class="col-8">

	<form:form action="getOOMFDetails" name="getOOMFDetails" id="getOOMFDetails" method="get">
		<div id="waitDiv" 
			style="display: none; line-height: 20px; z-index: 98; position: absolute; background: #ffffff; left: 25px;  height: 800px;
			 width: 1600px; filter: alpha(opacity = 60); -moz-opacity: .60; opacity: .60; text-align: center; float: left;">
			<table>  
				<tr>
					<td>
						<div align="center">
							<span style="padding-right:3px;  display:inline-block; width: 1600px;">
									<img class="manImg" src="resources/images/load.gif"></img>
							</span>
						</div>
					</td>
				</tr>
			</table> 
		</div>
		
    	<input type="hidden" name="fincode" id="fincode" value="" />
		<input type="hidden" name="finName" id="finName" value="" />
	
      	<table style="width:100%; align-content: center;" >
        	<tr align="center" >
          		<td><b>Financial Year <span style="color: red;">*</span></b></td>
 				<td>
              		<select name="year" id="year"  required="required" onchange="showReport();">
              			<option value="">--Select Year--</option>
						<c:if test="${not empty financialYear}">
							<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq finyr && lists.finYrCd ne 21}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>	
       							<c:if test="${lists.finYrCd ne finyr && lists.finYrCd ne 21}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
							</c:forEach>
						</c:if>  
					</select>
          		</td>
          		<td width="50%"></td>
       		</tr>
      	</table>
      	<div id="previewDiv" class="hiddenDivStyle" align="center"
			style="position: absolute; top: 100px; left: 25px; display: none; width: 300px; height: 50px; vertical-scrol: auto; background-color: gray;">
			<table align="center">
				<tr>
					<td>
						<div align="center">
							<span style="font-size: 25px;">Please Wait ...</span>
						</div>
					</td>
				</tr>
			</table>
		</div>
 	</form:form>
 
<br>
	<c:if test="${not empty oomfAchvList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${finyr}', '${finName}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${finyr}', '${finName}')" class="btn btn-info">PDF</button> 
	</c:if>   
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<table id = "tblReport" class = "table">
		<thead>
			<tr>
				<th colspan=6 class="text-left">Financial Year : <c:out value='${finName}' /></th>
			</tr>
			<tr>
				<th>S.No.</th>
				<th>State Name</th>
				<th>Total No. of District</th>
				<th>Total No. of Project</th>
				<th>Total No. of Project Submitted Half-Yearly Parameters Achievement</th>
				<th>Total No. of Project Submitted Year-wise Parameters Achievement</th>
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
			</tr>
			<c:forEach items="${oomfAchvList}" var="dt" varStatus="sno">
				<tr>
					<td class="text-left"><c:out value="${sno.count}" /></td>
					<td class="text-left"><c:out value="${dt.st_name}" /></td>
					<td class="text-right"><c:out value="${dt.totaldist}" /></td>
					<td class="text-right"><c:out value="${dt.totalproject}" /></td>
					<td class="text-right"><c:out value="${dt.halfyearfill}" /></td>
					<td class="text-right"><c:out value="${dt.yearwisefill}" /></td>
				</tr>
			
 				<c:set var = "totdist" 
 				value = "${totdist + dt.totaldist}" />
 				<c:set var = "totproj" 
 				value = "${totproj + dt.totalproject}" /> 
				<c:set var = "totHalfYr" 
 				value = "${totHalfYr + dt.halfyearfill}" />
 				<c:set var = "totYrWise" 
 				value = "${totYrWise + dt.yearwisefill}" /> 

			</c:forEach>
			<c:if test="${oomfAchvListSize>0}">
				<tr>
					<td colspan="2" align="right" class="table-primary"><b>Grand Total</b></td>
					<td align="right" class="table-primary"><b><c:out value="${totdist}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totproj}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totHalfYr}" /></b></td>
					<td align="right" class="table-primary"><b><c:out value="${totYrWise}" /></b></td>
				</tr>
			</c:if>
			<c:if test="${oomfAchvListSize==0}">
				<tr>
					<td align="center" colspan="6" class="required" style="color:red;"><b>Data Not Found</b></td>
				</tr>
			</c:if>
		</tbody>
	</table>
			</div>
 		</div>
	</div>


	<footer class=" text-center">
		<%@include file="/WEB-INF/jspf/footer2.jspf"%>
	</footer>
</body>
</html>