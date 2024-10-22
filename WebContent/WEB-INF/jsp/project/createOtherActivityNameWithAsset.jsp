<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
function savedata(e) 
{
	if(document.getElementById("project").value==='')
	{
		alert('Please select project !');
		$('#project').focus();
		e.preventDefault();
	}
	if(document.getElementById("year").value==='')
	{
		alert('Please select year !');
		$('#year').focus();
		e.preventDefault();
	}
	if(document.getElementById("othername").value==='')
	{
		alert('Please select Categories !');
		$('#othername').focus();
		e.preventDefault();
	}
	else{
		
		document.createAssetOtherActivityName.action="updateAssetOtherActivityName";
		document.createAssetOtherActivityName.method="post";
		document.createAssetOtherActivityName.submit();
	}
	return false;

}

</script>


<c:if test="${messagerj != null}">
	<script>
	    alert("<c:out value='${messagerj}'/>");
	</script>
</c:if>

<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">
 <!--  <h2>Toggleable Tabs</h2>
 
  <!-- Tab panes -->
  <div class="tab-content">
    <div id="assetId" class="container tab-pane active"><br>
      <h3>Add Sub-Categories of Others Activities for already approved Works</h3>
     <form name="createAssetOtherActivityName" id="createAssetOtherActivityName" action="createAssetOtherActivityName" method="get">
     <lable class="message badge badge-danger error"></lable>
      <hr/>
  <div class="form-row">
  <div class="form-group col-md-6">
    <label for="project">Projects: </label>
    <select class="form-control project" id="project" name="project" >
    	<option value="">--Select Project--</option>
     	<c:forEach items="${projectList}" var="project"> 
	     	<c:if test="${proj eq project.key }">
				<option value="<c:out value="${project.key}"/>" selected="selected"><c:out value="${project.value}" /></option>
			</c:if>	
			<c:if test="${proj ne project.key }">
				<option value="<c:out value="${project.key}"/>"><c:out value="${project.value}" /></option>
			</c:if>	
		</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-6">
    <label for="year">Financial Year: </label>
    <select class="form-control year" id="year" name="year" onchange="this.form.submit();">
      <option value="">--Select Year--</option>
      <c:forEach var="list" items="${yearList}">
			<c:if test="${fyear eq list.key }">
				<option value="${list.key }" selected><c:out value="${list.value }" /></option>
			</c:if>
			<c:if test="${fyear ne list.key }">
				<option value="${list.key }"><c:out value="${list.value }" /></option>
			</c:if>
	  </c:forEach>
       
    </select>
  </div>
  </div>
  
    
     <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"><u>List of approved Work-Id of Others Activity for Add Categories  </u></h5>
     <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise" style="width:100%">
     	<thead>
     	<tr>
     		<th>S.No.</th>
     		<th class="text-center">Works Id (Block, Village)</th>
     		<th class="text-center">Name of Head</th>
     		<th class="text-center">Name of Activity</th>
     		<th>Categories <span style="color: red;">*</span></th>
     	</tr>
     	</thead>
      <tbody>
   
	 <c:if test="${dataList != null}">
		<c:set var="assetid" value="" />
		<c:forEach items="${dataList}" var="dataV" varStatus="status">
		<tr>	
			<c:choose>
				<c:when test="${assetid ne dataV.assetid}">
					<c:set var="assetid" value="${dataV.assetid}" />
					<td><c:out value='${status.count}' /></td>
					<input type="hidden" id="workid" name="workid" value="${dataV.assetid}">
					<td><c:out value='${assetid}' />&nbsp;( &nbsp;<c:out value='${dataV.blockname}' />, &nbsp; <c:out value='${dataV.village_name}' />&nbsp;)</td>
					<td><c:out value='${dataV.headname}' /></td>
					<td><c:out value='${dataV.actname}' /></td>  
					<%-- <td>
					 <c:set var="otheract" value="${fn:split(dataV.other_activity_code,',')}" />
					 <c:set var="othername" value="${fn:split(dataV.other_activity_desc,',')}" />
					 <select class="form-control year" id="othername" name="othername" >
					 	<c:forEach var="num" items="${othername}">
  								<option value="${num}" ><c:out value="${num}" /></option>
						</c:forEach>
    				 </select>
					</td> --%>
					 <td>
					 <select class="form-control year" id="othername" name="othername" >  
					 	<c:forEach var="list" items="${otherList}">
					 	<c:if test="${dataV.other_activity_code_save eq list.key }">
					 		<option value="${list.key }"  selected="selected" ><c:out value="${list.value }"  /></option>
					 	</c:if>
					 	<c:if test="${dataV.other_activity_code_save ne list.key }">
					 		<option value="${list.key }"><c:out value="${list.value }" /></option>
					 	</c:if>
  								
						</c:forEach>
    				 </select>
					</td>
					
				
				</c:when>	
				<c:otherwise>
				
				</c:otherwise>
				
			</c:choose>
		</tr>		
		</c:forEach>
    </c:if> 
    	 <c:if test="${dataList == null}"> 
			<tr>
				<!-- <td align="center" colspan="4" class="required">Data Not Found</td> -->
				<td align="center" colspan="5" class="required">Note <span style='color: red;'>*</span> :-  Kindly contact your SLNA ADMIN to add the Others Activities Category Data.</td>
			</tr>
		 </c:if> 
		
		 <c:if test="${dataList == null}"> 
			<tr>
				<td align="center" colspan="5" class="required">Data Not Found</td>
				
			</tr>
		 </c:if>
    
  </tbody>
     
     
     </table>
   
     </div>
     </div>

     <div class="form-row">
   <div class="form-group col-md-6">
   <input type="button" class="btn btn-info" id="view" onclick="savedata(this);"  name="Save" value='Save' /> 
  <!--  <button class="btn btn-info" id="draftSave" name="draftSave" >Save</button> -->
  </div>
  </div>
     
   </form>
    <div class="form-row">
   <div class="form-group col-md-12">
   Note:-  Kindly contact your SLNA ADMIN to add the Others Activities Category Data.
 
  </div>
  </div>
   
   
     <br/>
     
     <br/>
      <c:if test="${saveList != null}">
      <h5 class="text-center font-weight-bold"><u>List of Added Others Activity </u></h5>
     <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise" style="width:100%">
     	<thead>
     	<tr>
     		<th>S.No.</th>
     		<th class="text-center">Works Id (Block, Village)</th>
     		<th class="text-center">Name of Head</th>
     		<th class="text-center">Name of Activity</th>
     		<th>Name of Other Activity</th>
     	</tr>
     	</thead>
      <tbody>
   
	
		<c:forEach items="${saveList}" var="dataV" varStatus="status">
		<tr>	
					<td><c:out value='${status.count}' /></td>
					<td><c:out value='${dataV.assetid1}' />&nbsp;( &nbsp;<c:out value='${dataV.blockname}' />, &nbsp; <c:out value='${dataV.village_name}' />&nbsp;)</td>
					<td><c:out value='${dataV.headname}' /></td>
					<td><c:out value='${dataV.actname}' /></td>
					<td><c:out value='${dataV.other_activity_desc}' /></td>
		</tr>		
		</c:forEach>
   
    	
  </tbody>
     </table>
     
    </c:if> 
   
   
   
   
   
 </div>
    
  </div>
</div>
</div>
</div>
 </div>
 </div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/assetId.js" />'></script>
</body>
</html>