<%@include file="/WEB-INF/jspf/header2.jspf"%>
  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
 <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">
	<script src='<c:url value="/resources/js/secondpasswordset.js" />'></script>
  
<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>Secondary Password Reset</h5></div>
 <form autocomplete="off" name="secondpasswordset" id="secondpasswordset" modelAttribute="secondPassword" >
 <label class="message badge badge-danger" id="error" name="error"></label>
 <label class="message badge badge-<c:if test="${result == 'success'}">success</c:if><c:if test="${result == 'fail'}">danger</c:if>" id="message">  <c:out value="${message}" /></label>
      <table class="table">
        <tr>
         <td colspan="2" class="label">User Type</td>
          <td colspan="1" class="field">
              <select name="userType" id="userType" class="form-control" onchange="userTypeChange();">
                 <!--  <option value="">-- Select --</option>
                 <option value="DLA"> DoLR Admin </option>
						<option value="DL"> DoLR </option>
						<option value="SLA"> SLNA Admin </option>
						<option value="SL"> SLNA </option>
						<option value="DI"> WCDC </option>
						<option value="PI"> PIA </option>
						<option value="FREEZE"> Unfreezing User </option>
						<option value="DP"> Data Porting </option> -->
						 <option value="">-- Select --</option>
                  <c:if test="${roleList != null}">
                     <c:forEach var="entry" items="${roleList}">
                        <option value="${entry.key}"><c:out value="${entry.value}"/></option>
                     </c:forEach>
                  </c:if>
              </select>
          </td>
          <td colspan="2" class="label">State</td>
          <td colspan="1" class="field">
              <select name="state" id="state" class="form-control" onchange="onStateChange(this.value);">
                  <option value="">-- Select --</option>
                  <c:if test="${stateList != null}">
                     <c:forEach var="entry" items="${stateList}">
                        <option value="${entry.key}"><c:out value="${entry.value}"/></option>
                     </c:forEach>
                  </c:if>
              </select>
          </td>
            </tr><tr>
           <td colspan="2" class="label">District</td>
          <td colspan="1" class="field">
              <select name="district" id="district" class="form-control" onchange="getProjectByDcode(this.value);">
                  <option value="">-- Select --</option>
                  
              </select>
          </td>
           <td colspan="2" class="label">Project List<span class="required"></span></td>
          <td colspan="1" class="field">
              <select name="project" id="project" class="form-control">
                  <option value="">-- Select --</option>
                  
              </select>
          </td>
        </tr>
        <tr>
         <td colspan="2" class="label">New Password<span class="required"></span></td>
         <td colspan="1" class="field"><input type="password" name="newPassword" id="newPassword" class="form-control" size="20"/></td>
         <td colspan="2" class="label">Confirm Password<span class="required"></span></td>
         <td colspan="1" class="field"><input type="password" name="confirmPassword" id="confirmPassword" class="form-control" size="20"/></td>
         <td colspan="2"></td>
        </tr>
        <tr>        
        <td><input type="button" class="btn btn-danger" id="btnCancel" onclick="btnCancelClick();" name="btnCancel" value='Cancel' align="right"/></td>
         <td><input type="button" class="btn btn-warning" id="btnReset" onclick="btnResetClick();" name="btnReset" value='Reset' align="right"/></td>
        <td><input type="button" class="btn btn-info" id="btnSave" onclick="validate(this);" name="btnSave" value='Save' align="right"/></td>
        <td colspan="6"></td>
        </tr>
      </table>
		</form>


	</div>
	
	
	
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