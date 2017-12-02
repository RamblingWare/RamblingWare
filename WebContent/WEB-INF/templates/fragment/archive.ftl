<div id="archive" class="w3-col m4 l2 no-print">
	<div class="w3-container w3-hide-small">
		<s:if test="#session.archiveFeatured != null && !#session.archiveFeatured.isEmpty()">
			<h3 class="w3-margin-top">Featured</h3>
			<s:iterator value="#session.archiveFeatured" status="f">
				<%@include file="/WEB-INF/blog/block-post.jspf" %>
			</s:iterator>
		</s:if>
	</div>
	<div class="w3-container">
		<s:if test="#session.archiveYears != null && !#session.archiveYears.isEmpty()">
			<h3 class="w3-margin-top"><a href="/year/">Archive</a></h3>
			<ul class="w3-medium w3-margin-0">
			<s:iterator value="#session.archiveYears" status="y">
				<li class="w3-opacity w3-hover-opaque"><a title="<s:property value="name" />" href="/year/<s:property value="name" />"><s:property value="name" /> (<s:property value="count" />)</a></li>
			</s:iterator>
			</ul>
		</s:if>
	</div>
	<div class="w3-container">
		<s:if test="#session.archiveCategories != null && !#session.archiveCategories.isEmpty()">
			<h3 class="w3-margin-top"><a href="/category/">Categories</a></h3>
			<p class="w3-small w3-margin-0">
			<s:iterator value="#session.archiveCategories" status="c">
				<a class="tag w3-round w3-theme-light w3-card w3-hover-light-grey w3-hover-shadow" style="white-space:nowrap" title="<s:property value="name" /> (<s:property value="count" />)" href="/category/<s:property value="name" />"><s:property value="name" /></a>
			</s:iterator>
			</p>
		</s:if>
	</div>
	<div class="w3-container">
		<s:if test="#session.archiveTags != null && !#session.archiveTags.isEmpty()">
			<h3 class="w3-margin-top"><a href="/tag/">Tags</a></h3>
			<p class="w3-small w3-margin-0">
			<s:iterator value="#session.archiveTags" status="t">
				<a class="tag w3-round w3-theme-l3 w3-card w3-hover-light-grey w3-hover-shadow" title="<s:property value="name" /> (<s:property value="count" />)" href="/tag/<s:property value="name" />"><s:property value="name" /></a>
			</s:iterator>
			</p>
		</s:if>
	</div>
</div>