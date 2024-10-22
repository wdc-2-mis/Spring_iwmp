<%@include file="/WEB-INF/jspf/header2.jspf"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
 
label{
 /* display: inline-block;
 width: 460px;
 margin-left: -50px;
 text-align: right; */
 font-size: 15px;
 }

.mytext {
  width: 300px;
  height: 150px;
  
detail {margin-bottom:5px;}
}

</style>


<div class="maindiv">

<div class="col formheading" style=""><h4><u>Add / View State Profile</u></h4> </div>
<form class="form-inline registrationstateform" autocomplete="off">
 <c:if test="${stateprofiledata.size() gt 0 }">
   <c:forEach var="list" items="${stateprofiledata}">
		<c:forEach var="listUser" items="${list.value}" >
		
         <c:if test="${listUser.status == 'd'}">
		<div class="form-row col-md-12">
		<br/>
		<div class="form-group col-md-6">
		                <label for="inputDistrict" >Total no. of Districts<span style="color: red;" >*</span></label>&nbsp;&nbsp;&nbsp;
						<br/>
						<input type="text"  class="form-control" id="inputDistrict" style="margin-bottom:5px;" value = "${listUser.noOfDist}" name="inputDistrict"  onkeyup="isNumeric;"  maxlength="10">
					</div>
		</div>
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict" >Total no. of Blocks <span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" id="inputBlocks" style="margin-bottom:5px;" name="inputBlocks" value = "${listUser.noOfBlock}"  onkeyup="isNumeric(this);"  maxlength="10">
					</div>
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total no. Of Micro-watersheds <span style="color: red;">*</span></label>&nbsp;&nbsp; 
						<input type="text" width="10%" class="form-control" id="inputmircowatersheds"  name="inputmircowatersheds"  value = "${listUser.totNoMicroWs}" onkeyup="isNumeric(this);"  maxlength="10">
					</div>
		</div>
		
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total geographical area (Lakh ha.)<span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" width="4%" class="form-control" style="margin-bottom:5px;" id="inputgeoarea"  name="inputgeoarea"  onblur="check(this);" onkeyup="isNumericWithPoint(this);" value = "${listUser.totGeoAreaHec}"  maxlength="15">
					</div>
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total untreatable area excluding assured irrigation (Lakh ha.) <span style="color: red;">*</span></label> 
						<input type="text" width="4%" class="form-control" id="inputuntreatarea"  style="margin-bottom:5px;" name="inputuntreatarea"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" value = "${listUser.totUntreatAreaHec}"  maxlength="15">
					</div>
		</div>
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-8">
						<label for="inputDistrict">Total area covered under pre-IWMP schemes of DoLR (Lakh ha.)<span style="color: red;">*</span></label>&nbsp; 
						<input type="text" class="form-control" id="inputiwmpProjects" style="margin-bottom:5px;" name="inputiwmpProjects"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" value = "${listUser.areaByPreiwmpProj}"  maxlength="15">
					</div>
					
					</div>
<!-- 		<div class="form-group col-md-6"> -->
<!-- 						<label for="inputDistrict">Area covered under pre-WDC-PMKSY projects (Lakh ha.)<span style="color: red;">*</span></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%-- 						<input type="text" class="form-control" id="inputiwmpProjects" style="margin-bottom:5px;" name="inputiwmpProjects"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" value = "${listUser.areaByPreiwmpProj}"  maxlength="15"> --%>
<!-- 					</div> -->
		<div class="form-row col-md-12">
		<div class="form-row col-md-8">
						<label for="inputDistrict">Total area covered under IWMP/WDC-PMKSY of DoLR(WDC 1.0)(Lakh ha.)<span style="color: red;">*</span></label> &nbsp; &nbsp;
						<input type="text" class="form-control" id="inputwdciwmpProjects" style="margin-bottom:5px;" name="inputwdciwmpProjects"  onkeyup="isNumericWithPoint(this);" value = "${listUser.iwmpwdcpmksyarea}"  maxlength="16" >
					</div>
