<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css"
	href="<c:url  value="/resources/css/welcome.css" />">

<style>
body {
  font-family: Arial, Helvetica, sans-serif;
}

* {
  box-sizing: border-box;
}

/* Create a column layout with Flexbox */

</style>
</head>
<body>


<nav class ="sidebar">
<div class="text">welcome: ${loginId}</div>
 <div class="not-found" id="not-found" style="display:none">Not Found</div>
 
    <input type="text" id="mySearch" onkeyup="myFunction()" placeholder="Search.." title="Type in a category">
   <ul id="myMenu">
   <ul>
      <li><a href="#">Allocation</a>
      
      <ul>
      <li>
      <a href="#">Format A1- Statewise Allocation of tentative Physical Targets</a>
      </li>
      </ul>
      </li>
      <li>
      <a href="#">Project Funding</a>
      <ul>
      <li><a href="#">Format F1- Sanction-wise Release of Project Funds from DoLR / State Govt.</a></li>
      <li><a href="#">Format F2- Project Fund - Year wise Central Funds Released</a></li>
      <li><a href="#">Format F3- Project-wise State Share Release Details</a></li>
      <li><a href="#">Format F5- Details of amount released from SLNA to WCDC</a></li>
      <li><a href="#">Format F6- Details of amount released from WCDC to PIA / GP / WC</a></li>
      <li><a href="#">Format F7- Details of amount released from PIA to GP / WC</a></li>
      <li><a href="#">Format F8- Details of amount released from GP to WC</a></li>
      </ul>
      </li>
      <li>
      <a href="#">Institutional Funding</a>
      <ul>
      <li><a href="#">Format G1- Sanction Order Details of Institutional Funds</a></li>
      <li><a href="#">Format G2- Details of Institutional amount Released by DoLR(Govt. of India) to SLNA</a></li>
      <li><a href="#">Format G3- Details of Institutional amount Released by SLNA to WCDC</a></li>
      <li><a href="#">Format G4- Details of expenditure of Institutional fund by SLNA / WCDC</a></li>
      </ul>
      </li>
      
      
    </ul>
  </ul>
  </nav>
  <script>
function myFunction() {
	 var input, filter, ul, li, a, a1,i;
	input = document.getElementById("mySearch");
	filter = input.value.toUpperCase();
	div = document.getElementById("myMenu");
	a = div.getElementsByTagName("a");
	for (i = 0; i < a.length; i++) {
	  txtValue = a[i].textContent || a[i].innerText;
	  if (txtValue.toUpperCase().indexOf(filter) > -1) {
	    a[i].style.display = "";
	  } else {
	     a[i].style.display = "none"; 
	    

	  }
	 
	}
	}

</script>
  <!-- Footer -->
<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>
</body>
</html>