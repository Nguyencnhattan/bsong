<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Quản lý danh mục</h2>
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
									<a href="<%=request.getContextPath()%>/adminAddCatController"
										class="btn btn-success btn-md">Thêm</a>
								</div>
								<div class="col-sm-6" style="text-align: right;">
									<form method="post" action="">
										<input type="submit"  
											class="btn btn-warning btn-sm" style="float: right" /> <input
											type="search"  name="search"  class="form-control input-sm"
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
									%>
									<div class="alert alert-success" role="alert">Xóa danh mục Không thành công</div>
									<%
								}
							%>
							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>ID</th>
										<th>Tên danh mục</th>
										<th width="160px">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<%
									int numberCat =(Integer)request.getAttribute("numberCat");
									int numberOfPage =(Integer)request.getAttribute("numberOfPages");
									int currentPage =(Integer)request.getAttribute("currentPage");
										List<Category> categories = (List<Category>) request.getAttribute("categories");
										if (categories != null && categories.size() > 0) {
											for (Category list : categories) {
												int id = list.getId();
												String name = list.getName();
									%>
									<tr>
										<td><%=id%></td>
										<td class="center"><%=name%></td>
										<td class="center"><a
											href="<%=request.getContextPath()%>/adminEditCatController?id=<%=id%>"
											title="" class="btn btn-primary"><i class="fa fa-edit "></i>
												Sửa</a> <a
											href="<%=request.getContextPath()%>/adminDeleteCatController?id=<%=id%>"
											title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa')"><i class="fa fa-pencil" ></i>
												Xóa</a></td>
									</tr>
									<%
										}
										}
									%>
								</tbody>
							</table>
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info" id="dataTables-example_info"
										style="margin-top: 27px">Hiển thị từ <%=currentPage %> đến <%=numberOfPage %> của <%=numberCat %>
										danh mục</div>
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
												id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/adminIndexCatController?page=<%=currentPage-1%>">Trang
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
												href="<%=request.getContextPath()%>/adminIndexCatController?page=<%=i%>"><%=i%></a></li>
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
												id="dataTables-example_next"><a href="<%=request.getContextPath()%>/adminIndexCatController?page=<%=currentPage+1%>">Trang tiếp</a></li>
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
	document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>