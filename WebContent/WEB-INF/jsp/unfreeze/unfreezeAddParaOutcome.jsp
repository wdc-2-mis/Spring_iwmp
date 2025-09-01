<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
	var year = $('#year').val();
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	if(project==='')
	{
		alert('Please select Project ');
		$('#project').focus();
		e.preventDefault();
	}
	if(year==='')
	{
		alert('Please select year ');
		$('#year').focus();
		e.preventDefault();
	}
	else{
		
		document.unfreezeAddParaOutcome.action="getAddParaOutcomeDetails";
		document.unfreezeAddParaOutcome.method="post";
		document.unfreezeAddParaOutcome.submit();
	}
	return false;
}

function selects(ele) {
    var checkboxes = document.getElementsByName('month');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

function checkAllCheckboxes() {
    var checkboxes = document.getElementsByName('month');
    var selectAllCheckbox = document.getElementById('allmonth');

    // Check if all checkboxes are checked
    var allChecked = Array.prototype.every.call(checkboxes, function(checkbox) {
        return checkbox.checked;
    });

    // If all checkboxes are checked, check the select all checkbox
    if (allChecked) {
        selectAllCheckbox.checked = true;
    } else {
        selectAllCheckbox.checked = false;
    }
}

function completebsl(){ 
		var checkboxes = document.getElementsByName('month');
	    var isAnyChecked = false;
	    
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i].checked) {
	            isAnyChecked = true;
	            break;
	        }
	    }
		
		if(isAnyChecked){
			if (confirm("Do you want to Unfreeze ?") == true) 
			{
					document.unfreezeAddParaOutcome.action="unfreezeAddParaOutcomeData";
					document.unfreezeAddParaOutcome.method="post";
					document.unfreezeAddParaOutcome.submit();
			}
		}else{
			alert('Please Select altleast one Check Box.');
		}
	}


</script>

</head>
<div class ="card">

<div class="table-responsive">
<c:if test="${messageUpload != null}">
	<script>
	    alert("<c:out value='${messageUpload}'/>");
	</script>
</c:if>
<div class="col" style="text-align:center;"><h5>Unfreeze Additional Parameter of Outcome</h5></div>
 <form:form autocomplete="off" name="unfreezeAddParaOutcome" id="unfreezeAddParaOutcome"  action="unfreezeAddParaOutcome" method="get">
 		<input type="hidden" name="projid" id="projid" value="" />
      <table >
        <tr>
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();" required="required">
              		<option value="">--Select State--</option>
              		
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
          
           <td class="label">District <span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="this.form.submit();" required="required">
              		<option value="">--Select District--</option>
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
          
           <td class="label">Project &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="project" id="project" required="required" onchange="this.form.submit();">
              <option value ="">--Select Project--</option>
              	<c:if test="${not empty ProjectList}">
               					<c:forEach items="${ProjectList}" var="lists">
               						<c:if test="${lists.key eq project}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne project}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
           <td class="label">Financial Year &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="year" id="year" required="required" >
              <option value="">--Select FinYear--</option>
              	 <c:if test="${not empty financialYear}">
               					<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       								  
       							</c:if>	
       							<c:if test="${lists.finYrCd ne year}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
								</c:forEach>
						</c:if>  
              </select>
          </td>
          
          
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>
      
     <div style="width: 100%;">
		<c:if test="${not empty state and not empty district and not empty project and not empty year}">
            	<table id="tblReport" class="table">
            	<thead>
              		
              		<c:if test = "${projlistUnfreezeBaselSize > 0}">
              		<tr>
              				<th class="displ" align="center"><input type="checkbox" id="allmonth" onchange="selects(this);">&nbsp;&nbsp; Select All</th>
              				<th align="center"> Financial Year</th>
              				<th align="center"> Month</th>
              				
              		</tr>
              		<c:forEach var="list" items="${projlistUnfreezeBasel}" varStatus="status">
              			<tr>
              				<td align="left"><input type="checkbox" id = "month" name="month" value=<c:out value ="${list.outcome2Id}"/> onchange="checkAllCheckboxes()"> &nbsp;&nbsp; <c:out value ="${status.count}"/></td>
              				<td align="left"> <c:out value ="${list.iwmpMFinYear.finYrDesc}"/></td>
              				<td align="left"> <c:out value ="${list.iwmpMMonth.monthName}"/></td>
              			</tr>
              		</c:forEach>
              		</c:if>
								<c:if test="${projlistUnfreezeBaselSize==0}">
									
										<td align="left" colspan = "5">No Data Found !</td>
								
								</c:if>
							
               		</thead>
               		<tbody>
               		
                	
                	<tr>
                	
                	<td colspan="5" align="center">
                	 <input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' /> 
                	 </td>
                	 </tr>
                	</tbody>
               		
              </table>
           </c:if>
           </div>

 
       
        
         </form:form>
    <br>
    
   
	
	</div>
	</div>


<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

</script>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>