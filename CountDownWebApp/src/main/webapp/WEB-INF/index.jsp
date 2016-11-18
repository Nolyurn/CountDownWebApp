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
	<link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
	<link rel="stylesheet" type="text/css" href="style.css">
	<title>CountDown</title>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>   
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="https://use.fontawesome.com/f7afa7f027.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<script src="ui.js"></script>
</head>

<body>
</div>
	<header>
		<div class="container">
			<h1><%
					out.println("Countdown app (EN)");
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
				<tr id="tr<% out.print(i);%>">
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
$('#myTable').DataTable({
	"bPaginate": false,
	"bInfo": false,
	"aoColumns": [ {"bSearchable": false}, 
		{"bSearchable": true}, 
		{"bSearchable": false},
		{"bSearchable": false}
		]
	})

var ws = new WebSocket("ws://localhost:8080/CountDownWebApp/ws");
ws.onopen = function(){
	ws.send('<%out.print(Util.getCookieValue(request,"countdownList").toString());%>');
};

ws.onmessage = function(message){
   
    var countdowns = JSON.parse(message.data);
    for(var i=0;i<Object.keys(countdowns).length;i++){
    	$("#myTable #tr"+(countdowns[i].id)+" td:eq("+2+")").html(countdowns[i].date);
    }
};

</script>

