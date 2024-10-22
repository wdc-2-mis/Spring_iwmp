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

<script>
function showReport(e) 
{
    var finName = document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
    var fincode = document.getElementById("year").value;
    
    document.getElementById("finName").value=finName;
    document.getElementById("fincode").value=fincode;
    
    if(document.getElementById("year").value=='')
	{
		alert('Please select Financial Year !');
		$('#year').focus();
		e.preventDefault();
	}
	else
	{
	document.getOOMFDetails.action="getAddAchRecords";
	document.getOOMFDetails.method="post";
	document.getOOMFDetails.submit();
	}
	return false;
}
</script>

<div id="getcompleteddata" class="">
		<div class="col formheading" style="">
			<h4>
				<u>State wise OOMF (half/yearly parameters) Achievement States</u>
			</h4>
		</div>
 <hr/>

<form:form action="getOOMFDetails" name="getOOMFDetails" id="getOOMFDetails" method="get">
		<div id="waitDiv" 
			style="display: none; line-height: 20px; z-index: 98; position: absolute; background: #ffffff; left: 25px;  height: 800px;
			 width: 1600px; filter: alpha(opacity = 60); -moz-opacity: .60; opacity: .60; text-align: center; float: left;">
			<table>  
				<tr>
					<td>
						<div align="center">
							<span style="padding-right:3px;  display:inline-block; width: 1600px;">
									<img class="manImg" src="resources/images/load.gif"></img>
							</span>
						</div>
					</td>
				</tr>
			</table> 
		</div>
		
    	<input type="hidden" name="fincode" id="fincode" value="" />
		<input type="hidden" name="finName" id="finName" value="" />
	
      	<table style="width:100%; align-content: center;" >
        	<tr align="center" >
          		<td><b>Financial Year <span style="color: red;">*</span></b></td>
 				<td>
              		<select name="year" id="year"  required="required" onchange="showReport();">
              			<option value="">--Select Year--</option>
						<c:if test="${not empty financialYear}">
							<c:forEach items="${financialYear}" var="lists">
               					<c:if test="${lists.finYrCd eq finyr && lists.finYrCd ne 21}">
       								<option value="<c:out value='${lists.finYrCd}'/>" selected="selected" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>	
       							<c:if test="${lists.finYrCd ne finyr && lists.finYrCd ne 21}">
       								<option value="<c:out value='${lists.finYrCd}'/>" ><c:out value="${lists.finYrDesc}" /></option>
       							</c:if>
							</c:forEach>
						</c:if>  
					</select>
          		</td>
          		<td width="50%"></td>
       		</tr>
      	</table>
      	<div id="previewDiv" class="hiddenDivStyle" align="center"
			style="position: absolute; top: 100px; left: 25px; display: none; width: 300px; height: 50px; vertical-scrol: auto; background-color: gray;">
			<table align="center">
				<tr>
					<td>
						<div align="center">
							<span style="font-size: 25px;">Please Wait ...</span>
						</div>
					</td>
				</tr>
			</table>
		</div>
 	</form:form>
<br>
<div class="form-group col-20 confirmation">
    
     <table class="tblAddPhysicalAchievement" id="tblAddPhysicalAchievement" name="tblAddPhysicalAchievement" style="width:100%">
     <thead>
     <tr>
     <th rowspan="2" class="text-center">S No.</th><th style="width:20%" rowspan="2" class="text-center">District Name</th><th rowspan="2" class="text-center">Total Project</th><th colspan="4" class="text-center">OOMF Parameter Entry</th></tr>
     <tr><th class="text-center">Total Projects enter Half Yearly</th><th class="text-center">Total Projects enter Yearly</th><th class="text-center">Total Projects not enter Half Yearly</th><th class="text-center">Total Projects not enter Yearly</th></tr>
     </thead>
     <tbody id="tbodyMovement">
     <c:set var="count" value="1" />
		<c:forEach var="list" items="${getachdata}">
			<c:forEach var="listUser" items="${list.value}" >
				<tr>
				<td><c:out value='${count}' /></td>
				<td align="right"><c:out value='${listUser.dist_name}' /></td>
				<td align="right"><c:out value='${listUser.total_proj}' /></td>
				<td align="right"><c:out value='${listUser.half_yearly}' /></td>
				<td align="right"><c:out value='${listUser.yearly}' /></td>
				<c:if test = "${listUser.not_half_yearly == 0}">
				<td align="right"><c:out value='${listUser.not_half_yearly}'></c:out></td>
				</c:if>
				<c:if test = "${listUser.not_half_yearly > 0}">
				<td align="right"><a href="#" data-id=<c:out value="${listUser.dcode}" />  class="nothalfyearly" id="nothalfyearly" name="nothalfyearly"><c:out value='${listUser.not_half_yearly}'></c:out></a></td>
				</c:if>
				<c:if test = "${listUser.not_yearly == 0}">
				<td align="right"><c:out value='${listUser.not_yearly}' /></td>
				</c:if>
				<c:if test = "${listUser.not_yearly > 0}">
				<td align="right"><a href="#" data-id=<c:out value="${listUser.dcode}"/> class="notyearly" id="notyearly" name="notyearly" ><c:out value='${listUser.not_yearly}'></c:out></a></td>
				</c:if>
				<c:set var="count" value="${count+1}" />
				</tr>
				</c:forEach>
				</c:forEach>
     
     </tbody>
     
     </table>
    
     </div>


</div>
</div>
</div>
</div>
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
<script src='<c:url value="/resources/js/addAchievRecords.js" />'></script>
