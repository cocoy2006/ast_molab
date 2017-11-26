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
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>resources/js/md5.js"></script>
		<script type="text/javascript" src="<%=basePath %>resources/js/messager.js"></script>
		<!-- Bootstrap -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript">
		<!--
		$(document).ready(function() {
			signupTipsBind();
			signupValidation();
			$("#r_u").focus();
		});
		$(document).bind("keydown", function(event) {
			var button = $(".defaultButton");
	        if(event.keyCode == 13) {
		        button.click();
		        event.returnValue = false;
	        }
		});
		var signupTips = {
			"r_u" : "支持中文、英文、数字、'_'和'-'",
			"r_p" : "密码由5-20个字符组成",
			"r_p_ag" : "请再次确认您的密码",
			"r_email" : "请输入正确的邮箱"
		};

		function signupTipsBind() {
			var o_username = "";
			for(tips in signupTips) {
				var id = "#" + tips;
				var tipsId = id + "_tips";
				$(tipsId).html(signupTips[tips]).hide();
				$(id).bind("focus", {tipsId: tipsId, tips: tips}, function(event) {
					$(event.data.tipsId).show();
					if("r_u" == event.data.tips) {
						o_username = $(this).val();
					}
				});
				if(tips == "r_u") {
					$(id).bind("blur", function() { // 判断用户名是否可用
						if($(this).val() != "" && $(this).val() != o_username) {
							checkUsername($(this).val());
						}
					});
				}
				$(id).bind("blur", {tipsId: tipsId}, function(event) {
					$(event.data.tipsId).hide();
				});
			}
		}

		function checkUsername(username) {
			$.ajax({
				url: "<%=basePath %>user/check/" + username,
				type: "POST",
				dataType: "json",
				success: function(data) {
					switch(data) {
						case 1: // SUCCESS
							success("用户名可以使用.");
							break;
						case 3: // USER_EXIST
							danger("用户名已经被占用.");
							break;
						default: // ERROR_SYSTEM
							danger("服务器异常，请稍候重试.");
							break;
					}
				}
			});
		}
		function signupValidation() {
			var signupForm = $("#signupForm");
			$("#r_u").val("");
			$("#r_p").val("");
			signupForm.validate({
				rules: {
					r_u: "required", 
					r_p: {required: true, minlength: 5, maxlength: 64},
					r_p_ag: {required: true, equalTo: "#r_p"},
					r_email: {required: true, email: true}
				},
				errorPlacement: function(error, element) {
					element.parent().parent().addClass("has-error");
				},
				submitHandler: function() {
					var signupBtn = $("#signupBtn");
					signupBtn.html("正在提交信息...").attr("disabled", "disabled");
					var md5Password = faultylabs.MD5($("#r_p").val()); 
					$("#r_p").val(md5Password);
					$("#r_p_ag").val(md5Password);
					$.ajax({
						url: "<%=basePath %>user/signupAction",
						type: "POST",
						data: signupForm.serialize(),
						dataType: "json",
						success: function(data) {
							switch(data) {
								case 3: // EXIST
									danger("用户名已经被占用");
									break;
								default: // user id
									success();
									window.setTimeout(function() {
										location = "<%=basePath %>";
									}, 1500);
									break;
							}
							signupBtn.html("<i class='glyphicon glyphicon-pencil'></i>&nbsp;&nbsp;快速注册").removeAttr("disabled");
						}
					});
				}
			});
		}

		function signup() {
			$("#signupForm").submit(function() {return false;});
		}
		//-->
		</script>
  	</head>
  
  	<body>
  		<h1 class="text-center" style="border-bottom: 1px solid #eee; margin: 40px 0 20px; padding-bottom: 9px;">
  			<a href="<%=basePath %>">App Server Testing</a>
  		</h1>
		<form id="signupForm" class="form-horizontal" style="width: 660px; margin: 0 auto;">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="r_u">
					* 用户名
				</label>
				<div class="col-sm-5">
					<input class="form-control" type="text" id="r_u" name="r_u" />
				</div>
				<div class="col-sm-5">
					<label id="r_u_tips">支持中文、英文、数字、'_'和'-'</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="r_p">
					* 密码
				</label>
				<div class="col-sm-5">
					<input class="form-control" type=password id="r_p" name="r_p" />
				</div>
				<div class="col-sm-5">
					<label id="r_p_tips">密码由5-20个字符组成</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="r_p_ag">
					* 确认密码
				</label>
				<div class="col-sm-5">
					<input class="form-control" type="password" id="r_p_ag" name="r_p_ag" />
				</div>
				<div class="col-sm-5">
					<label id="r_p_ag_tips">请再次确认您的密码</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="r_email">
					* 注册邮箱
				</label>
				<div class="col-sm-5">
					<input class="form-control" type="text" id="r_email" name="r_email" />
				</div>
				<div class="col-sm-5">
					<label id="r_email_tips">请输入正确的邮箱</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button onclick="signup()" class="defaultButton btn btn-success btn-lg">
						<i class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;快速注册
					</button>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="<%=basePath %>user/signin" class="btn btn-default btn-lg btn-block">已经拥有AST账号？直接登录</a>
				</div>
			</div>
		</form>
  	</body>
</html>
