<div class="w3-container w3-col s12 m12 l6 w3-round-large w3-hover-shadow w3-card w3-padding-0">
	<div class="w3-container w3-padding-16">
		<div class="w3-small w3-text-grey w3-margin-0 w3-padding-right">
		<#if author.thumbnail?has_content>
		<a href="/author/${(author.uri)!''}">
			<img class="w3-round w3-margin-right w3-left" height="48" width="48" style="vertical-align: middle;" alt="Profile" src="${(author.thumbnail)!''}" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'" />		
		</a>
		</#if>
		<h3 class="w3-padding-0 w3-margin-0"><a href="/author/${(author.uri)!''}">${(author.name)!'Unknown'}</a></h3>
		<p class="w3-small w3-margin-0">${(author.description)!"This author hasn't provided a bio yet."}</p> 
		
		<p class="w3-small w3-text-theme w3-padding-top">
			<span class="w3-tag w3-round w3-pale-blue" title="This person is an ${(author.role)!'Author'}" style="vertical-align: middle; white-space:nowrap;">${(author.role)!'Author'}</span>
			<span class="icon-time w3-medium w3-text-theme w3-padding-square" title="Date Joined"></span>
			<span title="Date Joined" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">${(author.createDateReadable)!''}</span>
		</p>
		</div>
	</div>
</div>