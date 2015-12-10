<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
    body{ background-color:#282828; color: white; font-family: "微软雅黑"; }
    h2{text-align:center;}
    .form_pz{ margin: 3%;}
   	.cont_top{ width: 1000px; height: 380px; background-color: #000; margin: 0px auto;} 
    .cont_text{ background-color:#3d3d3d;  width: 1000px; margin: -20px auto;}
    .con_text_title{ width: 800px; height: 340px; margin: 20px auto; letter-spacing:3px;}
    .con_text_title_font{ width: 600px; margin: 30px auto 0px;; font-size: 20px;}
    h2{ padding-top:65px;  margin: 20px auto;}
    p{word-wrap:break-word;
        word-break:break-all; text-indent:2em; line-height: 30px; }
    .cont_bottom{ float: right; margin:30px 80px;}
    span{ height: 10px; width: 800px;}
    .radio{ width: 400px; margin: 20px 0px;}
    
    .nav{ height: 300px; width: 950px; margin-top:20px; padding: 50px;}
        .nav ul li{  float: left; list-style: none;  text-align: center;}
       .div_title{ background-color:#282828; width: 150px; height: 205px; line-height: 205px;  margin-right: 10px; padding-top:40px;}
        .div_title:hover{  height: 215px; margin-top: -10px;}
        .nav ul li span{ top:30px;}
</style>
</head>
<script type="text/javascript">
	function subTheFile(){
		document.form.submit();
	}
</script>
<body>
<form action="${pageContext.request.contextPath}/fileUpload/fileToIdp" method="post" enctype="multipart/form-data" name="form">
<div class="form_pz">
    <div class="container">
<!--             <div class="cont_top"> -->
            
<!--              	<h2>CSV文件录入</h2> -->
<!--                 <div class="nav"> -->
<!--                 <ul> -->
<!--                     <li style=""> -->
<!--                         <div class="div_title" > -->
<!--                             <span>1.用户供应者</span> -->
<!--                         </div> -->
<!--                     </li> -->
<!--                     <li style=""> -->
<!--                         <div class="div_title"> -->
<!--                             <span>2.IDP配置信息</span> -->
<!--                         </div> -->
<!--                     </li> -->
<!--                     <li style=" "> -->
<!--                        <div  class="div_title" > -->
<!--                         <span style="">3.录入方式选择</span> -->
<!--                         </div> -->
<!--                     </li> -->
<!--                     <li> -->
<!--                             <div class="div_title"  style="height: 102px;line-height: 100px;background-color: #37b500; "> -->
<!--                                 <span>4-1.CSV文件录入</span> -->
<!--                             </div> -->
<!--                         <div  class="div_title" style="height: 101px;line-height: 100px; margin-top:2px;"> -->
<!--                             <span>4-2.AD LADP页面</span> -->
<!--                         </div> -->
<!--                     </li> -->
<!--                     <li> -->

<!--                         <div  class="div_title"  style="height: 205px;"> -->
<!--                             <span style="height: 100px;">&nbsp;</span> -->
<!--                             <span style="height: 100px;line-height: 102px; ">5.AD录入</span> -->
<!--                         </div> -->
<!--                     </li> -->
<!--                 </ul> -->
<!--             </div> -->
<!--         </div> -->
        <div class="cont_text">
            <div class="con_text_title">
         
                <div class="con_text_title_font" style="margin-top:-10px;">
                 <p style="height:50px;"></p>
                <div class="radio">
                        <label>
                            选择本地的CSV表格<input type="file" id="exampleInputFile" value="" name="file">
                        </label>
                    </div>
                </div>
                <div class="cont_bottom">
                    <a class="btn btn-primary btn-primary" href="${pageContext.request.contextPath}/admin/choose.do" type="submit">上一步</a>
                    <a  class="btn btn-danger btn-primary" onclick="subTheFile()" type="submit">开始录入</a >
                    <span>&nbsp;</span>
                </div>
               <span>&nbsp;</span>
                <span>&nbsp;</span><span>&nbsp;</span>
            </div>
         </div>
        </div>
</div>
</form>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>