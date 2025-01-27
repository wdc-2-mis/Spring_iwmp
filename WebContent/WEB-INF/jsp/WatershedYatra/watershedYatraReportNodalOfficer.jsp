<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<%-- <script src='<c:url value="/resources/js/unfreezeProjectLocation.js" />'></script> --%>
<script type="text/javascript">

$(document).on( 'change', '#level', function (e) {
	e.preventDefault();
					
	$gType = $('#level').val();
	
	if($gType==='a')
	{
		$('.yeark').addClass('d-none');
		$('.mont').addClass('d-none');
						
		$("select#district")[0].selectedIndex = 0;
		$("select#block")[0].selectedIndex = 0;
	}
	else if($gType==='state')
	{
		$('.yeark').addClass('d-none');
		$('.mont').addClass('d-none');
						
		$("select#district")[0].selectedIndex = 0;
		$("select#block")[0].selectedIndex = 0;
	}
	else if($gType==='district')
	{
		$('.yeark').removeClass('d-none');
		$('.mont').addClass('d-none');
						
		$("select#district")[0].selectedIndex = 0;
		$("select#block")[0].selectedIndex = 0;
	}
	else{
					
		$('.yeark').removeClass('d-none');
		$('.mont').removeClass('d-none');
						
		$("select#district")[0].selectedIndex = 0;
		$("select#block")[0].selectedIndex = 0;
	}			
			
});	

function showReport(e)
{
	var lvl = $('#level').val();
	var state = $('#state').val();
	var district = $('#district').val();
	var block = $('#block').val();
	
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	
	else{
		
		document.nodalOfficers.action="getNodalOfficerReportData";
		document.nodalOfficers.method="post";
		document.nodalOfficers.submit();
	}
	return false;
}

function showChangedata(){
	
	/* var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	// alert('kdy='+stName);
	if(lvl==='district' || lvl==='block' || lvl==='village' ) {
    	var finName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    	document.getElementById("distName").value=finName;
    }
	if( lvl==='block' || lvl==='village' ) {
    	var blkname = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    	document.getElementById("blkName").value=blkname;
	}
    var level = document.getElementById("level").options[document.getElementById("level").selectedIndex].text;
   
    
    document.getElementById("stName").value=stName;
    document.getElementById("lvlName").value=level;  */
    
    document.nodalOfficers.action="getWatershedYatraNodalReport";
	document.nodalOfficers.method="get";
	document.nodalOfficers.submit();
	}

function downloadPDF1(state, district, blkd, lvl){
	
	var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	// alert('kdy='+stName);
	if(lvl==='district' || lvl==='block' || lvl==='village' ) {
    	var finName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    	document.getElementById("distName").value=finName;
    }
	if( lvl==='block' || lvl==='village' ) {
    	var blkname = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    	document.getElementById("blkName").value=blkname;
	}
    var level = document.getElementById("level").options[document.getElementById("level").selectedIndex].text;
   
    
    document.getElementById("stName").value=stName;
    document.getElementById("lvlName").value=level;
    
    document.nodalOfficers.action="downloadNodalOfficerReportPDF";
	document.nodalOfficers.method="post";
	document.nodalOfficers.submit();
	}

function downloadExcel(lvl, state, district, blkd) {
	
    var lvl = document.getElementById("level").value;
    var state = document.getElementById("state").value;
    var distName = "";
    var blkName = "";

    if (lvl === "district" || lvl === "block" || lvl === "village") {
        district = document.getElementById("district").value;
        distName = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
    }
    
    if (lvl === "block" || lvl === "village") {
    	blkd = document.getElementById("block").value;
        blkName = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
    }
	
    var lvlName = document.getElementById("level").options[document.getElementById("level").selectedIndex].text;
    var stName = document.getElementById("state").options[document.getElementById("state").selectedIndex].text;
	
    document.getElementById("lvlName").value = lvlName;
    document.getElementById("stName").value = stName;
    document.getElementById("distName").value = distName;
    document.getElementById("blkName").value = blkName;
    
    document.nodalOfficers.action = "downloadNodalOfficerReportExcel";
    document.nodalOfficers.method = "post";
    document.nodalOfficers.submit();
}


</script>

</head>
<div class ="card">

