<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- Title and other stuffs -->
		<title>App Server Testing</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!-- JS -->
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.js"></script>
		<!-- Bootstrap -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript">
			function register() {
				$.ajax({
					url: "<%=basePath %>api/register",
					dataType: "json",
					success: function(data) {}
				});
			}
			
			function task() {
				$.ajax({
					url: "<%=basePath %>api/" + $("#agentid1").val() + "/1",
					data: $("#procnames").val(),
					dataType: "json",
					success: function(data) {}
				});
			}
			
			function response() {
				$.ajax({
					url: "<%=basePath %>api/" + $("#agentid2").val() + "/2",
					data: $("#parameters").val(),
					dataType: "json",
					success: function(data) {}
				});
			}
			
			function proxy() {
				$.ajax({
					url: "<%=basePath %>api/proxy",
					dataType: "json",
					success: function(data) {}
				});
			}
		</script>
	</head>
	<body>
		<nav class="navbar navbar-default">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand">AST - API</a>
				</div>
				<nav class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="<%=basePath %>api">首页</a>
						</li>
					</ul>
				</nav>
			</div>
		</nav>
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="<%=basePath %>api">首页</a><span class="divider"></span></li>
				<li class="active">TESTING ONLY</li>
			</ul>
			<div class="well">
				<button class="btn btn-info btn-block" onclick="register()">1.Register</button>
			</div>
			<div class="well">
				<form>
					<div class="form-group">
						<label>Agent ID:</label>
						<input type="text" id="agentid1" class="form-control" value="100000"/>
					</div>
					<div class="form-group">
						<label>Parameters:</label>
						<input type="text" id="procnames" class="form-control" value="procname="/>
					</div>
				</form>
				<button class="btn btn-info btn-block" onclick="task()">2.Task</button>
			</div>
			<div class="well">
				<form>
					<div class="form-group">
						<label>Agent ID:</label>
						<input type="text" id="agentid2" class="form-control" value="100000"/>
					</div>
					<div class="form-group">
						<label>Parameters:</label>
						<input type="text" id="parameters" class="form-control" value="taskid=&error=&message="/>
					</div>
				</form>
				<button class="btn btn-info btn-block" onclick="response()">3.Response</button>
			</div>
			<div class="well">
				<button class="btn btn-info btn-block" onclick="proxy()">4.Run proxy</button>
			</div>
		</div>
	</body>
</html>