<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<head>
<script type="text/javascript">
let formSubmitted = false;  

function savedata(event){
    event.preventDefault();  

    if (formSubmitted) return false;  

     var sanctionedC = $('#sanctionedC').val();
    var cShare = $('#cShare').val();
    var sShare = $('#sShare').val();
    var sancitonedP = $('#sancitonedP').val();
    var villageC = $('#villageC').val();
    var waterC = $('#waterC').val();
    var membersWC = $('#membersWC').val();
    var householdsC = $('#householdsC').val();

    if (sanctionedC === ''){
        alert('Please enter Total Sanctioned Cost of the Project!');
        $('#sanctionedC').focus();
        return false;
    }
    if (cShare === ''){
        alert('Please enter Central Share!');
        $('#cShare').focus();
        return false;
    }
    if (sShare === ''){
        alert('Please enter State Share!');
        $('#sShare').focus();
        return false;
    }
    if (sancitonedP === ''){
        alert('Please enter Total Sanctioned Project Area!');
        $('#sancitonedP').focus();
        return false;
    }
    if (villageC === ''){
        alert('Please enter Number of Village Covered!');
        $('#villageC').focus();
        return false;
    }
    if (waterC === ''){
        alert('Please enter Number of Watershed Committees!');
        $('#waterC').focus();
        return false;
    }
    if (membersWC === ''){
        alert('Please enter Number of Members in Watershed Committees!');
        $('#membersWC').focus();
        return false;
    }
    if (householdsC === ''){
        alert('Please enter Number of Households Covered in the Project area!');
        $('#householdsC').focus();
        return false;
    }

    if(confirm("Do you want to save Project Profile?")) {
        formSubmitted = true;  
        document.getElementById('projectProfiledata').action = "saveprojectProfile";
        document.getElementById('projectProfiledata').method = "post";
        document.getElementById('projectProfiledata').submit();
    }

    return false;
}
</script>



</head>
<body>
<div class="maindiv">


<div class="col formheading" style="display: flex; align-items: center; justify-content: center;">
	<a href="getProjectProfile?district=<c:out value="${dcode}"/>&project=<c:out value="${pcode}"/>&distName=<c:out value="${dname}"/>&projName=<c:out value="${pname}"/>&month=<c:out value="${mcode}"/>&monthName=<c:out value="${mname}"/>&finyear=<c:out value="${fcode}"/>&finName=<c:out value="${fname}"/>" style="position: absolute; left: 0;">
    	<img src="<c:url value='/resources/images/backbutton_PE.png'/>" alt="Back" style="height: 40px; width: 40px;">
	</a>
	<h4 style="margin: 0;">
		<span style="text-decoration:underline;">Mid Term Project Evaluation - Project Profile</span>
	</h4>
</div>

<hr />
<form:form name="projectProfiledata" id="projectProfiledata" modelAttribute="projectProfiledata" action="saveprojectProfile" method="post">
 		
	
		
		<input type="hidden" id="dcode" name="dcode" value= <c:out value='${dcode}' /> />
		<input type="hidden" id="distName" name="distName" value= "<c:out value='${dname}' />" />
		<input type="hidden" id="projname" name="projname" value= "<c:out value='${pname}' />" />
		<input type="hidden" id="projid" name="projid" value= <c:out value='${pcode}' /> />
		<input type="hidden" id="mcode" name="mcode" value= <c:out value='${mcode}' /> />
		<input type="hidden" id="mname" name="mname" value= <c:out value='${mname}' /> />
		<input type="hidden" id="fcode" name="fcode" value= <c:out value='${fcode}' /> />
		<input type="hidden" id="fname" name="fname" value= <c:out value='${fname}' /> />
		
		<div class="form-group">
				District Name : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project Name : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp;
		Financial Year : &nbsp; <b><c:out value='${fname}' /></b>, &nbsp;&nbsp;&nbsp; Month Name : &nbsp; <b><c:out value='${mname}' /></b>
			</div>
			<hr />
		
<table cellspacing="0" class="table"   width="auto">
  <thead>
  
