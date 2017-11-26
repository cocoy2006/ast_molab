<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<!-- JS 
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.js"></script>
		-->
		<!-- Bootstrap 
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap/js/bootstrap.js"></script>
		-->
  		<script type="text/javascript">
			location = "<%=basePath %>user/signin";
		</script>
  	</head>
  
  	<body style="margin-top: 50px;">
  		
  		<!-- 
  		<nav class="navbar navbar-default navbar-fixed-top" style="min-height: 0px;">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand">AST</a>
				</div>
				<nav class="collapse navbar-collapse pull-right">
					<ul class="nav navbar-nav">
						<li>
							<a href="<%=basePath %>#howto">业务流程</a>
						</li>
						<li>
							<a href="<%=basePath %>#contact">联系我们</a>
						</li>
						<c:if test="${empty sessionScope.user}">
							<li class="active">
								<a href="<%=basePath %>user/signin">登录</a>
							</li>
						</c:if>
						<c:if test="${not empty sessionScope.user}">
							<li class="active">
								<a href="<%=basePath %>user/main">${sessionScope.user.username}</a>
							</li>
						</c:if>
						<li>
							<a href="<%=basePath %>user/signup">注册</a>
						</li>
					</ul>
				</nav>
			</div>
		</nav>
		<div id="howto" class="alert alert-info text-center" style="height: 400px; margin: 50px 0 0; padding: 100px 0;">
			<h1>简洁的业务流程</h1>
			<h3>输入测试次数</h3>
			<h3>上传APP</h3>
			<h3>查看报告</h3>
		</div>
		<div id="contact" class="alert alert-success text-center" style="height: 400px; padding: 100px 0;">
			<h1>联系方式</h1>
			<h3>E-mail：ast@molab.com</h3>
			<h3>电&nbsp;&nbsp;&nbsp;话：138&nbsp;xxxx&nbsp;xxxx</h3>
		</div>
		-->
  	</body>
</html>
