<div class="w3-padding w3-row w3-margin-top"><hr/>
<div class="w3-col s4 m4 l4">
<#if prevPage != page>
	<a class="w3-btn w3-round-left w3-border-top w3-border-left w3-border-bottom w3-small w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-left" href="${(pageUri)!''}/1" title="First page">&lt;&lt; First</a>
	<a class="w3-btn w3-round-right w3-border w3-small w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-left" href="${(pageUri)!''}/${(prevPage)!''}" title="Previous Page">&lt; Prev</a>
</#if>&nbsp;
</div>
<div class="w3-col s4 m4 l4 w3-center">
	<span class="w3-small w3-text-grey">Page ${(page)!'?'} of ${(totalPages)!'?'}</span>
	<br/>
</div>
<div class="w3-col s4 m4 l4">
<#if nextPage != page>
	<a class="w3-btn w3-round-right w3-border w3-small w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-right" href="${(pageUri)!''}/${(totalPages)!''}" title="Last page">Last &gt;&gt;</a>
	<a class="w3-btn w3-round-left w3-border-top w3-border-left w3-border-bottom w3-small w3-theme-l3 w3-hover-light-grey w3-hover-shadow w3-right" href="${(pageUri)!''}/${(nextPage)!''}" title="Next page">Next &gt;</a>
</#if>&nbsp;
</div>
</div>