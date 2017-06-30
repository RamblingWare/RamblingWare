<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%><%@ page import="com.rant.config.Utils" %>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>
<title>Test - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Test</h1>
				
				<div class="w3-row">
					<div class="w3-container w3-padding w3-col s12 m12 l12">
<p>HTTP Headers</p>					
<pre><code class="http">Response Status: <%= response.getStatus() %>
Response Encoding: <%= response.getCharacterEncoding() %>
Response Content Type: <%= response.getContentType() %>
Request ContextPath: <%= request.getContextPath() %>
Request URI: <%= request.getRequestURI() %>
Request URL: <%= request.getRequestURL() %>
Servlet Path: <%= request.getServletPath() %>
Servlet Context: <%= request.getServletContext().getContextPath() %>
LocalAddr: <%= request.getLocalAddr() %>
LocalName: <%= request.getLocalName() %>
LocalPort: <%= request.getLocalPort() %>
Scheme: <%= request.getScheme() %>
ServerName: <%= request.getServerName() %>
Locale: <%= request.getLocale() %>
PathTranslated: <%= request.getPathTranslated() %>
QueryString: <%= request.getQueryString() %>
RemoteAddr: <%= request.getRemoteAddr() %>
RemoteHost: <%= request.getRemoteHost() %>
RemotePort: <%= request.getRemotePort() %>
RemoteUser: <%= request.getRemoteUser() %>
<% java.util.Enumeration<String> hdrs = request.getHeaderNames();
while(hdrs.hasMoreElements()) {
	String hdr = hdrs.nextElement();
	out.println(hdr+": "+request.getHeader(hdr));
	
}%></code></pre>
<p>System Properties</p>
<pre><code><% java.util.Properties props =  System.getProperties();
out.println(props); %></code></pre>				
					</div>	
				</div>
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>