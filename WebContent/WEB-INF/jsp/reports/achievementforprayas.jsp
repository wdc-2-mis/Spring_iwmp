<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<!DOCTYPE html>
<html>
<head>
<title>Report O11- State-wise, Year-wise and Month-wise Achievements for Prayas</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">
function showReport(e) 
{
	 var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();
	today = yyyy + '-' + mm + '-' + dd; 
	
	var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
	var month = document.getElementById("monthdtl").options[document.getElementById("monthdtl").selectedIndex].text;
	
	document.getElementById("finName").value=finName;
    document.getElementById("month").value=month;
    
    if(document.getElementById("monthdtl").value=='')
	{
		alert('Please select month !');
		$('#monthdtl').focus();
		e.preventDefault();
	}
	if(document.getElementById("year").value=='')
	{
		alert('Please select Year ! ');
		$('#year').focus();
		e.preventDefault();
	}
else{
		ShowContent("previewDiv");
		document.achievementForPrayas.action="getPrayasAchievement";
		document.achievementForPrayas.method="post";
		document.achievementForPrayas.submit();
		document.getElementById("view").disabled = true;
	}
	return false;
}	

function downloadPDF(year, monthdtl){
	var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var monthname = document.getElementById("monthdtl").options[document.getElementById("monthdtl").selectedIndex].text;
    document.getElementById("finName").value=finName;
    document.getElementById("monthname").value=monthname;
	
    document.getElementById("year").value=year;
    document.getElementById("monthdtl").value=monthdtl;
    document.achievementForPrayas.action="downloadAchievementofDishaPDF";
	document.achievementForPrayas.method="post";
	document.achievementForPrayas.submit();
	}

function exportExcel(year, monthdtl){
	
	var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var monthname = document.getElementById("monthdtl").options[document.getElementById("monthdtl").selectedIndex].text;
  //  alert(stName+distName+projName+yearName);
    document.getElementById("finName").value=finName;
    document.getElementById("monthname").value=monthname;
	
    document.getElementById("year").value=year;
    document.getElementById("monthdtl").value=monthdtl;
    document.achievementForPrayas.action="downloadExcelAchievementofDisha";
	document.achievementForPrayas.method="post";
	document.achievementForPrayas.submit();
	}

function exportDistExcel(year,monthdtl,stCode, stname){
	var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var monthname = document.getElementById("monthdtl").options[document.getElementById("monthdtl").selectedIndex].text;
    document.getElementById("finName").value=finName;
    document.getElementById("monthname").value=monthname;
    document.getElementById("stname").value=stname;
    document.getElementById("stCode").value=stCode;
    document.getElementById("year").value=year;
    document.getElementById("monthdtl").value=monthdtl;
     document.achievementForPrayas.action="downloadExcelDistAchievementofDisha";
	document.achievementForPrayas.method="post";
	document.achievementForPrayas.submit();
}

function exportDistPDF(year, monthdtl,stCode, stname){
	var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var monthname = document.getElementById("monthdtl").options[document.getElementById("monthdtl").selectedIndex].text;
    document.getElementById("finName").value=finName;
    document.getElementById("monthname").value=monthname;
    document.getElementById("stname").value=stname;
    document.getElementById("stCode").value=stCode;
    document.getElementById("year").value=year;
    document.getElementById("monthdtl").value=monthdtl;
    document.achievementForPrayas.action="downloadDistAchievementofDishaPDF";
	document.achievementForPrayas.method="post";
	document.achievementForPrayas.submit();
	}
	
</script>

<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas </h5></div>

<br>
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="achievementForPrayas" name="achievementForPrayas" id="achievementForPrayas" method="get">
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
		
     <input type="hidden" name="finName" id="finName" value="" />
	 <input type="hidden" name="month" id="month" value="" />
	 <input type="hidden" name="monthname" id="monthname" value="" />
	 <input type="hidden" name="stname" id="stname" value="" />
	<input type="hidden" name="stCode" id="stCode" value="" />
			
<table style="width:100%; align-content: center;" >
        <tr align="center" >
        <td><b>Financial Year <span style="color: red;">*</span></b></td>
           <td>
              <select name="year" id="year"  onchange="this.form.submit();" required="required">
              		<option value="0">-- ALL --</option>
						  <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       								  
       							</c:if>	
       							<c:if test="${lists.finYrCd ne year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
								</c:forEach>
						</c:if>  
              </select>
          </td>
          <td><b>Month <span style="color: red;">*</span></b></td>
          <td>
             <select  id="monthdtl" name="monthdtl" required="required">
    			<option value="0">-- ALL --</option>
     			 <c:if test="${not empty months}">
               			<c:forEach items="${months}" var="lists">
               				<c:if test="${lists.monthId eq monthdtl}">
               				<option value="<c:out value='${lists.monthId}'/>" selected="selected" ><c:out value="${lists.monthName}" /></option>
               				</c:if>
               				<c:if test="${lists.monthId ne monthdtl}">
               				<option value="<c:out value='${lists.monthId}'/>"  ><c:out value="${lists.monthName}" /></option>
       					</c:if>
       					</c:forEach>
				</c:if>  
    		</select>
          
          
          </td>
          
          
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Report' /> </td> </td>
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
</div>
</div>
<br>
</div>

