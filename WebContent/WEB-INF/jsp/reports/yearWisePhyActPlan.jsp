<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@include file="/WEB-INF/jspf/header.jspf"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	

	$(document).on( 'click', '#btnGetReport2', function (e) {
		$state=$('#state option:selected').val();
		$district=$('#district option:selected').val();
		$project=$('#project option:selected').val();
		$fromYear=$('#fromyear option:selected').val();
		$toYear=$('#toyear option:selected').val();
		if($fromYear =='' || $toYear ==''){
			alert('Please Select the Year.');
			return false;
		}
		if($fromYear > $toYear){
			alert('From Year date should be less than or equal to the To Year.');
			return false;
		}
		document.yearWisePhyActPlan.action="yearWisePhyActPlan";
		document.yearWisePhyActPlan.method="post";
		document.yearWisePhyActPlan.submit();
	});
	function exportExcel(stCode,distCode,projId,fYear,tYear){
		var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
	    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
	    var frmYrName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
	    var toYrName = document.getElementById("toyear").options[document.getElementById("toyear").selectedIndex].text;
		document.getElementById("stCode").value=stCode;
	    document.getElementById("distCode").value=distCode;
	    document.getElementById("projId").value=projId;
	    document.getElementById("fYear").value=fYear;
	    document.getElementById("tYear").value=tYear;
	    document.getElementById("stName").value=stName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.getElementById("frmYrName").value=frmYrName;
	    document.getElementById("toYrName").value=toYrName;
	    document.yearWisePhyActPlan.action="getyearWisePhyActPlanExcel";
		document.yearWisePhyActPlan.method="post";
		document.yearWisePhyActPlan.submit();
		}
	
	function downloadPDF(stCode,distCode,projId,fYear,tYear){
		var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	    var distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
	    var projName = document.getElementById("project").options[document.getElementById("project").selectedIndex].text;
	    var frmYrName = document.getElementById("fromyear").options[document.getElementById("fromyear").selectedIndex].text;
	    var toYrName = document.getElementById("toyear").options[document.getElementById("toyear").selectedIndex].text;
		document.getElementById("stCode").value=stCode;
	    document.getElementById("distCode").value=distCode;
	    document.getElementById("projId").value=projId;
	    document.getElementById("fYear").value=fYear;
	    document.getElementById("tYear").value=tYear;
	    document.getElementById("stName").value=stName;
	    document.getElementById("distName").value=distName;
	    document.getElementById("projName").value=projName;
	    document.getElementById("frmYrName").value=frmYrName;
	    document.getElementById("toYrName").value=toYrName;
	    document.yearWisePhyActPlan.action="downloadyrWisePhyActPlanPDF";
		document.yearWisePhyActPlan.method="post";
		document.yearWisePhyActPlan.submit();
		}
	
</script>

<title>Report T1- State-wise, District-wise,Project-wise and Year-wise Details of Physical Action Plan Targets as per List of Activities</title>


</head>

<div class="container-fluid">
<div class="row">
<div class="col text-center">
<br/>
<div class="offset-md-3 col-6 formheading" style="text-align:center;"  ><h5><label id="head">Report T1- State-wise, District-wise,Project-wise and Year-wise Details of Physical Action Plan Targets as per List of Activities </label></h5></div>
<hr/>
</div>
</div>
<div class="row">

<div class="col-12">
<form action="yearWisePhyActPlan" method="post" name="yearWisePhyActPlan" id="yearWisePhyActPlan">

	<input type="hidden" id="stCode" name="stCode" value="${stCode}" />
 	<input type="hidden" id="distCode" name="distCode" value="${distCode}" />
	<input type="hidden" id="projId" name="projId" value="${projectId}" />
	<input type="hidden" id="fYear" name="fYear" value="${fromYear}" />
	<input type="hidden" id="tYear" name="tYear" value="${toYear}" />
	
	<input type="hidden" name="stName" id="stName" value="" />
	<input type="hidden" name="distName" id="distName" value="" />
	<input type="hidden" name="projName" id="projName" value="" />
	<input type="hidden" name="frmYrName" id="frmYrName" value="" />
	<input type="hidden" name="toYrName" id="toYrName" value="" />
	
