<!DOCTYPE HTML>
<html>
<head>
<#include "../fragment/meta/meta.ftl">

<title>Years - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "../fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">

				<h1 style="vertical-align: middle;"><span class="icon-time"></span>&nbsp;Years</h1>

				<#if context.years??>
					<p>${(context.years?size)!''} years of posts.<br /></p>
					<ul>
					<#list context.years as year>
						<li><a title="${(context.year.name)!'?'}" href="/year/${(year.name)!''}" class="text-bold">${(year.name)!'?'}</a>
						&nbsp;(${(year.count)!'?'} posts)
						</li>
					</#list>
					</ul>
				<#else>
					<p class="padding border round border-danger text-danger text-bold">
					<span class="icon-cross text-medium margin-right"></span>
						No results were found for any year.</p>
				</#if>
			</div>

			<#include "../fragment/archive.ftl">
		</div>
	</article>

	<#include "../fragment/footer.ftl">
</body>
</html>
