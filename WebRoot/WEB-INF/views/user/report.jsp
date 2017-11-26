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
		
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/dhtmlxChart_v413/dhtmlxchart.css">
		<script type="text/javascript" src="<%=basePath %>resources/dhtmlxChart_v413/dhtmlxchart.js"></script>
		
		<script type="text/javascript">
		$(document).ready(function() {
			var data = [
		        <c:forEach var="stc" items="${tc.stcList}" varStatus="s">
					{pass : ${stc.st.pass }, total : ${stc.st.total }, time : '<fmt:formatDate pattern="yyyy-MM-dd" value="${stc.startdate }" />'},
				</c:forEach>
			];
			var chart =  new dhtmlXChart({
				view: "bar",
				container: "chart",
				value: "#pass#",
				color: "#a7ee70",
				label: " 通过#pass#/#total#个 ",
		        xAxis: {
			    	title:" 日期 ",
			    	template:"#time#"
				},
	            yAxis: {
			     	title:" 总任务数 / 通过任务数 "
		        }
			});
			chart.addSeries({
				view: "line",
				value: "#total#",
				label: "",
				tooltip:{
					template:" 总共#total#个 "
				}
			});
			chart.parse(data, "json");
		});
//		upload actions
		var taskId, actionType;
		function openUploadModal(id, type) {
			taskId = id;
			actionType = type;
			$("input[name='actions']").each(function() {
				$(this).val("");
			});
			$("#uploadModal").modal();
		}
		function upload() {
			var found = false;
			$("input[name='actions']").each(function() {
				if($(this).val()) {
					found = true;
				}
			});
			if(found) {
				var action = "<%=basePath %>task/uploadAction?taskId=" + taskId + "&actionType=" + actionType;
				$("#uploadForm").attr("action", action).submit();
				window.setTimeout(updateProgressbar, 600);
			} else {
				danger(" 脚本不能为空. ");
			}
		}
		var attempts = 0;
		function updateProgressbar() {
			$.ajax( {
				url : "<%=basePath %>task/percentDone",
				async : false,
				dataType : "json",
				success : function(data) {
					if (data == null || data == "" || data.data == -3) {
						if (attempts++ < 3) {
							setTimeout(updateProgressbar, 1600);
						} else {
							danger(" 网络不稳定，请稍后重试. ");
							location.reload();
						}
					} else {
						$(".progress-bar").attr("style", "width:" + data.data + "%;").html(data.data + "%");
						if (data.data < 99) {
							setTimeout(updateProgressbar, 200);
						} else {
							success(" 脚本上传成功. ");
							setTimeout(function() {
								location = "<%=basePath %>report/" + taskId;
							}, 1600);
						}
					}
				}
			});
		}
		function offline(id) {
			if(confirm("下线后将无法使用，确定继续？")) {
				$.ajax({
					url : "<%=basePath %>action/offline/" + id,
					dataType : "json",
					success : function(data) {
						success(" 脚本下线成功. ");
						setTimeout(function() {
							location.reload();
						}, 1600);
					}
				});
			}
		}
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
							<a href="#">首页</a>
						</li>
					</ul>
				</nav>
			</div>
		</nav>
		<div class="container">
			<!-- basic information -->
			<div class="panel panel-default">
		  		<div class="panel-heading">
		    		<h3 class="panel-title">${tc.application.name}
		    			<small>
		    				<c:choose>
								<c:when test="${tc.task.state == 0}">
									<span class="label label-info"><c:out value="运行中" /></span>
								</c:when>
								<c:when test="${tc.task.state == 2}">
									<span class="label label-success"><c:out value="已结束" /></span>
								</c:when>
								<c:otherwise>
									<span class="label label-danger"><c:out value="异常" /></span>
								</c:otherwise>
							</c:choose>
		    			</small>
		    			<span class="pull-right">
			    			<fmt:formatDate pattern="yyyy年MM月dd日" value="${tc.startdate}" />~
			    			<fmt:formatDate pattern="yyyy年MM月dd日" value="${tc.enddate}" />
		    			</span>
		    		</h3>
		  		</div>
		  		<div class="panel-body">
		    		<table class="table table-bordered">
						<tr>
							<th>PID</th>
							<th>每日新增用户数</th>
							<th>注册转化率</th>
							<th>日留存率</th>
							<th>周留存率</th>
							<th>月留存率</th>
							<th>测试进度</th>
							<th>提交时间</th>
						</tr>
						<tr>
							<td>${tc.application.pid}</td>
							<td>${tc.task.users}</td>
							<td>${tc.task.conversion}%</td>
							<td>${tc.task.dayRetention}%</td>
							<td>${tc.task.weekRetention}%</td>
							<td>${tc.task.monthRetention}%</td>
							<td>${tc.task.actual} / ${tc.task.total}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tc.time}" /></td>
						</tr>
						<tr>
							<th colspan="8">包名</th>
						</tr>
						<tr>
							<td colspan="8">${tc.application.startactivity}</td>
						</tr>
						<!-- 1/3 静默新用户脚本 -->
						<c:if test="${tc.task.conversion < 100 }">
							<tr>
								<th colspan="8">静默新用户脚本
									<button class="btn btn-link btn-xs" 
										onclick="openUploadModal(${tc.task.id}, 0)">上传</button>
								</th>
							</tr>
							<tr>
								<td colspan="8">
									<c:if test="${fn:length(tc.actionSilentList) > 0}">
										<c:forEach var="action" items="${tc.actionSilentList}" varStatus="s">
											<p>
												<span class="label label-default">${s.count }</span>
												<c:if test="${action.state == 0}">
													<span class="label label-danger"><c:out value="已下线" /></span>
												</c:if>
												<c:if test="${action.state == 1}">
													<span class="label label-success"><c:out value="正常" /></span>
												</c:if>
												${action.content }
												<c:if test="${action.state == 1}">
													<button class="btn btn-link btn-xs" 
														onclick="offline(${action.id})">【下线】</button>
												</c:if>
											</p>
										</c:forEach>
									</c:if>
									<c:if test="${fn:length(tc.actionSilentList) <= 0}">
									无
									</c:if>
								</td>
							</tr>
						</c:if>
						<!-- 2/3 活跃新用户脚本 -->
						<tr>
							<th colspan="8">活跃新用户脚本
								<button class="btn btn-link btn-xs" 
									onclick="openUploadModal(${tc.task.id}, 1)">上传</button>
							</th>
						</tr>
						<tr>
							<td colspan="8">
								<c:if test="${fn:length(tc.actionActiveList) > 0}">
									<c:forEach var="action" items="${tc.actionActiveList}" varStatus="s">
										<p>
											<span class="label label-default">${s.count }</span>
											<c:if test="${action.state == 0}">
												<span class="label label-danger"><c:out value="已下线" /></span>
											</c:if>
											<c:if test="${action.state == 1}">
												<span class="label label-success"><c:out value="正常" /></span>
											</c:if>
											${action.content }
											<c:if test="${action.state == 1}">
												<button class="btn btn-link btn-xs" 
													onclick="offline(${action.id})">【下线】</button>
											</c:if>
										</p>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(tc.actionActiveList) <= 0}">
								无
								</c:if>
							</td>
						</tr>
						<!-- 3/3 留存用户脚本 -->
						<tr>
							<th colspan="8">留存用户脚本
								<button class="btn btn-link btn-xs" 
									onclick="openUploadModal(${tc.task.id}, 2)">上传</button>
							</th>
						</tr>
						<tr>
							<td colspan="8">
								<c:if test="${fn:length(tc.actionRetentionList) > 0}">
									<c:forEach var="action" items="${tc.actionRetentionList}" varStatus="s">
										<p>
											<span class="label label-default">${s.count }</span>
											<c:if test="${action.state == 0}">
												<span class="label label-danger"><c:out value="已下线" /></span>
											</c:if>
											<c:if test="${action.state == 1}">
												<span class="label label-success"><c:out value="正常" /></span>
											</c:if>
											${action.content }
											<c:if test="${action.state == 1}">
												<button class="btn btn-link btn-xs" 
													onclick="offline(${action.id})">【下线】</button>
											</c:if>
										</p>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(tc.actionRetentionList) <= 0}">
								无
								</c:if>
							</td>
						</tr>
					</table>
		  		</div>
			</div>
			<div id="chart" style="height:330px;"></div>
		</div>
		<!-- /.upload modal -->
		<div id="uploadModal" class="modal fade">
	  		<div class="modal-dialog">
	    		<div class="modal-content">
	      			<div class="modal-header">
	        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        			<h4 class="modal-title">上传脚本文件</h4>
				    </div>
					<div class="modal-body">
	        			<form id="uploadForm" class="form-horizontal" name="uploadForm" 
	        				enctype="multipart/form-data" target="hiddenUploadFrame" method="post">
	        				<div class="form-group">
								<label class="col-sm-3 control-label">
									脚本1
								</label>
								<div class="col-sm-9">
									<input type="file" name="actions" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									脚本2
								</label>
								<div class="col-sm-9">
									<input type="file" name="actions" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									脚本3
								</label>
								<div class="col-sm-9">
									<input type="file" name="actions" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									脚本4
								</label>
								<div class="col-sm-9">
									<input type="file" name="actions" class="form-control">
								</div>
							</div>
	        			</form>
	        			<div class="progress ">
							<div class="progress-bar progress-bar-success progress-bar-striped" 
								style="min-width: 1em; width: 1%;">
							</div>
						</div>
	        			<iframe name="hiddenUploadFrame" id="hiddenUploadFrame" style="display: none;"></iframe>
	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	        			<button type="button" class="btn btn-primary" onclick="upload()">确定</button>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</body>
</html>