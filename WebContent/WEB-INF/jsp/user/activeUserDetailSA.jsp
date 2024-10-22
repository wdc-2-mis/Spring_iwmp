<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/afterlogin.css" />">
<script src='<c:url value="/resources/js/userDetailSA.js" />'></script>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>Details of Active Users</h5></div>
 <form:form autocomplete="off" name="userdetailsa" id="userdetailsa"  modelAttribute="userReg">
 
      <table>
        <tr>
          <td >User Type<span style="color: red;">*</span></td>
          <td> 
          		<select name="userType" id="userType" class="form-control" >
                      <option value="">--Select--</option>
                      <c:if test="${uktype eq null}">
                      	<option value="SL" >SLNA, WCDC and STATE</option>
                      	<option value="PI" >PIA</option>
                      </c:if>
                      <c:if test="${uktype eq 'DL'}">
                      	<option value="SL" >SLNA, WCDC and STATE</option>
                      	<option value="PI" >PIA</option>
                      </c:if>
                      <c:if test="${uktype eq 'SL'}">
                      	<option value="SL" selected="selected">SLNA, WCDC and STATE</option>
                      	<option value="PI" >PIA</option>
                      </c:if>
                      <c:if test="${uktype eq 'PI'}">
                      	<option value="SL" >SLNA, WCDC and STATE</option>
                      	<option value="PI" selected="selected">PIA</option>
                      </c:if>
                      
              	</select>
          </td>
          <td >State<span style="color: red;">*</span></td>
          <c:if test="${uktype1 ne 'DL'}">
          <td>
              <select name="state" id="state" class="form-control">
                  <option value="${sessionScope.stateCode}" ><c:out value="${sessionScope.stName}" /></option>
              </select>
          </td>
          </c:if>
          <c:if test="${uktype1 eq 'DL'}">
          <td>
           <select name="state" id="state" class="form-control">
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
          </c:if>
          
          <td>Status<span style="color: red;">*</span></td>
          <td>
          <select name="status" id="status" class="form-control">
          <option value="">--Select--</option>
          <option value="Active">Active</option>
          <option value="InActive">InActive</option>
          
          </select>
          </td>
          <td align="right"> <input type="button" class="btn btn-info" id="view" onclick="validate(this);" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>


	</div>
	
	
        <table >
          <tr>
            <td>
            	<table id="example" >
              		<tr>
		              	    <th style="text-align: center;">S.No.</th>
			              	<c:if test="${uktype eq 'PI'}">
	                            <th style="text-align: center;">District Name</th>
	                            <th style="text-align: center;" width="20%">Project Name</th>
	                        </c:if>   
	                        <c:if test="${uktype ne 'PI'}">
          					<th style="text-align: center;">District Name</th>
          					</c:if>
          					<th style="text-align: center;">User-ID </th>
                            <th style="text-align: center;">User Name</th>
                            <th style="text-align: center;">Department</th>
                            <th style="text-align: center;">Designation</th>
                            <th style="text-align: center;"> Address</th>
                            <th style="text-align: center;">Phone Number</th>
                            <th style="text-align: center;">Mobile Number</th>
                            <th style="text-align: center;">Fax Number</th>
                            <th style="text-align: center;">E-mail</th>
                           <c:if test="${uktype eq 'PI'}"> 
                            <!-- <th  align="center">Name of PIA</th>
                            <th style="text-align: center;">Head of PIA</th>
                            <th style="text-align: center;">Type of PIA</th> 
                            <th style="text-align: center;">Action</th> -->
                           </c:if> 
               		</tr>
               		
                	<c:if test="${uktype eq 'SL'}"> 
                	<c:forEach var="list" items="${userList}" varStatus="status">
	                	<tr>
	                	  	<td align="center">${status.count}</td>
	                	  	<td> <c:out value="${list.iwmpDistrict.distName}" />  </td>
	                	    <td> <c:out value="${list.userReg.userId}" />  </td>
	                	    <td> <c:out value="${list.userReg.userName}" />  </td>
	                	    <td> <c:out value="${list.userReg.department}" />  </td>
	                	    <td> <c:out value="${list.userReg.designation}" />  </td>
	                	    <td> <c:out value="${list.userReg.curAddress}" />  </td>
	                	    <td> <c:out value="${list.userReg.phoneNo}" />  </td>
	                	    <td> <c:out value="${list.userReg.mobileNo}" />  </td>
	                	    <td> <c:out value="${list.userReg.faxNo}" />  </td>
	                	    <td> <c:out value="${list.userReg.email}" />  </td>
	                	</tr>
                	</c:forEach>
                	</c:if>
                	
                	<c:if test="${uktype eq 'PI'}"> 
                	<c:set var="distname" value="" />
					<c:set var="count" value="1" />
                	<c:forEach var="list" items="${userList}" varStatus="status">
	                	<tr>
	                	
	                	<c:choose>
							<c:when test="${distname ne list.iwmpMProject.iwmpDistrict.distName}">
								<c:set var="distname" value="${list.iwmpMProject.iwmpDistrict.distName}" />
								<td><c:out value='${count}' /></td>
								<td><c:out value='${distname}' /></td>
								<c:set var="count" value="${count+1}" />
							</c:when>	
							<c:otherwise>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
	                	  	<%-- <td align="center">${status.count}</td>
	                	  	<td> <c:out value="${list.iwmpMProject.iwmpDistrict.distName}" />  </td> --%>
	                	  	<td> <c:out value="${list.iwmpMProject.projName}" />  </td>
	                	    <td> <c:out value="${list.userReg.userId}" />  </td>
	                	    <td> <c:out value="${list.userReg.userName}" />  </td>
	                	    <td> <c:out value="${list.userReg.department}" />  </td>
	                	    <td> <c:out value="${list.userReg.designation}" />  </td>
	                	    <td> <c:out value="${list.userReg.curAddress}" />  </td>
	                	    <td> <c:out value="${list.userReg.phoneNo}" />  </td>
	                	    <td> <c:out value="${list.userReg.mobileNo}" />  </td>
	                	    <td> <c:out value="${list.userReg.faxNo}" />  </td>
	                	    <td> <c:out value="${list.userReg.email}" />  </td>
	                	    <%-- <td> <c:out value="${list.userReg.piaName}" />  </td>
	                	    <td> <c:out value="${list.userReg.piaHead}" />  </td>
	                	    <td> <c:out value="${list.userReg.piaType}" />  </td> 
	                	    <td> <c:out value="${list.userReg.status}" />  </td> --%>
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
      

  <c:if test="${userList.size()<=0 }">
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