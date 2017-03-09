<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Login - RamblingWare</title>
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
			
				<!-- LOGIN BEGIN -->
				<div class="w3-container w3-padding w3-col m3 l4"></div>
				<div class="w3-container w3-padding w3-col m6 l4">
					
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Please Login</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/login" method="post">
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:</label>
								<input type="text" size="50" maxlength="100" name="username" id="username" value="<s:property value="username" />" required autofocus placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="password">Password:</label>
								<input type="password" size="50" maxlength="200" name="password" id="password" value="" required placeholder="" class="w3-input w3-round-large w3-border" />
							</p>
							<hr />
							<p>
								<button class="w3-btn w3-right w3-round w3-card w3-pale-green" type="submit" value="Login" title="Login">
									<span class="icon-key w3-large w3-margin-right"></span>Login</button>
								
								<a class="w3-btn w3-round w3-card w3-theme-light" title="Go to the home page" href="/">Cancel</a>
							</p>
						</form>
						
							<p class="footnote w3-center">
								<a href="/manage/forgot?type=username">Forgot Username?</a>&nbsp;|&nbsp; 
								<a href="/manage/forgot?type=password">Forgot Password?</a>
							</p>
						</div>
					</div>
				</div>
				<div class="w3-container w3-padding w3-col m3 l3"></div>
				<!-- LOGIN END -->
					
				
				<br />
				<br />
				<br />
				<br />
			</div>
		</div>
	</article>
	
</body>
</html>