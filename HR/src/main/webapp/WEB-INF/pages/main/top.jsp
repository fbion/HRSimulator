<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path=this.getServletContext().getContextPath();
%>
<!-- Navbar -->
	<div class="navbar" style="background-color:#333">
		<div class="navbar-inner">
			<div class="navbar-container">
				<!-- Navbar Barnd -->
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand"> <small> <img
							src="<%=path %>/assets/img/logo.png" alt="" /> </small> </a>
				</div>
				<!-- /Navbar Barnd -->
				<!-- Sidebar Collapse -->
				<div class="sidebar-collapse" id="sidebar-collapse">
				</div>
				<!-- /Sidebar Collapse -->
				<!-- Account Area and Settings --->
				<div class="navbar-header pull-right">
					<div class="navbar-account">
						<ul class="account-area">
							<li><a class="login-area dropdown-toggle"
								data-toggle="dropdown">
									<section>
										<h2>
											<span class="profile"><span>欢迎您 九州云腾</span>
											</span>
										</h2>
									</section> </a> 
									<!--Login Area Dropdown-->
								<ul
									class="pull-right dropdown-menu  dropdown-login-area" >
									<li class="dropdown-footer" id="logins" style=''><a href="<%=path %>/login.jsp" style=''> 退出 </a></li>
								</ul> <!--/Login Area Dropdown--></li>
							<!-- /Account Area -->


						</ul>
						<!-- Settings -->
						<div class="setting">
						</div>
						<!-- Settings -->
						<!-- Settings -->
					</div>
				</div>
				<!-- /Account Area and Settings -->
			</div>
		</div>
	</div>
	<!-- /Navbar -->