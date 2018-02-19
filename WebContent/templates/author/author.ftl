<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta-post.ftl">
<meta name="author" content="${(author.name)!''}"/>
<meta name="description" content="${(author.description)!''}"/>
<meta name="keywords" content="${(keywords)!'Oddox'}"/>

<!-- Facebook Open Graph -->
<meta property="fb:app_id" content="${(facebookAppId)!''}">
<meta property="og:url" content="${(url)!''}/author/${(author.uri)!''}"/>
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
<meta name="twitter:site" content="${(twitterHandle)!''}">
<meta name="twitter:creator" content="${(twitterAuthorHandle)!''}">
<meta name="twitter:title" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta name="twitter:description" content="${(author.description)!''}"/>
<meta name="twitter:image" content="${(author.thumbnail)!''}"/>
<meta name="twitter:domain" content="${(domain)!''}">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta itemprop="description" content="${(author.description)!''}"/>
<meta itemprop="image" content="${(author.thumbnail)!''}"/>

<title>${(author.name)!'Unknown'} - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
							
				<h1><span class="w3-small w3-text-grey w3-right">${(author.createDateReadable)!'Unknown Date'}</span>
				${(author.name)!'Unknown'}</h1>
				
				<div class="w3-container w3-padding">
					<#if author.thumbnail?has_content>
						<img src="${(author.thumbnail)!''}" class="w3-round w3-card-4 w3-margin-right w3-margin-bottom w3-left" style="max-height:200px" alt="Profile" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>
					</#if>
					<#if author.content?has_content>
						${(author.content)!'Error'}
					<#else>
						<p>This author hasn't provided a bio yet.</p>
					</#if>
				</div>
				
				<div class="w3-container w3-padding-left w3-padding-right w3-center">
					<hr />
					<p class="w3-large"><a href="/author/">See more authors...</a></p>
				</div>
			</div>
			
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
	<script type="text/javascript" src="/vendor/highlight/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>