<!-- 		<div class="form-row col-md-6"> -->
<!-- 						<label for="inputDistrict">Area covered under WDC-PMKSY/IWMP projects (Lakh ha)<span style="color: red;">*</span></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%-- 						<input type="text" class="form-control" id="inputwdciwmpProjects" style="margin-bottom:5px;" name="inputwdciwmpProjects"  onkeyup="isNumericWithPoint(this);" value = "${listUser.iwmpwdcpmksyarea}"  maxlength="16" > --%>
<!-- 					</div> -->
			</div>
		<div class="form-row col-md-12">
		<div class="form-row col-md-8">
						<label for="inputDistrict">Total area covered under schemes of other Ministries (Lakh ha.) <span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" id="inputWaterShedP"  name="inputWaterShedP"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" value = "${listUser.areaByOtherWs}"  maxlength="15">
					</div>
		</div>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
		                <label for="inputDistrict" >Total area with assured irrigation (Lakh ha.)<span style="color: red;" >*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" style="margin-bottom:5px;" id="inputAssIrrigation"  name="inputDistrict"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" value = "${listUser.totAreaAsurIrrig}"  maxlength="15">
					</div>
		</div>
		<br>
		<div class="col-sm-2">
					    <button id="draft" class="btn btn-info" style="margin-bottom:10px;" name="draft" value="d">Draft</button>
                    <button id="complete" class="btn btn-info" style="margin-bottom:10px;" name="complete" value="c">Complete</button>
				</div>
		</c:if>
		
		<c:if test="${listUser.status == 'c' }">
		<div class="form-row col-md-12">
		<br/>
		<div class="form-group col-md-6">
		                <label for="inputDistrict" >Total no. of Districts<span style="color: red;" >*</span></label>&nbsp;&nbsp;
						<br/>
						<input type="text"  class="form-control" id="inputDistrict" style="margin-bottom:5px;" value = "${listUser.noOfDist}" name="inputDistrict"  onkeyup="isNumericWithPoint(this);"  maxlength="10" disabled>
					</div>
		</div>
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict" >Total no. of Blocks <span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" id="inputBlocks" style="margin-bottom:5px;" name="inputBlocks" value = "${listUser.noOfBlock}"  onkeyup="isNumericWithPoint(this);"  maxlength="10" disabled>
					</div>
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total no. Of Micro-watersheds <span style="color: red;">*</span></label>&nbsp;&nbsp; 
						<input type="text" width="10%" class="form-control" id="inputmircowatersheds"  name="inputmircowatersheds"  value = "${listUser.totNoMicroWs}" onkeyup="isNumericWithPoint(this);"  maxlength="10" disabled>
					</div>
		</div>
		
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total geographical area (Lakh ha.)<span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" width="4%" class="form-control" style="margin-bottom:5px;" id="inputgeoarea"  name="inputgeoarea"  onkeyup="isNumericWithPoint(this);" value = "${listUser.totGeoAreaHec}"  maxlength="16" disabled>
					</div>
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total untreatable area excluding assured irrigation (Lakh ha.) <span style="color: red;">*</span></label> &nbsp;&nbsp;
						<input type="text" class="form-control" id="inputuntreatarea"  name="inputuntreatarea"  onkeyup="isNumericWithPoint(this);" value = "${listUser.totUntreatAreaHec}"  maxlength="16" disabled>
					</div>
		</div>
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total area covered under pre-IWMP schemes of DoLR (Lakh ha.)<span style="color: red;">*</span></label>&nbsp;&nbsp; 
						<input type="text" name="name"class="form-control" id="inputiwmpProjects" style="margin-bottom:5px;" name="inputiwmpProjects"  onkeyup="isNumericWithPoint(this);" value = "${listUser.areaByPreiwmpProj}"  maxlength="16" disabled>
					</div>
				</div>	
					<div class="form-row col-md-12">
		<div class="form-row col-md-8">
						<label for="inputDistrict">Total area covered under IWMP/WDC-PMKSY of DoLR(WDC 1.0) (Lakh ha.)<span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" id="inputwdciwmpProjects" style="margin-bottom:5px;" name="inputwdciwmpProjects"  onkeyup="isNumericWithPoint(this);" value = "${listUser.iwmpwdcpmksyarea}"  maxlength="16" disabled>
					</div>
					</div>
