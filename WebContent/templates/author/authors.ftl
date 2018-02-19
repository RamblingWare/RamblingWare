<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Authors - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">
			
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1 style="vertical-align: middle;"><span class="icon-time w3-text-theme"></span>&nbsp;Authors</h1>
				
				<#if authors??>
					<#if authors.size() == 1>	
						<p>Only 1 author writes this blog!<br /></p>
					<#else>
						<p>${(authors.size())!''} authors write for this blog.<br /></p>
					</#if>
					<div class="w3-row" style="min-height:0px">
					<#list authors as author>
						<#include "/templates/author/card-author.ftl">
					</#list>
					</div>				
				<#else>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						Something went wrong because no authors were found. Please try again later?</p>
				</#if>
			</div>
			
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>