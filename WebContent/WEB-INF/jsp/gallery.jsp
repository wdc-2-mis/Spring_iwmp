<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<div class="container-fluid">
<!-- <div class="row">
<div class="col-12 text-center">
<h5>Activities Photo Gallery</h5>
</div>
</div> -->
 <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
<script src="//cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
 
<div class="row">
<%-- <div class="col-sm-offset-3 col-sm-3"></div>
 
 <img src="<c:url value="/resources/images/webPageConstruction1.jpg" />" class="img-fluid" />
                                                
</div> --%>
<div class="col-9" style="border-right:2px solid #e2e2e2;border-left:2px solid #e2e2e2;border-top:2px solid #e2e2e2;box-shadow: 0px 10px 20px #4f9eab inset;">
<!-- <img src="FileServlet?path=C:/Users/Spectra/Desktop/DUST(13-10-2020)/Files/98/Documents.war/ProjectPhotographs/PRD/Documents/iwmp_file_0046.JPG" /> -->
<div class="col" style="background:#fff;border:1px solid #e2e2e2;box-shadow: 0px 10px 20px #4f9eab;margin-top:2px;border-radius:5px;text-align:justify;">
<p><h5><center><b>Photographs of Watershed Activities of WDC-PMKSY 2.0.</b></center></h5></p>
</div>

<div class="row">


    <!-- Page Content -->
   <div class="container page-top">



        <div class="row">
<c:forEach var="list" items="${files}">
				<c:forEach var="files" items="${list.value}">
			<%-- <li>	<a href="">${files.path}/${files.fileName}</a></li> --%>
			  <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="http://wdcpmksy.dolr.gov.in/filepath/PRD/CircularMessageAlert/${files.fileName}" class="fancybox" rel="ligthbox">
                    <img  src="http://wdcpmksy.dolr.gov.in/filepath/PRD/CircularMessageAlert/${files.fileName}" class="zoom img-fluid "  alt="">
                   
                </a>
            </div>
			</c:forEach>
			</c:forEach>

           <%--  <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="<c:url value="/resources/images/slide3.png" />" class="fancybox" rel="ligthbox">
                    <img  src="<c:url value="/resources/images/slide3.png" />" class="zoom img-fluid "  alt="">
                   
                </a>
            </div>
            <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="<c:url value="/resources/images/slide2.png" />"  class="fancybox" rel="ligthbox">
                    <img  src="<c:url value="/resources/images/slide2.png" />" class="zoom img-fluid"  alt="">
                </a>
            </div>
            
            <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="<c:url value="/resources/images/slide4.png" />" class="fancybox" rel="ligthbox">
                    <img  src="<c:url value="/resources/images/slide4.png" />" class="zoom img-fluid "  alt="">
                </a>
            </div>
            
            <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="<c:url value="/resources/images/slider1.jpg" />" class="fancybox" rel="ligthbox">
                    <img  src="<c:url value="/resources/images/slider1.jpg" />" class="zoom img-fluid "  alt="">
                </a>
            </div>
            
             <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="https://images.pexels.com/photos/1038914/pexels-photo-1038914.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" class="fancybox" rel="ligthbox">
                    <img  src="https://images.pexels.com/photos/1038914/pexels-photo-1038914.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" class="zoom img-fluid "  alt="">
                </a>
            </div>
            
             <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="https://images.pexels.com/photos/414645/pexels-photo-414645.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" class="fancybox" rel="ligthbox">
                    <img  src="https://images.pexels.com/photos/414645/pexels-photo-414645.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" class="zoom img-fluid "  alt="">
                </a>
            </div>
            
             <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="https://images.pexels.com/photos/56005/fiji-beach-sand-palm-trees-56005.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" class="fancybox" rel="ligthbox">
                    <img  src="https://images.pexels.com/photos/56005/fiji-beach-sand-palm-trees-56005.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" class="zoom img-fluid "  alt="">
                </a>
            </div>
            
             <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                <a href="https://images.pexels.com/photos/1038002/pexels-photo-1038002.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" class="fancybox" rel="ligthbox">
                    <img  src="https://images.pexels.com/photos/1038002/pexels-photo-1038002.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940" class="zoom img-fluid "  alt="">
                </a>
            </div> --%>
            
            
           
           
       </div>

     
      

    </div>

</div>
</div>

<div class="col-3" style="border-right:2px solid #e2e2e2;border-left:2px solid #e2e2e2;border-top:2px solid #e2e2e2;box-shadow: 0px 10px 20px #4f9eab inset;">
<h5>More from gallery......</h5>
<ul>
<c:forEach var="list" items="${allfiles}">
				<c:forEach var="files" items="${list.value}">
				<c:choose>
				<c:when test="${list.key eq ' ' || list.key eq null}">
				<li>	<a href="gallery?code=${files.stCode}">DOLR ${files.subject}</a></li>
				</c:when>
				<c:otherwise>
			    <li>	<a href="gallery?code=${files.stCode}">${list.key} ${files.subject}</a></li>
			    </c:otherwise>
			  </c:choose>
			</c:forEach>
			</c:forEach>
</ul>

</div>
</div>
</div>
<!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>