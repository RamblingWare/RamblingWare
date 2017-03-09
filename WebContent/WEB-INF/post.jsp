<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<%@include file="/WEB-INF/fragment/meta-post.jspf"%>
<meta name="author" content="<s:property value="author" />">
<meta name="description" content="<s:property value="description" />" />

<meta property="og:url" content="https://www.ramblingware.com/blog/post/<s:property value="uriName" />">
<meta property="og:type" content="blog">
<meta property="og:locale" content="en_US">
<meta property="og:title" content="<s:property value="title" />">
<meta property="og:image" content="<s:property value="thumbnail" />">
<meta property="og:description" content="<s:property value="description" />">

<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:site" content="https://www.ramblingware.com/blog/post/<s:property value="uriName" />">
<meta name="twitter:creator" content="<s:property value="author" />">
<meta name="twitter:title" content="<s:property value="title" />">
<meta name="twitter:description" content="<s:property value="description" />">
<meta name="twitter:image" content="<s:property value="thumbnail" />">

<title><s:property value="title" /> - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->

	<!-- CONTENT BEGIN -->
	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<% if(request.getAttribute("banner")!=null && !request.getAttribute("banner").toString().isEmpty()) { %>
				<div class="w3-padding-0 w3-animate-opacity w3-margin-0">
					<img class="w3-img w3-round w3-card-4" style="width: 100%;" alt="Photo for <s:property value="title" />" title="<s:property value="bannerCaption" />" src="<s:property value="banner" />">
					<p class="footnote w3-tiny"><s:property value="bannerCaption" /></p>
				</div>
				<% } %>
				
				<h1><s:property value="title" /></h1>
				
				<% out.print(request.getAttribute("htmlContent")); %>
				
				<br /><br />
				<hr />
				<div class="w3-container w3-padding w3-margin-0 w3-center">

					<div class="w3-col s12 m4 l4">
						<p class="w3-large w3-padding-0 w3-margin-0">
							<img class="w3-round" style="height: 33px; vertical-align: middle;" src="<s:property value="authorThumbnail" />">
							<a href="/author/<s:property value="authorUri" />" title="Author" class="w3-text-theme" style="vertical-align: middle;">
							<s:property value="author" /></a>
						</p>
					</div>
					<div class="w3-col s12 m4 l4">
						<p class="w3-large w3-text-theme w3-padding-0 w3-margin-0" title="Date Published">
							<s:property value="createDate" />
						</p>
					</div>
					<div class="w3-col s12 m4 l4">
						<s:if test="tags != null && !tags.isEmpty()">
							<s:iterator value="tags">
							&nbsp;<a class="tag w3-tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" title="<s:property />" href="/blog/search?tag=<s:property />"><s:property /></a>
							</s:iterator>
						</s:if>
					</div>
				</div>

				<hr class="w3-hide-medium w3-hide-large" />
				
				<div class="w3-container w3-padding-0 w3-margin-0 w3-animate-opacity no-print">
					<div class="w3-padding-16 w3-center">	
						<a title="Share on Facebook" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-indigo w3-theme-l4 icon-facebook"
						target="_Blank" href="https://www.facebook.com/sharer.php?u=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />"></a>				
						
						<a title="Share on Twitter"  class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-blue w3-theme-l4 icon-twitter"
						target="_Blank" href="https://twitter.com/intent/tweet?url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />"></a>
						
						<a title="Share on LinkedIn" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-blue-grey w3-theme-l4 icon-linkedin2"
						 target="_Blank" href="https://www.linkedin.com/shareArticle?mini=true&amp;url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />'"></a>
						
						<a title="Share on Google+" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-red w3-theme-l4 icon-google-plus"
						 target="_Blank" href="https://plus.google.com/share?url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />'"></a>
						
						<a title="Share on Reddit" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-black w3-theme-l4 icon-reddit"
						 target="_Blank" href="https://www.reddit.com/submit?url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />'"></a>
						
						<a title="Email to a friend" class="w3-btn w3-round-large w3-large w3-padding-square w3-hover-shadow w3-hover-teal w3-theme-l4 icon-mail2"
						 target="_Blank" href="mailto:?subject=Check%20out%20this%20RamblingWare%20post&body=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />"></a>
						
						<a title="Copy the permalink" href="javascript:void(0)" onclick="openPopup()" class="w3-btn w3-round-large w3-large w3-padding-square  w3-hover-shadow w3-hover-green w3-theme-l4 icon-share2"></a>
						
					</div>
					<div id="share-popup" class="w3-modal">
						<div class="w3-modal-content page-half w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Copy this link:
								<a title="Close" onclick="closePopup()" class="icon-cross w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<input id="plink" name="plink" class="w3-input w3-round-large w3-border" onClick="this.setSelectionRange(0, this.value.length)" value="https://www.ramblingware.com/blog/post/<s:property value="uriName" />" type="text" /><br />
							</div>
						</div>
					</div>
					<script>
					// Get the modal
					var modal = document.getElementById('share-popup');
					
					// When the user clicks anywhere outside of the modal, close it
					window.onclick = function(event) {
					    if (event.target == modal) {
					        modal.style.display = "none";
					    }
					}
					function openPopup() {
						document.getElementById('share-popup').style.display='block';
						var plink = document.getElementById('plink');
						plink.focus();
						plink.setSelectionRange(0, plink.value.length);
					}
					function closePopup() {
						document.getElementById('share-popup').style.display='none';
					}
					</script>

				</div>
				<hr />
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
			<!-- RECENTLY VIEWED BEGIN -->
			<%@include file="/WEB-INF/fragment/recentlyviewed.jspf" %>
			<!-- RECENTLY VIEWED END -->
			
		</div>
		
		<!-- COMMENTS BEGIN -->
		<div class="page w3-row no-print">
			<div id="comments-left" class="w3-col m2 w3-hide-medium w3-hide-small w3-padding"></div>
			<div id="comments" class="w3-col m10 l8 w3-container w3-padding">
			<h3>Comments</h3>
			<div id="disqus_thread"></div>
			<script>
			    var disqus_config = function () {
			        this.page.url = "https://www.ramblingware.com/blog/post/<%=request.getAttribute("uriName") %>";
			        this.page.identifier = "<%=request.getAttribute("uriName") %>";
			        this.page.title = "<%=request.getAttribute("title") %>";
			    };
			    (function() {
			        var d = document, s = d.createElement('script');
			        s.src = '//ramblingware.disqus.com/embed.js';
			        s.setAttribute('data-timestamp', +new Date());
			        (d.head || d.body).appendChild(s);
			    })();
			</script>
			<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
			</div>
			<div id="comments-right" class="w3-col m2 w3-hide-small w3-padding"></div>
		</div>
		<!-- COMMENTS END -->
		
	</article>
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>