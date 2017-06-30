<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-post.jspf"%>
<meta name="author" content="<s:property value="post.author.name" />">
<meta name="description" content="<s:property value="post.description" />" />
<meta name="keywords" content="<s:iterator value="post.tags"><s:property />,</s:iterator><%=Application.getSetting("keywords")%>">

<!-- Facebook Open Graph -->
<meta property="fb:app_id" content="<%=Application.getSetting("facebookAppId")%>" />
<meta property="og:url" content="<%=Application.getSetting("url")%>/blog/<s:property value="post.uriName" />">
<meta property="og:type" content="article">
<meta property="og:locale" content="en_US">
<meta property="og:title" content="<s:property value="post.title" />">
<meta property="og:image" content="<s:property value="post.thumbnail" />">
<meta property="og:description" content="<s:property value="post.description" />">
<meta property="og:site_name" content="<%=Application.getSetting("name")%>" />
<meta property="article:author" content="<s:property value="post.author.name" />">

<!-- Twitter Card -->
<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:site" content="<%=Application.getSetting("twitterHandle")%>">
<meta name="twitter:creator" content="<%=Application.getSetting("twitterAuthorHandle")%>">
<meta name="twitter:title" content="<s:property value="post.title" />">
<meta name="twitter:description" content="<s:property value="post.description" />">
<meta name="twitter:image" content="<s:property value="post.thumbnail" />">
<meta name="twitter:domain" content="<%=Application.getSetting("domain")%>">

<!-- Google+ Schema.org -->
<meta itemprop="name" content="<s:property value="post.title" />">
<meta itemprop="description" content="<s:property value="post.description" />">
<meta itemprop="image" content="<s:property value="post.thumbnail" />"> 

<title><s:property value="post.title" /> - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<s:if test="post.banner != null && !post.banner.trim().isEmpty()">
				<div class="w3-padding-0 w3-animate-opacity w3-margin-0">
					<img class="w3-img w3-round w3-card-4" style="width: 100%;" alt="Photo for <s:property value="post.title" />" title="<s:property value="post.bannerCaption" />" src="<s:property value="post.banner" />" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'"/>
					<p class="w3-tiny w3-text-grey w3-margin-0" style="text-align: right;"><s:property value="post.bannerCaption" /></p>
				</div>
				</s:if>
				
				<h1><s:property value="post.title" /></h1>
				<% String htmlContent = (String) request.getAttribute("post.htmlContent");
					if(htmlContent == null || htmlContent.isEmpty()) {
						out.print("This post seems to be empty or failed to load correctly. Please try again later?");
					} else {
					out.print(htmlContent); } %>				
				<hr />
				<div class="w3-container w3-padding w3-margin-0 w3-center">

					<div class="w3-col s12 m6 l3">
						<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<s:if test="post.author.thumbnail != null && !post.author.thumbnail.trim().isEmpty()">
								<img class="w3-round" alt="Profile" title="Author" style="vertical-align: middle;" src="<s:property value="post.author.thumbnail" />" height="24" width="24" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>&nbsp;
							</s:if>
							<s:else>
								<span class="icon-author w3-large w3-text-theme w3-padding-square"></span>
							</s:else>
							<a href="/author/<s:property value="post.author.uriName" />" title="Author" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">
							<s:property value="post.author.name" /></a>
						</p>
					</div>
					<div class="w3-col s12 m6 l3">
						<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<span class="icon-time w3-large w3-text-theme w3-padding-square"></span><a href="/year/<s:property value="post.publishYear" />" title="Date Published" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"><s:property value="post.publishDateReadable" /></a>
						</p>
					</div>
					<div class="w3-col s12 m4 l3">
						<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<span class="icon-folder w3-large w3-text-theme w3-padding-square"></span><a href="/category/<s:property value="post.category" />" title="Category" class="w3-text-theme" style="vertical-align: middle;"><s:property value="post.category" /></a>
						</p>
					</div>
					<div class="w3-col s12 m8 l3">
						<s:if test="post.tags != null && !post.tags.isEmpty()">
							<p class="w3-large w3-padding-0" style="vertical-align: middle;">
							<span class="icon-tag w3-large w3-text-theme w3-padding-square"></span><span class="w3-text-grey" style="vertical-align: middle;"><s:iterator value="post.tags">
							<a class="w3-text-theme" title="<s:property />" href="/tag/<s:property />"><s:property /></a>&nbsp;
							</s:iterator></span></p>
						</s:if>
					</div>
				</div>
				
				<div class="w3-container w3-padding-0 w3-margin-0 w3-animate-opacity no-print">
					<div class="w3-padding-16 w3-center">
						<p class="w3-small w3-text-grey w3-margin-0" style="vertical-align: middle;">Share this post.</p>	
						<a title="Share on Facebook" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-indigo w3-theme-l4 icon-facebook"
						target="_Blank" href="https://www.facebook.com/sharer.php?u=https%3A%2F%2F<%=Application.getSetting("domain")%>%2Fblog%2F<s:property value="post.uriName" />"></a>				
						
						<a title="Share on Twitter"  class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-blue w3-theme-l4 icon-twitter"
						target="_Blank" href="https://twitter.com/intent/tweet?url=https%3A%2F%2F<%=Application.getSetting("domain")%>%2Fblog%2F<s:property value="post.uriName" />"></a>
						
						<a title="Share on LinkedIn" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-blue-grey w3-theme-l4 icon-linkedin"
						 target="_Blank" href="https://www.linkedin.com/shareArticle?mini=true&amp;url=https%3A%2F%2F<%=Application.getSetting("domain")%>%2Fblog%2F<s:property value="post.uriName" />'"></a>
						
						<a title="Share on Google+" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-red w3-theme-l4 icon-google"
						 target="_Blank" href="https://plus.google.com/share?url=https%3A%2F%2F<%=Application.getSetting("domain")%>%2Fblog%2F<s:property value="post.uriName" />'"></a>
						
						<a title="Share on Reddit" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-black w3-theme-l4 icon-reddit"
						 target="_Blank" href="https://www.reddit.com/submit?url=https%3A%2F%2F<%=Application.getSetting("domain")%>%2Fblog%2F<s:property value="post.uriName" />'"></a>
						
						<a title="Email to a friend" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-teal w3-theme-l4 icon-mail"
						 target="_Blank" href="mailto:?subject=Check%20out%20this%20<%=Application.getSetting("name")%>%20post&body=https%3A%2F%2F<%=Application.getSetting("domain")%>%2Fblog%2F<s:property value="post.uriName" />"></a>
						
						<a title="Copy the permalink" href="javascript:void(0)" onclick="openPopup('share-popup')" class="w3-btn w3-round-large w3-large w3-padding-square  w3-hover-shadow w3-hover-green w3-theme-l4 icon-share"></a>
						
					</div>
					<div id="share-popup" class="w3-modal">
						<div class="w3-modal-content page-half w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Copy this link:
								<a title="Close" onclick="closePopup('share-popup')" class="icon-cross nounderline w3-text-black w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<input id="plink" name="plink" class="w3-input w3-round-large w3-border" onClick="this.setSelectionRange(0, this.value.length)" value="<%=Application.getSetting("url")%>/blog/<s:property value="post.uriName" />" type="text" /><br />
							</div>
						</div>
					</div>
				</div>
				<hr />
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>			
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

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>