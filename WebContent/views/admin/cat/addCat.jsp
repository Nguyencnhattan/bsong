<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
         <%String msg =request.getParameter("msg");
        
        if("error".equals(msg)){
        	out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px\">Có lỗi khi thêm</span>");
        }
        if("hasuser".equals(msg)){
        	out.print("<span style=\"background: yellow; color: red; font-weight: bold; padding: 5px\">Tên danh mục đã tồn tại</span>");
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
                                        <input type="text" id="name" name="name" class="form-control" required="required"/>
                                    </div>
                                  
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Thêm</button>
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