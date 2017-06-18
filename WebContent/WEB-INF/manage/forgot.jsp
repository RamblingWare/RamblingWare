<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>

<title>Forgot Credentials - RamblingWare</title>
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-dark">
		<div class="page w3-row">
		
			<div id="page-content">
			
				<!-- FORGOT BEGIN -->
				<div class="w3-container w3-padding w3-col m3 l4"></div>
				<div class="w3-container w3-padding w3-col m6 l4">
					
				<s:if test="type.equalsIgnoreCase('username')">
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Resend Username</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="remind" value="true" />
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:</label>
								<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required autofocus placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-right w3-round w3-card w3-pale-blue" type="submit" value="Send" title="Send a reminder email">Send Reminder</button>
								
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Go to the home page" href="/">Cancel</a>
							</p>
						</form>
							<p class="w3-small w3-text-grey w3-center">
								<a href="/manage/">Wait, I remember it now.</a>
							</p>
						</div>
					</div>
				</s:if>
				<s:if test="type.equalsIgnoreCase('password')">
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Reset Password</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="reset" value="true" />
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:</label>
								<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required autofocus placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-right w3-round w3-card w3-pale-red" type="submit" value="Send" title="Send a reset link">Send Reset</button>
								
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Go to the home page" href="/">Cancel</a>
							</p>
						</form>
							<p class="w3-small w3-text-grey w3-center"> 
								<a href="/manage/">Wait, I remember it now.</a>
							</p>
						</div>
					</div>
				</s:if>
				<s:if test="type.equalsIgnoreCase('twofactor')">
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Two Factor Recovery</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="recover" value="true" />
							
							<s:if test="#session.USER != null">
							<div class="w3-card w3-round-large w3-theme-light w3-padding w3-large w3-center">
								<img class="w3-round" src="<s:property value="#session.USER.getThumbnail()"/>" align="top" style="width:24px; height:24px;" />
								&nbsp;<s:property value="#session.USER.getName()"/>
							</div>
							</s:if>
							<s:else>
								<p>   
									<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:</label>
									<input type="text" size="50" maxlength="100" name="username" id="username" value="<s:property value="username" />" required placeholder="" class="w3-input w3-round-large w3-border" />
								</p>
								<p>   
									<label class="w3-validate w3-text-grey-light w3-large" for="password">Password:</label>
									<input type="password" size="50" maxlength="200" name="password" id="password" value="" required placeholder="" class="w3-input w3-round-large w3-border" />
								</p>
							</s:else>
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="code">Recovery Code:</label>
								<input type="text" size="50" maxlength="100" name="code" id="code" value="" required autofocus autocapitalize="off" autocorrect="off" autocomplete="off" placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<p class="w3-small w3-text-grey">Your recovery code was created when you enabled 2FA.</p>
							<hr />
							<p>
								<button class="w3-btn w3-right w3-round w3-card w3-pale-red" type="submit" value="Submit" title="Unlock">Unlock</button>
								
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Go to the home page" href="/manage/logout">Cancel</a>
							</p>
						</form>
							<p class="w3-small w3-text-grey w3-center"> 
								<a href="javascript: void(0);" onclick="alert('Please contact your system admin.')">What if I don't have this?</a>
							</p>
						</div>
					</div>
				</s:if>
				
				</div>
				<div class="w3-container w3-padding w3-col m3 l3"></div>
				<!-- FORGOT END -->
					
				
				<br />
				<br />
				<br />
				<br />
			</div>
		</div>
	</article>
	
</body>
</html>