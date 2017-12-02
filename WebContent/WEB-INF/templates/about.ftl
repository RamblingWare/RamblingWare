<!DOCTYPE HTML>
<html>
<head>
<#include "/WEB-INF/templates/fragment/meta/meta.ftl">

<title>About - ${(name)!"Oddox"}</title>
</head>
<body class="w3-theme-dark">

	<#include "/WEB-INF/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/WEB-INF/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
							
				<h1><span class="w3-small w3-text-grey w3-right">June 17th, 2017</span>
				About</h1>
				
				<p>
					${(name)!"Oddox"} is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.
					<br/><br/>
					I cover topics around web development, web security, and programming. Really, anything that I can rant about.
					<br/><br/>
					Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.
					<br/><br/>
					I hope you enjoy reading, and share a post if you like! Thanks! ~ <a href="/author/austin-delamar">Austin</a>			
				</p>
				<br />
				
				<h2>This Blog is Custom Built</h2>
				<p> Yep! I have written the code from scratch myself. It has been a work in progress for some time. But all the code is available on the <a target="_blank" href="https://github.com/oddoxorg/oddox/">GitHub repo</a>.
				I've used blog services before, but decided to program it myself. And I know Its not perfect but I have been having fun with it! I typically work on it at least once a month.<br />
				</p>
				<br />
				
				
				<h2>Report an Issue</h2>
				<p>
				If you want to help inform me of any problems with this website, you can open an issue on the 
				<a target="_blank" href="https://github.com/oddoxorg/oddox/">GitHub repo</a>. Or if you like this website and wanted to deploy your own using this code, you can do that too.
				</p>
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="https://github.com/oddoxorg/oddox//issues"><span class="icon-bug w3-large w3-margin-right"></span>Report Bug</a>
					&nbsp;&nbsp;
					<a class="w3-btn w3-card w3-round w3-light-grey" target="_blank" href="https://github.com/oddoxorg/oddox/"><span class="icon-embed w3-large w3-margin-right"></span>View Code</a>
				</p>
				<blockquote>â€œTalk is cheap. Show me the code.â€�<br />&nbsp;&nbsp;&nbsp;&nbsp;- Linus Torvalds</blockquote>
				<br />
				
				
				<h2>Contact</h2>
				<p>You can reach ${(name)!"Oddox"} via the social links on the bottom of every page, or send us an email at <a href="mailto:info@ramblingware.com">info@ramblingware.com</a>.
				
				<br />
				
			</div>
			
			<#include "/WEB-INF/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/WEB-INF/templates/fragment/footer.ftl">
</body>
</html>