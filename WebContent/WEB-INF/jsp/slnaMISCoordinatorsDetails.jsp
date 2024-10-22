<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header_new.jspf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en" dir="ltr">
   <head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   <link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/dolrsupportstyles.css" />" />
	<link rel="stylesheet" href="resources/css/font/bootstrap-icons.css" />
	<link rel="stylesheet"
	href="resources/css/font/font-awesome4.7.min.css">
   </head>
   <body>
    <section class="head pb-5">
    <div class="container py-5">
     <div class="card">
      <div class="card-body">
       <h1 class="font-weight-light text-center py-3"><spring:message code="label.MISCoordinatorSLNA"/></h1>
       <div class="panel-body">
       </div>
        <div class="row">

						<c:forEach var="list" items="${dataList}" varStatus="counter">
							<c:if test ="${counter.count % 2 ==0 }">
							<div class="col-lg-6 col-md-12 col-sm-12 col-12">
								<div class="row  pt-3">
									<div class="col-lg-1 offset-1 col-md-2 col-sm-2 col-2"></div>
									<div class="col-lg-10 col-md-9 col-sm-9 col-9">
										<h4>
											<i class="bi bi-person-fill"></i>
											<c:out value="${list.stname}"></c:out>
										</h4>
										<h6>
											<c:out value="${list.name}"></c:out>
										</h6>
										<h6>
											<c:out value="${list.email}"></c:out>
										</h6>
										<h6>
											<c:out value="${list.mobile}"></c:out>
										</h6>
									</div>
								</div>
							</div>
							</c:if>
							<c:if test ="${counter.count % 2 !=0 }">
							<div class="col-lg-6 col-md-12 col-sm-12 col-12">
								<div class="row  pt-3">
									<div class="col-lg-1 offset-1 col-md-2 col-sm-2 col-2"></div>
									<br>
									<div class="col-lg-10 col-md-9 col-sm-9 col-9">
										<h4>
											<i class="bi bi-person-fill"></i>
											<c:out value="${list.stname}"></c:out>
										</h4>
										<h6>
											<c:out value="${list.name}"></c:out>
										</h6>
										<h6>
											<c:out value="${list.email}"></c:out>
										</h6>
										<h6>
											<c:out value="${list.mobile}"></c:out>
										</h6>
									</div>
								</div>
							</div>
							</c:if>
						</c:forEach>
					</div>
     </div>
     
    </div>
    </div>
    </section>    
  

<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>