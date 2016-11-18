<%@ page pageEncoding="UTF-8" %>
<%@ page import="modele.Countdown" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="util.Util" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"> 
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-formhelpers/2.3.0/css/bootstrap-formhelpers.css">
	<title>CountDown</title>
	<style>
h1{
  font-size: 30px;
  color: #fff;
  text-transform: uppercase;
  font-weight: 300;
  text-align: center;
  margin-bottom: 15px;
}
table{
	
  width:100%;
  table-layout: fixed;
  color: white !important;
}

th{
  padding: 20px 15px;
  text-align: left;
  font-weight: 500;
  font-size: 12px;
  
  text-transform: uppercase;
}
td{
  padding: 15px;
  text-align: left;
  vertical-align:middle;
  font-weight: 300;
  font-size: 12px;
  border-bottom: solid 1px rgba(255,255,255,0.1);
}
button{
	background: rgba(0,0,0,0.0)!important;
  border-radius: 5px;
  border:1px solid #FFF!important;
  color:#fff!important;
  font-family: 'Roboto', sans-serif;
  
}
input,.input-group-addon{
	background: rgba(0,0,0,0.0)!important;
  border-radius: 5px;
  border:1px solid #FFF;
  color:#fff !important;
  font-family: 'Roboto', sans-serif;
}
.bootstrap-datetimepicker-widget{
  background: -webkit-linear-gradient(left, #b30086, #ff6666);
  background: linear-gradient(to right, #b30086, #ff6666);
  box-shadow: 0px 0px 2px #ffffff;
}

.bootstrap-datetimepicker-widget td:hover{
    cursor:pointer;
    font-weight:bold !important;
}

@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,300,700);
body{
  background: -webkit-linear-gradient(left, #9900cc, #e68a00);
  background: linear-gradient(to right, #9900cc, #e68a00);
  font-family: 'Roboto', sans-serif;
}
label{
	color:#fff;
	font-family: 'Roboto', sans-serif;
}
.glyphicon{
	color:white !important;
}
td form{
	display:inline-block !important;
}

	</style>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>   
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="https://use.fontawesome.com/f7afa7f027.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-formhelpers/2.3.0/js/bootstrap-formhelpers.min.js"></script>
</head>

<body>
</div>
	<header>
		<div class="container">
			<h1><%
					out.println("Countdown app(EN)");
			%></h1>
		</div>
	</header>
	<div class="container">
		<table id="myTable" class="table table-condensed">
			<thead>
			    <tr>
			      <th>#</th>
			      <th>Title</th>
			      <th>Countdown</th>
			      <th>Action</th>
			    </tr>
			</thead>
			<tbody>
				<%
				ArrayList<Countdown> alC = (ArrayList<Countdown>)request.getAttribute("countdowns");
				for (int i=0;i<alC.size();i++)
		        {
		        %>
				<tr>
					<td><% out.print(alC.get(i).getId());%></td>
					<td><% out.print(alC.get(i).getTitle());%></td>
					<td id="countdown<% out.print(alC.get(i).getId());%>"><%
						out.print(Util.diff(alC.get(i).getDate()));
					%></td>
					<td><div><button onclick="updateCountdown(<% out.print(alC.get(i).getId());%>,'<%out.print(alC.get(i).getTitle());%>','<%out.print(alC.get(i).getDate());%>')"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
						<form action="countdownDelete" method="POST"><button name="delete" value="<% out.print(alC.get(i).getId());%>" type="submit" ><i class="fa fa-times" aria-hidden="true"></i></button></form></div></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
	<div id="cuCountdown" class="container">
		<button id="addCountdownButton" onClick="addCountdown()">Add Countdown</button>
	</div>
</body>
</html>
<script>

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

var ws = new WebSocket("ws://localhost:8080/CountDownWebApp/ws");
ws.onopen = function(){
	ws.send('<%out.print(Util.getCookieValue(request,"countdownList").toString());%>');
};

ws.onmessage = function(message){
   
    var countdowns = JSON.parse(message.data);
    for(var i=0;i<Object.keys(countdowns).length;i++){
    	$("#myTable tr:eq("+(i+1)+") td:eq("+3+")").html(countdowns[i]);
    }
};

</script>

