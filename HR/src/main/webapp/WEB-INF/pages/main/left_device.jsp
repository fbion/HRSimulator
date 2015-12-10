<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- Page Sidebar -->
<style>
.font_color {
	color: #000;
}
</style>
<div class="page-sidebar" id="sidebar">
	<!-- Page Sidebar Header-->
	<div class="sidebar-header-wrapper">
		<input type="text" class="searchinput" /> <i
			class="searchicon fa fa-search"></i>

	</div>
	<!-- /Page Sidebar Header -->
	<!-- Sidebar Menu -->
	<ul class="nav sidebar-menu">
		<!--Dashboard-->
		<li class="active"><a
			href="<%=request.getContextPath()%>/deptList.jsp"> <i
				class="menu-icon glyphicon glyphicon-home"></i> <span
				class="menu-text"> Dashboard </span> </a>
		</li>
		<li><a href="<%=request.getContextPath()%>/public/user/regist">
				<i class="menu-icon fa fa-user-md"></i> <span class="menu-text">添加组织机构</span>
		</a></li>
		<li><a href="<%=request.getContextPath()%>/deptList.jsp">
				<i class="menu-icon fa fa-user-md"></i> <span class="menu-text">组织机构列表</span>
		</a></li>
		<li><a href="<%=request.getContextPath()%>/hr/public/user/add">
				<i class="menu-icon fa fa-user-md"></i> <span class="menu-text">添加用户</span>
		</a></li>
		<li><a href="<%=request.getContextPath()%>/empList.jsp"> <i
				class="menu-icon fa fa-user-md"></i> <span class="menu-text">用户列表</span>
		</a></li>
		<li><a href="<%=request.getContextPath()%>/userBecome.jsp"> <i
				class="menu-icon fa fa-user-md"></i> <span class="menu-text">员工转正</span>
		</a></li>
	</ul>
	<!-- /Sidebar Menu -->
</div>
<!-- /Page Sidebar -->
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script
	src="<%=request.getContextPath()%>/assets/js/datatable/left_tabs.js"></script>