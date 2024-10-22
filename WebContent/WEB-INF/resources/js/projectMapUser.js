
function saveRoleUserMap() 
{
	/*var t = document.getElementById("user");
	var selectedText = t.options[t.selectedIndex].text;
	document.getElementById("userid").value=selectedText;*/
	if(document.getElementById("district").value=='')
	{
		alert("select District !");
		document.getElementById("district").focus();
		
		return false;
	}
	if(document.getElementById("user").value=='')
	{
		alert("select User id !");
		document.getElementById("user").focus();
		
		return false;
	}
	if(document.getElementById("project").value=='')
	{
		alert("select Project !");
		document.getElementById("project").focus();
		
		return false;
	}
	
 	if(confirm("Do you want to Assigned Projects ?"))
	{
	    document.getElementById('projectmap').action ="saveUserProjectAssign";
	    document.getElementById('projectmap').method="post";
	    document.getElementById('projectmap').submit();
	}    
}

function deleteActiveUserProject() 
{
	/*if(document.getElementById("state").value=='')
	{
		alert("select state !");
		document.getElementById("state").focus();
		
		return false;
	}*/
	if(document.getElementById("district").value=='')
	{
		alert("select District !");
		document.getElementById("district").focus();
		
		return false;
	}
	if(document.getElementById("user").value=='')
	{
		alert("select User id !");
		document.getElementById("user").focus();
		
		return false;
	}
	if(document.getElementById("project").value=='')
	{
		alert("select Project !");
		document.getElementById("project").focus();
		
		return false;
	}
	
 	if(confirm("Do you want to remove Project from selected User ?"))
	{
	    document.getElementById('remprojectmap').action ="deleteActiveUserProject";
	    document.getElementById('remprojectmap').method="post";
	    document.getElementById('remprojectmap').submit();
	}    
}









