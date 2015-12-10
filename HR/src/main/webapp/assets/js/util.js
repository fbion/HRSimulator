
function getRootPath() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

//日期转换
Date.prototype.pattern = function(fmt) {

	var o = {

		"M+" : this.getMonth() + 1, //月份          

		"d+" : this.getDate(), //日          

		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时          

		"H+" : this.getHours(), //小时          

		"m+" : this.getMinutes(), //分          

		"s+" : this.getSeconds(), //秒          

		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度          

		"S" : this.getMilliseconds()
	//毫秒          

	};

	var week = {

		"0" : "\u65e5",

		"1" : "\u4e00",

		"2" : "\u4e8c",

		"3" : "\u4e09",

		"4" : "\u56db",

		"5" : "\u4e94",

		"6" : "\u516d"

	};

	if (/(y+)/.test(fmt)) {

		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));

	}

	if (/(E+)/.test(fmt)) {

		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
								: "\u5468")
								: "")
								+ week[this.getDay() + ""]);

	}

	for ( var k in o) {

		if (new RegExp("(" + k + ")").test(fmt)) {

			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));

		}

	}

	return fmt;

}

/**创建日期：15/10/16
 * 功能：信息提示
 **/

/**成功**/ 
function AlertSuccess(){
	$('#modal-success1').fadeOut(1);
      $('body').append("<div id='modal-success1' class='modal modal-message modal-success' style='display: block; font-size: 14px; color: #333; font-family: '微软雅黑'' aria-hidden='false'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><i class='glyphicon glyphicon-check'></i></div><div class='modal-body'>操作成功!</div><div class='modal-footer'><button type='button' id='AlertSuccess_btn' class='btn btn-success' data-dismiss='modal'>OK</button></div></div></div> </div>").slideDown(500);
      $("#AlertSuccess_btn").bind("click",function(){
    	  window.parent.location.reload();
      });
      setTimeout(function() {
          $('#modal-success1').fadeOut(4000);
      }, 1000);
      setInterval(function() {
   	   window.parent.location.reload();
     }, 4000);
}

/**失败**/ 
function DangerAlert(){
	$('#modal-danger1').fadeOut(1);
    $('body').append("<div id='modal-danger1' class='modal modal-message modal-danger' style='display: block; font-size: 14px; color: #333; font-family: '微软雅黑'' aria-hidden='false'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><i class='glyphicon glyphicon-remove'></i></div><div class='modal-body'>操作失败了!</div><div class='modal-footer'><button type='button' id='DangerAlert_btn' class='btn btn-danger' data-dismiss='modal'>NO</button></div></div></div> </div>").slideDown(500);
    $("#DangerAlert_btn").bind("click",function(){
  	  window.parent.location.reload();
    });
    setTimeout(function() {
        $('#modal-danger1').fadeOut(4000);
    }, 1000);
    setInterval(function() {
    	   window.parent.location.reload();
      }, 4000);
}