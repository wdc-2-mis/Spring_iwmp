<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
function validation() 
{
	if (document.getElementById("ddlProject").value =='') 
	{
		alert("Please select project");
		document.getElementById("ddlProject").focus();
		return;
	}
	document.delcompBase.action="getBaselineNewDraft";
	document.delcompBase.method="post";
	document.delcompBase.submit();
}

function exportExcel(){
	
	$projId = $('#ddlProject').val();
	alert($projId);
	document.getElementById("projId").value=$projId;
	document.delcompBase.action="getBaselineNewDraftExcel";
	document.delcompBase.method="post";
	document.delcompBase.submit();
}

function do_Delete(index, main, detail, detailtrans) 
{
	document.getElementById("main").value=main;
	document.getElementById("detail").value=detail;
	document.getElementById("detailtrans").value=detailtrans;
	var buttVal = document.getElementById("butt"+index).value;
	
	if (confirm(" Do you want to delete the data ?") == true) 
	{
			document.delcompBase.action="deleteBaselineNewDraft";
			document.delcompBase.method="post";
			document.delcompBase.submit();
	}
}

function completebsl() 
{ 
	if (document.getElementById("ddlProject").value =='') 
	{
		alert("Please select project and click on button Get Details");
		document.getElementById("ddlProject").focus();
		return;
	}
// 	 if (document.getElementById("villcode").checked !=true) 
// 	{
// 		alert("Please check on village checkbox. \n Or You have enter wrong plot no. Kindly delete it first.");
// 		document.getElementById("villcode").focus();
// 		return;
// 	} 
	if (confirm(" Have you filled all the plots data of selected village. \n  Do you want to lock the data ?") == true) 
	{
			document.delcompBase.action="completeBaselineNewDraft";
			document.delcompBase.method="post";
			document.delcompBase.submit();
	}
}

