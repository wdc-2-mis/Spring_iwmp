<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">

<div class="tab-content">
 <!-- <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script> -->
<script src='<c:url value="/resources/js/jquery-ui.js" />'></script> 
<script src='<c:url value="/resources/js/jquery.validate.min.js" />'></script> 

<ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="headstatus" data-toggle="tab" href="#getheadstatus">Wise Work Status</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" id="completeddata" data-toggle="tab" href="#getcompleteddata">Work Wise Completed Records</a>
    </li> 
    <li class="nav-item">
      <a class="nav-link" id="forcloseddata" data-toggle="tab" href="#getforcloseddata">Work Wise Forclosed Records</a>
    </li>
  </ul>
  
 <div class="tab-content">
     <div id="getheadstatus" class="container tab-pane active">
      <div class="col formheading" style="">
			<h4>
				<u>Work Wise Status</u>
			</h4>
		</div>

	   <hr/>
      <form name="assetwisestatus" id="assetwisestatus" class="form-inline">
   
            <div class="alert alert-danger perror" role="alert" style="display: none">Data Saved Successfully</div>

          <div class="alert alert-danger uperror" role="alert" style="display: none">Data Updated Successfully</div>
     
     
  <div class="form-row col-md-4">
    <label for="year">Projects:- </label>
   <!--  <select class="form-control block" id="asswiseproject" name="asswiseproject"> -->
    <select class="form-control col project" id="project" name="project">
      <option value="">--Select Project--</option>
   <c:forEach items="${getproject}" var="assetproject">
   <c:if test="${assetproject.key eq project}">
							<option value="<c:out value='${assetproject.key}'/>" selected="selected"><c:out
									value="${assetproject.value}" /></option>
						</c:if>
						<c:if test="${assetproject.key ne project}">
   	<option value="<c:out value="${assetproject.key}"/>"><c:out
									value="${assetproject.value}" /></option>
						</c:if>
						</c:forEach>
       
    </select>
  </div>
  
 
  
   <div class="form-group col-md-4">
			<label for="project">Financial Year:- </label> <select
				class="form-control col project" id="year" name="year" required>
				<option value="">--Select Financial Year--</option>
				<c:if test="${not empty financialYear}">
					<c:forEach items="${financialYear}" var="fyear">
						<c:if test="${fyear.key eq financial}">
							<option value="<c:out value="${fyear.key}"/>" selected="selected"><c:out
									value="${fyear.value}" /></option>
						</c:if>
						<c:if test="${fyear.key ne financial}">
							<option value="<c:out value="${fyear.key}"/>"><c:out
									value="${fyear.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
			</select>
			
		</div> 
  
  <input type="button" name="view"  id = "view" value="Get Status" class="btn btn-info"/>
  
  <br/><br/><br/><br/><br/>
  <label><span style="color: red;">  Kindly Note Only Selected Check Box Data Will Be Saved Or Update ||</span></label>
  <div class="form-group col-12 confirmation">
    
     <table class="assetWiseStatusId" id="assetWiseStatusId" name="assetWiseStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th>Select</th><th>Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Start Date</th><th>Status</th><th>Complete Date</th><th>Reason  </th><th>Convergence with other Scheme/Program</th></tr></thead>
     <tbody id="tbodyassetWiseStatusId"></tbody>
     
     
     </table>
    
     </div>
  
  </form>
    </div>
    
    <div id="getcompleteddata" class="container tab-pane fade">
		<div class="col formheading" style="">
			<h4>
				<u>Work Wise Completed Records</u>
			</h4>
		</div>
 <hr/>
 <form name="assetwisecomplete" id="assetwisecomplete" class="form-inline" autocomplete="off">
 <div class="form-row col-md-4">
    <label for="year">Projects:- </label>
   <!--  <select class="form-control block" id="asswiseproject" name="asswiseproject"> -->
    <select class="form-control col project" id="cproject" name="cproject">
      <option value="">--Select Project--</option>
   <c:forEach items="${getproject}" var="assetproject">
   <c:if test="${assetproject.key eq cproject}">
							<option value="<c:out value='${assetproject.key}'/>" selected="selected"><c:out
									value="${assetproject.value}" /></option>
						</c:if>
						<c:if test="${assetproject.key ne project}">
   	<option value="<c:out value="${assetproject.key}"/>"><c:out
									value="${assetproject.value}" /></option>
						</c:if>
						</c:forEach>
       
    </select>
  </div>
  
 
  
   <div class="form-group col-md-4">
			<label for="project">Financial Year:- </label> <select
				class="form-control col project" id="cyear" name="cyear" required>
				<option value="">--Select Financial Year--</option>
				<c:if test="${not empty financialYear}">
					<c:forEach items="${financialYear}" var="fyear">
						<c:if test="${fyear.key eq cfinancial}">
							<option value="<c:out value="${fyear.key}"/>" selected="selected"><c:out
									value="${fyear.value}" /></option>
						</c:if>
						<c:if test="${fyear.key ne financial}">
							<option value="<c:out value="${fyear.key}"/>"><c:out
									value="${fyear.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
			</select>
			
		</div> 
 &nbsp
  <input type="button" name="viewcomplete"  id = "viewcomplete" value="Get Status" class="btn btn-info"/>
 <br/><br/><br/>
 
  <div class="form-group col-12 confirmation">
    
     <table class="assetWiseStatusId" id="assetWiseStatusId" name="assetWiseStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th>Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Start Date</th><th>Complete Date</th><th>Convergence with other Scheme/Program</th></tr></thead>
     <tbody id="tbodyassetWiseCStatusId"></tbody>
     
     
     </table>
    
     </div>
 
 
 
 </form>
 
 </div>

   <div id="getforcloseddata" class="container tab-pane fade">
		<div class="col formheading" style="">
			<h4>
				<u>Work Wise Forclosed Records</u>
			</h4>
		</div>
 <hr/>
 <form name="assetwiseforclose" id="assetwiseforclose" class="form-inline" autocomplete="off">
 <div class="form-row col-md-4">
    <label for="year">Projects:- </label>
   <!--  <select class="form-control block" id="asswiseproject" name="asswiseproject"> -->
    <select class="form-control col project" id="fproject" name="fproject">
      <option value="">--Select Project--</option>
   <c:forEach items="${getproject}" var="assetproject">
   <c:if test="${assetproject.key eq fproject}">
							<option value="<c:out value='${assetproject.key}'/>" selected="selected"><c:out
									value="${assetproject.value}" /></option>
						</c:if>
						<c:if test="${assetproject.key ne project}">
   	<option value="<c:out value="${assetproject.key}"/>"><c:out
									value="${assetproject.value}" /></option>
						</c:if>
						</c:forEach>
       
    </select>
  </div>
  
 
  
   <div class="form-group col-md-4">
			<label for="project">Financial Year:- </label> <select
				class="form-control col project" id="foryear" name="foryear" required>
				<option value="">--Select Financial Year--</option>
				<c:if test="${not empty financialYear}">
					<c:forEach items="${financialYear}" var="fyear">
						<c:if test="${fyear.key eq ffinancial}">
							<option value="<c:out value="${fyear.key}"/>" selected="selected"><c:out
									value="${fyear.value}" /></option>
						</c:if>
						<c:if test="${fyear.key ne financial}">
							<option value="<c:out value="${fyear.key}"/>"><c:out
									value="${fyear.value}" /></option>
						</c:if>
					</c:forEach>
				</c:if>
			</select>
			
		</div> 
 &nbsp
  <input type="button" name="viewforclosed"  id = "viewforclosed" value="Get Status" class="btn btn-info"/>
 <br/><br/><br/>
 
  <div class="form-group col-12 confirmation">
    
     <table class="assetWiseStatusId" id="assetWiseStatusId" name="assetWiseStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th>Work-Id</th><th>Name of Head</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Start Date</th><th>Reason</th><th>Convergence with other Scheme/Program</th></tr></thead>
     <tbody id="tbodyassetWiseFStatusId"></tbody>
     
     
     </table>
    
     </div>
 
 
 
 </form>
 
 </div>


</div> 
 
</div> 
    
</div>
</div>
</div>


<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/assetstatus.js" />'></script>
</body>
</html>