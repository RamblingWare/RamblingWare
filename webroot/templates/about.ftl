<!DOCTYPE HTML>
<html>
<head>
<#include "fragment/meta/meta.ftl">

<title>About ${(context.name)!'Oddox'}</title>
</head>
<body>

	<#include "fragment/header.ftl">

	<article class="container padding-top-large margin-top-large">
		<div class="row">
			<div id="page-content" class="nine columns">
			
				<h1>About ${(context.name)!'Oddox'}</h1>
				
				<p>
					This is the about page. Its not filled out yet.
				</p>
			</div>
	
			<#include "fragment/archive.ftl">
		</div>
	</article>

	<#include "fragment/footer.ftl">
</body>
</html>
