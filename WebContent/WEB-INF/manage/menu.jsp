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
				
				<h1>Manage Menu</h1>		
				
				<div class="w3-row">
					<div class="w3-container w3-padding w3-col s12 m12 l6">
						<div class="w3-border w3-round">
							<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
								<h3 class="w3-margin-0 w3-padding uppercase">Blog Posts</h3>
							</div>
							<div class="w3-padding w3-small w3-theme-light">
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
						</div>
					</div>
					<div class="w3-container w3-padding w3-col s12 m12 l6">
						<div class="w3-border w3-round">
							<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
								<h3 class="w3-margin-0 w3-padding uppercase">Authors</h3>
							</div>
							<div class="w3-padding w3-small w3-theme-light">
								<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newuser"><span class="icon-user-tie w3-large w3-margin-right"></span>Add Author</a>
								<span class="footnote nowrap">Add a new Author!</span>
								</p>
								<p><a class="w3-btn w3-card w3-round w3-small w3-theme-light" href="/manage/users"><span class="icon-briefcase w3-large w3-margin-right"></span>View/Edit Authors</a>
								<span class="footnote nowrap">Modify current Authors.</span>
								</p>
							</div>
						</div>
					</div>	
				</div>
				
				<div class="w3-row">
					<div class="w3-container w3-padding w3-col s12 m12 l12">
						<div class="w3-border w3-round">
							<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
								<h3 class="w3-margin-0 w3-padding uppercase">My Settings</h3>
							</div>
							<div class="w3-padding w3-small w3-theme-light">
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
					</div>			
				</div>
				
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>