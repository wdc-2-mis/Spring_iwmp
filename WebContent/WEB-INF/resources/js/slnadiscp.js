function save(val)
{
var state = 0;
if(val=='N')
	{
		if(confirm("Are you sure you do not want District approval in your State ?"))
		{
			if(confirm("Have you consulted the decision with your CEO?"))	
				{
				$.ajax({  
            url:"updateslnadisper",
            type: "POST",
            data: {val:val, state:state}, 
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
            alert("successfuly saved");
            location.reload();
            }
            });
				
				
				}
			else (alert("Kindly confirm with your CEO first"))
		}
		return;
	}
	if(val=='Y')
		
		{
			if(confirm("Are you sure you want District approval in your State ?"))
			{
				if(confirm("Have you consulted the decision with your CEO?"))	
					{
				$.ajax({  
            url:"updateslnadisper",
            type: "POST",
            data: {val:val,state:state}, 
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
            alert("successfuly saved");
            location.reload();
           
            }
            });
					}
				else (alert("Kindly confirm with your CEO first"))
			}
			return;
		}
	}

function savedolr(val)
{
var state=document.getElementById("state").value;
if (state == 0) {
            alert("Please select state first");
        }
        else{
var state = $('#state option:selected').val();
	if(val=='N')
	{
		if(confirm("Are you sure you do not want District approval in your State ?"))
		{
			if(confirm("Have you consulted the decision with your CEO?"))	
				{
				$.ajax({  
            url:"updateslnadisper",
            type: "POST",
            data: {val:val, state:state}, 
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
            
            alert("successfuly saved");
            location.reload();
            }
            });
				
				
				}
			else (alert("Kindly confirm with your CEO first"))
		}
		return;
	}
	if(val=='Y')
		
		{
			if(confirm("Are you sure you want District approval in your State ?"))
			{
				if(confirm("Have you consulted the decision with your CEO?"))	
					{
				$.ajax({  
            url:"updateslnadisper",
            type: "POST",
            data: {val:val,state:state}, 
            error:function(xhr,status,er){
                console.log(er);
                },
            success: function(data) {
            alert("successfuly saved");
            location.reload();
           
            }
            });
					}
				else (alert("Kindly confirm with your CEO first"))
			}
			return;
		}
	}
	}
	

function changeOption(v)
{
				
				document.approval.action="selectdolr";
				document.approval.method="POSt";
				document.forms[0].submit();
				
}