function selects(ele) {
    var checkboxes = document.getElementsByName('villcode');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

</script>
  <c:if test="${message != null}">
	<script>
	    alert("<c:out value='${message}'/>");
	</script> 
</c:if>
<div class="container-fluid">

<div class="col" style="text-align:center;"><h4>Finalization of Baseline Survey village wise</h4></div>
  <form name="delcompBase" id="delcompBase">
     <lable class="message badge badge-danger error"></lable>
     	<c:if test="${not empty msg}">
				<div class="form-group  col-md-12">
					<div class="errormessage">
						<label class="alert alert-danger"> ${msg}</label>
					</div>
				</div>
			</c:if>
     <input type="hidden" id="main" name="main" value="" />
     <input type="hidden" id="detail" name="detail" value=""  />
     <input type="hidden" id="detailtrans" name="detailtrans" value=""  />
      <hr/>
      <div class="form-row">
     <div class="form-group col-md-4">
      <label for="ddlProject"><b>Projects: </b></label>
    <select class="form-control ddlProject" id="ddlProject" name="ddlProject" >
    <option selected hidden=true value="">--Select--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>" <c:out value="${project.key== projectcd ?'selected':'' }"/>>
									<c:out value="${project.value}" /></option>
						</c:forEach>
    </select>
     </div>
      <div class="form-group col-md-4">
    		<label for="village">Village: </label>
    		<span class="villageError"></span>
   			 <select class="form-control block" id="village" name="village">
      		<option value="0">--All--</option>
      		<c:if test ="${villListSize>0 }">
      		 <c:forEach items="${villList}" var="village">
							<option value="<c:out value="${village.key}"/>" <c:out value="${village.key== vcode ?'selected':'' }"/>>
									<c:out value="${village.value}" /></option>
						</c:forEach>
			</c:if>
    		</select>
  		</div>
      
     <div class="form-group col-1">
      <label for="btnGetActivity"> &nbsp;</label>
     <input type="button" class="btn btn-info" id="view" onclick="validation();" name="view" value='Get Details' /> 
     </div>
     </div>
   
<%--     <c:if test = "${dataListsize > 0 }"> --%>
<!-- 		<button name="exportExcel5" id="exportExcel5" onclick="exportExcel()" class="btn btn-info">Excel</button> -->
<%-- 	<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stcode}','${dcode}','${projid}','${fyear}')" class="btn btn-info">PDF</button> --%>
<%-- 	</c:if> --%>
     <div class="form-row">
     <div class="form-group ">
    <!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->
     <table class="tblAddPhysicalAchievement" id="delcompBase" name="delcompBase" style="width:100%">
     	<thead>
     	<tr>
     		<c:if test="${empty plotValidation}"><th class="text-center">Village &nbsp; All &nbsp;<input type="checkbox"   onchange="selects(this);"/></th></c:if>
     		<c:if test="${not empty plotValidation}"><th class="text-center">Village &nbsp; All &nbsp;<input type="checkbox"   onchange="selects(this);" disabled="disabled"/></th></c:if>
     		<th class="text-center">Plot/Gata No.</th>
     		<th class="text-center">Plot Area (in ha.)</th>
     		<th class="text-center">Irrigation Status</th>
     		<th class="text-center">Ownership</th>
     		<th class="text-center">Owner Name</th>
     		<th class="text-center">Classification of Land</th>
     		<th class="text-center">No. of Crop</th>
     		<th class="text-center">Forest Land type</th>
     		<th class="text-center">Season</th>
     		<th class="text-center">Crop Type</th>
     		<th class="text-center">Area (in ha) (Col-A)</th>
     		<th class="text-center">Crop Production per Hectare (in Quintal) (Col-B)</th>
     		<th class="text-center">Avg. Income per Quintal (in Rs.) (Col-C)</th>
     		<th class="text-center">Total Income (in Rs.) (A*B*C)</th>
     		<th class="text-center">Action</th>
     	</tr>
     	</thead>
     	<c:if test="${dataListsize>0}">
     	<c:set var="village" value="" />
     	<c:set var="plot" value="" />
     	<c:set var="tparea" value="" />
     	<c:set var="irri" value="" />
     	<c:set var="ownership" value="" />
     	<c:set var="ownername" value="" />
     	<c:set var="classland" value="" />
     	<c:set var="nocrop" value="" />
     	
     	<c:set var="totpltar" value="" />
     	<c:set var="totcrpar" value="" />
     	<c:set var="totcrprdtn" value="" />
     	<c:set var="totavgincm" value="" />
     	<c:set var="totincme" value="" />
     	<c:set var="clsslndid" value="" />
     	<c:set var="pltNo" value="" />
				<c:forEach items="${dataList}" var="dataV" varStatus="count">
					<c:if test = "${count.index != 0 && (village ne dataV[3] || dataV[11] != clsslndid ) }">
						<tr>
							<th colspan = "2" class="text-center">Total</th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits = "4" value="${totpltar}"/></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits = "4" value="${totcrpar}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits = "4" value="${totcrprdtn}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${totavgincm}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${totincme}"/></th>
							<th></th>
							
						</tr>
						<c:set var="totpltar" value="" />
     					<c:set var="totcrpar" value="" />
     					<c:set var="totcrprdtn" value="" />
     					<c:set var="totavgincm" value="" />
     					<c:set var="totincme" value="" />
					</c:if>
					<c:set var="clsslndid" value="${dataV[11]}" />
					<c:if test = "${pltNo ne dataV[4]}">
						<c:set var="totpltar" value="${totpltar + dataV[5]}" />
					</c:if>
     				<c:set var="totcrpar" value="${totcrpar + dataV[21]}" />
     				<c:set var="totcrprdtn" value="${totcrprdtn + dataV[19]}" />
     				<c:set var="totavgincm" value="${totavgincm + dataV[20]}" />
     				<c:set var="totincme" value="${totincme + dataV[21]*dataV[19]*dataV[20]}" />
     				<c:set var="pltNo" value="${dataV[4]}" />
		     	<tr> 
						<c:choose>
							<c:when test="${village ne dataV[3]}">
								<c:set var="village" value="${dataV[3]}" />
								<c:if test="${fn:contains(plotValidation, dataV[3])}"><td><input type="checkbox" id="villcode" name="villcode" value='${dataV[2]}' disabled> &nbsp; ${dataV[3]}</td></c:if>
								<c:if test="${not fn:contains(plotValidation, dataV[3])}"><td ><input type="checkbox" id="villcode" name="villcode" value='${dataV[2]}'> &nbsp; ${dataV[3]}</td></c:if>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose> 
						  <c:set var="valp" value="${dataV[4]}${dataV[3]}" /> 	
		     			<c:choose>
							<c:when test="${plot ne valp}">
								<c:set var="plot" value="${dataV[4]}${dataV[3]}" />
								<c:set var="check" value="false" />
								<c:forEach items="${duplicatePlotNo}" var="dupliPlotNo" >
								<c:if test = "${!check}">
									<c:if test="${fn:contains(dataV[4],'	') || dataV[4].trim() eq fn:substringBefore(dupliPlotNo,' ') && dataV[3] eq fn:substringAfter(dupliPlotNo,' ').trim()}"><td style="color: red;"> <c:out value='${dataV[4]}' /></td><c:set var="check" value="true" /></c:if>
									<c:if test="${dataV[4] eq fn:substringBefore(dupliPlotNo,' ') && dataV[3] ne fn:substringAfter(dupliPlotNo,' ').trim()}"><td> <c:out value='${dataV[4]}' /> </td><c:set var="check" value="true" /></c:if>
									<c:if test="${fn:contains(dupliPlotNo,dataV[4]) && dataV[4].trim() ne fn:substringBefore(dupliPlotNo,' ') && not fn:contains(dataV[4],'	')}"><td> <c:out value='${dataV[4]}' /> </td><c:set var="check" value="true" /></c:if>
								</c:if>
								</c:forEach>
								<c:if test="${not fn:contains(duplicatePlotNo, dataV[4])}"><td> <c:out value='${dataV[4]}' /> </td></c:if>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose> 
						<c:set var="valtp" value="${dataV[5]}${dataV[3]}" /> 
