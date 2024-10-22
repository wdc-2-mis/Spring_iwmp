<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


 <div class="col" style="text-align:center;"><h5></h5></div>
 <div class="container-fluid">
<div class="row">
<div class="container mt-2">

<!-- <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>--> 
 
<script src='<c:url value="/resources/js/jquery-ui.js" />'></script> 
<script src='<c:url value="/resources/js/jquery.validate.min.js" />'></script>  
<ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" id="headstatus" data-toggle="tab" href="#getheadstatus">Head Wise Work Status</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" id="completeddata" data-toggle="tab" href="#getcompleteddata">Head Wise Work Completed Records</a>
    </li> 
    <li class="nav-item">
      <a class="nav-link" id="forcloseddata" data-toggle="tab" href="#getforcloseddata">Head Wise Work Forclosed Records</a>
    </li>
  </ul>
  

<div class="tab-content">
 
      <div id="getheadstatus" class="container tab-pane active">
		<div class="col formheading" style="">
			<h4>
				<u>Head Wise Work Status</u>
			</h4>
		</div>

	
      <hr/>
      <form name="assetwiselivestatus" id="assetwiselivestatus" class="form-inline" autocomplete="off">
 
            <div class="alert alert-danger perror" role="alert" style="display: none">Data Saved Successfully</div>

          <div class="alert alert-danger uperror" role="alert" style="display: none">Data Updated Successfully</div>
     
     
   <div class="form-row col-md-3">
    <label for="year"><b>Projects:</b> </label>
   <!--  <select class="form-control block" id="asswiseproject" name="asswiseproject"> -->
    <select class="form-control col project" id="project" name="project">
      <option value="">--Select Project--</option>
   <c:forEach items="${getproject}" var="assetheadproject">
   <c:if test="${assetheadproject.key eq project}">
							<option value="<c:out value='${assetheadproject.key}'/>" selected="selected"><c:out
									value="${assetheadproject.value}" /></option>
						</c:if>
						<c:if test="${assetheadproject.key ne project}">
   	<option value="<c:out value="${assetheadproject.key}"/>"><c:out
									value="${assetheadproject.value}" /></option>
						</c:if>
						</c:forEach>
       
    </select>
  </div> 
  
  <div class="form-row col-md-4">

				<label for="project" class=""><b>Head:-</b></label>
<select class="form-control col project" id="head" name="head" required>
					<option value="">--Select Type--</option>

					<option value="l">Livelihood Activities for the Assetless Persons & Microenterprises</option>

					<option value="p">Production System</option>

					<option value="e">Entry Point Activity (EPA)</option>
</select>
			</div> 
			
<div class="form-row col-md-4">

				<label for="project" class=""><b>Head Activity:-</b></label>
<select class="form-control col project" id="headactivity" name=headactivity required>
      <option value="">--Select Head Activity--</option>
     <!--  <option value="0">--All--</option>  -->
    </select>
			</div>			
			
     
  
  &nbsp
  <input type="button" name="view"  id = "view" value="Get Status" class="btn btn-info"/>
  
  <br/><br/><br/>
  <label><span style="color: red;">  Kindly Note Only Selected Check Box Data Will Be Saved Or Update ||</span></label>
 
  <div class="form-group col-12 confirmation">
    
     <table class="assetWiseheadStatusId" id="assetWiseheadStatusId" name="assetWiseheadStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th>Select</th><th>Work-Id</th><th>Name of Activity</th><th>Block</th><th class="text-center">Village</th><th>Start Date</th><th>Status</th><th>Complete Date</th><th>Reason  </th></tr></thead>
     <tbody id="tbodyassetWiseHeadStatusId"></tbody>
     
     
     </table>
    
     </div>
  
  </form>
  </div>
  
  <div id="getcompleteddata" class="container tab-pane fade">
		<div class="col formheading" style="">
			<h4>
				<u>Head Wise Work Completed Records</u>
			</h4>
		</div>
 <hr/>
  <form name="assetwisecomplete" id="assetwisecomplete" class="form-inline" autocomplete="off">
    <div class="form-row col-md-4">
    <label for="year"><b>Projects:</b> </label>
   <!--  <select class="form-control block" id="asswiseproject" name="asswiseproject"> -->
    <select class="form-control col project" id="cproject" name="cproject">
      <option value="">--Select Project--</option>
   <c:forEach items="${getproject}" var="assetheadproject">
   <c:if test="${assetheadproject.key eq cproject}">
							<option value="<c:out value='${assetheadproject.key}'/>" selected="selected"><c:out
									value="${assetheadproject.value}" /></option>
						</c:if>
						<c:if test="${assetheadproject.key ne project}">
   	<option value="<c:out value="${assetheadproject.key}"/>"><c:out
									value="${assetheadproject.value}" /></option>
						</c:if>
						</c:forEach>
       
    </select>
  </div> 
  
  <div class="form-row col-md-4">

				<label for="project" class=""><b>Head:-</b></label>
