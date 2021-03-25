<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa thông tin người dùng</h2>
            </div>
        </div>
        <!-- /. ROW  -->
         <%String msg =request.getParameter("msg");
        String username =request.getParameter("username");
        String fullname =request.getParameter("fullname");
        User user = (User) request.getAttribute("user");
        if(user != null){
        	 username = user.getUsername();
        	 fullname = user.getFullname();
        }
        if("error".equals(msg)){
        	out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px\">Có lỗi khi sửa</span>");
        }
        if("hasuser".equals(msg)){
        	out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px\">Tên đăng nhập đã tồn tại</span>");
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
                                <form role="form" action="" method="post" id="form">
                                   <div class="form-group">
                                        <label for="username">Tên đăng nhập</label>
                                        <input required="required" type="text" id="username" value="<%if(username != null) out.print(username); %>" name="username" class="form-control" disabled="disabled" />
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="password">Mật khẩu</label>
                                        <input required="required" type="password" id="password" value="" name="password" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="fullname">Họ tên</label>
                                        <input required="required" type="text" id="fullname" value="<%if(fullname != null) out.print(fullname); %>" name="fullname" class="form-control" />
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Sửa</button>
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
				username:{
					required: " (Tên Đăng nhập không được trống)",
				},
				password:{
					required: " (Nhập mật khẩu)",
				},
				fullname:{
					required: " (Họ tên không được trống)",
				}
			}
		});
	});
</script>
<script>
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>