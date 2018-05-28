<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta.ftl">

<title>Error!</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1>Error</h1>
				<p>Oops! An error occurred.</p>
				
				<#if actionErrors??>
					<#list actionErrors as actionError>
					<p class="padding border round border-danger">
					<span class="icon-cross text-danger text-large margin-right"></span>${(actionError)!''}</p>
					</#list>
				</#if>
				<#if actionMessages??>
					<#list actionMessages as actionMessage>
					<p class="padding border round text-bold">
					<span class="icon-cog text-secondary text-large margin-right"></span>${(actionMessage)!''}</p>
					</#list>
				</#if>

				<br />
				<p>
					<a class="button button-primary" href="javascript: window.history.back()">
						<span class="icon-arrow-left text-medium margin-right"></span>Go Back</a>
				</p>

			</div>
		</div>
	</article>

	<#include "../fragment/footer.ftl">
</body>
</html>
