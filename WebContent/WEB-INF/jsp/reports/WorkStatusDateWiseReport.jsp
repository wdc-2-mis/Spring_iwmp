<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Report ME8- State and Date Wise Work Status List</title>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" ></script>

<script type="text/javascript">
function showReport(e)
{
	
	var state = $('#state').val();
	var userdate = $('#userdate').val();
	var userdateto = $('#userdateto').val();
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	document.getElementById("stName").value=stName;
	
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	
	if(userdate==='')
	{
		$('#userdateto').val('');
		alert('Please select From Date ');
		$('#userdate').focus();
		e.preventDefault();
	}
	if(userdateto==='')
	{
		alert('Please select To Date ');
		$('#userdateto').focus();
		e.preventDefault();
	}
	
	/* if(userdateto!='')
	{
		if(userdate==='')
		{
			$('#userdateto').val('');
			alert('Please select From Date ');
			$('#userdate').focus();
			e.preventDefault();
		}
	}  
	 	if(userdateto!='' && userdate!='')
	{
		const dateTimeStr = $('#userdate').val();
		const dateTimeStrto = $('#userdateto').val();
		const dateObj = new Date(dateTimeStr); 
		const dateObjto = new Date(dateTimeStrto); 
	if (dateObjto < dateObj) {
			alert('From date Can not be greater than To date');
			$('#userdate').val('');
			$('#userdateto').val('');
		}  
	} */
	else{
		
		document.workstatusr.action="getWorkStatusReportpost";
		document.workstatusr.method="post";
		document.workstatusr.submit();
	}
	return false;
} 

function downloadPDF(state, userdate, dateto){
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
  
    document.getElementById("stName").value=stName;
    document.getElementById("userdate1").value=userdate;
    document.getElementById("userdate2").value=dateto;
	
    document.workstatusr.action="downloadWorkStatusReportPDF";
	document.workstatusr.method="post";
	document.workstatusr.submit();
}


function downloadExcel(state, userdate, dateto){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
  
    document.getElementById("stName").value=stName;
    document.getElementById("userdate1").value=userdate;
    document.getElementById("userdate2").value=dateto;
	
    document.workstatusr.action="downloadExcelWorkStatusReport";
	document.workstatusr.method="post";
	document.workstatusr.submit();
}


</script>
</head>



 <br>
 <c:if test="${state==0}">
	<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report ME8- State and Date Wise Work Status List</h5></div>
</c:if>
<c:if test="${state gt 0}">
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5>Report ME8- Activity and Date Wise Work Status List</h5></div>
</c:if>
<br>
<div class ="card">
<div class="row">
<div class="col-1" ></div>
<div class="col-10">

<div class="table-responsive" id="exportHtmlToPdf1">

 <form:form action="getWorkStatusReport" name="workstatusr" id="workstatusr" method="get">
	<!-- <div id="waitDiv" 
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
			</div> -->
	
   		<input type="hidden" name="stName" id="stName" value="" />
		<input type="hidden" name="userdate1" id="userdate1" value="" />
		<input type="hidden" name="userdate2" id="userdate2" value="" />
		
      <table style="width:100%; align-content: center;" >
        <tr align="center" >
        
          <td><b>State <span style="color: red;">*</span></b></td>
          <td>
             <select  id="state" name="state" required="required">
    			<option value="0">--All--</option>
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
          <td><b>From Date :  <span style="color: red;">*</span></b></td>
           <td>
               <input type="date" name="userdate" id="userdate" class="form-control activity" style="width: 100%;" />
          </td>
          <td ><b>To Date <span style="color: red;">*</span></b></td>
          <td>
              <input type="date" name="userdateto" id="userdateto" class="form-control activity" style="width: 100%;" />
          </td>
        
           <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Report' /> </td>
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
	</div>
	<div class ="card">
	
	<div class="row">
	<div class="col-1" ></div>
	<div class="col-10"  id="exportHtmlToPdf">
<c:if test="${not empty dataList}">

<button name="exportExcel" id="exportExcel" onclick="downloadExcel('${state}','${userdate}', '${dateto}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${state}', '${userdate}', '${dateto}')" class="btn btn-info">PDF</button>

</c:if>

