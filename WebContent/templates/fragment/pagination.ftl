<div class="row">
<div class="four columns">
<#if prevPage != page>
	<a class="button button-small left" href="${(pageUri)!''}/1" title="First page">&lt;&lt; First</a>
	<a class="button button-small left" href="${(pageUri)!''}/${(prevPage)!''}" title="Previous Page">&lt; Prev</a>
</#if>&nbsp;
</div>
<div class="four columns text-center">
	<span class="text-tertiary">Page ${(page)!'?'} of ${(totalPages)!'?'}</span>
	<br/>
</div>
<div class="four columns">
<#if nextPage != page>
	<a class="button button-small right" href="${(pageUri)!''}/${(totalPages)!''}" title="Last page">Last &gt;&gt;</a>
	<a class="button button-small right" href="${(pageUri)!''}/${(nextPage)!''}" title="Next page">Next &gt;</a>
</#if>&nbsp;
</div>
</div>