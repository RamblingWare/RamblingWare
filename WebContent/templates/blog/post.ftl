<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta-post.ftl">
<meta name="author" content="${(post.author.name)!''}"/>
<meta name="description" content="${(post.description)!''}"/>
<meta name="keywords" content="<#list post.tags as tag>${(tag)!''},</#list>${(keywords)!''}"/>

<!-- Facebook Open Graph -->
<meta property="og:url" content="${(url)!''}/blog/${(post.uri)!''}"/>
<meta property="og:type" content="article"/>
<meta property="og:title" content="${(post.title)!''}"/>
<meta property="og:image" content="${(post.thumbnail)!''}"/>
<meta property="og:description" content="${(post.description)!''}"/>
<meta property="og:site_name" content="${(name)!'Oddox'})!''}"/>
<meta property="article:author" content="${(post.author.name)!''}"/>

<!-- Twitter Card -->
<meta name="twitter:card" content="summary_large_image"/>
<meta name="twitter:site" content="${(twitterHandle)!''}"/>
<meta name="twitter:title" content="${(post.title)!''}"/>
<meta name="twitter:description" content="${(post.description)!''}"/>
<meta name="twitter:image" content="${(post.thumbnail)!''}"/>
<meta name="twitter:domain" content="${(domain)!''}"/>
<meta name="twitter:dnt" content="on">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(post.title)!''}"/>
<meta itemprop="description" content="${(post.description)!''}"/>
<meta itemprop="image" content="${(post.thumbnail)!''}"/>

<title>${(post.title)!''} - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<#if post.banner?has_content>
				<div class=" animate-opacity ">
					<img class="round" style="width: 100%;" alt="Photo for ${(post.title)!''}" title="${(post.bannerCaption)!''}" src="${(post.banner)!''}" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'"/>
					<p class="text-tertiary " style="text-align: right;">${(post.bannerCaption)!''}</p>
				</div>
				</#if>

				<h1>${(post.title)!''}</h1>
				<#if post.content?has_content>
					${(post.content)!'Error'}
				<#else>
					<p>This post seems to be empty or failed to load correctly. Please try again later?</p>
				</#if>
				<hr />
				<div class="row margin text-center">

					<div class="three columns">
						<p class="">
							<#if post.author.thumbnail?has_content>
								<img class="round" alt="Profile" title="Author" src="${(post.author.thumbnail)!''}" height="24" width="24" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>&nbsp;
							<#else>
								<span class="icon-author padding" title="Author"></span>
							</#if>
							<a href="/author/${(post.author.uri)!''}" title="Author" class="" style="vertical-align: middle; white-space:nowrap;">
							${(post.author.name)!''}</a>
						</p>
						<#if post.coauthors??>
							<p class="">
							<#list post.coauthors as coauthor>
								<#if coauthor.thumbnail?has_content>
									<img class="round" alt="Profile" title="Co-Author" src="${(coauthor.thumbnail)!''}" height="24" width="24" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>&nbsp;
								<#else>
									<span class="icon-author padding" title="Co-Author"></span>
								</#if>
								<a href="/author/${(coauthor.uri)!''}" title="Co-Author" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">
								${(coauthor.name)!''}</a>&nbsp;
							</#list>
							</p>
						</#if>
						<#if post.editors??>
							<p class="">
							<#list post.editors as editor>
								<#if editor.thumbnail?has_content>
									<img class="round" alt="Profile" title="Editor" src="${(editor.thumbnail)!''}" height="24" width="24" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>&nbsp;
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
							<span class="icon-time padding"></span><a href="/year/${(post.publishYear)?string('0')!''}" title="Published ${(post.publishDateTimeReadable)!''}" class="" style="vertical-align: middle; white-space:nowrap;">${(post.publishDateReadable)!''}</a>
						</p>
					</div>
					<div class="three columns">
						<p class="">
							<span class="icon-folder padding"></span><a href="/category/${(post.category)!''}" title="Category" class="text-secondary"><code>${(post.category)!''}</code></a>
						</p>
					</div>
					<div class="three columns">
						<#if post.tags??>
							<p class="">
							<span class="icon-tag padding"></span><span class="text-tertiary"><#list post.tags as tag>
							<a title="${(tag)!''}" href="/tag/${(tag)!''}" class="text-secondary"><code>${(tag)!''}</code></a>&nbsp;
							</#list></span></p>
						</#if>
					</div>
				</div>

				<div class="container no-print">
					<div class="padding text-center">
						<p class="text-secondary">Share this post.<br/>
							<a title="Share on Facebook" class="padding text-secondary icon-facebook"
							target="_Blank" href="https://www.facebook.com/sharer.php?u=https%3A%2F%2F${(domain)!''}%2Fblog%2F${(post.uri)!''}"></a>
	
							<a title="Share on Twitter"  class="padding text-secondary icon-twitter"
							target="_Blank" href="https://twitter.com/intent/tweet?url=https%3A%2F%2F${(domain)!''}%2Fblog%2F${(post.uri)!''}"></a>
	
							<a title="Share on LinkedIn" class="padding text-secondary icon-linkedin"
							 target="_Blank" href="https://www.linkedin.com/shareArticle?mini=true&amp;url=https%3A%2F%2F${(domain)!''}%2Fblog%2F${(post.uri)!''}"></a>
	
							<a title="Share on Google+" class="padding text-secondary icon-google"
							 target="_Blank" href="https://plus.google.com/share?url=https%3A%2F%2F${(domain)!''}%2Fblog%2F${(post.uri)!''}"></a>
	
							<a title="Share on Reddit" class="padding text-secondary icon-reddit"
							 target="_Blank" href="https://www.reddit.com/submit?url=https%3A%2F%2F${(domain)!''}%2Fblog%2F${(post.uri)!''}"></a>
	
							<a title="Email to a friend" class="padding text-secondary icon-mail"
							 target="_Blank" href="mailto:?subject=Check%20out%20this%20Blog%20post&body=https%3A%2F%2F${(domain)!''}%2Fblog%2F${(post.uri)!''}"></a>
						</p>
					</div>
				</div>
				<hr />
			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>

		<div id="comments" class="row no-print">
			<h3>Comments</h3>
			<blockquote class="text-small">Disqus comments were removed. I plan to add a privacy-focused commenting system that doesn't track users across websites.<br><br>Email me if you have any suggestions!</blockquote>
		</div>

	</article>

	<#include "/templates/fragment/footer.ftl">
	<script type="text/javascript" src="/vendor/highlight/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>
