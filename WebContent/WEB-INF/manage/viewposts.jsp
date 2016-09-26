<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>View/Edit Posts - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<!-- META_END -->
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
		
			<div id="page-content" class="w3-col m8 w3-container w3-border w3-padding w3-card-2">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>View/Edit Posts</h1>
				<p>Use this page to view or edit blog posts to the webapp.</p>
				
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
						<p class="error"><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
						<p class="info"><s:property/></p>
					</s:iterator>
				</s:if>
				
				<!-- RESULTS START -->
				<s:if test="results != null">
				<s:if test="results.isEmpty()">
					<p class="footnote">Something went wrong because no results were found. Please try again later?</p>
				</s:if>
				<s:else>
					<p class="footnote"><s:property value="results.size()" /> result(s) found.</p>
					<table class="w3-table-all">
					<tr class="w3-theme-dark">
						<th>&#9776;</th>
						<th>Title</th>
						<th>Tags</th>
						<th>Author</th>
						<th>Published</th>
						<th>Modified</th>
					</tr>
					
					<s:iterator value="results" status="r">
						<tr>
						<td><a href="/blog/post/<s:property value="uriName" />">View</a>&nbsp;
						<a href="/manage/editpost/<s:property value="uriName" />">Edit</a></td>
						<td><s:property value="title" />
						<s:if test="!isVisible">
							&nbsp;<a class="icon-visible w3-padding" title="This post is hidden from public."></a>
						</s:if>
						<s:if test="isFeatured">
							&nbsp;<a class="icon-star w3-padding" title="This is a featured post."></a>
						</s:if>
						</td>
						<td><s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
								&nbsp;<a class="tag" href="/blog/search?tag=<s:property />"><s:property /></a>
							</s:iterator>
						</s:if></td>
						<td><a href="/author/<s:property value="uriAuthor" />"><s:property value="author" /></a></td>
						<td><s:property value="createDateReadable" /></td>
						<td><s:property value="modifyDateReadable" /></td>
					</s:iterator>
					</table>
				
				</s:else>
				</s:if>
				<!-- RESULTS END -->
					
				<br />
				<br />
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>