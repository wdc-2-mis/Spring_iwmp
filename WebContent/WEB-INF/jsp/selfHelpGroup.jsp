<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.input {    width: inherit !important;}
.halfwidth {width:50px !important;}
</style>
<div class="maindiv">
<script type="text/javascript">

function addRating() {
	var k=document.getElementById("noOf").value;
	//alert(k+ "kk");
	  if (k <= 5 && k >= 1) {
	    
	  } else {
		  document.getElementById("noOf").value="";
	    alert("Invalid Input. Please enter an integer between 1-5.");
	    return false;
	  }
	}


</script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script
		src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<c:forEach var="group" items="${groupType}" >
 <c:forEach var="files" items="${group.value}">
 <c:choose>
				<c:when test="${group.key == 'group'}">
				<div class="col formheading" style=""><h4><u>Add/ Edit User Group(UGs)</u></h4> </div>
				</c:when>
				<c:when test="${group.key == 'newSHG'}">
				<div class="col formheading" style=""><h4><u>Add/ Edit Newly Created Self Help Group(SHGs) </u></h4> </div>
				</c:when>
				<c:otherwise>
				<div class="col formheading" style=""><h4><u>Add/ Edit Existing Self Help Group(SHGs)</u></h4> </div>
				</c:otherwise>
				</c:choose>
			</c:forEach>
			</c:forEach>
    <hr/>
    <label class="errormessage"></label>
<form class="form-inline" autocomplete="off">

  <div class="form-group col-md-4">
    <label for="project" class="">Project:- </label>
     <select class="form-control col project" id="project" name="project" >
    <option value="">--Select Project--</option>
     <c:forEach items="${projectList}" var="project">
							<option value="<c:out value="${project.key}"/>"><c:out
									value="${project.value}" /></option>
						</c:forEach>
    </select>
  </div>
  <div class="form-group col-md-3">
    <label for="project" class="">Group Type:- </label>
     <select class="form-control col groupType" id="groupType" name="groupType" >
    <option value="">--Select Type--</option>
    <!--  <option value="newSHG">Newly created SHG</option>
     <option value="oldSHG">Existing SHG</option>
     <option value="group">User Groups</option> -->
      <c:forEach items="${groupType}" var="group">
							<option value="<c:out value="${group.key}"/>"><c:out
									value="${group.value}" /></option>
						</c:forEach> 
    </select>
  </div>
 
  <div class="form-group col-md-3 ">
    <label for="txtNoOf" class="lblNoOf noOf d-none"></label>
    <input type="text" class="form-control col-3 noOf d-none" id="noOf" name="noOf" onblur="addRating(this);" onmousedown="numericOnly(event)">
  </div>
  <div class="form-group col-md-2">
  <label for="txtNoOf" class="">&nbsp;</label>
   <button  class="btn btn-info d-none" id="btnGo" name="btnGo">Go</button>
  </div>
  
  <div style="margin-left:auto;margin-right:auto;">
  <br/> <br/>
  <table id="tblSHGDetails" >
  
  </table>
  
  
  <div class="form-group col text-center d-none button">
  <br/><br/>
   <button  class="btn btn-info col-2" id="btnCancel" name="btnCancel">Reset</button>&nbsp;&nbsp;
    <button  class="btn btn-info col-2" id="btnSave" name="btnSave">Save as Draft</button>
  </div>
  </div>
  
  <div style="margin-left:auto;margin-right:auto;">
  <br/><br/>
  <table id="tblSHGDetailsFinal" >
  
  </table>
  
  </div>
  
</form>
 
</div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/shg.js" />'></script>
</body>
</html>