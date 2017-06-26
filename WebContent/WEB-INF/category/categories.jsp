<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta.jspf"%>

<title>Categories - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-folder w3-text-theme"></span>&nbsp;Categories</h1>
				
				<s:if test="categories != null">
				<s:if test="categories.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No categories were found.</p>
				</s:if>
				<s:else>
					<s:if test="categories.size() == 1">	
						<p>1 category found.<br /></p>
					</s:if>
					<s:else>
						<p><s:property value="categories.size()" /> categories found. (Sorted alphabetically.)<br /></p>
					</s:else>
					
					<ol>
					<s:iterator value="categories" status="c">
						<s:set var="cval" value="categories[#c.index].substring(0,categories[#c.index].lastIndexOf('('))" />
						<s:set var="ccnt" value="categories[#c.index].substring(categories[#c.index].lastIndexOf('(')+1,categories[#c.index].length-1)" />
						<li><a title="<s:property value="cval" />" href="/category/<s:property value="cval" />"><s:property value="cval" /></a>
						&nbsp;
						<s:property value="ccnt" /> posts.
						</li>
					</s:iterator>
					</ol>
				
				</s:else>
				</s:if>
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>