<%-- 		     			<c:choose> --%>
<%-- 							<c:when test="${tparea ne valtp}"> --%>
								<c:set var="tparea" value="${dataV[5]}${dataV[3]}" />
								<c:if test="${fn:contains(plotAreaValidation, dataV[5])}"><td style="color: red;" align="right"> <c:out value='${dataV[5]}' /> </td></c:if>
								<c:if test="${not fn:contains(plotAreaValidation, dataV[5])}"><td align="right"> <c:out value='${dataV[5]}' /> </td></c:if>
<%-- 							</c:when>	 --%>
<%-- 							<c:otherwise> --%>
<!-- 								<td></td> -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
						<c:set var="valirr" value="${dataV[9]}${dataV[3]}" />
		     			<c:choose>
							<c:when test="${irri ne valirr}">
								<c:set var="irri" value="${dataV[9]}${dataV[3]}" />
								<td> <c:out value='${dataV[9]}' /> </td>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<c:set var="valow" value="${dataV[27]}${dataV[3]}" />
		     			<c:choose>
							<c:when test="${ownership ne valow}">
								<c:set var="ownership" value="${dataV[27]}${dataV[3]}" />
								<td> <c:out value='${dataV[27]}' /> </td>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
		     		
		     		<c:set var="valown" value="${dataV[6]}${dataV[3]}" />
		     			<c:choose>
							<c:when test="${ownername ne valown}">
								<c:set var="ownername" value="${dataV[6]}${dataV[3]}" />
								<td> <c:out value='${dataV[6]}' /> </td>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
		     		
		     		<c:set var="valcll" value="${dataV[12]}${dataV[3]}" />
		     			<c:choose>
							<c:when test="${classland ne valcll}">
								<c:set var="classland" value="${dataV[12]}${dataV[3]}" />
								<td> <c:out value='${dataV[12]}' /> </td>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						
						<c:set var="valno" value="${dataV[14]}${dataV[3]}" />
		     			<c:choose>
							<c:when test="${nocrop ne valno}">
								<c:set var="nocrop" value="${dataV[14]}${dataV[3]}" />
								<td> <c:out value='${dataV[14]}' /> </td>
							</c:when>	
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
		     		<td> <c:out value='${dataV[16]}' /> </td>
		     		<td> <c:out value='${dataV[23]}' /> </td>
		     		<td> <c:out value='${dataV[18]}' /> </td>
		     		<td align="right"> <c:out value='${dataV[21]}' /> </td>
		     		<td align="right"> <c:out value='${dataV[19]}' /> </td>
		     		<td align="right"> <c:out value='${dataV[20]}' /> </td>
		     		<td align="right"> <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${dataV[21]*dataV[19]*dataV[20]}"/></td>
					<td>
					<input type="button" data-toggle="modal" data-target="#editbaselinecomdata" value="  Edit  " data-id ="${dataV[25]}" name="editCrop" id = "editCrop" align="left" />
					<input type="button" id="butt${count.index}" value="Delete" name=" delete " align="right" 
								onclick="javascript:do_Delete('${count.index}','${dataV[24]}','${dataV[7]}','${dataV[25]}');" /> 
					</td>
					
		     	</tr>
		     	
		     	<c:if test = "${count.index == dataListsize-1}">
						<tr>
							<th colspan = "2" class="text-center">Total</th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits = "4" value="${totpltar}"/></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits = "4" value="${totcrpar}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="4" maxFractionDigits = "4" value="${totcrprdtn}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${totavgincm}"/></th>
							<th class="text-right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits = "2" value="${totincme}"/></th>
							<th></th>
							
						</tr>
						<c:set var="totpltar" value="" />
     					<c:set var="totcrpar" value="" />
     					<c:set var="totcrprdtn" value="" />
     					<c:set var="totavgincm" value="" />
     					<c:set var="totincme" value="" />
					</c:if>
		     	
		     	<input type="hidden" id="plotArea" name="plotArea" value ="${dataV[5] }">
     			</c:forEach>
     	</c:if>		
     </table>
     
     
     
     </div>
  </div>
   
  <c:if test="${dataListsize>0}">
   <div class="form-row">
      
     <div class="form-group">
      <label for="btnGetActivity"> &nbsp;</label>
     <input type="button" class="btn btn-info" id="complete" onclick="completebsl();" name="complete" value='Complete/Locked the data of selected village' /> 
     </div>
   </div>
 </c:if> 
  				<c:if test="${dataListsize==0}">
  					<table width="100%" border="0" cellspacing="0" cellpadding="0">
          				<tr class="tabs">
            				<td><center><span style="color: red;"> No Data Found !</span></center></td>
            				
          				</tr>
        			</table>
 				</c:if>
     </form>
     <br/>
     <div class="col">
     <h5> Note:</h5>
     <h5>	1. Kindly fill all plot data of a village and ensure the same is being displayed above.<br>
      		2. Data related to selected village would be saved and locked. <br>
     		3. No further entry can be made with respect to selected village.</h5>
     </div>
     </div>
     
     <div class="modal fade" id="editbaselinecomdata">  
    <div class="modal-dialog">  
      
      <!-- Modal content-->  
      <div class="modal-content">  
      	<form>
