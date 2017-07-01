<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta.jspf"%>

<title>About - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
							
				<h1><span class="w3-small w3-text-grey w3-right">June 17th, 2017</span>
				About</h1>
				
				<p>
					<%=Application.getSetting("name")%> is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.
					<br/><br/>
					I cover topics around web development, web security, and programming. Really, anything that I can rant about.
					<br/><br/>
					Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.
					<br/><br/>
					I hope you enjoy reading, and leave a comment if you like! Thanks! ~ <a href="/author/austin-delamar">Austin</a>			
				</p>
				<br />
				
				<h2>This Blog is Custom Built</h2>
				<p> Yep! I have written the code from scratch myself. It has been a work in progress for some time. But all the code is available on the <a target="_blank" href="<%=Application.getSetting("sourceCode")%>">GitHub repo</a>.
				I've used blog services before, but decided to program it myself. And I know Its not perfect but I have been having fun with it! I typically work on it at least once a month.<br />
				</p>
				<blockquote>“I reinvent the wheel because mine is rounder.”<br />&nbsp;&nbsp;&nbsp;&nbsp;- Anonymous</blockquote>
				<br />
				
				
				<h2>Report an Issue</h2>
				<p>
				If you want to help inform me of any problems with this website, you can open an issue on the 
				<a target="_blank" href="<%=Application.getSetting("sourceCode")%>">GitHub repo</a>. Or if you like this website and wanted to deploy your own using this code, you can do that too.
				</p>
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="<%=Application.getSetting("sourceCode")%>/issues"><span class="icon-bug w3-large w3-margin-right"></span>Report Bug</a>
					&nbsp;&nbsp;
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="<%=Application.getSetting("sourceCode")%>"><span class="icon-embed w3-large w3-margin-right"></span>View Code</a>
				</p>
				<blockquote>“Talk is cheap. Show me the code.”<br />&nbsp;&nbsp;&nbsp;&nbsp;- Linus Torvalds</blockquote>
				<br />
				
				
				<h2>Contact</h2>
				<p>You can reach <%=Application.getSetting("name")%> via the social links on the bottom of every page, or send us an email at <a href="mailto:<%=Application.getSetting("email")%>"><%=Application.getSetting("email")%></a>.
				
				<br />
				
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>			
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>