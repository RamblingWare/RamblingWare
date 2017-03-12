<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>View/Edit Authors - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<!-- META_END -->
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>View/Edit Authors</h1>
				<p>Use this page to view or edit authors of this blog.</p>
				
				<p><a class="w3-btn w3-card w3-round w3-small w3-pale-green" href="/manage/newuser"><span class="icon-user-tie w3-large w3-margin-right"></span>Add Author</a>
				<span class="footnote nowrap">Add a new Author!</span>
				</p>
				
				<!-- RESULTS START -->
				<s:if test="results != null">
				<s:if test="results.isEmpty()">
					<p class="footnote">Something went wrong because no results were found. Please try again later?</p>
				</s:if>
				<s:else>
					<p class="footnote"><s:property value="results.size()" /> result(s) found.</p>
					<table class="w3-table-all w3-small">
					<tr class="w3-theme-dark uppercase">
						<th></th>
						<th>Author</th>
						<th>Email</th>
						<th>Role</th>
						<th>Added</th>
						<th>Last Login</th>
					</tr>
					
					<s:iterator value="results" status="r">
						<tr>
						<td><a class="w3-btn w3-card w3-round w3-tiny w3-theme-light" href="/manage/edituser/<s:property value="uriName" />">Edit</a></td>
						<td>
							<a href="/author/<s:property value="uriName" />">
								<img class="w3-round" src="<s:property value="thumbnail" />" height="24px" width="24px" style="vertical-align: middle;" /></a>
							&nbsp;
							<a href="/author/<s:property value="uriName" />" style="vertical-align: middle;"><s:property value="name" /></a>
						</td>
							<td class="w3-small"><a href="mailto:<s:property value="email" />"><s:property value="email" /></a></td>
						<td>
							<s:if test="isAdmin()">
								<span class="w3-tag w3-round w3-theme">Admin</span>
							</s:if>
							<s:else>
								<span class="w3-tag w3-round w3-pale-blue">Author</span>
							</s:else>
						</td>
						<td><s:property value="createDateReadable" /></td>
						<td><s:property value="lastLoginDateReadable" /></td>
					</s:iterator>
					</table>
				
				</s:else>
				</s:if>
				<!-- RESULTS END -->
					
				<br />
				<br />
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>