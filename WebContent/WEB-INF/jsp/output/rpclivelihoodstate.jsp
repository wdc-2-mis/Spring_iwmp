<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<html>
<head>
<title>Report OC3- State wise details</title>
</head>
<script type="text/javascript">

function showDistWise(stateCode, stname, headtype)
{
	document.getElementById("headtypeh").value=headtype;
	document.getElementById("stateid").value=stateCode;
	document.getElementById("stname").value=stname;
	document.LiveProdEpa.action="getRptLiveProdEpaDist";
	document.LiveProdEpa.method="post";
	document.LiveProdEpa.submit();
}

function downloadPDF(head, stcd)
{
	if(document.getElementById("state").value==='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	if(document.getElementById("headtype").value==='')
	{
		alert('Please select Head ! ');
		$('#headtype').focus();
		e.preventDefault();
	}
	else{
		
		document.LiveProdEpa.action="downloadPDFRptLivProdEpa";
		document.LiveProdEpa.method="post";
		document.LiveProdEpa.submit();
	}
	return false;
	
}

function exportExcel(head, stcd)
{
	if(document.getElementById("state").value==='')
	{
		alert('Please select state !');
		$('#state').focus();
		e.preventDefault();
	}
	if(document.getElementById("headtype").value==='')
	{
		alert('Please select Head ! ');
		$('#headtype').focus();
		e.preventDefault();
	}
	else{
		
		document.LiveProdEpa.action="downloadExcelStgetRptLiveProdEpa";
		document.LiveProdEpa.method="post";
		document.LiveProdEpa.submit();
	}
	return false;
	
}
</script>

<div class="container-fluid">
	<div class="row">
		<div class="col text-center">
			<br />
			<div class="offset-md-3 col-6 formheading" style="text-align: center;"><h5><label id="head">Report OC3- State wise details of <c:if test="${headtypec eq 'production'}"> Production System</c:if> 
												  <c:if test="${headtypec eq 'epa'}">Entry point Activities(EPAs)</c:if>
						<c:if test="${headtypec eq 'livelihood'}"> Livelihood Activities for the Asst-less Person</c:if></label> </h5></div>
			<hr />
		</div>
	</div>
</div>

<div class="row">
	<div class="col-2"></div>
	<div class="col-12">

		<form action="getRptLiveProdEpa" method="post" id="LiveProdEpa" name="LiveProdEpa">
			<div class="form-row">
			<input type="hidden" name="stateid" id="stateid" value="" />
			<input type="hidden" name="headtypeh" id="headtypeh" value="" />
			<input type="hidden" name="stname" id="stname" value="" />
				<table style="width: 59%; margin-left: auto; margin-right: auto;">
					<tr align="center">


						<td><b>State <span style="color: red;">*</span></b></td>
						<td><select class="form-control project" id="state"
							name="state" required>
								<option value="0">--All--</option>
								<c:forEach items="${stateList}" var="state">
									<option value="<c:out value="${state.key}"/>"
										<c:out value="${state.key== stCode ?'selected':'' }"/>>
										<c:out value="${state.value}" /></option>
								</c:forEach>
						</select></td>

						<td><b>Head <span style="color: red;">*</span></b></td>
						<td>
							<select class="form-control project" name="headtype" id="headtype" required="required">
								<option value="">--Select Head--</option>
								<c:if test="${not empty headtype}">
	               					<c:forEach items="${headtype}" var="lists">
		               					<c:if test="${lists.key eq headtypec}">
		       								<option value="<c:out value='${lists.key}'/>" selected="selected"><c:out value="${lists.value}" /></option>
		       							</c:if>
		       							<c:if test="${lists.key ne headtypec}">
		       								<option value="<c:out value='${lists.key}'/>" ><c:out value="${lists.value}" /></option>
		       							</c:if>
									</c:forEach>
								</c:if> 
								<!-- 
								<option value="livelihood">Livelihood Activities for the Asst-less</option>
								<option value="production">Production</option>
								<option value="epa">EPA</option> -->
							</select>
						</td>

						<td align="center" class="label"><input type="submit"
							class="btn btn-info form-control" id="livelihood"
							name="livelihood" value="Submit"></td>
					</tr>
				</table>
			</div>
		</form>

		<div class="col-1"></div>
		<div class="col" id="reportDiv">
		<c:if test="${rptListSize gt 0 }">
		<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			<button name="exportExcel" id="exportExcel" onclick="exportExcel('${headtypec}', '${stCode}');"class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${headtypec}', '${stCode}');"class="btn btn-info">PDF</button>
			</c:if>
			
			<c:if test="${beanListEpaSize gt 0 }">
			<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
			<button name="exportExcel" id="exportExcel" onclick="exportExcel('${headtypec}', '${stCode}');"class="btn btn-info">Excel</button>
			<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${headtypec}', '${stCode}');"class="btn btn-info">PDF</button>
			
			</c:if>
			<c:if test="${rptListSize gt 0}">	
			<table id="dtBasicExample" cellspacing="0"  style="width: 60%; margin-left: auto; margin-right: auto;">
				<thead>
					<tr>
						<th width="3%" style="text-align: center;">S.No.</th>
						<th width="15%" style="text-align: center;">State Name</th>
						<th width="5%" style="text-align: center;">Total no. of Activities</th>
						<th colspan=5 class="text-center " width="35%">Total no. of beneficiaries</th>
					</tr>
				<tbody>
					<tr>
						<th colspan=3 class="text-center "></th>
						<th style="text-align: center;">SC</th>
						<th style="text-align: center;">ST</th>
						<th style="text-align: center;">Other</th>
						<th style="text-align: center;">Total</th>
						<th style="text-align: center;">Women</th>
					</tr>
				</tbody>
				</thead>

				<c:forEach items="${rptList}" var="rpt" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td><a href="#" onclick="showDistWise('${rpt.stcode}', '${rpt.stname}', '${headtypec}');">${rpt.stname}</a></td>
						<td align="right">${rpt.noactivity}</td>
						<td align="right">${rpt.sc}</td>
						<td align="right">${rpt.st}</td>
						<td align="right">${rpt.other}</td>
						<td align="right">${rpt.total}</td>
						<td align="right">${rpt.women}</td>
					</tr>
					<c:set var="totalnoactivity" 
				value="${totalnoactivity+rpt.noactivity}" />  
  				<c:set var="totalsc"  
 					value="${totalsc+rpt.sc}" />  
 				<c:set var="totalst" 
  					value="${totalst+rpt.st}" />  
 				<c:set var="totalother"  
  					value="${totalother+rpt.other}" />  
  					<c:set var="totaltotal"  
  					value="${totaltotal+rpt.total}" />  
  					<c:set var="totalwomen"  
  					value="${totalwomen+rpt.women}" />  
					
				</c:forEach>
				<tr> 
				<td class="table-primary"></td>
 				<td align="right" class="table-primary" ><b>Grand Total</b></td> 
 				<td align="right" class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td> 
				<td  align="right" class="table-primary"><b><c:out value="${totalsc}" /></b></td> 
 				<td align="right"  class="table-primary"><b><c:out value="${totalst}" /></b></td> 
 				<td  align="right" class="table-primary"><b><c:out value="${totalother}" /></b></td> 
 				<td  align="right" class="table-primary"><b><c:out value="${totaltotal}" /></b></td> 
 				<td align="right" class="table-primary"><b><c:out value="${totalwomen}"/></b></td>  
				
			</tr> 

			</table>
			
			</c:if>
			<c:if test="${beanListEpaSize gt 0}">	
			<table id="dtBasicExample" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;" >
				<thead>
					<tr>
						<th>S.No.</th>
						<th>State Name</th>
						<th>Total no. of Activities</th>
					</tr>
				
				<c:forEach items="${beanListEpa}" var="rpt" varStatus="status">
					<tr>
						<td align="center">${status.count}</td>
						<td><a href="#" onclick="showDistWise('${rpt.stcode}', '${rpt.stname}', '${headtypec}');">${rpt.stname}</a></td>
						<td>${rpt.noactivity}</td>
					</tr>
					<c:set var="totalnoactivity"  
 					value="${totalnoactivity+rpt.noactivity}" /> 
					
				</c:forEach>
				<tr> 
				<td class="table-primary"></td>
 				<td  align="right" class="table-primary "><b>Grand Total</b></td> 
 				<td  class="table-primary"><b><c:out value="${totalnoactivity}" /></b></td>
			</table>
			</c:if>
			
			<c:if test="${headtypec eq 'production'}"> 
				<c:if test="${rptListSize == 0 }">
				<table id="dtBasicExample" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;">
				<tr>
					<td align="center" colspan="8" class="required"> <font color="red"> Data Not Found </font></td>
				</tr>
				</table>
				</c:if>
			</c:if> 
			<c:if test="${headtypec eq 'epa'}">
			<c:if test="${beanListEpaSize == 0 }">
				<table id="dtBasicExample" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;">
				<tr>
					<td align="center" colspan="8" class="required"> <font color="red"> Data Not Found </font></td>
				</tr>
				</table>
				</c:if>
			</c:if>
			
			<c:if test="${headtypec eq 'livelihood'}">
				<c:if test="${rptListSize == 0 }">
				<table id="dtBasicExample" cellspacing="0" style="width: 60%; margin-left: auto; margin-right: auto;">
				<tr>
					<td align="center" colspan="8" class="required"> <font color="red"> Data Not Found </font></td>
				</tr>
				</table>
				</c:if>
			</c:if>	
			
			
			
		</div>

	</div>
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/>
<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>


</body>
</html>


