function messager(message, style, delay) {
	$(".messager").remove();
	genrate(message, style);
	if(delay > 0) {
		setTimeout(function() {
			$(".messager").remove();
		}, delay);
	}
}
function genrate(message, style) {
	var div = 
		'<div class="messager alert alert-' + style + ' alert-dismissible" role="alert" ' +
				'style="position: fixed; text-align: center; top: 0; width: 100%; z-index: 1999;">' + 
  			'<button type="button" class="close" data-dismiss="alert">' +
  				'<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>' +
  			'</button>' +
  			message +
		'</div>';
	$("body").prepend(div);
}

function success() {
	messager(" 操作成功 ", "success", 2000);
}

function success(msg) {
	messager(msg, "success", 2000);
}

function success(msg, delay) {
	messager(msg, "success", delay);
}

function danger() {
	messager(" 操作失败，请稍后重试 ", "danger", 2000);
}

function danger(msg) {
	messager(msg, "danger", 2000);
}