<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta-post.ftl">
<meta name="author" content="${(context.author.name)!''}"/>
<meta name="description" content="${(context.author.description)!''}"/>
<meta name="keywords" content="${(context.keywords)!'Oddox'}"/>

<!-- Facebook Open Graph -->
<meta property="og:url" content="${(context.url)!''}/author/${(context.author.uri)!''}"/>
<meta property="og:type" content="article"/>
<meta property="og:title" content="${(context.author.name)!''} - ${(context.name)!'Oddox'}"/>
<meta property="og:image" content="${(context.author.thumbnail)!''}"/>
<meta property="og:description" content="${(context.author.description)!''}"/>
<meta property="og:site_name" content="${(context.name)!'Oddox'}"/>
<meta property="article:author" content="${(context.author.name)!''}"/>

<!-- Twitter Card -->
<meta name="twitter:card" content="summary"/>
<meta name="twitter:site" content="${(context.twitterHandle)!''}">
<meta name="twitter:title" content="${(context.author.name)!''} - ${(name)!'Oddox'}"/>
<meta name="twitter:description" content="${(context.author.description)!''}"/>
<meta name="twitter:image" content="${(context.author.thumbnail)!''}"/>
<meta name="twitter:domain" content="${(context.domain)!''}">
<meta name="twitter:dnt" content="on">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(context.author.name)!''} - ${(context.name)!'Oddox'}"/>
<meta itemprop="description" content="${(context.author.description)!''}"/>
<meta itemprop="image" content="${(context.author.thumbnail)!''}"/>

<title>${(context.author.name)!'Unknown'} - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1>${(context.author.name)!'Unknown'}</h1>
				<span class="text-tertiary right">${(context.author.createDateReadable)!'Unknown Date'}</span>

				<div class="w3-container w3-padding">
					<#if context.author.thumbnail?has_content>
						<img src="${(context.author.thumbnail)!''}" class="round border margin-right margin-bottom left" style="max-height:200px" alt="Profile" onerror="this.src='/blog/images/placeholder-200.png';this.title='Failed to load image.'"/>
					</#if>
					<#if context.author.content?has_content>
						${(context.author.content)!'Error'}
					<#else>
						<p>This author hasn't provided a bio yet.</p>
					</#if>
				</div>

				<div class="text-center">
					<p><a href="/blog/author/">See more authors...</a></p>
				</div>
			</div>

			<#include "../fragment/archive.ftl">
		</div>
	</article>

	<#include "../fragment/footer.ftl">
	<script type="text/javascript" src="/blog/vendor/highlight/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>
