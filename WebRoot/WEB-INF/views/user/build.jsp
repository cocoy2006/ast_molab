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
		<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/bootstrap-filestyle.min.js"></script>
		<!-- Bootstrap datetimepicker -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>resources/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$('#startDate, #endDate').datetimepicker({
				startDate: "+1d",
				autoclose: true,
				minView: 2,
				language: 'zh-CN',
				initialDate: "+1d"
			});
		});
		function openDistrictModal() {
			$("#districtModal").modal();
		}
		function selectDistrict() {
			var selectedDistrict = $(".district:checked");
			var length = selectedDistrict.length;
			if(length > 0) {
				var value = "1";
				selectedDistrict.each(function() {
					value += "," + $(this).val();
				});
				$("#district").val(value).removeAttr("disabled").attr("checked", "checked");
				$("#districtAll").removeAttr("checked");
				$("#districtName").text(selectedDistrict.first().next().text());
				$("#districtLength").text(length);
			}
			$("#districtModal").modal("hide");
		}
		function checkAssets() {
			var state = false;
			$.ajax( {
				url : "<%=basePath %>user/checkAssets/${sessionScope.user.id}",
				async: false,
				data: $("#uploadForm").serialize(),
				dataType : "json",
				success : function(data) {
					switch(data.state) {
						case 1: // success
							state = true;
							break;
						case 2: // assets not enough
							danger("余额不足【预计消费" + data.total + "次，您的余额是" + data.assets + "次】");
							break;
						default: // error happend
							danger(data.message);
							break;
					}
				}
			});
			return state;
		}
		function addPeriod() {
			var totalPercent = 100 - getTotalPercent();
			if(totalPercent <= 0) {
				danger(" 用户占比已满，无法继续添加. ");
				return false;
			}
			var newPeriod = $("#period").clone();
			newPeriod.removeAttr("id");
			newPeriod.children(".hidden").last().removeClass("hidden");
			newPeriod.find("select.percent option").each(function() {
				if(this.value == totalPercent) {
					$(this).attr("selected", "selected");
				}
			});
			$("#periodSection").append(newPeriod);
		}
		function removePeriod(that) {
			$(that).parent().remove();
		}
		function checkPeriod() {
			var periodArray = new Array();
			$(".period").each(function() {
				var startPeriod = parseInt($(this).find(".startPeriod").val());
				var endPeriod = parseInt($(this).find(".endPeriod").val());
				if(startPeriod >= endPeriod) {
					danger(" 结束时间段必须晚于开始时间段. ");
					return false;
				} else {
					var percent = parseInt($(this).find(".percent").val());
					periodArray.push([startPeriod, endPeriod, percent]);	
				}
			});
			periodArray.sort(function(x, y) {
				return x[0] - y[0];
			});
			for(var i = 1; i < periodArray.length; i++) {
				if(periodArray[i][0] < periodArray[i - 1][1]) {
					danger(" 时间段存在交集. ");
					return false;
				}
			}
			return periodArray;
		}
		function getTotalPercent() {
			var totalPercent = 0;
			$(".percent").each(function() {
				totalPercent += parseInt(this.value);
			});
			return totalPercent;
		}
		function upload() {
			var periodArray = checkPeriod();
			if(!periodArray) {
				return false;
			}
			if(getTotalPercent() != 100) {
				danger(" 用户占比总和必须等于100%. ");
				return false;
			}
			if(!checkDate()) {
				danger(" 结束日期不能早于开始日期. ");
				return false;
			}
			if(!checkConversion()) {
				danger(" 转化率不能低于（日、周、月）留存率. ");
				return false;
			}
			if(checkAssets()) {
				var action = "<%=basePath %>task/uploadApp?periodLength=" + periodArray.length;
				for(var i = 0; i < periodArray.length; i++) {
					action += "&startPeriod" + i + "=" + periodArray[i][0] + 
						"&endPeriod" + i + "=" + periodArray[i][1] + "&percent" + i + "=" + periodArray[i][2];
				}
				var pid = $("#pid").val();
				if(pid) {
					action += "&pid=" + pid;
				}
				$("#uploadForm").attr("action", action).submit();
				window.setTimeout(updateProgressbar, 600);
			}
		}
		function checkConversion() {
			return $("#conversion").val() >= $("#dayRetention").val()
				&& $("#conversion").val() >= $("#weekRetention").val()
				&& $("#conversion").val() >= $("#monthRetention").val();
		}
		function checkDate() {
			return $("#startDate").val() <= $("#endDate").val();
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
							window.setTimeout(updateProgressbar, 1600);
						} else {
							danger("网络不稳定，请重试.");
							window.location.reload();
						}
					} else {
						$(".progress-bar").attr("style", "width:" + data.data + "%;").html(data.data + "%");
						if (data.data < 99) {
							window.setTimeout(updateProgressbar, 200);
						} else {
							success("任务创建成功");
							window.setTimeout(function() {
								location = "<%=basePath %>user/success";
							}, 1600);
						}
					}
				}
			});
		}
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
					<ul class="nav navbar-nav navbar-right">
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
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="<%=basePath %>user/main">首页</a><span class="divider"></span></li>
				<li class="active">发起新测试</li>
			</ul>
			<div>
				<form id="uploadForm" class="form-horizontal" name="uploadForm" enctype="multipart/form-data" 
					target="hiddenUploadFrame" method="post">
					<!-- 1/7 起止日期 -->
					<blockquote>
  						<p><i class="glyphicon glyphicon-calendar"></i> 起止日期</p>
					</blockquote>
				  	<div class="form-group">
				    	<label for="startDate" class="col-sm-2 control-label">开始日期</label>
				    	<div class="col-sm-2">
				      		<input type="text" id="startDate" name="startDate" class="form-control" 
				      			data-date-format="yyyy-mm-dd" placeholder="默认明天"/>
				    	</div>
				    	<label for="endDate" class="col-sm-2 control-label">结束日期</label>
				    	<div class="col-sm-2">
				      		<input type="text" id="endDate" name="endDate" class="form-control" 
				      			data-date-format="yyyy-mm-dd" placeholder="默认明天"/>
				    	</div>
				  	</div>
				  	<!-- 2/7 用户 -->
					<blockquote>
  						<p><i class="glyphicon glyphicon-user"></i> 选择用户</p>
					</blockquote>
					<div class="form-group">
				    	<label for="users" class="col-sm-2 control-label">每日新增用户数</label>
				    	<div class="col-sm-2">
				      		<input type="text" id="users" name="users" class="form-control" value="100"/>
				    	</div>
				    	<!-- 转化率 -->
				    	<label for="conversion" class="col-sm-2 control-label">转化率</label>
				    	<div class="col-sm-2">
				    		<select class="form-control" id="conversion" name="conversion">
				      			<option value="0">0%</option>
				      			<option value="10">10%</option>
				      			<option value="20">20%</option>
				      			<option value="30">30%</option>
				      			<option value="40">40%</option>
				      			<option value="50">50%</option>
				      			<option value="60">60%</option>
				      			<option value="70">70%</option>
				      			<option value="80">80%</option>
				      			<option value="90">90%</option>
				      			<option value="100" selected="selected">100%</option>
				      		</select>
				    	</div>
					</div>
					<!-- 3/7 用户分布 -->
					<blockquote>
  						<p><i class="glyphicon glyphicon-user"></i> 用户分布</p>
					</blockquote>
					<div class="form-group">
				    	<label class="col-sm-2 control-label">用户分布</label>
				    	<div class="col-sm-6">
							<label class="radio-inline"> 
								<input type="radio" id="districtAll" name="district" value="0" checked="checked">全国
							</label> 
							<label class="radio-inline disabled"> 
								<input type="radio" id="district" name="district" value="" disabled>
								<span id="districtName">北京</span>等<span id="districtLength">31</span>个省份
							</label>
							<button class="btn btn-link btn-xs" onclick="openDistrictModal()">选择省份</button>
						</div>
					</div>
				  	<!-- 4/7 留存规则 -->
					<blockquote>
  						<p><i class="glyphicon glyphicon-user"></i> 留存规则
  							<small>日留存用户出现在T+1日，周留存在T+7日，月留存在T+30日</small>
  						</p>
					</blockquote>
					<div class="form-group">
				    	<!-- 日留存 -->
				    	<label for="dayRetention" class="col-sm-2 control-label">日留存率</label>
				    	<div class="col-sm-2">
				    		<select class="form-control" id="dayRetention" name="dayRetention">
				      			<option value="0">0%</option>
				      			<option value="10" selected="selected">10%</option>
				      			<option value="20">20%</option>
				      			<option value="30">30%</option>
				      			<option value="40">40%</option>
				      			<option value="50">50%</option>
				      			<option value="60">60%</option>
				      			<option value="70">70%</option>
				      			<option value="80">80%</option>
				      			<option value="90">90%</option>
				      			<option value="100">100%</option>
				      		</select>
				    	</div>
				    	<!-- 周留存 -->
				    	<label for="weekRetention" class="col-sm-2 control-label">周留存率</label>
				    	<div class="col-sm-2">
				    		<select class="form-control" id="weekRetention" name="weekRetention">
				      			<option value="0" selected="selected">0%</option>
				      			<option value="10">10%</option>
				      			<option value="20">20%</option>
				      			<option value="30">30%</option>
				      			<option value="40">40%</option>
				      			<option value="50">50%</option>
				      			<option value="60">60%</option>
				      			<option value="70">70%</option>
				      			<option value="80">80%</option>
				      			<option value="90">90%</option>
				      			<option value="100">100%</option>
				      		</select>
				    	</div>
				    	<!-- 月留存 -->
				    	<label for="monthRetention" class="col-sm-2 control-label">月留存率（30日）</label>
				    	<div class="col-sm-2">
				    		<select class="form-control" id="monthRetention" name="monthRetention">
				      			<option value="0" selected="selected">0%</option>
				      			<option value="10">10%</option>
				      			<option value="20">20%</option>
				      			<option value="30">30%</option>
				      			<option value="40">40%</option>
				      			<option value="50">50%</option>
				      			<option value="60">60%</option>
				      			<option value="70">70%</option>
				      			<option value="80">80%</option>
				      			<option value="90">90%</option>
				      			<option value="100">100%</option>
				      		</select>
				    	</div>
				  	</div>
				  	<!-- 5/7 时间段 -->
				  	<blockquote>
  						<p><i class="glyphicon glyphicon-time"></i> 时间段</p>
					</blockquote>
				  	<div id="periodSection">
				  		<div class="form-group period" id="period">
					    	<label class="col-sm-2 control-label">开始时间段</label>
					    	<div class="col-sm-2">
					      		<select class="form-control startPeriod" onblur="checkPeriod()">
					      			<option value="0" selected="selected">0</option><option value="1">1</option><option value="2">2</option>
					      			<option value="3">3</option><option value="4">4</option><option value="5">5</option>
					      			<option value="6">6</option><option value="7">7</option><option value="8">8</option>
					      			<option value="9">9</option><option value="10">10</option><option value="11">11</option>
					      			<option value="12">12</option><option value="13">13</option><option value="14">14</option>
					      			<option value="15">15</option><option value="16">16</option><option value="17">17</option>
					      			<option value="18">18</option><option value="19">19</option><option value="20">20</option>
					      			<option value="21">21</option><option value="22">22</option><option value="23">23</option>
					      			<option value="24">24</option>
					      		</select>
					    	</div>
					    	<label class="col-sm-2 control-label">结束时间段</label>
					    	<div class="col-sm-2">
					      		<select class="form-control endPeriod" onblur="checkPeriod()">
					      			<option value="0">0</option><option value="1">1</option><option value="2">2</option>
					      			<option value="3">3</option><option value="4">4</option><option value="5">5</option>
					      			<option value="6">6</option><option value="7">7</option><option value="8">8</option>
					      			<option value="9">9</option><option value="10">10</option><option value="11">11</option>
					      			<option value="12">12</option><option value="13">13</option><option value="14">14</option>
					      			<option value="15">15</option><option value="16">16</option><option value="17">17</option>
					      			<option value="18">18</option><option value="19">19</option><option value="20">20</option>
					      			<option value="21">21</option><option value="22">22</option><option value="23">23</option>
					      			<option value="24" selected="selected">24</option>
					      		</select>
					    	</div>
					    	<label for="" class="col-sm-2 control-label">用户占比</label>
					    	<div class="col-sm-2">
					      		<select class="form-control percent" id="" name="">
					      			<option value="10">10%</option><option value="20">20%</option><option value="30">30%</option>
					      			<option value="40">40%</option><option value="50">50%</option><option value="60">60%</option>
					      			<option value="70">70%</option><option value="80">80%</option><option value="90">90%</option>
					      			<option value="100" selected="selected">100%</option>
					      		</select>
					    	</div>
					    	<button class="btn btn-link hidden" onclick="removePeriod(this)"><i class="glyphicon glyphicon-minus-sign"></i>&nbsp;删除</button>
					  	</div>
				  	</div>
				  	<div class="form-group">
				  		<button class="btn btn-link col-sm-2" onclick="addPeriod()"><i class="glyphicon glyphicon-plus-sign"></i>&nbsp;添加</button>
				  	</div>
				  	<!-- 6/7 短信验证码 -->
				  	<blockquote>
  						<p><i class="glyphicon glyphicon-phone"></i> 短信验证码
  							<small>不填写则不支持。如需支持，请先<a href="http://112.74.143.36/add_project.php" target="_blank">申请(http://112.74.143.36/)</a>并填写项目编号PID</small>
  						</p>
					</blockquote>
				  	<div class="form-group">
				    	<label for="pid" class="col-sm-2 control-label">项目编号PID</label>
				    	<div class="col-sm-2">
				      		<input type="text" id="pid" name="pid" class="form-control"/>
				    	</div>
				  	</div>
				  	<!-- 7/7 待测APP -->
				  	<blockquote>
  						<p><i class="glyphicon glyphicon-upload"></i> 待测APP<small>因Android系统限制，APP文件名中不能包含中文、空格等字符</small></p>
					</blockquote>
				  	<div class="form-group">
				    	<label for="file" class="col-sm-2 control-label">上传APP</label>
				    	<div class="col-sm-3">
				      		<input type="file" id="file" name="file" class="filestyle" onchange="upload(this.value)" 
								data-input="false" data-buttonText="上传APP" data-buttonName="btn-success" 
								data-size="lg" data-iconName="glyphicon glyphicon-folder-open">
				    	</div>
				  	</div>
				</form>
				<div class="progress hidden">
					<div class="progress-bar progress-bar-success progress-bar-striped" 
						style="min-width: 2em; width: 2%;">
					</div>
				</div>
				<iframe name="hiddenUploadFrame" id="hiddenUploadFrame" style="display: none;"></iframe>
			</div>
		</div>
		<!-- /.district modal -->
		<div id="districtModal" class="modal fade">
	  		<div class="modal-dialog">
	    		<div class="modal-content">
	      			<div class="modal-header">
	        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        			<h4 class="modal-title">区域选择</h4>
				    </div>
					<div class="modal-body">
	        			<form id="districtForm" class="form-horizontal" name="districtForm" method="post">
	        				<c:if test="${fn:length(districtList) > 0}">
								<c:forEach var="district" items="${districtList}">
									<label class="checkbox-inline"> 
						      			<input type="checkbox" class="district" name="district" value="${district.code }">
						      			<span>${district.name }</span>
									</label> 
								</c:forEach>
							</c:if>
	        			</form>
	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
	        			<button type="button" class="btn btn-primary" onclick="selectDistrict()">确定</button>
	      			</div>
	    		</div><!-- /.modal-content -->
	  		</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</body>
</html>