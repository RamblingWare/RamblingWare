<div class="row padding-none text-center" style="overflow:hidden;">
<a href="/blog/${(post.uri)!''}" title="${(post.description)!''}" class="text-secondary">
<#if post.thumbnail?has_content>
	<img alt="Thumbnail" class="margin-none round padding-none padding-bottom" style="width:100%; height:100%;" src="${(post.thumbnail)!''}">
</#if>
${(post.title)!''}</a>
</div>