<div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
<c:if test="${dataList != null && showdist == null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${year}','${monthdtl}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${year}','${monthdtl}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>

<c:if test="${dataList != null && showdist != null}">
<button name="exportDistExcel" id="exportDistExcel" onclick="exportDistExcel('${year}','${monthdtl}','${stCode}','${stname}')" class="btn btn-info">Excel</button>
<button name="exportDistPDF" id="exportDistPDF" onclick="exportDistPDF('${year}','${monthdtl}','${stCode}','${stname}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>

<c:if test="${showdist == null}">
<table id="tblReport" cellspacing="0" class="table" style="width:100%;">
  <thead>
	 <tr>
		<th colspan="9" style="text-align:right; "></th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">State Name</th> 
      <th rowspan="2"  style="text-align:center" rowspan="2">Area of degraded land covered and Rainfed area developed (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Area covered with Soil and Moisture conservation activities (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Area brought under Plantation Horticulture and Afforestation (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Water Harvesting Structures newly created and rejuvenated (in no.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Farmers benefitted (in no.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Additional Area brought under Protective irrigation (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Employment Generated (in mandays)</th>
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
		<th class="text-center">9</th>
		
		
		</tr>
		<c:if test="${dataList != null}">
		
		<c:forEach items="${dataList}" var="dataV" varStatus="status">
		<tr>		
			<td align="right"><c:out value='${dataV[0]}' /></td>
			<td><a href="getDistWiseAchievementforPrayas?stcode=<c:out value="${dataV[1]}"/>&stname=<c:out value="${dataV[2]}"/>&finCode=<c:out value="${year}"/>&month=<c:out value="${monthdtl}"/> "><c:out value='${dataV[2]}'/></a></td>
            <td align="right"><c:out value='${dataV[9]}' /></td>
			<td align="right"><c:out value='${dataV[3]}' /></td>
			<td align="right"><c:out value='${dataV[4]}' /></td>
			<td align="right"><c:out value='${dataV[5]}' /></td>
			<td align="right"><c:out value='${dataV[8]}' /></td>
			<td align="right"><c:out value='${dataV[6]}' /></td>
			<td align="right"><c:out value='${dataV[7]}' /></td>
		</tr>	
		
		</c:forEach>
		<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary"></td>
				<td align="right" class="table-primary" ><b>Grand Total </b></td>
				
			<%-- 	<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[4]}' /></fmt:formatNumber> </b></td> 
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[5]}' /></fmt:formatNumber> </b></td>--%>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[0]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[1]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[2]}' /> </fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[3]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="2" value="${netTotal[4]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="2" value="${netTotal[5]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="2" value="${netTotal[6]}"/></b></td>
				
				
				</tr>
	</c:forEach> 
		</c:if>
		</tbody>
</table>
</c:if>

<c:if test="${showdist != null}">
<table id="tblReport" cellspacing="0" class="table" style="width:100%;">
  <thead>
	 <tr>
		<th colspan="9" class="text-left"> State Name : &nbsp; <c:out value='${stname}' /></th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">District Name</th> 
      <th rowspan="2"  style="text-align:center" rowspan="2">Area of degraded land covered and Rainfed area developed (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Area covered with Soil and Moisture conservation activities (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Area brought under Plantation Horticulture and Afforestation (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Water Harvesting Structures newly created and rejuvenated (in no.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Farmers benefitted (in no.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Additional Area brought under Protective irrigation (in ha.)</th>
      <th rowspan="2"  style="text-align:center" rowspan="2">Employment Generated (in mandays)</th>
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
		<th class="text-center">9</th>
		
		
		</tr>
		<c:if test="${dataList != null}">
		
		<c:forEach items="${dataList}" var="dataV" varStatus="status">
		<tr>		
			<td><c:out value='${dataV[0]}' /></td>
			<td><c:out value='${dataV[4]}'/></td>
			<td><c:out value='${dataV[11]}' /></td>
			<td><c:out value='${dataV[5]}' /></td>
			<td><c:out value='${dataV[6]}' /></td>
			<td><c:out value='${dataV[7]}' /></td>
			<td><c:out value='${dataV[10]}' /></td>
			<td><c:out value='${dataV[8]}' /></td>
			<td><c:out value='${dataV[9]}' /></td>
		</tr>	
		
		</c:forEach>
		<c:forEach items="${dataListNetTotal}" var="netTotal" varStatus="seqTotal">
			<tr>
			<td class="table-primary"></td>
				<td align="right" class="table-primary" ><b>Grand Total </b></td>
				
			<%-- 	<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[4]}' /></fmt:formatNumber> </b></td> 
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="4"><c:out value='${netTotal[5]}' /></fmt:formatNumber> </b></td>--%>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[0]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[1]}' /></fmt:formatNumber> </b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[2]}' /> </fmt:formatNumber></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" minFractionDigits="2"><c:out value='${netTotal[3]}' /></fmt:formatNumber> </b></td>
				
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="2" value="${netTotal[4]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="2" value="${netTotal[5]}"/></b></td>
				<td align="right" class="table-primary" ><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="2" value="${netTotal[6]}"/></b></td>
				
				
				</tr>
	</c:forEach> 
		</c:if>
		</tbody>
</table>
</c:if>

</div>
</div>
</div>
</body>
</html>
			



      



