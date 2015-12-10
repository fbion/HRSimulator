<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/main/header.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Body -->
<head>
<title>设备列表</title>
<style type="text/css">
.modal-dialog{
	min-width: 300px;
	max-width: 560px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/main/top.jsp" />
	<!-- Main Container -->
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">

			<jsp:include page="/WEB-INF/pages/main/left_device.jsp" />

			<!-- Page Content -->
			<div class="page-content">
				<jsp:include page="/WEB-INF/pages/main/header_button.jsp" />

				<!-- Page Body -->
				<div class="page-body">

					<div class="row">
						<div class="col-xs-12 col-md-12">
							<div class="widget">
								<div class="widget-header ">
									<span class="widget-caption"><i class="menu-icon fa fa-user-md"></i>用户登录</span>
									<div class="widget-buttons">
										<a href="#" data-toggle="maximize"> <i
											class="fa fa-expand"></i> </a> <a href="#" data-toggle="collapse">
											<i class="fa fa-minus"></i> </a> <a href="#"
											data-toggle="dispose"> <i class="fa fa-times"></i> </a>
									</div>
								</div>
						 <div class="widget-body">
                                   <form id="user_detailForm" action="/PolicyServer/application/upload"
										method="post" class="form-horizontal" style=" margin-left:30%;width: 252px;">
										<div class="form-group">
										<div class="col-lg-12">
												<span class="input-icon icon-left"> <input type="text"
													id="username" class="form-control  col-lg-8" name="title"
													placeholder="请输入用户名"/> <i
													class="fa fa-user bg-darkorange white bg-blue-zdy"></i> </span>
										
										</div>
									</div>
									<div class="form-group">
										<div class="col-lg-12">
												<span class="input-icon icon-left"> <input type="password"
													id="password" class="form-control  col-lg-8" name="title"
													placeholder="请输入密码" /> <i
													class="fa fa-user bg-darkorange white bg-blue-zdy"></i> </span>
										
										</div>
									</div>
										<div class="form-group">
											<div class="form-button-div">
												<!-- 勿动 -->
												<input class="btn btn-palegreen" id="login_btnOk"
													type="button" value="登录" /> <input type="hidden"
													class="form-control  col-lg-8"  
													placeholder="账户编号" />
											</div>
										</div>
									</form>
                                </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/main/footer.jsp" />
	<script>
        $(".DTTT").css('display','none'); 
    </script>
</body>
   <script type="text/javascript">
  	$("#login_btnOk").click(function(){
  			var username = $("#username").val();
		  	var password = $("#password").val();
		  	function getData(){
		  		return {
		  			username:$("#username").val(),
		  			password:$("#password").val(),
		  		};
		  	}
  		$.ajax({
  			url:"${pageContext.request.contextPath}/public/login/oauth",
  			type:"POST",
  			data:JSON.stringify(getData()),
  			dateType:"json",
  			contentType:"application/json;charset=utf-8",
  			success:function(strData){
  				if(strData.success){
  					alert("登录成功!");
  				}else{
  					alert("登录失败!");
  				}
  				
  			},error:function(strData){
  				alert("登录失败!");
  			}
  			
  		});
  	
  	});
  </script>
</html>

