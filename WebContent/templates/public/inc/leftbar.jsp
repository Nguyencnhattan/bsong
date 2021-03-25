<%@page import="models.Songs"%>
<%@page import="daos.SongDAO"%>
<%@page import="java.util.List"%>
<%@page import="daos.CategoryDAO"%>
<%@page import="models.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="searchform">
	<form id="formsearch" name="formsearch" method="post" action="">
		<span> <input name="editbox_search" class="editbox_search"
			id="editbox_search" maxlength="80" 	type="text"  />
		</span> <input name="button_search"
			src="<%=request.getContextPath() %>/resources/public/images/search.jpg"
			class="button_search" type="image" />
	</form>
</div>
<div class="clr"></div>
<div class="gadget">
	<h2 class="star">Danh mục bài hát</h2>
	<div class="clr"></div>
	<ul class="sb_menu">
		<% CategoryDAO categoryDAO = new CategoryDAO();
  List<Category> categories = categoryDAO.getCategories(); 
  if(categories.size()>0){
	  for(Category item:categories){
  %>
		<li><a href="<%=request.getContextPath() %>/publicCatController?id=<%=item.getId()%>"><%=item.getName() %></a></li>

		<%} 
	  } %>
	</ul>
</div>

<div class="gadget">
	<h2 class="star">
		<span>Bài hát mới</span>
	</h2>
	<div class="clr"></div>
	<ul class="ex_menu">
			<% SongDAO songDAO = new SongDAO();
  List<Songs> recentSong = songDAO.getAll(6); 
  if(recentSong.size()>0){
	  for(Songs item:recentSong){
  %>
		<li><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=item.getId()%>"><%=item.getName() %></a><br /> 
		<% if(item.getPreview().length()>50) out.print(item.getPreview().substring(0,50)); else out.print(item.getPreview());%></li>
		
		<%}} %>
	</ul>
</div>