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
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="<%=basePath %>">@AST</a>
				</div>
				<nav class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="#">首页</a>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
			        	<li>
			        		<button class="btn btn-success navbar-btn" onclick="javascript:window.location='build'">
								<i class="glyphicon glyphicon-plus"></i>&nbsp;发起新测试
							</button>
			        	</li>
			        	<li>
							<a href="<%=basePath %>user/info">${sessionScope.user.username}</a>
						</li>
			        	<li>
							<a href="<%=basePath %>user/signout"><i class="glyphicon glyphicon-off"></i> 安全退出</a>
						</li>
			      	</ul>
				</nav>
			</div>
		</nav>
		<div class="container" style="padding-top: 80px;">
			<ul class="breadcrumb">
				<li><a href="#">首页</a><span class="divider"></span></li>
				<li class="active">管理中心</li>
			</ul>
			<div>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>序号</th>
							<th>APP名称</th>
							<th>测试进度</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>报告和脚本</th>
							<th>测试状态</th>
							<th>提交时间</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(tcList) > 0}">
							<c:forEach var="tc" items="${tcList}" varStatus="s">
								<tr>
									<td>${s.count}</td>
									<td>${tc.application.name}</td>
									<td>${tc.task.actual} / ${tc.task.total}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${tc.startdate}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${tc.enddate}" /></td>
									<td>
										<a href="<%=basePath %>report/${tc.task.id}" target="_blank">查看</a>
									</td>
									<td>
										<c:choose>
											<c:when test="${tc.task.state == 0}">
												<span class="label label-primary"><c:out value="运行中" /></span>
											</c:when>
											<c:when test="${tc.task.state == 2}">
												<span class="label label-success"><c:out value="已结束" /></span>
											</c:when>
											<c:otherwise>
												<span class="label label-danger"><c:out value="异常" /></span>
											</c:otherwise>
										</c:choose>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tc.time}" /></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${fn:length(tcList) <= 0}">
							<tr>
								<td colspan="8">无任务</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>