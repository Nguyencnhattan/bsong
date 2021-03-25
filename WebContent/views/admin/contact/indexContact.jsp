<%@page import="models.Contact"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Quản lý liên lệ</h2>
			</div>
		</div>
		<!-- /. ROW  -->
		<hr />
		<div class="row">
			<div class="col-md-12">
				<!-- Advanced Tables -->
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="table-responsive">
							<div class="row">
								<div class="col-sm-6">
								
								</div>
								<div class="col-sm-6" style="text-align: right;">
									<form method="post" action="">
										<input type="submit"  
											class="btn btn-warning btn-sm" style="float: right" /> <input
											type="search" name="search" class="form-control input-sm"
											placeholder="Nhập tên danh mục"
											style="float: right; width: 300px;" />
										<div style="clear: both"></div>
									</form>
									<br />
								</div>
							</div>
							<%String msg = request.getParameter("msg");
								if ("deletesuccess".equals(msg)) {
							%>
							<div class="alert alert-success" role="alert">Xóa danh mục thành công</div>
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
										<th>Tên liên lạc</th>
										<th>Email</th>
										<th>Website</th>
										<th>Message</th>
										<th width="80px">Chức năng</th>
									</tr>
								</thead>
								<tbody>
								<%
									int numberCon =(Integer)request.getAttribute("numberCon");
									int numberOfPage =(Integer)request.getAttribute("numberOfPages");
									int currentPage =(Integer)request.getAttribute("currentPage");
										List<Contact> contact = (List<Contact>) request.getAttribute("contacts");
										if (contact != null && contact.size() > 0) {
											for (Contact list : contact) {
												int id = list.getId();
												String name = list.getName();
												String email = list.getEmail();
												String website = list.getWebsite();
												String message = list.getMessage();
												
									%>
									<tr>
										<td class="center"><%=id %></td>
										<td class="center"><%=name %></td>
										<td class="center"><%=email %></td>
										<td class="center"><%=website %></td>
										<td class="center"><%=message %></td>
										<td class="center"><a
											href="<%=request.getContextPath() %>/contactDeleleController?id=<%=id %>"
											title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa')"><i class="fa fa-pencil" ></i>
												Xóa</a></td>
									</tr>
									<%}} %>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info" id="dataTables-example_info"
										style="margin-top: 27px">Hiển thị từ <%=currentPage %> đến <%=numberOfPage %>  của <%=numberCon %>
										liên hệ</div>
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
												id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/contactIndexController?page=<%=currentPage-1%>">Trang
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
												href="<%=request.getContextPath()%>/contactIndexController?page=<%=i%>"><%=i %></a></li>
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
												id="dataTables-example_next"><a href="<%=request.getContextPath()%>/contactIndexController?page=<%=currentPage+1%>">Trang tiếp</a></li>
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
	document.getElementById("contact").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>