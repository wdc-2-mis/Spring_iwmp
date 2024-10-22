<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Report PF7</title>
<script src='<c:url value="/resources/js/jquery-3.5.1.js" />'></script>
<script
	src='<c:url value="/resources/js/1.12.1jquery.dataTables.min.js" />'></script>
<script
	src='<c:url value="/resources/js/2.2.3dataTables.buttons.min.js" />'></script>
<script src='<c:url value="/resources/js/jszip.min.js" />'></script>
<script src='<c:url value="/resources/js/pdfmake.min.js" />'></script>
<script src='<c:url value="/resources/js/1.53vfs_fonts.js" />'></script>
<script
	src='<c:url value="/resources/js/export/buttons.html5.min.js" />'></script>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/bootstrap/jquery.dataTables.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/2.2.3buttons.dataTables.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">


<script type="text/javascript">
function downloadPDF(stCode, comp, fromYear){
	document.getElementById("stCode").value=stCode;
	document.getElementById("comp").value=comp;
	document.getElementById("fromYear").value=fromYear;
	document.pfmscomp.action="downloadStCompExpndtrPDF";
	document.pfmscomp.method="post";
	document.pfmscomp.submit();
}
function exportExcel(stCode, comp, fromYear){
	document.getElementById("stCode").value=stCode;
	document.getElementById("comp").value=comp;
	document.getElementById("fromYear").value=fromYear;
	document.pfmscomp.action="getStWiseComponentExpndtrExcel";
	document.pfmscomp.method="post";
	document.pfmscomp.submit();
}

</script>
</head>


<body>
 <br>
<div class="offset-md-2 col-8 formheading" style="text-align:center;"  ><h5>Report PF7- State Wise Component Expenditure</h5></div>

<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getPfmsComponentReport" method="post" name="pfmscomp" id="pfmscomp">
	<input type="hidden" id="stCode" name="stCode" value="${stcode}" />
	<input type="hidden" id="froYear" name="froYear" value="${fromYear}" />
	<input type="hidden" id="comp" name="comp" value="${comp}" />
	
	
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
          <select class="form-control project" id="state" name="state" required>
    			<option value="0">--All States--</option>
     			<c:forEach items="${stateList}" var="state">
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${state.value}" /></option>
				</c:forEach>
    		</select>

          </td>
          <td><b>Component Name<span style="color: red;">*</span></b></td>
          <td>
             <select class="form-control district" id="component" name="component" required="required" multiple="multiple">
      			<c:forEach items="${compList}" var="comp">
							<option value="<c:out value="${comp.key}"/>" <c:out value="${comp.key== Component_code ?'selected':'' }"/>>
							<c:out 	value="${comp.value}" /></option>
				</c:forEach>
    		</select>
          </td>
          
          <td ><b>Financial Year <span style="color: red;">*</span></b></td>
          <td >
              <select class="form-control fromyear" id="fromyear" name="fromYear" required="required">
      			<option value="">--Select Year--</option>
      			<c:forEach items="${yearList}" var="yearl">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== fromYear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
          </td>
         
          <td  align="center" class="label"> 
          <input type="submit" class="btn btn-info" id="btnGetReport"  name="btnGetReport" value="Get Report" />
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	  <input type="hidden" name="stCode" id="stCode" value="" />
	<input type="hidden" name="fromYear" id="fromYear" value="" />
	<input type="hidden" name="comp" id="comp" value="" />
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
	
	 <c:if test="${not empty dataList}">
	<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}','${comp}','${fromYear}')" class="btn btn-info">Excel</button>
	
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}','${comp}','${fromYear}')" class="btn btn-info">PDF</button> </c:if>  
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="tblReport" cellspacing="0" class="table">
  <thead>
	<c:if test="${dataListsize>0}">
	<tr>
      		<th style="text-align:right;" colspan="${cmpntMapSize+2}">All amount in Rs.</th>
      	</tr></c:if>
      	<c:if test="${dataListsize>0}">
						<tr>
						<c:if test="${stCode eq 0 }">
							<th colspan="${cmpntMapSize+2}">State : All State &nbsp; 
									 &nbsp;&nbsp;&nbsp; Financial Year :
								&nbsp; <c:out value='20${fromYear}-20${fromYear+1}' /> &nbsp;&nbsp;&nbsp;

							</th></c:if>
							<c:if test="${stCode gt 0 }">
							<th colspan="${cmpntMapSize+2}">State : &nbsp; <c:out
									value='${stName }' /> &nbsp;&nbsp;&nbsp; Financial Year :
								&nbsp; <c:out value= '20${fromYear}-20${fromYear+1}' /> &nbsp;&nbsp;&nbsp;

							</th></c:if>
						</tr></c:if>
						<tr>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 1%;">S.No.</th>
      		<th rowspan="2" style="text-align:center; vertical-align: middle; width: 2%;">State Name</th>
      		<th colspan="${cmpntMapSize}" style="text-align:center; vertical-align: middle; width: 7%;">Component Name </th>
       	</tr>
       	
     <tr>
       		<c:forEach var="cmp" items="${cmpntMap}">
      			<th style="text-align:center; vertical-align: middle; width: 7%;"><c:out value="${cmp.value}"/></th>
      		</c:forEach>
       	</tr>
  </thead>

  <tbody>
<tr>
		<c:if test="${dataListsize>0}">
			<th class="text-center">1</th>
			<th class="text-center">2</th>
			<c:set var ="count1" value ="1"/>
			<c:set var ="stcode" value ="0"/>
			<c:set var ="fromYear" value ="0"/>
			<c:forEach var="cmp" items="${cmpntMap}">
      			<th class="text-center"><c:out value="${count +3}"/></th>
      			<c:set var ="count" value ="${count +1}"/>
      		</c:forEach></c:if>
		</tr>
   				   <c:forEach items="${dataList}" var="proj" varStatus="status">
								<tr>
								<c:if test="${proj.st_code ne stcode}">
								<td align="center">${count1}</td>
									<td><a href="getdistWisePfmsComponentReport?stcode=${proj.st_code}&stname=${proj.stname}&comp=${comp}&fromYear=${proj.financial_year}"><c:out value="${proj.stname}"/></a></td>
									<c:forEach var="stcmp" items="${stcmpMap}">
										<c:if test= "${proj.st_code eq  fn:substringBefore(stcmp.key,',')}">
											<td><c:out value="${stcmp.value}" /></td>
										</c:if>
									</c:forEach>
									<c:set var ="count1" value ="${count1 + 1}"/>
								</c:if>
								<c:set var ="stcode" value ="${proj.st_code}"/>

									</c:forEach>
									<c:if test="${dataListsize>0}">                        
									<tr>
			<td colspan="2" class="table-primary" align="right"><strong>Grand Total</strong></td>
				<c:forEach  var = "totstmap" items ="${totstmap}">
					<td class="table-primary" style="text-align:center;"><b><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${totstmap.value}"/></b></td>
				</c:forEach>
			</tr></c:if>
									<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="${cmpntMapSize+2}" class="required">Data Not Found</td>
			</tr>
		</c:if>
  </tbody>
</table>
   
<br/>  
        </div>
        </div>
    <br>
	
	</div>

<script type="text/javascript">
		$(document).ready(function() {
			$(".sidebar-btn").click(function() {
				$(".wrapper").toggleClass("collapse");
			});
		});
	</script>
	<footer class=" text-center">

	</footer>
</body>
</html>