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
			function openDialogManufacture(obj, id, name) {
				$("#dialogManufacture h4").html(obj.title);
				$("#dialogManufactureId").val(id);
				$("#dialogManufactureName").val(name);
				$("#dialogManufacture").modal();
			}
			
			function saveOrUpdateManufacture() {
				var id = $("#dialogManufactureId").val();
				var name = $("#dialogManufactureName").val();
				if(!name) {
					danger("厂商名称不能为空");
					return false;
				}
				var exist = false;
				$(".manufactureTitle").each(function() {
					if($(this).text().toLowerCase() == name.toLowerCase()) {
						danger("已存在" + $(this).text() + "，请重新输入");
						exist = true;
					}
				});
				if(exist) {
					return false;
				}
				$.ajax({
					url: "<%=basePath %>manufacture/saveOrUpdate/" + id + "/" + name,
					type: "POST",
					dataType: "json",
					success: function(data) {
						if(data != "" && data == 1) {
							location.reload();				
						} else {
							danger("服务器异常，请稍候重试.");
						}
					}
				});
			}
			
			function deleteManufacture(id) {
				if(confirm("删除厂商将同时删除其下所有型号且无法恢复，确定继续?")) {
					$.ajax({
						url: "<%=basePath %>manufacture/remove/" + id,
						dataType: "json",
						success: function(data) {
							if(data != "" && data == 1) {
								location.reload();				
							} else {
								danger("服务器异常，请稍候重试.");
							}
						}
					});
				}
			}
						
			function openDialogModel(obj, id, manufactureId, name, occupancy) {
				$("#dialogModel h4").html(obj.title);
				$("#dialogModelId").val(id);
				$("#dialogManufactureId").val(manufactureId);
				$("#dialogModelName").val(name);
				$("#dialogModelOccupancy").val(occupancy);
				$("#dialogModel").modal();
			}
			
			function saveOrUpdateModel() {
				var id = $("#dialogModelId").val();
				var manufactureId = $("#dialogManufactureId").val();
				var name = $("#dialogModelName").val();
				var occupancy = $("#dialogModelOccupancy").val();
				// name exist or not
				var exist = false;
				var dialogModelTitles = "." + manufactureId + "_dialogModelTitle";
				$(dialogModelTitles).each(function() {
					if($(this).text().toLowerCase() == name.toLowerCase()) {
						danger("已存在" + $(this).text() + "，请重新输入");
						exist = true;
					}
				});
				if(exist) {
					return false;
				}
				// end
				$.ajax({
					url: "<%=basePath %>model/saveOrUpdate/" + id + "/" + manufactureId + "/" 
						+ name + "/" + occupancy + "/",
					type: "POST",
					dataType: "json",
					success: function(data) {
						if(data != "" && data == 1) {
							location.reload();				
						} else {
							danger("服务器异常，请稍候重试.");
						}
					}
				});
			}
			
			function removeModel(manufactureId) {
				var dataString = $("#" + manufactureId + " form").serialize();
				if(dataString.search("ids") == -1) return false;
				if(confirm("删除后无法恢复，确定继续?")) {
					$.ajax({
						url: "<%=basePath %>model/remove/" + manufactureId,
						type: "POST",
						data: dataString,
						dataType: "json",
						success: function(data) {
							if(data != "" && data == 1) {
								location.reload();				
							} else {
								danger("服务器异常，请稍候重试.");
							}
						}
					});
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
						<li>
							<a href="<%=basePath %>admin/user">用户管理</a>
						</li>
						<li class="active">
							<a href="#">厂商和型号管理</a>
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
			        		<button class="btn btn-default navbar-btn" title="创建厂商"
			        			onclick="openDialogManufacture(this, -1, '')">
								<i class="glyphicon glyphicon-plus"></i>&nbsp;创建厂商
							</button>
			        	</li>
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
			<c:if test="${metas != null}">
				<ul class="nav nav-tabs" role="tablist" style="margin-bottom: 15px;">
					<c:forEach var="manufacture" items="${metas}" varStatus="s">
						<li role="presentation" <c:if test="${s.index == 0}">class="active"</c:if>>
							<a href="#${manufacture.key }" aria-controls="${manufacture.key }" role="tab" data-toggle="tab">
								<span class="manufactureTitle">${manufacture.title }</span>
								(${fn:length(manufacture.children)})
							</a>
						</li>
					</c:forEach>
				</ul>
				<div class="tab-content">
					<c:forEach var="manufacture" items="${metas}" varStatus="s">
						<div role="tabpanel" class="tab-pane <c:if test="${s.index == 0}">active</c:if>" id="${manufacture.key }">
							<div style="margin-bottom: 15px;">
								<div class="btn-group" role="group" aria-label="button group">
									<button type="button" class="btn btn-default" title="编辑厂商"
										onclick="openDialogManufacture(this, ${manufacture.key }, '${manufacture.title }')">
										<i class="glyphicon glyphicon-pencil"></i>编辑厂商
									</button>
									<button type="button" class="btn btn-default" title="删除厂商"
										onclick="deleteManufacture(${manufacture.key })">
										<i class="glyphicon glyphicon-remove"></i>删除厂商
									</button>
								</div>
								<div class="btn-group" role="group" aria-label="button group">
									<button type="button" class="btn btn-default" title="添加型号"
										onclick="openDialogModel(this, -1, ${manufacture.key }, '', '', '', '')">
										<i class="glyphicon glyphicon-plus"></i>添加型号
									</button>
									<button type="button" class="btn btn-default" title="删除型号"
										onclick="removeModel(${manufacture.key })">
										<i class="glyphicon glyphicon-remove"></i>删除型号
									</button>
								</div>
							</div>
							<div class="col-sm-12">
								<c:forEach var="model" items="${manufacture.children}">
									<span class="span2 col-sm-2">
										<input type="checkbox" name="ids" value="${model.key }"/>
										<span class="${manufacture.key }_dialogModelTitle">${model.title }</span>
										(${model.occupancy }%)&nbsp;
										<i class="glyphicon glyphicon-pencil" title="编辑型号" style="cursor: pointer;"
											onclick="openDialogModel(this, ${model.key }, ${model.manufactureId }, 
											'${model.title }', '${model.occupancy }')"></i>
								</span>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
		<!-- 厂商管理 -->
		<div id="dialogManufacture" class="modal fade">
	  		<div class="modal-dialog">
	    		<div class="modal-content">
	      			<div class="modal-header">
	        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        			<h4 class="modal-title">厂商</h4>
				    </div>
					<div class="modal-body">
	        			<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="dialogManufactureName">
									厂商名
								</label>
								<div class="col-sm-9">
									<input type="text" id="dialogManufactureName" name="dialogManufactureName" class="form-control"
										autofocus placeholder="请输入厂商名称" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<input type="hidden" id="dialogManufactureId" value="-1"/>
									<button class="btn btn-primary btn-block" 
										onclick="saveOrUpdateManufacture()">确认</button>
								</div>
							</div>
						</div>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<!-- 型号管理 -->
		<div id="dialogModel" class="modal fade">
	  		<div class="modal-dialog">
	    		<div class="modal-content">
	      			<div class="modal-header">
	        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        			<h4 class="modal-title">型号</h4>
				    </div>
					<div class="modal-body">
	        			<div id="dialogModelForm" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label" for="dialogModelName">
									型号名
								</label>
								<div class="col-sm-9">
									<input type="text" id="dialogModelName" name="dialogModelName" class="form-control"
										autofocus placeholder="请输入型号名称" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" for="dialogModelOccupancy">
									市场占有率
								</label>
								<div class="col-sm-9">
									<input type="text" id="dialogModelOccupancy" name="dialogModelOccupancy" class="form-control"
										placeholder="请输入市场占有率，最多保留2位小数" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<input type="hidden" id="dialogModelId" value="-1"/>
									<button class="btn btn-primary btn-block" 
										onclick="saveOrUpdateModel()">确认</button>
								</div>
							</div>
						</div>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</body>
</html>