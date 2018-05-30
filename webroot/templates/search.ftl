<!DOCTYPE HTML>
<html>
<head>
<#include "fragment/meta/meta.ftl">

<title>Search - ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "fragment/header.ftl">

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
	
			<#include "fragment/archive.ftl">
		</div>
	</article>

	<#include "fragment/footer.ftl">
</body>
</html>
