<div class="row padding-none text-center" style="overflow:hidden;">
<a href="/blog/${(post.uri)!''}" title="${(post.description)!''}" class="text-secondary">
	<#if post.thumbnail?has_content>
		<img alt="${(post.bannerCaption)!''}" class="margin-none shade round padding-none padding-bottom" style="width:100%; height:100%;" src="${(post.thumbnail)!''}">
	</#if>
	<span class="padding">${(post.title)!''}</span>
</a>
</div>

