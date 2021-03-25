<%@page import="models.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
           <%String msg =request.getParameter("msg");
        String name =request.getParameter("name");
       
        Category cate = (Category) request.getAttribute("categories");
        if(cate != null){
        	name = cate.getName();
        
        }
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
                                <form role="form" action="" method="post"  id="form">
                                    <div class="form-group">
                                        <label for="name" >Tên danh mục</label>
                                        <input type="text" id="name" value="<%if(name != null) out.print(name); %>" name="name" class="form-control" required="required"/>
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
				name:{
					required: " (Tên danh mục không được trống)",
				}
			}
		});
	});
</script>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>