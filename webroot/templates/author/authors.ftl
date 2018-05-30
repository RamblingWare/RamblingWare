<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta.ftl">

<title>Authors - ${(context.name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-users"></span>&nbsp;Authors</h1>

				<#if context.authors??>
					<#if context.authors.size() == 1>
						<p>Only 1 author writes this blog!<br /></p>
					<#else>
						<p>${(context.authors.size())!''} authors write for this blog.<br /></p>
					</#if>
					<div class="row" style="min-height:0px">
					<#list context.authors as author>
						<#include "../author/card-author.ftl">
					</#list>
					</div>
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						Something went wrong because no authors were found. Please try again later?</p>
				</#if>
			</div>

			<#include "../fragment/archive.ftl">
		</div>
	</article>

	<#include "../fragment/footer.ftl">
</body>
</html>