<div class="form-row">
  <div class="form-group col">
    <label for="project">State: </label>
    <select class="form-control project" id="state" name="state" required>
    <option value="0">--All State--</option>
     <c:forEach items="${stateList}" var="state">
    
							<option value="<c:out value="${state.key}"/>" <c:out value="${state.key== stCode ?'selected':'' }"/>>
							<c:out 	value="${state.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col">
    <label for="year">District: </label>
    <select class="form-control district" id="district" name="district" required>
      <option value="0" selected>--All--</option>
       
    </select>
    
  </div>
  <div class="form-group col">
    <label for="year">Project: </label>
    <select class="form-control project" id="project" name="project" required>
      <option value="0" selected>--All--</option>
       
    </select>
  </div>
  <div class="form-group col">
    <label for="fromyear">From Year: </label>
    <select class="form-control finyear" id="fromyear" name="fromYear" required="required">
      			<option value="">--Select Year--</option>
      			<c:forEach items="${yearList}" var="yearl">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== fromYear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
   
  </div>
  <div class="form-group col">
    <label for="toyear">To Year: </label>
    <select class="form-control finyear" id="toyear" name="toYear" required="required">
      			<option value="">--Select Year--</option>
      			<c:forEach items="${yearList}" var="yearl">
							<option value="<c:out value="${yearl.key}"/>" <c:out value="${yearl.key== toYear ?'selected':'' }"/>>
							<c:out 	value="${yearl.value}" /></option>
				</c:forEach>
    		  </select>
    
  </div>
  <div class="form-group col-1">
  <label for="button">&nbsp;</label>
  <input type="submit" class="btn btn-info form-control" id="btnGetReport2" name="btnGetReport" value="Get Report">
  </div>
  </div>
  </form>
  <hr/>
  </div>
 <div class="col-1"></div>
<div class="col" id="reportDiv">
  
 
<c:if test = "${datalistsize ne null }">
<button name="exportExcel" id="exportExcel" onclick="exportExcel('${stCode}','${distCode}','${projectId}','${fromYear}','${toYear}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF('${stCode}','${distCode}','${projectId}','${fromYear}','${toYear}')" class="btn btn-info">PDF</button>
</c:if>

<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
<table id="dtBasicExample" cellspacing="0" class="table" >
  <thead>
    <tr>
      <th colspan=2></th>
      <th style="text-align:center" colspan="<c:out value='${yrListSize}'/>">Physical Target</th>
      <th colspan=1></th>
    </tr>

    <tr>
      <th>Name of the Activity</th>
      <th>Unit</th>
      <c:set var="count1" value="0" />
		<c:forEach items="${yrList}" var="yrList" varStatus="status">
			<th class="text-center "><c:out value='${yrList.value}' /></th>
		</c:forEach>
      <th>Total</th>
    </tr>
    <tr>
		<th class="text-center">1</th>
		<th class="text-center">2</th>
		<c:set var="count1" value="3" />
		<c:forEach items="${yrList}" var="yrList" varStatus="status">
			<th class="text-center "><c:out value='${count1}' /></th>
			<c:set var="count1" value="${count1 +1}" />
		</c:forEach>
		<th class="text-center"><c:out value='${count1}' /></th>
	</tr>
  </thead>

  <tbody>
	
		<c:set var="headVal" value="0" />
		<c:set var="actVal" value="" />
		<c:set var="actCount" value="1" />
		<c:set var="colTotal" value="0" />
		
		<c:set var="totTotal" value="0.00" />
		<c:forEach items="${dataList}" var="dataListSh">
			<c:if test = "${dataListSh.headcode ne headVal }">
				<c:if test ="${headVal ne 0 && headVal<8}">
					<tr>
						<th colspan = "2">Sub-Total</th>
						<c:forEach items="${subTotMap}" var="subTotMap" varStatus="status">
							<c:if test = "${fn:substringBefore(subTotMap.key,',') eq headVal}">
								<th class="text-right "><c:out value='${subTotMap.value}' /></th>
							</c:if>
						</c:forEach>
						<th class="text-right"><c:out value ="${totTotal}"/></th>
					</tr>
					<c:set var="totTotal" value="0.00" />
				</c:if>
				<tr>
					<td><c:out value ="${dataListSh.headcode}. ${dataListSh.headname}"/></td>
					<td></td>
					<c:forEach items="${yrList}" var="yrList" varStatus="status">
						<td></td>
					</c:forEach>
					<td></td>
				</tr>
				<c:set var="headVal" value="${dataListSh.headcode}" />
				<c:set var="actCount" value="1" />
			</c:if>
			<tr>
				<td><c:out value ="${dataListSh.headcode}.${actCount} ${dataListSh.activityname}"/></td>
				<td><c:out value = "${dataListSh.unitname}"/></td>
				<c:forEach items="${hdActMap}" var="hdActMap" varStatus="status">
					<c:if test = "${dataListSh.activitycode eq fn:substringBefore(hdActMap.key,',')}">
						<td class="text-right"><c:out value ="${hdActMap.value}"/></td>
						<c:set var="colTotal" value="${colTotal + hdActMap.value}" />
					</c:if>
				</c:forEach>
				<td class="text-right"><c:out value ="${colTotal}"/></td>
				<c:set var="totTotal" value="${totTotal + colTotal}" />
				<c:set var="colTotal" value="0" />
			</tr>
			<c:set var="actCount" value="${actCount + 1}" />
		
		</c:forEach>
    
    <c:if test="${datalistsize==0}">
			<tr>
				<td align="center" colspan="${yrListSize +3} " class="required">Data Not Found</td>
			</tr>
		</c:if>
  </tbody>
</table>
   
<br/>  
</div>
<div class="col-1"></div>
</div>
</div>

<script src='<c:url value="/resources/js/yearWisePhyActPlan.js" />'></script>
<!-- Footer -->
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>


</body>
</html>