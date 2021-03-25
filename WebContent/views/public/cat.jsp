<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
    <div class="article">
    <% 
     	Category category = (Category) request.getAttribute("category");
    	if(category != null ){
    %>
		<h1><%=category.getName() %></h1>
		<%} %>
    </div>
    <% ArrayList<Songs> songs = (ArrayList<Songs>) request.getAttribute("songs");
    if (songs != null && songs.size() >0){
    	int i =0;
    	for(Songs item :songs){
    		i++;
    %>
    <div class="article">
      <h2><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=item.getId()%>" title="<%=item.getName() %>"><%=item.getName() %></a></h2>
      <p class="infopost">Ngày đăng: <%=item.getDatecreate() %>. Lượt xem: <%=item.getCounter() %><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=item.getId()%>" class="com"><span><%=i %></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath() %>/uploads/<%=item.getPicture() %>" width="177" height="213" alt="<%=item.getName() %>" class="fl" /></div>
      <div class="post_content">
        <p><%=item.getDatecreate() %></p>
        <p class="spec"><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=item.getId()%>" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
    </div>
    <%}}else { %>
    <div class="article">
    chưa có bài hát nào
    </div>
    <%} %>
    <%
	int numberOfPages =(Integer) request.getAttribute("numberOfPages");
    int currentPages =(Integer) request.getAttribute("currentPage");
    if(songs != null && songs.size() >0){
    %>
    <p class="pages"><small>Trang <%=currentPages %> của <%=numberOfPages %></small>
    <%
    	for (int i=1; i<=numberOfPages; i++){
    		if(currentPages ==i){
    %>
    <span><%=i %></span>
    <%}else {%>
    <a href="<%=request.getContextPath() %>/publicCatController?id=<%=category.getId()%>&page=<%=i%>"><%=i %></a>
    <%}} %>
    <a href="<%=request.getContextPath() %>/publicCatController?id=<%=category.getId()%>&page=<%=currentPages+1%>">&raquo;</a></p>
    <%} %>
  </div>
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>