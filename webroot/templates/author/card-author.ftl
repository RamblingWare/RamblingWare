<div class="row round shade padding-none margin-bottom-large">
<div class="round padding-right padding-bottom">
<#if author.thumbnail?has_content>
	<a href="/blog/author/${(author.uri)!''}">
	<img class="round left margin-right margin-bottom" style="max-width:175px;" src="${(author.thumbnail)!''}" title="${(author.name)!''}" alt="Profile" onerror="this.src='/blog/images/placeholder-200.png';this.title='Failed to load image.'"/>
	</a>
</#if>
	<h3 class="padding-none margin-none"><a href="/blog/author/${(author.uri)!''}" class="text-secondary">${(author.name)!''}</a></h3>
	<p>${(author.description)!"This author doesn't have a description yet."}</p>

	<p class="text-secondary margin-none">
	    <span class="icon-author padding" title="Role"></span>
	    <span class="text-secondary" title="This person is an ${(author.role)!'Author'}" style="vertical-align: middle; white-space:nowrap;">${(author.role)!'Author'}</span>
		<span class="icon-time padding" title="Date Joined"></span>
		<span title="Joined ${(author.createDateReadable)!''}" class="text-secondary" style="vertical-align: middle; white-space:nowrap;">${(author.createDateReadable)!''}</span>
	</p>
</div>
</div>
