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
						<p class="success"><s:property/></p>
					</s:iterator>
				</s:if>
				
				<!-- SETTINGS BEGIN -->
				<div class="w3-row">	
				
				<div class="w3-container w3-padding w3-col m12 l6">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Account</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						
							<form action="/manage/settings" method="post">
							<input type="hidden" name="account" value="true" />
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="200" name="username" id="username" value="<s:property value="#session.USER.username" />" required class="w3-input w3-round-large w3-border" />
								<span class="footnote">Your username is used to login.</span>
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="text" size="50" maxlength="200" name="email" id="email" value="<s:property value="#session.USER.email" />" required class="w3-input w3-round-large w3-border" />
								<span class="footnote">Your email address is used to validate your identity.</span>
							</p>
							<p>
								<s:if test="#session.USER.isAdmin()">
									<label class="w3-validate w3-text-grey-light w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-theme">Admin</span>
									<br/>
									<span class="footnote">Admins have full access.</span>
								</s:if>
								<s:else>
									<label class="w3-validate w3-text-grey-light w3-large" for="title">Role:</label>&nbsp;<span class="w3-tag w3-round w3-pale-blue">Author</span>
									<br/>
									<span class="footnote">Authors can create/edit blog posts.</span>
								</s:else>
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-small w3-round w3-card w3-pale-green" type="submit" value="Save" title="Save Changes">Save Changes</button>
							</p>
							</form>
						</div>
					</div>
				
				</div>
				<div class="w3-container w3-padding w3-col m12 l6">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Password</h3>
						</div>
						<div class="w3-padding w3-theme-light">
							
							<form action="/manage/settings" method="post">
							<input type="hidden" name="password" value="true" />
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="passwordOld">Current Password:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordOld" id="passwordOld" value="" required class="w3-input w3-round-large w3-border" />
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="passwordNew">New Password:&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordNew" id="passwordNew" value="" required class="w3-input w3-round-large w3-border" />
							</p>
							<p>
								<label class="w3-validate w3-text-grey-light w3-large" for="passwordVerify">New Password (Verify):&nbsp;<span class="w3-text-red">*</span></label>
								<input type="password" size="50" maxlength="300" name="passwordVerify" id="passwordVerify" value="" required class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-small w3-round w3-card w3-pale-green" type="submit" value="Save" title="Save Changes">Save Changes</button>
							</p>
							</form>
						</div>
					</div>
				
				</div>
				<div class="w3-container w3-padding w3-col m12 l12">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Security</h3>
						</div>
						<div class="w3-padding w3-theme-light">
							
							<form action="/manage/settings" method="post">
							<input type="hidden" name="security" value="true" />
							
							<s:if test="#session.USER.isOTPEnabled()">
							<p>
								<label for="otpEnabled" class="w3-validate w3-text-grey-light w3-large" for="title">Two-Factor Authentication:</label>
								&nbsp;<span class="w3-tag w3-round w3-pale-green">Enabled</span>
								<br/>
								<span class="footnote">Your account is secured with 2FA by using a OTP (One-Time Password) every time you log in.</span>
							</p>
							<hr />
							<p>
								<a class="w3-btn w3-small w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="openPopup('setup-2fa')" title="Review 2FA">View 2FA</a>
								<a class="w3-btn w3-small w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="openPopup('recover-2fa')" title="View Recovery Code">Recovery Code</a>
								&nbsp;
								<a class="w3-small" href="javascript:void(0)" onclick="openPopup('remove-2fa')" value="Disable" title="Disable 2FA">Disable 2FA</a>
							</p>
							</s:if>
							<s:else>
							<p>
								<label for="otpEnabled" class="w3-validate w3-text-grey-light w3-large" for="title">Two-Factor Authentication:</label>
								&nbsp;<span class="w3-tag w3-round w3-pale-red">Disabled</span>
								<br/>
								<span class="footnote">Secure your account with 2FA by using a OTP (One-Time Password) every time you log in.</span>
							</p>
							<hr />
							<p>
								<input type="hidden" name="twofactor" value="true" />
								<a class="w3-btn w3-small w3-round w3-card w3-pale-green" href="javascript:void(0)" onclick="openPopup('setup-2fa')" title="Enable">Enable 2FA</a>
							</p>
							</s:else>
							</form>
						</div>
					</div>
					
					<div id="recover-2fa" class="w3-modal">
						<div class="w3-modal-content page-quarter w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Recovery Code
								<a title="Close" onclick="closePopup('recover-2fa')" class="icon-delete w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<p>
									In case you ever lose access to your mobile device or third-party Authentication app. Use this recovery code to login to your account.
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large">Recovery Code:</label>
									<input type="text" size="50" maxlength="20" value="<s:property value="#session.USER.keyRecover" />" readonly class="w3-input w3-round-large w3-border w3-large w3-pale-yellow" />
								</p>
								<p class="footnote">
									Keep it somewhere safe where you can always find it!
								</p>
								<hr />
								<p>
									<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="closePopup('recover-2fa')" title="Done">Done</a>
								</p>
							</div>
						</div>
					</div>
					<div id="setup-2fa" class="w3-modal">
						<div class="w3-modal-content page-quarter w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Enable 2FA
								<a title="Close" onclick="closePopup('setup-2fa')" class="icon-delete w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<form action="/manage/settings" method="post">
								<input type="hidden" name="security" value="true" />
								<p>
									Use a third-party Authentication app on your mobile device to enable your two factor authentication. If you do not have an Authentication app
									then we recommend using <a target="_blank" href="https://mattrubin.me/authenticator/">Authenticator</a>, <a target="_blank" href="https://www.authy.com/app/mobile/">Authy</a>, or <a target="_blank" href="https://goo.gl/gtjo1w">Google Authenticator</a>.
								</p>
								<p class="w3-center">
								<img class="w3-card w3-round-large" src="https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=otpauth://totp/RamblingWare:<s:property value="#session.USER.email" />?secret=<s:property value="secret" />&issuer=RamblingWare&algorithm=SHA1&digits=6&period=30" />
								<br />
								<span class="footnote">
									<b>Secret:</b> <s:property value="secret" />
								</span>
								</p>
								<p>
									To confirm the third-party app is setup correctly. Please enter the security code shown on your mobile device here:
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large" for="code">Security Code:&nbsp;<span class="w3-text-red">*</span></label>
									<input type="text" size="50" maxlength="300" name="code" id="code" value="" required autofocus autocapitalize="off" autocorrect="off" autocomplete="off" class="w3-input w3-round-large w3-border w3-large" />
								</p>
								<hr />
								<p>
									<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="closePopup('setup-2fa')" title="Cancel">Cancel</a>
									<button type="submit" class="w3-btn w3-round w3-card w3-pale-green" value="submit" title="Submit">Submit</button>
								</p>
								</form>
							</div>
						</div>
					</div>
					<div id="remove-2fa" class="w3-modal">
						<div class="w3-modal-content page-quarter w3-theme-light w3-animate-top w3-card-16">
							<div class="w3-container w3-padding-8">
								<h3>Disable 2FA
								<a title="Close" onclick="closePopup('remove-2fa')" class="icon-delete w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<form action="/manage/settings" method="post">
								<input type="hidden" name="security" value="true" />
								<p>
									Disabling Two Factor Authentication will bypass the additional security provided from using a OTP (One-Time Password) at every login request.
									Your account will still use a regular password, but would no longer ask for a Two-Factor code.
								</p>
								<p>
									To confirm the removal of this security feature. Please enter the recovery code and password:
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large" for="code">Recovery Code:&nbsp;<span class="w3-text-red">*</span></label>
									<input type="text" size="50" maxlength="20" name="code" id="code" value="" required autofocus autocapitalize="off" autocorrect="off" autocomplete="off" class="w3-input w3-round-large w3-border w3-large" />
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large" for="passwordOld">Password:&nbsp;<span class="w3-text-red">*</span></label>
									<input type="password" size="50" maxlength="300" name="passwordOld" id="passwordOld" value="" required class="w3-input w3-round-large w3-border" />
								</p>
								<hr />
								<p>
									<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="closePopup('remove-2fa')" title="Cancel">Cancel</a>
									<button type="submit" class="w3-btn w3-round w3-card w3-pale-red" value="submit" title="Submit">Submit</button>
								</p>
								</form>
							</div>
						</div>
					</div>
					<script>
					// Get the popup
					var setupPopup = document.getElementById('setup-2fa');
					var recoverPopup = document.getElementById('recover-2fa');
					var removePopup = document.getElementById('remove-2fa');
					
					// When the user clicks anywhere outside of the modal, close it
					window.onclick = function(event) {
					    if (event.target == setupPopup) {
					    	setupPopup.style.display = "none";
					    }
					    if (event.target == recoverPopup) {
					    	recoverPopup.style.display = "none";
					    }
					    if (event.target == removePopup) {
					    	removePopup.style.display = "none";
					    }
					}
					function openPopup(name) {
						document.getElementById(name).style.display='block';
					}
					function closePopup(name) {
						document.getElementById(name).style.display='none';
					}
					</script>
				
				</div>
				<div class="w3-container w3-padding w3-col m12 l12">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">About you</h3>
						</div>
						<div class="w3-padding w3-theme-light">
							<p>Review your public information from your Author page.</p>
							
							
							<span class="w3-col s3 m3 l3 w3-padding-16">
								<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile Picture" src="<s:property value="#session.USER.thumbnail" />">
							</span>
							<span class="w3-col s9 m9 l9 w3-padding-16">
								<span class="footnote w3-padding-right">
								<b><s:property value="#session.USER.name" /></b><br />
								<span class="w3-small"><s:property value="#session.USER.description" /></span> 
								</span>
							</span>								
							<hr />
							<p>
								<a class="w3-btn w3-small w3-round w3-card w3-theme-light" title="Edit author page" href="/manage/edituser/<s:property value="#session.USER.uriName" />">Edit</a>
								<a class="w3-btn w3-small w3-round w3-card w3-theme-light" title="Go to the author page" href="/author/<s:property value="#session.USER.uriName" />">View My Page</a>
							</p>
						
						</div>
					</div>
				
				</div>
				<div class="w3-container w3-padding w3-col m12 l12">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Additional Info</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						
							<p>Some important dates regarding your account.</p>
							<p>
							Date your Account was Created:&nbsp;<span class="bold"><s:property value="#session.USER.createDateReadable"/></span>
							<br />
							Date your Account was Last Modified:&nbsp;<span class="bold"><s:property value="#session.USER.modifyDateReadable"/></span>
							<br />
							Date of your Last Login:&nbsp;<span class="bold"><s:property value="#session.USER.lastLoginDateReadable" /></span>
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