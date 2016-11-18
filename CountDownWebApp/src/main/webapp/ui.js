$('#myTable').DataTable()
function addCountdown(){
	$("#cuCountdown").html('\
	<div class="container"> \
		<div class="row"> \
			<form action="countdownAdd" method="POST">\
				<div class="col-md-3">\
					<div class="form-group">\
						<label for="title">Title</label> \
						<input class="form-control" type="text" name="title" placeholder="Title"> \
					</div> \
				</div> \
				<div class="col-md-3">\
					<div class="form-group">\
						<label for="date">Date</label> \
						<div class="input-group date" id="datetimepicker"> \
							<input type="datetime" class="form-control" name="date"/> \
							<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span> \
							</span> \
						</div> \
					</div> \
				</div> \
				<div class="col-md-2"> \
					<div class="form-group"> \
						<label for="add"></label> \
						<input name="add" class="form-control" type="submit" value="Add"> \
					</div> \
				</div> \
			</form> \
		</div> \
	</div> \
	<button onClick="cancel()"> \
	<i class="fa fa-ban" aria-hidden="true"></i> \
	</button>'); 
	$(function () {
		var dateNow = new Date();
        $('#datetimepicker').datetimepicker({
            defaultDate:dateNow
        });
    });
}

function updateCountdown(id,title,date){
	$("#cuCountdown").html('\
	<div class="container"> \
		<div class="row"> \
			<form action="countdownUpdate" method="POST">\
				<div class="col-md-3">\
					<div class="form-group">\
						<label for="title">Title</label> \
						<input class="form-control" type="text" name="title" value="'+title+'"> \
					</div> \
				</div> \
				<div class="col-md-3">\
					<div class="form-group">\
						<label for="date">Date</label> \
						<div class="input-group date" id="datetimepicker"> \
							<input type="datetime" class="form-control" name="date"/> \
							<span class="input-group-addon"> \
							<span class="glyphicon glyphicon-calendar"></span> \
							</span> \
						</div> \
					</div> \
				</div> \
				<div class="col-md-2"> \
					<div class="form-group"> \
						<label for="add"></label> \
						<button name="update" class="form-control" type="submit" value="'+id+'">update</button> \
					</div> \
				</div> \
			</form> \
		</div>\
	</div>\
	<button onClick="cancel()">\
	<i class="fa fa-ban" aria-hidden="true"></i>\
	</button>');
	$(function () {
        $('#datetimepicker').datetimepicker({
            defaultDate:Date.parse(date)
        });
    })
}

function cancel(){
	$("#cuCountdown").html('\
	<div id="addCountdown"> \
		<button id="addCountdownButton" onClick="addCountdown()">Add Countdown</button> \
	</div> \
	');
}