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
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>View/Edit Posts</h1>
				<p>Use this page to view or edit posts on this blog.</p>
				
				<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newpost"><span class="icon-quill w3-large w3-margin-right"></span>Create New Post</a> 
				<span class="footnote nowrap">Make a new blog post!</span>
				</p>
				<p>
				<a class="w3-btn w3-card w3-round w3-small w3-pale-blue" target="_Blank" href="https://disqus.com/home/forums/ramblingware/"><span class="icon-bubbles w3-large w3-margin-right"></span>View Comments</a>
				<span class="footnote nowrap">Manage comments on blog posts using Disqus.</span>
				</p>
				
				<!-- RESULTS START -->
				<s:if test="results != null">
				<s:if test="results.isEmpty()">
					<p class="footnote">Something went wrong because no results were found. Please try again later?</p>
				</s:if>
				<s:else>
					<p class="footnote"><s:property value="results.size()" /> result(s) found.</p>
					<table class="w3-table-all w3-small">
					<tr class="w3-theme-dark uppercase">
						<th></th>
						<th>Title</th>
						<th>Tags</th>
						<th>Author</th>
						<th>Published</th>
						<th>Modified</th>
					</tr>
					
					<s:iterator value="results" status="r">
						<tr>
						<td>
							<a class="w3-btn w3-card w3-round w3-tiny w3-theme-light" href="/manage/editpost/<s:property value="uriName" />">Edit</a></td>
						<td>
						
						<s:if test="!isVisible">
							<a href="/manage/viewpost/<s:property value="uriName" />" title="<s:property value="description" />"><s:property value="title" /></a>
							&nbsp;<span class="icon-eye-blocked w3-large w3-text-red w3-padding-square" title="This post is hidden from public."></span>
						</s:if>
						<s:else>
							<a href="/blog/post/<s:property value="uriName" />" title="<s:property value="description" />"><s:property value="title" /></a>
						</s:else>
						<s:if test="isFeatured">
							&nbsp;<span class="icon-star-full w3-large w3-text-yellow w3-padding-square" title="This is a featured post."></span>
						</s:if>
						</td>
						<td><s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
								&nbsp;<a class="tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" href="/tag/<s:property />"><s:property /></a>
							</s:iterator>
						</s:if></td>
						<td><a href="/author/<s:property value="uriAuthor" />"><s:property value="author" /></a></td>
						<td><s:property value="publishDateReadable" /></td>
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