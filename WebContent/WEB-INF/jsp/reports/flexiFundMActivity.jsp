<%@include file="/WEB-INF/jspf/header2.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.6.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/phystyle.css" />">

<style>
    .table-title h5 {
        margin: 0;
        padding: 10px 0;
    }
    .alert {
        margin-top: 10px;
    }
</style>

<script type="text/javascript">
    $(document).ready(function() {
        
        // Prevent duplicate form submission
        $('form').on('submit', function() {
            var $form = $(this);
            var $submitBtn = $form.find('input[type="submit"]');
            
            // Check if already submitted
            if ($form.data('submitted') === true) {
                return false;
            }
            
            // Mark as submitted
            $form.data('submitted', true);
            
            // Disable submit button and change text
            $submitBtn.prop('disabled', true);
            $submitBtn.val('Processing...');
            
            return true;
        });
        
        // Edit functionality - open modal and populate data
        $('#activitydatatable').on('click', '.edit', function() {
            var row = $(this).closest('tr');
            var id = row.find('.act_id').val();
            var actname = row.find('.act_name_display').text();
            
            $("#actid1").val(id);
            $("#actname1").val(actname);
            $("#activityId").val(id);
            
            // Reset form submission flag when modal opens
            $('#editActivityModal form').data('submitted', false);
        });
        
        // Delete functionality - set id for deletion
        $('#activitydatatable').on('click', '.delete', function() {
            var row = $(this).closest('tr');
            var id = row.find('.act_id').val();
            $("#deleteid").val(id);
            
            // Reset form submission flag when modal opens
            $('#deleteActivityModal form').data('submitted', false);
        });
        
        // Clear form when add button is clicked
        $("#addactivity").click(function() {
            $("#actname").val('');
            // Reset form submission flag when modal opens
            $('#addActivityModal form').data('submitted', false);
        });
        
        // Auto-hide alert message after 5 seconds
        setTimeout(function() {
            $(".alert").fadeOut('slow');
        }, 5000);
        
        // Reset form submission flag when modals are closed
        $('.modal').on('hidden.bs.modal', function() {
            $(this).find('form').data('submitted', false);
            $(this).find('input[type="submit"]').prop('disabled', false);
            $(this).find('input[type="submit"]').val('Save');
        });
    });
</script>

<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6" onclick="location.href='showactivityform'">
                    <h5>Flexi Fund Activity Master <b>(flexi_fund_activity_master)</b></h5>
                </div>
                <div class="col-sm-6">
                    <a href="#addActivityModal" id="addactivity" class="btn btn-success" data-toggle="modal">
                        <i class="material-icons" data-toggle="tooltip">&#xE147;</i> 
                        <span>Add New Activity</span>
                    </a>
                </div>
            </div>
        </div>
        
        <!-- Display message if any -->
        <c:if test="${not empty message}">
            <div class="alert alert-info alert-dismissible fade show">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <label style="color:blue; font-size:14px;">${message}</label>
            </div>
        </c:if>
        
        <!-- Activity Data Table -->
        <table class="table table-striped table-hover" id="activitydatatable">
            <thead>
                <tr>
                    <th>Activity ID</th>
                    <th>Activity Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="list" items="${activitydata}">
                    <c:forEach var="listUser" items="${list.value}">
                        <tr>
                            <td><c:out value="${listUser.act_id}"></c:out></td>
                            <td class="act_name_display"><c:out value="${listUser.act_name}"></c:out></td>
                            <td>
                                <a href="#editActivityModal" class="edit" data-toggle="modal">
                                    <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
                                </a>
                                <input type="hidden" class="act_id" value="${listUser.act_id}">
                            </td>
                            <td>
                                <a href="#deleteActivityModal" class="delete" data-toggle="modal">
                                    <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                </a>
                                <input type="hidden" class="act_id" value="${listUser.act_id}">
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Add Activity Modal -->
<div id="addActivityModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/saveactivityData">
                <div class="modal-header">      
                    <h4 class="modal-title">Add Flexi Fund Activity</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">     
                    <div class="form-group">
                        <label>Activity Name <span style="color:red;">*</span></label>
                        <input type="text" class="form-control" id="actname" required="required" autocomplete="off" name="actname" placeholder="Enter Activity Name">
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-info" value="Save">
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Activity Modal -->
<div id="editActivityModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/updateactivityData">
                <div class="modal-header">      
                    <h4 class="modal-title">Edit Flexi Fund Activity</h4>
                    <button type="reset" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">   
                    <div class="form-group">
                        <label>Activity ID</label>
                        <input type="text" id="actid1" disabled="disabled" class="form-control" autocomplete="off">
                    </div>
                    <div class="form-group">
                        <label>Activity Name <span style="color:red;">*</span></label>
                        <input type="text" id="actname1" class="form-control" autocomplete="off" required="required" name="actname" placeholder="Enter Activity Name">
                    </div>
                    <input type="hidden" id="activityId" name="id">  
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-info" value="Update">
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Delete Activity Modal -->
<div id="deleteActivityModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/deleteactivityData">
                <div class="modal-header">      
                    <h4 class="modal-title">Delete Flexi Fund Activity</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">     
                    <p class="text-center">Are you sure you want to delete this Activity?</p>
                    <p class="text-center text-danger"><strong>Note: This action cannot be undone!</strong></p>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-danger" value="Delete">
                    <input type="hidden" name="id" id="deleteid">
                </div>
            </form>
        </div>
    </div>
</div>

<footer class="text-center">
    <%@include file="/WEB-INF/jspf/footer2.jspf"%>
</footer>

</body>
</html>