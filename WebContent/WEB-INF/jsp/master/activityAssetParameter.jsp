<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src='<c:url value="/resources/js/activityAssetParameter.js" />'></script>
<div class ="card">

<div class="table-responsive">


 <div class="container-fluid">
<div class="row">
<div class="container mt-2">

  <!-- Tab panes -->
  <div class="tab-content">
    <div id="physicalAnnualActionPlan" class="container tab-pane active"><br>
      
   <form name="activityAssetParameter" id="activityAssetParameter">
    
     <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold"> Add Activity of Work </h5>
     <table class="listActivityHeadWise" id="listActivityHeadWise" name="listActivityHeadWise">
     <thead>
     	<tr>
	     	<th class="text-center">Name of Head</th>
	     	<th class="text-center">Name of Activity</th>
	     	<th class="text-center"><span >Parameter</span></th>
	     	<th class="text-center">Unit <br/>(ha/ nos/ Rmt/ Cubic meter)</th>
     	</tr>
     </thead>
     <tbody id="listActivityHeadWiseTbody">
     	<tr>
     		<td>
     			<select id="ddlHead" name="ddlHead" class="ddlHead form-control">
     				<option value="">--Select Head--</option>
     				<c:forEach items="${headList}" var="head">
							<option value="<c:out value="${head.key}"/>"> <c:out value="${head.value}" /></option>
					</c:forEach>
     			</select>
     		</td>
     		<td>
     			<select id="ddlActivity" name="ddlActivity" class="ddlActivity form-control">
     				<option value="">--Select Activity--</option>
     			</select>
     		</td>
     		<td>
     			<input type="text" id="parameterDesc" name="parameterDesc"  class="txtTarget form-control" size="60"/>
     		</td>
     		<td>
     			<!-- <input type="text" id="ddlUnit" name="ddlUnit" class="ddlUnit form-control" /> -->
     			<select id="ddlUnit" name="ddlUnit" class="ddlUnit form-control">
     				<option value="">--Select Unit--</option>
     				<c:forEach items="${unitList}" var="unit">
							<option value="<c:out value="${unit.key}"/>"> <c:out value="${unit.value}" /></option>
					</c:forEach>
     			</select>
     		</td>
     		
     		
     	</tr>
     </tbody>
     </table>
     
     </div>
     </div><br>
     <div class="form-row">
   <div class="form-group col-md-12" align="center">
  
   <button class="btn btn-info" id="Save" name="Save" >Save</button>
  </div>
  </div>
  <div class="form-row">
     <div class="form-group col">
     <hr/>  
     <h5 class="text-center font-weight-bold">List of Added Activity of Work</h5>
     <table class="finalListActivityHeadWise" id="finalListActivityHeadWise" name="finalListActivityHeadWise">
     	<thead>
     		<tr>
     			<th>Name of Head</th>
     			<th>Name of Activity</th>
     			<th>Name of Unit</th>
     			<th>Work Parameters</th>
     		</tr>
     	</thead>
     <tbody id="tbodyPhysicalAnnualActionPlan"></tbody>
     </table>
     </div>
     </div>
    
   </form>
   <br/>
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

</body>
</html>