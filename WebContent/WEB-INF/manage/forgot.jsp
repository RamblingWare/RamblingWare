<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Forgot Credentials - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<!-- META_END -->
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-dark">
		<div class="page w3-row">
		
			<div id="page-content">
			
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
						<div class="w3-container"><p class="error"><s:property/></p></div>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
						<div class="w3-container"><p class="warning"><s:property/></p></div>
					</s:iterator>
				</s:if>
			
				<!-- FORGOT BEGIN -->
				<div class="w3-container w3-padding w3-col m3 l4"></div>
				<div class="w3-container w3-padding w3-col m6 l4">
				
				<s:if test="type.equalsIgnoreCase('username')">
					<div class="w3-border w3-round w3-animate-opacity">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Resend Username</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="remind" value="true" />
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:</label>
								<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="icon-redo w3-btn w3-right w3-round w3-card w3-theme-light w3-hover-green" type="submit" value="Send" title="Send a reminder email">Send Reminder</button>
								
								<a class="icon-delete w3-btn w3-round w3-card w3-theme-light w3-hover-theme" title="Go to the home page" href="/">Cancel</a>
							</p>
						</form>
							<p class="footnote w3-center">
								<a href="/manage/">Wait, I remember it now!</a>&nbsp;|&nbsp; 
								<a href="/manage/forgot?type=password">Forgot Password?</a>
							</p>
						</div>
					</div>
				</s:if>
				<s:if test="type.equalsIgnoreCase('password')">
					<div class="w3-border w3-round w3-animate-opacity">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Reset Password</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/forgot" method="post">
							<input type="hidden" name="reset" value="true" />
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:</label>
								<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="icon-redo w3-btn w3-right w3-round w3-card w3-theme-light w3-hover-blue" type="submit" value="Send" title="Send a reset link">Send Reset</button>
								
								<a class="icon-delete w3-btn w3-round w3-card w3-theme-light w3-hover-theme" title="Go to the home page" href="/">Cancel</a>
							</p>
						</form>
							<p class="footnote w3-center">
								<a href="/manage/forgot?type=username">Forgot Username?</a>&nbsp;|&nbsp; 
								<a href="/manage/">Wait, I remember it now!</a>
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