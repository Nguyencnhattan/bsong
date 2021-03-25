<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  <%
   	Songs songs = (Songs) request.getAttribute("songs");
  if(songs != null){
  %>
    <div class="article">
      <h1><%=songs.getName() %></h1>
      <div class="clr"></div>
      <p>Ngày đăng: <%=songs.getDatecreate() %>. Lượt xem: <%=songs.getCounter() %></p>
      <div class="vnecontent">
      <%=songs.getDetail() %>        
      </div>
    </div>
    <%} %>
    <div class="article">
      <h2>Bài viết liên quan</h2>
      <% 
      	ArrayList<Songs> relatedsongs = (ArrayList<Songs>) request.getAttribute("relatedsongs");
      	if(relatedsongs != null && relatedsongs.size() >0){
      		for(Songs item : relatedsongs){
      		
      %>
      <div class="clr"></div>
      <div class="comment"> <a href="<%=request.getContextPath() %>/publicDetailController?id=<%=item.getId() %>"><img src="<%=request.getContextPath() %>/uploads/<%=item.getPicture() %>" width="40" height="40" alt="" class="userpic" /></a>
        <h2><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=item.getId() %>"><%=item.getName() %></a></h2>
        <p><%=item.getPreview() %></p>
      </div>
      <%}} %>
    </div>
  </div>
  <div class="sidebar">
  <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
