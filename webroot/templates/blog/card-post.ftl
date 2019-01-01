<div class="row round shade padding-none margin-bottom-large">
<div class="round padding-right padding-bottom">
<#if post.featured>
	<span class="icon-star xlarge hover-text-yellow float-right padding" title="This is a featured post."></span>
</#if>
<#if post.thumbnail?has_content>
	<a href="/blog/${(post.uri)!''}">
	<img class="round left margin-right margin-bottom" style="max-width:250px;" src="${(post.thumbnail)!''}" alt="${(post.title)!''}" title="${(post.bannerCaption)!''}" onerror="this.src='/blog/images/placeholder-640.png';this.title='Failed to load image.'"/>
	</a>
</#if>
	<h3 class="padding-none margin-none"><a href="/blog/${(post.uri)!''}" class="text-secondary">${(post.title)!''}</a></h3>
	<p class="">${(post.description)!"This post doesn't have a description yet."}</p>

	<p class="text-secondary margin-none">
		<#if post.author.thumbnail?has_content>
			<span class="padding"><img class="round border" alt="Profile" title="Author" style="vertical-align: middle;" src="${(post.author.thumbnail)!''}" height="16" width="16" onerror="this.src='/blog/images/placeholder-200.png';this.title='Failed to load image.'"/></span>
		<#else>
			<span class="icon-author padding" title="Author"></span>
		</#if>
		<a href="/blog/author/${(post.author.uri)!''}" title="Author" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(post.author.name)!''}</a>

		<span class="icon-time padding" title="Publish Date"></span>
		<a href="/blog/year/${(post.publishYear?string('0'))!''}" title="Published ${(post.publishDateTimeReadable)!''}" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(post.publishDateReadable)!''}</a>
	</p>
	<p class="text-secondary margin-none">
		<#if post.category?has_content>
			<span class="icon-folder padding" title="Category"></span>
			<a href="/blog/category/${(post.category)!''}" title="Category" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(post.category)!''}</a>
		</#if>
		<span class="icon-tag padding" title="Tags"></span>
		<#if post.tags?has_content>
			<#list post.tags as tag>
				<a class="text-secondary" title="${(tag)!''}" href="/blog/tag/${(tag)!''}">${(tag)!''}</a>&nbsp;
			</#list>
		</#if>
		<span title="Clicks (${(post.view.count)!''})"><span class="icon-eye padding"></span>${(post.view.count)!''}</span>
		<span title="Comments (0)"><span class="icon-comments padding"></span><a class="text-secondary" href="/blog/${(post.uri)!''}#comments">0</a></span>
	</p>
</div>
</div>
