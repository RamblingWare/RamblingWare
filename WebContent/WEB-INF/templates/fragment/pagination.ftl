<div class="w3-container w3-row w3-margin-top">
<div class="w3-col s4 m4 l4">
<#if prevPage != page>
	<a class="w3-btn w3-round w3-small w3-theme w3-hover-light-grey w3-hover-shadow w3-left" href="${(pageUri)!''}/${(prevPage)!''}"><span class="icon-arrow-left w3-large w3-margin-right"></span> Prev Page</a>
</#if>&nbsp;
</div>
<div class="w3-col s4 m4 l4 w3-center">
	<span class="w3-small w3-text-grey">Page ${(page)!'?'} of ?</span>
	<br/>
</div>
<div class="w3-col s4 m4 l4">
<#if nextPage != page>
	<a class="w3-btn w3-round w3-small w3-theme w3-hover-light-grey w3-hover-shadow w3-right" href="${(pageUri)!''}/${(nextPage)!''}"><span class="icon-arrow-right w3-large w3-margin-right"></span>Next Page</a>
</#if>&nbsp;
</div>
</div>