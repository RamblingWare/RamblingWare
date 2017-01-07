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
				
					<s:if test="hasActionErrors()">
					   <s:iterator value="actionErrors">
						<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" data-close=""><s:property/></p>
						</s:iterator>
					</s:if>
					<s:if test="hasActionMessages()">
					   <s:iterator value="actionMessages">
						<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-green w3-text-green w3-border-green" onclick="this.style.display='none'" data-close=""><s:property/></p>
						</s:iterator>
					</s:if>
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Two Factor Authentication</h3>
						</div>
						<div class="w3-padding w3-theme-light">
						<form action="/manage/login" method="post">
							<div class="w3-card w3-round-large w3-theme-light w3-padding w3-large w3-center">
								<img class="w3-round" src="<s:property value="#session.USER.getThumbnail()"/>" align="top" style="width:24px; height:24px;" />
								&nbsp;<s:property value="#session.USER.getName()"/>
							</div>
							<p>   
								<label class="w3-validate w3-text-grey-light w3-large" for="code">Code:</label>
								<input type="text" size="50" maxlength="6" name="code" id="code" value="" required autofocus autocapitalize="off" autocorrect="off" autocomplete="off" placeholder="" class="w3-input w3-round-large w3-xlarge w3-border" />
							</p>
							<hr />
							<p>
								<button class="icon-redo w3-btn w3-right w3-round w3-card w3-pale-green" type="submit" value="Login" title="Login">Login</button>
								
								<a class="icon-delete w3-btn w3-round w3-card w3-theme-light" title="Go to the home page" href="/manage/logout">Cancel</a>
							</p>
						</form>
						
							<p class="footnote w3-center">
								<a href="/manage/forgot?type=twofactor">I don't have this.</a>
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