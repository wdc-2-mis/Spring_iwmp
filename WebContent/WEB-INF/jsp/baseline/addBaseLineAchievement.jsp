<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="container">
<div class="col" style="text-align:center;"><h5>Month-wise Updation Status of Baseline Plot-wise details</h5></div>
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
     <button class="btn btn-info" id="btnGetActivity" name="btnGetActivity" >Get Data</button>
     </div>
     </div>
     <div class="form-row">Duration of Baseline Updation:&nbsp;
     <div class="form-group col-2">
     <label for="ddlProject">Start Date: </label><label id="lblStartDate"></label>
     </div>
     <div class="form-group col-4">
     <label for="ddlProject">End Date: </label><label id="lblEndDate"></label>
     </div>
     </div>
     
     
     <div class="form-row">Note<span style="color: red;">*</span>&nbsp; :- If as on data is not correct then update your &nbsp;<a href="blsoutupdate" style="font-size:1vw;"> Plot-wise details under the Updation of Plot-wise details. </a> </div>
  
     <br>
     
     <div class="form-row">
     <div class="form-group col-12">
  
     <table class="tblAddPhysicalAchievement" id="tblAddPhysicalAchievement" name="tblAddPhysicalAchievement" style="width:100%">
     <thead><tr><th class="text-center">Sl.No.</th><th  class="text-center"> </th>
     <th class="text-center">Baseline Survey</th><th class="text-center">As on Date</th></tr></thead>
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
<script src='<c:url value="/resources/js/baselineUpdateAchievement.js" />'></script>
<script src='<c:url value="/resources/js/jquery.validate.js" />'></script>
</body>
</html>