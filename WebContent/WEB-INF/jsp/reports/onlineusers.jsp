<%@include file="/WEB-INF/jspf/header2.jspf"%>
  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script> -->
 <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/afterlogin.css" />">

	<script src='<c:url value="/resources/js/userSearch.js" />'></script>
  <script language="JavaScript" src="js/reports.js?timer=${timer}"></script>

<div class ="card" id="onlineuser">


	
	 <c:if test="${userList  ne null}">
	 
  	 
      	
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>List of Online User(s)</center></td>
            <td ><button class="btn btn-info" onclick="printPage(event);">Print</button></td>
            
          </tr>
        </table>
        <table class="table">
          <tr>
            <td><table id="example" class="table">
              <tr>
              	
                <td height="26" class="displ">User Id</td>
                <td class="displ">User Name</td>
                <td class="displ">State Name</td>
                <td class="displ">District Name</td>
                <td class="displ">Login Time</td>
                <td class="displ">IP Address</td>
                </tr>
                 <c:forEach var="list" items="${userList}">
					 <c:forEach var="listUser" items="${list.value}" >	
					 	
					 		
									<tr>
									<td>
									<c:out	value="${listUser.userid }"></c:out>
									
									  </td>
									<td>
									<c:out	value="${listUser.username }"></c:out>
									
									  </td>
									<td>
									<c:out	value="${listUser.statename }"></c:out>
									</td>
									<td>
									<c:out	value="${listUser.distname }"></c:out>
									</td>
									<td>
									<c:out	value="${listUser.logintime }"></c:out></td>
									<td><a href="#"><c:out	value="${listUser.requestip }">
									</c:out></a></td>
									
									
									</tr>
									
									</c:forEach>
									
							</c:forEach> 
              </table>
            </td>
          </tr>
          <tr>
            <td><p align="center"> Report as on: <%=app.util.Util.dateToString(null,"dd/MM/yyyy hh:mm aaa")%> </p></td>
          </tr>
        </table>
      
  </c:if>
  <c:if test="${codeList_size.size()<=0 }">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="tabs">
            <td><center>No Data Found !</center></td>
            <td >&nbsp;</td>
          </tr>
        </table>
  </c:if>
	
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