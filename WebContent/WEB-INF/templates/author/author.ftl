<!DOCTYPE HTML>
<html>
<head>
<#include "/WEB-INF/templates/fragment/meta/meta-post.ftl">
<meta name="author" content="${(author.name)!''}"/>
<meta name="description" content="${(author.description)!''}"/>
<meta name="keywords" content="${(keywords)!'Oddox'}"/>

<!-- Facebook Open Graph -->
<meta property="fb:app_id" content="968522543222407"/>
<meta property="og:url" content="https://www.ramblingware.com/author/${(author.uri)!''}"/>
<meta property="og:type" content="article"/>
<meta property="og:locale" content="en_US"/>
<meta property="og:title" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta property="og:image" content="${(author.thumbnail)!''}"/>
<meta property="og:image:type" content="image/png"/>
<meta property="og:image:width" content="256"/><!-- guessing -->
<meta property="og:image:height" content="256"/><!-- guessing -->
<meta property="og:description" content="${(author.description)!''}"/>
<meta property="og:site_name" content="${(name)!'Oddox'}"/>
<meta property="article:author" content="${(author.name)!''}"/>

<!-- Twitter Card -->
<meta name="twitter:card" content="summary"/>
<meta name="twitter:site" content="@amdelamar"/>
<meta name="twitter:creator" content="@amdelamar"/>
<meta name="twitter:title" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta name="twitter:description" content="${(author.description)!''}"/>
<meta name="twitter:image" content="${(author.thumbnail)!''}"/>
<meta name="twitter:domain" content="www.ramblingware.com"/>

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta itemprop="description" content="${(author.description)!''}"/>
<meta itemprop="image" content="${(author.thumbnail)!''}"/>

<title>${(author.name)!''} - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/WEB-INF/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/WEB-INF/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
							
				<h1><span class="w3-small w3-text-grey w3-right">${(author.createDateReadable)!'Unknown Date'}</span>
				${(author.name)!'Unknown'}</h1>
				
				<div class="w3-container w3-padding">
					<img src="${(author.thumbnail)!''}" class="w3-round w3-card-4 w3-margin-right w3-margin-bottom w3-left" style="max-height:200px" alt="Profile" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>
					<#if author.content??>
						${(author.content)!"Error"}
					<#else>
						<p>This author hasn't provided a bio yet.</p>
					</#if>
				</div>
				
				<div class="w3-container w3-padding-left w3-padding-right w3-center">
					<hr />
					<p class="w3-large"><a href="/author/">See more authors...</a></p>
				</div>
			</div>
			
			<#include "/WEB-INF/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/WEB-INF/templates/fragment/footer.ftl">
	<script type="text/javascript" src="/vendor/highlight/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>