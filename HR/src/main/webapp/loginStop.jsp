    <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<!--Head-->
<head>
    <meta charset="utf-8" />
    <title>Login Page</title>

    <meta name="description" content="login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


    <!--Basic Styles-->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="assets/css/font-awesome.min.css" rel="stylesheet" />

    <!--Beyond styles-->
    <link id="beyond-link" href="assets/css/beyond.min.css" rel="stylesheet" />



    <!-- zwq-css -->
    <link href="assets/demo/css/login.css" rel="stylesheet" />
</head>
<!--Head Ends-->
<!--Body-->
<body>
<div class="login-conter" id="login-conter">
    <div class="">
        <div class="page-body">
            <div class="row">
                <div class="col-lg-12 col-sm-12 col-xs-12">
                    <div class="row">
                        <div class="col-lg-12 col-sm-12 col-xs-12">
                            <div class="widget"   style="box-shadow:0px 0px 15px rgba(0,0,0,.6);">
                                <div class="widget-header bordered-bottom">
                                    <span class="widget-caption" style="font-size:30px; height:60px; width:100%;">
                                    	 <a href="" class="button-twitter">
                                        <img alt="" src="${pageContext.request.contextPath}/assets/img/loginLogo3.png" width="35" height="25" style="margin-top:-10px;">
                                    </a>
                                    </span>
                                </div>
                                
                                <div class="widget-body">
                                    <div>
                                        <form id="Form" method="POST" action="" class="form-horizontal"  data-bv-message="This value is not valid"
                                              data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
                                              data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
                                              data-bv-feedbackicons-validating="glyphicon glyphicon-refresh" >
                                            <div class="form-title" style="color:#a4a4a4; font-size:24px; font-family:'黑体';border:0px;" align="center">登&nbsp;录</div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">用户名</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control" value="admin" id="username" name="username" placeholder="请输入邮箱"  />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">密码</label>
                                                <div class="col-lg-7">
                                                    <input type="password" class="form-control" value="sso365" id="password" name="password" placeholder="请输入密码"  />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-offset-3 col-lg-4"><label>
                                                    <input type="checkbox">
                                                    <span class="text">记住我</span>
                                                    <a href="${pageContext.request.contextPath}/userRegist.jsp">点我注册</a>
                                                </label></label>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-lg-offset-8 col-lg-2">
                                                    <button class="btn" type="submit" id="login_btnOk"  style="background-color:#77c9dd;color: white;float: right; border:0px;font-family:'微软雅黑'">登录</button>
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
    </div>
</div>

<!--Basic Scripts-->
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>

<!--Page Related Scripts-->
<!--form-check-->
</body>
<!--Body Ends-->
<script>
//         $("#Form").bootstrapValidator();
  	$("#login_btnOk").click(function(){
  			var username = $("#username").val();
		  	var password = $("#password").val();
		//   	leftTabs(1,2);
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
  				if(strData.success = true){
  					alert("登录成功!");
  					window.location.href="${pageContext.request.contextPath}/public/user/userList";
  				}else{
  					alert("登录失败!");
  				}
  				
  			},error:function(strData){
  				alert(strData.errorMessage);
  			}
  			
  		});
  	
  	});
</script>
</html>
