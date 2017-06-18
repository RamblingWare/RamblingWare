<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta.jspf"%>

<title>About - RamblingWare</title>
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
							
				<h1><span class="w3-small w3-text-grey w3-right">June 17th, 2017</span>
				About</h1>
				
				<p>
					RamblingWare is a blog about computers, programming, tech, and things that bother me. I hope it bothers you too.
				</p>
				<p>
					I cover topics around web development, web security, and programming. Really, anything that I can ramble on about!
				</p>
				<p>
					I hope you enjoy reading, and leave a comment if you like! And don't forget to subscribe for more. Thanks! ~ <a href="/author/austin-delamar">Austin</a>			
				</p>
				<p class="w3-small w3-text-grey quote">
					Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.
				</p>
				
				<br />
				
				<h2>This Blog is Custom Built</h2>
				<p> Yep! I have written the code from scratch myself. It has been a work in progress for some time. But all the code is available on the <a target="_blank" href="https://github.com/RamblingWare/RamblingWare">GitHub repo</a>.
				I've used blog services before, but decided to program it myself. And I know Its not perfect but I have been having fun with it! I typically work on it at least once a month.<br />
				</p>
				<blockquote>“Talk is cheap. Show me the code.”<br />&nbsp;&nbsp;&nbsp;&nbsp;- Linus Torvalds</blockquote>
				<p>
				If you want to help inform me of any problems with this website, you can open an issue on the 
				<a target="_blank" href="https://github.com/RamblingWare/RamblingWare">GitHub repo</a>. Or if you like this website and wanted to deploy your own using this code, you can do that too.
				</p>
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="https://github.com/RamblingWare/RamblingWare/issues"><span class="icon-bug w3-large w3-margin-right"></span>Report Bug</a>
					&nbsp;&nbsp;
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="https://github.com/RamblingWare/RamblingWare"><span class="icon-embed w3-large w3-margin-right"></span>View Code</a>
				</p>
				
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
		</div>
	</article>

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>