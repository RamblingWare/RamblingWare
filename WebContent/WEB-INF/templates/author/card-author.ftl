<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="w3-col s12 m10 l7 w3-round-large w3-hover-shadow w3-card w3-padding-0 w3-margin-bottom">
	<div class="w3-col s3 m3 l3 w3-padding-16">
		<a href="/author/<s:property value="uri" />">
		<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile" src="<s:property value="thumbnail" />" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'" />
		</a>
	</div>
	<div class="w3-col s9 m9 l9 w3-padding-16">
		<div class="w3-small w3-text-grey w3-margin-0 w3-padding-right">
		<h3 class="w3-padding-0 w3-margin-0"><a href="/author/<s:property value="uri" />"><s:property value="Name" /></a></h3>
		<p class="w3-small w3-margin-0">
		<s:if test="description == null || description.isEmpty()">This author hasn't provided a bio yet.</s:if><s:else><s:property value="description" /></s:else>
		</p> 
		
		<p class="w3-small w3-text-theme w3-padding-top">
			<s:if test="role.id % 4 == 0">
				<span class="w3-tag w3-round w3-pale-red" title="This person is an <s:property value="role.name" />" style="vertical-align: middle; white-space:nowrap;"><span class="icon-cog"></span>&nbsp;<s:property value="role.name" /></span>
			</s:if>
			<s:elseif test="role.id % 3 == 0">
				<span class="w3-tag w3-round w3-pale-yellow" title="This person is an <s:property value="role.name" />" style="vertical-align: middle; white-space:nowrap;"><span class="icon-star"></span>&nbsp;<s:property value="role.name" /></span>
			</s:elseif>
			<s:elseif test="role.id % 2 == 0">
				<span class="w3-tag w3-round w3-pale-blue" title="This person is an <s:property value="role.name" />" style="vertical-align: middle; white-space:nowrap;"><span class="icon-quill"></span>&nbsp;<s:property value="role.name" /></span>
			</s:elseif>
			<s:elseif test="role.id % 1 == 0">
				<span class="w3-tag w3-round w3-pale-green" title="This person is an <s:property value="role.name" />" style="vertical-align: middle; white-space:nowrap;"><span class="icon-author"></span>&nbsp;<s:property value="role.name" /></span>
			</s:elseif>
					
			<span class="icon-time w3-medium w3-text-theme w3-padding-square" title="Date Joined"></span>
			<span title="Date Joined" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"><s:property value="createDateReadable" /></span>
		</p>
		
		</div>
	</div>
</div>