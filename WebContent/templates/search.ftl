<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Search - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<#include "/templates/fragment/tabs.ftl">
			
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Search</h1>
				
				<div class="w3-center">
					<form action="/search" method="post">
					<p>
						<label for="searchQ" class="icon-search w3-xlarge w3-text-theme"></label>
						<input id="searchQ" name="q" class="w3-input w3-hover-shadow w3-card w3-round-large" style="display:inline; width:85%" maxlength="50" size="20" placeholder="Search..." type="text" />
					</p>
					<p>
						<button class="w3-btn w3-round w3-card w3-theme-light" type="submit" title="Enter">Enter</button>
					</p>
					</form>
				</div>
			
			</div>
			
			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>
	
	<#include "/templates/fragment/footer.ftl">	
</body>
</html>