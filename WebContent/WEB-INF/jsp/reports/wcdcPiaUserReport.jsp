<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<!DOCTYPE html>
<html>
<head>
<title>Report OT2- List of Account Created At District Level</title>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/userDetailSA.js" />'></script>


<script type="text/javascript">

function getWcdcPiaUser(){
	var state = $('#state').val();
	
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	
	else{
		
		document.wcdcPiaUser.action="wcdcPiaUserList";
		document.wcdcPiaUser.method="post";
		document.wcdcPiaUser.submit();
	}
	return false;
}

function exportPDF(){
	var table="";
	var reportName = "List of Account Created At District/Project Level";
	var tableName = "#example";
	var columnSpan =false;
	var colLength=$("#example tr:first th").length;
	if ( ! $.fn.DataTable.isDataTable( tableName ) ) {
		createDataTable(reportName,tableName,columnSpan,colLength);
		}
	//createDataTable(headerTitle,reportName,tableName);
	//trigger pdf button of datatable
	table=$(tableName).DataTable().buttons(0,1).trigger();
	table.buttons('.buttons-pdf').nodes().css("display", "none");
	table.buttons('.buttons-excel').nodes().css("display", "none");
	}
function downloadWCDCExcel(state,userType)
	{
			document.getElementById("state").value=state;
			document.getElementById("userType").value=userType;
			document.wcdcPiaUser.action="downloadExcelWCDCUserList";
			document.wcdcPiaUser.method="post";
			document.wcdcPiaUser.submit();	
	}

$(document).on('change','#userType',function(e){
	document.wcdcPiaUser.action="wcdcPiaUser";
	document.wcdcPiaUser.method="get";
	document.wcdcPiaUser.submit();
});


$(document).on('change','#state',function(e){
	document.wcdcPiaUser.action="wcdcPiaUser";
	document.wcdcPiaUser.method="get";
	document.wcdcPiaUser.submit();
});

function downloadPIAExcel(state,district,userType)
{
		document.getElementById("state").value=state;
		document.getElementById("district").value=district;
		document.getElementById("userType").value=userType;
		document.wcdcPiaUser.action="downloadExcelPIAUserList";
		document.wcdcPiaUser.method="post";
		document.wcdcPiaUser.submit();	
}

		function downloadPDF(state,userType){
			document.getElementById("state").value=state;
   		    document.getElementById("userType").value=userType;
            document.wcdcPiaUser.action="downloadwcdcPiaUserListPDF";
	        document.wcdcPiaUser.method="post";
	        document.wcdcPiaUser.submit();
	}
		
		function downloadPIAPDF(state,district,userType)
		{
				document.getElementById("state").value=state;
				document.getElementById("district").value=district;
				document.getElementById("userType").value=userType;
				document.wcdcPiaUser.action="downloadPIAUserListPDF";
				document.wcdcPiaUser.method="post";
				document.wcdcPiaUser.submit();	
		}

</script>

</head>
<body>
<h5><div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><c:if test="${userType ne 'WCDC' && userType ne 'PIA'}">Report OT2- List of Account Created At District/ Project Level </c:if>
			 <c:if test="${userType eq 'WCDC'}">
								 Report OT2- List of Account Created At District Level</c:if> 
								
								 <c:if test="${userType eq 'PIA'}"> Report OT2- List of Account Created At Project Level  </c:if>
								</div> </h5>
<%-- <c:if test="${userType eq 'WCDC' || userType==null }"> --%>
<!-- <div class="offset-md-3 col-6 formheading" style="text-align:center;" ><h5>Report OT2- List of Account Created At WCDC Level</h5></div> -->
<%-- </c:if> --%>
<%-- <c:if test="${userType eq 'PIA'}"> --%>
<!-- <div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report OT2- List of Account Created At Project Level</h5></div> -->
<%-- </c:if> --%>
<br>
<div class ="card">
<div class="row">
<div class="col-3" ></div>
<div class="col-8">

<div class="table-responsive">

 <form:form autocomplete="off" name="wcdcPiaUser" id="wcdcPiaUser"  modelAttribute="WcdcPiaUser" action="wcdcPiaUser" method="get">
<!--  <input type="hidden" name="state" id="state" value="" /> -->
<!-- 	<input type="hidden" name="district" id="district" value="" /> -->
<!-- 	<input type="hidden" name="userType" id="userType" value="" /> -->
      <table >
        <tr align="center">
        <td class="label">Type of User <span style="color: red;">*</span></td>
          <td>
          		<select name="userType" id="userType" onchange="this.form.submit();">
          		<c:if test="${userType eq 'WCDC' || userType==null }">
						<option value="WCDC">WCDC</option>
						<option value="PIA">PIA</option>
				</c:if>	
				<c:if test="${userType eq 'PIA'}">
						<option value="PIA">PIA</option>
						<option value="WCDC">WCDC</option>
				</c:if>		
				</select>
          </td>
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();">
              		<option value="0">All States</option>
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
          <c:if test="${userType eq 'PIA'}">
          <td class="label">District <span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" >
              		<option value="0">--All--</option>
                  	<c:if test="${not empty districtList}">
               			<c:forEach items="${districtList}" var="lists">
               				<c:if test="${lists.key eq district}">
       							<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne district}">
       							<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>	
						</c:forEach>
					</c:if> 
              </select>
          </td>
          <!-- <td>
          	<input type="checkbox" name="acHoldName" id="acHoldName"/> 
          	<label id="acHoldLebel">Account Holder Name </label>
          </td> -->
          </c:if>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="getWcdcPiaUser();" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>
 </div>
 </div>
