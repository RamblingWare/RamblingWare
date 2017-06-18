<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>Tags - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->

	<!-- CONTENT BEGIN -->
	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-tag w3-text-theme"></span>&nbsp;Tags</h1>
				
				<!-- TAGS START -->
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
						<s:set var="tval" value="tags[#t.index].substring(0,tags[#t.index].lastIndexOf('('))" />
						<s:set var="tcnt" value="tags[#t.index].substring(tags[#t.index].lastIndexOf('(')+1,tags[#t.index].length-1)" />
						<li><a class="tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" title="<s:property value="tval" />" href="/tag/<s:property value="tval" />"><s:property value="tval" /></a>
						&nbsp;
						<s:property value="tcnt" /> posts.
						</li>
					</s:iterator>
					</ol>
				
				</s:else>
				</s:if>
				<!-- TAGS END -->
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
		</div>
	</article>
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>