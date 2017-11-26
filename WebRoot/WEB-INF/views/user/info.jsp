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
		<nav class="navbar navbar-inverse">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">@AST</a>
				</div>
				<nav class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="<%=basePath %>user/main">首页</a>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
			        	<li>
			        		<button class="btn btn-success navbar-btn" onclick="javascript:window.location='build'">
								<i class="glyphicon glyphicon-plus"></i>&nbsp;发起新测试
							</button>
			        	</li>
			        	<li>
							<a href="#">${sessionScope.user.username}</a>
						</li>
			        	<li>
							<a href="<%=basePath %>user/signout"><i class="glyphicon glyphicon-off"></i> 安全退出</a>
						</li>
			      	</ul>
				</nav>
			</div>
		</nav>
		<div class="container">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-info">
			    	<div class="panel-heading" role="tab" id="headingRecharge">
				      	<h4 class="panel-title">
				      		<a role="button" data-toggle="collapse" data-parent="#accordion" 
				        		href="#collapseRecharge" aria-expanded="true" aria-controls="collapseRecharge">
						    	在线充值
						    </a>
						    <small>剩余测试次数：${sessionScope.user.assets }</small>
				      	</h4>
				    </div>
			    	<div id="collapseRecharge" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingRecharge">
			      		<div class="panel-body">
			        		<a href="https://www.taobao.com/" target="_blank">1元人民币100次</a>
			      		</div>
			    	</div>
			  	</div>
			  	<div class="panel panel-info">
			    	<div class="panel-heading" role="tab" id="headingPwd">
				      	<h4 class="panel-title">
				        	<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" 
				        		href="#collapsePwd" aria-expanded="false" aria-controls="collapsePwd">
						    	密码修改
						    </a>
				      	</h4>
				    </div>
			    	<div id="collapsePwd" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingPwd">
			      		<div class="panel-body">
			        		
			      		</div>
			    	</div>
			  	</div>
			</div>
		</div>
	</body>
</html>