<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src='<c:url value="/resources/js/slnadiscp.js" />'></script>
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">

<body>
<form name="approval" action="updateslnadisper">
<c:if test='${sessionScope.userType=="DL"}'>
		<table width="100%" cellpadding="2" class="gen_table" cellspacing="1" id="linkButton">
			<tr>
				<td class="labelReport">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tbody>
		<tr>
			<td class="labelReport" width="10%">State <font style="color: red;">*</font></td>
			<td class="labelReport" width="10%">
			<select name="state" id="state"  style="width:200px;" onchange="changeOption(this)"> 
	              <option value="0" label="---Select---"></option>
	              <c:if test="${not empty stateVector}">
	              	<c:forEach items="${stateVector}" var="lists">
               			<c:if test="${lists.key eq stCode}">
       					<option value="<c:out value='${lists.key}'/>"  selected="selected" ><c:out value="${lists.value}" /></option>
       						</c:if>	
       						<c:if test="${lists.key ne stCode}">
       								<option value="<c:out value='${lists.key}'/>"  ><c:out value="${lists.value}" /></option>
       						</c:if>
						</c:forEach>
					</c:if> 
	              
	              
	            </select></td>
			</tr>
	   </tbody>
	        </table>
		</tr>
	</table>
</c:if>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			</p>
			<td width="100%"  colspan="10" class="tabs" align="center"><b><h2>District approval Required ?</h2></b></td>
		</tr>
		<tr class="tabs">
			<td colspan="2"></td>
		</tr>	
	</table>
	<table  height="150" border="0" cellspacing="1" cellpadding="2" align="center" class="gen_table" >
			
			<tr>
				<td width="100%"> 
				<label class="label" style="font-size:22px;">
					<br>
					<br>
				    1. Data entered by PIAs  has to be verified/approved by District and SLNA.
				   <br><br>
				    
				   2. Approval by District (WCDC) is not mandatory. State has  to decide by filling "DISTRICT APPROVAL FORM". 
				   <br><br>
				   3. Approval by SLNA is mandatory. After approval of data by SLNA, data would be locked and no request would be entertain for unfreeze of data.
				   <br><br>
				   4. Please confirm whether you want District to verify/approve the data filled by PIAs.
				   It is being instructed to MIS Coordinator to fill up the form only after approval from your CEO.
				   <br> &nbsp; &nbsp; Kindly send the copy of approval of CEO to support-wdcpmksy@nic.in.   
					<br>
					<br>
				   5. Please Note that in case of verify/approval from District is not selected, all verify/approvals of data of PIAs will be done at SLNA level only. 
					<br>
					<br>
					 	<p><big>&nbsp;&nbsp;&nbsp;<b>Your present selection for District Approval is : </b><u><font color="red"><c:out value='${statusMsg}'/></font></big></u></p>			
				</label>
				</td>
			</tr>
	</table>
<table border="0" cellspacing="0" cellpadding="0" >
			 <tr>
			     <td>
			     <table width="100%" border="0" cellspacing="0" cellpadding="0" >
			     <c:if test='${sessionScope.userType=="SL"}'>
			     
			     <c:if test="${DesicionDate!=null}">
			          <tr>
			             <td align="center" width="5000">
			              <input id="bttn1" type="button" value='YES' disabled="disabled" onclick="javascript: return save('Y');"/>
			             <input id="bttn2" type="button" value="NO" disabled="disabled" onclick="javascript: return save('N');"/>
			            
			             </td>
			           </tr>
			      </c:if>     
			      <c:if test='${DesicionDate==null}'>
			          <tr>
			             <td align="center" width="5000">
			             <input id="bttn1" type="button" value='YES'  onclick="javascript: return save('Y');"/>
			             <input id="bttn2" type="button" value="NO"  onclick="javascript: return save('N');"/>
			             </td>
			           </tr>
			     </c:if>
			     </c:if>
			     <c:if test='${sessionScope.userType=="DL"}'>
			       <tr>
			             <td align="center" width="5000">
			              <input id="bttn1" type="button" value='YES' onclick="javascript: return savedolr('Y');"/>
			             <input id="bttn2" type="button" value="NO"  onclick="javascript: return savedolr('N');"/>
			             </td>
			       </tr>
			     </c:if>
			     
			     </table>
			     </td>
			  </tr>
	</table>	
<footer class=" text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</form>
</body>