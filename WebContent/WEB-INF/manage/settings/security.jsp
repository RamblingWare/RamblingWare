<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>

<title>Security - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>

	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/manage/settings/settings-tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Security</h1>
				
				<div class="w3-row">
				<div class="w3-container w3-padding w3-col s12 m12 l12">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Security</h3>
						</div>
						<div class="w3-padding w3-small w3-theme-light">
							
							<form action="/manage/settings/security" method="post">
							<input type="hidden" name="security" value="true" />
							
							<s:if test="#session.USER.isOTPEnabled()">
							<p>
								<label for="otpEnabled" class="w3-validate w3-text-grey-light w3-large" for="title">Two-Factor Authentication:</label>
								&nbsp;<span class="w3-tag w3-round w3-pale-green">Enabled</span>
								<br/>
								<span class="w3-small w3-text-grey">Your account is secured with 2FA by using a OTP (One-Time Password) every time you log in.</span>
							</p>
							<hr />
							<p>
								<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="openPopup('setup-2fa')" title="Review 2FA"><span class="icon-qrcode w3-large w3-margin-right"></span>View 2FA</a>
								<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="openPopup('recover-2fa')" title="View Recovery Code"><span class="icon-embed w3-large w3-margin-right"></span>Recovery Code</a>
								&nbsp;
								<a class="w3-small" href="javascript:void(0)" onclick="openPopup('remove-2fa')" value="Disable" title="Disable 2FA">Disable 2FA</a>
							</p>
							</s:if>
							<s:else>
							<p>
								<label for="otpEnabled" class="w3-validate w3-text-grey-light w3-large" for="title">Two-Factor Authentication:</label>
								&nbsp;<span class="w3-tag w3-round w3-pale-red">Disabled</span>
								<br/>
								<span class="w3-small w3-text-grey">Secure your account with 2FA by using a OTP (One-Time Password) every time you log in.</span>
								<span class="w3-small w3-text-grey">
									Use an app on your mobile device to enable this feature. If you do not have an Authentication app
									then I recommend using <a target="_blank" href="https://mattrubin.me/authenticator/">Authenticator</a>, <a target="_blank" href="https://www.authy.com/app/mobile/">Authy</a>, or <a target="_blank" href="https://goo.gl/gtjo1w">Google Authenticator</a>.
								</span>
							</p>
							<hr />
							<p>
								<input type="hidden" name="twofactor" value="true" />
								<a class="w3-btn w3-round w3-card w3-pale-green" href="javascript:void(0)" onclick="openPopup('setup-2fa')" title="Enable"><span class="icon-qrcode w3-large w3-margin-right"></span>Enable 2FA</a>
							</p>
							</s:else>
							</form>
						</div>
					</div>
					
					<div id="recover-2fa" class="w3-modal">
						<div class="w3-modal-content page-quarter w3-theme-light w3-animate-top w3-card-4">
							<div class="w3-container w3-padding-8">
								<h3>Recovery Code
								<a title="Close" onclick="closePopup('recover-2fa')" class="icon-cross w3-text-black w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<p class="w3-small">
									In case you ever lose access to your mobile device or third-party Authentication app. Use this recovery code to login to your account.
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large">Recovery Code:</label>
									<input type="text" size="50" maxlength="20" value="<s:property value="#session.USER.keyRecover" />" readonly class="w3-input w3-round-large w3-border w3-large w3-pale-yellow" />
								</p>
								<p class="w3-small w3-text-grey">
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
						<div class="w3-modal-content page-quarter w3-theme-light w3-animate-top w3-card-4">
							<div class="w3-container w3-padding-8">
								<h3>Enable 2FA
								<a title="Close" onclick="closePopup('setup-2fa')" class="icon-cross w3-text-black w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<form action="/manage/settings" method="post">
								<input type="hidden" name="security" value="true" />
								<p class="w3-center">
								<img class="w3-card w3-round-large" src="https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=otpauth://totp/RamblingWare:<s:property value="#session.USER.email" />?secret=<s:property value="secret" />&issuer=RamblingWare&algorithm=SHA1&digits=6&period=30" />
								<br />
								<span class="w3-small w3-text-grey">
									<b>Secret:</b> <s:property value="secret" />
								</span>
								</p>
								<p class="w3-small">
									To confirm the third-party app is setup correctly. Please enter the security code shown on your mobile device here:
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large" for="code">Security Code:&nbsp;<span class="w3-text-red">*</span></label>
									<input type="text" size="50" maxlength="300" name="code" id="code" value="" required autocapitalize="off" autocorrect="off" autocomplete="off" class="w3-input w3-round-large w3-border w3-large" />
								</p>
								<hr />
								<p>
									<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="closePopup('setup-2fa')" title="Cancel">Cancel</a>
									<button type="submit" class="w3-btn w3-right w3-round w3-card w3-pale-green" value="submit" title="Submit"><span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
								</p>
								</form>
							</div>
						</div>
					</div>
					<div id="remove-2fa" class="w3-modal">
						<div class="w3-modal-content page-quarter w3-theme-light w3-animate-top w3-card-4">
							<div class="w3-container w3-padding-8">
								<h3>Disable 2FA
								<a title="Close" onclick="closePopup('remove-2fa')" class="icon-cross w3-text-black w3-opacity w3-hover-opaque w3-right" href="javascript:void(0);">&nbsp;</a>
								</h3>
								<form action="/manage/settings" method="post">
								<input type="hidden" name="security" value="true" />
								<p class="w3-small w3-text-grey">
									Disabling Two Factor Authentication will bypass the additional security provided from using a OTP (One-Time Password) at every login request.
									Your account will still use a regular password, but would no longer ask for a Two-Factor code.
								</p>
								<p class="w3-small">
									To confirm the removal of this security feature. Please enter the recovery code and password:
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large" for="code">Recovery Code:&nbsp;<span class="w3-text-red">*</span></label>
									<input type="text" size="50" maxlength="20" name="code" id="code" value="" required autocapitalize="off" autocorrect="off" autocomplete="off" class="w3-input w3-round-large w3-border w3-large" />
								</p>
								<p>
									<label class="w3-validate w3-text-grey-light w3-large" for="passwordOld">Password:&nbsp;<span class="w3-text-red">*</span></label>
									<input type="password" size="50" maxlength="300" name="passwordOld" id="passwordOld" value="" required class="w3-input w3-round-large w3-border" />
								</p>
								<hr />
								<p>
									<a class="w3-btn w3-round w3-card w3-theme-light" href="javascript:void(0)" onclick="closePopup('remove-2fa')" title="Cancel">Cancel</a>
									<button type="submit" class="w3-btn w3-right w3-round w3-card w3-pale-red" value="submit" title="Submit"><span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
								</p>
								</form>
							</div>
						</div>
					</div>
					<script>
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
				</div>
			</div>
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>