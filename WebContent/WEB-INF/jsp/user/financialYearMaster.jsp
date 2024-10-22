<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">
<html>
<head>
<meta charset="ISO-8859-1">
<title>projectPreparedness</title>
<script type="text/javascript">

function setData(val){
	if(val.value!=''){
	  document.getElementById("startFrom").value=val.value.split("<-->")[0];
	  document.getElementById("endTo").value=val.value.split("<-->")[1];
	  document.getElementById("finYearDesc").value=val.options[val.selectedIndex].text;
	  
	  document.getElementById("fromdate").value=val.value.split("<-->")[0];
	  document.getElementById("enddate").value=val.value.split("<-->")[1];
	  document.getElementById("finYearDes").value=val.options[val.selectedIndex].text;
	  
	}
}




function saveFinYear(){
	var finYear = $('#finYear').val();
	
	if(finYear==='')
	{
		alert('Please select financial year ');
		$('#finYear').focus();
		e.preventDefault();
	}
	else{
		if(confirm("Do you want to save financial year ?"))
		{
			document.getElementById('addFinancialYear').action ="saveFinancialYear";
		    document.getElementById('addFinancialYear').method="post";
		   	document.getElementById('addFinancialYear').submit();
			
		}
	}
	return false;
}


</script>

</head>
<body>
<c:if test="${MessagefinYear != null}">
	<script>
	    alert("<c:out value='${MessagefinYear}'/>");
	</script>
</c:if>

<div class ="card">
<div class="table-responsive">
				<c:if test="${MessagefinYear ne null}"> 
		 			<div style="text-align: center;" ><p class="message badge badge-success"> ${MessagefinYear} </p></div>
				</c:if>
<div class="col" style="text-align:center;"><h5><b>Add Financial Year</b></h5></div>
 <form:form  name="addFinancialYear" id="addFinancialYear" modelAttribute="addFinancialYear" action="saveFinancialYear" method="post">
 					<input type="hidden" id="finYearDes" name="finYearDes"  />
 					<input type="hidden" id="fromdate" name="fromdate"  />
 					<input type="hidden" id="enddate" name="enddate"   />
      <table style="text-align:center;" class="col">
        	<tr>
          		<td class="label">Financial Year <span style="color: red;">*</span> </td>
          		<td>
              		<select name="finYear" title="finYear" id="finYear"  onchange="setData(this)" >
						<option value="">--select--</option>
						 <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.key eq yr}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected"><c:out value="${lists.value}" /></option>
       							</c:if>
       							<c:if test="${lists.key ne yr}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       							</c:if>	
								</c:forEach>
						</c:if> 
					</select>
          		</td>
          		
          		<td class="label">Financial Year Description<span style="color: red;">*</span> </td>
          		<td>  <input type="text" name="finYearDesc" id="finYearDesc"  value=""  disabled="disabled"/> </td>
          		
           		<td class="label">Status <span style="color: red;">*</span> </td>
          		<td>
             		<select name="finYearstatus" title="finYear" id="finYearstatus"  >
						<option value="A">Active</option>
						<!-- <option value="I">InActive</option> -->
					</select>
          		</td>
         	</tr>
         	<tr> 
           		<td class="label">Start From <span style="color: red;">*</span> </td>
          		<td>  <input type="text" name="startFrom" id="startFrom"  value="" disabled="disabled"/> </td>
           		<td class="label">End To <span style="color: red;">*</span> </td>
          		<td><input type="text" name="endTo" id="endTo"  value="" disabled="disabled"/></td>
          		<td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;
          					<input type="button" name="savefy" id="savefy" value="Save" class="btn btn-info" onclick="saveFinYear();" /> 
          		</td>
       		</tr>
      </table>
 </form:form>


	</div>
	<c:if test="${not empty finYearMasterList}">
	<table class="table">
          <tr>
            <td>
				<div class="col" style="text-align:center;"><h6><b>Details of Financial Year(iwmp_m_fin_year)</b></h6></div>
			</td>
		  </tr>
	</table>	
	
        <table class="table">
          <tr>
            <td>
            	<table id="example" class="table">
              		<tr>
		              	    <th width="2">S.No.</th>
		              	    <th width="3">Financial Year Code</th>
		              	    <th width="5">Financial Year Description</th>
							<th width="7">Start Date</th>
							<th width="7">End Date</th>
							<th width="5">Status</th>
               		</tr>
                	<c:forEach items="${finYearMasterList}" var="list" varStatus="status">
				    <tr>
				        <td> ${status.count}</td>
				     	<td> <c:out value="${list.fin_yr_cd}" /></td>
				        <td> <c:out value="${list.fin_yr_desc}" /></td>
				     	<td> <c:out value="${list.start_date}" /></td>
				        <td> <c:out value="${list.end_date}" /></td> 
				        <td> <c:out value="${list.act_flag}" /></td>
				    </tr> 
			    	</c:forEach>
              	</table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        </c:if> 
    <br>
	
	</div>



<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

</script>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>