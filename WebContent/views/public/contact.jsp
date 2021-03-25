<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp"%>
<div class="content_resize">
	<div class="mainbar">
		<div class="article">
			<h2>
				<span>Liên hệ</span>
			</h2>
			<div class="clr"></div>
			<p>Khi bạn có thắc mắc, vui lòng gửi liên hệ, chúng tôi sẽ phản
				hồi trong thời gian sớm nhất.</p>
		</div>
		<div class="article">
			<h2>Gửi liên hệ đến chúng tôi</h2>
			<%
				String msg = request.getParameter("msg");
				if ("success".equals(msg)) {
			%>
			<div class="alert alert-success" role="alert">Gửi liên lạc
				thành công</div>
			<%
				}
				if ("error".equals(msg)) {
					out.print(
							"<span style=\"background: yellow; color:red; font-weight:bold; padding:5px\">GỬi không thành công</span>");
				}
			%>
			<div class="clr"></div>
			<form action="" method="post" id="sendemail">
				<ol>
					<li><label for="name">Họ tên (required)</label> <input
						id="name" value="" name="name" class="text" /></li>
					<li><label for="email">Email (required)</label> <input
						id="email" value="" name="email" class="text" /></li>
					<li><label for="website">Website</label> <input id="website"
						value="" name="website" class="text" /></li>
					<li><label for="message">Nội dung</label> <textarea
							id="message" name="message" rows="8" cols="50"></textarea></li>
					<li><input type="image" name="imageField" id="imageField"
						src="<%=request.getContextPath()%>/resources/public/images/submit.gif"
						class="send" />
						<div class="clr"></div></li>
				</ol>
			</form>
		</div>
	</div>
	<div class="sidebar">
		<%@ include file="/templates/public/inc/leftbar.jsp"%>
	</div>
	<div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp"%>
