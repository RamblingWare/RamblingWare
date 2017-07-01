<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Login - <%=Application.getSetting("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-dark">
		<div class="page w3-row">
		
			<div id="page-content">
			
				<div class="w3-container w3-padding w3-col m3 l4"></div>
				<div class="w3-container w3-padding w3-col m6 l4">
				
					<div class="w3-border w3-round w3-padding w3-theme-light">
						<div class="w3-margin-0 w3-padding-0 w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Two Factor Code</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/login" method="post">
							<s:if test="#session.USER != null">
							<div class="w3-card w3-round-large w3-theme-light w3-padding w3-large w3-center">
								<img class="w3-round" src="<s:property value="#session.USER.getThumbnail()"/>" align="top" style="width:24px; height:24px;" />
								&nbsp;<s:property value="#session.USER.getName()"/>
							</div>
							</s:if>
							<p>
								<input type="text" size="50" maxlength="6" name="code" id="code" value="" required autofocus autocapitalize="off" autocorrect="off" autocomplete="off" placeholder="Code" class="w3-input w3-round-large w3-xlarge w3-border" />
							</p>
							<p class="w3-small w3-text-grey">Your 2FA code is displayed on your Authenticator app.</p>
							<p class="w3-center">
								<button class="w3-btn-wide w3-round w3-blue w3-hover-indigo" type="submit" value="Login" title="Login">Login</button>
							</p>
						</form>
						
							<p class="w3-small w3-text-grey w3-center">
								<a href="/manage/forgot?type=twofactor">I lost my 2FA device or app.</a>
							</p>
						</div>
					</div>
				</div>
				<div class="w3-container w3-padding w3-col m3 l3"></div>
			</div>
		</div>
	</article>
</body>
</html>