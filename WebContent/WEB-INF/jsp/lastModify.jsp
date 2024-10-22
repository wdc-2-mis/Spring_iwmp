<%-- for finding last modified date of every jsp page in application, this jsp is imported only in footer --%>
<%@ page import="java.io.*,java.util.*,java.text.*"  errorPage="error.jsp"%>

<%
		
/* getting full path of current file */
String uri = request.getRequestURI();
String fileName = uri.substring(uri.indexOf("WEB-INF"));
String fullPath = application.getRealPath(fileName);

File file = new File(fullPath);

/* setting the date and time format */
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

%>