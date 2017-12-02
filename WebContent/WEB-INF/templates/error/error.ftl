<!DOCTYPE HTML>
<html>
<head>
<#include "/WEB-INF/templates/fragment/meta/meta-nocache.ftl">

<title>Error!</title>
</head>
<body class="w3-theme-dark">

	<#include "/WEB-INF/templates/fragment/header.ftl">
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<#include "/WEB-INF/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Error</h1>
				<p>Oops! An error occurred.</p>
				
				<s:if test="hasActionErrors()">
					<s:iterator value="actionErrors">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
					<s:iterator value="actionMessages">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-theme-light w3-text-theme w3-border-theme">
					<span class="icon-cog w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>		
				
				<% if(exception!=null) { %>
				<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
				<span class="icon-cross w3-large w3-margin-right"></span>	
				<% exception.printStackTrace(new java.io.PrintWriter(out)); %>
				</p>	
				<% } %>
				
				<p class="w3-small w3-text-grey"><% if(exception!=null)System.err.println("Exception: "+exception.getClass().getName()+" "+exception.getMessage()); %></p>
				
				<br />
				
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" href="javascript: window.history.back()">
						<span class="icon-arrow-left w3-large"></span>&nbsp;&nbsp;Go Back</a>
				</p>
			
			</div>
		</div>
	</article>

	<#include "/WEB-INF/templates/fragment/footer.ftl">
</body>
</html>