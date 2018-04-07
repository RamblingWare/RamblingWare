<div class="row margin-top-large margin-bottom">
<div class="four columns">
<#if prevPage != page>
	<a class="button button-small button-secondary left" href="${(pageUri)!''}/1" title="First page">&lt;&lt; First</a>
	<a class="button button-small button-primary left" href="${(pageUri)!''}/${(prevPage)!''}" title="Previous Page">&lt; Prev</a>
</#if>&nbsp;
</div>
<div class="four columns text-center">
	<span class="text-secondary">Page <a title="${(page)!'?'}" href="${(pageUri)!''}/${(page)!'1'}" class="text-secondary"><code>${(page)!'?'}</code></a> of <a title="${(totalPages)!'?'}" href="${(pageUri)!''}/${(totalPages)!'1'}" class="text-secondary"><code>${(totalPages)!'?'}</code></a></span>
	<br/>
</div>
<div class="four columns">
<#if nextPage != page>
	<a class="button button-small button-secondary right" href="${(pageUri)!''}/${(totalPages)!''}" title="Last page">Last &gt;&gt;</a>
	<a class="button button-small button-primary right" href="${(pageUri)!''}/${(nextPage)!''}" title="Next page">Next &gt;</a>
</#if>&nbsp;
</div>
</div>