<div class="table-responsive">
<br/>
<div class="col" style="text-align:center;"><h5>Details of Nodal Officers</h5></div>
 <form:form autocomplete="off" name="nodalOfficers" id="nodalOfficers"  action="getWatershedYatraNodalReport" method="get">
 		<br/>
 		<input type="hidden" name="stName" id="stName" value="" />
		<input type="hidden" name="distName" id="distName" value="" />
		<input type="hidden" name="blkName" id="blkName" value="" />
		<input type="hidden" name="lvlName" id="lvlName" value="" />
      <table >
        <tr>
        
        <td class="label">Level <span style="color: red;">*</span></td>
          <td>
              <select name="level" id="level"  required="required" onchange="showChangedata();">
              		<option value="a">--All Level--</option>
    				<c:forEach items="${level}" var="dist"> 
    				<c:if test="${dist.key eq lvl}">
						<option value="<c:out value="${dist.key}"/>" selected="selected"><c:out value="${dist.value}" /></option>
					</c:if>
					<c:if test="${dist.key ne lvl}">
						<option value="<c:out value="${dist.key}"/>"><c:out value="${dist.value}" /></option>
					</c:if>
					</c:forEach>
    			
              </select>
          </td>
        
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="showChangedata();" required="required">
              		<option value="0">--All State--</option>
                  	<c:if test="${not empty stateList}">
               			<c:forEach items="${stateList}" var="lists">
               				<c:if test="${lists.key eq state}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne state}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
          <c:if test="${lvl eq 'district' || lvl eq 'block' || lvl eq 'village'}">
          <td>District <span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="showChangedata();" >
              		<option value="0">--All District--</option>
                  	 <c:if test="${not empty districtList}">
               					<c:forEach items="${districtList}" var="lists">
               						<c:if test="${lists.key eq district}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne district}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 
              </select>
          </td>
          </c:if>
           <c:if test="${lvl eq 'block' || lvl eq 'village'}">
           <td>Block &nbsp;<span style="color: red;">*</span></td>
           <td>
              <select name="block" id="block"  >
              <option value="0">--All Block--</option>
              	<c:if test="${not empty blockList}">
               					<c:forEach items="${blockList}" var="lists">
               						<c:if test="${lists.key eq blkd}">
       									<option value="<c:out value='${lists.key}'/>" selected="selected" ><c:out value="${lists.value}" /></option>
       								</c:if>	
       								<c:if test="${lists.key ne blkd}">
       									<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       								</c:if>	
								</c:forEach>
					</c:if> 	
              </select>
          </td>
          </c:if> 
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReport(this);"  name="view" value='Get Data' /> </td>
       </tr>
      </table>

<br/>
<c:if test="${not empty routePlanList}">
<button name="exportExcel" id="exportExcel" onclick="downloadExcel('${lvl}','${state}','${district}','${blkd}')" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="downloadPDF1('${state}','${district}','${blkd}','${lvl}')" class="btn btn-info">PDF</button>
</c:if>
<p align="right"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p>
 <br/>
        <table class="table">
          <tr>
            <td>
            	<table id="tblReport" class="table">
            	<thead>
              		<tr>	
              			<th style="width:2%">S.No. </th> 
						<th style="width:5%">Level</th>
						<th style="width:5%">State Name</th>
						<c:if test="${lvl eq 'a' || lvl eq 'district' || lvl eq 'block' || lvl eq 'village'}">
							<th style="width:5%">District Name</th>
						</c:if>
						<c:if test="${lvl eq 'a' || lvl eq 'block' || lvl eq 'village'}">
							<th style="width:5%">Block Name</th>
						</c:if>
						<th style="width:5%">Name</th>
						<th style="width:5%">Designation</th>
						<th style="width:5%">Mobile</th>
						<th style="width:5%">Email Id</th>
               		</tr>
               		</thead>
               		<tbody>
               		<c:set var="proj" value="" />
               		<c:set var="dist" value="" />
                	<c:if test="${routePlanList ne null}"> 
                	<c:forEach var="dataV" items="${routePlanList}" varStatus="status">
	                	<tr>
							 	<td><c:out value='${status.count}' />  </td>
								<c:if test="${dataV.level eq 'state'}">
									<td> <c:out value="State" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'district'}">
									<td> <c:out value="District" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'block'}">
									<td> <c:out value="Block/Project" /></td>	
								</c:if>
								<c:if test="${dataV.level eq 'village'}">
									<td> <c:out value="Village/Van Standing Point" /></td>	
								</c:if>
								<c:choose>
									<c:when test="${proj ne dataV.stname}">
										<c:set var="proj" value="${dataV.stname}" />
										<td> <c:out value="${dataV.stname}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								<c:if test="${lvl eq 'a' || lvl eq 'district' || lvl eq 'block' || lvl eq 'village'}">
									<c:choose>
									<c:when test="${dist ne dataV.district}">
										<c:set var="dist" value="${dataV.district}" />
										<td> <c:out value="${dataV.district}" /></td>
									</c:when>	
								<c:otherwise>
										<td></td>
								</c:otherwise>
								</c:choose>
								</c:if>
								<c:if test="${lvl eq 'a' || lvl eq 'block' || lvl eq 'village'}">
									<td> <c:out value="${dataV.blockname}" /></td>
								</c:if>
								<td> <c:out value="${dataV.nodal_name}" /></td>
								<td> <c:out value="${dataV.designation}" /></td>
								<td> <c:out value="${dataV.mobile}" /></td>
								<td> <c:out value="${dataV.email}" /></td>
								
							</tr>
                	</c:forEach>
                	</c:if>
                	<c:if test="${routePlanListSize eq 0}">
							<tr>
								<td align="center" colspan="9" class="required" style="color:red;">Data Not Found</td>
							</tr>
					</c:if>
                	</tbody>
               		
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        
         </form:form>
    <br>
    
	</div>
	</div>


<script type="text/javascript">
$(document).ready(function(){
$(".sidebar-btn").click(function(){
$(".wrapper").toggleClass("collapse");
	});
	});

</script>
<footer class=" ">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>