<%@page import="models.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Quản lý người dùng</h2>
			</div>
		</div>
		<!-- /. ROW  -->
		<%
			String msg = request.getParameter("msg");
			if ("error".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Có lỗi khi thêm</span>");
			}
			if ("success".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Thêm người dùng thành công</span>");
			}
			if ("errorid".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">ID không tồn tại</span>");
			}
			if ("editsuccess".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Sửa thông tin thành công</span>");
			}
			if ("Err".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Không có quyền thêm</span>");
			}
		%>
		<hr />
		<div class="row">
			<div class="col-md-12">
				<!-- Advanced Tables -->
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="table-responsive">
							<div class="row">
									
								<div class="col-sm-6">
								<%
											if ("admin".equals(userLogin.getUsername())) {
										%>
									<a href="<%=request.getContextPath()%>/userAddController"
										class="btn btn-success btn-md">Thêm</a>
										<%} %>
								</div>
								
								<div class="col-sm-6" style="text-align: right;">
									<form method="post" action="">
										<input type="submit"
											class="btn btn-warning btn-sm" style="float: right" /> <input
											type="search" name="search"  class="form-control input-sm"
											placeholder="Nhập tên bài hát"
											style="float: right; width: 300px;" />
										<div style="clear: both"></div>
									</form>
									<br />
								</div>
							</div>
							<%
								if ("deletesuccess".equals(msg)) {
							%>
							<div class="alert alert-success" role="alert">Xóa người
								dùng thành công</div>
							<%
								}
								if ("deleterror".equals(msg)) {
									out.print(
											"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Xóa không thành công</span>");
								}
							%>
							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>ID</th>
										<th>Tên tài khoản</th>
										<th>Tên người dùng</th>
										<th width="160px">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<%
									int numberUser =(Integer)request.getAttribute("numberUser");
									int numberOfPage =(Integer)request.getAttribute("numberOfPages");
									int currentPage =(Integer)request.getAttribute("currentPage");
										ArrayList<User> users = (ArrayList<User>) request.getAttribute("user");
										if (users != null && users.size() > 0) {
											for (User item : users) {
									%>

									<tr>
										<td><%=item.getId()%></td>
										<td class="center"><%=item.getUsername()%></td>
										<td class="center"><%=item.getFullname()%></td>
										<%
											if ("admin".equals(userLogin.getUsername())) {
										%>

										<td class="center"><a
											href="<%=request.getContextPath()%>/userEditController?id=<%=item.getId()%>"
											title="" class="btn btn-primary"><i class="fa fa-edit "></i>
												Sửa</a> <a
											href="<%=request.getContextPath()%>/userDeleteController?id=<%=item.getId()%>"
											title="" class="btn btn-danger"
											onclick="return confirm('Bạn có chắc chắn muốn xóa')"><i
												class="fa fa-pencil"></i> Xóa</a></td>
										<%
											} else {
										%>
										<td class="center">
										<%	if(userLogin.getId() == item.getId()){ %>
										<a
											href="<%=request.getContextPath()%>/userEditController?id=<%=item.getId()%>"
											title="" class="btn btn-primary"><i class="fa fa-edit "></i>
												Sửa</a><%} %></td>
										<%
											}
										%>
									</tr>

									<%
										}
										} else {
									%>
									<tr>
										<td colspan="4" align="center">Chưa có người dùng nào</td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info" id="dataTables-example_info"
										style="margin-top: 27px">Hiển thị từ <%=currentPage %> đến <%=numberOfPage %> của <%=numberUser %>
										người dùng</div>
								</div>
								<div class="col-sm-6" style="text-align: right;">
									<div class="dataTables_paginate paging_simple_numbers"
										id="dataTables-example_paginate">
										<ul class="pagination">
											<%	String dis=""; 
										if(currentPage ==1 ){
											dis="disabled";
										}else{
											dis="";
										}
											%>
											<li class="paginate_button previous <%=dis %>"
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/userIndexController?page=<%=currentPage-1%>">Trang
													trước</a></li>
											<% 	
														String active = "";												
														for (int i =1 ; i <= numberOfPage; i++){
															if(currentPage == i){
																active = " active";
															}else{
																active= "";
															}
															
															
													%>
											<li class="paginate_button"
												aria-controls="dataTables-example" tabindex="0"><a
												href="<%=request.getContextPath()%>/userIndexController?page=<%=i%>"><%=i %></a></li>
												<%} %>
												
												<%	String dis2=""; 
										if(currentPage == numberOfPage ){
											dis2="disabled";
										}else{
											dis2="";
										}
											%>
											<li class="paginate_button next <%=dis2 %>"
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_next"><a href="<%=request.getContextPath()%>/userIndexController?page=<%=currentPage-1%>">Trang tiếp</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				<!--End Advanced Tables -->
			</div>
		</div>
	</div>
</div>
<script>
	document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>