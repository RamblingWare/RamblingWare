<div class="w3-display-container w3-margin-top w3-padding-0 w3-round w3-hover-shadow w3-card w3-center" style="max-height:350px; max-width:280px; overflow:hidden; height:150px;">
<a href="/blog/<s:property value="uri" />" title="<s:property value="description" />">
<s:if test="featured == true">
	<span class="icon-star w3-xlarge w3-text-theme w3-hover-text-yellow w3-padding-square" title="This is a featured post." style="position:absolute;"></span>
</s:if>
	<s:if test="thumbnail != null && !thumbnail.isEmpty()">
	<img alt="Thumbnail" class="w3-margin-0 w3-round w3-padding-0" style="width:100%; min-height:100px;" src="<s:property value="thumbnail" />"></a>
	<a href="/blog/<s:property value="uri" />" class="w3-small w3-theme-light w3-padding-4 bottomright" style="width: 100%;"><s:property value="title" /></a>
	</s:if>
	<s:else>
	<a href="/blog/<s:property value="uri" />" class="w3-small w3-left w3-theme-light w3-padding-4" style="width: 100%;"><s:property value="title" /></a>
	<span class="w3-small w3-left w3-padding-4 w3-text-theme"><s:property value="description"/></span>
	</s:else>
</div>