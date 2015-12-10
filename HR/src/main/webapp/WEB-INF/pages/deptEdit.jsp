<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Body -->
<head>
<title>设备列表</title>
<style type="text/css">
.modal-dialog{
	min-width: 300px;
	max-width: 760px;
}
</style>
</head>
<body>
	<!-- Main Container -->
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">


			<!-- Page Content -->
			<div class="page-content">

				<!-- Page Body -->
				<div class="page-body">

					<div class="row">
						<div class="col-xs-12 col-md-12">
							<div class="widget">
								<div class="widget-header ">
									<span class="widget-caption"><i class="menu-icon fa fa-user-md"></i>机构修改</span>
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
                                            <div class="form-title" style="color:#a4a4a4; font-size:24px; font-family:'黑体';border:0px;" align="center">用户注册</div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">机构名称1</label>
                                                <div class="col-lg-7">
                                                <input type="hidden" class="form-control" id="id" value="${dept.id }"  placeholder="请输入机构名称"/>
                                                    <input type="text" class="form-control" id="text" value="${dept.text }"  placeholder="请输入机构名称"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">机构负责人</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control" id="leader"value="${dept.leader }"  placeholder="请输入机构负责人"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">负责人电话</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control" id="telephone" value="${dept.telephone }" placeholder="请输入负责人电话"/>
                                                </div>
                                            </div>
                                             <div class="form-group">
                                                <label class="col-lg-3 control-label">负责人工号</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control" id="leaderCode"value="${dept.leaderCode }"  placeholder="请输入负责人工号"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">传真</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control" id="fax"value="${dept.fax }" placeholder="请输入传真"/>
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
		<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/assets/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/assets/js/slimscroll/jquery.slimscroll.min.js"></script>
	<!--Beyond Scripts-->
	<script src="<%=request.getContextPath() %>/assets/js/beyond.js"></script>
	<script>
        $(".DTTT").css('display','none'); 
    </script>
       <script type="text/javascript">
 	function getData(){
  		return {
  			id:$("#id").val().trim(),
  			 text:$("#text").val().trim(),
		  	 leader:$("#leader").val().trim(),
		  	 telephone:$("#telephone").val().trim(),
		  	 leaderCode:$("#leaderCode").val().trim(),
		  	 fax:$("#fax").val().trim()
  		};
  	}
function submitEdit(){
  		$.ajax({
  			url:"${pageContext.request.contextPath}/hr/user/add",
  			type:"POST",
  			data:JSON.stringify(getData()),
  			dateType:"json",
  			contentType:"application/json;charset=utf-8",
  			success:function(strData){
  				alert(strData.detail);
  			},error:function(strData){
  				alert(strData.detail);
  				
  			}
  		});
  	}
 
  </script>
</body>

</html>

