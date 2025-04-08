<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
<style>
 .modal {
            display: none; /* Hidden by default */
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5); /* Background overlay */
        }

        /* Modal content styling */
        .modal-content {
            background-color: #fff;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 30%; /* Modal width */
            height: 30%; /* Modal height */
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
            position: relative; /* Relative positioning for the close button */
        }

        /* Close button styling */
        .close {
            color: #aaa;
            position: absolute; /* Position it absolutely */
            top: 10px; /* Position from the top */
            right: 10px; /* Position from the right */
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: #000;
            text-decoration: none;
        }

        /* Header styling */
        .modal-header {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
            text-align: center;
            border-bottom: 2px solid #ddd;
            padding-bottom: 10px;
        }

        /* Body styling for blocks */
        .modal-body {
            font-size: 16px;
            line-height: 1.5;
            text-align: left; /* Align to the left */
            overflow-y: auto; /* Scroll if content overflows */
        }

</style>
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

function updateTotal() {
    const centralShare = parseFloat(document.getElementById('cShare').value) || 0; 
    const stateShare = parseFloat(document.getElementById('sShare').value) || 0;   
    const totalSanctioned = centralShare + stateShare;                            
    document.getElementById('sanctionedC').value = totalSanctioned.toFixed(4);    
}

function openModal() {
    document.getElementById("myModal").style.display = "block";
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

let hasError = false; 
function validateInput(event) {
    const value = event.target.value.trim();
    const numericValue = parseFloat(value);

     if (!hasError && (isNaN(numericValue) || numericValue <= 0)) {
        alert("Error: Please enter a value greater than 0!");
        hasError = true; 
        event.target.value = ""; 
        event.target.focus(); 
    } else {
        hasError = false; 
    }
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
			State : &nbsp; <b><c:out value='${stName}' /></b>, &nbsp;&nbsp;&nbsp; District : &nbsp; <b><c:out value='${dname}' /></b>, &nbsp;&nbsp;&nbsp; Project : &nbsp; <b><c:out value='${pname}' /></b>, &nbsp;&nbsp;&nbsp; Block : &nbsp; <c:choose>  <c:when test="${fn:length(blockList) == 2}"><b>${blockList.values().toArray()[0]}</b>, <b>${blockList.values().toArray()[1]}</b></c:when> <c:otherwise><b>${blockList.values().toArray()[0]}</b>, <b>${blockList.values().toArray()[1]}</b>     <a href="#" onclick="openModal()"><b>....more</b></a></c:otherwise></c:choose>
			 ,&nbsp;&nbsp;&nbsp; Financial Year : &nbsp; <b><c:out value='${fname}' /></b>, &nbsp;&nbsp;&nbsp; Month : &nbsp; <b><c:out value='${mname}' /></b>
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
     <input type="text" id="sancitonedP" name="sancitonedP" value="${listUser.sanctioned_area}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" onblur="validateInput(event)" class="" maxlength="15" placeholder="Only Decimal" />
 </td>
</tr>

     <tr>
      <td width="4%">
       <b><c:out	value="2."></c:out></b>
     </td>
     <td width="40%">
      <b><c:out	value="Total Sanctioned Cost of the Project (Rs. Crore)"></c:out></b>
     </td><td>
     <input type="text" id="sanctionedC" name="sanctionedC" value="${listUser.sanctioned_cost}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" class="" maxlength="11" placeholder="Only Decimal" readonly/>
     </td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="3."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Central Share (Rs. Crore)"></c:out></b>
  </td><td>
     <input type="text" id="cShare" name="cShare" value="${listUser.central}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" onblur="validateInput(event)" class="" maxlength="11" placeholder="Only Decimal" oninput="updateTotal()"/>
 </td>
</tr> 

<tr>
 <td width="4%">
  <b><c:out	value="4."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="State Share (Rs. Crore)"></c:out></b>
  </td><td>
     <input type="text" id="sShare" name="sShare" value="${listUser.state}" autocomplete = "off" onfocusin="decimalToFourPlace(event)" onblur="validateInput(event)" class="" maxlength="11" placeholder="Only Decimal" oninput="updateTotal()"/>
 </td>
</tr> 



<tr>
 <td width="4%">
  <b><c:out	value="5."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Number of Village Covered"></c:out></b>
  </td><td>
     <input type="text" id="villageC" name="villageC" value="${listUser.no_vc}" autocomplete = "off" onfocusin="numericOnly(event)" onblur="validateInput(event)" class="" maxlength="5" placeholder="Only Number" />
 </td>
</tr>
 
 <tr>
 <td width="4%">
  <b><c:out	value="6."></c:out></b>
 </td>
  <td width="40%">
   <b><c:out	value="Number of Watershed Committees"></c:out></b>
  </td><td>
     <input type="text" id="waterC" name="waterC" value="${listUser.no_wc}" autocomplete = "off" onfocusin="numericOnly(event)" onblur="validateInput(event)" class="" maxlength="5" placeholder="Only Number" />
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
        <input type="text" id="membersWC" name="membersWC" autocomplete="off" onfocusin="numericOnly(event)" onblur="validateInput(event)" class="" placeholder="Only Number"/>
    </c:when>
    <c:otherwise>
        <input type="text" id="membersWC" name="membersWC" value="${listUser.member_wc}" autocomplete="off" onfocusin="numericOnly(event)" onblur="validateInput(event)" class="" maxlength="5" placeholder="Only Number"/>
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

<div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <div class="modal-header">All Blocks</div>
            <div class="modal-body">
                <!-- Display comma-separated blocks -->
                <c:forEach var="block" items="${blockList.values()}" varStatus="status">
                    <span>${block}</span>
                    <c:if test="${!status.last}">, </c:if> <!-- Add comma if not the last block -->
                </c:forEach>
            </div>
        </div>
    </div>

</form:form>

</div>



    <footer class="text-center mt-4">
        <%@include file="/WEB-INF/jspf/footer2.jspf"%>
    </footer>
</body>