<!-- 	<tr> -->
<%-- 		<th colspan="24" >District Name : &nbsp; <c:out value='${dname}' /> &nbsp;&nbsp;&nbsp; Project Name: &nbsp; <c:out value='${pname}' /> &nbsp;&nbsp;&nbsp;Month : &nbsp; <c:out value='${mname}' /> &nbsp;&nbsp;&nbsp;  --%>
<%-- 		Financial Year : &nbsp; <c:out value='${fname}' />  </th> --%>
<!-- 	</tr> -->
	<tr>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">S.No.</th>
      <th rowspan="5" style="text-align:left; vertical-align: middle;"></th>
      <th rowspan="2" style="text-align:left; vertical-align: middle;">Details as per MIS</th>
      </tr>
      </thead>
      <c:forEach var="list" items="${projectList}" varStatus="status">
           <c:forEach var="listUser" items="${list.value}" >
    
    <tr>
 <td width="4%">
  <b><c:out	value="1."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Total Sanctioned Project Area (in ha.)"></c:out></b>
  </td><td>
     <input type="text" id="sancitonedP" name="sancitonedP" value="${listUser.sanctioned_area}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class="" maxlength="15" placeholder="Only Decimal" />
 </td>
</tr>

     <tr>
      <td width="4%">
       <b><c:out	value="2."></c:out></b>
     </td>
     <td width="40%">
      <b><c:out	value="Total Sanctioned Cost of the Project (Rs. Crore)"></c:out></b>
     </td><td>
     <input type="text" id="sanctionedC" name="sanctionedC" value="${listUser.sanctioned_cost}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class="" maxlength="11" placeholder="Only Decimal" />
     </td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="3."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Central Share (Rs. Crore)"></c:out></b>
  </td><td>
     <input type="text" id="cShare" name="cShare" value="${listUser.central}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class="" maxlength="11" placeholder="Only Decimal" />
 </td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="4."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="State Share (Rs. Crore)"></c:out></b>
  </td><td>
     <input type="text" id="sShare" name="sShare" value="${listUser.state}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class="" maxlength="11" placeholder="Only Decimal"/>
 </td>
</tr> 



<tr>
 <td width="4%">
  <b><c:out	value="5."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Number of Village Covered"></c:out></b>
  </td><td>
     <input type="text" id="villageC" name="villageC" value="${listUser.no_vc}" autocomplete = "off" onfocusin="numericOnly(event)" class="" maxlength="5" placeholder="Only Number" />
 </td>
</tr>
 
 <tr>
 <td width="4%">
  <b><c:out	value="6."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Number of Watershed Committees"></c:out></b>
  </td><td>
     <input type="text" id="waterC" name="waterC" value="${listUser.no_wc}" autocomplete = "off" onfocusin="numericOnly(event)" class="" maxlength="5" placeholder="Only Number" />
 </td>
</tr>

<tr>
 <td width="4%">
  <b><c:out	value="7."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Number of Members in Watershed Committees"></c:out></b>
  </td><td>
    <c:choose>
    <c:when test="${listUser.member_wc eq null}">
        <input type="text" id="membersWC" name="membersWC" autocomplete="off" onfocusin="numericOnly(event)" class="" placeholder="Only Number"/>
    </c:when>
    <c:otherwise>
        <input type="text" id="membersWC" name="membersWC" value="${listUser.member_wc}" autocomplete="off" onfocusin="numericOnly(event)" class="" maxlength="5" placeholder="Only Number"/>
    </c:otherwise>
</c:choose>

 </td>
</tr>

<tr>
 <td width="4%">
  <b><c:out	value="8."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Number of Households Covered in the Project area"></c:out></b>
  </td><td>
  <c:choose>
     <c:when test = "${listUser.household eq null}">
     <input type="text" id="householdsC" name="householdsC" autocomplete = "off" onfocusin="numericOnly(event)" class=""  placeholder="Only Number"/>
     </c:when>
     <c:when test = "${listUser.household ne null}">
     <input type="text" id="householdsC" name="householdsC" value="${listUser.household}" autocomplete = "off" onfocusin="numericOnly(event)" class=""  maxlength="5" placeholder="Only Number"/>
     </c:when>
  </c:choose>   
 </td>
</tr>
</c:forEach>
</c:forEach>
<tr>
<th colspan="24" >
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
<input type="button" name="view"  id = "view" value="Confirm"
					class="btn btn-info" onclick="savedata(event);"/>
										
</th>
</tr>



</table>
</form:form>
</div>
    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
</body>
