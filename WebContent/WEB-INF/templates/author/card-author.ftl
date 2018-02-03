<div class="w3-container w3-col s12 m10 l7 w3-round-large w3-hover-shadow w3-card w3-padding-0 w3-margin-bottom">
<#if author.thumbnail?has_content>
	<div class="w3-container w3-col s3 m3 l3 w3-padding-16">
		<a href="/author/${(author.uri)!''}">
			<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile" src="${(author.thumbnail)!''}" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'" />		
		</a>
	</div>
</#if>
	<div class="w3-container w3-col s9 m9 l9 w3-padding-16">
		<div class="w3-small w3-text-grey w3-margin-0 w3-padding-right">
		<h3 class="w3-padding-0 w3-margin-0"><a href="/author/${(author.uri)!''}">${(author.name)!'Unknown'}</a></h3>
		<p class="w3-small w3-margin-0">${(author.description)!"This author hasn't provided a bio yet."}</p> 
		
		<p class="w3-small w3-text-theme w3-padding-top">
			<span class="w3-tag w3-round w3-pale-blue" title="This person is an ${(author.roleId)!''}" style="vertical-align: middle; white-space:nowrap;"><span class="icon-quill"></span>&nbsp;${(author.roleId)!''}</span>
			<span class="icon-time w3-medium w3-text-theme w3-padding-square" title="Date Joined"></span>
			<span title="Date Joined" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">${(author.createDateReadable)!''}</span>
		</p>
		</div>
	</div>
</div>