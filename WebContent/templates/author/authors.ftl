<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Authors - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-time"></span>&nbsp;Authors</h1>

				<#if authors??>
					<#if authors.size() == 1>
						<p>Only 1 author writes this blog!<br /></p>
					<#else>
						<p>${(authors.size())!''} authors write for this blog.<br /></p>
					</#if>
					<div class="row" style="min-height:0px">
					<#list authors as author>
						<#include "/templates/author/card-author.ftl">
					</#list>
					</div>
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						Something went wrong because no authors were found. Please try again later?</p>
				</#if>
			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
