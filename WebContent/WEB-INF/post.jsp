<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-post.jspf"%>
<meta name="author" content="<s:property value="author" />">
<meta name="description" content="<s:property value="description" />" />
<meta name="og:url" content="https://www.ramblingware.com/blog/post/<s:property value="uriName" />">
<meta name="og:type" content="website">
<meta name="og:title" content="<s:property value="title" />">
<meta name="og:image" content="<s:property value="thumbnail" />">
<meta name="og:description" content="<s:property value="description" />">

<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:site" content="https://www.ramblingware.com/blog/post/<s:property value="uriName" />">
<meta name="twitter:creator" content="<s:property value="author" />">
<meta name="twitter:title" content="<s:property value="title" />">
<meta name="twitter:description" content="<s:property value="description" />">
<meta name="twitter:image" content="<s:property value="thumbnail" />">
<!-- META_END -->
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
				
				<div class="w3-container w3-margin-0 w3-padding-0">
					<p><span class="w3-right">&nbsp;&nbsp;<a class="footnote" href="#comments"><span class="disqus-comment-count" data-disqus-identifier="<%=request.getAttribute("uriName") %>"></span></a></span></p>
				</div>
				
				<% out.print(request.getAttribute("htmlContent")); %>
				
				<hr />
				<div class="w3-container w3-padding w3-margin-0 w3-animate-opacity">
				<div class="w3-col s12 m6 l6 w3-padding-left">
				<img class="w3-round w3-margin-left w3-left" style="width: 40px; height: 40px" alt="Profile Picture" src="https://i.imgur.com/21fYx3ks.jpg">
				<p class="footnote w3-left w3-small w3-padding w3-padding-left">
				<b>Author:</b> <s:property value="author" /><br />
				<a href="/blog">View more posts by this author.</a>
				</p>
				</div>
				<div class="w3-col s12 m6 l6 w3-padding-left">
				<p class="footnote w3-small">
					<b>Published:</b> <s:property value="createDate" />
				</p>
				<span class="footnote w3-small"><b>Tags:</b></span>
				<s:if test="tags != null && !tags.isEmpty()">
					<s:iterator value="tags">
						&nbsp;<a class="tag w3-tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow" href="/blog/search?tag=<s:property />"><s:property /></a>
					</s:iterator>
				</s:if>
				</div></div>
				<hr />				
				
				<div class="w3-container w3-padding-0 w3-margin-0 w3-animate-opacity no-print">
					<div class="w3-col s12 m6 l6 w3-padding-16 w3-center">
					
						<div class="w3-col s12 m6 l5 w3-padding-4">
						<button class="icon-undo w3-btn w3-card w3-round w3-light-grey" onclick="window.location.href='/blog/'">View all posts</button>
						</div>
						<div class="w3-col s12 m8 l4 w3-padding-4">
						<button class="icon-redo w3-btn w3-card w3-round w3-light-grey" onclick="window.location.href='/'">View newest post</button>
						</div>
					</div>
					<div class="w3-col s12 m6 l6 w3-padding-16 w3-center">	
						<a title="Share on Facebook" 
						target="_Blank" href="https://www.facebook.com/sharer.php?u=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-facebook.png" alt="Facebook" /></a>				
						
						<a title="Share on Twitter" 
						target="_Blank" href="https://twitter.com/intent/tweet?url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-twitter.png" alt="Twitter" /></a>
						
						<a title="Share on LinkedIn"
						 target="_Blank" href="https://www.linkedin.com/shareArticle?mini=true&amp;url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />'">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-linkedin.png" alt="LinkedIn" /></a>
						
						<a title="Share on Google+"
						 target="_Blank" href="https://plus.google.com/share?url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />'">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-google.png" alt="Google+" /></a>
						
						<a title="Share on Reddit"
						 target="_Blank" href="https://www.reddit.com/submit?url=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />'">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-reddit.png" alt="Reddit" /></a>
						
						<a title="Email to a friend"
						 target="_Blank" href="mailto:?subject=Check%20out%20this%20RamblingWare%20post&body=https%3A%2F%2Fwww.ramblingware.com%2Fblog%2Fpost%2F<s:property value="uriName" />">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-email.png" alt="Email" /></a>
						
						<a title="Copy the permalink" href="javascript:void(0)" onclick="openPopup()">
						<img class="w3-hover-shadow w3-hover-theme w3-circle" src="/img/btn-link.png" alt="Permalink" /></a>
						
					</div>
					
					<div id="share-popup" class="w3-modal">
						<div class="w3-modal-content page-half w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Copy this link:
								<a title="Close" onclick="closePopup()" class="icon-delete w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
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
				<br />
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
			<br />
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