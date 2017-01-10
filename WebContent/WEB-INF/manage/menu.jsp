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
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Management Menu</h1>
				<p>Use this page to manage the blog. Add/Edit posts, monitor traffic, and change server settings.</p>
		
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" data-close="">
						<span class="icon-cross w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-green w3-text-green w3-border-green" onclick="this.style.display='none'" data-close="">
						<span class="icon-checkmark w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>			
				
				<div class="w3-row">
					<div class="w3-col l6 m6 s12">
						<h3>Manage Blog Posts</h3>
						<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newpost"><span class="icon-quill w3-large w3-margin-right"></span>Create New Post</a> 
						<span class="footnote nowrap">Make a new blog post!</span>
						</p>
						<p>
						<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/posts"><span class="icon-clipboard w3-large w3-margin-right"></span>View/Edit Posts</a>
						<span class="footnote nowrap">Modify current blog posts.</span>
						</p>
						<p>
						<a class="w3-btn w3-card w3-round w3-small w3-pale-blue" target="_Blank" href="https://disqus.com/home/forums/ramblingware/"><span class="icon-bubbles w3-large w3-margin-right"></span>View Comments</a>
						<span class="footnote nowrap">Manage comments using Disqus.</span>
						</p>
					</div>
					<div class="w3-col l6 m6 s12">
						<h3>Manage Authors</h3>
						<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newuser"><span class="icon-user-tie w3-large w3-margin-right"></span>Add Author</a>
						<span class="footnote nowrap">Add a new Author!</span>
						</p>
						<p><a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/users"><span class="icon-briefcase w3-large w3-margin-right"></span>View/Edit Authors</a>
						<span class="footnote nowrap">Modify current Authors.</span>
						</p>
					</div>	
				</div>
				
				<div class="w3-row">
					<div class="w3-col l12 m12 s12">
						<h3>My Settings</h3>
						<p><a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/settings"><span class="icon-shield w3-large w3-margin-right"></span>Change Username / Email</a> 
						<span class="footnote nowrap">Modify your username or email address.</span>
						</p>
						<p>
						<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/settings"><span class="icon-key w3-large w3-margin-right"></span>Change Password</a>
						<span class="footnote nowrap">Modify your password to login.</span>
						</p>
						<p>
						<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/edituser/<s:property value="#session.USER.uriName" />"><span class="icon-user-tie w3-large w3-margin-right"></span>Update my Author page</a>
						<span class="footnote nowrap">Modify your public author page.</span>
						</p>
					</div>			
				</div>
				
				<hr />
							
				<h3>Application Statistics</h3>
				<s:if test="start != null">
				<table class="w3-table-all">
					<thead>
						<tr class="w3-theme-dark uppercase">
							<th width="33%">Property</th>
							<th>Value</th>
						</tr>
					</thead><tbody>
						<tr><td>Start Time</td><td><s:property value="start" /></td></tr>
						<tr><td>OS</td><td><s:property value="os" /></td></tr>
						<tr><td>Java</td><td><s:property value="java" /></td></tr>
						<tr><td>Max Memory</td><td><span class="w3-tag w3-round-large w3-theme-light w3-center"><s:property value="maxMem" /> MB</span></td></tr>
						<tr><td>Free Memory</td><td><span class="w3-tag w3-round-large w3-theme-light w3-center"><s:property value="freeMem" /> MB</span></td></tr>
						<tr><td>Total Memory</td><td><span class="w3-tag w3-round-large w3-theme-light w3-center"><s:property value="totalMem" /> MB</span></td></tr>
					</tbody>
				</table>
				
				<br/>
				
				<table class="w3-table-all">
					<thead><tr class="w3-theme-dark uppercase">
						<th width="33%" >Database Name</th>
						<th>Size (MB)</th>
					</tr></thead><tbody>
					<s:iterator value="databases" status="d">
					<tr>
						<td><s:property value="name" /></td>
						<td><span class="w3-tag w3-round-large w3-theme-light w3-center"><s:property value="size" /> MB</span></td>
					</tr>
					</s:iterator></tbody>
				</table>
				</s:if>
				<s:else>
				<p>
					<a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/">Reload</a>
					<span class="footnote">Refresh the statistics.</span>
				</p>
				</s:else>
				
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>