<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Forgot Credentials - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-dark">
		<div class="page w3-row">
		
			<div id="page-content">
			
				<div class="w3-container w3-padding w3-col m3 l4"></div>
				<div class="w3-container w3-padding w3-col m6 l4">

				<s:if test="type.equalsIgnoreCase('username') || type.equalsIgnoreCase('password') || type.equalsIgnoreCase('twofactor')">
				<s:if test="type.equalsIgnoreCase('username')">
					<div class="w3-border w3-round w3-padding w3-theme-light">
						<div class="w3-margin-0 w3-padding-0 w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Resend Username</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="remind" value="true" />
							<p>
								<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required autofocus placeholder="Email Address" class="w3-input w3-round-large w3-border" />
							</p>
							<p class="w3-center">
								<button class="w3-btn-wide w3-round w3-deep-orange w3-hover-red" type="submit" value="Send" title="Send a reminder email">Send</button>
							</p>
						</form>
							<p class="w3-small w3-text-grey w3-center">
								<a href="/manage/">Wait, I remember it now.</a>
							</p>
						</div>
					</div>
				</s:if>
				<s:if test="type.equalsIgnoreCase('password')">
					<div class="w3-border w3-round w3-padding w3-theme-light">
						<div class="w3-margin-0 w3-padding-0 w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Reset Password</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="reset" value="true" />
							<p>
								<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required autofocus placeholder="Email Address" class="w3-input w3-round-large w3-border" />
							</p>
							<p class="w3-center">
								<button class="w3-btn-wide w3-round w3-deep-orange w3-hover-red" type="submit" value="Send" title="Send a reset link">Send</button>
							</p>
						</form>
							<p class="w3-small w3-text-grey w3-center"> 
								<a href="/manage/">Wait, I remember it now.</a>
							</p>
						</div>
					</div>
				</s:if>
				<s:if test="type.equalsIgnoreCase('twofactor')">
					<div class="w3-border w3-round w3-padding w3-theme-light">
						<div class="w3-margin-0 w3-padding-0 w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Recover</h3>
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
									<input type="text" size="50" maxlength="100" name="username" id="username" value="<s:property value="username" />" required placeholder="Email Address" class="w3-input w3-round-large w3-border" />
								</p>
								<p>
									<input type="password" size="50" maxlength="200" name="password" id="password" value="" required placeholder="Password" class="w3-input w3-round-large w3-border" />
								</p>
							</s:else>
							<p>
								<input type="text" size="50" maxlength="100" name="code" id="code" value="" required autofocus autocapitalize="off" autocorrect="off" autocomplete="off" placeholder="Recovery Code" class="w3-input w3-round-large w3-border" />
							</p>
							<p class="w3-small w3-text-grey">Your recovery code was created when you enabled 2FA.</p>
							<p class="w3-center">
								<button class="w3-btn-wide w3-round w3-deep-orange w3-hover-red" type="submit" value="Submit" title="Unlock">Unlock</button>
								
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Go to the home page" href="/manage/logout">Cancel</a>
							</p>
						</form>
							<p class="w3-small w3-text-grey w3-center"> 
								<a href="javascript: void(0);" onclick="alert('Please contact your system admin.')">What if I don't have this?</a>
							</p>
						</div>
					</div>
				</s:if>
				</s:if>
				<s:else>
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Forgot</h3>
						</div>
						<div class="w3-padding w3-theme-light">
							<p>   
								What did you forget?
							</p>
							<hr />
							<p>   
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Forgot Username" href="/manage/forgot?type=username">Username</a>
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Forgot Password" href="/manage/forgot?type=password">Password</a>
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Forgot 2FA device" href="/manage/forgot?type=twofactor">2FA</a>
							</p>
							<p class="w3-small w3-text-grey w3-center">
								<a href="/manage/">Back to Login.</a>
							</p>
						</div>
					</div>
				</s:else>
				
				</div>
				<div class="w3-container w3-padding w3-col m3 l3"></div>
			</div>
		</div>
	</article>	
</body>
</html>