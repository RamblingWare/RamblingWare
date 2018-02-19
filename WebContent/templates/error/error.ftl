<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta-nocache.ftl">

<title>Error!</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Error</h1>
				<p>Oops! An error occurred.</p>
				
				<#if actionErrors??>
					<#list actionErrors as actionError>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>${(actionError)!''}</p>
					</#list>
				</#if>
				<#if actionMessages??>
					<#list actionMessages as actionMessage>
					<p class="w3-padding w3-border w3-card-2 w3-round w3-theme-light w3-text-theme w3-border-theme">
					<span class="icon-cog w3-large w3-margin-right"></span>${(actionMessage)!''}</p>
					</#list>
				</#if>
				
				<br />
				<p>
					<a class="w3-btn w3-card w3-round w3-light-grey" href="javascript: window.history.back()">
						<span class="icon-arrow-left w3-large"></span>&nbsp;&nbsp;Go Back</a>
				</p>
			
			</div>
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>