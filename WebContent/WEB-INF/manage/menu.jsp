<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Management Menu - RamblingWare</title>
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
		
			<div id="page-content" class="w3-col m8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Management Menu</h1>
				<p>Use this page to manage the blog. Add/Edit posts, monitor traffic, and change server settings.</p>
		
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
				
				<hr />
				
				
				<h3>Manage Blog</h3>
				<p><a class="w3-btn w3-green w3-hover-text-white w3-card w3-round w3-small" href="/manage/newpost">Create New Post</a> 
				<span class="footnote">Make a new blog post!</span>
				</p>
				<p>
				<a class="w3-btn w3-theme-light w3-card w3-round w3-small" href="/manage/posts">View/Edit Posts</a>
				<span class="footnote">Modify current blog posts.</span>
				</p>
				<p>
				<a class="w3-btn w3-card w3-round w3-blue w3-hover-text-white w3-small" target="_Blank" href="https://disqus.com/home/forums/ramblingware/">View Disqus Comments</a>
				<span class="footnote">Manage comments on blog posts using Disqus.</span>
				</p>
				
				<hr />
				<h3>Manage Authors</h3>
				<p><a class="w3-btn w3-green w3-hover-text-white w3-card w3-round w3-small" href="/manage/newuser">Add New Author</a>
				<span class="footnote">Add a new Author!</span>
				</p>
				<p><a class="w3-btn w3-theme-light w3-card w3-round w3-small" href="/manage/users">View/Edit Authors</a>
				<span class="footnote">Modify current Authors.</span>
				</p>
				<hr />
				
				
				<h3>Webapp Statistics</h3>
				<table class="w3-table-all w3-small">
					<tr class="w3-light-green">
						<th>Property</th>
						<th>Value</th>
					</tr>
					<tr><td>Start Time</td><td><s:property value="start" /></td></tr>
					<tr><td>OS</td><td><s:property value="os" /></td></tr>
					<tr><td>Java</td><td><s:property value="java" /></td></tr>
					<tr><td>Max Memory</td><td><s:property value="maxMem" /> MB</td></tr>
					<tr><td>Free Memory</td><td><s:property value="freeMem" /> MB</td></tr>
					<tr><td>Total Memory</td><td><s:property value="totalMem" /> MB</td></tr>
					<tr class="w3-light-green">
						<td>Database Name</td>
						<td>Size (MB)</td>
					</tr>
					<s:iterator value="databases" status="d">
					<tr>
						<th><s:property value="name" /></th>
						<th><s:property value="size" /> MB</th>
					</tr>
					</s:iterator>
				</table>
				
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