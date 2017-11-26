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
		<script type="text/javascript" src="<%=basePath %>resources/js/messager.js"></script>
		<!-- Bootstrap -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript">
		
		</script>
	</head>
	<body>
		<nav class="navbar navbar-inverse">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="<%=basePath %>">@AST</a>
				</div>
				<nav class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="<%=basePath %>user/main">首页</a>
						</li>
					</ul>
				</nav>
			</div>
		</nav>
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="<%=basePath %>user/main">首页</a><span class="divider"></span></li>
				<li class="active">提交成功</li>
			</ul>
			<div class="alert alert-success text-center">
				<h2>恭喜，您已成功提交测试！</h2>
				<div>
					<a class="btn btn-link" href="<%=basePath %>user/main">回到首页</a>
					<span class="divider">|</span>
					<a class="btn btn-link" href="<%=basePath %>user/build">继续提交测试</a>
				</div>
			</div>
		</div>
	</body>
</html>