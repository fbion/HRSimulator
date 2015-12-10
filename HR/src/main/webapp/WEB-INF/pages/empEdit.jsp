<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
									<span class="widget-caption"><i class="menu-icon fa fa-user-md"></i>新增机构</span>
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
                                            <div class="form-title" style="color:#a4a4a4; font-size:24px; font-family:'黑体';border:0px;" align="center">编辑机构</div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">员工姓名</label>
                                                <div class="col-lg-7">
                                                	<input type="hidden" id="id" value="${emp.id}">
                                                    <input type="text" class="form-control" value="${emp.username}" id="username"  placeholder="请输入机构名称"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">员工编号</label>
                                                <div class="col-lg-7">
                                                    <input type="text" class="form-control"value="${emp.enumber}" id="enumber"  placeholder="请输入机构负责人"/>
                                                </div>
                                            </div>
                                             <div class="form-group">
                                                <label class="col-lg-3 control-label">入职时间</label>
                                                	<div class="col-lg-7">
											<input class="form-control" id="edate" value="${emp.edate}"type="text">
									</div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-lg-3 control-label">员工状态</label>
                                                <div class="col-lg-7">
                                                    <div class="radio">
						                            <label>
						                            	<input type="hidden" id="hidden_state" value="${emp.state}">
						                               <input name="state" type="radio" value="0" class="colored-blueberry">
						                                <span class="text"> 启用</span>
						                             </label>
						                             <label>
						                                <input name="state" type="radio" value="1" class="colored-blueberry">
						                                 <span class="text"> 禁止</span>
						                             </label>
					                      		</div>
                                                </div>
                                            </div>
                                             <div class="form-group">
                                                <label class="col-lg-3 control-label">所属机构</label>
                                                <div class="col-lg-7">
                                                <input type="hidden" id="chi_dept" value="${emp.dept}">
                                                	<select class="form-control" id="dept">
													 	<option value="0">请选择机构</option>
													 	<c:forEach items="${dept }" var="de">
													 		<option name="dept_check" value="${de.id}">${de.text }</option>
													 	</c:forEach>
													 </select>
                                                </div>
                                            </div>
                                             <div class="form-group">
                                              	<label class="col-lg-3 control-label">推送UD</label>
												<div class="radio">
						                            <label>
						                            <input type="hidden" id="ispush_hidden"value="${emp.ispush}">
						                               <input name="ispush" type="radio" value="0" class="colored-blueberry">
						                                <span class="text"> 是</span>
						                             </label>
						                             <label>
						                                <input name="ispush" type="radio" value="1" class="colored-blueberry">
						                                 <span class="text"> 否</span>
						                             </label>
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
       var sex = $("#hidden_state").val();
		if(sex==0) {
			$("input[name='state']").eq(0).prop("checked",true);
		}else {
			$("input[name='state']").eq(1).prop("checked",true);
		}
		var push = $("#ispush_hidden").val();
		if(push==0) {
			$("input[name='ispush']").eq(0).prop("checked",true);
		}else {
			$("input[name='ispush']").eq(1).prop("checked",true);
		}
		var d = $("#chi_dept").val();
		$("#dept").val(d);
 	function getData(){
 		var did = $("#dept").find("option:selected").val();
  		return {
  			id:$("#id").val().trim(),
  			 username:$("#username").val().trim(),
		  	state:$("input[name='state']:checked").val(),
		  	 enumber:$("#enumber").val().trim(),
		  	 dept:did,
		  	 ispush:$("input[name='ispush']:checked").val(),
		  	 edate:$("#edate").val().trim()
  		};
  	}
  	function submitEdit(){
  		$.ajax({
  			url:"${pageContext.request.contextPath}/hr/user/push/add",
  			type:"POST",
  			data:JSON.stringify(getData()),
  			dateType:"json",
  			contentType:"application/json;charset=utf-8",
  			success:function(strData){
  				alert(strData.detail);
  				window.parent.location.reload();
  			},error:function(strData){
  				alert(strData.detail);
  				window.parent.location.reload();
  			}
  			
  		});
 }
  </script>
</body>

</html>

