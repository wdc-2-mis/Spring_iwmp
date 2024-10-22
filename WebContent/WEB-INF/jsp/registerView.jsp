<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>

<script type="text/javascript">

function submitRegisterview(id) 
{
	if(document.getElementById("newuserRId").value=='')
	{
		alert("Enter Registration Id !");
		document.getElementById("newuserRId").focus();
		return false;
	}
	if(confirm("Do you want to View/Download ?"))
	{
	    document.getElementById('newuserview').action = "viewNewRegPDF";
	    document.getElementById('newuserview').method="post";
	    document.getElementById('newuserview').submit();
	}    
}

</script>

</head>

<div class="table-responsive" align="center">

<div class="col" style="text-align:center;"><h5>View/Download PDF for new user Registration</h5></div>
 <form:form autocomplete="off" name="newuserview" id="newuserview"  modelAttribute="newuserview" action="viewNewRegPDF" method="post" >
 
      <table >
        <tr>
          <td class="label">Registration Id &nbsp;&nbsp;&nbsp;</td>
          <td>
            <input type="text" id="newuserRId" name="newuserRId"  maxlength="50" />
          </td>
          <td align="left"> &nbsp; &nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-info" id="view" onclick="submitRegisterview(this);" name="view" value='View/Download PDF' /> </td>
       </tr>
      </table>
 </form:form>

</div>

<footer class="text-center">
	<%@include file="/WEB-INF/jspf/footer.jspf"%>
</footer>