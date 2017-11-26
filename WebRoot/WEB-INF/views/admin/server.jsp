<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<script type="text/javascript" src="<%=basePath %>resources/js/messager.js"></script>
		<!-- Bootstrap -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<nav class="navbar navbar-inverse navbar-default">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand">AST</a>
				</div>
				<nav class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href="<%=basePath %>admin/user">用户管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/meta">厂商和型号管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/district">地域管理</a>
						</li>
						<li class="active">
							<a href="#">服务器管理</a>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
			        	<li>
							<a href="#">${sessionScope.user.username}</a>
						</li>
			        	<li>
							<a href="<%=basePath %>admin/signout">安全退出</a>
						</li>
			      	</ul>
				</nav>
			</div>
		</nav>
		<div class="container">
			<div>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>服务器地址</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="server" items="${serverList}" varStatus="s">
						<tr>
							<td>${s.count}</td>
							<td>${server.url}</td>
							<td></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>