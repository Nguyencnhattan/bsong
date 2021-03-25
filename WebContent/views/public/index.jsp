<%@page import="java.sql.Timestamp"%>
<%@page import="models.Songs"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
    <div class="article">
    	<%List<Songs> songs = (List<Songs>) request.getAttribute("songs");
										if (request.getAttribute("songs") != null) {
											
											if (songs.size() > 0) {
												int i = 0;
												for (Songs song : songs) {
													int id = song.getId();
													String songName = song.getName();
													int counter = song.getCounter();
													String picture = song.getPicture();
													Timestamp createAt = song.getDatecreate();
													String catname = song.getCategory().getName();
													String preview = song.getPreview();
													i++;
									%>
    
      <h2><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=id %> " title="<%=songName %>"><%=songName %></a></h2>
      <p class="infopost">Ngày đăng: <%=createAt %> Lượt xem: <%=counter %> <a href="#" class="com"><span><%=i %></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath() %>/uploads/<%=picture %>" width="177" height="213" alt="<%=songName %>" class="fl" /></div>
      <div class="post_content">
      <%=preview %>
      
        <p class="spec"><a href="<%=request.getContextPath() %>/publicDetailController?id=<%=id%>" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
        <%}}} %>
    </div>
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
    <a href="<%=request.getContextPath() %>/publicIndexController?&page=<%=i%>"><%=i %></a>
    <%}} %>
    <a href="<%=request.getContextPath() %>/publicIndexController&page=<%=currentPages+1%>">&raquo;</a></p>
    <%} %>
  </div>
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
