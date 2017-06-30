<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-post.jspf"%>
<meta name="author" content="<s:property value="author.name" />"/>
<meta name="description" content="<s:property value="author.description" />"/>
<meta name="keywords" content="<%=Application.getSetting("keywords")%>"/>

<!-- Facebook Open Graph -->
<meta property="fb:app_id" content="<%=Application.getSetting("facebookAppId")%>"/>
<meta property="og:url" content="<%=Application.getSetting("url")%>/author/<s:property value="author.uriName" />"/>
<meta property="og:type" content="article"/>
<meta property="og:locale" content="en_US"/>
<meta property="og:title" content="<s:property value="author.name" /> - <%=Application.getSetting("name")%>"/>
<meta property="og:image" content="<s:property value="author.thumbnail" />"/>
<meta property="og:description" content="<s:property value="author.description" />"/>
<meta property="og:site_name" content="<%=Application.getSetting("name")%>"/>
<meta property="article:author" content="<s:property value="author.name" />"/>

<!-- Twitter Card -->
<meta name="twitter:card" content="summary"/>
<meta name="twitter:site" content="<%=Application.getSetting("twitterHandle")%>"/>
<meta name="twitter:creator" content="<%=Application.getSetting("twitterAuthorHandle")%>"/>
<meta name="twitter:title" content="<s:property value="author.name" /> - <%=Application.getSetting("name")%>"/>
<meta name="twitter:description" content="<s:property value="author.description" />"/>
<meta name="twitter:image" content="<s:property value="author.thumbnail" />"/>
<meta name="twitter:domain" content="<%=Application.getSetting("domain")%>"/>

<!-- Google+ Schema.org -->
<meta itemprop="name" content="<s:property value="author.name" /> - <%=Application.getSetting("name")%>"/>
<meta itemprop="description" content="<s:property value="author.description" />"/>
<meta itemprop="image" content="<s:property value="author.thumbnail" />"/>

<title><s:property value="author.name" /> - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
							
				<h1><span class="w3-small w3-text-grey w3-right"><s:property value="createDate" /></span>
				<s:property value="author.name" /></h1>
				
				<div class="w3-container w3-padding">
					<img src="<s:property value="author.thumbnail" />" class="w3-round w3-card-4 w3-margin-right w3-margin-bottom w3-left" style="max-height:200px" alt="Profile" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/>
					<% String htmlContent = (String) request.getAttribute("author.htmlContent");
					if(htmlContent == null || htmlContent.isEmpty()) {
						out.print("This author hasn't provided a bio yet.");
					} else {
					out.print(htmlContent); } %>
				</div>
				
				<div class="w3-container w3-padding-left w3-padding-right w3-center">
					<hr />
					<p class="w3-large"><a href="/author/">See more authors...</a></p>
				</div>
			</div>
			
			<%@include file="/WEB-INF/fragment/archive.jspf" %>			
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>