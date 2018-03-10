<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Search - ${(name)!'Oddox'}</title>
</head>
<body>

	<#include "/templates/fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">
			
				<h1>Search</h1>
	
				<form action="/search" method="post">
				<p>
					<input name="q" maxlength="50" size="50" placeholder="Search..." type="text" />
					<button class="button button-primary" type="submit" title="Enter">
					<i class="icon-search text-medium margin-right"></i>Search</button>
				</p>
				</form>
	
			</div>
	
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>
