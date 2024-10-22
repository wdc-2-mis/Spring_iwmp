<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/jspf/header2.jspf"%>



<html>
<head>
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css"> -->
<link rel="stylesheet"
	href="resources/css/all.css"
	integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/phystyle.css" />">
<!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
 
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu Management Screen</title>
<script type="text/javascript">
	function getValue(obj) {
		//alert("You have selected Country: " + obj.value);
		var parameter = obj.value;

		window.location.href = "fetchMenu?role=" + parameter;
		// alert(window.location.href);
	}

	$('table .delete').on('click', function() {
		var id = $(this).parent().find('#id').val();
		alert(id);
		$('#deletesubmenu #id').val(id);
	});
</script>
<!-- </head>
<body> -->
<%--   <form:form action="fetchMenu" method="get" >  --%>
<div class="table-wrapper">
	<div class="table-title">
		<div class="row">

			<div class="col-sm-9">
				<h5>Menu List to Modify or Delete the Menu</h5>

			</div>


		</div>
	</div>

	<h3 align="right">
		<a href="newMenu">Add Menu</a>
	</h3>

	<%-- 		<form:form action="getMenu" method="post" modelAttribute="menu">   --%>



	<table class="table table-striped table-highlight">
		<tr>
			<td>Role</td>
			<td><form:select onchange="getValue(this);" path="role">
					<form:option value="0" label="---Select---"></form:option>
					<form:option value="999" label="---All---"></form:option>
					<form:options items="${listRole}" itemLabel="roleName"
						itemValue="roleId" />

				</form:select></td>
			<!--                 <td  align="right"><input type="submit" value="Go"></td> -->
		</tr>
	</table>


	<table class="table table-striped table-highlight">
		<tr>
			<td colspan="9">
			<c:choose>
					<c:when test="${msg eq 'success'}">
						<label class="alert alert-danger"> Record is successfully
							Deleted</label>

					</c:when>
					<c:when test="${msg eq 'fail'}">
						<label class="alert alert-danger"> Record can not Deleted
							as it is referenced by other submenus</label>
					</c:when>
				</c:choose> <br /></td>
		</tr>
		<tr>
			<td colspan="11"><br /></td>
		</tr>
		<tr>
			<th>Role</th>
			<th>Parent ID</th>
			<th>Parent Sequence</th>
			<th>Parent</th>
			<th>Menu Id</th>
			<th>Menu Name</th>

			<th>Target</th>
			<th>Sequence</th>
			<th>Parent IsACtive</th>
			<th>Child IsACtive</th>

			<th>Action</th>
		</tr>
		<c:forEach var="list" items="${listMenu}">
			<tr>

				<td>${fn:substringBefore(list.key, ",")}</td>
				<td>${fn:substringBefore(fn:substringAfter(fn:substringAfter(list.key, ","),":::"),"$")}</td>
				<td>${fn:substringBefore(fn:substringAfter(fn:substringAfter(fn:substringAfter(list.key, ","),":::"),"$"),"@")}</td>
				<td>${fn:substringBefore(fn:substringAfter(list.key, ","),":::")}</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			
				<td>${fn:substringAfter(fn:substringAfter(fn:substringAfter(fn:substringAfter(list.key, ","),":::"),"$"),"@")}</td>
					<td></td>
				<td>
				<a href="editMenu?pmenuId=${fn:substringBefore(fn:substringAfter(fn:substringAfter(list.key,","),":::"),"$")}">Modify</a>
					<a href="#" class="delete" data-id="${fn:substringBefore(fn:substringAfter(fn:substringAfter(list.key,",") ,":::"),"$")}">Delete</a>
		
				</td>
			</tr>
			
			<c:forEach var="menulist" items="${list.value}">
			<c:if test="${menulist.menuid > 0}">
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>${menulist.menuid}</td>
					<td>${menulist.menuname}</td>
					<td>${menulist.target}</td>
					<td>${menulist.sequence}</td>
					<td></td>
					<td>${menulist.cactive}</td>
					
					<td><a href="editMenu?menuId=${menulist.menuid}">Modify</a> <a
						href="#" data-id="${menulist.menuid}+" class="delete" >Delete</a>
						<input type="hidden" id="id" value="${menulist.menuid}"></td>

				</tr>
				</c:if>
			</c:forEach>



		</c:forEach>

	</table>
	<%-- <div id="deletesubmenu" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post" action="deleteMenu">
					<div class="modal-header">
						<h4 class="modal-title">Delete Menu</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to delete "${param.id}" Menu?</p>

					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							value="Cancel"> <input type="submit"
							class="btn btn-danger" value="Delete"> <input
							type="hidden" name="menuId" value="${menulist.menuid}">
					</div>
				</form>
			</div>
		</div>

	</div> --%>
	<%-- 		</form:form> --%>
</div>
<script>
/* $(function(){
	$('.delete').click(function(e) {
		var del = e.target.getAttribute('data-id');
		confirmAlert('Do you want to delete the '+del+' menu ?');
		$("#ok").click(function(){
			$('#popup').modal('hide');
		$.ajax({  
            url:"deleteMenu",
            type: "post",  
            data: {menuId:del},
            error:function(xhr,status,er){
                console.log(er);
            },
            success: function(data) {
            	//successAlert('Record is successfully Deleted');
            	if(data==='success'){
            		
            		successAlert('Record is successfully Deleted');
        			 $("#successok").click(function(){
        			$('#popup').modal('hide');
        			window.location.href="mainmenu";
        			});  
        			$(".close").click(function(){
        			$('#popup').modal('hide');
        			window.location.href="mainmenu";
        			}); 	
    	}else{
    		$('.alert').html('Record can not Deleted as it is referenced by other submenus');
    		successAlert('Record can not Deleted as it is referenced by other submenus');
			  $("#successok").click(function(){
			$('#popup').modal('hide');
			window.location.href="mainmenu";
			});  
			$(".close").click(function(){
			$('#popup').modal('hide');
			window.location.href="mainmenu";
			});  
    				}	
            }
		});
		});
	});
}); */
</script>

<script src='<c:url value="/resources/js/menu.js" />'></script>
<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>
</body>
</html>