<nav id="sidebar" class="w3-sidebar w3-bar-block w3-black w3-card-4 w3-animate-left">
  	<button onclick="closeSidebar()" class="w3-bar-item uppercase w3-black w3-text-theme w3-large" data-close="">Menu</button>
  	<a class="w3-bar-item w3-hover-text-light-grey w3-btn" title="Home" href="/"><span class="icon-home w3-large w3-margin-right"></span>Home</a>
	<a class="w3-bar-item w3-hover-text-light-grey w3-btn" title="Years" href="/year/"><span class="icon-time w3-large w3-margin-right"></span>Archive</a>
	<a class="w3-bar-item w3-hover-text-light-grey w3-btn" title="Categories" href="/category/"><span class="icon-folder w3-large w3-margin-right"></span>Categories</a>
	<a class="w3-bar-item w3-hover-text-light-grey w3-btn" title="Tags" href="/tag/"><span class="icon-tag w3-large w3-margin-right"></span>Tags</a>
	<a class="w3-bar-item w3-hover-text-light-grey w3-btn" title="Authors" href="/author/"><span class="icon-author w3-large w3-margin-right"></span>Authors</a>
	<a class="w3-bar-item w3-hover-text-light-grey w3-btn" title="About" href="/about">About</a>
	<p class="w3-container w3-center w3-small w3-text-theme">
		${(copyright)!''}<br/>
		Last Updated: ${(lastUpdated)!''}
		<br/><br/>
		<a href="/terms-of-use" title="Terms of Use">Terms</a> | 
		<a href="/privacy-policy" title="Privacy Policy">Privacy</a><br/>
		<a target="_Blank" href="https://oddox.org" title="Oddox.org">Powered by Oddox</a><br/> 
		<a target="_Blank" href="https://www.ibm.com/cloud" title="IBM Cloud">Hosted on IBM Cloud</a>
	</p>
</nav>
<header>
	<div class="page w3-container w3-theme-dark">
		<div class="w3-col s2 w3-padding-4 w3-hide-medium w3-hide-large no-print w3-dropdown-hover">
			<button class="w3-btn icon-menu w3-padding-square w3-theme-dark w3-text-theme w3-xxlarge" onclick="openSidebar()"></button>		
		</div>
		<div class="w3-col s10 m7 l7 w3-container w3-padding-0">
			<a href="/"><img id="logo" src="/img/logo.png" alt="${(name)!'Oddox'}" /></a>
		</div>
		<div id="search" class="w3-col s2 m3 l3 w3-padding-square w3-right w3-small w3-hide-small no-print">
			<div class="w3-padding-left w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-card w3-round-large">
				<form action="/search" method="post" class="w3-medium">
				<label for="searchHead" class="icon-search w3-large w3-text-theme"></label>
				<input id="searchHead" name="q" class="w3-input w3-hover-light-grey w3-theme-l3 w3-border-0 w3-padding-4" style="display:inline;width:75%;border:none!important;box-shadow:none;" maxlength="50" size="20" placeholder="Search..." type="text">
				</form>
			</div>
		</div>
	</div>
</header>
<#if actionErrors??>
	<#list actionErrors as actionError>
	<div style="float:left; position:absolute; left:50%; top:1%;">
	<div class="w3-animate-top w3-padding w3-margin w3-border w3-card-4 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" style="left:-50%; min-width:280px;" data-close="">
		<span class="w3-medium w3-text-black w3-margin-right">
			<span class="icon-cross w3-large w3-margin-right w3-text-red"></span>${(actionError)!''}
		</span>
	</div>
	</div>
	</#list>
</#if>
<#if actionMessages??>
	<#list actionMessages as actionMessage>
	<div style="float:left; position:absolute; left:50%; top:1%;">
	<div class="w3-animate-top w3-padding w3-margin w3-border w3-card-4 w3-round w3-pale-green w3-text-green w3-border-green" onclick="this.style.display='none'" style="left:-50%; min-width:280px;" data-close="">
		<span class="w3-medium w3-text-black w3-margin-right">
			<span class="icon-checkmark w3-large w3-margin-right w3-text-green"></span>${(actionMessage)!''}
		</span>
	</div>
	</div>
	</#list>
</#if>