<select class="form-control col project" id="chead" name="chead" required>
					<option value="">--Select Type--</option>

					<option value="l">Livelihood Activities for the Assetless Persons & Microenterprises</option>

					<option value="p">Production System</option>

					<option value="e">Entry Point Activity (EPA)</option>
</select>
			</div> 
			
			<div class="form-row col-md-3">

				<label for="project" class=""><b>Head Activity:-</b></label>
<select class="form-control col project" id="cheadactivity" name=cheadactivity required>
      <option value="">--Select Head Activity--</option>
     <!--  <option value="0">--All--</option>  -->
    </select>
			</div>	
       &nbsp
  <input type="button" name="viewcomplete"  id = "viewcomplete" value="Get Status" class="btn btn-info"/>
  
  <br/><br/><br/>
  <div class="form-group col-12 confirmation">
    
     <table class="assetWiseheadStatusId" id="assetWiseheadStatusId" name="assetWiseheadStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th>Work-Id</th><th>Name of Activity</th><th>Block</th><th>Village</th><th>Start Date</th><th>Complete Date</th></tr></thead>
     <tbody id="tbodyassetWiseCHeadStatusId"></tbody>
     
     
     </table>
    
     </div>
      </form>
      </div>
      
 <div id="getforcloseddata" class="container tab-pane fade">
		<div class="col formheading" style="">
			<h4>
				<u>Head Wise Work Forclosed Records</u>
			</h4>
		</div>

	
      <hr/> 
      <form name="assetwiseforclose" id="assetwiseforclose" class="form-inline" autocomplete="off">
         <div class="form-row col-md-4">
    <label for="year"><b>Projects:</b> </label>
   <!--  <select class="form-control block" id="asswiseproject" name="asswiseproject"> -->
    <select class="form-control col project" id="fproject" name="fproject">
      <option value="">--Select Project--</option>
   <c:forEach items="${getproject}" var="assetheadproject">
   <c:if test="${assetheadproject.key eq project}">
							<option value="<c:out value='${assetheadproject.key}'/>" selected="selected"><c:out
									value="${assetheadproject.value}" /></option>
						</c:if>
						<c:if test="${assetheadproject.key ne fproject}">
   	<option value="<c:out value="${assetheadproject.key}"/>"><c:out
									value="${assetheadproject.value}" /></option>
						</c:if>
						</c:forEach>
       
    </select>
  </div> 
  
  <div class="form-group">

				<label for="project" class=""><b>Head:-</b></label>
<select class="form-control col project" id="fhead" name="fhead" required>
					<option value="">--Select Type--</option>

					<option value="l">Livelihood Activities for the Assetless Persons & Microenterprises</option>

					<option value="p">Production System</option>

					<option value="e">Entry Point Activity (EPA)</option>
</select>
			</div>    
			&nbsp
  <input type="button" name="viewforclosed"  id = "viewforclosed" value="Get Status" class="btn btn-info"/>
  
  <br/><br/><br/>
  <div class="form-group col-12 confirmation">
    
     <table class="assetWiseheadStatusId" id="assetWiseheadStatusId" name="assetWiseheadStatusId" style="width:100%">
     <thead><tr><th>S No.</th><th>Work-Id</th><th>Name of Activity</th><th>Block</th><th>Village</th><th>Start Date</th><th>Reason</th></tr></thead>
     <tbody id="tbodyassetWiseFHeadStatusId"></tbody>
     </table>
     </div> 
  </form>
  </div>
  
  
  </div>
  </div>
  </div>
  </div>
   
  
  <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/assetheadstatus.js" />'></script>
</body>
</html>