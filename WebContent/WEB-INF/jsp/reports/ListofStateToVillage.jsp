<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" type="text/css" href="<c:url  value="/resources/css/report.css" />">
<script src='<c:url value="/resources/js/userDetailSA.js" />'></script>

<script type="text/javascript">

function showReportSTVill(e){
	var state = $('#state').val();
	var district = $('#district').val();
	if(state==='')
	{
		alert('Please select state ');
		$('#state').focus();
		e.preventDefault();
	}
	if(district==='')
	{
		alert('Please select District ');
		$('#district').focus();
		e.preventDefault();
	}
	else{
		
		document.listofstvill.action="ListofStateVill";
		document.listofstvill.method="post";
		document.listofstvill.submit();
	}
	return false;
}

</script>



</head>
<div class ="card">

<div class="table-responsive">

<div class="col" style="text-align:center;"><h5>List of Villages</h5></div>
 <form:form autocomplete="off" name="listofstvill" id="listofstvill"  modelAttribute="listofStateToVillge" action="listofStateToVillge" method="get">
 
      <table >
        <tr>
          <td class="label">State <span style="color: red;">*</span></td>
          <td>
              <select name="state" id="state" onchange="this.form.submit();">
              		<option value="">--Select--</option>
              		<c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
              			<c:if test="${state=='0' }"> 
              				<option value="0" selected="selected" >--All--</option>
              			</c:if>
              			<c:if test="${state==null || state gt '0' || state==''}"> 
              				<option value="0" >--All--</option>
              			</c:if>
              		</c:if>
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
          
           <td class="label">District <span style="color: red;">*</span></td>
          <td>
              <select name="district" id="district" onchange="this.form.submit();">
              		<option value="0">--All--</option>
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
          
           <td class="label">Block &nbsp;&nbsp;&nbsp;</td>
          <td>
              <select name="block" id="block" onchange="this.form.submit();">
              		<option value="0">--All--</option>
                  	<c:if test="${not empty blockList}">
               			<c:forEach items="${blockList}" var="lists">
               				<c:if test="${lists.key eq block}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne block}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
          
          <td class="label">Gram Panchayat &nbsp;&nbsp;&nbsp;</td>
          <td>
              <select name="gp" id="gp">
              		<option value="0">--All--</option>
                  	<c:if test="${not empty gpList}">
               			<c:forEach items="${gpList}" var="lists">
               				<c:if test="${lists.key eq gp}">
       								<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne gp}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
              </select>
          </td>
          <c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
           <td class="label">Status &nbsp;</td>
          <td>
          <c:if test="${state ne '0' }"> 
          <c:if test="${unviewlgd==null || unviewlgd=='true' }"> 
              <select name="unviewlgd" id="unviewlgd">
              		<option value="true">Active</option>
              		<option value="false">InActive</option>
              		<option value="0">Both</option>
              </select>
           </c:if> 
           <c:if test="${unviewlgd=='false' }"> 
              <select name="unviewlgd" id="unviewlgd">
              		<option value="false">InActive</option>
              		<option value="true">Active</option>
              		<option value="0">Both</option>
              </select>
           </c:if> 
           <c:if test="${unviewlgd=='0' }"> 
              <select name="unviewlgd" id="unviewlgd">
              		<option value="0">Both</option>
              		<option value="true">Active</option>
              		<option value="false">InActive</option>
              </select>
           </c:if> 
           </c:if> 
           <c:if test="${state=='0' }"> 
           <select name="unviewlgd" id="unviewlgd">
              		<option value="false">InActive</option>
              		<option value="true" disabled="disabled">Active</option>
              		<option value="0" disabled="disabled">Both</option>
              </select>
           </c:if> 
          </td>
          </c:if>
          
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="showReportSTVill(this);" name="view" value='View Report' /> </td>
       </tr>
      </table>
 </form:form>

