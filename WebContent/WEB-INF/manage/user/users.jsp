<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>View/Edit Authors - <%=Application.getString("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m10 l10 w3-container w3-padding">
				
				<h1>View/Edit Authors</h1>
				
				<p><a class="w3-btn w3-round w3-small w3-blue w3-hover-indigo" href="/manage/newuser"><span class="icon-author w3-large"></span>&nbsp;Add Author</a>
				<span class="w3-small w3-text-grey nowrap">Add a new Author.</span>
				</p>
				
				<s:if test="users != null">
				<s:if test="users.isEmpty()">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red">
					<span class="icon-cross w3-large w3-margin-right"></span>
						Something went wrong because no users were found. Please try again later?</p>
				</s:if>
				<s:else>
					<p class="w3-small w3-text-grey w3-margin-0 w3-right"><s:property value="users.size()" /> result(s) found.</p>
					<table class="w3-table w3-bordered w3-striped w3-small">
					<tr class="w3-theme-dark uppercase">
						<th></th>
						<th><span class="icon-author w3-large w3-text-theme w3-padding-square" title="Title"></span>Author</th>
						<th><span class="icon-mail w3-large w3-text-theme w3-padding-square" title="Title"></span>Email</th>
						<th><span class="icon-star w3-large w3-text-theme w3-padding-square" title="Title"></span>Role</th>
						<th><span class="icon-time w3-large w3-text-theme w3-padding-square" title="Title"></span>Added</th>
						<th><span class="icon-time w3-large w3-text-theme w3-padding-square" title="Title"></span>Last Login</th>
					</tr>
					
					<s:iterator value="users" status="r">
						<tr>
						<td><a class="w3-btn w3-card w3-round w3-tiny w3-theme-light" href="/manage/edituser/<s:property value="uri" />">Edit</a></td>
						<td>
							<a href="/author/<s:property value="uri" />">
								<img class="w3-round" src="<s:property value="thumbnail" />" height="24px" width="24px" style="vertical-align: middle;" onerror="this.src='/img/error-200.png';this.title='Failed to load image.'" /></a>
							&nbsp;
							<a href="/author/<s:property value="uri" />" class="w3-medium" style="vertical-align: middle;"><s:property value="name" /></a>
						</td>
							<td class="w3-small"><a href="mailto:<s:property value="email" />"><s:property value="email" /></a></td>
						<td>
							<s:if test="role.id % 4 == 0">
								<span class="w3-tag w3-round w3-pale-red" title="<s:property value="role.description" />"><span class="icon-cog"></span>&nbsp;<s:property value="role.name" /></span>
							</s:if>
							<s:elseif test="role.id % 3 == 0">
								<span class="w3-tag w3-round w3-pale-yellow" title="<s:property value="role.description" />"><span class="icon-star"></span>&nbsp;<s:property value="role.name" /></span>
							</s:elseif>
							<s:elseif test="role.id % 2 == 0">
								<span class="w3-tag w3-round w3-pale-blue" title="<s:property value="role.description" />"><span class="icon-quill"></span>&nbsp;<s:property value="role.name" /></span>
							</s:elseif>
							<s:elseif test="role.id % 1 == 0">
								<span class="w3-tag w3-round w3-pale-green" title="<s:property value="role.description" />"><span class="icon-author"></span>&nbsp;<s:property value="role.name" /></span>
							</s:elseif>
						</td>
						<td><s:property value="createDateReadable" /></td>
						<td><s:property value="lastLoginDateReadable" /></td>
					</s:iterator>
					</table>
					
					<%@include file="/WEB-INF/fragment/pagination.jspf" %>				
				</s:else>
				</s:if>
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>