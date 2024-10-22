<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
.input {    width: inherit !important;}
.halfwidth {width:50px !important;}
</style>
<div class="maindiv">

<div class="col formheading" style=""><h4><u>Add Details of Training </u></h4> </div>
    <hr/>
    <label class="errormessage"></label>
<form class="form-inline" autocomplete="off">
<div class="col-2"></div>
  
  <div class="form-group col-md-4">
    <label for="level" class="">Training conducted at level </label> &nbsp;&nbsp;
     <select class="form-control col-4 groupType" id="groupType" name="groupType" style="text-align:center; width: 30%; ">
    <option value="">--Select level--</option>
     <option value="SL">SLNA</option>
     <option value="DI">WCDC</option>
     <option value="PI">PIA</option>
     <option value="WC">WCs</option>
    </select>
  </div>
 
  <div class="form-group col-md-3 ">
    <label for="txtNoOf" class=""> No. of training conducted </label> &nbsp;&nbsp;
    <input type="text" class="form-control col-3" id="noOf" name="noOf" onmousedown="numericOnly(event);">
  </div>
  <div class="form-group col-md-2">
  <label for="txtNoOf" class=""></label>
   <button  class="btn btn-info" id="btnGo" name="btnGo">Go</button>
  </div>
 
  <br/> <br/>
  <div class="col-2"> <br/><br/></div>
  <br />
		<br />
		<br>
		<br>
		<br />
  <table id="tblSHGDetails" style="width:30% !important">
  
  </table>
   
  <div class="form-group col text-center d-none button">
 
  <div class="col-3"></div>
  <br><br><br><br>
  <!--  <button  class="btn btn-info col-2" id="btnCancel" name="btnCancel">Reset</button>&nbsp;&nbsp; -->
    <button  class="btn btn-info col-2" id="btnSave" name="btnSave">Save</button>
  </div>
  
</form>
 
</div>

<c:if test="${dataListSize gt 0}">
<div style="margin-left:auto;margin-right:auto; width: 60%;">
  <br />
		<br/>
		<br/>
		<br />
		<h5><b> List of recently added training </b></h5>
   <table class="table table-bordered table-striped table-highlight w-auto">
  
  <thead>
 
	  <tr>
		  <th> Level at which training conducted</th>
		  <th> No. of Training conducted</th>
		  <th> Total no. of Persons trained</th>
		  <th width="20"> Subject</th>
		  <th> Start Date</th>
		  <th> End Date</th>
		  <th> No. of Training days</th>
  </thead>
  <c:set var="train" value="" />
  <c:forEach items="${dataList}" var="dataV" varStatus="count">   
					<tr>
						
						
						
		           		
		           		<c:choose>
							<c:when test="${train ne dataV.training_id}">
								<c:set var="train" value="${dataV.training_id}" />
								<c:if test="${dataV.training_level eq 'SL'}">
									<td align="center">SLNA</td>
								</c:if>
								<c:if test="${dataV.training_level eq 'DI'}">
									<td align="center">WCDC</td>
								</c:if>
								<c:if test="${dataV.training_level eq 'PI'}">
									<td align="center">PIA</td>
								</c:if>
								<c:if test="${dataV.training_level eq 'WC'}">
									<td align="center">WC</td>
								</c:if>
								<td align="center"><c:out value='${dataV.totalno}' /> </td>
								
							</c:when>	
							<c:otherwise>
								<c:if test="${dataV.training_level eq 'SL'}">
									<td align="center"></td>
								</c:if>
								<c:if test="${dataV.training_level eq 'DI'}">
									<td align="center"></td>
								</c:if>
								<c:if test="${dataV.training_level eq 'PI'}">
									<td align="center"></td>
								</c:if>
								<c:if test="${dataV.training_level eq 'WC'}">
									<td align="center"></td>
								</c:if>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td align="center"><c:out value='${dataV.person_no}' /> </td>
						<td align="left" width="15"><c:out value='${dataV.subject}' /> </td>
						<td align="center"><c:out value='${dataV.start_date}' /> </td>
						<td align="center"><c:out value='${dataV.end_date}' /> </td>
						<td align="center"><c:out value='${dataV.training_days}' /> </td>
  
  	</tr>
  	</c:forEach>
  </table>
  
  </div>
</c:if>

<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/trainingCapicityBuilding.js" />'></script>
</body>
</html>