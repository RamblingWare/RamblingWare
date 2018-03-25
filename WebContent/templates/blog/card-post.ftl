<div class="row round border padding-none margin-bottom">
<div class="round four columns" style="overflow: hidden;">
	<a href="/blog/${(post.uri)!''}">
	<#if post.thumbnail?has_content>
	<img class="full-width" src="${(post.thumbnail)!''}" alt="Thumbnail ${(post.title)!''}" title="${(post.bannerCaption)!''}" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'"/>
	</#if>
	</a>
</div>
<div class="round eight columns padding">
<#if post.featured>
	<span class="icon-star xlarge hover-text-yellow right padding" title="This is a featured post."></span>
</#if>
	<h3 class="padding-none margin-none"><a href="/blog/${(post.uri)!''}">${(post.title)!''}</a></h3>
	<p>${(post.description)!"This post doesn't have a description yet."}</p>

	<p class="text-secondary margin-none">
		<#if post.author.thumbnail?has_content>
			<span class="padding"><img class="round border" alt="Profile" title="Author" style="vertical-align: middle;" src="${(post.author.thumbnail)!''}" height="16" width="16" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/></span>
		<#else>
			<span class="icon-author padding" title="Author"></span>
		</#if>
		<a href="/author/${(post.author.uri)!''}" title="Author" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(post.author.name)!''}</a>

		<span class="icon-time padding" title="Publish Date"></span>
		<a href="/year/${(post.publishYear)!''}" title="Published ${(post.publishDateTimeReadable)!''}" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(post.publishDateReadable)!''}</a>
	</p>
	<p class="text-secondary margin-none">
		<#if post.category?has_content>
			<span class="icon-folder padding" title="Category"></span>
			<a href="/category/${(post.category)!''}" title="Category" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(post.category)!''}</a>
		</#if>
		<span class="icon-tag padding" title="Tags"></span>
		<#if post.tags?has_content>
			<#list post.tags as tag>
				<a class="text-secondary" title="${(tag)!''}" href="/tag/${(tag)!''}">${(tag)!''}</a>&nbsp;
			</#list>
		</#if>
		<span title="Hits (${(post.view.count)!''}) / Actual (${(post.view.session)!''})"><span class="icon-eye padding"></span>${(post.view.count)!''}</span>
		<span title="Comments (0)"><span class="icon-comments padding"></span><a class="text-secondary" href="/blog/${(post.uri)!''}#comments">0</a></span>
	</p>
</div>
</div>