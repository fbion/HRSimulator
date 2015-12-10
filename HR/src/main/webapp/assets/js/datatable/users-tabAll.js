 window.onload=function(){
	$("#app_allok").bind("click",function(){ 
		var xg = document.getElementById("to_detail").contentDocument.getElementById("is_edit");
		if($(xg).val() == null)
		{
//			var iframe = document.getElementById("to_detail").contentDocument.getElementById("userforgroupTable");
//			var aa  = $(iframe).find("input[type=checkbox]");
//			var join = [];
//			var unjoin = [];
//			for ( var i = 0; i < aa.length; i++) {
//				if (aa[i].checked) {
//					var roleId = aa[i].value;
//					join.push(roleId);
//				}if(aa[i].checked==false){
//					unjoin.push(aa[i].value);
//				} 
//			}
//			var beanUserRole = {roleList:join,unList:unjoin};
//			$.ajax({
//			url: getRootPath()+"/userGroup/addUserForGroup",
//			type : "POST",
//			contentType : "application/json; charset=utf-8",
//			dataType : "json",
//			data:JSON.stringify(beanUserRole),
//			success : function(strData) {
//				if (strData.success) {
//					alert("保存成功!");
//					window.parent.location.href=getRootPath()+"/user/userList";
//				} else {
//					alert("保存失败！");
//				}
//			},
//			error : function(strData, textStatus, errorThrown) {
//				if (errorThrown != null) {
//					alert("<spring:message code='common.error' />: "
//							+ errorThrown);
//				} else {
//					alert("<spring:message code='common.error' />: "
//							+ JSON.stringify(strData));
//				}
//			}
//	
//		  });
//		      $.ajax({
//		            url:"<%=request.getContextPath()%>/user/v1/user/edit",
//		            type:"POST",
//		            data:JSON.stringify(model_userList),
//		            contentType: "application/json; charset=utf-8",
//		            dataType: "json",
//		            success:function(strData){
//			            if(strData.success){
//				         // window.parent.location.href="<%=request.getContextPath()%>/user/userList";
//								}
//							},
//							error : function(strData) {
//								alert("Error:"
//										+ JSON.stringify(strData));
//							}
//						});
			//触发详情按钮事件
//			var click_detal = document.getElementById("to_detail").contentDocument.getElementById("userbtnok_detail");
//			$(click_detal).trigger("click");
			var model_userList = document.getElementById("to_detail").contentWindow.saveDetail();
			var accounts = document.getElementById("to_detail").contentWindow.updateAccount();
			var o2 = {};
			var i,o = {};
			for (i in model_userList) {
			o[i]= model_userList[i];
			 }
			for (i in accounts) {
			o[i]= accounts[i];
			 }
			   $.ajax({
		            url:getRootPath()+"/user/v1/user/edit",
		            type:"POST",
		            data:JSON.stringify(o),
		            contentType: "application/json; charset=utf-8",
		            dataType: "json",
		            success:function(strData){
			            if(strData.success){
			            	 	alert(strData.errorMessage);
					            window.parent.location.href=getRootPath()+"/user/userList";
					            }else{
					            	alert(strData.errorMessage);
					            }
							},
							error : function(strData) {
								alert("Error:"
										+ JSON.stringify(strData));
							}
						});
}
	if($(xg).val() == "ROLE"){
		//触发分配角色事件
		var click_role = document.getElementById("to_detail").contentDocument.getElementById("userfor_role");
		$(click_role).trigger("click");
		
	}
	
});//点击事件结束
	$("#Close_btns").bind("click",function(){
	            $("#myModal0").modal('hide'); 
	});
	//新增用户
	$("#user_btnok").bind("click",function(){ //.contentDocument.getElementById("userbtnok");
		//var click_role = document.getElementById("toDetailRole").contentDocument.getElementById("userbtnok") ;
		//$(click_role).trigger("click");
		var model_userList = document.getElementById("toDetailRole").contentWindow.userOK() ;
		if(model_userList!=false){
			var account = document.getElementById("toDetailRolePerm").contentWindow.userOK();
			if(account!=false){
				//合并2个json
				var o2 = {};
				var i,o = {};
				for (i in model_userList) {
				o[i]= model_userList[i];
				 }
				for (i in account) {
				o[i]= account[i];
				 }
				 $.ajax({
			            url:getRootPath()+"/user/v1/user/add",
			            type:"POST",
			            data:JSON.stringify(o),
			            contentType: "application/json; charset=utf-8",
			            dataType: "json",
			            success:function(strData){
				            if(strData.success){
					            alert(strData.errorMessage);
					            window.parent.location.href=getRootPath()+"/user/userList";
					            }else{
					            	alert(strData.errorMessage);
					            }
				            }
			        });
			}
		}
	});
	
 }
 function getRootPath(){
	    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	    var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPaht=curWwwPath.substring(0,pos);
	    //获取带"/"的项目名，如：/uimcardprj
	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    return(localhostPaht+projectName);
	} 