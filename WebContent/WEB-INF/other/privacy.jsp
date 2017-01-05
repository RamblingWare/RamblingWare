<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
<title>Privacy Policy - RamblingWare</title>
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
			
				<h1><font class="footnote w3-right">Nov 24th, 2015</font>
				Privacy Policy</h1>
				<p>
				<span class="bold">Personal Info.</span> This website does not collect or disclose personal information of users.<br /><br />
				If you choose to share personal information in the comments or elsewhere, it is at your own risk.<br /><br />
				<span class="bold">Third-Parties.</span> Because this site is hosted by IBM Bluemix, it would be my recommendation to read <a target="_Blank" href="http://www.ibm.com/privacy">IBM's Privacy Policy</a> too. If you leave a comment on any post I would recommend reading <a target="Blank" href="https://help.disqus.com/customer/portal/articles/466259-privacy-policy">Disqus' Privacy Policy</a>.<br /><br />
				<span class="bold">Cookies.</span> This website may make use of cookies, but they are not necessary for using it. JavaScript is not necessary, since its mainly used for comments powered by <a target="_Blank" href="https://disqus.com/"></a>Disqus</a>.<br /><br />
				<span class="bold">Log Files.</span> This website may gather certain information automatically and store it in log files. This information may include Internet protocol (IP) addresses, browser type, internet service provider (ISP), referring/exit pages, operating system, date/time stamp, and/or clickstream data. This information is not shared with third-party services, and is not used to identify users in any way. In fact, I personally don't even look at it.<br /><br />
				Please be sure to read our <a href="/terms">Terms of use</a> as well.
				</p>
				<br />
				<%! int hits = 1; %>
				<!-- Terms JSP Hits: <%=hits++ %>  -->
				
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
		</div>
	</article>
	<!-- CONTENT END -->

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>