<div class="w3-container w3-round-large w3-border w3-card w3-hover-shadow w3-padding-0 w3-margin-bottom">				
<div class="w3-container w3-round-large w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-l4" style="overflow: hidden;">
	<a href="/blog/<s:property value="uri" />">
<s:if test="featured == true">
	<span class="icon-star w3-xlarge w3-text-theme w3-hover-text-yellow w3-padding-square" title="This is a featured post." style="position:absolute;"></span>
</s:if>
	<s:if test="thumbnail != null && !thumbnail.isEmpty()">
	<img class="thumbnail" src="<s:property value="thumbnail" />" alt="Thumbnail <s:property value="title" />" title="<s:property value="bannerCaption" />" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'"/>
	</s:if>
	</a>
</div>
<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
	<h3 class="w3-padding-0 w3-margin-0"><a href="/blog/<s:property value="uri" />"><s:property value="title" /></a></h3>
	<p class="w3-small w3-margin-0"><s:if test="description == null || description.isEmpty()">This post doesn't have a description yet.</s:if><s:else><s:property value="description" /></s:else></p>

	<p class="w3-small w3-text-theme w3-padding-top">
		<s:if test="author.thumbnail != null && !author.thumbnail.trim().isEmpty()">
			<span class="w3-padding-square"><img class="w3-round" alt="Profile" title="Author" style="vertical-align: middle;" src="<s:property value="author.thumbnail" />" height="16" width="16" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'"/></span>
		</s:if>
		<s:else>
			<span class="icon-author w3-medium w3-text-theme w3-padding-square" title="Author"></span>
		</s:else>
		<a href="/author/<s:property value="author.uri" />" title="Author" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;">
		<s:property value="author.name" /></a>
		
		<span class="icon-time w3-medium w3-text-theme w3-padding-square" title="Publish Date"></span>
		<a href="/year/<s:property value="publishYear" />" title="Published <s:property value="publishDateTimeReadable" />" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"><s:property value="publishDateReadable" /></a>
	</p>
	<p class="w3-small w3-text-theme">
		<span class="icon-folder w3-medium w3-text-theme w3-padding-square" title="Category"></span>
		<a href="/category/<s:property value="category" />" title="Category" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"><s:property value="category" /></a>
		
		<span class="icon-tag w3-medium w3-text-theme w3-padding-square" title="Tags"></span>
		<s:if test="tags != null && !tags.isEmpty()">
			<s:iterator value="tags">
				<a class="w3-text-theme" title="<s:property />" href="/tag/<s:property />"><s:property /></a>&nbsp;
			</s:iterator>
		</s:if>
		<span title="Hits (<s:property value="view.count" />) / Actual (<s:property value="view.session" />)"><span class="icon-eye w3-medium w3-text-theme w3-padding-square"></span><span class="w3-text-theme"><s:property value="view.count" /></span></span>
		<span title="Comments (0)"><span class="icon-comments w3-medium w3-text-theme w3-padding-square"></span><a class="w3-text-theme" href="/blog/<s:property value="uri" />#comments">0</a></span>
	</p>
</div>
</div>