<button name="exportExcel" id="exportExcel" onclick="exportExcel()" class="btn btn-info">Excel</button>
<button name="exportPDF" id="exportPDF" onclick="exportPDF()" class="btn btn-info">PDF</button>
	
	
        <table class="table">
          <tr>
            <td>
            	<table id="tblReport" class="table">
            	<thead>
              		<tr>
              				<th class="displ" align="center">Sl. No.</th>
		              	    <th class="displ" align="center">State Name</th>
		              	    <th class="displ" align="center">State LGD Code</th>
                            <th class="displ" align="center">District Name</th>
                            <th class="displ" align="center">District LGD Code</th>
                            <th class="displ" align="center">Block Name</th>
                            <th class="displ" align="center">Block LGD Code</th>
                            <th class="displ" align="center">Gram Panchayat Name</th>
                            <th class="displ" align="center">Gram Panchayat LGD Code</th>
                            <th class="displ" align="center">Villages Name</th>
                             <th class="displ" align="center">Villages LGD Code</th>
                            <c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
                            <th class="displ" align="center">Status</th>
                            </c:if>
               		</tr>
               		</thead>
               		<tbody>
               		<tr>
               		<th align="center"><b> 1 </b></th>
               		<th align="center"><b> 2 </b></th>
               		<th align="center"><b> 3 </b></th>
               		<th align="center"><b> 4 </b></th>
               		<th align="center"><b> 5 </b></th>
               		<th align="center"><b> 6 </b></th>
               		<th align="center"><b> 7 </b></th>
               		<th align="center"><b> 8 </b></th>
               		<th align="center"><b> 9 </b></th>
               		<th align="center"><b> 10 </b></th>
               		<th align="center"><b> 11 </b></th>
               		<c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
               		<th align="center"><b> 12 </b></th>
               		</c:if>
               		</tr>
                	<c:if test="${Listofstatetovill ne null}"> 
                	<c:forEach var="list" items="${Listofstatetovill}" varStatus="status">
	                	<tr>
	                	   	<td> ${status.count}</td>
	                	   
	                	    <td> 	
	                	    	<c:if test="${statename ne list.st_name}">
		           						<c:out value="${list.st_name}"></c:out>
		           						<c:set value="${list.st_name}" var="statename"/>
		           				</c:if>  
		           			</td>
		           			<td> 	
	                	   		<c:if test="${statelgd ne list.state_codelgd}">
		           						<c:out value="${list.state_codelgd}"></c:out>
		           						<c:set value="${list.state_codelgd}" var="statelgd"/>
		           				</c:if>  
		           			</td>
		           			
	                	    <td> 
	                	    	<c:if test="${distname ne list.dist_name}">
		           						<c:out value="${list.dist_name}"></c:out>
		           						<c:set value="${list.dist_name}" var="distname"/>
		           				</c:if>  
		           			</td>
		           			<td> 
	                	    	<c:if test="${distlgd ne list.district_codelgd}">
		           						<c:out value="${list.district_codelgd}"></c:out>
		           						<c:set value="${list.district_codelgd}" var="distlgd"/>
		           				</c:if>  
		           			</td>
		           			<c:if test="${list.blockactive ne 'true'}">
						    	<td> 
	                	    		<c:if test="${blockname ne list.block_name}">
						           		<c:out value="${list.block_name}"></c:out>  &nbsp;&nbsp; <input type="checkbox" id="blockname" name="blockname" checked="checked" disabled="disabled" />
						           		<c:set value="${list.block_name}" var="blockname"/>
						           	</c:if>   
						    	</td>
						    	<td> 
	                	    		<c:if test="${blocklgd ne list.block_codelgd}">
						           		<c:out value="${list.block_codelgd}"></c:out> 
						           		<c:set value="${list.block_codelgd}" var="blocklgd"/>
						           	</c:if>   
						    	</td>
						    </c:if>
						    
						    <c:if test="${list.blockactive eq 'true'}">
	                	    	<td> 
	                	    		<c:if test="${blockname ne list.block_name}">
						           		<c:out value="${list.block_name}"></c:out>
						           		<c:set value="${list.block_name}" var="blockname"/>
						           	</c:if>   
						    	</td>
						    	<td> 
	                	    		<c:if test="${blocklgd ne list.block_codelgd}">
						           		<c:out value="${list.block_codelgd}"></c:out>
						           		<c:set value="${list.block_codelgd}" var="blocklgd"/>
						           	</c:if>   
						    	</td>
						    </c:if>
						    <c:if test="${list.gpactive ne 'true'}">
						    	<td>
			                	    <c:if test="${gpname ne list.gram_panchayat_name}">
						           		<c:out value="${list.gram_panchayat_name}"></c:out> &nbsp;&nbsp; <input type="checkbox" id="gpname" name="gpname" checked="checked" disabled="disabled" /> 
						           		<c:set value="${list.gram_panchayat_name}" var="gpname"/>
						           	</c:if>  
						    	</td>
						    	<td>
			                	    <c:if test="${gplgd ne list.gram_panchayat_lgd_code}">
						           		<c:out value="${list.gram_panchayat_lgd_code}"></c:out>  
						           		<c:set value="${list.gram_panchayat_lgd_code}" var="gplgd"/>
						           	</c:if>  
						    	</td>
						    </c:if>
						    
						    <c:if test="${list.gpactive eq 'true'}">
						    	<td>
			                	    <c:if test="${gpname ne list.gram_panchayat_name}">
						           		<c:out value="${list.gram_panchayat_name}"></c:out>
						           		<c:set value="${list.gram_panchayat_name}" var="gpname"/>
						           	</c:if>  
						    	</td>
						    	<td>
			                	    <c:if test="${gplgd ne list.gram_panchayat_lgd_code}">
						           		<c:out value="${list.gram_panchayat_lgd_code}"></c:out>
						           		<c:set value="${list.gram_panchayat_lgd_code}" var="gplgd"/>
						           	</c:if>  
						    	</td>
						    </c:if>
						    
						    <c:if test="${list.villageactive ne 'true'}">
		                	    <td><span style="color: red;"> <c:out value="${list.village_name}" /></span> </td>
		                	    <td> <c:out value="${list.village_lgdcode}" />  </td>
		                	     <c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
		                	    	<td> InActive </td>
		                	    </c:if>
	                	    </c:if>
	                	    <c:if test="${list.villageactive eq 'true'}">
		                	    <td> <c:out value="${list.village_name}" />  </td>
		                	    <td> <c:out value="${list.village_lgdcode}" />  </td>
		                	    <c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
		                	    	<td> Active </td>
		                	    </c:if>
	                	    </c:if>
	                	</tr>
                	</c:forEach>
                	</c:if>
                	</tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
    <br>
    
    <%-- <c:if test="${Listofstatetovill.size()>0 }">
     <c:if test='${sessionScope.userType=="ADMIN" || sessionScope.userType=="DL"}'> 
	    <table>
	    <tr>
	    <td>
	   		<b>Note: &nbsp; <input type="checkbox" id="villname" name="villname" checked="checked" disabled="disabled" /> show InActive  </b>
	    </td>
	    </tr>
	    </table>  
	    </c:if>
	</c:if> --%>
  <c:if test="${Listofstatetovill.size()<=0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
	
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