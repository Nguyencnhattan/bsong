<%@page import="models.Songs"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Quản lý bài hát</h2>
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
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Thêm bài hát thành công</span>");
			}
			if ("errorid".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">ID không tồn tại</span>");
			}
			if ("editsuccess".equals(msg)) {
				out.print(
						"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">Sửa thông tin thành công</span>");
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
									<a href="<%=request.getContextPath() %>/songAddController" class="btn btn-success btn-md">Thêm</a>
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
											<div class="alert alert-success" role="alert">
											  Xóa bài hát thành công
											  </div>
								<% }
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
										<th>Tên bài hát</th>
										<th>Danh mục</th>
										<th>Lượt đọc</th>
										<th>Hình ảnh</th>
										<!--   %><th>Tiêu đề</th>
										<th>Chi tiết</th>-->
										<th width="160px">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<%
									List<Songs> songs = (List<Songs>) request.getAttribute("songs");
										if (request.getAttribute("songs") != null) {
											
											if (songs.size() > 0) {
												for (Songs song : songs) {
													int id = song.getId();
													String songName = song.getName();
													int counter = song.getCounter();
													String picture = song.getPicture();
													String catname = song.getCategory().getName();
									%>

									<tr>
									
										<td class="center"><%=id %></td>
										<td class="center"><%=songName %></td>
										<td class="center"><%=catname %></td>
										<td class="center"><%=counter%></td>
										<td class="center">
											<%
												if (!"".equals(picture)) {
											%> <img width="200px"
											height="200px" src="<%=request.getContextPath() %>/uploads/<%=picture %>" alt="<%=picture%>" />
											<%
												} else {
											%>không có hình <%
												}
											%>
										</td>
										
										<td class="center"><a href="<%=request.getContextPath() %>/songEditController?id=<%=id%>" title=""
											class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a> <a
											href="<%=request.getContextPath() %>/songDeleteController?id=<%=id%>" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa')"><i
												class="fa fa-pencil" ></i> Xóa</a></td>
									</tr>
									<%
										}
											}
										}
									%>
								</tbody>
							</table>
							<% 	int numberSongs =(Integer)request.getAttribute("numberSongs");
								int numberOfPage =(Integer)request.getAttribute("numberOfPages");
							int currentPage =(Integer)request.getAttribute("currentPage");
							if(songs != null && songs.size() >0){
							%>
							
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info" id="dataTables-example_info"
										style="margin-top: 27px">Hiển thị từ <%=currentPage %> đến <%=numberOfPage%>  của <%=numberSongs%>
										bài hát</div>
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
												id="dataTables-example_previous" ><a href="<%=request.getContextPath()%>/songIndexController?page=<%=currentPage-1%>">Trang
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
											<li class="paginate_button<%=active %>"
												aria-controls="dataTables-example" tabindex="0"><a
												href="<%=request.getContextPath()%>/songIndexController?page=<%=i%>"><%=i %></a></li>
										
												<%} %>
												<%	String dis2=""; 
										if(currentPage == numberOfPage ){
											dis2="disabled";
										}else{
											dis2="";
										}
											%>
											<li class="paginate_button next <%=dis2 %> "
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_next"><a href="<%=request.getContextPath()%>/songIndexController?page=<%=currentPage+1%>">Trang tiếp</a></li>
										</ul>
									</div>
								</div>
								<%} %>
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
	document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>