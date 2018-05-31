<div class="row margin-top-large margin-bottom">
<div class="four columns">
<#if context.prevPage != context.page>
	<a class="button button-small button-secondary left" href="?page=1" title="First page">&lt;&lt; First</a>
	<a class="button button-small button-primary left" href="?page=${(context.prevPage)!'?'}" title="Previous Page">&lt; Prev</a>
</#if>&nbsp;
</div>
<div class="four columns text-center">
	<span class="text-secondary">Page <a title="${(context.page)!'?'}" href="?page=${(context.page)!'1'}" class="text-secondary">
		<code>${(context.page)!'?'}</code></a> of <a title="${(context.totalPages)!'?'}" href="?page=${(context.totalPages)!'1'}" class="text-secondary"><code>${(context.totalPages)!'?'}</code></a>
	</span>
	<br/>
</div>
<div class="four columns">
<#if context.nextPage != context.page>
	<a class="button button-small button-secondary right" href="?page=${(context.totalPages)!''}" title="Last page">Last &gt;&gt;</a>
	<a class="button button-small button-primary right" href="?page=${(context.nextPage)!''}" title="Next page">Next &gt;</a>
</#if>&nbsp;
</div>
</div>