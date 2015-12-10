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
									<span class="widget-caption"><i class="menu-icon fa fa-user-md"></i>员工转正</span>
									<div class="widget-buttons">
										<a href="#" data-toggle="maximize"> <i
											class="fa fa-expand"></i> </a> <a href="#" data-toggle="collapse">
											<i class="fa fa-minus"></i> </a> <a href="#"
											data-toggle="dispose"> <i class="fa fa-times"></i> </a>
									</div>
								</div>
						 <div class="widget-body">
                                 <form  action="" class="form-horizontal"  data-bv-message="This value is not valid"
                                              data-bv-feedbackicons-validating="glyphicon glyphicon-refresh" >
                                            <div class="form-title" style="color:#a4a4a4; font-size:24px; font-family:'黑体';border:0px;" align="center">员工转正</div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">员工编号</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control" id="empnumber"  placeholder="请输入员工编号"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-lg-offset-8 col-lg-2">
                                                    <button class="btn" type="button" id="user_btnOk1"  style="background-color:#77c9dd;color: white;float: right; border:0px;font-family:'微软雅黑'">转正</button>
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
       <script type="text/javascript">
$("#user_btnOk1").bind("click",function(){
 	  var number = $("#empnumber").val().trim();
  		$.ajax({
  			url:"${pageContext.request.contextPath}/hr/public/user/become/"+number,
  			type:"GET",
  			dateType:"json",
  			contentType:"application/json;charset=utf-8",
  			success:function(strData){
  				alert(strData.detail);
  			},error:function(strData){
  				alert(strData.detail);
  			}
  			
  		});
  	
});
 
  </script>
</body>

</html>

