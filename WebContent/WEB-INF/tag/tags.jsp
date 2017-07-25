<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta.jspf"%>

<title>Tags - <%=Application.getString("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-tag w3-text-theme"></span>&nbsp;Tags</h1>
				
				<s:if test="tags != null">
				<s:if test="tags.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No tags were found.</p>
				</s:if>
				<s:else>
					<s:if test="tags.size() == 1">	
						<p>1 tag found.<br /></p>
					</s:if>
					<s:else>
						<p><s:property value="tags.size()" /> tags found. (Sorted alphabetically.)<br /></p>
					</s:else>
					
					<ol>
					<s:iterator value="tags" status="t">
						<li><a class="tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" title="<s:property value="name" />" href="/tag/<s:property value="name" />"><s:property value="name" /></a>
						&nbsp;(<s:property value="count" /> posts)
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