<!-- 		<div class="form-group col-md-6"> -->
<!-- 						<label for="inputDistrict">Area covered under other watershed programs (Lakh ha.) <span style="color: red;">*</span></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%-- 						<input type="text" class="form-control" id="inputWaterShedP"  name="inputWaterShedP"  onkeyup="isNumericWithPoint(this);" value = "${listUser.areaByOtherWs}"  maxlength="16" disabled> --%>
<!-- 					</div> -->
<!-- 		</div> -->
		
		<br/>
		
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
		                <label for="inputDistrict" >Total area with assured irrigation (Lakh ha.)<span style="color: red;" >*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" style="margin-bottom:5px;" id="inputAssIrrigation"  name="inputDistrict"  onkeyup="isNumericWithPoint(this);" value = "${listUser.totAreaAsurIrrig}"  maxlength="16" disabled>
					</div>
		</div>
		<div class="form-row col-md-12">
		<div class="form-row col-md-6">
						<label for="inputDistrict">Total area covered under schemes of other Ministries (Lakh ha.) <span style="color: red;">*</span></label> &nbsp;&nbsp;
						<input type="text" class="form-control" id="inputWaterShedP"  name="inputWaterShedP"  onkeyup="isNumericWithPoint(this);" value = "${listUser.areaByOtherWs}"  maxlength="16" disabled>
					</div>
		</div>
		<div class="col-sm-2">
					    <button id="draft" class="btn btn-info" style="margin-bottom:10px;" name="draft" value="d" disabled>Draft</button>
                    <button id="complete" class="btn btn-info" style="margin-bottom:10px;" name="complete" value="c" disabled>Complete</button>
				</div>
		</c:if>
		</c:forEach>
		</c:forEach>
		
		</c:if>
		<c:if test="${stateprofiledata.size() eq 0 }">
		<div class="form-row col-md-12">
		<br>
		<div class="form-group col-md-6">
		                <label for="inputDistrict" >Total no. of Districts<span style="color: red;" >*</span></label> &nbsp;&nbsp;
						<br/>
						<input type="text"  class="form-control" id="inputDistrict" style="margin-bottom:5px;" name="inputDistrict" placeholder="Total no. of Districts" onkeyup="isNumeric(this);"  maxlength="10">
					</div>
		</div>
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict" >Total no. of Blocks <span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" id="inputBlocks" style="margin-bottom:5px;" name="inputBlocks"  placeholder="Total no. of Blocks" onkeyup="isNumeric(this);"  maxlength="10">
					</div>
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total no. Of Micro-watersheds <span style="color: red;">*</span></label>&nbsp;&nbsp; 
						<input type="text" width="10%" class="form-control" id="inputmircowatersheds"  name="inputmircowatersheds"  placeholder="Total no. Of Micro-watersheds" onkeyup="isNumeric(this);"  maxlength="10">
					</div>
		</div>
		
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total geographical area (Lakh ha.)<span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" width="4%" class="form-control" style="margin-bottom:5px;" id="inputgeoarea"  name="inputgeoarea"  placeholder="Total geographical area" onblur="check(this);" oninput="isNumericWithPoint(this)" maxlength="15">
					</div>
		<div class="form-group col-md-6">
						<label for="inputDistrict">Total untreatable area excluding assured irrigation (Lakh ha.) <span style="color: red;">*</span></label> &nbsp;&nbsp;
						<input type="text" width="4%" class="form-control" id="inputuntreatarea"  style="margin-bottom:5px;" name="inputuntreatarea"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" placeholder="Total untreatable area"  maxlength="15">
					</div>
		</div>
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-8">
						<label for="inputDistrict">Total area covered under pre-IWMP schemes of DoLR (Lakh ha.)<span style="color: red;">*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" id="inputiwmpProjects" style="margin-bottom:5px;" name="inputiwmpProjects"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" placeholder="Area covered under pre-IWMP projects"  maxlength="15">
					</div>
					</div>
		<div class="form-row col-md-12">
		<div class="form-row col-md-12">
						<label for="inputDistrict">Total area covered under IWMP/WDC-PMKSY of DoLR(WDC 1.0) (Lakh ha.)<span style="color: red;">*</span></label> &nbsp;&nbsp;
						<input type="text" class="form-control" id="inputwdciwmpProjects" style="margin-bottom:5px;" name="inputwdciwmpProjects"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" placeholder="Area covered under wdcpmksy-iwmp projects"  maxlength="15" >
					</div>
					</div>
		<div class="form-row col-md-12">
		<div class="form-group col-md-8">
						<label for="inputDistrict">Total area covered under schemes of other Ministries (Lakh ha.) <span style="color: red;">*</span></label> &nbsp;&nbsp;
						<input type="text" class="form-control" id="inputWaterShedP"  name="inputWaterShedP"  onkeyup="isNumericWithPoint(this);" onblur="check(this);" placeholder="Area covered under other watershed programmes"  maxlength="15">
					</div>
		</div>
		
		<br/>
		<div class="form-row col-md-12">
		<div class="form-group col-md-6">
		                <label for="inputDistrict" >Total area with assured irrigation (Lakh ha.)<span style="color: red;" >*</span></label>&nbsp;&nbsp;
						<input type="text" class="form-control" style="margin-bottom:15px;" id="inputAssIrrigation"  name="inputDistrict"  onblur="check(this);" onkeyup="isNumericWithPoint(this);" placeholder="Total area with assured irrigation"  maxlength="15">
					</div>
		</div>
		<div class="col-sm-2">
					    <button id="draft" class="btn btn-info" style="margin-bottom:10px;" name="draft" value="d">Draft</button>
                    <button id="complete" class="btn btn-info" style="margin-bottom:10px;" name="complete" value="c">Complete</button>
				</div>
		</c:if>
		
		<br/>
		
		
		
		
			
<br/>
<div class="form-row col-md-12">
		<label style="color: red;">* Indicates mandatory</label>
		</div>	
</form>
</div>

				
				
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
 <script src='<c:url value="/resources/js/stateprofilewdcd.js" />'></script> 
</body>
</html>
