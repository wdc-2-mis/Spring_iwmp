<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/userDetailSA.js" />'></script>
</head>
<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>Last Date of Activity by User</h5></div>
 <form:form autocomplete="off" name="lastActivity" id="lastActivity"  modelAttribute="lastActivity">
 
      <table >
        <tr>
          <td class="label">State &nbsp;&nbsp;&nbsp;</td>
          <td>
              <select name="state" id="state">
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
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>


	</div>
	
        <table class="table">
          <tr>
            <td>
            	<table id="example" class="table">
              		<tr>
		              	    <th class="displ" align="center">S.No.</th>
		              	    <th class="displ" align="center">User ID</th>
          					<th class="displ" align="center">User Type</th>
                            <th class="displ" align="center">District</th>
                            <th class="displ" align="center">Last updated date</th>
               		</tr>
                	<c:if test="${userLogList ne null}"> 
                	<c:forEach var="list" items="${userLogList}" varStatus="status">
	                	<tr>
	                	  	<td align="center">${status.count}</td>
	                	  	<td> <c:out value="${list.loginid}" />  </td>
	                	  	<c:if test="${list.user_type eq 'SL'}"> 
	                	    	<td> SLNA </td>
	                	    </c:if>
	                	    <c:if test="${list.user_type eq 'DI'}"> 
	                	    <td> WCDC </td>
	                	    </c:if>
	                	    <c:if test="${list.user_type eq 'PI'}"> 
	                	    <td> PIA </td>
	                	    </c:if>
	                	    <td> <c:out value="${list.dist_name}" />  </td>
	                	    <td> <c:out value="${list.last_login}" />  </td>
	                	    
	                	</tr>
                	</c:forEach>
                	</c:if>
                	
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
      

  <c:if test="${userLogList.size()<=0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
	
	</div>
	</div>
</div>
</div>
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