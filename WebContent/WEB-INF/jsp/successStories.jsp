<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<head>
<%
response.addHeader("X-Frame-Options", "DENY");
response.addHeader("Referrer-Policy", "origin-when-cross-origin");
response.addHeader("Content-Security-Policy", "self");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
response.setHeader("X-Frame-Options", "SAMEORIGIN");
response.setHeader("X-Powered-By", "unset");
response.setHeader("Server", "unset");
response.addHeader("X-XSS-Protection: 1", "mode=block");
/* response.addHeader("Content-Security-Policy", "self"); */
response.addHeader("referrer", "no referrer");
response.addHeader("X-Content-Type-Options", "nosniff");
response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
%>

</head>
<div class="container-fluid successContainer">
	<div class="row">
		<div class="col-12 text-center list-group-item ">
			<h5>Success Stories</h5>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3 list-group successListGroup">
			<c:forEach var="list" items="${stories}">
				<a href="#" id="showpdf"
					onclick="showPdf('FileServlet?path=${list.path}/${list.fileName}','${list.subject}');"
					class="list-group-item list-group-item-action list-group-item-info">
					<h6 class="font-weight-bold my-3">
						<c:out value="${list.subject }"></c:out>
					</h6>
				</a>
			</c:forEach>
			<div class="inline">
			<c:if test="${prevPage>0}">
			<a href="#" class="btn-info" id="prev" value="${prevPage}" onclick="pagination(prevPage)" style="padding:5px;margin:3px;">|</a>
			</c:if>
			<c:if test="${displayNext}">
				<a href="#" class="btn-info" id="next" value="${nextPage}" onclick="pagination(nextPage)" style="padding:5px;margin:3px;">|</a>
				</c:if>
				</div>
		</div>
		<!-- <div class="col-sm-1 successMiddleDiv" ></div> -->
		<div class="col-sm-9 successPdfMainDiv">
			<!-- <button id="show-pdf-button" style="display:none;">Show PDF</button>  -->
			<div class="pdfIcon">
				<p>Click on the link left to view success stories</p>
				<img src="<c:url value="/resources/images/pdficon.png" />"
					alt="pdf Icon" />
			</div>

			<div id="pdf-main-container">
				<div id="pdf-loader">Loading document ...</div>
				<div id="pdf-contents">
					<h4 class="pdfHeading">Heading</h4>
					<div id="pdf-meta">
						<div id="pdf-buttons">
							<button id="pdf-prev" class="btn-info">|</button>
							<button id="pdf-next" class="btn-info">|</button>
						</div>
						<div id="page-count-container">
							Page
							<div id="pdf-current-page"></div>
							of
							<div id="pdf-total-pages"></div>
						</div>
					</div>
					<canvas id="pdf-canvas" class="d-block" ></canvas>
					<div id="page-loader">Loading page ...</div>
				</div>
			</div>

		</div>


	</div>
</div>

<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>

</body>
</html>