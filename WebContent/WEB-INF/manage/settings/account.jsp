<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Account - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Account</h1>
				
				<div class="w3-row">	
				<div class="w3-container w3-padding w3-col s12 m12 l6">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Account</h3>
						</div>
						<div class="w3-padding w3-small w3-theme-light">
						
							<form action="/manage/settings/account" method="post">
							<input type="hidden" name="account" value="true" />
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="200" name="username" id="username" value="<s:property value="#session.USER.username" />" required class="w3-input w3-round-large w3-border" />
								<span class="w3-small w3-text-grey">Your username is used to login.</span>
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="200" name="email" id="email" value="<s:property value="#session.USER.email" />" required class="w3-input w3-round-large w3-border" />
								<span class="w3-small w3-text-grey">Your email address is used to validate your identity.</span>
							</p>
							<p>
								<s:if test="#session.USER.getRole() == 0">
									<label class="w3-validate w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-medium w3-pale-blue"><span class="icon-author"></span>&nbsp;Author</span>
									<br/>
									<span class="w3-small w3-text-grey">Authors can create/edit their own posts. Edit their Profile page.</span>
								</s:if>
								<s:elseif test="#session.USER.getRole() == 1">
									<label class="w3-validate w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-medium w3-pale-blue"><span class="icon-search"></span>&nbsp;Editor</span>
									<br/>
									<span class="w3-small w3-text-grey">Editors can create/edit any post. See all hidden posts. Edit any Profile page.</span>
								</s:elseif>
								<s:elseif test="#session.USER.getRole() == 2">
									<label class="w3-validate w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-medium w3-pale-yellow"><span class="icon-star"></span>&nbsp;Owner</span>
									<br/>
									<span class="w3-small w3-text-grey">Owners can create/edit any post. Edit their own Profile page. Add/Delete users.</span>
								</s:elseif>
								<s:elseif test="#session.USER.getRole() == 3">
									<label class="w3-validate w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-medium w3-theme"><span class="icon-eye"></span>&nbsp;Admin</span>
									<br/>
									<span class="w3-small w3-text-grey">Admins can edit any post. See all hidden posts. Edit any profile pages. No profile page. Add/Delete users.</span>
								</s:elseif>
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-round w3-card w3-pale-green" type="submit" value="Save" title="Save Changes">Save Changes</button>
							</p>
							</form>
						</div>
					</div>
				
				</div>
				</div>	
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>	
</body>
</html>