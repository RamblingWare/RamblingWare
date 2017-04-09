<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
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
							
				<h1><font class="w3-small w3-text-grey w3-right">Apr 9th, 2017</font>
				About</h1>
				
				<p>
					RamblingWare is a blog about computers, programming, tech, and things that bother me. I hope it bothers you too.
				</p>
				<p>
					I cover topics around web development, web security, and programming. Really, anything that I can ramble on about!
				</p>
				<p>
					I hope you enjoy reading, and leave a comment if you like! Thanks. And don't forget to subscribe for more.				
				</p>
				<p class="w3-small w3-text-grey quote">
					Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.
				</p>
				
				<br />
				
				<h2>About the Author</h2>
				<div class="w3-row" style="min-height:0px">
				<div class="w3-col s12 m10 l7 w3-padding-0 w3-margin-0 w3-round w3-hover-shadow w3-card">
					<a href="/author/austin-delamar">
					<div class="w3-col s3 m3 l3 w3-padding-16">
						<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile Picture" src="https://i.imgur.com/21fYx3ks.jpg">
					</div>
					<div class="w3-col s9 m9 l9 w3-padding-16">
						<p class="w3-small w3-text-grey w3-margin-0 w3-padding-right">
						<b>Austin Delamar</b><br />
						<span class="w3-small">
						Java Developer, Burger &amp; Beer enthusiast, and Owner of RamblingWare.
						When Austin's not at his computer, he likes to relax outdoors, play tennis,
						snow ski, play guitar, and talk about himself in third-person.</span> 
						</p>
					</div>
					</a>
				</div>
				</div>
				
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
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="https://github.com/RamblingWare/RamblingWare"><span class="icon-embed2 w3-large w3-margin-right"></span>View Code</a>
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