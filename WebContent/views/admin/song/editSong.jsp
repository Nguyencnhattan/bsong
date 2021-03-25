<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@page import="models.Songs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Sửa bài hát</h2>
			</div>
		</div>
		<!-- /. ROW  -->
		<%
			String name = request.getParameter("name");
			String preview = request.getParameter("preview_text");
			String detail = request.getParameter("detail_text");
		
			String category = request.getParameter("catName");
			int categoryID = 0;
			Songs song = (Songs) request.getAttribute("songs");
			String picture= "";
			if (song != null) {
				name = song.getName();
				preview = song.getPreview();
				detail = song.getDetail();
				category = song.getCategory().getName();
				categoryID = song.getCategory().getId();
				picture = song.getPicture();
			}
		%>
		 <%String msg =request.getParameter("msg");
       
        if("error".equals(msg)){
        	out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px\">Có lỗi khi sửa</span>");
        }
      
        if("editerror".equals(msg)){
        	out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px\">Sửa thông tin không thành công</span>");
        }
        %>
		<hr />
		<div class="row">
			<div class="col-md-12">
				<!-- Form Elements -->
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12">
								<form role="form" method="post" enctype="multipart/form-data"
									id="form">
									<div class="form-group">
										<label for="name">Tên bài hát</label> <input type="text"
											id="name" value="<%if (name != null) out.print(name);%>"
											name="name" class="form-control" required="required" />
									</div>
									<div class="form-group">
										<label for="category">Danh mục bài hát</label>
										 <select id="category" name="category" class="form-control" required="required">

											<%
												if (request.getAttribute("categories") != null) {
													List<Category> categories = (List<Category>) request.getAttribute("categories");
													if (categories.size() > 0) {
														for (Category item : categories) {
															int catId = item.getId();
															String catName = item.getName();
															if (catId == categoryID) {
											%>
											<option value="<%=catId%>" selected="selected"><%=catName%></option>
											<%
												} if (catId != categoryID) {
											%>
											<option value="<%=catId%>"><%=catName%></option>
											<%
												}
														}
													}
												}
											%>

										</select>
									</div>
									<div class="form-group">
										<label for="picture">Hình ảnh</label>
										 <input type="file"
											name="picture" value=""/>
										<%if(!picture.isEmpty()){ %>
										<br>
										<img alt="Ảnh" src="<%=request.getContextPath() %>/uploads/<%=picture %>" width="200px" height="200px">
										<%} %>
									</div>
									<div class="form-group">
										<label for="preview">Mô tả</label>
										<textarea id="preview" class="form-control" rows="3"
											name="preview" required="required"><%
												if (preview != null)
													out.print(preview);
											%></textarea>
									</div>
									<div class="form-group">
										<label for="detail">Chi tiết</label>
										<textarea id="detail" class="form-control" id="detail"
											rows="5" name="detail" required="required">
											<%
												if (detail != null)
													out.print(detail);
											%>
										</textarea>
									</div>
									<button type="submit" name="submit"
										class="btn btn-success btn-md">Sửa</button>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- End Form Elements -->
			</div>
		</div>
		<!-- /. ROW  -->
	</div>
	<!-- /. PAGE INNER  -->
</div>
<script>
	$().ready(function(){
		
		var validator = $("#form").validate({
			errorPlacement: function(error, element){
				$(element).closest("form").find("label[for='" + element.attr("id")+"']").append(error);
			},
			errorElement: "span",
			messages:{
				name:{
					required: " (Tên bài hát không được trống)",
				},
				category:{
					required: " (chọn danh mục)",
				},
				preview:{
					required: " (Mô tả không được trống)",
				}
			}
		});
	});
</script>
<script>
	var editor = CKEDITOR.replace('detail');
</script>
<script>
	document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>