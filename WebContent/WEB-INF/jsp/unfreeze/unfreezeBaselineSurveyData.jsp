<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/unfreezeProjectLocation.js" />'></script>
<script type="text/javascript">

function showReport(e)
{
	var state = $('#state').val();
	var district = $('#district').val();
	var project = $('#project').val();
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
	else{
		
		document.unfreezebaseline.action="getUnfreezeBaselineSurveyData";
		document.unfreezebaseline.method="post";
		document.unfreezebaseline.submit();
	}
	return false;
}

function selects(ele) {
    var checkboxes = document.getElementsByName('villcode');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].type == 'checkbox') {
            checkboxes[i].checked = ele.checked;
        }
    }
}

	function completebsl() 
	{ 
		if (confirm("Do you want to Unfreeze ?") == true) 
		{
				document.unfreezebaseline.action="unfreezeBaselineSurveyDataComplete";
				document.unfreezebaseline.method="post";
				document.unfreezebaseline.submit();
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
<div class="col" style="text-align:center;"><h5>Unfreeze Baseline Survey Data</h5></div>
 <form:form autocomplete="off" name="unfreezebaseline" id="unfreezebaseline"  action="unfreezeBaselineSurveyData" method="get">
 		<input type="hidden" name="projid" id="projid" value="" />
      <table >
        <tr>
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();" required="required">
              		<option value="">--Select--</option>
              		
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
              <option value="">--Select Project--</option>
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
           <td class="label">Village &nbsp;&nbsp;&nbsp;</td>
           <td>
              <select name="village" id="village" required="required" >
              <option value="0">--All Villages--</option>
              	<c:if test="${not empty villList}">
               					<c:forEach items="${villList}" var="lists">
               						<c:if test="${lists.key eq vill}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne vill}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>

 
        <table class="table">
          <tr>
            <td>
            	<table id="tblReport" class="table">
            	<thead>
              		<tr>	<th class="displ" align="center">Sl. No.</th>
              				<th class="displ" align="center"> All&nbsp; Villages&nbsp;<input type="checkbox"   onchange="selects(this);"/>
              			 	<th class="displ" align="center">Village Name</th>
               		</tr>
               		</thead>
               		<tbody>
               		
                	<c:if test="${projlistUnfreezeBasel ne null}"> 
                	<c:forEach var="list" items="${projlistUnfreezeBasel}" varStatus="status">
	                	<tr>
	                		<td align="center"> <c:out value="${status.count}" /></td>
	                		<td><input type="checkbox" id="villcode" name="villcode" value='${list.vcode}' > </td>
						    <td> <c:out value='${list.villname}' /> </td>
	                	</tr>
                	</c:forEach>
                	<tr>
                	<td></td>
                	<td>
                	 <input type="button" class="btn btn-info" id="Unfreeze" onclick="completebsl();" name="Unfreeze" value='Unfreeze' /> 
                	 </td>
                	 </tr>
                	</c:if>
                	</tbody>
               		
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        
         </form:form>
    <br>
    
   
  <c:if test="${projlistUnfreezeBaselSize==0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
	
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