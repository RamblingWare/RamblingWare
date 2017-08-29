<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>View/Edit Posts - <%=Application.getString("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m10 l10 w3-container w3-padding">
				
				<h1>View/Edit Posts</h1>
				
				<p><a class="w3-btn w3-round w3-small w3-green w3-hover-teal" href="/manage/newpost"><span class="icon-quill w3-large"></span>&nbsp;Create New Post</a> 
				<span class="w3-small w3-text-grey nowrap">Make a new blog post.</span>
				</p>
				
				<s:if test="posts != null">
				<s:if test="posts.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						Something went wrong because no posts were found. Please try again later?</p>
				</s:if>
				<s:else>
					<p class="w3-small w3-text-grey w3-margin-0 w3-right"><s:property value="posts.size()" /> result(s) found.</p>
					<table class="w3-table w3-bordered w3-striped w3-small">
					<tr class="w3-theme-dark uppercase">
						<th></th>
						<th><span class="icon-quill w3-large w3-text-theme w3-padding-square" title="Title"></span>Title</th>
						<th><span class="icon-folder w3-large w3-text-theme w3-padding-square" title="Category"></span>Category</th>
						<th><span class="icon-tag w3-large w3-text-theme w3-padding-square" title="Tags"></span>Tags</th>
						<th><span class="icon-author w3-large w3-text-theme w3-padding-square" title="Author"></span>Author</th>
						<th><span class="icon-time w3-large w3-text-theme w3-padding-square" title="Publish Date"></span>Published</th>
						<th><span class="icon-eye w3-large w3-text-theme w3-padding-square" title="Views"></span>Views</th>
					</tr>
					
					<s:iterator value="posts" status="r">
						<tr>
						<td>
							<a class="w3-btn w3-card w3-round w3-tiny w3-theme-light" href="/manage/editpost/<s:property value="uri" />">Edit</a></td>
						<td>
						<s:if test="thumbnail != null && !thumbnail.isEmpty()">
							<img src="<s:property value="thumbnail" />" height="27px" width="48px" style="vertical-align: middle;" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'" />
						</s:if>
						<s:if test="published == false">
							<a href="/manage/viewpost/<s:property value="uri" />" class="w3-medium" title="<s:property value="description" />"><s:property value="title" /></a>
							&nbsp;<span class="icon-eye w3-large w3-text-red w3-padding-square" title="This post is hidden from the public."></span>
						</s:if>
						<s:else>
							<a href="/blog/<s:property value="uri" />"  class="w3-medium" title="<s:property value="description" />"><s:property value="title" /></a>
						</s:else>
						<s:if test="featured == true">
							&nbsp;<span class="icon-star w3-large w3-text-yellow w3-padding-square" title="This is a featured post."></span>
						</s:if>
						</td>
						<td><a class="w3-text-black" href="/category/<s:property value="category" />"><s:property value="category" /></a></td>
						<td><s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
								<a class="w3-text-black" href="/tag/<s:property />" title="<s:property />"><s:property /></a>&nbsp;
							</s:iterator>
						</s:if></td>
						<td><a class="w3-text-black" href="/author/<s:property value="author.uri" />"><s:property value="author.name" /></a></td>
						<td><a class="w3-text-black" href="/year/<s:property value="publishYear" />" title="<s:property value="publishDate" />"><s:property value="publishDateReadable" /></a></td>
						<td><span class="w3-text-black" title="Views (<s:property value="view.count" />) / Actual (<s:property value="view.session" />)">
						<s:property value="view.countReadable" /> (<s:property value="view.sessionReadable" />)
						</span></td>
					</s:iterator>
					</table>
					
					<%@include file="/WEB-INF/fragment/pagination.jspf" %>
				
				</s:else>
				</s:if>				
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>