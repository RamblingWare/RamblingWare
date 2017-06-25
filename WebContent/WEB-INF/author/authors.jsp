<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>Authors - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-author w3-text-theme"></span>&nbsp;Authors</h1>
				
				<s:if test="authors != null">
				<s:if test="authors.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						Something went wrong because no authors were found. Please try again later?</p>
				</s:if>
				<s:else>
					<s:if test="authors.size() == 1">	
						<p>Only 1 author writes this blog!<br /></p>
					</s:if>
					<s:else>
						<p><s:property value="authors.size()" /> authors write for this blog.<br /></p>
					</s:else>
					
					<div class="w3-row" style="min-height:0px">
					<s:iterator value="authors" status="r">
						<s:if test="#r.index < 3">
							<%@include file="/WEB-INF/author/card-author.jspf" %>
						</s:if>
					</s:iterator>
					
					</div>
				
				</s:else>
				</s:if>
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>