<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
.input {    width: inherit !important;}
.halfwidth {width:50px !important;}
</style>
<div class="maindiv">

<div class="col formheading" style=""><h4><u>Approval of Revised Action Plan </u></h4> </div>
    <hr/>
<form name="revisedActPlan" id="revisedActPlan">
<lable class="message badge badge-danger error"></lable>
<hr/>
<div class="form-row">
     <div class="form-group col-3">
      <label for="district">District: </label>
    <select class="form-control district" id="district" name="district" >
    <option value="">--Select--</option>
     <c:forEach items="${distList}" var="dist">
							<option value="<c:out value="${dist.key}"/>"><c:out
									value="${dist.value}" /></option>
						</c:forEach>
    </select>
     </div>
     <div class="form-group col-3 ">
      <label for="project">Project Name: </label>
    <select class="form-control project" id="project" name="project" >
    <option value="">--Select--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
     </div>
     
     <div class="form-group col-3 ">
      <label for="finyear">Action Plan(Financial Year): </label>
    <select class="form-control finyear" id="finyear" name="finyear" >
    <option value="">--Select--</option>
     <c:forEach items="${financialYear}" var="finYr" >
							<option value="<c:out value='${finYr.finYrCd}'/>">  <c:out value="${finYr.finYrDesc}" />
							</option>
						</c:forEach>
    </select>
     </div>
  </div>
  <div class="form-row">
     
      <div class="form-group col-7 ">
      <label for="dprstatus">Whether changes in DPR(Annual Action Plan) in being made with the approval of competent Authority:  </label>
    <select class="form-control dprstatus" id="dprstatus" name="dprstatus" >
    <option value="">--Select--</option>
    <option value="Y">Yes</option>
    <option value="N">No</option>
    </select>
     </div>
     </div>
     
     <div class="form-group col-6 ">
      <label for="authname">Name of Competent Authority:  </label>
    <input type= "text" id="authname" name="authname" >
     </div>
  
      
     <div class="form-group col-6">
      <label for="button"> &nbsp;</label>
     <button class="btn btn-info" id="saveActionPlan" name="saveActionPlan" >Submit</button>
     </div>
     
     	<table class="table table-bordered table-striped table-highlight w-auto" id="dtBasicExample">
						<thead>
							<tr>
								<th>S.No.</th>
								<th>Project Name</th>
								<th>Action Plan(Financial Year)</th>
								<th>Approved</th>
								<th>Name of Competent Authority</th>
								<th>Action</th>
							</tr>

						</thead>
						<tbody id ="tbodyRevisedActPlanListId">
							<tr><td colspan='6' class='text-center'>Data not found !</td></tr>
						</tbody>
		</table>
     </form>
     </div>
    
 <footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/approvalRevisedActPlan.js" />'></script>
</body>
</html>