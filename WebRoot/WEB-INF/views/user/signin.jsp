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
		<!-- JS -->
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>resources/js/md5.js"></script>
		<script type="text/javascript" src="<%=basePath %>resources/js/messager.js"></script>
		<!-- Bootstrap -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript">
		<!--
		$(document).ready(function() {
			$("#l_u").focus();
		});
		$(document).bind("keydown", function(event) {
			var button = $(".defaultButton");
	        if(event.keyCode == 13) {
		        button.click();
		        event.returnValue = false;
	        }
		});
		function signin() {
			var username = $("#l_u").val();
			var password = $("#l_p").val();
			if(!username || !password) {
				danger("用户和密码不能为空");
				return false;
			}
			var md5Password = faultylabs.MD5($("#l_p").val()); 
			$("#l_p").val(md5Password);
			$.ajax({
				url: "<%=basePath %>user/signinAction/",
				type: "POST",
				data: "l_u=" + username + "&l_p=" + md5Password,
				dataType: "json",
				success: function(data) {
					switch(data) {
						case 0: // USER_UNACTIVED
							danger("用户未激活，请登录邮箱激活");
							break;
						case 1: // SUCCESS
							location = "<%=basePath %>user/main";
							break;
						case 2: // USER_NOT_EXIST
							danger("用户不存在");
							$("#l_u").val(""); $("#l_p").val(""); $("#l_u").focus();
							break;
						case 4: // PASSWORD_NOT_MATCH
							danger("密码不正确，请重新输入");
							$("#l_p").val(""); $("#l_p").focus();
							break;
						case 99: // ERROR_SYSTEM
							danger("服务器异常，请稍候重试");
							break;
					}
				}
			});
		}
		//-->
		</script>
  	</head>
  
  	<body>
  		<h1 class="text-center" style="border-bottom: 1px solid #eee; margin: 40px 0 20px; padding-bottom: 9px;">
  			<a href="<%=basePath %>">App Server Testing</a>
  		</h1>
		<div id="loginForm" class="form-horizontal" style="width: 50%; margin: auto;">
			<div class="form-group">
				<label for="l_u" class="col-sm-2 control-label">
					* 用户名
				</label>
				<div class="col-sm-10">
					<input type="text" name="l_u" id="l_u" class="form-control">
				</div>
			</div>
			<div class="form-group">
				<label for="l_p" class="col-sm-2 control-label">
					* 密码
				</label>
				<div class="col-sm-10">
					<input type="password" name="l_p" id="l_p" class="form-control">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button onclick="signin()" class="defaultButton btn btn-success btn-lg">
						<i class="glyphicon glyphicon-user"></i>&nbsp;&nbsp;快速登录
					</button>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="<%=basePath %>user/signup" class="btn btn-default btn-lg btn-block">还没有AST账号？快速注册</a>
				</div>
			</div>
		</div>
  	</body>
</html>
