<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Settings - RamblingWare</title>
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
		
			<div id="page-content" class="w3-col m10 l8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>My Settings</h1>
				<p>Use this page to make changes to your account settings.</p>
		
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
						<p class="error"><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
						<p class="info"><s:property/></p>
					</s:iterator>
				</s:if>
				
				<!-- SETTINGS BEGIN -->
				<div class="w3-row">
				<div class="w3-container w3-padding w3-col m12 l6">
				
					<div class="w3-border w3-round w3-animate-opacity">
						<div class="w3-margin-0 w3-padding-0 w3-theme w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Account</h3>
						</div>
						<div class="w3-padding w3-theme-light">
							<p>Review your account.</p>
						
							<form action="/manage/settings" method="post">
							<input type="hidden" name="account" value="true" />
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="200" name="username" id="username" value="<s:property value="#session.USER.username" />" required class="w3-input w3-round-large w3-hover-light-grey w3-border" />
								<span class="footnote">Your username is used to login.</span>
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="title">Your Full Name:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="#session.USER.name" />" required class="w3-input w3-round-large w3-hover-light-grey w3-border" />
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="200" name="email" id="email" value="<s:property value="#session.USER.email" />" required class="w3-input w3-round-large w3-hover-light-grey w3-border" />
								<span class="footnote">Your email address is used to validate your identity.</span>
							</p>
							<p>
								<s:if test="#session.USER.isAdmin()">
									<label class="w3-validate w3-text-grey-light w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-blue">Admin</span>
									<br/>
									<span class="footnote">Admins have full access.</span>
								</s:if>
								<s:else>
									<label class="w3-validate w3-text-grey-light w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-green">Author</span>
									<br/>
									<span class="footnote">Only the Admin can change your role for you.</span>
								</s:else>
							</p>
							</form>
						</div>
					</div>
				
				</div>
				<div class="w3-container w3-padding w3-col m12 l6">
				
					<div class="w3-border w3-round w3-animate-opacity">
						<div class="w3-margin-0 w3-padding-0 w3-theme w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Password &amp; Security</h3>
						</div>
						<div class="w3-padding w3-theme-light">
							<p>Review your security and authentication.</p>
							
							<form action="/manage/settings" method="post">
							<input type="hidden" name="security" value="true" />
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="title">Password:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="password" id="password" value="" required class="w3-input w3-round-large w3-hover-light-grey w3-border" />
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="title">Password (Verify):&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordAgain" id="passwordAgain" value="" required class="w3-input w3-round-large w3-hover-light-grey w3-border" />
							</p>
							
							<hr />
							
							<p>
								<s:if test="#session.USER.isOTPEnabled()">
									<label for="otpEnabled" class="w3-validate w3-text-grey-light w3-large" for="title">Two-Factor Authentication:</label>
									&nbsp;<span class="w3-tag w3-round w3-green">Enabled</span>
								</s:if>
								<s:else>
									<label for="otpEnabled" class="w3-validate w3-text-grey-light w3-large" for="title">Two-Factor Authentication:</label>
									&nbsp;<span class="w3-tag w3-round w3-red">Disabled</span>
								</s:else>
								<br/>
								<span class="footnote">Secure your account with 2FA by using a OTP (One-Time Password) every time you log in.</span>
							</p>
							
							</form>
						</div>
					</div>
				
				</div>
				<div class="w3-container w3-padding w3-col m12 l12">
				
					<div class="w3-border w3-round w3-animate-opacity">
						<div class="w3-margin-0 w3-padding-0 w3-theme w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Information</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						
							<p>Some important dates regarding your account.</p>
							<p>
							Date your Account was Created:&nbsp;<span class="bold"><s:property value="#session.USER.createDate"/></span>
							<br />
							Date your Account was Last Modified:&nbsp;<span class="bold"><s:property value="#session.USER.modifyDate"/></span>
							<br />
							Date of your Last Login:&nbsp;<span class="bold"><s:property value="#session.USER.lastLoginDate" /></span>
							</p>
							
						
						</div>
					</div>
				
				</div>				
				</div>
				<!-- SETTINGS END -->
			
								
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