<!--         <form method="post" -->
<%-- 					action="${pageContext.request.contextPath}/updateData"> --%>
					<div class="modal-header">
						<h4 class="modal-title"> Update of Plot wise Details</h4>
						<button type="reset" class="close"
											 data-dismiss="modal"
											aria-hidden="true">&times;</button>
					</div>
					<div style="position: relative;-webkit-box-flex: 1;-ms-flex: 1 1 auto;flex: 1 1 auto;padding: 1rem;">

										<div class="form-group" >
											<label id="lblSeason" >Season</label>
											 <select class="form-control" name="season" title="Project" disabled="disabled"
												id="season" name="season" required="required"
												 style="height: 100%">
											</select>
										</div>

										<div class="form-group">
											<label>Crop Type</label> <select name="ctype" title="Project"
												id="ctype" name="ctype" required="required" disabled="disabled"
												class="form-control" style="height: 100%">

											</select>
										</div>
										<div class="form-group">
											<label>Area(in ha.)</label> <input type="text" onfocusin="decimalToFourPlace(event)"
												class="form-control areahac" id="areahac" name="areahac"
												required autocomplete="off" name="areahac"><span id="areahacError"></span>
										</div>

										<div class="form-group">
											<label>Crop Production per ha.</label> <input type="text"
												class="form-control" id="crop_prod" required="required"
												autocomplete="off" name="crop_prod">
										</div>

										<input type="hidden" id="bsl_crop_id" name="bsl_crop_id">
										<input type="hidden" id="bsl_detail_id" name="bsl_detail_id">
										<input type="hidden" id="plot_no" name="plot_no">
										<input type="hidden" id="vcode" name="vcode">
										<input type="hidden" id="proj_id" name="proj_id">

										<div class="form-group">
											<label>Avg. income per Quintal </label> <input type="text"
												class="form-control" id="avg_income" required="required"
												autocomplete="off" name="avg_income">
										</div>
								
					</div>
					<div class="modal-footer">
						<button class="btn btn-info" id="cropUpdate" name="cropUpdate" >Update</button>
					</div>
				</form>  
      </div>  
        
    </div>  
  </div>
     
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/outcomeachievement.js" />'></script>
<script src='<c:url value="/resources/js/jquery.validate.js" />'></script>
</body>
</html>