<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report O12 - State and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">
function showReport(e) 
{
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
  //  alert(stName+distName+projName+yearName);
    document.getElementById("stName").value=stName;
    document.getElementById("finName").value=finName;
 
	if(document.getElementById("state").value=='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	if(document.getElementById("year").value=='')
	{
		alert('Please select Financial Year ! ');
		$('#year').focus();
		e.preventDefault();
	}
	
	else{
		
		ShowContent("previewDiv");
		document.indicators.action="getYrlyIncrmntInFrmrIncm";
		document.indicators.method="post";
		document.indicators.submit();
		document.getElementById("view").disabled = true;
	}
	return false;

}

	function exportExcel(state, year) {
		document.getElementById("state").value = state;
		document.getElementById("year").value = year;
		document.indicators.action = "getStWiseFrmrIncmExcel";
		document.indicators.method = "post";
		document.indicators.submit();

	}

	function downloadPDF(state, year) {
		document.getElementById("state").value = state;
		document.getElementById("year").value = year;
		document.indicators.action = "getStWiseFrmrIncmPDF";
		document.indicators.method = "post";
		document.indicators.submit();

	}
</script>
</head>


<body>
 <br>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report O12 - State and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).</h5></div>

<br>	
	<div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
	
	 <form:form action="indicators" name="indicators" id="indicators" method="get">
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
		
    <input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="finName" id="finName" value="" />
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select  id="state" name="state" required="required">
    			<option value="0">--All State--</option>
     			 <c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
				</c:if>  
    		</select>
          </td>
          <td><b>Financial Year <span style="color: red;">*</span></b></td>
           <td>
              <select name="year" id="year"  required="required">
              		<option value="0">--All Year--</option>
						  <c:if test="${not empty finMap}">
               					<c:forEach items="${finMap}" var="lists">
               					<c:if test="${lists.key eq year}">
       								<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       							</c:if>	
       							<c:if test="${lists.key ne year}">
       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
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
	
<c:if test="${finalList != null}">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${state}','${year}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${year}')" class="btn btn-info">PDF</button>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
</c:if>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <tr>
		<th colspan="${(finMapsize*2) +2}" style="text-align:right; ">All Area in Ha.  </th>
	</tr>
    <tr>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">S.No.</th>
      <th rowspan="2" style="text-align:center; vertical-align: middle;">State Name</th> 
      <th style="text-align:center" colspan="${finMapsize}">Change in Crop Area Year wise</th>
      <th style="text-align:center" colspan="${finMapsize}">Change in Farmer Income Year wise(in %)</th>
    </tr>
    
    <tr>
    <c:forEach var = "i" begin = "1" end = "2">
    	<c:forEach items="${finMap}" var="finMap" varStatus="status">
      		<th  class="text-center"><c:out value = "${finMap.value}"/></th>
     	</c:forEach>
     </c:forEach> 
    </tr>
    
  </thead>

  <tbody>
     <tr>
     	<c:forEach var = "i" begin = "1" end = "${(finMapsize*2) +2}">
     		<th class="text-center"><c:out value = "${i}"/></th>
     	</c:forEach> 
     </tr>

	 <c:if test="${finalList != null}">
		<c:set var = "count" value = "1"/>
		<c:set var = "area" value = "0"/>
		<c:set var = "frmr" value = "0"/>
		<c:forEach items="${finalList}" var="dataV" varStatus="status">
		
		<tr>		
			<td><c:out value='${count}' /></td>
			<td><a href="getDistWiseYrlyIncrmntInFrmrIncm?state=${dataV.stcode}&year=${year}&stname=${dataV.stname}&yrName=${yrName}"><c:out value='${dataV.stname}' /></a></td>
			<c:forEach items="${areaMap}" var="areaMap" varStatus="status">
				<c:if test ="${dataV.stcode eq fn:substringBefore(areaMap.key,',')}">
      					<td class="text-center"><c:out value = "${areaMap.value}"/></td>
      					<c:set var = "area" value = "${areaMap.value}"/>
				</c:if>
     		</c:forEach>
     		<c:forEach items="${frmrMap}" var="frmrMap" varStatus="status">
				<c:if test ="${dataV.stcode eq fn:substringBefore(frmrMap.key,',')}">
      					<td class="text-center"><c:out value = "${frmrMap.value}"/></td>
      					<c:set var = "frmr" value = "${frmrMap.value}"/>
				</c:if>
     		</c:forEach>
			
			
		</tr>	
		<c:set var = "count" value = "${count + 1}"/>	
		</c:forEach>
		
    </c:if>
    	<c:if test="${finalListSize==0}">
			<tr>
				<td align="center" colspan="${(finMapsize*2) +2}" class="required" style="color:red;">Data Not Found</td>
			</tr>
		</c:if>
    
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>
	
	

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>