<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>Years - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-time w3-text-theme"></span>&nbsp;Years</h1>
				
				<s:if test="years != null">
				<s:if test="years.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						No results were found for any year.</p>
				</s:if>
				<s:else>
					<s:if test="years.size() == 1">	
						<p>1 year containing blog posts.<br /></p>
					</s:if>
					<s:else>
						<p><s:property value="years.size()" /> years of blog posts.<br /></p>
					</s:else>
					
					<ul>
					<s:iterator value="years" status="y">
						<s:set var="yval" value="years[#y.index].substring(0,4)" />
						<s:set var="ycnt" value="years[#y.index].substring(6,years[#y.index].length-1)" />
						<li><a title="<s:property value="yval" />" href="/year/<s:property value="yval" />"><s:property value="yval" /></a>
						&nbsp;
						<s:property value="ycnt" /> posts.
						</li>
					</s:iterator>
					</ul>
				
				</s:else>
				</s:if>
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>