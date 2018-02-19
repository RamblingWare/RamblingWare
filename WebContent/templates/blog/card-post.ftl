<div class="w3-container w3-round-large w3-border w3-card w3-hover-shadow w3-padding-0 w3-margin-bottom">
<div class="w3-container w3-round-large w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-l4" style="overflow: hidden;">
	<a href="/blog/${(post.uri)!''}">
	<#if post.thumbnail?has_content>
	<img class="thumbnail" src="${(post.thumbnail)!''}" alt="Thumbnail ${(post.title)!''}" title="${(post.bannerCaption)!''}" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'"/>
	</#if>
	</a>
</div>
<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
<#if post.featured>
	<span class="icon-star w3-xlarge w3-text-theme w3-hover-text-yellow w3-right w3-padding-square" title="This is a featured post."></span>
</#if>
	<h3 class="w3-padding-0 w3-margin-0"><a href="/blog/${(post.uri)!''}">${(post.title)!''}</a></h3>
	<p class="w3-small w3-margin-0">${(post.description)!"This post doesn't have a description yet."}</p>

	<p class="w3-small w3-text-theme w3-padding-top">
		<#if post.author.thumbnail?has_content>
			<span class="w3-padding-square"><img class="w3-round" alt="Profile" title="Author" style="vertical-align: middle;" src="${(post.author.thumbnail)!''}" height="16" width="16" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/></span>
		<#else>
			<span class="icon-author w3-medium w3-text-theme w3-padding-square" title="Author"></span>
		</#if>
		<a href="/author/${(post.author.uri)!''}" title="Author" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">${(post.author.name)!''}</a>
		
		<span class="icon-time w3-medium w3-text-theme w3-padding-square" title="Publish Date"></span>
		<a href="/year/${(post.publishYear)!''}" title="Published ${(post.publishDateTimeReadable)!''}" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">${(post.publishDateReadable)!''}</a>
	</p>
	<p class="w3-small w3-text-theme">
		<#if post.category?has_content>
			<span class="icon-folder w3-medium w3-text-theme w3-padding-square" title="Category"></span>
			<a href="/category/${(post.category)!''}" title="Category" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">${(post.category)!''}</a>
		</#if>
		<span class="icon-tag w3-medium w3-text-theme w3-padding-square" title="Tags"></span>
		<#if post.tags?has_content>
			<#list post.tags as tag>
				<a class="w3-text-theme" title="${(tag)!''}" href="/tag/${(tag)!''}">${(tag)!''}</a>&nbsp;
			</#list>
		</#if>
		<span title="Hits (${(post.view.count)!''}) / Actual (${(post.view.session)!''})"><span class="icon-eye w3-medium w3-text-theme w3-padding-square"></span><span class="w3-text-theme">${(post.view.count)!''}</span></span>
		<span title="Comments (0)"><span class="icon-comments w3-medium w3-text-theme w3-padding-square"></span><a class="w3-text-theme" href="/blog/${(post.uri)!''}#comments">0</a></span>
	</p>
</div>
</div>