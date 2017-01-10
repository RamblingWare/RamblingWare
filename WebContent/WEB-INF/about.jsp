<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
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
							
				<h1><font class="footnote w3-right">Jan 9th, 2017</font>
				About</h1>
				
				
				<p>This is my <b>blog</b> about computers, programming, tech, and things that bother me. I hope it bothers you too. ;-)<br />
				<br />
				I cover topics around web development and programming. I plan to cover computer security, java programming, and workplace related topics.<br />
				<br />
				I hope you enjoy reading, and leave a comment if you like! Thanks.				
				</p>
				
				<br />
				<br />
				
				<h2>About the Author</h2>
				<div class="w3-row" style="min-height:0px">
				<div class="w3-col s12 m10 l7 w3-padding-0 w3-margin-0 w3-round w3-hover-shadow w3-card">
					<a href="/author/austin-delamar">
					<div class="w3-col s3 m3 l3 w3-padding-16">
						<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile Picture" src="https://i.imgur.com/21fYx3ks.jpg">
					</div>
					<div class="w3-col s9 m9 l9 w3-padding-16">
						<p class="footnote w3-padding-right">
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
				<br />
				
				<h2>This Blog is Custom Built</h2>
				<p> Yep! I have written the code from scratch myself. It has been a work in progress for some time. But all the code is available on the <a target="_blank" href="https://github.com/RamblingWare/RamblingWare">GitHub repo</a>.
				I've used blog services before, but had decided to program my own. And I know Its not perfect but I have been having fun coding it! I typically work on it at least once a month.<br />
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
				
				<br />
				<br />				
				
				<h2>Further Reading</h2>
				<div class="w3-container w3-round w3-border w3-card w3-hover-shadow w3-padding-0">
					<div class="w3-container w3-round w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-light" style="overflow: hidden;">
						<a href="/blog/post/problems-with-building-your-own-blogsite">
						<img style="max-height:200px;" src="https://i.imgur.com/j9Et09Xl.jpg" alt="Photo for Problems with building your own blogsite" title="Blog post photo.">
						</a>
					</div>
					<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
					<h3 class="w3-padding-0 w3-margin-0"><a href="/blog/post/problems-with-building-your-own-blogsite">Problems with building your own blogsite</a></h3>
					<p class="footnote">To build or not to build? That is the question. Sometimes building your own blog website can be beneficial. But not always.</p>
					<p class="footnote"><br>Austin Delamar&nbsp;|&nbsp;Aug 11, 2016</p>
					<p class="footnote"><b>Tags:</b>&nbsp;
							&nbsp;<a class="tag w3-tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" href="/blog/search?tag=web-development">web-development</a>
							&nbsp;<a class="tag w3-tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" href="/blog/search?tag=funny">funny</a>
					<span class="w3-right">&nbsp;&nbsp;<a class="footnote" href="/blog/post/problems-with-building-your-own-blogsite#comments"><span class="disqus-comment-count" data-disqus-identifier="problems-with-building-your-own-blogsite">0 comments</span></a></span>
					</p>
					</div>
				</div>
				
				<br />
				<br />
				<div class="w3-container">
					<p class="footnote quote">Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.</p>
				</div>
				<%! int hits = 1; %>
				<!-- About JSP Hits: <%=hits++ %>  -->
				<br />
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