<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<br/>
<table id="tblReport" cellspacing="0" class="table" width="auto">
  <thead>
	 <tr> 
	 	<th colspan="7" style="text-align:left; "><b>State :</b> ${stName} &emsp; <b>From Date :</b> ${fromDateStr} &emsp; <b> To Date :</b> ${toDateStr}</th>
		
	</tr>
	<c:if test="${state==0}">
    <tr>
     	<th style="text-align:center; vertical-align: middle;">S.No.</th>  
		<th style="text-align:center; vertical-align: middle;">State Name</th>
		<th style="text-align:center; vertical-align: middle;">Created Works</th> 
		<th style="text-align:center; vertical-align: middle;">Started Works</th> 
		<th style="text-align:center; vertical-align: middle;">Ongoing Works</th>
 		<th style="text-align:center; vertical-align: middle;">Completed Works</th>  
		<th style="text-align:center; vertical-align: middle;">Foreclosed Works</th>
     </tr>
   </c:if>
   <c:if test="${state gt 0}">
    <tr>
     	<th style="text-align:center; vertical-align: middle;">Name of the Activity</th>  
		<th style="text-align:center; vertical-align: middle;">Unit</th>
		<th style="text-align:center; vertical-align: middle;">Created Works</th> 
		<th style="text-align:center; vertical-align: middle;">Started Works</th> 
		<th style="text-align:center; vertical-align: middle;">Ongoing Works</th>
 		<th style="text-align:center; vertical-align: middle;">Completed Works</th>  
		<th style="text-align:center; vertical-align: middle;">Foreclosed Works</th>
     </tr>
   </c:if>
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
	 
	 			<c:if test="${dataList != null}">
		 			<c:if test="${state== 0}">
						<c:forEach items="${dataList}" var="data" varStatus="count">
 							<tr>
								<td><c:out value='${count.count}' /></td>
								<td><c:out value="${data.st_name}" /></td>
 								<td align="right"><c:out value="${data.createdwork}" /></td>
 								<td align="right"><c:out value="${data.startedwork}" /></td>
 								<td align="right"><c:out value="${data.ongoing}" /></td>
								<td align="right"><c:out value="${data.completed}" /></td>
								<td align="right"><c:out value="${data.forclosed}" /></td>
 								
 							</tr>
 						</c:forEach>
 						<tr>
							<td align="right" class="table-primary" colspan="2"><b>Grand Total </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${createdwork1}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${startedwork1}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${ongoing1}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${completed1}' /> </b></td>
							<td align="right" class="table-primary" ><b><c:out value='${forclosed1}' /> </b></td>
	 					</tr>
	 				</c:if>	
	 				</c:if>
	 				
	 <c:if test="${state gt 0}">
	 	<c:set var="counter" value="0" />
		<c:set var="count1" value="1" />
		<c:set var="headVal" value="" />
		<c:set var="actVal" value="" />
		<c:set var="hdCount" value="1" />
		<c:set var="actCount" value="1" />
		<c:set var="subActCount" value="1" />
		<c:set var="colTotal" value="0" />
				
		<c:set var="countHead" value="0" />
		<c:set var="headOld" value="" />
		<c:set var="keyPlc" value="0" />
		<c:set var="headYrSum" value="0" />
						
		<c:set var="totPhyTar" value="" />
		<c:set var="totPhyAch" value="" />
		<c:set var="totWrk" value="" />
		<c:set var="totOnWrk" value="" />
		<c:set var="totCoWrk" value="" />
		<c:set var="totYrlyWrk" value="" />
		<c:set var="totClsWrk" value="" />
		<c:set var="unitName" value="" />
						
		<c:set var="grndtotWrk" value="" />
		<c:set var="grndtotOnWrk" value="" />
		<c:set var="grndtotCoWrk" value="" />
		<c:set var="grndtotYrlyWrk" value="" />
		<c:set var="grndtotClsWrk" value="" />
		
	 <c:if test="${dataListact != null}">
	 				
	 	<c:forEach items="${dataListact}" var="dataListSh">
	
			<c:if test="${countHead == 0}">
				<c:set var="countHead" value="1" />
				<c:set var="headOld" value="${dataListSh[2]}" />
			</c:if>

			<c:if test="${headOld != dataListSh[2]}">
				<c:set var="headYrSum" value="0" />
				
				<c:set var="countHead" value="${countHead + 1}" />
				<c:set var="headOld" value="${dataListSh[2]}" />
				<c:set var="keyPlc" value="${keyPlc + 1}" />
			</c:if>
			
			<c:if test="${dataListSh[1] != null}">
						<c:if test="${headVal != dataListSh[0]}">
							<c:if test="${hdCount != 1 && dataListSh[0] lt 9}">
							<tr>
							<th colspan ="2" class="text-center">Sub-Total</th>
							<%-- <c:if test = "${unitName eq 'ha' }">
							<th class="text-right"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${totPhyTar}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${totPhyAch}"/></th>
							</c:if>
							<c:if test = "${unitName eq 'nos' }">
							<th class="text-right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${totPhyTar}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${totPhyAch}"/></th>
							</c:if>
							<th class="text-right"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${(totPhyAch*100)/totPhyTar}"/>%</th> --%>
							<th class="text-right"><c:out value="${totWrk}"/></th>
							<th class="text-right"><c:out value="${totOnWrk}"/></th>
							<th class="text-right"><c:out value="${totCoWrk}"/></th>
							<th class="text-right"><c:out value="${totYrlyWrk}"/></th>
							<th class="text-right"><c:out value="${totClsWrk}"/></th>
							</tr>
							</c:if>
								<c:set var="totPhyTar" value="" />
								<c:set var="totPhyAch" value="" />
								<c:set var="totWrk" value="" />
								<c:set var="totOnWrk" value="" />
								<c:set var="totCoWrk" value="" />
								<c:set var="totYrlyWrk" value="" />
								<c:set var="totClsWrk" value="" />
							<tr>
								<td class="altrow">
								<SPAN>
								<c:out value="${dataListSh[1]}" /> 
								</SPAN> 
								<br> 
								<c:set	var="headVal" value="${dataListSh[0]}" /> 
								<c:set  var="hdCount" value="${hdCount + 1}" /> 
								<c:set var="actCount" value="1" /> 
								<c:set var="subActCount" value="1" />
								</td>
								<!-- <td></td>
								<td></td>
								<td></td> -->
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:if>
						
						<c:if test="${actVal != dataListSh[2]}">
							<tr>
								<td class="altrow">
									<span style="padding-left: 20px;">
										<c:out value="${dataListSh[3]}" /> 
									</span> 
									<br>
									<c:set var="actVal" value="${dataListSh[2]}" />
									<c:set var="actCount" value="${actCount + 1}" />
								</td>
								<td class="altrow">
									<c:out value="${dataListSh[4]}" />
								</td>
									
								<%-- <td align="right"> 
									<c:if test="${dataListSh[4]=='nos'}"> 
										<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataListSh[5]}"/>
									</c:if>
									<c:if test="${dataListSh[4]!='nos'}"> 
										<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${dataListSh[5]}"/>
									</c:if>
								</td>
								<td align="right">
									<c:if test="${dataListSh[4]=='nos'}"> 
										<fmt:formatNumber type="number" maxFractionDigits="0" minFractionDigits="0" value="${dataListSh[6]}"/>
									</c:if>
									<c:if test="${dataListSh[4]!='nos'}"> 
										<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${dataListSh[6]}"/>
									</c:if>
								</td>
								<td align="right">
									<c:out value="${dataListSh[11]}" /><b>%</b>
								</td> --%>
								<td align="right">
									<c:out value="${dataListSh[5]}" />
								</td>
								<td align="right">
									<c:out value="${dataListSh[6]}" />
								</td>
								<td align="right">
									<c:out value="${dataListSh[7]}" />
								</td>
								<td align="right">
									<c:out value="${dataListSh[8]}" />
								</td>
								<td align="right">
									<c:out value="${dataListSh[9]}" />
								</td>
								<c:set var="unitName" value="${dataListSh[4]}" />
							<%-- 	<c:set var="totPhyTar" value="${totPhyTar + dataListSh[5]}" />
								<c:set var="totPhyAch" value="${totPhyAch + dataListSh[6]}" /> --%>
								<c:set var="totWrk" value="${totWrk + dataListSh[5]}" />
								<c:set var="totOnWrk" value="${totOnWrk + dataListSh[6]}" />
								<c:set var="totCoWrk" value="${totCoWrk + dataListSh[7]}" />
								<c:set var="totYrlyWrk" value="${totYrlyWrk + dataListSh[8]}" />
								<c:set var="totClsWrk" value="${totClsWrk + dataListSh[9]}" />
								
						</c:if>
							
					</c:if>
								<c:set var="grndtotWrk" value="${grndtotWrk + dataListSh[5]}" />
								<c:set var="grndtotOnWrk" value="${grndtotOnWrk + dataListSh[6]}" />
								<c:set var="grndtotCoWrk" value="${grndtotCoWrk + dataListSh[7]}" />
								<c:set var="grndtotYrlyWrk" value="${grndtotYrlyWrk + dataListSh[8]}" />
								<c:set var="grndtotClsWrk" value="${grndtotClsWrk + dataListSh[9]}" />
	
	</c:forEach>
	 				
	 				
	 					</c:if>
	 </c:if>
    			
    	<c:if test="${dataListsize==0}">
			<tr>
				<td align="center" colspan="7" class="required" style="color:red;">Data Not Found</td>
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
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

document.getElementById('userdateto').addEventListener('change', function() {
    var fromDate = document.getElementById('userdate').value;
    if (!fromDate) {
        alert('Please select From Date first.');
        this.value = ''; // Reset the To Date
        $('#userdate').focus();
    }
});


</script>	

 <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>