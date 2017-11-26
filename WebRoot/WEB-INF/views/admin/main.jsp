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
		<nav class="navbar navbar-default">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand">AST</a>
				</div>
				<nav class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="#">工单管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/user">用户管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/meta">厂商和型号管理</a>
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
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>APP名称</th>
						<th>包名</th>
						<th>测试进度</th>
						<th>提交时间</th>
						<th>测试状态</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${fn:length(tcList) > 0}">
						<c:forEach var="tc" items="${tcList}" varStatus="s">
							<tr>
								<td>${s.count}</td>
								<td>${tc.application.name}</td>
								<td>${tc.application.packagename}</td>
								<td>${tc.task.actual} / ${tc.task.total}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${tc.time}" /></td>
								<td>
									<c:choose>
										<c:when test="${tc.task.state == 0}">
											<c:out value="运行中" />
										</c:when>
										<c:when test="${tc.task.state == 2}">
											<c:out value="已结束" />
										</c:when>
										<c:otherwise>
											<c:out value="异常" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(tcList) <= 0}">
						<tr>
							<td colspan="7">无任务</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</body>
</html>