<br>
	</div>
	<div class="row">
	<div class="col-2" ></div>
	<div class="col-8">
	<c:if test="${dataListSize>0}">
	<c:if test="${userType eq 'WCDC'}">
	<button name="exportExcel" id="exportExcel" onclick="downloadWCDCExcel('${state}','${userType}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}','${userType }')" class="btn btn-info">PDF</button></c:if>
<%-- 	<c:if test="${userType eq 'WCDC'}"> --%>
<%-- 	<button name="exportPDF" id="exportPDF" onclick="'${state}','${userType }')" class="btn btn-info">PDF</button></c:if> --%>
<%-- 	</c:if>	 --%>
	<c:if test="${userType eq 'PIA'}">
	<button name="exportExcel" id="exportExcel" onclick="downloadPIAExcel('${state}','${district}','${userType}')" class="btn btn-info">Excel</button>
	<button name="exportPDF" id="exportPDF" onclick="downloadPIAPDF('${state}','${district}','${userType}')" class="btn btn-info">PDF</button>
	</c:if>
	<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
	<!-- <a href="" onclick="exportExcel();">
	   <input name="excel" id="excel" src="WEB-INF/jsp/images/excel_icon.jpg" style="height:40px;width:30px;border-width:0px;" type="image" title="Download excel"/>
	</a>
	<a href="#" onclick="downexportPDF();">
	   <input name="pdf" id="pdf1" src="WEB-INF/jsp/images/pdf.jpg" style="height:40px;width:30px;border-width:0px;" type="image" title="PDF Report"/>
	</a> -->
	</c:if>
	
        <table class="table">
          <tr>
            <td>
            	<table id="example" class="table">
            	<thead>
              		<tr align="center">
              			<th align="center">S.No.</th>
		              	<th align="center">State Name</th>
                        <th align="center">District Name</th>
                        <c:if test="${userType eq 'PIA'}">
                        <th align="center">Project Name For which permission given</th>
                        <th align="center">Account Holder Name</th>
                        </c:if>
               		</tr>
               		</thead>
               		<tbody>
               		<tr>
	               		<th align="center"><b> 1 </b></th>
	               		<th align="center"><b> 2 </b></th>
	               		<th align="center"><b> 3 </b></th>
	               		<c:if test="${userType eq 'PIA'}">
		               		<th align="center"><b> 4 </b></th>
		               		<th align="center"><b> 5 </b></th>
	               		</c:if>
               		</tr>
               		
               		<c:if test="${dataListSize>0}">
							<c:forEach items="${dataList}" var="dataV" varStatus="count">
								<c:if test="${dataV[0]!='Total Users' && dataV[0]!='Total' }">
									<tr>
										<td class="p-3 mb-2 bg-light text-dark"><b><c:out value='${dataV[0]}' /> </b></td>
										<td class="p-3 mb-2 bg-light text-dark"><c:out value='${dataV[1]}' /></td>
										<td class="p-3 mb-2 bg-light text-dark"><c:out value='${dataV[2]}' /></td>
										<c:if test="${userType eq 'PIA'}">
										<td class="p-3 mb-2 bg-light text-dark"><c:out value='${dataV[3]}' /></td>
										<td class="p-3 mb-2 bg-light text-dark"><c:out value='${dataV[4]}' /></td>
										</c:if>
									</tr>
								</c:if>
								<c:if test="${dataV[0]=='Total'}">
									<tr>
										<td  align="right" class="table-info" />
										<td  align="right" class="table-info" />
										<td align="right" class="table-info" ><b><c:out value='${dataV[0]}' />:<c:out value='${dataV[1]}' /></b></td>
									</tr>
								</c:if>
								<c:if test="${dataV[0]=='Total Users'}">
									<tr>
									<td  align="right" class="table-info" />
									<td  align="right" class="table-info" />
									<td  align="right" class="table-info" />
										<td  align="right" class="table-info" ><b><c:out value='${dataV[3]}' />:<c:out value='${dataV[2]}' /></b></td>
										<td align="right" class="table-info" ><b><c:out value='${dataV[0]}' />:<c:out value='${dataV[1]}' /> </b></td>
									</tr>
								</c:if>
							</c:forEach>
							<tr>
							<c:if test="${userType eq 'PIA'}">
							<td  align="right" class="table-primary" />
								<td  align="right" class="table-primary" />
								<td  align="right" class="table-primary" />
								<td  align="right" class="table-primary" ><b>Grand Total :</b><b><c:out value='${dataArrNetTotalStr1}' /> </b></td>
								</c:if>
								<c:if test="${userType eq 'WCDC'}">
								<td  align="right" class="table-primary" />
								<td  align="right" class="table-primary" />
								</c:if>
								<td  align="right" class="table-primary" ><b>Grand Total :</b>&nbsp; <b><c:out value='${dataArrNetTotalStr}' /> </b></td>
							</tr>
						</c:if>
						</tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td>
            	<c:if test="${dataListSize<=0 }">
   					<table width="100%" border="0" cellspacing="0" cellpadding="0">
          				<tr class="tabs">
            				<td><center><span style="color: red;"> No Data Found !</span></center></td>
            				
          				</tr>
        			</table>
  				</c:if>
  			</td>
          </tr>
        </table>
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