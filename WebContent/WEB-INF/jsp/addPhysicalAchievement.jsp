<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="container">
<div class="col" style="text-align:center;"><h5>Add Physical Achievement</h5></div>
  <form name="addPhysicalAchievement" id="addPhysicalAchievement">
     <lable class="message badge badge-danger error"></lable>
     <input type="hidden" value="" id="finYr" />
     <input type="hidden" value="" id="monthId" />
      <hr/>
      <div class="form-row">
     <div class="form-group col-5">
      <label for="ddlProject">Projects: </label>
    <select class="form-control ddlProject" id="ddlProject" name="ddlProject" >
    <option selected hidden=true value="">--Select--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
     </div>
      
     <div class="form-group col-1">
      <label for="btnGetActivity"> &nbsp;</label>
     <button class="btn btn-info" id="btnGetActivity" name="btnGetActivity" >Get Activity</button>
     </div>
     </div>
     <div class="form-row">Duration of Achievement:&nbsp;
     <div class="form-group col-2">
     <label for="ddlProject">Start Date: </label><label id="lblStartDate"></label>
     </div>
     <div class="form-group col-4">
     <label for="ddlProject">End Date: </label><label id="lblEndDate"></label>
     </div>
     </div>
     
     
     <div class="form-row">Note<span style="color: red;">*</span>&nbsp; :- Click and Confirm Activity blue link before Submitting the Achievement. </div>
  
     <br>
     
     <div class="form-row">
     <div class="form-group col-12">
    <!--  <h5 class="text-center font-weight-bold"><u>Forwarded List of Activity With Temp. Asset Id</u></h5> -->
     <table class="tblAddPhysicalAchievement" id="tblAddPhysicalAchievement" name="tblAddPhysicalAchievement" style="width:100%">
     <thead><tr><th rowspan="2" class="text-center">Name of Head</th><th rowspan="2" class="text-center">Name of Activity</th><th rowspan="2" class="text-center">Unit</th><th colspan="5" class="text-center">Achievement</th></tr>
     <tr><th class="text-center">Balanced target upto previous year (A)</th><th class="text-center">Target of the year (B)</th><th class="text-center">Total target (A+B)</th><th class="text-center">Achieved till now in the above mentioned financial year</th><th class="text-center">Achievement during the above mentioned period</th></tr></thead>
     <tbody id="tbodyAddPhysicalAchievement"></tbody>
     </table>
     </div>
  </div>
   <br>
   	
  <div class="checkbox-group required">
   <input type="checkbox" id="blsid" name="blsid" />&nbsp; :- It is certified that Plot-wise data has been updated wherever there have been changes in the Plots.
  </div>
  
   <br>
  <div class="form-row">
   <div class="col-md-2 d-inline-block">
   <button class="btn btn-info d-none" id="btnForward" name="btnForward" >Forward</button>
  </div>
  
  <div class="col-md-2 d-inline-block">
   <select id="ddlUser" class="d-none form-control"></select>
  </div>
  </div>
     </form>
     <br/>
     </div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/physicalAchievement.js" />'></script>
<script src='<c:url value="/resources/js/jquery.validate.js" />'></script>
</body>
</html>