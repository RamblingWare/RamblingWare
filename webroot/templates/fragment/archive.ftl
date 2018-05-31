<div id="archive" class="three columns">
	<div class="margin">
		<#if context.archiveFeatured??>
			<h4 class="text-secondary"><span class="icon-star"></span>&nbsp;Featured</h4>
			<#list context.archiveFeatured as post>
				<#include "../blog/block-post.ftl">
			</#list>
		</#if>
	</div>
	<div class="margin">
		<#if context.archiveYears??>
			<h4 class="margin-top"><a href="/year/" class="text-secondary"><span class="icon-time"></span>&nbsp;Years</a></h4>
			<ul class="margin-none">
			<#list context.archiveYears as year>
				<li class="margin-none"><a title="${(year.name)!''}" href="/year/${(year.name)!''}" class="text-secondary">${(year.name)!''} (${(year.count)!''})</a></li>
			</#list>
			</ul>
		</#if>
	</div>
	<div class="margin">
		<#if context.archiveCategories??>
			<h4 class="margin-top"><a href="/category/" class="text-secondary"><span class="icon-folder"></span>&nbsp;Categories</a></h4>
			<p class="margin-none">
			<#list context.archiveCategories as category>
				<a style="white-space:nowrap" title="${(category.name)!''} (${(category.count)!''})" href="/category/${(category.name)!''}" class="text-secondary"><code>${(category.name)!''}</code></a>
			</#list>
			</p>
		</#if>
	</div>
	<div class="margin">
		<#if context.archiveTags??>
			<h4 class="margin-top"><a href="/tag/" class="text-secondary"><span class="icon-tag"></span>&nbsp;Tags</a></h4>
			<p class="margin-none">
			<#list context.archiveTags as tag>
				<a title="${(tag.name)!''} (${(tag.count)!''})" href="/tag/${(tag.name)!''}" class="text-tertiary"><code class="background-secondary border-secondary">${(tag.name)!''}</code></a>
			</#list>
			</p>
		</#if>
	</div>
</div>
