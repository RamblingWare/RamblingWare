<!DOCTYPE HTML>
<html>
<head>
<#include "/WEB-INF/templates/fragment/meta/meta-post.ftl">
<meta name="author" content="${(post.author.name)!''}">
<meta name="description" content="${(post.description)!''}"/>
<meta name="keywords" content="<#iterator value="post.tags"><#property />,</#iterator><%=Application.getString("keywords")%>">

<!-- Facebook Open Graph -->
<meta property="fb:app_id" content="${(facebookAppId)!''}">
<meta property="og:url" content="/blog/${(post.uri)!''}">
<meta property="og:type" content="article">
<meta property="og:locale" content="en_US">
<meta property="og:title" content="${(post.title)!''}">
<meta property="og:image" content="${(post.thumbnail)!''}">
<meta property="og:image:type" content="image/png"/>
<meta property="og:image:width" content="640"/><!-- guessing -->
<meta property="og:image:height" content="427"/><!-- guessing -->
<meta property="og:description" content="${(post.description)!''}">
<meta property="og:site_name" content="${(name)!'Oddox'})!''}
<meta property="article:author" content="${(post.author.name)!''}">

<!-- Twitter Card -->
<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:site" content="${(twitterHandle)!''}">
<meta name="twitter:creator" content="${(twitterAuthorHandle)!''}">
<meta name="twitter:title" content="${(post.title)!''}">
<meta name="twitter:description" content="${(post.description)!''}">
<meta name="twitter:image" content="${(post.thumbnail)!''}">
<meta name="twitter:domain" content="${(domain)!''}">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="${(post.title)!''}">
<meta itemprop="description" content="${(post.description)!''}">
<meta itemprop="image" content="${(post.thumbnail)!''}"> 

