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
			var userId;
			function openEditModal(id) {
				userId = id;
				$("#userModal").modal();
			}
			function update() {
				
			}
			function openRechargeModal(id) {
				userId = id;
				$("#rechargeModal").modal();
			}
			function recharge() {
				var amount = $("#amount").val();
				if(amount && !isNaN(amount)) {
					$.ajax({
						url: "<%=basePath %>admin/recharge/" + userId,
						type: "POST",
						data: "amount=" + amount,
						dataType: "json",
						success: function(data) {
							switch(data) {
								case 0: // ERROR
									danger("充值失败，请重试");
									break;
								case 1: // SUCCESS
									success("充值成功，当前页面2秒钟后自动刷新");
									setTimeout(function() {
										location.reload();
									}, 2000);
									break;
								default: // ERROR_SYSTEM
									danger("服务器异常，请稍候重试");
									break;
							}
						}
					});
				} else {
					danger("金额不正确");
				}
			}
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
						<li class="active">
							<a href="#">用户管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/meta">厂商和型号管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/district">地域管理</a>
						</li>
						<li>
							<a href="<%=basePath %>admin/server">服务器管理</a>
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
							<th>用户名</th>
							<th>昵称</th>
							<th>邮箱</th>
							<th>联系方式</th>
							<th>资产（次）</th>
							<th>状态</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="user" items="${userList}" varStatus="s">
						<tr>
							<td>${s.count}</td>
							<td>${user.username}</td>
							<td>${user.nickname}</td>
							<td>${user.email}</td>
							<td>${user.phone}</td>
							<td>${user.assets}</td>
							<td>
								<c:choose>
									<c:when test="${user.state == 0}">
										<c:out value="未激活" />
									</c:when>
									<c:when test="${user.state == 1}">
										<c:out value="正常" />
									</c:when>
									<c:otherwise>
										<c:out value="异常" />
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<button class="btn btn-default btn-sm" onclick="openEditModal(${user.id})" style="display: none;">编辑</button>
								<a href="javascript:void(0);" onclick="openRechargeModal(${user.id})">充值</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div id="userModal" class="modal fade">
	  		<div class="modal-dialog">
	    		<div class="modal-content">
	      			<div class="modal-header">
	        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        			<h4 class="modal-title">用户信息</h4>
				    </div>
					<div class="modal-body">
					
	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	        			<button type="button" class="btn btn-primary">确定</button>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<div id="rechargeModal" class="modal fade">
	  		<div class="modal-dialog">
	    		<div class="modal-content">
	      			<div class="modal-header">
	        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        			<h4 class="modal-title">在线充值<small>1元人民币=100次</small></h4>
				    </div>
					<div class="modal-body">
	        			<div id="rechargeForm" class="form-horizontal">
	        				<div class="form-group">
								<label for="amount" class="col-sm-2 control-label">
									充值金额
								</label>
								<div class="col-sm-10">
									<input type="text" name="amount" id="amount" class="form-control">
								</div>
							</div>
	        			</div>
	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	        			<button type="button" class="btn btn-primary" onclick="recharge()">确定</button>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</body>
</html>