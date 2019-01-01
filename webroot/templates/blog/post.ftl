<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta-post.ftl">
<meta name="author" content="${(context.post.author.name)!''}"/>
<meta name="description" content="${(context.post.description)!''}"/>
<meta name="keywords" content="<#list context.post.tags as tag>${(tag)!''},</#list>${(context.keywords)!''}"/>

<!-- Facebook Open Graph -->
<meta property="og:url" content="${(context.url)!''}/blog/${(context.post.uri)!''}"/>
<meta property="og:type" content="article"/>
<meta property="og:title" content="${(context.post.title)!''}"/>
<meta property="og:image" content="${(context.post.thumbnail)!''}"/>
<meta property="og:description" content="${(context.post.description)!''}"/>
<meta property="og:site_name" content="${(context.name)!'Oddox'})!''}"/>
<meta property="article:author" content="${(context.post.author.name)!''}"/>

<!-- Twitter Card -->
<meta name="twitter:card" content="summary_large_image"/>
<meta name="twitter:title" content="${(context.post.title)!''}"/>
<meta name="twitter:description" content="${(context.post.description)!''}"/>
<meta name="twitter:image" content="${(context.post.thumbnail)!''}"/>
<meta name="twitter:domain" content="${(context.domain)!''}"/>
<meta name="twitter:dnt" content="on">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(context.post.title)!''}"/>
<meta itemprop="description" content="${(context.post.description)!''}"/>
<meta itemprop="image" content="${(context.post.thumbnail)!''}"/>

<title>${(context.post.title)!'Unknown'} - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<#if context.post.banner?has_content>
				<div class=" animate-opacity ">
					<img class="shade round" style="width: 100%;" alt="Photo for ${(context.post.title)!''}" title="${(context.post.bannerCaption)!''}" src="${(context.post.banner)!''}" onerror="this.src='/blog/images/placeholder-640.png';this.title='Failed to load image.'"/>
				</div>
				</#if>

				<h1 class="margin-top">${(context.post.title)!''}</h1>
				<#if context.post.content?has_content>
					${(context.post.content)!'Error'}
				<#else>
					<p>This post seems to be empty or failed to load correctly. Please try again later?</p>
				</#if>
				<hr />
				<div class="row margin text-center">

					<div class="three columns">
						<p class="">
							<#if context.post.author.thumbnail?has_content>
								<img class="round" alt="Profile" title="Author" src="${(context.post.author.thumbnail)!''}" height="24" width="24" onerror="this.src='/blog/images/placeholder-200.png';this.title='Failed to load image.'"/>&nbsp;
							<#else>
								<span class="icon-author padding" title="Author"></span>
							</#if>
							<a href="/author/${(context.post.author.uri)!''}" title="Author" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">
							${(context.post.author.name)!''}</a>
						</p>
						<#if context.post.coauthors??>
							<p class="">
							<#list context.post.coauthors as coauthor>
								<#if coauthor.thumbnail?has_content>
									<img class="round" alt="Profile" title="Co-Author" src="${(coauthor.thumbnail)!''}" height="24" width="24" onerror="this.src='/blog/images/placeholder-200.png';this.title='Failed to load image.'"/>&nbsp;
								<#else>
									<span class="icon-author padding" title="Co-Author"></span>
								</#if>
								<a href="/author/${(coauthor.uri)!''}" title="Co-Author" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">
								${(coauthor.name)!''}</a>&nbsp;
							</#list>
							</p>
						</#if>
						<#if context.post.editors??>
							<p class="">
							<#list context.post.editors as editor>
								<#if editor.thumbnail?has_content>
									<img class="round" alt="Profile" title="Editor" src="${(editor.thumbnail)!''}" height="24" width="24" onerror="this.src='/blog/images/placeholder-200.png';this.title='Failed to load image.'"/>&nbsp;
								<#else>
									<span class="icon-author padding" title="Editor"></span>
								</#if>
								<a href="/author/${(editor.uri)!''}" title="Editor" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">
								${(editor.name)!''}</a>&nbsp;
							</#list>
							</p>
						</#if>
					</div>
					<div class="three columns">
						<p class="">
							<span class="icon-time padding"></span><a href="/year/${(context.post.publishYear)?string('0')!''}" title="Published ${(context.post.publishDateTimeReadable)!''}" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(context.post.publishDateReadable)!''}</a>
						</p>
					</div>
					<div class="three columns">
						<p class="">
							<span class="icon-folder padding"></span><a href="/category/${(context.post.category)!''}" title="${(context.post.category)!''}" class="text-secondary"><code>${(context.post.category)!''}</code></a>
						</p>
					</div>
					<div class="three columns">
						<#if context.post.tags??>
							<p class="">
							<span class="icon-tag padding"></span><span class="text-tertiary"><#list context.post.tags as tag>
							<a title="${(tag)!''}" href="/tag/${(tag)!''}" class="text-secondary"><code>${(tag)!''}</code></a>&nbsp;
							</#list></span></p>
						</#if>
					</div>
				</div>

				<div class="container no-print">
					<div class="padding text-center">
						<p class="text-secondary">Share this post.<br/>
							<a title="Share on Facebook" class="padding text-secondary icon-facebook"
							target="_Blank" href="https://www.facebook.com/sharer.php?u=https%3A%2F%2F${(context.domain)!''}%2Fblog%2F${(context.post.uri)!''}"></a>
	
							<a title="Share on Twitter"  class="padding text-secondary icon-twitter"
							target="_Blank" href="https://twitter.com/intent/tweet?url=https%3A%2F%2F${(context.domain)!''}%2Fblog%2F${(context.post.uri)!''}"></a>
	
							<a title="Share on LinkedIn" class="padding text-secondary icon-linkedin"
							 target="_Blank" href="https://www.linkedin.com/shareArticle?mini=true&amp;url=https%3A%2F%2F${(context.domain)!''}%2Fblog%2F${(context.post.uri)!''}"></a>
	
							<a title="Share on Google+" class="padding text-secondary icon-google"
							 target="_Blank" href="https://plus.google.com/share?url=https%3A%2F%2F${(context.domain)!''}%2Fblog%2F${(context.post.uri)!''}"></a>
	
							<a title="Share on Reddit" class="padding text-secondary icon-reddit"
							 target="_Blank" href="https://www.reddit.com/submit?url=https%3A%2F%2F${(context.domain)!''}%2Fblog%2F${(context.post.uri)!''}"></a>
	
							<a title="Email to a friend" class="padding text-secondary icon-mail"
							 target="_Blank" href="mailto:?subject=Check%20out%20this%20Blog%20post&body=https%3A%2F%2F${(context.domain)!''}%2Fblog%2F${(context.post.uri)!''}"></a>
						</p>
					</div>
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