<title>${(post.title)!''} - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/WEB-INF/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/WEB-INF/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<#if test="post.banner != null && !post.banner.trim().isEmpty()">
				<div class="w3-padding-0 w3-animate-opacity w3-margin-0">
					<img class="w3-img w3-round w3-card-4" style="width: 100%;" alt="Photo for ${(post.title)!''}" title="${(post.bannerCaption)!''}" src="${(post.banner)!''}" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'"/>
					<p class="w3-tiny w3-text-grey w3-margin-0" style="text-align: right;">${(post.bannerCaption)!''}</p>
				</div>
				</#if>
				
				<h1>${(post.title)!''}</h1>
				<% String content = (String) request.getAttribute("post.content");
					if(content == null || content.isEmpty()) {
						out.print("This post seems to be empty or failed to load correctly. Please try again later?");
					} else {
					out.print(content); } %>				
				<hr />
				<div class="w3-container w3-padding w3-margin-0 w3-center">

					<div class="w3-col s12 m6 l3">
						<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<#if test="post.author.thumbnail != null && !post.author.thumbnail.trim().isEmpty()">
								<img class="w3-round" alt="Profile" title="Author" style="vertical-align: middle;" src="${(post.author.thumbnail)!''}" height="24" width="24" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>&nbsp;
							</#if>
							<#else>
								<span class="icon-author w3-large w3-text-theme w3-padding-square"></span>
							</#else>
							<a href="/author/${(post.author.uri)!''}" title="Author" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">
							${(post.author.name)!''}</a>
						</p>
					</div>
					<div class="w3-col s12 m6 l3">
						<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<span class="icon-time w3-large w3-text-theme w3-padding-square"></span><a href="/year/${(post.publishYear)!''}" title="Published ${(post.publishDateTimeReadable)!''}" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">${(post.publishDateReadable)!''}</a>
						</p>
					</div>
					<div class="w3-col s12 m4 l3">
						<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<span class="icon-folder w3-large w3-text-theme w3-padding-square"></span><a href="/category/${(post.category)!''}" title="Category" class="w3-text-theme" style="vertical-align: middle;">${(post.category)!''}</a>
						</p>
					</div>
					<div class="w3-col s12 m8 l3">
						<#if test="post.tags != null && !post.tags.isEmpty()">
							<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<span class="icon-tag w3-large w3-text-theme w3-padding-square"></span><span class="w3-text-grey" style="vertical-align: middle;"><#iterator value="post.tags">
							<a class="w3-text-theme" title="<#property />" href="/tag/<#property />"><#property /></a>&nbsp;
							</#iterator></span></p>
						</#if>
					</div>
				</div>
				
				<div class="w3-container w3-padding-0 w3-margin-0 w3-animate-opacity no-print">
					<div class="w3-padding-16 w3-center">
						<p class="w3-small w3-text-grey w3-margin-0" style="vertical-align: middle;">Share this post.</p>	
						<a title="Share on Facebook" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-indigo w3-theme-l4 icon-facebook"
						target="_Blank" href="https://www.facebook.com/sharer.php?u=https%3A%2F%2F<%=Application.getString("domain")%>%2Fblog%2F${(post.uri)!''}"></a>				
						
						<a title="Share on Twitter"  class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-blue w3-theme-l4 icon-twitter"
						target="_Blank" href="https://twitter.com/intent/tweet?url=https%3A%2F%2F<%=Application.getString("domain")%>%2Fblog%2F${(post.uri)!''}"></a>
						
						<a title="Share on LinkedIn" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-blue-grey w3-theme-l4 icon-linkedin"
						 target="_Blank" href="https://www.linkedin.com/shareArticle?mini=true&amp;url=https%3A%2F%2F<%=Application.getString("domain")%>%2Fblog%2F${(post.uri)!''}'"></a>
						
						<a title="Share on Google+" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-red w3-theme-l4 icon-google"
						 target="_Blank" href="https://plus.google.com/share?url=https%3A%2F%2F<%=Application.getString("domain")%>%2Fblog%2F${(post.uri)!''}'"></a>
						
						<a title="Share on Reddit" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-black w3-theme-l4 icon-reddit"
						 target="_Blank" href="https://www.reddit.com/submit?url=https%3A%2F%2F<%=Application.getString("domain")%>%2Fblog%2F${(post.uri)!''}'"></a>
						
						<a title="Email to a friend" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-teal w3-theme-l4 icon-mail"
						 target="_Blank" href="mailto:?subject=Check%20out%20this%20Blog%20post&body=https%3A%2F%2F<%=Application.getString("domain")%>%2Fblog%2F${(post.uri)!''}"></a>
						
						<a title="Copy the permalink" href="javascript:void(0)" onclick="openPopup('share-popup')" class="w3-btn w3-round-large w3-large w3-padding-square  w3-hover-shadow w3-hover-green w3-theme-l4 icon-share"></a>
						
					</div>
					<div id="share-popup" class="w3-modal">
						<div class="w3-modal-content page-half w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Copy this link:
								<a title="Close" onclick="closePopup('share-popup')" class="icon-cross nounderline w3-text-black w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<input id="plink" name="plink" class="w3-input w3-round-large w3-border" onClick="this.setSelectionRange(0, this.value.length)" value="/blog/${(post.uri)!''}" type="text" /><br />
							</div>
						</div>
					</div>
				</div>
				<hr />
			</div>
			
			<#include "/WEB-INF/templates/fragment/archive.ftl">
		</div>
		
		<div class="page w3-row no-print">
			<div id="comments-left" class="w3-col m2 w3-hide-medium w3-hide-small w3-padding"></div>
			<div id="comments" class="w3-col m10 l8 w3-container w3-padding">
			<h3>Comments</h3>
			<blockquote class="w3-small">Disqus comments were removed. I plan to add a privacy-focused commenting system that doesn't track users across websites.<br><br>Email me if you have any suggestions!</blockquote>
			</div>
			<div id="comments-right" class="w3-col m2 w3-hide-small w3-padding"></div>
		</div>
		
	</article>

	<#include "/WEB-INF/templates/fragment/footer.ftl">
	<script type="text/javascript" src="/vendor/highlight/highlight.pack.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>