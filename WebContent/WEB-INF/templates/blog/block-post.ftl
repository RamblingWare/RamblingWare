<div class="w3-display-container w3-margin-top w3-padding-0 w3-round w3-hover-shadow w3-card w3-center" style="overflow:hidden;">
<a href="/blog/${(post.uri)!''}" title="${(post.description)!''}">
<#if post.featured>
	<span class="icon-star w3-xlarge w3-text-theme w3-hover-text-yellow w3-padding-square" title="This is a featured post." style="position:absolute;"></span>
</#if>
	<#if post.thumbnail?has_content>
		<img alt="Thumbnail" class="w3-margin-0 w3-round w3-padding-0 w3-padding-bottom" style="width:100%; height:100%;" src="${(post.thumbnail)!''}"></a>
		<a href="/blog/${(post.uri)!''}" class="w3-small w3-theme-light w3-padding-4 bottomright" style="width: 100%;">${(post.title)!''}</a>
	<#else>
	<a href="/blog/${(post.uri)!''}" class="w3-small w3-left w3-theme-light w3-padding-4" style="width: 100%;">${(post.title)!''}</a>
	<span class="w3-small w3-left w3-padding-4 w3-text-theme">${(post.description)!''}</span>
	</#if>
</div>