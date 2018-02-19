<div id="archive" class="w3-col m4 l2 no-print">
	<div class="w3-container w3-hide-small">
		<#if archiveFeatured??>
			<h3 class="w3-margin-top">Featured</h3>
			<#list archiveFeatured as post>
				<#include "/templates/blog/block-post.ftl">
			</#list>
		</#if>
	</div>
	<div class="w3-container">
		<#if archiveYears??>
			<h3 class="w3-margin-top"><a href="/year/">Archive</a></h3>
			<ul class="w3-medium w3-margin-0">
			<#list archiveYears as year>
				<li class="w3-opacity w3-hover-opaque"><a title="${(year.name)!''}" href="/year/${(year.name)!''}">${(year.name)!''} (${(year.count)!''})</a></li>
			</#list>
			</ul>
		</#if>
	</div>
	<div class="w3-container">
		<#if archiveCategories??>
			<h3 class="w3-margin-top"><a href="/category/">Categories</a></h3>
			<p class="w3-small w3-margin-0">
			<#list archiveCategories as category>
				<a class="tag w3-round w3-theme-light w3-card w3-hover-light-grey w3-hover-shadow" style="white-space:nowrap" title="${(category.name)!''} (${(category.count)!''})" href="/category/${(category.name)!''}">${(category.name)!''}</a>
			</#list>
			</p>
		</#if>
	</div>
	<div class="w3-container">
		<#if archiveTags??>
			<h3 class="w3-margin-top"><a href="/tag/">Tags</a></h3>
			<p class="w3-small w3-margin-0">
			<#list archiveTags as tag>
				<a class="tag w3-round w3-theme-l3 w3-card w3-hover-light-grey w3-hover-shadow" title="${(tag.name)!''} (${(tag.count)!''})" href="/tag/${(tag.name)!''}">${(tag.name)!''}</a>
			</#list>
			</p>
		</#if>
	</div>
</div>