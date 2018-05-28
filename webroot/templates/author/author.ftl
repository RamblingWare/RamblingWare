<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta-post.ftl">
<meta name="author" content="${(author.name)!''}"/>
<meta name="description" content="${(author.description)!''}"/>
<meta name="keywords" content="${(keywords)!'Oddox'}"/>

<!-- Facebook Open Graph -->
<meta property="og:url" content="${(url)!''}/author/${(author.uri)!''}"/>
<meta property="og:type" content="article"/>
<meta property="og:title" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta property="og:image" content="${(author.thumbnail)!''}"/>
<meta property="og:description" content="${(author.description)!''}"/>
<meta property="og:site_name" content="${(name)!'Oddox'}"/>
<meta property="article:author" content="${(author.name)!''}"/>

<!-- Twitter Card -->
<meta name="twitter:card" content="summary"/>
<meta name="twitter:site" content="${(twitterHandle)!''}">
<meta name="twitter:title" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta name="twitter:description" content="${(author.description)!''}"/>
<meta name="twitter:image" content="${(author.thumbnail)!''}"/>
<meta name="twitter:domain" content="${(domain)!''}">
<meta name="twitter:dnt" content="on">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(author.name)!''} - ${(name)!'Oddox'}"/>
<meta itemprop="description" content="${(author.description)!''}"/>
<meta itemprop="image" content="${(author.thumbnail)!''}"/>

<title>${(author.name)!'Unknown'} - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1>${(author.name)!'Unknown'}</h1>
				<span class="text-tertiary right">${(author.createDateReadable)!'Unknown Date'}</span>

				<div class="w3-container w3-padding">
					<#if author.thumbnail?has_content>
						<img src="${(author.thumbnail)!''}" class="round border margin-right margin-bottom left" style="max-height:200px" alt="Profile" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>
					</#if>
					<#if author.content?has_content>
						${(author.content)!'Error'}
					<#else>
						<p>This author hasn't provided a bio yet.</p>
					</#if>
				</div>

				<div class="text-center">
					<p><a href="/author/">See more authors...</a></p>
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
