<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Login - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<!-- META_END -->
</head>
<body class="w3-theme-dark" onload="emailFocus()">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
		
			<div id="page-content" class="w3-col m8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Management Menu</h1>
				<p>Please login to continue.</p>
				<div class="w3-container w3-row">
				
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
				
					<!-- LOGIN BEGIN -->
					<div class="w3-container w3-padding w3-col m2 l3"></div>
					<div class="w3-container w3-padding w3-col m8 l6">
					
						<div class="w3-border w3-round w3-animate-opacity">
							<div class="w3-margin-0 w3-padding-0 w3-theme">
								<h3 class="w3-margin-0 w3-padding">Login</h3>
							</div>
							<div class="w3-padding">
							<form action="/manage/login" method="post">
								<p>   
									<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:</label>
									<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required placeholder="" class="w3-input w3-round-large w3-border" />
								</p>
								<p>   
									<label class="w3-validate w3-text-grey-light w3-large" for="password">Password:</label>
									<input type="password" size="50" maxlength="100" name="password" id="password" value="" required placeholder="" class="w3-input w3-round-large w3-border" />
								</p>
								
								<p>
									<button class="icon-redo w3-btn w3-right w3-round w3-card w3-theme-light w3-hover-green" type="submit" value="Login" title="Login">Login</button>
									<span>&nbsp;&nbsp;</span>
									<button class="icon-delete w3-btn w3-round w3-card w3-theme-light w3-opacity" type="reset" value="Reset" title="Reset search fields">Clear</button>
								</p>
							</form>
							</div>
						</div>
					</div>
					<div class="w3-container w3-padding w3-col m2 l3"></div>
					<!-- LOGIN END -->
					
				</div>
				<br />
				<br />
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