$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();

	window.setTimeout(function() {
		$(".alert").fadeTo(1000, 0).slideUp(1000, function() {
			$(this).remove();
		});
	}, 5000);

    $('table .delete').on('click', function(e) {
   e.preventDefault();
    var id = $(this).parent().find('#id').val();
   
   $('#deletebaselineModal #id').val(id);
		});


	$('#add').on('click', function(e) {
		e.preventDefault();

		$.ajax({
			url: "gettypecode",
			type: "POST",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				var $btype = $('#btype');
				$btype.empty();
				$btype.append('<option value=""> --Select Base Line Type Data--</option>');

				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$btype.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}

			}


		});
	});

	$('table .edit').on('click', function() {
		var id = $(this).parent().find('#id').val();
		$.ajax({
			url: "gettypecode",
			type: "POST",
			data: {},
			error: function(xhr, status, er) {
				console.log(er);
			},
			success: function(data) {
				var $btype = $('#btype1');
				$btype.empty();
				$btype.append('<option value=""> --Select Base Line Type Data--</option>');

				for (var key in data) {
					if (data.hasOwnProperty(key)) {
						$btype.append('<option value=' + key + '>' + data[key] + '</option>');
					}
				}

			}


		});


		$.ajax({
			type: 'GET',
			url: "baselinefind",
			data: { id: id },
			error: function(xhr, status, er) {
				console.log(er);
			},

			success: function(data) {
				for (var key in data) {
					if (data.hasOwnProperty(key)) {

						$("#btype1 option[value=" + data[key].bldescription + "]").attr("selected", "selected")
						$('#bdesc').val(data[key].description);
						$('#seqno1').val(data[key].seqno.toFixed(2));
						$('#baselineId').val(id);
					}
				}

			}

		});


	});


});